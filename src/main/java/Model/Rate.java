package Model;

public class Rate {
    private int rateId;
    private Customer rater;
    private double rate;
    private static int allRatesNumber;
    private Product product;

    public Rate(Customer rater, double rate, Product product) {
        this.rater = rater;
        this.rate = rate;
        this.product = product;
    }

    public Customer getRater() {
        return rater;
    }

    public double getRate() {
        return rate;
    }

    public Product getProduct() {
        return product;
    }

    public String getRateInfo(){
        String toString = null;
        toString = rater.getUsername()+" : "+ rate+"  this rate is for product "+getProduct().getName()+" with id "+ getProduct().getProductId()+"\n";
        return toString;
    }
}
