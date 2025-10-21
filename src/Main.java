public class Main {
    public static void main(String[] args) {
        Database db = null;
        try {
            db = new Database.getInstance();
        } catch (Exception e) {
            // Sout che utilizza ... dell'errore
            System.err.println("Impossibile connettersi al database" + e.getMessage());
            System.exit(-1);
        }

        db.insert("Nigiri salmone", 3, 2);
        System.out.println(db.selectAll());
    }
}