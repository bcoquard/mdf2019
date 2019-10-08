import sun.misc.Perf;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class SautALaPerche {
    public static void main(String[] argv) throws Exception {
        FileInputStream is = new FileInputStream(new File("saut-a-la-perche/input8.txt"));
        System.setIn(is);

        String line;
        Scanner sc = new Scanner(System.in);

        List<String> stringList = new ArrayList<String>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            stringList.add(line);
        }

        int nbEssaiConcours = Integer.parseInt(stringList.get(0));
        List<Performance> performances = new ArrayList<Performance>();
        Map<String, Integer> names = new HashMap<String, Integer>();
        for (int i = 0; i < nbEssaiConcours; i++) {
            String[] currentLine = stringList.get(i + 1).split(" ");

            Integer position = names.get(currentLine[0]);
            if (position == null) {
                names.put(currentLine[0], performances.size());

                Performance performance = new Performance();
                performance.name = currentLine[0];

                performance.nbTries = 1;
                performance.lastSuccess = currentLine[2].equals("S") ? Float.parseFloat(currentLine[1]) : 0f;

                performance.currentTries = currentLine[2].equals("S") ? 0 : 1;
                performance.current = Float.parseFloat(currentLine[1]);

                performances.add(performance);

            } else {
                Performance performance = performances.get(position);

                performance.nbTries = currentLine[2].equals("S") ? performance.currentTries + 1 : performance.nbTries;
                performance.lastSuccess = currentLine[2].equals("S") ? Float.parseFloat(currentLine[1]) : performance.lastSuccess;

                performance.currentTries = currentLine[2].equals("S") ? 1 : performance.currentTries++;
                performance.current = Float.parseFloat(currentLine[1]);
            }
        }

        float maxHeigh = 0f;
        int tries = 0;
        List<Performance> winners = new ArrayList<Performance>();
        for (Performance iter : performances) {
            if (iter.lastSuccess > maxHeigh) {
                winners.clear();

                maxHeigh = iter.lastSuccess;
                tries = iter.nbTries;

                winners.add(iter);
            } else if (iter.lastSuccess == maxHeigh) {
                if (iter.nbTries < tries) {
                    winners.clear();

                    tries = iter.nbTries;

                    winners.add(iter);
                } else if (iter.nbTries == tries) {
                    winners.add(iter);
                }
            }
        }

        if (winners.size() == 1) {
            System.out.print(winners.get(0).name);
        } else {
            System.out.print("KO");
        }
    }

    static class Performance {
        String name;
        float lastSuccess;
        int nbTries;
        float current;
        int currentTries;
    }
}
