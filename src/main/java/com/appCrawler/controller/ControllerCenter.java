package com.appCrawler.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import us.codecraft.webmagic.SpiderTrigger;
import us.codecraft.webmagic.selector.thread.CountableThreadPool;

/**
 * 接受外部请求的Controller
 * 
 * json数据格式(数组)：
 * {
		employees: [
			{firstName:John, lastName:Doe },
			{ firstName:Anna , lastName:Smith },
			{ firstName:Peter , lastName:Jones }
		]
	}
 * @author buildhappy
 *
 */

@Controller
@RequestMapping(value = "/")
public class ControllerCenter{
	
	private static CountableThreadPool threadPool;
	static{
		threadPool = new CountableThreadPool(3);
	}
	@RequestMapping(value="/" , method=GET)
	public String test(){
		System.out.println("in index  ");
		return "index";
	}

	//接收任务
	@RequestMapping(value="/task/{taskId}" ,consumes="application/json")
	public String receiveTask(@PathVariable String taskId , @RequestBody String body){
		System.out.println("taskId:" + taskId);
		System.out.println("body:" + body);
		String channels = "6";
		List<String> channelsList = getChannelsFromString(channels);
		String channelId;
		String keyword = null;
		Iterator<String> it = channelsList.iterator();
		while(it.hasNext()){
			channelId = it.next();
			//threadPool.execute(new SpiderTrigger(taskId , channelId , keyword));
		}
		return "adddone";
	}
	
	//接收任务
	@RequestMapping(value="/tasktest/{taskId}")
	public String receiveTask(@PathVariable String taskId){
		System.out.println("taskId:" + taskId);
		String channels = "6";
		List<String> channelsList = getChannelsFromString(channels);
		String channelId;
		String keyword = null;
		Iterator<String> it = channelsList.iterator();
		while(it.hasNext()){
			channelId = it.next();
			threadPool.execute(new SpiderTrigger(taskId , channelId , keyword));
		}
		return "adddone";
	}
	
	//跳转到添加页面
	@RequestMapping(value="/adduser")
	public String addUser(){
		return "jsonAddUser";
	}
	
	//http://localhost:9090/crawler/jobstatus/<status>
	@RequestMapping(value="/jobstatus/{status}")
	public String receiveTaskStatus(@PathVariable String status){
		System.out.println("status  " + status);
		return "jsonAddUser";
	}
	
	/**
	 * 根据url产生Http请求,并发送请求
	 * @param url
	 * @return
	 */
	private void httpRequestSender(String url){
		URL realUrl;
		URLConnection connection;
		HttpURLConnection httpConnection;
		try {
			realUrl = new URL(url);
			connection = realUrl.openConnection();
			httpConnection = (HttpURLConnection)connection;
			//System.out.println("response:" + httpConnection.getResponseCode());
			//return httpConnection.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * convert the string array to list
	 * @param channels
	 * @return
	 */
	public List<String> getChannelsFromString(String channels){
		return Arrays.asList(channels.split(","));
	}
	
	public static void main(String[] args){
		String s = "fafaf,2324";
		ControllerCenter c = new ControllerCenter();
		List<String> l = c.getChannelsFromString(s);
		Iterator it = l.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
}
