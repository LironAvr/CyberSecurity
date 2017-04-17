import java.io.*;

/**
 * Created by lirona on 17/04/2017
 **/

class InputOutput {
    private int bufferSize ;
    private String readPath ;
    private PrintWriter writer ;
    private FileReader reader;

    InputOutput(int size, String path) throws FileNotFoundException {
        bufferSize = size;
        readPath = path;
        if (!new File(readPath).isFile()){
            throw new FileNotFoundException("File Not Found: " + path);
        }
    }

    char [] Read()
    {
        char[] buffer = new char[bufferSize];
        try {
            if (null == reader)
                reader = new FileReader(readPath);
            int charRead, i = 0;

            while (i < bufferSize && (-1 != (charRead = reader.read()))) {
                char current = (char) charRead;
                buffer[i] = current;
                i++;
            }

            if (0 == i) {
                return null;
            }

            if (bufferSize != i){
                char [] newBuffer = new char[i];
                for (int j=0 ; j < newBuffer.length ; j ++ ){
                    newBuffer[j]= buffer[j];
                }
                return newBuffer ;
            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return buffer ;
    }

    void write(String path, String data) throws FileNotFoundException {
        if (!new File(readPath).exists()){
            throw new FileNotFoundException("Invalid output path");
        }
        try{
            if (null == writer) {
                writer = new PrintWriter(path, "UTF-8");
                for (int i = 0 ; i < data.length() ; i++){
                    //if (arrayToWrite[i]!= 0)
                    writer.append(data.charAt(i));
                }
                writer.flush();
                writer.close();
            }
            else {
                FileWriter writer = new FileWriter(path, true);
                for (int i = 0 ; i < data.length() ; i++){
                    writer.write(data.charAt(i));
                }
                writer.flush();
                writer.close();
            }
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
