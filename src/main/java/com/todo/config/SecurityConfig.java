package com.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final EntryPoint entryPoint;

    @Autowired
    public SecurityConfig(
            @Qualifier("delegatedEntryPoint") EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails omer = User.builder().username("omer").password("{noop}test123").roles("EMPLOYEE").build();
        UserDetails faruk = User.builder().username("faruk").password("{noop}test123").roles("EMPLOYEE", "MANAGER").build();
        UserDetails tester = User.builder().username("tester").password("{noop}test123").roles("EMPLOYEE", "MANAGER", "ADMIN").build();

        return new InMemoryUserDetailsManager(omer, faruk, tester);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(HttpMethod.GET, "api/v1/todos").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "api/v1/todos/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "api/v1/todos").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PUT, "api/v1/todos/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PATCH, "api/v1/todos/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "api/v1/todos/**").hasRole("ADMIN")
        );


//        http.exceptionHandling(customizer -> customizer
//                .accessDeniedHandler((request, response, accessDeniedException) -> {
//                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                    response.setContentType("application/json");
//                    try (PrintWriter writer = response.getWriter()) {
//                        ForbiddenException forbiddenException = new ForbiddenException();
//                        ApiError apiError = new ApiError();
//                        apiError.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                        apiError.setMessage(forbiddenException.getMessage());
//                        apiError.setPath(request.getRequestURI());
//                        writer.println(new ObjectMapper().writeValueAsString(apiError));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }).authenticationEntryPoint((request, response, authException) -> {
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    response.setContentType("application/json");
//                    try (PrintWriter writer = response.getWriter()) {
//                        UnauthorizedException unauthorizedException = new UnauthorizedException();
//                        ApiError apiError = new ApiError();
//                        apiError.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                        apiError.setMessage(unauthorizedException.getMessage());
//                        apiError.setPath(request.getRequestURI());
//                        writer.println(new ObjectMapper().writeValueAsString(apiError));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                })
//        );

        http.exceptionHandling(customizer -> customizer.authenticationEntryPoint(entryPoint));


        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}