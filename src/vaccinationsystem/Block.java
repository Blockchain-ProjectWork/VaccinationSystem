/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinationsystem;

import java.time.Instant;
import org.apache.commons.codec.digest.DigestUtils;
/**
 *
 * @author aditya
 */
public class Block {
    private String nonce=null;
    private String timestamp=null;
    private String previousBlockHash=null;
    private String hash=null;
    private Report report;

    
    //Block constructor
    public Block(String previousBlockHash, String timestamp, String nonce, Report report) 
    {
        this.previousBlockHash = previousBlockHash;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.report = report;
        this.hash = DigestUtils.sha256Hex(this.previousBlockHash+this.timestamp+this.nonce+this.report);

    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
    
    

    public String getNonce() {
        return nonce;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public String getHash() {
        return hash;
    }
    
    public String toString(){
        StringBuffer res = new StringBuffer();
        res.append("{\n");
        res.append(String.format(""+"%-84s","previousBlockHash: "+previousBlockHash)+"\n");
        res.append(String.format(""+"%-84s","timestamp:         "+timestamp)+"\n");
        res.append(String.format(""+"%-84s","nonce:             "+nonce)+"\n");
        res.append("----------------------------------------------------------------------------------\n");
        res.append("Proof of Work:     hash(previousBlockHash+timestamp+nonce) < maxbound\n");
        res.append("Hash:              "+hash                                            +"\n");
        res.append("Max bound:         "+String.format("%64s",Peer.POW_MAX_BOUND.toString(16)).replace(' ', '0')+"\n");
        res.append("                                                                                   }\n");
        return res.toString();
    }    
}