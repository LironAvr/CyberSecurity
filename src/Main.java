import java.io.File;
import java.io.IOException;

/**
 * Created by lirona on 15/04/2017
 **/
public class Main {
    /*public static void main(String[] args){

        Encryption enc = new Encryption();
        enc.encryptCBC("C:\\Users\\liron\\IdeaProjects\\CyberSecurity\\src\\text.txt",
                        "C:\\Users\\liron\\IdeaProjects\\CyberSecurity\\src\\IV.txt",
                        "C:\\Users\\liron\\IdeaProjects\\CyberSecurity\\src\\key.txt",
                        "C:\\Users\\liron\\IdeaProjects\\CyberSecurity\\src\\output.txt");
    }*/

    public static void main(String[] args) throws IOException {
        if(args.length != 4) {
            System.out.print(args.length + " Not enough args Or too many \n");
        } else {
            File plainMsg = new File(args[0]);
            File cipherMsg = new File(args[1]);
            File cipherText = new File(args[2]);
            File iV = new File(args[3]);
            PlainTextAttack t = new PlainTextAttack();
            t.readFiles(cipherText, iV, cipherMsg, plainMsg);
            t.findPartialKey();
        }

    }
}
