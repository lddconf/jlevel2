public class DayOfWeekMain {

    public static void main(String[] args) {
        System.out.println(getWorkingHours(DayOfWeek.MONDAY));


        System.out.println(getWorkingHours(DayOfWeek.FRIDAY));

        System.out.println(getWorkingHours(DayOfWeek.SATURDAY));
    }

    static String getWorkingHours(DayOfWeek day) {
        int hoursToWeekend = 40 - day.ordinal() * 8;

        if ( hoursToWeekend <= 0 ) {
            return "Сегодня выходной";
        }

        return "До конца рабочей недели осталось " + hoursToWeekend + " рабочих часов";
    }
}