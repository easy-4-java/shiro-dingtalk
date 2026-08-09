package org.apache.shiro.spring.boot.dingtalk.token;

import org.apache.shiro.biz.authc.token.DefaultAuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkScanCodeLoginRequest;

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;

/**
 * Authentication token for DingTalk scan-code (QR code) login flow.
 *
 * <p>Wraps a {@link DingTalkScanCodeLoginRequest} as the principal and stores
 * the DingTalk user identifiers (unionid, openid) and profile information
 * returned after a successful scan-code authentication.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@SuppressWarnings("serial")
public class DingTalkScanCodeAuthenticationToken extends DefaultAuthenticationToken {

	private final DingTalkScanCodeLoginRequest principal;
	/** DingTalk union identifier. */
	private String unionid;
	/** DingTalk open identifier. */
	private String openid;
	/** DingTalk user profile information. */
	private OapiSnsGetuserinfoBycodeResponse.UserInfo userInfo;

	public DingTalkScanCodeAuthenticationToken(DingTalkScanCodeLoginRequest loginRequest, String host) {
		this.principal = loginRequest;
		setHost(host);
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public OapiSnsGetuserinfoBycodeResponse.UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(OapiSnsGetuserinfoBycodeResponse.UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
