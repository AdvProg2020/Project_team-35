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
        super(seller);
        this.toEdit = toEdit;
        this.newName = newName;
        this.newCompany = newCompany;
        this.newPrice = newPrice;
        this.newInventory = newInventory;
        this.newSpecialAttributes = newSpecialAttributes;
        this.newCategory = newCategory;
    }

    public void execute() {
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
    }
        public String getDetails() {
            return "Seller Register Request : \nRequestId: " + this.getRequestId() + "\n" + this.seller.getPersonalInfo();

        }

    @Override
    public String getRequestInfo() {
        return "  EDIT PRODUCT Request --- UserName: " + seller.getUsername() + " --- RQId: " + this.getRequestId();
    }


    public void decline(){
        this.toEdit.setProductStatus(ProductAndOffStatus.CONFIRMED);
    }
}
