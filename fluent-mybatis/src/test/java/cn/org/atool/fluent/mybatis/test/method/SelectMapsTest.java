package cn.org.atool.fluent.mybatis.test.method;

import cn.org.atool.fluent.mybatis.demo.generate.datamap.TM;
import cn.org.atool.fluent.mybatis.demo.generate.mapper.UserMapper;
import cn.org.atool.fluent.mybatis.demo.generate.query.UserEntityQuery;
import cn.org.atool.fluent.mybatis.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author darui.wu
 * @create 2019/10/29 9:33 下午
 */
public class SelectMapsTest extends BaseTest {
    @Autowired
    private UserMapper mapper;

    @Test
    public void test_selectMaps() throws Exception {
        db.table(t_user).clean()
            .insert(TM.user.createWithInit(4)
                .id.values(23, 24, 25, 26)
                .user_name.values("u1", "u2", "u3", "u2")
            );
        UserEntityQuery query = new UserEntityQuery()
            .and.id.eq(24L);
        List<Map<String, Object>> users = mapper.selectMaps(query);
        db.sqlList().wantFirstSql().start("SELECT").end("FROM t_user WHERE (id = ?)");
        want.list(users).eqDataMap(TM.user.create(1)
            .user_name.values("u2"));
    }

    @Test
    public void test_selectMaps_hasMultiple() throws Exception {
        db.table(t_user).clean()
            .insert(TM.user.createWithInit(4)
                .id.values(23, 24, 25, 26)
                .user_name.values("u1", "u2", "u3", "u2")
            );
        UserEntityQuery query = new UserEntityQuery()
            .and.userName.eq("u2");
        List<Map<String, Object>> users = mapper.selectMaps(query);
        db.sqlList().wantFirstSql().start("SELECT").end("FROM t_user WHERE (user_name = ?)");
        want.list(users).eqDataMap(TM.user.create(2)
            .user_name.values("u2"));
    }
}