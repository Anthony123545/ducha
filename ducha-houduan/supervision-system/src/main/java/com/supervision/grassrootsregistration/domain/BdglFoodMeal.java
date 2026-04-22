package com.supervision.grassrootsregistration.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 一日三餐对象 bdgl_food_meal
 * 
 * @author supervision
 * @date 2022-03-01
 */
public class BdglFoodMeal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer id;

    /** 主食 */
    @Excel(name = "主食")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String stapleFood;

    /** 菜谱 */
    @Excel(name = "菜谱")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String menu;

    /** 类型 */
    @Excel(name = "类型")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String type1;

    /** 就餐人员数量 */
    @Excel(name = "就餐人员数量")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer jiucanCount;

    /** 连队人员数量 */
    @Excel(name = "连队人员数量")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer lianduiCount;

    /** 外来人员数量 */
    @Excel(name = "外来人员数量")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer wailaiCount;

    /** 面粉消耗 */
    @Excel(name = "面粉消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double mianfen;

    /** 大米消耗 */
    @Excel(name = "大米消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double dami;

    /** 肉类消耗 */
    @Excel(name = "肉类消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double rolei;

    /** 鱼类消耗 */
    @Excel(name = "鱼类消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double yulei;

    /** 油类消耗 */
    @Excel(name = "油类消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double youlei;

    /** 黄豆消耗 */
    @Excel(name = "黄豆消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double huangdo;

    /** 蔬菜消耗 */
    @Excel(name = "蔬菜消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double suchai;

    /** 水果消耗 */
    @Excel(name = "水果消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double shuiguo;

    /** 糖消耗 */
    @Excel(name = "糖消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double tang;

    /** 燃料消耗 */
    @Excel(name = "燃料消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double ranliao;

    /** 调料消耗 */
    @Excel(name = "调料消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double tiaoliao;

    /** $column.columnComment */
    @Excel(name = "调料消耗")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer foodId;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setStapleFood(String stapleFood) 
    {
        this.stapleFood = stapleFood;
    }

    public String getStapleFood() 
    {
        return stapleFood;
    }
    public void setMenu(String menu) 
    {
        this.menu = menu;
    }

    public String getMenu() 
    {
        return menu;
    }
    public void setType1(String type1) 
    {
        this.type1 = type1;
    }

    public String getType1() 
    {
        return type1;
    }
    public void setJiucanCount(Integer jiucanCount) 
    {
        this.jiucanCount = jiucanCount;
    }

    public Integer getJiucanCount() 
    {
        return jiucanCount;
    }
    public void setLianduiCount(Integer lianduiCount) 
    {
        this.lianduiCount = lianduiCount;
    }

    public Integer getLianduiCount() 
    {
        return lianduiCount;
    }
    public void setWailaiCount(Integer wailaiCount) 
    {
        this.wailaiCount = wailaiCount;
    }

    public Integer getWailaiCount() 
    {
        return wailaiCount;
    }
    public void setMianfen(Double mianfen)
    {
        this.mianfen = mianfen;
    }

    public Double getMianfen()
    {
        return mianfen;
    }
    public void setDami(Double dami)
    {
        this.dami = dami;
    }

    public Double getDami()
    {
        return dami;
    }
    public void setRolei(Double rolei)
    {
        this.rolei = rolei;
    }

    public Double getRolei()
    {
        return rolei;
    }
    public void setYulei(Double yulei)
    {
        this.yulei = yulei;
    }

    public Double getYulei()
    {
        return yulei;
    }
    public void setYoulei(Double youlei)
    {
        this.youlei = youlei;
    }

    public Double getYoulei()
    {
        return youlei;
    }
    public void setHuangdo(Double huangdo)
    {
        this.huangdo = huangdo;
    }

    public Double getHuangdo()
    {
        return huangdo;
    }
    public void setSuchai(Double suchai)
    {
        this.suchai = suchai;
    }

    public Double getSuchai()
    {
        return suchai;
    }
    public void setShuiguo(Double shuiguo)
    {
        this.shuiguo = shuiguo;
    }

    public Double getShuiguo()
    {
        return shuiguo;
    }
    public void setTang(Double tang)
    {
        this.tang = tang;
    }

    public Double getTang()
    {
        return tang;
    }
    public void setRanliao(Double ranliao)
    {
        this.ranliao = ranliao;
    }

    public Double getRanliao()
    {
        return ranliao;
    }
    public void setTiaoliao(Double tiaoliao)
    {
        this.tiaoliao = tiaoliao;
    }

    public Double getTiaoliao()
    {
        return tiaoliao;
    }
    public void setFoodId(Integer foodId)
    {
        this.foodId = foodId;
    }

    public Integer getFoodId()
    {
        return foodId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("stapleFood", getStapleFood())
            .append("menu", getMenu())
            .append("type1", getType1())
            .append("jiucanCount", getJiucanCount())
            .append("lianduiCount", getLianduiCount())
            .append("wailaiCount", getWailaiCount())
            .append("mianfen", getMianfen())
            .append("dami", getDami())
            .append("rolei", getRolei())
            .append("yulei", getYulei())
            .append("youlei", getYoulei())
            .append("huangdo", getHuangdo())
            .append("suchai", getSuchai())
            .append("shuiguo", getShuiguo())
            .append("tang", getTang())
            .append("ranliao", getRanliao())
            .append("tiaoliao", getTiaoliao())
            .append("foodId", getFoodId())
            .toString();
    }
}
