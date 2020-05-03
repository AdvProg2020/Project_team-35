package Views;

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
        System.out.println(product.getName());
    }
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
                subPages.put("add to cart" , this);
                subPages.put("select seller" , this);

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
