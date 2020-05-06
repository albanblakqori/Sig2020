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

        String comm = "read";
        String type = "private";
        String marresi = "test";
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

            System.out.println(Write.write(marresi,"Hello i Nderuari test"));



        }else if(comm == "read"){
            String str = "YWxiYW4=.OC93JVQNX+E=.vTwgyQ1GYzEdtbHEQ89tDLA3aJ9Ib2sAC32B/g7BP8lfPOs20hjPXGPv1cNsDK3P8r9NDB/KCAePJqZY88wAXEUvJFVbxmfCU68ntRdpfJ+3PBHhmMyMMJONS9/Nlw4NCCfg1/3MCFFdVutwshO4QpyZuQZA8fsXeIF+oQF2UeQ=.jQDaoVt08dIGryWtEImWqA==";
            String str1 = "dGVzdA==.RWe3lWmDsCE=.Pb8eOOl38JWicIWUBfXEDsH7X0fj7K1wSqKE7cAs4TWP2V5Txds5TGFQkxUqXbAMr0BFkbfAFghecIR4ebTWUqJQz77dygxWO17+ujbu6K91EqgLIegBYx15dKLqQShp+Ts2CUTMAKDiz/P9b6McefdwhdfRE6Ki7ESMZwGd0io=.o5WjVI+I8Z50qGXNSeJ3TUiDQhex8TRN";
             Read obj = new Read(str1);
             PrivateKey pk = obj.privateKey(obj.name);
             byte[] celesiSekret = obj.decodeRSA(pk,obj.encryptedKey);
             String decodedDes = obj.decryptDes(celesiSekret, obj.msg1);
             obj.printAll(decodedDes);



        }












    }
}
