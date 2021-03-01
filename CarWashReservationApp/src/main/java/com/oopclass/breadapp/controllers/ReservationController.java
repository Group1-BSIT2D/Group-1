package com.oopclass.breadapp.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.oopclass.breadapp.config.StageManager;
import com.oopclass.breadapp.models.Reservation;
import com.oopclass.breadapp.services.impl.ReservationService;
import com.oopclass.breadapp.views.FxmlView;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Controller
public class ReservationController implements Initializable {

    @FXML
    private Label reservationId;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField middleName;

    @FXML
    private TextField address;
    
    @FXML
    private TextField cancelAnswer;
    
    @FXML
    private TextField cancelReason;
    
    @FXML
    private TextField lateCustomer;

    @FXML
    private DatePicker dor;

    @FXML
    private DatePicker created;

    @FXML
    private RadioButton rbCleaning;

    @FXML
    private RadioButton rbRepair;

    @FXML
    private RadioButton rbUpfront;

    @FXML
    private RadioButton rbAfter;

    @FXML
    private Button reset;
    
    @FXML
    private Button vehicleProblem;

    @FXML
    private Button saveReservation;

    @FXML
    private Button deleteReservation;

    @FXML
    private DatePicker timeUpdated;
    
    @FXML
    private Button cancelReservation;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Long> colReservationId;

    @FXML
    private TableColumn<Reservation, String> colFirstName;

    @FXML
    private TableColumn<Reservation, String> colLastName;

    @FXML
    private TableColumn<Reservation, String> colMiddleName;

    @FXML
    private TableColumn<Reservation, String> colAddress;

    @FXML
    private TableColumn<Reservation, LocalDate> colDOR;

    @FXML
    private TableColumn<Reservation, String> colService;

    @FXML
    private TableColumn<Reservation, String> colLateCustomer;

    @FXML
    private TableColumn<Reservation, String> colCancel;

    @FXML
    private TableColumn<Reservation, String> colReason;

    @FXML
    private TableColumn<Reservation, String> colPaymentTime;

    @FXML
    private TableColumn<Reservation, LocalDate> colCreatedAt;

    @FXML
    private TableColumn<Reservation, LocalDate> colTimeUpdated;

    @FXML
    private TableColumn<Reservation, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private ReservationService reservationService;

