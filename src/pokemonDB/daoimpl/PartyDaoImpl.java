package pokemonDB.daoimpl;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import pokemonDB.dao.PartyDao;
import pokemonDB.model.Party;
import pokemonDB.model.Pokemon;

public class PartyDaoImpl implements PartyDao
{
	
	private MongoDatabase database = null;
	private MongoCollection<Document> coll = null;
	Party party = null;
	
    public PartyDaoImpl(MongoDatabase db) 
    {
    	database = db;
    	coll = database.getCollection("pokemon");
        if(coll == null) 
        {
            // TODO: Throw exception (pokemon collection DNE)
        }
        party = null;	//load games from real database as needed
    }

	@Override
	public Party getParty() 
	{
		BasicDBObject query = new BasicDBObject("in_party", 1);
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) 
		{
			ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
			for (Document d : docs)
			{
				pokemon.add(new Pokemon(d.getInteger("pokedex_number"), d.getString("name"), d.getString("type1"), d.getString("type2"), d));
			}
			
			assert pokemon.size() <= 6 : "Party overflow";
			
			return new Party(pokemon);
		}
		return null;
	}

	@Override
	public void clearParty()
	{
		BasicDBObject query = new BasicDBObject();
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) 
		{
			coll.updateMany(query, new Document("$set", new Document("in_party", 0)));
		}	
	}
	
	@Override
	public Party replaceParty(String ... pokemon)
	{
		assert pokemon.length <= 6 : "Party overflow"; 
		
		clearParty();
				
		for (String name : pokemon)
		{
			addMember(name);
		}
		
		return getParty();
	}

	@Override
	public Party addMember(String name)
	{
		BasicDBObject query = new BasicDBObject("name", name.toLowerCase().trim());
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) 
		{
			coll.updateOne(query, new Document("$set", new Document("in_party", 1)));
		}
		
		return getParty();
	}

	@Override
	public Party removeMember(String name)
	{
		BasicDBObject query = new BasicDBObject("name", name.toLowerCase().trim());
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) 
		{
			coll.updateOne(query, new Document("$set", new Document("in_party", 0)));
		}
		
		return getParty();
	}

}