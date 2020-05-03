import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExportKey {

    public ExportKey(String fileName){
        try{
            File obj = new File(fileName);
            Scanner myReader = new Scanner(obj);
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();



        }catch (FileNotFoundException e){
            System.out.println("Celesi ." + fileName + " nuk ekiston");
            e.printStackTrace();
        }

    }


}
