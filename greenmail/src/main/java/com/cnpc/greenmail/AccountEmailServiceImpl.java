package com.cnpc.greenmail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class AccountEmailServiceImpl implements AccountEmailService {
	/**
	 * @Fields javaMailSender : 简化邮件发送的工具类
	 */
	private JavaMailSender javaMailSender;

	/**
	 * @Fields systemEmail : 系统邮箱账号
	 */
	private String systemEmail;

	public void sendEmail(String to, String subject, String htmlText) throws AccountEmailException {
		try {
			// 使用javaMailSender创建一个MimeMessage msg，对应了将要发送的邮件
			MimeMessage msg = javaMailSender.createMimeMessage();
			// 通过MimeMessage创建一个MimeMessageHelper msgHelper
			MimeMessageHelper msgHelper = new MimeMessageHelper(msg);
			// 使用MimeMessageHelper设置邮件的发送地址、收件地址、主题以及内容
			msgHelper.setFrom(systemEmail);
			msgHelper.setTo(to);
			msgHelper.setSubject(subject);
			msgHelper.setText(htmlText, true);
			// 发送邮件
			javaMailSender.send(msg);
		} catch (MessagingException e) {
			throw new AccountEmailException("Failed to send email.", e);
		}
	}

	/**
	 * @return the javaMailSender
	 */
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	/**
	 * @param javaMailSender
	 *            the javaMailSender to set
	 */
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * @return the systemEmail
	 */
	public String getSystemEmail() {
		return systemEmail;
	}

	/**
	 * @param systemEmail
	 *            the systemEmail to set
	 */
	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}

}
