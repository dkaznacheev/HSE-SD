import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.List;

public class SQSService {
    private String queueIn;
    private String queueOut;
    private AmazonSQS sqs;

    public SQSService(String queueIn, String queueOut, AmazonSQS sqs) {
        this.queueIn = queueIn;
        this.queueOut = queueOut;
        this.sqs = sqs;
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

    private static AmazonSQS waitForSQS() {
        while (true) {
            try {
                return AmazonSQSClientBuilder.standard().withRegion("us-east-1").build();
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) {
        AmazonSQS sqs = waitForSQS();
        new SQSService(args[0], args[1], sqs).start();
    }
}