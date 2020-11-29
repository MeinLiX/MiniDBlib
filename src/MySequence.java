import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

public class MySequence {
    private ArrayList<MyDataLex> dataTypes = new ArrayList<>();
    private int test = 5;

    public MySequence() {
    }

    public MySequence(MySequence Ex) {
        this(Ex, true);
    }

    public MySequence(MySequence Ex, boolean SaveValue) {
        if (!SaveValue)
            dataTypes = new ArrayList<>(Ex.getSequenceArr());
        else
            dataTypes = new ArrayList<>(Ex.getCloneData());
    }

    public boolean hasEmptyValue() {
        for (var i : dataTypes)
            if (!i.hasValue()) return true;

        return false;
    }

    public boolean swapSequenceType(String name, int idx) {
        int currPos = getSequenceTypePos(name);
        if (currPos == -1)
            return false;

        Collections.swap(dataTypes, currPos, idx);
        return true;
    }

    public void addSequenceType(String name, String token) {
        if (!dataTypes.contains(new MyDataLex(name, (String) null)))
            dataTypes.add(new MyDataLex(name, token));
    }

    public void addSequenceType(String name, Function<String, Boolean> _method) {
        if (!dataTypes.contains(new MyDataLex(name, (String) null)))
            dataTypes.add(new MyDataLex(name, _method));
    }

    public boolean setValue(String value) throws Exception {
        var idx = getSequenceLastTypeIndex();
        if (idx == -1)
            return false; //new Exception("Input file is not correct. All elements already initialized");

        return setValue(value, getSequenceLastTypeIndex());
    }

    public boolean setValue(String value, int idx) {
        return dataTypes.get(idx).setValue(value);
    }

    public boolean setValue(String value, int idx, boolean force) {
        if (force)
            dataTypes.get(idx).clearValue();

        return setValue(value, idx);
    }

    private int getSequenceLastTypeIndex() {
        for (int i = 0; i < dataTypes.size(); i++)
            if (!dataTypes.get(i).hasValue()) return i;

        return -1;
    }

    private int getSequenceTypePos(String name) {
        for (int i = 0; i < dataTypes.size(); i++)
            if (Objects.equals(dataTypes.get(i).getName(), name)) return i;

        return -1;
    }

    public int getSequenceSize() {
        return dataTypes.size();
    }

    //signature
    public ArrayList<MyDataLex> getSequenceArr() {
        ArrayList<MyDataLex> clone = new ArrayList<>(dataTypes.size());
        for (MyDataLex item : dataTypes)
            clone.add(new MyDataLex(item));
        for (MyDataLex i : clone)
            i.clearValue();
        return clone;
    }

    public ArrayList<MyDataLex> getCloneData() {
        ArrayList<MyDataLex> clone = new ArrayList<>(dataTypes.size());
        for (MyDataLex item : dataTypes)
            clone.add(new MyDataLex(item));
        return clone;
    }

    public String getValue(String name) {
        int idx = getSequenceTypePos(name);
        return (name != null && dataTypes != null && idx != -1) ? dataTypes.get(idx).getValue() : null;
    }

    public String getValueFormat(int idx, int lengthAVG) {
        StringBuilder res = new StringBuilder(String.format("%1$" + lengthAVG + "s", dataTypes.get(idx).toString()));
        while (res.toString().startsWith(" ")) {
            res.append(" ");
            res.delete(0, 1);
        }
        return res.toString();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (var i : dataTypes)
            res.append(i).append(" | ");

        return res.delete(res.length() - 2, res.length()).toString().trim();
    }

    public String toString(boolean debug) {
        if (!debug) return toString();
        StringBuilder res = new StringBuilder();
        for (var i : dataTypes)
            res.append(i.toString(debug)).append(" | ");

        return res.delete(res.length() - 2, res.length()).toString().trim();
    }
}
