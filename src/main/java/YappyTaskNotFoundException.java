public class YappyTaskNotFoundException extends YappyException {
    public YappyTaskNotFoundException(int taskIndex) {
        super("Task " + taskIndex + " does not exist. Use `list` to find a valid task");
    }
}
