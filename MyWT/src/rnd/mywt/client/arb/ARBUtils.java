package rnd.mywt.client.arb;

public class ARBUtils {

	private static ARBAsync arb;

	public static ARBAsync getARB() {
		return arb;
	}

	public static void setARB(ARBAsync arb) {
		ARBUtils.arb = arb;
	}

}
