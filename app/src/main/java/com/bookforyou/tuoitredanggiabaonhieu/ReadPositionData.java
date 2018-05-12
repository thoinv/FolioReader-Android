package com.bookforyou.tuoitredanggiabaonhieu;

import com.folioreader.model.ReadPosition;

public class ReadPositionData implements ReadPosition {
    private String bookId;
    private String chapterId;
    private String chapterHref;
    private int chapterIndex = -1;
    private boolean usingId;
    private String value;


    @Override
    public String getBookId() {
        return bookId;
    }

    @Override
    public String getChapterId() {
        return chapterId;
    }

    @Override
    public String getChapterHref() {
        return chapterHref;
    }

    @Override
    public int getChapterIndex() {
        return chapterIndex;
    }

    @Override
    public boolean isUsingId() {
        return usingId;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toJson() {
        return null;
    }
}
