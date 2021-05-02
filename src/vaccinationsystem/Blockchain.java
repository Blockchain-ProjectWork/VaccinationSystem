/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinationsystem;

import java.util.ArrayList;

/**
 *
 * @author aditya
 */

public class Blockchain {
    private ArrayList<Block> blocks = new ArrayList<>();

    public ArrayList<Block> getBlocks() {
        return blocks;
    }
    public String toString() {
        StringBuffer res = new StringBuffer();
        
        for(int i=0;i<blocks.size();i++){
            res.append("------------------------------------------------------------------------------------\n");
            res.append(String.format("%-85s","previousBlockHash: "+blocks.get(i).getPreviousBlockHash())+"\n");
            res.append(String.format("%-85s","timestamp:         "+blocks.get(i).getTimestamp())+"\n");
            res.append(String.format("%-85s","nonce:             "+blocks.get(i).getNonce())+"\n");
            
            if(i==blocks.size()-1){
                res.append(String.format("%-71s","hash:              "+blocks.get(i).getHash())+"\n");
                res.append("------------------------------------------------------------------------------------\n");
            } else{
                res.append(String.format("%-71s","hash:              "+blocks.get(i).getHash())+"\n");
                res.append("------------------------------------------------------------------------------------\n");
            }
        }
        
        return res.toString();
    }
}
