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
public class ServerThread extends Thread {
   private ServerSocket serverSocket;
   private Set<ServerThreadThread> serverThreadThreads = new HashSet<ServerThreadThread>(); // Set of ServerThreadThread
    public ServerThread(String portNumb) throws IOException{
        serverSocket = new ServerSocket(Integer.valueOf(portNumb)); // Initializing Server Socket 
        
    }
   
   public void run(){
   try {
       while (true) {
           ServerThreadThread serverThreadThread = new ServerThreadThread(serverSocket.accept(), this);
           serverThreadThreads.add(serverThreadThread); // putting serverthreadthread into set 
           serverThreadThread.start();
       }
   } catch (Exception e) {
       e.printStackTrace();
       
   }
   }
   // Method to send Messages
  void sendMessage(String message) {
        try { serverThreadThreads.forEach(t-> t.getPrintWriter().println(message)); }
        catch(Exception e) { e.printStackTrace();}
    }
    public Set<ServerThreadThread> getServerThreadThreads() { return serverThreadThreads; } // Get method 
   
}
