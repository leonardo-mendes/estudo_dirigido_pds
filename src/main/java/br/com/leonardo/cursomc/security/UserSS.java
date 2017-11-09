package br.com.leonardo.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.leonardo.cursomc.domain.enums.Perfil;

// Essa classe serve para implementar a interface que é um contrato que o Spring precisa para funcionar o Security
public class UserSS implements UserDetails{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String senha;
	private String email;
	private  Collection<? extends GrantedAuthority> authorities;
	
	
	public UserSS() {
		
	}
	
		
	public UserSS(Integer id, String senha, String email, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.senha = senha;
		this.email = email;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	

	public Integer getId() {
		return id;
	}


	@Override // Esse método se fossemos utilizar uma regra de negócio que a conta expiraria transformariamos o seu retorno em false
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
