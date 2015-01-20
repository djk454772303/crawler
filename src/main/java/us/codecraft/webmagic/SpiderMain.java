package us.codecraft.webmagic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import com.appCrawler.pagePro.Android173Sy;

public class SpiderMain {
	public static void main(String[] args) throws IOException, Exception{
		String[] seedUrl = null;//起始url
		String pageProName = null;
		String downloadLocation = null;//apk的保存路径
		PageProcessor pagePro = null;
		Pipeline consolePipe = null;
		String postParam = null;
		String keyword = null;
		int apkId;
		apkId = 21;
		//consolePipe = new ApkFilePipe();
		
		//将待抓取的网站信息存在xml文件中
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();

		InputStream in = SpiderMain.class.getClassLoader().getResourceAsStream("sitesInfo.xml");

		//将xml文件转化成dom树
		Document doc = builder.parse(in);
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();
		//利用xpath获取节点信息
		XPathExpression exp = xPath.compile("//site[@id='"+ apkId + "']/seedUrl");
		Node node = (Node) exp.evaluate(doc , XPathConstants.NODE);
		seedUrl = node.getTextContent().split(",");
		
		exp = xPath.compile("//site[@id='"+ apkId + "']/pageProcessor");
		node = (Node) exp.evaluate(doc , XPathConstants.NODE);
		pageProName = node.getTextContent();
		
		exp = xPath.compile("//site[@id='"+ apkId + "']/downloadLocation");
		node = (Node) exp.evaluate(doc , XPathConstants.NODE);
		downloadLocation = node.getTextContent();
		
		exp = xPath.compile("//site[@id='"+ apkId + "']/postPara");
		node = (Node) exp.evaluate(doc , XPathConstants.NODE);
		if(node != null){
			//处理post请求
			postParam = node.getTextContent();
//			SpiderOlder.create(pagePro)
//			.addPipeline(consolePipe).downloader(new CurlDownloader(postParam , keyword))
//			.addUrl(seedUrl).thread(1).run();
		}else{
//			SpiderOlder.create(pagePro)
//			.addPipeline(consolePipe).downloader(new MyHttpClientDownloader())
//			.addUrl(seedUrl).thread(1).run();
		}
		
		
		
		//String url = "http://app.suning.com/android/app/page?pack=com.babeltime.fknsango_suningyigou";
		String url  = "http://www.97sky.cn/list/359_1.html";
		url = "http://android.173sy.com/games/search.php?key=%E6%8D%95%E9%B1%BC%E8%BE%BE%E4%BA%BA";
		LinkedBlockingQueue<Apk> infos = new LinkedBlockingQueue<Apk>();
		//pagePro = dynamicCreateObjByName(pageProName);
		pagePro = new Android173Sy();
		Spider.create(pagePro , infos)
		.addUrl(url).thread(1).run();
		
		Iterator<Apk> it = infos.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		System.out.println("all apk :" + infos.size());
		
	}
	
	/**
	 * 通过类名创建类的对象，
	 * @param name:为类的完整名，包括包名
	 * @return
	 * @throws Exception
	 */
	public static PageProcessor dynamicCreateObjByName(String name) throws Exception{
		Class c;
		PageProcessor o;
		c = Class.forName(name);
		o = (PageProcessor) (c.getClassLoader().loadClass(name)).newInstance();
		return o;
		
	}
}

