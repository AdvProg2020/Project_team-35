package Controller;

public class SellerBoss {
    public static void editPersonalInfoGetFieldName(String field) throws UserNameChange, FieldDoesNotExist {
        if (field.equalsIgnoreCase("username")){
            throw new UserNameChange("you can't change your username");
        }
        if (!(field.equalsIgnoreCase("firstName")|| field.equalsIgnoreCase("lastName") || field.equalsIgnoreCase("password") || field.equalsIgnoreCase("email") || field.equalsIgnoreCase("phoneNumber") || field.equalsIgnoreCase("companyName"))){
            throw new FieldDoesNotExist("this field is valid");
        }
    }
}
