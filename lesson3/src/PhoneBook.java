import java.util.HashMap;
import java.util.HashSet;

/**
 * Simple phonebook realization
 */
public class PhoneBook {
    private HashMap<String, HashSet<String>> users;

    /**
     * Default constructor
     */
    public PhoneBook() {
        users = new HashMap<>();
    }

    /**
     * Add new record to phone book
     * @param secondName user's second name
     * @param phone      users's phone number
     * @return  {@code true} if new record was added to phonebook
     */
    public boolean add(String secondName, String phone) {
        boolean result;
        HashSet<String> phones = users.getOrDefault(secondName, new HashSet<>());
        result = phones.add( phone );

        // If new record derrived
        if ( phones.size() == 1 ) {
            users.put(secondName, phones);
        }
        return result;
    }

    /**
     * Get user phones by second name
     * @param secondName  user's second name
     * @return user's phones
     */
    public String[] get(String secondName) {
        HashSet<String> phones = users.get(secondName);
        if ( phones == null ) {
            return null;
        }
        String[] array = new String[phones.size()];
        phones.toArray(array);
        return array;
    }

    /**
     * Get total records count in phonebook
     * @return records count
     */
    public int getRecordsCount() {
        return users.size();
    }

    /**
     * Get all user names from phonebook
     * @return user names
     */
    public String[] getUserNames() {
        String[] users = new String[this.users.keySet().size()];
        this.users.keySet().toArray(users);
        return users;
    }
}
