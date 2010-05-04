package rnd.mywt.client;

public class Logger {

	public static void log(String name, Object value) {
		String str = name + ":\t" + value;
		log(str);
	}

	public static void log(String str) {
		indent();
		System.err.println(str);
	}

	public static void startMethod(String scope, String className, String methodName) {
		startMethod(scope + "." + className, methodName);
	}

	public static void startMethod(String className, String methodName) {
		incrIndent();
		System.err.println("Start " + className + "." + methodName);
	}

	public static void endMethod(String scope, String className, String methodName) {
		endMethod(scope + "." + className, methodName);
	}

	public static void endMethod(String className, String methodName) {
		decrIndent();
		System.err.println("End " + className + "." + methodName);
	}

	// Private

	private static int indent;

	private static void incrIndent() {
		indent();
		indent++;
	}

	private static void decrIndent() {
		indent--;
		indent();
	}

	private static void indent() {
		for (int i = 0; i < indent; i++) {
			System.err.print("\t");
		}
	}

}
