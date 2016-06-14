package com.george.tonia;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/*This class is used to capture html content and return it as a string*/
public class HttpCapture {

/*This method gets the conten from the pages and converts the response to string before returning*/
    public static String getHttpContent(String url) throws IOException{

        CloseableHttpClient client =HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);

        InputStream inStream =null;
        StringWriter writer = null;
        String responseAsString = "";

        try{
            HttpEntity entity1 = response.getEntity();

            if (entity1 != null){
                inStream= entity1.getContent();
            }
            while(inStream.read() != -1){
                writer = new StringWriter();
                IOUtils.copy(inStream, writer);
                responseAsString = writer.toString();
            }
        }finally{
            inStream.close();
            writer.close();
            response.close();
        }
        return responseAsString;
    }

/*Pretty much same functionality as the one above but in a different context. Gets a header value, 'Content-Length'.*/
    public static String getPageSize(String url) throws IOException{

        CloseableHttpClient client =  HttpClients.createDefault();
        Header[] headers = client.execute(new HttpGet(url)).getAllHeaders();;
        String sizeInKb = "";

        for(Header h:  headers){
            if (h.getName().equals("Content-Length")){
                Integer size = Integer.parseInt(h.getValue())/1024;
                sizeInKb = size.toString();
            }
        }
        return sizeInKb;
    }
}
