/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author piyush
 */

package vaccinationsystem;


import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import javax.json.Json;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.common.codec.digest.DigestUtils;

/**
 *
 * @author piyush
 */
public class Peer {
    public static final BigInteger POW_MAX_BOUND = new BigInteger("0DFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF",16);
    public static boolean proofOfWorkCompleteFlag = false;
    
        private static final String ALGORITHM = "RSA";
        public static Block block = null;
        private BufferedReader bufferedReader = null;
        private ServerThread serverThread = null;
        private static PrivateKey privatekey;
        private static PublicKey publickey;
        private Blockchain blockchain = new Blockchain();
        private String portNumb="";
        File f = null;
        static private int counter = 1;
    public static void main(String[] args)
    {
        Peer peer = new Peer();
        peer.blockchain.getBlocks().add(new Block(DigestUtils.sha256Hex("Genesis Block"),"00000000000000000","1608617882515", new Report("USER1", "Doctor1", "HS", "Vaccine")));
        while(true){
            try{
                
                peer.handleInitialEntry();
                peer.addListenToPeers();
                while(true){


            if(peer.communicate())System.exit(0);
         }
            }catch(Exception e) {System.out.println("Invalid Entry");}
        }
       
       
    }
    private void handleInitialEntry() throws Exception{
         bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello!! Enter Port number of the system");//enter username and port number
        portNumb = bufferedReader.readLine();
        Wallet w = new Wallet();
        w.getKeys(portNumb);
        privatekey=w.getPrivatekey();
        publickey=w.getPublickey();
        String loc = "Ledger/"+portNumb+"/";
        f = new File(loc);
        f.mkdirs();
        serverThread = new ServerThread(portNumb);
        serverThread.start();
       // addListenToPeers(bufferedReader, setupValues[0], serverThread);
    }
 
    private void addListenToPeers() throws Exception{
        System.out.println("Please enter the name of the System and port <Systemname:port>");
        
        String input = bufferedReader.readLine().toString();
        String[] inputValues = input.split(" ");
        if(!input.equals("s")) for (int i =0;i< inputValues.length;i++)
        {
            String[] address = inputValues[i].split(":");
            Socket socket = null;
            try{
                if(!validatePeers(inputValues[i])){
                    socket = new Socket(InetAddress.getByName(address[0]), Integer.valueOf(address[1]));
                    PeerThread peerThread = new PeerThread(socket);
                    peerThread.start();
     
                }
            }catch(Exception e) {
                if(socket != null) socket.close();
                else System.out.println("invalid input. skipping to next step");
            }
        }
       // communicate(bufferedReader, username, serverThread);
    }
    private boolean validatePeers(String inputValue){
        boolean flag = false;
        Thread[] threads = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        Thread.currentThread().getThreadGroup().enumerate(threads);
        ArrayList<String> peers = new ArrayList<String>();
        for(int i =0;i< threads.length;i++)
        {
            if(threads[i] instanceof PeerThread && !peers.contains(((PeerThread)threads[i]).toString()))
                peers.add(((PeerThread)threads[i]).getHostAddress()+":"+((PeerThread)threads[i]).getPort());
            else if(threads[i] instanceof ServerThread && !peers.contains(((ServerThread)threads[i]).toString()))
                peers.add(((ServerThread)threads[i]).toString());
        }
        if(peers.contains(inputValue)) flag = true;
        return flag;
        
    }
    public String mineBlock() throws NoSuchAlgorithmException, IOException{
        StartupPage st = new StartupPage();
        st.Startup();
        
        System.out.println("Enter the name of Patient");
        String PaitentName = bufferedReader.readLine();
        System.out.println("Enter the name of Doctor");
        String DoctorName = bufferedReader.readLine();
        System.out.println("Enter the name of Hospital");
        String HospitalName = bufferedReader.readLine();
        System.out.println("Enter the name of Vaccine");
        String VaccineName = bufferedReader.readLine();
        System.out.println("Enter the 11 Digit Certificate Number");
        String certNo = bufferedReader.readLine();
        
        String res= PaitentName+"\n"+DoctorName+"\n"+HospitalName+"\n"+VaccineName;
        String hashString = null;
        
        VerifyAadhar v = new VerifyAadhar();
        if(!v.isValid(certNo)){
            System.out.println("Invalid Certificate Number exiting....\n");
            return null;
        }
        
        BigInteger upperIndex = new BigInteger("9999999999999999999999999999999999",16);
        for(BigInteger index = upperIndex; index.compareTo(BigInteger.ZERO) > 0; index = index.subtract(BigInteger.ONE)) {
            if(proofOfWorkCompleteFlag) {
                if(blockchain.getBlocks().get(blockchain.getBlocks().size()-1).getHash().equals(block.getPreviousBlockHash())){
                    blockchain.getBlocks().add(new Block(block.getPreviousBlockHash(), block.getNonce(), block.getTimestamp(), new Report(PaitentName, DoctorName, HospitalName, VaccineName)));
                    System.out.println(">verified block\n"+block);
                }else System.out.println("[unable to add block to blockchain: invalid previous block hash]\n");
                break;
            }else{
                    String currentTime = Long.toString(System.currentTimeMillis());
                    hashString = DigestUtils.sha256Hex(blockchain.getBlocks().get(blockchain.getBlocks().size()-1).getHash()+index.toString(16)+currentTime);
                    System.out.println(hashString);
                    if(new BigInteger(hashString,16).compareTo(POW_MAX_BOUND)==-1){
                        block = new Block(blockchain.getBlocks().get(blockchain.getBlocks().size()-1).getHash(),index.toString(16), currentTime, new Report(PaitentName, DoctorName, HospitalName, VaccineName));
                        blockchain.getBlocks().add(new Block(blockchain.getBlocks().get(blockchain.getBlocks().size()-1).getHash(),block.getNonce(), block.getTimestamp(), new Report(PaitentName, DoctorName, HospitalName, VaccineName)));
                        System.out.println("\n> mined block\n"+block);
                        System.out.println(publickey.getEncoded());
                        File f1 = new File("Ledger/"+portNumb+"/"+Integer.toString(counter)+"/");
                        counter++;
                        f1.getParentFile().mkdir();
                        try{
                            FileOutputStream fos = new FileOutputStream(f1);
                            byte[] b = encrypt(publickey.getEncoded(), res.getBytes());
                            fos.write(b);
                            fos.flush();
                        } catch (Exception ex) {
                                Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            
                        break;
                    }
                }
                
            }
        
        return hashString;
    }
    
    public static byte[] encrypt(byte[] publicKey, byte[] inputData)
            throws Exception {

        PublicKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(publicKey));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(inputData);

        return encryptedBytes;
    }
    
