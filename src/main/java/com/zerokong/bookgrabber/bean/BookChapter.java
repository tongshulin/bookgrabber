package com.zerokong.bookgrabber.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="t_book_chapter")
public class BookChapter {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @GenericGenerator(name = "persistenceGenerator", strategy = "increment")
    @Column(name="id")
    private Integer id;

    @Column(name="book_id")
    private Integer bookId;

    @Column(name="chapter_name")
    private String chapterName;

    @Column(name="chapter_url")
    private String chapterUrl;


    public BookChapter(){

    }

    public BookChapter(Integer bookId,String chapterName,String chapterUrl){
        this.bookId = bookId;
        this.chapterName = chapterName;
        this.chapterUrl = chapterUrl;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }
}
