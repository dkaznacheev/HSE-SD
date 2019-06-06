import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.List;

public class SQSService {
    private String queueIn;
    private String queueOut;
    private AmazonSQS sqs;

    public SQSService(String queueIn, String queueOut) {
        this.queueIn = queueIn;
        this.queueOut = queueOut;
        this.sqs = AmazonSQSClientBuilder.standard().withRegion("us-east-1").build();
    }

    private long getMessage() {
        List<Message> messages = sqs.receiveMessage(queueIn).getMessages();
        while (messages.isEmpty()) {
            messages = sqs.receiveMessage(queueIn).getMessages();
        }
        String message = messages.get(0).getBody();
        return Long.parseLong(message);
    }

    private void sendMessage(long x) {
        SendMessageRequest msg = new SendMessageRequest()
                .withQueueUrl(queueOut)
                .withMessageBody(String.valueOf(x));
        sqs.sendMessage(msg);
    }

    public void start() {
        if (queueIn.endsWith("a")) {
            sendMessage(0);
        }
        while (true) {
            long x = getMessage();
            System.out.println(x);
            sendMessage(x + 1);
        }
    }

    public static void main(String[] args) {
        for (String arg: args) {
            System.out.println(arg);
        }
        //new SQSService(args[0], args[1]).start();
    }
}