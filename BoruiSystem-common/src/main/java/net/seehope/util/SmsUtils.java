package net.seehope.util;

import java.io.StringReader;

public class SmsUtils {
    public  static String  connect(String... args){
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        for(String text:args){
            stringBuffer.append(text);
            if(i%2==0){
                stringBuffer.append(":");
            }else{
                stringBuffer.append(",");
            }
            i++;

        }

        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        return stringBuffer.toString();
    }


}
