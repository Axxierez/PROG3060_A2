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
@Table(name = "HOUSEHOLDSIZE" ,schema="APP")
public class HouseholdSize {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="description")
	private String description;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Household> getHousehold() {
		return household;
	}

	public void setHousehold(Set<Household> household) {
		this.household = household;
	}

	@OneToMany(mappedBy="totalIncome")
	private Set<Household> household = new HashSet<Household>();

}
