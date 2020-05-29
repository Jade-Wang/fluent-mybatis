package cn.org.atool.fluent.mybatis.generator.template.mapper;

import org.test4j.generator.mybatis.config.TableInfo;
import org.test4j.generator.mybatis.template.BaseTemplate;

import java.util.Map;

public class PartitionMapperTemplate extends BaseTemplate {
    public PartitionMapperTemplate() {
        super("templates/mapper/Partition.java.vm", "mapper/*PartitionMapper.java");
        super.setPartition(true);
    }

    @Override
    public String getTemplateId() {
        return "partition";
    }

    @Override
    protected void templateConfigs(TableInfo table, Map<String, Object> context) {
    }
}