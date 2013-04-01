package nca.any2logitec.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MessageWriter {
	public static void writeMessages(Path pathToWrite, List<String> messages) throws IOException {		
		Files.write(pathToWrite, messages, StandardCharsets.UTF_8);		
	}
}
