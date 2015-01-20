package us.codecraft.webmagic.downloader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.selector.PlainText;

/**
 * 利用Linux的curl工具，下载页面，主要是针对宝瓶网的search请求是post方法提交的。
 * @author Administrator
 *
 */
public class CurlDownloader implements Downloader{

	/**
	 * postPara：post提交的参数名称
	 * keyword：post提交的参数名称对应的值
	 */
	private String postPara;
	private String keyword;
	
	public CurlDownloader(String postPara , String keyword){
		this.postPara = postPara;
		this.keyword = keyword;
	}
	@Override
	public Page download(Request request, Task task) {
		Page page = new Page();
		String url = request.getUrl();
		String encoding = "utf-8";
		String[] commands = generateCurlConmmand(url);//curl命令
		StringBuffer stringBuf = new StringBuffer();
		String context = null;
		try {
			Process process = Runtime.getRuntime().exec(commands);
			InputStream curlIn = new DataInputStream(process.getInputStream());
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(curlIn , encoding));
			String line = null;
			while((line = bufReader.readLine()) != null){
				stringBuf.append(line);
			}
			context = new String(stringBuf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		page.setRawText(context);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        //page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
		return page;
	}

	@Override
	public void setThread(int threadNum) {
		
	}
	
	/**
	 * 构造curl命令
	 * @param url:搜索页面对于的url
	 * @return
	 */
	private String[] generateCurlConmmand(String url){
		int length = postPara.length();
		
		//curl --data keyword=qq www.abc.com
		String [] commands = new String[]{"curl" , "--data-urlencode" , postPara , "=" , keyword , url};
		
		return commands;
	}
	
	public static void main(String[] args) {

	}

}
