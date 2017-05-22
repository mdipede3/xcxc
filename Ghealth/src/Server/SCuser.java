/**
 * TODO This is the class description
 */


package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import enums.Roles;
import enums.Status;
import models.Clinic;
import models.Envelope;
import models.User;

/**
 * @author G5 lab group
 * The Class SCuser.
 */
public class SCuser {

	PreparedStatement stmt; 
	ResultSet result = null;
	
	/**
	 * Gets the exist user.
	 *
	 * @param uID the u id
	 * @return the envelope
	 */
	public static Envelope GetExistUser(String uID)
	{
		int rowCount=0;
		PreparedStatement stmt = null; 
		ResultSet result = null;
		String querystr;
		User us = null;
		Envelope en = new Envelope();
		Clinic cl = new Clinic();
		/* Return patient row if exist */
		querystr="SELECT *"
				+ "FROM user u,clinic c "
				+ "WHERE u.ucID = c.cID AND u.uID = ?;";
		try 
		{
			stmt = mysqlConnection.conn.getDbConnession().prepareStatement(querystr);
			result = getSql(querystr, uID);
			
			result.last();
			rowCount = result.getRow();
			result.first();
			
			if(rowCount == 1)
			{
				/* Get & Create the exist user from DB */
				us = new User();
				us.setuID(result.getString("uID"));
				us.setuPassword(result.getString("uPassword"));
				us.setuFirstName(result.getString("uFirstName"));
				us.setuLastName(result.getString("uLastName"));
				us.setuEmail(result.getString("uEmail"));
				
				cl.setcID(result.getInt("ucID"));
				cl.setcLocation(result.getString("cLocation"));
				cl.setcName(result.getString("cName"));
				us.setuClinic(cl);
				
				String temp124=result.getString("role");
				us.setuRole(Roles.valueOf(temp124));
				
				en.addobjList(us);
				
				en.setStatus(Status.EXIST);
			}else{
				
				en.setStatus(Status.NOT_EXIST);
				us = new User(null,null,null,null,null,null,null);
				en.addobjList(us);
			}
			
		}
		catch (SQLException ex) 
   	    {/* handle any errors*/
          return null;
        }
		
		return en;
	}
	
	
	/**
	 * Assegna i valori passati come parametri allo statements.
	 * 
	 * @param statement
	 * @param fields
	 * @throws SQLException
	 */
	private static void assignValues(PreparedStatement statement, Object... fields) throws SQLException {
		int index = 0;
		for (Object obj : fields) {
			statement.setObject(++index, obj);
		}
	}
	

	/**
	 * Getting SQL query results back from DB
	 * 
	 * @param query
	 */
	public static ResultSet getSql(String query) {
		PreparedStatement stmt = null; 
		ResultSet result = null;
		try {
			stmt = mysqlConnection.autoConn.getDbConnession().prepareStatement(query);
			result = stmt.executeQuery();
			// mysqlConnection.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return result;
	}

	/**
	 * Getting SQL query results back from DB with params
	 * 
	 * @param query
	 */
	public static ResultSet getSql(String query, Object... fields) {
		PreparedStatement stmt = null; 
		ResultSet result = null;
		try {
			stmt = mysqlConnection.autoConn.getDbConnession().prepareStatement(query);
			assignValues(stmt, fields);
			result = stmt.executeQuery();
			// mysqlConnection.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return result;
	}

	/**
	 * Sending SQL query.
	 *
	 * @param query
	 *            the new SQL query
	 */
	public static void setSql(String query) {
		PreparedStatement stmt = null; 
		ResultSet result = null;
		try {
			stmt = mysqlConnection.autoConn.getDbConnession().prepareStatement(query);
			stmt.executeUpdate();
			// mysqlConnection.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/**
	 * Sending SQL query with params.
	 *
	 * @param query
	 *            the new SQL query
	 */
	public static void setSql(String query, Object...fields) {
		PreparedStatement stmt = null; 
		ResultSet result = null;
		try {
			stmt = mysqlConnection.autoConn.getDbConnession().prepareStatement(query);
			assignValues(stmt, fields);
			stmt.executeUpdate();
			// mysqlConnection.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	
	
}
