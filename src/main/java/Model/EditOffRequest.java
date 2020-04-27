package Model;

import java.util.ArrayList;

public class EditOffRequest extends Request {
    private Off toEdit;
    private ArrayList<Product> newIncludedProducts;
    private double newMaximumAmountOfOff;
    private double newOffPercent;
    private ProductAndOffStatus newOffStatus;

    public EditOffRequest(StringBuilder request, RequestTypes requestTypes) {
        super(request, requestTypes);
    }

    public StringBuilder getDetails() {
        return null;
    }
    public void execute() {

    }
}
