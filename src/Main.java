import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) throws Exception, FileNotFoundException,InvalidKeySpecException, NoSuchAlgorithmException, IOException {


        String comm = args[0];

        switch (comm){
            case "create-user":
                String name = args[1];
                String privateloc = "RSA\\" + name + ".pem";
                String publicloc = "RSA\\" + name + ".pub.pem";

                KeyPair keyPair = KeyGenerator.createKeyPair(1024);
                PrivateKey privateKey = keyPair.getPrivate();
                PublicKey publicKey = keyPair.getPublic();
                KeyGenerator.writeFile(KeyGenerator.getPrivateKeyAsEncoded(privateKey),privateloc);
                KeyGenerator.writeFile(KeyGenerator.getPublicKeyAsEncoded(publicKey),publicloc);
                break;
            case "delete-user":
                String nameToDelete = args[1];
                String privateloctoDelete = "RSA\\" + nameToDelete + ".pem";
                String publicloctoDelete = "RSA\\" + nameToDelete + ".pub.pem";
                new DeleteKey(privateloctoDelete,"Privat");
                new DeleteKey(publicloctoDelete,"Publik");
                break;
            case  "export-key":
                String type = args[1];
                String nameToExport = args[2];
                System.out.println(type);
                if(type.equals("publik")){
                    String pubKeyLoc = "RSA\\" + nameToExport + ".pub.pem";
                    new ExportKey(pubKeyLoc);
                }else if(type.equals("privat")){
                    String privKeyLoc = "RSA\\" + nameToExport + ".pem";
                    new ExportKey(privKeyLoc);
                }else{
                    System.out.println("something went wrong");
                }
                break;
            case "import-key":
                String nameToSave = args[1];
                String fromWhere = args[2];
                boolean isPrivate = ImportKey.checkPrivateKey(fromWhere);
                String celesi = ImportKey.teksti_fajllit;
                if(isPrivate != true){
                    nameToSave = "RSA\\" + nameToSave + ".pub.pem";

                }else if(isPrivate == true){
                    nameToSave =  "RSA\\" + nameToSave + ".pem";
                }
                ImportKey.move(isPrivate,fromWhere,nameToSave);
                break;
            case "write-message":
                String marresi = args[1];
                String msg = args[2];
                System.out.println(Write.write(marresi,msg));
                break;
            case "read-message":
                String encryptedMSg = args[1];
                Read obj = new Read(encryptedMSg);
                PrivateKey pk = obj.privateKey(obj.name);
                byte[] celesiSekret = obj.decodeRSA(pk,obj.encryptedKey);
                String decodeDes = obj.decryptDes(celesiSekret,obj.msg1);
                obj.printAll(decodeDes);
        }











    }
}
