package ai.chat2db.excel.cache.selector;

import ai.chat2db.excel.cache.ReadCache;
import org.apache.poi.openxml4j.opc.PackagePart;

/**
 * Choose a eternal cache
 *
 * @author Jiaju Zhuang
 **/
public class EternalReadCacheSelector implements ReadCacheSelector {
    private ReadCache readCache;

    public EternalReadCacheSelector(ReadCache readCache) {
        this.readCache = readCache;
    }

    @Override
    public ReadCache readCache(PackagePart sharedStringsTablePackagePart) {
        return readCache;
    }
}
