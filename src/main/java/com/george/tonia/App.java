package com.george.tonia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.*;

/*This class contains the main method and orchestrates the program.
*The app is tightly focused on the specific type of list-page provided for the test.
*At the occurrence of any error it prompts for a valid link.
*'runIt' method calls 'ContentSanitization' class for an ArrayList of items and then converts them to Json format.*/
public class App {

        public static void main (String[] args) {
            boolean cont = true;
            while(cont){
                try {
                    App.runIt();
                } catch (Exception e) {
                    System.out.println("Address not valid!! Please enter a valid link or \"shutdown\" to exit");
                    continue;
                }
            }
        }
        public static void runIt()throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please enter the link or \"shutdown\" to Exit");
            String input = br.readLine();
            if(input.equalsIgnoreCase("shutdown")){
                System.exit(0);
            }else{
                ContentSanitization cs = new ContentSanitization();
                ArrayList<String[]> products =cs.getProductList(input);
                System.out.println(products.size());

                JsonArray results = new JsonArray();
                JsonObject jObj = new JsonObject();
                double totalValue = 0;

                for(String[] sa: products){
                    JsonObject innerObj = new JsonObject();
                    innerObj.addProperty("title", sa[0]);
                    innerObj.addProperty("size", sa[1]+"kb");
                    innerObj.addProperty("unit_price", sa[2]);
                    innerObj.addProperty("description", sa[3]);
                    totalValue = totalValue+Double.parseDouble(sa[2]);
                    results.add(innerObj);
                }
                jObj.add("results", results);
                jObj.addProperty("total", (double)((int)Math.round(totalValue)*100)/100);
                String jsonPretty = new GsonBuilder().setPrettyPrinting().create().toJson(jObj);
                System.out.println(jsonPretty);
            }
        }
    }

