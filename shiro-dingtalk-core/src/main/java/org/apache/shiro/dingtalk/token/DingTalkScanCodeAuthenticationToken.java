package org.apache.shiro.dingtalk.token;

import org.apache.shiro.biz.authc.token.DefaultAuthenticationToken;
import org.apache.shiro.dingtalk.authc.DingTalkScanCodeLoginRequest;

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;

@SuppressWarnings("serial")
public class DingTalkScanCodeAuthenticationToken extends DefaultAuthenticationToken {

	private final DingTalkScanCodeLoginRequest principal;
	private String unionid;
	private String openid;
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
