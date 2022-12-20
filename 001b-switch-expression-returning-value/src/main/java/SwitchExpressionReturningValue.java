public class SwitchExpressionReturningValue {
	public static void main(String[] args) {
		
		String result = switch (args[0]) {
			case "1" -> "Sunday";
			case "2" -> "Monday";
			case "3" -> "Tuesday";
			case "4" -> "Wednesday";
			case "5" -> "Thursday";
			case "6" -> "Friday";
			case "7" -> "Saturday";
			default -> "Invalid Choice";
		};
		
		System.out.println(result);
	}
}