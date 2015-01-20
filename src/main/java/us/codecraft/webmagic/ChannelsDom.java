package us.codecraft.webmagic;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import us.codecraft.webmagic.processor.PageProcessor;

/**
 * package the chanel info into a class
 * using the class to load channel info from siteInfo.xml
 * usage:
 * 	provide the channel id,return the searchUrl and PageProcessor of the channel
 * @author buildhappy
 *
 */
//auto wired
@Component
public class ChannelsDom {
	private String channelId;
	private String searchUrl;
	private PageProcessor pagePro;
	private String postParam = null;

	public ChannelsDom(String channelId){
		this.channelId = channelId;
		load();
		
	}
	public void load(){
		try {
			// 将待抓取的网站信息存在xml文件中
			DocumentBuilderFactory dbf = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();

			InputStream in = ChannelsDom.class.getClassLoader()
					.getResourceAsStream("sitesInfo.xml");

			// 将xml文件转化成dom树
			Document doc = builder.parse(in);
			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			// 利用xpath获取节点信息
			XPathExpression exp = xPath.compile("//site[@id='" + channelId
					+ "']/searchUrl");
			Node node = (Node) exp.evaluate(doc, XPathConstants.NODE);
			String s = node.getTextContent();
			searchUrl = s.replace("!%!%!%", "&");
			
			exp = xPath.compile("//site[@id='" + channelId + "']/pageProcessor");
			node = (Node) exp.evaluate(doc, XPathConstants.NODE);
			String pageProName = node.getTextContent();
			pagePro = dynamicCreateObjByName(pageProName);

			exp = xPath.compile("//site[@id='" + channelId + "']/postPara");
			node = (Node) exp.evaluate(doc, XPathConstants.NODE);
			if (node != null) {
				// 处理post请求
				postParam = node.getTextContent();
//				Spider.create(pagePro)
//						.setDownloader(new CurlDownloader(postParam, keyword))
//						.addUrl(seedUrl).thread(1).run();
			} else {
//				Spider.create(pagePro)
//						.setDownloader(new MyHttpClientDownloader())
//						.addUrl(seedUrl).thread(1).run();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过类名创建类的对象，
	 * 
	 * @param name:为类的完整名，包括包名
	 * @return
	 * @throws Exception
	 */
	private PageProcessor dynamicCreateObjByName(String name)
			throws Exception {
		Class c;
		PageProcessor o;
		c = Class.forName(name);
		o = (PageProcessor) (c.getClassLoader().loadClass(name)).newInstance();
		return o;

	}
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getSearchUrl() {
		return searchUrl;
	}
	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}
	public PageProcessor getPagePro() {
		return pagePro;
	}
	public void setPagePro(PageProcessor pagePro) {
		this.pagePro = pagePro;
	}
	public String getPostParam() {
		return postParam;
	}
	public void setPostParam(String postParam) {
		this.postParam = postParam;
	}
	
	
	public static void main(String[] args){
		ChannelsDom dom = new ChannelsDom("4");
		dom.load();
		System.out.println(dom.getChannelId());
		System.out.println(dom.getSearchUrl());
		String ss = "http://www.ggg.cn/misc/search.cgi?q=*#*#*#!%!%!%type=all";
		String s = ss.replace("!%!%!%", "&");
		System.out.println(s);
		
	}

}
