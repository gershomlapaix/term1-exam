package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rw.ac.rca.termOneExam.domain.City;
import static org.junit.Assert.assertTrue;

import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {
    @Mock
    private ICityRepository iCityRepository;

    @InjectMocks
    private CityService cityService;


        @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllCities() {

        when(iCityRepository.findAll()).thenReturn(Arrays.asList(new City(1,"Gitarama",20,48),
                new City(2,"Kibuye",21,53)));
        assertEquals("Gitarama",cityService.getAll().get(0).getName());
    }

    @Test
    public void post_Success() {
        CreateCityDTO createCityDTO = new CreateCityDTO("Kabarondo",18);
        City city = new City(101, "Kigali", 25, 60);
        when(iCityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(iCityRepository.existsByName(createCityDTO.getName())).thenReturn(true);
        when(iCityRepository.save(city)).thenReturn(city);

        City saveCity = cityService.save(createCityDTO);
        assertTrue(saveCity.getName().contains("Kabarondo"));
    }

    @Test
    public void post_404() throws Exception {
        CreateCityDTO createCityDTO = new CreateCityDTO("Kigali",18);
        City city = new City(101, "Kigali", 25, 60);
        when(iCityRepository.findById(1L)).thenReturn(Optional.empty());


                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/cities/add")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult =
                mockMvc.perform(request)
                        .andExpect(status().isBadRequest())
                        .andReturn();

    }

    

}
