package br.com.cotiinformatica.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Produto;

@Repository
public interface IProdutoRepository extends CrudRepository<Produto, Integer>{

}
