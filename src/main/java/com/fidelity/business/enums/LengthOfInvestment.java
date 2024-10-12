package com.fidelity.business.enums;

public enum LengthOfInvestment {
	zero_to_five_years(10, "zero_to_five_years"), five_to_seven_years(7.5, "five_to_seven_years"),
	seven_to_ten_years(5, "seven_to_ten_years"), ten_to_fifteen_years(2.5, "ten_to_fifteen_years");

	protected final double score;

	private String code;

	private LengthOfInvestment(double score, String code) {
		this.score = score;
		this.code = code;
	}

	public static LengthOfInvestment of(String code) {
		int check = 1;
		for (LengthOfInvestment li : values()) {
			if (li.code.equals(code)) {
				return li;
			}
		}
		if (check == 1) {
			return null;
		}
		throw new IllegalArgumentException("Unknown code");
	}

	public double getScore() {
		return score;
	}

	public String getCode() {
		return code;
	}
}