package hg.party.entity.echarts;
/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年4月4日上午11:19:56<br>
 * 修改内容： <br>
 */
public class EchartObj {
	private String keyname;
	private double valname;
	public String getKeyname() {
		return keyname;
	}
	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}
	public double getValname() {
		return valname;
	}
	public void setValname(double valname) {
		this.valname = valname;
	}
	@Override
	public String toString() {
		return "echartObj [keyname=" + keyname + ", valname=" + valname + "]";
	}
}
