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

public class cbc {
    public int blocksize = 10;

    public cbc() {
    }

    public void encrypt(File PlainTex, File vectorTxt, File keyText) {
        try {
            FileInputStream in = new FileInputStream(vectorTxt);
            FileOutputStream out = new FileOutputStream(PlainTex.getName().substring(0, PlainTex.getName().length() - 4) + "_encrypted.txt");
            byte[] vector = new byte[10];
            in.read(vector);
            in.close();
            in = new FileInputStream(keyText);
            int j = (int)keyText.length();
            byte[] key = new byte[j];
            in.read(key);
            in.close();
            byte[] chiperTextBuffer = new byte[vector.length];
            byte[] PlainTextBuffer = new byte[vector.length];
            in = new FileInputStream(PlainTex);

            for(int rc = in.read(PlainTextBuffer); rc != -1; rc = in.read(PlainTextBuffer)) {
                int i;
                if(rc != vector.length) {
                    for(i = rc; i < vector.length; ++i) {
                        PlainTextBuffer[i] = 0;
                    }
                }

                for(i = 0; i < vector.length; ++i) {
                    chiperTextBuffer[i] = (byte)(PlainTextBuffer[i] ^ vector[i]);

                    for(int z = 0; z < key.length; z += 4) {
                        if(chiperTextBuffer[i] == key[z]) {
                            chiperTextBuffer[i] = key[z + 2];
                            z = key.length;
                        }
                    }
                }

                vector = chiperTextBuffer;
                out.write(chiperTextBuffer);
            }

            in.close();
            out.close();
        } catch (Exception var14) {
            ;
        }

    }

    public void encrypt3(File PlainTex, File vectorTxt, File keyText) {
        try {
            FileInputStream in = new FileInputStream(vectorTxt);
            FileOutputStream out = new FileOutputStream(PlainTex.getName().substring(0, PlainTex.getName().length() - 4) + "_encrypted.txt");
            byte[] vector = new byte[8128];
            in.read(vector);
            in.close();
            in = new FileInputStream(keyText);
            int j = (int)keyText.length();
            byte[] key = new byte[j];
            in.read(key);
            in.close();
            byte[] chiperTextBuffer = new byte[vector.length];
            byte[] PlainTextBuffer = new byte[vector.length];
            in = new FileInputStream(PlainTex);

            for(int rc = in.read(PlainTextBuffer); rc != -1; rc = in.read(PlainTextBuffer)) {
                int i;
                if(rc != vector.length) {
                    for(i = rc; i < vector.length; ++i) {
                        PlainTextBuffer[i] = 0;
                    }
                }

                for(i = 0; i < vector.length; ++i) {
                    chiperTextBuffer[i] = (byte)(PlainTextBuffer[i] ^ vector[i]);

                    for(int z = 0; z < key.length; z += 4) {
                        if(chiperTextBuffer[i] == key[z]) {
                            chiperTextBuffer[i] = key[z + 2];
                            z = key.length;
                        }
                    }
                }

                vector = chiperTextBuffer;
                out.write(chiperTextBuffer);
            }

            in.close();
            out.close();
        } catch (Exception var14) {
            ;
        }

    }

    public void decrypt(File chiperText, File vectorTxt, File keyText) {
        try {
            FileInputStream in = new FileInputStream(vectorTxt);
            byte[] vector = new byte[10];
            in.read(vector);
            in.close();
            in = new FileInputStream(keyText);
            int j = (int)keyText.length();
            byte[] key = new byte[j];
            in.read(key);
            in.close();
            this.decrypt(chiperText, vector, key);
        } catch (Exception var8) {
            ;
        }

    }

    public void decrypt3(File chiperText, File vectorTxt, File keyText) {
        try {
            FileInputStream in = new FileInputStream(vectorTxt);
            byte[] vector = new byte[8128];
            in.read(vector);
            in.close();
            in = new FileInputStream(keyText);
            int j = (int)keyText.length();
            byte[] key = new byte[j];
            in.read(key);
            in.close();
            this.decrypt(chiperText, vector, key);
        } catch (Exception var8) {
            ;
        }

    }

    public void decrypt(File chiperText, byte[] vector, byte[] key) {
        try {
            FileOutputStream out = new FileOutputStream(chiperText.getName().substring(0, chiperText.getName().length() - 14) + "_decrypted.txt");
            byte[] chiperTextBuffer = new byte[vector.length];
            byte[] PlainTextBuffer = new byte[vector.length];
            FileInputStream in = new FileInputStream(chiperText);

            for(int rc = in.read(chiperTextBuffer); rc != -1; rc = in.read(chiperTextBuffer)) {
                PlainTextBuffer = this.decrypt(chiperTextBuffer, vector, key);

                for(int i = 0; i < vector.length; ++i) {
                    vector[i] = chiperTextBuffer[i];
                }

                out.write(PlainTextBuffer);
            }

            in.close();
            out.close();
        } catch (Exception var10) {
            ;
        }

    }

    public byte[] decrypt(byte[] chiperTextBuffer, byte[] vector, byte[] key) {
        byte[] PlainTextBuffer = new byte[vector.length];

        for(int i = 0; i < vector.length; ++i) {
            PlainTextBuffer[i] = chiperTextBuffer[i];

            for(int z = 2; z < key.length; z += 4) {
                if(PlainTextBuffer[i] == key[z]) {
                    PlainTextBuffer[i] = key[z - 2];
                    z = key.length;
                }
            }

            PlainTextBuffer[i] ^= vector[i];
        }

        return PlainTextBuffer;
    }
}
