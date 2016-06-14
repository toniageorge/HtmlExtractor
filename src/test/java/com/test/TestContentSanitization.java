package com.test;


import com.george.tonia.ContentSanitization;
import com.george.tonia.HttpCapture;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;

/* This class tests 'getProductList' method in 'ContentSanitization' class.
* PowerMockito is used to to mock two static methods in 'HttpCapture' class and 'getItemInfo' method within 'ContentSanitization' class.*/

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpCapture.class)
public class TestContentSanitization {

    @Test
    public void testGetProductList()throws IOException{

        String outFromGetHttpContent = "<body><divclass=\"productInfo\"><a href=\"http://www.test.com\"><img src =apple.jpg\"/> Sweet Apple</a></body";
        String[] outFromGetItemInfo = {"Apple", "34kb","3.00", "Sweet and Crispy"};

        // Mocking classes
        ContentSanitization contentSanitization = PowerMockito.mock(ContentSanitization.class);
        PowerMockito.mockStatic(HttpCapture.class);
        // Setting return objects from mocked static methods
        PowerMockito.when(HttpCapture.getHttpContent("http://www.getIt.com")).thenReturn(outFromGetHttpContent);
        PowerMockito.when(HttpCapture.getPageSize("http://www.test.com\">")).thenReturn("10");
        // Unmocking one method whilst setting return object for the other.
        PowerMockito.when(contentSanitization.getProductList("http://www.getIt.com")).thenCallRealMethod();
        PowerMockito.when(contentSanitization.getItemInfo("http://www.test.com\">")).thenReturn(outFromGetItemInfo);

        ArrayList<String[]> arrayList = contentSanitization.getProductList("http://www.getIt.com");

        Assert.assertEquals("Apple", arrayList.get(0)[0]);
        Assert.assertEquals("Sweet and Crispy", arrayList.get(0)[3]);


    }

}
