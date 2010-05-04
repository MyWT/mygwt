package rnd.mywt.client.utils;

public class ExceptionUtils {

	public static Object makeUnchecked(Block block) {
		try {
			return block.execute();
		}
		catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
