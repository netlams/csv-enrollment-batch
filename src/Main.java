import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Main class to run
 *
 * @author Dau lam
 * @Date 07/18/2019
 */
public class Main {

    public static void main(String[] args) {
        List listOfEnrollments = readFile("input.csv");    // reads the csv file
        Map mapOfFiles = processCSVEnrollments(listOfEnrollments);  // separate by insurance company
                                                                    // and sort the enrollment by names
                                                                    // and finally if duplicates,
                                                                    // take record greatest version number
        print(mapOfFiles);                                          // print the parsed data
    }

    /**
     * Prints the insurance company files and the enrollment records of each file
     *
     * @param mapOfFiles map of insurance company files
     */
    private static void print(Map<String, SortedSet> mapOfFiles) {
        mapOfFiles.forEach( (k,v) -> {
            System.out.println("Insurance: " + k + ", Enrollment Count: " + v.size());
            v.forEach( e-> {
                System.out.println(e.toString());
            });
            System.out.println();
        });
    }

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

    /**
     * Process the csv file into a map
     *
     * @param listOfEnrollments list of enrollments records
     * @return map of insurance company files
     */
    private static Map<String, SortedSet> processCSVEnrollments(List<Enrollment> listOfEnrollments) {
        Map<String, SortedSet> mapOfFiles = new HashMap<>();    // map with insurance names as key,
                                                                // and the collection of enrollment records as value

        for (Enrollment enrollment : listOfEnrollments)
        {
            SortedSet<Enrollment> file = mapOfFiles.get(enrollment.getInsuranceCompany());
            if (file != null)
            {
                boolean isSuccess = file.add(enrollment); // try to add the enrollment record
                if (isSuccess == false)
                {
                    // enrollment record already exists, duplicate record so we check the version
                    final String userId = enrollment.getUserId();
                    Enrollment existingEnrollment = file.stream()
                            .filter( e -> e.getUserId().equalsIgnoreCase(userId))
                            .findFirst().orElse(null); // using userId as unique identifier, search and return the enrollment record

                    // remove the duplicate and add the newer version
                    if (existingEnrollment.getVersion() < enrollment.getVersion() ) {
                        mapOfFiles.get(enrollment.getInsuranceCompany()).remove(existingEnrollment);
                        mapOfFiles.get(enrollment.getInsuranceCompany()).add(enrollment);
                    }
                }
            }
            else
            {
                // new insurance company encountered
                // create a set and add the enrollment
                file =  new TreeSet<>();
                file.add(enrollment);
                mapOfFiles.put(enrollment.getInsuranceCompany(), file);
            }
        }
        return mapOfFiles;
    }
}
