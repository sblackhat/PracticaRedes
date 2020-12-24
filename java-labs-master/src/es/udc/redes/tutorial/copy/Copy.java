package es.udc.redes.tutorial.copy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Copy {

    public static void main(String[] args) throws IOException, FileNotFoundException{

        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        try {
            inputStream = new BufferedReader(new FileReader(args[0]));
            outputStream = new PrintWriter(new FileWriter(args[1]));

            String l;
            while ((l = inputStream.readLine()) != null) {
                outputStream.println(l);
            }
        }
        catch (FileNotFoundException e){
                System.out.println("I/O error : " + e.getLocalizedMessage());
                }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
} 
