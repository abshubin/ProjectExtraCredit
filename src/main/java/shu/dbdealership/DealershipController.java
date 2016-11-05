package shu.dbdealership;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Shubin on 11/4/16.
 */
@RestController
public class DealershipController {

    private final String PATH = FileUtils.getUserDirectoryPath()
            + "/IdeaProjects/DealershipProject"
            + "/src/main/java/shu/dealership/inventory.txt";

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        // ObjectMapper provides functionality for reading and writing JSON
        ObjectMapper mapper = new ObjectMapper();

        // Create a FileWrite to write to inventory.txt and APPEND mode is true
        FileWriter output = new FileWriter(PATH, true);

        // Serialize greeting object to JSON and write it to file
        mapper.writeValue(output, newVehicle);

        // Append a new line character to the file
        // The above FileWriter ("output") is automatically closed by the mapper
        FileUtils.writeStringToFile(new File(PATH),
                System.lineSeparator(),     // newline String
                CharEncoding.UTF_8,         // encoding type
                true);                      // Append mode is true
        return newVehicle;
    }

    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        List<Vehicle> vehicles = readVehicles();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }

        return null;
    }

    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        List<Vehicle> vehicles = readVehicles();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == newVehicle.getId()) {
                vehicle.setMakeModel(newVehicle.getMakeModel());
                vehicle.setRetailPrice(newVehicle.getRetailPrice());
                vehicle.setYear(newVehicle.getYear());
            }
        }

        overwriteVehicles(vehicles);

        return newVehicle;
    }

    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
        List<Vehicle> vehicles = readVehicles();

        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId() == id) {
                vehicles.remove(i);
                i--;    // not sure if the removal will throw off
                        // the iteration, so i-- to be safe...
            }
        }

        overwriteVehicles(vehicles);

        return null; // Not sure what I'm supposed to be returning...
    }

    @RequestMapping(value = "/getHighestId", method = RequestMethod.GET)
    public int getHighestId() throws IOException {
        List<Vehicle> vehicles = readVehicles();
        int maxId = 0;

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() > maxId) {
                maxId = vehicle.getId();
            }
        }

        return maxId;
    }

    private void overwriteVehicles(List<Vehicle> vehicles) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String newContents = "";

        for (Vehicle vehicle : vehicles) {
            newContents += mapper.writeValueAsString(vehicle) + "\n";
        }

        FileUtils.writeStringToFile(new File(PATH),
                newContents,
                CharEncoding.UTF_8,
                false);
    }

    private List<Vehicle> readVehicles() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String[] jsonVehicles = StringUtils.split(FileUtils.readFileToString(new File(PATH),
                CharEncoding.UTF_8), '\n');

        List<Vehicle> vehicles = new ArrayList<>();
        for (String bean : jsonVehicles) {
            vehicles.add(mapper.readValue(bean, Vehicle.class));
        }

        return vehicles;
    }
}
