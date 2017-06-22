
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by mfaiz on 12/2/2016.
 */
public class fileCommands {
    public static void totalCollection(){
        String totalFile = System.getProperty("user.dir") + File.separator+"mhadi1_java_part2"
                +File.separator+"total.txt";
        File file = new File(totalFile);
        if (!file.exists()) {
            System.out.println("total.txt not found");
            System.exit(1);
        }
        try {
            Scanner scanner = new Scanner(file);
            String[] totalfile = scanner.nextLine().split(" ");
            assignmentP3.totalCollection=Integer.parseInt(totalfile[2]);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String[] fileExtract( String wordSearch){

        String fileDirectory = System.getProperty("user.dir") + File.separator+"mhadi1_java_part2"
                +File.separator+"dictionary.csv";
        File file = new File(fileDirectory);
        if (!file.exists()){
            System.out.println("File dictionary does not exists");
            System.exit(1);
        }

        String[] lineFound = null;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                if (line.contains(wordSearch)){
                    String[] output = line.split(",");
                    if (output[0].equals(wordSearch)) {
                        lineFound=output;
                        //System.out.println(line);
                        break;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("not Found");
            e.printStackTrace();
        }

        return lineFound;
    }

    public static HashMap<Integer, Integer> postingReturn(File file1, int offset, int df){

        HashMap<Integer,Integer> postingMap = new HashMap<>();
            int offsetReal = offset + 1;
            try {
                FileInputStream fs = new FileInputStream(file1);
                BufferedReader bf = new BufferedReader(new InputStreamReader(fs));
                for (int i = 0; i < offset; ++i)
                    bf.readLine();
                for (int j =0; j<df; j++) {
                    String lineOffset = bf.readLine();
                    //System.out.println(lineOffset);
                    String[] outut = lineOffset.split(",");
                    postingMap.put(Integer.parseInt(outut[0]), Integer.parseInt(outut[1]));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return postingMap;
    }

    public static void docsTableRead(){
        String docsFile = System.getProperty("user.dir") + File.separator+"mhadi1_java_part2"
                +File.separator+"docsTable.csv";
        File file = new File(docsFile);
        if (!file.exists()) {
            System.out.println("docsTable.csv not found");
            System.exit(1);
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String[] output = line.split(",");

                    outputStructure outputStructure = new outputStructure(output);
                    assignmentP3.displayStrct.put(Integer.parseInt(output[0]), outputStructure);

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(int count, String query, int docId, Double probability){
        StringBuilder result = new StringBuilder();
        if (count==1)
            result.append(query+"\n");
        if (probability.toString().equals("-Infinity") )
            result.append("No Result\n\n");
        else if (docId !=0) {
            result.append(assignmentP3.displayStrct.get(docId).headline + "\n");
            result.append(assignmentP3.displayStrct.get(docId).path + "\n");
            result.append("Computed probability: " + probability + "\n");
            result.append(assignmentP3.displayStrct.get(docId).snippet + "\n\n");
        }
        else
            result.append("No Result\n\n");
        FileWriter writer =null;
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File("result.txt");
            if (!file.exists())
                file.createNewFile();
            writer = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(result.toString());
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
