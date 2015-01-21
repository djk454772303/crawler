package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 手游世界http://android.173sy.com/
 * 可以通过伪造下载链接来构造下载链接
 * http://android.173sy.com/download/downloadapk.php?id=13425&s=1
 * 将id后的参数修改成相应的apk的id就好
 * @author Administrator
 *
 */
public class Android173Sy implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(300);
	@Override
	public Apk process(Page page) {
		//index page
		if(page.getUrl().regex("http://android.173sy.com/games/search.php\\?*").match()){
			//app的具体介绍页面
			List<String> url1 = page.getHtml().links("//div[@class='searchlist']").regex("http://android\\.173sy\\.com/.*").all();
			
			//添加下一页url(翻页)
			List<String> url2 = page.getHtml().links("//div[@class='pagel tac r mt_25']").regex("http://android\\.173sy\\.com/games/search.php\\?key=.*").all();
			url1.addAll(url2);
			
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(url1);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
			}
		}
		
		//the app detail page
		if(page.getUrl().regex("http://android\\.173sy\\.com/games/detail*").match()){
			Apk apk = null;
			String appName = null;
			String appDetailUrl = null;
			String appDownloadUrl = null;
			String osPlatform = null ;
			String appVersion = null;
			String appSize = null;
			String appUpdateDate = null;
			String appType = null;
			String cookie = null;
			
			appDetailUrl = page.getUrl().toString();
			
			String rawId = page.getHtml().xpath("//p[@class='dbtn_download']/@onclick").get();
			String id = null;
			String rawVersion = page.getHtml().xpath("//p[@class='cf51e7e lh20 mt_5']/text()").toString();
			if((rawId != null) && (rawVersion != null)){
				id = rawId.split("\\(")[1].split(",")[0];
				appDownloadUrl = "http://android.173sy.com/download/downloadapk.php?id="+ id + "&s=1";
				appVersion = rawVersion.split("： ")[1].trim();
			}
			appName = page.getHtml().xpath("//p[@class='dl_ititle']/text()").toString();
			
			if(appName != null && appDownloadUrl != null){
				apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize);
			}
			
			return apk;
		}
		return null;

	}

	@Override
	public Site getSite() {
		return site;
	}

}
