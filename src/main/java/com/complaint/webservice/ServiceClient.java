package com.complaint.webservice;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.complaint.model.User;

public class ServiceClient {
	   public static void main(String[] args1) throws AxisFault
	    {
	        String path = "http://localhost:8080/services/SpringAwareService";
	        EndpointReference targetEPR = new EndpointReference(path);
	        RPCServiceClient serviceClient = new RPCServiceClient();
	        Options options = serviceClient.getOptions();
	        options.setTo(targetEPR);
	        QName opGetWeather = new QName("http://webservice.complaint.com","greeting");
	        User user = new User();
	        user.setUserid(5);
	        Object[] response = serviceClient.invokeBlocking(opGetWeather,
	                new Object[]{"张三"},new Class[]{String.class});
	        System.out.println(response[0]);
	    }
}
