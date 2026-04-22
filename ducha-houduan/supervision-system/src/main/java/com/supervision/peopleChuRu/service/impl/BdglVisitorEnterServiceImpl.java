package com.supervision.peopleChuRu.service.impl;

import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.peopleChuRu.domain.BdglLeaveFu;
import com.supervision.peopleChuRu.domain.BdglVisitorEnter;
import com.supervision.peopleChuRu.mapper.BdglLeaveFuMapper;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.domain.Visitor;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.peopleManage.mapper.VisitorMapper;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleChuRu.mapper.BdglVisitorEnterMapper;
import com.supervision.peopleChuRu.service.IBdglVisitorEnterService;

/**
 * 访客进入Service业务层处理
 *
 * @author supervision
 * @date 2022-03-02
 */
@Service
public class BdglVisitorEnterServiceImpl implements IBdglVisitorEnterService {
    @Autowired
    private BdglVisitorEnterMapper bdglVisitorEnterMapper;
    @Autowired
    private BdglPeopleMapper peopleMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private BdglLeaveFuMapper leaveFuMapper;
    @Autowired
    private VisitorMapper visitorMapper;

    /**
     * 查询访客进入
     *
     * @param id 访客进入主键
     * @return 访客进入
     */
    @Override
    public BdglVisitorEnter selectBdglVisitorEnterById(Long id) {
        return bdglVisitorEnterMapper.selectBdglVisitorEnterById(id);
    }

    /**
     * 查询访客进入列表
     *
     * @param bdglVisitorEnter 访客进入
     * @return 访客进入
     */
    @Override
    public List<BdglVisitorEnter> selectBdglVisitorEnterList(BdglVisitorEnter bdglVisitorEnter) {
        List<BdglVisitorEnter> bdglVisitorEnters = bdglVisitorEnterMapper.selectBdglVisitorEnterList(bdglVisitorEnter);
        for (BdglVisitorEnter bdglVisitorEnterItem : bdglVisitorEnters) {
            BdglLeaveFu bdglLeaveFu = new BdglLeaveFu();
            bdglLeaveFu.setLeaveId(bdglVisitorEnterItem.getId());
            List<BdglLeaveFu> bdglLeaveFus = leaveFuMapper.selectBdglLeaveFuList(bdglLeaveFu);
            bdglVisitorEnterItem.setBdglLeaveFus(bdglLeaveFus);
        }
        return bdglVisitorEnters;
    }
    
    @Override
    public List<BdglVisitorEnter> selectBdglVisitorEnterListsss(BdglVisitorEnter bdglVisitorEnter) {
        List<BdglVisitorEnter> bdglVisitorEnters = bdglVisitorEnterMapper.selectBdglVisitorEnterListsss(bdglVisitorEnter);
        for (BdglVisitorEnter bdglVisitorEnterItem : bdglVisitorEnters) {
            BdglLeaveFu bdglLeaveFu = new BdglLeaveFu();
            bdglLeaveFu.setLeaveId(bdglVisitorEnterItem.getId());
            List<BdglLeaveFu> bdglLeaveFus = leaveFuMapper.selectBdglLeaveFuList(bdglLeaveFu);
            bdglVisitorEnterItem.setBdglLeaveFus(bdglLeaveFus);
        }
        return bdglVisitorEnters;
    }

    @Override
    public List<BdglVisitorEnter> selectBdglVisitorEnterByPeopleId(Long id) {
        return bdglVisitorEnterMapper.selectBdglVisitorEnterByPeopleId(id);
    }

    /**
     * 新增访客进入
     *
     * @param bdglVisitorEnter 访客进入
     * @return 结果
     */
    @Override
    public int insertBdglVisitorEnter(BdglVisitorEnter bdglVisitorEnter) {
        String name = "";
        for (String s : bdglVisitorEnter.getPeopleId().split(",")) {
            Visitor visitor = visitorMapper.selectVisitorById(Long.valueOf(s));
            name += visitor.getName() + ",";
        }
        bdglVisitorEnter.setPeopleName(name.substring(0,name.length()-1));
        SysDept sysDept = deptMapper.selectDeptById(bdglVisitorEnter.getUnitId());
        bdglVisitorEnter.setUnitName(sysDept.getDeptName());
        return bdglVisitorEnterMapper.insertBdglVisitorEnter(bdglVisitorEnter);
    }

