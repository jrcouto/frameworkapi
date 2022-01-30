package br.com.framework.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreatedEvent extends ApplicationEvent{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse response;
	
	private Integer id;

	public ResourceCreatedEvent(Object source, HttpServletResponse response, Integer id) {
		super(source);
		
		this.response = response;
		this.id = id;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Integer getId() {
		return id;
	}

	
}
