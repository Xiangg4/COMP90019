package sqlite.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "PARKING_BAY")
public class ParkingBay {
    //public static final String TABLE_NAME = "PARKING_BAY";
    @PrimaryKey
    @ColumnInfo(name = "BayId")
    @SerializedName("BayId")
    private int BayId;
    @ColumnInfo(name = "DeviceId")
    @SerializedName("DeviceId")
    private int DeviceId;
    @ColumnInfo(name = "Description1")
    @SerializedName("Description1")
    private String Description1;
    @ColumnInfo(name = "Description2")
    @SerializedName("Description2")
    private String Description2;
    @ColumnInfo(name = "Description3")
    @SerializedName("Description3")
    private String Description3;
    @ColumnInfo(name = "Description4")
    @SerializedName("Description4")
    private String Description4;
    @ColumnInfo(name = "Description5")
    @SerializedName("Description5")
    private String Description5;
    @ColumnInfo(name = "Description6")
    @SerializedName("Description6")
    private String Description6;
    @ColumnInfo(name = "DisabilityExt1")
    @SerializedName("DisabilityExt1")
    private int DisabilityExt1;
    @ColumnInfo(name = "DisabilityExt2")
    @SerializedName("DisabilityExt2")
    private int DisabilityExt2;
    @ColumnInfo(name = "DisabilityExt3")
    @SerializedName("DisabilityExt3")
    private int DisabilityExt3;
    @ColumnInfo(name = "DisabilityExt4")
    @SerializedName("DisabilityExt4")
    private int DisabilityExt4;
    @ColumnInfo(name = "DisabilityExt5")
    @SerializedName("DisabilityExt5")
    private int DisabilityExt5;
    @ColumnInfo(name = "DisabilityExt6")
    @SerializedName("DisabilityExt6")
    private int DisabilityExt6;
    @ColumnInfo(name = "Duration1")
    @SerializedName("Duration1")
    private int Duration1;
    @ColumnInfo(name = "Duration2")
    @SerializedName("Duration2")
    private int Duration2;
    @ColumnInfo(name = "Duration3")
    @SerializedName("Duration3")
    private int Duration3;
    @ColumnInfo(name = "Duration4")
    @SerializedName("Duration4")
    private int Duration4;
    @ColumnInfo(name = "Duration5")
    @SerializedName("Duration5")
    private int Duration5;
    @ColumnInfo(name = "Duration6")
    @SerializedName("Duration6")
    private int Duration6;
    @ColumnInfo(name = "EffectiveOnPH1")
    @SerializedName("EffectiveOnPH1")
    private int EffectiveOnPH1;
    @ColumnInfo(name = "EffectiveOnPH2")
    @SerializedName("EffectiveOnPH2")
    private int EffectiveOnPH2;
    @ColumnInfo(name = "EffectiveOnPH3")
    @SerializedName("EffectiveOnPH3")
    private int EffectiveOnPH3;
    @ColumnInfo(name = "EffectiveOnPH4")
    @SerializedName("EffectiveOnPH4")
    private int EffectiveOnPH4;
    @ColumnInfo(name = "EffectiveOnPH5")
    @SerializedName("EffectiveOnPH5")
    private int EffectiveOnPH5;
    @ColumnInfo(name = "EffectiveOnPH6")
    @SerializedName("EffectiveOnPH6")
    private int EffectiveOnPH6;
    @ColumnInfo(name = "EndTime1")
    @SerializedName("EndTime1")
    private String EndTime1;
    @ColumnInfo(name = "EndTime2")
    @SerializedName("EndTime2")
    private String EndTime2;
    @ColumnInfo(name = "EndTime3")
    @SerializedName("EndTime3")
    private String EndTime3;
    @ColumnInfo(name = "EndTime4")
    @SerializedName("EndTime4")
    private String EndTime4;
    @ColumnInfo(name = "EndTime5")
    @SerializedName("EndTime5")
    private String EndTime5;
    @ColumnInfo(name = "EndTime6")
    @SerializedName("EndTime6")
    private String EndTime6;
    @ColumnInfo(name = "Exemption1")
    @SerializedName("Exemption1")
    private String Exemption1;
    @ColumnInfo(name = "Exemption2")
    @SerializedName("Exemption2")
    private String Exemption2;
    @ColumnInfo(name = "Exemption3")
    @SerializedName("Exemption3")
    private String Exemption3;
    @ColumnInfo(name = "Exemption4")
    @SerializedName("Exemption4")
    private String Exemption4;
    @ColumnInfo(name = "Exemption5")
    @SerializedName("Exemption5")
    private String Exemption5;
    @ColumnInfo(name = "Exemption6")
    @SerializedName("Exemption6")
    private String Exemption6;
    @ColumnInfo(name = "FromDay1")
    @SerializedName("FromDay1")
    private String FromDay1;
    @ColumnInfo(name = "FromDay2")
    @SerializedName("FromDay2")
    private String FromDay2;
    @ColumnInfo(name = "FromDay3")
    @SerializedName("FromDay3")
    private String FromDay3;
    @ColumnInfo(name = "FromDay4")
    @SerializedName("FromDay4")
    private String FromDay4;
    @ColumnInfo(name = "FromDay5")
    @SerializedName("FromDay5")
    private String FromDay5;
    @ColumnInfo(name = "FromDay6")
    @SerializedName("FromDay6")
    private String FromDay6;
    @ColumnInfo(name = "StartTime1")
    @SerializedName("StartTime1")
    private String StartTime1;
    @ColumnInfo(name = "StartTime2")
    @SerializedName("StartTime2")
    private String StartTime2;
    @ColumnInfo(name = "StartTime3")
    @SerializedName("StartTime3")
    private String StartTime3;
    @ColumnInfo(name = "StartTime4")
    @SerializedName("StartTime4")
    private String StartTime4;
    @ColumnInfo(name = "StartTime5")
    @SerializedName("StartTime5")
    private String StartTime5;
    @ColumnInfo(name = "StartTime6")
    @SerializedName("StartTime6")
    private String StartTime6;
    @ColumnInfo(name = "ToDay1")
    @SerializedName("ToDay1")
    private String ToDay1;
    @ColumnInfo(name = "ToDay2")
    @SerializedName("ToDay2")
    private String ToDay2;
    @ColumnInfo(name = "ToDay3")
    @SerializedName("ToDay3")
    private String ToDay3;
    @ColumnInfo(name = "ToDay4")
    @SerializedName("ToDay4")
    private String ToDay4;
    @ColumnInfo(name = "ToDay5")
    @SerializedName("ToDay5")
    private String ToDay5;
    @ColumnInfo(name = "ToDay6")
    @SerializedName("ToDay6")
    private String ToDay6;

