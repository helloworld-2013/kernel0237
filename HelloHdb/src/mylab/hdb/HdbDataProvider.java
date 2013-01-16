package mylab.hdb;

import android.os.Handler;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HdbDataProvider {

	public ArrayList<String> towns;
	public ArrayList<String> rooms;
    public ArrayList<String> prices;
	
	private ThreadPoolExecutor executor = null;
	
	public void requestHdbData(Handler handler) {
		if (executor == null) {
			executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(3);
		}
		
		for (String town : towns) {
			for (String room : rooms) {
				HdbConnection connection = new HdbConnection();
                String _room = HdbRoomTypeActivity.rooms.get(room);
				connection.hdbRoom = _room;
                String _town = HdbTownActivity.towns.get(town);
				connection.hdbTown = _town;
				connection.handler = handler;
				executor.execute(connection);
			}
		}
	}
	
}
