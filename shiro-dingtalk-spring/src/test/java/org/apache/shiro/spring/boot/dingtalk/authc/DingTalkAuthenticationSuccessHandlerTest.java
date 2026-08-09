package org.apache.shiro.spring.boot.dingtalk.authc;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkMaAuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkScanCodeAuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkTmpCodeAuthenticationToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit tests for {@link DingTalkAuthenticationSuccessHandler}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkAuthenticationSuccessHandlerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("supports DingTalkMaAuthenticationToken")
    void supportsMaToken() {
        DingTalkAuthenticationSuccessHandler handler =
                new DingTalkAuthenticationSuccessHandler(objectMapper, null, false);
        DingTalkMaAuthenticationToken token = new DingTalkMaAuthenticationToken(
                new DingTalkMaLoginRequest("k", "t", "c"), "host");
        assertThat(handler.supports(token)).isTrue();
    }

    @Test
    @DisplayName("supports DingTalkScanCodeAuthenticationToken")
    void supportsScanCodeToken() {
        DingTalkAuthenticationSuccessHandler handler =
                new DingTalkAuthenticationSuccessHandler(objectMapper, null, false);
        DingTalkScanCodeAuthenticationToken token = new DingTalkScanCodeAuthenticationToken(
                new DingTalkScanCodeLoginRequest("k", "t", "c"), "host");
        assertThat(handler.supports(token)).isTrue();
    }

    @Test
    @DisplayName("supports DingTalkTmpCodeAuthenticationToken")
    void supportsTmpCodeToken() {
        DingTalkAuthenticationSuccessHandler handler =
                new DingTalkAuthenticationSuccessHandler(objectMapper, null, false);
        DingTalkTmpCodeAuthenticationToken token = new DingTalkTmpCodeAuthenticationToken(
                new DingTalkTmpCodeLoginRequest("k", "t", "c"), "host");
        assertThat(handler.supports(token)).isTrue();
    }

    @Test
    @DisplayName("does not support unrelated token types")
    void doesNotSupportUnrelatedToken() {
        DingTalkAuthenticationSuccessHandler handler =
                new DingTalkAuthenticationSuccessHandler(objectMapper, null, false);
        AuthenticationToken token = new AuthenticationToken() {
            @Override
            public Object getPrincipal() { return "principal"; }
            @Override
            public Object getCredentials() { return "creds"; }
        };
        assertThat(handler.supports(token)).isFalse();
    }

    @Test
    @DisplayName("getOrder returns Integer.MAX_VALUE - 2")
    void getOrder() {
        DingTalkAuthenticationSuccessHandler handler =
                new DingTalkAuthenticationSuccessHandler(objectMapper, null, false);
        assertThat(handler.getOrder()).isEqualTo(Integer.MAX_VALUE - 2);
    }

    @Test
    @DisplayName("checkExpiry getter and setter")
    void checkExpiry() {
        DingTalkAuthenticationSuccessHandler handler =
                new DingTalkAuthenticationSuccessHandler(objectMapper, null, false);
        assertThat(handler.isCheckExpiry()).isFalse();
        handler.setCheckExpiry(true);
        assertThat(handler.isCheckExpiry()).isTrue();
    }

    @Test
    @DisplayName("jwtPayloadRepository getter returns null when not set")
    void jwtPayloadRepository() {
        DingTalkAuthenticationSuccessHandler handler =
                new DingTalkAuthenticationSuccessHandler(objectMapper, null, false);
        assertThat(handler.getJwtPayloadRepository()).isNull();
    }
}
