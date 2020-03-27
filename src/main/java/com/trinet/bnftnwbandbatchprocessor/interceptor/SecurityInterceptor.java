/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.interceptor;

/**
 * @author imistry
 *
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.trinet.common.CommonConstants;

public class SecurityInterceptor implements HandlerInterceptor {

	private static final String MICROSERVICE = "contextPath";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Retrieve Request ID.
		String requestId = request.getHeader("X-Request-ID");

		if (StringUtils.isEmpty(requestId))
			requestId = java.util.UUID.randomUUID().toString();

		MDC.put(CommonConstants.REQUEST_ID, requestId);
		String contextPath = request.getContextPath();
		MDC.put(MICROSERVICE, contextPath);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		MDC.remove(CommonConstants.REQUEST_ID);
		MDC.remove(MICROSERVICE);
	}

}
