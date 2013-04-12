package nca.any2logitec.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Writes 
 * @author Beat
 *
 */
public class MessageWriter {
	public static void writeMessages(Path pathToWrite, List<String> messages) throws IOException {		
		Files.write(pathToWrite, messages, StandardCharsets.UTF_8);		
	}
}