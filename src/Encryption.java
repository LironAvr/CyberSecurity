import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lirona on 15/04/2017
 **/

public class Encryption {

    private final int bufferSize = 10;
    private Map<Character, Character> key;
    private FileReader plainText;
    private FileWriter output;
    private char[] buffer;
    private char[] IV;
    private char[] Zero;
    private String encryptedText;

    public boolean encryptCBC(String plainText, String IV, String key, String output){

        try{
            openText(plainText);
            openOutput(output);
            initIV(IV);
            initKey(key);
            initZero();
            encryptedText = "";
            this.buffer = new char[bufferSize];

            while (-1 != this.plainText.read(this.buffer)){
                encryptBlock(this.buffer, this.IV);
            }
            this.output.write(encryptedText);
            this.output.flush();
            this.output.close();
            return true;
        }catch(Exception ex){
            //TODO: Handle exception
            System.out.println(ex.getMessage());
            return false;
        }
    }

    private void initZero() {
        Zero = new char[bufferSize];
        for (int i = 0; i < bufferSize; i++)
            Zero[i] = 0;
    }

    private void encryptBlock(char[] block, char[] vector){
        char[] enc = xor(block, vector);
        char[] newBlock = this.Zero.clone();

        for(int i = 0; i < enc.length; i ++){
            if (key.containsKey(enc[i]))
                newBlock[i] = key.get(enc[i]);
            else newBlock[i] = enc[i];
        }

        this.IV = newBlock;
        encryptedText += String.valueOf(newBlock);
    }

    private static char[] xor(char[] a, char[] b) {
        char[] result = new char[Math.min(a.length, b.length)];

        for (int i = 0; i < result.length; i++) {
            result[i] = (char) (a[i] ^ b[i]);
        }
        return result;
    }

    private void printBlock(char[] newBlock) {
        //TODO: write block to file
        try{
            System.out.print(newBlock);
            this.output.write(newBlock);
            output.flush();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private void openOutput(String output) {
        try{
            this.output = new FileWriter(output);
        }catch(Exception ex){
            //TODO: Handle file not found
            System.out.println(ex.getMessage());
        }
    }

    private void openText(String path){
        try{
            this.plainText = new FileReader(path);
        }catch(Exception ex){
            //TODO: Handle file not found
            System.out.println(ex.getMessage());
        }
    }

    private void initIV(String path){
        try{
            this.IV = new char[10];
            FileReader inputStream = new FileReader(path);
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
}
