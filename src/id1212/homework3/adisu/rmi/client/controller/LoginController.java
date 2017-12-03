/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1212.homework3.adisu.rmi.client.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author ema
 */
public class LoginController implements Initializable {

    String message = "", signMessage = "";
    private static final String PATH = "/id1212/homework3/adisu/rmi/client/view/MainFXMLDocument.fxml";
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnLogIn;
    @FXML
    private AnchorPane panSignUp;
    @FXML
    private AnchorPane panLogin;
    @FXML
    private JFXTextField txtUser, txtUserName, txtFullName, txtEmail;
    @FXML
    private JFXPasswordField txtPass, txtPassword;
    @FXML
    private Label lblMessage;
    @FXML
    private Label logMessage;

    @FXML
    private void handlePane(MouseEvent event) {
        if (event.getTarget() == btnSignUp) {
            panLogin.setVisible(false);
            panSignUp.setVisible(true);
        } else if (event.getTarget() == btnLogin) {
            panSignUp.setVisible(false);
            panLogin.setVisible(true);
        }
    }

    @FXML
    private void handleRegistration(ActionEvent event) {
        new RegistrationHandler().start();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        if (txtUser.getText().equals("") || txtPass.getText().equals("")) {
            clearText();
            message = "Please fill the form";
            new LoginHandler().start();
        } else {
           // message = "Authenticating Your credentials...";
            //new LoginHandler().start();
            boolean check = false;
            try {
                check = CallRMIServer.lookupLogin(txtUser.getText(), txtPass.getText());
            } catch (Exception ex) {
                message = "Error: " + ex;
            }
            if (check) {
                try {
                    lblMessage.setText("");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH));
                    Parent root1 = (Parent) loader.load();
                    MainFXMLController cont = loader.getController();
                    cont.setUser(txtUser.getText());
                    Stage stage = new Stage();
                    stage.setTitle("Client Pannel");
                    stage.setScene(new Scene(root1));
                    stage.show();
                    clearText();
                } catch (IOException ex) {
                    System.out.println("Error; " + ex);
                }
            } else {
                clearText();
                message = "Incorrect User Name or Password!";
                new LoginHandler().start();
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void clearText() {
        txtUser.setText("");
        txtPass.setText("");
    }

    public String getUser() {
        return txtUser.getText();
    }

    private class LoginHandler extends Service<String> {

        private LoginHandler() {
            setOnSucceeded((WorkerStateEvent e) -> {
                String val = (String) e.getSource().getValue();
                logMessage.setText(val);
            });
            setOnFailed((WorkerStateEvent e) -> {
                System.out.println("failed...");
            });
        }

        @Override
        protected Task<String> createTask() {

            return new Task<String>() {
                @Override
                protected String call() throws IOException, InterruptedException, ClassNotFoundException {
                    return message;
                }
            };
        }
    }

    private class RegistrationHandler extends Service<String> {

        private RegistrationHandler() {
            setOnSucceeded((WorkerStateEvent e) -> {
                String val = (String) e.getSource().getValue();
                System.out.println("Got: " + val);
                lblMessage.setText(val);
            });
            setOnFailed((WorkerStateEvent e) -> {
                System.out.println("failed...");
            });
        }

        @Override
        protected Task<String> createTask() {

            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    String fname = txtFullName.getText();
                    String uname = txtUserName.getText();
                    String email = txtEmail.getText();
                    String password = txtPassword.getText();
                    try {
                        int rs = CallRMIServer.lookupCreateAccount(fname, uname, email, password);
                        if (rs == 0) {
                            signMessage = "Registerd Successfully!";
                        } else {
                            signMessage = "User name conflicted! Please try again";
                        }
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                    return signMessage;
                }
            };
        }

    }
}
