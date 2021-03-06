package com.petstore.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 后台登录验证拦截器
 */
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 检测登录状态
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if(uri.contains("css/") || uri.contains("js/") || uri.contains("img/") 
				|| uri.contains("login") || uri.contains("logout")) {
			return true; // 不拦截路径
		}
		Object username = request.getSession().getAttribute("adminName");
		if (Objects.nonNull(username) && !username.toString().trim().isEmpty()) {
			return true; // 登录验证通过
		}
		response.sendRedirect("login.jsp");
		return false; // 其他情况一律拦截
	}

}
