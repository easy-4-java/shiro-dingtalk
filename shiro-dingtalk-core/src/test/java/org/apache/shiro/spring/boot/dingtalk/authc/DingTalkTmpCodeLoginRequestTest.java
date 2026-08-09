package org.apache.shiro.spring.boot.dingtalk.authc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link DingTalkTmpCodeLoginRequest}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkTmpCodeLoginRequestTest {

    @Test
    @DisplayName("constructor sets key, token and code")
    void constructorShouldSetFields() {
        DingTalkTmpCodeLoginRequest request = new DingTalkTmpCodeLoginRequest("key1", "token1", "code1");
        assertThat(request.getKey()).isEqualTo("key1");
        assertThat(request.getToken()).isEqualTo("token1");
        assertThat(request.getCode()).isEqualTo("code1");
    }

    @Test
    @DisplayName("setters and getters work correctly")
    void settersAndGetters() {
        DingTalkTmpCodeLoginRequest request = new DingTalkTmpCodeLoginRequest("k", "t", "c");
        request.setKey("newKey");
        request.setToken("newToken");
        request.setCode("newCode");
        request.setAccessToken("accessToken123");
        assertThat(request.getKey()).isEqualTo("newKey");
        assertThat(request.getToken()).isEqualTo("newToken");
        assertThat(request.getCode()).isEqualTo("newCode");
        assertThat(request.getAccessToken()).isEqualTo("accessToken123");
    }

    @Test
    @DisplayName("constructor with null fields")
    void constructorWithNullFields() {
        DingTalkTmpCodeLoginRequest request = new DingTalkTmpCodeLoginRequest(null, null, null);
        assertThat(request.getKey()).isNull();
        assertThat(request.getToken()).isNull();
        assertThat(request.getCode()).isNull();
        assertThat(request.getAccessToken()).isNull();
    }
}
