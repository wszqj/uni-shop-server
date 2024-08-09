package com.wszqj.interceptors;

import com.wszqj.utils.JwtUtil;
import com.wszqj.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import static com.wszqj.constant.JWTConstant.AUTHORIZATION_HEADER;
import static com.wszqj.constant.JWTConstant.TOKEN;

/**
 * @ClassName LoginInterceptor
 * @description: 处理基于JWT和Redis的登录认证拦截器
 * @date 2024年07月24日
 * @version: 1.0
 */
@Component
@AllArgsConstructor
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求路径
        String requestURI = request.getRequestURI();

        // 检查请求路径是否是公开资源或Swagger UI
        if (requestURI.startsWith("/uploads/") ||
                requestURI.startsWith("/swagger-ui.html") ||
                requestURI.startsWith("/doc.html") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/webjars/") ||
                requestURI.startsWith("/swagger-resources/") ||
                requestURI.startsWith("/csrf")) {
            // 允许公开访问的资源和Swagger UI不进行认证
            return true;
        }

        // 从请求头中获取token
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.hasLength(token)) {
            // 如果token为空或不存在，记录警告并返回401未授权状态
            log.warn("Authorization头部丢失或为空");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            // 从Redis中获取缓存的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(TOKEN);
            if (!StringUtils.hasLength(redisToken)) {
                // 如果Redis中不存在该token，记录警告并返回401未授权状态
                log.warn("Redis中未找到对应的token: {}", token);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            // 解析并验证JWT token
            Map<String, Object> claims = JwtUtil.parseToken(token);
            if (claims == null) {
                // 如果解析失败或无效，记录警告并返回401未授权状态
                log.warn("无效的JWT token: {}", token);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            // 将claims存储到ThreadLocal中
            ThreadLocalUtil.set(claims);

            // 放行请求
            return true;
        } catch (Exception e) {
            // 如果发生异常，记录错误并返回401未授权状态
            log.error("验证token时发生错误", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除ThreadLocal中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
        if (ex != null) {
            // 如果请求处理过程中发生异常，记录错误
            log.error("请求处理过程中发生异常", ex);
        }
    }
}
