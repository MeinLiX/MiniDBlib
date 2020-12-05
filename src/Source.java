import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Source {
    private static final ArrayList<MySequence> dataSet;
    private static String splitInRegexp;
    private static String splitOutRegexp;
    private static MySequence prop;

    static { //default value
        dataSet = new ArrayList<>();
        splitInRegexp = "[,.;:|!?]+";
        splitOutRegexp = ";";
        prop = new MySequence();
    }

    private boolean CheckProperties() {
        return splitInRegexp == null || splitOutRegexp == null || prop == null || prop.getSequenceArr().size() < 1;
    }

    public void InitialDelimiters(String in, String out) {
        splitInRegexp = in;
        splitOutRegexp = out;
    }

    public void InitialProp(String str) throws Exception {
        if (str == null)
            return;

        Scanner scanner = new Scanner(str);
        prop = new MySequence();

        while (scanner.hasNextLine())
            prop.addSequenceType(scanner.next(), scanner.nextLine().substring(1));

        scanner.close();
    }

    public boolean isCorrectFileByProp(File file) throws Exception { //USELESS
        if (CheckProperties())
            throw new Exception("Please invoke InitialProp or(and) InitialDelimiters.");

        Scanner scanner = new Scanner(file);

        comment:
        while (scanner.hasNextLine()) {
            MySequence tempData = new MySequence(prop, false);
            for (String word : scanner.nextLine().split(splitInRegexp)) {
                if (Objects.equals(word, ""))
                    continue;

                if (word.startsWith("//"))
                    continue comment;

                word = word.trim();
                if (!tempData.setValue(word))
                    return false;
            }
            if (tempData.hasEmptyValue())
                return false;
        }
        scanner.close();
        return true;
    }

    public void printSequence() throws Exception {
        printSequence(false);
    }

    public void printSequence(boolean WithRegexp) throws Exception {
        if (CheckProperties())
            throw new Exception("Please invoke InitialProp or(and) InitialDelimiters or(and) readFileAndInitialDataSet.");

        System.out.println(prop.toString(WithRegexp));
    }

    private int getAVG(int idx) {
        int avg = 0;
        for (MySequence word : dataSet) {
            int length = word.getValueFormat(idx, 1).length();
            if (length > avg) avg = length;
        }
        return avg;
    }

    public void saveData(File file) throws Exception {
        if (CheckProperties())
            throw new Exception("Please invoke InitialProp or(and) InitialDelimiters or(and) readFileAndInitialDataSet.");

        FileWriter FWrt = new FileWriter(file);
        for (MySequence mySequence : dataSet) {
            for (int j = 0; j < prop.getSequenceSize(); j++) {
                String toPrint = mySequence.getValueFormat(j, getAVG(j));
                if (j + 1 < prop.getSequenceSize())
                    toPrint = toPrint + splitOutRegexp;

                FWrt.write(toPrint);
            }
            FWrt.write("\n");
        }
        FWrt.close();
    }

    public void printData() throws Exception {
        if (CheckProperties())
            throw new Exception("Please invoke InitialProp or(and) readFileAndInitialDataSet");

        for (MySequence mySequence : dataSet) {
            for (int j = 0; j < prop.getSequenceSize(); j++) {
                String toPrint = mySequence.getValueFormat(j, getAVG(j));
                if (j + 1 < prop.getSequenceSize())
                    toPrint = toPrint + splitOutRegexp;

                System.out.print(toPrint);
            }
            System.out.println("\n");
        }
    }

    public void readFileAndInitialDataSet(File file) throws Exception {
        if (CheckProperties())
            throw new Exception("Please invoke InitialProp or(and) InitialDelimiters.");

        Scanner scanner = new Scanner(file);
        dataSet.clear();

        comment:
        while (scanner.hasNextLine()) {
            MySequence tempData = new MySequence(prop, false);
            for (String word : scanner.nextLine().split(splitInRegexp)) {
                if (Objects.equals(word, ""))
                    continue;

                if (word.startsWith("//"))
                    continue comment;

                word = word.trim();
                if (!tempData.setValue(word))
                    throw new Exception("Input file is not correct! Word:" + word);
            }
            if (tempData.hasEmptyValue())
                throw new Exception("Input file is not correct. Once or more elements has empty value!");

            dataSet.add(new MySequence(tempData));
        }
        scanner.close();
    }

    public int removeItemsFromDataSet(String Name, String value) throws Exception {
        if (CheckProperties())
            throw new Exception("Please invoke InitialProp or(and) InitialDelimiters or(and) readFileAndInitialDataSet.");

        int count = 0;
        for (int i = 0; i < dataSet.size(); i++)
            if (Pattern.matches(dataSet.get(i).getValue(Name), value)) {
                count++;
                dataSet.remove(i);
            }

        return count; // Count of deleted lines
    }
}