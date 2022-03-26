import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;


public class HCP_GUI extends JDialog {

    /*
    *
    * put_patient -> puts 1 patient in their hall and redraws the GUI
    * remove_patient -> removes 1 patient from the hall and redraws the GUI
    *
    * change_color ->TODO
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

    private static String[][] patients_ETR1 = new String[50][2];
    private static String[][] patients_ETR2 = new String[50][2];








    public HCP_GUI() {
        setContentPane(contentPane);
        setModal(true);

        put_patient("ETR1", "C1", "Gray");
        put_patient("ETR2", "A4", "Gray");
        put_patient("ETR1", "C2", "Yellow");

        //test
        remove_patient("ETR1", "C1");
        //draw_person(patients_ETR1);

    }

    public void remove_patient(String hall, String id) {

        switch(hall) {
            case "ETR1":
                // code block
                System.out.println("Remove patient - ETR1");

                for (int pos = 0; pos < patients_ETR1.length; pos ++){

                    if (patients_ETR1[pos][0] == id){

                        patients_ETR1[pos][0] = null;

                    }
                }

                draw_person(patients_ETR1);

                //for (int j = 0; j < patients_ETR1.length; j++) {
                //    System.out.println(patients_ETR1[j]);
                //}





                break;

            case "ETR2":
                // code block
                System.out.println("Remove patient - ETR1");

                for (int pos = 0; pos < patients_ETR2.length; pos ++){

                    if (patients_ETR2[pos][0] == id){

                        patients_ETR2[pos][0] = null;

                    }
                }

                break;

            default:
                // code block
                System.out.println("V  O  I  D, please select another location");
        }


    }

    public void put_patient(String hall, String id, String color){

        Boolean bool_patient_added = false;

        switch(hall) {
            case "ETR1":
                // code block
                System.out.println("Add patient - ETR1");

                for (int i = 0; i < patients_ETR1.length; i++){


                    if (bool_patient_added == false && patients_ETR1[i][0] == null) {
                        patients_ETR1[i][0] = id;
                        patients_ETR1[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                //for (int j = 0; j < patients_ETR1.length; j++){
                //    System.out.println(patients_ETR1[j]);
                //}


                draw_person(patients_ETR1);


                break;
            case "ETR2":
                // code block
                System.out.println("Add patient - ETR2");

                for (int i = 0; i < patients_ETR2.length; i++){


                    if (bool_patient_added == false && patients_ETR2[i][0] == null) {
                        patients_ETR2[i][0] = id;
                        patients_ETR2[i][1] = color;

                        bool_patient_added = true;
                    }

                }
                break;

            case "ETR3":
                // code block
                System.out.println("Add patient - ETR1");

                for (int i = 0; i < patients_ETR1.length; i++){


                    if (bool_patient_added == false && patients_ETR1[i][0] == null) {
                        patients_ETR1[i][0] = id;
                        patients_ETR1[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                for (int j = 0; j < patients_ETR1.length; j++){
                    System.out.println(patients_ETR1[j]);
                }
                break;



            default:
                // code block
                System.out.println("V  O  I  D, please select another location");
        }
    }

    public void draw_person(String[][] list){

        System.out.println("me");

        if (list == patients_ETR1){

            ETR1.removeAll();

            for (int patient = 0; patient < patients_ETR1.length; patient++){

                if (patients_ETR1[patient][0] != null){

                    draw_children(patients_ETR1[patient]);
                }


            }
        }


    }

    public void draw_children(String[] child){


        ETR1.setLayout(new FlowLayout());
        ETR1.add(new JLabel(child[0]));

        if (child[1] == "Gray") {
            ETR1.add(new Circle(gray) );
        } else if (child[1] == "Yellow") {
            ETR1.add(new Circle(yellow) );

        }



        //ETR1.add(new Rectangle(BLACK));



    }



    public static void main(String[] args) {
        HCP_GUI dialog = new HCP_GUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);




    }
}
