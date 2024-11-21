package bzblz.gen_net_app.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("getRequestURI: " + request.getRequestURI());
        System.out.println("getId: " + request.getSession().getId());
        System.out.println("isNew" + request.getSession().isNew());
//        System.out.println("getCookies.length" + request.getCookies().length);

        filterChain.doFilter(request, response);
    }
}
