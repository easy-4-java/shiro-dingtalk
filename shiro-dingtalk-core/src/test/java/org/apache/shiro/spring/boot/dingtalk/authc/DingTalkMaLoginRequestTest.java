package org.apache.shiro.spring.boot.dingtalk.authc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link DingTalkMaLoginRequest}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkMaLoginRequestTest {

    @Test
    @DisplayName("constructor sets key, token and authCode")
    void constructorShouldSetFields() {
        DingTalkMaLoginRequest request = new DingTalkMaLoginRequest("appKey1", "token1", "code1");
        assertThat(request.getKey()).isEqualTo("appKey1");
        assertThat(request.getToken()).isEqualTo("token1");
        assertThat(request.getAuthCode()).isEqualTo("code1");
    }

    @Test
    @DisplayName("setters and getters work correctly")
    void settersAndGetters() {
        DingTalkMaLoginRequest request = new DingTalkMaLoginRequest("k", "t", "c");
        request.setKey("newKey");
        request.setToken("newToken");
        request.setAuthCode("newCode");
        request.setAccessToken("accessToken123");
        assertThat(request.getKey()).isEqualTo("newKey");
        assertThat(request.getToken()).isEqualTo("newToken");
        assertThat(request.getAuthCode()).isEqualTo("newCode");
        assertThat(request.getAccessToken()).isEqualTo("accessToken123");
    }

    @Test
    @DisplayName("constructor with null fields")
    void constructorWithNullFields() {
        DingTalkMaLoginRequest request = new DingTalkMaLoginRequest(null, null, null);
        assertThat(request.getKey()).isNull();
        assertThat(request.getToken()).isNull();
        assertThat(request.getAuthCode()).isNull();
        assertThat(request.getAccessToken()).isNull();
    }
}
