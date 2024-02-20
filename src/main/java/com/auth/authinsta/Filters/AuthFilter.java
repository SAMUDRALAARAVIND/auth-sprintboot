package com.auth.authinsta.Filters;

import com.auth.authinsta.auth.JwtManager;
import com.auth.authinsta.auth.UserContext;
import com.auth.authinsta.auth.model.Role;
import com.auth.authinsta.auth.model.UserInfo;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Order(2)
@Component
@Slf4j
public class AuthFilter implements Filter {
    private static final String unAuthorizedMessage = "You're unauthorized to perform this operation" ;
    private static final String accessDeniedMessage = "You don't have permissions to this";
    private static final int accessDeniedStatusCode = 401 ;
    private static final int unAuthorizedStatusCode = 403 ;

    @Autowired
    JwtManager jwtManager;

    @Autowired
    UserContext userContext;


    public void sendUnAuthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(unAuthorizedStatusCode);
        response.setContentType("application/json");
        response.getWriter().write(
            String.format("{\"message\": \"%s\",\"statusCode\": %d}", unAuthorizedMessage, unAuthorizedStatusCode)
        );
    }

    private void sendAccessDeniedResponse(HttpServletResponse response) throws  IOException{
        response.setStatus(accessDeniedStatusCode);
        response.setContentType("application/json");
        response.getWriter().write(
                String.format("{\"message\": \"%s\",\"statusCode\": %d}", accessDeniedMessage, accessDeniedStatusCode)
        );
    }

    private void handleInvalidTokenResponse(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException , IOException{
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestedRoute = httpServletRequest.getRequestURI();

        if(!isPrivateRoute(requestedRoute)){
            // if the requested url is not a private route, then 404 error need to be shown to the user.
            filterChain.doFilter(request, response);
        }
        else {
            // if the requested url is private route then 401 should be sent as response as the token is invalid.
            sendAccessDeniedResponse(httpServletResponse);
        }
    }

    private boolean isPrivateRoute(String endpoint){
        return Arrays.asList("/user/details").contains(endpoint);
    }

    private boolean authorizeUserByRole(Role userRole, String endpoint){
        boolean authorized = false;
        if(userRole.equals(Role.ADMIN)){
           authorized =  Arrays.asList("/user/details").contains(endpoint);
        }
        else if(userRole.equals(Role.STUDENT)){
            authorized = Arrays.asList("/user/details").contains(endpoint);
        }
        return authorized;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = ( HttpServletRequest )  request;
        HttpServletResponse httpServletResponse = ( HttpServletResponse )  response;

        String endpoint = httpServletRequest.getRequestURI();

        String authToken = httpServletRequest.getHeader("Authorization");

        log.info("Received request for Endpoint: "+endpoint+" with token: "+authToken);

        if(authToken == null || authToken.isEmpty()){
            // Token not present then only public endpoints should be exposed
            handleInvalidTokenResponse(request, response, filterChain);
        }
        else {
            // sample token format : "Bearer: actual_token"
            authToken = authToken.substring(8);
            boolean isTokenValid = jwtManager.validateToken(authToken) ;
            log.info("Token validation isTokenValid: "+isTokenValid);
            if(!isTokenValid){
                handleInvalidTokenResponse(request, response, filterChain);
            }
            else {
                UserInfo userInfo = jwtManager.getUserInfo(authToken);
                if(userInfo != null && authorizeUserByRole(userInfo.getUserRole(), endpoint)){
                    log.info("Token validated with userId: "+ userInfo.getUserId() + " with role: "+userInfo.getUserRole());
                    // Fill user context with the current user info.
                    userContext.setRole(userInfo.getUserRole());
                    userContext.setUserId(userInfo.getUserId());
                    filterChain.doFilter(request, response);
                }
                else {
                    sendUnAuthorizedResponse(httpServletResponse);
                }
            }
        }
    }
}
