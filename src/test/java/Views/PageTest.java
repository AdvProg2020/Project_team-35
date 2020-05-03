package Views;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.junit.Assert;
import org.junit.Test;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class PageTest {
    @Test
    public void setScannerTest(){
        Scanner scanner = new Scanner(System.in);
        Assert.assertTrue(Page.setScanner(scanner));
    }
    @Test
    public void checkFormatOfPersonalInformationTest(){
        String type = "email address";
        String input = "asdsadsaasd@s.w";
        String type2 = "phone number";
        String input2 = "23";
        Assert.assertTrue(Page.checkFormatOfPersonalInformation(type,input) && Page.checkFormatOfPersonalInformation(type2,input2));
    }
    @Test
    public void getMatcherTest(){
        String input = "create 22";
        String regex = "create (\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        Assert.assertNotEquals(matcher,Page.getMatcher(input,regex));
    }

}