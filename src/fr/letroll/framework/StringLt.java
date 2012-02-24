package fr.letroll.framework;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StringLt {
    public static String getOnlyNumerics(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer strBuff = new StringBuffer();
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }
    public static String loadTextFile(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int len = 0;
        while ((len = inputStream.read(bytes)) > 0)
          byteStream.write(bytes, 0, len);
        return new String(byteStream.toByteArray(), "UTF8");
      }
    
    public static String lastSegment(String string,char Char){
        if(string.charAt(string.length()-1)==Char){
            string=string.substring(0, string.length()-2);
            return lastSegment(string, Char);
        }else{
            int pos=string.lastIndexOf(Char)+1;
            string=string.substring(pos);
            return string;
        }
    }
}
