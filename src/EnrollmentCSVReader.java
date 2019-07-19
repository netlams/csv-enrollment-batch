import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * EnrollmentCSVReader class to parse csv files
 *
 * @author Dau lam
 * @Date 07/18/2019
 */
public class EnrollmentCSVReader {

    /**
     * Method to read and parse a csv file and return a list of enrollment records
     *
     * @param filename
     * @return list of enrollment records
     */
    public static List<Enrollment> readFile(String filename)
    {
        String line = "";
        String cvsSplitBy = ",";
        List<Enrollment> listOfRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] record = line.split(cvsSplitBy);
                Enrollment enrollment = new Enrollment(record[0], record[1], record[2], Integer.parseInt(record[3]), record[4]);
                listOfRecords.add(enrollment);
            }

        } catch (IOException e) {
            System.out.printf("Exception while reading file [%s]\n", filename);
            throw e;
        } finally
        {
            return listOfRecords;
        }
    }
}
