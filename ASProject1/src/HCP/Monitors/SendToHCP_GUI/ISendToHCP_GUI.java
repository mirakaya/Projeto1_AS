package HCP.Monitors.SendToHCP_GUI;

import HCP.Enums.AvailableHalls;
import HCP.Enums.HCPOrders;
import HCP.Enums.PatientEvaluation;

public interface ISendToHCP_GUI {

    void sendPatient();
    void createRequest(HCPOrders order, String idWAge, AvailableHalls hall, PatientEvaluation color);
}
