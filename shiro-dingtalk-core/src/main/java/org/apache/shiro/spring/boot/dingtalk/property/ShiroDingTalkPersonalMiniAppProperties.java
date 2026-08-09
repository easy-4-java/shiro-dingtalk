package org.apache.shiro.spring.boot.dingtalk.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for DingTalk third-party personal mini-application.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@Getter
@Setter
@ToString
public class ShiroDingTalkPersonalMiniAppProperties {

	/**
	 * Application ID assigned to the personal application; used to obtain the user-authorized access token.
	 */
	private String appId;
	/**
	 * Application secret assigned to the personal application; used to obtain the user-authorized access token.
	 */
	private String appSecret;

}
