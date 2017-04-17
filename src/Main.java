/**
 * Created by lirona on 15/04/2017
 **/
public class Main {
    public static void main(String[] args){

        Encryption enc = new Encryption();
        enc.encryptCBC("/Users/lirona/Documents/workspace/CyberSecurity/src/plainMsg_example.txt",
                        "/Users/lirona/Documents/workspace/CyberSecurity/src/IV.txt",
                        "/Users/lirona/Documents/workspace/CyberSecurity/src/key.txt",
                        "/Users/lirona/Documents/workspace/CyberSecurity/src/output.txt", 10);
    }
}
