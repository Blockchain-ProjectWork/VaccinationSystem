/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinationsystem;

/**
 *
 * @author aditya
 */
import java.io.*;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;

public class GenerateKeys {
    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    
    public GenerateKeys(int keylength, String Algo) throws Exception {
        this.keyGen = KeyPairGenerator.getInstance(Algo);
        this.keyGen.initialize(keylength);
    }
    
    public void KeyGenerator(){
        try{
            this.pair = this.keyGen.generateKeyPair();
            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();
            writeKey("KeyPair/publicKey", this.getPublicKey().getEncoded());
            writeKey("KeyPair/privateKey", this.getPrivateKey().getEncoded());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    private static void writeKey(String location, byte[] encoded) throws IOException {
        File f = new File(location);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(encoded);
        fos.flush();
        fos.close();
    }
    
}
