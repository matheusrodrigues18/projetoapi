package br.com.cotiinformatica.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Usuario;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario,Integer>{

	@Query("select u from Usuario u where u.login = :login")
	Usuario findByLogin(@Param("login") String login);
	
	@Query("select u from Usuario u where u.login = :login and u.senha = :senha")
	Usuario findByLoginAndSenha(@Param("login") String login, @Param("senha") String senha);
	
}
