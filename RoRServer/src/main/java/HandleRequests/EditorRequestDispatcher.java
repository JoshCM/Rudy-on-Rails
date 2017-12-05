package HandleRequests;

import models.dataTranserObject.MessageInformation;

public class EditorRequestDispatcher {
	
	public EditorRequestDispatcher() {
		new EditorRequestDispatcher();

	}
	
	public void dispatch(String request, String message) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		MessageInformation messageInformation = requestSerializer.deserialize(message);

		// Wird wahrscheinlich gar nicht gebraucht
		// createRequestToFunctionMap(request,requestInformation);
	}
}
