package org.apache.shiro.dingtalk.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 	移动接入应用：扫码登录配置
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@Getter
@Setter
@ToString
public class ShiroDingTalkLoginProperties {

	/**
	 * 	移动接入应用-扫码登录应用的appId
	 */
	private String appId;
	/**
	 * 	移动接入应用-扫码登录应用的appSecret
	 */
	private String appSecret;

}
