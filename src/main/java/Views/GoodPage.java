package Views;

import Controller.ProductBoss;
import Model.Product;

import java.util.ArrayList;
import java.util.HashMap;

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
                        super.execute();
                    }
                });
                subPages.put("2", new Page("select seller",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });

            }

            @Override
            public void execute() {
                System.out.println(ProductBoss.showSummeryOfProductDetails(product));
                setSubPages(subPages);
                Page nextPage = null;
                show();
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("1")){

                }else if (command.equalsIgnoreCase("2")){

                }else if (command.equalsIgnoreCase(String.valueOf(subPages.keySet().size()+1))){
                    nextPage = parentPage;
                }else {

                }
                nextPage.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }
    private Page attributes(){
        return new Page("attributes" ,  this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }
    private Page compare(){
        return new Page("compare" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }
    private Page comments(){
        return new Page("Comments" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("Add comment" , this);
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }

    @Override
    public boolean show() {
        super.show();
        return false;
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public void setSubPages(HashMap<String, Page> subPages) {
        super.setSubPages(subPages);
    }
}
