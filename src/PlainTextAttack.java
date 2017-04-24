/**
 * Created by liron on 24/04/2017.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Set;

public class PlainTextAttack {
    byte[] vector;
    byte[][] cipher;
    byte[] CipherFirstBlock;
    byte[] PlainFirstBlock;
    String filename;
    byte[] key = new byte[]{97, 32, 32, 10, 98, 32, 32, 10, 99, 32, 32, 10, 100, 32, 32, 10, 101, 32, 32, 10, 102, 32, 32, 10, 103, 32, 32, 10, 104, 32, 32, 10, 105, 32, 32, 10, 106, 32, 32, 10, 107, 32, 32, 10, 108, 32, 32, 10, 109, 32, 32, 10, 110, 32, 32, 10, 111, 32, 32, 10, 112, 32, 32, 10, 113, 32, 32, 10, 114, 32, 32, 10, 115, 32, 32, 10, 116, 32, 32, 10, 117, 32, 32, 10, 118, 32, 32, 10, 119, 32, 32, 10, 120, 32, 32, 10, 121, 32, 32, 10, 122, 32, 32, 10, 65, 32, 32, 10, 66, 32, 32, 10, 67, 32, 32, 10, 68, 32, 32, 10, 69, 32, 32, 10, 70, 32, 32, 10, 71, 32, 32, 10, 72, 32, 32, 10, 73, 32, 32, 10, 74, 32, 32, 10, 75, 32, 32, 10, 76, 32, 32, 10, 77, 32, 32, 10, 78, 32, 32, 10, 79, 32, 32, 10, 80, 32, 32, 10, 81, 32, 32, 10, 82, 32, 32, 10, 83, 32, 32, 10, 84, 32, 32, 10, 85, 32, 32, 10, 86, 32, 32, 10, 87, 32, 32, 10, 88, 32, 32, 10, 89, 32, 32, 10, 90, 32, 32};

    public PlainTextAttack() {
    }

    public void readFiles(File encrypted, File vectortxt, File CipherBlock, File PlainBlock) {
        try {
            this.filename = encrypted.getName().substring(0, encrypted.getName().length() - 4);
            FileInputStream in = new FileInputStream(CipherBlock);
            this.CipherFirstBlock = new byte[(int)CipherBlock.length()];
            in.read(this.CipherFirstBlock);
            in.close();
            in = new FileInputStream(PlainBlock);
            this.PlainFirstBlock = new byte[(int)PlainBlock.length()];
            in.read(this.PlainFirstBlock);
            in.close();
            in = new FileInputStream(vectortxt);
            this.vector = new byte[8128];
            in.read(this.vector);
            in.close();
            in = new FileInputStream(encrypted);
            int size = (int)Math.ceil((double)(encrypted.length() / (long)this.vector.length));
            this.cipher = new byte[size][this.vector.length];
            in.read(this.cipher[0]);

            for(int i = 1; i < this.cipher.length; ++i) {
                in.read(this.cipher[i]);
            }

            in.close();
        } catch (Exception var8) {
            System.out.print("not good");
        }

    }

    public void findPartialKey() {
        for(int i = 0; i < this.PlainFirstBlock.length; ++i) {
            this.PlainFirstBlock[i] ^= this.vector[i];
            if(this.PlainFirstBlock[i] <= 122 && this.PlainFirstBlock[i] >= 65) {
                this.setkey(i);
            }
        }

        String s = this.createMissingKeyString();
        Set<String> keys = (new Permutation()).crunchifyPermutation(s);
        byte[] copy = this.copy(this.key);
        byte[] goodKey = this.copy(this.key);
        int grade = 0;
        Dictionary dic = new Dictionary();
        Iterator var8 = keys.iterator();

        while(var8.hasNext()) {
            String str = (String)var8.next();
            int indexkey = 0;

            for(int index = 2; indexkey < str.length(); ++indexkey) {
                while(this.key[index] != 32) {
                    index += 4;
                }

                copy[index] = (byte)str.charAt(indexkey);
                index += 4;
            }

            temp t = new temp(this.vector, this.cipher, copy, dic);
            int grade2 = t.checkKey(grade, 8128);
            if(grade2 > grade) {
                grade = grade2;
                goodKey = this.copy(copy);
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(this.filename + "_key.txt");
            out.write(goodKey);
            out.close();
        } catch (Exception var13) {
            ;
        }

    }

    private void setkey(int i) {
        if(this.PlainFirstBlock[i] == 97) {
            this.key[2] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 98) {
            this.key[6] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 99) {
            this.key[10] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 100) {
            this.key[14] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 101) {
            this.key[18] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 102) {
            this.key[22] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 103) {
            this.key[26] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 104) {
            this.key[30] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 105) {
            this.key[34] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 106) {
            this.key[38] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 107) {
            this.key[42] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 108) {
            this.key[46] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 109) {
            this.key[50] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 110) {
            this.key[54] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 111) {
            this.key[58] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 112) {
            this.key[62] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 113) {
            this.key[66] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 114) {
            this.key[70] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 115) {
            this.key[74] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 116) {
            this.key[78] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 117) {
            this.key[82] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 118) {
            this.key[86] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 119) {
            this.key[90] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 120) {
            this.key[94] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 121) {
            this.key[98] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 122) {
            this.key[102] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 65) {
            this.key[106] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 66) {
            this.key[110] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 67) {
            this.key[114] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 68) {
            this.key[118] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 69) {
            this.key[122] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 70) {
            this.key[126] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 71) {
            this.key[130] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 72) {
            this.key[134] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 73) {
            this.key[138] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 74) {
            this.key[142] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 75) {
            this.key[146] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 76) {
            this.key[150] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 77) {
            this.key[154] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 78) {
            this.key[158] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 79) {
            this.key[162] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 80) {
            this.key[166] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 81) {
            this.key[170] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 82) {
            this.key[174] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 83) {
            this.key[178] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 84) {
            this.key[182] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 85) {
            this.key[186] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 86) {
            this.key[190] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 87) {
            this.key[194] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 88) {
            this.key[198] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 89) {
            this.key[202] = this.CipherFirstBlock[i];
        } else if(this.PlainFirstBlock[i] == 90) {
            this.key[206] = this.CipherFirstBlock[i];
        }

    }

    private String createMissingKeyString() {
        String str = "";

        char c;
        boolean flag;
        int i;
        for(c = 65; c <= 90; ++c) {
            flag = false;

            for(i = 0; i < this.key.length && !flag; i += 4) {
                if(this.key[i + 2] == c) {
                    flag = true;
                }
            }

            if(!flag) {
                str = str + c;
            }
        }

        for(c = 97; c <= 122; ++c) {
            flag = false;

            for(i = 0; i < this.key.length && !flag; i += 4) {
                if(this.key[i + 2] == c) {
                    flag = true;
                }
            }

            if(!flag) {
                str = str + c;
            }
        }

        return str;
    }

    private byte[] copy(byte[] c) {
        byte[] copy = new byte[this.key.length];

        for(int i = 0; i < c.length; ++i) {
            copy[i] = c[i];
        }

        return copy;
    }
}
