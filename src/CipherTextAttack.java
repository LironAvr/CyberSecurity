/**
 * Created by liron on 24/04/2017.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CipherTextAttack {
    int keyindex = 0;
    Set<String> keys;
    byte[][] key;
    private byte[][] chiper;
    private byte[] vector;
    String Filename;

    public CipherTextAttack(String s) {
        this.keys = this.crunchifyPermutation(s);
        this.key = new byte[this.keys.size()][4 * s.length() - 1];

        for(Iterator var3 = this.keys.iterator(); var3.hasNext(); ++this.keyindex) {
            String str = (String)var3.next();

            for(int j = 0; j < 4 * s.length(); j += 4) {
                this.key[this.keyindex][j] = (byte)s.charAt(j / 4);
                this.key[this.keyindex][j + 1] = 32;
                this.key[this.keyindex][j + 2] = (byte)str.charAt(j / 4);
                if(j + 3 != 4 * s.length() - 1) {
                    this.key[this.keyindex][j + 3] = 10;
                }
            }
        }

        this.keyindex = 0;
    }

    public Set<String> crunchifyPermutation(String str) {
        Set<String> crunchifyResult = new HashSet();
        if(str == null) {
            return null;
        } else if(str.length() == 0) {
            crunchifyResult.add("");
            return crunchifyResult;
        } else {
            char firstChar = str.charAt(0);
            String rem = str.substring(1);
            Set<String> words = this.crunchifyPermutation(rem);
            Iterator var7 = words.iterator();

            while(var7.hasNext()) {
                String newString = (String)var7.next();

                for(int i = 0; i <= newString.length(); ++i) {
                    crunchifyResult.add(this.crunchifyCharAdd(newString, firstChar, i));
                }
            }

            return crunchifyResult;
        }
    }

    public String crunchifyCharAdd(String str, char c, int j) {
        String first = str.substring(0, j);
        String last = str.substring(j);
        return first + c + last;
    }

    public void savekey(int num) {
        try {
            FileOutputStream out = new FileOutputStream(this.Filename.substring(0, this.Filename.length() - 4) + "_key.txt");
            out.write(this.key[num]);
            out.close();
        } catch (Exception var3) {
            ;
        }

    }

    public void readFiles(String encrypted1, String vectortxt1) {
        try {
            this.Filename = encrypted1;
            File encrypted = new File(encrypted1);
            File vectortxt = new File(vectortxt1);
            FileInputStream in = new FileInputStream(vectortxt);
            this.vector = new byte[10];
            in.read(this.vector);
            in.close();
            in = new FileInputStream(encrypted);
            int size = (int)Math.ceil((double)(encrypted.length() / (long)this.vector.length));
            if(size > 1000) {
                size = 1000;
            }

            this.chiper = new byte[size][this.vector.length];
            in.read(this.chiper[0]);

            for(int i = 1; i < this.chiper.length; ++i) {
                in.read(this.chiper[i]);
            }

            in.close();
        } catch (Exception var8) {
            System.out.print("not good");
        }

    }

    public void findKey(String encrypted, String vectortxt) {
        this.readFiles(encrypted, vectortxt);
        int error = 0;
        int mindis = 0;
        Dictionary dic = new Dictionary();
        dic.loadFrequentWords();

        for(int i = 0; i < this.key.length; ++i) {
            temp t = new temp(this.vector, this.chiper, this.key[i], dic);
            int error2 = t.checkKey(error, 10);
            if(error2 > error) {
                mindis = i;
                error = error2;
            }
        }

        this.savekey(mindis);
    }

    public boolean checkLastblock(byte[] chiper) {
        for(int i = 9; i < 10; ++i) {
            if(chiper[i] != 0) {
                return false;
            }
        }

        return true;
    }
}
