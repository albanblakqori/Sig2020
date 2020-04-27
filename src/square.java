import java.util.ArrayList;
import java.util.Arrays;

public class square {


    public static char[][] leftop() {

        //alfabeti lefttop
        char[][] lefttop = new char[5][5];

        int q = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (97 + i * 5 + j >= 113) {

                    lefttop[i][j] = (char) (98 + i * 5 + j);
                    q = 1;
                }

                lefttop[i][j] = (char) (97 + i * 5 + j + q);
            }
        }
        return lefttop;
    }


    public static char[][] leftbot(String keyword1) {

        //leftbot
        ArrayList<Character> leftbot = new ArrayList<>();
        for (int i = 0; i < keyword1.length(); i++) {
            leftbot.add(keyword1.charAt(i));
            if (i == keyword1.length() - 1) {
                for (int j = 0; j <= 25; j++) {
                    leftbot.add((char) (65 + j));
                }
            }
        }
        leftbot.remove(keyword1.length() + 16);

        for (int i = leftbot.size() - 1; i >= keyword1.length(); i--) {
            for (int j = 0; j < keyword1.length(); j++) {
                if (leftbot.get(i) == keyword1.charAt(j)) {
                    leftbot.remove(i);
                }
            }
        }

        char[][] leftbotarray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                leftbotarray[i][j] = leftbot.get(i * 5 + j);

            }
        }

        return leftbotarray;
    }

    public static char[][] righttop(String keyword2) {

        //rightop cipher
        ArrayList<Character> righttop = new ArrayList<>();
        for (int i = 0; i < keyword2.length(); i++) {
            righttop.add(keyword2.charAt(i));
            if (i == keyword2.length() - 1) {
                for (int j = 0; j <= 25; j++) {
                    righttop.add((char) (65 + j));
                }
            }
        }
        righttop.remove(keyword2.length() + 16);
        for (int i = righttop.size() - 1; i >= keyword2.length(); i--) {
            for (int j = 0; j < keyword2.length(); j++) {
                if (righttop.get(i) == keyword2.charAt(j)) {
                    righttop.remove(i);
                }
            }
        }


        char[][] righttoparray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                righttoparray[i][j] = (char) righttop.get(i * 5 + j);
            }
        }

        return righttoparray;
    }



    //i ndajm shkronjat ne cifte ketu i marim shkronjat e para
    public static ArrayList<Character> firstletters(String str) {
        ArrayList<Character> firstletter = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (i % 2 == 0) {
                firstletter.add(str.charAt(i));
            }

        }
        return firstletter;
    }



    //ketu i marim shkronjat e dyta
    public static ArrayList<Character> secondletter(String str) {

        ArrayList<Character> secondletter = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {

            if (i % 2 != 0) {
                secondletter.add(str.charAt(i));
            }
        }
        return secondletter;
    }



    //indexat e shkronjave te para ne leftop
    public static int[][] plainfirstletterindex(String str) {

        ArrayList<Integer> flp = new ArrayList<>();
        for (int g = 0; g < square.firstletters(str).size(); g++) {

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (square.firstletters(str).get(g) == square.leftop()[i][j]) {
                        flp.add(i);
                        flp.add(j);
                    }
                }
            }
        }
        int[][] firstletterindex = new int[flp.size() / 2][2];
        for (int i = 0; i < firstletterindex.length; i++) {
            for (int j = 0; j < 2; j++) {

                firstletterindex[i][j] = flp.get(i * 2 + j);
            }
        }

        return firstletterindex;
    }


    //indeksi i shkronjave te dyat ne lefttop
    public static int[][] plainseccondletterindex(String str) {

        ArrayList<Integer> slp = new ArrayList<>();
        for (int g = 0; g < square.secondletter(str).size(); g++) {

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (square.secondletter(str).get(g) == square.leftop()[i][j]) {
                        slp.add(i);
                        slp.add(j);
                    }
                }
            }
        }
        int[][] secondletterindex = new int[slp.size() / 2][2];
        for (int i = 0; i < secondletterindex.length; i++) {
            for (int j = 0; j < 2; j++) {

                secondletterindex[i][j] = slp.get(i * 2 + j);
            }
        }

        return secondletterindex;
    }

    public static String decrypt(String keyword1, String keyword2, String ciphertext){

        StringBuffer first = new StringBuffer();
        StringBuffer sec = new StringBuffer();

        for (int i = 0; i < ciphertext.length(); i++) {
            if (i == 0 || i % 2 == 0) {
                first.append(ciphertext.charAt(i));
            } else {
                sec.append(ciphertext.charAt(i));
            }
        }

        ArrayList<Integer> firstindex = new ArrayList<>();
        ArrayList<Integer> secindex = new ArrayList<>();

        for (int g = 0; g < first.length(); g++) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (square.leftbot(keyword1)[i][j] == first.charAt(g)) {
                        firstindex.add(i);
                        firstindex.add(j);
                    }
                    if(square.righttop(keyword2)[i][j] == sec.charAt(g)){
                        secindex.add(i);
                        secindex.add(j);
                    }
                }
            }
        }

        int[][] firstindexplain = new int[firstindex.size()/2][2];

        for(int i = 0; i < firstindexplain.length; i++){
            for(int j = 0; j < 2; j++){
                firstindexplain[i][j] =firstindex.get(i * 2 + j);
            }
        }

        int[][] secindexplain = new int[secindex.size()/2][2];
        for(int i = 0; i < secindexplain.length; i++){
            for(int j = 0; j < 2; j++){
                secindexplain[i][j] = secindex.get(i * 2 + j);
            }
        }

        ArrayList<Character> firstplain = new ArrayList<>();
        for(int i = 0; i < firstindexplain.length; i++){

            firstplain.add(square.leftop()[secindexplain[i][0]][firstindexplain[i][1]]);


        }

        ArrayList<Character> secplain = new ArrayList<>();

        for(int i = 0; i < secindexplain.length; i++){
            secplain.add(square.leftop()[firstindexplain[i][0]][secindexplain[i][1]]);

        }

        StringBuffer realplaintext = new StringBuffer();
        for(int i = 0 ; i < firstplain.size(); i++){
            realplaintext.append(firstplain.get(i));
            realplaintext.append(secplain.get(i));
        }

        String plain = realplaintext.toString();

        return plain;
    }



    public static void main(String[] args) {
//
        String type = args[0];
        String en = "encrypt";
        String de = "decrypt";
        String keyword1 = args[1];
        keyword1 = keyword1.toUpperCase();
        String keyword2 = args[2];
        keyword2 = keyword2.toUpperCase();

        String plain = args[3];
        if(type.equals("encrypt")){
            plain = plain.toLowerCase();
        }else if(type.equals("decrypt")){
            plain = plain.toUpperCase();
        }



        String plaintext = plain.replaceAll("[^a-zA-Z0-9\\\\s+]", "");
        plaintext = plaintext.replaceAll("[0-9]","");

        StringBuffer plain1 = new StringBuffer();

        for (int i = 0; i < plaintext.length(); i++) {
            plain1.append(plaintext.charAt(i));
        }
        while (plain1.length() % 2 != 0) {
            plain1.append('x');
        }


        String plain11 = plain1.toString();


        char[][] lefttop = square.leftop();
        char[][] leftbot = square.leftbot(keyword1);
        char[][] righttop = square.righttop(keyword2);


        if(en.equals(type)) {





            int[][] firstletterindex = square.plainfirstletterindex(plain11);
            int[][] secondletterindex = square.plainseccondletterindex(plain11);


            StringBuffer ciphertext = new StringBuffer();

            for (int i = 0; i < plain11.length() / 2; i++) {
                ciphertext.append(leftbot[secondletterindex[i][0]][firstletterindex[i][1]]);
                ciphertext.append(righttop[firstletterindex[i][0]][secondletterindex[i][1]]);

            }

            System.out.println(ciphertext);
        }else if(de.equals(type) ){


            System.out.println( square.decrypt(keyword1,keyword2,plain11));



        }
        else{
            System.out.println("Keni shkru diqka gabim");
        }

    }

}
