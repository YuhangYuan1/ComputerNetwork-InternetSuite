//Author: Jason Yuan
import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.*;

class UDPClient
{
   public static void main(String arg[]) throws Exception
   {
      //variables
      boolean end = false;
      boolean qualify;
      int id;
      int score;
      int studentAbove;
      int count;
      Character choice;
      String firstname;
      String lastname;

      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];

      Scanner scan = new Scanner(System.in);

      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");

      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      

      //runtime loop for the client interface
      while(end == false)
      {

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
     
	 //get the option from the user, send the option to server before enter the next step
         choice = scan.next().charAt(0);
	 sendData = choice.toString().getBytes();
	 sendPacket.setData(sendData);
         clientSocket.send(sendPacket);

	 if(choice == 'a' || choice == 'A')
	 {
            System.out.println("Please enter student first name: ");
	    firstname = scan.next();
	    sendData = firstname.getBytes();
	    sendPacket.setData(sendData);
            clientSocket.send(sendPacket);

	    System.out.println("Please enter student last name: ");
	    lastname = scan.next();
	    sendData = lastname.getBytes();
	    sendPacket.setData(sendData);
            clientSocket.send(sendPacket);

	    System.out.println("Please enter student ID: ");
	    id = scan.nextInt();
	    sendData = String.valueOf(id).getBytes();
	    sendPacket.setData(sendData);
            clientSocket.send(sendPacket);

	    System.out.println("Please enter Network score: ");
	    score = scan.nextInt();
	    sendData = String.valueOf(score).getBytes();
	    sendPacket.setData(sendData);
            clientSocket.send(sendPacket);
	    
	    System.out.println("");
	 }

	 else if(choice == 'b' || choice == 'B')
	 {
	    System.out.println("Please enter a student ID:");
	    id = scan.nextInt();
	    sendData = String.valueOf(id).getBytes();
	    sendPacket.setData(sendData);
            clientSocket.send(sendPacket);

	    clientSocket.receive(receivePacket);
	    qualify = Boolean.parseBoolean(new String(receivePacket.getData(),0,receivePacket.getLength()));

	    if(qualify == false)
	    {
		clientSocket.receive(receivePacket);
	        System.out.println(new String(receivePacket.getData(),0,receivePacket.getLength()));
	    }

	    else if(qualify == true) {

	    clientSocket.receive(receivePacket);
	    System.out.println("Student first name is:" + new String(receivePacket.getData(),0,receivePacket.getLength()));

	    clientSocket.receive(receivePacket);
	    System.out.println("Student last name is:" +  new String(receivePacket.getData(),0,receivePacket.getLength()));


	    clientSocket.receive(receivePacket);
	    System.out.println("Student score is:" +  new String(receivePacket.getData(),0,receivePacket.getLength()));

	    System.out.println("");

	    }

	    System.out.println("");
	 }

	 else if(choice == 'c' || choice == 'C')
	 {
            System.out.println("Please enter a score:");
	    score = scan.nextInt();
	    sendData = String.valueOf(score).getBytes();
	    sendPacket.setData(sendData);
	    clientSocket.send(sendPacket);

	    clientSocket.receive(receivePacket);
	    qualify = Boolean.parseBoolean(new String(receivePacket.getData(),0,receivePacket.getLength()));

	    if(qualify == false)
	    {
	       	clientSocket.receive(receivePacket);
	        System.out.println(new String(receivePacket.getData(),0,receivePacket.getLength()));
	    }

	    else if(qualify == true) {

               clientSocket.receive(receivePacket);
	       studentAbove = Integer.parseInt(new String(receivePacket.getData(),0,receivePacket.getLength()));

	       for(int i = 0; i < studentAbove; i++)
	       {
		  clientSocket.receive(receivePacket);
                  System.out.println("Student first name is: " + new String(receivePacket.getData(),0,receivePacket.getLength()));

		  clientSocket.receive(receivePacket);
		  System.out.println("Student last name is: " + new String(receivePacket.getData(),0,receivePacket.getLength()));

		  clientSocket.receive(receivePacket);
		  System.out.println("Student id is: " + new String(receivePacket.getData(),0,receivePacket.getLength()));

		  clientSocket.receive(receivePacket);
		  System.out.println("Student socre is: " + new String(receivePacket.getData(),0,receivePacket.getLength()));
		  System.out.println("");
	       }
	    }

	    System.out.println("");
	 }

	 else if(choice == 'd' || choice == 'D')
	 {
            System.out.println("Information for ALL students:");

	    clientSocket.receive(receivePacket);
	    qualify = Boolean.parseBoolean(new String(receivePacket.getData(),0,receivePacket.getLength()));
	    
	    if(qualify == false)
	    {
	       	clientSocket.receive(receivePacket);
	        System.out.println(new String(receivePacket.getData(),0,receivePacket.getLength()));
	    }

	    else if(qualify == true)
	    {
	       clientSocket.receive(receivePacket);
	       count = Integer.parseInt(new String(receivePacket.getData(),0,receivePacket.getLength()));

               for(int i = 0; i < count; i++)
	       {

		  clientSocket.receive(receivePacket);
                  System.out.println("Student first name is: " + new String(receivePacket.getData(),0,receivePacket.getLength()));

		  clientSocket.receive(receivePacket);
		  System.out.println("Student last name is: " + new String(receivePacket.getData(),0,receivePacket.getLength()));

		  clientSocket.receive(receivePacket);
		  System.out.println("Student id is: " + new String(receivePacket.getData(),0,receivePacket.getLength()));

		  clientSocket.receive(receivePacket);
		  System.out.println("Student socre is: " + new String(receivePacket.getData(),0,receivePacket.getLength()));
		  System.out.println("");
	       }
	    }
	    System.out.println("");

	 }

	 else if(choice == 'e' || choice == 'E')
	 {
            System.out.println("Please enter a student ID:");
            id = scan.nextInt();
	    sendData = String.valueOf(id).getBytes();
	    sendPacket.setData(sendData);
            clientSocket.send(sendPacket);

	    clientSocket.receive(receivePacket);
            System.out.println(new String(receivePacket.getData(),0,receivePacket.getLength()));

	    System.out.println("");

	 }

	 else if(choice == 'f' || choice == 'F')
         {
            clientSocket.close();
            end = true;
         }
     
      }

   }
}
