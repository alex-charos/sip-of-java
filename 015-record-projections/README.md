# Using Records as Projections in JPA

Java Records, introduced in java 16, allow for the easy definition of transparent data carriers. For developers maintaining applications that rely on JPA, Records could be an excellent option for use a database projection. 

## Records Cannot be Entities

Records however can only be used as projections. Popular JPA implementations like Hibernate depend upon entities that have no argument constructors, non-final fields, setters, and non-final classes, for the creation of proxies, all of which are either discouraged or explicitly prevented by records. 

## Records with JPA

If you are using JPA directly in your application, there are several different ways to incorporate records into your data access layer. 

### Criteria Builder

Records can be used with `CriteriaBuilder`. Like in the example below:

```java
public List<AdvocateRecord> findAllWithCriteriaBuilder() {
	CriteriaBuilder cb = em.getCriteriaBuilder();
	CriteriaQuery<AdvocateRecord> cq 
		= cb.createQuery(AdvocateRecord.class);

	Root<AdvocateEntity> root = cq.from(AdvocateEntity.class);
	
	cq.select(cb.construct(
			AdvocateRecord.class, 
			root.get("id"), 
			root.get("fName"), 
			root.get("lName"),
			root.get("region"), 
			root.get("twitterFollowers")));
	
	TypedQuery<AdvocateRecord> q = em.createQuery(cq);
	return q.getResultList();
}
```

### Typed Query

Records can also be used with `TypedQuery`, the fully qualified constructor will need to be provided in the JPQL query. 

```java
public List<AdvocateNameRecord> 
	findAdvocateNamesByRegionTypedQuery(String region) {

	TypedQuery<AdvocateNameRecord> query = em.createQuery("""
			SELECT
			new com.bk.records.AdvocateNameRecord(a.fName, a.lName)
			FROM AdvocateEntity a
			WHERE region = :region
			""", AdvocateNameRecord.class);

	query.setParameter("region", region);

	return query.getResultList();
}
```

### Native Query

Records can also be used with `NativeQuery`. A mapping definition needs to be provided to handle the mapping of the fields from the query to the Record fields, like with `AdvocateNameRecordMapping` in the below example:

```java
public List<AdvocateNameRecord> 
	findAdvocateNamesByIdNativeQuery(int id) {
	Query query = em.createNativeQuery("""
			SELECT
			f_name, l_name
			FROM advocates
			WHERE id = :id
			""", 
			"AdvocateNameRecordMapping");

	query.setParameter("id", id);
	return query.getResultList();
}
```

#### Mapping Definition:

The mapping `AdvocateNameRecordMapping` is defined in the `AdvocateEntity` entity class:

```java
@Entity
@Table(name = "advocates")
@SqlResultSetMapping(
        name = "AdvocateNameRecordMapping",
        classes = @ConstructorResult(
            targetClass = AdvocateNameRecord.class,
            columns = { 
                        @ColumnResult(name = "f_name"), 
                        @ColumnResult(name = "l_name")}))
public class AdvocateEntity {
...
```

## Records with Spring Data

Records are also supported by Spring Data, if you are using JPA this way in your application. Like with using JPA directly, there are several ways to use Records when using Spring Data.

### Automatic Mapping

Spring Data can handle the mapping of the return of query automatically if the components of the Record match the fields of the tracked entity like in the below example:

```java
public interface AdvocateRepo 
	extends CrudRepository<AdvocateEntity, Integer> {
	
	Iterable<AdvocateRecord> findByRegion(String region);
}
```

#### Record:

Here the components Record `AdvocateRecord` match the fields of the `@Entity` class `AdvocateEntity`:

```java
public record AdvocateRecord(
int id, 
String fName, 
String lName, 
String region, 
int twitterFollowers) {}
```

#### Tracked Entity:
```
public class AdvocateEntity {
	@Id
	private int id;
	private String fName;
	private String lName;
	private String region;
	private int twitterFollowers;
...
```

### Query

Spring Data also allows JPQL queries to be provided in `@Query`: 

```java
public interface AdvocateRepo 
	extends CrudRepository<AdvocateEntity, Integer> {
	@Query("""
	       SELECT 
	       new com.bk.records.AdvocateNameRecord(a.fName, a.lName)
	       FROM AdvocateEntity a
	       WHERE region = ?1
	       """)
	Iterable<AdvocateNameRecord> findNamesByRegion(String region);
}
```

### Customized Repo Implementation

Spring Data also supports [customized repo implementations](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations), which can also be used for handling the mapping of a query return to a Record class. To use a customized repo implementation, defined an interface:  

```java
public interface CustomAdvocateRepo {
	Iterable<AdvocateNameRecord> findAllNameRecords();
}
```

Add the interface to the extends of the Spring Data repo:

```java
public interface AdvocateRepo 
	extends CrudRepository<AdvocateEntity, Integer>,
	 CustomAdvocateRepo {
}
```
 
And provide the implementation of the repo. In this example a `RowMapper` is being used to handle the mapping of the query results:
 
```java
public class CustomAdvocateRepoImpl implements CustomAdvocateRepo {
	private JdbcTemplate jdbcTemplate;

	protected CustomAdvocateRepoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	class AdvocateRecordDtoRowMapper 
	implements RowMapper<AdvocateNameRecord> {
		@Override
		public AdvocateNameRecord 
		mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new AdvocateNameRecord(
					rs.getString("f_name"), rs.getString("l_name"));
		}
	}

	@Override
	public Iterable<AdvocateNameRecord> findAllNameRecords() {
		return jdbcTemplate.query(
		"SELECT f_name, l_name FROM advocates", 
			new AdvocateRecordDtoRowMapper());

	}
}
```

## Further Reading

* Java Records – How to use them with Hibernate and JPA, by Thorben Janssen: [https://thorben-janssen.com/java-records-hibernate-jpa/](https://thorben-janssen.com/java-records-hibernate-jpa/)
* The best way to use Java Records with JPA and Hibernate, by Vlad Mihalcea: [https://vladmihalcea.com/java-records-jpa-hibernate/](https://vladmihalcea.com/java-records-jpa-hibernate/)
* Records JEP: [https://openjdk.java.net/jeps/395](https://openjdk.java.net/jeps/395)

Happy Coding!