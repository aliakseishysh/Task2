package by.alekseyshysh.task2.entity;

import java.util.List;

public class Medicine {

	private String id;
	private String name;
	private String pharm;
	private String group;
	private List<String> analogs;
	private List<Version> versions;

	public Medicine() {
	}

	public Medicine(String id, String name, String pharm, String group, List<String> analogs, List<Version> versions) {
		this.id = id;
		this.name = name;
		this.pharm = pharm;
		this.group = group;
		this.analogs = analogs;
		this.versions = versions;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPharm() {
		return pharm;
	}

	public void setPharm(String pharm) {
		this.pharm = pharm;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<String> getAnalogs() {
		return analogs;
	}

	public void setAnalogs(List<String> analogs) {
		this.analogs = analogs;
	}

	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pharm == null) ? 0 : pharm.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((analogs == null) ? 0 : analogs.hashCode());
		result = prime * result + ((versions == null) ? 0 : versions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		Medicine other = (Medicine) object;
		boolean result = false;
		if (id.equals(other.id) && name.equals(other.name) && pharm.equals(other.pharm) && group.equals(other.group)
				&& analogs.equals(other.analogs) && versions.equals(other.versions)) {
			result = true;
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("\nMedicine [id=");
		stringBuilder.append(id);
		stringBuilder.append(", name=");
		stringBuilder.append(name);
		stringBuilder.append(", pharm=");
		stringBuilder.append(pharm);
		stringBuilder.append(", group=");
		stringBuilder.append(group);
		stringBuilder.append(", analogs=");
		stringBuilder.append(analogs.toString());
		stringBuilder.append(", versions=");
		stringBuilder.append(versions.toString());
		stringBuilder.append("]");
		String result = stringBuilder.toString();
		return result;
	}

}
