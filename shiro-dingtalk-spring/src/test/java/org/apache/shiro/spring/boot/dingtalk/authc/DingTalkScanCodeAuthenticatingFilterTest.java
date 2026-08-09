package org.apache.shiro.spring.boot.dingtalk.authc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkScanCodeAuthenticationToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit tests for {@link DingTalkScanCodeAuthenticatingFilter}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@ExtendWith(MockitoExtension.class)
class DingTalkScanCodeAuthenticatingFilterTest {

    @Mock
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("filter creates token from request parameters")
    void createsTokenFromParameters() {
        DingTalkScanCodeAuthenticatingFilter filter = new DingTalkScanCodeAuthenticatingFilter(objectMapper);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("key")).thenReturn("appKey1");
        when(request.getParameter("token")).thenReturn("token1");
        when(request.getParameter("loginTmpCode")).thenReturn("tmpCode1");

        AuthenticationToken token = filter.createDingTalkToken(request);
        assertThat(token).isInstanceOf(DingTalkScanCodeAuthenticationToken.class);
        DingTalkScanCodeAuthenticationToken scanToken = (DingTalkScanCodeAuthenticationToken) token;
        DingTalkScanCodeLoginRequest principal = (DingTalkScanCodeLoginRequest) scanToken.getPrincipal();
        assertThat(principal.getKey()).isEqualTo("appKey1");
        assertThat(principal.getToken()).isEqualTo("token1");
        assertThat(principal.getLoginTmpCode()).isEqualTo("tmpCode1");
    }

    @Test
    @DisplayName("filter throws when key is missing")
    void throwsWhenKeyMissing() {
        DingTalkScanCodeAuthenticatingFilter filter = new DingTalkScanCodeAuthenticatingFilter(objectMapper);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("key")).thenReturn(null);
        when(request.getParameter("token")).thenReturn("token1");
        when(request.getParameter("loginTmpCode")).thenReturn("tmpCode1");

        assertThatThrownBy(() -> filter.createDingTalkToken(request))
                .hasMessageContaining("No key");
    }

    @Test
    @DisplayName("filter throws when loginTmpCode is missing")
    void throwsWhenCodeMissing() {
        DingTalkScanCodeAuthenticatingFilter filter = new DingTalkScanCodeAuthenticatingFilter(objectMapper);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("key")).thenReturn("appKey1");
        when(request.getParameter("token")).thenReturn("token1");
        when(request.getParameter("loginTmpCode")).thenReturn(null);

        assertThatThrownBy(() -> filter.createDingTalkToken(request))
                .hasMessageContaining("No loginTmpCode");
    }

    @Test
    @DisplayName("constant values are correct")
    void constants() {
        assertThat(DingTalkScanCodeAuthenticatingFilter.SPRING_SECURITY_FORM_APP_KEY).isEqualTo("key");
        assertThat(DingTalkScanCodeAuthenticatingFilter.SPRING_SECURITY_FORM_TOKEN_KEY).isEqualTo("token");
        assertThat(DingTalkScanCodeAuthenticatingFilter.SPRING_SECURITY_FORM_TMPCODE_KEY).isEqualTo("loginTmpCode");
    }
}
