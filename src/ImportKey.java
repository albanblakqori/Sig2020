import sun.security.rsa.RSAPrivateCrtKeyImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;


public class ImportKey {
    public static String teksti_fajllit;

    public static String destination = "RSA\\";




    public static boolean checkPrivateKey(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuffer str = new StringBuffer();
        String line;
        while((line = br.readLine()) != null){
            str.append(line);
        }
        br.close();
        fr.close();
        String privateKey = str.toString();
        teksti_fajllit = privateKey;


        boolean isPrivateKey ;
                if((privateKey.length() > 500 && privateKey.length() < 3400) && privateKey.indexOf("-----BEGIN PRIVATE KEY-----") != -1  && privateKey.indexOf("-----END PRIVATE KEY-----") != -1 ){

                    isPrivateKey = true;
                }else{
                    isPrivateKey = false;
                }
        return  isPrivateKey;
    }


   public static void move(boolean privatekey,String pathi,String emri) throws NoSuchAlgorithmException,InvalidKeySpecException,java.lang.Exception,IOException{
        Path result =result = Files.move(Paths.get(pathi), Paths.get(destination + emri)); ;
       if(privatekey == true) {
            emri = emri.replaceAll(".pem","");
            emri =  emri + ".pub.pem";

           String onlyPrivateKey = privateKeyString(teksti_fajllit);
           byte[] privateKeyByte = privateKeyByte(onlyPrivateKey);
           String publicKeyString = publicKey(privateKeyByte);
           KeyGenerator.writeFile(publicKeyString,destination + emri);


           }


   }



    public static String privateKeyString(String privateKey){
        privateKey = privateKey.replaceAll("-----BEGIN PRIVATE KEY-----","");

        privateKey = privateKey.replaceAll("-----END PRIVATE KEY-----","");

        return privateKey;
    }



    public static byte[] privateKeyByte(String privateKey){

       byte[] privateKeyByte = Base64.getMimeDecoder().decode(privateKey);

        return privateKeyByte;
    }



    public  static String publicKey(byte[] privateKeyByte) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey1 = kf.generatePrivate(keySpec);
        RSAPrivateCrtKeyImpl privk = (RSAPrivateCrtKeyImpl) privateKey1;
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privk.getModulus(),privk.getPublicExponent());
        PublicKey publicKey = kf.generatePublic(publicKeySpec);
        byte[] pubkeyencoded = publicKey.getEncoded();
        String publicKeyString = Base64.getMimeEncoder().encodeToString(pubkeyencoded);
        publicKeyString = "-----BEGIN PUBLIC KEY-----\n" + publicKeyString + "\n" +"-----END PUBLIC KEY-----";
        return  publicKeyString;
    }




}
