package Model;

import java.util.HashMap;

public class EditProductRequest extends Request {
    private Product toEdit;

    private String newName;
    private String newCompany;
    private double newPrice;
    private int newInventory;
    private Category newCategory;
    public HashMap<String, String> newSpecialAttributes;

    public EditProductRequest(Seller seller, Product toEdit, String newName, String newCompany, double newPrice, int newInventory, HashMap<String, String> newSpecialAttributes ,Category newCategory) {
        super(seller, "Edit Product");
        this.toEdit = toEdit;
        this.newName = newName;
        this.newCompany = newCompany;
        this.newPrice = newPrice;
        this.newInventory = newInventory;
        this.newSpecialAttributes = newSpecialAttributes;
        this.newCategory = newCategory;
    }

    public boolean execute() {
        this.isDone = true;
        toEdit.setProductStatus(ProductAndOffStatus.CONFIRMED);
        if (newName!=null){
            toEdit.setName(newName);
        }
        if (newCategory!=null){
            toEdit.setCategory(newCategory);
            HashMap<String,String> specialists = new HashMap<>();
            for (String attribute : toEdit.getCategory().getSpecialAttributes()) {
                specialists.put(attribute,"");
            }
            toEdit.setSpecialAttributes(specialists);
        }
        if (newCompany!=null){
            toEdit.setCompany(newCompany);
        }
        if (newPrice != -1.0){
            toEdit.setPrice(newPrice);
        }if (newInventory != -1){
            toEdit.setInventory(newInventory);
        }
        if (newSpecialAttributes.keySet().size()!=0) {
            if (newSpecialAttributes != null) {

              HashMap<String,String>  attributes = toEdit.getSpecialAttributes();
                for (String s : newSpecialAttributes.keySet()) {
                    attributes.put(s,newSpecialAttributes.get(s));
                }
                toEdit.setSpecialAttributes(attributes);
            }
        }
        return true;
    }
        public String getDetails() {
            return "Edit Product Request : \nRequestId: " + this.getRequestId() + "\nRequester Username: " + this.seller.getUsername()
                    + "\nNewCategory: " + this.newCategory + " --- CurrentCategory: " + this.toEdit.getCategory() +
                    "\nNewPrice: " + this.newPrice + " --- CurrentPrice: " + this.toEdit.getPrice() + "\nNewCompany: " + this.newCompany
                    + " --- CurrentCompany: " + this.toEdit.getCompany() + "\nNewInventory: " + this.newInventory + " --- CurrentInventory: " + this.toEdit.getInventory() +
                    "\nProductId: " + this.toEdit.getProductId() + "\n(-1 means no change)";
        }

    @Override
    public String getRequestInfo() {
        return "  Edit Product Request --- UserName: " + seller.getUsername() + " --- RQId: " + this.getRequestId();
    }

    @Override
    public void decline(){
        this.toEdit.setProductStatus(ProductAndOffStatus.CONFIRMED);
    }
}
