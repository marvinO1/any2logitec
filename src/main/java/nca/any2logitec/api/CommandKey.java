package nca.any2logitec.api;

import java.nio.file.Path;

/**
 * Represents the buttons of the game pad.
 */
public enum CommandKey {

	BUTTON_0("b0.pressed"), 
	BUTTON_1("b1.pressed"), 
	BUTTON_2("b2.pressed"),
	BUTTON_3("b3.pressed");

	private String fileRepresentation;

	private CommandKey(String fileRepresentation) {
		this.fileRepresentation = fileRepresentation;
	}

	static public CommandKey fromFileRepresentation(Path path, String feedName) {
		for (CommandKey key : values()) {
			String name = path.toString();
			if (name.endsWith(key.fileRepresentation) && name.indexOf(feedName + ".") != -1) {
				return key;
			}
		}
		return null;
	}

}
