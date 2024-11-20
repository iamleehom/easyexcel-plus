package ai.chat2db.excel.test.core.extra;

import java.io.File;

import ai.chat2db.excel.enums.CellExtraTypeEnum;
import ai.chat2db.excel.read.listener.ReadListener;
import ai.chat2db.excel.test.util.TestFileUtil;
import ai.chat2db.excel.EasyExcel;
import ai.chat2db.excel.context.AnalysisContext;
import ai.chat2db.excel.metadata.CellExtra;
import com.alibaba.fastjson2.JSON;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
public class ExtraDataTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtraDataTest.class);
    private static File file03;
    private static File file07;

    private static File extraRelationships;

    @BeforeAll
    public static void init() {
        file03 = TestFileUtil.readFile("extra" + File.separator + "extra.xls");
        file07 = TestFileUtil.readFile("extra" + File.separator + "extra.xlsx");
        extraRelationships = TestFileUtil.readFile("extra" + File.separator + "extraRelationships.xlsx");
    }

    @Test
    public void t01Read07() {
        read(file07);
    }

    @Test
    public void t02Read03() {
        read(file03);
    }

    @Test
    public void t03Read() {
        EasyExcel.read(extraRelationships, ExtraData.class, new ReadListener() {
                @Override
                public void invoke(Object data, AnalysisContext context) {
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                }

                @Override
                public void extra(CellExtra extra, AnalysisContext context) {
                    LOGGER.info("extra data:{}", JSON.toJSONString(extra));
                    switch (extra.getType()) {
                        case HYPERLINK:
                            if ("222222222".equals(extra.getText())) {
                                Assertions.assertEquals(1, (int)extra.getRowIndex());
                                Assertions.assertEquals(0, (int)extra.getColumnIndex());
                            } else if ("333333333333".equals(extra.getText())) {
                                Assertions.assertEquals(1, (int)extra.getRowIndex());
                                Assertions.assertEquals(1, (int)extra.getColumnIndex());
                            } else {
                                Assertions.fail("Unknown hyperlink!");
                            }
                            break;
                        default:
                    }
                }
            })
            .extraRead(CellExtraTypeEnum.HYPERLINK)
            .sheet()
            .doRead();
    }

    private void read(File file) {
        EasyExcel.read(file, ExtraData.class, new ExtraDataListener()).extraRead(CellExtraTypeEnum.COMMENT)
            .extraRead(CellExtraTypeEnum.HYPERLINK).extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
    }

}
