package com.gilbertteam.lily.io;

import com.gilbertteam.lily.LilyRecord;
import com.gilbertteam.lily.LilyRecordLink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LilyDumpParser implements AutoCloseable {

    private static final Pattern recordIdPattern = Pattern.compile("^ID = (.+)$");
    private static final Pattern recordVersionPattern = Pattern.compile("^Version = (\\d+)$");
    private static final Pattern recordLinkTypePattern = Pattern.compile("\\s*([^\\s]+)\\s+=\\s*");
    private static final Pattern recordLinkItemPattern = Pattern.compile("\\s*\\[\\d+\\]\\s*(.+)");
    private final LilyDumpScanner scanner;
    private LilyRecord currentRecord;

    public LilyDumpParser(String dumpFilePath) throws IOException {
        scanner = new LilyDumpScanner(dumpFilePath);
    }

    private boolean isStringEmpty(String string) {
        return
            string == null ||
            string.equals("");
    }

    private String match(Pattern pattern) throws LilyDumpParserException {
        final String currentLine = scanner.readLine();

        return match(pattern, currentLine);
    }

    private boolean isMatch(Pattern pattern, String string) {
        final Matcher matcher = pattern.matcher(string);

        return matcher.matches();
    }
                                                              
    private String match(Pattern pattern, String string) throws LilyDumpParserException {
        if (isStringEmpty(string)) {
            throwSyntaxException(string);
        }
                                                              
        final Matcher matcher = pattern.matcher(string);
                                                              
        if (!matcher.matches()) {
            throwSyntaxException(string);
        }
        
        return matcher.group(1);
    }

    private String parseRecordId() throws LilyDumpParserException {
        return match(recordIdPattern);
    } 
                                                                                   
    private int parseRecordVersion() throws LilyDumpParserException {
        int result = -1;
                                                                                   
        try {
            result = Integer.parseInt(match(recordVersionPattern));
        } catch (NumberFormatException exception) {
            // TODO: throwSyntaxException();
        } 
                                                                                   
        return result;
    }
                                                                                   
    private LilyRecordLink parseRecordLink() throws LilyDumpParserException {
        final String recordLinkType = match(recordLinkTypePattern);
        final List<String> recordLinkItems = new ArrayList<>();

        while (true) {
            final String line = scanner.peekLine();

            if (isStringEmpty(line)) {
                break;
            }

            final String recordLinkItem = tryMatch(recordLinkItemPattern, line);

            if (isStringEmpty(recordLinkItem)) {
                break;
            }

            recordLinkItems.add(recordLinkItem);

            scanner.readLine();
        }

        final LilyRecordLink recordLink = new LilyRecordLink(recordLinkType);
        recordLink.getItems().addAll(recordLinkItems);

        return recordLink;
    }

    private List<LilyRecordLink> parseRecordLinks() throws LilyDumpParserException {
        final List<LilyRecordLink> recordLinks = new ArrayList<>();

        recordLinks.add(parseRecordLink());

        while (true) {
            String line = scanner.peekLine();
            
            if (isStringEmpty(line)) {
                break;
            }
            if (!isMatch(recordLinkTypePattern, line)) {
                break;
            }

            recordLinks.add(parseRecordLink());
        }

        return recordLinks;
    }

    private void throwSyntaxException(String line) throws LilyDumpParserException {
        throw new LilyDumpParserException(line, scanner.getCurrentLineNumber());
    }

    private String tryMatch(Pattern pattern, String string) {
        if (isStringEmpty(string)) {
            return null;
        }

        final Matcher matcher = pattern.matcher(string);

        if (!matcher.matches()) {
            return null;
        }

        return matcher.group(1);
    }

    public boolean hasNext()throws LilyDumpParserException {
        if (currentRecord != null) {
            return true;
        }
        if (!scanner.hasNextLine()) {
            return false;
        }

        final String recordId = parseRecordId(); 
        final int recordVersion = parseRecordVersion();

        for (int i = 0; i < 4; i++) {
            scanner.readLine();
        }

        final List<LilyRecordLink> recordLinks = parseRecordLinks();

        currentRecord = new LilyRecord(recordId);
        currentRecord.setVersion(recordVersion);
        currentRecord.getLinks().addAll(recordLinks);
                                                                     
        return true;
    }

    public LilyRecord next() throws LilyDumpParserException {
        if (!hasNext()) {
            return null;
        }

        final LilyRecord result = currentRecord;

        currentRecord = null;

        return result;
    }

    @Override
    public void close() throws Exception {
        scanner.close();
    }
}

