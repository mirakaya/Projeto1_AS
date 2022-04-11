package gui;

import HCP.Enums.SimulationState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.*;

import static java.lang.Integer.parseInt;

public class CCP_GUI {

    private static JFrame frame_main;

    private final String values_nr_seats[] = { "4", "2", "6", "8", "10" };
    private final String values_time[] = { "100", "250", "500", "1000" };

    private JLabel l_adult_patients = new JLabel();
    private JLabel l_child_patients = new JLabel();
    private JLabel l_nr_seats = new JLabel();
    private JLabel l_EVT = new JLabel();
    private JLabel l_MDT = new JLabel();
    private JLabel l_PYT = new JLabel();
    private JLabel l_time_to_move = new JLabel();

    private JTextField t_adult_patients = new JTextField("10");
    private JTextField t_child_patients = new JTextField("10");
    private JComboBox c_nr_seats = new JComboBox(values_nr_seats);
    private JComboBox c_EVT = new JComboBox(values_time);
    private JComboBox c_MDT = new JComboBox(values_time);
    private JComboBox c_PYT = new JComboBox(values_time);
    private JComboBox c_time_to_move = new JComboBox(values_time);

    private JButton b_start = new JButton("Start");

    private JButton b_start2 = new JButton("Start");
    private JButton b_suspend = new JButton("Suspend");
    private JButton b_resume = new JButton("Resume");
    private JButton b_stop = new JButton("Stop");
    private JButton b_end = new JButton("End");

    private JRadioButton r_operating_mode = new JRadioButton();

    // create panels
    private JPanel frame_init = new JPanel();
    private JPanel frame_start_button = new JPanel();

    private JPanel sec_panel = new JPanel();
    private JPanel radio_panel = new JPanel();

    private Socket s;



    public CCP_GUI() {

        try {
            s = new Socket("127.0.0.1", 8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BuildGUI();

    }

    private void createInitialFrame(){

        sec_panel.setVisible(false);
        radio_panel.setVisible(false);

        // add text to label
        l_adult_patients.setText("Number of adult patients");
        l_child_patients.setText("Number of child patients");
        l_nr_seats.setText("Number of seats");
        l_EVT.setText("Evaluation time");
        l_MDT.setText("Medical appointment time");
        l_PYT.setText("Payment time");
        l_time_to_move.setText("Time to move");

        //add grid with 2 rows
        GridLayout grid = new GridLayout(0,2);
        grid.setVgap(20);
        grid.setHgap(20);
        frame_init.setLayout(grid);

        // add label to panel
        frame_init.add(l_adult_patients);
        frame_init.add(t_adult_patients);

        frame_init.add(l_child_patients);
        frame_init.add(t_child_patients);

        frame_init.add(l_nr_seats);
        frame_init.add(c_nr_seats);

        frame_init.add(l_EVT);
        frame_init.add(c_EVT);

        frame_init.add(l_MDT);
        frame_init.add(c_MDT);

        frame_init.add(l_PYT);
        frame_init.add(c_PYT);

        frame_init.add(l_time_to_move);
        frame_init.add(c_time_to_move);

        // add panel to frame
        frame_main.add(frame_init, BorderLayout.NORTH);

        frame_start_button.add(b_start);

        //add buttons
        frame_main.add(frame_start_button, BorderLayout.PAGE_END);

        //listener for button
        b_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int nr_adults = parseInt(t_adult_patients.getText());
                int nr_children = parseInt(t_child_patients.getText());
                int nr_seats = parseInt(c_nr_seats.getSelectedItem().toString());
                int val_EVT = parseInt(c_EVT.getSelectedItem().toString());
                int val_MDT = parseInt(c_MDT.getSelectedItem().toString());
                int val_PYT = parseInt(c_PYT.getSelectedItem().toString());
                int val_time_to_move = parseInt(c_time_to_move.getSelectedItem().toString());

                System.out.println("Nr adults - " + nr_adults + "\nNr child - " + nr_children + "\nNr seats - " + nr_seats + "\nEVT - " + val_EVT + "\nMDT - " + val_MDT + "\nPYT - " + val_PYT + "\nTTM - " + val_time_to_move);

                //nr adults, nr children, nr seats, EVT, MDT, PYT, TTM
                int [] info = {nr_adults, nr_children, nr_seats, val_EVT, val_MDT, val_PYT, val_time_to_move};

                //send the array through a socket
                try {

                    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                    out.writeObject(info);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                frame_init.setVisible(false);
                frame_start_button.setVisible(false);

                createSecondScreen();
            }
        });

    }

