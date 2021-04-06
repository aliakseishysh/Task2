package by.alekseyshysh.task2.dom;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.testng.annotations.Test;

import by.alekseyshysh.task2.builder.DomMedicineBuilder;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.exception.MedicinesException;

public class DomMedicineBuilderTest {

	@Test
	public void parseTest() throws MedicinesException, URISyntaxException {
		DomMedicineBuilder builder = new DomMedicineBuilder();
		URI uri = getClass().getResource("/data/Meds.xml").toURI();
		String absolutePath = new File(uri).getAbsolutePath();
		builder.buildMedicines(absolutePath);
		List<Medicine> medicines = builder.getMedicines();
		System.out.println(medicines.toString());
	}
}
