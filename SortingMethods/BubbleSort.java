package SortingMethods;

import java.util.ArrayList;

public class BubbleSort {
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
    
    public static void swap(ArrayList<Student> students, int leftIndex, int rightIndex) {
        Student tempStudent = students.get(leftIndex);
        students.set(leftIndex, students.get(rightIndex));
        students.set(rightIndex, tempStudent);
    }
}
