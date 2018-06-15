package com.tupperware.huishengyi.utils.logutils;


import com.tupperware.huishengyi.config.Constant;

import java.util.ArrayList;
import java.util.List;


public class ParseList {

    private List<Parser> parseList;
    private static ParseList singleton;

    private ParseList() {
        parseList = new ArrayList<>();
        addParserClass(Constant.DEFAULT_PARSE_CLASS);
    }

    public static ParseList getInstance() {
        if (singleton == null) {
            synchronized (ParseList.class) {
                if (singleton == null) {
                    singleton = new ParseList();
                }
            }
        }
        return singleton;
    }

    public void addParserClass(Class<? extends Parser>... classes) {
        // TODO: 16/3/12 判断解析类的继承关系
        for (Class<? extends Parser> cla : classes) {
            try {
                parseList.add(0, cla.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Parser> getParseList() {
        return parseList;
    }
}
