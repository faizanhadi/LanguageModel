/**
 * Created by mfaiz on 12/1/2016.
 */

import java.io.File;
import java.io.FileWriter;
import java.util.*;


public class assignmentP3 {

    public static HashMap<Integer, outputStructure> displayStrct = new HashMap<>();
    public static HashMap<String, HashMap<Integer, Double>> docLogMap =null;
    public static int totalCollection =0;

    public static void main (String[] args){
        fileCommands.totalCollection();
        fileCommands.docsTableRead();

        File fileCheck = new File("result.txt");
        if (fileCheck.exists())
            fileCheck.delete();

        while (true) {
            logStructure doClog= new logStructure();
            HashMap<Integer, Double> finalMap = new HashMap<>();

            System.out.println("Enter the queries or give \"EXIT\" to leave the program...");
            Scanner scanner = new Scanner(System.in);
            String query = scanner.nextLine();
            if (query.equals("EXIT")) {
                System.out.println("\n***Leaving program !***");
                System.exit(1);
            }
            docLogMap = new HashMap<String, HashMap<Integer, Double>>();
            String tokenQuery = extractingTokens.tokens(query);
            String[] words = tokenQuery.split("\\s+");
            ArrayList<Integer> docList = new ArrayList<>();

            for (int i =0; i<words.length; i++) {
                String[] lineFound;
                lineFound= fileCommands.fileExtract( words[i]);
                int cf =0;
                int df = 0;
                int offset = 0;
                HashMap<Integer, Integer> postingEntries = new HashMap<>();
                if (lineFound == null) {
                    cf =0;
                    df = 0;
                    offset = 0;
                }

                else {
                    String word = lineFound[0];
                    cf = Integer.parseInt(lineFound[1]);
                    df = Integer.parseInt(lineFound[2]);
                    offset = Integer.parseInt(lineFound[3]);
                    String postingFile = System.getProperty("user.dir") + File.separator + "mhadi1_java_part2"
                            + File.separator + "posting.csv";
                    File file1 = new File(postingFile);
                    if (!file1.exists()) {
                        System.out.println("File posting does not exists");
                        System.exit(1);
                    }

                    postingEntries = fileCommands.postingReturn(file1, offset, df);
                    for (Map.Entry<Integer, Integer> entry : postingEntries.entrySet()) {
                        if (!docList.contains(entry.getKey()))
                            docList.add(entry.getKey());
                    }

                }

                    docLogMap.put(words[i], logStructure.calculateLog(postingEntries, cf));



            }
            /*for (Map.Entry<String, HashMap<Integer, Double>> entry: docLogMap.entrySet()){
                System.out.println(entry.getKey() + " value "+ entry.getValue());
            }
            System.out.println();*/

            int mapSize =0;
            for (Map.Entry<String, HashMap<Integer, Double>> entry: docLogMap.entrySet()) {
                if (mapSize < entry.getValue().size())
                    mapSize = entry.getValue().size();
            }

            for (Map.Entry<String, HashMap<Integer, Double>> entry: docLogMap.entrySet()){
                HashMap<Integer, Double> entryValue = new HashMap<>();
                entryValue = entry.getValue();
                    for (int i=0; i<docList.size(); i++){
                        if ( !(entryValue.containsKey(docList.get(i))) ){
                            String[] lineFound = fileCommands.fileExtract(entry.getKey());
                            //System.out.println("line "+lineFound);
                            int cf=0;
                            if (lineFound==null)
                                cf=0;
                            else
                                cf = Integer.parseInt(lineFound[1]);
                            double logValue = logStructure.zeroLogCalculate(docList.get(i), cf);
                            //System.out.println("v "+logValue);
                            entryValue.put(docList.get(i), logValue);

                        }
                    }
            }
            /*System.out.println();
            for (Map.Entry<String, HashMap<Integer, Double>> entry: docLogMap.entrySet()){
                System.out.println(entry.getKey() + " value "+ entry.getValue());
            }*/

            //for (int i=0;i<docList.size(); i++){
                for (Map.Entry<String, HashMap<Integer, Double>> entry: docLogMap.entrySet()){
                    HashMap<Integer, Double> logMap = new HashMap<>();
                    logMap = entry.getValue();
                    for (Map.Entry<Integer, Double> entry1: logMap.entrySet()){
                        if (finalMap.containsKey(entry1.getKey())){
                            double tempLog = finalMap.get(entry1.getKey());
                            double newLog = tempLog + entry1.getValue();
                            finalMap.put(entry1.getKey(), newLog);
                        }else {
                            finalMap.put(entry1.getKey(), entry1.getValue());
                        }
                    }

                }
            //}
           /* System.out.println("\nfinal V");
            for (Map.Entry<Integer, Double> entry: finalMap.entrySet()){
                System.out.println(entry.getKey() + " value "+ entry.getValue());
            }*/


            Map<Double,Integer> reverMap = new TreeMap(Collections.reverseOrder());
            for (Map.Entry<Integer,Double> entry: finalMap.entrySet()){
                reverMap.put(entry.getValue(), entry.getKey());
            }

            int count =1;
            for (Map.Entry<Double, Integer> entry: reverMap.entrySet()){
                if (count<6) {
                    double probability = entry.getKey();
                    int docID=0;
                    if (entry.getValue() !=null)
                        docID = entry.getValue();
                    fileCommands.writeFile(count, query, docID, probability);
                    count = count + 1;
                }
            }

            System.out.println();
        }

    }
}
