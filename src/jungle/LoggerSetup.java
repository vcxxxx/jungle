package jungle;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Static instance with file handling capability to avoid console.
 * It is implemented in a separate log file.
 * This class is used for debugging and monitoring code flow.
 *
 * @author 240027249
 */
public class LoggerSetup {

    private static final Logger LOGGER = Logger.getLogger("MyLogger");

    static {
        try {
            // Configure the LOGGER to write to a file
            FileHandler fileHandler = new FileHandler("application.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
            LOGGER.setUseParentHandlers(false); // Disable console output
        } catch (IOException e) {
            System.err.println("Failed to initialize LOGGER: " + e.getMessage());
        }
    }

    /**
     * Returns the LOGGER instance.
     *
     * @return LOGGER
     */
    public static Logger getLogger() {
        return LOGGER;
    }
}
