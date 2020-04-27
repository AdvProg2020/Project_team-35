import Model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductTest {
    private Product product;
    private Seller seller;
    @Test
    public void getProductInfoTest(){
        ArrayList<String> specials = new ArrayList<>();
        specials.add("weight");
        specials.add("long");
        Category category = new Category("labaniat",specials ) ;
        HashMap<String , String> specialAttributes = new HashMap<>();
        specialAttributes.put("wight", "23.34");
        specialAttributes.put("long"  , "43");
        product = new Product("cheese","mihan" , 20.0 ,seller,2 ,category,specialAttributes);



        String productInfo = null;
        productInfo = "name : "+"chesse"+"\n"
                +"company name : "+"mihan"+"\n"
                +"price : "+20.0+"\n"
                +"seller : \n"+seller.getPersonalInfo()+"\n"
                +"inventory : "+2+"\n"
                +"category : "+category.getCategoryName()+"\n"
                +"product id : "+product.getProductId()+"\n";
        productInfo += "special Attributes : \n";
        for (String s : specialAttributes.keySet()) {
            productInfo = productInfo + s+" : "+specialAttributes.get(s)+"\n";
        }
        productInfo+= "comment List : \n";
      /*  for (Comment comment : commentsList) {
            productInfo+= comment.getCommentInfo();
        }
        productInfo+= "rate list : \n";
        for (Rate rate : ratesList) {
            productInfo+= rate.getRateInfo();
        }*/







        Assert.assertEquals(product.toString() , "s");
    }
}
