package Controller;

import Controller.Exceptions.NullProduct;
import Controller.Exceptions.ProductIsNotConfirmed;
import Model.Product;
import Model.ProductAndOffStatus;

import java.util.ArrayList;

public class OffBoss {
    public static Product ProductPageTransfer(int id) throws NullProduct, ProductIsNotConfirmed {
        Product product = Product.getProductWithId(id);
        if (product==null){
            throw new NullProduct("this is not existed",1);
        }
        if (!product.getProductStatus().equals(ProductAndOffStatus.CONFIRMED)){
            throw new ProductIsNotConfirmed("this is not confirmed",2);
        }
        return product;
    }
    public void startCreateOff(ArrayList<Integer> includedProductsIds, double percent, double maximum, char date) {

    }
    public void startEditOff(int offId, ArrayList<Integer> newIncludedProducts, double newPercent, double newMaximum, char newDate) {

    }
    public void startRemoveOff(int OffId) {

    }

}
