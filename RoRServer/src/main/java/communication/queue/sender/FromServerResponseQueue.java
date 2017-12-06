package communication.queue.sender;

public class FromServerResponseQueue extends QueueSender{

    public FromServerResponseQueue(String queueName) {
        super(queueName);
    }

    @Override
    protected void createQueue() {
    	super.createQueue();
    }
}
