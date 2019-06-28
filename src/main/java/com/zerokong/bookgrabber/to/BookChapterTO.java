package com.zerokong.bookgrabber.to;

public class BookChapterTO {

    private String chapterName;
    private String chapterUrl;
    private Integer bookId;

    public BookChapterTO(){

    }

    public BookChapterTO(Integer bookId,String chapterName,String chapterUrl){
        this.bookId = bookId;
        this.chapterName = chapterName;
        this.chapterUrl = chapterUrl;
    }

}
