package com.cnpc.greenmail;

public class AccountEmailException extends Exception {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -5399025975888079256L;

	/**
	 * 错误消息
	 */
	private String message;

	public AccountEmailException(String message) {
		this.message = message;
	}

	public AccountEmailException(String message, Throwable throwable) {
		super(message, throwable);
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
