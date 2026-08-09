package org.apache.shiro.spring.boot.dingtalk.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for DingTalk third-party enterprise application (mini-app / H5).
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@Getter
@Setter
@ToString
public class ShiroDingTalkSuiteProperties {

	/**
	 * Suite ID for the third-party enterprise application.
	 */
	private String suiteId;
	/**
	 * Application ID for the third-party enterprise application.
	 */
	private String appId;
	/**
	 * Suite key (unique identifier) for the third-party enterprise application.
	 */
	private String suiteKey;
	/**
	 * Suite secret for the third-party enterprise application.
	 */
	private String suiteSecret;

}
