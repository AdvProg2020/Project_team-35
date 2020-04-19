package Model;

import java.util.ArrayList;

public class EditOffRequest extends Request {
    private Off toEdit;
    private ArrayList<Product> newIncludedProducts;
    private double newMaximumAmountOfOff;
    private double newOffPercent;
    private ProductAndOffStatus newOffStatus;

    public String getDetails() {
        return null;
    }
    public void execute() {

    }
}
