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
@Table(name="CENSUSYEAR", schema = "APP")
public class CensusYear {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="censusYearID")
	private int censusYearID;
	
	@Column(name="censusYear")
	private int censusYear;

	@OneToMany(mappedBy="censusYear")
	private Set<Age> age = new HashSet<Age>();

	public Set<Household> getHousehold() {
		return household;
	}

	public void setHousehold(Set<Household> household) {
		this.household = household;
	}

	public Set<Age> getAge() {
		return age;
	}

	public void setAge(Set<Age> age) {
		this.age = age;
	}
	
	@OneToMany(mappedBy="censusYear")
	private Set<Household> household = new HashSet<Household>();

	
	
	public int getCensusYearID() {
		return censusYearID;
	}

	public void setCensusYearID(int censusYearID) {
		this.censusYearID = censusYearID;
	}

	public int getCensusYear() {
		return censusYear;
	}

	public void setCensusYear(int censusYear) {
		this.censusYear = censusYear;
	}
	
}
