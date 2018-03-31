package prog3060.zmag_a2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="HOUSEHOLDSBYAGERANGE", schema="APP")
public class HouseholdsByAgeRange {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", nullable = false)
    private int id;
    @Column(name="DESCRIPTION", nullable = false)
    private String description;
    
	public HouseholdsByAgeRange() {}
    
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
}
