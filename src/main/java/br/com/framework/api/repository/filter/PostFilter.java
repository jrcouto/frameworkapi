package br.com.framework.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class PostFilter {

	private String text;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate publishDateFrom;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate publishDateTo;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDate getPublishDateFrom() {
		return publishDateFrom;
	}

	public void setPublishDateFrom(LocalDate publishDateFrom) {
		this.publishDateFrom = publishDateFrom;
	}

	public LocalDate getPublishDateTo() {
		return publishDateTo;
	}

	public void setPublishDateTo(LocalDate publishDateTo) {
		this.publishDateTo = publishDateTo;
	}
	
	
	
	
	
}