    private ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }
    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void cancelReservations (ActionEvent event){
        stageManager.switchScene(FxmlView.CANCEL);
    }
    
    @FXML
    private void vehicleProblems (ActionEvent event){
        stageManager.switchScene(FxmlView.PROBLEM);
    }
    

    @FXML
    private void saveReservation(ActionEvent event) {

        if (validate("First Name", getFirstName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Last Name", getLastName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Middle Name", getMiddleName(), "([a-zA-Z]{3,30}\\s*)+")) {

            if (reservationId.getText() == null || "".equals(reservationId.getText())) {
                if (true) {

                    Reservation reservation = new Reservation();
                    reservation.setFirstName(getFirstName());
                    reservation.setLastName(getLastName());
                    reservation.setMiddleName(getMiddleName());
                    reservation.setAddress(getAddress());
                    reservation.setDor(getDor());
                    reservation.setService(getService());
                    reservation.setLateCustomer(getLateCustomer());
                    reservation.setCancelAnswer(getCancelAnswer());
                    reservation.setCancelReason(getCancelReason());
                    reservation.setPaymentTime(getPaymentTime());
                    reservation.setCreatedAt(getCreatedAt());
                    reservation.setTimeUpdated(getTimeUpdated());

                    Reservation newReservation = reservationService.save(reservation);

                    saveAlert(newReservation);
                }

            } else {
                Reservation reservation = reservationService.find(Long.parseLong(reservationId.getText()));
                reservation.setFirstName(getFirstName());
                reservation.setLastName(getLastName());
                reservation.setMiddleName(getMiddleName());
                reservation.setAddress(getAddress());
                reservation.setDor(getDor());
                reservation.setService(getService());
                reservation.setLateCustomer(getLateCustomer());
                reservation.setCancelAnswer(getCancelAnswer());
                reservation.setCancelReason(getCancelReason());
                reservation.setPaymentTime(getPaymentTime());
                reservation.setCreatedAt(getCreatedAt());
                reservation.setTimeUpdated(getTimeUpdated());
                Reservation updatedReservation = reservationService.update(reservation);
                updateAlert(updatedReservation);
            }

            clearFields();
            loadReservationDetails();
        }

    }

    @FXML
    private void deleteReservations(ActionEvent event) {
        List<Reservation> reservations = reservationTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            reservationService.deleteInBatch(reservations);
        }

        loadReservationDetails();
    }

    private void clearFields() {
        reservationId.setText(null);
        firstName.clear();
        lastName.clear();
        middleName.clear();
        address.clear();
        dor.getEditor().clear();
        rbCleaning.setSelected(false);
        rbRepair.setSelected(false);
        lateCustomer.clear();
        rbUpfront.setSelected(false);
        rbAfter.setSelected(false);
        created.getEditor().clear();
        cancelAnswer.clear();
        cancelReason.clear();
        timeUpdated.getEditor().clear();

    }

    private void saveAlert(Reservation reservation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reservation saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The reservation " + reservation.getFirstName() + " " + reservation.getLastName() + " has been created and \n" + " id is " + reservation.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Reservation reservation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reservation updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The reservation " + reservation.getFirstName() + " " + reservation.getLastName() + " has been updated.");
        alert.showAndWait();
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public String getLastName() {
        return lastName.getText();
    }

    public String getMiddleName() {
        return middleName.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public LocalDate getDor() {
        return dor.getValue();
    }

    public LocalDate getCreatedAt() {
        return created.getValue();
    }

    public LocalDate getTimeUpdated() {
        return timeUpdated.getValue();
    }
    
    public String getCancelAnswer() {
        return cancelAnswer.getText();
    }
    
    public String getCancelReason() {
        return cancelReason.getText();
    }

    public String getService() {
        return rbCleaning.isSelected() ? "Cleaning" : "Repair";
    }

    public String getLateCustomer() {
        return lateCustomer.getText();
    }

    public String getPaymentTime() {
        return rbUpfront.isSelected() ? "Upfront" : "After";
    }

    /*
	 *  Set All reservationTable column properties
     */
    private void setColumnProperties() {
        /* Override date format in table
		  colTimeUpdated.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "HH:mm:ss";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/

        colReservationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOR.setCellValueFactory(new PropertyValueFactory<>("dor"));
        colService.setCellValueFactory(new PropertyValueFactory<>("service"));
        colLateCustomer.setCellValueFactory(new PropertyValueFactory<>("lateCustomer"));
        colCancel.setCellValueFactory(new PropertyValueFactory<>("cancelAnswer"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("cancelReason"));
        colPaymentTime.setCellValueFactory(new PropertyValueFactory<>("paymentTime"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colTimeUpdated.setCellValueFactory(new PropertyValueFactory<>("timeUpdated"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Reservation, Boolean>, TableCell<Reservation, Boolean>> cellFactory
            = new Callback<TableColumn<Reservation, Boolean>, TableCell<Reservation, Boolean>>() {
        @Override
        public TableCell<Reservation, Boolean> call(final TableColumn<Reservation, Boolean> param) {
            final TableCell<Reservation, Boolean> cell = new TableCell<Reservation, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            Reservation reservation = getTableView().getItems().get(getIndex());
                            updateReservation(reservation);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void updateReservation(Reservation reservation) {
                    reservationId.setText(Long.toString(reservation.getId()));
                    firstName.setText(reservation.getFirstName());
                    lastName.setText(reservation.getLastName());
                    middleName.setText(reservation.getMiddleName());
                    address.setText(reservation.getAddress());
                    dor.setValue(reservation.getDor());
                    created.setValue(reservation.getCreatedAt());
                    timeUpdated.setValue(reservation.getTimeUpdated());
                    cancelAnswer.setText(reservation.getCancelAnswer());
                    cancelReason.setText(reservation.getCancelReason());
                    lateCustomer.setText(reservation.getLateCustomer());
                    if (reservation.getService().equals("Cleaning")) {
                        rbCleaning.setSelected(true);
                    } else {
                        rbRepair.setSelected(true);
                    }
                    if (reservation.getPaymentTime().equals("Upfront")) {
                        rbUpfront.setSelected(true);
                    } else {
                        rbAfter.setSelected(true);
                    }
                    
                        

                }
            };
            return cell;
        }
    };

    /*
	 *  Add All reservations to observable list and update table
     */
    private void loadReservationDetails() {
        reservationList.clear();
        reservationList.addAll(reservationService.findAll());

        reservationTable.setItems(reservationList);
    }

    /*
	 * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        reservationTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

//        // Add all reservations into table
        loadReservationDetails();
    }
}