    @ColumnInfo(name = "TypeDesc1")
    @SerializedName("TypeDesc1")
    private String TypeDesc1;

    @ColumnInfo(name = "TypeDesc2")
    @SerializedName("TypeDesc2")
    private String TypeDesc2;

    @ColumnInfo(name = "TypeDesc3")
    @SerializedName("TypeDesc3")
    private String TypeDesc3;

    @ColumnInfo(name = "TypeDesc4")
    @SerializedName("TypeDesc4")
    private String TypeDesc4;

    @ColumnInfo(name = "TypeDesc5")
    @SerializedName("TypeDesc5")
    private String TypeDesc5;

    @ColumnInfo(name = "TypeDesc6")
    @SerializedName("TypeDesc6")
    private String TypeDesc6;


    /**
    public static final String COLUMN_BayID = "BayId";

    public static final String COLUMN_DeviceID = "DeviceId";
    public static final String COLUMN_Description_1 = "Description_1";
    public static final String COLUMN_Description_2 = "Description_2";
    public static final String COLUMN_Description_3 = "Description_3";
    public static final String COLUMN_Description_4 = "Description_4";
    public static final String COLUMN_Description_5 = "Description_5";
    public static final String COLUMN_Description_6 = "Description_6";
    public static final String COLUMN_DisabilityExt1 = "DisabilityExt1";
    public static final String COLUMN_DisabilityExt2 = "DisabilityExt2";
    public static final String COLUMN_DisabilityExt3 = "DisabilityExt3";
    public static final String COLUMN_DisabilityExt4 = "DisabilityExt4";
    public static final String COLUMN_DisabilityExt5 = "DisabilityExt5";
    public static final String COLUMN_DisabilityExt6 = "DisabilityExt6";
    public static final String COLUMN_Duration1 = "Duration1";
    public static final String COLUMN_Duration2 = "Duration2";
    public static final String COLUMN_Duration3 = "Duration3";
    public static final String COLUMN_Duration4 = "Duration4";
    public static final String COLUMN_Duration5 = "Duration5";
    public static final String COLUMN_Duration6 = "Duration6";
    public static final String COLUMN_EffectiveOnPH1 = "EffectiveOnPH1";
    public static final String COLUMN_EffectiveOnPH2 = "EffectiveOnPH2";
    public static final String COLUMN_EffectiveOnPH3 = "EffectiveOnPH3";
    public static final String COLUMN_EffectiveOnPH4 = "EffectiveOnPH4";
    public static final String COLUMN_EffectiveOnPH5 = "EffectiveOnPH5";
    public static final String COLUMN_EffectiveOnPH6 = "EffectiveOnPH6";
    public static final String COLUMN_EndTime1 = "EndTime1";
    public static final String COLUMN_EndTime2 = "EndTime2";
    public static final String COLUMN_EndTime3 = "EndTime3";
    public static final String COLUMN_EndTime4 = "EndTime4";
    public static final String COLUMN_EndTime5 = "EndTime5";
    public static final String COLUMN_EndTime6 = "EndTime6";
    public static final String COLUMN_Exemption1 = "Exemption1";
    public static final String COLUMN_Exemption2 = "Exemption2";
    public static final String COLUMN_Exemption3 = "Exemption3";
    public static final String COLUMN_Exemption4 = "Exemption4";
    public static final String COLUMN_Exemption5 = "Exemption5";
    public static final String COLUMN_Exemption6 = "Exemption6";
    public static final String COLUMN_FromDay1 = "FromDay1";
    public static final String COLUMN_FromDay2 = "FromDay2";
    public static final String COLUMN_FromDay3 = "FromDay3";
    public static final String COLUMN_FromDay4 = "FromDay4";
    public static final String COLUMN_FromDay5 = "FromDay5";
    public static final String COLUMN_FromDay6 = "FromDay6";
    public static final String COLUMN_StartTime1 = "StartTime1";
    public static final String COLUMN_StartTime2 = "StartTime2";
    public static final String COLUMN_StartTime3 = "StartTime3";
    public static final String COLUMN_StartTime4 = "StartTime4";
    public static final String COLUMN_StartTime5 = "StartTime5";
    public static final String COLUMN_StartTime6 = "StartTime6";
    public static final String COLUMN_ToDay1 = "ToDay1";
    public static final String COLUMN_ToDay2 = "ToDay2";
    public static final String COLUMN_ToDay3 = "ToDay3";
    public static final String COLUMN_ToDay4 = "ToDay4";
    public static final String COLUMN_ToDay5 = "ToDay5";
    public static final String COLUMN_ToDay6 = "ToDay6";
    public static final String COLUMN_TypeDesc1 = "TypeDesc1";
    public static final String COLUMN_TypeDesc2 = "TypeDesc2";
    public static final String COLUMN_TypeDesc3 = "TypeDesc3";
    public static final String COLUMN_TypeDesc4 = "TypeDesc4";
    public static final String COLUMN_TypeDesc5 = "TypeDesc5";
    public static final String COLUMN_TypeDesc6 = "TypeDesc6";




    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_BayID + " INTEGER PRIMARY KEY,"
                    + COLUMN_DeviceID + " INTEGER,"
                    + COLUMN_Description_1 + " TEXT,"
                    + COLUMN_Description_2 + " TEXT,"
                    + COLUMN_Description_3 + " TEXT,"
                    + COLUMN_Description_4 + " TEXT,"
                    + COLUMN_Description_5 + " TEXT,"
                    + COLUMN_Description_6 + " TEXT,"
                    + COLUMN_DisabilityExt1 + " INTEGER,"
                    + COLUMN_DisabilityExt2 + " INTEGER,"
                    + COLUMN_DisabilityExt3 + " INTEGER,"
                    + COLUMN_DisabilityExt4 + " INTEGER,"
                    + COLUMN_DisabilityExt5 + " INTEGER,"
                    + COLUMN_DisabilityExt6 + " INTEGER,"
                    + COLUMN_Duration1 + " INTEGER,"
                    + COLUMN_Duration2 + " INTEGER,"
                    + COLUMN_Duration3 + " INTEGER,"
                    + COLUMN_Duration4 + " INTEGER,"
                    + COLUMN_Duration5 + " INTEGER,"
                    + COLUMN_Duration6 + " INTEGER,"
                    + COLUMN_EffectiveOnPH1 + " INTEGER,"
                    + COLUMN_EffectiveOnPH2 + " INTEGER,"
                    + COLUMN_EffectiveOnPH3 + " INTEGER,"
                    + COLUMN_EffectiveOnPH4 + " INTEGER,"
                    + COLUMN_EffectiveOnPH5 + " INTEGER,"
                    + COLUMN_EffectiveOnPH6 + " INTEGER,"
                    + COLUMN_EndTime1 + " TEXT,"
                    https://www.sqlite.org/datatype3.html
                    + COLUMN_EndTime2 + " TEXT,"
                    + COLUMN_EndTime3 + " TEXT,"
                    + COLUMN_EndTime4 + " TEXT,"
                    + COLUMN_EndTime5 + " TEXT,"
                    + COLUMN_EndTime6 + " TEXT,"
                    + COLUMN_Exemption1 + " TEXT,"
                    + COLUMN_Exemption2 + " TEXT,"
                    + COLUMN_Exemption3 + " TEXT,"
                    + COLUMN_Exemption4 + " TEXT,"
                    + COLUMN_Exemption5 + " TEXT,"
                    + COLUMN_Exemption6 + " TEXT,"
                    + COLUMN_FromDay1 + " INTEGER,"
                    + COLUMN_FromDay2 + " INTEGER,"
                    + COLUMN_FromDay3 + " INTEGER,"
                    + COLUMN_FromDay4 + " INTEGER,"
                    + COLUMN_FromDay5 + " INTEGER,"
                    + COLUMN_FromDay6 + " INTEGER,"
                    + COLUMN_StartTime1 + " TEXT,"
                    + COLUMN_StartTime2 + " TEXT,"
                    + COLUMN_StartTime3 + " TEXT,"
                    + COLUMN_StartTime4 + " TEXT,"
                    + COLUMN_StartTime5 + " TEXT,"
                    + COLUMN_StartTime6 + " TEXT,"
                    + COLUMN_ToDay1 + " INTEGER,"
                    + COLUMN_ToDay2 + " INTEGER,"
                    + COLUMN_ToDay3 + " INTEGER,"
                    + COLUMN_ToDay4 + " INTEGER,"
                    + COLUMN_ToDay5 + " INTEGER,"
                    + COLUMN_ToDay6 + " INTEGER,"
                    + COLUMN_TypeDesc1 + " TEXT"
                    + COLUMN_TypeDesc2 + " TEXT"
                    + COLUMN_TypeDesc3 + " TEXT"
                    + COLUMN_TypeDesc4 + " TEXT"
                    + COLUMN_TypeDesc5 + " TEXT"
                    + COLUMN_TypeDesc6 + " TEXT"
                    + ")";
**/
    public int getBayId() {
        return BayId;
    }

