package yappy.exception;
public class YappyInputException extends YappyException {
    public YappyInputException(String action, String desiredInputFormat) {
        super("To " + action + ", please have your input be of the following form:\n\n  " + desiredInputFormat);
    }
}
