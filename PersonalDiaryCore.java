import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PersonalDiaryCore {
    private static final TreeMap<LocalDate, List<String>> diary = new TreeMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        int choice;
        System.out.println("📓 Welcome to Your Personal Diary!");

        do {
            showMenu();
            choice = getIntInput();

            switch (choice) {
                case 1 -> addEntry();
                case 2 -> viewEntryByDate();
                case 3 -> viewAllEntries();
                case 4 -> deleteEntry();
                case 5 -> System.out.println("👋 Goodbye!");
                default -> System.out.println("❌ Invalid choice.");
            }
        } while (choice != 5);
    }

    private static void showMenu() {
        System.out.println("\n--- Diary Menu ---");
        System.out.println("1. Add Entry");
        System.out.println("2. View Entry by Date");
        System.out.println("3. View All Entries");
        System.out.println("4. Delete Entry");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addEntry() {
        LocalDate date = getDateInput("Enter date (yyyy-MM-dd): ");
        System.out.print("Write your diary entry: ");
        String content = scanner.nextLine();

        diary.computeIfAbsent(date, d -> new ArrayList<>()).add(content);
        System.out.println("✅ Entry added.");
    }

    private static void viewEntryByDate() {
        LocalDate date = getDateInput("Enter date to view (yyyy-MM-dd): ");

        List<String> entries = diary.get(date);
        if (entries != null && !entries.isEmpty()) {
            System.out.println("📅 Entries for " + date + ":");
            for (int i = 0; i < entries.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + entries.get(i));
            }
        } else {
            System.out.println("📭 No entries for this date.");
        }
    }

    private static void viewAllEntries() {
        if (diary.isEmpty()) {
            System.out.println("📭 Diary is empty.");
            return;
        }

        System.out.println("📖 All Diary Entries:");
        for (Map.Entry<LocalDate, List<String>> entry : diary.entrySet()) {
            System.out.println("🗓 " + entry.getKey() + ":");
            List<String> entries = entry.getValue();
            for (int i = 0; i < entries.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + entries.get(i));
            }
        }
    }

    private static void deleteEntry() {
        LocalDate date = getDateInput("Enter date to delete from (yyyy-MM-dd): ");
        List<String> entries = diary.get(date);

        if (entries == null || entries.isEmpty()) {
            System.out.println("❌ No entries to delete.");
            return;
        }

        System.out.println("🗑 Entries for " + date + ":");
        for (int i = 0; i < entries.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + entries.get(i));
        }

        System.out.print("Enter entry number to delete: ");
        int index = getIntInput();

        if (index < 1 || index > entries.size()) {
            System.out.println("❌ Invalid entry number.");
            return;
        }

        entries.remove(index - 1);
        if (entries.isEmpty()) {
            diary.remove(date);
        }

        System.out.println("✅ Entry deleted.");
    }

    // ---------------- Utility Methods ----------------

    private static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String dateStr = scanner.nextLine();
                return LocalDate.parse(dateStr, formatter);
            } catch (Exception e) {
                System.out.println("❗ Invalid date format. Try again.");
            }
        }
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("❗ Please enter a valid number.");
            scanner.nextLine();
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        return num;
    }
}



