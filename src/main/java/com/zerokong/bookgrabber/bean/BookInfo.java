package com.zerokong.bookgrabber.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="t_book_info",schema = "book")
public class BookInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @GenericGenerator(name = "persistenceGenerator", strategy = "increment")
    @Column(name="id")
    private Integer id;

    @Column(name="book_name",length=50,nullable = true)
    private String bookName;

    @Column(name="book_url",nullable = true)
    private String bookUrl;

    @Column(name="book_web_site",nullable = true)
    private String bookWebSite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getBookWebSite() {
        return bookWebSite;
    }

    public void setBookWebSite(String bookWebSite) {
        this.bookWebSite = bookWebSite;
    }
}
