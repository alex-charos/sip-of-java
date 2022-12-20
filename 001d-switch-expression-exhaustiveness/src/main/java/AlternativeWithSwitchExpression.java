public class AlternativeWithSwitchExpression {
  public static void main(String[] args) {
    switch (args[0]) {
      case "Liverpool" -> System.out.println("Very good choice!");
      case "Everton" -> System.err.println("What were you thinking?!?!?!");
      case "Man City" -> System.out.println("When did you start following football?");
      default -> System.out.println("Meh..");
    }
  }


}
