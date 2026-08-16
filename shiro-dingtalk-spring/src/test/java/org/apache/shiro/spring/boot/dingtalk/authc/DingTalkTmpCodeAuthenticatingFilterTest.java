package org.apache.shiro.spring.boot.dingtalk.authc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkTmpCodeAuthenticationToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tools.jackson.databind.ObjectMapper;

/**
 * Unit tests for {@link DingTalkTmpCodeAuthenticatingFilter}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@ExtendWith(MockitoExtension.class)
class DingTalkTmpCodeAuthenticatingFilterTest {

    @Mock
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("filter creates token from request parameters")
    void createsTokenFromParameters() {
        DingTalkTmpCodeAuthenticatingFilter filter = new DingTalkTmpCodeAuthenticatingFilter(objectMapper);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("key")).thenReturn("appKey1");
        when(request.getParameter("token")).thenReturn("token1");
        when(request.getParameter("code")).thenReturn("code1");

        AuthenticationToken token = filter.createDingTalkToken(request);
        assertThat(token).isInstanceOf(DingTalkTmpCodeAuthenticationToken.class);
        DingTalkTmpCodeAuthenticationToken tmpToken = (DingTalkTmpCodeAuthenticationToken) token;
        DingTalkTmpCodeLoginRequest principal = (DingTalkTmpCodeLoginRequest) tmpToken.getPrincipal();
        assertThat(principal.getKey()).isEqualTo("appKey1");
        assertThat(principal.getToken()).isEqualTo("token1");
        assertThat(principal.getCode()).isEqualTo("code1");
    }

    @Test
    @DisplayName("filter throws when key is missing")
    void throwsWhenKeyMissing() {
        DingTalkTmpCodeAuthenticatingFilter filter = new DingTalkTmpCodeAuthenticatingFilter(objectMapper);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("key")).thenReturn(null);
        when(request.getParameter("token")).thenReturn("token1");
        when(request.getParameter("code")).thenReturn("code1");

        assertThatThrownBy(() -> filter.createDingTalkToken(request))
                .hasMessageContaining("No key");
    }

    @Test
    @DisplayName("filter throws when code is missing")
    void throwsWhenCodeMissing() {
        DingTalkTmpCodeAuthenticatingFilter filter = new DingTalkTmpCodeAuthenticatingFilter(objectMapper);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("key")).thenReturn("appKey1");
        when(request.getParameter("token")).thenReturn("token1");
        when(request.getParameter("code")).thenReturn(null);

        assertThatThrownBy(() -> filter.createDingTalkToken(request))
                .hasMessageContaining("No Code");
    }

    @Test
    @DisplayName("constant values are correct")
    void constants() {
        assertThat(DingTalkTmpCodeAuthenticatingFilter.SPRING_SECURITY_FORM_APP_KEY).isEqualTo("key");
        assertThat(DingTalkTmpCodeAuthenticatingFilter.SPRING_SECURITY_FORM_TOKEN_KEY).isEqualTo("token");
        assertThat(DingTalkTmpCodeAuthenticatingFilter.SPRING_SECURITY_FORM_CODE_KEY).isEqualTo("code");
    }
}
