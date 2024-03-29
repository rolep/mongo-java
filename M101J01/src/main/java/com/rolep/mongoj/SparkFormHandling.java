package com.rolep.mongoj;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Created by rolep on 19/03/16.
 */
public class SparkFormHandling {

    public static void main(String[] args) {
        //Configure Freemarker
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(SparkFormHandling.class, "/");

        //Configure routes
        Spark.get("/", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    Map<String, Object> fruitsMap = new HashMap<String, Object>();
                    fruitsMap.put("fruits", Arrays.asList("apple", "orange", "banana", "peach"));

                    Template fruitPickerTemplate = configuration.getTemplate("fruitPicker.ftl");
                    StringWriter writer = new StringWriter();
                    fruitPickerTemplate.process(fruitsMap, writer);
                    return writer;
                }
                catch (Exception e) {
                    halt(500);
                    return null;
                }
            }
        });

        Spark.post("/favorite_fruit", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String fruit = request.queryParams("fruit");
                if (fruit == null) {
                    return "Why don't you pick one?";
                }
                else {
                    return "Your favorite fruit is " + fruit;
                }
            }
        });
    }
}
