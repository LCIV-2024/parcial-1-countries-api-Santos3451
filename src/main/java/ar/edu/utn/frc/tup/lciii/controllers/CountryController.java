package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ContinenteDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/countries")
    public List<CountryDTO> getCountries() {

        List<CountryDTO> countries = countryService.getAllContries1();

        return countries;
    }

    @GetMapping("/countries/{code}")
    public CountryDTO[] getCountryByCodeOrName(@RequestParam(required = false) String code, @RequestParam(required = false) String name) {
        return countryService.getAllContries1ByCodeOrName(code, name);
    }

    @GetMapping("/countriesByContinent")
    public List<ContinenteDTO> getAllCountriesbyContinent(@RequestParam String continent) {
        return countryService.getAllContinents(continent);
    }

    @GetMapping("/countriesByLanguage")
    public List<CountryDTO> getAllCountriesByLanguage(@RequestParam String language) {
        return countryService.getPaisesIdiomas(language);
    }
    @GetMapping("/countriesByFrontera")
    public CountryDTO getAllCountriesByFrontera() {
        return countryService.getCountriesConMasFronteras();
    }



}