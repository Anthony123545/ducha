package com.supervision.system.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import org.apache.ibatis.annotations.Param;
import com.supervision.common.core.domain.entity.SysDept;

/**
 * 部门管理 数据层
 *
 * @author supervision
 */
public interface SysDeptMapper {

    /**
     * 获取所有营级单位
     * @return
     */
    List<Map<String,SysDept>> selectAllCamp();

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId            角色ID
     * @param deptCheckStrictly 部门树选择项是否关联显示
     * @return 选中部门列表
     */
    public List<Integer> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    public List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 修改所在部门正常状态
     *
     * @param deptIds 部门ID组
     */
    public void updateDeptStatusNormal(Long[] deptIds);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    List<SysDept> selectDeptLists(SysDept sysDept);
    /**根据部门id获取部门名称*/
    List<SysDept> selectname(@Param("split") String[] split);

    /**查询顶级部门*/
    SysDept selectTopDept(Long parentId);

    List<SysDept> selectDeptByBattalion(Long parentId);

    List<SysDept> selectPeopleList(Long parentId);


    List<SysDept> selectDeptListByEven(Long parentId);

    List<SysDept> selectSysDeptByIdList(@Param("idList") List<Long> idList);

    List<Long> selectDeptTwoById(Long deptId);

    List<Long> selectDeptTwoListById(@Param("set") Set<Long> deptId);

    List<Long> selectOneDeptIdList(Long deptId);
}
