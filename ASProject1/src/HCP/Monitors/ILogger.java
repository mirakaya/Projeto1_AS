package HCP.Monitors;

import HCP.Enums.AvailableHalls;

public interface ILogger {

    void writeInfo();
    void createContent(int id, AvailableHalls room);
    void createLoggerFile ();

}
