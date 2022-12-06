# Switch Expressions

### Exhaustiveness and enums 

A switch expression must be exhaustive. In most cases this will mean a `default` case must be defined for the expression. However in the below example using a `enum` a default case is not required as ever enum value has a case mapped to it:

```java
enum DaysOfWeek {
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY ;
}
	
public static void main(String[] args) {
	
	DaysOfWeek dayOfWeek = DaysOfWeek.SUNDAY;
	String result = switch (dayOfWeek) {
	case SUNDAY -> "Sunday";
	case MONDAY -> "Monday";
	case TUESDAY -> "Tuesday";
	case WEDNESDAY -> "Wednesday";
	case THURSDAY -> "Thursday";
	case FRIDAY -> "Friday";
	case SATURDAY -> "Saturday";
	};
	
	System.out.println(result);
}
```
