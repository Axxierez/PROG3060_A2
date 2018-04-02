package prog3060.zmag_a2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AGEGROUP", schema="APP")
public class AgeGroup {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="AGEGROUPID", nullable = false)
    private int ageGroupID;
    @Column(name="DESCRIPTION", nullable = false)
    private String description;
    
	public AgeGroup() {}

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
