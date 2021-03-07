/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinationsystem;

import java.util.HashMap;

/**
 *
 * @author nehit
 */
public class Aadhar {
    private HashMap<String, Long> lookup = new HashMap<String, Long>();
    
    HashMap<String, Long> setData(){
        lookup.put("Aditya", 917351600203L);
        lookup.put("Aditya2", 917017997064L);
        
        return lookup;
    }
    
}
