package org.apache.shiro.spring.boot.dingtalk.token;

import org.apache.shiro.biz.authc.token.DefaultAuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkMaLoginRequest;

/**
 * Authentication token for DingTalk Mini-Application (MA) login flow.
 *
 * <p>Wraps a {@link DingTalkMaLoginRequest} as the principal and carries the
 * host information for the authentication attempt.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
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
