package com.deepamart;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter(urlPatterns = {
    "/admin-dashboard.jsp",
    "/seller-dashboard.jsp",
    "/products",
    "/my-orders",
    "/cart",
    "/checkout",
    "/review"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        // Not logged in
        if (session == null ||
            session.getAttribute("username") == null) {

            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String role = (String) session.getAttribute("role");
        String uri = req.getRequestURI();

        // Admin page
        if (uri.endsWith("/admin-dashboard.jsp")
                && !"ADMIN".equalsIgnoreCase(role)) {

            res.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Access Denied");
            return;
        }

        // Seller page
        if (uri.endsWith("/seller-dashboard.jsp")
                && !"SELLER".equalsIgnoreCase(role)) {

            res.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Access Denied");
            return;
        }

        // Other protected pages
        chain.doFilter(request, response);
    }
}