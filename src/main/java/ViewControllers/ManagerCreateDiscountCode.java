package ViewControllers;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ManagerCreateDiscountCode {

    public TextField codeText;
    public TextField percent;
    public TextField maximumDiscountAmount;
    public TextField repeatRate;
    public DatePicker startDate;
    public DatePicker finalDate;
    public TextArea customersUserNames;
    public TextField maximumTotalPrice;
    public CheckBox noMinimumCheckBox;
    public Button createButton;
    public Label information;


    public void createDiscountCode(MouseEvent mouseEvent) {
        LocalDate localDate = startDate.getValue();
        System.out.println(localDate.toString());
    }
}
