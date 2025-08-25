package yappy.exception;
public class YappyException extends Exception {
	public YappyException(String message) {
		super("Yappy cannot yap!!! " + message);
	}
}
