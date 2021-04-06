package old;

import java.util.List;

import by.alekseyshysh.task2.entity.Medicine;
import by.alekseyshysh.task2.entity.Version;

public interface MedicineBuilder {

	void setId(String id);

	void setName(String name);

	void setPharm(String pharm);

	void setGroup(String group);

	void setAnalogs(List<String> analogs);

	void setVersions(List<Version> versions);

	public Medicine createInstance();
}