    public void setBayId(int bayId) {
        BayId = bayId;
    }

    public int getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(int deviceId) {
        DeviceId = deviceId;
    }

    public String getDescription1() {
        return Description1;
    }

    public void setDescription1(String description1) {
        Description1 = description1;
    }

    public String getDescription2() {
        return Description2;
    }

    public void setDescription2(String description2) {
        Description2 = description2;
    }

    public String getDescription3() {
        return Description3;
    }

    public void setDescription3(String description3) {
        Description3 = description3;
    }

    public String getDescription4() {
        return Description4;
    }

    public void setDescription4(String description4) {
        Description4 = description4;
    }

    public String getDescription5() {
        return Description5;
    }

    public void setDescription5(String description5) {
        Description5 = description5;
    }

    public String getDescription6() {
        return Description6;
    }

    public void setDescription6(String description6) {
        Description6 = description6;
    }

    public int getDisabilityExt1() {
        return DisabilityExt1;
    }

    public void setDisabilityExt1(int disabilityExt1) {
        DisabilityExt1 = disabilityExt1;
    }

    public int getDisabilityExt2() {
        return DisabilityExt2;
    }

    public void setDisabilityExt2(int disabilityExt2) {
        DisabilityExt2 = disabilityExt2;
    }

    public int getDisabilityExt3() {
        return DisabilityExt3;
    }

