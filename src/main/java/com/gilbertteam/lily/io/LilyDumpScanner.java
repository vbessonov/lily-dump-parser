package com.gilbertteam.lily.io;

import java.io.IOException;

import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class LilyDumpScanner implements AutoCloseable {

    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private final Scanner scanner;

    private int currentLineNumber = 0;
    private final Deque<String> currentLines = new ArrayDeque<>();

    public LilyDumpScanner(String dumpFilePath) 
        throws IOException {
        if (dumpFilePath == null) {
            throw new IllegalArgumentException("dumpFilePath");
        }

        scanner = new Scanner(Paths.get(dumpFilePath), ENCODING.name());
    }

    public int getCurrentLineNumber() {
        return currentLineNumber;
    }

    public boolean hasNextLine() {
        return
                currentLines.size() > 0 ||
                scanner.hasNextLine();
    }

    public String readLine() {
        String result = null;

        if (currentLines.size() == 0) {
            if (scanner.hasNextLine()) {
                currentLineNumber++;
                result = scanner.nextLine();
            }
        } else {
            result = currentLines.pop();
        }

        return result;
    }

    public String peekLine() {
        String result = null;

        if (currentLines.size() == 0){
            if (scanner.hasNextLine()) {
                currentLineNumber++;
                result = scanner.nextLine();
                currentLines.push(result);
            }
        } else {
            result = currentLines.peek();
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        scanner.close();
    }
}

