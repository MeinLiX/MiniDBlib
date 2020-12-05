import java.io.File;

public class Main {
    private static Source engine;
    private static final String PropExample;
    private static String splitInRegexp;
    private static String splitOutRegexp;

    Main(String[] args) {
        engine = new Source();
    }

    public void Start_Program(String Prop, File ReadFile) throws Exception {
        engine.InitialProp(Prop);
        engine.readFileAndInitialDataSet(ReadFile);
    }

    static {
        engine = new Source();
        PropExample =
                """
                        Імя [A-zА-яІіЇїЄєҐґ'`’-]{1,25}
                        По-Батькові [A-zА-яІіЇїЄєҐґ'`’-]{1,29}
                        Прізвище [A-zА-яІіЇїЄєҐґ'`-]{1,23}
                        Курс [1-4]
                        Код_Групи [1-9-]{1,6}
                        Аудиторія [1-9]+
                        Вид_Занять L|pr|S|lab
                        День_Тижня [1-5]
                        Пара [1-4]
                        Навчальний_тиждень [1-9]|1[0-5]
                        Предмет [ A-zА-яІіЇїЄєҐґ1-9'`’-]{1,29}
                        """;
        splitInRegexp = "[,.;:|!?]+";
        splitOutRegexp = ";";
    }

    //([ ]+)?[A-zА-яІіЇїЄєҐґ'`’-]{1,25}([ ]+)?[,.;:|!?]([ ]+)?[A-zА-яІіЇїЄєҐґ'`’-]{1,29}([ ]+)?[,.;:|!?]([ ]+)?[A-zА-яІіЇїЄєҐґ'`-]{1,23}([ ]+)?[,.;:|!?]([ ]+)?[1-4]([ ]+)?[,.;:|!?]([ ]+)?[1-9-]{1,6}([ ]+)?[,.;:|!?]([ ]+)?[1-9]+([ ]+)?[,.;:|!?]([ ]+)?(?:L|pr|S|lab)([ ]+)?[,.;:|!?]([ ]+)?[1-5]([ ]+)?[,.;:|!?]([ ]+)?[1-4]([ ]+)?[,.;:|!?]([ ]+)?(?:[1-9]|1[0-5])([ ]+)?[,.;:|!?]([ ]+)?[ A-zА-яІіЇїЄєҐґ1-9'`’-]{1,29}

    public static void main(String[] args) throws Exception {
        //new Main(args).Start_Program(PropExample,new File("InputData.txt"));

        File InputFile = new File("InputData.txt");
        File OutFile = new File("OutData.txt");

        engine.InitialProp(PropExample); // задаємо поля, та їх перевірку
        engine.InitialDelimiters(splitInRegexp, splitOutRegexp); // задаємо деліметри вводу та виводу

        //if (engine.isCorrectFileByProp(InputFile)) // Перевірка на корректність файлу (не потрібна, так як readFileAndInitialDataSet перевіряє корректність файлу, при його обробці)
        engine.readFileAndInitialDataSet(InputFile); //Викидає Exception у випадку не корректного файлу

        //engine.printSequence(true); //при false виводить всі доступні поля для вводу\виводу (при true, виводить разом з РВ)
        engine.printData(); //виводить всі данні зчитані з файлу.

        engine.removeItemsFromDataSet("Вид_Занять", "pr"); //видаляє рядок, якщо поле "Name" з данного рядку містить в собі переданий ВР "value"

        engine.saveData(OutFile);
    }
}
