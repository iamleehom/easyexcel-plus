package ai.chat2db.excel.event;

/**
 * Implement this interface when sorting
 *
 * @author Jiaju Zhuang
 */
public interface Order {
    /**
     * The smaller the first implementation
     *
     * @return
     */
    int order();
}
