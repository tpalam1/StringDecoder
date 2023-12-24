import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> arr = parseInt("./src/dictionary.txt");
        Collections.sort(arr);

        System.out.println("Sorted, parsed integers:\t" + arr);

        ArrayList<ArrayList<Integer>> staircaseArr = toStaircase(arr);
        System.out.println("As a staircase:\t" + staircaseArr);

        ArrayList<Integer> lastElems = getLastElementsAsArr(staircaseArr);
        System.out.println("Only the last elements:\t" + lastElems);
    }

    /**
     * Transforms the given ArrayList as a series of successive Arrays of increasing size.
     * @param arr the ArrayList to parse
     * @return the ArrayList, represented as a series of increasing arrays
     */
    public static ArrayList<ArrayList<Integer>> toStaircase(ArrayList<Integer> arr){
        Iterator<Integer> iter = arr.iterator();

        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        output.add(new ArrayList<>());

        /* Denotes the maximum length of the current stairstep. */
        int maxLengthCurrStep = 1;

        while(iter.hasNext()){
            ArrayList<Integer> currArr = getLastElement(output);
            int size = currArr.size();
            if(size == maxLengthCurrStep){
                output.add(new ArrayList<>());
                maxLengthCurrStep++;
            }

            getLastElement(output).add(iter.next());
        }

        return output;
    }

    /**
     * Returns only the last elements of each subarray of the given ArrayList.
     *
     * @param arr the ArrayList to parse
     * @param <T> the data type of the ArrayList.
     * @return a list of the last elements of each subarray
     */
    public static <T> ArrayList<T> getLastElementsAsArr(ArrayList<ArrayList<T>> arr){
        ArrayList<T> output = new ArrayList<>();
        for (ArrayList<T> currArr : arr) {
            T lastElem = getLastElement(currArr);
            output.add(lastElem);
        }
        return output;
    }

    /**
     * Returns the last element of the given ArrayList.
     * @param arr the ArrayList to parse
     * @return the last element of the ArrayList
     * @param <T> the data type of the ArrayList
     */
    public static <T> T getLastElement(ArrayList<T> arr){
        return arr.get(arr.size() - 1);
    }

    /**
     * Scans the given filepath for integers and returns them as an array.
     */
    public static ArrayList<Integer> parseInt(String filepath){
        ArrayList<Integer> output = new ArrayList<>();

        try {
            File f = new File(filepath);
            Scanner s = new Scanner(f);

            while (s.hasNext()){
                String curr = s.next();

                if(!containsInt(curr)){ continue; }

                int val = Integer.parseInt(curr);
                output.add(val);
            }

            return output;
        } catch(FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace(System.out);
            System.exit(-1);
        }

        return output;
    }

    /**
     * Checks whether the given String has an integer.
     * @param s the String to parse
     * @return whether it contains an integer
     */
    public static boolean containsInt(String s){
        for(int i = 0; i < s.length(); i++){
            char curr = s.charAt(i);

            if('0' <= curr && curr <= '9'){
                return true;
            }
        }
        return false;
    }
}