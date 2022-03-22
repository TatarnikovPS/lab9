package com.metanit;
import java.util.*;
/*Task:
 *Реализация расчёта результата строчного выражения с простыми дробями
 *На вход программе приходит строка, введённая пользователем с клавиатуры, на выходе выводится результат введённого выражения
 *После ввода одного выражения и расчёта его результата пользователь может ввести следующее выражение
 *Для выхода из программы пользователь вводит с клавиатуры стоп-слово (например, quit)
 *Для оценки корректности работы программы необходимо разработать серию тестов Junit
 */
public class lab9 {

    //Знак операции
    public static String op;
    //Операнды (как правило - дроби)
    public static String f1;
    public static String f2;
    //Преобразование
    public static int w1;
    public static int w2;
    //Числители
    public static int n1;
    public static int n2;
    //Знаменатели
    public static int d1;
    public static int d2;

    public static void main(String[] args) throws Exception {
        System.out.println("Добро пожаловать в калькулятор с дробями!");
        System.out.println("Для умножения используйте \"*\", для разности - \"-\", для сложения - \"+\", для деления - любой иной знак");
        System.out.println("Пример корректного ввода: 1/2 + 1/2");
        Scanner enter = new Scanner(System.in);
        System.out.print("Введите выражение (или \"quit\" для выхода): ");
        //Ввод выражения
        f1 = enter.next();
        //Проверка, не желает ли пользователь выйти из калькулятора
        if(f1.equalsIgnoreCase("quit")){
            System.out.println("Хорошего дня!");
        }
        //Обработка последующих выражений
        while(!f1.equalsIgnoreCase("quit")){
            op = enter.next();
            f2 = enter.next();
            dataProcessing(f1, op, f2);
            System.out.print("Введите выражение (или \"quit\" для выхода): ");
            f1 = enter.next();
            if(f1.equalsIgnoreCase("quit")){
                System.out.println("Хорошего дня!");
            }
        }
    }

    //Осмысление введённых пользователем данных
    public static void dataProcessing(String f1, String op, String f2){

        //Первый операнд
        if(f1.contains("_")){ //Для выделения смешанных чисел
            w1=Integer.parseInt(f1.substring(0,f1.indexOf("_")));
            n1=Integer.parseInt(f1.substring(f1.indexOf("_")+1,f1.indexOf("/")));
            d1=Integer.parseInt(f1.substring(f1.indexOf("/")+1));
            n1=(w1*d1)+n1; //Формирование неправильной дроби
            if(d1 == 0){
                System.out.println("\n Знаменатель не может быть равен нулю! Попробуйте снова\n");
                return;
            }
        } else if(f1.contains("/")) { //Для выделения дробей
            try {
                n1 = Integer.parseInt(f1.substring(0, f1.indexOf("/")));
                d1 = Integer.parseInt(f1.substring(f1.indexOf("/") + 1));
            }
            catch(NumberFormatException e){
                System.out.println("\n Неверный формат! Попробуйте снова\n");
                return;
            }
            if(d1 == 0) {
                System.out.println("\n Знаменатель не может быть равен нулю! Попробуйте снова\n");
                return;
            }
        } else {//Для целого числа
            try {
                w1 = Integer.parseInt(f1.substring(0));
                n1 = w1;
                d1 = 1;
            }
            catch(NumberFormatException e){
                System.out.println("\n Неверный формат! Попробуйте снова\n");
                return;
            }
        }

        //Второй операнд
        if(f2.contains("_")){
            w2=Integer.parseInt(f2.substring(0,f2.indexOf("_")));
            n2=Integer.parseInt(f2.substring(f2.indexOf("_")+1,f2.indexOf("/")));
            d2=Integer.parseInt(f2.substring(f2.indexOf("/")+1));
            n2=w2*d2+n2;
            if(d2 == 0){
                System.out.println("\n Знаменатель не может быть равен нулю! Попробуйте снова\n");
                return;
            }
        } else if(f2.contains("/")) {
            try {
                n2 = Integer.parseInt(f2.substring(0, f2.indexOf("/")));
                d2 = Integer.parseInt(f2.substring(f2.indexOf("/") + 1));
            }
            catch(NumberFormatException e){
                System.out.println("\n Неверный формат! Попробуйте снова\n");
                return;
            }
            if(d2 == 0) {
                System.out.println("\n Знаменатель не может быть равен нулю! Попробуйте снова\n");
                return;
            }
        } else {
            try {
                w2 = Integer.parseInt(f2.substring(0));
                n2 = w2;
                d2 = 1;
            }
            catch(NumberFormatException e){
                System.out.println("\n Неверный формат! Попробуйте снова\n");
                return;
            }
        }
        operation(n1, n2, d1, d2, op);
    }

    //Определение и реализация операции
    public static void operation(int n1, int n2, int d1, int d2, String op) {
        if(op.equals("+")){
            System.out.println(add(n1, n2, d1, d2));
        } else if(op.equals("-")) {
            n2=-1*n2;
            System.out.println(add(n1, n2, d1, d2));
        } else if(op.equals("*")) {
            System.out.println(multiply(n1, n2, d1, d2));
        } else {
            int x = n2;
            int y = d2;
            d2=x;
            n2=y;
            System.out.println(multiply(n1, n2, d1, d2));
        }
    }



    //Сложение и вычитание
    public static String add(int n1, int n2, int d1, int d2) {
        int newn = (n1*d2) + (n2*d1);
        int newd = d1*d2;

        int divisor = reduce(newn,newd);
        newn/=divisor;
        newd/=divisor;
        int integerComponent=0;

        while(newn >= newd) {
            integerComponent++;
            newn-=newd;
        }
        String answer="";
        if(integerComponent>0) {
            answer += integerComponent +"_";
        }
        if(newn!=0) {
            answer += newn+"/"+newd;
        }
        return answer;
    }

    //Умножение и деление
    public static String multiply(int n1, int n2, int d1, int d2) {
        int newn = n1*n2;
        int newd = d1*d2;

        int divisor = reduce(newn,newd);
        newn/=divisor;
        newd/=divisor;

        int integerComponent=0;

        while(newn >= newd) {
            integerComponent++;
            newn-=newd;
        }
        String answer ="";
        if(integerComponent>0) {
            answer += integerComponent +"_";
        }
        if(newn!=0) {
            answer += newn+"/"+newd;
        }
        return answer;
    }

    //НОК
    public static int lcd(int n1,int d1, int n2, int d2){
        int dividend=(d1*n2)+(n1*d2);
        int divisor = d1*d2;
        int rem = dividend % divisor;
        while (rem != 0){
            dividend = divisor;
            divisor = rem;
            rem = dividend % divisor;
        }
        return divisor;
    }

    //Сокращение
    public static int reduce (int newn, int newd) {
        int newn_abs = Math.abs (newn);
        int newd_abs = Math.abs (newd);

        int min_num = Math.min (newn_abs, newd_abs);

        int divisor = 1;

        for (int i = 1; i <= min_num; i++) {
            if (newn%i == 0 && newd%i == 0){

                divisor = 1;
            }
        }
        return divisor;
    }
}
