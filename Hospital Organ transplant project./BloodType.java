/**
 * The BloodType class contains a String member variable
 * that denotes bloodType. Serializable is implemented
 * by this class
 *
 * @author Varun Vaidhyanathan
 **/

import java.io.Serializable;

public class BloodType implements Serializable {
    private String bloodType;

    /**
     * non parameterised constructor
     */
    public BloodType() {
        bloodType = "";
    }

    /**
     * Getter method for bloodType
     * @return String the blood type of the patient
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Setter method for blood type of the patient
     * @param bloodType the blood type of the patient
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * this method checks if the blood types of the donor and the recipient
     * are compatible.
     * @param recipient the recipient's BloodType object
     * @param donor the donor's BloodType object
     * @return boolean whether the blood type is compatible or not
     */
    public static boolean isCompatible(BloodType recipient, BloodType donor) {
        if (donor.getBloodType().equalsIgnoreCase("O"))
            return true;
        else if (recipient.getBloodType().equalsIgnoreCase(donor.getBloodType()))
            return true;
        else if (recipient.getBloodType().equalsIgnoreCase("AB"))
            return true;
        else
            return false;
    }


}
