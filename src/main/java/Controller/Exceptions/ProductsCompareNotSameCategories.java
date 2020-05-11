package Controller.Exceptions;

public class ProductsCompareNotSameCategories extends Exception {
    private int id;
    public ProductsCompareNotSameCategories(int id,String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
