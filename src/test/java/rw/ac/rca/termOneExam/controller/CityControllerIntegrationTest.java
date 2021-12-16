package rw.ac.rca.termOneExam.controller;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.service.CityService;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
@SpringBootTest(classes = CityController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

        @MockBean
    private CityService cityService;

    @Autowired
    private MockMvc mockMvc;


    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads(){}

    @Test
    public void testGetAllCities(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl()+"/api/cities/all",
                HttpMethod.GET,entity,String.class);
        assertNotNull(response.getBody());
    }


    @Test
    public void testGetByIdSuccess(){
        City city = restTemplate.getForObject(getRootUrl()+"/api/cities/id/101", City.class);
        System.out.println(city.getName());
        assertNotNull(city);
    }

    @Test
    public void getById_404() {
        ResponseEntity<APICustomResponse> city =
                this.restTemplate.getForEntity("/api/cities/id/110", APICustomResponse.class);
        City city1 = new City(110,"Kg",20,50);

        assertTrue(city.getStatusCodeValue()==404);
        assertFalse(city.getBody().isStatus());
        assertEquals("City not found with id " + city1.getId(), city.getBody().getMessage());
    }

    @Test
    public void createTestSuccess(){
        City city = new City();
        city.setName("Huye");
        city.setWeather(18.43);

        ResponseEntity<City> createResponse = restTemplate.postForEntity(getRootUrl()+"/api/cities/add",city,City.class);
        assertNotNull(createResponse);
        assertNotNull(createResponse.getBody());
    }

    @Test
    public void createTestFail(){
        City city = new City();
        city.setName("Kigali");
        city.setWeather(18.43);

        CreateCityDTO createCityDTO = new CreateCityDTO();
        createCityDTO.setName("Kigali");
        createCityDTO.setWeather(18.37);

        when(cityService.save(createCityDTO)).thenReturn(city);
        ResponseEntity<City> createResponse = restTemplate.postForEntity(getRootUrl()+"/api/cities/add",city,City.class);
        assertEquals("City name " + createCityDTO.getName() + " is registered already",createResponse.getBody().getMessage());
    }

}
