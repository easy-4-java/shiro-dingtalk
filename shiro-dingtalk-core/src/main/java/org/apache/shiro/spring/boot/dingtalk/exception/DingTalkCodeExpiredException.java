package org.apache.shiro.spring.boot.dingtalk.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Exception thrown when the DingTalk authorization code has expired.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@SuppressWarnings("serial")
public class DingTalkCodeExpiredException extends AuthenticationException {

	public DingTalkCodeExpiredException(String msg) {
		super(msg);
	}
	
	public DingTalkCodeExpiredException(String msg, Throwable t) {
		super(msg, t);
	}

}
