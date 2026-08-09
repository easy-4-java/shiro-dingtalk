package org.apache.shiro.spring.boot.dingtalk;

import org.apache.shiro.biz.authz.principal.ShiroPrincipal;

import lombok.Getter;
import lombok.Setter;

/**
 * Shiro principal that holds DingTalk user profile attributes after successful authentication.
 *
 * <p>Extends {@link ShiroPrincipal} with DingTalk-specific fields such as unionid,
 * mobile, email, department, avatar, admin/boss flags and other employee metadata.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class ShiroDingTalkPrincipal extends ShiroPrincipal {

	private String unionid;
	private String name;
	private String tel;
	private String workPlace;
	private String remark;
	private String mobile;
	private String email;
	private String orgEmail;
	private String active;
	private String orderInDepts;
	private boolean admin;
	private boolean boss;
	private boolean leaderInDepts;
	private boolean hide;
	private String department;
	private String position;
	private String avatar;
	private String hiredDate;
	private String jobnumber;
	private String extattr;
	private boolean senior;
	private String stateCode;

}
