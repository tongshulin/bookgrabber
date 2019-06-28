package com.zerokong.bookgrabber.schedual;


import com.alibaba.druid.util.StringUtils;
import com.zerokong.bookgrabber.bean.BookChapter;
import com.zerokong.bookgrabber.dao.BookChapterDAO;
import com.zerokong.bookgrabber.utils.WebPageUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

public class GrabberSchedual {

    @Resource(name="webPageUtils")
    private WebPageUtils webPageUtils;

    @Resource(name="bookChapterDAO")
    private BookChapterDAO bookChapterDAO;

    private Logger logger = LoggerFactory.getLogger(GrabberSchedual.class);

    private String webUrl = "https://www.siluke.tw/ny12755/";
    /**
     * 计算维持保证金项
     */
    @Scheduled(cron = "5 * * ? * MON-FRI")
    public void grabber(){
        boolean isNewBook = false;
        boolean isFound = false;
        String lastedUrl = null;

        try {

            String wholeContext =  webPageUtils.getContext(webUrl);
            BookChapter lastedBookChapter = bookChapterDAO.getLastedBookChapter(1);


            if(lastedBookChapter!=null){
                lastedUrl = lastedBookChapter.getChapterUrl();
            }else{
                isNewBook = true;       //这是本新书
            }

            Document doc = Jsoup.parse(wholeContext);
            Elements elements = doc.select("div[id=list]").select("dd").select("a");
            for(Element e:elements){
                if(!isNewBook){
                    if(!isFound){
                        if(StringUtils.equals(e.attr("href"),e.attr("href"))){
                            isFound = true;
                        }
                        continue;
                    }
                }
                logger.info("准备写入文章章节: title is " +e.attr("href") + " and url is " + e.text() );
                BookChapter bookChapter = new BookChapter(1,e.text(),e.attr("href"));
                bookChapterDAO.saveOrUpdate(bookChapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
