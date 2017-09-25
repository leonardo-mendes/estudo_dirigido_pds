package br.com.leonardo.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.leonardo.cursomc.domain.enums.EstadoPagamento;

@Entity
public class PagamentoBoleto extends Pagamento{
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dtvenc;
	
	@JsonFormat(pattern="dd/MM/yyyy hh:mm")
	private Date dtpag;
	
	public PagamentoBoleto() {
		
	}

	public PagamentoBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dtvenc, Date dtpag) {
		super(id, estado, pedido);
		this.dtpag=dtpag;
		this.dtvenc=dtvenc;
	}

	public Date getDtvenc() {
		return dtvenc;
	}

	public void setDtvenc(Date dtvenc) {
		this.dtvenc = dtvenc;
	}

	public Date getDtpag() {
		return dtpag;
	}

	public void setDtpag(Date dtpag) {
		this.dtpag = dtpag;
	}
	
	
	
	
}
