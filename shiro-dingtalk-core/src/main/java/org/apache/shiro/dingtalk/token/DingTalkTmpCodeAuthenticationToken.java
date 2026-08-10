package org.apache.shiro.dingtalk.token;

import org.apache.shiro.biz.authc.token.DefaultAuthenticationToken;
import org.apache.shiro.dingtalk.authc.DingTalkTmpCodeLoginRequest;

@SuppressWarnings("serial")
public class DingTalkTmpCodeAuthenticationToken extends DefaultAuthenticationToken {

	private final DingTalkTmpCodeLoginRequest principal;

	public DingTalkTmpCodeAuthenticationToken(DingTalkTmpCodeLoginRequest loginRequest, String host) {
		this.principal = loginRequest;
		setHost(host);
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

}
