# Switch Expressions

Switch Expressions, added in Java 14 ([JEP 361](https://openjdk.java.net/jeps/361)) are a great way of handling evaluations that have _n_ paths like in this example: 

```java
String input = args[0];
if (input.equals("1")) {
	System.out.println("Monday");
} else if (input.equals("2")) {
	System.out.println("Tuesday");
} else if (input.equals("3")) {
	System.out.println("Wednesday");
} else if (input.equals("4")) {
	System.out.println("Thursday");
} else if (input.equals("5")) {
	System.out.println("Friday");
} else if (input.equals("6")) {
	System.out.println("Saturday");
} else if (input.equals("7")) {
	System.out.println("Sunday");
} else {
	System.out.println("Invalid selection, valid choices 1-7");
}
```

The above can be expressed more succinctly, and less error prone, with a switch expression: 

```java
switch (args[0]) {
case "1" -> System.out.println("Sunday");
case "2" -> System.out.println("Monday");
case "3" -> System.out.println("Tuesday");
case "4" -> System.out.println("Wednesday");
case "5" -> System.out.println("Thursday");
case "6" -> System.out.println("Friday");
case "7" -> System.out.println("Saturday");
default -> System.out.println("Invalid selection, valid choices 1-7");
}
```