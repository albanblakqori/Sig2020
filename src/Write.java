import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Write {

    public static byte[] iv = new byte[8] ;
    public static SecretKey key;
    public static IvParameterSpec IV;






    public static String write(String marresi,String msg) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException, InvalidKeySpecException {
        String marresiPubKey = "RSA\\" + marresi + ".pub.pem";
       String marresiU =  Base64.getEncoder().encodeToString(marresi.getBytes("UTF-8"));
       String msgDesEncrypt = desEncrypt(msg);
        String secretEncoded  = encodewithPub(generatePublicKey(readKey(marresiPubKey)),key);
       String IVBase64 = Base64.getEncoder().encodeToString(IV.getIV());
       String finalen =marresiU + "." + IVBase64 + "." + secretEncoded + "." + msgDesEncrypt;

       // String secretKeyBase64 = Base64.getEncoder().encodeToString(key.getEncoded());




       return finalen;


    }


    public static String desEncrypt(String msg) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        key = KeyGenerator.getInstance("DES").generateKey();
        IV = new IvParameterSpec(iv);
        Cipher encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE,key,IV);
        byte[] encryptedMsg = encryptCipher.doFinal(msg.getBytes());
        return Base64.getEncoder().encodeToString(encryptedMsg);

    }



    public static String readKey(String pathi) throws IOException {
        File file = new File(pathi);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuffer str = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            str.append(line);
        }
        br.close();
        fr.close();

        String publicKey = str.toString();
        publicKey = publicKey.replaceAll("-----BEGIN PUBLIC KEY-----", "");
        publicKey = publicKey.replaceAll("-----END PUBLIC KEY-----", "");
        return publicKey;

    }




    public static PublicKey generatePublicKey(String celsei) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] celseibyte = Base64.getDecoder().decode(celsei);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(celseibyte);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(spec);
        return publicKey;

    }




    public static String encodewithPub(PublicKey publickey, SecretKey secretKey) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publickey);
        cipher.update(secretKey.getEncoded());
        byte[] finalCipher = cipher.doFinal();
        String encodedRSA = Base64.getEncoder().encodeToString(finalCipher);
        return encodedRSA;

    }





}
