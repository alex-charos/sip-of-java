public class MultipleIfClauses {
	public static void main(String[] args) {
		String input = args[0];
		if ("Liverpool".equals(input)) {
			System.out.println("Very good choice!");
		} else if ("Everton".equals(input)) {
			System.err.println("What were you thinking?!?!?!");
		} else if ("Man City".equals(input)) {
			System.err.println("When did you start following football?");
		} else {
			System.out.println("Meh...");
		}
	}
}