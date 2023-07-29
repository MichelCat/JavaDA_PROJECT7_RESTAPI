package com.nnk.springboot.configuration;

import com.nnk.springboot.Exception.MessagePropertieFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PoseidonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     *  Custom success handler
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }


    /**
     *  Maps the user to the target URL
     */
    protected void handle(
                            HttpServletRequest request,
                            HttpServletResponse response,
                            Authentication authentication
                            ) throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            log.debug(MessagePropertieFormat.getMessage("logger.AlreadyCommittedUnableRedirect", targetUrl));
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }


    /**
     *  Returns the mapped URL for the user's first role in the authorities collection
     */
    protected String determineTargetUrl(final Authentication authentication) {
        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("USER", "/bidList/list");
        roleTargetUrlMap.put("ADMIN", "/home");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }


    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
