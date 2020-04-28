import java.io.File;
public class DeleteKey {

    public DeleteKey(String filename,String tipi){
       File myObj = new File(filename);
       if(myObj.delete()){
           System.out.println("Eshte larguar celesi" +tipi + myObj.getName());


       }else {
           System.out.println("Gabim: Celesi " + filename + " nuk ekziston");
       }
    }


}
