package com.gwg.demo.mapper;

import com.gwg.demo.domain.SysOperationLog;
import com.gwg.demo.domain.SysOperationLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysOperationLogMapper {
    int countByExample(SysOperationLogExample example);

    int deleteByExample(SysOperationLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysOperationLog record);

    int insertSelective(SysOperationLog record);

    List<SysOperationLog> selectByExample(SysOperationLogExample example);

    SysOperationLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysOperationLog record, @Param("example") SysOperationLogExample example);

    int updateByExample(@Param("record") SysOperationLog record, @Param("example") SysOperationLogExample example);

    int updateByPrimaryKeySelective(SysOperationLog record);

    int updateByPrimaryKey(SysOperationLog record);
}