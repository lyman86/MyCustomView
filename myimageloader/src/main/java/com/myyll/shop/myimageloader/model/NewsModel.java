package com.myyll.shop.myimageloader.model;

import java.util.List;

/**
 * Created by luoyan on 16/8/27.
 */
public class NewsModel {
    public int status;
    public List<DataModel>data;
    public String msg;

    public class DataModel{
        public int id;
        public String name;
        public String picSmall;
        public String picBig;
        public String description;
        public int learner;
    }

}
