package br.com.leonardo.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.domain.PagamentoBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoBoleto(PagamentoBoleto pagto, Date instante) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDtvenc(cal.getTime());
		
	}
	
	
}
