package org.apache.shiro.spring.boot.dingtalk.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for DingTalk enterprise internal application (mini-app / H5).
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@Getter
@Setter
@ToString
public class ShiroDingTalkCropAppProperties {

	/**
	 * DingTalk agent ID for the enterprise internal application.
	 */
	private String agentId;
	/**
	 * Unique application identifier key (appKey) for the enterprise internal application.
	 */
	private String appKey;
	/**
	 * Application secret for the enterprise internal application.
	 */
	private String appSecret;

}
