package pokemonDB;
/**
 * Represents pokemon type information and 
 * attack/defense type modifiers 
 * 
 * @author luke grammer
 */
public class PokemonTypeInfo
{
	public static final int NUM_TYPES = 18;

	public static enum TYPES { NORMAL, FIGHTING, FLYING, 
							   POISON, GROUND, ROCK, BUG, 
							   GHOST, STEEL, FIRE, WATER, 
							   GRASS, ELECTRIC, PSYCHIC, 
							   ICE, DRAGON, DARK, FAIRY; }

	/**
	 * Two-dimensional matrix representing the attack modifier of 
	 * each pokemon type against every other.  
	 */
	public static final Double[][] typeInfo = new Double[][] {
	//   NRM  FGT  FLY  PSN  GRD  RCK  BUG  GST  STL  FRE  WTR  GRS  ELC  PSY  ICE  DRG  DRK  FRY
		{1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 0.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, // NORMAL
		{2.0, 1.0, 0.5, 0.5, 1.0, 2.0, 0.5, 0.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 2.0, 0.5}, // FIGHTING
		{1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0}, // FLYING
		{1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 0.5, 0.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0}, // POISON
		{1.0, 1.0, 0.0, 2.0, 1.0, 2.0, 0.5, 1.0, 2.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0}, // GROUND
		{1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0}, // ROCK
		{1.0, 0.5, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 2.0, 0.5}, // BUG
		{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 1.0}, // GHOST
		{1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 0.5, 1.0, 2.0, 1.0, 1.0, 2.0}, // STEEL
		{1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 2.0, 0.5, 0.5, 2.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0}, // FIRE
		{1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0}, // WATER
		{1.0, 1.0, 0.5, 0.5, 2.0, 2.0, 0.5, 1.0, 0.5, 0.5, 2.0, 0.5, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0}, // GRASS
		{1.0, 1.0, 2.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 0.5, 1.0, 1.0}, // ELECTRIC
		{1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 0.0, 1.0}, // PSYCHIC
		{1.0, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 2.0, 1.0, 1.0, 0.5, 2.0, 1.0, 1.0}, // ICE
		{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.0}, // DRAGON
		{1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5}, // DARK
		{1.0, 2.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0}, // FAIRY
	};
	
	/**
	 * Returns the attack modifier of attacker pokemon
	 * type against the defender pokemon type as a Double.
	 * 
	 * @param attackerType is the attacking pokemon's type as a String
	 * @param defenderType is the defending pokemon's type as a String
	 * @return the attack modifier of attacker vs defender
	 * @throws IllegalArgumentException when the attacker or defender type is unrecognized
	 */
	public static Double getAttackModifier(String attackerType, String defenderType) throws IllegalArgumentException
	{
		try 
		{
			return getAttackModifier(attackerType, defenderType, null);
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}
	
	/**
	 * Returns the attack modifier of attacker pokemon
	 * type against the defender pokemon type as a Double.
	 * 
	 * @param attacker is the attacking pokemon's type as a String
	 * @param defenderType1 is the defending pokemon's primary type as a String
	 * @param defenderType2 is the defensing pokemon's secondary type as a String
	 * @return the attack modifier of attacker vs defender
	 * @throws IllegalArgumentException when the attacker or defender type is unrecognized
	 */
	public static Double getAttackModifier(String attackerType, String defenderType1, String defenderType2) throws IllegalArgumentException
	{
		Double d;
		try 
		{
			if (defenderType2 != null)
			{
				d = typeInfo[TYPES.valueOf(attackerType.toUpperCase().trim()).ordinal()]
							[TYPES.valueOf(defenderType1.toUpperCase().trim()).ordinal()]
				  * typeInfo[TYPES.valueOf(attackerType.toUpperCase().trim()).ordinal()]
						  	[TYPES.valueOf(defenderType2.toUpperCase().trim()).ordinal()];
			}
			else
			{
				d = typeInfo[TYPES.valueOf(attackerType.toUpperCase().trim()).ordinal()]
					    [TYPES.valueOf(defenderType1.toUpperCase().trim()).ordinal()];				
			}
			return d;
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}
}
