/**
 * This comparator class is used to sort the lists
 * by patient ID. This class implements the Comparator<Patient>
 *
 * @author Varun Vaidhyanathan
 * e-mail:varun.vaidhyanathan@stonybrook.edu
 * Stony Brook ID:112527969
 * Recitation: R03
 **/

import java.util.Comparator;

public class IDComparator implements Comparator<Patient> {

    /**
     * implemented compare method
     * @param o1 Patient object 1
     * @param o2 Patient object 2
     * @return int the appropriate result of the comparison
     */
    public int compare(Patient o1, Patient o2) {
        Patient e1 = (Patient) o1;
        Patient e2 = (Patient) o2;
        if (e1.getID() == e2.getID())
            return 0;
        else if (e1.getID() > e2.getID())
            return 1;
        else
            return -1;
    }

}
