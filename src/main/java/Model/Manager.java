package Model;

import java.util.ArrayList;

public class Manager extends Account {
    public static ArrayList<Manager> allManagers;
    public static ArrayList<Request> newRequests;




    public static boolean isThereAnyManager() {
        return true;
    }
    @Override
    public String toString() {
        return null;
    }
    @Override
    public void deleteAccount() {

    }
    @Override
    public void getPersonalInfo() {
        this.toString();
    }
    @Override
    public void editPersonalField(String field) {

    }

}
