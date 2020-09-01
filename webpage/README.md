# Introduction

This project has used state of the art technologies in order to ease the 
development and scalability future issues. Also, it also accomplishes a 
familiar and simple UI for the users to interact with Rainforest products.

The project has been splitted up into two sub-projects. One the one hand, there
is the front-end project - developed in Angular.js. On the other hand, there is
the back-end project - developer in Java.

Therefore, they need to be connected somehow.

# Sub-Projects

Having separated the front-end and the back-end allows to have an independent 
implementation of each part of the development stack. This means that the same 
back-end could be use by different application. For example, different web 
application or phone application - iOS, Android, Windows Phone, etc. but use 
the same back-end.

As expected, the **front-end** will contain the UI, the part of the project 
where the user can interact, make requests, look at the information 
displayed, etc. It also contains a small part of logic. This logic os just the
necessary to give access to certain pages, or to manipulate the data obtained
from a back-end call - the front part of the RESTful API.

The **back-end** includes the main logic of the program, and the database where
data is stored. To connect both, the back-end part of the RESTful API handles 
the requests from the front, retrieves the data and manipulates it. It also 
counts with threads for analytical purposes.

These two projects are independent one another. In order for them to interact, 
the `.jar` created out of the front-end project must be inserted into the 
back-end project. In order to do so, Maven comes in handy.

# DIY

0. You should follow before the steps to create the 
[Back-end](https://github.com/laurapm/UBICUA/tree/master/webpage/eco-webpage) 
and 
[Front-end](https://github.com/laurapm/UBICUA/tree/master/webpage/eco-front) 
projects. 
1. Duplicate the `pom.xml` from the back-end folder to the parent folder. That 
   is, the folder containing both sub-projects.
2. That file must contain:
  - Should specify that the `<artifactId>` is the parent module.
  - Does not need the `<dependencies>`.
  - Keeps the `<parent>` and the `<properties>` attributes.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<name>eco-smart-pot</name>
	<description>Ecø smart pot. A Tamagotchi in real life.</description>

    <groupId>org.rainforest.eco</groupId>
	<artifactId>parent</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>pom</packaging>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.2.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<java.version>1.8</java.version>
	</properties>

</project>
```

3. Back to the back-end `pom.xml`:
  - The parent attributes are the `<groupId>`, `<artifactId>` and `<version>` 
    created in the parent folder `pom.xml`.
  - Keeps its originals `<artifactId>` and its `<dependencies>`.
  - To avoid future errors, also include the `maven-surefire-plugin` to the 
    `<build>` > `<plugins>` attribute.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

  <artifactId>eco-webpage</artifactId>
	<name>eco-webpage</name>
	<description>Ecø smart pot. A Tamagotchi in real life.</description>

	<parent>
		<groupId>org.rainforest.eco</groupId>
		<artifactId>parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.19.1</version>
      </plugin>
		</plugins>
	</build>

</project>
```

4. Copy the `pom.xml` file from the back-end to the front-end folder:
  - Remember to change the `<artifactId>` and `<name>` for this project.
  - The `<parent>` attribute should stay as it is.
  - Need to specify where the compiled project should be kept in the back-end
    folder.
  - Also, need to include a couple of plugins and tell Maven how to compile the
    project.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

  <artifactId>eco-front</artifactId>
  <name>eco-front</name>
	<description>Ecø smart pot. A Tamagotchi in real life.</description>

	<parent>
		<groupId>org.rainforest.eco</groupId>
		<artifactId>parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>

  <!-- Install npm and dependencies -->
	<build>
    <resources>
      <resource>
        <directory>target/frontend</directory>
        <targetPath>static</targetPath>
      </resource>
    </resources>

	  <plugins>
      <plugin>
        <groupId>com.github.eirslett</groupId>
        <artifactId>frontend-maven-plugin</artifactId>
        <version>1.3</version>

        <configuration>
          <nodeVersion>v14.2.0</nodeVersion>o
          <npmVersion>6.14.4</npmVersion>
          <workingDirectory>src/app</workingDirectory>
        </configuration>

        <executions>
          <execution>
            <id>install node and npm</id>
            <goals>
              <goal>install-node-and-npm</goal>
            </goals>
          </execution>

          <execution>
            <id>npm install</id>
            <goals>
              <goal>npm</goal>
            </goals>
          </execution>

          <execution>
            <id>npm run build</id>
            <goals>
              <goal>npm</goal>
            </goals>

            <configuration>
              <arguments>run build</arguments>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins>
	</build>

</project>
```

5. Now that the front-end has an `<artifactId>`, include the following snippet
   to the `<dependencies>` section. This connects the front-end and the 
   back-end.

   ```xml
    <dependency>
		<groupId>org.rainforest.eco</groupId>
		<artifactId>eco-front</artifactId>
		<version>${project.version}</version>
		<scope>runtime</scope>
	</dependency>
   ```

6. Finally, and now that both sub-projects `<artifactId>` has been set,
   include this snippet to the parent `pom.xml`:

   ```xml
    <modules>
		<module>eco-front</module>
		<module>eco-webpage</module>
    </modules>
   ```
