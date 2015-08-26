package com.here.framework.service;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;
import org.codehaus.jackson.map.ObjectMapper;

public class ServiceExceptionMapper implements ResponseExceptionMapper<ServiceException>{
	 
    /** (non-Javadoc)
     * @see org.apache.cxf.jaxrs.client.ResponseExceptionMapper#fromResponse(javax.ws.rs.core.Response)
     */
    @Override
    public ServiceException fromResponse(Response r) {
        Object obj = r.getEntity();
        String className = r.getHeaderString("exception");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
        	if(null!=className){
        		Object exception=r.readEntity(Class.forName(className));
        		if(null!=exception){
        			return new ServiceException((Throwable)exception);
        		}
        	}
			return new ServiceException(obj.toString());
		} catch (ClassNotFoundException e) {
			return new ServiceException(obj.toString());
		}
    }
     
}