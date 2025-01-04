public interface Animal
{
	public String speak();
}

public class Dog implements Animal
{
	
	public String speak()
	{
		return "bark!";
	}
}

public class Bear implements Animal
{
	
	public String speak()
	{
		return "growl";
	}
}