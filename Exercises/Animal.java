public abstract class Animal
{
	private int numLegs;
	public Animal(int numLegs)
	{
		this.numLegs = numLegs;
	}

	public String speak()
	{
		return "grrr " + _speakHelper() + " grrr";
	}

	abstract String _speakHelper();

	abstract void _move();
}

public class Dog extends Animal
{
	public Dog(int num)
	{
		super(num);
	}

	public String _speakHelper() { return "bark!";}
	public void move() { run();}
}