import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;


public class HCP_GUI extends JDialog {

    /*
    *
    * put_patient -> puts 1 patient in their hall and redraws the GUI
    * remove_patient -> removes 1 patient from the hall and redraws the GUI
    *
    * nurses - needs redoing because nurses are no longer part of the rooms
    *
    * */


    private JPanel contentPane;
    private JPanel hospital;
    private JPanel ETR1;
    private JPanel EVR1;
    private JPanel EVR2;
    private JPanel EVR3;
    private JPanel EVR4;
    private JPanel ETR2;
    private JPanel WTH1;
    private JPanel WTH2;
    private JPanel MDW;
    private JPanel MDR1;
    private JPanel MDR2;
    private JPanel MDR3;
    private JPanel MDR4;
    private JPanel PYH;
    private JPanel b_entering;
    private JPanel a_exiting;

    private static String[][] patients_ETR1 = new String[50][2];
    private static String[][] patients_ETR2 = new String[50][2];
    private static String[][] patients_EVR1 = new String[50][2];
    private static String[][] patients_EVR2 = new String[50][2];
    private static String[][] patients_EVR3 = new String[50][2];
    private static String[][] patients_EVR4 = new String[50][2];
    private static String[][] patients_WTH1 = new String[50][2];
    private static String[][] patients_WTH2 = new String[50][2];
    private static String[][] patients_MDW = new String[50][2];
    private static String[][] patients_MDR1 = new String[50][2];
    private static String[][] patients_MDR2 = new String[50][2];
    private static String[][] patients_MDR3 = new String[50][2];
    private static String[][] patients_MDR4 = new String[50][2];
    private static String[][] patients_PYH = new String[50][2];
    private static String[][] patients_b_entering = new String[50][2];
    private static String[][] patients_a_exiting = new String[50][2];



    public HCP_GUI() {
        setContentPane(contentPane);
        setModal(true);

        draw_person(patients_MDR1);
        draw_person(patients_MDR2);
        draw_person(patients_MDR3);
        draw_person(patients_MDR4);


        //test
        put_patient("ETR1", "C1", "Gray");
        put_patient("ETR2", "A4", "Gray");
        put_patient("ETR1", "C2", "Yellow");
        put_patient("BEN", "A4", "Gray");
        put_patient("AEX", "C5", "Red");

        put_patient("EVR1", "Nurse EVR1", "");
        put_patient("EVR2", "Nurse EVR2", "");
        put_patient("EVR3", "Nurse EVR3", "");
        put_patient("EVR4", "Nurse EVR4", "");

        put_patient("PYH", "Cashier PYH", "");

        remove_patient("ETR1", "C1");

    }

    public void remove_patient_aux (String [][] patient_list, String id) {
        for (int pos = 0; pos < patient_list.length; pos ++){

            if (patient_list[pos][0] == id){

                patient_list[pos][0] = null;

            }
        }

        draw_person(patient_list);

    }

    public void remove_patient(String hall, String id) {

        switch(hall) {
            case "BEN":
                remove_patient_aux(patients_b_entering, id);
                break;

            case "ETR1":
                remove_patient_aux(patients_ETR1, id);
                break;

            case "ETR2":
                remove_patient_aux(patients_ETR2, id);
                break;

            case "EVR1":
                remove_patient_aux(patients_EVR1, id);
                break;

            case "EVR2":
                remove_patient_aux(patients_EVR2, id);
                break;

            case "EVR3":
                remove_patient_aux(patients_EVR3, id);
                break;

            case "EVR4":
                remove_patient_aux(patients_EVR4, id);
                break;

            case "WTH1":
                remove_patient_aux(patients_WTH1, id);
                break;

            case "WTH2":
                remove_patient_aux(patients_WTH2, id);
                break;

            case "MDW":
                remove_patient_aux(patients_MDW, id);
                break;

            case "MDR1":
                remove_patient_aux(patients_MDR1, id);
                break;

            case "MDR2":
                remove_patient_aux(patients_MDR2, id);
                break;

            case "MDR3":
                remove_patient_aux(patients_MDR3, id);
                break;

            case "MDR4":
                remove_patient_aux(patients_MDR4, id);
                break;

            case "PYH":
                remove_patient_aux(patients_PYH, id);
                break;
            case "AEX":
                remove_patient_aux(patients_a_exiting, id);
                break;

            default:
                System.out.println("V  O  I  D, please select another location");
        }


    }

    private void put_patient_aux (String [][] patients_list, String id, String color){ //TODO: rename

        Boolean bool_patient_added = false;

        System.out.println("Add patient - " + patients_list);

        for (int i = 0; i < patients_list.length; i++){


            if (bool_patient_added == false && patients_list[i][0] == null) {
                patients_list[i][0] = id;
                patients_list[i][1] = color;

                bool_patient_added = true;
            }

        }

        draw_person(patients_list);

    }

