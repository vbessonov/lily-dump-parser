package com.gilbertteam.lily.io;

import com.gilbertteam.lily.LilyRecord;
import com.gilbertteam.lily.LilyRecordLink;

/**
 * Created with IntelliJ IDEA.
 * User: vyacheslav
 * Date: 22/12/2013
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
public class LilyRecordPrint {
    public static void print(LilyRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("record");
        }

        System.out.println(String.format("ID = %s", record.getId()));
        System.out.println(String.format("Version = %d", record.getVersion()));

        for (LilyRecordLink recordLink : record.getLinks()) {
            System.out.println(String.format("\t%s = ", recordLink.getType()));
            int i = 0;

            for (String recordLinkItem : recordLink.getItems()) {
                System.out.println(String.format("\t\t[%d] %s", i, recordLinkItem));
                i++;
            }
        }
    }
}
