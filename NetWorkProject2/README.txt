# NetWorkProject2
Author: Jason Yuan

# Description to the Project:

* The project has been implemented with all five functionalities as required, and the program runs without any problems.
* Both client code and server code are writen in java.

# How to Test:

* 1..If you run both sever and client code on two different servers, then we need to compile both GreetignServer.java, and GreetingClient.java files. 
  2..Then you run "java UDPServer" on zeus as a sever.
  3..Then you run "java UDPClient zeus.cs.txstate.edu" on eros as a client.

* if you run both server and client code on local linux environment, then client side you only need to run "java UDPServer" on one terminal, and run "java UDPClient" on anoter terminal.  


# Problems may have:

* In this program, I only implement an array of object as the data structure to hold student information. The limit will be 10 students.

* I did NOT implement the program to prevent database from holding two exact same student ids. In other words, the database can hold two student information with the same student id, the same first name, or the same last name.

* When delete the student information, the program will simply swap the target information with the last inforamtion in the array. Then the program will delete the last information in the array. So the order the student inforamtion could be changed during the deleting process. So this version of the program is not good for certain advanced sorting and storing purpose.