    public static byte[] decrypt(byte[] privateKey, byte[] inputData)
            throws Exception {

        PrivateKey key = KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(privateKey));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

                
        byte[] decryptedBytes = cipher.doFinal(inputData);

        return decryptedBytes;
    }
    
    private boolean communicate() {
        boolean exitFlag=true;
        try {
            boolean flag = true;
            while(flag) {
                System.out.println("Enter Your Choice");
                System.out.println("Press:\n 'm' to mine block\n 'a' to add peers\n 'l' to list connections\n 'd' to display blockchain\n 'e' to exit");
                String commandOrMessage = bufferedReader.readLine();
                switch(commandOrMessage) {
                    default:
                        addListenToPeers();
                        break;
                    case "m":
                        proofOfWorkCompleteFlag = false;
                        mineBlock();
                        StringWriter sW = new StringWriter();
                        Json.createWriter(sW).writeObject(Json.createObjectBuilder()
                                                          .add("previousBlockHash", block.getPreviousBlockHash())
                                                           .add("nonce", block.getNonce())
                                                            .add("timestamp", block.getTimestamp())
                                                            .build());
                        serverThread.sendMessage(sW.toString());
                        flag = true;
                        break;
                        
                    case "a":
                        addListenToPeers();
                        break;
                    case "l":
                        listConnections();
                        break;
                    case "d":
                        System.out.println(blockchain);
                        break;
                    case "e":
                        exitFlag = true;
                        flag = false;
                        break;
                }
            }
            System.exit(0);
        }catch(Exception e) { e.printStackTrace();}
       return exitFlag;
    }
    private void listConnections(){
        Thread[] threads = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        Thread.currentThread().getThreadGroup().enumerate(threads);
        ArrayList<PeerThread> peerThreads = new ArrayList<PeerThread>();
         ArrayList<ServerThreadThread> serverThreadThreads = new ArrayList<ServerThreadThread>();
          ArrayList<ServerThread> serverThreads = new ArrayList<ServerThread>();
         for(int i =0; i< threads.length;i++){
             if(threads[i] instanceof PeerThread) peerThreads.add((PeerThread)threads[i]);    
             else if(threads[i] instanceof ServerThreadThread) serverThreadThreads.add((ServerThreadThread)threads[i]);
             else if(threads[i] instanceof ServerThread) serverThreads.add((ServerThread)threads[i]);    
         }
         if(!peerThreads.isEmpty()) {
             System.out.println(String.format("%-20s","PeerThread")+" | (Connected to) Serverthread\n"+"------------------");
             peerThreads.forEach(x -> System.out.println(String.format("%-20s",x)+" | "+x.getHostAddress() + ":"+x.getPort()));
         } else System.out.println("This Peer is not listening to message coming from any other peer");
         if(!serverThreadThreads.isEmpty()) {
             System.out.println("\n"+String.format("%-20s","ServerThread")+" | ServerThreadThread | (Connected to) Peerthread");
             System.out.println("----------------------------------------------------------");
             for(int i =0;i<serverThreadThreads.size();i++)
             {
                 System.out.println(String.format("%-20s", serverThreads.get(0)) +" | #"+ (i+1) +"              |"+serverThreadThreads.get(i));
             };
    }else System.out.println("No other peers listening to message coming from this peer");
}
}
