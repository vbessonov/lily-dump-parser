package com.gilbertteam.lily;

import java.util.ArrayList;
import java.util.List;

public class LilyRecord {
    private final String id;
    private final List<LilyRecordLink> links = new ArrayList<>();
    private int version;

    public LilyRecord(String id) {
        if (id == null ||
            id.equals("")) {
            throw new IllegalArgumentException("id");
        }

        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<LilyRecordLink> getLinks() {
        return links;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LilyRecord that = (LilyRecord) o;

        if (version != that.version) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + version;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("LilyRecord{");
        stringBuilder.append("id='").append(id).append('\'');
        stringBuilder.append(", version=").append(version);
        stringBuilder.append('}');

        return stringBuilder.toString();
    }
}

