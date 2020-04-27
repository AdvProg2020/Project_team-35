package Model;

import java.util.HashMap;

public class EditProductRequest extends Request {
    private Product toEdit;

    private ProductAndOffStatus newProductStatus;
    private String newName;
    private String newCompany;
    private double newPrice;
    private int newInventory;
    public HashMap<String, String> newSpecialAttributes;

    public EditProductRequest(StringBuilder request, RequestTypes requestTypes) {
        super(request, requestTypes);
    }

    public StringBuilder getDetails() {
        return null;
    }
    public void execute() {

    }
}
