package com.myyll.shop.myimageloader.utils.http;

import com.myyll.shop.myimageloader.utils.JsonUtils;
import com.myyll.shop.myimageloader.model.NewsModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by luoyan on 16/8/27.
 */
public class HttpUtils {

    public static NewsModel getNewsJsonData(String url) {
        NewsModel newsModel = null;
        try {
            String jsonString = readStream(new URL(url).openStream());
            newsModel = JsonUtils.jsonToEntity(jsonString,NewsModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsModel;
    }

    public static String readStream(InputStream is) {
        InputStreamReader isr = null;
        StringBuilder result = new StringBuilder("");
        try {
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
