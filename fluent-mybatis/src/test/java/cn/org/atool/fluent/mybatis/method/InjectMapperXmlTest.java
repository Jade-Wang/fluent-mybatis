package cn.org.atool.fluent.mybatis.method;

import cn.org.atool.fluent.mybatis.demo.generate.mapper.UserMapper;
import cn.org.atool.fluent.mybatis.method.model.InjectMapperXml;
import org.junit.jupiter.api.Test;
import org.test4j.hamcrest.matcher.string.StringMode;
import org.test4j.junit5.Test4J;
import org.test4j.tools.commons.ResourceHelper;

import java.io.File;

/**
 * MethodInjectTest
 *
 * @author:darui.wu Created by darui.wu on 2020/5/25.
 */
class InjectMapperXmlTest extends Test4J {

    @Test
    void buildMapperXml() throws Exception {
        String result = InjectMapperXml.buildMapperXml(UserMapper.class, new InjectMethods.DefaultInjectMethods().methods());
        String xml = ResourceHelper.readFromFile(new File(System.getProperty("user.dir") + "/src/test/java/cn/org/atool/fluent/mybatis/method/UserMapper.xml"));
        want.string(result).eq(xml, StringMode.IgnoreSpace);
    }
}