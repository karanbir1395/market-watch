package com.mcmaster.architecture.marketWatch.controller;

import com.mcmaster.architecture.marketWatch.DBModels.WatchList;
import com.mcmaster.architecture.marketWatch.DBModels.WatchListRepository;
import com.mcmaster.architecture.marketWatch.TwelveData.StockData;
import com.mcmaster.architecture.marketWatch.model.StockApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


//    This is the controller class of the MVC architecture which will handle the requests between view, business logic and model classes.
@RestController
public class stockApi {

    @Autowired
    WatchListRepository repository;

    @Autowired
    StockData stockData;

    @GetMapping("/api/stock/{ticker}")
    public StockApiResponse getStockData(@PathVariable String ticker) {

        StockApiResponse res =  stockData.stockData(ticker);

        return res;
    }

    //This API will return the user's saved watch list after fetching it from the MySQL database hosted on AWS cloud
    @GetMapping("api/stock/watchList")
    public @ResponseBody
    Iterable<WatchList> getWatchList() {

        return repository.findAll();
    }
}
