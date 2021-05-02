/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author piyush
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinationsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import javax.json.Json;
import javax.json.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author piyush
 */
public class PeerThread extends Thread {
    private BufferedReader bufferedReader;
    private int port;
    private int localPort;
    private String localHostAddress = null;
    private String hostAddress = null;
    public PeerThread(Socket socket) throws IOException{
        this.localHostAddress = socket.getLocalAddress().getHostName();
        this.localPort = socket.getLocalPort();
        this.port = socket.getPort();
        this.hostAddress = socket.getInetAddress().getHostName();
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
    }
    public void run(){
    boolean flag = true;
    while (flag){
        try{
            JsonObject jsonObject = Json.createReader(bufferedReader).readObject();
            if(jsonObject.containsKey("nonce"))
                verifyBlock(jsonObject.getString("previousBlockHash"),jsonObject.getString("nonce"),jsonObject.getString("timestamp"), new Report(jsonObject.getString("PaitentName"), jsonObject.getString("DoctorName"), jsonObject.getString("HospitalName"), jsonObject.getString("VaccineName")));
        }catch(Exception e) {
            flag = false;
            interrupt();
        }
    }
    }
    void verifyBlock(String previousBlockHash, String nonce, String timestamp, Report report) throws NoSuchAlgorithmException {
        String hashString = DigestUtils.sha256Hex(previousBlockHash+nonce+timestamp);
        if(new BigInteger(hashString,16).compareTo(Peer.POW_MAX_BOUND)==-1) {
           Peer.proofOfWorkCompleteFlag = true;
           Peer.block= new Block(previousBlockHash, nonce, timestamp, report);
    //Peer.minedBlockUsername = username;
     //Peer.minedBlockNounce = nounce;
     //Peer.minedBlockTimestamp = timestamp;
     //Peer.minedBlockHash = hashString;
        }
    }
    public int getPort(){ return port;}
    public String getHostAddress(){ return hostAddress;}
    public String toString() { return this.localHostAddress+":"+this.localPort;}
    
}
