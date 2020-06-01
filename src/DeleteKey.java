import java.io.File;
public class DeleteKey {

    public DeleteKey(String filename,String tipi){
        String lokacioni = "C:\\Users\\Hp\\IdeaProjects\\Sig2020\\";
       File myObj = new File(lokacioni + filename);
       if(myObj.delete()){
           if(tipi == "Publik" || tipi == "Privat"){

               System.out.println("Eshte larguar celesi " +tipi + " " + myObj.getName());
           }
           else if(tipi == "Password"){
               System.out.println("Eshte larguar passwordi i " + myObj.getName() );
           }


       }else {
           System.out.println("Gabim: Celesi " + filename + " nuk ekziston");
       }
    }


}
