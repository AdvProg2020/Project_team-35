package Model;

import java.util.ArrayList;

public class Off {
    public static ArrayList<Off> allActiveOffs;
    private static int offIdNumber;
    private int offId;
    private String finalDate;
    private ArrayList<Product> includedProducts;
    private double maximumAmountOfOff;
    private double offPercent;
    private ProductAndOffStatus offStatus;
}
