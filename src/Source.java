import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Source {
    private static final ArrayList<MySequence> dataSet = new ArrayList<>();
    private static final String splitRegexp = "[,.;:|!?]+";
    private static MySequence prop = new MySequence();

    private void InitialProp(File file) throws Exception {
        //todo in futures
    }

    public void InitialProp(String str) throws Exception {
        Scanner scanner = new Scanner(str);
        prop = new MySequence();

        while (scanner.hasNextLine()) {
            prop.addSequenceType(scanner.next(), scanner.nextLine().substring(1));
        }
        scanner.close();
    }

    public boolean IsCorrectFileByProp(File file) throws Exception { //TODO need?
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            MySequence tempData = new MySequence(prop,false);
            for (String word : scanner.nextLine().split(splitRegexp)) {
                if (Objects.equals(word, "")) continue;
                if (word.startsWith("//")) break;

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

    public void PrintSequence() {
        PrintSequence(false);
    }

    public void PrintSequence(boolean WithRegexp) {
        System.out.println(prop.toString(WithRegexp));
    }

    private int GetAVG(int idx) {
        int avg = 0;
        for (MySequence word : dataSet) {
            int length = word.getValueFormat(idx, 1).length();
            if (length > avg) avg = length;
        }
        return avg;
    }

    public void PrintData() {
        for (MySequence mySequence : dataSet) {
            for (int j = 0; j < prop.getSequenceSize(); j++) {
                String toPrint = mySequence.getValueFormat(j, GetAVG(j));
                if (j + 1 < prop.getSequenceSize())
                    toPrint = toPrint + "|";

                System.out.print(toPrint);
            }


            System.out.println("\n");
        }
    }

    public void ReadFileAndInitialDataSet(File file) throws Exception {
        if (prop == null || prop.getSequenceArr().size() < 1)
            throw new Exception("Please invoke InitialProp.");

        Scanner scanner = new Scanner(file);
        dataSet.clear();
        while (scanner.hasNextLine()) {
            MySequence tempData = new MySequence(prop, false);
            boolean comment = false;
            for (String word : scanner.nextLine().split(splitRegexp)) {
                if (Objects.equals(word, "")) continue;
                if (word.startsWith("//")) {
                    comment = true;
                    break;
                }
                word = word.trim();
                if (!tempData.setValue(word))
                    throw new Exception("Input file is not correct! Word:" + word);
            }
            if (!comment && tempData.hasEmptyValue())
                throw new Exception("Input file is not correct. Once or more elements has empty value!");
            else if (!comment)
                dataSet.add(new MySequence(tempData));

        }
        scanner.close();
    }

    public int RemoveItemsFromDataSet(String Name, String value) {
        int count = 0;
        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get(i).getValue(Name).equals(value)) {
                count++;
                dataSet.remove(i);
            }

        }
        return count;
    }
}