import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InvestissementCrypto {

    public static void main(String[] argv) throws Exception {
        FileInputStream is = new FileInputStream(new File("investissement-crypto/input2.txt"));
        System.setIn(is);

        String line;
        Scanner sc = new Scanner(System.in);

        List<String> inputList = new ArrayList<String>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            inputList.add(line);
        }

        int nbJourHistorique = Integer.parseInt(inputList.get(0));
        Crypto bitcoin = new Crypto();
        Crypto etercoin = new Crypto();

        for (int i = 0; i < nbJourHistorique; i++) {
            String variations = inputList.get(i + 1);
            bitcoin.add(Integer.parseInt(variations.split(" ")[0]));
            etercoin.add(Integer.parseInt(variations.split(" ")[1]));
        }

        Integer bitcoinVar = bitcoin.calculateVariation();
        Integer eterVar = etercoin.calculateVariation();

        System.err.println(bitcoinVar + " " + eterVar);

        if (bitcoinVar == eterVar) {
            System.out.println("KO");
        } else if (bitcoinVar < eterVar) {
            System.out.println("BITCOIN");
        } else {
            System.out.println("ETHEREUM");
        }
    }

    static class Crypto {
        List<Integer> cours = new ArrayList<>();
        List<Integer> variations = new ArrayList<>();

        public void add(Integer cours) {
            this.cours.add(cours);
        }

        public Integer calculateVariation() {
            Integer currentVar = null;
            String lastDirection = null;

            for (Integer iter : cours) {
                String currentDirection;
                if (currentVar == null) {
                    currentVar = iter;
                } else {
                    Integer newVar = iter - currentVar;

                    if (newVar == 0) {
                        currentDirection = "NULL";
                    } else if (newVar > 0) {
                        currentDirection = "UP";
                    } else {
                        currentDirection = "DOWN";
                    }

                    if (lastDirection == null || (!"NULL".equals(currentDirection) && !lastDirection.equals(currentDirection))) {
                        variations.add(newVar);
                    }

                    currentVar = iter;
                    lastDirection = currentDirection;
                }
            }

            return variations.size();
        }
    }
}
