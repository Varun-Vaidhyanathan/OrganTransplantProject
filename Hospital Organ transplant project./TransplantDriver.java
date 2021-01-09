/**
 * The TransplantDriver class acts as the driver class for the whole
 * program. The static main method callse all the necessary methods.
 *
 * @author Varun Vaidhyanathan
 **/

import java.io.*;
import java.util.*;

public class TransplantDriver {
    public static final String DONOR_FILE = "donors.txt";
    public static final String RECIPIENT_FILE = "recipients.txt";

    /**
     * the static main method drives the program. First an existing object it tried to
     * load data from and if it doesn't exist, a new object is made and
     * data from the given files is read using a method in TransplantGraph class. User input
     * is taken using the Scanner class and appropriate methods are called.
     *
     * @param args
     */
    public static void main(String[] args) {
        TransplantGraph tg = loadObject();
        if (tg.getDonors().size() == 0) {
            System.out.println("Loading data from 'donors.txt'...\n" +
                    "Loading data from 'recipients.txt'...");
            try {
                tg = TransplantGraph.buildFromFiles(DONOR_FILE, RECIPIENT_FILE);
            } catch (InvalidBloodTypeException e) {
                e.getMessage();
            }
        }
        Scanner obj = new Scanner(System.in);
        String choice = "";
        System.out.println();

        System.out.println("Menu:\n" +
                "    (LR) - List all recipients\n" +
                "    (LO) - List all donors\n" +
                "    (AO) - Add new donor\n" +
                "    (AR) - Add new recipient\n" +
                "    (RO) - Remove donor\n" +
                "    (RR) - Remove recipient\n" +
                "    (SR) - Sort recipients\n" +
                "    (SO) - Sort donors\n" +
                "    (Q) - Quit");
        System.out.println();
        do {
            System.out.println();
            System.out.print("Please select an option: ");
            choice = obj.nextLine();
            System.out.println();
            boolean flag = true;
            for (int i = 0; i < choice.length(); i++) {
                if (!Character.isLetter(choice.charAt(i)))
                    flag = false;
            }
            if (flag)
                choice = choice.toUpperCase();
            switch (choice) {
                case "LR":
                    tg.printAllRecipients();
                    break;
                case "LO":
                    tg.printAllDonors();
                    break;
                case "AO":
                    System.out.print("Please enter the organ donor name: ");
                    String name = obj.nextLine();
                    System.out.print("Please enter the organs " + name + " is donating: ");
                    String organ = obj.nextLine();
                    organ = Character.toUpperCase(organ.charAt(0)) + organ.substring(1);
                    System.out.print("Please enter the blood type of " + name + ": ");
                    String bloodType = obj.next().toUpperCase();
                    bloodType = bloodType.toUpperCase();
                    obj.nextLine();
                    System.out.print("Please enter the age of " + name + " : ");
                    try {
                        int age = obj.nextInt();
                        obj.nextLine();
                        Patient newPatient = new Patient();
                        newPatient.setName(name);
                        newPatient.setOrgan(organ);
                        BloodType bT = new BloodType();
                        bT.setBloodType(bloodType);
                        newPatient.setBloodType(bT);
                        newPatient.setAge(age);
                        System.out.println();
                        tg.addDonor(newPatient);
                    } catch (InvalidBloodTypeException e) {
                        e.getMessage();
                    } catch (InputMismatchException e) {
                        System.out.println("Age has to be an integer.");
                        obj.nextLine();
                    }
                    break;
                case "AR":
                    System.out.print("Please enter the recipient's name: ");
                    String name1 = obj.nextLine();
                    int age = 0;
                    String bloodType1 = "";
                    try {
                        System.out.print("Please enter the recipient's blood type: ");
                        bloodType1 = obj.next();
                        bloodType1 = bloodType1.toUpperCase();
                        obj.nextLine();
                        System.out.print("Please enter the recipient's age: ");
                        age = obj.nextInt();
                        obj.nextLine();
                    } catch (InputMismatchException E) {
                        System.out.println("Age must be an integer.");
                        obj.nextLine();
                    }
                    System.out.print("Please enter the organ needed: ");
                    String organ1 = obj.nextLine();
                    Patient newPatient = new Patient();
                    newPatient.setName(name1);
                    newPatient.setOrgan(organ1);
                    BloodType bT = new BloodType();
                    bT.setBloodType(bloodType1);
                    newPatient.setBloodType(bT);
                    newPatient.setAge(age);
                    try {
                        System.out.println();
                        tg.addRecipient(newPatient);
                    } catch (InvalidBloodTypeException e) {
                        e.getMessage();
                    }
                    break;
                case "RO":
                    System.out.print("Please enter the name of the organ donor to remove: ");
                    String name2 = obj.nextLine();
                    try {
                        System.out.println();
                        tg.removeDonor(name2);
                    } catch (PatientDoesntExistException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "RR":
                    System.out.print("Please enter the name of the recipient to remove: ");
                    String name3 = obj.nextLine();
                    try {
                        System.out.println();
                        tg.removeRecipient(name3);
                    } catch (PatientDoesntExistException e) {
                        e.getMessage();
                    }
                    break;
                case "SR":
                    String ch;
                    do {
                        System.out.println(
                                "    (I) Sort by ID\n" +
                                        "    (N) Sort by Number of Donors\n" +
                                        "    (B) Sort by Blood Type\n" +
                                        "    (O) Sort by Organ Needed\n" +
                                        "    (Q) Back to Main Menu");
                        System.out.println();
                        System.out.print("Please select an option: ");
                        ch = obj.nextLine();
                        System.out.println();
                        boolean flag1 = true;
                        for (int i = 0; i < ch.length(); i++) {
                            if (!Character.isLetter(ch.charAt(i)))
                                flag1 = false;
                        }
                        if (flag1)
                            ch = ch.toUpperCase();
                        switch (ch) {
                            case "I":
                                Collections.sort(tg.getRecipients(), new IDComparator());
                                tg.redoConnections();
                                tg.printAllRecipients();
                                break;
                            case "N":
                                Collections.sort(tg.getRecipients(), new NumConnectionsComparator());
                                tg.redoConnections();
                                tg.printAllRecipients();
                                break;
                            case "B":
                                Collections.sort(tg.getRecipients(), new BloodTypeComparator());
                                tg.redoConnections();
                                tg.printAllRecipients();
                                break;
                            case "O":
                                Collections.sort(tg.getRecipients(), new OrganComparator());
                                tg.redoConnections();
                                tg.printAllRecipients();
                                break;
                            case "Q":
                                Collections.sort(tg.getRecipients(), new IDComparator());
                                tg.redoConnections();
                                tg.printAllRecipients();
                                System.out.println("Returning to main menu. ");
                                break;
                            default:
                                System.out.println("Please enter a valid option.");
                                break;
                        }

                    } while (!ch.equalsIgnoreCase("Q"));
                    break;
                case "SO":
                    String ch1;
                    do {
                        System.out.println(
                                "    (I) Sort by ID\n" +
                                        "    (N) Sort by Number of Recipients\n" +
                                        "    (B) Sort by Blood Type\n" +
                                        "    (O) Sort by Organ Donated\n" +
                                        "    (Q) Back to Main Menu");
                        System.out.println();
                        System.out.print("Please select an option: ");
                        ch1 = obj.nextLine();
                        System.out.println();
                        boolean flag1 = true;
                        for (int i = 0; i < ch1.length(); i++) {
                            if (!Character.isLetter(ch1.charAt(i)))
                                flag1 = false;
                        }
                        if (flag1)
                            ch1 = ch1.toUpperCase();
                        switch (ch1) {
                            case "I":
                                Collections.sort(tg.getDonors(), new IDComparator());
                                tg.redoConnections();
                                tg.printAllDonors();
                                break;
                            case "N":
                                Collections.sort(tg.getDonors(), new NumConnectionsComparator());
                                tg.redoConnections();
                                tg.printAllDonors();
                                break;
                            case "B":
                                Collections.sort(tg.getDonors(), new BloodTypeComparator());
                                tg.redoConnections();
                                tg.printAllDonors();
                                break;
                            case "O":
                                Collections.sort(tg.getDonors(), new OrganComparator());
                                tg.redoConnections();
                                tg.printAllDonors();
                                break;
                            case "Q":
                                Collections.sort(tg.getDonors(), new IDComparator());
                                tg.redoConnections();
                                tg.printAllDonors();
                                System.out.println("Returning to main menu. ");
                                break;
                            default:
                                System.out.println("Please enter a valid option.");
                                break;
                        }

                    } while (!ch1.equalsIgnoreCase("Q"));
                    break;
                case "Q":
                    saveTransplantGraph(tg);
                    System.out.println();
                    System.out.println("Program terminating normally...");
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    break;
            }
        } while (!choice.equalsIgnoreCase("Q"));
    }

    /**
     * This method saves the current object so that it can be loaded later
     * @param tg the TransplantGraph object
     */
    public static void saveTransplantGraph(TransplantGraph tg) {
        FileOutputStream file = null;

        try {
            file = new FileOutputStream("transplant.obj");
            ObjectOutputStream fout = new ObjectOutputStream(file);

            fout.writeObject(tg); // Here "objToWrite" is the object to serialize
            fout.close();
            System.out.println("Writing data to transplant.obj...");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to create transplant.obj");
        } catch (IOException e) {
            System.out.println("Unable to save to transplant.obj");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to load the previously saved data.
     * @return TransplantGraph the object saved earlier
     */
    public static TransplantGraph loadObject() {
        TransplantGraph tg = null;
        try {
            FileInputStream file = new FileInputStream("transplant.obj");
            ObjectInputStream fin = new ObjectInputStream(file);
            tg = (TransplantGraph) fin.readObject();
            fin.close();
            System.out.println("Loading data from transplant.obj... ");
        } catch (IOException e) {
            // This exception is thrown if transplant.obj does not exist.
            // Since there is nothing to load, here you should just create a new object, instead.
            System.out.println("transplant.obj does not exist. Creating new TransplantGraph object...");
            tg = new TransplantGraph();
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load transplant.obj : Invalid class encountered");
            tg = new TransplantGraph();
        }
        return tg;
    }
}
