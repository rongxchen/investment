package rongxchen.investment.interceptors;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import rongxchen.investment.annotations.LoginToken;
import rongxchen.investment.exceptions.HttpException;
import rongxchen.investment.models.JwtParamMap;
import rongxchen.investment.util.JwtUtil;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }
        LoginToken loginToken = null;
        // find from class first, and then from method
        if (method.getClass().isAnnotationPresent(LoginToken.class)) {
            loginToken = method.getClass().getAnnotation(LoginToken.class);
        }
        if (method.getMethodAnnotation(LoginToken.class) != null) {
            loginToken = method.getMethodAnnotation(LoginToken.class);
        }
        if (loginToken == null) {
            return true;
        }
        // check if token is required
        if (loginToken.required()) {
            String authorization = request.getHeader("Authorization");
            if (StrUtil.isBlank(authorization)) {
                throw new HttpException(HttpStatus.UNAUTHORIZED, "invalid token");
            }
            String token = authorization.replace("Bearer ", "");
            JwtParamMap paramMap = JwtUtil.decodeToken(token);
            request.setAttribute("appId", paramMap.getAppId());
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
