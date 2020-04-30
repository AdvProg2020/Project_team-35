package Controller;

import Controller.SellerBoss;
import Model.Seller;
import org.junit.Assert;
import org.junit.Test;

public class SellerBossTest {
   private Seller seller;

    @Test
    public void sellerCreditTest(){
        seller = new Seller("a","a","a","a","s","d","d");
       Assert.assertTrue( (seller.getMoney() == SellerBoss.sellerCredit(seller)));
    }
    @Test
    public void salam(){

    }
}
