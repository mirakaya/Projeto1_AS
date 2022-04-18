package HCP.Entities;

import HCP.Monitors.Simulation.Simulation;
import HCP.gui.HCP_GUI;


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
        }


    }
