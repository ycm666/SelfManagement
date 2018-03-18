package vo;

import org.json.simple.JSONAware;

public class GroupListVo implements JSONAware{
	int g_index;
	String g_subject;
	String c_name;
	int m_index;
	int c_index;
	
	public GroupListVo() {
		// TODO Auto-generated constructor stub
	}
	
	
	public GroupListVo(String g_subject, int m_index, int c_index) {
		super();
		this.g_subject = g_subject;
		this.m_index = m_index;
		this.c_index = c_index;
	}


	

	public String getC_name() {
		return c_name;
	}


	public void setC_name(String c_name) {
		this.c_name = c_name;
	}


	public int getG_index() {
		return g_index;
	}
	public void setG_index(int g_index) {
		this.g_index = g_index;
	}
	public String getG_subject() {
		return g_subject;
	}
	public void setG_subject(String g_subject) {
		this.g_subject = g_subject;
	}
	public int getM_index() {
		return m_index;
	}
	public void setM_index(int m_index) {
		this.m_index = m_index;
	}
	public int getC_index() {
		return c_index;
	}
	public void setC_index(int c_index) {
		this.c_index = c_index;
	}


	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		String json_str = String.format("{'g_index':%d,'g_subject':'%s','c_index':%d,'c_name':'%s','m_index':%d}",
				                                   g_index,g_subject, c_index,c_name,m_index);
		return json_str;
	}
	
	
}
