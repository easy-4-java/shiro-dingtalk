package org.apache.shiro.spring.boot.dingtalk.authc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link DingTalkScanCodeLoginRequest}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkScanCodeLoginRequestTest {

    @Test
    @DisplayName("constructor sets key, token and loginTmpCode")
    void constructorShouldSetFields() {
        DingTalkScanCodeLoginRequest request = new DingTalkScanCodeLoginRequest("key1", "token1", "tmpCode1");
        assertThat(request.getKey()).isEqualTo("key1");
        assertThat(request.getToken()).isEqualTo("token1");
        assertThat(request.getLoginTmpCode()).isEqualTo("tmpCode1");
    }

    @Test
    @DisplayName("setters and getters work correctly")
    void settersAndGetters() {
        DingTalkScanCodeLoginRequest request = new DingTalkScanCodeLoginRequest("k", "t", "c");
        request.setKey("newKey");
        request.setToken("newToken");
        request.setLoginTmpCode("newTmpCode");
        assertThat(request.getKey()).isEqualTo("newKey");
        assertThat(request.getToken()).isEqualTo("newToken");
        assertThat(request.getLoginTmpCode()).isEqualTo("newTmpCode");
    }

    @Test
    @DisplayName("constructor with null fields")
    void constructorWithNullFields() {
        DingTalkScanCodeLoginRequest request = new DingTalkScanCodeLoginRequest(null, null, null);
        assertThat(request.getKey()).isNull();
        assertThat(request.getToken()).isNull();
        assertThat(request.getLoginTmpCode()).isNull();
    }
}
