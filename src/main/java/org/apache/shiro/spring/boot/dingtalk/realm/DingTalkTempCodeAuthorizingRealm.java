package org.apache.shiro.spring.boot.dingtalk.realm;

import java.util.Objects;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.realm.AbstractAuthorizingRealm;
import org.apache.shiro.biz.realm.AuthorizingRealmListener;
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkTmpCodeLoginRequest;
import org.apache.shiro.spring.boot.dingtalk.exception.DingTalkAuthenticationServiceException;
import org.apache.shiro.spring.boot.dingtalk.exception.DingTalkCodeNotFoundException;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkTmpCodeAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.dingtalk.spring.boot.DingTalkTemplate;
import com.taobao.api.ApiException;

public class DingTalkTempCodeAuthorizingRealm extends AbstractAuthorizingRealm {

	private static final Logger LOG = LoggerFactory.getLogger(DingTalkTempCodeAuthorizingRealm.class);

	private final DingTalkTemplate dingTalkTemplate;

	public DingTalkTempCodeAuthorizingRealm(DingTalkTemplate dingTalkTemplate) {
		this.dingTalkTemplate = dingTalkTemplate;
	}

	@Override
	public Class<?> getAuthenticationTokenClass() {
		return DingTalkTmpCodeAuthenticationToken.class;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		LOG.info("Handle authentication token {}.", token);
		AuthenticationException ex = null;
		AuthenticationInfo info = null;
		try {
			DingTalkTmpCodeAuthenticationToken dingTalkToken = (DingTalkTmpCodeAuthenticationToken) token;
			DingTalkTmpCodeLoginRequest loginRequest = (DingTalkTmpCodeLoginRequest) dingTalkToken.getPrincipal();
			validateAppKey(loginRequest.getKey());
			if (!StringUtils.hasText(loginRequest.getCode())) {
				throw new DingTalkCodeNotFoundException("No Code found in request.");
			}
			String corpId = dingTalkTemplate.getCorpId(loginRequest.getKey());
			loginRequest.setAccessToken(dingTalkTemplate.getAccessToken(corpId, loginRequest.getKey()));
			info = getRepository().getAuthenticationInfo(dingTalkToken);
		} catch (AuthenticationException e) {
			ex = e;
		} catch (ApiException e) {
			ex = new DingTalkAuthenticationServiceException(e.getErrMsg(), e);
		}
		notifyListeners(token, ex, info);
		if (Objects.nonNull(ex)) {
			throw ex;
		}
		return info;
	}

	private void validateAppKey(String key) {
		if (!StringUtils.hasText(key) || !dingTalkTemplate.hasAppKey(key)) {
			throw new DingTalkCodeNotFoundException("Invalid App Key.");
		}
	}

	private void notifyListeners(AuthenticationToken token, AuthenticationException ex, AuthenticationInfo info) {
		if (Objects.nonNull(getRealmsListeners()) && !getRealmsListeners().isEmpty()) {
			for (AuthorizingRealmListener realmListener : getRealmsListeners()) {
				if (Objects.nonNull(ex) || Objects.isNull(info)) {
					realmListener.onFailure(this, token, ex);
				} else {
					realmListener.onSuccess(this, info);
				}
			}
		}
	}

}
