
public class Case {

    public static String lower(String str){
        str = str.toLowerCase();
        return str;
    }

    public static String upper(String str){
        str = str.toUpperCase();
        return  str;
    }

    public static String capital(String str){
        String something = str;
        something  = something.trim();
        String[] regualte = something.split(" ");
        StringBuffer cipher = new StringBuffer();
        for(int i =0 ; i < regualte.length; i++){
            String reg = regualte[i];
            reg = reg.substring(0,1).toUpperCase() + reg.substring(1).toLowerCase();
            cipher.append(reg);
            cipher.append(" ");
        }

        String ciphertext = cipher.toString();

        return ciphertext;
    }

    public static String inverse(String str){

        String test = str;
        StringBuffer inverz = new StringBuffer();
        for(int i = 0; i < test.length(); i++){
            if(test.charAt(i) >= 97 && test.charAt(i) <= 122){
                inverz.append(test.substring(i,i+1).toUpperCase());
            }else if
            (test.charAt(i) >= 65 && test.charAt(i) <= 90){
                inverz.append(test.substring(i, i + 1).toLowerCase());
            } else{
                inverz.append(test.charAt(i));
            }
        }
        String cipher = inverz.toString();

        return cipher;
    }

    public static String alternating(String str){

        String test = str;
        test = test.trim();
        StringBuffer cipher = new StringBuffer();
        for(int i = 0; i < test.length(); i++){
            if(i % 2 == 0){
                cipher.append(test.substring(i,i+1).toLowerCase());
            }else {
                cipher.append(test.substring(i,i+1).toUpperCase());
            }
        }
        String ciphertext = cipher.toString();
        return ciphertext;

    }


    public static void main(String[] args){

        String type = args[0];
        String text = args[1];

        if(type.equals("lower")){
            System.out.println(Case.lower(text));

        }else if(type.equals("upper")){
            System.out.print(Case.upper(text));
        }else if(type.equals("capitalize")){
            System.out.println(Case.capital(text));
        }else if(type.equals("inverse")){
            System.out.println(Case.inverse(text));
        }else if(type.equals("alternating")){
            System.out.println(Case.alternating(text));
        }else{
            System.out.println("Zgjidh:lower,upper,captial,inverse,alternating");
        }
    }

}
