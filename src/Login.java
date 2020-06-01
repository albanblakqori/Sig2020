import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Login {

    StringBuffer txt = new StringBuffer();
    //String txtToString = txt.toString();
   String[] parts = txt.toString().split(",");

    String lokacioni = "C:\\Users\\Hp\\IdeaProjects\\Sig2020\\";

    public Login(){}


    public void readFiles(String emri) throws FileNotFoundException {

        File myObj = new File(lokacioni + "Password\\" + emri + ".txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()){
            String data = myReader.nextLine();
            String subdata = data.substring(6);
            txt.append(subdata);

        }
    }


    public boolean CheckUser(String emri) throws FileNotFoundException {
        boolean test = false;
        String txt = this.txt.toString();
            if(txt.contains(emri)){
               test = true;
            }
            return test;
    }


    public void checkPass(String[] parts,String pasi) throws NoSuchAlgorithmException {

        String saltedPas =pasi +  parts[2];

        String hashed = hashedPassword(saltedPas);

        if(hashed.equals(parts[1])){
            System.out.println("Ju lejohet hyrja ");
        }else{
            System.out.println("Ju nuk mund te hyni");
        }
    }



    public String hashedPassword(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] b = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b1 : b) {
            sb.append(Integer.toHexString(b1 & 0xff).toString());
        }
        return sb.toString();
    }

}
