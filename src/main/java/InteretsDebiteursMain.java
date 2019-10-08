import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InteretsDebiteursMain {

    public static void main(String[] argv) throws Exception {
        FileInputStream is = new FileInputStream(new File("interets-debiteurs/input6.txt"));
        System.setIn(is);

        String line;
        Scanner sc = new Scanner(System.in);


        List<String> stringList = new ArrayList<String>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            stringList.add(line);
        }

        float interestNew = 0f;
        float interestOld = 0f;
        int balance = Integer.parseInt(stringList.get(1));
        int nbDaysNeg = 0;

        for (int i = 0; i < Integer.parseInt(stringList.get(0)); i++) {
            balance += Integer.parseInt(stringList.get(i + 2));
            if (balance < 0) {
                nbDaysNeg++;
            } else {
                nbDaysNeg = 0;
            }

            if (nbDaysNeg > 0 && nbDaysNeg < 3) {
                interestNew += Math.abs(balance) * 0.2;
            } else if (nbDaysNeg == 3) {
                interestOld += Math.abs(balance) * 0.1;
                interestNew += Math.abs(balance) * 0.2;
            } else if (nbDaysNeg > 3) {
                interestOld += Math.abs(balance) * 0.1;
                interestNew += Math.abs(balance) * 0.3;
            }
        }

        System.out.println(Math.round(interestNew - interestOld));
    }
}
