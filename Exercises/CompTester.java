import java.util.*;

public class CompTester
{
   public static void main(String[] args)
   {
       ArrayList<Student> studentList = new ArrayList<>();
       Student[] studentArray = new Student[5];
      
       studentList.add(new Student("Smidt", "Bob", 26, 2.67));
       studentList.add(new Student("Workman", "Julie", 21, 3.84));
       studentList.add(new Student("Johnson", "Jane", 18, 3.59));
       studentList.add(new Student("Hatalsky", "Paul", 22, 3.99));
       studentList.add(new Student("Wood", "Zoe", 21, 3.62));

       printStudents(studentList);

       //old way with instantiating object of a class that implements comparator
       Comparator<Student> comp1 = new StudentAgeComparator();
       Collections.sort(studentList, comp1);
       printStudents(studentList);


       //use lambda
       Comparator<Student> comp2 = (Student s1, Student s2) -> {return s1.age()-s2.age();} ;
       Collections.sort(studentList,  comp2);











        //don't need parameter type if implied
       Collections.sort(studentList,  (s1, s2) -> s1.age()-s2.age());
       printStudents(studentList);

       //thenComparing with old way AND lambda
        //compare by age then gpa
       Comparator<Student> compA = new StudentAgeComparator();
       Comparator<Student> compC = compA.thenComparing(
                                       (s1, s2)->
                                          s1.lastName().compareTo(s2.lastName())
                                    );

       Collections.sort(studentList, compC);

       printStudents(studentList); 


       //key extractions - if the lambda you were passing in just takes in an
       // object of certain class and calls a method...
       Comparator<Student> comp3 = Comparator.comparing(s->s.age());
       //Comparator<Student> comp3 = Comparator.comparing(Student::age);
       System.out.println("Comp class?? " + comp3.getClass());
       Collections.sort(studentList, comp3);

       printStudents(studentList); 
 
       // thenComparing with old way and key extractor
      /* Comparator<Student> compA = new StudentAgeComparator();
       Comparator<Student> compB = compA.thenComparing(Student::lastName);

       Collections.sort(studentList, compB);

       printStudents(studentList); */


   } 




















   private static void printStudents(List<Student> students)
   {
       System.out.println("\nStudent List:");
       for (Student s : students)
           System.out.println("   " + s);  
   }
}
