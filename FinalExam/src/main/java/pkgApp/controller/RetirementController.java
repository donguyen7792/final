package pkgApp.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import javafx.beans.value.*;

import pkgApp.RetirementApp;
import pkgCore.Retirement;

public class RetirementController implements Initializable {

	private RetirementApp mainApp = null;
	@FXML
	private TextField txtSaveEachMonth;
	@FXML
	private TextField txtYearsToWork;
	@FXML
	private TextField txtAnnualReturnWorking;
	@FXML
	private TextField txtWhatYouNeedToSave;
	@FXML
	private TextField txtYearsRetired;
	@FXML
	private TextField txtAnnualReturnRetired;
	@FXML
	private TextField txtRequiredIncome;
	@FXML
	private TextField txtMonthlySSI;

	private HashMap<TextField, String> hmTextFieldRegEx = new HashMap<TextField, String>();

	public RetirementApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(RetirementApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		hmTextFieldRegEx.put(txtYearsToWork, "(0*)?([1-3]?\\d|40)");
		hmTextFieldRegEx.put(txtAnnualReturnWorking, "(0*)?(\\d?(\\.\\d*)?|10(\\.0*)?)");

		hmTextFieldRegEx.put(txtYearsRetired, "(0*)?(1?\\d|20)");
		hmTextFieldRegEx.put(txtAnnualReturnRetired, "(0*)?(\\d?(\\.\\d*)?|10(\\.0*)?)");
		hmTextFieldRegEx.put(txtRequiredIncome, "(0*)?((2(6(4[2-9]|[5-9]\\d)|[7-9]\\d{2})|[3-9]\\d{3})|10000)");
		hmTextFieldRegEx.put(txtMonthlySSI, "(0*)?(1?\\d{1,3}|2([0-5]\\d{2}|6([0-3]\\d|4[0-2])))");

		Iterator it = hmTextFieldRegEx.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TextField txtField = (TextField) pair.getKey();
			String strRegEx = (String) pair.getValue();

			txtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
			
					if (!newPropertyValue) {
						if (!txtField.getText().matches(strRegEx)) {
							txtField.setText("");
							txtField.requestFocus();
						}
					}
				}
			});
		}

	}

	@FXML
	public void btnClear(ActionEvent event) {
		System.out.println("Clear pressed");

		txtSaveEachMonth.clear();
		txtSaveEachMonth.setDisable(true);

		txtWhatYouNeedToSave.clear();
		txtWhatYouNeedToSave.setDisable(true);

		txtYearsToWork.clear();
		txtYearsToWork.setDisable(false);

		txtAnnualReturnWorking.clear();
		txtAnnualReturnWorking.setDisable(false);

		txtYearsRetired.clear();
		txtYearsRetired.setDisable(false);

		txtAnnualReturnRetired.clear();
		txtAnnualReturnRetired.setDisable(false);

		txtRequiredIncome.clear();
		txtRequiredIncome.setDisable(false);

		txtMonthlySSI.clear();
		txtMonthlySSI.setDisable(false);

	}

	@FXML
	public void btnCalculate() {

		System.out.println("calculating");

		txtSaveEachMonth.setDisable(false);
		txtWhatYouNeedToSave.setDisable(false);

		int YearsToWork = Integer.parseInt(txtYearsToWork.getText());
		Double AnnualReturnWorking = Double.parseDouble(txtAnnualReturnWorking.getText());

		int YearsRetired = Integer.parseInt(txtYearsRetired.getText());
		Double AnnualReturnRetired = Double.parseDouble(txtAnnualReturnRetired.getText());
		Double RequiredIncome = Double.parseDouble(txtRequiredIncome.getText());
		Double MonthlySSI = Double.parseDouble(txtMonthlySSI.getText());

		Retirement retirement = new Retirement(YearsToWork, AnnualReturnWorking, YearsRetired, AnnualReturnRetired,
				RequiredIncome, MonthlySSI);

		Double WhatYouNeedToSave = Math.abs(retirement.TotalAmountToSave());
		txtWhatYouNeedToSave.setText(WhatYouNeedToSave.toString());

		Double SaveEachMonth = Math.abs(retirement.MonthlySavings());
		txtSaveEachMonth.setText(SaveEachMonth.toString());

	}
}