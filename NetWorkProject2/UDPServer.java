//Author: Jason Yuan
import java.lang.*;
import java.util.*;
import java.io.*;
import java.net.*;

class UDPServer
{
   public static int studentCount = 0;
   //Data structure to hold Student information
   public static class Student
   {
      private String firstName;
      private String lastName;
      private int id;
      private int score;

      public void setFirstName(String fName) {
         firstName = fName;
      }

      public void setLastName(String lName) {
	 lastName = lName;
      }

      public void setId(int ID) {
	 id = ID;
      }

      public void setScore(int Score) {
	 score = Score;
      }
      
      public String getFirstName() {
	 return firstName;
      }

      public String getLastName() {
	 return lastName;
      }

      public int getId() {
	 return id;
      }

      public int getScore() {
	 return score;
      }
   }

   public static void main(String args[]) throws Exception
   {
      boolean end = false;
      int port;
      int count;
      InetAddress IPAddress;
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      int idTemp;
      int scoreTemp;
      String firstname;
      String lastname;
      String aboveString;

      Student [] student = new Student[10];

      /**Create the Socket for the Server side**/
      DatagramSocket serverSocket = new DatagramSocket(9876);

      //Create the receive packet get ready to receive packet from the client
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

      //Create the send packet get ready to send packet to the client
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);

