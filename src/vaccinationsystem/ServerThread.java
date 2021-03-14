/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinationsystem;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.*;

/**
 *
 * @author piyush
 */
public class ServerThread extends Thread {
    private Server server;
    private Socket socket;
    private PrintWriter printWriter;
    public ServerThread(Socket socket, Server serverThread)  { // Initializing serverthread and socket 
        this.server = serverThread;
        this.socket = socket;
    }
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            while(true) server.sendMessage(bufferedReader.readLine());
        } catch (Exception e) { server.getServerThreadThreads().remove(this); }
    }
    public PrintWriter getPrintWriter() { return printWriter; }
    
}
