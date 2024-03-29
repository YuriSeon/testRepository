package com.kh.board.model.vo;

public class Category {
//	CATEGORY_NO	NUMBER
//	CATEGORY_NAME	VARCHAR2(30 BYTE)
	
	private int categoryNo;
	private String categoryName;
	
	public Category(){
		
	}
	
	public Category(int categoryNo, String categoryName) {
		this.categoryNo = categoryNo;
		this.categoryName = categoryName;
	}
	
	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}
	
	public int getCategoryNo() {
		return categoryNo;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public String toString() {
		return "Category [categoryNo=" + categoryNo + ", categoryName=" + categoryName + "]";
	}
	
	

}
