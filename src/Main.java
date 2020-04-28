import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.io.*;
import java.security.*;
import java.util.Base64;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;


public class Main {

    public static void main(String[] args) throws Exception {
        String delcomm = "delete";
        String comm = "create";
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String privateloc = "RSA\\"+s+".xml";
        String publicloc = "RSA\\"+s+".pub.xml";
        if(comm == delcomm){
            new DeleteKey(privateloc,"privat");
            new DeleteKey(publicloc,"publik");
        }else if(comm == "create"){
            KeyPair keyPair = KeyGenerator.createKeyPair(1024);
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            String privateKeyAsXml = KeyGenerator.getPrivateKeyAsXml(privateKey);
            KeyGenerator.writeFile(privateKeyAsXml,privateloc);
            String publicKeyAsXml = KeyGenerator.getPublicKeyAsXml(publicKey);
            KeyGenerator.writeFile(publicKeyAsXml,publicloc);

        }












    }
}
