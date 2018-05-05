package top.kylewang.crawler;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * @author KyleWang
 * @version 1.0
 * @description
 * @date 2018年05月05日
 */
public class MyJsoupHttpclientTest {

	@Test
	public void testZhiHu() throws IOException {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建httpGet发送请求
		HttpGet httpGet = new HttpGet("https://www.zhihu.com/");
		// 设置请求头,模拟请求发送
		httpGet.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36");
		httpGet.addHeader("Cookie",
				"q_c1=a10bb76ca2e848d9b87d85566a86706b|1507426358000|1507426358000; _zap=8343ca2b-2428-4363-8961-970dbfac3b28; z_c0=\"2|1:0|10:1520059333|4:z_c0|92:Mi4xVF9VSUFnQUFBQUFBa0d0TXVLdzZEU1lBQUFCZ0FsVk54WkdIV3dDNlhtY1ZUTms5ZXd4NkNYVG05bl9ITUtKZFR3|57a0468ad1355b82fe2225885e7fb2ff1cbb5a21eec34bfe2bc048933e9c671c\"; __utma=51854390.12236170.1513830366.1513830366.1520059344.2; __utmz=51854390.1520059344.2.2.utmcsr=zhihu.com|utmccn=(referral)|utmcmd=referral|utmcct=/question/20975331; __utmv=51854390.100-1|2=registration_date=20150829=1^3=entry_date=20150829=1; __DAYU_PP=RFrqIBfUeBZqN77mbiub2da6cf229c8a; q_c1=a10bb76ca2e848d9b87d85566a86706b|1524809545000|1507426358000; d_c0=\"AAAh6wZLiA2PTj7_oPatSP9BLar3jAD5ru0=|1525264772\"; aliyungf_tc=AQAAAHvcuipR3wEAzjuXdc+ajlW5v9MU; _xsrf=dd518f89-a11b-41da-ad63-5268c6eeb75a");
		// 发送请求, 抓取页面
		CloseableHttpResponse response = httpClient.execute(httpGet);
		String content = EntityUtils.toString(response.getEntity(), "utf-8");
		// 使用Jsoup解析网页内容
		Document document = Jsoup.parse(content);
		// 获取文档标题
		String title = document.title();
		System.out.println("title:" + title);
		Elements elements = document.select("a[data-za-detail-view-element_name=Title]");
		System.out.println(elements);
		for (Element element : elements) {
			System.out.println(element.text() + ":" + element.attr("href"));
		}
	}

	@Test
	public void testBaiduNews() throws IOException {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建httpGet发送请求
		HttpGet httpGet = new HttpGet("https://news.baidu.com/");
		// 设置请求头,模拟请求发送
		httpGet.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36");
		// 发送请求, 抓取页面
		CloseableHttpResponse response = httpClient.execute(httpGet);
		String content = EntityUtils.toString(response.getEntity(), "utf-8");
		// 使用Jsoup解析网页内容
		Document document = Jsoup.parse(content);
		// 获取文档标题
		String title = document.title();
		System.out.println("title:" + title);
		Elements elements = document.select("#pane-news a,.bdlist a,.olist a,.ulist a").not(".name");
		// Elements elements = document.select("a[target=_blank]");
		for (Element element : elements) {
			String newsTitle = element.attr("title");
			System.out.println((StringUtil.isBlank(newsTitle) ? element.text() : newsTitle) + ":" + element.attr("href"));
		}
        System.out.println("新闻总条数:"+elements.size());
	}

}
