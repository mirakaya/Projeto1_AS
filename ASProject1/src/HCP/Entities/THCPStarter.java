package HCP.Entities;

import HCP.Monitors.Simulation.Simulation;
import HCP.gui.HCP_GUI;

import java.io.IOException;


/**
     * HCP Starter Thread
     */
    public class THCPStarter extends Thread {


        public THCPStarter() {

            setDaemon(true);
        }

        @Override
        public void run() {

                HCP_GUI hcp_gui = new HCP_GUI();
            try {
                hcp_gui.receiveObjects();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }


    }