    public void setDisabilityExt3(int disabilityExt3) {
        DisabilityExt3 = disabilityExt3;
    }

    public int getDisabilityExt4() {
        return DisabilityExt4;
    }

    public void setDisabilityExt4(int disabilityExt4) {
        DisabilityExt4 = disabilityExt4;
    }

    public int getDisabilityExt5() {
        return DisabilityExt5;
    }

    public void setDisabilityExt5(int disabilityExt5) {
        DisabilityExt5 = disabilityExt5;
    }

    public int getDisabilityExt6() {
        return DisabilityExt6;
    }

    public void setDisabilityExt6(int disabilityExt6) {
        DisabilityExt6 = disabilityExt6;
    }

    public int getDuration1() {
        return Duration1;
    }

    public void setDuration1(int duration1) {
        Duration1 = duration1;
    }

    public int getDuration2() {
        return Duration2;
    }

    public void setDuration2(int duration2) {
        Duration2 = duration2;
    }

    public int getDuration3() {
        return Duration3;
    }

    public void setDuration3(int duration3) {
        Duration3 = duration3;
    }

    public int getDuration4() {
        return Duration4;
    }

    public void setDuration4(int duration4) {
        Duration4 = duration4;
    }

    public int getDuration5() {
        return Duration5;
    }

