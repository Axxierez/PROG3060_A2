// ----------------------------------------------------
// Zack Meadows & Alex Galka
// PROG3060 Assignment 2
// 2018-03-27
// ----------------------------------------------------

package prog3060.zmag_a2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class JPABean {
	
    static final String PERSISTENCE_UNIT_NAME = "ZMAG_A2";
    static EntityManagerFactory entityMF = null;
    static EntityManager entityManager = null;
    
	static final int EXIT_FAILURE_COMMAND_LINE_ARGS = 1;
	static final int EXIT_UNHANDLED_ERROR = 2;

	public JPABean() {
        try
        {
	    	entityMF = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    	
	    	// Something we'll need to work with later
//        	entityManager.setProperty("javax.persistence.jdbc.user", "user");
//        	entityManager.setProperty("javax.persistence.jdbc.password", "123");
	    	entityManager = entityMF.createEntityManager();
        }
        catch (Exception e)
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
	}
	
	public void closeConn() {
        entityManager.getTransaction().rollback();
        if (entityManager != null)
        	entityManager.close();
        if (entityMF != null)
        	entityMF.close();
	}
	
	public int getHouseholdsMatchingAreaSQL(String area, Connection dbConn) {
//		int householdCount=0;
//		String query ="SELECT COUNT(*) from Household h inner join CensusYear y on h.censusYear=y.censusYearId "
//				+ "inner join GeographicArea a on h.geographicArea = a.geographicAreaId "
//				+ "inner join householdEarners e on h.householdEarners=e.id "
//				+ "inner join householdSize s on h.householdSize=s.id "
//				+ "inner join totalIncome i on h.totalIncome=i.id where (TRIM(CAST(CAST(a.alternativeCode AS CHAR(30)) AS VARCHAR(30))) LIKE '"+area+ "%')"
//						+ " and y.censusYear=2016 and e.description='1 earner or more'"
//						+ " and s.description='2 or more persons' and i.description='$80,000 to $89,999'";
//
//		
//		try (Statement statement = dbConn.createStatement(); ResultSet result = statement.executeQuery(query)) {
//			
//			while (result.next()) {
//				householdCount=Integer.parseInt(result.getString("1"));
//			}
//			
//		}catch (SQLException e) {
//			e.printStackTrace();
//			System.exit(e.getErrorCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(EXIT_UNHANDLED_ERROR);
//		}
//		return householdCount;
		return 0;
	}
	
	public int getHouseholdsMatchingArea(String level, Connection dbConn) {
//
//		 EntityManagerFactory tempEntityManagerFactory = null;
//	        EntityManager tempEntityManager = null;
//	        int householdCount = 0;
//
//	        try
//	        {
//
//	            tempEntityManagerFactory = Persistence.createEntityManagerFactory("ZMAG_A2");
//	            tempEntityManager = tempEntityManagerFactory.createEntityManager();
//	            tempEntityManager.getTransaction().begin();
//	            
//	            
//	            String queryString = "FROM Household "
//	                    + "WHERE geographicArea.level = :level and censusYear.censusYear= :censusYear and householdEarners.description= :householdEarners"
//	                    + " and householdSize.description= :householdSize and totalIncome.description= :totalIncome";
//
//	            Query tempQuery = tempEntityManager.createQuery(queryString)
//	            		.setParameter("level", level).setParameter("censusYear", 2016).setParameter("householdEarners", "1 earner or more").
//	            		setParameter("householdSize", "2 or more persons").setParameter("totalIncome", "$80,000 to $89,999");
//	            
//	            householdCount=  tempQuery.getResultList().size();
//	            
//	            
//	        } catch (Exception e)
//	        {
//	            if (tempEntityManager != null)
//	            {
//	                tempEntityManager.getTransaction().rollback();
//	            }
//
//	            e.printStackTrace();
//	        }
//	        finally
//	        {
//	            if (tempEntityManager != null)
//	            {
//	                tempEntityManager.close();
//	            }
//
//	            if (tempEntityManagerFactory != null)
//	            {
//	                tempEntityManagerFactory.close();
//	            }
//	        }
//
//		return householdCount;
		return 0;

	}

	public List<Object[]> getGeographicAreasByID(String id) {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup = 1 AND a.censusYear = 1";
    	String order = " ORDER BY ga.level, ga.name";
    	
    	Query query = null;
    	if(id != null) {
			jpqlQuery +=" AND ga.geographicAreaID = :id"
					+ order;
			
	    	query = entityManager.createQuery(jpqlQuery);
	    	query.setParameter("id", id);
    	}
    	else {
    		jpqlQuery += order;
        	query = entityManager.createQuery(jpqlQuery);
    	}
    	
		return getGeographicAreas(query);
	}

	public List<Object[]> getGeographicAreasByParent(String code, String level) {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup = 1 AND a.censusYear = 1";
    	String order = " ORDER BY ga.level, ga.name";
    	
    	Query query = null;
    	if(code != null) {
			jpqlQuery += " AND (TRIM(CAST(CAST(ga.alternativeCode AS CHAR(30)) AS VARCHAR(30))) LIKE :codeCheck"
					+ " AND ga.level > :level"
					+ order;
			
	    	query = entityManager.createQuery(jpqlQuery);
	    	query.setParameter("codeCheck", code + "%");
	    	query.setParameter("level", level);
    	}
    	else {
    		jpqlQuery += order;
        	query = entityManager.createQuery(jpqlQuery);
    	}
    	
		return getGeographicAreas(query);
	}

	public List<Object[]> getGeographicAreasByLevel(String level) {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup.age = 1 AND a.censusYear = 1";
    	String order = " ORDER BY ga.level, ga.name";
    	
    	Query query = null;
    	if(level != null) {
			jpqlQuery += " AND ga.level = :level";
			
	    	query = entityManager.createQuery(jpqlQuery);
	    	query.setParameter("level", level);
    	}
    	else {
    		jpqlQuery += order;
        	query = entityManager.createQuery(jpqlQuery);
    	}
		return getGeographicAreas(query);
	}

	public List <Object[]> getGeographicAreas(Query query) {
		try {
        	entityManager.getTransaction().begin();
        	
        	List <Object[]> resultSet = query.getResultList();
			return resultSet;
		}
        catch (Exception e)
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
        }
		return null;
	}

	public List<AgeGroup> getAgeGroupPopulation(Connection dbConn) {
//		String query = "SELECT a.censusYear, ag.description, a.male, a.female FROM AGE a JOIN AGEGROUP ag ON ag.agegroupid = a.agegroup"
//				+ " WHERE ag.agegroupid in(3, 9, 15, 22, 28, 34, 40, 46, 52, 58, 64, 70, 76, 83, 89, 95, 101, 108, 114, 120, 126)"
//				+ " AND geographicArea = 1 ORDER BY ag.description";
//		try (Statement statement = dbConn.createStatement(); ResultSet result = statement.executeQuery(query)) {
//			List<AgeGroup> resultSet = new ArrayList<AgeGroup>();
//			String current_ageGroup = "";
//			String last_ageGroup = "";
//			AgeGroup tempAgeGroup = new AgeGroup();
//			while (result.next()) {
//				current_ageGroup = result.getString("description");
//
//				// tempAgeGroup.setAgegroup(current_ageGroup);
//				if (result.getString("censusyear").equals("1")) {
//					// tempAgeGroup.setMalePopulation2016(Integer.parseInt(result.getString("male")));
//					// tempAgeGroup.setFemalePopulation2016(Integer.parseInt(result.getString("female")));
//				} else {
//					// tempAgeGroup.setMalePopulation2011(Integer.parseInt(result.getString("male")));
//					// tempAgeGroup.setFemalePopulation2011(Integer.parseInt(result.getString("female")));
//				}
//				if (current_ageGroup.equals(last_ageGroup)) {
//					resultSet.add(tempAgeGroup);
//					tempAgeGroup = new AgeGroup();
//				} else {
//					last_ageGroup = current_ageGroup;
//				}
//			}
//			dbConn.rollback();
//			return resultSet;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.exit(e.getErrorCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(EXIT_UNHANDLED_ERROR);
//		}
		return null;
	}
}