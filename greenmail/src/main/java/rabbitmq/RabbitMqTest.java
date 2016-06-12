package rabbitmq;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

public class RabbitMqTest {
	private static final String userName = "admin";
	private static final String password = "admin";
	private static final String virtualHost = "/";
	private static final String hostName = "11.11.161.128";
	private static final Integer portNumber = 5672;

	@Test
	public void testConn() throws IOException, TimeoutException {
		String exchangeName = "lushuangexchange";
		String routingKey = "testroutingkey";
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(userName);
		factory.setPassword(password);
		factory.setVirtualHost(virtualHost);
		factory.setHost(hostName);
		factory.setPort(portNumber);
		Connection conn = factory.newConnection();
		System.out.println(conn);
		final Channel channel = conn.createChannel();
		System.out.println(channel);
		
		// channel.exchangeDeclare(exchangeName, "direct", true);
		// String queueName = channel.queueDeclare().getQueue();
		// System.out.println(queueName);
		// channel.queueBind(queueName, exchangeName, routingKey);

		String queueName = "lushuangqueue";
		channel.exchangeDeclare(exchangeName, "direct", true);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);

		byte[] messageBodyBytes = "Hello, world! test".getBytes();
		channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);

		boolean autoAck = false;
		channel.basicConsume(queueName, autoAck, "myConsumerTag", new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String routingKey = envelope.getRoutingKey();
				String contentType = properties.getContentType();
				long deliveryTag = envelope.getDeliveryTag();
				// (process the message components here ...)
				channel.basicAck(deliveryTag, false);
			}
		});
		
//		boolean autoAck = false;
//		GetResponse response = channel.basicGet(queueName, autoAck);
//		System.out.println(new String(response.getBody()));
		
	}

	@Test
	public void testConn2()
			throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		// amqp://userName:password@hostName:portNumber/virtualHost
		factory.setUri("amqp://admin:admin@11.11.161.128:5672/");
		Connection conn = factory.newConnection();
		System.out.println(conn);
	}

}
