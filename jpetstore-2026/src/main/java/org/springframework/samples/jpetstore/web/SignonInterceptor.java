package org.springframework.samples.jpetstore.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.util.WebUtils;

public class SignonInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (WebUtils.getSessionAttribute(request, ViewSupport.USER_SESSION) != null) {
			return true;
		}
		String query = request.getQueryString();
		String target = query != null ? request.getServletPath() + "?" + query : request.getServletPath();
		ModelAndView signonForm = new ModelAndView("SignonForm");
		signonForm.addObject("signonForwardAction", target);
		throw new ModelAndViewDefiningException(signonForm);
	}
}
