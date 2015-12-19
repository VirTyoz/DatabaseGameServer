package database.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;

import database.pocket.PlanetInfo;

public class GameServiceHandler implements GameService.Iface{
	private static final Logger logger = LogManager.getLogger(GameServiceHandler.class);
	private Statement loginServerStmt;
	private ResultSet loginServerRS;
	
	public GameServiceHandler(Connection loginServerDB) throws SQLException{
		loginServerStmt = loginServerDB.createStatement();
	}

	@Override
	public List<Integer> getId(int select ,int Id){	//get object ID in DB
		List<Integer> planetsId = new ArrayList<Integer>();
		
		try {
			switch(select){
			case 0: //get planets id
				
				loginServerRS = loginServerStmt.executeQuery("SELECT * FROM  planets WHERE system_id = '"+Id+"'");
				
				while(loginServerRS.next()){
				planetsId.add(loginServerRS.getInt("id"));
				}
				break;
				
			case 1: //get buildings id
				loginServerRS = loginServerStmt.executeQuery("SELECT * FROM  built WHERE planet_id = '"+Id+"'");
				
				while(loginServerRS.next()){
				planetsId.add(loginServerRS.getInt("id"));
				}
				break;
			default:
				break;
			}
			} catch (Exception e) {
				logger.catching(e);
				
			}
		return planetsId;
	}

	@Override
	public PlanetInfo getPlanetInfo(int Id) throws TException {
		PlanetInfo planetInfo = new PlanetInfo();
		try {
			loginServerRS = loginServerStmt.executeQuery("SELECT * FROM  planets WHERE id = '"+Id+"'");
			planetInfo.name = loginServerRS.getString("name");
			planetInfo.ownerId = loginServerRS.getInt("ownerId");
			planetInfo.x = loginServerRS.getInt("x");
			planetInfo.y = loginServerRS.getInt("y");
			planetInfo.z = loginServerRS.getInt("z");
		} catch (Exception e) {
			logger.catching(e);
		}
		return null;
	}
	
	

}
