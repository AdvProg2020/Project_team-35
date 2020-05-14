package Views;

import Controller.Exceptions.NullProduct;
import Controller.OffBoss;
import Controller.Exceptions.ProductIsNotConfirmed;
import Model.Off;
import Model.Product;
import Model.ProductAndOffStatus;

import java.util.regex.Matcher;

public class Offs extends Page{
    public Offs(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("1",new RegisteringPanel("registering  panel",this));
        subPages.put("2", goToProductPage());

    }
    private Page goToProductPage(){
        return new Page("go to product page",this) {
            @Override
            public void execute() {
                System.out.println("enter command:");
                Page nextPage = null;
                String command = scanner.nextLine();
                String regex = "^(\\d+)$";
                Matcher matcher = getMatcher(command,regex);
                matcher.matches();
                if (command.matches(regex)){
                    String id = matcher.group(1);
                    try {
                        Product product = OffBoss.ProductPageTransfer(Integer.parseInt(id));
                        nextPage = new GoodPage(product.getName() , this,product);
                    } catch (NullProduct | ProductIsNotConfirmed nullProduct) {
                        nullProduct.printStackTrace();
                        nextPage = this;
                    }
                }
                else  if (command.equalsIgnoreCase("back")){
                    nextPage = parentPage;
                }else if (command.equalsIgnoreCase("help")){
                    System.out.println("[productId] *** help *** back");
                    nextPage = this;
                }else {
                    System.err.println("invalid command");
                    nextPage = this;
                }
                nextPage.execute();
            }
        };
    }

    @Override
    public void execute() {
        for (Off activeOff : Off.allActiveOffs) {

            if (activeOff.getOffStatus().equals(ProductAndOffStatus.CONFIRMED)){
                System.out.println("off id : "+activeOff.getOffId());
                for (Product product : activeOff.getIncludedProducts()) {
                    if (product.getPrice()>activeOff.getMaximumAmountOfOff())
                        System.out.println(product.getName()+" : "+"    real price : "+product.getPrice()+"     off price : "+(product.getPrice()-activeOff.getMaximumAmountOfOff()));
                    else if (product.getPrice()<activeOff.getMaximumAmountOfOff()){
                        System.out.println(product.getName()+" : "+"    real price : "+product.getPrice()+"     off price : "+(product.getPrice()*(1-activeOff.getOffPercent()/100)));
                    }
                }
            }
        }
        super.execute();
    }
}
