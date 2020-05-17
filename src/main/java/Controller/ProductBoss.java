package Controller;

import Controller.Exceptions.NullProduct;
import Controller.Exceptions.ProductIsFinished;
import Controller.Exceptions.ProductsCompareNotSameCategories;
import Model.Comment;
import Model.Customer;
import Model.Product;
import Model.ProductAndOffStatus;
import Views.GoodPage;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductBoss {

    public static GoodPage goToGoodPage(int id) throws NullProduct {
        Product product = Product.getProductWithId(id);
        GoodPage goodPage = GoodPage.getGoodPage(product);
        if (goodPage == null) {
            throw new NullProduct("this product does not exist", 1);
        }
        return goodPage;
    }

    public static ArrayList<Product> sortProduct(String field)  {

        ArrayList<Product> confirmedProducts = new ArrayList<>();
        for (Product product : Product.getProductFieldForSort(field)) {
            if (product.getProductStatus().equals(ProductAndOffStatus.CONFIRMED))
            confirmedProducts.add(product);
        }
        return confirmedProducts;
    }

    public static HashMap<String, String> showAttributes(Product product) {
        return product.attributeShow();
    }

    public static StringBuilder compare(String id, Product product2) throws ProductsCompareNotSameCategories, ProductIsFinished, NullProduct {
        Product product = Product.getProductWithId(Integer.parseInt(id));
        if (product == null) {
                throw new NullProduct("null product",1);
        } else if (product.getInventory() == 0) {
                throw new ProductIsFinished(2,"finished");
        }else if (!product.getCategory().equals(product2.getCategory())){
                throw new ProductsCompareNotSameCategories(3,"not same category");
        }
        HashMap<String, String> a1 = product2.attributeShow();
        HashMap<String, String> a2 = product.attributeShow();
        StringBuilder result = new StringBuilder();
        result.append(product2.getName()+"**********"+product.getName()+"\n");
        for (String s : a1.keySet()) {
            for (String s1 : a2.keySet()) {
                if (a2.get(s1).equals(a1.get(s))) {
                    result.append(a1.get(s) + ":\n");
                    if (s!=null && s1!=null) {
                        result.append(s + "**********" + s1 + "\n");
                    }else if (s==null){
                        result.append(" "+"**********"+s1+"\n");
                    }else if (s1==null){
                        result.append(s+"**********"+" "+"\n");
                    }

                }
            }
        }
        return result;
    }

    public static void makeComment(String comment,String title , Product product , Customer customer){
                Comment comment1 = new Comment(product,comment,customer,title);
    }
    public static String showSummeryOfProductDetails(Product product) {
        return product.showSummeryDetailsOfProduct();
    }

    public static HashMap<String , String> showComments(Product product){
        HashMap<String , String> comments = new HashMap<>();
        for (Comment comment : product.getCommentsList()) {
            comments.put(comment.getCommenter().getUsername(),comment.getCommentText());
        }
        return comments;
    }
    public static void updateReviewNumberOfAProductPage(Product product){
        product.setReviewNumber(product.getReviewNumber()+1);
    }
}
