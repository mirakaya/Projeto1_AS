package HCP.Monitors.SendToHCP_GUI;

import HCP.Enums.AvailableHalls;
import HCP.Enums.HCPOrders;
import HCP.Enums.PatientEvaluation;
import HCP.Utils.UnboundedQueue;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class MSendToHCP_GUI implements ISendToHCP_GUI{
    private Socket s ;
    private final ReentrantLock monitor = new ReentrantLock();

    private UnboundedQueue<Object> requestqueue = new UnboundedQueue<>();

    public MSendToHCP_GUI() {

        try {
            s = new Socket("127.0.0.1", 8765);
            s.setKeepAlive(true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void createRequest(HCPOrders order, String idWAge, AvailableHalls hall, PatientEvaluation color){

        monitor.lock();


        Object[] orderToSend = {order, idWAge, hall, color};

        requestqueue.enqueue(orderToSend);

        sendPatient();

        monitor.unlock();




    }

    public void sendPatient(){
        monitor.lock();

        Object orderToSend = requestqueue.dequeue();

        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(orderToSend);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        monitor.unlock();
    }



}
