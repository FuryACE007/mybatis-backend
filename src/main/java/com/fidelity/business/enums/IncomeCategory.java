package com.fidelity.business.enums;

public enum IncomeCategory {
	zero_to_twentyk(1.5, "zero_to_twentyk"), twentyk_to_fortyk(3, "twentyk_to_fortyk"),
	fortyk_to_sixtyk(4.5, "fortyk_to_sixtyk"), sixtyk_to_eightyk(6, "sixtyk_to_eightyk"),
	eightyk_to_onelakhk(7.5, "eightyk_to_onelakhk"), onelakhk_to_oneandhalflakhk(8.5, "onelakhk_to_oneandhalflakhk"),
	oneandhalflakhk_plus(10, "oneandhalflakhk_plus");

	protected final double score;

	private String code;

	private IncomeCategory(double score, String code) {
		this.score = score;
		this.code = code;
	}

	public static IncomeCategory of(String code) {
		int check = 1;
		for (IncomeCategory ip : values()) {
			if (ip.code.equals(code)) {
				return ip;
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