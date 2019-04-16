package pokemonDB.daoimpl;

import pokemonDB.dao.PokemonDao;
import pokemonDB.model.Pokemon;

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
	ArrayList<Pokemon> pokemon = null;
	
    public PokemonDaoImpl(MongoDatabase db) 
    {
    	database = db;
    	coll = database.getCollection("pokemon");
        if(coll == null) 
        {
            // TODO: Throw exception (pokemon collection DNE)
        }
        pokemon = null;	//load games from real database as needed
    }


	@Override
	public Pokemon findPokemon(String name) 
	{
		BasicDBObject query = new BasicDBObject();
		query.put("name", name.toLowerCase().trim());
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) 
		{
			Pokemon pokemon = null;
	        for (Document d : docs) 
	        {
				pokemon = new Pokemon(d.getInteger("pokedex_number"), d.getString("name"), d.getString("type1"), d.getString("type2"), d); //complete the mapping of a document to POJO
			}
	        return pokemon;
		}
		return null;
	}

	@Override
	public Collection<Pokemon> findAllPokemon() 
	{
		BasicDBObject query = new BasicDBObject();
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) 
		{
			ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
			for (Document d : docs)
			{
				pokemon.add(new Pokemon(d.getInteger("pokedex_number"), d.getString("name"), d.getString("type1"), d.getString("type2"), d));
			}
			return pokemon;
		}
		return null;
	}

}