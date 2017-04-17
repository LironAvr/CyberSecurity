import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by lirona on 15/04/2017
 **/

public class Encryption {

    //private final int bufferSize = 10;
    private int bufferSize;
    private HashMap<Character, Character> enc_key;
    private HashMap<Character, Character> dec_key;
    private char[] IV;

    public void encryptCBC(String plainTextPath, String initVectorPath, String keyPath, String outputPath, int bufferSize){

        try{
            this.bufferSize = bufferSize;
            char[] cipher = new char[bufferSize];
            char[] buffer;
            String encryptedText = "";

            initIV(initVectorPath);
            initKey(keyPath);
            InputOutput IO = new InputOutput(bufferSize, plainTextPath);

            while (null != (buffer = IO.Read())){

                //Handle wrong size
                if (bufferSize != buffer.length){
                    char[] tempBuffer = new char[bufferSize];
                    for (int i = 0; i < buffer.length; i++){
                        tempBuffer[i] = buffer[i];
                    }
                    buffer = tempBuffer;
                }

                //Encrypt
                for (int i = 0; i < buffer.length; i++){
                    cipher[i] = (char) (buffer[i] ^ IV[i]);
                    if(enc_key.containsKey(cipher[i])){
                        cipher[i] = enc_key.get(cipher[i]);
                    }
                }
                encryptedText += String.valueOf(cipher);
                IV = cipher;
            }
            IO.write(outputPath, encryptedText);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void decryptCBC(String cipherTextPath, String initVectorPath, String keyPath, String outputPath, int bufferSize){

        try{
            this.bufferSize = bufferSize;
            char[] plainText = new char[bufferSize];
            char[] buffer;
            String decryptedText = "";

            initIV(initVectorPath);
            initKey(keyPath);
            InputOutput IO = new InputOutput(bufferSize, cipherTextPath);

            while (null != (buffer = IO.Read())){

                //Handle wrong size
                if (bufferSize != buffer.length){
                    char[] tempBuffer = new char[bufferSize];
                    for (int i = 0; i < buffer.length; i++){
                        tempBuffer[i] = buffer[i];
                    }
                    buffer = tempBuffer;
                }

                //Decrypt
                for (int i = 0; i < buffer.length; i++){
                    if(dec_key.containsKey(buffer[i])){
                        plainText[i] = dec_key.get(buffer[i]);
                    }
                    else plainText[i] = buffer[i];
                    plainText[i] = (char) (plainText[i] ^ IV[i]);
                }
                decryptedText += String.valueOf(plainText);
                IV = buffer;
            }
            IO.write(outputPath, decryptedText);

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    /*public void encTemp(String plainText, String IV, String key, String output){
        openText(plainText);
        openOutput(output);
        initIV(IV);
        initKey(key);
        encryptedText = "";
        this.buffer = new char[bufferSize];
        try{
            while (-1 != this.plainText.read(this.buffer)){
                //Check length
                if (this.buffer.length != bufferSize){
                    //TODO: Handle length
                }

                //XOR
                for (int i = 0; i < buffer.length; i++){
                    this.IV[i] = (char)(this.buffer[i] ^ this.IV[i]);
                    if (this.key.containsKey(this.IV[i])){
                        this.IV[i] = this.key.get(this.IV[i]);
                    }
                }
                buffer = new char[bufferSize];
                encryptedText += String.valueOf(this.IV);
            }
            this.output.write(encryptedText);
            this.output.flush();
            this.output.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }*/

    private static char[] xor(char[] a, char[] b) {
        char[] result = new char[Math.min(a.length, b.length)];

        for (int i = 0; i < result.length; i++) {
            result[i] = (char) (a[i] ^ b[i]);
        }
        return result;
    }

    private void initIV(String path){
        try{
            this.IV = new InputOutput(bufferSize, path).Read();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private void initKey(String path){
        this.enc_key = new HashMap<>();
        this.dec_key = new HashMap<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String s;
            while((s = reader.readLine()) != null){
                String[] var = s.split(" ");
                if (2 == var.length){
                    this.enc_key.put(var[0].charAt(0), var[1].charAt(0));
                    this.dec_key.put(var[1].charAt(0), var[0].charAt(0));
                }
                else{
                    System.out.println("Invalid Key");
                }
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
