package com.chifanhero.api.helpers;

import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.Source;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RestaurantDeduperTest {

    @Test
    public void testBasic() {
        Restaurant restaurant1 = createRestaurant("1", Source.GOOGLE);
        Restaurant restaurant2 = createRestaurant("2", Source.CHIFANHERO);
        Restaurant restaurant3 = createRestaurant("3", Source.CHIFANHERO);
        List<Restaurant> deduped = RestaurantDeduper.dedupe(Arrays.asList(restaurant1, restaurant2, restaurant3));
        Assert.assertEquals(3, deduped.size());
        deduped.sort(Comparator.comparing(Restaurant::getPlaceId));
        Assert.assertEquals("1", deduped.get(0).getPlaceId());
        Assert.assertEquals(Source.GOOGLE, deduped.get(0).getSource());
        Assert.assertEquals("2", deduped.get(1).getPlaceId());
        Assert.assertEquals(Source.CHIFANHERO, deduped.get(1).getSource());
        Assert.assertEquals("3", deduped.get(2).getPlaceId());
        Assert.assertEquals(Source.CHIFANHERO, deduped.get(2).getSource());
    }

    @Test
    public void testFilterInvalid() {
        Restaurant restaurant1 = createRestaurant("1", Source.GOOGLE);
        Restaurant restaurant2 = createRestaurant("2", Source.CHIFANHERO);
        Restaurant restaurant3 = createRestaurant("3", null);
        List<Restaurant> deduped = RestaurantDeduper.dedupe(Arrays.asList(restaurant1, restaurant2, restaurant3));
        Assert.assertEquals(2, deduped.size());
        deduped.sort(Comparator.comparing(Restaurant::getPlaceId));
        Assert.assertEquals("1", deduped.get(0).getPlaceId());
        Assert.assertEquals(Source.GOOGLE, deduped.get(0).getSource());
        Assert.assertEquals("2", deduped.get(1).getPlaceId());
        Assert.assertEquals(Source.CHIFANHERO, deduped.get(1).getSource());
    }

    @Test
    public void testConflict() {
        Restaurant restaurant1 = createRestaurant("1", Source.GOOGLE);
        Restaurant restaurant2 = createRestaurant("2", Source.CHIFANHERO);
        Restaurant restaurant3 = createRestaurant("1", Source.CHIFANHERO);
        List<Restaurant> deduped = RestaurantDeduper.dedupe(Arrays.asList(restaurant1, restaurant2, restaurant3));
        Assert.assertEquals(2, deduped.size());
        deduped.sort(Comparator.comparing(Restaurant::getPlaceId));
        Assert.assertEquals("1", deduped.get(0).getPlaceId());
        Assert.assertEquals(Source.CHIFANHERO, deduped.get(0).getSource());
        Assert.assertEquals("2", deduped.get(1).getPlaceId());
        Assert.assertEquals(Source.CHIFANHERO, deduped.get(1).getSource());
    }

    private Restaurant createRestaurant(String placeId, Source source) {
        Restaurant restaurant = new Restaurant();
        restaurant.setPlaceId(placeId);
        restaurant.setSource(source);
        return restaurant;
    }
}
