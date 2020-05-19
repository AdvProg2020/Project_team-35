package Model;

import Views.GoodPage;
import Views.ProductsPage;

import java.util.*;

public class Product {
    public static ArrayList<Product> allProducts = new ArrayList<>();
    private int productId;
    private static int productNumber;
    private ProductAndOffStatus productStatus;
    private String name;
    private int reviewNumber;
    private String description;
    private String company;
    private double price;
    private Seller seller;
    private int inventory;
    private Category category;
    private ArrayList<Comment> commentsList;
    private ArrayList<Rate> ratesList;
    private HashMap<String, String> specialAttributes;
    private Product onlineProduct;
    private ArrayList<Customer> whoBoughtThisGood;
    //when the page of product is open.


    public Product(String name, String company, double price, Seller seller, int inventory, Category category, HashMap<String, String> specialAttributes, String description) {
        this.name = name;
        this.description = description;
        this.company = company;
        this.price = price;
        this.seller = seller;
        this.inventory = inventory;
        this.category = category;
        reviewNumber = 0;
        this.specialAttributes = specialAttributes;
        productNumber += 1;
        productId = productNumber;
        commentsList = new ArrayList<>();
        whoBoughtThisGood = new ArrayList<>();
        ratesList = new ArrayList<>();
        allProducts.add(this);
        seller.getSalableProducts().add(this);
    }


    public ArrayList<Customer> getWhoBoughtThisGood() {
        return whoBoughtThisGood;
    }

    public static boolean isThereProductWithId(int id) {
        for (Product product : allProducts) {
            if (product.productId == id)
                return true;
        }
        return false;
    }

    /**
     * this is updated.
     *
     * @param productId
     * @return
     */
    public static Product getProductWithId(int productId) {
        for (Product product : allProducts) {
            if (product.productId == productId)
                return product;
        }
        return null;
    }
/*
    private void updateProductAverageRate(int productId) {
        Product product = Product.getProductWithId(productId);
        double average = 0.0;
        for (Rate rate : product.getRatesList()) {
            average+=rate.getRate();
        }
        }

 */



    public int getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        String productInfo = null;
        productInfo = "name : " + name + "\n"
                + "company name : " + company + "\n"
                + "price : " + price + "\n"
                + "seller : " + seller.getUsername() + "\n"
                + "inventory : " + inventory + "\n";
        if (category!=null) {
           productInfo +="category : " + category.getCategoryName() + "\n";
        }
             productInfo+= "product id : " + productId + "\n";
        productInfo += "special Attributes : \n";
        if (specialAttributes != null) {
            for (String s : specialAttributes.keySet()) {
                productInfo = productInfo + s + " : " + specialAttributes.get(s) + "\n";
            }
        }
        productInfo += "comment List : \n";
        for (Comment comment : commentsList) {
            productInfo += comment.getCommentInfo();
        }
        productInfo += "rate list : \n";
        for (Rate rate : ratesList) {
            productInfo += rate.getRateInfo();
        }

