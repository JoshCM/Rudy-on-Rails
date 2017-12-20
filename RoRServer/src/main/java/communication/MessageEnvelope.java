package communication;

public class MessageEnvelope {
	private String topicName;
	private String messageType;
	private MessageInformation messageInformation;
	
	public MessageEnvelope(String topicName, String messageType, MessageInformation messageInformation) {
		this.topicName = topicName;
		this.messageType = messageType;
		this.messageInformation = messageInformation;
	}
	
	public String getTopicName() {
		return topicName;
	}
	
	public String getMessageType() {
		return messageType;
	}
	
	public MessageInformation getMessageInformation() {
		return messageInformation;
	}
}
