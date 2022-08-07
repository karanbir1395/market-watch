package com.mcmaster.architecture.marketWatch.TwelveData;

import com.mcmaster.architecture.marketWatch.model.StockApiResponse;

public interface StockData {

    public StockApiResponse stockData(String ticker);
}