    public void put_patient(String hall, String id, String color){

        Boolean bool_patient_added = false;



        switch(hall) {
            case "BEN":
                put_patient_aux(patients_b_entering, id, color);
                break;

            case "ETR1":
                put_patient_aux(patients_ETR1, id, color);
                break;

            case "ETR2":
                put_patient_aux(patients_ETR2, id, color);
                break;

            case "EVR1":
                put_patient_aux(patients_EVR1, id, color);
                break;

            case "EVR2":
                put_patient_aux(patients_EVR2, id, color);
                break;

            case "EVR3":
                put_patient_aux(patients_EVR3, id, color);
                break;

            case "EVR4":
                put_patient_aux(patients_EVR4, id, color);
                break;

            case "WTH1":
                put_patient_aux(patients_WTH1, id, color);
                break;

            case "WTH2":
                put_patient_aux(patients_WTH2, id, color);
                break;

            case "MDW":
                put_patient_aux(patients_MDW, id, color);
                break;

            case "MDR1":
                put_patient_aux(patients_MDR1, id, color);
                break;

            case "MDR2":
                put_patient_aux(patients_MDR2, id, color);
                break;

            case "MDR3":
                put_patient_aux(patients_MDR3, id, color);
                break;

            case "MDR4":
                put_patient_aux(patients_MDR4, id, color);
                break;

            case "PYH":
                put_patient_aux(patients_PYH, id, color);
                break;

            case "AEX":
                put_patient_aux(patients_a_exiting, id, color);
                break;

            default:
                System.out.println("V  O  I  D, please select another location");
        }
    }



    private void gui_aux (JPanel curr_panel, String [][] patients_list){ //TODO: rename
        curr_panel.removeAll();

        for (int patient = 0; patient < patients_list.length; patient++){

            if (patients_list[patient][0] != null){
                draw_human(curr_panel, patients_list[patient]);
            }
        }

    }

    public void draw_person(String[][] list){
        String[] doctor;

        if (list == patients_b_entering) {
            gui_aux(b_entering, patients_b_entering);
        }else if (list == patients_ETR1) {
            gui_aux(ETR1, patients_ETR1);
        }else if (list == patients_ETR2) {
                gui_aux(ETR2, patients_ETR2);
        }else if (list == patients_EVR1) {
                gui_aux(EVR1, patients_EVR1);
        }else if (list == patients_EVR2) {
                gui_aux(EVR2, patients_EVR2);
        }else if (list == patients_EVR3) {
                gui_aux(EVR3, patients_EVR3);
        }else if (list == patients_EVR4) {
                gui_aux(EVR4, patients_EVR4);
        }else if (list == patients_WTH1) {
                gui_aux(WTH1, patients_WTH1);
        }else if (list == patients_WTH2) {
                gui_aux(WTH2, patients_WTH2);
        }else if (list == patients_MDW) {
                gui_aux(MDW, patients_MDW);
        }else if (list == patients_MDR1) {
                gui_aux(MDR1, patients_MDR1);
                doctor = new String[]{"Doctor MDR1", " "};
                draw_human(MDR1, doctor);
        }else if (list == patients_MDR2) {
                gui_aux(MDR2, patients_MDR2);
                doctor = new String[]{"Doctor MDR2", " "};
                draw_human(MDR2, doctor);
        }else if (list == patients_MDR3) {
                gui_aux(MDR3, patients_MDR3);
                doctor = new String[]{"Doctor MDR3", " "};
                draw_human(MDR3, doctor);
        }else if (list == patients_MDR4) {
            gui_aux(MDR4, patients_MDR4);
            doctor = new String[]{"Doctor MDR4", " "};
            draw_human(MDR4, doctor);
        }else if (list == patients_PYH) {
            gui_aux(PYH, patients_PYH);
        } else if (list == patients_a_exiting) {
            gui_aux(a_exiting, patients_a_exiting);
        }else {
            System.out.println("Algo de errado não está certo...");
        }


    }


    public void draw_human(JPanel curr_frame, String[] patient){//TODO: rename

        curr_frame.setLayout(new FlowLayout());
        curr_frame.add(new JLabel(patient[0]));

        if (patient[0] == "Cashier PYH"){
            curr_frame.add(new Triangle(orange));

        } else if (patient[0].charAt(0) == 'C') {

            if (patient[1] == "Blue") {
                curr_frame.add(new Circle(blue));
            } else if (patient[1] == "Yellow") {
                curr_frame.add(new Circle(yellow));
            } else if (patient[1] == "Red") {
                curr_frame.add(new Circle(red));
            } else {
                curr_frame.add(new Circle(gray));
            }
        }else if (patient[0].charAt(0) == 'A'){

            if (patient[1] == "Blue") {
                curr_frame.add(new Rectangle(blue));
            } else if (patient[1] == "Yellow") {
                curr_frame.add(new Rectangle(yellow));
            } else if (patient[1] == "Red") {
                curr_frame.add(new Rectangle(red));
            } else {
                curr_frame.add(new Rectangle(gray));
            }

        }else if (patient[0].charAt(0) == 'D') {
            curr_frame.add(new Triangle(cyan));

        }else if (patient[0].charAt(0) == 'N') {
            curr_frame.add(new Triangle(pink));

        }

    }

    public static void main(String[] args) {
        HCP_GUI dialog = new HCP_GUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
