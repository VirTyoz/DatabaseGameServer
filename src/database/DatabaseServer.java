package database;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import database.service.GameService;
import database.service.GameServiceHandler;

public class DatabaseServer {
	private static final Logger logger = LogManager.getLogger(DatabaseServer.class);
	public static void main(String[] args){
		try {
			
			//MySQL
			Connection loginServerDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameServer", "root", "***REMOVED***");
			
			//rpc thrift
			GameService.Processor<GameServiceHandler> processor = new GameService.Processor<GameServiceHandler>(new GameServiceHandler(loginServerDB));
			TServerTransport serverTransport = new TServerSocket(6067);
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
			logger.info("Game Service Start");
			server.serve();
	
		} catch (Exception e) {
			logger.catching(e);
		}
	}

}
