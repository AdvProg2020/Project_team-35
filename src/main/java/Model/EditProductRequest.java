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

    public EditProductRequest(StringBuilder request, RequestTypes requestTypes, Account requester, Product toEdit, ProductAndOffStatus newProductStatus, String newName, String newCompany, double newPrice, int newInventory, HashMap<String, String> newSpecialAttributes) {
        super(request, requestTypes, requester);
        this.toEdit = toEdit;
        this.newProductStatus = newProductStatus;
        this.newName = newName;
        this.newCompany = newCompany;
        this.newPrice = newPrice;
        this.newInventory = newInventory;
        this.newSpecialAttributes = newSpecialAttributes;
    }

    public StringBuilder getDetails() {
        return null;
    }
    public void execute() {

    }
}
