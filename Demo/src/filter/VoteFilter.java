package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 针对投票来处理的Filter
 */
@WebFilter("/VoteServlet")
public class VoteFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Cookie[] cookies = request.getCookies();
        Cookie vote = null;
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("vot")){
                    vote=cookie;
                }
            }
        }
        if (vote!=null){
            String value = vote.getValue();
            int num = Integer.parseInt(value);
            if(num > 1){
                chain.doFilter(request,response);
                num--;
                vote.setValue(num+"");
                response.addCookie(vote);
                response.getWriter().write("还剩"+num+"次");
            }else{
                response.getWriter().write("今日最大投票次数已超上限！！！请明天再来");
            }

        }else{
            vote = new Cookie("vot", "5");
            vote.setMaxAge(60*60*24);
            response.addCookie(vote);
            chain.doFilter(request, response);//投票动作
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {

    }

}
