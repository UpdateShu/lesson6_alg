

public class MainApp {

    public static void main(String[] args) {

        printAlphabet();
        MyTreeMap<Integer, String> map = createRandomTree(3);
        printMap(map);

        int k = 100000;
        int s = 0;
        for (int i = 0; i < k; i++) {
            map = createRandomTree(6);
            if (map.isBalanced()) {
                s++;
            }
        }
        double procent = (double) s * 100/ k;
        //String formattedDouble = new DecimalFormat("#0.000").format(procent);
        System.out.println("Из " + k + " случайно созданных бинарных деревьев "
                + s + " являются сбалансированными (" + procent + "%)");
    }

    static MyTreeMap<Integer, String> createRandomTree(int maxLevel) {
        MyTreeMap<Integer, String> map = new MyTreeMap<>();
        int level = 0;
        do {
            int k = (int)(Math.random() * 200) - 100;
            map.put(k, "*");
            level = map.lastLevel();
        }
        while (level < maxLevel + 1);
        map.deleteLastNode();
        return map;
    }

    static void printAlphabet() {
        MyTreeMap<Integer, String> map = new MyTreeMap<>();
        printMap(map);

        map.put(20, "T");
        printMap(map);

        map.put(10, "J");
        map.put(4, "D");
        map.put(17, "Q");
        printMap(map);

        map.put(2, "B");
        printMap(map);

        map.put(5, "E");
        map.put(3, "C");
        map.put(8, "H");
        map.put(6, "F");
        map.put(7, "G");
        map.put(9, "I");
        map.put(1, "A");
        map.put(13, "M");
        map.put(19, "S");
        map.put(16, "P");
        map.put(12, "L");
        map.put(18, "R");
        map.put(14, "N");
        map.put(15, "O");
        map.put(11, "K");

        map.put(24, "X");
        map.put(26, "Y");
        map.put(25, "Z");
        map.put(22, "V");
        map.put(23, "W");
        map.put(21, "U");

        printMap(map);
    }

    static void printMap(MyTreeMap<Integer, String> map) {
        System.out.println(map);
        System.out.println("Высота дерева = " + map.getHeight());
        System.out.println(map.isBalanced() ? "Дерево сбалансировано" : "Дерево несбалансировано");
        System.out.println();
    }
}
