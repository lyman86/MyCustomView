package com.myyll.shop.myimageloader.utils;

import com.google.gson.Gson;

/**
 * Created by luoyan on 16/8/27.
 */
public class JsonUtils {

        /**
         * 对象转json
         *
         * @param entity
         * @return String
         * @author Desmond 2014-10-15 上午10:40:06
         */
        public static String entityToJson(Object entity) {
            String json = null;
            try {
                json = new Gson().toJson(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        /**
         * json转单个对象
         *
         * @param json
         * @param clazz
         * @param <T>
         * @param <T>
         * @return Object
         * @author Desmond 2014-10-15 上午10:40:16
         */
        public static <T> T jsonToEntity(String json, Class<T> clazz) {
            T t = null;
            try {
                t = new Gson().fromJson(json, clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return t;
        }
}