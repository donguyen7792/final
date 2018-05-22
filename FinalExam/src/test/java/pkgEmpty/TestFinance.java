package pkgEmpty;

import static org.junit.Assert.*;

import org.junit.Test;

import pkgCore.Retirement;

import org.apache.poi.ss.formula.functions.*;

public class TestFinance {

	@Test
	public void TestPV() {

		int iYearsToWork = 40;
		double dAnnualReturnWorking = 7;
		int iYearsRetired = 20;
		double dAnnualReturnRetired = 2;
		double dRequiredIncome = 10000;
		double dMonthlySSI = 2642;

		double PV = Retirement.PV((dAnnualReturnRetired / 100.00) / 12.00, iYearsRetired * 12.00, dRequiredIncome - dMonthlySSI, 0, false);
		System.out.println(PV);
		assertEquals(1454485.55, Math.abs(PV), 0.01);

		Retirement retirement = new Retirement(iYearsToWork, dAnnualReturnWorking, iYearsRetired, dAnnualReturnRetired, dRequiredIncome, dMonthlySSI);

		double totalAmountToSave = retirement.TotalAmountToSave();
		System.out.println(totalAmountToSave);
		assertEquals(1454485.55, Math.abs(totalAmountToSave), 0.01);

	}

	@Test
	public void TestPMT() {

		int iYearsToWork = 40;
		int iYearsRetired = 20;
		double dAnnualReturnWorking = 7;
		double dAnnualReturnRetired = 2;
		double dRequiredIncome = 10000;
		double dMonthlySSI = 2642;

		double PV = Retirement.PV((dAnnualReturnRetired / 100.00) / 12.00, iYearsRetired * 12.00, dRequiredIncome - dMonthlySSI, 0, false);
		double PMT = Retirement.PMT((dAnnualReturnWorking / 100.00) / 12.00, iYearsToWork * 12.00, 0, PV, false);
		System.out.println(PMT);
		assertEquals(554.13, Math.abs(PMT), 0.01);

		Retirement retirement = new Retirement(iYearsToWork, dAnnualReturnWorking, iYearsRetired, dAnnualReturnRetired, dRequiredIncome, dMonthlySSI);
		double monthlySavings = retirement.MonthlySavings();
		System.out.println(monthlySavings);
		assertEquals(554.13, Math.abs(monthlySavings), 0.01);

	}
}
