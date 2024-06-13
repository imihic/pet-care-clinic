package hr.tvz.application.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;


import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    @Value("${my.app.auth.secret}")
    private String authSecret;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/admin-only/**"))
                .hasAnyRole("admin");
        http.authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/public/**"))
                .permitAll();

        http.authorizeHttpRequests(
                authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll());

        // Icons from the line-awesome addon
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll());

        super.configure(http);
        // Disable creating and using sessions in Spring Security
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Register your login view to the view access checker mechanism
        setLoginView(http, "/login");

        http.oauth2Login()
                .loginPage("/login").permitAll().and()
                .logout().logoutSuccessUrl("/").permitAll();

        // Enable stateless authentication
        setStatelessAuthentication(http,
                new SecretKeySpec(Base64.getDecoder().decode(authSecret),
                        JwsAlgorithms.HS256),
                "com.example.application");
    }

}
