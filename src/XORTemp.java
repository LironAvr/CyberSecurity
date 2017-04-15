/**
 * Created by aovadya on 4/15/2017.
 */
public class XORTemp {

    public static byte[] XOR (byte[] first, byte[] second){
        String[] sfirstBinary = toArrayBinary(first);
        String[] ssecondBinary = toArrayBinary(second);
        String[] result = new String[sfirstBinary.length];
        int calc;
        for (int i = 0; i < sfirstBinary.length; i++) {
            result[i] = new String("");
            for (int j = 0; j < sfirstBinary[i].length(); j++) {
                calc = ((int)sfirstBinary[i].charAt(j) + (int)ssecondBinary[i].charAt(j))% 2;
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(calc);
                result[i] += sb.toString();
            }
        }
        return toArrayDecimal(result);
    }

    //convert from array of binary numbers (in string) to array of bytes. every cell in decimal.
    private static byte[] toArrayDecimal(String[] binaryArray){
        byte[] ans = new byte[binaryArray.length];
        for (int i = 0; i < binaryArray.length; i++) {
            ans[i] = fromBinary(binaryArray[i])[0];
        }
        return ans;
    }

    private static String[] toArrayBinary(byte[] bytes){
        String[] ans = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            ans[i] = toBinary(bytes[i]);
        }
        return  ans;
    }

    private static String toBinary( byte bytes )
    {
        StringBuilder sb = new StringBuilder( Byte.SIZE);
        for( int i = 0; i < Byte.SIZE; i++ )
            sb.append((bytes << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    private static byte[] fromBinary( String s )
    {
        int sLen = s.length();
        byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
        char c;
        for( int i = 0; i < sLen; i++ )
            if( (c = s.charAt(i)) == '1' )
                toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
            else if ( c != '0' )
                throw new IllegalArgumentException();
        return toReturn;
    }
}
