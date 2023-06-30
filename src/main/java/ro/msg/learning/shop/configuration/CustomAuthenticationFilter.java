package ro.msg.learning.shop.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.msg.learning.shop.model.UserLoginRequestModel;
import ro.msg.learning.shop.model.UserLoginResponseModel;
import ro.msg.learning.shop.util.JwtUtil;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final RSAPrivateKey privateKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, RSAPrivateKey privateKey) {
        this.authenticationManager = authenticationManager;
        this.privateKey = privateKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            UserLoginRequestModel credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), UserLoginRequestModel.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        String token = JwtUtil.generateToken(auth, privateKey);
        res.getWriter().write(objectMapper.writeValueAsString(new UserLoginResponseModel(token)));
        res.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

}
