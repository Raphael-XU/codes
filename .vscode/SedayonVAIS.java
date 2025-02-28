import java.sql.*;
import java.util.Scanner;

public class SedayonVAIS {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/virtual_assistant";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    private static Scanner inputScanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Virtual Assistant Invoice System ===");
            System.out.println("1. Client Management");
            System.out.println("2. Service Management");
            System.out.println("3. Invoice Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int selection = inputScanner.nextInt();
            inputScanner.nextLine();

            switch (selection) {
                case 1: manageClients(); break;
                case 2: manageServices(); break;
                case 3: manageInvoices(); break;
                case 4: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void manageClients() {
        System.out.println("\n=== Client Management ===");
        System.out.println("1. Add Client");
        System.out.println("2. View Clients");
        System.out.println("3. Update Client");
        System.out.println("4. Delete Client");
        System.out.print("Enter your choice: ");
        int selection = inputScanner.nextInt();
        inputScanner.nextLine();
    }

    private static void manageServices() {
        System.out.println("\n=== Service Management ===");
        System.out.println("1. Add Service");
        System.out.println("2. View Services");
        System.out.println("3. Update Service");
        System.out.println("4. Delete Service");
        System.out.print("Enter your choice: ");
        int selection = inputScanner.nextInt();
        inputScanner.nextLine();
    }

    private static void manageInvoices() {
        System.out.println("\n=== Invoice Management ===");
        System.out.println("1. Create New Invoice");
        System.out.println("2. View All Invoices");
        System.out.println("3. View Invoice by Client");
        System.out.print("Enter your choice: ");
        int selection = inputScanner.nextInt();
        inputScanner.nextLine();
        
        switch (selection) {
            case 1: createInvoice(); break;
            case 2: viewInvoices(); break;
            case 3: viewInvoiceByClient(); break;
            default: System.out.println("Invalid choice.");
        }
    }

    private static void createInvoice() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Select client ID:");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM clients");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " + rs.getString("name"));
            }
            int clientId = inputScanner.nextInt();
            inputScanner.nextLine();

            System.out.println("Select service ID:");
            rs = stmt.executeQuery("SELECT id, description, rate FROM services");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " + rs.getString("description") + " ($" + rs.getDouble("rate") + "/hr)");
            }
            int serviceId = inputScanner.nextInt();
            inputScanner.nextLine();

            System.out.print("Enter hours: ");
            int hours = inputScanner.nextInt();
            inputScanner.nextLine();

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO invoices (client_id, service_id, hours, total) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, serviceId);
            pstmt.setInt(3, hours);
            pstmt.setDouble(4, hours * getServiceRate(conn, serviceId));
            pstmt.executeUpdate();

            System.out.println("Invoice created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static double getServiceRate(Connection conn, int serviceId) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT rate FROM services WHERE id = ?");
        pstmt.setInt(1, serviceId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble("rate");
        }
        return 0.0;
    }

    private static void viewInvoices() {
        System.out.println("Displaying all invoices...");
    }

    private static void viewInvoiceByClient() {
        System.out.println("Enter client name to view invoices: ");
        String clientName = inputScanner.nextLine();
    }
}