    public void setDuration5(int duration5) {
        Duration5 = duration5;
    }

    public int getDuration6() {
        return Duration6;
    }

    public void setDuration6(int duration6) {
        Duration6 = duration6;
    }

    public int getEffectiveOnPH1() {
        return EffectiveOnPH1;
    }

    public void setEffectiveOnPH1(int effectiveOnPH1) {
        EffectiveOnPH1 = effectiveOnPH1;
    }

    public int getEffectiveOnPH2() {
        return EffectiveOnPH2;
    }

    public void setEffectiveOnPH2(int effectiveOnPH2) {
        EffectiveOnPH2 = effectiveOnPH2;
    }

    public int getEffectiveOnPH3() {
        return EffectiveOnPH3;
    }

    public void setEffectiveOnPH3(int effectiveOnPH3) {
        EffectiveOnPH3 = effectiveOnPH3;
    }

    public int getEffectiveOnPH4() {
        return EffectiveOnPH4;
    }

    public void setEffectiveOnPH4(int effectiveOnPH4) {
        EffectiveOnPH4 = effectiveOnPH4;
    }

    public int getEffectiveOnPH5() {
        return EffectiveOnPH5;
    }

    public void setEffectiveOnPH5(int effectiveOnPH5) {
        EffectiveOnPH5 = effectiveOnPH5;
    }

    public int getEffectiveOnPH6() {
        return EffectiveOnPH6;
    }

    public void setEffectiveOnPH6(int effectiveOnPH6) {
        EffectiveOnPH6 = effectiveOnPH6;
    }

    public String getEndTime1() {
        return EndTime1;
    }

    public void setEndTime1(String endTime1) {
        EndTime1 = endTime1;
    }

    public String getEndTime2() {
        return EndTime2;
    }

    public void setEndTime2(String endTime2) {
        EndTime2 = endTime2;
    }

    public String getEndTime3() {
        return EndTime3;
    }

    public void setEndTime3(String endTime3) {
        EndTime3 = endTime3;
    }

    public String getEndTime4() {
        return EndTime4;
    }

    public void setEndTime4(String endTime4) {
        EndTime4 = endTime4;
    }

    public String getEndTime5() {
        return EndTime5;
    }

    public void setEndTime5(String endTime5) {
        EndTime5 = endTime5;
    }

    public String getEndTime6() {
        return EndTime6;
    }

    public void setEndTime6(String endTime6) {
        EndTime6 = endTime6;
    }

    public String getExemption1() {
        return Exemption1;
    }

    public void setExemption1(String exemption1) {
        Exemption1 = exemption1;
    }

    public String getExemption2() {
        return Exemption2;
    }

    public void setExemption2(String exemption2) {
        Exemption2 = exemption2;
    }

    public String getExemption3() {
        return Exemption3;
    }

