import java.lang.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class GreetingServer extends Thread {
   private ServerSocket serverSocket;

   public int studentCount = 0;

   //data structure to hold student information
   public class Student{
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
   
   public GreetingServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   public void run() {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());

	    DataOutputStream out = new DataOutputStream(server.getOutputStream());
            DataInputStream in = new DataInputStream(server.getInputStream());

	    //data base can only contain 10 students
	    Student [] student = new Student[10];

	    int id;
	    int score;
	    int count;
	    boolean end = false;
	    char choice;

	    //while loop to recive choice and make decision from client
	    while(end == false)
	    {
               choice = in.readChar();

	       //server code to add a student into the database
	       if(choice == 'a' || choice == 'A') {

		  System.out.println("server received a");

	          studentCount++;
	          student[studentCount - 1] = new Student();
	          student[studentCount - 1].setFirstName(in.readUTF());
	          student[studentCount - 1].setLastName(in.readUTF());
	          student[studentCount - 1].setId(in.readInt());
	          student[studentCount - 1].setScore(in.readInt());

               
	          System.out.println("Student first name is : " +  student[studentCount - 1].getFirstName());
	          System.out.println("Student last name is : " +  student[studentCount - 1].getLastName());
	          System.out.println("Student id is : " +  student[studentCount - 1].getId());
	          System.out.println("Student score is : " +  student[studentCount - 1].getScore());
		  System.out.println("");
	       }

	       //server code to retrieve a student information by student id
	       else if(choice == 'b' || choice == 'B') {

	          System.out.println("server received b");
                  count = 0;
	          id = in.readInt();

	          if(studentCount == 0) {
		     out.writeBoolean(false);
		     out.writeUTF("no student info in the database, Please add new student info!");
	          }
               
	          else if(studentCount > 0) {

	             for(int i = 0; i < studentCount; i++) {

                        if(student[i].getId() == id) {
		           out.writeBoolean(true);
		           out.writeUTF(student[i].getFirstName());
		           out.writeUTF(student[i].getLastName());
		           out.writeInt(student[i].getScore());
			   count++;
		           break;
	                }
		     }

		     if(count == 0) {
		        out.writeBoolean(false);
		        out.writeUTF("NO such student id exist in the database!");
		     }		     
	          }
	       }
	       
	       //server code to retrieve a student(s) information by student score
	       else if(choice == 'c' || choice == 'C') {

		  System.out.println("server received c");
		  count = 0;
		  score = in.readInt();
                    
		  if(studentCount == 0) {
		     out.writeBoolean(false);
		     out.writeUTF("no student info in the database, Please add new student info!");
		  }

		  else if(studentCount > 0) {
                     
		     for(int i = 0; i < studentCount; i++) {
                        if(student[i].getScore() >= score) count++;
		     }
                     
                     if(count == 0) {
			out.writeBoolean(false);
			out.writeUTF("no student score is above " + score);
		     }

		     else if(count > 0) {
			out.writeBoolean(true);
			out.writeInt(count);
		        for(int i = 0; i < studentCount; i++) {
			   if(student[i].getScore() >= score) {
		              out.writeUTF(student[i].getFirstName());
			      out.writeUTF(student[i].getLastName());
			      out.writeInt(student[i].getId());
			      out.writeInt(student[i].getScore());
			   }
		        }
	              }
		    }
	       }

	       //server code to display all student information in the database
	       else if(choice == 'd' || choice == 'D') {

		  System.out.println("server received d");
                  
                  if(studentCount == 0) {
	             out.writeBoolean(false);
		     out.writeUTF("no student info in the database, Please add new student info!");
		  }

		  else if(studentCount > 0) {
		     out.writeBoolean(true);
		     out.writeInt(studentCount);
		     
		     for(int i = 0; i < studentCount; i++) {
                        out.writeUTF(student[i].getFirstName());
			out.writeUTF(student[i].getLastName());
			out.writeInt(student[i].getId());
			out.writeInt(student[i].getScore());
		     }
	          }
	       }

	      //server code to delete a student information in the database
	      else if(choice == 'e' || choice == 'E') {

         	 System.out.println("server received e");
		 id = in.readInt();
		 count = 0;

		 if(studentCount == 0) {
		    out.writeUTF("no student info in the database, Please add new student info!");
		 }

		 else if(studentCount > 0) {
		     
		    for(int i = 0; i < studentCount; i++) {
			
		       if(student[i].getId() == id) {
		          if(i == studentCount - 1) {
			     student[i] = null;
			     studentCount--;
			     count++;
		             out.writeUTF("student inforamtion deleted for student with id : " + id + "!");
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
		             out.writeUTF("student inforamtion deleted for student with id : " + id + "!");
			     break;
			  }
		        }
		     } 
		     
		     if(count == 0) {
		        out.writeUTF("no student info in the database, Please check student id!");
		     }
	          }
	       }

               //server code to diconnect from the socket, and client
	       else if(choice == 'f' || choice == 'F') {

		  System.out.println("Disconnect from Client!");
		  server.close();
		  end = true;
	       }
	    }
                        
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");

         } catch (IOException e) {
            e.printStackTrace();
            
         }
   }
   
   public static void main(String [] args) {
      int port = Integer.parseInt(args[0]);
      try {
         Thread t = new GreetingServer(port);
         t.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
