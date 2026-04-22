package com.supervision.peopleManage.mapper;

import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.domain.dto.StatOutPeopleDto;
import com.supervision.peopleManage.domain.dto.StatPersonAgeDto;
import com.supervision.peopleManage.domain.dto.StatPersonZzmmDto;
import com.supervision.print.domain.PrintsLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 人员管理Mapper接口
 *
 * @author supervision
 * @date 2022-01-22
 */
public interface BdglPeopleMapper
{
    /**
     * 查询人员管理
     *
     * @param id 人员管理主键
     * @return 人员管理
     */
    public BdglPeople selectBdglPeopleById(Long id);
    /**
     * 查询人员管理列表分权
     * @param bdglPeople
     * @return
     */
    public List<BdglPeople> selectPowerPeoleList(BdglPeople bdglPeople);
    /**
     * 查询人员管理列表
     *
     * @param bdglPeople 人员管理
     * @return 人员管理集合
     */
    public List<BdglPeople> selectBdglPeopleList(BdglPeople bdglPeople);

    /**
     * 新增人员管理
     *
     * @param bdglPeople 人员管理
     * @return 结果
     */
    public int insertBdglPeople(BdglPeople bdglPeople);

    /**
     * 修改人员管理
     *
     * @param bdglPeople 人员管理
     * @return 结果
     */
    public int updateBdglPeople(BdglPeople bdglPeople);

    /**
     * 删除人员管理
     *
     * @param id 人员管理主键
     * @return 结果
     */
    public int deleteBdglPeopleById(Long id);

    /**
     * 批量删除人员管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglPeopleByIds(Long[] ids);
    /**获取当前部门  在位 在编 未在编  人员情况*/
    Map<String, Object> getDeptXiangQi(BdglPeople bdglPeople);

    List<BdglPeople> select(@Param("split") String[] split);

    /**
     * 根据人员状态和是否在编 获取人员数量
     * @param type 是否在编
     * @return
     */
    public List<Map<String, String>> selectPeopleByRegin(String type);
    //根据人员id获取人员姓名
    List<BdglPeople> selectname(@Param("split") String[] split);

   // <!-- 根据人员状态和是否是否纳编来查询人员信息 -->
    List<BdglPeople> selectBdglPeopleInfo(BdglPeople bdglPeople);
    /**查询当前部门及下属部门人员*/
    List<BdglPeople> selectPowerPeoleLists(BdglPeople bdglPeople);

    /**
     * 查询未体检人员
     * @param bdglPeople
     * @return
     */
    List<BdglPeople> selectExaminationList(BdglPeople bdglPeople);

    Map<String, Object> examinationCountss(BdglPeople bdglPeople);


    List<HashMap<String, Object>> selectGroupBYPeope(BdglPeople bdglPeople);

    int selectTotalCountByDeptId(Long deptId);

    int selectReginCountByDeptId(Long deptId);

    int selectBdglPeopleByunitId(@Param("unitId") int unitId, @Param("year") String year);

    List<BdglPeople> selectBdglPeopleOnUnitList(BdglPeople bdglPeople);

    int selectReginCount2ByDeptId(Long deptId);

    List<StatOutPeopleDto> selectReginByDeptId(Long deptId);

    List<StatPersonAgeDto> selectPersonAgeQjList(List<Long> deptIdList);

    List<StatPersonZzmmDto> selectZzmmAndCountWithDeptId(List<Long> deptIdList);

    List<StatOutPeopleDto> selectReginCount(List<Long> deptIdList);

    List<StatPersonAgeDto> selectPersonAgeDtoAllList(@Param("set") Set<Long> deptId);

    List<StatPersonZzmmDto> selectZzmmAndCountAll(@Param("set") Set<Long> deptId);

    List<BdglPeople> countTypeNumber();

    Map<String, Object> getDeptXiangQi2(BdglPeople bdglPeople);

    Map<String, Object> getDeptXiangQi3(BdglPeople bdglPeople);
    
    BdglPeople selectBdglPeople(String ename);
    
    List<String> selectPowerPeoleListes(String peopleId);
    
    List<BdglPeople> selectPowerPeoleListsss(BdglPeople bdglPeople);

    @Select("select idcard from bdgl_people")
    List<String> selectIdcard();

    BdglPeople selectBdglPeopleByEmployeeNumber(String employeeNumber);
}
