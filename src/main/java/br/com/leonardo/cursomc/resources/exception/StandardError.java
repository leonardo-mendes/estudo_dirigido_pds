package br.com.leonardo.cursomc.resources.exception;

import java.io.Serializable;

public class StandardError implements Serializable{ // Utilizamos o Serializable para conseguir utilizar a classe em trafego de dados.
	private static final long serialVersionUID = 1L; // Sempre é criado quando implementamos a Serializable
	
	private Integer status;
	private String msg;
	private Long timestamp;
	
	public StandardError(Integer status, String msg, Long timestamp) {
		super();
		this.status = status;
		this.msg = msg;
		this.timestamp = timestamp;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
