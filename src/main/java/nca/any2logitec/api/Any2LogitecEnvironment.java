package nca.any2logitec.api;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implements methods to get environment settings.
 */
public class Any2LogitecEnvironment {

	/**
	 * Answers the path where you can place inbound files which will be consumed
	 * by the pad.
	 * 
	 * @return Path never null!
	 */
	public static Path getLogitecHubInboundFolder() {
		String pathStr = System.getenv("LOGITEC_DISPLAY_FOLDER_ROOT");
		if (pathStr == null) {
			pathStr = "C:/temp/logitec";
		}
		return Paths.get(pathStr).resolve("inbound");
	}

	/**
	 * Answers the path where you can read outbound files.
	 * 
	 * @return Path never null!
	 */
	public static Path getLogitecHubOutboundFolder() {
		String pathStr = System.getenv("LOGITEC_DISPLAY_FOLDER_ROOT");
		if (pathStr == null) {
			pathStr = "C:/temp/logitec";
		}
		return Paths.get(pathStr).resolve("outbound");
	}
}
