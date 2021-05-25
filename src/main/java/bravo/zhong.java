package bravo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class zhong {
    public static void main(String[] args) {


        System.out.println(System.getProperty("user.dir"));
        Properties p=System.getProperties();
        p.setProperty("user.dir","C:\\XZX\\wkSpace_java\\Concurrency\\src\\main\\java\\bravo");
        System.out.println(System.getProperty("user.dir"));

        try{
            InputStream f=new FileInputStream("log.txt");
            System.out.println(f.read());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
