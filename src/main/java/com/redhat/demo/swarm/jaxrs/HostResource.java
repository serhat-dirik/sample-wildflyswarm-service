package com.redhat.demo.swarm.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.joda.time.DateTime;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

@Path("/")
@Api(value = "Host Service")
@Produces(MediaType.APPLICATION_JSON)
public class HostResource {

	private static String ip;
	private static String hostname;
	@Context
	UriInfo context;

	static {
		try {
			ip = "{\"ip\":\" " + InetAddress.getLocalHost().getHostAddress().toString() + "\"}";
			hostname = "{\"hostName\":\"" + InetAddress.getLocalHost().getHostName() + "\"}";
			ArrayList<String> ips = new ArrayList<>();
					
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			for (; n.hasMoreElements();) {
				NetworkInterface e =  n.nextElement();
				System.out.println("Interface: " + e.getName());
				Enumeration<InetAddress> a = e.getInetAddresses();
				for (; a.hasMoreElements();) {
					InetAddress addr = (InetAddress) a.nextElement();
					if(! addr.getHostAddress().startsWith("127") && ! addr.getHostAddress().startsWith("0") ){
						 String str = addr.getHostAddress();
						 if (str.contains(":")){
							 str = str.substring(0, str.indexOf("%") - 1);
							 ips.add(str);
						 }else{
							 ips.add(addr.getHostAddress());
						 }
					}
				}
			}
			
			if(ips.size() > 0){
			    ip = "";
				for (String str : ips) {
					if(ip.length() == 0){
						ip = "{\"ip\":["+"\""+str+"\"";		
					}else{
						ip += ",\""+str+"\"";	
					}
				}
				ip += "] }";
			}else{
				ip = "{\"ip\":[] }";
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	@ApiOperation(value = "Forwards to API doc",hidden = true)
	public String forwardToSwagger() {
		return "<meta http-equiv=\"refresh\" content=\"0; url="+context.getBaseUri()+"swagger-ui/index.html?url=/swagger.json\" />";
	}
	
	@GET
	@Path("/name")
	@ApiOperation(value = "Get the host name", notes = "Returns the hostname", response = String.class)
	@Produces(MediaType.APPLICATION_JSON)
	public String hostName() {
		return hostname;
	}

	@GET
	@Path("/ip")
	@ApiOperation(value = "Get the ip address of the host", notes = "Returns the ip address as a string", response = String.class)
	@Produces(MediaType.APPLICATION_JSON)
	public String ip() {
		return ip;
	}

	@GET
	@Path("/time")
	@ApiOperation(value = "Get the current time", notes = "Returns the time as a string", response = String.class)
	@Produces(MediaType.APPLICATION_JSON)
	public String now() {
		return String.format("{\"time\":\"%s\"}", new DateTime());
	}
}
