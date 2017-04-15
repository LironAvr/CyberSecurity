import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
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
                encryptBlock(buffer);
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
        String temp = new String(block);
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

    private void printBlock(char[] newBlock) {
        //TODO: write block to file
        System.out.print(newBlock);
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
}
