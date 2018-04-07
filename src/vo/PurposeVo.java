package vo;

import org.json.simple.JSONAware;

public class PurposeVo implements JSONAware{

	int p_index;
	String p_content;
	String p_subject;
	String p_unit;
	int p_goal;
	String p_date;
	int m_index;
	int p_now;
	
	
	public PurposeVo() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public PurposeVo(String p_subject,String p_content,  String p_unit, int p_goal, int m_index) {
		super();
		this.p_content = p_content;
		this.p_subject = p_subject;
		this.p_unit = p_unit;
		this.p_goal = p_goal;
		this.m_index = m_index;
	}


	

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		String json_str = String.format("{'p_index':%d,'p_content':'%s','p_subject':'%s','p_unit':'%s','p_goal':%d,'p_date':'%s','m_index':%d,'p_now':%d}", p_index,p_content,p_subject,p_unit,p_goal,p_date,m_index,p_now);
		return json_str;
	}
	
	public int getP_now() {
		return p_now;
	}
	public void setP_now(int p_now) {
		this.p_now = p_now;
	}
	public int getP_index() {
		return p_index;
	}
	public void setP_index(int p_index) {
		this.p_index = p_index;
	}
	public String getP_content() {
		return p_content;
	}
	public void setP_content(String p_content) {
		this.p_content = p_content;
	}
	public String getP_subject() {
		return p_subject;
	}
	public void setP_subject(String p_subject) {
		this.p_subject = p_subject;
	}
	public String getP_unit() {
		return p_unit;
	}
	public void setP_unit(String p_unit) {
		this.p_unit = p_unit;
	}
	public int getP_goal() {
		return p_goal;
	}
	public void setP_goal(int p_goal) {
		this.p_goal = p_goal;
	}
	
	public String getP_date() {
		return p_date;
	}
	public void setP_date(String p_date) {
		this.p_date = p_date;
	}
	public int getM_index() {
		return m_index;
	}
	public void setM_index(int m_index) {
		this.m_index = m_index;
	}
	
	
	
	
}
