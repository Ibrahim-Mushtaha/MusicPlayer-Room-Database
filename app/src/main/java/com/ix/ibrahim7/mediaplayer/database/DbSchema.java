package com.ix.ibrahim7.mediaplayer.database;

/**
 * Created by deadsec on 10/14/17.
 */

public class DbSchema {
    public static class RecentHistory {
        public static String TABLE_NAME = "RecentHistory";
       public static class Cols {
            public static final String ID = "id";
            public static final String TIME_PLAYED = "time_played";
        }
    }

    // database schema for user queue
    public static class playBackQueue {
        //
    }
}
