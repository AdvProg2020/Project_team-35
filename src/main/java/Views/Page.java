package Views;

import Model.Account;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Page {
    protected String name;

    protected HashMap<String , Page> subPages;
    protected   Page parentPage;
    protected Account account;
    protected static Scanner scanner;
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

    public void execute(){

            show();
            Page nextPage = null;
            String command = scanner.nextLine();
        for (String s : subPages.keySet()) {
            if (command.equals(s)){
                nextPage = getPageOfSubPage(command);
            }
        }
        if (command.equals("back") && parentPage!=null){
         nextPage = parentPage;
        }
         if (command.equals("exit") ){
            return;
        }
        try {
            nextPage.execute();
        }catch (Exception e){
            System.err.println("invalid command");
            this.execute();
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }

    private Page getPageOfSubPage(String command){
        for (String s : subPages.keySet()) {
            if (s.equals(command))
                return subPages.get(s);
        }
        return null;
    }
    public boolean show(){
        System.out.println(name);
        int i =1;
        for (String s : subPages.keySet()) {
            System.out.println(i+"."+s);
            i+=1;
        }
        if (parentPage!=null){
            System.out.println((subPages.keySet().size()+1)+".back");
        }
        else {
            System.out.println((subPages.keySet().size()+1)+".exit");
        }
        return true;
    }
    protected static Matcher getMatcher(String input , String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
    /**
     * this method check email address and phone number be in a true form.
     * @param type
     * @param input
     * @return
     */
    protected static boolean checkFormatOfPersonalInformation(String type, String input) {

        if (type.equals("email address")) {
            Matcher matcher = getMatcher(input, "^(\\w+)@(\\w+).(\\w+)$");
            return matcher.matches();
        } else if (type.equals("phone number")) {
            Matcher matcher = getMatcher(input, "\\d+");
            return matcher.matches();
        }
        return true;
    }

}
