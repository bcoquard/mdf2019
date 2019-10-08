import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class PetriMain {


    public static void main(String[] argv) throws Exception {
        FileInputStream is = new FileInputStream(new File("petri/input6.txt"));
        System.setIn(is);

        String line;
        Scanner sc = new Scanner(System.in);

        Grid grid = new Grid();
        int count = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (count == 0) {
                grid.init(Integer.parseInt(line));
            } else {
                grid.populate(line, count - 1);
            }
            count++;
        }

//        grid.print();
        grid.reproduce(false);

        grid.printBest();
    }

    static class Grid {
        Set<Integer>[][] grid;
        int x;
        int y;

        void init(int size) {
            this.x = size;
            this.y = size;
            grid = new HashSet[size][size];
        }

        void populate(String columns, int row) {
            for (int i = 0; i < y; i++) {
                Set<Integer> values = new HashSet<Integer>();
                if (columns.charAt(i) != '.' && columns.charAt(i) != '#') {
                    values.add(Integer.parseInt(columns.charAt(i) + ""));
                }

                grid[row][i] = columns.charAt(i) == '#' ? null : values;
            }
        }

        void reproduce(boolean printStep) {
            Set<Integer>[][] gridInProgress = this.copy();

            boolean changeHappenned = false;

            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    Set<Integer> values = grid[j][i];
                    if (values != null) {
                        for (Integer iter : values) {
                            if (i > 0) {
                                Set<Integer> cellleft = gridInProgress[j][i - 1];
                                if (isAvailable(grid, j, i - 1)) {
                                    cellleft.add(iter);
                                    changeHappenned = true;
                                }
                            }

                            if (j > 0) {
                                Set<Integer> celltop = gridInProgress[j - 1][i];
                                if (isAvailable(grid, j - 1, i)) {
                                    celltop.add(iter);
                                    changeHappenned = true;
                                }
                            }

                            if (i < x - 1) {
                                Set<Integer> cellright = gridInProgress[j][i + 1];
                                if (isAvailable(grid, j, i + 1)) {
                                    cellright.add(iter);
                                    changeHappenned = true;
                                }
                            }

                            if (j < y - 1) {
                                Set<Integer> cellbottom = gridInProgress[j + 1][i];
                                if (isAvailable(grid, j + 1, i)) {
                                    cellbottom.add(iter);
                                    changeHappenned = true;
                                }
                            }
                        }
                    }
                }
            }

            grid = gridInProgress;
            if (printStep) {
                print();
            }

            if (changeHappenned) {
                reproduce(printStep);
            }
        }

        boolean isAvailable(Set<Integer>[][] grid, int y, int x) {
            Set<Integer> cell = grid[y][x];
            if (cell == null) {
                return false;
            } else if (cell.size() == 0) {
                return true;
            } else {
                return false;
            }
        }

        void print() {
            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    System.err.print(grid[j][i] == null ? "[#]" : grid[j][i]);
                }
                System.err.print("\n");
            }
            System.err.println();
        }

        Set<Integer>[][] copy() {
            Set<Integer>[][] res = new Set[y][x];
            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    if (grid[j][i] != null) {
                        Set<Integer> set = new HashSet<Integer>();
                        for (Integer iter : grid[j][i]) {
                            set.add(iter);
                        }
                        res[j][i] = set;
                    }
                }
            }

            return res;
        }

        void printBest() {
            Map<Integer, Integer> result = new HashMap<Integer, Integer>();
            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    Set<Integer> bacteries = grid[j][i];
                    if (bacteries != null && bacteries.size() == 1) {
                        for (Integer iter : bacteries) {
                            if (result.get(iter) == null) {
                                result.put(iter, 0);
                            }
                            result.replace(iter, result.get(iter) + 1);
                        }
                    }
                }
            }

            int max = 0;
            for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                }
            }

            System.out.println(max);
        }
    }
}