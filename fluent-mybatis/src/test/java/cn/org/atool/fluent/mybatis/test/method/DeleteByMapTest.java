package cn.org.atool.fluent.mybatis.test.method;

import cn.org.atool.fluent.mybatis.demo.generate.ITable;
import cn.org.atool.fluent.mybatis.demo.generate.datamap.TM;
import cn.org.atool.fluent.mybatis.demo.generate.mapper.UserMapper;
import cn.org.atool.fluent.mybatis.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.test4j.hamcrest.matcher.string.StringMode;

import java.util.Arrays;
import java.util.HashMap;

public class DeleteByMapTest extends BaseTest {
    @Autowired
    private UserMapper mapper;

    @Test
    public void testDeleteByIds() {
        db.table(ITable.t_user).clean().insert(TM.user.createWithInit(2)
            .id.values(23L, 24L)
            .user_name.values("user1", "user2")
        );
        mapper.deleteByMap(new HashMap<String, Object>() {
            {
                this.put("id", 24);
                this.put("user_name", "user2");
            }
        });
        db.sqlList().wantFirstSql()
            .eq("DELETE FROM t_user WHERE user_name = ? AND id = ?", StringMode.SameAsSpace);
        db.table(ITable.t_user).query().eqDataMap(TM.user.create(1)
            .id.values(23L)
        );
    }
}