package by.alekseyshysh.task2.entity;

import java.util.List;
import java.util.stream.Collectors;

public class Medicine {
	
	private String id;
	private String name;
	private String pharm;
	private String group;
	private List<Analog> analogs;
	private List<Version> versions;

	public Medicine(String id, String name, String pharm, String group, List<Analog> analogs, List<Version> versions) {
		this.id = id;
		this.name = name;
		this.pharm = pharm;
		this.group = group;
		this.analogs = analogs;
		this.versions = versions;
	}

	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + ", pharm=" + pharm + ", group=" + group + ", analogs="
				+ analogs.stream().map(Analog::toString).collect(Collectors.toList()) + ", versions=" + versions.stream().map(Version::toString).collect(Collectors.toList()) + "]";
	}
	

}
