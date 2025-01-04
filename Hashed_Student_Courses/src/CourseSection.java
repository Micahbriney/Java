import java.time.LocalTime;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   public boolean equals(Object o){
      if (o == null)
         return false;
      if (o.getClass() != this.getClass())
         return false;

      CourseSection cs = (CourseSection)o;
      boolean result;

      if (prefix == null)
         result = cs.prefix == null;
      else
         result = prefix.equals(cs.prefix);

      if (number == null)
         result = result && cs.number == null;
      else
         result = result && number.equals(cs.number);

      if (startTime == null)
         result = result && cs.startTime == null;
      else
         result = result && startTime.equals(cs.startTime);

      if (endTime == null)
         result = result && cs.endTime == null;
      else
         result = result && endTime.equals(cs.endTime);

      return result && cs.enrollment == this.enrollment;

   }

   public int hashCode(){
      int hash = 1;
      int primer = 31;

      hash = hash * primer + ((prefix == null) ? 0 : prefix.hashCode());
      hash = hash * primer + ((number == null) ? 0 : number.hashCode());
      hash = hash * primer + ((Integer)enrollment).hashCode();
      hash = hash * primer + ((startTime == null) ? 0 : startTime.hashCode());
      hash = hash * primer + ((endTime == null) ? 0 : endTime.hashCode());

      return hash;
   }
}
