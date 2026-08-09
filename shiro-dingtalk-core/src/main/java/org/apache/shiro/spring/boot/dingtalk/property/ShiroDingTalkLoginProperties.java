package org.apache.shiro.spring.boot.dingtalk.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for DingTalk mobile access application scan-code login.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@Getter
@Setter
@ToString
public class ShiroDingTalkLoginProperties {

	/**
	 * Application ID for the mobile-access scan-code login.
	 */
	private String appId;
	/**
	 * Application secret for the mobile-access scan-code login.
	 */
	private String appSecret;

}
