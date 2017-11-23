package communication.helper;

import java.util.Map;

public class Command {
	public CommandEnum cEnum;
	private Map<String, String> attributes;

	public CommandEnum getcEnum() {
		return cEnum;
	}

	public void setcEnum(CommandEnum cEnum) {
		this.cEnum = cEnum;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
