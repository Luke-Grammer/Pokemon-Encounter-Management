package pokemon.model;

import java.io.Serializable;

/**
 * Represents a pokemon from a MongoDB collection as a POJO.
 * 
 * @author luke grammer
 */
public class Pokemon implements Serializable {

	private static final long serialVersionUID = 3338124225388353508L;
	
	private Integer number;
    private String name;
    private String name_jap;
    private String desc;
    private String type1;
    private String type2 = null;

    public Pokemon(String line) {
        String[] split = line.split(",");
        
        number = Integer.parseInt(split[0]);
        name = split[1];
        name_jap = split[2];
        desc = split[3];
        type1 = split[4];
        type2 = (split.length == 5) ? null : split[5];
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
    public Pokemon(Integer number, String name, String name_jap, String desc, String type1, String type2) {
    	assert number > 0 : "Invalid pokedex number";
    	
        this.number = number;
        this.name = name.toLowerCase().trim();
        this.name_jap = name_jap;
        this.desc = desc;
        this.type1 = type1.toLowerCase().trim();
        if (type2 != null && type2 != "")
        {
        	this.type2 = type2.toLowerCase().trim();
        }
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
	
	public String getNameJap()
	{
		return name_jap;
	}

	public String getDesc()
	{
		return desc;
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
	
	public String toString()
	{
		return number + "," + name + "," + name_jap + "," + desc + "," + type1 + "," + ((type2 == null) ? "" : type2);
	}
}