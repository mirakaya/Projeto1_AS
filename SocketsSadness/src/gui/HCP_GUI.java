import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;


public class HCP_GUI extends JDialog {

    /*
    *
    * put_patient -> puts 1 patient in their hall and redraws the GUI
    * remove_patient -> removes 1 patient from the hall and redraws the GUI
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



    public HCP_GUI() {
        setContentPane(contentPane);
        setModal(true);
        draw_person(patients_EVR1);
        draw_person(patients_EVR2);
        draw_person(patients_EVR3);
        draw_person(patients_EVR4);

        draw_person(patients_MDR1);
        draw_person(patients_MDR2);
        draw_person(patients_MDR3);
        draw_person(patients_MDR4);

        draw_person(patients_PYH);

        //test
        put_patient("ETR1", "C1", "Gray");
        put_patient("ETR2", "A4", "Gray");
        put_patient("ETR1", "C2", "Yellow");

        //remove_patient("ETR1", "C1");
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

                break;

            case "ETR2":
                // code block
                System.out.println("Remove patient - ETR2");

                for (int pos = 0; pos < patients_ETR2.length; pos ++){

                    if (patients_ETR2[pos][0] == id){

                        patients_ETR2[pos][0] = null;

                    }
                }

                draw_person(patients_ETR2);

                break;

            case "EVR1":
                // code block
                System.out.println("Remove patient - EVR1");

                for (int pos = 0; pos < patients_EVR1.length; pos ++){

                    if (patients_EVR1[pos][0] == id){

                        patients_EVR1[pos][0] = null;

                    }
                }

                draw_person(patients_EVR1);

                break;

            case "EVR2":
                // code block
                System.out.println("Remove patient - EVR2");

                for (int pos = 0; pos < patients_EVR2.length; pos ++){

                    if (patients_EVR2[pos][0] == id){

                        patients_EVR2[pos][0] = null;

                    }
                }

                draw_person(patients_EVR2);

                break;

            case "EVR3":
                // code block
                System.out.println("Remove patient - EVR3");

                for (int pos = 0; pos < patients_EVR3.length; pos ++){

                    if (patients_EVR3[pos][0] == id){

                        patients_EVR3[pos][0] = null;

                    }
                }

                draw_person(patients_EVR3);

                break;

            case "EVR4":
                // code block
                System.out.println("Remove patient - EVR4");

                for (int pos = 0; pos < patients_EVR4.length; pos ++){

                    if (patients_EVR4[pos][0] == id){

                        patients_EVR4[pos][0] = null;

                    }
                }

                draw_person(patients_EVR4);

                break;



            case "WTH1":
                // code block
                System.out.println("Remove patient - WTH1");

                for (int pos = 0; pos < patients_WTH1.length; pos ++){

                    if (patients_WTH1[pos][0] == id){

                        patients_WTH1[pos][0] = null;

                    }
                }

                draw_person(patients_WTH1);

                break;

            case "WTH2":
                // code block
                System.out.println("Remove patient - WTH2");

                for (int pos = 0; pos < patients_WTH2.length; pos ++){

                    if (patients_WTH2[pos][0] == id){

                        patients_WTH2[pos][0] = null;

                    }
                }

                draw_person(patients_WTH2);

                break;

            case "MDW":
                // code block
                System.out.println("Remove patient - MDW");

                for (int pos = 0; pos < patients_MDW.length; pos ++){

                    if (patients_MDW[pos][0] == id){

                        patients_MDW[pos][0] = null;

                    }
                }

                draw_person(patients_MDW);

                break;

            case "MDR1":
                // code block
                System.out.println("Remove patient - MDR1");

                for (int pos = 0; pos < patients_MDR1.length; pos ++){

                    if (patients_MDR1[pos][0] == id){

                        patients_MDR1[pos][0] = null;

                    }
                }

                draw_person(patients_MDR1);

                break;

            case "MDR2":
                // code block
                System.out.println("Remove patient - MDR2");

                for (int pos = 0; pos < patients_MDR2.length; pos ++){

                    if (patients_MDR2[pos][0] == id){

                        patients_MDR2[pos][0] = null;

                    }
                }

                draw_person(patients_MDR2);

                break;

            case "MDR3":
                // code block
                System.out.println("Remove patient - MDR3");

                for (int pos = 0; pos < patients_MDR3.length; pos ++){

                    if (patients_MDR3[pos][0] == id){

                        patients_MDR3[pos][0] = null;

                    }
                }

                draw_person(patients_MDR3);

                break;

            case "MDR4":
                // code block
                System.out.println("Remove patient - MDR4");

                for (int pos = 0; pos < patients_MDR4.length; pos ++){

                    if (patients_MDR4[pos][0] == id){

                        patients_MDR4[pos][0] = null;

                    }
                }

                draw_person(patients_MDR4);

                break;

            case "PYH":
                // code block
                System.out.println("Remove patient - PYH");

                for (int pos = 0; pos < patients_PYH.length; pos ++){

                    if (patients_PYH[pos][0] == id){

                        patients_PYH[pos][0] = null;

                    }
                }

                draw_person(patients_PYH);

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

                draw_person(patients_ETR1);

                break;

            case "ETR2":
                // code block
                System.out.println("Add patient - ETR1");

                for (int i = 0; i < patients_ETR2.length; i++){


                    if (bool_patient_added == false && patients_ETR2[i][0] == null) {
                        patients_ETR2[i][0] = id;
                        patients_ETR2[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_ETR2);

                break;

            case "EVR1":
                // code block
                System.out.println("Add patient - EVR1");

                for (int i = 0; i < patients_EVR1.length; i++){


                    if (bool_patient_added == false && patients_EVR1[i][0] == null) {
                        patients_EVR1[i][0] = id;
                        patients_EVR1[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_EVR1);

                break;
            case "EVR2":
                // code block
                System.out.println("Add patient - EVR2");

                for (int i = 0; i < patients_EVR2.length; i++){


                    if (bool_patient_added == false && patients_EVR2[i][0] == null) {
                        patients_EVR2[i][0] = id;
                        patients_EVR2[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_EVR2);

                break;

            case "EVR3":
                // code block
                System.out.println("Add patient - EVR3");

                for (int i = 0; i < patients_EVR3.length; i++){


                    if (bool_patient_added == false && patients_EVR3[i][0] == null) {
                        patients_EVR3[i][0] = id;
                        patients_EVR3[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_EVR3);

                break;

            case "EVR4":
                // code block
                System.out.println("Add patient - EVR4");

                for (int i = 0; i < patients_EVR1.length; i++){


                    if (bool_patient_added == false && patients_EVR4[i][0] == null) {
                        patients_EVR4[i][0] = id;
                        patients_EVR4[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_EVR4);

                break;
            case "WTH1":
                // code block
                System.out.println("Add patient - WTH1");

                for (int i = 0; i < patients_EVR1.length; i++){


                    if (bool_patient_added == false && patients_WTH1[i][0] == null) {
                        patients_WTH1[i][0] = id;
                        patients_WTH1[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_WTH1);

                break;

            case "WTH2":
                // code block
                System.out.println("Add patient - WTH1");

                for (int i = 0; i < patients_EVR1.length; i++){


                    if (bool_patient_added == false && patients_WTH2[i][0] == null) {
                        patients_WTH2[i][0] = id;
                        patients_WTH2[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_WTH2);

                break;

            case "MDW":
                // code block
                System.out.println("Add patient - WTH1");

                for (int i = 0; i < patients_EVR1.length; i++){


                    if (bool_patient_added == false && patients_MDW[i][0] == null) {
                        patients_MDW[i][0] = id;
                        patients_MDW[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_MDW);

                break;

            case "MDR1":
                // code block
                System.out.println("Add patient - WTH1");

                for (int i = 0; i < patients_EVR1.length; i++){


                    if (bool_patient_added == false && patients_MDR1[i][0] == null) {
                        patients_MDR1[i][0] = id;
                        patients_MDR1[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_MDR1);

                break;

            case "MDR2":
                // code block
                System.out.println("Add patient - WTH1");

                for (int i = 0; i < patients_EVR1.length; i++){


                    if (bool_patient_added == false && patients_MDR2[i][0] == null) {
                        patients_MDR2[i][0] = id;
                        patients_MDR2[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_MDR2);

                break;

            case "MDR3":
                // code block
                System.out.println("Add patient - WTH1");

                for (int i = 0; i < patients_EVR1.length; i++){


                    if (bool_patient_added == false && patients_MDR3[i][0] == null) {
                        patients_MDR3[i][0] = id;
                        patients_MDR3[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_MDR3);

                break;

            case "MDR4":
                // code block
                System.out.println("Add patient - MDR4");

                for (int i = 0; i < patients_EVR1.length; i++){


                    if (bool_patient_added == false && patients_MDR4[i][0] == null) {
                        patients_MDR4[i][0] = id;
                        patients_MDR4[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_MDR4);

                break;


            case "PYH":
                // code block
                System.out.println("Add patient - WTH1");

                for (int i = 0; i < patients_EVR1.length; i++){


                    if (bool_patient_added == false && patients_PYH[i][0] == null) {
                        patients_PYH[i][0] = id;
                        patients_PYH[i][1] = color;

                        bool_patient_added = true;
                    }

                }

                draw_person(patients_PYH);

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

                    draw_ETR1(patients_ETR1[patient]);
                }
            }
        } else if (list == patients_ETR2){

            ETR2.removeAll();

            for (int patient = 0; patient < patients_ETR2.length; patient++){

                if (patients_ETR2[patient][0] != null){

                    draw_ETR2(patients_ETR2[patient]);
                }
            }
        }else if (list == patients_EVR1){

            EVR1.removeAll();
            String[] nurse = {"Nurse EVR1", " "};
            draw_EVR1(nurse);

            for (int patient = 0; patient < patients_EVR1.length; patient++){

                if (patients_EVR1[patient][0] != null){

                    draw_EVR1(patients_EVR1[patient]);
                }
            }
        }else if (list == patients_EVR2){

            EVR2.removeAll();
            String[] nurse = {"Nurse EVR2", " "};
            draw_EVR2(nurse);

            for (int patient = 0; patient < patients_EVR2.length; patient++){

                if (patients_EVR2[patient][0] != null){

                    draw_EVR2(patients_EVR2[patient]);
                }
            }
        }else if (list == patients_EVR3){

            EVR3.removeAll();
            String[] nurse = {"Nurse EVR3", " "};
            draw_EVR3(nurse);

            for (int patient = 0; patient < patients_EVR3.length; patient++){

                if (patients_EVR3[patient][0] != null){

                    draw_EVR3(patients_EVR3[patient]);
                }
            }
        }else if (list == patients_EVR4){

            EVR4.removeAll();
            String[] nurse = {"Nurse EVR4", " "};
            draw_EVR4(nurse);

            for (int patient = 0; patient < patients_EVR4.length; patient++){

                if (patients_EVR4[patient][0] != null){

                    draw_EVR4(patients_EVR4[patient]);
                }
            }
        }else if (list == patients_WTH1){

            WTH1.removeAll();


            for (int patient = 0; patient < patients_WTH1.length; patient++){

                if (patients_WTH1[patient][0] != null){

                    draw_WTH1(patients_WTH1[patient]);
                }
            }
        }else if (list == patients_WTH2) {

            WTH2.removeAll();


            for (int patient = 0; patient < patients_WTH2.length; patient++) {

                if (patients_WTH2[patient][0] != null) {

                    draw_WTH2(patients_WTH2[patient]);
                }
            }
        }else if (list == patients_MDW){

            MDW.removeAll();


            for (int patient = 0; patient < patients_MDW.length; patient++){

                if (patients_MDW[patient][0] != null){

                    draw_MDW(patients_MDW[patient]);
                }
            }
        }else if (list == patients_MDR1){

            MDR1.removeAll();
            String[] doctor = {"Doctor MDR1", " "};
            draw_MDR1(doctor);


            for (int patient = 0; patient < patients_MDR1.length; patient++){

                if (patients_MDR1[patient][0] != null){

                    draw_MDR1(patients_MDR1[patient]);
                }
            }
        }else if (list == patients_MDR2){

            MDR2.removeAll();
            String[] doctor = {"Doctor MDR2", " "};
            draw_MDR2(doctor);


            for (int patient = 0; patient < patients_MDR2.length; patient++){

                if (patients_MDR2[patient][0] != null){

                    draw_MDR2(patients_MDR2[patient]);
                }
            }
        }else if (list == patients_MDR3){

            MDR3.removeAll();
            String[] doctor = {"Doctor MDR3", " "};
            draw_MDR3(doctor);


            for (int patient = 0; patient < patients_MDR3.length; patient++){

                if (patients_MDR3[patient][0] != null){

                    draw_MDR3(patients_MDR3[patient]);
                }
            }
        }else if (list == patients_MDR4){

            MDR4.removeAll();
            String[] doctor = {"Doctor MDR4", " "};
            draw_MDR4(doctor);


            for (int patient = 0; patient < patients_MDR4.length; patient++){

                if (patients_MDR4[patient][0] != null){

                    draw_MDR4(patients_MDR4[patient]);
                }
            }
        }else if (list == patients_PYH){

            PYH.removeAll();
            String[] cashier = {"Cashier PYH", " "};
            draw_PYH(cashier);


            for (int patient = 0; patient < patients_PYH.length; patient++){

                if (patients_PYH[patient][0] != null){

                    draw_PYH(patients_PYH[patient]);
                }
            }
        }
        else{
            System.out.println("Algo de errado não está certo...");
        }










    }

    public void draw_ETR1(String[] patient){

        ETR1.setLayout(new FlowLayout());
        ETR1.add(new JLabel(patient[0]));

        if (patient[1] == "Blue") {
            ETR1.add(new Circle(blue) );
        } else if (patient[1] == "Yellow") {
            ETR1.add(new Circle(yellow) );
        } else if (patient[1] == "Red") {
            ETR1.add(new Circle(red) );
        }else{
            ETR1.add(new Circle(gray) );
        }

    }

    public void draw_ETR2(String[] patient){

        ETR2.setLayout(new FlowLayout());
        ETR2.add(new JLabel(patient[0]));

        if (patient[1] == "Blue") {
            ETR2.add(new Rectangle(blue) );
        } else if (patient[1] == "Yellow") {
            ETR2.add(new Rectangle(yellow) );
        } else if (patient[1] == "Red") {
            ETR2.add(new Rectangle(red) );
        }else{
            ETR2.add(new Rectangle(gray) );
        }

    }

    public void draw_EVR1(String[] patient){

        EVR1.setLayout(new FlowLayout());
        EVR1.add(new JLabel(patient[0]));

        if (patient[0].charAt(0) == 'C') {

            if (patient[1] == "Blue") {
                EVR1.add(new Circle(blue));
            } else if (patient[1] == "Yellow") {
                EVR1.add(new Circle(yellow));
            } else if (patient[1] == "Red") {
                EVR1.add(new Circle(red));
            } else {
                EVR1.add(new Circle(gray));
            }
        }else if (patient[0].charAt(0) == 'A'){

            if (patient[1] == "Blue") {
                EVR1.add(new Rectangle(blue));
            } else if (patient[1] == "Yellow") {
                EVR1.add(new Rectangle(yellow));
            } else if (patient[1] == "Red") {
                EVR1.add(new Rectangle(red));
            } else {
                EVR1.add(new Rectangle(gray));
            }

        }else if (patient[0].charAt(0) == 'N'){
            EVR1.add(new Triangle(green));


        }

    }

    public void draw_EVR2(String[] patient){

        EVR2.setLayout(new FlowLayout());
        EVR2.add(new JLabel(patient[0]));

        if (patient[0].charAt(0) == 'C') {

            if (patient[1] == "Blue") {
                EVR2.add(new Circle(blue));
            } else if (patient[1] == "Yellow") {
                EVR2.add(new Circle(yellow));
            } else if (patient[1] == "Red") {
                EVR2.add(new Circle(red));
            } else {
                EVR2.add(new Circle(gray));
            }
        }else if (patient[0].charAt(0) == 'A'){

            if (patient[1] == "Blue") {
                EVR2.add(new Rectangle(blue));
            } else if (patient[1] == "Yellow") {
                EVR2.add(new Rectangle(yellow));
            } else if (patient[1] == "Red") {
                EVR2.add(new Rectangle(red));
            } else {
                EVR2.add(new Rectangle(gray));
            }

        }else if (patient[0].charAt(0) == 'N'){
            EVR2.add(new Triangle(green));


        }

    }

    public void draw_EVR3(String[] patient){

        EVR3.setLayout(new FlowLayout());
        EVR3.add(new JLabel(patient[0]));

        if (patient[0].charAt(0) == 'C') {

            if (patient[1] == "Blue") {
                EVR3.add(new Circle(blue));
            } else if (patient[1] == "Yellow") {
                EVR3.add(new Circle(yellow));
            } else if (patient[1] == "Red") {
                EVR3.add(new Circle(red));
            } else {
                EVR3.add(new Circle(gray));
            }
        }else if (patient[0].charAt(0) == 'A'){

            if (patient[1] == "Blue") {
                EVR3.add(new Rectangle(blue));
            } else if (patient[1] == "Yellow") {
                EVR3.add(new Rectangle(yellow));
            } else if (patient[1] == "Red") {
                EVR3.add(new Rectangle(red));
            } else {
                EVR3.add(new Rectangle(gray));
            }

        }else if (patient[0].charAt(0) == 'N'){
            EVR3.add(new Triangle(green));


        }

    }

    public void draw_EVR4(String[] patient){

        EVR4.setLayout(new FlowLayout());
        EVR4.add(new JLabel(patient[0]));

        if (patient[0].charAt(0) == 'C') {

            if (patient[1] == "Blue") {
                EVR4.add(new Circle(blue));
            } else if (patient[1] == "Yellow") {
                EVR4.add(new Circle(yellow));
            } else if (patient[1] == "Red") {
                EVR4.add(new Circle(red));
            } else {
                EVR4.add(new Circle(gray));
            }
        }else if (patient[0].charAt(0) == 'A'){

            if (patient[1] == "Blue") {
                EVR4.add(new Rectangle(blue));
            } else if (patient[1] == "Yellow") {
                EVR4.add(new Rectangle(yellow));
            } else if (patient[1] == "Red") {
                EVR4.add(new Rectangle(red));
            } else {
                EVR4.add(new Rectangle(gray));
            }

        }else if (patient[0].charAt(0) == 'N'){
            EVR4.add(new Triangle(green));


        }

    }

    public void draw_WTH1(String[] patient){

        WTH1.setLayout(new FlowLayout());
        WTH1.add(new JLabel(patient[0]));

        if (patient[1] == "Blue") {
            WTH1.add(new Circle(blue) );
        } else if (patient[1] == "Yellow") {
            WTH1.add(new Circle(yellow) );
        } else if (patient[1] == "Red") {
            WTH1.add(new Circle(red) );
        }else{
            WTH1.add(new Circle(gray) );
        }

    }

    public void draw_WTH2(String[] patient){

        WTH2.setLayout(new FlowLayout());
        WTH2.add(new JLabel(patient[0]));

        if (patient[1] == "Blue") {
            WTH2.add(new Rectangle(blue) );
        } else if (patient[1] == "Yellow") {
            WTH2.add(new Rectangle(yellow) );
        } else if (patient[1] == "Red") {
            WTH2.add(new Rectangle(red) );
        }else{
            WTH2.add(new Rectangle(gray) );
        }

    }

    public void draw_MDW(String[] patient){

        MDW.setLayout(new FlowLayout());
        MDW.add(new JLabel(patient[0]));

        if (patient[0].charAt(0) == 'C') {

            if (patient[1] == "Blue") {
                MDW.add(new Circle(blue));
            } else if (patient[1] == "Yellow") {
                MDW.add(new Circle(yellow));
            } else if (patient[1] == "Red") {
                MDW.add(new Circle(red));
            } else {
                MDW.add(new Circle(gray));
            }
        }else if (patient[0].charAt(0) == 'A'){

            if (patient[1] == "Blue") {
                MDW.add(new Rectangle(blue));
            } else if (patient[1] == "Yellow") {
                MDW.add(new Rectangle(yellow));
            } else if (patient[1] == "Red") {
                MDW.add(new Rectangle(red));
            } else {
                MDW.add(new Rectangle(gray));
            }

        }

    }

    public void draw_MDR1(String[] patient){

        MDR1.setLayout(new FlowLayout());
        MDR1.add(new JLabel(patient[0]));

        if (patient[0].charAt(0) == 'N') {

            if (patient[1] == "Blue") {
                MDR1.add(new Circle(blue));
            } else if (patient[1] == "Yellow") {
                MDR1.add(new Circle(yellow));
            } else if (patient[1] == "Red") {
                MDR1.add(new Circle(red));
            } else {
                MDR1.add(new Circle(gray));
            }

        } else if (patient[0].charAt(0) == 'D'){
            MDR1.add(new Triangle(cyan));


        }

    }

    public void draw_MDR2(String[] patient) {

        MDR2.setLayout(new FlowLayout());
        MDR2.add(new JLabel(patient[0]));

        if (patient[0].charAt(0) == 'N') {

            if (patient[1] == "Blue") {
                MDR2.add(new Circle(blue));
            } else if (patient[1] == "Yellow") {
                MDR2.add(new Circle(yellow));
            } else if (patient[1] == "Red") {
                MDR2.add(new Circle(red));
            } else {
                MDR2.add(new Circle(gray));
            }

        } else if (patient[0].charAt(0) == 'D') {
            MDR2.add(new Triangle(cyan));

        }
    }

    public void draw_MDR3(String[] patient) {

        MDR3.setLayout(new FlowLayout());
        MDR3.add(new JLabel(patient[0]));

        if (patient[0].charAt(0) == 'N') {

            if (patient[1] == "Blue") {
                MDR3.add(new Rectangle(blue));
            } else if (patient[1] == "Yellow") {
                MDR3.add(new Rectangle(yellow));
            } else if (patient[1] == "Red") {
                MDR3.add(new Rectangle(red));
            } else {
                MDR3.add(new Rectangle(gray));
            }

        } else if (patient[0].charAt(0) == 'D') {
            MDR3.add(new Triangle(cyan));

        }
    }

    public void draw_MDR4(String[] patient) {

        MDR4.setLayout(new FlowLayout());
        MDR4.add(new JLabel(patient[0]));

        if (patient[0].charAt(0) == 'N') {

            if (patient[1] == "Blue") {
                MDR4.add(new Rectangle(blue));
            } else if (patient[1] == "Yellow") {
                MDR4.add(new Rectangle(yellow));
            } else if (patient[1] == "Red") {
                MDR4.add(new Rectangle(red));
            } else {
                MDR4.add(new Rectangle(gray));
            }

        } else if (patient[0].charAt(0) == 'D') {
            MDR4.add(new Triangle(cyan));

        }
    }

    public void draw_PYH(String[] patient){

        PYH.setLayout(new FlowLayout());
        PYH.add(new JLabel(patient[0]));

        if (patient[0] == "Cashier PYH"){
            PYH.add(new Triangle(magenta));


        } else if (patient[0].charAt(0) == 'C') {

            if (patient[1] == "Blue") {
                PYH.add(new Circle(blue));
            } else if (patient[1] == "Yellow") {
                PYH.add(new Circle(yellow));
            } else if (patient[1] == "Red") {
                PYH.add(new Circle(red));
            } else {
                PYH.add(new Circle(gray));
            }
        }else if (patient[0].charAt(0) == 'A'){

            if (patient[1] == "Blue") {
                PYH.add(new Rectangle(blue));
            } else if (patient[1] == "Yellow") {
                PYH.add(new Rectangle(yellow));
            } else if (patient[1] == "Red") {
                PYH.add(new Rectangle(red));
            } else {
                PYH.add(new Rectangle(gray));
            }

        }

    }













    public static void main(String[] args) {
        HCP_GUI dialog = new HCP_GUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);




    }
}
