package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Account {
    public static ArrayList<Seller> allSellers = new ArrayList<Seller>();
    private ArrayList<Off> sellerOffs;
    private ArrayList<SellLog> sellLogs;
    private String companyName;
    private double money;
    private ArrayList<Product> salableProducts;

    /**
     * a constructor for seller
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     * @param companyName
     */
    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password,3);
        this.companyName = companyName;
        sellerOffs = new ArrayList<Off>();
        sellLogs = new ArrayList<SellLog>();
        money = 0.0;
        salableProducts = new ArrayList<Product>();
    }

    public HashMap<Product, Integer> getSalesHistory(){
        HashMap<Product,Integer> history = new HashMap<>();
        for (SellLog sellLog : sellLogs) {
            if (sellLog.getSoldProducts()!=null) for (Product product : sellLog.getSoldProducts()) {
                history.put(product, sellLog.getNumberOfProducts(product));
            }
        }
        return history;
    }
    public int hasHeProductWithId(int productId) {
        Product product = Product.getProductWithId(productId);
        if (product==null)
            return 5;
       else if (salableProducts.contains(product))
            return 2;
        else
            return 7;
    }
    public boolean setMoney(double money){
        if (money<0)
            return false;
        this.money = money;
        return true;
    }
    @Override
    public String getUsername(){
        return super.getUsername();
    }
    @Override
    public String getPersonalInfo(){
        return "Type: Seller\n" +
                "Username: "+getUsername()+"\n" +
                "Name: "+getFirstName()+"\n" +
                "Last Name: "+getLastName()+"\n"+
                "Email: "+getEmail()+"\n" +
                "PhoneNumber: "+getPhoneNumber()+"\n"+
                "Company: "+getCompanyName()+"\n"+
                "password: "+getPassword();
    }
    public String getCompanyName(){
        return companyName;
    }

    public boolean setCompanyName(String companyName){
        this.companyName = companyName;
        return true;
    }

    public double getMoney(){
        return money;
    }

    public ArrayList<SellLog> getSellLogs(){
        return sellLogs;
    }

    public ArrayList<Product> getSalableProducts() {
       ArrayList<Product> result = new ArrayList<>();
        for (Product product : salableProducts) {
            if (product.getInventory()!=0)
                result.add(product);
        }
        setSalableProducts(result);
        return salableProducts;
    }

    public boolean setSalableProducts(ArrayList<Product> salableProducts) {
        for (Product product : salableProducts) {
            if (product.getInventory()<=0)
                return false;
        }
        this.salableProducts = salableProducts;
        return true;
    }

    public ArrayList<Off> getSellerOffs() {
        ArrayList<Off> result = new ArrayList<>();
        for (Off activeOff : Off.getAllActiveOffs()) {
            if (activeOff.getSeller().equals(this))
                result.add(activeOff);
        }
        setSellerOffs(result);
        return sellerOffs;
    }

    public boolean setSellerOffs(ArrayList<Off> sellerOffs){
        for (Off off : sellerOffs) {
            if (off==null)
                return false;
        }

            this.sellerOffs = sellerOffs;
            return true;

    }

    @Override
    public String getShortInfo(){
        return "UserName : " + this.getUsername() + "  --  " + "Type : Seller" + " -- Condition: " + getIsConfirmedOrWaitForCheck();
    }
    public static ArrayList<Seller> getAllSellers() {
        return allSellers;
    }
    public double countPriceOfOffsProduct(Product product){
        Off productsOff = null;
        for (Off sellerOff : getSellerOffs()) {
            if (sellerOff.getIncludedProducts().contains(product)){
                productsOff = sellerOff;
            }
        }
        if (productsOff==null){
            return product.getPrice();
        }else {
            return productsOff.countOff(product);
        }
    }
    public Off findOffWithAProduct(Product product){
        for (Off off : getSellerOffs()) {
            if (off.getIncludedProducts().contains(product))
                return off;
        }
        return null;
    }
}
