package pokemonDB.model;

import java.io.Serializable;

import org.bson.Document;

/**
 * Represents a pokemon from a MongoDB collection as a POJO.
 * 
 * @author luke grammer
 */
public class Pokemon implements Serializable {

	private static final long serialVersionUID = 3338124225388353508L;
	
	private Integer number;
    private String name;
    private String type1;
    private String type2;
    private Document doc;
    
    /**
     * Constructor for a Pokemon object with a 
     * primary type but no secondary type.
     * 
     * @param number is the pokemon's pokedex number. 
     * @param name is the name of the pokemon.
     * @param type1 is the pokemon's primary type.
     * @param d is the document in the database 
     *        corresponding to the object.
     */
    public Pokemon(Integer number, String name, String type1, Document d) {
    	assert number > 0 : "Invalid pokedex number";
    	
        this.number = number;
        this.name = name.toLowerCase().trim();
        this.type1 = type1.toLowerCase().trim();
        this.type2 = null;
        this.doc = d;
    }

    /**
     * Constructor for a Pokemon object with a 
     * primary type and a secondary type.
     * 
     * @param number is the pokemon's pokedex number. 
     * @param name is the name of the pokemon.
     * @param type1 is the pokemon's primary type.
     * @param type2 is the pokemon's secondary type.
     * @param d is the document in the database 
     *        corresponding to the object.
     */
    public Pokemon(Integer number, String name, String type1, String type2, Document d) {
    	assert number > 0 : "Invalid pokedex number";
    	
        this.number = number;
        this.name = name.toLowerCase().trim();
        this.type1 = type1.toLowerCase().trim();
        if (type2 != null)
        {
        	this.type2 = type2.toLowerCase().trim();
        }
        this.doc = d;
    }

    /**
     * Retrieves this pokemon's pokedex number.
     * 
     * @return the pokedex number of this pokemon.
     */
	public Integer getPokedexNumber()
	{
		return number;
	}
	
	/**
	 * Retrieves this pokemon's name 
	 * (capitalizes first letter).
	 * 
	 * @return the name of this pokemon.
	 */
	public String getName()
	{
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	/**
	 * Retrieves this pokemon's primary type 
	 * (capitalizes first letter).
	 * 
	 * @return the primary type of this pokemon.
	 */
	public String getPrimaryType()
	{
		return type1.substring(0, 1).toUpperCase() + type1.substring(1);
	}

	/**
	 * Retrieves this pokemon's secondary type 
	 * (capitalizes first letter).
	 * 
	 * @return the secondary type of this pokemon.
	 */
	public String getSecondaryType()
	{
		if (type2 != null)
			return type2.substring(0, 1).toUpperCase() + type2.substring(1);
		else 
			return null;
	}
	
	/**
	 * Retrieves an arbitrary string field from the 
	 * document in the database representing this 
	 * pokemon.
	 * 
	 * @param fieldName is the name of the document
	 *        field to retrieve.
	 * @return an arbitrary string field from the
	 * 		   document specified by fieldName.
	 */
	public String getString(String fieldName) {
		return doc.getString(fieldName);
		// TODO: Test errors when fieldName is not present in document
	}

	/**
	 * Retrieves an arbitrary integer field from 
	 * the document in the database representing 
	 * this pokemon.
	 * 
	 * @param fieldName is the name of the document
	 *        field to retrieve.
	 * @return an arbitrary integer field from the
	 * 		   document specified by fieldName.
	 */
	public Integer getInteger(String fieldName) {
		return doc.getInteger(fieldName);
		// TODO: Test errors when fieldName is not present in document
	}
}