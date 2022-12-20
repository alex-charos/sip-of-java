# Switch Expressions

Switch Expressions, added in Java 14 ([JEP 361](https://openjdk.java.net/jeps/361)) are a great way of handling evaluations that have _n_ paths like in this example: 

```java
String input = args[0];
if ("Liverpool".equals(input))) {
    System.out.println("Very good choice!");
} else if ("Everton".equals(input)) {
    System.err.println("What were you thinking?!?!?!");
} else if ("Man City".equals(input)) {
    System.err.println("When did you start following football?");
} else {
    System.out.println("Meh...");
}
```

The above can be expressed more succinctly, and less error prone, with a switch expression: 

```java
switch (args[0]) {
    case "Liverpool" -> System.out.println("Very good choice!");
    case "Everton" -> System.err.println("What were you thinking?!?!?!");
    case "Man City" -> System.out.println("When did you start following football?");
    default -> System.out.println("Meh..");
}
```