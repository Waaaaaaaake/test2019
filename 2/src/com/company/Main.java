package com.company;

import java.util.Arrays;
import java.util.Scanner;

// by Stanislav Lukyanov

public class Main {

    // Количество элементов в исходных массивах

    public static int countElements(String str, String[] stats_data, char[] stats) { // передаются ссылки на объекты
        int p = 0;
        int ascii1 = 0;

        for (int start = 0; start < str.length(); start++) {
            int ascii_security = (int)str.charAt(start); // получили ASCII код текущего символа
            try {
                ascii1 = (int)str.charAt(start+1); // если следующего символа нет выдаст исключение
            } catch (Exception e) {
                ascii1 = 0; // обработали исключение так, чтобы не нарушился цикл
            }
            if ((ascii_security > 64 && ascii_security < 91) && (ascii1 == 58)) { // Диапазон A..Z из таблицы ASCII
                stats[p] = str.charAt(start); // Записываем характеристику
                start = start + 2; // игнорируем двоеточие
                stats_data[p]="";
//                stats_data[p]+=String.valueOf(str.charAt(start));
//                while (((int)str.charAt(start)) != 0 || ((int)str.charAt(start)) != 32 || start <= str.length()) {
                while ((int)str.charAt(start) != 32) {
                    stats_data[p]+= String.valueOf(str.charAt(start));
//                    System.out.println(String.valueOf(str.charAt(start)));
//                    System.out.println("Num: " + (int)str.charAt(start));
//                    System.out.println("Length:" + start);
//                    new java.util.Scanner(System.in).nextLine();
                    start++;
                    if(start == str.length()){ break; } // если итератор вышел за массив
                }
                p++; // увеличиваем индекс массивов записи характеристик
            }
        }
        stats = Arrays.copyOfRange(stats,0,p); // Массив характеристик
        return p;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String str_security = scanner.nextLine();
        String str_gnome = scanner.nextLine();
        char[] stats_security = new char[10];
        char[] stats_gnome = new char[10];
        String []  stats_data_security = new String[10];
        String []  stats_data_gnome = new String[10];

        int exit = 0, index_dash;
        String before_dash, after_dash, tmp;
        int int_before_dash = 0, int_after_dash = 0, int_stats_data_gnome, int_tmp = 0;
        int p_security = countElements(str_security, stats_data_security, stats_security);
        int p_gnome = countElements(str_gnome,stats_data_gnome,stats_gnome);

//        L:100-160 B:30 G:5 H:4-7 A:L
//        L:132 B:40 W:4 G:8 H:5 E:8 A:122

//        L:B-A B:30 G:5 H:4-7 A:40
//        L:32 B:30 G:5 H:8 A:40
//        false

//        L:B-A B:30 G:5 H:4-7 A:40
//        L:31 B:30 G:5 H:8 A:40
//        OK
//        false

//        System.out.println(a.length());



//        System.out.println("Security");
//        for (int i = 0; i < p_security; i++) {
//            System.out.println(stats_security[i] + " with " + stats_data_security[i]);
//        }
//        System.out.println("Gnom");
//        for (int i = 0; i < p_gnome; i++) {
//            System.out.println(stats_gnome[i] + " with " + stats_data_gnome[i]);
//        }

        // поиск элементов допуска security в gnome
        // если хоть одного элемента нет, выходим из цикла и пишем false

        for (int i = 0; i < p_security; i++) {
            for (int j = 0; j < p_gnome; j++) {
                // если нашли нужную букву, которая равна букве охранника
                if (stats_security[i] == stats_gnome[j]) {
                    // взяли стату гнома для сравнения
                    int_stats_data_gnome = Integer.valueOf(stats_data_gnome[j]);
                    if (stats_data_security[i].contains("-")) {

                        index_dash = stats_data_security[i].indexOf("-");
                        before_dash = stats_data_security[i].substring(0, index_dash);
                        after_dash = stats_data_security[i].substring(index_dash+1, stats_data_security[i].length());

                        // далее
                        // цифра или буква первый символ?
                        if (before_dash.length() == 1 && (int)before_dash.charAt(0) > 64 && (int)before_dash.charAt(0) < 91) {
                            for (int it = 0; it < p_security; it++) {
                                // если нашли нужную букву (не работает без приведения типов)
                                if (before_dash.equals(String.valueOf(stats_security[it]))) {
                                    int_before_dash = Integer.valueOf(stats_data_security[it]);
                                    break;
                                }
                            }
                        } else {
                            int_before_dash = Integer.valueOf(before_dash);
                        }
                        if (after_dash.length() == 1 && (int)after_dash.charAt(0) > 64 && (int)after_dash.charAt(0) < 91) {
                            for (int it = 0; it < p_security; it++) {
                                // если нашли нужную букву (не работает без приведения типов)
                                if (after_dash.equals(String.valueOf(stats_security[it]))) {
                                    int_after_dash = Integer.valueOf(stats_data_security[it]);
                                    break;
                                }
                            }
                        } else {
                            int_after_dash = Integer.valueOf(after_dash);
                        }
                        // две цифры у охранника если это тире.
                        if ((int_stats_data_gnome > int_before_dash) && (int_stats_data_gnome < int_after_dash)) {
                            break;
                        } else {
//                            System.out.println("Here1");
//                            System.out.println("int" + int_stats_data_gnome);
//                            System.out.println("before: " + int_before_dash);
//                            System.out.println("after: " + int_after_dash);
//                            L:100-160 B:30 G:5 H:4-7 A:L
//                            L:132 B:40 W:4 G:8 H:5 E:8 A:161
                            exit = 1;
                            break;
                        }
                    }
                    // в случае если буква 1
                    // за буквой либо 1 цифра либо интервал цифр
                    if (stats_data_security[i].length() == 1 && (int)stats_data_security[i].charAt(0) > 64 && (int)stats_data_security[i].charAt(0) < 91) {
                        tmp = stats_data_security[i];
                            for (int it = 0; it < p_security; it++) {
                                // если нашли нужную букву (не работает без приведения типов)
                                if (tmp.equals(String.valueOf(stats_security[it]))) {
                                    // перешли по букве, а там диапазон данных
//                                    L:100-160 B:30 G:5 H:4-7 A:L
//                                    L:132 B:40 W:4 G:8 H:5 E:8 A:122
                                    if (stats_data_security[it].contains("-")) {
                                        index_dash = stats_data_security[it].indexOf("-");
                                        before_dash = stats_data_security[it].substring(0, index_dash);
                                        after_dash = stats_data_security[it].substring(index_dash+1, stats_data_security[it].length());
                                        // цифра или буква первый символ?
                                        if (before_dash.length() == 1 && (int)before_dash.charAt(0) > 64 && (int)before_dash.charAt(0) < 91) {
                                            for (int it1 = 0; it1 < p_security; it1++) {
                                                // если нашли нужную букву (не работает без приведения типов)
                                                if (before_dash.equals(String.valueOf(stats_security[it1]))) {
                                                    int_before_dash = Integer.valueOf(stats_data_security[it1]);
                                                    break;
                                                }
                                            }
                                        } else {
                                            int_before_dash = Integer.valueOf(before_dash);
                                        }
                                        if (after_dash.length() == 1 && (int)after_dash.charAt(0) > 64 && (int)after_dash.charAt(0) < 91) {
                                            for (int it1 = 0; it1 < p_security; it1++) {
                                                // если нашли нужную букву (не работает без приведения типов)
                                                if (after_dash.equals(String.valueOf(stats_security[it1]))) {
                                                    int_after_dash = Integer.valueOf(stats_data_security[it1]);
                                                    break;
                                                }
                                            }
                                        } else {
                                            int_after_dash = Integer.valueOf(after_dash);
                                        }
//                                        System.out.println("Before: " + int_before_dash);
//                                        System.out.println("After: " + int_after_dash);
//                                        System.out.println(int_stats_data_gnome);

//                                        L:100-120 B:40 W:4 G:10 H:5 E:8 A:222
//                                        L:121 B:40 W:4 G:8 H:5 E:8 A:222
//                                        L:100-120 B:40 W:4 G:10 H:5 E:8 A:222
//                                        L:121 B:40 W:5 G:8 H:5 E:8 A:221
//                                        L:100-120 B:40 W:4 G:10 H:5 E:8 A:222
//                                        L:121 B:40 W:5 G:11 H:5 E:8 A:222
                                        if ((int_stats_data_gnome > int_after_dash)) {
                                            break;
                                        } else {
//                                            System.out.println("Here2");
                                            exit = 1;
                                            break;
                                        }
                                    } else {
                                        int_tmp = Integer.valueOf(stats_data_security[it]);
                                        if (int_stats_data_gnome > int_tmp) {
                                            break;
                                        } else {
                                            exit = 1;
//                                            System.out.println("Here3");
                                            break;
                                        }
                                    }
                                }
                        }

//                        L:A B:30 G:5 H:4-7 A:40
//                        L:41 B:30 G:5 H:5 A:40

//                        L:20-40 B:20-50 G:5 H:5 A:40
//                        L:39 B:28 G:5 H:6 A:41

                    } else {
                        if (Integer.valueOf(stats_data_security[i]) < int_stats_data_gnome) {
                            break;
                        } else {
                            exit = 1;
                            break;
                        }
                    }
                    // если что-то подходит = делаем брейк , если не подходит экзит 1 и брейк
                } else {
                    if ((j+1) == p_gnome) {
                        exit = 1;
                        break;
                    }
                }
            }
            if (exit == 1) {
                break;
            }
        }

        if (exit == 1) {
            System.out.println("false");
        } else {
            System.out.println("true");
        }
    }
}