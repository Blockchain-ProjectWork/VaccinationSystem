/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinationsystem;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author nehit
 */
public class VerifyAadhar {
    private HashMap<String, Long> lookup = new HashMap<String, Long>();
    private HashSet<String> isValid = new HashSet<>();
    
    
    HashMap<String, Long> setData(){
        lookup.put("Aditya", 917351600203L);
        lookup.put("Aditya2", 917017997064L);
        
        return lookup;
    }
    
    boolean isValid(String num){
        isValid.add("12345678910");
        isValid.add("23456789101");
        
        if(isValid.contains(num))
            return true;
        
        return false;
    }
    
}
