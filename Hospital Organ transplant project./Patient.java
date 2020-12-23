/**
 * The Patient class represents an active organ donor or recipient in the database.
 * Comparable and Serializable interfaces are implemented by this class
 *
 * @author Varun Vaidhyanathan
 * e-mail:varun.vaidhyanathan@stonybrook.edu
 * Stony Brook ID:112527969
 * Recitation: R03
 **/

import java.io.Serializable;

public class Patient implements Serializable, Comparable {
    private String name;
    private String organ;
    private int age;
    private BloodType bloodType;
    private int ID;
    private boolean isDonor;
    private int numConnections;

    /**
     * non parameterised constructor
     */
    public Patient() {
        name = "";
        organ = "";
        age = 0;
        bloodType = new BloodType();
        ID = -1;
        isDonor = false;
    }

    /**
     * Getter method for the number of connections
     *
     * @return int the number of connections
     */
    public int getNumConnections() {
        return numConnections;
    }

    /**
     * Setter method for the number of connections
     *
     * @param numConnections the number of connections
     */
    public void setNumConnections(int numConnections) {
        this.numConnections = numConnections;
    }

    /**
     * getter method for the name of the patient
     *
     * @return String the name of the patient
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for the name of the patient
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the organ the patient is donating or receiving
     *
     * @return String the organ
     */
    public String getOrgan() {
        return organ;
    }

    /**
     * Setter method for the organ
     *
     * @param organ the organ
     */
    public void setOrgan(String organ) {
        this.organ = organ;
    }

    /**
     * Getter method for the age of the patient
     *
     * @return int age
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter method for the age of a patient
     *
     * @param age int
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Getter method for bloodType
     *
     * @return BloodType
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Setter method for bloodType
     *
     * @param bloodType the bloodType object
     */
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Getter method for ID
     *
     * @return int the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Setter method for ID
     *
     * @param ID the ID of the patient
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Getter method for isDonor
     *
     * @return boolean the value of isDonor(isDonor itself)
     */
    public boolean isDonor() {
        return isDonor;
    }

    /**
     * Setter method for isDonor
     *
     * @param donor whether the patient is a donor or not
     */
    public void setDonor(boolean donor) {
        isDonor = donor;
    }

    /**
     * compares the IDs of two Patient objects. Implementation
     * for the Comparable interface
     *
     * @param o the Patient object to be compared
     * @return 1, 0 or -1 depending on the result of the comparison
     */
    public int compareTo(Object o) {
        if (this.getID() == ((Patient) o).getID()) {
            return 0;
        } else if (this.getID() > ((Patient) o).getID())
            return 1;
        else
            return -1;
    }

    /**
     * The string representation of the patient object
     *
     * @return String the string representation
     */
    public String toString() {
        //0  | Kevin Malone       | 45  |  Heart        |      O     |
        String out = "";
        out += String.format("%6d", this.getID()) + "|";
        out += String.format("%20s", this.getName()) + "|";
        out += String.format("%5d", this.getAge()) + "|";
        out += String.format("%15s", this.getOrgan()) + "|";
        out += String.format("%12s", this.getBloodType().getBloodType()) + "|";
        return out;
    }
}
