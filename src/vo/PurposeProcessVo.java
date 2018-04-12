package vo;

import org.json.simple.JSONAware;

public class PurposeProcessVo implements JSONAware{

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		String json_str = String.format("{'pp_index':%d,'pp_memo':'%s','pp_date':'%s','pp_value':%d,'p_index':%d}",pp_index,pp_memo,pp_date,pp_value,p_index); 
		return json_str;
	}
	int pp_index;
	String pp_memo;
	String pp_date;
	int pp_value;
	int p_index;
	
	public PurposeProcessVo() {
		
	}
	
	
	public PurposeProcessVo(String pp_memo, int pp_value,String pp_date, int p_index) {
		super();
		this.pp_memo = pp_memo;
		this.pp_value = pp_value;
		this.p_index = p_index;
		this.pp_date = pp_date;
		
		
	}
	public int getPp_index() {
		return pp_index;
	}
	public void setPp_index(int pp_index) {
		this.pp_index = pp_index;
	}
	public String getPp_memo() {
		return pp_memo;
	}
	public void setPp_memo(String pp_memo) {
		this.pp_memo = pp_memo;
	}
	public String getPp_date() {
		return pp_date;
	}
	public void setPp_date(String pp_date) {
		this.pp_date = pp_date;
	}
	public int getPp_value() {
		return pp_value;
	}
	public void setPp_value(int pp_value) {
		this.pp_value = pp_value;
	}
	public int getP_index() {
		return p_index;
	}
	public void setP_index(int p_index) {
		this.p_index = p_index;
	}
	
	
	
}
