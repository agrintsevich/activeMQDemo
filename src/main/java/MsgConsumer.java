import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.beans.ExceptionListener;

public class MsgConsumer implements Runnable, ExceptionListener {

    public void run() {
        try {
            final ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("vm://localhost");
            final Connection connection = activeMQConnectionFactory.createConnection();
            connection.start();

            final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            final Destination destination = session.createQueue("TEST.FOO");

            final MessageConsumer messageConsumer = session.createConsumer(destination);
            final Message message = messageConsumer.receive();
            if (message instanceof TextMessage) {
                final TextMessage textMessage = (TextMessage) message;
                System.out.println(String.format("Received: %s", textMessage.getText()));
            } else {
                System.out.println(String.format("Received: %s", message));
            }
            messageConsumer.close();
            session.close();
            connection.close();
        } catch (final Exception e) {
            System.out.println("Caught " + e.getMessage());
            e.printStackTrace();
        }


    }

    public void exceptionThrown(final Exception e) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }
}
