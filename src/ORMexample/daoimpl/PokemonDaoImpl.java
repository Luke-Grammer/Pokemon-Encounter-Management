package ORMexample.daoimpl;

import ORMexample.dao.PokemonDao;
import ORMexample.model.DBPokemon;

import java.util.ArrayList;
import java.util.Collection;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class PokemonDaoImpl implements PokemonDao {

    //list is working as a database
	private MongoDatabase database = null;
	private MongoCollection<Document> coll = null;
	ArrayList<DBPokemon> pokemon = null;
	
    public PokemonDaoImpl(MongoDatabase db) 
    {
    	database = db;
    	coll = database.getCollection("pokemon");
        if(coll!=null)
        	System.out.println("Select pokemon successful"); //should throw an exception
        else System.out.println("pokemon NOT found!");
        pokemon = null;	//load games from real database as needed
    }


	@Override
	public DBPokemon findPokemon(String name) 
	{
		BasicDBObject query = new BasicDBObject();
		query.put("name", name.toLowerCase().trim());
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) 
		{
			DBPokemon pokemon = null;
	        for (Document d : docs) 
	        {
				pokemon = new DBPokemon(d.getInteger("pokedex_number"), d.getString("name"), d.getString("type1"), d.getString("type2"), d); //complete the mapping of a document to POJO
			}
	        return pokemon;
		}
		return null;
	}

	@Override
	public Collection<DBPokemon> findAllPokemon() 
	{
		BasicDBObject query = new BasicDBObject();
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) 
		{
			ArrayList<DBPokemon> pokemon = new ArrayList<DBPokemon>();
			for (Document d : docs)
			{
				pokemon.add(new DBPokemon(d.getInteger("pokedex_number"), d.getString("name"), d.getString("type1"), d.getString("type2"), d));
			}
			return pokemon;
		}
		return null;
	}

}