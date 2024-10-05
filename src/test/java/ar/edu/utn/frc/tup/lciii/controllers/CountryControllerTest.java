package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ContinenteDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getCountries() {
        CountryDTO country1 = new CountryDTO("ARGE", "Argentina");
        CountryDTO country2 = new CountryDTO("BRA", "Brazil");
        List<CountryDTO> countries = Arrays.asList(country1, country2);

        when(countryService.getAllContries1()).thenReturn(countries);

        List<CountryDTO> result = countryController.getCountries();
        assertEquals(2, result.size());
        assertEquals("Argentina", result.get(0).getName());
        assertEquals("Brazil", result.get(1).getName());

    }
    @Test
    void getCountryByCodeOrName() {
        CountryDTO country1 = new CountryDTO("ARG", "Argentina");
        CountryDTO[] countries = {country1};

        when(countryService.getAllContries1ByCodeOrName("ARG", null)).thenReturn(countries);

        CountryDTO[] result = countryController.getCountryByCodeOrName("ARG", null);
        assertEquals(1, result.length);
        assertEquals("Argentina", result[0].getName());
    }

    @Test
    void getAllCountriesbyContinent() {
        ContinenteDTO continente1 = new ContinenteDTO("Americas");
        List<ContinenteDTO> continentes = Arrays.asList(continente1);

        when(countryService.getAllContinents("Americas")).thenReturn(continentes);

        List<ContinenteDTO> result = countryController.getAllCountriesbyContinent("Americas");
        assertEquals(1, result.size());
        assertEquals("Americas", result.get(0).getNombre());
    }

    @Test
    void getAllCountriesByLanguage() {
        CountryDTO country1 = new CountryDTO("ARG", "Argentina");
        List<CountryDTO> countries = Arrays.asList(country1);

        when(countryService.getPaisesIdiomas("Spanish")).thenReturn(countries);

        List<CountryDTO> result = countryController.getAllCountriesByLanguage("Spanish");
        assertEquals(1, result.size());
        assertEquals("Argentina", result.get(0).getName());
    }
    @Test
    void getAllCountriesByFrontera() {
        CountryDTO country = new CountryDTO("CHN", "China");

        when(countryService.getCountriesConMasFronteras()).thenReturn(country);

        CountryDTO result = countryController.getAllCountriesByFrontera();
        assertEquals("China", result.getName());
    }
}