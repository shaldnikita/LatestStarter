package ru.shaldnikita.starter.backend.model;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author n.shaldenkov on 17/01/2018
 */

@Entity
@SpringComponent
@ViewScope
public class TerminalState extends AbstractEntity {

    public TerminalState(String serialNumber, String tmsId, String status, String model, String softwareVersion, LocalDate lastTmsUpdate, LocalDateTime lastUpdate, String address, int paperCount, String battery, String cardReadingErrors, String networkErrors) {
        this.serialNumber = serialNumber;
        this.tmsId = tmsId;
        this.status = status;
        this.model = model;
        this.softwareVersion = softwareVersion;
        this.lastTmsUpdate = lastTmsUpdate;
        this.lastUpdate = lastUpdate;
        this.address = address;
        this.paperCount = paperCount;
        this.battery = battery;
        this.cardReadingErrors = cardReadingErrors;
        this.networkErrors = networkErrors;
    }

    public TerminalState() {
    }

    private String serialNumber;
    private String tmsId;

    private String status;

    private String model;
    private String softwareVersion;

    private LocalDate lastTmsUpdate;
    private LocalDateTime lastUpdate;

    private String address;

    private int paperCount;
    private String battery;

    private String cardReadingErrors;
    private String networkErrors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getTmsId() {
        return tmsId;
    }

    public void setTmsId(String tmsId) {
        this.tmsId = tmsId;
    }

    public LocalDate getLastTmsUpdate() {
        return lastTmsUpdate;
    }

    public void setLastTmsUpdate(LocalDate lastTmsUpdate) {
        this.lastTmsUpdate = lastTmsUpdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(int paperCount) {
        this.paperCount = paperCount;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCardReadingErrors() {
        return cardReadingErrors;
    }

    public void setCardReadingErrors(String cardReadingErrors) {
        this.cardReadingErrors = cardReadingErrors;
    }

    public String getNetworkErrors() {
        return networkErrors;
    }

    public void setNetworkErrors(String networkErrors) {
        this.networkErrors = networkErrors;
    }
}
