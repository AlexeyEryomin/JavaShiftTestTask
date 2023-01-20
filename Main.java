
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.File;
import java.io.PrintWriter;




public class Main {

    public static ArrayList<ArrayList<String> > listAfterCheckInt = new ArrayList<>(); //2d здесь будут сохраняться числа после проверки массива на то что там только числа

    public static ArrayList<String> tempList = new ArrayList<>();

    public static ArrayList<ArrayList<String> > listAfterCheckString = new ArrayList<>(); //2d здесь будут сохраняться строки после проверки массива на то что там только строки

    public static ArrayList<String> txtLists = new ArrayList<>(); //создадим лист в которой будут записаны пути к файлам txt
    public static ArrayList<ArrayList<String> > txtAfterScan = new ArrayList<>(); // 2d лист в котором сохраним отсканированные файлы txt (вложенным массив нужен для того чтобы можно было сохранить разделение между данными из файлов)


    public static void getFileList(String[] files) { //создадим метод который просканирует все аргументы введенные из консоли на наличие строки с содержанием txt и добавим ее в txtLists(пути файлов txt)
        System.out.println(); // разделить для удобства чтения информации из консоли
        for (int i = 0; i < files.length; i++) {
            if (files[i].contains("txt")) {
                System.out.println("Найден путь " + files[i] + " добавляем его в список расположений файлов для сортировки");
                txtLists.add(files[i]);
            }
        }
        System.out.println(); // разделить для удобства чтения информации из консоли
        System.out.println("Вы добавили " + txtLists.size() + " расположений для файлов txt");
        System.out.println(); // разделить для удобства чтения информации из консоли
        System.out.println((String.join(" , ", txtLists)) + " В консоль выведен список ArrayList сформированный из введенных путей файлов");
        System.out.println(); // разделить для удобства чтения информации из консоли
    }

    public static ArrayList<String> txtToArrayList(String transform) { // txt в лист
        try {
            String[] tempList = Files.newBufferedReader(Paths.get(transform), StandardCharsets.UTF_8).lines().toArray(String[]::new); //временный массив для обработки txt

            ArrayList<String> list = new ArrayList<>();

            Collections.addAll(list, tempList); // Да, я знаю, что результат можно было записывать как-то в ArrayList сразу без конвертации, но это заработало сразу и что-то менять было рисково

            return list;

        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Вероятнее всего указанный путь к файлу txt неверный или txt файл не существует и будет пропущен");
            System.out.println(); // разделить для удобства чтения информации из консоли
        }
        return new ArrayList<>();
    }


    public static void checkForIntTypeAndRewrite(ArrayList<ArrayList<String>> rewrite) { // убираем все что не является целым числом
        System.out.println("Запущена проверка на то является ли строка числом");
        for (int i = 0; i < rewrite.size(); i++) {
            ArrayList<String> firstDimensionList = new ArrayList<>(); //первое измерение чисел
            for (int j = 0; j < rewrite.get(i).size(); j++) {

                String check = rewrite.get(i).get(j);
                boolean ifIntTarget = check.matches("-?\\d+(\\\\d+)?");
                if (ifIntTarget) {
                    System.out.println("Является числом и добавляется для последующей работы " + check);
                    //rewrite.get(i).remove(j); \\как говорил Эммет Браун - эта пакость не работает
                    firstDimensionList.add(check);
                }
            }
            if (firstDimensionList.size() == 0) continue;
            listAfterCheckInt.add(firstDimensionList);
        }
    }

    public static void checkForStringTypeAndRewrite(ArrayList<ArrayList<String>> rewrite) { // убираем все что не является строкой
        System.out.println();
        System.out.println("Запущена проверка на то является ли строка числом, строки которые не являются числом, содержат пробел или какой-либо знак препинания будут удалены");
        for (int i = 0; i < rewrite.size(); i++) {
            ArrayList<String> firstDimensionList = new ArrayList<>(); //первое измерение чисел
            for (int j = 0; j < rewrite.get(i).size(); j++) {

                String check = rewrite.get(i).get(j);
                boolean ifIntTarget = check.chars().allMatch(Character::isLetter);
                if (ifIntTarget) {
                    System.out.println("состоит из букв и добавляется для последующей работы " + check);
                    //rewrite.get(i).remove(j); \\как говорил Эммет Браун - эта пакость не работает
                    if (check.equals("")) continue;
                    firstDimensionList.add(check);
                }
            }
            if (firstDimensionList.size() == 0) continue;
            listAfterCheckString.add(firstDimensionList);
        }
    }