   private void createSecondScreen() {

       GridLayout grid2 = new GridLayout(0, 1);
       grid2.setVgap(20);
       grid2.setHgap(20);
       sec_panel.setLayout(grid2);

       sec_panel.add(b_start2);
       sec_panel.add(b_suspend);
       sec_panel.add(b_resume);
       sec_panel.add(b_stop);
       sec_panel.add(b_end);

       b_start2.addActionListener( new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               System.out.println("Start");

               try {
                   ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                   out.writeObject(SimulationState.START);

               } catch (IOException ioException) {
                   ioException.printStackTrace();
               }
           }
       });

       b_suspend.addActionListener( new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               System.out.println("Sus");

               try {
                   ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                   out.writeObject(SimulationState.SUSPEND);

               } catch (IOException ioException) {
                   ioException.printStackTrace();
               }
           }
       });

       b_resume.addActionListener( new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               System.out.println("Resume");

               try {
                   ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                   out.writeObject(SimulationState.RESUME);

               } catch (IOException ioException) {
                   ioException.printStackTrace();
               }
           }
       });

       b_stop.addActionListener( new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               System.out.println("Stop");

               try {
                   ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                   out.writeObject(SimulationState.STOP);

               } catch (IOException ioException) {
                   ioException.printStackTrace();
               }
           }
       });


       b_end.addActionListener( new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               System.out.println("End");

               try {
                   ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                   out.writeObject(SimulationState.END);

               } catch (IOException ioException) {
                   ioException.printStackTrace();
               }
           }
       });




       // Initialization radio buttons
       JRadioButton r_auto = new JRadioButton();
       r_auto.setSelected(true);
       JRadioButton r_man = new JRadioButton();

       // Initialization of radio group
       ButtonGroup radio_group = new ButtonGroup();

       JLabel l_mode = new JLabel("Select mode");

       r_auto.setText("Auto");
       r_man.setText("Manual");


       // add label to panel
       radio_panel.add(l_mode);

       // add buttons to panel
       radio_panel.add(r_auto);
       radio_panel.add(r_man);

       //radio buttons listener
       ActionListener radio_listener = new ActionListener() {
           public void actionPerformed(ActionEvent actionEvent) {
               AbstractButton aButton = (AbstractButton) actionEvent.getSource();
               System.out.println("Selected: " + aButton.getText());
           }
       };

       r_auto.addActionListener(radio_listener);
       r_man.addActionListener(radio_listener);

       // Adding radios to group
       radio_group.add(r_auto);
       radio_group.add(r_man);



        //add elems to frame_main
       frame_main.add(sec_panel, BorderLayout.PAGE_START);
       frame_main.add(radio_panel, BorderLayout.PAGE_END);

       sec_panel.setVisible(true);
       radio_panel.setVisible(true);


   }



    private void BuildGUI() {

        // create a new frame to store text field and button
        frame_main = new JFrame("Projeto AS");

        createInitialFrame();

        // set the size of frame
        frame_main.setSize(1000,400 );

        frame_main.show();
    }


    private void open() {
        frame_main.setVisible(true);
    }


    public static void main(String[] args) {
        CCP_GUI gui = new CCP_GUI();
        gui.open();

    }


}

