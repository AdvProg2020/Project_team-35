package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bill {
    private static ArrayList<Bill> bills = new ArrayList<>();
    private String id;
    private double amount;
    public  Bill(String id , double amount){
        this.amount = amount;
        this.id = id;
        bills.add(this);
    }

    public static ArrayList<Bill> getBills() {
        return bills;
    }

    public static void setBills(ArrayList<Bill> bills) {
        Bill.bills = bills;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public static Bill getBill(String id){
        for (Bill bill : bills) {
            if (bill.getId().equalsIgnoreCase(id)){
                return bill;
            }
        }
        return null;
    }
}
