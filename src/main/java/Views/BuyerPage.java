
package Views;

import Controller.CustomerBoss;
import Controller.Exceptions.NullProduct;
import Controller.Exceptions.ProductIsFinished;
import Controller.ProductBoss;
import Model.Account;
import Model.BuyLog;
import Model.Customer;
import Model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class BuyerPage extends Page {
    private String phone = "";
    private String address = "";
    public BuyerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("2", viewCart());
        subPages.put("4", viewOrders());
        subPages.put("3", viewBalance());
        subPages.put("1", viewDiscountCodes());

    }

    private Page viewCart() {
        return new Page("view cart", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("4", new Page("show products", this) {
                    @Override
                    public void execute() {
                        System.out.println(name);
                        System.out.println(CustomerBoss.showProductsInCart((Customer) Account.getOnlineAccount()));
                        parentPage.execute();
                    }
                });
                subPages.put("2", new Page("view product", this) {
                    @Override
                    public void execute() {
                        String command = scanner.nextLine();
                        String regex = "^view product (\\d+)$";
                        Page nextPage = null;
                        Matcher matcher = getMatcher(command, regex);
                        if (command.matches(regex)) {
                            int productId = Integer.parseInt(matcher.group(1));
                            try {
                                nextPage = ProductBoss.goToGoodPage(productId);
                            } catch (NullProduct nullProduct) {
                                nullProduct.printStackTrace();
                            }
                        } else if (command.equalsIgnoreCase("back")) {
                            nextPage = parentPage;
                        } else {
                            System.err.println("invalid command");
                            nextPage = this;
                        }
                        nextPage.execute();
                    }
                });
                subPages.put("5", new Page("increase number", this) {
                    @Override
                    public void execute() {
                        System.out.println(name);
                        String command = scanner.nextLine();
                        String regex = "^increase (\\d+)$";
                        Page nextPage = null;
                        Matcher matcher = getMatcher(command, regex);
                        if (command.matches(regex)) {
                            int id = Integer.parseInt(matcher.group(1));
                            try {
                                CustomerBoss.increaseNumber(id, (Customer) Account.getOnlineAccount(), 1);
                                nextPage = parentPage;
                            } catch (NullProduct | ProductIsFinished nullProduct) {
                                nullProduct.printStackTrace();
                            }

                        } else if (command.equalsIgnoreCase("back")) {
                            nextPage = parentPage;
                        } else {
                            System.err.println("invalid command");
                            nextPage = this;
                        }
                    }
                });
                subPages.put("6", new Page("decrease number", this) {
                    @Override
                    public void execute() {
                        System.out.println(name);
                        String command = scanner.nextLine();
                        String regex = "^decrease (\\d+)$";
                        Page nextPage = null;
                        Matcher matcher = getMatcher(command, regex);
                        if (command.matches(regex)) {
                            int id = Integer.parseInt(matcher.group(1));
                            try {
                                CustomerBoss.increaseNumber(id, (Customer) Account.getOnlineAccount(), -1);
                                nextPage = parentPage;
                            } catch (NullProduct | ProductIsFinished nullProduct) {
                                nullProduct.printStackTrace();
                            }

                        } else if (command.equalsIgnoreCase("back")) {
                            nextPage = parentPage;
                        } else {
                            System.err.println("invalid command");
                            nextPage = this;
                        }
                    }
                });

                subPages.put("1", new Page("show total price", this) {
                    @Override
                    public void execute() {
                        System.out.println("your cart total price is = " + CustomerBoss.showTotalCartPrice((Customer) Account.getOnlineAccount()));
                        parentPage.execute();
                    }
                });
                subPages.put("3", new Page("purchase", this) {
                    @Override
                    public void setSubPages(HashMap<String, Page> subPages) {
                        subPages.put("receive", receiveInfo());
                        subPages.put("discount", discountCodeCheck());
                        subPages.put("payment", payment());
                    }

                    @Override
                    public void execute() {
                        System.out.println("your path");
                        setSubPages(subPages);
                        show();
                        receiveInfo().execute();
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

    private Page showOrder(){
        return new Page("show order",viewOrders()) {
            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private Page rate(){
        return new Page("rate",viewOrders()) {
            @Override
            public void execute() {
                super.execute();
            }
        };
    }
    private Page viewOrders() {
        return new Page("view orders", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("1", showOrder());
                subPages.put("2", rate());
            }

            @Override
            public void execute() {
                ArrayList<BuyLog> buyLogs = CustomerBoss.showBuyResume((Customer)Account.getOnlineAccount());
                for (BuyLog log : buyLogs) {
                    System.out.println(log.getOrderNumber());
                    HashMap<Product,Integer> products = CustomerBoss.showProductsOfALog(log);
                    for (Product product : products.keySet()) {
                        System.out.println(product.getName()+"  **************  "+products.get(product));
                    }
                }
                super.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }

    private Page viewBalance() {
        return new Page("view balance", this) {
            @Override
            public void execute() {
                System.out.println("balance:");
                System.out.println(CustomerBoss.showMoney((Customer) Account.getOnlineAccount()));
                parentPage.execute();
            }
        };
    }

    private Page viewDiscountCodes() {
        return new Page("view discount codes", this) {
            @Override
            public void execute() {
                for (String discountCodeInformation : CustomerBoss.showDiscountCodes((Customer) Account.getOnlineAccount())) {
                    System.out.println(discountCodeInformation);
                }
            }
        };
    }

    private Page logout() {
        return new Page("logout", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
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

    private Page receiveInfo() {
        return new Page("receive information", this) {
            @Override
            public void execute() {
                String regex = "";
                String command = "";
                boolean heChooseBack = false;
                Matcher matcher = null;
                Page nextPage = null;
               while (true){
                   System.out.println("phone number");
                   command = scanner.nextLine();
                   regex = "^(\\d+)$";
                   matcher = getMatcher(command,regex);
                   if (command.matches(regex)){
                       phone = matcher.group(1);
                       break;
                   }else if (command.equalsIgnoreCase("back")){
                            heChooseBack = true;
                            nextPage = parentPage;
                            break;
                   }else {
                       System.err.println("invalid command");
                   }
               }
               if (heChooseBack==false) {
                   while (true) {
                       System.out.println("address");
                       command = scanner.nextLine();
                       regex = "^(\\.+)$";
                       matcher = getMatcher(command,regex);
                       if (command.equalsIgnoreCase("back")) {
                           heChooseBack = true;
                           nextPage = parentPage;
                           break;
                       } else if (command.matches(regex)) {
                            address = matcher.group(1);
                            nextPage = discountCodeCheck();
                            break;
                       } else {
                        System.err.println("invalid command");
                       }
                   }
               }
               //we can add post code to this place
                nextPage.execute();
            }
        };
    }

    private Page discountCodeCheck() {
        return new Page("discount code page", this) {
            @Override
            public void execute() {
                String command = "";
                String regex = "";
                Matcher matcher = null;
                Page nextPage = null;
                while (true) {
                    System.out.println("discount code id: ");
                    command = scanner.nextLine();
                    regex = "^(\\d+)$";
                    matcher = getMatcher(command, regex);
                    if (command.matches(regex)) {
                        if (CustomerBoss.hasDiscountCodeWithId((Customer) Account.getOnlineAccount(), command))
                            CustomerBoss.useDiscountCode((Customer) Account.getOnlineAccount(), command);
                        else {
                            System.err.println("this discount code isn't available for you.");
                            continue;
                        }
                        nextPage = payment();
                        break;
                    }
                    else if (command.equalsIgnoreCase("back")) {
                        nextPage = parentPage;
                        break;
                    }
                    else System.err.println("invalid command");
                }
                nextPage.execute();
            }
        };
    }

    private Page payment() {
        return new Page("payment", this) {
            @Override
            public void execute() {
                if (!CustomerBoss.doPayment((Customer) Account.getOnlineAccount()))
                    System.err.println("your money is not enough! the purchase wasn't successful.");
                else
                    System.out.println("purchase successfully done!");
                parentPage.execute();
            }
        };
    }

    @Override
    public boolean show() {
        super.show();
        return false;
    }

    @Override
    public void execute() {
        super.execute();
    }
}
