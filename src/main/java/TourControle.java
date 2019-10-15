import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class TourControle {

    public static void main(String[] argv) throws Exception {
        FileInputStream is = new FileInputStream(new File("tour-controle/input4.txt"));
        System.setIn(is);

        String line;
        Scanner sc = new Scanner(System.in);

        List<String> inputList = new ArrayList<String>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            inputList.add(line);
        }

        int nbAtterrissagesJour = Integer.parseInt(inputList.get(0));
        int nbAtterrissagesMax45Min = Integer.parseInt(inputList.get(1));

        Map<Integer, Piste> pistes = new HashMap<>();
        boolean COLLISION = false;

        for (int i = 0; i < nbAtterrissagesJour; i++) {
            String atterissage = inputList.get(i + 2);
            String horaire = atterissage.split(" ")[0];
            Integer piste = Integer.parseInt(atterissage.split(" ")[1]);
            if (!pistes.containsKey(piste)) {
                pistes.put(piste, new Piste(piste, nbAtterrissagesMax45Min));
            }

            if (!pistes.get(piste).atterrissage(horaire)) {
                COLLISION = true;
                break;
            }
        }

        System.out.println(COLLISION ? "COLLISION" : "OK");
    }

    static class Piste {
        Integer numero;
        Integer nb45Glissant;
        List<Integer> last45min = new ArrayList<>();

        Integer heureDernierAtr = 0;
        Integer minutesDernierAtr = 0;

        public Piste(int numero, int glissant) {
            this.numero = numero;
            this.nb45Glissant = glissant;
        }

        public boolean atterrissage(String time) {
            Integer heures = Integer.parseInt(time.split(":")[0]);
            Integer minutes = Integer.parseInt(time.split(":")[1]);

            Integer allminutes = heures * 60 + minutes;
            Integer allminutesDernier = heureDernierAtr * 60 + minutesDernierAtr;

            if (allminutes - allminutesDernier < 6) {
                return false;
            } else {
                if (allminutes - allminutesDernier < 45) {
                    List<Integer> newlast45min = new ArrayList<>();
                    for (Integer iter : last45min) {
                        if (allminutes - iter <= 45) {
                            newlast45min.add(iter);
                        }
                    }

                    last45min = newlast45min;
                    if (last45min.size() >= nb45Glissant) {
                        return false;
                    } else {
                        last45min.add(allminutes);
                    }

                } else {
                    last45min.clear();
                    last45min.add(allminutes);
                }

            }

            setTime(heures, minutes);
            return true;
        }

        public void setTime(Integer heure, Integer minutes) {
            this.heureDernierAtr = heure;
            this.minutesDernierAtr = minutes;
        }
    }
}
