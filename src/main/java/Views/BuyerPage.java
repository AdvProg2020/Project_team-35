
package Views;

import java.util.HashMap;

public class BuyerPage extends Page {
    public BuyerPage(String name, Page parentPage) {
        super(name, parentPage);
        HashMap<String , Page> subPages = new HashMap<String, Page>();

    }
    private Page viewPersonalInfo(){
        return new Page("view personal info" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("edit",this);
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public void show() {
                super.show();
            }
        };
    }
    private Page viewCart(){
        return new Page("view cart",this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("show products" , this);
                subPages.put("view",this);
                subPages.put("increase" , this);
                subPages.put("decrease",this);
                subPages.put("show total price" , this);
                subPages.put("purchase" , this);
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public void show() {
                super.show();
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
            public void execute() {
                super.execute();
            }

            @Override
            public void show() {
                super.show();
            }
        };
    }
    private Page viewBalance(){
        return new Page("view balance" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public void show() {
                super.show();
            }
        };
    }
    private Page viewDiscountCodes(){
      return new Page("view page"  , this) {
          @Override
          public void setSubPages(HashMap<String, Page> subPages) {
              super.setSubPages(subPages);
          }

          @Override
          public void execute() {
              super.execute();
          }

          @Override
          public void show() {
              super.show();
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
            public void execute() {
                super.execute();
            }

            @Override
            public void show() {
                super.show();
            }
        };
    }
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void execute() {
        super.execute();
    }
}
