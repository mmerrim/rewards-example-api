# rewards-example-api
Example Java/Spring Boot application to demonstrate calculating reward points for customers based on the amount of their
purchases. 

# Build
I am using Java OpenJDK 11, I have not tested if this can build on an older version.


To build from the root directory (Windows):

`.\gradlew.bat build`

To build from the root directory (Linux)
 
`./gradlew build`

You may need to run `chmod +x ./gradlew` before building.

# Run
To run the .jar file from the build/libs directory after building:

`java -jar rewards-0.0.1-SNAPSHOT.jar`

To run the .jar file with debugger support:

`java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar rewards-0.0.1-SNAPSHOT.jar`

# Try out the API

The API has two methods, a method to store the customer transaction data, and a method to query for the point
calculations on a per-customer basis. The store method will need to be called before you can run queries to look up
points.

The API method URLs will be:

PUT method to store data: localhost:8120/application/rewards

GET method to run queries:

localhost:8120/application/rewards/{customerId}

where {customerID} is an ID from the test data set.

Swagger UI is running at http://localhost:8120/swagger-ui.html, the OpenAPI definition for the API is linked from that
page. This assumes the port hasn't been changed in the application.yml.

You can run example requests from that Swagger page, the example body for the PUT request is in example-transaction-data-request.json in
the developer-notes directory. The GET request just needs a customer ID from that data set.

I also included an example Postman project that could be used instead of the Swagger UI, the export file for that
project is in the developer-notes directory in rewards.postman_collection.json.

# Questions/Assumptions

I had some clarifications I wanted based on the problem statement but was told to make my best guess.

I wasn't sure whether to structure this as a single REST request or two or several, it would depend on who the users of
the API are and how they are using it. To start with I did one PUT request to store the data, and a GET request to
query by customer. The PUT request will need to be run to initialize the data before lookup requests are done. Running
a different PUT request will replace the old data with data from the second request.

Q: Do we need to account for fractional points or round down to nearest dollar?
I am assuming round down, i.e., $120.99 is treated like $120 on a per transaction basis.

Q: Need explanation of the math for the over $50 part of the problem description, it looks to me like it should be 110
points rather than 90 in the original example given.

Q: What is the desired behavior if there is data in the request that is out of the 3 month range? Ignore it, calculate
the full range, throw an error and stop processing or something else?
A: I am calculating results for all data given even if there are more than 3 months, I would need to add something in
to handle it if we wanted to do that rather than fixing the input data.

Q: What to do if a customer is not found during the lookup request. I am throwing a 404.



