import com.sun.org.apache.xerces.internal.impl.io.ASCIIReader;
import com.sun.xml.internal.ws.util.ASCIIUtility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lirona on 15/04/2017
 **/

public class Encryption {

    private final int bufferSize = 10;
    private Map<Character, Character> key;
    private FileInputStream plainText;
    private FileOutputStream output;
    private byte[] buffer;
    private byte[] IV;

    public boolean encryptCBC(String plainText, String IV, String key, String output){

        try{
            openText(plainText);
            openOutput(output);
            initIV(IV);
            initKey(key);

            buffer = new byte[bufferSize];
            while (-1 != this.plainText.read(buffer)) {
                this.IV = xor(this.buffer, this.IV);
                encryptBlock(this.IV);
                buffer = new byte[bufferSize];
            }

            return true;
        }catch(Exception ex){
            //TODO: Handle exception
            System.out.println(ex.getMessage());
            return false;
        }
    }

    private void encryptBlock(byte[] block){
        String temp = new String(Base64.getEncoder().encodeToString(block));
        //String temp  = convert(block);
        //for (byte b:block) {
            //temp += ASCIIUtility.toString(block, 0 , block.length);;
        //}

        char[] charBlock = temp.toCharArray();
        char[] newBlock = new char[charBlock.length];
        int i = 0;
        for (char c:charBlock){
            if (key.containsKey(c))
                newBlock[i] = key.get(c);
            else newBlock[i] = c;
            i++;
        }
        printBlock(newBlock);
    }

    String convert(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length);
        sb.append("");
        for (int i = 0; i < data.length; ++ i) {
            if (data[i] < 0) throw new IllegalArgumentException();
            sb.append((char) data[i]);
        }
        return sb.toString();
    }

    private static byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[Math.min(a.length, b.length)];

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (((int) a[i]) ^ ((int) b[i]));
        }
        return result;
    }
    private void printBlock(char[] newBlock) {
        //TODO: write block to file
        byte[] out = newBlock.toString().getBytes();
        try{
            System.out.print(newBlock);
            this.output.write(out);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private void openOutput(String output) {
        try{
            this.output = new FileOutputStream(output);
        }catch(Exception ex){
            //TODO: Handle file not found
            System.out.println(ex.getMessage());
        }
    }

    private void openText(String path){
        try{
            this.plainText = new FileInputStream(path);
        }catch(Exception ex){
            //TODO: Handle file not found
            System.out.println(ex.getMessage());
        }
    }

    private void initIV(String path){
        try{
            this.IV = new byte[10];
            FileInputStream inputStream = new FileInputStream(path);
            inputStream.read(this.IV);
        }catch (Exception ex){
            //TODO: Handle file not found
            System.out.println(ex.getMessage());
        }
    }

    private void initKey(String path){
        this.key = new HashMap<Character, Character>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String s;
            while((s = reader.readLine()) != null){
                String[] var = s.split(" ");
                if (2 == var.length){
                    this.key.put(new Character(var[0].charAt(0)), new Character(var[1].charAt(0)));
                }
                else{
                    //TODO: Handle wrong key

                }
            }

        }catch(Exception ex){
            //TODO: Handle file not found
            System.out.println(ex.getMessage());
        }
    }

    public static byte[] XOR (byte[] first, byte[] second){
        String[] sfirstBinary = toArrayBinary(first);
        String[] ssecondBinary = toArrayBinary(second);
        String[] result = new String[sfirstBinary.length];
        int calc;
        for (int i = 0; i < sfirstBinary.length; i++) {
            result[i] = new String("");
            for (int j = 0; j < sfirstBinary[i].length(); j++) {
                calc = ((int)sfirstBinary[i].charAt(j) + (int)ssecondBinary[i].charAt(j))% 2;
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(calc);
                result[i] += sb.toString();
            }
        }
        byte[] temp = toArrayDecimal(result);
        return temp;//toArrayDecimal(result);
    }

    //convert from array of binary numbers (in string) to array of bytes. every cell in decimal.
    private static byte[] toArrayDecimal(String[] binaryArray){
        byte[] ans = new byte[binaryArray.length];
        for (int i = 0; i < binaryArray.length; i++) {
            ans[i] = fromBinary(binaryArray[i])[0];
        }
        return ans;
    }

    private static String[] toArrayBinary(byte[] bytes){
        String[] ans = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            ans[i] = toBinary(bytes[i]);
        }
        return  ans;
    }

    private static String toBinary( byte bytes )
    {
        StringBuilder sb = new StringBuilder( Byte.SIZE);
        for( int i = 0; i < Byte.SIZE; i++ )
            sb.append((bytes << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    private static byte[] fromBinary( String s )
    {
        int sLen = s.length();
        byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
        char c;
        for( int i = 0; i < sLen; i++ )
            if( (c = s.charAt(i)) == '1' )
                toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
            else if ( c != '0' )
                throw new IllegalArgumentException();
        return toReturn;
    }
}
