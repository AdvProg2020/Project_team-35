package Controller;

import Model.Customer;
import Model.DiscountCode;
import Model.Off;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Boss {

    public static boolean removeExpiredOffsAndDiscountCodes() {
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
            if (now.isAfter(off.getFinalDate())) {
                Off.getAllActiveOffs().remove(off);
                j--;
            }
        }
        return true;
    }
}
