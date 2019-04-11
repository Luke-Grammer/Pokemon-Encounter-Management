
public class ResultPair implements Comparable<ResultPair>{
	
	public String name; 
	public Double modifier;
  
	public ResultPair(String x, Double y)
	{ 
		this.name = x; 
		this.modifier = y; 
	}

	public ResultPair(ResultPair resultPair)
	{
		this.name = resultPair.name;
		this.modifier = resultPair.modifier;
	}

	@Override
	public int compareTo(ResultPair o)
	{
		return modifier.compareTo(o.modifier);
	}
} 