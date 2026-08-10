package org.apache.shiro.dingtalk.token;

import org.apache.shiro.biz.authc.token.DefaultAuthenticationToken;
import org.apache.shiro.dingtalk.authc.DingTalkMaLoginRequest;

@SuppressWarnings("serial")
public class DingTalkMaAuthenticationToken extends DefaultAuthenticationToken {

	private final DingTalkMaLoginRequest principal;

	public DingTalkMaAuthenticationToken(DingTalkMaLoginRequest loginRequest, String host) {
		this.principal = loginRequest;
		setHost(host);
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

}
