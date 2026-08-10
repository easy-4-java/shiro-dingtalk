package org.apache.shiro.dingtalk.authc;

import org.apache.shiro.dingtalk.token.DingTalkMaAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DingTalkMaAuthenticatingFilter extends AbstractDingTalkAuthenticatingFilter {

	public static final String SPRING_SECURITY_FORM_APP_KEY = "key";
	public static final String SPRING_SECURITY_FORM_TOKEN_KEY = "token";
	public static final String SPRING_SECURITY_FORM_CODE_KEY = "authCode";

	private String keyParameter = SPRING_SECURITY_FORM_APP_KEY;
	private String tokenParameter = SPRING_SECURITY_FORM_TOKEN_KEY;
	private String authCodeParameter = SPRING_SECURITY_FORM_CODE_KEY;

	public DingTalkMaAuthenticatingFilter(ObjectMapper objectMapper) {
		super(objectMapper);
	}

	@Override
	protected DingTalkMaAuthenticationToken createDingTalkToken(javax.servlet.ServletRequest request) {
		DingTalkMaLoginRequest loginRequest = readObjectRequest(request, DingTalkMaLoginRequest.class);
		if (java.util.Objects.isNull(loginRequest)) {
			loginRequest = new DingTalkMaLoginRequest(obtainKey(request), obtainToken(request), obtainAuthCode(request));
		}
		requireText(loginRequest.getKey(), "No key (appId or appKey) found in request.");
		requireText(loginRequest.getAuthCode(), "No AuthCode found in request.");
		return new DingTalkMaAuthenticationToken(loginRequest, getHost(request));
	}

	protected String obtainKey(javax.servlet.ServletRequest request) {
		return request.getParameter(keyParameter);
	}

	protected String obtainToken(javax.servlet.ServletRequest request) {
		return request.getParameter(tokenParameter);
	}

	protected String obtainAuthCode(javax.servlet.ServletRequest request) {
		return request.getParameter(authCodeParameter);
	}

}
