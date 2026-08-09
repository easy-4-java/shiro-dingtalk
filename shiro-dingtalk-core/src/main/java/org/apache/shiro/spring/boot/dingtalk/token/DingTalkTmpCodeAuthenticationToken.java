package org.apache.shiro.spring.boot.dingtalk.token;

import org.apache.shiro.biz.authc.token.DefaultAuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkTmpCodeLoginRequest;

/**
 * Authentication token for DingTalk temporary-code (free-login) flow.
 *
 * <p>Wraps a {@link DingTalkTmpCodeLoginRequest} as the principal and carries
 * the host information for the authentication attempt.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
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
