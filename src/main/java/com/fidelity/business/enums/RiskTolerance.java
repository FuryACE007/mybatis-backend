package com.fidelity.business.enums;

public enum RiskTolerance {

	CONSERVATIVE(2, "CONSERVATIVE"), BELOW_AVERAGE(4, "BELOW_AVERAGE"), AVERAGE(6, "AVERAGE"),
	ABOVE_AVERAGE(8, "ABOVE_AVERAGE"), AGGRESSIVE(10, "AGGRESSIVE");

	protected final double score;

	private String code;

	private RiskTolerance(double score, String code) {
		this.score = score;
		this.code = code;
	}

	public static RiskTolerance of(String code) {
		int check = 1;
		for (RiskTolerance rt : values()) {
			if (rt.code.equals(code)) {
				return rt;
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