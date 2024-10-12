package com.fidelity.business.entity;


import com.fidelity.business.enums.CustomerScore;
import com.fidelity.business.enums.IncomeCategory;
import com.fidelity.business.enums.LengthOfInvestment;
import com.fidelity.business.enums.RiskTolerance;


public class InvestmentPreference {
    private int id;
    private String investmentPreference;
    private RiskTolerance riskTolerance;
    private IncomeCategory incomeCategory;
    private LengthOfInvestment lengthOfInvestment;
    private CustomerScore clientScore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvestmentPreference() {
        return investmentPreference;
    }

    public void setInvestmentPreference(String investmentPreference) {
        this.investmentPreference = investmentPreference;
    }

    public RiskTolerance getRiskTolerance() {
        return riskTolerance;
    }

    public void setRiskTolerance(RiskTolerance riskTolerance) {
        this.riskTolerance = riskTolerance;
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public LengthOfInvestment getLengthOfInvestment() {
        return lengthOfInvestment;
    }

    public void setLengthOfInvestment(LengthOfInvestment lengthOfInvestment) {
        this.lengthOfInvestment = lengthOfInvestment;
    }

    public CustomerScore getClientScore() {
        return clientScore;
    }

    public void setClientScore(CustomerScore clientScore) {
        this.clientScore = clientScore;
    }

    public InvestmentPreference(int id, String investmentPreference, RiskTolerance riskTolerance, IncomeCategory incomeCategory,
                                LengthOfInvestment lengthOfInvestment) {

        if (riskTolerance == null || incomeCategory == null || lengthOfInvestment == null) {
            throw new IllegalArgumentException("Enum fields cannot be null");
        }
        this.id = id;
        this.investmentPreference = investmentPreference;
        this.riskTolerance = riskTolerance;
        this.incomeCategory = incomeCategory;
        this.lengthOfInvestment = lengthOfInvestment;
        this.clientScore = calculateAverageScore(riskTolerance, incomeCategory, lengthOfInvestment);
    }

    private CustomerScore calculateAverageScore(RiskTolerance riskTolerance, IncomeCategory incomeCategory,
                                                LengthOfInvestment lengthOfInvestment) {
        double[] investmentPreferenceScores = {riskTolerance.getScore(), incomeCategory.getScore(),
                lengthOfInvestment.getScore()};
        double[] weightsOfPreference = {0.5, 0.3, 0.2};
        double weightedAverage = calculateWeightedAverage(investmentPreferenceScores, weightsOfPreference);
        if (weightedAverage > 8 && weightedAverage < 10) {
            return CustomerScore.High_Risky;
        } else if (weightedAverage > 6 && weightedAverage < 8) {
            return CustomerScore.Risky;
        } else if (weightedAverage > 3 && weightedAverage < 6) {
            return CustomerScore.Medium;
        } else {
            return CustomerScore.Safe;
        }

    }

    private static double calculateWeightedAverage(double[] values, double[] weights) {
        double sum = 0;
        double weightSum = 0;

        for (int i = 0; i < values.length; i++) {
            sum += values[i] * weights[i];
            weightSum += weights[i];
        }

        return sum / weightSum;
    }

    public void updateFields(String newinvestmentPreference, RiskTolerance newriskTolerance,
                             IncomeCategory newincomeCategory, LengthOfInvestment newlengthOfInvestment) {
        if (newriskTolerance == null || newincomeCategory == null || newlengthOfInvestment == null) {
            throw new IllegalArgumentException("Enum fields cannot be null");
        }
        this.investmentPreference = newinvestmentPreference;
        this.riskTolerance = newriskTolerance;
        this.lengthOfInvestment = newlengthOfInvestment;
        this.clientScore = calculateAverageScore(riskTolerance, incomeCategory, lengthOfInvestment);
    }

}