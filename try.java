import java.util.*;

class Movie {
    String title;
    String genre;
    int watchTime;

    public Movie(String title, String genre, int watchTime) {
        this.title = title;
        this.genre = genre;
        this.watchTime = watchTime;
    }
}

class User {
    String name;
    List<Movie> watchHistory;
    Map<String, Integer> genreWatchTime;

    public User(String name) {
        this.name = name;
        this.watchHistory = new ArrayList<>();
        this.genreWatchTime = new HashMap<>();
    }

    public void watchMovie(Movie movie) {
        watchHistory.add(movie);
        genreWatchTime.put(movie.genre, genreWatchTime.getOrDefault(movie.genre, 0) + movie.watchTime);
    }

    public void showWatchHistory() {
        System.out.println("Watch History for " + name + ":");
        for (Movie movie : watchHistory) {
            System.out.println("- " + movie.title + " (" + movie.genre + ", " + movie.watchTime + " mins)");
        }
    }

    public void recommendMovie(List<Movie> movies) {
        if (genreWatchTime.isEmpty()) {
            System.out.println("No watch history found. Please watch some movies first.");
            return;
        }

        String favoriteGenre = Collections.max(genreWatchTime.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("Recommended movies in your favorite genre (" + favoriteGenre + "):");
        for (Movie movie : movies) {
            if (movie.genre.equals(favoriteGenre) && !watchHistory.contains(movie)) {
                System.out.println("- " + movie.title);
            }
        }
    }
}

public class NetflixCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Movie> movieCatalog = Arrays.asList(
            new Movie("Inception", "Sci-Fi", 148),
            new Movie("The Matrix", "Sci-Fi", 136),
            new Movie("Titanic", "Romance", 195),
            new Movie("The Notebook", "Romance", 123),
            new Movie("Avengers: Endgame", "Action", 181),
            new Movie("John Wick", "Action", 101)
        );

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        User user = new User(name);

        while (true) {
            System.out.println("\n1. Watch a Movie\n2. Show Watch History\n3. Get Movie Recommendations\n4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Available Movies:");
                    for (int i = 0; i < movieCatalog.size(); i++) {
                        System.out.println((i + 1) + ". " + movieCatalog.get(i).title + " (" + movieCatalog.get(i).genre + ")");
                    }
                    System.out.print("Enter the number of the movie to watch: ");
                    int movieIndex = scanner.nextInt() - 1;
                    if (movieIndex >= 0 && movieIndex < movieCatalog.size()) {
                        user.watchMovie(movieCatalog.get(movieIndex));
                        System.out.println("You watched " + movieCatalog.get(movieIndex).title);
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                case 2:
                    user.showWatchHistory();
                    break;
                case 3:
                    user.recommendMovie(movieCatalog);
                    break;
                case 4:
                    System.out.println("Exiting Netflix CLI. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
