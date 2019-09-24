package com.entor.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class SessionFilter implements Filter {

	public void destroy() {
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
//		String contextPath = req.getContextPath();
//		String uri = req.getRequestURI();
//		String url = req.getRequestURL().toString();
//		String servletPath = req.getServletPath();
		String queryString = req.getQueryString();
//		System.out.println("contextPath:"+contextPath);
//		System.out.println("uri:"+uri);
//		System.out.println("url:"+url);
//		System.out.println("servletPath:"+servletPath);
//		System.out.println("queryString:"+queryString);
		if(req.getSession().getAttribute("s")==null) {
			//登录页面可以允许通过
			if(queryString!=null&&(queryString.contains("login")||queryString.contains("add"))) {
				//请求地址通过
				chain.doFilter(req, resp);
			}else {
				//未登录，无权访问，重定向到登录页面
				resp.sendRedirect("StudentServlet?opType=loginForm");
			}
		}else {
			//请求地址通过
			chain.doFilter(req, resp);
		}
	}
	
	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
