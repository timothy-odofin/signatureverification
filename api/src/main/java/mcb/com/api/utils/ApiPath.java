package mcb.com.api.utils;

public class ApiPath {
    public final static String AUTH_PATH="/auth";
    public final static String ACCOUNT_PATH="/account";
    public final static String LIST_EVENT_SOURCE_PATH="/event-sources";
    public final static String UPDATE_EVENT_SOURCE_PATH=LIST_EVENT_SOURCE_PATH+"/{event-pid}";
    public final static String LIST_EVENT_SOURCE_SUMMARY_PATH="/event-sources/summary";
    public final static String LIST_USERS_PATH="/users";
    public final static String RETRIEVE_SIGNATURE_PATH="/signature/retrieve";
    public final static String VALIDATE_SIGNATURE_PATH="/signature/validate";
    public final static String CURRENCY_PATH="/currencies";
}
