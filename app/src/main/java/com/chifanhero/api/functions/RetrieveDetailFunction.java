package com.chifanhero.api.functions;

import com.chifanhero.api.models.response.Restaurant;
import com.chifanhero.api.models.response.RestaurantSearchResponse;
import com.chifanhero.api.models.response.Source;
import com.chifanhero.api.services.elasticsearch.ElasticsearchService;
import com.google.common.base.Function;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by shiyan on 10/8/17.
 */
public class RetrieveDetailFunction implements Function<RestaurantSearchResponse, RestaurantSearchResponse> {

    private final ElasticsearchService elasticsearchService;

    public RetrieveDetailFunction(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @Override
    public RestaurantSearchResponse apply(RestaurantSearchResponse input) {
        Optional.ofNullable(input.getResults()).ifPresent(restaurants -> {
            Set<String> ids = restaurants.stream().filter(restaurant -> restaurant.getSource() == Source.GOOGLE).map(Restaurant::getId).collect(Collectors.toSet());
            Map<String, Restaurant> stringRestaurantMap = elasticsearchService.batchGetRestaurant(ids);
            List<Restaurant> newResults = new ArrayList<>(restaurants);
            Map<Integer, Restaurant> toReplace = new HashMap<>();
            for (int i = 0; i < restaurants.size(); i++) {
                Restaurant restaurant = restaurants.get(i);
                if (restaurant.getSource() == Source.GOOGLE && stringRestaurantMap.get(restaurant.getId()) != null) {
                    Restaurant detailed = stringRestaurantMap.get(restaurant.getId());
                    detailed.applyPatch(restaurant);
                    toReplace.put(i, detailed);
                }
            }
            toReplace.entrySet().forEach(entry -> {
                newResults.remove(entry.getKey().intValue());
                newResults.add(entry.getKey(), entry.getValue());
            });
            input.setResults(newResults);
        });
        return input;
    }
}
