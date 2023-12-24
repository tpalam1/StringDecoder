import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

        Dictionary<Integer, String> translationMap = parseDictionary("./src/dictionary.txt");
        System.out.println("Dictionary lookup:\t" + translationMap);

        String translatedString = translate(lastElems, translationMap);
        System.out.println("Translated String:\t" + translatedString);

        printToOutput(translatedString, "output.txt");
    }

    /**
     * Prints the contents of the given String to a text file.
     * @param s the String to append into the output file
     * @param outputFilename the filename to give to the output file
     */
    public static void printToOutput(String s, String outputFilename){
        String outputNameCorrected = "./out/" + outputFilename;
        File f = new File(outputNameCorrected);
        try {
            FileWriter fw = new FileWriter(f);
            fw.append(s);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Uses a Dictionary to convert the set of Integer keys into a set of Strings.
     * @param keys the list of Integers to convert from
     * @param dict the Integer-String pairings of the cipher
     * @return a String representation of the translated ArrayList
     */
    public static String translate(ArrayList<Integer> keys, Dictionary<Integer, String> dict){
        if(keys.isEmpty() || dict.isEmpty()){
            throw new RuntimeException("One or more of the inputs are empty!");
        }

        StringBuilder sb = new StringBuilder();

        Iterator<Integer> iter = keys.iterator();

        while(iter.hasNext()) {
            int currInt = iter.next();
            String currString = dict.get(currInt);

            if(currString == null){
                throw new RuntimeException("One of the given Integers does not have a dictionary value. Exiting now.");
            }

            sb.append(currString);

            if(iter.hasNext()) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    /**
     * Transforms the given ArrayList as a series of successive Arrays of increasing size.
     * @param arr the ArrayList to parse
     * @return the ArrayList, represented as a series of increasing arrays
     */
    public static ArrayList<ArrayList<Integer>> toStaircase(ArrayList<Integer> arr){
        if(arr.isEmpty()){
            throw new RuntimeException("The input array is empty!");
        }

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
        if(arr.isEmpty()){
            throw new RuntimeException("The input array is empty!");
        }

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
        if(arr.isEmpty()){
            throw new RuntimeException("The input array is empty!");
        }
        return arr.get(arr.size() - 1);
    }

    /**
     * Scans the given filepath for Integer-String pairs and returns them as a Dictionary.
     * @param filepath the filepath to evaluate
     * @return a dictionary containing the Integer-String pairs.
     */
    public static Dictionary<Integer, String> parseDictionary(String filepath){
        Dictionary<Integer, String> output = new Hashtable<>();

        try{
            File f = new File(filepath);
            Scanner s = new Scanner(f);

            while(s.hasNext()){
                int currVal = s.nextInt();
                String currString = s.next();

                output.put(currVal, currString);
            }
        } catch(FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace(System.out);
            System.exit(-1);
        }

        return output;
    }

    /**
     * Scans the given filepath for integers and returns them as an array.
     */
    public static ArrayList<Integer> parseInt(String filepath){
        ArrayList<Integer> output = new ArrayList<>();

        try {
            /* Open the file for reading. */
            File f = new File(filepath);
            Scanner s = new Scanner(f);

            /* As long as there are still tokens in the file,
            * check if they are of the correct data type,
            * and append them to the output.
            **/
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
        if(s.isEmpty()){
            throw new RuntimeException("The given string is empty!");
        }

        for(int i = 0; i < s.length(); i++){
            char curr = s.charAt(i);

            if('0' <= curr && curr <= '9'){
                return true;
            }
        }
        return false;
    }
}