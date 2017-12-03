/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1212.homework3.adisu.rmi.client.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author ema
 */
public class MainFXMLController implements Initializable {

    private final ObservableList<String> accessType = FXCollections.observableArrayList("Private", "Public");
    String user, message;
    private static final String CURRENT_DIR = "/Users/ema/Downloads/";

    @FXML
    private AnchorPane panDownload;
    @FXML
    private AnchorPane panAddFile;
    @FXML
    private AnchorPane panSearchFiles;
    @FXML
    private AnchorPane panViewAll;
    @FXML
    private ListView listview;
    @FXML
    private Button btnAddFile, btnSearchFiles, btnViewAll, btnDownload, btnLogout;
    @FXML
    private Label lblUser, lblMessage, lblConfirm;
    @FXML
    private ComboBox cmBoxAccess;
    @FXML
    private JFXTextField txtFileName;
    @FXML
    private CheckBox cBoxRead, cBoxWrite;
    @FXML
    private Button btnAvailableFiles;
    @FXML
    private TableView<PopulateToTableViewHandler> tblView;
    @FXML
    private TableColumn<PopulateToTableViewHandler, String> columnFileName;
    @FXML
    private TableColumn<PopulateToTableViewHandler, String> columnAccess;
    @FXML
    private TableColumn<PopulateToTableViewHandler, String> columnOwner;
    @FXML
    private TableColumn<PopulateToTableViewHandler, String> columnPerm;
    @FXML
    private TableColumn<PopulateToTableViewHandler, String> columnSize;
    private ObservableList<PopulateToTableViewHandler> toTable;

    @FXML
    private void btnHandlePans(MouseEvent event) {
        if (event.getTarget() == btnAddFile) {
            panSearchFiles.setVisible(false);
            panViewAll.setVisible(false);
            panDownload.setVisible(false);
            panAddFile.setVisible(true);
        } else if (event.getTarget() == btnSearchFiles) {
            panViewAll.setVisible(false);
            panDownload.setVisible(false);
            panAddFile.setVisible(false);
            panSearchFiles.setVisible(true);
        } else if (event.getTarget() == btnViewAll) {
            panDownload.setVisible(false);
            panAddFile.setVisible(false);
            panSearchFiles.setVisible(false);
            panViewAll.setVisible(true);
        } else if (event.getTarget() == btnDownload) {
            panViewAll.setVisible(false);
            panAddFile.setVisible(false);
            panSearchFiles.setVisible(false);
            panDownload.setVisible(true);
        }
    }

    @FXML
    private void btnBrowseFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(CURRENT_DIR));
        chooser.getExtensionFilters().addAll(new ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = chooser.showOpenDialog(null);
        if (selectedFile != null) {
            listview.getItems().add(selectedFile.getAbsolutePath());
        } else {
            System.out.println("Filse is not valid");
        }
    }

    @FXML
    private void btnUploadFile(ActionEvent event) {

        String path = (String) listview.getSelectionModel().getSelectedItem();
        System.out.println("The path is; " + path);
        String filename = txtFileName.getText();
        String access = cmBoxAccess.getSelectionModel().getSelectedItem().toString();
        String permission = "";
        if (cBoxRead.isSelected()) {
            permission = cBoxRead.getText() + " ";
        }
        if (cBoxWrite.isSelected()) {
            permission += cBoxWrite.getText();
        }
        try {
            int rs = CallRMIServer.lookupUpload(filename, access, getUser(), permission, path);
            if (rs == 0) {
                message = "Uploaded Successfully!";
                new MessageHandlerTask().start();
            } else {
                message = "Unable to upload! Please try again...";
                new MessageHandlerTask().start();
            }
        } catch (Exception ex) {
            System.err.println("Error:... " + ex);
        }
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        PopulateToTableViewHandler getVal = tblView.getSelectionModel().getSelectedItem();
        String filename = getVal.getFileName();
        String permission = getVal.getPermission();
        String owner = getVal.getOwner();
        if (!owner.equals(user) && permission.equalsIgnoreCase("Read ")) {
            lblConfirm.setText("File is read only!");
        } else {
            try {
                int rs = CallRMIServer.lookupDelete(filename);
                lblConfirm.setText("Rows " + rs + " Affected");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void btnDownload(ActionEvent event) {
        PopulateToTableViewHandler getVal = tblView.getSelectionModel().getSelectedItem();
        String filename = getVal.getFileName();
        String permission = getVal.getPermission();
        String owner = getVal.getOwner();

        if (!owner.equals(user) && permission.equalsIgnoreCase("Read ")) {
            lblConfirm.setText("File is read only!");
        } else {
            try {
                CallRMIServer.lookupServer();
                byte[] content = CallRMIServer.server.download(filename);
                File file = new File(filename + ".pdf");
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    fileOut.write(content);
                    lblConfirm.setText("File Downloaded as " + filename + ".pdf");
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void btnLogoutHandler(ActionEvent event) {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnViewAll(ActionEvent event) throws Exception {
        lblConfirm.setText("Please select a file before doing any operation!");
        CallRMIServer.lookupServer();
        List list = CallRMIServer.server.viewAll(user);
        toTable = FXCollections.observableArrayList();
        for (int i = 0; i < list.size() - 4; i += 5) {
            toTable.add(new PopulateToTableViewHandler(String.valueOf(list.get(i)), String.valueOf(list.get(i + 1)),
                    String.valueOf(list.get(i + 2)), String.valueOf(list.get(i + 3)),
                    convertToKB(Integer.parseInt(String.valueOf(list.get(i + 4))))));
        }
        columnFileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        columnAccess.setCellValueFactory(new PropertyValueFactory<>("access"));
        columnOwner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        columnPerm.setCellValueFactory(new PropertyValueFactory<>("permission"));
        columnSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        tblView.setItems(null);
        tblView.setItems(toTable);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmBoxAccess.setValue("Private");
        cmBoxAccess.setItems(accessType);
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        lblUser.setText(user.toUpperCase(Locale.ENGLISH));
        this.user = user;
    }

    private String convertToKB(int bytes) {
        double rs = (bytes / 1024);
        return rs + " KB";
    }

    private class MessageHandlerTask extends Service<String> {

        private MessageHandlerTask() {
            setOnSucceeded((WorkerStateEvent e) -> {
                String val = (String) e.getSource().getValue();
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
                protected String call() {
                    return message;
                }
            };
        }

    }
}
