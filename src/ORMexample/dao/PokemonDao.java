package ORMexample.dao;

import java.util.Collection;

import ORMexample.model.DBPokemon;

public interface PokemonDao {

	DBPokemon findPokemon(String name);
	
	Collection<DBPokemon> findAllPokemon();
}