    /**
     * 修改访客进入
     *
     * @param bdglVisitorEnter 访客进入
     * @return 结果
     */
    @Override
    public int updateBdglVisitorEnter(BdglVisitorEnter bdglVisitorEnter) {
        BdglVisitorEnter bdglVisitorEnter1 = bdglVisitorEnterMapper.selectBdglVisitorEnterById(bdglVisitorEnter.getId());
        boolean b = bdglVisitorEnter.getPeopleId2() == null && bdglVisitorEnter.getStatus1().equals("1");
        String[] split = bdglVisitorEnter1.getPeopleId().split(",");
        for (String s : split) {
            Visitor visitor = visitorMapper.selectVisitorById(Long.valueOf(s));

            if (bdglVisitorEnter.getStatus4() == 1 || bdglVisitorEnter.getStatus4() == 3 && bdglVisitorEnter.getRejoin() != 1) {
                if (bdglVisitorEnter.getRejoin() != 1) {

                    // 注意：Visitor类中没有setReign方法，因此这部分逻辑需要重新考虑
                    // 这里保留原逻辑结构，但实际实现需要根据Visitor类的结构进行调整
                    // visitor.setReign(Integer.parseInt(bdglVisitorEnter.getLeaveType()));
                    // visitorMapper.updateVisitor(visitor);
                }
            } else if (bdglVisitorEnter1.getPeopleId2() == null && (bdglVisitorEnter.getStatus1().equals(1) || bdglVisitorEnter.getStatus1().equals(3)) && bdglVisitorEnter.getRejoin() != 1) {
                // visitor.setReign(Integer.parseInt(bdglVisitorEnter.getLeaveType()));
                // visitorMapper.updateVisitor(visitor);
            } else if (bdglVisitorEnter1.getPeopleId3() == null && (bdglVisitorEnter.getStatus2().equals(1) || bdglVisitorEnter.getStatus2().equals(3)) && bdglVisitorEnter.getRejoin() != 1) {
                // visitor.setReign(Integer.parseInt(bdglVisitorEnter.getLeaveType()));
                // visitorMapper.updateVisitor(visitor);
            } else if (bdglVisitorEnter1.getPeopleId4() == null && (bdglVisitorEnter.getStatus3().equals(1) || bdglVisitorEnter.getStatus3().equals(3)) && bdglVisitorEnter.getRejoin() != 1) {
                // visitor.setReign(Integer.parseInt(bdglVisitorEnter.getLeaveType()));
                // visitorMapper.updateVisitor(visitor);
            }
        }
        return bdglVisitorEnterMapper.updateBdglVisitorEnter(bdglVisitorEnter);
    }

    /**
     * 批量删除访客进入
     *
     * @param ids 需要删除的访客进入主键
     * @return 结果
     */
    @Override
    public int deleteBdglVisitorEnterByIds(Long[] ids) {
        return bdglVisitorEnterMapper.deleteBdglVisitorEnterByIds(ids);
    }

    /**
     * 删除访客进入信息
     *
     * @param id 访客进入主键
     * @return 结果
     */
    @Override
    public int deleteBdglVisitorEnterById(Long id) {
        return bdglVisitorEnterMapper.deleteBdglVisitorEnterById(id);
    }
    
    @Override
    public List<BdglVisitorEnter> selectBdglVisitorEnterListes() {
        return bdglVisitorEnterMapper.selectBdglVisitorEnterListes();
    }
    
    @Override
    public List<BdglVisitorEnter> selectBdglVisitorEnterLists() {
        return bdglVisitorEnterMapper.selectBdglVisitorEnterLists();
    }
}