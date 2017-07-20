
package sah;

import java.io.*;
import java.net.*;

import java.net.*;
import java.io.*;

public class EchoServer {
    public static void main(String[] args) throws IOException {
             
        int portNumber = 1995;
        
        try (
            ServerSocket serverSocket =
                new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();    
            Socket clientSocket1 = serverSocket.accept();   
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
          
            PrintWriter out1 =
                new PrintWriter(clientSocket1.getOutputStream(), true);                   
            BufferedReader in1 = new BufferedReader(
                new InputStreamReader(clientSocket1.getInputStream()));
        ) {
            String inputLine="Hello there!",i2="Hello friend!";
            while(true)
            {
           
                if((inputLine=in.readLine())!=null)
                {
                     out.println("Client 2: "+i2); 
                     out1.println("Client 1: "+inputLine);
                     System.out.println("Client 1: "+inputLine); 
                     inputLine="it is my turn!";
                }
             
                if((i2=in1.readLine())!=null)
                {
                   out.println("Client 2: "+i2); 
                   out1.println("Client 1: "+inputLine);
                   System.out.println("Client 2: "+i2);  
                   i2="it is my turn!";
                }          
            }            
            
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}