    public void setExemption3(String exemption3) {
        Exemption3 = exemption3;
    }

    public String getExemption4() {
        return Exemption4;
    }

    public void setExemption4(String exemption4) {
        Exemption4 = exemption4;
    }

    public String getExemption5() {
        return Exemption5;
    }

    public void setExemption5(String exemption5) {
        Exemption5 = exemption5;
    }

    public String getExemption6() {
        return Exemption6;
    }

    public void setExemption6(String exemption6) {
        Exemption6 = exemption6;
    }

    public String getFromDay1() {
        return FromDay1;
    }

    public void setFromDay1(String fromDay1) {
        FromDay1 = fromDay1;
    }

    public String getFromDay2() {
        return FromDay2;
    }

    public void setFromDay2(String fromDay2) {
        FromDay2 = fromDay2;
    }

    public String getFromDay3() {
        return FromDay3;
    }

    public void setFromDay3(String fromDay3) {
        FromDay3 = fromDay3;
    }

    public String getFromDay4() {
        return FromDay4;
    }

    public void setFromDay4(String fromDay4) {
        FromDay4 = fromDay4;
    }

    public String getFromDay5() {
        return FromDay5;
    }

    public void setFromDay5(String fromDay5) {
        FromDay5 = fromDay5;
    }

    public String getFromDay6() {
        return FromDay6;
    }

    public void setFromDay6(String fromDay6) {
        FromDay6 = fromDay6;
    }

    public String getStartTime1() {
        return StartTime1;
    }

    public void setStartTime1(String startTime1) {
        StartTime1 = startTime1;
    }

    public String getStartTime2() {
        return StartTime2;
    }

    public void setStartTime2(String startTime2) {
        StartTime2 = startTime2;
    }

    public String getStartTime3() {
        return StartTime3;
    }

    public void setStartTime3(String startTime3) {
        StartTime3 = startTime3;
    }

    public String getStartTime4() {
        return StartTime4;
    }

    public void setStartTime4(String startTime4) {
        StartTime4 = startTime4;
    }

    public String getStartTime5() {
        return StartTime5;
    }

    public void setStartTime5(String startTime5) {
        StartTime5 = startTime5;
    }

    public String getStartTime6() {
        return StartTime6;
    }

    public void setStartTime6(String startTime6) {
        StartTime6 = startTime6;
    }

    public String getToDay1() {
        return ToDay1;
    }

    public void setToDay1(String toDay1) {
        ToDay1 = toDay1;
    }

    public String getToDay2() {
        return ToDay2;
    }

    public void setToDay2(String toDay2) {
        ToDay2 = toDay2;
    }

    public String getToDay3() {
        return ToDay3;
    }

    public void setToDay3(String toDay3) {
        ToDay3 = toDay3;
    }

    public String getToDay4() {
        return ToDay4;
    }

    public void setToDay4(String toDay4) {
        ToDay4 = toDay4;
    }

    public String getToDay5() {
        return ToDay5;
    }

    public void setToDay5(String toDay5) {
        ToDay5 = toDay5;
    }

    public String getToDay6() {
        return ToDay6;
    }

    public void setToDay6(String toDay6) {
        ToDay6 = toDay6;
    }

    public String getTypeDesc1() {
        return TypeDesc1;
    }

    public void setTypeDesc1(String typeDesc1) {
        TypeDesc1 = typeDesc1;
    }

    public String getTypeDesc2() {
        return TypeDesc2;
    }

    public void setTypeDesc2(String typeDesc2) {
        TypeDesc2 = typeDesc2;
    }

    public String getTypeDesc3() {
        return TypeDesc3;
    }

    public void setTypeDesc3(String typeDesc3) {
        TypeDesc3 = typeDesc3;
    }

    public String getTypeDesc4() {
        return TypeDesc4;
    }

    public void setTypeDesc4(String typeDesc4) {
        TypeDesc4 = typeDesc4;
    }

    public String getTypeDesc5() {
        return TypeDesc5;
    }

    public void setTypeDesc5(String typeDesc5) {
        TypeDesc5 = typeDesc5;
    }

    public String getTypeDesc6() {
        return TypeDesc6;
    }

    public void setTypeDesc6(String typeDesc6) {
        TypeDesc6 = typeDesc6;
    }
}
