package org.apache.shiro.spring.boot.dingtalk.token;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkMaLoginRequest;
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkScanCodeLoginRequest;
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkTmpCodeLoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for DingTalk authentication token classes.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkAuthenticationTokenTest {

    @Test
    @DisplayName("DingTalkMaAuthenticationToken stores principal and host")
    void maToken() {
        DingTalkMaLoginRequest request = new DingTalkMaLoginRequest("key1", "token1", "code1");
        DingTalkMaAuthenticationToken token = new DingTalkMaAuthenticationToken(request, "localhost");
        assertThat(token.getPrincipal()).isSameAs(request);
        assertThat(token.getHost()).isEqualTo("localhost");
    }

    @Test
    @DisplayName("DingTalkScanCodeAuthenticationToken stores principal, host and user info")
    void scanCodeToken() {
        DingTalkScanCodeLoginRequest request = new DingTalkScanCodeLoginRequest("key1", "token1", "tmpCode1");
        DingTalkScanCodeAuthenticationToken token = new DingTalkScanCodeAuthenticationToken(request, "127.0.0.1");
        assertThat(token.getPrincipal()).isSameAs(request);
        assertThat(token.getHost()).isEqualTo("127.0.0.1");

        token.setUnionid("union1");
        token.setOpenid("open1");
        assertThat(token.getUnionid()).isEqualTo("union1");
        assertThat(token.getOpenid()).isEqualTo("open1");
        assertThat(token.getUserInfo()).isNull();
    }

    @Test
    @DisplayName("DingTalkTmpCodeAuthenticationToken stores principal and host")
    void tmpCodeToken() {
        DingTalkTmpCodeLoginRequest request = new DingTalkTmpCodeLoginRequest("key1", "token1", "code1");
        DingTalkTmpCodeAuthenticationToken token = new DingTalkTmpCodeAuthenticationToken(request, "host1");
        assertThat(token.getPrincipal()).isSameAs(request);
        assertThat(token.getHost()).isEqualTo("host1");
    }
}
