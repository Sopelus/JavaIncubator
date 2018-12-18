# JavaIncubator,

This project was created as a test task for a java intern job.


# Building the project

The project was created in InteliJ Idea IDE, using Maven to add external libraries.

Libraries used: HikariCP, MYSQL connector, log4j.

Use date.ini file to change the desired Date.

Requires MYSQL database with table with following structure: id(int,pk), url(string, not null), date(date, null), status(int, null) 

Example database has been placed into incubator_db.rar file.

Remember to change path to date.ini file in IncubatorTest.java:

File dateSetup = new File("your_path"+"date.ini");

Remember to change MYSQL connection settings in HicariCPDataSource.java file

config.setJdbcUrl("jdbc:mysql:"path to database"?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
        config.setUsername("login");
        config.setPassword("password");
        
