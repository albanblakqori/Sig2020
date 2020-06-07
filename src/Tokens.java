import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import static javax.crypto.Cipher.SECRET_KEY;

public class Tokens {

   public static String  destination = "C:\\Users\\Hp\\IdeaProjects\\Sig2020\\";
  public static  String privateKeyPath;



    public static PrivateKey privateKey(String pathh) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] celseibyte = Base64.getMimeDecoder().decode(readKey(destination + privateKeyPath(pathh)));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(celseibyte);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);
        return privateKey;

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
        String privateKey = str.toString();
        privateKey = privateKey.replaceAll("-----BEGIN PRIVATE KEY-----", "");
        privateKey = privateKey.replaceAll("-----END PRIVATE KEY-----", "");
        return privateKey;
    }

    public static String readKeyPub(String pathi) throws IOException {
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
        privateKey = privateKey.replaceAll("-----BEGIN PUBLIC KEY-----", "");
        privateKey = privateKey.replaceAll("-----END PUBLIC KEY-----", "");
        return privateKey;
    }
   public static void writeToken(String token, String emri) throws IOException, NoSuchAlgorithmException {
        File myobj = new File(destination + "\\Tokens\\" + emri + ".txt");
        FileWriter mywriter = new FileWriter(destination + "\\Tokens\\" + emri + ".txt");
        mywriter.write(token);
        mywriter.close();
    }

    public  static String privateKeyPath(String name) {
        privateKeyPath = "RSA\\" + name + ".pem";
        return privateKeyPath;
    }

    public static String createToken(String emri) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
       PrivateKey privateKey = privateKey(emri);
       Instant now = Instant.now();
       String jwt = Jwts.builder()
               .setSubject(emri)
               .setIssuedAt(Date.from(now))
               .setExpiration(Date.from(now.plus(20,ChronoUnit.MINUTES)))
               .signWith(SignatureAlgorithm.RS256,privateKey)
               .compact();
      writeToken(jwt,emri);
       return jwt;
    }


    public static PublicKey generatePublicKey(String emri) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        byte[] celseibyte = Base64.getMimeDecoder().decode(readKeyPub(destination + "RSA\\" + emri + ".pub.pem"));
        X509EncodedKeySpec spec  = new X509EncodedKeySpec(celseibyte);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(spec);
        return publicKey;
    }


    public static boolean veirfyToken(String tokeni) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
  try {
            String[] parts = tokeni.split("\\.");
            String tokenWithoutSig = parts[0] +"."+ parts[1] + "\\.";
          //  System.out.println(tokenWithoutSig);
            Jwt<Header, Claims> result =  Jwts.parser().parseClaimsJwt(tokenWithoutSig);
           // System.out.println(result.getBody().getSubject());
            Jws<Claims> result1 = Jwts.parser()
                    .setSigningKey(generatePublicKey(result.getBody().getSubject()))
                    .parseClaimsJws(tokeni);
            System.out.println("--------------------------------");
            System.out.println("User: " + result.getBody().getSubject());
            System.out.println("Vlaid : po" );
            System.out.println("Skadimi: " +result.getBody().getExpiration());

            return true;
      }catch (Exception e){
         System.out.println("Tokeni nuk eshte valid");
         return  false;
    }
    }

    public static String getTokenName(String tokeni){

        String[] parts = tokeni.split("\\.");
        String tokenWithoutSig = parts[0] +"."+ parts[1] + "\\.";
        //System.out.println(tokenWithoutSig);
        Jwt<Header, Claims> result =  Jwts.parser().parseClaimsJwt(tokenWithoutSig);
        String emri = result.getBody().getSubject();
       return  emri;
    }





}
