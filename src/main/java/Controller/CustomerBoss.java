package Controller;

import Controller.Exceptions.*;
import Model.*;

import java.text.ParseException;

public class CustomerBoss {
    public static double showMoney(Customer customer) {
        return customer.getMoney();
    }
}
