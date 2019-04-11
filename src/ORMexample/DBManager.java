package ORMexample;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ORMexample.daoimpl.PokemonDaoImpl;
import ORMexample.model.DBPokemon;

public class DBManager {
	public static final String DATABASE = "PokemonDB";
	public static final String IP = "localhost";
	public static final int PORT = 27017; 
	
	private PokemonDaoImpl pkmDao = null;
	private MongoClient mongoClient = null;
	private MongoDatabase database = null;
	private static DBManager DBmgr = null;
	private Logger mongoLogger = null;
	MongoCollection<Document> coll = null;

	public DBManager() {
		try {
			mongoLogger = Logger.getLogger( "org.mongodb.driver" );
			mongoLogger.setLevel(Level.WARNING);
			
			mongoClient = new MongoClient(IP , PORT);
			database = mongoClient.getDatabase(DATABASE);
			if(database!=null) {
				System.out.println("Connect to testdb Database Successful");
				pkmDao = new PokemonDaoImpl(database);
			}
			else System.out.println("Database NOT found!");
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			mongoClient.close();
		}
		catch (MongoException e) {
			e.printStackTrace();
		}
	}
	// A singleton design pattern 
	public static DBManager getInstance(){
		if(DBmgr==null){
			DBmgr = new DBManager();
		}
		return DBmgr;
	}

	public DBPokemon findPokemon(String name) {
		return pkmDao.findPokemon(name);
	}
	
	public Collection<DBPokemon> findPokemon() {
		return pkmDao.findAllPokemon();
	}
}
