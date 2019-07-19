import java.util.*;

/**
 * Main class to run
 *
 * @author Dau lam
 * @Date 07/18/2019
 */
public class Main {

    public static void main(String[] args) {
        Map<String, SortedSet> mapOfFiles = processCSVEnrollments("input.csv");

        print(mapOfFiles);

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
     * Process the csv file into a map
     *
     * @param fileName the csv file path and name
     * @return map of insurance company files
     */
    private static Map<String, SortedSet> processCSVEnrollments(String fileName) {
        Map<String, SortedSet> mapOfFiles = new HashMap<>(); // map with insurance names as key, and the collection of enrollment records as value

        List<Enrollment> listOfEnrollments = EnrollmentCSVReader.readFile(fileName);

        for (Enrollment enrollment : listOfEnrollments)
        {
            SortedSet<Enrollment> file = mapOfFiles.get(enrollment.getInsuranceCompany());
            if (file != null)
            {
                boolean isSuccess = mapOfFiles.get(enrollment.getInsuranceCompany()).add(enrollment); // try to add the enrollment record
                if (isSuccess == false)
                {
                    final String userId = enrollment.getUserId();
                    Enrollment existingEnrollment = file.stream()
                            .filter( e -> e.getUserId().equalsIgnoreCase(userId))
                            .findFirst().orElse(null);

                    if (existingEnrollment.getVersion() < enrollment.getVersion() ) {
                        // remove the duplicate and add the newer version
                        mapOfFiles.get(enrollment.getInsuranceCompany()).remove(existingEnrollment);
                        mapOfFiles.get(enrollment.getInsuranceCompany()).add(enrollment);
                    }
                }
            }
            else
            {
                file =  new TreeSet<>();
                file.add(enrollment);
                mapOfFiles.put(enrollment.getInsuranceCompany(), file);
            }
        }
        return mapOfFiles;
    }
}
