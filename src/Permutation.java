import java.util.ArrayList;


public class Permutation {
    public static  void main(String[] args){


        String key = args[0];
        String str = args[1];
        String plaintext = str.replaceAll("\\s","");

        // key from string to int array
        int[] arraykey = new int[key.length()];
        for(int i = 0;i<key.length();i++){
            arraykey[i] = Integer.parseInt(String.valueOf(key.charAt(i)))-1;
        }


        //plain text from string to arraylist,and add 'x'
        ArrayList<Character> cha = new ArrayList<>();
        for(int i = 0; i < plaintext.length(); i++){
            cha.add(plaintext.charAt(i));
        }
        while(cha.size()%key.length() != 0){
            cha.add('x');
        }



        //plaintext from arraylist to char array and twodimensional array
        char[] plain1 = new char[cha.size()];
        System.out.println(plain1.length/key.length());
        for(int i = 0; i < cha.size(); i++){
            plain1[i] = cha.get(i);
        }
        char[][]  plain2di = new char[plain1.length/key.length()][key.length()];
        for(int i = 0; i < plain1.length/key.length(); i++){
            for(int j = 0; j< key.length();j++){
                plain2di[i][j] = plain1[i*key.length() + j];

            }
        }



        //add space to plain text
        StringBuffer plaintextadded = new StringBuffer();
        for(int i = 0; i < cha.size(); i++){
            if(i %key.length()==0 && i != 0){
                plaintextadded.append(" ");
            }
            plaintextadded.append(cha.get(i));

        }




        //twodimensional array cipher and string cipher
        char[][] cipher = new char[plain1.length/key.length()][key.length()];

        StringBuffer scipher = new StringBuffer();

        for(int i = 0; i< plain1.length/key.length();i++){
            for(int j = 0; j < key.length(); j++){
                cipher[i][j] = plain2di[i][arraykey[j]];

                scipher.append(cipher[i][j]);
            }

        }


        //add space to string cipher
        StringBuffer scipherfinal = new StringBuffer();
        for(int i = 0; i<scipher.length();i++){
            if(i % key.length() ==0 && i!=0){
                scipherfinal.append(" ");
            }
            scipherfinal.append(scipher.charAt(i));
        }

        //key under plaintext
        StringBuffer keybuff = new StringBuffer();
        for(int i = 0;i < cha.size()/key.length();i++){
            keybuff.append(key+ " ");
        }

        System.out.println("plaintext: " + plaintextadded);
        System.out.println("key:       "+keybuff);
        System.out.println("cipher:    "+scipherfinal);








    }
}



