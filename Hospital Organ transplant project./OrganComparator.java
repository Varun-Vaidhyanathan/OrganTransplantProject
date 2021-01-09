/**
 * This comparator class is used to sort the lists
 * by the Organ the patient needs or is donating.
 * This class implements the Comparator<Patient>
 *
 * @author Varun Vaidhyanathan
 **/

import java.util.Comparator;

public class OrganComparator implements Comparator<Patient> {

    /**
     * The compare method is implemented
     * @param o1 Patient object 1
     * @param o2 Patient object 2
     * @return int the result of the comparison
     */
    @Override
    public int compare(Patient o1, Patient o2) {
        return (o1.getOrgan().compareTo(o2.getOrgan()));
    }
}
