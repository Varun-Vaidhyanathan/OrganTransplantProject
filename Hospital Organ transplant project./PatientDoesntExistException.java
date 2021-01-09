
/**
 * The PatientDoesntException class extends the Exception class.
 * This exception is thrown when then the Patient being looked for
 * doesn't exist in the lists.
 *
 * @author Varun Vaidhyanathan
 **/
public class PatientDoesntExistException extends Exception {

    /**
     * Parameterised constructor the Exception constructor
     *
     * @param message the exception message to be displayed
     */
    public PatientDoesntExistException(String message) {
        super(message);
    }
}
