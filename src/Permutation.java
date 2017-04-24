/**
 * Created by liron on 24/04/2017.
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Permutation {
    public Permutation() {
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
}

