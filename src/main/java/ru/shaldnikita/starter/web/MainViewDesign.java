package ru.shaldnikita.starter.web;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ComboBox;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class MainViewDesign extends VerticalLayout {
    protected HorizontalLayout top;
    protected ComboBox<String> appSelection;
    protected Label viewName;
    protected CssLayout topRightButtons;
    protected Button logout;
    protected HorizontalLayout main;
    protected VerticalLayout menu;
    protected CssLayout buttons;
    protected Button profileButton;
    protected Button settingsButton;
    protected Button sysLogButton;
    protected Button usersButton;
    protected Button rolesButton;
    protected Button devicesButton;
    protected Button mapButton;
    protected Button dashboardButton;
    protected Button reportListButton;
    protected Button reportConfigButton;
    protected VerticalLayout content;

    public MainViewDesign() {
        Design.read(this);
    }
}