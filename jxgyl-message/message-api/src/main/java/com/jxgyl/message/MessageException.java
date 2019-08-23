package com.jxgyl.message;

/**
 * 消息运行时异常
 *
 * @author iss002
 *
 */
public class MessageException extends RuntimeException {

	private static final long serialVersionUID = -2082831458312926086L;

	public MessageException() {
		super();
	}

	public MessageException(String message) {
		super(message);
	}

	public MessageException(Throwable cause) {
		super(cause);
	}

	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}
}
