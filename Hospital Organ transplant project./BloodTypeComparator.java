/**
 * This comparator class is used to sort the lists
 * by Blood Type of the patients.
 * This class implements the Comparator<Patient>
 *
 * @author Varun Vaidhyanathan
 * e-mail:varun.vaidhyanathan@stonybrook.edu
 * Stony Brook ID:112527969
 * Recitation: R03
 **/

import java.util.Comparator;

public class BloodTypeComparator implements Comparator<Patient> {

    /**
     * The compare method is implemented
     * @param o1 Patient object 1
     * @param o2 Patient object 2
     * @return int the result of the comparison
     */
    @Override
    public int compare(Patient o1, Patient o2) {
        return (o1.getBloodType().getBloodType().compareTo(o2.getBloodType().getBloodType()));
    }
}
