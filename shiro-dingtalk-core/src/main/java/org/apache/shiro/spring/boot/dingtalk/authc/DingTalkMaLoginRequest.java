package org.apache.shiro.spring.boot.dingtalk.authc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Login request payload for DingTalk Mini-Application (MA) authentication.
 *
 * <p>Used when a DingTalk mini-application performs a silent login (enterprise
 * internal application or third-party enterprise application). The client sends
 * the application key, a temporary authorization code and an optional binding
 * token; the server exchanges the code for an access token via the DingTalk API.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DingTalkMaLoginRequest {

	/**
	 * Unique application identifier key (appKey or appId).
	 */
	protected String key;
	/**
	 * Temporary authorization code obtained from the DingTalk runtime.
	 */
	protected String authCode;
	/**
	 * Current request token used for binding the authenticated user.
	 */
	protected String token;
	/**
	 * Access token obtained by exchanging the authorization code.
	 */
	protected String accessToken;

	@JsonIgnoreProperties(ignoreUnknown = true)
    @JsonCreator
    public DingTalkMaLoginRequest(@JsonProperty("key") String key,
								  @JsonProperty("token") String token,
								  @JsonProperty("authCode") String authCode) {
        this.key = key;
		this.token = token;
        this.authCode = authCode;
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

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
