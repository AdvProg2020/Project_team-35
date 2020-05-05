
package Views;

import Controller.CustomerBoss;
import Controller.Exceptions.NullProduct;
import Controller.Exceptions.ProductIsFinished;
import Controller.ProductBoss;
import Model.Account;
import Model.Customer;

import java.util.HashMap;
import java.util.regex.Matcher;

public class BuyerPage extends Page {
    public BuyerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("2", viewCart());
        subPages.put("4", viewOrders());
        subPages.put("3", viewBalance());
        subPages.put("1", viewDiscountCodes());

    }

    private Page viewCart(){
        return new Page("view cart",this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("4", new Page("show products",this) {
                    @Override
                    public void execute() {
                        System.out.println(name);
                        System.out.println(CustomerBoss.showProductsInCart((Customer)Account.getOnlineAccount()));
                        parentPage.execute();
                    }
                });
                subPages.put("2", new Page("view product",this) {
                    @Override
                    public void execute() {
                        String command = scanner.nextLine();
                        String regex = "^view product (\\d+)$";
                        Page nextPage = null;
                        Matcher matcher = getMatcher(command,regex);
                        if (command.matches(regex)){
                            int productId = Integer.parseInt(matcher.group(1));
                            try {
                                nextPage = ProductBoss.goToGoodPage(productId);
                            } catch (NullProduct nullProduct) {
                                nullProduct.printStackTrace();
                            }
                        }else if (command.equalsIgnoreCase("back")){
                            nextPage = parentPage;
                        }else {
                            System.err.println("invalid command");
                            nextPage = this;
                        }
                        nextPage.execute();
                    }
                });
                subPages.put("5", new Page("increase number",this) {
                    @Override
                    public void execute() {
                        System.out.println(name);
                        String command = scanner.nextLine();
                        String regex = "^increase (\\d+)$";
                        Page nextPage = null;
                        Matcher matcher = getMatcher(command,regex);
                        if (command.matches(regex)){
                            int id = Integer.parseInt(matcher.group(1));
                            try {
                                CustomerBoss.increaseNumber(id,(Customer)Account.getOnlineAccount());
                                nextPage = parentPage;
                            } catch (NullProduct | ProductIsFinished nullProduct) {
                                nullProduct.printStackTrace();
                            }

                        }else if (command.equalsIgnoreCase("back")){
                            nextPage = parentPage;
                        }else {
                            System.err.println("invalid command");
                            nextPage = this;
                        }
                    }
                });
                subPages.put("6", new Page("decrease number",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("1", new Page("show total price",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("3", new Page("purchase",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }
    private Page viewOrders(){
        return new Page("view orders" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("show order" , this);
                subPages.put("rate" , this);
            }

            @Override
            public void execute( ) {
                super.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }
    private Page viewBalance(){
        return new Page("view balance" , this) {
            @Override
            public void execute() {
                System.out.println("balance:");
                System.out.println(CustomerBoss.showMoney((Customer) Account.getOnlineAccount()));
                parentPage.execute();
            }
        };
    }
    private Page viewDiscountCodes(){
      return new Page("view discount codes"  , this) {
          @Override
          public void execute() {
              for (String discountCodeInformation : CustomerBoss.showDiscountCodes((Customer) Account.getOnlineAccount())) {
                  System.out.println(discountCodeInformation);
              }
          }
      }  ;
    }
    private Page logout(){
        return new Page("logout" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
            }

            @Override
            public void execute( ) {
                super.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }
    @Override
    public boolean show() {
        super.show();
        return false;
    }

    @Override
    public void execute( ) {
    super.execute();
    }
}
