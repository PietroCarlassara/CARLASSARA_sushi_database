import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
    private Connection connection;
    // Usiamo sempre la stessa istanza
    // Incapsulamento. L'oggetto verrà costrutito una sola volta
    private static Database instance;

    // Costruttore
    // Il costruttore lancia l'eccezione
    // Propago l'eccezione
    private Database() throws Exception {
        String url = "jdbc:sqlite:database/sushi.db";
        connection = DriverManager.getConnection(url);
        // Se gestisco l'eccezione dentro il costruttore, l'oggetto viene
        // creato ma senza una connesione
        System.out.println("Connessione database");
    }

    // Ci ritorna un istanza della classe
    public static Database getInstance() throws Exception {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public String selectAll(){
        String result = "";

        // Controlliamo se il database sia ancora collegato
        try{
            if(connection == null || !connection.isValid(5)) {
                System.err.println("Connessione nulla o non valida");
                return null;
            }
        }catch(Exception e){
            System.err.println("Connessione nulla o non valida");
            return null;
        }

        String query = "SELECT * FROM menu";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result += rs.getString("id") +"\t";
                result += rs.getString("piatto") +"\t";
                result += rs.getString("prezzo") +"\t";
                result += rs.getString("quantita") +"\t";
            }
        } catch (Exception e){
            System.err.println("Errore in query" + query);
            return null;
        }

        return result;
    }

    public boolean insert(String piatto, double prezzo, int quantita) {
        // Controlliamo se il database sia ancora collegato
        try{
            if(connection == null || !connection.isValid(5)) {
                System.err.println("Connessione nulla o non valida");
                return false;
            }
        }catch(Exception e){
            System.err.println("Connessione nulla o non valida");
            return false;
        }
        String query = "INSERT INTO menu(piatto, prezzo, quantita) VALUES (?, ?, ?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, piatto);
            statement.setDouble(2, prezzo);
            statement.setInt(3, quantita);
            statement.execute();
            // java ci assicura che la stringa sia formattata bene
            // connection.createStatement().executeUpdate(query);
        }
        catch(Exception e){
            System.err.println("Errore in query" + query);
            return false;
        }

        return true;
    }
}