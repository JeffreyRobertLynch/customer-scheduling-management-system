package jrl.qam2final.Controller;

import jrl.qam2final.DAO.ReportDAO;
import jrl.qam2final.DAO.ReportDAOIMPL;
import jrl.qam2final.Helper.JDBCHelper;
import javafx.fxml.Initializable;
import java.io.IOException;
import javafx.scene.Scene;
import jrl.qam2final.Helper.TranslationManagerHelper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jrl.qam2final.Model.ReportCustomerMonthlyModel;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.net.URL;

/**
 * Controller class for the Customer and Month Screen. I paid close attention to setting up all FXML elements with clear
 * IDs and annotations. This will make it easier to establish translation keys for use with the TranslationManagerHelper
 * class in the future and achieve full multi-language support for all FXML elements in the GUI. Though this proactive
 * approach will help a great deal towards full multi-language support, translating dynamic values will require more
 * time and effort.
 *
 * @author Jeffrey Robert Lynch
 */
public class ReportCustomerMonthlyController implements Initializable {

    @FXML
    public TableView<ReportCustomerMonthlyModel> reportCustomerMonthTableView;
    @FXML
    public Label customerMonthTitleLabel;
    @FXML
    public TableColumn<ReportCustomerMonthlyModel, String> reportYearColumn;
    @FXML
    public TableColumn<ReportCustomerMonthlyModel, String> reportCustomerIdColumn;
    @FXML
    public TableColumn<ReportCustomerMonthlyModel, String> reportMonthColumn;
    @FXML
    public TableColumn<ReportCustomerMonthlyModel, String> reportCustomerNameColumn;
    @FXML
    public TableColumn<ReportCustomerMonthlyModel, Integer> reportCountColumn;
    @FXML
    public Button onActionRCuMCToRMCButton;

    /**
     * Method to handle navigation to the Reports Main Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Reports Main" button.
     */
    @FXML
    private void onActionReportMainRCuMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/ReportMainFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Loaded FXML: " + fxmlFileName);
        } catch (Exception e) {
            displayErrorRCuMC("Failed to load FXML");
        }
    }

    /**
     * Helper method that displays error messages for methods in the ReportCustomerMonthlyController class. It takes
     * customMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param customMessage Custom error message displayed to user
     */
    public void displayErrorRCuMC(String customMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Error Encountered");
        alert.setContentText(customMessage);
        alert.showAndWait();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of ReportDAOIMPL.
     */
    private ReportDAO createReportDAORCuMC() {
        return new ReportDAOIMPL();
    }

    /**
     * Initialization for the ReportCustomerMonthlyController class. Establishes table columns and populates the table
     * with data from the database to generate a report of appointments by customer and month.
     *
     * @param url            URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBCHelper.startDBConnection();
        ReportDAO newReportDao =  createReportDAORCuMC();
        reportCustomerMonthTableView.setItems(newReportDao.readReportCustomerMonthlyRepDAO());
        reportMonthColumn.setCellValueFactory(new PropertyValueFactory<>("month_ST"));
        reportCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("custID_IN"));
        reportCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("custN_ST"));
        reportCountColumn.setCellValueFactory(new PropertyValueFactory<>("count_IN"));
        reportYearColumn.setCellValueFactory(new PropertyValueFactory<>("year_IN"));
    }
}