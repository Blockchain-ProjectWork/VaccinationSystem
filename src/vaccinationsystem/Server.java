/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinationsystem;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author piyush
 */
public class Server extends Thread {
   private ServerSocket serverSocket;
   private Set<ServerThread> serverThreads = new HashSet<ServerThread>(); // Set of ServerThreadThread
    public Server(String portNumb) throws IOException{
        serverSocket = new ServerSocket(Integer.valueOf(portNumb)); // Initializing Server Socket 
        
    }
   
   public void run(){
   try {
       while (true) {
           ServerThread serverThreadThread = new ServerThread(serverSocket.accept(), this);
           serverThreads.add(serverThreadThread); // putting serverthreadthread into set 
           serverThreadThread.start();
       }
   } catch (Exception e) {
       e.printStackTrace();
       
   }
   }
   // Method to send Messages
  void sendMessage(String message) {
        try { serverThreads.forEach(t-> t.getPrintWriter().println(message)); }
        catch(Exception e) { e.printStackTrace();}
    }
    public Set<ServerThread> getServerThreadThreads() { return serverThreads; } // Get method 
   
}
