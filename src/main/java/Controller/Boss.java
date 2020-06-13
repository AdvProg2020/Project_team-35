package Controller;

import Model.DiscountCode;
import Model.Off;

import java.time.LocalDateTime;

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

