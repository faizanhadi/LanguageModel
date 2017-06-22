import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mfaiz on 12/1/2016.
 */
public class extractingTokens {
    public static String tokens(String query){
        String removeSpace = null;

        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(query);
        removeSpace = matcher.replaceAll(" ");


        String returnQuery = new String();
        Scanner dScan = new Scanner(removeSpace);
        while ((dScan.hasNext())) {
            String tempData = dScan.next();
            String tempLowerCase = tempData.toLowerCase();
            String tempcheckStop1 = CheckStopWords(tempLowerCase);
            if (tempcheckStop1 != null) {
                String startWithM = StartWithMarks(tempcheckStop1);
                String endWithM = EndsWithMarks(startWithM);
                String tempcheckStop2 = CheckStopWords(endWithM);
                if (tempcheckStop2 != null) {
                    String PluralStemCheck = PluralStemming(tempcheckStop2);
                    String singleChar = SingleCharCheck(PluralStemCheck);
                    if (singleChar!=null) {
                        String finalString = SpecialChar(singleChar);
                        String finalSpecialCharRem = SpecialChar(finalString);

                        if (returnQuery.isEmpty()){
                            returnQuery = finalSpecialCharRem;
                        }else {
                            returnQuery = returnQuery + " ";
                            returnQuery = returnQuery + finalSpecialCharRem;
                            //***************************
                        }
                    }
                }
            }
        }
        dScan.close();
        return returnQuery;
    }

    public static String SpecialChar (String sChar){

        return sChar.replaceAll("[^A-Za-z0-9]","");
    }

    public static String CheckStopWords(String stopWord){
        if (stopWord.equals("and") || stopWord.equals("a") || stopWord.equals("the") ||
                stopWord.equals("an") ||stopWord.equals("by")|| stopWord.equals("from") || stopWord.equals("for") ||
                stopWord.equals("hence") || stopWord.equals("of") || stopWord.equals("with") || stopWord.equals("in")
                || stopWord.equals("within") || stopWord.equals("who") || stopWord.equals("when")
                || stopWord.equals("where") || stopWord.equals("why") || stopWord.equals("how") || stopWord.equals("whom")
                || stopWord.equals("have") || stopWord.equals("had")  || stopWord.equals("has") || stopWord.equals("not")
                || stopWord.equals("for") || stopWord.equals("but") || stopWord.equals("do") || stopWord.equals("does")
                || stopWord.equals("done"))
            return null;
        else
            return stopWord;
    }

    public static String EndsWithMarks (String markCheck){

        String markWord= SStemming(markCheck);

        String newWord=null;
        boolean flag = false;
        while (flag==false){
            if ( markWord.endsWith("'") || markWord.endsWith("\"") || markWord.endsWith(")") || markWord.endsWith("]") ||
                    markWord.endsWith(",") || markWord.endsWith(".") || markWord.endsWith("?") || markWord.endsWith(":") ||
                    markWord.endsWith(";") || markWord.endsWith("!") || markWord.endsWith("(") || markWord.endsWith("[")
                    || markWord.endsWith("$") || markWord.endsWith("&"))
            {
                markWord = markWord.substring(0, markWord.length() - 1);
            }
            if ( markWord.endsWith("'") || markWord.endsWith("\"") || markWord.endsWith(")") || markWord.endsWith("]") ||
                    markWord.endsWith(",") || markWord.endsWith(".") || markWord.endsWith("?") || markWord.endsWith(":") ||
                    markWord.endsWith(";") || markWord.endsWith("!") || markWord.endsWith("(") || markWord.endsWith("[")
                    || markWord.endsWith("$") || markWord.endsWith("&"))
            {
                flag=false;
            }
            else {
                flag=true;
                newWord=markWord;
            }
        }
        return newWord;
    }

    public static String StartWithMarks (String markWord){
        String newWord=null;
        boolean flag = false;
        while (flag==false){
            if (markWord.startsWith("'") || markWord.startsWith("\"") || markWord.startsWith("(") || markWord.startsWith("[")
                    || markWord.startsWith(")") || markWord.startsWith("]") || markWord.startsWith("`")
                    || markWord.startsWith("$") || markWord.startsWith("&"))
            {
                markWord = markWord.substring(1, markWord.length());
            }
            if (markWord.startsWith("'") || markWord.startsWith("\"") || markWord.startsWith("(") || markWord.startsWith("[")
                    || markWord.startsWith(")") || markWord.startsWith("]") || markWord.startsWith("`")
                    || markWord.startsWith("$") || markWord.startsWith("&")){
                flag=false;
            }
            else {
                flag=true;
                newWord=markWord;
            }
        }


        return newWord;
    }

    public static String SingleCharCheck (String charCheck){
        if (charCheck.length()==1)
            return null;
        else
            return charCheck;
    }

    public static String SStemming(String sstem){
        if(sstem !=null) {
            if (sstem.endsWith("'s") || sstem.endsWith("s'"))
                sstem = sstem.substring(0, sstem.length() - 2);
        }
        return sstem;
    }

    public static String PluralStemming (String stem){

        if(stem.endsWith("ies") && !((stem.endsWith("eies") || stem.endsWith("aies") ))){
            stem=stem.substring(0,stem.length()-3);
            stem=stem+"y";
        }
        else if (stem.endsWith("es") && !(stem.endsWith("aes") || stem.endsWith("ees") || stem.endsWith("oes"))){
            stem=stem.substring(0,stem.length()-1);
        }
        else if (stem.endsWith("s") && !(stem.endsWith("us") || stem.endsWith("ss") )){
            stem=stem.substring(0,stem.length()-1);
        }
        return stem;
    }



}