        return productInfo;
    }

    public Seller getSeller() {
        return seller;
    }

    public String getName() {
        return name;
    }
    public double getAverageOfRates() {
        double average = 0.0;
        int number = 0;
        for (Rate rate : ratesList) {
            average += rate.getRate();
            number += 1;
        }
        return average / number;
    }

    public static boolean deleteProduct(Product product) {
        for (Off allActiveOff : Off.allActiveOffs) {
            if (allActiveOff.getIncludedProducts().contains(product)) {
                allActiveOff.getIncludedProducts().remove(product);
                break;
            }
        }
        allProducts.remove(product);
        product.getSeller().getSalableProducts().remove(product);
        product.getCategory().getCategoryProducts().remove(product);
        return true;
    }

    public HashMap<String, String> getSpecialAttributes() {
        return specialAttributes;
    }

    public String getCompany() {
        return company;
    }

    public double getPrice() {
        return price;
    }

    public int getInventory() {
        return inventory;
    }

    public Category getCategory() {
        return category;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setProductStatus(ProductAndOffStatus productStatus) {
        this.productStatus = productStatus;
    }

    public boolean setName(String name) {
        this.name = name;
        return true;
    }

    public boolean setCompany(String company) {
        this.company = company;
        return true;
    }

    public boolean setPrice(double price) {
        if (price<=0)
            return false;
        this.price = price;
        return true;
    }

    public boolean setInventory(int inventory) {
        if (inventory<=0)
            return false;
        this.inventory = inventory;

        return true;
    }

    public boolean setCategory(Category category) {
        this.category = category;
        return true;
    }

    public void setSpecialAttributes(HashMap<String, String> specialAttributes) {
        this.specialAttributes = specialAttributes;
    }

    public static ArrayList<Product> getProductFieldForSort(String field) {
        if (field.equalsIgnoreCase("rate")) {
            Comparator<Product> rateCompare = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return (int) -(o1.getAverageOfRates()-o2.getAverageOfRates());
                }
            };
            Collections.sort(allProducts,rateCompare);

        } else if (field.equalsIgnoreCase("reviewNumber")) {
            Comparator<Product> reviewCompare = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return -(o1.getReviewNumber() - o2.getReviewNumber());
                }
            };
            Collections.sort(allProducts,reviewCompare);
        } else if (field.equalsIgnoreCase("inventory")) {
            Comparator<Product> inventoryCompare = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return -(o1.getInventory() - o2.getInventory());
                }
            };
            Collections.sort(allProducts, inventoryCompare);

        } else if (field.equalsIgnoreCase("name")) {
            Comparator<Product> nameCompare = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };
            Collections.sort(allProducts, nameCompare);

        } else if (field.equalsIgnoreCase("price")) {

            Comparator<Product> priceCompare = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return (int) -(o1.getPrice() - o2.getPrice());
                }
            };
            Collections.sort(allProducts, priceCompare);
        }
        return allProducts;
    }

    public String showSummeryDetailsOfProduct() {
        String result = "description :\n" + description + "\n" + "price :\n" + price + "\n" + "category :\n" + category.getCategoryName() + "\n" + "seller :\n" + seller.getUsername() + "\n" + "average :\n" + getAverageOfRates();
        return result;
    }

    /**
     * maybe it has mistake
     *
     * @return
     */
    public HashMap<String, String> attributeShow() {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put(String.valueOf(getProductId()), "id");
        attributes.put(getName(), "name");
        attributes.put(getCompany(), "company");
        attributes.put(getSeller().getUsername(), "seller");
        attributes.put(productStatus.name(), "status");
        attributes.put(String.valueOf(getPrice()), "price");
        attributes.put(String.valueOf(getInventory()), "inventory");
        attributes.put(getCategory().getCategoryName(), "category");
        attributes.put(getDescription(), "description");
        if (getWhoBoughtThisGood() != null) {
            for (Customer customer : getWhoBoughtThisGood()) {
                attributes.put(customer.getUsername(), "buyer");
            }
        }
        if (ratesList != null) {
            for (Rate rate : ratesList) {
                attributes.put(rate.getRater().getUsername() + ":" + rate.getRate(), "rate");
            }
        }
        for (Comment comment : commentsList) {
            attributes.put(comment.getCommenter().getUsername() + ":" + comment.getCommentText(), "comment");
        }
        for (String s : getSpecialAttributes().keySet()) {
            attributes.put(specialAttributes.get(s), s);
        }
        return attributes;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * just for test
     *
     * @param commentsList
     */
    public void setCommentsList(ArrayList<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    /**
     * just for test
     *
     * @param ratesList
     */
    public void setRatesList(ArrayList<Rate> ratesList) {
        this.ratesList = ratesList;
    }

    /**
     * just for test
     *
     * @param whoBoughtThisGood
     */
    public void setWhoBoughtThisGood(ArrayList<Customer> whoBoughtThisGood) {
        this.whoBoughtThisGood = whoBoughtThisGood;
    }

    public ArrayList<Comment> getCommentsList() {
        return commentsList;
    }

    public String showComments() {
        String result = "";
        for (Comment comment : getCommentsList()) {
            result += comment.getCommenter().getUsername()+" : title -> "+"( "+comment.getTitle()+" ) "+comment.getCommentText()+"\n";
        }
        return result;
    }

    public ProductAndOffStatus getProductStatus() {
        return productStatus;
    }

    public int getReviewNumber() {
        return reviewNumber;
    }

    public boolean setReviewNumber(int reviewNumber) {
        if (reviewNumber<=0)
            return false;
        this.reviewNumber = reviewNumber;
        return true;
    }
    public ArrayList<Customer> sortBuyers(String field){
        if (field.equalsIgnoreCase("username")){
            Comparator<Customer> usernameCompare = new Comparator<Customer>() {
                @Override
                public int compare(Customer o1, Customer o2) {
                    return o1.getUsername().compareTo(o2.getUsername());
                }
            };
            Collections.sort(whoBoughtThisGood,usernameCompare);

        }else if (field.equalsIgnoreCase("number")){
            Comparator<Customer> numberCompare = new Comparator<Customer>() {
                @Override
                public int compare(Customer o1, Customer o2) {
                    return -(o1.getNumberOfBoughtProduct(Product.getProductWithId(productId))-o2.getNumberOfBoughtProduct(Product.getProductWithId(productId)));
                }
            };
            Collections.sort(whoBoughtThisGood,numberCompare);
        }
        return whoBoughtThisGood;
    }

    public ArrayList<Rate> getRatesList() {
        return ratesList;
    }

    public static void resetProductNumberToZero() {
        Product.productNumber = 0;
    }
}
