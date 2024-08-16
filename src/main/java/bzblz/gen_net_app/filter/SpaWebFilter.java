package bzblz.gen_net_app.filter;

import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.services.AccountsService;
import bzblz.gen_net_app.services.ProjectsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class SpaWebFilter extends OncePerRequestFilter {
    private final AccountsService accountsService;


    public SpaWebFilter(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    private void initSessionAccount() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            System.out.println("ensureSessionAccount SessionId: " + RequestContextHolder.currentRequestAttributes().getSessionId());
            accountsService.ensureSessionAccount(RequestContextHolder.currentRequestAttributes().getSessionId());
        }
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Init db for session
        initSessionAccount();

        String path = request.getRequestURI().toLowerCase();
        log.info("SpaWebFilter path: " + path);

        if (
//                !path.equals("/") &&
                !path.startsWith("/api") &&
                !path.startsWith("/page") &&
                !path.startsWith("/static") &&
                !path.startsWith("/manifest.json") &&
                !path.startsWith("/favicon.ico") &&
                !path.startsWith("/robots.txt") &&
//                !path.startsWith("/app.bundle.js") &&
//                !path.startsWith("/runtime.bundle.js") &&
                !path.endsWith("xml") &&
                !path.endsWith("json") &&
                !path.endsWith("jpg") &&
                !path.endsWith("jpeg") &&
                !path.endsWith("gif") &&
                !path.endsWith("png")) {
            log.info("SpaWebFilter forwarding to /index.html from path: " + path);
            request.getRequestDispatcher("/index.html").forward(request, response);
            return;
        }


        log.info("SpaWebFilter sent along its way path: " + path);
        filterChain.doFilter(request, response);
    }
}
