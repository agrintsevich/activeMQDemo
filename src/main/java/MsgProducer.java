import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MsgProducer implements Runnable {
    public void run() {
        try {
            final ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("vm://localhost");
            final Connection connection = activeMQConnectionFactory.createConnection();
            connection.start();
            final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            final Destination destination = session.createQueue("TEST.FOO");
            final MessageProducer messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            final String message = String.format("Hello world from %s", Thread.currentThread().getName());
            final TextMessage textMessage = session.createTextMessage(message);

            System.out.println(String.format("Sent message %s", message));
            messageProducer.send(textMessage);
            session.close();
            connection.close();

        } catch (final Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
