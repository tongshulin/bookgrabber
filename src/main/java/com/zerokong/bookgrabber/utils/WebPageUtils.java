package com.zerokong.bookgrabber.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component("webPageUtils")
public class WebPageUtils {
    private Logger logger = LoggerFactory.getLogger(WebPageUtils.class);

    public String getContext(String webUrl){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); // 创建Get请求
        HttpGet httpGet = new HttpGet(webUrl); // 响应模型
        httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=GBK"));
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity,"utf-8");
            }

        } catch (ClientProtocolException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return "";
    }


    public static void main(String[] args) {
        WebPageUtils utils = new WebPageUtils();
        String wholeContext =  utils.getContext("https://www.siluke.tw/ny12755/");
        Document doc = Jsoup.parse(wholeContext);
        Elements elements = doc.select("div[id=list]").select("dd").select("a");
        int i =0;
        for(Element e:elements){
            System.out.println("======================" + i++);
            System.out.println("href = " + e.attr("href") + " and title is " + e.text());

            System.out.println("======================");
        }


    }
}
