package org.apache.shiro.spring.boot.dingtalk.authc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.authc.AuthcResponse;
import org.apache.shiro.biz.utils.WebUtils2;
import org.apache.shiro.biz.web.filter.authc.AbstractTrustableAuthenticatingFilter;
import org.apache.shiro.biz.web.servlet.http.HttpStatus;
import org.apache.shiro.spring.boot.dingtalk.exception.DingTalkCodeNotFoundException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base authenticating filter for all DingTalk login flows.
 *
 * <p>Handles stateless session access, login request detection and JSON error
 * responses. Subclasses must implement {@link #createDingTalkToken(ServletRequest)}
 * to produce the appropriate DingTalk authentication token.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
abstract class AbstractDingTalkAuthenticatingFilter extends AbstractTrustableAuthenticatingFilter {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractDingTalkAuthenticatingFilter.class);

	private final ObjectMapper objectMapper;

	protected AbstractDingTalkAuthenticatingFilter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isSessionStateless()) {
			AuthenticationToken token = createToken(request, response);
			try {
				Subject subject = getSubject(request, response);
				subject.login(token);
				return onAccessSuccess(token, subject, request, response);
			} catch (AuthenticationException e) {
				return onAccessFailure(token, e, request, response);
			}
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				if (LOG.isTraceEnabled()) {
					LOG.trace("Login submission detected. Attempting to execute login.");
				}
				return executeLogin(request, response);
			}
			String message = "Authentication url [" + getLoginUrl() + "] Not Http Post request.";
			if (LOG.isTraceEnabled()) {
				LOG.trace(message);
			}
			writeFailure(response, HttpStatus.SC_BAD_REQUEST, message);
			return false;
		}
		String message = "Attempting to access a path which requires authentication.";
		if (LOG.isTraceEnabled()) {
			LOG.trace(message);
		}
		if (WebUtils2.isAjaxRequest(request)) {
			writeFailure(response, HttpStatus.SC_UNAUTHORIZED, message);
			return false;
		}
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		return createDingTalkToken(request);
	}

	protected abstract AuthenticationToken createDingTalkToken(ServletRequest request);

	protected <T> T readObjectRequest(ServletRequest request, Class<T> requestType) {
		if (!WebUtils2.isObjectRequest(request)) {
			return null;
		}
		try {
			return objectMapper.readValue(request.getReader(), requestType);
		} catch (IOException e) {
			throw new AuthenticationException(e);
		}
	}

	protected void requireText(String value, String message) {
		if (!StringUtils.hasText(value)) {
			throw new DingTalkCodeNotFoundException(message);
		}
	}

	private void writeFailure(ServletResponse response, int status, String message) throws IOException {
		WebUtils.toHttp(response).setStatus(HttpStatus.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		objectMapper.writeValue(response.getOutputStream(), AuthcResponse.fail(status, message));
	}

}
