import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * scraper 
 * create - 2017-12-11
 * 
 * @author jun
 */
public class Crawler {
	public static void main(String[] args) {
		ArrayList<SearchModel> oModelList = new ArrayList<SearchModel>();
		// URL
		String sUrl = "https://no1s.biz/";
		String sUrlRegex = "<a href=\"(.+?)\"";

		try {
			ArrayList<String> sCrap = getSource(sUrl);
			TreeSet<String> tLinks = new TreeSet<String>();
			for (String sSource: sCrap) {
				Matcher ｍUrlM = null;
				if (sSource.contains("<a href=\"https")) {
					if (!sSource.contains("facebook")) {
						ｍUrlM = Pattern.compile(sUrlRegex).matcher(sSource);
						
						while (ｍUrlM.find()) {
							String sUrlString = ｍUrlM.group(1);
							tLinks.add(sUrlString);
						}
					}
				}
			}
			// 重複を除いたリンクリスト
			ArrayList<String> lLinkList = new ArrayList<String>(tLinks);
			for (String sLink: lLinkList) {
				ArrayList<String> sTitleCrap = getSource(sLink);
				for (String sTitleSource: sTitleCrap) {
					if (sTitleSource.contains("</title>")) {
						String sTitle = sTitleSource.replace("</title>", "");
						SearchModel oModel = new SearchModel();
						
						//オブジェクト作成
						oModel.setTitle(sTitle);
						oModel.setHref(sLink);
						oModelList.add(oModel);
						
						break;
					}
				}
			}
			printList(oModelList);
		}
		catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public static void printList(ArrayList<SearchModel> mList) throws Exception {
		final String sHeader= "<<<  No1soulutionsリンク一覧  >>>";
		final String sBoundary = "======================================";
		
		System.out.println(sHeader);
		System.out.println(sBoundary);
		for (SearchModel m: mList) {
			System.out.println("<< Title : " + m.getTitle() + " >>");
			System.out.println("    Link : " + m.getHref());
			System.out.println("------------------------");
		}
		System.out.println("");
		System.out.println(sBoundary);
	}

	public static ArrayList<String> getSource(String sUrl) throws MalformedURLException, IOException
	{
		ArrayList<String> output = new ArrayList<>();

		URL urlObject = new URL(sUrl);
		URLConnection urlCon = urlObject.openConnection();
		urlCon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");

		InputStream inputStream = urlCon.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
		String sLine;
		while ((sLine = br.readLine()) != null) {
			output.add(sLine);
		}
		br.close();
		
		return output;
	}
}




