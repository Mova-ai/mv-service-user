// security/FirebaseAuthFilter.java
package com.mova.users.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

public class FirebaseAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String idToken = header.substring(7);
            try {
                FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(idToken);

                // Authorities básicas: desde custom claims ("roles") si las usas
                var roles = decoded.getClaims().containsKey("roles")
                        ? ((java.util.List<String>) decoded.getClaims().get("roles"))
                        .stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toSet())
                        : java.util.Set.<GrantedAuthority>of(new SimpleGrantedAuthority("ROLE_USER"));

                var auth = new UsernamePasswordAuthenticationToken(
                        decoded.getUid(), null, roles);
                // puedes adjuntar email/nombre en details
                auth.setDetails(decoded.getEmail());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
