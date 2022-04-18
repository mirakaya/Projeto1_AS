package HCP.Entities;

import HCP.Enums.AvailableHalls;
import HCP.Enums.HCPOrders;
import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.CCH.ICCHallPatient;
import HCP.Monitors.EH.IEntranceHallPatient;
import HCP.Monitors.EVH.IEVPatient;
import HCP.Monitors.MDH.IMDHPatient;
import HCP.Monitors.MLogger;
import HCP.Monitors.PYH.IPYPatient;
import HCP.Monitors.SendToHCP_GUI.ISendToHCP_GUI;
import HCP.Monitors.SendToHCP_GUI.MSendToHCP_GUI;
import HCP.Monitors.WTH.IWTHPatient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Patient Thread
 */
public class TPatient extends Thread {
    private static int idCounter;

    private final int id = idCounter++;
    private final PatientAge age;

    private final IEntranceHallPatient eh;
    private final IEVPatient evh;
    private final ICCHallPatient cch;
    private final IWTHPatient wth;
    private final IMDHPatient mdh;
    private final IPYPatient pyh;

    private final MLogger log;

    //private final int sendToHCPport;

   // private final MSendToHCP_GUI sendToHCP_gui;

    public TPatient(
            IEntranceHallPatient eh, ICCHallPatient cch,
            IEVPatient evh, IWTHPatient wth,
            IMDHPatient mdh, IPYPatient pyh,
            PatientAge age, MLogger log//, MSendToHCP_GUI sendToHCP_gui
    ) {
        this.age = age;
        this.eh = eh;
        this.cch = cch;
        this.evh = evh;
        this.wth = wth;
        this.mdh = mdh;
        this.pyh = pyh;
        this.log = log;
        //this.sendToHCP_gui = sendToHCP_gui;

        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            String idWAge;

            System.out.println("idk");


            if (age == PatientAge.ADULT){
                idWAge = "A" + id;
            } else {
                idWAge = "C" + id;
            }

            //sendToHCP_gui.createRequest(HCPOrders.CREATE, idWAge, AvailableHalls.BEN, PatientEvaluation.NONE);

            /*Socket s = null;
            try {
                s = new Socket("127.0.0.1", sendToHCPport);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/


            eh.waitFreeRoom(id, age);
            log.createContent(idWAge, AvailableHalls.ETH);
            //sendToHCP_gui.createRequest(HCPOrders.DELETE, idWAge, AvailableHalls.BEN, PatientEvaluation.NONE);
            //sendToHCP_gui.createRequest(HCPOrders.CREATE, idWAge, AvailableHalls.ETH, PatientEvaluation.NONE);

            ObjectOutputStream out = null;


            eh.waitEvaluationHallCall(age);
            eh.awakeWaitingPatient(age);

            Object[] result = evh.waitEvaluation();
            PatientEvaluation evaluation = (PatientEvaluation) result[0];
            int room = (int) result[1];

            AvailableHalls roomChosen;

            switch (room){
                case 1:
                    roomChosen = AvailableHalls.EVR1;
                    break;
                case 2:
                    roomChosen = AvailableHalls.EVR2;
                    break;
                case 3:
                    roomChosen = AvailableHalls.EVR3;
                    break;
                case 4:
                    roomChosen = AvailableHalls.EVR4;
                    break;
                default:
                    roomChosen = AvailableHalls.EVR1;
                    break;

            }

            log.createContent(idWAge, roomChosen);
            //sendToHCP_gui.createRequest(HCPOrders.DELETE, idWAge, AvailableHalls.ETH, evaluation);
            //sendToHCP_gui.createRequest(HCPOrders.CREATE, idWAge, roomChosen, evaluation);

            cch.informLeftEVHall();

            log.createContent(idWAge, AvailableHalls.WTH);
            //sendToHCP_gui.createRequest(HCPOrders.DELETE, idWAge, roomChosen, evaluation);
            //sendToHCP_gui.createRequest(HCPOrders.CREATE, idWAge, AvailableHalls.WTH, evaluation);
            int wtn = wth.waitWTRFree(age);

            AvailableHalls wtnChoosen;
            switch (wtn) {
                case 1:
                    wtnChoosen = AvailableHalls.WTR1;
                    break;
                case 2:
                    wtnChoosen = AvailableHalls.WTR2;
                    break;
                default:
                    wtnChoosen = AvailableHalls.WTR1;
            }

            log.createContent(idWAge, wtnChoosen);
            //sendToHCP_gui.createRequest(HCPOrders.DELETE, idWAge, AvailableHalls.WTH, evaluation);
            //sendToHCP_gui.createRequest(HCPOrders.CREATE, idWAge, wtnChoosen, evaluation);


            cch.informLeftWTR(age);
            wth.waitMDWCall(wtn, age,evaluation);

            log.createContent(idWAge, AvailableHalls.MDH);
            //sendToHCP_gui.createRequest(HCPOrders.DELETE, idWAge, wtnChoosen,evaluation);
            //sendToHCP_gui.createRequest(HCPOrders.CREATE, idWAge, AvailableHalls.MDH, evaluation);

            int mdhRoom = mdh.waitMDRCall(age, wtn);
            AvailableHalls mdhRoomChoosen;
            switch (mdhRoom){
                case 1:
                    mdhRoomChoosen = AvailableHalls.MDR1;
                    break;
                case 2:
                    mdhRoomChoosen = AvailableHalls.MDR2;
                    break;
                case 3:
                    mdhRoomChoosen = AvailableHalls.MDR3;
                    break;
                case 4:
                    mdhRoomChoosen = AvailableHalls.MDR4;
                    break;
                default:
                    mdhRoomChoosen = AvailableHalls.MDR1;
                    break;

            }


            log.createContent(idWAge, mdhRoomChoosen);
            //sendToHCP_gui.createRequest(HCPOrders.DELETE, idWAge, AvailableHalls.MDH, evaluation);
            //sendToHCP_gui.createRequest(HCPOrders.CREATE, idWAge, mdhRoomChoosen, evaluation);
            cch.informLeftMDW(age);
            mdh.waitMDRConcluded(age, wtn, mdhRoom);
            cch.informLeftMDR(age);

            log.createContent(idWAge, AvailableHalls.PYH);
            //sendToHCP_gui.createRequest(HCPOrders.DELETE, idWAge, mdhRoomChoosen, evaluation);
            //sendToHCP_gui.createRequest(HCPOrders.CREATE, idWAge, AvailableHalls.PYH, evaluation);
            pyh.waitPayment(id);

        } catch (InterruptedException ignored) {}
    }
}
