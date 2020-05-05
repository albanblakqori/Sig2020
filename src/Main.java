import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.*;
import java.util.Base64;
import java.io.*;
import java.security.*;
import java.util.Base64;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.*;
import java.io.FileReader;
import sun.security.rsa.RSAPrivateCrtKeyImpl;


public class Main {

    public static void main(String[] args) throws Exception, FileNotFoundException,InvalidKeySpecException, NoSuchAlgorithmException, IOException {

        String comm = "write";
        String type = "private";
        String marresi = "alban";
        String  imp_path = "C:\\Users\\Hp\\Desktop\\toimportfrom\\albani_importuar.pem";
        String imp_emri = "albani_importuar";
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String privateloc = "RSA\\"+s+".pem";
        String publicloc = "RSA\\"+s+".pub.pem";
        if(comm == "delete"){
            new DeleteKey(privateloc,"privat");
            new DeleteKey(publicloc,"publik");
        }else if(comm == "create"){
            KeyPair keyPair = KeyGenerator.createKeyPair(1024);
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            KeyGenerator.writeFile(KeyGenerator.getPrivateKeyAsEncoded(privateKey),privateloc);
            KeyGenerator.writeFile(KeyGenerator.getPublicKeyAsEncoded(publicKey),publicloc);

        }else if(comm == "export-key"){
            if(type == "private"){

                new ExportKey(privateloc);
            }else if(type == "public"){
                new ExportKey(publicloc);
            }

           // Import kommanda
        }else if(comm == "import-key"){

         boolean isprivate = ImportKey.checkPrivateKey(imp_path);
         String celesi = ImportKey.teksti_fajllit;


         if(isprivate != true){
             imp_emri = imp_emri + ".pub.pem";

         }else if(isprivate == true){
             imp_emri = imp_emri + ".pem";

         }

            ImportKey.move(isprivate,imp_path,imp_emri);

        }else if(comm == "write"){

            System.out.println(Write.write(marresi,"Hello Firend"));



        }












    }
}
