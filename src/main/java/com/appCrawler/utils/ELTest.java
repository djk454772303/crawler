package com.appCrawler.utils;
/**
 * 正则表达式的使用
 */
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.codecraft.webmagic.selector.Json;
import net.minidev.json.JSONObject;

public class ELTest {
	public static void main(String[] args){
		//String mes1 = "http://www.9game.cn/game/downs_515085_2.html?ch=JY_21";
		String mes1 = "http://www.9game.cn/game/downs_515085_2.html?ch=JY_21";
		String mes2 = "http://www.9game.cn/xcqzj/";
		
		//String regex= "[[^\\s]*html\\?ch=[^\\s]*][\\d$]";//"[\\S]+['html\\?ch=']+\\d$";
		//String regex= "(http://www\\.9game\\.cn/game)(.*)(html?ch=)(.*)";//"[\\S]+['html\\?ch=']+\\d$";
		String regex= "(?=(http://www\\.9game\\.cn).*)^(?!.*(html\\?ch)).*$";//"[\\S]+['html\\?ch=']+\\d$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mes2);
		//System.out.println(m.matches());
//		
//		String reg="(?=(不管).*)^(?!.*(不合谐)).*$";//用到了前瞻
//		System.out.println("不管信不信,反正现在很不合谐".matches(reg));//false不通过
//		System.out.println("不管信不信,反正现在非常合谐".matches(reg));//true通过
//		System.out.println("合谐在某国是普遍存在的".matches(reg));//false不通过
		
		String s = "showdow(82,'宝瓶市场','/soft/img/13812828611695645441.png');";
//		String[] ss= s.split("'|,");
		int i = 0;
//		while(i < ss.length){
//			System.out.println(s.split("'|,")[i]);
//			i++;
//		}
		
		s = "<p><label>适用系统：</label>2.1 以上</p>";
		String[] ss = s.split("<label>|</label>|</p>|<p>");
//		while(i < ss.length){
//			System.out.println(ss[i]);
//			i++;
//		}
		//System.out.println(s.split("/")[4]);
		
//		s = "<p class=at><span class=one><label>版本：</label>1.9.1</span><span><label>更新时间：</label>2014-06-16</span></p>";
//		ss = s.split("<p class=at><span class=one><label>版本：</label>|</span><span><label>");
//		while(i < ss.length){
//			System.out.println(ss[i]);
//			i++;
//		}
		
//		s="https://chart.googleapis.com/chart?cht=qr&chs=120x120&choe=UTF-8&chld=S|1&chl=http://download.eoemarket.com/app?id=30970%26client_id=146%26channel_id=401%26track=pc_qq_show_index_app30970_2";
//		ss = s.split("chld\\=S\\|1\\&chl=");
//		while(i < ss.length){
//			System.out.println(ss[i]);
//			i++;
//		}
		
//		s = "http://c11.eoemarket.com/app0/17/17896/icon/721873.png";
//		ss = s.split("http://c11.eoemarket.com/|.png");
//		while(i < ss.length){
//			System.out.println(i + ":" + ss[i].replace("icon", "apk"));
//			
//			i++;
//		}
		
//		s = "http://download.eoemarket.com/app?id=17896%26client_id=146%26channel_id=401%26track=pc_qq_show_index_app17896_2";
//		ss = s.split("channel_id=|%");
//		while(i < ss.length){
//			System.out.println(i + ":" + ss[i]);
//			
//			i++;
//		}
		
		s = "http://android.d.cn/game/43151.html";
		ss = s.split("http://android.d.cn|/|.html");
		
		s = "<a id='J_appdwnNewBtn' class='download-btn' href='' data-android-install-url='http://rj.m.taobao.com/wap/appmark/download_new.htm?flag=wapzs&amp;pn=com.jk37du.XiaoNiMei&amp;sid=166722301552105dbd61ce55fc14b495' data-android-native-url='taoappcenter://download?key_appId=5208607&amp;key_packageName=com.jk37du.XiaoNiMei'> <i class='icon icon-download-larg'></i>高速下载</a>";
		ss = s.split("<a id='J_appdwnNewBtn' class='download-btn' href='' data-android-install-url=|' data-android-native-url='");
		s="http://app.taobao.com/software/detail.htm?spm=a210u.1000832.0.0.qjk6zO&appId=5208607";
		s = s.split("&appId=").length > 0?s.split("&appId=")[1]:null;
		
		s = "<title>盛名列车时刻表手机版下载|盛名列车时刻表手机版 2014.12.15 中文免费版下载|七喜下载站 </title>";
		ss = s.split("下载\\||<title>");
		
		//s = "<a class="install" data-spm="d7006" href="" 520001',="" '淘宝',="" 'http:="" app.taobao.com="" ownload="" c_download_new.htm?apkid="10199568&amp;xfrom='," img01.taobaocdn.com="" mgextra="" b1nmyugpxxxxaeaxxxwu0bfxxx.png',="" '31214385',="" 'com.taobao.taobao');"="" data-spm-anchor-id="a210u.1000872.1000752.d7006"> 一键安装 </a>"
		while(i < ss.length){
		System.out.println(i + ":" + ss[i].replace("icon", "apk"));
		
		i++;
		}
		
		s = "https://play.google.com/store/apps/details?id=com.infraware.office.link";
		System.out.println(s.split("id=")[1]);
	}
}
