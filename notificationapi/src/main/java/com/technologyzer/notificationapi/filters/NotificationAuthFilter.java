package com.technologyzer.notificationapi.filters;

import com.technologyzer.notificationapi.models.context.NotificationContext;
import com.technologyzer.notificationapi.models.context.NotificationContextHolder;
import com.technologyzer.notificationapi.utils.CommonUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.technologyzer.notificationapi.constants.ApplicationConstants.X_TENANT_ID;

@Component
public class NotificationAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(isValidApi(request.getRequestURI())) {
            String xTenantId = request.getHeader(X_TENANT_ID);
            if(CommonUtils.isEmpty(xTenantId)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized! API key is required!");
                return;
            }
            NotificationContextHolder.setContext(new NotificationContext(xTenantId, false));
        }
        filterChain.doFilter(request,response);
        if(isValidApi(request.getRequestURI())) {
            NotificationContextHolder.clear();
            MDC.clear();
        }
    }
    private boolean isValidApi(final String apiPath){
       return apiPath.startsWith("/api");
    }
}
