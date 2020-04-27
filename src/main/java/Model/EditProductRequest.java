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

    public EditProductRequest() {
        super();
    }

    public String getDetails() {
        return null;
    }
    public void execute() {

    }
    public String getRequestInfo() {
        return null;
    }
}
