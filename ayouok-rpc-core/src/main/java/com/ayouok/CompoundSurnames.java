package com.ayouok;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

/**
 * 复姓操作
 */
public class CompoundSurnames {

    /**
     * 复姓集合
     */
    private static final Set<String> SURNAMES = Collections.unmodifiableSet(new HashSet<String>() {{
        add("欧阳");
        add("太史");
        add("端木");
        add("上官");
        add("司马");
        add("东方");
        add("独孤");
        add("南宫");
        add("万俟");
        add("闻人");
        add("夏侯");
        add("诸葛");
        add("尉迟");
        add("公羊");
        add("赫连");
        add("澹台");
        add("皇甫");
        add("宗政");
        add("濮阳");
        add("公冶");
        add("太叔");
        add("申屠");
        add("公孙");
        add("慕容");
        add("仲孙");
        add("钟离");
        add("长孙");
        add("宇文");
        add("司徒");
        add("鲜于");
        add("司空");
        add("闾丘");
        add("子车");
        add("亓官");
        add("司寇");
        add("巫马");
        add("公西");
        add("颛孙");
        add("壤驷");
        add("公良");
        add("漆雕");
        add("乐正");
        add("宰父");
        add("谷梁");
        add("拓跋");
        add("夹谷");
        add("轩辕");
        add("令狐");
        add("段干");
        add("百里");
        add("呼延");
        add("东郭");
        add("南门");
        add("羊舌");
        add("微生");
        add("公户");
        add("公玉");
        add("公仪");
        add("梁丘");
        add("公仲");
        add("公上");
        add("公门");
        add("公山");
        add("公坚");
        add("左丘");
        add("公伯");
        add("西门");
        add("公祖");
        add("第五");
        add("公乘");
        add("贯丘");
        add("公皙");
        add("南荣");
        add("东里");
        add("东宫");
        add("仲长");
        add("子书");
        add("子桑");
        add("即墨");
        add("达奚");
        add("褚师");
    }});

    /**
     * 判断是否是复姓
     * @param surname 姓
     * @return 是否是复姓
     */
    public static boolean contains(String surname) {
        return SURNAMES.contains(surname);
    }
}