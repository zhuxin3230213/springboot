package cn.gmuni.utils;

import java.util.Map;

public class PageUtils {

    private int page;

    private  int size;

    private PageUtils(Map<String, ?> params){
        int cPage = 0;
        int size = 10;
        try {
            cPage = Integer.parseInt(params.get("page").toString());
            size = Integer.parseInt(params.get("pageSize").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.page = cPage;
        this.size = size;
    }

    public static PageUtils getPage(Map<String,?> params){
        return new PageUtils(params);
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
