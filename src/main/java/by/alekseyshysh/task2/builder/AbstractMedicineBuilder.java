package by.alekseyshysh.task2.builder;

import java.util.ArrayList;
import java.util.List;

import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.exception.MedicinesException;

public abstract class AbstractMedicineBuilder {
	
	protected List<Medicine> medicines;
	
	protected AbstractMedicineBuilder() {
		medicines = new ArrayList<>();
	}
	
	protected AbstractMedicineBuilder(List<Medicine> medicines) {
		this.medicines = new ArrayList<>(medicines);
	}
	
	public List<Medicine> getMedicines() {
		return new ArrayList<>(medicines);
	}
	
	public abstract void buildMedicines(String xmlFilePath) throws MedicinesException;
	
}
