//import sun.security.rsa.RSASignature;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Read {

    String name;
    byte[] iv;
    byte[] encryptedKey;
    byte[] msg1;
    String privateKeyPath;
    String sender;
    byte[] signature;
    String destination = "C:\\Users\\Hp\\IdeaProjects\\Sig2020\\";
    boolean paNenshkrim = false;

    public Read(String ms) throws UnsupportedEncodingException {
        String[] parts = ms.split("\\.");
        this.name = new String(Base64.getDecoder().decode(parts[0]), "UTF8");
        this.iv = Base64.getDecoder().decode(parts[1]);
        this.encryptedKey = Base64.getDecoder().decode(parts[2]);
        this.msg1 = Base64.getDecoder().decode(parts[3]);
        if(parts.length == 6) {
            this.sender = new String(Base64.getDecoder().decode(parts[4]));
            this.signature = Base64.getDecoder().decode(parts[5]);
            paNenshkrim = true;
        }
    }



    public boolean verifySig() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(Write.generatePublicKey(Write.readKey(Write.Destination + "RSA\\" + sender +".pub.pem")));
        //byte[] data = this.msg1;
        signature.update(msg1);
        boolean verified = signature.verify(this.signature);
        return verified;
    }

    public String privateKeyPath(String name) {

        privateKeyPath = "RSA\\" + name + ".pem";
        return privateKeyPath;

    }

    public void printAll(String mesazhi) throws InvalidKeySpecException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        System.out.println("---------------------------");
        System.out.println("Marresi:  " + name);
        System.out.println("Mesazhi: " + mesazhi);
        if(paNenshkrim){
            System.out.println("Derguesi: " + this.sender);
            if(verifySig()){
                System.out.println("Nenshkrimi : Valid");
            }else{
                System.out.println("Nuk eshte valid");
            }
        }


    }

    public byte[] decodeRSA(PrivateKey privateKey, byte[] encryptedKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeySpecException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        decryptCipher.update(encryptedKey);
        byte[] decryptedKey = decryptCipher.doFinal();
        // String decryptedKeyStr = new String(decryptedKey);
        // return decryptedKeyStr;
        return decryptedKey;
    }

    public String decryptDes(byte[] key, byte[] msg) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        SecretKey k = new SecretKeySpec(key, 0, key.length, "DES");
        Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, k, IV(iv));
        byte[] decoedMsgByte = c.doFinal(msg);
        String decoedMsg = new String(decoedMsgByte);
        return decoedMsg;

    }

    public  PrivateKey privateKey(String pathh) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] celseibyte = Base64.getMimeDecoder().decode(readKey(destination + privateKeyPath(pathh)));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(celseibyte);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);
        return privateKey;

    }

    public String readKey(String pathi) throws IOException {
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

        String privateKey = str.toString();
        privateKey = privateKey.replaceAll("-----BEGIN PRIVATE KEY-----", "");
        privateKey = privateKey.replaceAll("-----END PRIVATE KEY-----", "");
        return privateKey;

    }

    public IvParameterSpec IV(byte[] iv) {

        IvParameterSpec IV = new IvParameterSpec(iv);
        return IV;

    }

}






