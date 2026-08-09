package org.apache.shiro.spring.boot.dingtalk.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.authc.AuthenticationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for DingTalk exception classes.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkExceptionTest {

    @Test
    @DisplayName("DingTalkAuthenticationServiceException with message")
    void authenticationServiceExceptionWithMessage() {
        DingTalkAuthenticationServiceException ex = new DingTalkAuthenticationServiceException("service error");
        assertThat(ex).isInstanceOf(AuthenticationException.class);
        assertThat(ex.getMessage()).isEqualTo("service error");
    }

    @Test
    @DisplayName("DingTalkAuthenticationServiceException with message and cause")
    void authenticationServiceExceptionWithCause() {
        Throwable cause = new RuntimeException("root cause");
        DingTalkAuthenticationServiceException ex = new DingTalkAuthenticationServiceException("error", cause);
        assertThat(ex.getCause()).isSameAs(cause);
        assertThat(ex.getMessage()).isEqualTo("error");
    }

    @Test
    @DisplayName("DingTalkCodeExpiredException with message")
    void codeExpiredExceptionWithMessage() {
        DingTalkCodeExpiredException ex = new DingTalkCodeExpiredException("code expired");
        assertThat(ex).isInstanceOf(AuthenticationException.class);
        assertThat(ex.getMessage()).isEqualTo("code expired");
    }

    @Test
    @DisplayName("DingTalkCodeExpiredException with message and cause")
    void codeExpiredExceptionWithCause() {
        Throwable cause = new RuntimeException("timeout");
        DingTalkCodeExpiredException ex = new DingTalkCodeExpiredException("expired", cause);
        assertThat(ex.getCause()).isSameAs(cause);
    }

    @Test
    @DisplayName("DingTalkCodeIncorrectException with message")
    void codeIncorrectExceptionWithMessage() {
        DingTalkCodeIncorrectException ex = new DingTalkCodeIncorrectException("incorrect code");
        assertThat(ex).isInstanceOf(AuthenticationException.class);
        assertThat(ex.getMessage()).isEqualTo("incorrect code");
    }

    @Test
    @DisplayName("DingTalkCodeIncorrectException with message and cause")
    void codeIncorrectExceptionWithCause() {
        Throwable cause = new RuntimeException("bad code");
        DingTalkCodeIncorrectException ex = new DingTalkCodeIncorrectException("incorrect", cause);
        assertThat(ex.getCause()).isSameAs(cause);
    }

    @Test
    @DisplayName("DingTalkCodeNotFoundException default constructor")
    void codeNotFoundExceptionDefault() {
        DingTalkCodeNotFoundException ex = new DingTalkCodeNotFoundException();
        assertThat(ex).isInstanceOf(AuthenticationException.class);
        assertThat(ex.getMessage()).isNull();
    }

    @Test
    @DisplayName("DingTalkCodeNotFoundException with message")
    void codeNotFoundExceptionWithMessage() {
        DingTalkCodeNotFoundException ex = new DingTalkCodeNotFoundException("not found");
        assertThat(ex.getMessage()).isEqualTo("not found");
    }

    @Test
    @DisplayName("DingTalkCodeNotFoundException with message and cause")
    void codeNotFoundExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("cause");
        DingTalkCodeNotFoundException ex = new DingTalkCodeNotFoundException("not found", cause);
        assertThat(ex.getCause()).isSameAs(cause);
    }

    @Test
    @DisplayName("DingTalkCodeNotFoundException with cause only")
    void codeNotFoundExceptionWithCause() {
        Throwable cause = new RuntimeException("cause");
        DingTalkCodeNotFoundException ex = new DingTalkCodeNotFoundException(cause);
        assertThat(ex.getCause()).isSameAs(cause);
    }
}