    /*public static boolean isaStringMethod(String[] methodscanner) {
        int x = 0; // стоямба для условия
        while (x != 1 || x != 2) {
            for (int i = 0; i < methodscanner.length; i++) {
                if (methodscanner[i].contains("-s")) {
                    x = 1;
                    return true;
                } else if (methodscanner[i].contains("-i")) {
                    x = 2;
                    return false;
                } else {
                    System.out.println("Перезапустите программу и введите условие -s для строк или -i для чисел");
                    System.exit(0);
                }
            }
        }
        return false;
    }*/

    public static void tempIntList(ArrayList<ArrayList<String> > separate) {

        for (int i = 0; i < separate.size(); i++) {
            for (int j = 0; j < separate.get(i).size(); j++) {
                tempList.add(separate.get(i).get(j));
            }
        }
    }

    public static void mergeSort(String[] src, int n) {
        if (n < 2) return;

        int mid = n / 2;
        String[] left = new String[mid];
        String[] right = new String[n - mid];

        System.arraycopy(src, 0, left, 0, mid);
        System.arraycopy(src, mid, right, 0, n - mid);


        mergeSort(left, mid);
        mergeSort(right, n - mid);
        merge(src, left, right, mid, n - mid);

    }

    public static void stringMergeSort(String[] src, int n) {
        if (n < 2) return;

        int mid = n / 2;
        String[] left = new String[mid];
        String[] right = new String[n - mid];

        System.arraycopy(src, 0, left, 0, mid);
        System.arraycopy(src, mid, right, 0, n - mid);


        stringMergeSort(left, mid);
        stringMergeSort(right, n - mid);
        stringMerge(src, left, right, mid, n - mid);

    }

    public static void stringMerge(String[] src, String[] left, String[] right, int leftLength, int rightLength) {
        int k = 0, i = 0, j = 0;

        while (i < leftLength && j < rightLength) {
            if (left[i].length() <= right[j].length()) src[k++] = left[i++];
            else src[k++] = right[j++];
        }
        while (i < leftLength) {
            src[k++] = left[i++];
        }
        while (i < leftLength) {
            src[k++] = right[j++];
        }
    }

    public static void merge(String[] src, String[] left, String[] right, int leftLength, int rightLength) {
        int k = 0, i = 0, j = 0;

        while (i < leftLength && j < rightLength) {
            if (Integer.parseInt(left[i]) <= Integer.parseInt(right[j])) src[k++] = left[i++];
            else src[k++] = right[j++];
        }
        while (i < leftLength) {
            src[k++] = left[i++];
        }
        while (i < leftLength) {
            src[k++] = right[j++];
        }
    }





