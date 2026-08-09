package org.apache.shiro.spring.boot.dingtalk;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ShiroDingTalkPrincipal}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class ShiroDingTalkPrincipalTest {

    @Test
    @DisplayName("all DingTalk user profile fields are accessible via getters")
    void allFieldsAccessible() {
        ShiroDingTalkPrincipal principal = new ShiroDingTalkPrincipal();
        principal.setUnionid("union1");
        principal.setName("John Doe");
        principal.setTel("1234567890");
        principal.setWorkPlace("Beijing");
        principal.setRemark("test remark");
        principal.setMobile("13800138000");
        principal.setEmail("john@example.com");
        principal.setOrgEmail("john@corp.com");
        principal.setActive("1");
        principal.setOrderInDepts("100");
        principal.setAdmin(true);
        principal.setBoss(false);
        principal.setLeaderInDepts(true);
        principal.setHide(false);
        principal.setDepartment("Engineering");
        principal.setPosition("Developer");
        principal.setAvatar("https://avatar.url");
        principal.setHiredDate("2020-01-01");
        principal.setJobnumber("EMP001");
        principal.setExtattr("{\"key\":\"value\"}");
        principal.setSenior(true);
        principal.setStateCode("86");

        assertThat(principal.getUnionid()).isEqualTo("union1");
        assertThat(principal.getName()).isEqualTo("John Doe");
        assertThat(principal.getTel()).isEqualTo("1234567890");
        assertThat(principal.getWorkPlace()).isEqualTo("Beijing");
        assertThat(principal.getRemark()).isEqualTo("test remark");
        assertThat(principal.getMobile()).isEqualTo("13800138000");
        assertThat(principal.getEmail()).isEqualTo("john@example.com");
        assertThat(principal.getOrgEmail()).isEqualTo("john@corp.com");
        assertThat(principal.getActive()).isEqualTo("1");
        assertThat(principal.getOrderInDepts()).isEqualTo("100");
        assertThat(principal.isAdmin()).isTrue();
        assertThat(principal.isBoss()).isFalse();
        assertThat(principal.isLeaderInDepts()).isTrue();
        assertThat(principal.isHide()).isFalse();
        assertThat(principal.getDepartment()).isEqualTo("Engineering");
        assertThat(principal.getPosition()).isEqualTo("Developer");
        assertThat(principal.getAvatar()).isEqualTo("https://avatar.url");
        assertThat(principal.getHiredDate()).isEqualTo("2020-01-01");
        assertThat(principal.getJobnumber()).isEqualTo("EMP001");
        assertThat(principal.getExtattr()).isEqualTo("{\"key\":\"value\"}");
        assertThat(principal.isSenior()).isTrue();
        assertThat(principal.getStateCode()).isEqualTo("86");
    }

    @Test
    @DisplayName("default values are null or false")
    void defaultValues() {
        ShiroDingTalkPrincipal principal = new ShiroDingTalkPrincipal();
        assertThat(principal.getUnionid()).isNull();
        assertThat(principal.getName()).isNull();
        assertThat(principal.getTel()).isNull();
        assertThat(principal.getMobile()).isNull();
        assertThat(principal.getEmail()).isNull();
        assertThat(principal.isAdmin()).isFalse();
        assertThat(principal.isBoss()).isFalse();
        assertThat(principal.isLeaderInDepts()).isFalse();
        assertThat(principal.isHide()).isFalse();
        assertThat(principal.isSenior()).isFalse();
    }
}
