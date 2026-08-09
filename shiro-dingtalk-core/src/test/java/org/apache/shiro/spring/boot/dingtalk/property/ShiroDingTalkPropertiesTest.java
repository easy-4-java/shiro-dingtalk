package org.apache.shiro.spring.boot.dingtalk.property;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for DingTalk configuration property classes.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class ShiroDingTalkPropertiesTest {

    @Test
    @DisplayName("ShiroDingTalkCropAppProperties setters and getters")
    void cropAppProperties() {
        ShiroDingTalkCropAppProperties props = new ShiroDingTalkCropAppProperties();
        props.setAgentId("agent1");
        props.setAppKey("key1");
        props.setAppSecret("secret1");
        assertThat(props.getAgentId()).isEqualTo("agent1");
        assertThat(props.getAppKey()).isEqualTo("key1");
        assertThat(props.getAppSecret()).isEqualTo("secret1");
        assertThat(props.toString()).contains("agent1", "key1");
    }

    @Test
    @DisplayName("ShiroDingTalkLoginProperties setters and getters")
    void loginProperties() {
        ShiroDingTalkLoginProperties props = new ShiroDingTalkLoginProperties();
        props.setAppId("appId1");
        props.setAppSecret("secret1");
        assertThat(props.getAppId()).isEqualTo("appId1");
        assertThat(props.getAppSecret()).isEqualTo("secret1");
        assertThat(props.toString()).contains("appId1", "secret1");
    }

    @Test
    @DisplayName("ShiroDingTalkPersonalMiniAppProperties setters and getters")
    void personalMiniAppProperties() {
        ShiroDingTalkPersonalMiniAppProperties props = new ShiroDingTalkPersonalMiniAppProperties();
        props.setAppId("appId2");
        props.setAppSecret("secret2");
        assertThat(props.getAppId()).isEqualTo("appId2");
        assertThat(props.getAppSecret()).isEqualTo("secret2");
        assertThat(props.toString()).contains("appId2", "secret2");
    }

    @Test
    @DisplayName("ShiroDingTalkSuiteProperties setters and getters")
    void suiteProperties() {
        ShiroDingTalkSuiteProperties props = new ShiroDingTalkSuiteProperties();
        props.setSuiteId("suite1");
        props.setAppId("app1");
        props.setSuiteKey("key1");
        props.setSuiteSecret("secret1");
        assertThat(props.getSuiteId()).isEqualTo("suite1");
        assertThat(props.getAppId()).isEqualTo("app1");
        assertThat(props.getSuiteKey()).isEqualTo("key1");
        assertThat(props.getSuiteSecret()).isEqualTo("secret1");
        assertThat(props.toString()).contains("suite1", "app1", "key1");
    }
}
