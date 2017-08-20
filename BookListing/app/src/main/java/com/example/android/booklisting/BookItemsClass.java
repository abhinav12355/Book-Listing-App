package com.example.android.booklisting;

public class BookItemsClass {
    private String mImageId;
    private String mTitle;
    private String mAuthor;
    private String mLink;

    public BookItemsClass(String image, String title, String author, String link) {
        mImageId = image;
        mTitle = title;
        mAuthor = author;
        mLink = link;
    }


    public String getimageid() {
        return mImageId;
    }

    public String gettitle() {
        return mTitle;
    }

    public String getauthor() {
        return mAuthor;
    }

    public String getlink() {
        return mLink;
    }
}
