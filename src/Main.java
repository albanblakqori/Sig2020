import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) throws Exception, FileNotFoundException,InvalidKeySpecException, NoSuchAlgorithmException, IOException {


        /*byte[] decode = Base64.getDecoder().decode("cGVyc2hlbmRldGpl");
        String str = new String(decode);
        System.out.println(str);
*/
        
        String comm = args[0];


        switch (comm){
            case "create-user":
                java.io.Console console = System.console();
                String pass = new String(console.readPassword("Passowrd: "));

                String name = args[1];
                String privateloc = "RSA\\" + name + ".pem";
                String publicloc = "RSA\\" + name + ".pub.pem";

                KeyPair keyPair = KeyGenerator.createKeyPair(1024);
                PrivateKey privateKey = keyPair.getPrivate();
                PublicKey publicKey = keyPair.getPublic();
                KeyGenerator.writeFile(KeyGenerator.getPrivateKeyAsEncoded(privateKey),privateloc);
                KeyGenerator.writeFile(KeyGenerator.getPublicKeyAsEncoded(publicKey),publicloc);
                Password save = new Password(name,pass);
                save.WritePass(name,pass);

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

                try{
                    if(type.equals("public")){
                        String pubKeyLoc = "RSA\\" + nameToExport + ".pub.pem";
                        new ExportKey(pubKeyLoc);
                    }else if(type.equals("private")){
                        String privKeyLoc = "RSA\\" + nameToExport + ".pem";
                        new ExportKey(privKeyLoc);
                    }
                }catch (IOException e){
                    System.out.println("gabim celesi nuk ekziston");
                }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("Numri i argumenteve eshte gabim");
                }



                break;
            case "import-key":

                try{

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
                }catch (IOException e){
                    System.out.println("Celesi nuk ekziston");
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Gabimi ne argumente");
                }

                break;
            case "write-message":
                try{
                    String marresi = args[1];
                    String msg = args[2];
                    System.out.println(Write.write(marresi,msg));
                }catch (IOException e){
                    System.out.println("Gabim celesi nuk ekziston");
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Gabim ne argumente");
                }

                break;
            case "read-message":
                try{
                    String encryptedMSg = args[1];
                    Read obj = new Read(encryptedMSg);
                    String emri = obj.name;
                    PrivateKey pk = obj.privateKey(obj.name);
                    byte[] celesiSekret = obj.decodeRSA(pk,obj.encryptedKey);
                    String decodeDes = obj.decryptDes(celesiSekret,obj.msg1);
                    obj.printAll(decodeDes);
                }catch (IOException e){
                    System.out.println("Gabim celesi nuk ekziston" );
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Gabim ne argumenet");
                }

               break;
            default:
                System.out.println("Komanda nuk ekziston");
        }











    }
}
