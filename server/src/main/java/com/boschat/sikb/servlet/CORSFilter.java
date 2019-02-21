package com.boschat.sikb.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static com.boschat.sikb.common.configuration.ApplicationProperties.CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_HEADERS;
import static com.boschat.sikb.common.configuration.ApplicationProperties.CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_METHODS;
import static com.boschat.sikb.common.configuration.ApplicationProperties.CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_ORIGIN;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_ACCESS_CONTROL_ALLOW_HEADERS;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_ACCESS_CONTROL_ALLOW_METHODS;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_ACCESS_CONTROL_ALLOW_ORIGIN;

/**
 * Servlet Filter implementation class CORSFilter
 */
// Enable it for Servlet 3.x implementations
/* @ WebFilter(asyncSupported = true, urlPatterns = { "/*" }) */
@Provider
public class CORSFilter implements Filter {

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.setHeader(HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_ORIGIN.getValue());
        resp.setHeader(HEADER_ACCESS_CONTROL_ALLOW_METHODS, CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_METHODS.getValue());
        resp.setHeader(HEADER_ACCESS_CONTROL_ALLOW_HEADERS, CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_HEADERS.getValue());

        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (request.getMethod().equals(HttpMethod.OPTIONS)) {
            // Authorize (allow) all domains to consume the content
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        chain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
        // Nothing to destroy
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig fConfig) {
        // Nothing to init
    }

}