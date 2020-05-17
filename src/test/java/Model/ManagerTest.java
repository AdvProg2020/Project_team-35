package Model;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ManagerTest {
    Manager manager = new Manager("username", "name", "lastName", "mail@e.ir", "09100577581", "09100577581");

    @Test
    public void getNewRequests() {
        Seller seller1 = new Seller("a","a","s","sd","ds","sad","asd");
        SellerRegisterRequest request = new SellerRegisterRequest(seller1);
        Manager.newRequests.add(request);
        ArrayList<Request> a = new ArrayList();
        a.add(request);
        Assert.assertEquals(Manager.getNewRequests().get(0), a.get(0));

    }

    @Test
    public void getCheckedRequests() {
        Seller seller1 = new Seller("a","a","s","sd","ds","sad","asd");
        SellerRegisterRequest request = new SellerRegisterRequest(seller1);
        Manager.checkedRequests.add(request);
        ArrayList<Request> a = new ArrayList();
        a.add(request);
        Assert.assertEquals(Manager.getCheckedRequests().get(0), a.get(0));

    }

    @Test
    public void isThereAnyManager() {
        Assert.assertEquals(Manager.isThereAnyManager(), true);
    }

    @Test
    public void testToString() {
    }

    @Test
    public void getNewRequestWithId() {
        Assert.assertEquals(Manager.getNewRequestWithId(1), null);
        Seller seller1 = new Seller("a","a","s","sd","ds","sad","asd");
        SellerRegisterRequest request = new SellerRegisterRequest(seller1);
        Manager.newRequests.add(request);
        Assert.assertEquals(Manager.getNewRequestWithId(1), request);
    }

    @Test
    public void getCheckedRequestWithId() {
        Assert.assertEquals(Manager.getCheckedRequestWithId(1), null);
        Seller seller1 = new Seller("a","a","s","sd","ds","sad","asd");
        SellerRegisterRequest request = new SellerRegisterRequest(seller1);
        Manager.checkedRequests.add(request);
        Assert.assertEquals(Manager.getCheckedRequestWithId(1), request);
    }

    @Test
    public void isThereNewRequestWithId() {
        Assert.assertEquals(Manager.isThereNewRequestWithId(1), false);
        Seller seller1 = new Seller("a","a","s","sd","ds","sad","asd");
        SellerRegisterRequest request = new SellerRegisterRequest(seller1);
        Manager.newRequests.add(request);
        Assert.assertEquals(Manager.isThereNewRequestWithId(1), true);
    }

    @Test
    public void isThereCheckedRequestWithId() {
        Assert.assertEquals(Manager.isThereCheckedRequestWithId(1), false);
        Seller seller1 = new Seller("a","a","s","sd","ds","sad","asd");
        SellerRegisterRequest request = new SellerRegisterRequest(seller1);
        Manager.checkedRequests.add(request);
        Assert.assertEquals(Manager.isThereCheckedRequestWithId(1), true);

    }

    @Test
    public void getPersonalInfo() {
        Assert.assertEquals(manager.getPersonalInfo(),     "Type: Manager\n" +
                "Username: " + manager.getUsername() + "\n" +
                "Name: " + manager.getFirstName() + "\n" +
                "Email: " + manager.getEmail() + "\n" +
                "PhoneNumber: " + manager.getPhoneNumber());
    }

    @Test
    public void getShortInfo() {
        Assert.assertEquals(manager.getShortInfo(),"UserName : " + manager.getUsername() + "  --  " + "Type : Manager" + " -- Condition: " + manager.getIsConfirmedOrWaitForCheck());

    }

    @Test
    public void getAllManagers() {
        ArrayList<Manager> a = new ArrayList<>();
        a.add(manager);
        Assert.assertEquals(Manager.getAllManagers(), a);
    }
}
