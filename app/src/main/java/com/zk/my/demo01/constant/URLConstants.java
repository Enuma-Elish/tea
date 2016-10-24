package com.zk.my.demo01.constant;

/**
 * Created by bodhixu on 2016/10/11.
 */
public class URLConstants {
    //茶百科

    //广告地址
    public static final String AD_URL = "http://sns.maimaicha.com/api? apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";

    //头条
    public static final String TT_URL = "http://sns.maimaicha.com/api? apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&page=%d&rows=15";

    //百科/资讯/数据/经营  列表数据
    public static final String OTHRER_URL = "http://sns.maimaicha.com/api? apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&page=%d&rows=15&type=%d";//根据API文档: d% page是变量可以改变;rows返回的数据行数,也可以改变但是不建议;d% type新闻的分类

    //详情画面
    public static final String DETAIL_URL = "http://sns.maimaicha.com/api? apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=%s";

    //http://capi.douyucdn.cn/api/v1/getbigDataHotRoom?limit=20&offset=0&time=1470801060
    public static final String BASE_URL="http://capi.douyucdn.cn/";
    public static class Path{
        public static final String path="api/v1/getbigDataHotRoom";
    }
    public static class Params{
        public static final String LIMIT="limit";
        public static final String OFFSET="offset";
        public static final String TIME="time";
    }
    public static class DefaultValue{
        public static final String LIMIT = "20";
        public static final String OFFSET = "0";
        public static final String TIME = "1470800460";
    }
}
