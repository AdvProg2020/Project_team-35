package Views;

import Model.User;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Page {
    protected String name;
    private HashMap<String , Page> subPages;
    private  Page parentPage;
    protected User user;
    private static Scanner scanner;
    public Page(String name, Page parentPage) {
        this.name = name;
        subPages = new HashMap<String, Page>();
        this.parentPage = parentPage;
    }

    public static void setScanner(Scanner scanner) {
        Page.scanner = scanner;
    }

    public void setSubPages(HashMap<String, Page> subPages) {
        this.subPages = subPages;
    }
    private static boolean checkLoginOfUser(User user){
        if (user.isUserLogin())
            return true;
        return false;
    }
    public void execute(){
            show();
            Page nextPage = null;
            String command = scanner.nextLine();
        for (String s : subPages.keySet()) {
            if (command.equals(s)){
                nextPage = getPageOfSubPage(command);
            }
        }
        nextPage.execute();

    }
    private Page getPageOfSubPage(String command){
        for (String s : subPages.keySet()) {
            if (s.equals(command))
                return subPages.get(s);
        }
        return null;
    }
    public void show(){
        for (int i = 0; i < subPages.size(); i++) {
            System.out.println(subPages.keySet());
        }
        if (parentPage!=null){

        }
        else {

        }
    }
}
