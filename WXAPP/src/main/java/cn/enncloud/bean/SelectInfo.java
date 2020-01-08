package cn.enncloud.bean;

import java.util.ArrayList;
import java.util.List;

public class SelectInfo {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private List<SelectInfo> children;
	public SelectInfo(String id,String name){
		this.id=id;
		this.name=name;
		this.children = new ArrayList<SelectInfo>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SelectInfo> getChildren() {
		return children;
	}
	public void setChildren(List<SelectInfo> children) {
		this.children = children;
	}
}
