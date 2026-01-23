/**
 * 
 */
package com.flipfit.bean;

import java.util.List;

/**
 * 
 */
public class Customer {
	private double walletBalance;
    private List<String> preferences;

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }
}
