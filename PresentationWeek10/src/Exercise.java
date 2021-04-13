import java.util.List;

public class Exercise {
    /*Presentation exercise 5
Suppose that you are given an array of n different numbers that strictly increase from index 0 to index
m, and strictly decrease from index m to index n − 1, where n is known but m is unknown. Note that
there is a unique largest number in such a list, and it is at index m. Write a recursive method that
returns the index m of the largest number in the array, in time proportional to log n. Your method
should not use any additional data structure.

     */

    //The peak has characteristic of being greater than it's previous and it's next;

    //Steps :
    //1. Choose middle
    //2. FindPeak on both halves
    //3. Continue.

    public static int FindPeak(int[] list, int left, int right) {
        //Edge cases
            //Basic checks
            //If it's decreasing after the leftest element (i.e. element@left > elemenet@left+1)),
            //then the leftest element is the peak.
        if (list.length == 1) return list[0];
        if (list[left] > list[left + 1]) return left;

        //Same logic : if the rightest element is bigger than it's previous element, then it's the peak.

        if (list[right] > list[right - 1]) return right;
        //Choose middle
             //Take half to do it in log2 n time!
        int mid = (left + right)/2;

        //Base case
            //If the middle has the properties of a peak
        if (list[mid-1] < list[mid] && list[mid] > list[mid+1]) {
            return mid;
        }
        //Conditions for middle.
        //If previous term bigger, and next term bigger, then we're on the right half of the mountain.
        else if (list[mid - 1] > list[mid] && list[mid] > list[mid + 1]) {
            //We reduce list size by half, by taking mid as the right index. (i.e., search from left to mid)
           return FindPeak(list, left, mid);
        }
        //Same logic, if the adjacent terms of mid follow a increasing pattern, then we're on the left half of the mountain.
        else if (list[mid - 1] < list[mid] && list[mid] < list[mid + 1]) {
            return FindPeak(list, mid, right);
        } else return -1; //this will never happen, assuming correct input.

    }


}
