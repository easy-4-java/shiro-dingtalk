package org.apache.shiro.spring.boot.dingtalk.authc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Login request payload for DingTalk scan-code (QR code) authentication.
 *
 * <p>Used in third-party system integrations where the user scans a DingTalk
 * QR code to authenticate. The client provides the application key, a binding
 * token and a temporary login code returned by the DingTalk scan callback.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DingTalkScanCodeLoginRequest {

	/**
	 * Unique application identifier key (appKey or appId).
	 */
	protected String key;
	/**
	 * Current request token used for binding the authenticated user.
	 */
	protected String token;
	/**
	 * Temporary login code returned by the DingTalk scan-code callback.
	 */
	protected String loginTmpCode;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonCreator
	public DingTalkScanCodeLoginRequest(@JsonProperty("key") String key,
										@JsonProperty("token") String token,
									    @JsonProperty("loginTmpCode") String loginTmpCode) {
		this.key = key;
		this.token = token;
		this.loginTmpCode = loginTmpCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLoginTmpCode() {
		return loginTmpCode;
	}

	public void setLoginTmpCode(String loginTmpCode) {
		this.loginTmpCode = loginTmpCode;
	}

}
