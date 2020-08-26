# Introduction

As it has been mentioned before, the project has been divided into front and 
back modules. This is the explanation for the back-end part of the project.

### Auto Re-run

During the development of an application it is quite common to be in a 
situation where part of the code has been changed/adapted, and that means that
the application needs to be re-executed in order for the changes to appear.

To do that, the dependency `Spring Boot DevTools` can be included. Adding the
following syntax to the `pom.xml` file allows to auto-update the application
and reflect the last changes made to it.

```xml
<dependency> <!-- Get file changes during runtime -->
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
	<scope>runtime</scope>
	<optional>true</optional>
</dependency>
```

### Logging

In order to track the execution of the different components of the back-end 
module, a log has been included into the project. 

In Spring Boot it is usually used the `log4j2` logging system. It is supposed 
to be an [improved](https://logging.apache.org/log4j/2.x/) `log4j` - mainly
because it fixes some inherent problems in the LogBack's architecture.

In order to work with it, a dependency must be included into the `pom.xml` 
file:

```xml
<dependency> <!-- Logging -->
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

Besides that, a configuration file, by the name of `log4j2.xml` must be 
created. It is recommended to place the file under the folder
`src/main/resources`. Following the instructions from 
[the official documentation]((https://logging.apache.org/log4j/2.x/manual/configuration.html)) 
the configuration file in this project would be like this:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN" monitorInterval="30">
    <Properties>
        <Property name="LOG_PATTERN">
            %d{yyyy-MM-dd HH:mm:ss.SSS} %5p ${hostName} --- [%15.15t] %-40.40c{1.} : %m%n%ex
        </Property>
    </Properties>
    <Appenders>
        <Console name="ConsoleAppender" target="SYSTEM_OUT" follow="true">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="com.rainforest.eco" level="debug" additivity="false">
            <AppenderRef ref="ConsoleAppender" />
        </Logger>

        <Root level="info">
            <AppenderRef ref="ConsoleAppender" />
        </Root>
    </Loggers>
    
    <!-- Rolling File Appender -->
	<RollingFile name="FileAppender" fileName="logs/eco.log" 
             	 filePattern="logs/eco-%d{yyyy-MM-dd}-%i.log">
	    <PatternLayout>
	        <Pattern>${LOG_PATTERN}</Pattern>
	    </PatternLayout>
	    <Policies>
	        <SizeBasedTriggeringPolicy size="10MB" />
	    </Policies>
	    <DefaultRolloverStrategy max="10"/>
	</RollingFile>
    
</Configuration>
```

_Disclaimer:_ It might occur that no logging file is created. The messages 
might appear on the terminal after running the Java Application.

### Back-end REST API

The part of the [RESTful API](https://restfulapi.net/) that handles the 
requests and performs the necessary operations is the back-end REST API.

What it does is, it uses the port opened by the Spring Boot Application to 
redirect the requests sent to specific URLs and give the result of the 
corresponding operation as _Response_.

It is basically a 
[JPA](https://www.infoworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html),
which has:
- `Model`: representation of each collection in MongoDB - sub-documents are 
represented as referenced objects.
- `Repository`: it is what connects the code with the database. Uses `model`s 
to know the "shape" of the collection. Provides default operations to perform 
against the database in exchange.
- `Controller`: the logic of the requests is hidden in this layer. Its task is 
to execute the necessary functions to retrieve the data, manipulate it and 
return the desired result.

![](https://github.com/laurapm/UBICUA/tree/master/webpage/eco-webpage/images/SpringRestApi.jpg)

## DIY

To create a Spring Boot project on your own, you need to follow the next steps:

1. Go to [spring initializr](https://start.spring.io/) (yes, it is written that 
way) and select what you need. In this project's case:
  - **Project**: `Maven Project`
    - It is how dependencies are downloaded and imported to the project.
  - **Language**: `Java`
    - The development language of the project.
  - **Spring Boot**: `2.3.3`
  - **Project Metadata**:
    - **Group**: `com.rainforest`
    - **Artifact**: `eco`
    - **Name**: `eco`
    - **Description**: `Ecø smart pot. A Tamagotchi in real life.` 
    - **Package name**: `com.rainforest.eco`
    - **Packaging**: `Jar`
    - **Java**: `8`
  - **Dependencies**: Search for:
    - `Spring Web`: Build web, using RESTful, applications using Spring MVC.
    - `Spring Data MongoDB`: Store data using MongoDB.
    - `Spring Boot DevTools`: Allows to configure application to auto re-run 
    itself when a file is changed.
2. Include the dependencies that need to be added manually: `log4j2` and their
respective configuration (can find it on this same document, on the previous 
sections).
3. Create the REST API. For more information of the code, please refer to the 
**Code** section of this document. In order to do that:
  - Create a `model` for each collection in the database.
  - Generate as many `repository` as needed, taking into account each 
  `repository` refers to a particular model:
    - It is an `interface`, not a class.
    - It `extends` from `CrudRepository<T, S>` - where `T` is the `model` it
    refers to; and `S` is usually of type `String`.
  - Develop the `controller`s to redirect the requests to the appropriate 
  function. It uses the methods given by the `repository` to achieve the 
  desired response. 

## Code

The back-end logic can easily be divided into two separate parts. The first 
part is what it is necessary to create the RESTful API. The other part of the
code belongs to threads that analyze the existing data and produce a certain
response to it depending on the state of the environment (environment as the 
state of the plant).

### API

To create the API, it should be followed the model: 
`model-repository-controller` explained before.

To create the **model**, the annotation 
`@Document(collection="<collectionName>)"`. Inside the class, so that the API 
automatically recognizes the attribute corresponding to the `_id` field in the 
collection, it is marked with the annotation `@Id`. 

For the **repository**, it is created an `interface` with the annotation
`@Repository`. It also extends from `CrudRepository<<Model>, String>` to 
count with some implemented 
[some operations](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html). 
It could also extend from `MongoRepository`, but it
was more appropriate the first on due to the operations it allows. One of the 
advantages it brings is the pre-implemented functions, but it also allows to
create custom functions to, for example, search by a specific field.

Regarding the **controller** part of the API, they start using the content of
the following snippet. 

```java
@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
```

The annotations indicate that the class is a `Controller`, and therefore 
allowing _classpath_ scanning to get the rest of the necessary components. 
Next, the `EnableAutoConfiguration` auto-configures beans that the class is
likely to need. Finally, the `RequestMapping` on top of the class includes 
the indicated string at the end of the URL used to access the API. By 
convention, it is simple added `/api`. Others also include the version of the
API, for compatibility reasons.

Inside the class, the _repositories_ that are going to be used to call the 
functions are called with the `@Autowired` annotation on top, to automatically
link constructors, functions, etc.

On top of each function, two other annotations are added.

```java
@RequestMapping(value="/devices", method=RequestMethod.POST)
@ResponseBody
```

The first annotation adds the `value` string at the end of the URL when calling 
the API, it is a way to differentiate requests to the same methods in the same 
API. The `method` value indicates what type of method is called when the 
function is executed. In this project four have been used: `POST`, `GET`, `PUT`
and `DELETE`. 

The other annotation is VERY important. Do not make the same mistakes that we 
did. 
[Spending](https://bezkoder.com/spring-boot-jwt-auth-mongodb/) 
[4 days](https://www.baeldung.com/spring-cors) 
[debugging](https://spring.io/guides/gs/rest-service-cors/) 
[the API](https://spring.io/blog/2015/06/08/cors-support-in-spring-framework) 
[and](https://stackoverflow.com/questions/36968963/how-to-configure-cors-in-a-spring-boot-spring-security-application) 
[trying](https://www.onlinetutorialspoint.com/spring-boot/how-to-enable-spring-boot-cors-example-crossorigin.html) 
[every tutorial](https://medium.com/@gtommee97/rest-api-java-spring-boot-and-mongodb-4dffbcabbaf5) 
[available](https://www.codementor.io/@gtommee97/rest-api-java-spring-boot-and-mongodb-j7nluip8d) 
[is not a very pleasant](https://www.javaguides.net/2019/12/spring-boot-mongodb-crud-example-tutorial.html) 
[thing to do](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin). 
The only solution that we found was starting from a simplistic
[HelloWorld Application](https://dzone.com/articles/hello-world-program-spring-boot) 
and then include the project's requirements one by one. The `@RequestBody` is 
used to bind the HTTP response body with a domain object in the return type.

Each collection (except for the model version that should not be affected by 
any outer actor) has a _controller_ with the following functions:

#### Create

It creates a new document in the corresponding collection. Most of the 
collections do not even need to check certain conditions before creating a new 
document.

This is not the case of, for example, `user` collection. That is because no 
_user_ can be created if the username or the email of the new user are 
currently being used.

The structure of each function goes as follows:

```java
@RequestMapping(value="/<api_direction>", method=RequestMethod.POST)
@ResponseBody
public ResponseEntity<Model> createDocument(@RequestBody Model document)
{
	String LogHeader = "[/<api_direction>: createDocument] ";
	
	try {
		Log.logger.info(LogHeader + "Requested");
		
		Model _document = modelRepository.save(
			new Model(
				document.getSomeField(),
				document.getSomeOtherField(),
				document.getSomeThisField(),
				document.getDescriptionField()
			)
		);
			
		Log.logger.info(LogHeader + "Successful");
		return new ResponseEntity<>(_document, HttpStatus.OK);
			
	} catch (Exception e) {
		Log.logger.error(LogHeader + "some error ocurred: " + e);
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
```

#### Get All

Retrieves all the documents in a collection. No filters are are included in 
this function.

```java
@RequestMapping(value="/<api_direction>", method=RequestMethod.GET)
@ResponseBody
public ResponseEntity<List<Model>> getAllDocuments() 
{
	String LogHeader = "[/<api_direction>: getAllDocuments] ";
		
	try {
		Log.logger.info(LogHeader + "Requested");
		List<Model> documents = new ArrayList<Model>();
			
		modelRepository.findAll().forEach(documents::add);
			
		if (documents.isEmpty()) {
			Log.logger.info(LogHeader + "No documents found");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
			
		Log.logger.info(LogHeader + "Successful");
		return new ResponseEntity<>(documents, HttpStatus.OK);
	} catch (Exception e) {
		Log.logger.error(LogHeader + "some error ocurred: " + e);
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
```

#### Get By ID

In this case, documents are filtered on their `_id`. That means that only one 
document is going to be returned, as the `_id` cannot be repeated.

```java 
@RequestMapping(value="/<api_direction>/{id}", method=RequestMethod.GET)
@ResponseBody
public ResponseEntity<Model> getDocumentById(@PathVariable("id") String id)
{
	String LogHeader = "[/<api_direction>/id: getDocumentById] ";
		
	try {
		Log.logger.info(LogHeader + "Requested");
		Optional<Model> documentData = modelRepository.findById(id);
		
		if (documentData.isPresent()) {
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(documentData.get(), HttpStatus.OK);
		} else {
			Log.logger.info(LogHeader + "No document found with id: " + id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	} catch (Exception e) {
		Log.logger.error(LogHeader + "some error ocurred: " + e);
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
```

#### Update

Documents might need to be updated. Some data might be inserted incorrectly.
A user might want to change its email, etc.

```java
@RequestMapping(value="/<api_direction>", method=RequestMethod.PUT)
@ResponseBody
public ResponseEntity<Model> updateDocument(@RequestBody Model document) 
{
	String LogHeader = "[/<api_direction>: updateDocument] ";
	
	try {
		Log.logger.info(LogHeader + "Requested");
		Optional<Model> documentData = modelRepository.findById(document.getId());
			
		if (documentData.isPresent()) 
		{
			Model _document = documentData.get();
			_document.setId            (document.getId());
			_document.setField         (document.getField());
			_document.setOtherModel    (document.getOtherField());
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(modelRepository.save(_document), HttpStatus.OK);
		} else {
			Log.logger.info(LogHeader + "The document to update has not been found");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	} catch (Exception e) {
		Log.logger.error(LogHeader + "some error ocurred: " + e);
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
```

#### Delete By ID

To delete a single document, it is as simple as using its `_id`. At the 
beginning we thought of using other fields, like `username` for the `user`
collection - as it is a unique value. But depending on the username, passing it
through the URL request might lead to errors. 

Given a certain document to delete its `_id` is always going to be known. The 
reason behind this statement is that no element is loaded statically in the
webpage. That means, that any information appearing at any point of the 
webpage, other than the components, has to be loaded from database. It also
makes the system a little bit more secure, as finding an `_id` is harder than
finding a short string representing, for example, a `username`. Therefore,
it is almost impossible to know data outside the website.

```java
@RequestMapping(value="/<api_direction>/{id}", method=RequestMethod.DELETE)
@ResponseBody
public ResponseEntity<HttpStatus> deleteDocument(@PathVariable("id") String id)
{
	String LogHeader = "[/<api_direction>/id: deleteDocument] ";
		
	try {
		Log.logger.info(LogHeader + "Requested");
		Optional<Model> document = modelRepository.findById(id);
		
		if (document.isPresent()) {
			Log.logger.info(LogHeader + "The document \"" + document.get().getId() + "\" is going to be deleted");
			modelRepository.deleteById(id);
		}
		
		Log.logger.info(LogHeader + "Successful");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	} catch (Exception e) {
		Log.logger.error(LogHeader + "some error ocurred: " + e);
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
```

#### Delete All

It deletes all documents from a collection. Although it might sound like a 
great way to clean everything up, it does not always work. Careful, the REST 
API might return the error `405 method not allowed`.

```java
@RequestMapping(value="/devices/", method=RequestMethod.DELETE)
@ResponseBody
public ResponseEntity<HttpStatus> deleteAllDevices()
{
	String LogHeader = "[/devices: deleteAllDevices] ";
	
	try {
		Log.logger.info(LogHeader + "Requested");
		deviceRepository.deleteAll();
		
		Log.logger.info(LogHeader + "Successful");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	} catch (Exception e) {
		Log.logger.error(LogHeader + "some error ocurred: " + e);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
```

#### Custom

The functions introduced in the previous sub-sections do not cover all the 
behavior and information that this application should provide. Other functions
have been implemented to increase the API functionality.

Some functions can use `CrudRepository` to implement new functions. Other need
the `MongoTemplate` class. It allows to perform queries/aggregation pipelines 
on a pre-established connection to the database (do not try to configure the
connection on your own, the right variables in the `application.properties` 
file are more than enough to connect to the database - it is safer and 
simpler). 

##### Get By Other Field

As it has been mentioned before, a `repository` extends from `CrudRepository`.
That means that some function calls are already implemented, like the ones that
have been explained before. 

But, it also allows to implement custom functions to search by a specific 
field. In order to do that, the `repository` must specify the definition of a 
function as follows:

```java
TypeToReturn<Model> findByField(FieldType field);
```

Remember that the `repository` is an interface. No further implementation is 
needed. This code (adapting it to the specific _model_ and query - might return
just one element or more) works in the same way as the `findById` function.

Some examples are the ones implemented in:
- Reminder:
```java
List<Reminder> findByTitle(String title);
```
- Ticket
```java
List<Ticket> findByOwner(ObjectId owner);
```
- Treatment
```java
List<Treatment> findByDevice(ObjectId device);
```

The actual implementations are not going to be shown, as the changes between 
functions are minimal, and quite similar to the `findById` functions in all 
_controllers_.

##### After Values

The aforementioned `MongoTemplate` comes in handy for this type of queries. 
Actually it does for every query... but let's specify for those that do not
get into any of the previously explained cases.

One example would be getting all the documents where a specific field is 
greater than a certain value.

This is the case of the `MeasurementsController`, function 
`getMeasurementsAfterDate`. In this case, the documents where the measures are 
newer than a certain date are returned.

```java
public ResponseEntity<List<Measurements>> getMeasurementsAfterDate(@RequestBody DayRequest date)
{
	String LogHeader = "[/measurements/from: getMeasurementsAfterDate] ";
	
	try {
		Log.logger.info(LogHeader + "Requested");
		
		Query query = new Query();
		query.addCriteria(Criteria.where("device").is(new ObjectId(date.getDevice())));
		query.addCriteria(Criteria.where("date").gte(date.getToday()));
		List<Measurements> measurements = mongoTemplate.find(query, Measurements.class);
		
		if (!measurements.isEmpty()) {
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(measurements, HttpStatus.OK);
		} else {
			Log.logger.info(LogHeader + "No measures found after date: " + date.getToday());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	} catch (Exception e) {
		Log.logger.error(LogHeader + "some error ocurred: " + e);
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
```

##### Between Values

Just as the situation before, but in this occasion adding the condition that 
the field value must also have an upper limit. This is the situation in 
`Product` u¡in tge function `getProductBetweenPrices`; and `measurements` in 
the function `getMeasurementsBetweenDates`. They are both very similar (except)
one uses decimals and the other one uses dates.

```java
@RequestMapping(value="/measurements/between", method=RequestMethod.GET)
@ResponseBody
public ResponseEntity<List<Measurements>> getMeasurementsBetweenDates(@RequestBody BetweenDatesRequest date)
{
	String LogHeader = "[/measurements/between: getMeasurementsAfterDate] ";
	
	try {
		Log.logger.info(LogHeader + "Requested");
		
		Query query = new Query();
		query.addCriteria(Criteria.where("device").is(new ObjectId(date.getDevice())));
		query.addCriteria(Criteria.where("date").gte(date.getMinDate()).lte(date.getMaxDate()));
		List<Measurements> measurements = mongoTemplate.find(query, Measurements.class);
			
		if (!measurements.isEmpty()) {
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(measurements, HttpStatus.OK);
		} else {
			Log.logger.info(LogHeader + "No measures found between the dates: " + date.getMinDate() + " and " + date.getMaxDate());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	} catch (Exception e) {
		Log.logger.error(LogHeader + "some error ocurred: " + e);
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
```

There is another similar implementation, where only the initial date is given 
and where the upper value is the pre-computed _tomorrow_ date (in `Reminder`
and `Treatment`).

```java
@RequestMapping(value="/treatments/programmed", method=RequestMethod.GET)
@ResponseBody
public ResponseEntity<List<Treatment>> getTreatmentsProgrammedNextDay(@RequestBody DayRequest today)
{
	String LogHeader = "[/treatments/programmed: getTreatmentsProgrammedNextDay] ";
		
	try {
		Log.logger.info(LogHeader + "Requested");
		
		Query query = new Query();
		query.addCriteria(Criteria.where("device").is(new ObjectId(today.getDevice())));
		query.addCriteria(Criteria.where("actionTime").gte(today.getToday()).lte(today.getTomorrow()));
		List<Treatment> treatments = mongoTemplate.find(query, Treatment.class);
		
		if (!treatments.isEmpty()) {
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(treatments, HttpStatus.OK);
		} else {
			Log.logger.info(LogHeader + "No treatments found for date: " + today.getToday());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	} catch (Exception e) {
		Log.logger.error(LogHeader + "some error ocurred: " + e);
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
```

### Analytical Threads
