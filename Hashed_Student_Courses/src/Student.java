import java.util.List;
import java.util.Objects;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }

   public boolean equals(Object o){
      if (o == null)
         return false;
      if (o.getClass() != this.getClass())
         return false;

      Student s = (Student)o;

      return Objects.equals(s.surname, this.surname) &&
              Objects.equals(s.givenName, this.givenName) &&
              Objects.equals(s.currentCourses, this.currentCourses) &&
              s.age == this.age;
   }

   public int hashCode(){
      return Objects.hash(surname, givenName, age, currentCourses);
   }
}