      while(end == false)
      {
	 //receive the first choice made from the client side
	 serverSocket.receive(receivePacket);
	 //get the Port number and IP address of the client side
	 port = receivePacket.getPort();
	 IPAddress = receivePacket.getAddress();

         sendPacket.setPort(port);
	 sendPacket.setAddress(IPAddress);

	 System.out.println("Server Received a packet from the Client");
	 System.out.println("Port number: " + port);
	 System.out.println("IP address: " + IPAddress);
	 System.out.println("");

	 //get the option for the Next step
	 String message = new String(receivePacket.getData());

	 char choice = message.charAt(0);

	 /**Server side's logic to deal with the choice made by the Client***/
	
	 if(choice == 'a' || choice == 'A')
	 {
	    System.out.println("choice is A");
            studentCount++;
	    student[studentCount - 1] = new Student();

	    serverSocket.receive(receivePacket);
	    student[studentCount - 1].setFirstName(new String(receivePacket.getData(),0,receivePacket.getLength()));

	    serverSocket.receive(receivePacket);
	    student[studentCount - 1].setLastName(new String(receivePacket.getData(),0,receivePacket.getLength()));

	    serverSocket.receive(receivePacket);
	    student[studentCount - 1].setId(Integer.parseInt(new String(receivePacket.getData(),0,receivePacket.getLength())));

	    serverSocket.receive(receivePacket);
	    student[studentCount - 1].setScore(Integer.parseInt(new String(receivePacket.getData(),0,receivePacket.getLength())));
               
	    System.out.println("Student first name is : " +  student[studentCount - 1].getFirstName());
	    System.out.println("Student last name is : " +  student[studentCount - 1].getLastName());
	    System.out.println("Student id is : " +  student[studentCount - 1].getId());
	    System.out.println("Student score is : " +  student[studentCount - 1].getScore());
	    
	    System.out.println("");
	 }

	 else if(choice == 'b' || choice == 'B')
	 {
            System.out.println("choice is B");
	    count = 0;

	    serverSocket.receive(receivePacket);
	    idTemp = Integer.parseInt(new String(receivePacket.getData(),0,receivePacket.getLength()));

            System.out.println("Student ID from client is: " + idTemp);

	    if(studentCount == 0)
	    {
		sendData = String.valueOf(false).getBytes();
		sendPacket.setData(sendData);
                serverSocket.send(sendPacket);
	             
		sendData = "no student info in the database, Please add new student info!".getBytes();
		sendPacket.setData(sendData);
		serverSocket.send(sendPacket);
	    }
               
	    else if(studentCount > 0)
            {

	        for(int i = 0; i < studentCount; i++)
		{

                      if(student[i].getId()==idTemp)
		      {
			   sendData = String.valueOf(true).getBytes();
		           sendPacket.setData(sendData);
                           serverSocket.send(sendPacket);

		           sendData = student[i].getFirstName().getBytes();
		           sendPacket.setData(sendData);
                           serverSocket.send(sendPacket);
  
		           sendData = student[i].getLastName().getBytes();
		           sendPacket.setData(sendData);
                           serverSocket.send(sendPacket);

		           sendData = String.valueOf(student[i].getScore()).getBytes();
		           sendPacket.setData(sendData);
                           serverSocket.send(sendPacket);

			   count++;
		           break;
	               }
		  }

		  if(count == 0)
		  {
		     sendData = String.valueOf(false).getBytes();
		     sendPacket.setData(sendData);
                     serverSocket.send(sendPacket);
	             
		     sendData = "NO such student id exist in the database!".getBytes();
		     sendPacket.setData(sendData);
		     serverSocket.send(sendPacket);
		   }		     
	      }

	 }

	 else if(choice == 'c' || choice == 'C')
	 {
	    System.out.println("choice is C");
            count = 0;

	    serverSocket.receive(receivePacket);
            scoreTemp = Integer.parseInt(new String(receivePacket.getData(),0,receivePacket.getLength()));
                    
            if(studentCount == 0)
	    {
	       sendData = String.valueOf(false).getBytes();
	       sendPacket.setData(sendData);
               serverSocket.send(sendPacket);

	       sendData = "no student info in the database, Please add new student info!".getBytes();
	       sendPacket.setData(sendData);
	       serverSocket.send(sendPacket);
            }

            else if(studentCount > 0)
	    {          
		  for(int i = 0; i < studentCount; i++) {
                      if(student[i].getScore() >= scoreTemp) count++;
		  }
                     
                  if(count == 0) {
		      sendData = String.valueOf(false).getBytes();
	              sendPacket.setData(sendData);
                      serverSocket.send(sendPacket);

		      aboveString = "no student score is above " + scoreTemp;
		      sendData = aboveString.getBytes();
	              sendPacket.setData(sendData);
	              serverSocket.send(sendPacket);
		   }

		  else if(count > 0) {
		     sendData = String.valueOf(true).getBytes();
	             sendPacket.setData(sendData);
                     serverSocket.send(sendPacket);
	             
		     sendData = String.valueOf(count).getBytes();
		     sendPacket.setData(sendData);
                     serverSocket.send(sendPacket);

		     for(int i = 0; i < studentCount; i++) {
			   if(student[i].getScore() >= scoreTemp)
			   {
                               sendData = student[i].getFirstName().getBytes();
		               sendPacket.setData(sendData);
                               serverSocket.send(sendPacket);
  
		               sendData = student[i].getLastName().getBytes();
		               sendPacket.setData(sendData);
                               serverSocket.send(sendPacket);

                               sendData = String.valueOf(student[i].getId()).getBytes();
		               sendPacket.setData(sendData);
                               serverSocket.send(sendPacket);

		               sendData = String.valueOf(student[i].getScore()).getBytes();
		               sendPacket.setData(sendData);
                               serverSocket.send(sendPacket);
			   }
		        }
	           }
	      }

	 }

	 else if(choice == 'd' || choice == 'D')
	 {
	    System.out.println("choice is D");

	    if(studentCount == 0)
	    {
	       sendData = String.valueOf(false).getBytes();
	       sendPacket.setData(sendData);
               serverSocket.send(sendPacket);

	       sendData = "no student info in the database, Please add new student info!".getBytes();
	       sendPacket.setData(sendData);
	       serverSocket.send(sendPacket);
            }

            else if(studentCount > 0)
	    {
	       sendData = String.valueOf(true).getBytes();
	       sendPacket.setData(sendData);
               serverSocket.send(sendPacket);

	       sendData = String.valueOf(studentCount).getBytes();
	       sendPacket.setData(sendData);
               serverSocket.send(sendPacket);
		     
	       for(int i = 0; i < studentCount; i++)
	       {
                   sendData = student[i].getFirstName().getBytes();
		   sendPacket.setData(sendData);
                   serverSocket.send(sendPacket);
  
		   sendData = student[i].getLastName().getBytes();
		   sendPacket.setData(sendData);
                   serverSocket.send(sendPacket);

                   sendData = String.valueOf(student[i].getId()).getBytes();
		   sendPacket.setData(sendData);
                   serverSocket.send(sendPacket);

		   sendData = String.valueOf(student[i].getScore()).getBytes();
		   sendPacket.setData(sendData);
                   serverSocket.send(sendPacket);
	       }	          
	    }
	 }

	 else if(choice == 'e' || choice == 'E')
	 {
	    System.out.println("choice is E");

	    serverSocket.receive(receivePacket);
            idTemp = Integer.parseInt(new String(receivePacket.getData(),0,receivePacket.getLength()));
            count = 0;

	    if(studentCount == 0)
	    {
	       sendData = String.valueOf(false).getBytes();
	       sendPacket.setData(sendData);
               serverSocket.send(sendPacket);

	       sendData = "no student info in the database, Please add new student info!".getBytes();
	       sendPacket.setData(sendData);
	       serverSocket.send(sendPacket);
            }

            else if(studentCount > 0)
	    {	     
		 for(int i = 0; i < studentCount; i++)
		 {	
		    if(student[i].getId() == idTemp)
		    {
		        if(i == studentCount - 1)
		       	{
			    student[i] = null;
			    studentCount--;
			    count++;
			    aboveString = "student inforamtion deleted for student with id : " + idTemp + "!";
		            sendData = aboveString.getBytes();
	                    sendPacket.setData(sendData);
	                    serverSocket.send(sendPacket);
			    break;
			  }
			  else {
			     student[i].setFirstName(student[studentCount - 1].getFirstName());
			     student[i].setLastName(student[studentCount - 1].getLastName());
			     student[i].setId(student[studentCount - 1].getId());
			     student[i].setScore(student[studentCount - 1].getScore());
			     student[studentCount - 1] = null;
			     studentCount--;
			     count++;
			     aboveString = "student inforamtion deleted for student with id : " + idTemp + "!";
		             sendData = aboveString.getBytes();
	                     sendPacket.setData(sendData);
	                     serverSocket.send(sendPacket);
			     break;
			  }
		        }
		     } 
		     
		     if(count == 0)
		     {
			sendData = "no student info in the database, Please check student id!".getBytes();
	                sendPacket.setData(sendData);
	                serverSocket.send(sendPacket);
		     }
	          }

	 }


	 else if(choice == 'F' || choice == 'f')
	 {
	     System.out.println("The Client side decided to quit!");
	     serverSocket.close();
	     System.out.println("Server socket closed!");
	     end = true;

	 }

      }
   }
}
