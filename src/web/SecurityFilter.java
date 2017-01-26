package web;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;

public class SecurityFilter implements Filter {
    private Pattern secureUrlsPattern;

    @Override
    public void init(FilterConfig config) throws ServletException {
        secureUrlsPattern = Pattern.compile(config.getInitParameter("secure-urls-pattern"));
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        String contextPath = request.getServletContext().getContextPath();
        String uri = request.getRequestURI().substring(contextPath.length());
        if(secureUrlsPattern.matcher(uri).matches()) {
            HttpSession session = request.getSession(false);
            if(session != null) {
                User user = (User)session.getAttribute("user");
                if(user != null) {
                    chain.doFilter(req, resp);
                    return;
                }
            }
            ((HttpServletResponse)resp).sendRedirect(contextPath + "/login.html");
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {}
}