package prog3060.zmag_a2;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AGEGROUP", schema = "APP")
public class AgeGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ageGroupID")
	private int ageGroupID;
	
	@OneToMany(mappedBy="ageGroup")
	private Set<Age> age = new HashSet<Age>();

	public Set<Age> getAge() {
		return age;
	}

	public void setAge(Set<Age> age) {
		this.age = age;
	}

	@Column(name = "description")
	private String description;

	public int getAgeGroupID() {
		return ageGroupID;
	}

	public void setAgeGroupID(int ageGroupID) {
		this.ageGroupID = ageGroupID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
