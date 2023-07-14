package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import ro.msg.learning.shop.exception.AuthenticationTypeNotImplementedException;
import ro.msg.learning.shop.util.JwtUtil;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    public static final String BASIC_AUTH = "with-basic";
    public static final String FORM_AUTH = "with-form";
    public static final String OAUTH2_AUTH = "with-oauth";

    @Autowired
    private AuthenticationManagerBuilder authManagerBuilder;

    @Autowired(required = false)
    private RSAPrivateKey privateKey;

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
            case OAUTH2_AUTH:
                http.addFilter(getAuthenticationFilter())
                        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                break;
            default:
                throw new AuthenticationTypeNotImplementedException();
        }
    }

    @Bean
    @ConditionalOnProperty(name = "authentication.type", havingValue = OAUTH2_AUTH)
    public JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
        return JwtUtil.getJwtDecoder(rsaPublicKey);
    }

    public CustomAuthenticationFilter getAuthenticationFilter() {
        final CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authManagerBuilder.getOrBuild(), privateKey);
        filter.setFilterProcessesUrl("/login");

        return filter;
    }

}
