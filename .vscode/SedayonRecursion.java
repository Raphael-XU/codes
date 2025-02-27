import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; 

class FileNode {
    String name;
    boolean isDirectory;
    List<FileNode> children;

    FileNode(String name, boolean isDirectory) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.children = new ArrayList<>();
    }

    void addChild(FileNode child) {
        if (this.isDirectory) {
            this.children.add(child);
        }
    }
}

class FileSearcher {
    private final String extension;
    private final List<String> results;

    FileSearcher(String extension) {
        this.extension = extension;
        this.results = new ArrayList<>();
    }

    void search(FileNode node, String path) {
        if (!node.isDirectory && node.name.endsWith(extension)) {
            // Change the path separator from "/" to "\"
            String foundPath = path + "\\" + node.name;
            System.out.println("File found: " + foundPath);
            results.add(foundPath);
        } else if (node.isDirectory) {
            for (FileNode child : node.children) {
                // Use "\\" to ensure the correct path separator
                search(child, path + "\\" + node.name);
            }
        }
    }

    void saveResults(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String result : results) {
                writer.write(result + "\n");
            }
            System.out.println("Search completed. Results saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

public class SedayonRecursion {
    public static void main(String[] args) {

        FileNode rootFolder = new FileNode("Documents", true);
        FileNode textFile1 = new FileNode("notes.txt", false);
        FileNode subDirectory = new FileNode("subfolder", true);
        FileNode textFile2 = new FileNode("todo.txt", false);
        FileNode imageFile = new FileNode("image.jpg", false);

        rootFolder.addChild(textFile1);
        rootFolder.addChild(subDirectory);
        subDirectory.addChild(textFile2);
        subDirectory.addChild(imageFile);

        String directoryPath;
        String fileExtension;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter directory path: ");
            directoryPath = scanner.nextLine();
            System.out.print("Enter file extension to search for: ");
            fileExtension = scanner.nextLine();
        }

        FileSearcher searcher = new FileSearcher(fileExtension);
        searcher.search(rootFolder, directoryPath);
        searcher.saveResults("search_results.txt");
    }
}