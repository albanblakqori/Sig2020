import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Password {

    String salt = getSalt();
    String destination = "C:\\Users\\Hp\\IdeaProjects\\Sig2020\\";
    public String emri;
    public String pass;

    public Password(String emri, String passi) {
        this.emri = emri;
        this.pass = passi;
    }


    void WritePass(String Emri, String Pasi) throws IOException, NoSuchAlgorithmException {
        File myobj = new File(destination + "\\Password\\" + Emri + ".txt");
        FileWriter mywriter = new FileWriter(destination + "\\Password\\" + Emri + ".txt");
        mywriter.write("User:" + Emri + "\n" + "Pass:" + hashedPassword(Pasi) + "\n" + "Salti: " + salt);
        mywriter.close();
    }


    public String hashedPassword(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String salti =  salt;
        String saltedPwd = input + salt;
        md.update(saltedPwd.getBytes());
        byte[] b = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b1 : b) {
            sb.append(Integer.toHexString(b1 & 0xff).toString());
        }
        return sb.toString();
    }


    public static String getSalt() {

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        String salti = Base64.getEncoder().encodeToString(bytes);
        return salti;

    }

}
