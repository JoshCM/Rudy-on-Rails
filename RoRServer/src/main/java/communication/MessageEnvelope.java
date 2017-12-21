package communication;

public class MessageEnvelope {
	private String destinationName;
	private String messageType;
	private MessageInformation messageInformation;
	
	public MessageEnvelope(String destinationName, String messageType, MessageInformation messageInformation) {
		this.destinationName = destinationName;
		this.messageType = messageType;
		this.messageInformation = messageInformation;
	}
	
	public String getDestinationName() {
		return destinationName;
	}
	
	public String getMessageType() {
		return messageType;
	}
	
	public MessageInformation getMessageInformation() {
		return messageInformation;
	}
}
