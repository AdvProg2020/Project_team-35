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

    public static boolean setScanner(Scanner scanner) {
        Page.scanner = scanner;
        return true;
    }

    public void setSubPages(HashMap<String, Page> subPages) {
        this.subPages = subPages;
    }

    public void execute(){
            setSubPages(subPages);
            show();
            Page nextPage = null;
            String command = scanner.nextLine();
        for (String s : subPages.keySet()) {
            if (command.equals(s)){
                nextPage = getPageOfSubPage(command);
            }
        }
        if (command.equals(String.valueOf(subPages.size()+1)) && parentPage!=null){
            // i need help in this part//********************************
            if (parentPage instanceof UserPage && Account.getOnlineAccount()==null){
                nextPage = parentPage.parentPage;
            }else {
                nextPage = parentPage;
            }
        }
        else if (command.equals(String.valueOf(subPages.size()+1)) && this instanceof MainPage){
            return;
        }
        try {
            nextPage.execute();
        }catch (Exception e){
            e.printStackTrace();
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
            System.out.println(i+"."+subPages.get(s).name);
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

        if (type.equals("email address")|| type.equalsIgnoreCase("email")) {
            Matcher matcher = getMatcher(input, "^(\\w+)@(\\w+).(\\w+)$");
            return matcher.matches();
        } else if (type.equals("phone number")|| type.equalsIgnoreCase("phoneNumber")) {
            Matcher matcher = getMatcher(input, "\\d+");
            return matcher.matches();
        }
        return true;
    }
}
