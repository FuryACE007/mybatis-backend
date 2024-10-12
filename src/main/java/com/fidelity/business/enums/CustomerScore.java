package com.fidelity.business.enums;



public enum CustomerScore {
	High_Risky(4,"High_Risky"), Risky(3,"Risky"), Medium(2,"Medium"), Safe(1,"Safe");

    private String code;
	private int val;

	private CustomerScore(String code) {
		this.code = code;
	}

	CustomerScore(int i, String string) {
		this.val = i;
		this.code = string;
	}

	public static CustomerScore of(String code) {
		int check = 1;
		for (CustomerScore cs : values()) {
			if (cs.code.equals(code)) {
				return cs;
			}
		}
		if (check == 1) {
			return null;
		}
		throw new IllegalArgumentException("Unknown code");
	}

    public int getScore() {
		return val;
	}
}