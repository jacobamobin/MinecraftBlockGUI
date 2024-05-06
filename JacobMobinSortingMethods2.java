import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JacobMobinSortingMethods2 {
    public static void main(String[] args) {
        // Calling the method to read data from the file
        ArrayList<Student> students = readInData("dataStudents.txt");

        // use built-in forEach method to print all students
        System.out.println("Beginning List:");
        for (Student myStudent : students)
            System.out.println(myStudent);

        // Bubble sorted by last name
        bubbleSort(students);
        System.out.println("\nBubble sorted List: (Last Name)");
        students.forEach((s) -> System.out.println(s.lastName));

        // Insertion Sort by first name
        insertionSort(students);
        System.out.println("\nInsertion List: (First Name)");
        students.forEach((s) -> System.out.println(s.firstName));

        // Selection Sort by student number
        selectionSort(students);
        System.out.println("\nSelection List: (Student Number)");
        students.forEach((s) -> System.out.println(s.studentNum));

        mergeSort(students);
        System.out.println("\n Merge sorted by university");
        students.forEach((s) -> System.out.println(s.university));
    }

    // Method to read data from a file and return an ArrayList of Student objects
    public static ArrayList<Student> readInData(String fileName) {
        ArrayList<Student> students = new ArrayList<>(); // ArrayList to store Student objects

        try {
            // Creating a BufferedReader to read from the file
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            // Reading each line from the file until end of file
            while ((line = reader.readLine()) != null) {
                // Splitting the line into data using comma as delimiter
                String[] data = line.split(",");

                // Creating an array to store marks of a student
                double[] marks = new double[8];

                // Populating the marks array
                for (int i = 0; i < marks.length; i++) {
                    // Checking if the mark is not empty or null
                    if (!data[i + 3].isEmpty() && !data[i + 3].equals("null"))
                        marks[i] = Double.parseDouble(data[i + 3]);
                    else
                        marks[i] = 0.0;
                }

                Integer studentNum = Integer.valueOf(data[0]);

                // Creating a new Student object with the extracted data
                Student myStudent = new Student(studentNum, data[1], data[2], marks, data[11]);

                // Adding the Student object to the ArrayList
                students.add(myStudent);
            }

            // Closing the reader
            reader.close();

            // Returning the ArrayList of Student objects
            return students;
        } catch (IOException iox) {
            // Handling IO exception if file reading fails
            System.out.println("Problem Reading " + fileName);
        }

        // Returning null if an exception occurs
        return null;
    }

    /*
     * Method: Bubble sort Sorts students alphabetically by last name
     */
    public static void bubbleSort(ArrayList<Student> myStudents) {
        for (int m = 0; m < myStudents.size(); m++) {
            for (int i = 0; i < myStudents.size() - 1; i++) {

                String left = myStudents.get(i).lastName;
                String right = myStudents.get(i + 1).lastName;

                if (left.compareTo(right) > 0)
                    swap(myStudents, i, i + 1);

            }
        }
    }

    /*
     * Method: swap Swap two student objects in an ArrayList
     */
    public static void swap(ArrayList<Student> students, int leftIndex, int rightIndex) {
        Student tempStudent = students.get(leftIndex);
        students.set(leftIndex, students.get(rightIndex));
        students.set(rightIndex, tempStudent);
    }

    /*
     * Method: Insertion Sort Sort the students alphabetically by first name
     */
    public static void insertionSort(ArrayList<Student> myStudents)
    {
        // cycle through each student starting at the second student and ending at the last student 
        for (int i = 1; i < myStudents.size(); i++)
        {

            // get the index of the sudent directly to the left of student 1
            int x = i-1;
            // store the value of student 1 to a variable
            Student sortObj = myStudents.get(i);
            String name1 = sortObj.firstName;

            // go through each student until you find a student that comes before student 1
            // shift everything down 1 each time
            while (x >= 0 && name1.compareTo(myStudents.get(x).firstName)<0)
            {
                myStudents.set(x+1, myStudents.get(x));
                x--; 
            }

            // set the value of student 1 to the index below the student that comes before student 1
            myStudents.set(x+1, sortObj);
        }
    }

    /*
     * Method: Selection Sort 
     * Task: Sort the students by student number
     */
    public static void selectionSort(ArrayList<Student> myStudents)
    {
        // cycle through every student until the second last student
        for (int i = 0; i < myStudents.size()-1; i++)
        {
            int swapIndex = i; 
            // go through every student from the first to last student 
            for (int x = i + 1; x < myStudents.size(); x++ )
            {
                int num1 = myStudents.get(i).studentNum;
                int num2 = myStudents.get(x).studentNum;

                // if the first student num is greater than the second one then don't switch them 
                // if the first student num is smaller than the second one and the second student num is less than the previous one then swap them
                if (num1 > num2 && num2 < myStudents.get(swapIndex).studentNum) // works bc if swapIndex remains i then its just the same thing twice 
                    swapIndex = x; 
            }

            if(!(swapIndex == i))
                swap(myStudents, i,swapIndex);
        }
    }

    /*z
     * Method: mergeSort
     * Task: sort the students alphabeticaly by universit
     */

    public static void mergeSort(ArrayList<Student> myStudents) {
        ArrayList<Student> first = new ArrayList<Student>();
         ArrayList<Student> second = new ArrayList<Student>();
      
        for(int i = 0 ; i < myStudents.size() ; i++)
            if(i < myStudents.size()/2)
                first.add(myStudents.get(i));
            else
                second.add(myStudents.get(i));
      
        if(first.size() > 1)
            mergeSort(first);
        if(second.size() > 1)
            mergeSort(second);
      
      
      int index1 = 0, index2 = 0;
      for(int i = 0 ; i < first.size()+second.size() && index1 < first.size() && index2 < second.size() ; i++)
      {
         String listOneStudentUni = first.get(index1).university;
         String listTwoStudentUni = second.get(index2).university;
         
         if(listOneStudentUni.compareTo(listTwoStudentUni) < 0) {
            myStudents.set(i, first.get(index1));
            myStudents.set(i+1, second.get(index2));
            index1++;     
        } else {
            myStudents.set(i+1, first.get(index1));
            myStudents.set(i, second.get(index2));
            index2++;
        }
      }
     
    }
}

class Student {
    // Fields
    int studentNum;
    String firstName;
    String lastName;
    double[] marks = new double[8];
    String university;

    public Student(int studentNum, String fn, String ln, double[] marks, String u) {
        this.studentNum = studentNum;
        firstName = fn;
        lastName = ln;
        this.marks = marks;
        university = u;
    }

    public double average() {
        double sum = 0;
        int ctr = 0;
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] > 0.0) {
                sum += marks[i];
                ctr++;
            }
        }
        return sum / ctr;
    }

    public String toString() {
        return studentNum + " " + firstName + " " + lastName + " " + marks[0] + " " + marks[1] + " " + marks[2] + " "
                + marks[3] + " " + marks[4] + " " + marks[5] + " " + marks[6] + " " + marks[7] + " " + university;
    }
}
