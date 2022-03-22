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

    @Order(1)
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
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .exceptionHandling()
                    .defaultAuthenticationEntryPointFor(jwtAuthenticationEntryPoint, PROTECTED_URLS)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin()
                    .and()
                    .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/api/auth/**").permitAll()
                    .antMatchers("/api/user/**").permitAll()
                    .requestMatchers(PROTECTED_URLS).authenticated()
                    .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .logout().disable()
                    .apply(new JwtSecurityConfig(tokenProvider));
        }
    }

    @Order(2)
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