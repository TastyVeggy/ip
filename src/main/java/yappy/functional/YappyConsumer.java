package yappy.functional;

import yappy.exception.YappyException;

@FunctionalInterface
public interface YappyConsumer<T> {
	void accept(T t) throws YappyException;
}
