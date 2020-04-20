package Views;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Page {
    protected String name;
    protected String command;
    protected HashMap<String , Page> subPages;
    protected   Page parentPage;
    protected User user;
    protected static Scanner scanner;
    public Page(String name, Page parentPage) {
        this.name = name;
        subPages = new HashMap<String, Page>();
        this.parentPage = parentPage;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        Page.scanner = scanner;
    }

    public void setSubPages(HashMap<String, Page> subPages) {
        this.subPages = subPages;
    }
    private static boolean checkLoginOfUser(User user){

        return false;
    }
    public void execute(String command){
            show();
            Page nextPage = null;
            command = scanner.nextLine();
        for (String s : subPages.keySet()) {
            if (command.equals(s)){
                nextPage = getPageOfSubPage(s);
            }
        }
        if (command.equalsIgnoreCase("back") && parentPage != null){
            nextPage = parentPage;
        }
        else if (command.equalsIgnoreCase("exit") && parentPage==null){
            return;
        }
        else if (nextPage==null){
            //exception
        }
        nextPage.execute(command);

    }
    private Page getPageOfSubPage(String command){
        for (String s : subPages.keySet()) {
            if (s.equals(command))
                return subPages.get(s);
        }
        return null;
    }
    public void show(){
        System.out.println(this.name);
        for (String s : subPages.keySet()) {
            System.out.println(s);
        }
        if (parentPage!=null){
            System.out.println("back");
        }
        else {
            System.out.println("exit");
        }
    }

    public void setCommand(String command) {
        this.command = command;
    }

    protected static Matcher getMatcher(String input , String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public String getCommand() {
        return command;
    }
}
