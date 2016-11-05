package shu.dbdealership;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private VehicleDao vehicleDao;

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        vehicleDao.create(newVehicle);
        return newVehicle;
    }

    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        return vehicleDao.getById(id);
    }

    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        vehicleDao.update(newVehicle);
        return newVehicle;
    }

    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
        vehicleDao.deleteById(id);
        return null; // Not sure what I'm supposed to be returning...
    }
}
