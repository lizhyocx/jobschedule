package com.lizhy.auto.dao;

import com.lizhy.auto.model.ScheduleJobDO;

import java.util.List;
import java.util.Map;

public interface ScheduleJobDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job
     *
     * @mbg.generated 2017-10-18 15:11:51
     */
    int insert(ScheduleJobDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job
     *
     * @mbg.generated 2017-10-18 15:11:51
     */
    int insertSelective(ScheduleJobDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job
     *
     * @mbg.generated 2017-10-18 15:11:51
     */
    ScheduleJobDO selectByPrimaryKey(Long jobId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job
     *
     * @mbg.generated 2017-10-18 15:11:51
     */
    int updateByPrimaryKeySelective(ScheduleJobDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job
     *
     * @mbg.generated 2017-10-18 15:11:51
     */
    int updateByPrimaryKey(ScheduleJobDO record);

    int selectCountByParam(Map<String, Object> params);

    List<ScheduleJobDO> selectListByParam(Map<String, Object> params);

    int updateJobStatus(Map<String, Object> params);
}