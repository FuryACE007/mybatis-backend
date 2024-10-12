package com.fidelity.business.entity;

public class RoboAdvisor {
    private int id;
    private double marketFiveDayReturn;
    private double stockFivedayReturn;

    public RoboAdvisor(double[] marketPrice, double[] stockPrice) {

        double[] marketFiveDayReturnArray = {0, 0, 0, 0, 0};
        double[] stockFivedayReturnArray = {0, 0, 0, 0, 0};
        double sumStock = 0;
        double sumMarket = 0;

        for (int i = 0; i < marketPrice.length - 1; i++) {
            marketFiveDayReturnArray[i] = ((marketPrice[i] - marketPrice[i + 1]) / marketPrice[i + 1]) * 100;
        }
        for (int i = 0; i < marketPrice.length - 1; i++) {
            stockFivedayReturnArray[i] = ((stockPrice[i] - stockPrice[i + 1]) / stockPrice[i + 1]) * 100;
        }
        for (int i = 0; i < stockPrice.length - 1; i++) {
            sumStock += stockFivedayReturnArray[i];
        }
        this.stockFivedayReturn = sumStock / 4;

        for (int i = 1; i <= 5; i++) {
            sumMarket += marketFiveDayReturnArray[i - 1];
        }
        this.marketFiveDayReturn = sumMarket / 4;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMarketFiveDayReturn() {
        return marketFiveDayReturn;
    }

    public void setMarketFiveDayReturn(double marketFiveDayReturn) {
        this.marketFiveDayReturn = marketFiveDayReturn;
    }

    public double getStockFivedayReturn() {
        return stockFivedayReturn;
    }

    public void setStockFivedayReturn(double stockFivedayReturn) {
        this.stockFivedayReturn = stockFivedayReturn;
    }

    public double stockRecommendationPoint() {
        double probabilityOfMarketEvent = 0;
        double probabilityOfStockEvent = 0;

        if (marketFiveDayReturn > 10) {
            probabilityOfMarketEvent = 1;

        } else if (marketFiveDayReturn > 5 && marketFiveDayReturn < 10) {
            probabilityOfMarketEvent = marketFiveDayReturn/10;
        } else if (marketFiveDayReturn > 0 && marketFiveDayReturn < 5) {
            probabilityOfMarketEvent = marketFiveDayReturn/10;
        } else if (marketFiveDayReturn > -5 && marketFiveDayReturn < 0) {
            probabilityOfMarketEvent = 0;
        } else {
            probabilityOfMarketEvent = 0;

        }

        if (stockFivedayReturn > 10) {
            probabilityOfStockEvent = 1;

        } else if (stockFivedayReturn > 5 && stockFivedayReturn < 10) {
            probabilityOfStockEvent = stockFivedayReturn/10;
        } else if (stockFivedayReturn > 0 && stockFivedayReturn < 5) {
            probabilityOfStockEvent = stockFivedayReturn/10;
        } else if (stockFivedayReturn > -5 && stockFivedayReturn < 0) {
            probabilityOfStockEvent = 0;
        } else {
            probabilityOfStockEvent = 0;

        }

        if (probabilityOfStockEvent < probabilityOfMarketEvent) {
            return 0.0;
        } else {
            double probabilityOfStockConsMarket = calculateBayesProbability(probabilityOfMarketEvent,
                    probabilityOfStockEvent);
            return Math.round(probabilityOfStockConsMarket * 100.0) / 100.0;
        }

    }

    public double calculateBayesProbability(double probabilityOfMarketEvent, double probabilityOfStockEvent) {
        probabilityOfMarketEvent += 0.1;
        double probabilityOfStockConsMarket = (probabilityOfMarketEvent * probabilityOfStockEvent)
                / probabilityOfMarketEvent;
        return probabilityOfStockConsMarket;
    }
}
