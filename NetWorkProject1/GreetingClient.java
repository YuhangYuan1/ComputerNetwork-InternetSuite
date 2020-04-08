import java.lang.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class GreetingClient {
	
   public static void main(String [] args) {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
          
         System.out.println("Just connected to " + client.getRemoteSocketAddress());

         OutputStream outToServer = client.getOutputStream();
	 InputStream inToClient = client.getInputStream();

         DataOutputStream out = new DataOutputStream(outToServer);
	 DataInputStream in = new DataInputStream(inToClient);
         
	 //variables
	 boolean end = false;
	 boolean qualify;
         int id;
	 int score;
	 int studentAbove;
	 int count;
	 char choice;
	 String firstname;
	 String lastname;
	 Scanner scan = new Scanner(System.in);

	 //runtime loop for the client interface
	 while(end == false) {

         //client code to display the option interface
	 System.out.println("Welcome to the Student Information System");
	 System.out.println("A. Add a new student.");
	 System.out.println("B. Search a student by a ID.");
	 System.out.println("C. Search a student(s) by a Socre.");
	 System.out.println("D. Display all student(s) information.");
	 System.out.println("E. Delete an exist student.");
	 System.out.println("F. Quit the system.");
	 System.out.println("Please Choose one of the options above: ");
	 System.out.println("");

	 //initiate the Scanner class
	 choice = scan.next().charAt(0);
	 
	 //client code to add student id, and update the server
         if(choice == 'a' || choice == 'A') {

            out.writeChar(choice);

	    System.out.println("Please enter student first name: ");
	    firstname = scan.next();
	    out.writeUTF(firstname);

	    System.out.println("Please enter student last name: ");
	    lastname = scan.next();
	    out.writeUTF(lastname);

	    System.out.println("Please enter student ID: ");
	    id = scan.nextInt();
	    out.writeInt(id);

	    System.out.println("Please enter Network score: ");
	    score = scan.nextInt();
	    out.writeInt(score);

	    System.out.println("");
	 }

	 //client code to request student information from the sever by providng student id
	 else if(choice == 'b' || choice == 'B') {

            out.writeChar(choice);

	    System.out.println("Please enter a student ID:");
	    id = scan.nextInt();
	    out.writeInt(id);

	    qualify = in.readBoolean();

	    if(qualify == false) System.out.println(in.readUTF());

	    else if(qualify == true) {
	    System.out.println("Student first name is:" + in.readUTF());
	    System.out.println("Student last name is:" + in.readUTF());
	    System.out.println("Student score is:" + in.readInt());
	    System.out.println("");

	    }

	    System.out.println("");

	 }

	 //client code to request student information by providing a score
	 else if(choice == 'c' || choice == 'C') {

	    out.writeChar(choice);

	    System.out.println("Please enter a score:");
	    score = scan.nextInt();
	    out.writeInt(score);

	    qualify = in.readBoolean();

	    if(qualify == false) System.out.println(in.readUTF());

	    else if(qualify == true) {

	       studentAbove = in.readInt();

	       for(int i = 0; i < studentAbove; i++) {
                  System.out.println("Student first name is: " + in.readUTF());
		  System.out.println("Student last name is: " + in.readUTF());
		  System.out.println("Student id is: " + in.readInt());
		  System.out.println("Student socre is: " + in.readInt());
		  System.out.println("");
	       }
	    }

	    System.out.println("");

	 }

	 //client code to request all student information
	 else if(choice == 'd' || choice == 'D') {

	    out.writeChar(choice);
	    System.out.println("Information for ALL students:");

	    qualify = in.readBoolean();
	    
	    if(qualify == false) System.out.println(in.readUTF());

	    else if(qualify == true) {
	       count = in.readInt();
               for(int i = 0; i < count; i++) {
		  System.out.println("Student first name is: " + in.readUTF());
		  System.out.println("Student last name is: " + in.readUTF());
		  System.out.println("Student id is: " + in.readInt());
		  System.out.println("Student socre is: " + in.readInt());
		  System.out.println("");
	       }
	    }
	    System.out.println("");
	 }

	 //client code to request to delete a student information by providing a student id
	 else if(choice == 'e' || choice == 'E') {
	    out.writeChar(choice);

	    System.out.println("Please enter a student ID:");
            id = scan.nextInt();
	    out.writeInt(id);

            System.out.println(in.readUTF());

	    System.out.println("");

	 }

	 //client code to end client program, disconect from server
	 else if(choice == 'f' || choice == 'F') {
            out.writeChar(choice);
            System.out.println("Thank you for using sudent information system, Bye!");
	    client.close();
	    end = true;

	    System.out.println("");

	 }

	 //client code to detect wrong input in the menu
	 else { 
	    System.out.println("ERROR! Invalid input! Choose again!");
	    System.out.println("");
	 }
       }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
