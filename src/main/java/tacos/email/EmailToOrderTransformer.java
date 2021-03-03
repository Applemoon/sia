package tacos.email;

import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<Order4Email> {

    @Override
    protected AbstractIntegrationMessageBuilder<Order4Email> doTransform(Message message) throws Exception {
        Order4Email order = processPayload(message);
        return MessageBuilder.withPayload(order);
    }

    private Order4Email processPayload(Message message) throws MessagingException {
        if (message.getFrom().length == 0) {
            throw new MessagingException();
        }
        String email = message.getFrom()[0].toString();
        return new Order4Email(email);
    }
}
