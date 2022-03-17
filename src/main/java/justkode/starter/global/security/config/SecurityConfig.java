package justkode.starter.global.security.config;

import justkode.starter.global.security.handler.JwtAccessDeniedHandler;
import justkode.starter.global.security.entry.JwtAuthenticationEntryPoint;
import justkode.starter.global.security.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Order(2)
    @RequiredArgsConstructor
    @Configuration
    public static class JWTRestSecurityConfig extends WebSecurityConfigurerAdapter {
        private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
                new AntPathRequestMatcher("/api/**")
        );

        private final TokenProvider tokenProvider;
        private final CorsFilter corsFilter;
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                    .csrf().disable()

                    .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                    .exceptionHandling()
                    .defaultAuthenticationEntryPointFor(jwtAuthenticationEntryPoint, PROTECTED_URLS)
                    .accessDeniedHandler(jwtAccessDeniedHandler)

                    // enable h2-console
                    .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin()

                    // 세션을 사용하지 않기 때문에 STATELESS로 설정
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/auth/**").permitAll()
                    .antMatchers("/api/user/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .apply(new JwtSecurityConfig(tokenProvider));
        }
    }

    @Order(1)
    @Configuration
    public static class SwaggerSecurityConfig extends WebSecurityConfigurerAdapter {
        private static final RequestMatcher SWAGGER_URLS = new OrRequestMatcher(
                new AntPathRequestMatcher("/swagger-ui/**")
        );

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .requestMatchers(SWAGGER_URLS).authenticated()
                    .and()
                    .csrf().disable()
                    .httpBasic();
        }
    }
}