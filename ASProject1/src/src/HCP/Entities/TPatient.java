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

    private final int sendToHCPport;

    public TPatient(
            IEntranceHallPatient eh, ICCHallPatient cch,
            IEVPatient evh, IWTHPatient wth,
            IMDHPatient mdh, IPYPatient pyh,
            PatientAge age, MLogger log, int sendToHCP
    ) {
        this.age = age;
        this.eh = eh;
        this.cch = cch;
        this.evh = evh;
        this.wth = wth;
        this.mdh = mdh;
        this.pyh = pyh;
        this.log = log;
        this.sendToHCPport = sendToHCP;

        setDaemon(true);
    }

    @Override
    public void run() {
        try {



            String idWAge;

            if (age == PatientAge.ADULT){
                idWAge = "A" + id;
            } else {
                idWAge = "C" + id;
            }

            /*Socket s = null;
            try {
                s = new Socket("127.0.0.1", sendToHCPport);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/


            eh.waitFreeRoom(id, age);
            log.createContent(idWAge, AvailableHalls.ETH);

            ObjectOutputStream out = null;
            /*try {
                out = new ObjectOutputStream(s.getOutputStream());
                Object[] orderHCP = {HCPOrders.CREATE, id, AvailableHalls.ETH, PatientEvaluation.NONE};
                out.writeObject(orderHCP);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/

            eh.waitEvaluationHallCall(age);
            eh.awakeWaitingPatient(age);
            //TODO: missing room ET
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

            cch.informLeftEVHall();

            log.createContent(idWAge, AvailableHalls.WTH);
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
            cch.informLeftWTR(age);
            wth.waitMDWCall(wtn, age,evaluation);

            log.createContent(idWAge, AvailableHalls.MDH);
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
            cch.informLeftMDW(age);
            mdh.waitMDRConcluded(age, wtn, mdhRoom);
            cch.informLeftMDR(age);

            log.createContent(idWAge, AvailableHalls.PYH);
            pyh.waitPayment(id);

            System.out.println("me");
        } catch (InterruptedException ignored) {}
    }
}
