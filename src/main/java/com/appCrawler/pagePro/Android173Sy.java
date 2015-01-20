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
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(300);
	@Override
	public Apk process(Page page) {
		//index page
		if(page.getUrl().regex("http://android.173sy.com/games/search.php\\?*").match()){
			List<String> urls = page.getHtml().links("//div[@class='searchlist']").regex("http://android\\.173sy\\.com/.*").all();
			
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(urls);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());//添加app的具体介绍页面
			}
			
			////////////////////////////////////////////////////////
			//添加下一页url
			//String url = page.getHtml().links("//div[@class='pagel tac r mt_25']").regex("http://android\\.173sy\\.com/games/search\\.php?key=qq&p=*").all();
			//page.addTargetRequests();
		}
		
		//the app detail page
		if(page.getUrl().regex("http://android\\.173sy\\.com/.*").match()){
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
			System.out.println(appDownloadUrl);
			appName = page.getHtml().xpath("//p[@class='dl_ititle']/text()").toString();
			
			if(page.getResultItems().get("downloadUrl") == null || page.getResultItems().get("name") == null
					|| page.getResultItems().get("version") == null){
				page.setSkip(true);
			}
			
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
