package Views;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Page {
    protected String name;
    protected HashMap<String , Page> subPages;
    private  Page parentPage;
    protected User user;
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
    private static boolean checkLoginOfUser(User user){
        //if (user.isUserLogin())
          //  return true;
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
        if (command.equals("back") && parentPage!=null){
         nextPage = parentPage;
        }
        else if (command.equals("exit") && parentPage==null){
            return;
        }
        try {
            nextPage.execute();
        }catch (Exception e){
            System.err.println("invalid command");
            this.execute();
        }
    }
    private Page getPageOfSubPage(String command){
        for (String s : subPages.keySet()) {
            if (s.equals(command))
                return subPages.get(s);
        }
        return null;
    }
    public void show(){
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
    }
}
