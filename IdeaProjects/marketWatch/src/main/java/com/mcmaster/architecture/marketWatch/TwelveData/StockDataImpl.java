package com.mcmaster.architecture.marketWatch.TwelveData;

import com.mcmaster.architecture.marketWatch.DBModels.WatchList;
import com.mcmaster.architecture.marketWatch.DBModels.WatchListRepository;
import com.mcmaster.architecture.marketWatch.model.StockApiResponse;
import com.mcmaster.architecture.marketWatch.model.TwelveDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;


@Component
public class StockDataImpl implements StockData{

    @Autowired
    private WatchListRepository repository;

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

   // Using abstraction and interface architecture here. This implementation of StockData interface will implement
  //  the business logic of fetching stock data from TwelveData Api

    //This API also saves data to MySQL DB hosted on AWS cloud

    @Override
    public StockApiResponse stockData(String ticker) {
        WebClient client = WebClient.create();

        WebClient.ResponseSpec responseSpec = client.get()
                .uri("https://api.twelvedata.com/time_series?apikey=2fcfe5afc3614322989ce4ca807651e2&interval=1day&symbol={ticker}&outputsize=1", ticker)
                .retrieve();

        TwelveDataResponse res = responseSpec.bodyToMono(TwelveDataResponse.class).block(REQUEST_TIMEOUT);
        StockApiResponse response = new StockApiResponse();
        response.setClose(res.getValues().get(0).getClose());
        response.setHigh(res.getValues().get(0).getHigh());
        response.setLow(res.getValues().get(0).getLow());
        response.setVolume(res.getValues().get(0).getVolume());

        WatchList watchList = new WatchList();
        watchList.setName(ticker);
        watchList.setPrice(response.getClose());
        watchList.setHigh(response.getHigh());
        watchList.setLow(response.getLow());
        repository.save(watchList);

        return response;
    }
}

