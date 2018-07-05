[TOC]

# Recommended Project Structure
```
project
|  pom.xml
|__sqls
   |  DDL_20180801231423.sql
   |  DML_20180801531423.sql
   |  ...

```
# Usage
## Use the plugin
```xml
<build>
		<plugins>
			<plugin>
				<groupId>org.ysh</groupId>
				<artifactId>db-deployer-maven-plugin</artifactId>
				<version>1.0.0</version>
			</plugin>
		</plugins>
	</build>
```
## Configuration
### Config the plugin in xml
```xml
	<build>
		<plugins>
			<plugin>
				<groupId>org.ysh</groupId>
				<artifactId>db-deployer-maven-plugin</artifactId>
				<version>1.0.0</version>
				<configuration>
					<driverClass>com.mysql.jdbc.Driver</driverClass>
					<url>jdbc:mysql://localhost:3306/demo</url>
					<username>demo</username>
					<password>demo</password>
					<sqlScriptDir>sqls</sqlScriptDir>
					<sqlStatementDelimiter>/</sqlStatementDelimiter>
				</configuration>
			</plugin>
		</plugins>
	</build>
```

- jdbcDriver : The name of the jdbc driver used, for example **com.mysql.jdbc.Driver**
- url : The jdbc url, for example **jdbc:mysql://localhost:3306/demo**
- username : The user name used to connect to db
- password : The password used to connect to db
- sqlScriptDir : The diectory of the sql scripts
- sqlStatementDelimiter: The statement delimiter used to split sql statement in sql files, for example **semicolon(;)**
### Config the plugin using system properties
```xml
	<build>
		<plugins>
			<plugin>
				<groupId>org.ysh</groupId>
				<artifactId>db-deployer-maven-plugin</artifactId>
				<version>1.0.0</version>
			</plugin>
		</plugins>
	</build>
```	
```bash
	mvn dbdeployer:reset
		-Djdbc.driverClass=com.mysql.jdbc.Driver 
		-Djdbc.url=jdbc:mysql://localhost:3306/demo 
		-Djdbc.username=demo 
		-Djdbc.password=demo 
		-Dsql.scriptDir=sqls 
		-Dsql.statementDelimiter=;
```

## Goals
 * **dbdeployer:DDL**  
	Create a new DDL script file
 * **dbdeployer:DML**  
	Create a new DML script file
 * **dbdeployer:reset**  
	Execute all the sql script files 
 * **dbdeployer:update**  
	Execute the un executed sql script file