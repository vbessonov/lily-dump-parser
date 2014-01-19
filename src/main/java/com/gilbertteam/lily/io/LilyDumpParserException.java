package com.gilbertteam.lily.io;

public class LilyDumpParserException
    extends Exception {

    public LilyDumpParserException(String illegalString) {
        super(String.format("Found syntax error in string: %s", illegalString));
    }

    public LilyDumpParserException(String illegalString, int lineNumber) {
        super(String.format("Found syntax error in %dth string: %s", lineNumber, illegalString));
    }
}

