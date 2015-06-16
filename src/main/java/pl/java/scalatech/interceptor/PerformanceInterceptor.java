package pl.java.scalatech.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class PerformanceInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "PERF_START";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(START_TIME, new Long(System.currentTimeMillis()));
        return true;
    }

    @Override
    //TODO debug instead of info
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (request.getRequestURI().endsWith(".js") || request.getRequestURI().endsWith(".css") || request.getRequestURI().endsWith(".pgn")
                || request.getRequestURI().endsWith(".jpg"))
            return;
        Long startTime = (Long) request.getAttribute(START_TIME);
        request.removeAttribute("startTime");
        if (startTime != null) {
            String uri = request.getRequestURI();
            String query = request.getQueryString();
            long last = System.currentTimeMillis() - startTime.longValue();
            String cut = request.getContextPath() + "" + request.getServletPath();
            uri.replace(cut, "");

            if (query != null) {
                uri = uri + '?' + query;
                log.debug("URL: " + uri);
            }
            log.debug("Execute after rendering: " + last + "ms.");

        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long startTime = (Long) request.getAttribute(START_TIME);
        long last = System.currentTimeMillis() - startTime.longValue();
        if (modelAndView != null) {
            modelAndView.addObject("handlingTime", last);
        } else {

            request.setAttribute("handlingTime", last);
        }

    }

}
