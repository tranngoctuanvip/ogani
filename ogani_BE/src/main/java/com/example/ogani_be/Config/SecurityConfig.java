package com.example.ogani_be.Config;

import com.example.ogani_be.Config.Security.UserPrincical.Userdetail;
import com.example.ogani_be.Config.Security.jwt.JwtEntryPoint;
import com.example.ogani_be.Config.Security.jwt.JwtTokenFilter;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtEntryPoint jwtEntryPoint;
    @Autowired
    private Userdetail userdetail;
    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }
        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userdetail);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("Access-Control-Allow-Origin"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(false);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().csrf().disable();

       http.authorizeRequests().antMatchers("/product/getAll/**","/product/lastestProduct","/category/getAll",
                       "/auth/signin/**","/auth/signup/**","/auth/logout","/auth/sendEmail","/auth/resetPassword","/images/**",
                       "/product/getProduct/**","/category/getNameAndCode/**", "/product/update/**","/payment/**",
                       "/product/findById/**","/blog/getBlog/**","/blog/selectTop3","/product/topRatedProduct","/auth/getUser").permitAll()

               .antMatchers("/product/update/**","/product/create/**","/blog/create/**","/blog/update/**",
                       "/statistical/**","/auth/getUser").hasAnyAuthority("ADMIN","STAFF")

               .antMatchers("/product/delete/**","/blog/delete/**","/category/delete/**").hasAnyAuthority("ADMIN")

               .antMatchers("/cart/create/**","/cart/unpaid","/cart/getUnpaidCart","/cart/deleteCart/**",
                       "/cart/update/**","/cart/unpaid","/auth/logout","/auth/changePassword/**","/comment/create/**",
                       "/order/create/**","/order/getAll","/payment/totalPayment","/payment/createPayment","/chat/send",
                       "/chat/bot/**").hasAnyAuthority("USER","SHIPPER","STAFF","ADMIN")

               .antMatchers("/order/update/**").hasAnyAuthority("SHIPPER","ADMIN","STAFF")

                .anyRequest().authenticated().and()
                .logout().logoutUrl("/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
