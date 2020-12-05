import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

class MyDataLex {
    private final String name;
    private String value;
    private final String token; //regexp
    private final Function<String, Boolean> method;

    public MyDataLex(MyDataLex MDL) {
        name = MDL.name;
        value = MDL.value;
        token = MDL.token;
        method = MDL.method;
    }

    MyDataLex(String _name, String pattern) {
        this(_name, null, pattern, null);
    }

    MyDataLex(String _name, String pattern, String _value) {
        this(_name, null, pattern, _value);
    }

    MyDataLex(String _name, Function<String, Boolean> _method) {
        this(_name, _method, null, null);
    }

    MyDataLex(String _name, Function<String, Boolean> _method, String _value) {
        this(_name, _method, null, _value);
    }

    MyDataLex(String _name, Function<String, Boolean> _method, String pattern, String _value) {
        name = _name != null ? new String(_name) : null;
        token = pattern != null ? new String(pattern) : null;
        method = _method;
        setValue(_value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void clearValue() {
        value = null;
    }

    public boolean hasValue() {
        return value != null;
    }

    public boolean setValue(String _value) {
        if (value != null || _value == null)
            return false;
        if (token != null && Pattern.matches(token, _value)) {
            value = new String(_value);
            return true;
        } else if (method != null && method.apply(_value)) {
            value = new String(_value);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyDataLex myDataLex = (MyDataLex) o;
        return name.equals(myDataLex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return getValue();
    }

    public String toString(boolean debug) {
        if (!debug)
            return getName();

        return getName() + "{" + token + "}";
    }
}