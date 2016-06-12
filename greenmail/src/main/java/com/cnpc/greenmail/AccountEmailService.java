package com.cnpc.greenmail;

public interface AccountEmailService {
	/**
	 * 发送邮件方法
	 * 
	 * @author jackie
	 * @date Mar 30, 2013
	 * @param to
	 * @param subject
	 * @param htmlText
	 * @throws Exception
	 * @return void
	 */
	void sendEmail(String to, String subject, String htmlText) throws AccountEmailException;
}
