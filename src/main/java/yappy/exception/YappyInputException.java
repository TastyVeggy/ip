package yappy.exception;

import yappy.ui.CommandInfos.CommandInfo;

/**
 * Represents an exception thrown to indicate a user input error in the Yappy application.
 *
 * It includes the proper usage of the associated command.
 */
public class YappyInputException extends YappyException {

    /**
     * Creates a YappyInputException with proper usage of the associated command.
     *
     * @param commandUsage Record containing information about the associated command.
     */
    public YappyInputException(CommandInfo commandUsage) {
        super("To " + commandUsage.action()
                + ", please have your input be of the following form:\n\n  "
                + commandUsage.usage());
    }
}
