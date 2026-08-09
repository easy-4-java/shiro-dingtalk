package org.apache.shiro.spring.boot.dingtalk.authc;

import org.apache.shiro.spring.boot.dingtalk.token.DingTalkScanCodeAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Authenticating filter for DingTalk scan-code (QR code) login requests.
 *
 * <p>Extracts the application key, binding token and temporary login code from the
 * HTTP request (either as JSON body or form parameters) and creates a
 * {@link DingTalkScanCodeAuthenticationToken} for the Shiro authentication chain.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
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
