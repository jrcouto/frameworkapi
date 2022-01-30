package br.com.framework.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.framework.api.event.ResourceCreatedEvent;

@Component
public class ResourceCreatedListener implements ApplicationListener <ResourceCreatedEvent>{

	@Override
	public void onApplicationEvent(ResourceCreatedEvent resourceCreatedEvent) {
		// TODO Auto-generated method stub
		HttpServletResponse response = resourceCreatedEvent.getResponse();
		Integer id = resourceCreatedEvent.getId();
		
		addHeaderLocation(response, id);

	}
	
	private void addHeaderLocation(HttpServletResponse response,Integer id) {
		//Pega a URL do item gerado para retornar no header da resposta
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
		
		//Retornar a URI do item (recurso) criado no Header é uma boa prática para identificar o caminho do recurso
		response.setHeader("Location", uri.toASCIIString());
	}

}
