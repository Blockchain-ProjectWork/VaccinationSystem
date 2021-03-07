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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSMS {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC0381ab57705eda660ced1eae4c403ea7";
    public static final String AUTH_TOKEN = "54dc8f7d35c7c6159cff9afe551b3082";
    public static final String MSID = "MGd8a7fa7af252e854209d7135ca6da2fc";
    public static final String fno = "+18184461125";
    public String send(String Pno, String OTP, String Name) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

         Message message = Message
                .creator(new PhoneNumber(Pno), // to
                        new PhoneNumber("+18184461125"), // from
                        "Hello! "+Name+" Your OTP is"+OTP)
                .create();

        return message.getSid();
    }
}
//"Hello! "+Name+" Your OTP is"+OTP