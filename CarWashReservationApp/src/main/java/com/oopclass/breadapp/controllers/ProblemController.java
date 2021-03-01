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
import com.oopclass.breadapp.models.Problem;
import com.oopclass.breadapp.services.impl.ProblemService;
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
public class ProblemController implements Initializable {

    @FXML
    private Label problemId;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField middleName;

    @FXML
    private TextField address;

    @FXML
    private TextField service;

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
    private Button reset;

    @FXML
    private Button back;

    @FXML
    private Button saveProblem;

    @FXML
    private RadioButton rbYes;

    @FXML
    private RadioButton rbNo;

    @FXML
    private RadioButton rbUpfront;

    @FXML
    private RadioButton rbAfter;

    @FXML
    private TableView<Problem> problemTable;

    @FXML
    private TableColumn<Problem, Long> colProblemId;

    @FXML
    private TableColumn<Problem, String> colFirstName;

    @FXML
    private TableColumn<Problem, String> colLastName;

    @FXML
    private TableColumn<Problem, String> colMiddleName;

    @FXML
    private TableColumn<Problem, String> colAddress;

    @FXML
    private TableColumn<Problem, LocalDate> colDOR;

    @FXML
    private TableColumn<Problem, String> colService;

    @FXML
    private TableColumn<Problem, String> colPaymentTime;

    @FXML
    private TableColumn<Problem, LocalDate> colCreatedAt;

    @FXML
    private TableColumn<Problem, String> colRefund;

    @FXML
    private TableColumn<Problem, String> colCancel;

    @FXML
    private TableColumn<Problem, String> colReason;
    
    @FXML
    private TableColumn<Problem, String> colLateCustomer;

    @FXML
    private TableColumn<Problem, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private ProblemService problemService;

    private ObservableList<Problem> problemList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }
    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void returnHome(ActionEvent event) {
        stageManager.switchScene(FxmlView.RESERVATION);
    }

    @FXML
    private void saveProblems(ActionEvent event) {

        if (validate("First Name", getFirstName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Last Name", getLastName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Middle Name", getMiddleName(), "([a-zA-Z]{3,30}\\s*)+")) {

            if (problemId.getText() == null || "".equals(problemId.getText())) {
                if (true) {

                    Problem problem = new Problem();
                    problem.setFirstName(getFirstName());
                    problem.setLastName(getLastName());
                    problem.setMiddleName(getMiddleName());
                    problem.setAddress(getAddress());
                    problem.setDor(getDor());
                    problem.setService(getService());
                    problem.setPaymentTime(getPaymentTime());
                    problem.setCreatedAt(getCreatedAt());
                    problem.setRefund(getRefund());
                    problem.setCancelAnswer(getCancelAnswer());
                    problem.setCancelReason(getCancelReason());
                    problem.setLateCustomer(getLateCustomer());

                    Problem newProblem = problemService.save(problem);

                    saveAlert(newProblem);
                }

            } else {
                Problem problem = problemService.find(Long.parseLong(problemId.getText()));
                problem.setFirstName(getFirstName());
                problem.setLastName(getLastName());
                problem.setMiddleName(getMiddleName());
                problem.setAddress(getAddress());
                problem.setDor(getDor());
                problem.setService(getService());
                problem.setPaymentTime(getPaymentTime());
                problem.setCreatedAt(getCreatedAt());
                problem.setRefund(getRefund());
                problem.setCancelAnswer(getCancelAnswer());
                problem.setCancelReason(getCancelReason());
                problem.setLateCustomer(getLateCustomer());
                Problem updatedProblem = problemService.update(problem);
                updateAlert(updatedProblem);
            }

            clearFields();
            loadProblemDetails();
        }

    }

    @FXML
    private void deleteProblems(ActionEvent event) {
        List<Problem> problems = problemTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            problemService.deleteInBatch(problems);
        }

        loadProblemDetails();
    }

    private void clearFields() {
        problemId.setText(null);
        firstName.clear();
        lastName.clear();
        middleName.clear();
        address.clear();
        dor.getEditor().clear();
        rbYes.setSelected(false);
        rbNo.setSelected(false);
        rbUpfront.setSelected(false);
        rbAfter.setSelected(false);
        created.getEditor().clear();
        lateCustomer.clear();
        cancelAnswer.clear();
        cancelReason.clear();
        service.setText("Repair");

    }

    private void saveAlert(Problem problem) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Problem saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The problem " + problem.getFirstName() + " " + problem.getLastName() + " has been created and \n" + " id is " + problem.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Problem problem) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Problem updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The problem " + problem.getFirstName() + " " + problem.getLastName() + " has been updated.");
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

    public String getService() {
        return service.getText();
    }

    public String getPaymentTime() {
        return rbUpfront.isSelected() ? "Upfront" : "After";
    }

    public String getRefund() {
        return rbYes.isSelected() ? "Yes" : "No";
    }
    
    public String getCancelAnswer() {
        return cancelAnswer.getText();
    }
    
    public String getCancelReason() {
        return cancelReason.getText();
    }
    
    public String getLateCustomer() {
        return lateCustomer.getText();
    }

    /*
	 *  Set All problemTable column properties
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

        colProblemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOR.setCellValueFactory(new PropertyValueFactory<>("dor"));
        colService.setCellValueFactory(new PropertyValueFactory<>("service"));
        colPaymentTime.setCellValueFactory(new PropertyValueFactory<>("paymentTime"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colRefund.setCellValueFactory(new PropertyValueFactory<>("refund"));
        colLateCustomer.setCellValueFactory(new PropertyValueFactory<>("lateCustomer"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Problem, Boolean>, TableCell<Problem, Boolean>> cellFactory
            = new Callback<TableColumn<Problem, Boolean>, TableCell<Problem, Boolean>>() {
        @Override
        public TableCell<Problem, Boolean> call(final TableColumn<Problem, Boolean> param) {
            final TableCell<Problem, Boolean> cell = new TableCell<Problem, Boolean>() {
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
                            Problem problem = getTableView().getItems().get(getIndex());
                            updateProblem(problem);
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

                private void updateProblem(Problem problem) {
                    problemId.setText(Long.toString(problem.getId()));
                    firstName.setText(problem.getFirstName());
                    lastName.setText(problem.getLastName());
                    middleName.setText(problem.getMiddleName());
                    address.setText(problem.getAddress());
                    dor.setValue(problem.getDor());
                    created.setValue(problem.getCreatedAt());
                    cancelAnswer.setText(problem.getCancelAnswer());
                    cancelReason.setText(problem.getCancelReason());
                    lateCustomer.setText(problem.getLateCustomer());
                    service.setText(problem.getService());
                    if (problem.getPaymentTime().equals("Upfront")) {
                        rbUpfront.setSelected(true);
                    } else {
                        rbAfter.setSelected(true);
                    }
                    if (problem.getRefund().equals("Yes")) {
                        rbYes.setSelected(true);
                    } else {
                        rbNo.setSelected(true);
                    }

                }
            };
            return cell;
        }
    };

    /*
	 *  Add All problems to observable list and update table
     */
    private void loadProblemDetails() {
        problemList.clear();
        problemList.addAll(problemService.findAll());

        problemTable.setItems(problemList);
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

        problemTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

//        // Add all problems into table
        loadProblemDetails();
    }
}
