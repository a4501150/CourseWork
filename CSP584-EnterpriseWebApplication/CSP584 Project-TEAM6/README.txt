1. Requirement:

- Java 7 or newer.
- Tomcat 7.0.34 for deployment.
- MySQL Server for main database.
- MySQL Workbench as MySQL client.
- MongoDB for Review storage.

2. Deployment:

- Ensures that mysql-connector and mongo-java-driver are exists within tomcat/lib folder.
- Extracts the deliverables.
- Database schema and sample data are initialized on web app startup.
- Copy all files and folders in Deliverables folder into tomcat/webapps/CSP584-Project/ directory.
- Run the batch file tomcat/bin/startup.bat.

3. Using the webapp:

- The webapp can be accessed by the URL: http://localhost/CSP584-Project.

4. SLOC:
- The SLOC for this project is 9,257 line of code (using cloc for measuring, not counting external libraries and templates).
