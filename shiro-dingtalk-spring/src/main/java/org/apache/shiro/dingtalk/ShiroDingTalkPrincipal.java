package org.apache.shiro.dingtalk;

import org.apache.shiro.biz.authz.principal.ShiroPrincipal;

import lombok.Getter;
import lombok.Setter;

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
