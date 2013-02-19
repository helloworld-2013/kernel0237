/* ========================================================
 * HdbDataProvider.java
 *
 * Copyright 2012 Indra Gunawan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * ======================================================== */
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
