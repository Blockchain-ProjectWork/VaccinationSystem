/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Peer class is main method
package vaccinationsystem;
import java.io.*;
import java.net.Socket;
import javax.json.Json;
/**
 *
 * @author piyush
 */
public class Peer {
   
    public static void main(String[] args) throws Exception
    {
       
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("> enter username & port # for this peer");  //enter username and port number
        String[] setupValues = bufferedReader.readLine().split(" "); // splitting the input
        Server serverThread = new Server(setupValues[1]);
        serverThread.start();
        new Peer().updateListenToPeers(bufferedReader, setupValues[0], serverThread);
    }
 
    // The following method is basically to let you know which peer(port number) is recieving message from which port number
    
    private void updateListenToPeers(BufferedReader bufferedReader, String username, Server serverThread) throws Exception{
        System.out.println("> enter(space seperated) computername:port# "); // Each user will have hostname and portnumber
        System.out.println(" peers to receive messages from, skip");
        String input = bufferedReader.readLine(); // Taking input 
        String[] inputValues = input.split(" ");
        if(!input.equals("s")) for (int i =0;i< inputValues.length;i++) // Running for all peers
        {
            String[] address = inputValues[i].split(":");
            Socket socket = null;
            try{
                
                    socket = new Socket(address[0], Integer.valueOf(address[1])); // Instantiating a socket, passing it the host name (address[0]) and a port number(address[1])
                    new PeerThread(socket).start(); // Peer thread instant for each peer having a socket
                    
     
                
            }catch(Exception e) {
                if(socket != null) socket.close();
                else System.out.println("invalid input. skipping to next step");
            }
        }
        communicate(bufferedReader, username, serverThread);
    }
   // The following method helps you to send messages to peers
    
    private void communicate(BufferedReader bufferedReader, String username, Server serverThread) {
        try {
            System.out.println("> you can communicate (e to exit, c to change)");
            boolean flag = true;
            while(flag) {
                String message = bufferedReader.readLine(); // Taking message
                if (message.equals("e")) {
                    flag = false;
                    break;
                } else if (message.equals("c")) { // if user wants to change the peers to whom he want to communicate
                    updateListenToPeers(bufferedReader, username, serverThread); // It will update it 
                } else {
                        StringWriter sW = new StringWriter();
                        Json.createWriter(sW).writeObject(Json.createObjectBuilder()
                                                          .add("username", username)
                                                           .add("message", message)
                                                            .build());
                        serverThread.sendMessage(sW.toString()); // Sending message
                   
                }
            }
            System.exit(0);
        }catch(Exception e) {}
    }
   
}
