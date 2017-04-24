/**
 * Created by liron on 24/04/2017.
 */

import java.io.File;
import java.io.FileInputStream;

public class temp {
    byte[] vector;
    byte[][] chiper;
    byte[] key2;
    byte[][] plaintext;
    String plain;
    Dictionary dic;

    public temp() {
        this.dic = new Dictionary();
        this.dic.loadFrequentWords();
    }

    public temp(byte[] vector2, byte[][] chiper2, byte[] key, Dictionary dic1) {
        this.key2 = new byte[key.length];
        this.vector = new byte[vector2.length];
        int size = (int)Math.ceil((double)chiper2.length);
        this.chiper = new byte[size][this.vector.length];
        this.plaintext = new byte[size][this.vector.length];
        this.dic = dic1;

        int j;
        for(j = 0; j < vector2.length; ++j) {
            this.vector[j] = vector2[j];
        }

        for(j = 0; j < chiper2.length; ++j) {
            for(int i = 0; i < chiper2[j].length; ++i) {
                this.chiper[j][i] = chiper2[j][i];
            }
        }

        for(j = 0; j < key.length; ++j) {
            this.key2[j] = key[j];
        }

        this.plaintext = new byte[this.chiper.length][this.vector.length];
    }

    public void readFiles(File encrypted, File vectortxt, File key) {
        try {
            FileInputStream in = new FileInputStream(key);
            this.key2 = new byte[(int)key.length()];
            in.read(this.key2);
            in.close();
            in = new FileInputStream(vectortxt);
            this.vector = new byte[(int)vectortxt.length()];
            in.read(this.vector);
            in.close();
            in = new FileInputStream(encrypted);
            int size = (int)Math.ceil((double)(encrypted.length() / (long)this.vector.length));
            this.chiper = new byte[size][this.vector.length];
            this.plaintext = new byte[size][this.vector.length];
            in.read(this.chiper[0]);

            for(int i = 1; i < this.chiper.length; ++i) {
                in.read(this.chiper[i]);
            }

            in.close();
        } catch (Exception var7) {
            System.out.print("not good");
        }

    }

    public int checkKey(int error1, int blocksize) {
        cbc decrypter = new cbc();
        decrypter.blocksize = blocksize;
        int grade = 0;
        this.plain = new String(decrypter.decrypt(this.chiper[0], this.vector, this.key2));

        for(int j = 1; j < this.chiper.length; ++j) {
            int end;
            if(this.plain.charAt(this.plain.length() - 1) == 32 || this.plain.charAt(this.plain.length() - 1) == 10 || this.plain.charAt(this.plain.length() - 1) == 44) {
                end = 0;
                int start = 0;
                String temp = null;

                while(true) {
                    while(end < this.plain.length()) {
                        while(start < this.plain.length() && (this.plain.charAt(start) == 10 || this.plain.charAt(start) == 13 || this.plain.charAt(start) == 32 || this.plain.charAt(start) == 46 || this.plain.charAt(start) == 44)) {
                            ++start;
                        }

                        if(end < start) {
                            end = start + 1;
                        }

                        if(end < this.plain.length() && (this.plain.charAt(end) == 10 || this.plain.charAt(end) == 13 || this.plain.charAt(end) == 32 || this.plain.charAt(end) == 46 || this.plain.charAt(end) == 44)) {
                            String word = this.plain.substring(start, end);
                            if(word.charAt(0) <= 90 && word.charAt(0) >= 65) {
                                ++grade;
                            } else {
                                word = word.toLowerCase();
                                temp = this.deletePlurels(word);
                                if(this.dic.words.contains(word) || temp != null && this.dic.words.contains(temp)) {
                                    ++grade;
                                }
                            }

                            start = end;
                        } else {
                            ++end;
                        }
                    }

                    this.plain = "";
                    break;
                }
            }

            for(end = 0; end < this.vector.length; ++end) {
                this.vector[end] = this.chiper[j - 1][end];
            }

            this.plain = this.plain + new String(decrypter.decrypt(this.chiper[j], this.vector, this.key2));
        }

        return grade;
    }

    public boolean isword(String s) {
        for(int i = 0; i < s.length(); ++i) {
            if(s.charAt(i) > 122 || s.charAt(i) < 97) {
                return false;
            }
        }

        return true;
    }

    public String deletePlurels(String s) {
        return s.length() > 1 && s.charAt(s.length() - 1) == 115?s.substring(0, s.length() - 1):null;
    }
}
