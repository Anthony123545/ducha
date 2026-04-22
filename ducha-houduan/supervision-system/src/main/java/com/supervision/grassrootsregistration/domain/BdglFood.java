package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 伙食管理登记薄对象 bdgl_food
 * 
 * @author supervision
 * @date 2022-02-26
 */
public class BdglFood extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer id;

    /** 登记日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登记日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date recordDate;

    /** 单位 */
    @Excel(name = "单位")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer unitId;

    /** 值班员 */
    @Excel(name = "值班员")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer peopleId;

    /** 经委会主任 */
    @Excel(name = "经委会主任")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer chairmanId;

    /** 单位首长 */
    @Excel(name = "单位首长")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer peopleId1;

    /** 炊事人员及食堂卫生 */
    @Excel(name = "炊事人员及食堂卫生")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String contents;

    /** 帮厨人员 */
    @Excel(name = "帮厨人员")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String peopleIds;

    /** 就餐人员意见 */
    @Excel(name = "就餐人员意见")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String repastContent;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date updatetime;

    /** 值班姓名 */
    @Excel(name = "值班姓名")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String peopleName;

    /** 经委会主任姓名 */
    @Excel(name = "经委会主任姓名")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String chairmanName;

    /** 单位首长姓名 */
    @Excel(name = "单位首长姓名")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String people1Name;

    /** 单位名称 */
    @Excel(name = "单位名称")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String unitName;

    /**帮厨人员*/
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String peopleNames;

    public String getPeopleNames() {
        return peopleNames;
    }

    public void setPeopleNames(String peopleNames) {
        this.peopleNames = peopleNames;
    }

    private String[] commons;

    public String[] getCommons() {
        return commons;
    }

    public void setCommons(String[] commons) {
        this.commons = commons;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getChairmanName() {
        return chairmanName;
    }

    public void setChairmanName(String chairmanName) {
        this.chairmanName = chairmanName;
    }

    public String getPeople1Name() {
        return people1Name;
    }

    public void setPeople1Name(String people1Name) {
        this.people1Name = people1Name;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**一日三餐*/
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BdglFoodMeal> bdglFoodMeal;
    /**实物检查验收*/
    private List<BdglFoodKind> bdglFoodKinds;
    
    /**新的实物检查验收数据封装*/
    private Map<String,List<BdglFoodKind>> bdglFoodKindData;
    
    /**采购食材*/
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BdglFoodProcurement> bdglFoodProcurements;
    /**交退伙食费*/
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BdglFoodBoardWages> bdglFoodBoardWagess;
    /**在食堂购物*/
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BdglFoodShopping> bdglFoodShoppings;

    public List<BdglFoodMeal> getBdglFoodMeal() {
        return bdglFoodMeal;
    }

    public void setBdglFoodMeal(List<BdglFoodMeal> bdglFoodMeal) {
        this.bdglFoodMeal = bdglFoodMeal;
    }

    public List<BdglFoodKind> getBdglFoodKinds() {
        return bdglFoodKinds;
    }

    public void setBdglFoodKinds(List<BdglFoodKind> bdglFoodKinds) {
        this.bdglFoodKinds = bdglFoodKinds;
    }
    
    public Map<String, List<BdglFoodKind>> getBdglFoodKindData() {
        return bdglFoodKindData;
    }
    
    public void setBdglFoodKindData(Map<String, List<BdglFoodKind>> bdglFoodKindData) {
        this.bdglFoodKindData = bdglFoodKindData;
    }
    
    public List<BdglFoodProcurement> getBdglFoodProcurements() {
        return bdglFoodProcurements;
    }

    public void setBdglFoodProcurements(List<BdglFoodProcurement> bdglFoodProcurements) {
        this.bdglFoodProcurements = bdglFoodProcurements;
    }

    public List<BdglFoodBoardWages> getBdglFoodBoardWagess() {
        return bdglFoodBoardWagess;
    }

    public void setBdglFoodBoardWagess(List<BdglFoodBoardWages> bdglFoodBoardWagess) {
        this.bdglFoodBoardWagess = bdglFoodBoardWagess;
    }

    public List<BdglFoodShopping> getBdglFoodShoppings() {
        return bdglFoodShoppings;
    }

    public void setBdglFoodShoppings(List<BdglFoodShopping> bdglFoodShoppings) {
        this.bdglFoodShoppings = bdglFoodShoppings;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setRecordDate(Date recordDate) 
    {
        this.recordDate = recordDate;
    }

    public Date getRecordDate() 
    {
        return recordDate;
    }
    public void setUnitId(Integer unitId) 
    {
        this.unitId = unitId;
    }

    public Integer getUnitId() 
    {
        return unitId;
    }
    public void setPeopleId(Integer peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId() 
    {
        return peopleId;
    }
    public void setChairmanId(Integer chairmanId) 
    {
        this.chairmanId = chairmanId;
    }

    public Integer getChairmanId() 
    {
        return chairmanId;
    }
    public void setPeopleId1(Integer peopleId1) 
    {
        this.peopleId1 = peopleId1;
    }

    public Integer getPeopleId1() 
    {
        return peopleId1;
    }
    public void setContents(String contents) 
    {
        this.contents = contents;
    }

    public String getContents() 
    {
        return contents;
    }
    public void setPeopleIds(String peopleIds) 
    {
        this.peopleIds = peopleIds;
    }

    public String getPeopleIds() 
    {
        return peopleIds;
    }
    public void setRepastContent(String repastContent) 
    {
        this.repastContent = repastContent;
    }

    public String getRepastContent() 
    {
        return repastContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("recordDate", getRecordDate())
            .append("unitId", getUnitId())
            .append("peopleId", getPeopleId())
            .append("chairmanId", getChairmanId())
            .append("peopleId1", getPeopleId1())
            .append("contents", getContents())
            .append("peopleIds", getPeopleIds())
            .append("repastContent", getRepastContent())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
