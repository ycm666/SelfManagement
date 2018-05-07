package vo;

import org.json.simple.JSONAware;

public class MemberVo  implements JSONAware{
	int m_index;
	String m_id,m_pwd,m_name,m_hint,m_phone_num;
	
	public MemberVo() {
		// TODO Auto-generated constructor stub
	}
	
	public MemberVo(String m_id, String m_pwd, String m_name, String m_phone_num, String m_hint) {
		super();
		this.m_id = m_id;
		this.m_pwd = m_pwd;
		this.m_name = m_name;
		this.m_phone_num = m_phone_num;
		this.m_hint = m_hint;
	}




	public String getM_phone_num() {
		return m_phone_num;
	}
	public void setM_phone_num(String m_phone_num) {
		this.m_phone_num = m_phone_num;
	}
	public int getM_index() {
		return m_index;
	}
	public void setM_index(int m_index) {
		this.m_index = m_index;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_pwd() {
		return m_pwd;
	}
	public void setM_pwd(String m_pwd) {
		this.m_pwd = m_pwd;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	
	public String getM_hint() {
		return m_hint;
	}

	public void setM_hint(String m_hint) {
		this.m_hint = m_hint;
	}

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		String json_str = String.format("{'m_index':%d,'m_id':'%s','m_pwd':'%s','m_name':'%s','m_phone_num':'%s','m_hint':'%s'}", 
				                               m_index,      m_id,         m_pwd,       m_name,        m_phone_num, m_hint
				);
		return json_str;
	}
	
	
}
