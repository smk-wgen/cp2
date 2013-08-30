package strategy;

import models.UserAddress;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by skunnumkal on 7/11/13.
 */
public class GMapHelper {
    // URL prefix to the geocoder
    private static final String GEOCODER_REQUEST_PREFIX_FOR_JSON = "http://maps.google.com/maps/api/geocode/json";
    private static final String encoding = "UTF-8";
    public static String getAddressJson(UserAddress address){
        String formattedString = formatAddress(address);
        // prepare a URL to the geocoder
        URL url = null;
        try {
            url = new URL(GEOCODER_REQUEST_PREFIX_FOR_JSON + "?address=" + URLEncoder.encode(formattedString, "UTF-8") + "&sensor=false");
            //prepare an HTTP connection to geocoder
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            StringWriter writer = new StringWriter();
            IOUtils.copy(conn.getInputStream(), writer, encoding);
            String theString = writer.toString();
            return theString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }


       return "";
    }

    private static String formatAddress(UserAddress address){
        StringBuffer sb = new StringBuffer();
        sb.append(address.line1().replace(' ','+'));
        sb.append('+');
        if(!address.line2().isEmpty()){
            sb.append(address.line2().replace(' ','+'));
            sb.append('+');
        }

        sb.append(address.city().replace(' ','+'));
        sb.append('+');
        sb.append(address.state().replace(' ','+'));
        sb.append('+');
        sb.append(address.zip());
        System.out.println("AddressString =" + sb.toString());
        return sb.toString();

    }
}
