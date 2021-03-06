package data;

public class StorageService {

    public static final String STORAGE_TYPE_TEXT = "STORAGE_TYPE_TEXT";
    public static final String STORAGE_TYPE_OBJECT = "STORAGE_TYPE_OBJECT";
    public static final String STORAGE_TYPE_SQL = "STORAGE_TYPE_SQL";


    private TextStorage textStorage = new TextStorage();
    private ObjectStorage objectStorage = new ObjectStorage();
    private SQLStorage sqlStorage = new SQLStorage();

    /**
     *
     * @param storageType
     * @return
     */
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
