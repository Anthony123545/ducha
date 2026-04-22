package com.supervision.peopleChuRu.service.impl;

import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.peopleChuRu.domain.BdglLeaveFu;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import com.supervision.peopleChuRu.mapper.BdglLeaveFuMapper;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleChuRu.mapper.BdglLeaveMapper;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.service.IBdglLeaveService;

/**
 * 人员请假Service业务层处理
 *
 * @author supervision
 * @date 2022-03-02
 */
@Service
public class BdglLeaveServiceImpl implements IBdglLeaveService {
    @Autowired
    private BdglLeaveMapper bdglLeaveMapper;
    @Autowired
    private BdglPeopleMapper peopleMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private BdglLeaveFuMapper leaveFuMapper;
    @Autowired
    private IBdglPeopleService bdglPeopleService;

    /**
     * 查询人员请假
     *
     * @param id 人员请假主键
     * @return 人员请假
     */
    @Override
    public BdglLeave selectBdglLeaveById(Long id) {
        return bdglLeaveMapper.selectBdglLeaveById(id);
    }

    /**
     * 查询人员请假列表
     *
     * @param bdglLeave 人员请假
     * @return 人员请假
     */
    @Override
    public List<BdglLeave> selectBdglLeaveList(BdglLeave bdglLeave) {
        List<BdglLeave> bdglLeaves = bdglLeaveMapper.selectBdglLeaveList(bdglLeave);
        for (BdglLeave bdglLeaf : bdglLeaves) {
            BdglLeaveFu bdglLeaveFu = new BdglLeaveFu();
            bdglLeaveFu.setLeaveId(bdglLeaf.getId());
            List<BdglLeaveFu> bdglLeaveFus = leaveFuMapper.selectBdglLeaveFuList(bdglLeaveFu);
            bdglLeaf.setBdglLeaveFus(bdglLeaveFus);
        }
        return bdglLeaves;
    }

    @Override
    public List<BdglLeave> selectBdglLeaveListsss(BdglLeave bdglLeave) {
        List<BdglLeave> bdglLeaves = bdglLeaveMapper.selectBdglLeaveListsss(bdglLeave);
        for (BdglLeave bdglLeaf : bdglLeaves) {
            BdglLeaveFu bdglLeaveFu = new BdglLeaveFu();
            bdglLeaveFu.setLeaveId(bdglLeaf.getId());
            List<BdglLeaveFu> bdglLeaveFus = leaveFuMapper.selectBdglLeaveFuList(bdglLeaveFu);
            bdglLeaf.setBdglLeaveFus(bdglLeaveFus);
        }
        return bdglLeaves;
    }

    @Override
    public List<BdglLeave> selectBdglLeaveByPeopleId(Long id) {
        return bdglLeaveMapper.selectBdglLeaveByPeopleId(id);
    }

    @Override
    public BdglLeave selectNewLeaveByCarNumber(String license) {
        return bdglLeaveMapper.selectNewLeaveByCarNumber(license);
    }

