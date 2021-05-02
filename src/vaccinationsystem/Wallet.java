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
import java.security.PrivateKey;
import java.security.PublicKey;
public class Wallet {
    private PrivateKey privatekey;
    private PublicKey publickey;
    
    public void getKeys(String port){
        try{
            GenerateKeys g = new GenerateKeys(512, "RSA");
            g.KeyGenerator(port);
            privatekey = g.getPrivateKey();
            publickey = g.getPublicKey();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public PrivateKey getPrivatekey() {
        return privatekey;
    }

    public PublicKey getPublickey() {
        return publickey;
    }
    
}
