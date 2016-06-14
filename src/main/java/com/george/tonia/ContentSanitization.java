package com.george.tonia;

import java.io.IOException;
import java.util.ArrayList;

/*
* This class contains two methods, for the purpose of extracting required fields from html pages using the class 'HttpCapture'.
* */
public class ContentSanitization {

    /*This method is called from 'App' class. It takes the url as string. Upon gettin the html page as string using 'HttpCapture.getHttpContent,
     * it extracts products in the main page and corresponding link to the item page. */
    public  ArrayList<String[]> getProductList(String url){

        String responseAsString = null;
        String[] resultSet = null;

        try{
            responseAsString = HttpCapture.getHttpContent(url);

        }catch(IOException ex){
            ex.printStackTrace();
        }
        /* Looks for the particular term in the string (below) for spliting at the right point. It is hard-coded here but could also come from a property file.*/
        String subStringBeg = "<divclass=\"productInfo\">";

        String htmlBody = responseAsString.replace(" ", "").replaceAll("\\r\\n|\\r|\\n|\\t", "");

        if(htmlBody.contains(subStringBeg)){

            resultSet = htmlBody.split(subStringBeg);

            for(int i=1; i < resultSet.length; i++){
                resultSet[i] = resultSet[i].substring(resultSet[i].indexOf("http"), resultSet[i].indexOf("<imgsrc"));
            }
        }
        /* Once item name and the link are put in an array, it calls 'getItemInfo' for every item in the array and creates an Arraylist with results, which is returned.*/
        ArrayList<String[]> resultsFinal = new ArrayList<String[]>();
        for(int i = 1; i<resultSet.length; i++){
            resultsFinal.add(getItemInfo(resultSet[i]));
        }

        return resultsFinal;
    }
    /*This method gets required fields from item page as well as header info for the page size and returns values as an array. Some repetition may be visible compared to the above method, which can be isolated if needed*/
    public  String[] getItemInfo(String product){

        String [] out = new String[4];
        String itemPage = "";

        try{
            itemPage= HttpCapture.getHttpContent(product.split("\">")[0]).replace(" ", "").replaceAll("\\r\\n|\\r|\\n|\\t", "");
            out[1] = HttpCapture.getPageSize(product.split("\">")[0]);
        }catch(IOException ie){
            ie.printStackTrace();
        }
        out[0] = product.split("\">")[1];
        out[2] = itemPage.split("&pound;")[1].split("<abbr")[0];
        out[3] = itemPage.split("Description</h3><divclass=\"productText\"><p>")[1].split("</p>")[0];
        return out;

    }

}
