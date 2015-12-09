Ticket Service
 
Requirements:
1.Java 1.8
2.Maven 3.x.x
3.Environment variables for Java and Maven should be set
 
Steps to Execute:
1. Clone or download the project from Github and save it into your desired directory
2. Open Command prompt and navigate to the saved directory
3. Run the command “mvn clean install” - This should install the project successfully and create a jar file under target directory in the same folder
4. Navigate to the generated target directory and run the command “java –jar TicketService-1.jar”.
5. There will be prompt to key in request attributes and choose the functionality accordingly and press enter after each prompt.
6. The program should execute and print the results in the next line.
 
Running Tests:
mvn test
 
 
Assumptions and Constraints:
1. Assumption is that program can only handle for one event, and fixed time.
2. Assuming the expiry time is 30secs, but can be configured in application-context.xml
3. Assuming the most preferred is level 1 (botton one) and the least preferred is level 4
4. The mockdata is stored in the in-memory, no database persistance
5. The service is designed without exposing any REST API.
 
Stuff that is missing
1.No logging was added
2.A persistent database