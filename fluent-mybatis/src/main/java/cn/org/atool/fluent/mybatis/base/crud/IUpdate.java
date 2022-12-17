package cn.org.atool.fluent.mybatis.base.crud;

import cn.org.atool.fluent.common.kits.StrKey;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.entity.IMapping;
import cn.org.atool.fluent.mybatis.base.mapper.IRichMapper;
import cn.org.atool.fluent.mybatis.base.mapper.UpdaterExecutor;
import cn.org.atool.fluent.mybatis.mapper.WrapperFunction;
import cn.org.atool.fluent.mybatis.segment.BaseWrapper;
import cn.org.atool.fluent.mybatis.segment.WhereBase;
import cn.org.atool.fluent.mybatis.segment.fragment.Column;
import cn.org.atool.fluent.mybatis.segment.model.WrapperData;
import cn.org.atool.fluent.mybatis.utility.RefKit;

import java.util.Optional;
import java.util.function.Function;

import static cn.org.atool.fluent.mybatis.mapper.WrapperFunction.IUpdateFunction;
import static cn.org.atool.fluent.mybatis.utility.MybatisUtil.assertNotNull;

/**
 * 更新构造器基类
 *
 * @param <E> 实体类型
 * @author wudarui
 */
@SuppressWarnings({"unchecked", "rawtypes", "UnusedReturnValue"})
public interface IUpdate<E extends IEntity> {
    /**
     * 数据库映射定义
     *
     * @return Optional<IMapping>
     */
    Optional<IMapping> mapping();

    /**
     * 设置更新值
     *
     * @param column 更新字段
     * @param value  更新值
     * @param <U>    更新器类型
     * @return self
     */
    default <U extends IUpdate<E>> U updateSet(String column, Object value) {
        this.data().updateSet(Column.set((IWrapper) this, column), value);
        return (U) this;
    }

    /**
     * 设置limit值
     *
     * @param limit limit
     * @return self
     */
    <U extends IUpdate<E>> U limit(int limit);

    /**
     * 追加在sql语句的末尾
     * !!!慎用!!!
     * 有sql注入风险
     *
     * @param lastSql 追加SQL
     * @return self
     */
    <U extends IUpdate<E>> U last(String lastSql);

    /**
     * 返回where
     *
     * @return self
     */
    WhereBase where();

    /**
     * 返回查询器或更新器对应的xml数据
     * 系统方法, 请勿调用
     *
     * @return WrapperData
     */
    WrapperData data();

    /**
     * 根据Updater定义执行后续操作
     * <p>
     * <pre>
     * 要使用本方法
     * 需要定义 {@link cn.org.atool.fluent.mybatis.spring.MapperFactory} spring bean
     *
     * same as {@link #of(IRichMapper)} 方法
     * </pre>
     */
    default UpdaterExecutor to() {
        Class entityClass = ((BaseWrapper) this).getEntityClass();
        assertNotNull("entity class", entityClass);
        IRichMapper mapper = RefKit.mapper(entityClass);
        return new UpdaterExecutor(mapper, this);
    }

    /**
     * 根据Updater定义执行后续操作
     *
     * @param mapper 执行操作的mapper
     * @return UpdaterExecutor
     */
    default UpdaterExecutor of(IRichMapper<E> mapper) {
        return new UpdaterExecutor(mapper, this);
    }

    /**
     * 执行更新操作
     *
     * @param executor 具体更新操作
     * @return 返回更新的记录数
     * @deprecated replaced by {@link #of(IRichMapper).method(...)}
     */
    @Deprecated
    default int execute(Function<IUpdate<E>, Integer> executor) {
        return executor.apply(this);
    }

    /**
     * 获取mybatis占位符sql语句和参数上下文
     *
     * @param updater IUpdateFunction
     * @return sql语句 + 上下文
     */
    @SuppressWarnings("rawtypes")
    default StrKey sql(IUpdateFunction updater) {
        return WrapperFunction.sql((IWrapper) this, updater);
    }
}