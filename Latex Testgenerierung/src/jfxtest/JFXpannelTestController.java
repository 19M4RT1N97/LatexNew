/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxtest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.table.DefaultTableModel;

/**
 * FXML Controller class
 *
 * @author Martin
 */
public class JFXpannelTestController implements Initializable {

    static DefaultTableModel themaliste;
    @FXML
    private Tab TabbedPaneTopic;
    @FXML
    private Button BTNaddthema;
    @FXML
    private Button BTNdelthema;
    @FXML
    private ComboBox<?> CBOThemen;
    @FXML
    private ListView<?> LVthemen;
    @FXML
    private Tab TabAnzahl;
    @FXML
    private TextField TFfragenanzahl;
    @FXML
    private Label LBLmaxanzahl;
    @FXML
    private Tab SchwierigkeitenTab;
    @FXML
    private Label LBLdiff1;
    @FXML
    private Label LBLdiff2;
    @FXML
    private Label LBLdiff3;
    @FXML
    private TextField TFdiff1;
    @FXML
    private TextField TFdiff2;
    @FXML
    private TextField TFdiff3;
    @FXML
    private MenuItem MenuÖffnen;
    @FXML
    private MenuItem MenuClose;
    @FXML
    private TextField TFFragenSuche;
    @FXML
    private Button BTNSuche;
    @FXML
    private Button BTNLöschen;
    private TextArea TAfragenwahl;
    @FXML
    private ListView<?> LVfragenwahl;
    @FXML
    private Tab AbwahlTab;
    @FXML
    private Tab FertigTab;
    @FXML
    private Button BTNspeichern;
    @FXML
    private Button BTNnew;
    @FXML
    private TabPane TabbedPane;
    @FXML
    private AnchorPane out_Anchorpane;
    @FXML
    private Pane out_Pane;
    @FXML
    private MenuBar menubar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList ol = FXCollections.observableArrayList();
        String ha = "Name\t\t\t\tTopic\t\t\tSchwierigkeit";
        ol.add(ha);
        LVfragenwahl.getItems().addAll(ol);
        TabAnzahl.setDisable(true);
        SchwierigkeitenTab.setDisable(true);
        AbwahlTab.setDisable(true);
        FertigTab.setDisable(true);
    }

    @FXML
    private void TabAnzahl_OnTabChange(Event event) {
        themaliste = new DefaultTableModel();
        themaliste.addColumn("themen");
        ObservableList ol = FXCollections.observableArrayList();
        ol = LVthemen.getItems();
        ListIterator li = ol.listIterator();
        while (li.hasNext()) {
            String line = (String) li.next();
            themaliste.addRow(new String[]{line});
        }
        int fragencount = Util.getThemencount(themaliste);
        LBLmaxanzahl.setText(fragencount + "");
    }

    @FXML
    private void Tabdiff_OnSelect(Event event) {
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("themen");
        ObservableList ol = FXCollections.observableArrayList();
        ol = LVthemen.getItems();
        ListIterator li = ol.listIterator();
        while (li.hasNext()) {
            String line = (String) li.next();
            dm.addRow(new String[]{line});
        }
        int[] difcount = Util.getDifMaxCount(dm);
        LBLdiff1.setText(difcount[0] + "");
        LBLdiff2.setText(difcount[1] + "");
        LBLdiff3.setText(difcount[2] + "");

    }

    @FXML
    private void AbwahlTab_OnSelect(Event event) {
        Util.DiffFragenanzahl(TFdiff1.getText(), TFdiff2.getText(), TFdiff3.getText());

    }

    @FXML
    private void Fertig_OnSelect(Event event) {
    }

    @FXML
    private void BTNaddthema_OnAction(ActionEvent event) {

        ObservableList ol = FXCollections.observableArrayList();
        if (CBOThemen.getValue() != null) {
            ol.add(CBOThemen.getValue());
            LVthemen.getItems().addAll(ol);
            CBOThemen.getItems().remove(CBOThemen.getValue());
            CBOThemen.getSelectionModel().select(0);
        }
        if (LVthemen.getItems().isEmpty()) {
            TabAnzahl.setDisable(true);
        } else {
            TabAnzahl.setDisable(false);
        }

    }

    @FXML
    private void BTNdelthema_OnAction(ActionEvent event) {

        int index = LVthemen.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            ObservableList ol = FXCollections.observableArrayList();
            ol.add(LVthemen.getSelectionModel().getSelectedItem());
            CBOThemen.getItems().addAll(ol);
            LVthemen.getItems().remove(index);
            CBOThemen.getSelectionModel().select(0);
        }
        if (LVthemen.getItems().isEmpty()) {
            TabAnzahl.setDisable(true);
        } else {
            TabAnzahl.setDisable(false);
        }
    }

    @FXML
    private void MenuOeffnen_OnAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open TEX File");

        FileChooser.ExtensionFilter exf = new FileChooser.ExtensionFilter("Dateiname", "*.tex");
        fileChooser.getExtensionFilters().add(exf);
        File f = fileChooser.showOpenDialog(new Stage());

        if (f != null) {
            Util.readFile(f.getAbsolutePath());

        }

        LinkedList<String> themen = Util.themenRead();
        ObservableList ol = FXCollections.observableArrayList();

        for (String thema : themen) {
            ol.add(thema);

        }

        CBOThemen.setItems(ol);
        CBOThemen.getSelectionModel().select(0);

    }

    @FXML
    private void MenuClose_OnAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void TFanzahl_OnKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.ENTER) {
            if (event.getCode() == KeyCode.BACK_SPACE && TFfragenanzahl.getText().isEmpty()) {
                SchwierigkeitenTab.setDisable(true);
            }
        } else {
            try {
                int anzahl = Integer.parseInt(TFfragenanzahl.getText());
                if (!TFfragenanzahl.getText().isEmpty()) {
                    SchwierigkeitenTab.setDisable(false);
                    if (anzahl > Integer.parseInt(LBLmaxanzahl.getText())) {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Bitte geben Sie eine gültige Zahl ein.", ButtonType.CLOSE);
                        a.show();
                        SchwierigkeitenTab.setDisable(true);
                        TFfragenanzahl.setText("");
                    }
                } else {
                    SchwierigkeitenTab.setDisable(true);
                }
            } catch (NumberFormatException e) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Bitte geben Sie eine gültige Zahl ein.", ButtonType.CLOSE);
                a.show();
            }
        }
    }

    @FXML
    private void TFdiff1_KeyReleased(KeyEvent event
    ) {
        try {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                if (TFdiff1.getText().isEmpty() && TFdiff2.getText().isEmpty() && TFdiff3.getText().isEmpty()) {
                    AbwahlTab.setDisable(true);
                }
            } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {

            } else {

                int istzahl = Integer.parseInt(TFdiff1.getText());

                if (istzahl > Integer.parseInt(LBLdiff1.getText())) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Die eingegebene Zahl ist zu groß!", ButtonType.CLOSE);
                    a.show();
                    String s = TFdiff1.getText();
                    TFdiff1.setText(s.substring(0, s.length() - 1));

                }
            }
            if (TFdiff1.getText().isEmpty() && TFdiff2.getText().isEmpty() && TFdiff3.getText().isEmpty()) {
                AbwahlTab.setDisable(true);
            } else {
                AbwahlTab.setDisable(false);
            }

        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Bitte geben Sie eine gültige Zahl ein.", ButtonType.CLOSE);
            a.show();
            String s = TFdiff1.getText();
            TFdiff1.setText(s.substring(0, s.length() - 1));

        }
    }

    @FXML
    private void TFdiff2_KeyReleased(KeyEvent event
    ) {
        try {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                if (TFdiff1.getText().isEmpty() && TFdiff2.getText().isEmpty() && TFdiff3.getText().isEmpty()) {
                    AbwahlTab.setDisable(true);
                }
            } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {

            } else {

                int istzahl = Integer.parseInt(TFdiff2.getText());

                if (istzahl > Integer.parseInt(LBLdiff2.getText())) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Die eingegebene Zahl ist zu groß!", ButtonType.CLOSE);
                    a.show();
                    String s = TFdiff2.getText();
                    TFdiff2.setText(s.substring(0, s.length() - 1));

                }
            }
            if (TFdiff1.getText().isEmpty() && TFdiff2.getText().isEmpty() && TFdiff3.getText().isEmpty()) {
                AbwahlTab.setDisable(true);
            } else {

                AbwahlTab.setDisable(false);
            }
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Bitte geben Sie eine gültige Zahl ein.", ButtonType.CLOSE);
            a.show();
            String s = TFdiff2.getText();
            TFdiff2.setText(s.substring(0, s.length() - 1));

        }
    }

    @FXML
    private void TFdiff3_KeyReleased(KeyEvent event
    ) {
        try {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                if (TFdiff1.getText().isEmpty() && TFdiff2.getText().isEmpty() && TFdiff3.getText().isEmpty()) {
                    AbwahlTab.setDisable(true);
                }
            } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {

            } else {

                int istzahl = Integer.parseInt(TFdiff3.getText());

                if (istzahl > Integer.parseInt(LBLdiff3.getText())) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Die eingegebene Zahl ist zu groß!", ButtonType.CLOSE);
                    a.show();
                    String s = TFdiff3.getText();
                    TFdiff3.setText(s.substring(0, s.length() - 1));
                }
            }
            if (TFdiff1.getText().isEmpty() && TFdiff2.getText().isEmpty() && TFdiff3.getText().isEmpty()) {
                AbwahlTab.setDisable(true);
            } else {

                AbwahlTab.setDisable(false);
            }
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Bitte geben Sie eine gültige Zahl ein.", ButtonType.CLOSE);
            a.show();
            String s = TFdiff3.getText();
            TFdiff3.setText(s.substring(0, s.length() - 1));

        }
    }

    @FXML
    private void BTNSuche_OnAction(ActionEvent event
    ) {
        ObservableList ol = FXCollections.observableArrayList();
        DefaultTableModel questiontable = new DefaultTableModel(new String[]{"Name", "Topic", "Schwierigkeit"}, 0);
        DefaultTableModel questiontabletemp = new DefaultTableModel(new String[]{"Name", "Topic", "Schwierigkeit"}, 0);
        if (TFFragenSuche.getText().isEmpty()) {
            questiontable = Util.fillTable(questiontabletemp, themaliste);

        } else {
            questiontable = Util.fillTable(questiontabletemp, Integer.parseInt(TFFragenSuche.getText()));
        }

        ol.clear();
        String ha = "";
        if (questiontable != null) {
            for (int tabler = 0; tabler < questiontable.getRowCount(); tabler++) {
                for (int tablec = 0; tablec < questiontable.getColumnCount(); tablec++) {
                    ha += questiontable.getValueAt(tabler, tablec) + "\t\t";
                    if (tablec == 0 && ha.length() > 25) {
                        ha = (String) ha.subSequence(0, 15) + "\t\t";
                    }
                    if (tablec == 1 && ha.length() < 28) {
                        ha += "\t";
                    }
                }

                ol.add(ha);
                LVfragenwahl.getItems().addAll(ol);
                ha = "";
                ol.clear();
            }

        }
        if (LVfragenwahl.getItems().size() > 1) {
            FertigTab.setDisable(false);
        }
    }

    @FXML
    private void BTNLoeschen_OnAction(ActionEvent event
    ) {

        if (LVfragenwahl.getSelectionModel().getSelectedIndex() > 0) {
            LVfragenwahl.getItems().remove(LVfragenwahl.getSelectionModel().getSelectedIndex());
            Util.TableLoeschen(LVfragenwahl.getSelectionModel().getSelectedIndex());
        }
        if (LVfragenwahl.getItems().size() <= 1) {
            FertigTab.setDisable(true);
        }
    }

    @FXML
    private void BTNspeichern_OnAction(ActionEvent event
    ) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save TEX File");
        FileChooser.ExtensionFilter exf = new FileChooser.ExtensionFilter("Dateiname", "*.tex");
        fileChooser.getExtensionFilters().add(exf);
        File f = fileChooser.showSaveDialog(new Stage());
        if (f != null) {
            try {
                Util.createLaTexDoc(f.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(JFXpannelTestController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void BTNnew_OnAction(ActionEvent event
    ) {
        LVthemen.getItems().clear();
        LVfragenwahl.getItems().remove(1, LVfragenwahl.getItems().size() - 1);
        TFdiff1.setText("");
        TFdiff2.setText("");
        TFdiff3.setText("");
        TFfragenanzahl.setText("");
        TFFragenSuche.setText("");
        CBOThemen.getItems().clear();
        Util.reset();
        TabAnzahl.setDisable(true);
        SchwierigkeitenTab.setDisable(true);
        AbwahlTab.setDisable(true);
        FertigTab.setDisable(true);
        TabbedPane.getSelectionModel().selectFirst();
    }

}
