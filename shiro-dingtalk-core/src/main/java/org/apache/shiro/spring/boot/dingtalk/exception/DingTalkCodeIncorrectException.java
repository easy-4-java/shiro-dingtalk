package org.apache.shiro.spring.boot.dingtalk.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Exception thrown when the DingTalk authorization code is incorrect.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@SuppressWarnings("serial")
public class DingTalkCodeIncorrectException extends AuthenticationException {

	public DingTalkCodeIncorrectException(String msg) {
		super(msg);
	}
	
	public DingTalkCodeIncorrectException(String msg, Throwable t) {
		super(msg, t);
	}
	
}
