import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import strategy.GMapHelper;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
/**
 * Created by skunnumkal on 9/17/13.
 */
public class GMapHelperTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

//    @Test
//    public void testGoodAddressGMaps(){
//        final String address = "108 71st Street Guttenberg, NJ ";
//        try{
//        String result = GMapHelper.getAddressJson(address);
//        //System.out.println(result);
//        assertThat(result.contains("ROOFTOP"));
//        }catch(Exception e){
//            Assert.fail();
//        }
//    }
//    @Test
//    public void testVagueAddressGMaps(){
//        final String address = "NJ ";
//        try{
//            String result = GMapHelper.getAddressJson(address);
//            //System.out.println(result);
//            assertThat(result.contains("APPROXIMATE"));
//        }catch(Exception e){
//            Assert.fail();
//        }
//    }
//
//    @Test
//    public void testBadAddressGMaps(){
//        final String address = "270 Jenkinsy Blvd Mercy City NJ 07302";
//        try{
//            String result = GMapHelper.getAddressJson(address);
//            //System.out.println(result);
//            assertThat(result.contains("APPROXIMATE"));
//        }catch(Exception e){
//            Assert.fail();
//        }
//    }
}
