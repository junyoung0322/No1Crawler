
/**
 * スクレーピング対象モデル
 * 
 * @author jun
 *
 */
public class SearchModel {
	// リンク先URL
	private String href;
	
	// タイトル
	private String title;
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
