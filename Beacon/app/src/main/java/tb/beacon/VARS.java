package tb.beacon;

/**
 * Created by harrison on 11/10/15.
 */
public class VARS {
    public static String P_OBJ_NAME = "BEACON_V3";
    public static String DB_B_NAME = "beacon_name";
    public static String DB_B_LAT= "latitude";
    public static String DB_B_LONG="longitude";
    public static String DB_B_DURATION="time_length";

    public static long GET_NUM_MINS(long millis){
        return millis/(long)(60*60*1000);
    }
}