    /**
     * 新增人员请假
     *
     * @param bdglLeave 人员请假
     * @return 结果
     */
    @Override
    public int insertBdglLeave(BdglLeave bdglLeave) {
        String name = "";
        for (String s : bdglLeave.getPeopleId().split(",")) {
            BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(s));
            name += bdglPeople.getName() + ",";
        }
        bdglLeave.setPeopleName(name.substring(0,name.length()-1));
        SysDept sysDept = deptMapper.selectDeptById(bdglLeave.getUnitId());
        bdglLeave.setUnitName(sysDept.getDeptName());
        return bdglLeaveMapper.insertBdglLeave(bdglLeave);
    }

    /**
     * 修改人员请假
     *
     * @param bdglLeave 人员请假
     * @return 结果
     */
    @Override
    public int updateBdglLeave(BdglLeave bdglLeave) {
        BdglLeave bdglLeave1 = bdglLeaveMapper.selectBdglLeaveById(bdglLeave.getId());
        boolean b = bdglLeave.getPeopleId2() == null && bdglLeave.getStatus1().equals("1");
        String[] split = bdglLeave1.getPeopleId().split(",");
        for (String s : split) {
            BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(s));

            if (bdglLeave.getStatus4() == 1 || bdglLeave.getStatus4() == 3 && bdglLeave.getRejoin() != 1) {
                if (bdglLeave.getRejoin() != 1) {

                    bdglPeople.setReign(Integer.parseInt(bdglLeave.getLeaveType()));
                    peopleMapper.updateBdglPeople(bdglPeople);
                }
            } else if (bdglLeave1.getPeopleId2() == null && (bdglLeave.getStatus1().equals(1) || bdglLeave.getStatus1().equals(3)) && bdglLeave.getRejoin() != 1) {
                bdglPeople.setReign(Integer.parseInt(bdglLeave.getLeaveType()));
                peopleMapper.updateBdglPeople(bdglPeople);
            } else if (bdglLeave1.getPeopleId3() == null && (bdglLeave.getStatus2().equals(1) || bdglLeave.getStatus2().equals(3)) && bdglLeave.getRejoin() != 1) {
                bdglPeople.setReign(Integer.parseInt(bdglLeave.getLeaveType()));
                peopleMapper.updateBdglPeople(bdglPeople);
            } else if (bdglLeave1.getPeopleId4() == null && (bdglLeave.getStatus3().equals(1) || bdglLeave.getStatus3().equals(3)) && bdglLeave.getRejoin() != 1) {
                bdglPeople.setReign(Integer.parseInt(bdglLeave.getLeaveType()));
                peopleMapper.updateBdglPeople(bdglPeople);
            }
        }
        return bdglLeaveMapper.updateBdglLeave(bdglLeave);
    }

    /**
     * 批量删除人员请假
     *
     * @param ids 需要删除的人员请假主键
     * @return 结果
     */
    @Override
    public int deleteBdglLeaveByIds(Long[] ids) {
        for (Long id : ids) {
            // 1. 根据ID查出假单，拿到里面的人员ID
            BdglLeave leave = bdglLeaveMapper.selectBdglLeaveById(id);
            if (leave != null && leave.getPeopleId() != null) {
                // 2. 将关联的所有人员 reign 状态重置为 0
                String[] pIds = leave.getPeopleId().split(",");
                for (String pId : pIds) {
                    BdglPeople p = bdglPeopleService.selectBdglPeopleById(Long.valueOf(pId));
                    if (p != null) {
                        p.setReign(0); // 强制变回“在位”
                        bdglPeopleService.updateBdglPeople(p);
                    }
                }
            }
        }
        return bdglLeaveMapper.deleteBdglLeaveByIds(ids);
    }

    /**
     * 删除人员请假信息
     *
     * @param id 人员请假主键
     * @return 结果
     */
    @Override
    public int deleteBdglLeaveById(Long id) {
        // 1. 根据ID查出假单，拿到里面的人员ID
        BdglLeave leave = bdglLeaveMapper.selectBdglLeaveById(id);
        if (leave != null && leave.getPeopleId() != null) {
            // 2. 将关联的所有人员 reign 状态重置为 0
            String[] pIds = leave.getPeopleId().split(",");
            for (String pId : pIds) {
                BdglPeople p = bdglPeopleService.selectBdglPeopleById(Long.valueOf(pId));
                if (p != null) {
                    p.setReign(0); // 强制变回“在位”
                    bdglPeopleService.updateBdglPeople(p);
                }
            }
        }
        return bdglLeaveMapper.deleteBdglLeaveById(id);
    }

    @Override
    public List<BdglLeave> selectBdglLeaveListes() {
        return bdglLeaveMapper.selectBdglLeaveListes();
    }

    @Override
    public List<BdglLeave> selectBdglLeaveLists() {
        return bdglLeaveMapper.selectBdglLeaveLists();
    }
}
