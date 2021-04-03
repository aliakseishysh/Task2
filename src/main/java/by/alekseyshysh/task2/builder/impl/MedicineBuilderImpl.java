package by.alekseyshysh.task2.builder.impl;

import java.util.List;

import by.alekseyshysh.task2.builder.MedicineBuilder;
import by.alekseyshysh.task2.entity.Analog;
import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.Version;

public class MedicineBuilderImpl implements MedicineBuilder {

	private String id;
	private String name;
	private String pharm;
	private String group;
	private List<Analog> analogs;
	private List<Version> versions;

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setPharm(String pharm) {
		this.pharm = pharm;
	}

	@Override
	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public void setAnalogs(List<Analog> analogs) {
		this.analogs = analogs;
	}

	@Override
	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	@Override
	public Medicine createInstance() {
		return new Medicine(id, name, pharm, group, analogs, versions);
	}

}
