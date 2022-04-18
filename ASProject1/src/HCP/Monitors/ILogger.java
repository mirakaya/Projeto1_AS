package HCP.Monitors;

import HCP.Enums.AvailableHalls;

public interface ILogger {

    //creates the log file and writes the header
    void createLoggerFile();

    void writeInfo();

    //writes 1 line of the log given the info to write (id) and the room
    void createContent(String id, AvailableHalls room);
}