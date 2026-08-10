package org.apache.shiro.dingtalk.authc;

import org.apache.shiro.dingtalk.token.DingTalkTmpCodeAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DingTalkTmpCodeAuthenticatingFilter extends AbstractDingTalkAuthenticatingFilter {

	public static final String SPRING_SECURITY_FORM_APP_KEY = "key";
	public static final String SPRING_SECURITY_FORM_TOKEN_KEY = "token";
	public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";

	private String keyParameter = SPRING_SECURITY_FORM_APP_KEY;
	private String tokenParameter = SPRING_SECURITY_FORM_TOKEN_KEY;
	private String codeParameter = SPRING_SECURITY_FORM_CODE_KEY;

	public DingTalkTmpCodeAuthenticatingFilter(ObjectMapper objectMapper) {
		super(objectMapper);
	}

	@Override
	protected DingTalkTmpCodeAuthenticationToken createDingTalkToken(javax.servlet.ServletRequest request) {
		DingTalkTmpCodeLoginRequest loginRequest = readObjectRequest(request, DingTalkTmpCodeLoginRequest.class);
		if (java.util.Objects.isNull(loginRequest)) {
			loginRequest = new DingTalkTmpCodeLoginRequest(obtainKey(request), obtainToken(request), obtainCode(request));
		}
		requireText(loginRequest.getKey(), "No key (appId or appKey) found in request.");
		requireText(loginRequest.getCode(), "No Code found in request.");
		return new DingTalkTmpCodeAuthenticationToken(loginRequest, getHost(request));
	}

	protected String obtainKey(javax.servlet.ServletRequest request) {
		return request.getParameter(keyParameter);
	}

	protected String obtainToken(javax.servlet.ServletRequest request) {
		return request.getParameter(tokenParameter);
	}

	protected String obtainCode(javax.servlet.ServletRequest request) {
		return request.getParameter(codeParameter);
	}

}
