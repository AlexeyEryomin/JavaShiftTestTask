import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.io.IOException;


public class Main {
    // Лист в который будут записаны все строки если выбран строкоый метод сортировки
    public static ArrayList<String> stringSort = new ArrayList<>();

    // Лист в который будут сохранены все числа если будет выбран числовой метод сортировки
    public static ArrayList<Integer> integerSort = new ArrayList<>();

    // Лист где сохраняем все найденные пути входящих txt файлов
    public static ArrayList<Path> txtPaths = new ArrayList<>();

    // Переменная которая будет хранить тип сортировки числовой или строковый
    // Если 0 тип сортировки указан не был, если 1 то тип сортировки числовой, если 2 то тип сортировки строковый
    public static int typeSort = 0;

    // Переменная хранит в себе информацию о том было ли введено название выходного файла
    // Если значение 0 то пользователь забыл ввести название выходного файла если 1 то нет
    public static int haveOutFileName = 0;

    // Переменная определит путь для выходящего файла
    public static Path outPath;

    // Переменная в которой сохраним информацию в прямой или обратной последовательности будем записывать файл на выход
    // 0  - это прямой, 1 - это обратный, по умолчанию прямой
    public static int isReverse = 0;

    // Метод проверяем является ли сортировка числовой или строковой
    public static void isStringOrInteger (String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-i")) {
                typeSort = 1; // числовой
                System.out.println("Вы выбрали числовой метод сортировки");
            }
            if (args[i].equals("-s")) {
                typeSort = 2; // строковый
                System.out.println("Вы выбрали стороквый метод сортировки");
            }
        }
    }

    // Проверяем был ли указан тип сортировки если типов было указано два это тоже ошибка
    public static void setupType(String[] args) {
        for (int i = 0; i < args.length; i++) {
            for (int k = 0; k < args.length; k++) {
                if (args[i].equals("-i") && args[k].equals("-s")) {
                    System.out.println("Перезапустите программу и введите только одно условие -s для строк или -i для чисел");
                    System.exit(0);
                }
            }
        }
        if (typeSort == 0) {
            System.out.println("Вы не указали тип сортировки перезапустите программу и введите только одно условие -s для строк или -i для чисел");
            System.exit(0);
        }
    }

    // Метод проверяем что пользователь ввел название выходного файла, а так же заменим в названии файла формат out на txt
    public static void checkOutName(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("out")) {
            outPath = Path.of(args[i].replaceAll("out", "txt"));
            haveOutFileName = 1;
            }
        }
        if (haveOutFileName == 0) {
            System.out.println("Перезапустите программу и обязательно укажите путь и название файла в формате C:\\path\\file.out - где C:\\path это путь где будет сохранен файл директория обязательно должна существовать, а file.out где file это название файла и оно может быть любым, а out(менять нельзя - это не расширение, файл будет по умолчанию сохранен в формате txt) указывает программе где брать путь для сохранения");
            System.exit(0);
        }
    }

    // Метод проверим прямой будет порядок или обратный по умолчанию прямой
    public static void issReverse(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-d")) {
                isReverse = 1;
                System.out.println("Вы выбрали обратный порядок записи");
            }
        }
        if (isReverse == 0) System.out.println("Вы выбрали прямой порядок по умолчанию");
    }

    // Метод получаем массив путей из аргументов консоли
    public static void getFIleList(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("txt")) {
                txtPaths.add(Path.of(args[i]));
                System.out.println("Найден путь " + args[i] + " добавляем его в список расположений файлов для сортировки");
            }
        }
    }

    // Метод извлекаем добавляем строки из всех файлов txt c помощью входящего потока
    public static void txtInjection (ArrayList<Path> paths) {
        for (int i = 0; i < paths.size(); i++) {
                try (BufferedReader reader = Files.newBufferedReader(paths.get(i))) {
                    while (reader.ready()) {
                        String q = reader.readLine(); // Временная переменная в которую сохраним строку извлекания txt
                        if (typeSort == 1 && q.matches("-?\\d+(\\\\d+)?")) {
                            try {
                                integerSort.add(Integer.parseInt(q));
                            } catch (NumberFormatException e) {
                                System.out.println("Число " + q + " выходит за рамки типа int и будет пропущено");
                                continue;
                            }
                        }
                        if (typeSort == 2 && q.chars().allMatch(Character::isLetter)) {
                            stringSort.add(q);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    // Основной метод тестового задания сортировка слиянием для числовой сортировки
    public static void mergeSort(ArrayList<Integer> a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        ArrayList<Integer> l = new ArrayList<>();
        ArrayList<Integer> r = new ArrayList<>();

        for (int i = 0; i < mid; i++) {
            l.add(i, a.get(i));
        }
        for (int i = mid; i < n; i++) {
            r.add(i - mid, a.get(i));
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }

    // Метод - этот метод часть метода сортировки слиянием для числовой сортировки
    public static void merge(
            ArrayList<Integer> a, ArrayList<Integer> l, ArrayList<Integer> r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l.get(i) <= r.get(j)) {
                a.set(k++, l.get(i++));
            }
            else {
                a.set(k++, r.get(j++));
            }
        }
        while (i < left) {
            a.set(k++, l.get(i++));
        }
        while (j < right) {
            a.set(k++, r.get(j++));
        }
    }

    // Основной метод тестового задания сортировка слиянием для строковой сортировки
    public static void stringMergeSort(ArrayList<String> a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        ArrayList<String> l = new ArrayList<>();
        ArrayList<String> r = new ArrayList<>();

        for (int i = 0; i < mid; i++) {
            l.add(i, a.get(i));
        }
        for (int i = mid; i < n; i++) {
            r.add(i - mid, a.get(i));
        }
        stringMergeSort(l, mid);
        stringMergeSort(r, n - mid);

        stringMerge(a, l, r, mid, n - mid);
    }

    // Метод - этот метод часть метода сортировки слиянием для числовой сортировки
    public static void stringMerge(
            ArrayList<String> a, ArrayList<String> l, ArrayList<String> r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l.get(i).length() <= r.get(j).length()) {
                a.set(k++, l.get(i++));
            }
            else {
                a.set(k++, r.get(j++));
            }
        }
        while (i < left) {
            a.set(k++, l.get(i++));
        }
        while (j < right) {
            a.set(k++, r.get(j++));
        }
    }

    // Метод который запишет наш результат в файл
    public static void resultWriter() {
        if (typeSort == 1 && isReverse == 0) {
            File file = new File(String.format("%s", outPath));
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try(BufferedWriter writer = Files.newBufferedWriter(outPath)) {
                for (int i = 0; i < integerSort.size(); i++) {
                    writer.write(String.format("%s", integerSort.get(i)));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (typeSort == 1 && isReverse == 1) {
            File file = new File(String.format("%s", outPath));
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try(BufferedWriter writer = Files.newBufferedWriter(outPath)) {
                for (int i = integerSort.size() - 1; i >= 0; i--) {
                    writer.write(String.format("%s", integerSort.get(i)));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (typeSort == 2 && isReverse == 0) {
            File file = new File(String.format("%s", outPath));
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try(BufferedWriter writer = Files.newBufferedWriter(outPath)) {
                for (int i = 0; i < stringSort.size(); i++) {
                    writer.write(String.format("%s", stringSort.get(i)));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (typeSort == 2 && isReverse == 1) {
            File file = new File(String.format("%s", outPath));
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try(BufferedWriter writer = Files.newBufferedWriter(outPath)) {
                for (int i = stringSort.size() - 1; i >= 0; i--) {
                    writer.write(String.format("%s", stringSort.get(i)));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public static void main(String[] args) {
        isStringOrInteger(args);
        setupType(args);
        checkOutName(args);
        issReverse(args);
        getFIleList(args);
        txtInjection(txtPaths);
        // Если сортировка числовая то фильтруем строки на числа и копируем в лист integerSort
        if (typeSort == 1) {
        mergeSort(integerSort, integerSort.size()); }
        // Если сортировка строковая то фильтруем строки на строки и копируем в лист stringSort
        if (typeSort == 2) {
            stringMergeSort(stringSort, stringSort.size()); }
        resultWriter();
    }
}