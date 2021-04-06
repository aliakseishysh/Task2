package by.alekseyshysh.task2.builder;

import java.util.ArrayList;
import java.util.List;

import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.exception.MedicinesException;

public abstract class AbstractMedicineBuilder {
	
	protected List<Medicine> medicines;
	
	public AbstractMedicineBuilder() {
		medicines = new ArrayList<>();
	}
	
	// TODO Clone
	public AbstractMedicineBuilder(List<Medicine> medicines) {
		this.medicines = medicines;
	}
	
	// TODO Clone
	public List<Medicine> getMedicines() {
		return medicines;
	}
	
	public abstract void buildMedicines(String xmlFilePath) throws MedicinesException;
	
}
