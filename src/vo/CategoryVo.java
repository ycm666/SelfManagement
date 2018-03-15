package vo;

import org.json.simple.JSONAware;

public class CategoryVo implements JSONAware{
	int c_index;
	String c_name;
	public int getC_index() {
		return c_index;
	}
	public void setC_index(int c_index) {
		this.c_index = c_index;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		String json_str = String.format("{'c_index':%d,'c_name':'%s'}", c_index,c_name);
		return json_str;
	}
	
}
