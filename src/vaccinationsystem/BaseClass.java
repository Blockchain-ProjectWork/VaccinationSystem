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
public class BaseClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            GenerateKeys g = new GenerateKeys(1024, "RSA");
            g.KeyGenerator();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Hello wOrls");
    }
    
}
