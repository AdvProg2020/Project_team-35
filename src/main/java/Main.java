import Views.MainPage;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainPage.setScanner(scanner);
        MainPage mainPage = new MainPage();
        mainPage.execute(null);
    }
}
