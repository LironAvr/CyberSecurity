import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lirona on 15/04/2017
 **/
public class Encryption {

    private final int bufferSize = 10;
    private Map<Character, Character> key;
    private FileInputStream plainText;
    private byte[] buffer;
    private byte[] IV;

    public static boolean encryptCBC(String plainText, String IV, String key, String output){

        return true;
    }

    private File openText(String path){

    }

    private void getIV(String path){
        try{
            IV = new byte[10];
            FileInputStream inputStream = new FileInputStream(path);
            inputStream.read(IV);
        }catch (Exception ex){
            //TODO: Handle file not found
        }
    }

    private void getKey(String path){
        key = new HashMap<Character, Character>();
        try{
            FileInputStream reader = new FileInputStream(path);

        }catch(Exception ex){
            //TODO: Handle file not found
        }
    }
}
