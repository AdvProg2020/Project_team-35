import Views.MainPage;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainPage mainPage = new MainPage();
        MainPage.setScanner(scanner);
        mainPage.execute();
    }
}
