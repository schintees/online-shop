package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ro.msg.learning.shop.exception.AuthenticationTypeNotImplementedException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    public static final String BASIC_AUTH = "with-basic";
    public static final String FORM_AUTH = "with-form";

    @Value("#{'${authentication.type}'.toLowerCase()}")
    private String authenticationType;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable);
        configureAuthentication(http);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring()
                .requestMatchers("/test-database/**", "/error/**")
                .requestMatchers(HttpMethod.POST, "/customers");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void configureAuthentication(HttpSecurity http) throws Exception {
        switch (authenticationType) {
            case BASIC_AUTH:
                http.httpBasic(Customizer.withDefaults());
                break;
            case FORM_AUTH:
                http.formLogin(Customizer.withDefaults());
                break;
            default:
                throw new AuthenticationTypeNotImplementedException();
        }
    }

}
