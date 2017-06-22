import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mfaiz on 12/2/2016.
 */

public class logStructure {
    HashMap<Integer, Integer> logCalc = new HashMap<>();

    public static HashMap<Integer, Double> calculateLog(HashMap<Integer, Integer> postingList, int cf){
        HashMap<Integer, Double> logValueMap = new HashMap<>();

        if (postingList.size()>0) {
            for (Map.Entry<Integer, Integer> entry : postingList.entrySet()) {

                int doclength = assignmentP3.displayStrct.get(entry.getKey()).doclength;
                double num1T = (double) entry.getValue() / (double) doclength;
                double num1 = 0.9 * num1T;
                double num2 = 0.1 * ((double) cf / (double) assignmentP3.totalCollection);
                double logValue = Math.log(num1 + num2) / Math.log(2);

                logValueMap.put(entry.getKey(), logValue);

            }
        }else {
            double num1T = (double) 0;
            double num1 = 0.9 * num1T;
            double num2 = 0.1 * ((double) cf / (double) assignmentP3.totalCollection);
            double logValue = Math.log(num1 + num2) / Math.log(2);

            logValueMap.put(postingList.get(0), logValue);
        }

        return  logValueMap;

    }

    public static Double zeroLogCalculate (int docId, int cf){
        int docLenth = assignmentP3.displayStrct.get(docId).doclength;

        double num1 = 0.9*(0/(double)docLenth) + 0.1*((double)cf / (double)assignmentP3.totalCollection);
        double logValue = Math.log(num1) / Math.log(2);
        return logValue;
    }

}
