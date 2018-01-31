package ru.shaldnikita.starter.backend.model.dict;

/**
 * @author n.shaldenkov on 23/01/2018
 */


public class Group {
    public final static String GROUP_1 = "group1";

    private Group() {
    }

    public static String[] allValues() {
        return new String[]{GROUP_1};
    }
}
