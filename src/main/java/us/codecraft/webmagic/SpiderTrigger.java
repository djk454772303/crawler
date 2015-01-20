package us.codecraft.webmagic;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.CurlDownloader;
import us.codecraft.webmagic.downloader.MyHttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬虫的启动接口，controller模块接受任务后，调用SpiderTrigger启动爬虫
 * @author Administrator
 *
 */
public class SpiderTrigger implements Runnable {
	private String channelId;
	private String taskId;
	private String keyword;

	public SpiderTrigger(String taskId , String channelId , String keyword){
		this.taskId = taskId;
		this.channelId = channelId;
		this.keyword = keyword;
	}
	
	public void run() {
		ChannelsDom channelDom = new ChannelsDom(channelId);
		String searchUrl;
		PageProcessor pagePro;
		String postParam;
		pagePro = channelDom.getPagePro();
		System.out.println(pagePro);
		String s = channelDom.getSearchUrl();
		searchUrl = s.replace("*#*#*#", keyword);
		//searchUrl = searchUrl + keyword;
		postParam = channelDom.getPostParam();
		Spider spider = new Spider(pagePro ,taskId ,channelId);
		
		if (postParam != null) {
			spider.setDownloader(new CurlDownloader(postParam, keyword))
					.addUrl(searchUrl).thread(1).run();
		} else {
			spider.setDownloader(new MyHttpClientDownloader())
					.addUrl(searchUrl).thread(1).run();
		}
	}
	
	public static void main(String[] args){
		SpiderTrigger spiderTrigger = new SpiderTrigger("task1" , "1" , "保卫萝卜");
		Thread thread = new Thread(spiderTrigger);
		thread.run();
	}
}
