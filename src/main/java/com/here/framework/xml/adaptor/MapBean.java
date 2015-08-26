package com.here.framework.xml.adaptor;

import java.util.ArrayList;
import java.util.List;
/**
 * map转换bean
 * @author koujp
 *
 */
public class MapBean {
	public static class Entry{
		private String key;
		private String value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	private List<Entry> maps=new ArrayList<MapBean.Entry>();
	public List<Entry> getMaps() {
		return maps;
	}
	public void setMaps(List<Entry> maps) {
		this.maps = maps;
	}
}
