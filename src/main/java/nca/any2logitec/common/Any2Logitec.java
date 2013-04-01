package nca.any2logitec.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Any2Logitec {
	
	public static Path getLogitecDisplayFolderRoot() {
		String pathStr = System.getenv("LOGITEC_DISPLAY_FOLDER_ROOT");
		if (pathStr == null) {
			pathStr = "C:/temp/logitec";
		}
		return Paths.get(pathStr);
	}
}
