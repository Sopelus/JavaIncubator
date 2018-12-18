import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class incubatorTest extends Thread {


    static ResultSet rset;
    static Connection conn;
    static String dateString;
    static SimpleDateFormat format;
    static String DB_NAME = "siteslist";



    public static void main(String[] args) throws IOException, ParseException, SQLException {
        rset = ConfigureSet();
        int iterationAmount = 0;

        while (rset.next())
            iterationAmount++;

        for (int i = 0; i < iterationAmount; i++)
            new JThread("JThread " + i).start();
    }



    public static ResultSet ConfigureSet() throws IOException, ParseException, SQLException {

        File dateSetup = new File("F:\\downloads\\JavaIncubator\\src\\main\\resources\\date.ini");
        BufferedReader br = new BufferedReader(new FileReader(dateSetup));
        String pattern = "yyyy-MM-dd";
        format = new SimpleDateFormat(pattern);
        dateString = br.readLine();
        Date date = format.parse(dateString);
        conn = HikariCPDataSource.getConnection();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        String strSelect = "select * from "+DB_NAME+" where date < '" + format.format(date) + "'";
        return stmt.executeQuery(strSelect);

    }


    static class JThread extends Thread {
        int code;

        JThread(String name) {
            super(name);
        }

        public void run() {
            synchronized (rset) {
                try {
                    while (rset.next()) {
                        URL url = new URL(rset.getString("url"));
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        try {
                            connection.connect();
                        }
                        catch (java.net.ConnectException e){
                            System.out.println("Connection timed out");

                        }
                        finally {
                            code = connection.getResponseCode();
                            rset.updateInt("status", code);
                            rset.updateString("date", dateString);
                            rset.updateRow();
                        }

                    }
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}



