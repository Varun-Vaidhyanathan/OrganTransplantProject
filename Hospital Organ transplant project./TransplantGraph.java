/**
 * The TransplantGraph class contains an adjacency matrix
 * for the organ donors and recipients. There are ArrayLists used to
 * store donor and recipients in separate lists. This class implements
 * Serializable.
 *
 * @author Varun Vaidhyanathan
 **/

import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class TransplantGraph implements Serializable {
    private ArrayList<Patient> donors;
    private ArrayList<Patient> recipients;
    public static final int MAX_PATIENTS = 100;
    private boolean[][] connections;
    private static boolean donorFlag = true;
    private static boolean recipientFlag = true;

    /**
     * non parameterised constructor
     */
    public TransplantGraph() {
        donors = new ArrayList<>(MAX_PATIENTS);
        recipients = new ArrayList<>(MAX_PATIENTS);
        connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];
    }

    /**
     * Getter method for the list donors
     * @return ArrayList<Patient> the list of donors
     */
    public ArrayList<Patient> getDonors() {
        return donors;
    }

    /**
     * Getter method to get recipients
     * @return ArrayList<Patients> the list of recipients
     */
    public ArrayList<Patient> getRecipients() {
        return recipients;
    }

    /**
     * this method Creates and returns a new TransplantGraph object,
     * initialized with the donor information found in donorFile and the
     * recipient information found in recipientFile.
     *
     * @param donorFile the file with donors information
     * @param recipientFile the file with recipient information
     * @return TransplantGraph object
     * @throws InvalidBloodTypeException thrown when the blood type is invalid
     */
    public static TransplantGraph buildFromFiles(String donorFile, String recipientFile)
            throws InvalidBloodTypeException {
        //1, Pam Beesly, 28, Kidney, O
        TransplantGraph newObject = new TransplantGraph();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(donorFile);
        } catch (FileNotFoundException e) {
            System.out.println("The file is not found. ");
        }
        Scanner read = new Scanner(fis);
        donorFlag = false;
        while (read.hasNextLine()) {
            Patient readPatient = new Patient();
            String[] readLine = read.nextLine().split(", ");
            readPatient.setID(Integer.parseInt(readLine[0]));
            readPatient.setName(readLine[1]);
            readPatient.setAge(Integer.parseInt(readLine[2]));
            readPatient.setOrgan(readLine[3]);
            readPatient.getBloodType().setBloodType(readLine[4]);
            newObject.addDonor(readPatient);
        }
        FileInputStream fis1 = null;
        try {
            fis1 = new FileInputStream(recipientFile);
        } catch (FileNotFoundException e) {
            System.out.println("The file is not found. ");
        }
        Scanner read1 = new Scanner(fis1);
        recipientFlag = false;
        while (read1.hasNextLine()) {
            Patient readPatient = new Patient();
            String[] readLine = read1.nextLine().split(", ");
            readPatient.setID(Integer.parseInt(readLine[0]));
            readPatient.setName(readLine[1]);
            readPatient.setAge(Integer.parseInt(readLine[2]));
            readPatient.setOrgan(readLine[3]);
            readPatient.getBloodType().setBloodType(readLine[4]);
            newObject.addRecipient(readPatient);
        }
        for (int i = 0; i < newObject.recipients.size(); i++) {
            int numConnections = 0;
            for (int j = 0; j < newObject.donors.size(); j++) {
                if (newObject.connections[j][i])
                    numConnections++;
            }
            newObject.recipients.get(i).setNumConnections(numConnections);
        }
        for (int i = 0; i < newObject.donors.size(); i++) {
            int numConnections = 0;
            for (int j = 0; j < newObject.recipients.size(); j++) {
                if (newObject.connections[i][j])
                    numConnections++;
            }
            newObject.donors.get(i).setNumConnections(numConnections);
        }
        recipientFlag = true;
        donorFlag = true;
        return newObject;
    }

    /**
     * this method add recipients to the list and then makes
     * connections in the adjacency matrix.
     * @param patient the patient to be added
     * @throws InvalidBloodTypeException theown when the blood type of the patient is invalid
     */
    public void addRecipient(Patient patient) throws InvalidBloodTypeException {
        patient.setOrgan(Character.toUpperCase(patient.getOrgan().charAt(0)) + patient.getOrgan().substring(1));
        String bloodType = patient.getBloodType().getBloodType();
        if (!bloodType.equalsIgnoreCase("A") && !bloodType.equalsIgnoreCase("B")
                && !bloodType.equalsIgnoreCase("AB") &&
                !bloodType.equalsIgnoreCase("O"))
            throw new InvalidBloodTypeException("The blood type provided is invalid");
        int j = recipients.size();
        patient.setID(j);
        recipients.add(patient);
        for (int i = 0; i < donors.size(); i++) {
            if (BloodType.isCompatible(patient.getBloodType(), donors.get(i).getBloodType())) {
                if (donors.get(i).getOrgan().equalsIgnoreCase(patient.getOrgan()))
                    connections[i][j] = true;
                else
                    connections[i][j] = false;
            } else connections[i][j] = false;
        }
        if (recipientFlag) {
            System.out.println(patient.getName() + " is now on the organ transplant waitlist!");
        }
    }

    /**
     * this method adds a donor to the list and makes new connections
     * in the adjacency matrix
     * @param patient the patient to be added
     * @throws InvalidBloodTypeException thrown when the blood type of the patient is invalid
     */
    public void addDonor(Patient patient) throws InvalidBloodTypeException {
        patient.setOrgan(Character.toUpperCase(patient.getOrgan().charAt(0)) + patient.getOrgan().substring(1));
        String bloodType = patient.getBloodType().getBloodType();
        if (!bloodType.equalsIgnoreCase("A") && !bloodType.equalsIgnoreCase("B")
                && !bloodType.equalsIgnoreCase("AB") &&
                !bloodType.equalsIgnoreCase("O"))
            throw new InvalidBloodTypeException("The blood type provided is invalid");
        int k = donors.size();
        patient.setID(k);
        donors.add(patient);
        for (int i = 0; i < recipients.size(); i++) {
            if (BloodType.isCompatible(recipients.get(i).getBloodType(), patient.getBloodType())) {
                if (recipients.get(i).getOrgan().equalsIgnoreCase(patient.getOrgan()))
                    connections[k][i] = true;
                else
                    connections[k][i] = false;
            } else
                connections[k][i] = false;
        }
        if (donorFlag) {
            System.out.println("The organ donor with ID " + k + " was successfully added to the donor list!");
        }
    }

    /**
     * this method removes the recipient from the list and makes connections
     * with the updated information
     * @param name the name of the recipient to be removed
     * @throws PatientDoesntExistException thrown when there isnt a patient with such a name
     */
    public void removeRecipient(String name) throws PatientDoesntExistException {
        Patient removedRecipient = new Patient();
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).getName().equalsIgnoreCase(name)) {
                removedRecipient = recipients.remove(i);
                break;
            }
        }
        if (removedRecipient.getName().equals(""))
            throw new PatientDoesntExistException("There is no such recipient");
        System.out.println(removedRecipient.getName() + " was removed from the organ transplant waitlist.");
        redoConnections();
    }

    /**
     * remove donor from the list and make connections again.
     * @param name the donor to be removed
     * @throws PatientDoesntExistException thrown when the patient with the given name doesnt exist
     */
    public void removeDonor(String name) throws PatientDoesntExistException {
        Patient removedDonor = new Patient();
        for (int i = 0; i < donors.size(); i++) {
            if (donors.get(i).getName().equalsIgnoreCase(name)) {
                removedDonor = donors.remove(i);
                break;
            }
        }
        if (removedDonor.getName().equals(""))
            throw new PatientDoesntExistException("There is no such donor.");
        System.out.println(removedDonor.getName() + " was removed from the organ donor list.");
        redoConnections();
    }

    /**
     * this method makes the connections from the two lists in the adjacency matrix.
     * This method is called frequently as updates to the lists are made.
     */
    public void redoConnections() {
        connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];
        for (int i = 0; i < recipients.size(); i++) {
            for (int j = 0; j < donors.size(); j++) {
                if (BloodType.isCompatible(recipients.get(i).getBloodType(), donors.get(j).getBloodType())) {
                    if (recipients.get(i).getOrgan().equalsIgnoreCase(donors.get(j).getOrgan()))
                        connections[j][i] = true;
                    else
                        connections[j][i] = false;
                } else
                    connections[j][i] = false;
            }
        }
    }

    /**
     * this method prints all the recipients in the recipients list
     */
    public void printAllRecipients() {
        System.out.println("Index | Recipient Name     | Age | Organ Needed  | Blood Type | Donor IDs\n" +
                "========================================================================");
        String printRecipients = "";
        for (int i = 0; i < this.recipients.size(); i++) {
            printRecipients += this.recipients.get(i).toString();
            for (int j = 0; j < this.donors.size(); j++) {
                if (this.connections[j][i])
                    printRecipients += j + ", ";
            }
            if (printRecipients.charAt(printRecipients.length() - 1) == ' ') {
                printRecipients = printRecipients.substring(0, printRecipients.length() - 2);
            }
            printRecipients += "\n";
        }
        System.out.println(printRecipients);
    }

    /**
     * this method prints all the donors in the donors list
     */
    public void printAllDonors() {
        System.out.println("Index | Donor Name         | Age | Organ Donated | Blood Type | Recipient IDs\n" +
                "=============================================================================");
        String printDonors = "";
        for (int i = 0; i < this.donors.size(); i++) {
            printDonors += this.donors.get(i).toString();
            for (int j = 0; j < this.recipients.size(); j++) {
                if (this.connections[i][j])
                    printDonors += j + ", ";
            }
            if (printDonors.charAt(printDonors.length() - 1) == ' ') {
                printDonors = printDonors.substring(0, printDonors.length() - 2);
            }
            printDonors += "\n";
        }
        System.out.println(printDonors);
    }


}
