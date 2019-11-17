package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限校验的过滤器
 */
@WebFilter({"/VoteServlet", "/index.html"})
public class PermissionFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Object user = request.getSession().getAttribute("user");
        if (user!=null){
            chain.doFilter(request,response);
        }else response.getWriter().write("您还没登录，没有权限访问！<a href='login.html'>去登录</a>");
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {

    }

}
