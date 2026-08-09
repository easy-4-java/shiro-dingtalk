package org.apache.shiro.spring.boot.dingtalk.authc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.authc.AuthenticationSuccessHandler;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.apache.shiro.biz.web.servlet.http.HttpStatus;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkMaAuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkScanCodeAuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkTmpCodeAuthenticationToken;
import org.apache.shiro.spring.boot.jwt.JwtPayloadRepository;
import org.apache.shiro.spring.boot.utils.SubjectJwtUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Authentication success handler for DingTalk login flows.
 *
 * <p>After a successful DingTalk authentication, this handler issues a JWT token
 * (if a {@link JwtPayloadRepository} is configured) and writes the token map as
 * a JSON response to the client.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
public class DingTalkAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger LOG = LoggerFactory.getLogger(DingTalkAuthenticationSuccessHandler.class);

	private final ObjectMapper objectMapper;
	private final JwtPayloadRepository jwtPayloadRepository;
	private boolean checkExpiry = false;

	public DingTalkAuthenticationSuccessHandler(ObjectMapper objectMapper, JwtPayloadRepository jwtPayloadRepository,
			boolean checkExpiry) {
		this.objectMapper = objectMapper;
		this.jwtPayloadRepository = jwtPayloadRepository;
		this.checkExpiry = checkExpiry;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return SubjectUtils.isAssignableFrom(token.getClass(), DingTalkMaAuthenticationToken.class,
				DingTalkScanCodeAuthenticationToken.class, DingTalkTmpCodeAuthenticationToken.class);
	}

	@Override
	public void onAuthenticationSuccess(AuthenticationToken token, ServletRequest request, ServletResponse response,
			Subject subject) {
		try {
			String tokenString = "";
			if (Objects.nonNull(subject.getPrincipal())
					&& ShiroPrincipal.class.isAssignableFrom(subject.getPrincipal().getClass())
					&& Objects.nonNull(getJwtPayloadRepository())) {
				tokenString = getJwtPayloadRepository().issueJwt(token, subject);
			}
			Map<String, Object> tokenMap = SubjectJwtUtils.tokenMap(subject, tokenString);
			WebUtils.toHttp(response).setStatus(HttpStatus.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			objectMapper.writeValue(response.getWriter(), tokenMap);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE - 2;
	}

	public JwtPayloadRepository getJwtPayloadRepository() {
		return jwtPayloadRepository;
	}

	public boolean isCheckExpiry() {
		return checkExpiry;
	}

	public void setCheckExpiry(boolean checkExpiry) {
		this.checkExpiry = checkExpiry;
	}

}
