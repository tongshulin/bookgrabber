package com.zerokong.bookgrabber.dao;


import com.zerokong.bookgrabber.base.BaseDAO;
import com.zerokong.bookgrabber.bean.BookChapter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "bookChapterDAO")
public class BookChapterDAO extends BaseDAO<BookChapter> {

    String HQL_FIND_LASTED_URL = "from BookChapter where id = (select max(id) from BookChapter where bookId = ? )";

    public BookChapter getLastedBookChapter(Integer bookId) throws Exception {
        List<BookChapter> tempList = this.list(HQL_FIND_LASTED_URL,bookId);
        if(tempList!=null && tempList.size()>0){
            return tempList.get(0);
        }
        return null;
    }
}
