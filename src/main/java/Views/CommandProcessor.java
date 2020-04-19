package Views;

import java.util.Scanner;

public class CommandProcessor {
    private static Scanner scanner;
    public static void run(){
        scanner = new Scanner(System.in);
            Page.setScanner(scanner);
            while (true){
                String command = scanner.nextLine();
            }
    }
}
