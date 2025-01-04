public abstract class BinaryExpression implements Expression{

    private Expression lft;
    private Expression rht;
    String operation;

    BinaryExpression(Expression lft, Expression rht, String operation){
        this.lft = lft;
        this.rht = rht;
        this.operation = operation;
    }

    abstract protected double _applyOperator(double lft, double rht);

    public double evaluate(final Bindings bindings)
    {
        return _applyOperator(lft.evaluate(bindings), rht.evaluate(bindings));
    }

    public String toString()
    {
        return "(" + lft + operation + rht + ")";
    }
}
