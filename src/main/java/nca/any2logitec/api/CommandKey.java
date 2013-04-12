package nca.any2logitec.api;

import java.nio.file.Path;

public enum CommandKey {

	BUTTON_0("b0.pressed"),
	BUTTON_1("b1.pressed"),
	BUTTON_2("b2.pressed"),
	BUTTON_3("b3.pressed");
	
	private String fileRepresentation;

	private CommandKey(String fileRepresentation) {
		this.fileRepresentation = fileRepresentation;
	}
	
	static public CommandKey fromFileRepresentation(Path path) {
		for (CommandKey key : values()) {
			if (path.toString().endsWith(key.fileRepresentation)) {
				return key;
			}
		}
		return null;
	}
	
	
	
}

