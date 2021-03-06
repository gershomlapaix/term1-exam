package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityUtilTest {


    @Mock
    private ICityRepository iCityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void getCitiesCelciusMoreThan40() {
        City city1 = new City(2, "Kibuye", 42, 53);
        City city2 = new City(1, "Gitarama", 52, 48);
        List<City> cities = null;
        cities.add(city2);
        cities.add(city1);
        when(iCityRepository.findAll()).thenReturn(cities);

//        assertEquals(10,itemService.getAll().get(0).getValue());
        for (int i = 0; i < cities.size(); i++) {
            assertTrue(String.valueOf(true), cities.get(i).getWeather() > 40);
        }
    }

    @Test
    public void getCitiesCelciusLessThan40() {
        City city1 = new City(2, "Kibuye", 32, 53);
        City city2 = new City(1, "Gitarama", 19, 48);
        List<City> cities = null;
        cities.add(city2);
        cities.add(city1);
        when(iCityRepository.findAll()).thenReturn(cities);

//        assertEquals(10,itemService.getAll().get(0).getValue());
        for (int i = 0; i < cities.size(); i++) {
            assertTrue(String.valueOf(true), cities.get(i).getWeather() < 40);
        }
    }


    @Test
    public void getCitiesContainsMusanze() {
        City city1 = new City(2, "Muzanze", 32, 53);
        City city2 = new City(1, "Gitarama", 19, 48);
        List<City> cities = null;
        cities.add(city2);
        cities.add(city1);
        when(iCityRepository.findAll()).thenReturn(cities);

            assertTrue(String.valueOf(true), city1.getName().contains("Muzanze"));
    }

    @Test
    public void getCitiesContainsKigali() {
        City city1 = new City(2, "Muzanze", 32, 53);
        City city2 = new City(1, "Kigali", 19, 48);
        List<City> cities = null;
        cities.add(city2);
        cities.add(city1);
        when(iCityRepository.findAll()).thenReturn(cities);

        assertTrue(String.valueOf(true), city2.getName().contains("Kigali"));
    }

    @Test
    public void testMocking() {
        ArrayList<City> arrayListMock =  mock(ArrayList.class);
        System.out.println(arrayListMock.get(0));//null
        System.out.println(arrayListMock.size());//0

        arrayListMock.add(new City("Paris",15));
        arrayListMock.add(new City("Dortmund", 12));
        System.out.println(arrayListMock.size());//0

        when(arrayListMock.size()).thenReturn(5);
        System.out.println(arrayListMock.size());//5
    }

    @Test
    public void testSpying() {
        ArrayList<City> arrayListSpy =  spy(ArrayList.class);
        arrayListSpy.add(new City("Kayonza",21));
        System.out.println(arrayListSpy.get(0));//Test0
        System.out.println(arrayListSpy.size());//1

        arrayListSpy.add(new City("Rwamagana",27));
        arrayListSpy.add(new City("Bugesera",28));
        System.out.println(arrayListSpy.size());

        when(arrayListSpy.size()).thenReturn(5);
        System.out.println(arrayListSpy.size());//5

        arrayListSpy.add(new City("Gatsibo",32));
        System.out.println(arrayListSpy.size());//5
    }


}
