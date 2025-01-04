class AddExpression extends BinaryExpression
{

   public AddExpression(Expression lft, Expression rht)
   {
      super(lft, rht, " + ");
   }

   @Override
   protected double _applyOperator(double lft, double rht) {
      return lft + rht;
   }
}
