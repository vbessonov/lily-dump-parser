package com.gilbertteam.lily;

import com.gilbertteam.lily.io.LilyDumpParser;
import com.gilbertteam.lily.io.LilyDumpParserException;
import com.gilbertteam.lily.io.LilyRecordPrint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, LilyDumpParserException {
        LilyDumpParser parser = new LilyDumpParser(args[0]);

        while (parser.hasNext()) {
            final LilyRecord record = parser.next();

            LilyRecordPrint.print(record);
        }
    }
}
