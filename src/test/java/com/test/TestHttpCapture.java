package com.test;

import com.george.tonia.HttpCapture;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class TestHttpCapture {

    @Test
    public void testGetHttpContent()throws IOException{
        String content = HttpCapture.getHttpContent("http://www.brainjar.com/java/host/test.html");
        assertTrue(content.contains("This is a very simple HTML file."));
    }

    @Test
    public void testGetPageSize()throws IOException{
        String size = HttpCapture.getPageSize("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html");
        assertEquals("82",size);
    }
}
