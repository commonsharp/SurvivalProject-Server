package log;

public class Log {
	public static void log(String s) {
		System.out.println(s);
	}

	public static void error(String s) {
		System.out.println("Error: " + s);
		System.err.println("Error: " + s);
	}
}
