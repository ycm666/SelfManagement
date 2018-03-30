package vo;

import org.json.simple.JSONAware;

public class DailyVo implements JSONAware {
	int d_index;
	String d_subject;
	String d_content;
	String d_image;
	String d_date;
	int g_index;
	
	
	public DailyVo() {
		// TODO Auto-generated constructor stub
	}
	
	
	public DailyVo(int d_index, String d_subject, String d_content) {
		super();
		this.d_index = d_index;
		this.d_subject = d_subject;
		this.d_content = d_content;
	}


	public DailyVo(String d_subject, String d_content, String d_image, int g_index) {
		super();
		this.d_subject = d_subject;
		this.d_content = d_content;
		this.d_image = d_image;
		this.g_index = g_index;
	}



	public int getD_index() {
		return d_index;
	}
	public void setD_index(int d_index) {
		this.d_index = d_index;
	}
	public String getD_subject() {
		return d_subject;
	}
	public void setD_subject(String d_subject) {
		this.d_subject = d_subject;
	}
	public String getD_content() {
		return d_content;
	}
	public void setD_content(String d_content) {
		this.d_content = d_content;
	}
	public String getD_image() {
		return d_image;
	}
	public void setD_image(String d_image) {
		this.d_image = d_image;
	}
	public String getD_date() {
		return d_date;
	}
	public void setD_date(String d_date) {
		this.d_date = d_date;
	}
	public int getG_index() {
		return g_index;
	}
	public void setG_index(int g_index) {
		this.g_index = g_index;
	}


	
	
	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		String json_str = String.format("{'d_index':%d,'g_index':%d,'d_subject':'%s','d_content':'%s','d_date':'%s','d_image':'%s'}",
				                         d_index,g_index,d_subject,d_content,d_date,d_image  
				);
		return json_str;
	}
	
	
}
