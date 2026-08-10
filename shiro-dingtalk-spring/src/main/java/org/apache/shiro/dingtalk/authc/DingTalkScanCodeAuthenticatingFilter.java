package org.apache.shiro.dingtalk.authc;

import org.apache.shiro.dingtalk.token.DingTalkScanCodeAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DingTalkScanCodeAuthenticatingFilter extends AbstractDingTalkAuthenticatingFilter {

	public static final String SPRING_SECURITY_FORM_APP_KEY = "key";
	public static final String SPRING_SECURITY_FORM_TOKEN_KEY = "token";
	public static final String SPRING_SECURITY_FORM_TMPCODE_KEY = "loginTmpCode";

	private String keyParameter = SPRING_SECURITY_FORM_APP_KEY;
	private String tokenParameter = SPRING_SECURITY_FORM_TOKEN_KEY;
	private String codeParameter = SPRING_SECURITY_FORM_TMPCODE_KEY;

	public DingTalkScanCodeAuthenticatingFilter(ObjectMapper objectMapper) {
		super(objectMapper);
	}

	@Override
	protected DingTalkScanCodeAuthenticationToken createDingTalkToken(javax.servlet.ServletRequest request) {
		DingTalkScanCodeLoginRequest loginRequest = readObjectRequest(request, DingTalkScanCodeLoginRequest.class);
		if (java.util.Objects.isNull(loginRequest)) {
			loginRequest = new DingTalkScanCodeLoginRequest(obtainKey(request), obtainToken(request), obtainCode(request));
		}
		requireText(loginRequest.getKey(), "No key (appId or appKey) found in request.");
		requireText(loginRequest.getLoginTmpCode(), "No loginTmpCode found in request.");
		return new DingTalkScanCodeAuthenticationToken(loginRequest, getHost(request));
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
