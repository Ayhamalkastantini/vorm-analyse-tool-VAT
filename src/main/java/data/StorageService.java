package data;

public class StorageService {
    public static final String STORAGE_TYPE_TEXT = "STORAGE_TYPE_TEXT";
    public static final String STORAGE_TYPE_OBJECT = "STORAGE_TYPE_OBJECT";
    public static final String STORAGE_TYPE_SQL = "STORAGE_TYPE_SQL";


    /**
     * Text Storage instance
     */
    private TextStorage textStorage = new TextStorage();

    /**
     * Object Storage instance
     */
    private ObjectStorage objectStorage = new ObjectStorage();

    /**
     * SQL Storage instance
     */
    private SQLStorage sqlStorage = new SQLStorage();

    public StorageInterface getStorage(String storageType) {
        switch (storageType) {
            case STORAGE_TYPE_TEXT:
                return textStorage;
            case STORAGE_TYPE_OBJECT:
                return objectStorage;
            default:
                return null;
        }
    }
}
