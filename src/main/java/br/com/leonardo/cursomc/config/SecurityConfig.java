package br.com.leonardo.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.leonardo.cursomc.security.JWTAuthenticationFilter;
import br.com.leonardo.cursomc.security.JWTAuthorizationFilter;
import br.com.leonardo.cursomc.security.JWTUtil;

/*Como configuramos o pom.xml com o Security o spring ja bloqueou todos os nossos endpoints então teremos que configura-lo para funcionar corretamente*/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends  WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	// Criamos um vetor de string para colocar todos os caminhos da URL que serão livres para acesso.
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**" // Tudo a frente de /h2-console/
	};
	
	// Criamos um vetor de string para colocar todos os caminhos da URL que serão livres para acesso para busca GET.
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**"
    };
	
	// Criamos um vetor de string para colocar todos os caminhos da URL que serão livres para acesso para insert.
		private static final String[] PUBLIC_MATCHERS_POST = {
				"/clientes/**"
	    };
	
	@Override // Vamos sobreescrever esse metódo com o parametro padrão do Spring
	protected void configure(HttpSecurity http) throws Exception{
		
		// Essa configuração permite que possamos utilizar o banco do H2 sem ocorrer erro
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }
		
		http.cors().and().csrf().disable();
		// A expressão abaixo autoriza o meu vetor acima a responder todas requisições
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // Essa parte do método eu defino que para requisições GET eu irei permitir os links do vetor
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll() // Essa parte do método eu defino que para requisições POST eu irei permitir os links do vetor
			.antMatchers(PUBLIC_MATCHERS)
			.permitAll()
			.anyRequest()
			.authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	// Esse Bean é para disponibilizar um decode para o sistema, no caso senha do Cliente
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Essa sobrecarga ocorre porque nos vamos utilizar a autenticação do framework, então temos que passar para ele quem é o UserDetail utilizado e qual sera a decodificação.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

}
