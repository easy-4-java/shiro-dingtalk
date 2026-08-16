package org.apache.shiro.spring.boot.dingtalk.authc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkMaAuthenticationToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tools.jackson.databind.ObjectMapper;

/**
 * Unit tests for {@link DingTalkMaAuthenticatingFilter}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@ExtendWith(MockitoExtension.class)
class DingTalkMaAuthenticatingFilterTest {

    @Mock
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("filter creates token from request parameters")
    void createsTokenFromParameters() {
        DingTalkMaAuthenticatingFilter filter = new DingTalkMaAuthenticatingFilter(objectMapper);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("key")).thenReturn("appKey1");
        when(request.getParameter("token")).thenReturn("token1");
        when(request.getParameter("authCode")).thenReturn("code1");

        AuthenticationToken token = filter.createDingTalkToken(request);
        assertThat(token).isInstanceOf(DingTalkMaAuthenticationToken.class);
        DingTalkMaAuthenticationToken maToken = (DingTalkMaAuthenticationToken) token;
        DingTalkMaLoginRequest principal = (DingTalkMaLoginRequest) maToken.getPrincipal();
        assertThat(principal.getKey()).isEqualTo("appKey1");
        assertThat(principal.getToken()).isEqualTo("token1");
        assertThat(principal.getAuthCode()).isEqualTo("code1");
    }

    @Test
    @DisplayName("filter throws when key is missing")
    void throwsWhenKeyMissing() {
        DingTalkMaAuthenticatingFilter filter = new DingTalkMaAuthenticatingFilter(objectMapper);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("key")).thenReturn(null);
        when(request.getParameter("token")).thenReturn("token1");
        when(request.getParameter("authCode")).thenReturn("code1");

        assertThatThrownBy(() -> filter.createDingTalkToken(request))
                .hasMessageContaining("No key");
    }

    @Test
    @DisplayName("filter throws when authCode is missing")
    void throwsWhenAuthCodeMissing() {
        DingTalkMaAuthenticatingFilter filter = new DingTalkMaAuthenticatingFilter(objectMapper);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("key")).thenReturn("appKey1");
        when(request.getParameter("token")).thenReturn("token1");
        when(request.getParameter("authCode")).thenReturn(null);

        assertThatThrownBy(() -> filter.createDingTalkToken(request))
                .hasMessageContaining("No AuthCode");
    }

    @Test
    @DisplayName("constant values are correct")
    void constants() {
        assertThat(DingTalkMaAuthenticatingFilter.SPRING_SECURITY_FORM_APP_KEY).isEqualTo("key");
        assertThat(DingTalkMaAuthenticatingFilter.SPRING_SECURITY_FORM_TOKEN_KEY).isEqualTo("token");
        assertThat(DingTalkMaAuthenticatingFilter.SPRING_SECURITY_FORM_CODE_KEY).isEqualTo("authCode");
    }
}
