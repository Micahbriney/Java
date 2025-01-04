/*examples to work on day 2 of 203 - actual data on size of CSC frosh class with one year retention data */

public class CSCohort 
{
   private int year;
   private int enrolled;
   private double retainedPercent;

   public csCohort(int inYear, int inEnroll, double inPercent) {
      this.year = inYear;
      this.enrolled = inEnroll;
      this.retainedPercent = inPercent;
   }

   public int getYear() { return year; }
   public int getEnrolled() { return enrolled; }
   public double getPercent() { return retainedPercent; }
   public int retained() { return (int)(retainedPercent*enrolled); }
}

