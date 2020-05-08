package nl.rabobank.aggregationservice.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.aggregationservice.model.PowerOfAttorney;
import nl.rabobank.aggregationservice.service.PowerOfAttorneyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@ApiModel(description = "Endpoint for retrieving aggregated power of attorney data.")
@RequestMapping("/api/v1/power-of-attorneys")
@RestController
public class PowerOfAttorneyController {

    private final PowerOfAttorneyService powerOfAttorneyService;

    public PowerOfAttorneyController(final PowerOfAttorneyService powerOfAttorneyService) {
        this.powerOfAttorneyService = powerOfAttorneyService;
    }

    @ApiOperation("Returns all power of attorneys.")
    @GetMapping
    public ResponseEntity<List<PowerOfAttorney>> getPowerOfAttorneys() {
        LOG.info("Getting all power of attorneys...");
        return ResponseEntity.ok(powerOfAttorneyService.getPowerOfAttorneys());
    }


    @ApiOperation("Returns the power of attorney for given ID.")
    @GetMapping("/{id}")
    public ResponseEntity<PowerOfAttorney> getPowerOfAttorney(@PathVariable("id") final String id) {
        LOG.info("Getting power of attorney with id '{}'...'", id);
        return ResponseEntity.ok(powerOfAttorneyService.getPowerOfAttorney(id));
    }
}
