package rnd.mywt.client.bean;

public class ValueVetoException extends RuntimeException {

	public ValueVetoException(String message) {
		super(message);
	}

	public ValueVetoException(String message, Throwable cause) {
		super(message, cause);
	}

}