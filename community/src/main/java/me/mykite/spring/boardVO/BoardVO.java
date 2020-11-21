package me.mykite.spring.boardVO;

public class BoardVO {
	
	private int bNum;
	private String bId;
	private String bTitle;
	private String bContent;
	private String bRegDate;
	private int bParent;
	private int bIndent;
	private int bOrder;
	private int bFrequency;
	
	public BoardVO() {
		
	}
	
	public BoardVO(String bId, String bContent) {
		this.bId = bId;
		this.bContent = bContent;
	}
	
	public int getbNum() {
		return bNum;
	}
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public String getbTitle() {
		return bTitle;
	}
	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}
	public String getbContent() {
		return bContent;
	}
	public void setbContent(String bContent) {
		this.bContent = bContent;
	}
	public String getbRegDate() {
		return bRegDate;
	}
	public void setbRegDate(String bRegDate) {
		this.bRegDate = bRegDate;
	}
	public int getbParent() {
		return bParent;
	}
	public void setbParent(int bParent) {
		this.bParent = bParent;
	}
	public int getbIndent() {
		return bIndent;
	}
	public void setbIndent(int bIndent) {
		this.bIndent = bIndent;
	}
	public int getbOrder() {
		return bOrder;
	}
	public void setbOrder(int bOrder) {
		this.bOrder = bOrder;
	}
	public int getbFrequency() {
		return bFrequency;
	}
	public void setbFrequency(int bFrequency) {
		this.bFrequency = bFrequency;
	}
	
	
}
