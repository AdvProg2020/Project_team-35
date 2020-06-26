package Controller;

import Model.DiscountCode;
import Model.Off;
import Model.Product;
import javafx.scene.image.Image;

import java.awt.*;
import java.time.LocalDateTime;

public class Boss {
    public static boolean removeExpiredOffsAndDiscountCodes() {
        Image offImage = new Image("./Resources/off.jpg");
        Image notImage = new Image("./Resources/not.jpg");
        for (Product product : Product.getAllProducts()) {
            if (product.getInventory()==0){
                product.setStatusImage(notImage);
            }
           else if (Off.isThereProduct(product)){
                product.setStatusImage(offImage);
            }else {
               product.setStatusImage(product.getProductImage());
            }
        }




        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < DiscountCode.getAllDiscountCodes().size(); i++) {
            DiscountCode discountCode = DiscountCode.getAllDiscountCodes().get(i);
            if (now.isAfter(discountCode.getFinalDate())) {
                DiscountCode.getAllDiscountCodes().remove(discountCode);
                i--;
            }
        }

        for (int j = 0; j < Off.getAllActiveOffs().size(); j++) {
            Off off = Off.getAllActiveOffs().get(j);
            if (off != null) {
                LocalDateTime localDateTime = off.getFinalDate();
                if (localDateTime != null) {
                    if (localDateTime.isBefore(LocalDateTime.now())) {
                        Off.getAllActiveOffs().remove(off);
                        j--;
                    }
                }
            }
        }
        return true;
    }
}

