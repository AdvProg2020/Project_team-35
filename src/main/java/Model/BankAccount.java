package Model;

public class BankAccount {
    private Double money;
    private String numberOfAccount;

    public BankAccount(String numberOfAccount) {
        this.numberOfAccount= numberOfAccount;
    }

    public String getNumberOfAccount() {
        return numberOfAccount;
    }

    public void setNumberOfAccount(String numberOfAccount) {
        this.numberOfAccount = numberOfAccount;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
