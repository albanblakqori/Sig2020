import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ExportKey {
        String lokacioni = "C:\\Users\\Hp\\IdeaProjects\\Sig2020\\";
    public ExportKey(String fileName)throws IOException, FileNotFoundException {
        StringBuffer str = new StringBuffer();
        try{
            File obj = new File(lokacioni + fileName);
            Scanner myReader = new Scanner(obj);
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                str.append(data);
                System.out.println(data);
            }
            myReader.close();



        }catch (FileNotFoundException e){
            System.out.println("Celesi ." + fileName + " nuk ekiston");

        }

    }


}
