# HtmlExtractor

This console application takes the link provided for the test and returns the result in Json format. 
At the occurance of any excception, user is returned with "Invalid Link" message and prompted for the correct link.

To compile and run the program, from project's root directory, please use the commands below. 

mvn clean package
mvn -q exec:java

To exit the program, enter "shutdown" before or after execution.

Since the the code is specific to the list page provided for the test, including it here:

http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html

Finally please be aware that one test method requires internet access, which may be an issue when building the project.
Otherwise the following can be added to the command to skip test while compiling

-Dmaven.test.skip=true
