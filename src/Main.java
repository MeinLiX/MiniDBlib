import java.io.File;

public class Main {
    private static Source engine;
    private static final String PropExample;

    Main(String[] args) {
        engine = new Source();
    }

    public void Start_Program(String Prop, File ReadFile) throws Exception {
        engine.InitialProp(Prop);
        engine.ReadFileAndInitialDataSet(ReadFile);
    }

    static {
        engine = new Source();
        PropExample =
                """
                        Імя [A-zА-яіїєґ'`’-]{1,25}
                        По-Батькові [A-zА-яіїєґ'`’-]{1,29}
                        Прізвище [A-zА-яіїєґ'`-]{1,23}
                        Курс [1-4]
                        Код_Групи [1-9-]{1,6}
                        Аудиторія [1-9]+
                        Вид_Занять L|pr|S|lab
                        День_Тижня [1-5]
                        Пара [1-4]
                        Навчальний_тиждень [1-9]|1[0-5]
                        Предмет [ A-zА-яіїєґ1-9'`’-]{1,29}
                        """;
    }

    public static void main(String[] args) throws Exception {
        //new Main(args).Start_Program(PropExample,new File("InputData.txt"));

        File InputFile = new File("InputData.txt");

        engine.InitialProp(PropExample);
        //if (engine.IsCorrectFileByProp(InputFile)) // Перевірка на корректність файлу (Завдання 3) не потрібна, так як ReadFileAndInitialDataSet перевіряє корректність файлу, при його обробці.
        engine.ReadFileAndInitialDataSet(InputFile); //Викидає Exception у випадку не корректного файлу.

        //engine.PrintSequence(true);
        engine.PrintData(); //виводить всі данні зчитані з файлу.
        engine.RemoveItemsFromDataSet("Вид_Занять","pr"); //видаляє всі данні рядку, якщо поле "Name" містить в собі переданий "value"
        engine.saveData(new File("OutData.txt"));
    }
}
