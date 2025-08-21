@FunctionalInterface
public interface YappyConsumer<T> {
	void accept(T t) throws YappyException;
}
