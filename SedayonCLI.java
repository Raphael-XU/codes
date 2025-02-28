import java.util.*;

class Film {
    String name;
    String category;
    int duration;

    public Film(String name, String category, int duration) {
        this.name = name;
        this.category = category;
        this.duration = duration;
    }
}

class Viewer {
    String username;
    List<Film> viewingHistory;
    Map<String, Integer> categoryWatchTime;

    public Viewer(String username) {
        this.username = username;
        this.viewingHistory = new ArrayList<>();
        this.categoryWatchTime = new HashMap<>();
    }

    public void watchFilm(Film film) {
        viewingHistory.add(film);
        categoryWatchTime.put(film.category, categoryWatchTime.getOrDefault(film.category, 0) + film.duration);
    }

    public void showViewingHistory() {
        System.out.println("Viewing History for " + username + ":");
        for (Film film : viewingHistory) {
            System.out.println("- " + film.name + " (" + film.category + ", " + film.duration + " mins)");
        }
    }

    public void recommendFilm(List<Film> films) {
        if (categoryWatchTime.isEmpty()) {
            System.out.println("No viewing history found. Please watch some films first.");
            return;
        }

        String favoriteCategory = Collections.max(categoryWatchTime.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("Recommended films in your favorite category (" + favoriteCategory + "):");
        for (Film film : films) {
            if (film.category.equals(favoriteCategory) && !viewingHistory.contains(film)) {
                System.out.println("- " + film.name);
            }
        }
    }
}

public class SedayonCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Film> filmCatalog = Arrays.asList(
            new Film("Attack on Titan", "Action", 24),
            new Film("Death Note", "Thriller", 37),
            new Film("Your Name", "Romance", 107),
            new Film("Demon Slayer: Mugen Train", "Action", 117),
            new Film("Spirited Away", "Fantasy", 125),
            new Film("One Piece Film: Red", "Adventure", 115)
        );

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        Viewer viewer = new Viewer(username);

        while (true) {
            System.out.println("\n1. Watch a Film\n2. Show Viewing History\n3. Get Film Recommendations\n4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
    case 1 -> System.out.println("You chose to watch a film.");
    case 2 -> System.out.println("You chose to view history.");
    case 3 -> System.out.println("You chose to get recommendations.");
    case 4 -> {
        System.out.println("Exiting Sedayon CLI. Goodbye!");
        scanner.close();
        return;
    }
    default -> System.out.println("Invalid choice. Please try again.");
}

        }
    }
}
