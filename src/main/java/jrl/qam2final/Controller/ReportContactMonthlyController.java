package jrl.qam2final.Controller;

import jrl.qam2final.DAO.ReportDAO;
import jrl.qam2final.DAO.ReportDAOIMPL;
import jrl.qam2final.Helper.JDBCHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import jrl.qam2final.Helper.TranslationManagerHelper;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jrl.qam2final.Model.ReportContactMonthlyModel;
import javafx.fxml.FXML;
import java.net.URL;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Controller class for the Contact and Month Screen. I paid close attention to setting up all FXML elements with clear
 * IDs and annotations. This will make it easier to establish translation keys for use with the TranslationManagerHelper
 * class in the future and achieve full multi-language support for all FXML elements in the GUI. Though this proactive
 * approach will help a great deal towards full multi-language support, translating dynamic values will require more
 * time and effort.
 *
 * @author Jeffrey Robert Lynch
 */
public class ReportContactMonthlyController implements Initializable {

    @FXML
    public Button onActionRCoMCToRMCButton;
    @FXML
    public TableColumn<ReportContactMonthlyModel, Integer> reportCountColumn;
    @FXML
    public TableColumn<ReportContactMonthlyModel, String> reportContactColumn;
    @FXML
    public TableColumn<ReportContactMonthlyModel, String> reportYearColumn;
    @FXML
    public TableColumn<ReportContactMonthlyModel, String> reportMonthColumn;
    @FXML
    public TableView<ReportContactMonthlyModel> reportContactMonthTableView;
    @FXML
    public Label contactMonthTitleLabel;

    /**
     * Method to handle navigation to the Reports Main Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Reports Main" button.
     */
    @FXML
    private void onActionNavigateReportMainRCoMC(ActionEvent actionEvent) {
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
            displayErrorRCoMC("Failed to load FXML");
        }
    }

    /**
     * Helper method that displays error messages for methods in the ReportContactMonthlyController class. It takes
     * customMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param customMessage Custom error message displayed to user
     */
    public void displayErrorRCoMC(String customMessage) {
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
    private ReportDAO createReportDAORCoMC() {
        return new ReportDAOIMPL();
    }

    /**
     * Initialization for the ReportContactMonthlyController class. Establishes table columns and populates the table
     * with data from the database to generate a report of appointments by contact and month.
     *
     * @param url            URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBCHelper.startDBConnection();
        ReportDAO newReportDao =  createReportDAORCoMC();
        reportContactMonthTableView.setItems(newReportDao.readReportContactMonthlyRepDAO());
        reportMonthColumn.setCellValueFactory(new PropertyValueFactory<>("month_ST"));
        reportContactColumn.setCellValueFactory(new PropertyValueFactory<>("contN_ST"));
        reportCountColumn.setCellValueFactory(new PropertyValueFactory<>("count_IN"));
        reportYearColumn.setCellValueFactory(new PropertyValueFactory<>("year_IN"));
    }
}