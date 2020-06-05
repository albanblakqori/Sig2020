import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
 /*import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import io.jsonwebtoken.Jwts;
*/

public class Main {

    public static void main(String[] args) throws Exception, FileNotFoundException,InvalidKeySpecException, NoSuchAlgorithmException, IOException {

        /* Instant now = Instant.now();
         String jwt = Jwts.builder().setSubject("Alban Blakqor").
                 setAudience("video demo").
                 setIssuedAt(Date.from(now)).
                 setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES))).
                 compact();

         //System.out.println(jwt);
        /*byte[] decode = Base64.getDecoder().decode("cGVyc2hlbmRldGpl");
        String str = new String(decode);
        System.out.println(str);
*/
        
        String comm = args[0];


        switch (comm){
            case "create-user":
                java.io.Console console = System.console();
                String pass = new String(console.readPassword("Passowrd: "));
                if(pass.length()>6 && (pass.matches("(.*[0-9].*)") || pass.matches("(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)"))){
                    String verifyPass = new String(console.readPassword("Password again: "));
                    if(pass.equals(verifyPass) ){
                        String name = args[1];
                        String privateloc = "RSA\\" + name + ".pem";
                        String publicloc = "RSA\\" + name + ".pub.pem";

                        KeyPair keyPair = KeyGenerator.createKeyPair(2048);
                        PrivateKey privateKey = keyPair.getPrivate();
                        PublicKey publicKey = keyPair.getPublic();
                        KeyGenerator.writeFile(KeyGenerator.getPrivateKeyAsEncoded(privateKey),privateloc);
                        KeyGenerator.writeFile(KeyGenerator.getPublicKeyAsEncoded(publicKey),publicloc);
                        Password save = new Password(name,pass);
                        save.WritePass(name,pass);
                    }else{
                        System.out.println("Passwordi nuk perputhet!");
                    }
                }else{
                    System.out.println("Passi duhet te jet me i gjate se 6 dhe duhet te permbaje numra ose simbole");
                }




                break;
            case "delete-user":
                String nameToDelete = args[1];
                String privateloctoDelete = "RSA\\" + nameToDelete + ".pem";
                String publicloctoDelete = "RSA\\" + nameToDelete + ".pub.pem";
                String Password = "Password\\" + nameToDelete + ".txt";
                String Token = "Tokens\\" + nameToDelete + ".txt";
                new DeleteKey(privateloctoDelete,"Privat");
                new DeleteKey(publicloctoDelete,"Publik");
                new DeleteKey(Password,"Password");
                new DeleteKey(Token,"Token");
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
            case "login":
                java.io.Console console1 = System.console();
                String passi = new String(console1.readPassword("Passowrd: "));
                String emri = args[1];
                Login obj = new Login();
                obj.readFiles(emri);
                boolean user = obj.CheckUser(emri);
                String[] parts = obj.txt.toString().split(",");
                if(user){

                   if(obj.checkPass(parts,passi)){
                       System.out.println("ju jeni loguar");
                     String tokeni =  Tokens.createToken(emri);

                      System.out.println(tokeni);
                      System.out.println("---------------------------------- \n");

                   }
                }
                break;
            case "status":
                String tokeni = args[1];
                Tokens.veirfyToken(tokeni);

                break;
            default:
                System.out.println("Komanda nuk ekziston");
        }











    }
}