    public static void main(String[] args) {
        getFileList(args);

        int isaStringMethod = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-s")) {
                isaStringMethod = 1;
            }
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-i")) {
                isaStringMethod = 3;
        }}

        if (isaStringMethod == 0) {
            System.out.println("Перезапустите программу и введите условие -s для строк или -i для чисел");
            System.exit(0);
        }

        for (int i = 0; i < txtLists.size(); i++) {
            txtAfterScan.add(txtToArrayList(txtLists.get(i)));
        }

        System.out.println((txtAfterScan) + " проверяем что создали вложенный arraylist");
        System.out.println(); // разделить для удобства чтения информации из консоли

        if (isaStringMethod == 1) {
            checkForStringTypeAndRewrite(txtAfterScan);
            tempIntList(listAfterCheckString);
            String[] tempArray = new String[tempList.size()];
            tempList.toArray(tempArray);
            stringMergeSort(tempArray, tempArray.length);
            ArrayList<String> finalTemporaryList = new ArrayList<String>(Arrays.asList(tempArray));
            System.out.println(); // разделить для удобства чтения информации из консоли
            System.out.println(finalTemporaryList + " массив без проверки предварительно сортированного ввода данных");


            int uk = 0;
            while (uk < finalTemporaryList.size()) {
                if (uk == finalTemporaryList.size() - 1) break;
                if (finalTemporaryList.get(uk + 1).length() < finalTemporaryList.get(uk).length()) {
                    finalTemporaryList.remove(uk + 1);
                    continue;
                }
                uk++;
            }

            System.out.println(); // разделить для удобства чтения информации из консоли
            System.out.println(finalTemporaryList + " итоговый список для вывода его в файл после проверки несортированных данных возможна потеря данных");
            String path = "C:\\out.txt"; //путь и имя файла по умолчанию
            int checkout = 0; //проверка на наличие ввода названия в командной строке
            for (int i = 0; i < args.length; i++) {
                if (args[i].contains("out")) {
                    path = args[i].replaceAll("out", "txt");
                    checkout = 1;
                }
            }

            if (checkout != 1) {
                System.out.println("Перезапустите программу и обязательно укажите путь и название файла в формате C:\\path\\file.out - где C:\\path это путь где будет сохранен файл директория обязательно должна существовать, а file.out где file это название файла и оно может быть любым, а out(менять нельзя - это не расширение, файл будет по умолчанию сохранен в формате txt) указывает программе где брать путь для сохранения");
                System.exit(0);
            }


            int forvardOrReverse = 0; // 0 это прямой 1 это обратный
            for (int i = 0; i < args.length; i++) {
                if (args[i].contains("-d")) {
                    forvardOrReverse = 1;
                }
            }



            if (forvardOrReverse == 1) {
                try {
                    Collections.reverse(finalTemporaryList);
                    File file = new File(String.format("%s", path));
                    if (!file.exists()) {
                        file.createNewFile();
                        System.out.println();
                        System.out.println("Cоздан с обратным порядком");
                    }
                    PrintWriter pw = new PrintWriter(file);
                    for (String i : finalTemporaryList) {
                        pw.println(i);
                    }
                    pw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    File file = new File(String.format("%s", path));

                    if (!file.exists()) {
                        file.createNewFile();
                        System.out.println();
                        System.out.println("Cоздан с прямым порядком");
                    }
                    PrintWriter pw = new PrintWriter(file);
                    for (String i : finalTemporaryList) {
                        pw.println(i);
                    }
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } else {
            checkForIntTypeAndRewrite(txtAfterScan);
            tempIntList(listAfterCheckInt);
            String[] tempArray = new String[tempList.size()];
            tempList.toArray(tempArray);
            mergeSort(tempArray, tempArray.length);
            ArrayList<String> finalTemporaryList = new ArrayList<String>(Arrays.asList(tempArray));
            System.out.println(); // разделить для удобства чтения информации из консоли
            System.out.println(finalTemporaryList + " массив без проверки предварительно сортированного ввода данных");

            int uk = 0;
            while (uk < finalTemporaryList.size()) {
                if (uk == finalTemporaryList.size() - 1) break;
                if (Integer.parseInt(finalTemporaryList.get(uk + 1)) < Integer.parseInt(finalTemporaryList.get(uk))) {
                    finalTemporaryList.remove(uk + 1);
                    continue;
                }
                uk++;
            }

            System.out.println(); // разделить для удобства чтения информации из консоли
            System.out.println(finalTemporaryList + " подготовлен список для преобразования его в файл с обратным или прямым порядком");
            String path = "C:\\out.txt"; //путь и имя файла по умолчанию
            int checkout = 0; //проверка на наличие ввода названия в командной строке
            for (int i = 0; i < args.length; i++) {
                if (args[i].contains("out")) {
                    path = args[i].replaceAll("out", "txt");
                    checkout = 1;
                }
            }

            if (checkout != 1) {
                System.out.println("Перезапустите программу и обязательно укажите путь и название файла в формате C:\\path\\file.out - где C:\\path это путь где будет сохранен файл директория обязательно должна существовать, а file.out где file это название файла и оно может быть любым, а out(менять нельзя - это не расширение, файл будет по умолчанию сохранен в формате txt) указывает программе где брать путь для сохранения");
                System.exit(0);
            }


            int forvardOrReverse = 0; // 0 это прямой 1 это обратный
            for (int i = 0; i < args.length; i++) {
                if (args[i].contains("-d")) {
                    forvardOrReverse = 1;
                }
            }



            if (forvardOrReverse == 1) {
                try {
                    Collections.reverse(finalTemporaryList);
                    File file = new File(String.format("%s", path));
                    if (!file.exists()) {
                        file.createNewFile();
                        System.out.println();
                        System.out.println("Cоздан с обратным порядком");
                    }
                    PrintWriter pw = new PrintWriter(file);
                    for (String i : finalTemporaryList) {
                        pw.println(i);
                    }
                    pw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    File file = new File(String.format("%s", path));

                    if (!file.exists()) {
                        file.createNewFile();
                        System.out.println();
                        System.out.println("Cоздан с прямым порядком");
                    }
                    PrintWriter pw = new PrintWriter(file);
                    for (String i : finalTemporaryList) {
                        pw.println(i);
                    }
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}