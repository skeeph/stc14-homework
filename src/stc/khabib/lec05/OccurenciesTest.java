package stc.khabib.lec05;

import java.io.IOException;

public class OccurenciesTest {
    public static void main(String[] args) throws IOException {
        Occurencies oc = new Occurencies();
        oc.getOccurencies(
                new String[]{
//                        "./generated/1.txt",
                        "https://www.ietf.org/rfc/rfc2616.txt"
                },
                new String[]{"эртугрул", "текфур", "canonical", "age"},
                "found.txt"
        );
    }
}
