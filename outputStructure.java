import java.util.HashMap;

/**
 * Created by mfaiz on 12/2/2016.
 */
public class outputStructure {

    int docID =0;
    String headline =null;
    int doclength =0;
    String snippet = null;
    String path =null;

    public outputStructure(String[] contents){

        docID = Integer.parseInt(contents[0]);
        headline = contents[1];
        doclength = Integer.parseInt(contents[2]);
        snippet = contents[3];
        path = contents[4];


    }


}
