package org.apache.shiro.spring.boot.dingtalk.authc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Login request payload for DingTalk temporary-code (free-login) authentication.
 *
 * <p>Supports three scenarios: enterprise internal application free-login,
 * third-party enterprise application free-login and application management
 * console free-login. The client sends the application key, a temporary code
 * and an optional binding token.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DingTalkTmpCodeLoginRequest {

	/**
	 * Unique application identifier key (appKey or appId).
	 */
	protected String key;
	/**
	 * Temporary authorization code for free-login scenarios.
	 */
	protected String code;
	/**
	 * Current request token used for binding the authenticated user.
	 */
	protected String token;
	/**
	 * Access token obtained by exchanging the temporary code.
	 */
	protected String accessToken;

	@JsonIgnoreProperties(ignoreUnknown = true)
    @JsonCreator
    public DingTalkTmpCodeLoginRequest(@JsonProperty("key") String key,
									   @JsonProperty("token") String token,
									   @JsonProperty("code") String code) {
        this.key = key;
		this.token = token;
		this.code = code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
