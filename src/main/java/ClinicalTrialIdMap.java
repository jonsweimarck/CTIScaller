import java.util.HashMap;

public class ClinicalTrialIdMap {

    public static String UAT_JW1 = "uat-UAT_JW1";
    public static String UAT_JW3 = "UAT_JW3";
    public static String UAT_JW4 = "UAT_JW4";
    public static String UAT_JW5 = "UAT_JW5";
    public static String UAT_JW6 = "UAT_JW6";
    public static String UAT_JW7 = "UAT_JW7";
    public static String UAT_JW8 = "UAT_JW8";
    public static String UAT_JW9 = "UAT_JW9";
    public static String UAT_JW10 = "UAT_JW10";
    public static String UAT_JW11 = "UAT_JW11";
    public static String UAT_JW12 = "UAT_JW12";
    public static String MPA_SÖ_211006_1 = "MPA_SÖ_211006_1";
    public static String MPA_SÖ_211006_2 = "MPA_SÖ_211006_2";
    public static String MPA_SG = "MPA_SG";
    public static String MPA_ = "MPA_";
    public static String RBC_Exempel_1 = "RBC_Exempel_1";
    public static String RBC_Exempel_2 = "RBC_Exempel_2";


    public static HashMap<String, ClinicalTrialIds> name2Ids = new HashMap<>();

    static{
        name2Ids.put(UAT_JW1, new ClinicalTrialIds("uat-jw1", "2021-500058-32-00", "75"));
        name2Ids.put(UAT_JW3, new ClinicalTrialIds("uat-jw3", "2021-500718-27-00", "951"));
        name2Ids.put(UAT_JW4, new ClinicalTrialIds("uat-jw4", "2021-500956-73-00", "1284"));
        name2Ids.put(UAT_JW5, new ClinicalTrialIds("uat-jw5", "2021-501039-15-00", "1486"));
        name2Ids.put(UAT_JW6, new ClinicalTrialIds("uat-jw6", "2021-501061-70-00", "1549"));
        name2Ids.put(UAT_JW7, new ClinicalTrialIds("uat-jw7", "2021-501074-10-00", "1568"));
        name2Ids.put(UAT_JW8, new ClinicalTrialIds("uat-jw8", "2021-501083-14-00", "1579"));
        name2Ids.put(UAT_JW9, new ClinicalTrialIds("uat-jw9", "2021-501087-15-00", "1597"));
        name2Ids.put(UAT_JW10, new ClinicalTrialIds("uat-jw10", "2021-501088-34-00", "1598"));
        name2Ids.put(UAT_JW11, new ClinicalTrialIds("uat-jw11", "2021-501099-26-00", "1619"));
        name2Ids.put(UAT_JW12, new ClinicalTrialIds("uat-jw12", "2021-501159-23-00", "1706"));
        name2Ids.put(MPA_SÖ_211006_1, new ClinicalTrialIds("MPA_SÖ_211006_to test haow documentURL", "2021-500994-17-00", "1329"));
        name2Ids.put(MPA_SÖ_211006_2, new ClinicalTrialIds("MPA_SÖ_211006_to test haow documentURL", "2021-501000-14-00", "1335"));
        name2Ids.put(MPA_SG, new ClinicalTrialIds("MPA_SG", "2021-501073-37-00", "1564"));
        name2Ids.put(MPA_, new ClinicalTrialIds("MPA_", "2021-501005-20-00", "1347"));
        name2Ids.put(RBC_Exempel_1, new ClinicalTrialIds(RBC_Exempel_1, "2021-501192-91-00", "1800"));
        name2Ids.put(RBC_Exempel_2, new ClinicalTrialIds(RBC_Exempel_2, "2021-501200-18-00", "1809"));

    }

    public static ClinicalTrialIds get(String name) {
        return ClinicalTrialIdMap.name2Ids.get(name);
    }
}
