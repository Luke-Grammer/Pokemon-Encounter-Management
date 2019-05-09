package pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import pokemon.model.Pokemon;

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
	
	public static Double getAttackModifier(Pokemon attacker, Pokemon defender, boolean primary)
	{
		String attackerType = (primary) ? attacker.getPrimaryType() : attacker.getSecondaryType();
		
		String defenderPrimaryType = defender.getPrimaryType();
		String defenderSecondaryType = defender.getSecondaryType();
		
		return PokemonTypeInfo.getAttackModifier(attackerType, defenderPrimaryType, defenderSecondaryType);
	}
	
	/**
	 * Returns the attack modifier of attacker pokemon
	 * type against the defender pokemon type as a Double.
	 * 
	 * @param attacker is the attacking pokemon's type as a String
	 * @param defenderType1 is the defending pokemon's primary type as a String
	 * @param defenderType2 is the defensing pokemon's secondary type as a String
	 * @return the attack modifier of attacker vs defender
	 * @throws IllegalArgumentException when the attacker or defender type is non-null but unrecognized
	 */
	public static Double getAttackModifier(String attackerType, String defenderType1, String defenderType2) throws IllegalArgumentException
	{
		Double d;
		try 
		{
			if (attackerType != null)
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
			}
			else
				return null;

			return d;
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}
	
	// TODO: Doc comment
	public static Collection<Pair<Pokemon, Double>> rankAttackModifiers(Collection<Pokemon> attackers, Pokemon defender, boolean primary)
	{
		ArrayList<Pair<Pokemon, Double>> results = new ArrayList<Pair<Pokemon, Double>>();
		for(Pokemon attacker : attackers)
		{
			Double d = getAttackModifier(attacker, defender, primary);
			if (d != null)
			{
				results.add(new Pair<Pokemon, Double>(attacker, d));
			}
		}
		
		Collections.sort(results, Collections.reverseOrder());
		return results;
	}
	
	// TODO: Doc comment
	public static Collection<Pair<Pokemon, Double>> rankDefenseModifiers(Pokemon attacker, Collection<Pokemon> defenders, boolean primary)
	{
		ArrayList<Pair<Pokemon, Double>> results = new ArrayList<Pair<Pokemon, Double>>();
		for(Pokemon defender : defenders)
		{
			Double d = getAttackModifier(attacker, defender, primary);
			if (d != null)
			{
				results.add(new Pair<Pokemon, Double>(defender, getAttackModifier(attacker, defender, primary)));
			}
		}
		
		Collections.sort(results);
		return results;
	}
}
