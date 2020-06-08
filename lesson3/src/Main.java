import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        //Task 1 test stuff
        String[] any_words = {
            "word1", "word2", "word3", "word4", "word5",
            "word1", "word1", "word2", "word3", "word4"
        };
        printStats(getWordStats(any_words));

        //------Task 2 test stuff
        PhoneBook book = new PhoneBook();
        book.add("Bob", "+74994550760");
        book.add("Bob", "+74994550761");
        book.add("Alice", "+74954550760");

        // Print phones for all users
        String[] users = book.getUserNames();
        for (String surname : users) {
            printPhones(surname, book.get(surname));
        }
    }

    /**
     * Print phones array
     * @param name  user name or surname
     * @param phones phones array
     */
    private static void printPhones(String name, String[] phones) {
        System.out.printf("%s has phones: ", name);
        for ( String phone: phones ) {
            System.out.print(" [ " + phone + " ] ");
        }
        System.out.println();
    }

    /**
     * Calulate word stat
     * @param words  words array
     * @return  words repetition statistic
     */
    static private HashMap<String, Integer> getWordStats(String[] words) {
        HashMap<String, Integer> histogram = new HashMap<>();

        for ( String word: words ) {
            histogram.put(word, histogram.getOrDefault(word, 0 ) + 1);
        }
        return histogram;
    }

    /**
     * Print word statistic
     * @param wordStat  words repetition statistic
     */
    static private void printStats( HashMap<String, Integer> wordStat ) {
        System.out.println("Word repetition statistics:");
        wordStat.forEach((s, integer) ->{
            System.out.printf("\"%s\" repetition meets %d times\n", s, integer);
        });
    }

}
