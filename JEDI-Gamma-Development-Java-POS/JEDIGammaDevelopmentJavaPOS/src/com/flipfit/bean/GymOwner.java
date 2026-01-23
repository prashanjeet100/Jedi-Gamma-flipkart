/**
 * 
 */
package com.flipfit.bean;

import java.util.List;

/**
 * 
 */
public class GymOwner {
	private String panCard;
    private List<GymCentre> gymCentres;

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public List<GymCentre> getGymCentres() {
        return gymCentres;
    }

    public void setGymCentres(List<GymCentre> gymCentres) {
        this.gymCentres = gymCentres;
    }
}
