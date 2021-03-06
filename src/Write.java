import com.fasterxml.jackson.databind.ser.Serializers;

import javax.crypto.KeyGenerator;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Write {

    public static byte[] iv = new byte[8] ;
    public static SecretKey key;
    public static IvParameterSpec IV;

    public static String Destination = "C:\\Users\\Hp\\IdeaProjects\\Sig2020\\";




    public static String write(String marresi,String msg) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException, InvalidKeySpecException {
        String marresiPubKey = "RSA\\" + marresi + ".pub.pem";
       String marresiU =  Base64.getEncoder().encodeToString(marresi.getBytes("UTF-8"));
       String msgDesEncrypt = desEncrypt(msg);
        String secretEncoded  = encodewithPub(generatePublicKey(readKey( Destination + marresiPubKey)),key);
       String IVBase64 = Base64.getEncoder().encodeToString(IV.getIV());
       String finalen =marresiU + "." + IVBase64 + "." + secretEncoded + "." + msgDesEncrypt;
       // String secretKeyBase64 = Base64.getEncoder().encodeToString(key.getEncoded());

       return finalen;
    }

    public static String write(String marresi,String msg,String emri) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException, InvalidKeySpecException, SignatureException {
        String marresiPubKey = "RSA\\" + marresi + ".pub.pem";
        String sender = Base64.getEncoder().encodeToString(emri.getBytes());
        String marresiU =  Base64.getEncoder().encodeToString(marresi.getBytes("UTF-8"));
        String msgDesEncrypt = desEncrypt(msg);
        String secretEncoded  = encodewithPub(generatePublicKey(readKey( Destination + marresiPubKey)),key);
        String IVBase64 = Base64.getEncoder().encodeToString(IV.getIV());
        String signature = encodewithPriv(emri,msgDesEncrypt);
        String finalen =marresiU + "." + IVBase64 + "." + secretEncoded + "." + msgDesEncrypt + "." + sender + "." + signature;
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
        byte[] celseibyte = Base64.getMimeDecoder().decode(celsei);
         X509EncodedKeySpec spec  = new X509EncodedKeySpec(celseibyte);
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

    public static String encodewithPriv(String emri,String toSig) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException, SignatureException {
        Signature sig = Signature.getInstance("SHA256withRSA");
        PrivateKey privateKey =Tokens.privateKey(emri) ;
        sig.initSign(privateKey);
        byte[] data = Base64.getDecoder().decode(toSig);
         sig.update(data);
         byte[] digSig  = sig.sign();
         String StringSig = Base64.getEncoder().encodeToString(digSig);
         return StringSig;


    }





}
