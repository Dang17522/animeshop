package com.anime.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo implements Serializable {

	private static final long serialVersionUID = -1952180930844725508L;
	
	private String from;
	private String to;
	private String subject;
	private String body;

	public MailInfo(String to, String subject, String body) {
		this.from = "hfurnitureshop@gmail.com";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}
