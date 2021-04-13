import java.util.ArrayList;

public class BubbleSort {
    private ArrayList<Integer> arrayList;
    public void swap(int index1, int index2){
        Integer temp = arrayList.get(index1);
        arrayList.set(index1, arrayList.get(index2));
        arrayList.set(index2,temp);
    }
}
