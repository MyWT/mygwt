package rnd.mywt.client;

public interface Context {

	Object getContext(String key);

	Object setContext(String key, Object value);

}
