package HCP.Monitors;

import HCP.Enums.AvailableHalls;
import HCP.Utils.UnboundedQueue;

import java.io.*;
import java.util.concurrent.locks.ReentrantLock;

public class MLogger implements ILogger{
    private final ReentrantLock monitor = new ReentrantLock();
    private int numPatients;
    private int entered;

    // Queue with the free rooms
    private final UnboundedQueue<String> logsToWrite;

    private static final String FILENAME = "log.txt";

    public MLogger(int numPatients) {
        createLoggerFile();
        this.numPatients = numPatients;
        this.logsToWrite = new UnboundedQueue<>();
        this.entered = 0;
    }


    //creates the log file and writes the header
    @Override
    public void createLoggerFile () {

        monitor.lock();

        File f = new File(FILENAME);
        PrintWriter pw = null;


        if(!f.exists()) { //file doesn't exist, must create
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            pw = new PrintWriter(FILENAME);
            pw.write(" STT | ETH ET1 ET2 | EVR1 EVR2 EVR3 EVR4 | WTH WTR1 WTR2 | MDH MDR1 MDR2 MDR3 MDR4 | PYH\n");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        System.out.println("me");

        monitor.unlock();
    }

    @Override
    public void writeInfo(){

        monitor.lock();


        String content = logsToWrite.dequeue();

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(FILENAME, true));
            writer.write(content + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        monitor.unlock();
    }


    //writes 1 line of the log given the info to write (id) and the room
    @Override
    public void createContent(int id, AvailableHalls room) {

        monitor.lock();

        File f = new File(FILENAME);

        String content = "";

        AvailableHalls[] states = AvailableHalls.values();

        for (int pos = 0; pos < states.length; pos++) {

            //System.out.println(states[pos]);


            if (room == states[pos]){

                System.out.println("yes" + entered);

                content += " " + id;

                if (states[pos].toString().length() == 4) {
                    content += " ";
                }


            } else {

                if (states[pos].toString().length() == 3) {
                    content += "    ";
                } else if (states[pos].toString().length() == 4) {
                    content += "     ";
                }
            }
            if (pos == 0 || pos == 3 || pos == 7 || pos == 10 || pos == 15) {
                content += " |";
            }
        }
        //System.out.println(content);

        logsToWrite.enqueue(content);
        this.entered ++;
        writeInfo();

        monitor.unlock();


        //if (entered == (numPatients -1) * 2){writeInfo();};


    }


}
