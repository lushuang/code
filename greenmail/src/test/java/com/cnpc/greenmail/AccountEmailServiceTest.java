package com.cnpc.greenmail;

import static org.junit.Assert.assertEquals;

import javax.mail.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

public class AccountEmailServiceTest {
	private GreenMail greenMail;

	private ApplicationContext applicationContext;

	/**
	 * 启动邮件服务器
	 * 
	 * @author jackie
	 * @date Mar 30, 2013
	 * @throws Exception
	 * @return void
	 */
	@Before
	public void setUp() throws Exception {
		greenMail = new GreenMail(ServerSetup.SMTP);
		greenMail.setUser("test@gmail.com", "123456");
		greenMail.start();
	}

	/**
	 * Test method for
	 * {@link com.jackie.codeproject.account.email.AccountEmailService#sendEmail(java.lang.String, java.lang.String, java.lang.String)}
	 * 
	 */
	@Test
	public void testSendEmail() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("account-email.xml");
		AccountEmailService accountEmailService = (AccountEmailService) applicationContext
				.getBean("accountEmailService");
		String subject = "Test Subject";
		String htmlText = "<h3> Test </h3>";

		accountEmailService.sendEmail("test2@gmail.com", subject, htmlText);

		greenMail.waitForIncomingEmail(2000, 1);
		Message[] msgs = greenMail.getReceivedMessages();
		assertEquals(1, msgs.length);
		assertEquals(subject, msgs[0].getSubject());
		assertEquals(htmlText, GreenMailUtil.getBody(msgs[0]).trim());
	}

	/**
	 * 关闭邮件服务器
	 * 
	 * @author jackie
	 * @date Mar 30, 2013
	 * @throws Exception
	 * @return void
	 */
	@After
	public void tearDown() throws Exception {
		greenMail.stop();
	}
}
