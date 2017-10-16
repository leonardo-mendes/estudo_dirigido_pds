package br.com.leonardo.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

// Essa classe foi criada exclusivamente para pegar a URL com a lista de IDs e tratar para outros metodos.

public class URL {
	
	public static List<Integer> decodeIntList(String s){
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		
		for(int i=0; i<vet.length ; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		
		return list;
	}
	
	// Esse metodo serve para decodificar a URL que vem como parametro, pois pode vim com tratamento de espaço como por exemplo: "TV LED" ira vim com "TV20%LED"
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		
	}

}
