package Controller;

public class ProductIsNotConfirmed extends  Exception {
    private  int id;
    public ProductIsNotConfirmed(String message,int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
