package com.equivi.mailsy.dto.campaign;


import java.io.Serializable;

public class CampaignStatisticDTO implements Serializable {

    private static final long serialVersionUID = 521785305130028411L;

    private Long campaignId;

    private Long totalSent;

    private Long totalOpened;

    private Long totalDelivered;

    private Long totalFailed;

    private Long totalUnsubscribed;

    private Long totalClicked;

    private Long totalOpenUsingMobile;

    private Long totalOpenUsingDesktop;

    private Long totalOpenUsingTablet;

    private Long totalOpenUsingOthers;

    private Double openUsingMobilePercentage;

    private Double openUsingDesktopPercentage;

    private Double openUsingTabletPercentage;

    private Double openUsingOtherPercentage;

    private Double openMailPercentage;

    private Double clickedMailPercentage;

    private Double deliverMailPercentage;

    private Double failedMailPercentage;

    public Double getOpenMailPercentage() {
        return openMailPercentage;
    }

    public void setOpenMailPercentage(Double openMailPercentage) {
        this.openMailPercentage = openMailPercentage;
    }

    public Double getDeliverMailPercentage() {
        return deliverMailPercentage;
    }

    public void setDeliverMailPercentage(Double deliverMailPercentage) {
        this.deliverMailPercentage = deliverMailPercentage;
    }

    public Double getFailedMailPercentage() {
        return failedMailPercentage;
    }

    public void setFailedMailPercentage(Double failedMailPercentage) {
        this.failedMailPercentage = failedMailPercentage;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getTotalSent() {
        return totalSent;
    }

    public void setTotalSent(Long totalSent) {
        this.totalSent = totalSent;
    }

    public Long getTotalOpened() {
        return totalOpened;
    }

    public void setTotalOpened(Long totalOpened) {
        this.totalOpened = totalOpened;
    }

    public Long getTotalDelivered() {
        return totalDelivered;
    }

    public void setTotalDelivered(Long totalDelivered) {
        this.totalDelivered = totalDelivered;
    }

    public Long getTotalFailed() {
        return totalFailed;
    }

    public void setTotalFailed(Long totalFailed) {
        this.totalFailed = totalFailed;
    }

    public Long getTotalUnsubscribed() {
        return totalUnsubscribed;
    }

    public void setTotalUnsubscribed(Long totalUnsubscribed) {
        this.totalUnsubscribed = totalUnsubscribed;
    }

    public Long getTotalOpenUsingMobile() {
        return totalOpenUsingMobile;
    }

    public void setTotalOpenUsingMobile(Long totalOpenUsingMobile) {
        this.totalOpenUsingMobile = totalOpenUsingMobile;
    }

    public Long getTotalOpenUsingDesktop() {
        return totalOpenUsingDesktop;
    }

    public void setTotalOpenUsingDesktop(Long totalOpenUsingDesktop) {
        this.totalOpenUsingDesktop = totalOpenUsingDesktop;
    }

    public Long getTotalOpenUsingOthers() {
        return totalOpenUsingOthers;
    }

    public void setTotalOpenUsingOthers(Long totalOpenUsingOthers) {
        this.totalOpenUsingOthers = totalOpenUsingOthers;
    }

    public Double getOpenUsingMobilePercentage() {
        return openUsingMobilePercentage;
    }

    public void setOpenUsingMobilePercentage(Double openUsingMobilePercentage) {
        this.openUsingMobilePercentage = openUsingMobilePercentage;
    }

    public Double getOpenUsingDesktopPercentage() {
        return openUsingDesktopPercentage;
    }

    public void setOpenUsingDesktopPercentage(Double openUsingDesktopPercentage) {
        this.openUsingDesktopPercentage = openUsingDesktopPercentage;
    }

    public Double getOpenUsingOtherPercentage() {
        return openUsingOtherPercentage;
    }

    public void setOpenUsingOtherPercentage(Double openUsingOtherPercentage) {
        this.openUsingOtherPercentage = openUsingOtherPercentage;
    }

    public Long getTotalClicked() {
        return totalClicked;
    }

    public void setTotalClicked(Long totalClicked) {
        this.totalClicked = totalClicked;
    }

    public Double getClickedMailPercentage() {
        return clickedMailPercentage;
    }

    public void setClickedMailPercentage(Double clickedMailPercentage) {
        this.clickedMailPercentage = clickedMailPercentage;
    }

    public Long getTotalOpenUsingTablet() {
        return totalOpenUsingTablet;
    }

    public void setTotalOpenUsingTablet(Long totalOpenUsingTablet) {
        this.totalOpenUsingTablet = totalOpenUsingTablet;
    }

    public Double getOpenUsingTabletPercentage() {
        return openUsingTabletPercentage;
    }

    public void setOpenUsingTabletPercentage(Double openUsingTabletPercentage) {
        this.openUsingTabletPercentage = openUsingTabletPercentage;
    }
}
