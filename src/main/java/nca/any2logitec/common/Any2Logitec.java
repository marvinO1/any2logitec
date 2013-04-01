package nca.any2logitec.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Any2Logitec {

	
	public static Path getLogitecDisplayFolderRoot() {
		String pathStr = System.getProperty("LOGITEC_DISPLAY_FOLDER_ROOT", "C:/temp/logitec");
		return Paths.get(pathStr);
	}
}
