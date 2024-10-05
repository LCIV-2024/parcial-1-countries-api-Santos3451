package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.ContinenteDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.IdiomaDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {



        @Autowired
        private final RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        public List<CountryDTO> getAllContries1() {
                List<Country> countriesData = this.getAllCountries();

                List<CountryDTO> countries = new ArrayList<>();
                for (Country country : countriesData) {
                        CountryDTO countryDTO = new CountryDTO(country.getCode(), country.getName());
                        countries.add(countryDTO);
                }




                return countries;
        }

        public CountryDTO[] getAllContries1ByCodeOrName(String code, String name) {
                List<Country> countriesData = this.getAllCountries();
                CountryDTO[] countries = new CountryDTO[1];

                if (code != null || !code.isEmpty()) {
                        countriesData = countriesData.stream()
                                .filter(country -> country.getCode().equals(code) || country.getName().equals(name))
                                .collect(Collectors.toList());

                       for  (Country country : countriesData) {
                               countries[0] = mapToDTO(country);
                       }


                } else {
                        throw new RuntimeException("No se encontró el país con código o nombre: " + code);
                }



                return countries;
        }



        public List<ContinenteDTO> getAllContinents(String continente) {

                List<Country> countriesData = this.getAllCountries();
                List<ContinenteDTO> continentes = new ArrayList<>();

                if (continente != null || !continente.isEmpty()) {
                        countriesData = countriesData.stream()
                                .filter(country -> country.getRegion().equals(continente))
                                .collect(Collectors.toList());

                        for  (Country country : countriesData) {
                                ContinenteDTO continenteDTO = new ContinenteDTO(country.getName());
                                continentes.add(continenteDTO);
                        }

                } else {
                        throw new RuntimeException("No se encontró el continente: " + continente);
                }

                return continentes;
        }

        public List<CountryDTO> getPaisesIdiomas(String idioma){

                List<Country> countriesData = this.getAllCountries();
                List<CountryDTO> idiomas = new ArrayList<>();

                if (idioma != null || !idioma.isEmpty()) {
                        countriesData = countriesData.stream()
                                .filter(country -> country.getLanguages().containsValue(idioma))
                                .collect(Collectors.toList());

                        for  (Country country : countriesData) {
                                CountryDTO idiomaDTO = new CountryDTO(country.getCode(), country.getName());
                                idiomas.add(idiomaDTO);
                        }

                } else {
                        throw new RuntimeException("No se encontró el idioma: " + idioma);
                }

                return idiomas;


        }

        public CountryDTO getCountriesConMasFronteras() {
                List<Country> countriesData = this.getAllCountries();
                CountryDTO countryDTO = new CountryDTO();
                int max = 0;
                for (Country country : countriesData) {
                        if (country.getBorders().size() > max) {
                                max = country.getBorders().size();
                                countryDTO = new CountryDTO(country.getCode(), country.getName());
                        }
                }
                return countryDTO;
        }









        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .code((String) countryData.get("cca3"))
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {

                return new CountryDTO(country.getCode(), country.getName());
        }
}