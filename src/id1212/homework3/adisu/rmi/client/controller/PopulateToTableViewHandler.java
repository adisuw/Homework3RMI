/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1212.homework3.adisu.rmi.client.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ema
 */
public class PopulateToTableViewHandler {

    private final StringProperty fileName;
    private final StringProperty access;
    private final StringProperty owner;
    private final StringProperty permission;
    private final StringProperty size;

    public PopulateToTableViewHandler(String fileName, String access, String owner, String permission, String size) {
        this.fileName = new SimpleStringProperty(fileName);
        this.access = new SimpleStringProperty(access);
        this.owner = new SimpleStringProperty(owner);
        this.permission = new SimpleStringProperty(permission);
        this.size = new SimpleStringProperty(size);
    }

    public String getFileName() {
        return fileName.get();
    }

    public String getAccess() {
        return access.get();
    }

    public String getOwner() {
        return owner.get();
    }

    public String getPermission() {
        return permission.get();
    }

    public String getSize() {
        return size.get();
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public void setAccess(String access) {
        this.access.set(access);
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public void setPermission(String permission) {
        this.permission.set(permission);
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public StringProperty accessProperty() {
        return access;
    }

    public StringProperty ownerProperty() {
        return owner;
    }

    public StringProperty permissionProperty() {
        return permission;
    }

    public StringProperty sizeProperty() {
        return size;
    }

}
