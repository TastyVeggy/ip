package yappy.exception;

import yappy.ui.CommandInfos.CommandInfo;

public class YappyInputException extends YappyException {
    public YappyInputException(CommandInfo commandUsage) {
        super("To " + commandUsage.action() + ", please have your input be of the following form:\n\n  " + commandUsage.usage());
    }
}
