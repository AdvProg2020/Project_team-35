package Views;

import Controller.ProductBoss;
import Model.Account;
import Model.Customer;
import Model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class GoodPage extends Page {
    private Product product;
    private int numberOfReview;
    private static ArrayList<GoodPage> allGoodsPages = new ArrayList<>();
    public GoodPage(String name, Page parentPage, Product product) {
        super(name, parentPage);
        this.product = product;
        subPages.put("1",digest());
        subPages.put("2",attributes());
        subPages.put("3",compare());
        subPages.put("4",comments());
        subPages.put("5",showRates());
        subPages.put("6",showComment());
        subPages.put("7",new RegisteringPanel("registering panel",this));

    }
    private Page showComment(){
        return new Page("show comment",this) {
            @Override
            public void execute() {
                super.execute();
            }
        };
    }
    private Page showRates(){
        return new Page("show rate",this) {
            @Override
            public void execute() {

            }
        };
    }

    /**
     * this method is used in controller for moving from productPage to a special Products page
     * @param product
     * @return
     */
    public static GoodPage getGoodPage(Product product){
        for (GoodPage goodsPage : allGoodsPages) {
            if (goodsPage.getProduct().equals(product))
                return goodsPage;
        }
        return null;
    }

    public static ArrayList<GoodPage> getAllGoodsPages() {
        return allGoodsPages;
    }

    public Product getProduct() {
        return product;
    }

    private Page digest(){
        return new Page("digest" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("1", new Page("add to cart",this) {
                    @Override
                    public void execute() {
                        //this place should change for multi user program
                        Page nextPage = null;
                        if (Account.getOnlineAccount()==null){
                            System.err.println("first login");
                                nextPage = new RegisteringPanel("registering panel",this);
                        }else if (!(Account.getOnlineAccount() instanceof Customer)){
                            System.err.println("this process is just for customer");
                            nextPage = parentPage;
                        }
                        else if (product.getInventory()!=0){
                            Customer customer = (Customer) Account.getOnlineAccount();
                            customer.getListOFProductsAtCart().put(product,1);
                            System.out.println("successfully added");
                            nextPage = parentPage;
                        }else{
                            System.err.println("this product is finished");
                            nextPage = parentPage;
                        }
                        nextPage.execute();
                    }
                });
                subPages.put("2", new Page("select seller",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });

            }

        };
    }
    private Page attributes(){
        return new Page("attributes" ,  this) {
            @Override
            public void execute() {
                System.out.println(name);
              HashMap<String,String> attributes =    ProductBoss.showAttributes(product);
                for (String s : attributes.keySet()) {
                    System.out.println(attributes.get(s)+":\n"+s);
                }
            parentPage.execute();
            }
        };
    }
    private Page compare(){
        return new Page("compare" , this) {
            @Override
            public void execute() {
                System.out.println(name);
                String command = scanner.nextLine();
                String regex = "^compare (\\d+)$";
                Matcher matcher = getMatcher(command,regex);
                Page nextPage = null;
                if (command.matches(regex)){
                    nextPage = parentPage;
                }else if (command.equalsIgnoreCase("back")){
                    nextPage = parentPage;
                }else {
                    System.err.println("invalid command");
                    nextPage = this;
                }
                nextPage.execute();
            }
        };
    }
    private Page comments(){
        return new Page("Comments" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("Add comment", new Page("add comment",this) {
                    @Override
                    public void execute() {
                        Page nextPage = null;
                        System.out.println("Title:");
                        String title = scanner.nextLine();
                        if (title.equalsIgnoreCase("back")){
                            nextPage = parentPage;
                        }else {
                            System.out.println("Content:");
                            String content = scanner.nextLine();
                            if (content.equalsIgnoreCase("back")){
                                nextPage = this;
                            }
                            else {
                                ProductBoss.makeComment(content,title,product,(Customer)Account.getOnlineAccount());
                                System.out.println("add comment successfully");
                                nextPage = parentPage;
                            }
                        }
                        nextPage.execute();
                    }
                });
            }
        };
    }
}
