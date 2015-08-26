package com.here.framework.xml.adaptor;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.here.framework.util.JSONUtil;
/**
 * map转换
 * @author koujp
 *
 */
@XmlType(name = "MapAdapter")
@XmlAccessorType(XmlAccessType.FIELD)
public class MapAdaptor extends XmlAdapter<String, Map<Object,Object>> {

	@Override
	public Map<Object, Object> unmarshal(String v) throws Exception {
		
		return JSONUtil.toBean(v, Map.class);
	}

	@Override
	public String marshal(Map<Object, Object> v) throws Exception {
		// TODO Auto-generated method stub
		return JSONUtil.toJson(v);
	}

	

}
