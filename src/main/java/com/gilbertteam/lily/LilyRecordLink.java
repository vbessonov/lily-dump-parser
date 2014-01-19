package com.gilbertteam.lily;

import java.util.ArrayList;
import java.util.List;

public class LilyRecordLink {
    private final String type;
    private final List<String> items = new ArrayList<>();

    public LilyRecordLink(String type) {
        if (type == null ||
            type.equals("")) {
            throw new IllegalArgumentException("type");
        }

        this.type = type;
    }

    public String getType() {
        return type;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("LilyRecordLink{");
        stringBuilder.append("type='").append(type).append('\'');
        stringBuilder.append(", items=").append(items.size());
        stringBuilder.append('}');

        return stringBuilder.toString();
    }
}

