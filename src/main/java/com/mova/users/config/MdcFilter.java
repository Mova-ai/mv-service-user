package com.mova.users.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Component
public class MdcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            // Crear un ID único por request
            String requestId = UUID.randomUUID().toString();
            MDC.put("requestId", requestId);

            chain.doFilter(request, response);
        } finally {
            // Siempre limpiar para evitar "fugas" entre hilos
            MDC.clear();
        }
    }
}
