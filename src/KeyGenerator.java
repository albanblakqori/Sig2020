import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.security.*;
import java.util.Base64;


public class KeyGenerator {
    static final String KEY_ALGORITHM = "RSA";
    static final int KEY_LENGTH = 1024;
    public static String lokacioni = "C:\\Users\\Hp\\IdeaProjects\\Sig2020\\";



    static public KeyPair createKeyPair(int keyLength) throws NoSuchAlgorithmException{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        System.out.println(keyGen.getProvider());
        keyGen.initialize(keyLength);
        KeyPair keyPair = keyGen.generateKeyPair();
        return keyPair;
    }


    static String getPrivateKeyAsEncoded(PrivateKey privateKey){
        byte[] privateKeyEncodedBytes = privateKey.getEncoded();
        String privateKeyBase64 = Base64.getMimeEncoder().encodeToString(privateKeyEncodedBytes);
        String privateKeyPem = "-----BEGIN PRIVATE KEY-----\n" + privateKeyBase64 + "\n" + "-----END PRIVATE KEY-----";
        return  privateKeyPem;
    }

    static String getPublicKeyAsEncoded(PublicKey publicKey){
        byte[] publicKeyEncodedBytes = publicKey.getEncoded();
        String publicKeyBase64 = Base64.getMimeEncoder().encodeToString(publicKeyEncodedBytes);
        String publicKeyPem = "-----BEGIN PUBLIC KEY-----\n" + publicKeyBase64 + "\n" + "-----END PUBLIC KEY-----";
        return publicKeyPem;
    }





    static void writeFile(String text, String filename) throws Exception{

         boolean ekziston = fileFound(lokacioni + filename);

            if(ekziston == true){
                System.out.println(filename + " Ekziston paraprakisht");
            }else if(ekziston == false){
                PrintWriter writer = new PrintWriter(lokacioni + filename);
                writer.write(text);
                System.out.println(filename + " Eshte regjistruar");
                writer.flush();
                writer.close();

            }else{
                System.out.println("wtf is happening");
            }

    }
    public static boolean fileFound(String fileName){
        try{
            BufferedReader in = new BufferedReader(new FileReader(fileName));
        }catch(FileNotFoundException e){
            return  false;
        }
        return true;
    }



}
