// ----------------------------------------------------
// Zack Meadows & Alex Galka
// PROG3060 Assignment 2
// 2018-03-27
// ----------------------------------------------------

package prog3060.zmag_a2;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JPABean {
	
    static final String PERSISTENCE_UNIT_NAME = "ZMAG_A2";
    
    static EntityManagerFactory entityMF = null;
    static EntityManager entityManager = null;
    boolean validConn = false;

	public boolean isValid() {
		return validConn;
	}
	public int parseStringToInt(String input) {
		int output = 0;
		try
		{
		    output = Integer.parseInt(input);
		}
		catch (NumberFormatException e)
		{
			output = 0;
		}
		return output;
	}
	public boolean openConn(String user, String pass) {
        try
        {
	    	validConn = true;
	    	Map<String, String> properties = new HashMap<String, String>();
	    	properties.put("hibernate.connection.username", user);
	    	properties.put("hibernate.connection.password", pass);
	    	
	    	entityMF = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	    	entityManager = entityMF.createEntityManager();
	    	return true;
        }
        catch (Exception e)
        {
	    	validConn = false;
            if (entityManager != null)
            	entityManager.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
	}
	
	public void closeConn() {
        entityManager.getTransaction().rollback();
        if (entityManager != null)
        	entityManager.close();
        if (entityMF != null)
        	entityMF.close();
    	validConn = false;
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
	
	public List<Object[]> getHouseholdsByArea(int id) {
    	String jpqlQuery = "SELECT h FROM Household h"
    			+ " WHERE h.geographicArea.geographicAreaID = :id AND h.censusYear.censusYear = 2016"
    			+ " AND h.householdEarners.description LIKE '1 earner or more'"
    			+ " AND h.householdsByAgeRange.description LIKE 'Total - Households by number of persons aged 0 to 17 years'"
    			+ " AND h.householdSize.description LIKE '2 or more persons'"
    			+ " AND h.householdType.description LIKE 'One couple census family without other persons in the household'"
    			+ " AND h.totalIncome.description LIKE '$80,000 to $89,999'";
    		
	    Query query = entityManager.createQuery(jpqlQuery);
	    query.setParameter("id", id);

		return doQuery(query);
	}
	
	public List<Object[]> getAllGeographicAreas() {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup = 1 AND a.censusYear = 1"
    			+ " ORDER BY ga.level, ga.name";
    	
    	Query query = entityManager.createQuery(jpqlQuery);
		return doQuery(query);
	}
	
	public List<Object[]> getGeographicAreasByID(int id) {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup = 1 AND a.censusYear = 1";
    	String order = " ORDER BY ga.level, ga.name";
    	
    	Query query = null;
    	if(id != 0) {
			jpqlQuery +=" AND ga.geographicAreaID = :id"
					+ order;
			
	    	query = entityManager.createQuery(jpqlQuery);
	    	query.setParameter("id", id);
    	}
    	else {
    		jpqlQuery += order;
        	query = entityManager.createQuery(jpqlQuery);
    	}
    	
		return doQuery(query);
	}

	public List<Object[]> getGeographicAreasByParent(int code, int level) {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup = 1 AND a.censusYear = 1";
    	String order = " ORDER BY ga.alternativeCode DESC";
    	
    	Query query = null;
    	if(code != 0) {
    		// FIX THIS
			jpqlQuery += " AND ga.alternativeCode LIKE '" + code + "%'"
					+ " AND ga.level > :level"
					+ order;
			
	    	query = entityManager.createQuery(jpqlQuery);
	    	query.setParameter("level", level);
    	}
    	else {
    		jpqlQuery += order;
        	query = entityManager.createQuery(jpqlQuery);
    	}
    	
		return doQuery(query);
	}

	public List<Object[]> getGeographicAreasByLevel(int level) {
    	String jpqlQuery = "SELECT a, ga FROM Age a"
    			+ " JOIN a.geographicArea ga "
    			+ " WHERE a.ageGroup = 1 AND a.censusYear = 1";
    	String order = " ORDER BY ga.level, ga.name";
    	
    	Query query = null;
    	if(level != 0) {
			jpqlQuery += " AND ga.level = :level"
					+ order;
			
	    	query = entityManager.createQuery(jpqlQuery);
	    	query.setParameter("level", level);
    	}
    	else {
    		jpqlQuery += order;
        	query = entityManager.createQuery(jpqlQuery);
    	}
		return doQuery(query);
	}

	private List <Object[]> doQuery(Query query) {
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

	public List<Object[]> getAgeGroupPopulation(int censusYear) {
    	String jpqlQuery = "SELECT a, ag FROM Age a"
    			+ " JOIN a.ageGroup ag "
    			+ " WHERE ag.ageGroupID IN (3, 9, 15, 22, 28, 34, 40, 46, 52, 58, 64, 70, 76, 83, 89, 95, 101, 108, 114, 120, 126)"
    			+ " AND a.geographicArea.geographicAreaID = 1"
    			+ " AND a.censusYear.censusYear = :censusYear"
    			+ "	ORDER BY ag.description";
    	Query query = entityManager.createQuery(jpqlQuery);
    	query.setParameter("censusYear", censusYear);
    	
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
		return null;}
}
