<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item prop="attendedTime">
        <el-date-picker clearable size="small" v-model="queryParams.attendedTime" type="date" value-format="yyyy-MM-dd"
          placeholder="选择值班时间" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="号码号牌ID" prop="numberPlateId">
        <el-input
          v-model="queryParams.numberPlateId"
          placeholder="请输入号码号牌ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item prop="numberPlateName">
        <el-input v-model="queryParams.numberPlateName" placeholder="请输入号码号牌" clearable size="small"
          @input="handleQuery" />
      </el-form-item>
      <el-form-item prop="driverId">
        <el-select v-model="queryParams.driverId" placeholder="请选择司机名字" clearable size="small" @change="handleQuery">
          <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="联系方式" prop="telephone">
        <el-input
          v-model="queryParams.telephone"
          placeholder="请输入联系方式"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item>
        <!-- <el-button type="primary" size="btn" @click="handleQuery"
          >搜索</el-button
        > -->
        <el-button size="btr" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['combatduty:militarydutyinfo:add']">新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['combatduty:militarydutyinfo:edit']">修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['combatduty:militarydutyinfo:remove']">删除</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['combatduty:militarydutyinfo:export']"
        >导出</el-button>
      </el-col>-->
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>

    <el-table v-loading="loading" :data="militarydutyinfoList" @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键ID" align="center" prop="id" /> -->
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="值班时间" align="center" prop="attendedTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.attendedTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="号码号牌ID" align="center" prop="numberPlateId" /> -->
      <el-table-column label="号码号牌" align="center" prop="numberPlateName" show-overflow-tooltip />
      <!-- <el-table-column label="车属单位" align="center" prop="carUnitId" :formatter="carUnitIdFormat" /> -->
      <el-table-column label="车属单位名称" align="center" prop="carUnitName" show-overflow-tooltip />
      <el-table-column label="车辆类别" align="center" prop="carType" />
      <el-table-column label="车辆状态" align="center" prop="carStates">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.military" :value="scope.row.carStates" />
        </template>
      </el-table-column>
      <el-table-column label="所在位置" align="center" prop="adress" />
      <!-- <el-table-column label="司机id" align="center" prop="driverId" /> -->
      <el-table-column label="司机名字" align="center" prop="driverName" show-overflow-tooltip />
      <el-table-column label="联系方式" align="center" prop="telephone" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button size="btu" type="text" @click="handleUpdate(scope.row)"
            v-hasPermi="['combatduty:militarydutyinfo:edit']">修改</el-button>
          <el-button size="btd" type="text" @click="handleDelete(scope.row)"
            v-hasPermi="['combatduty:militarydutyinfo:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改军车值班对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="843px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <div class="jiben">基本信息</div>
        <el-form-item label="值班时间" prop="attendedTime">
          <el-date-picker clearable size="small" v-model="form.attendedTime" type="date" value-format="yyyy-MM-dd"
            placeholder="" v-if="qdflg == 2"></el-date-picker>
          <el-date-picker v-model="form.attendedTime" type="daterange" range-separator="至" start-placeholder
            end-placeholder value-format="yyyy-MM-dd" v-if="qdflg == 1"></el-date-picker>
        </el-form-item>
        <el-form-item label="车属单位" prop="carUnitId">
          <el-select v-model="form.carUnitId" placeholder="" filterable clearable>
            <el-option v-for="dict in deptOptions" :key="dict.deptId" :label="dict.deptName" :value="dict.deptId"
              @click.native="carUnitNamechange(dict)">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="号码号牌" prop="numberPlateName">
          <el-select v-model="busDuoList" @change="numberPlateNamechange" placeholder="" class="ren_yuans" multiple
            filterable>
            <el-option v-for="dict in buslist" :key="dict.id" :label="dict.vehiclenumber" :value="dict.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="司机名字" prop="driverId">
          <el-select v-model="busDuoShi" filterable placeholder="" class="ren_yuans" multiple
            @remove-tag="busShi(busDuoShi)">
            <el-option v-for="item,i in rylist" :key="i" :label="item.name" :value="item.id"
              @click.native="driverNamefun(item)">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="telephone">
          <!-- <el-input  placeholder="" v-model="tag"> -->
          <div class="ren_yuans tags">
            <el-tag v-for="tag,i1 in tags" :key="i1" closable :type="tag.type" @close="tagsFn(i1)">
              {{tag.name}}
            </el-tag>
          </div>
        </el-form-item>
        <el-form-item label="车辆类别" prop="carType">
          <el-input v-model="form.carType" placeholder="" />
        </el-form-item>
        <el-form-item label="车辆状态" prop="carStates">
          <el-select v-model="form.carStates" placeholder="" :disabled="chuFn">
            <el-option v-for="dict in dict.type.military" :key="dict.value" :label="dict.label" :value="dict.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在位置" prop="adress">
          <el-input v-model="form.adress" placeholder="" />
        </el-form-item>
        <!-- <el-form-item label="司机id" prop="driverId">
          <el-input v-model="form.driverId" placeholder="请输入司机id" />
        </el-form-item>-->
        <!-- <el-form-item label="司机名字" prop="driverName">
          <el-select v-model="form.driverName" @change="driverNamefun" filterable clearable placeholder="">
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item"></el-option>
          </el-select>
        </el-form-item> -->

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1" v-if="baoCun">保存并继续编辑</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listMilitarydutyinfo,
  getMilitarydutyinfo,
  delMilitarydutyinfo,
  addMilitarydutyinfo,
  updateMilitarydutyinfo,
  exportMilitarydutyinfo,
  rllist,
  getDept,
  getthebus,
  updatestatus,
  listPeople
} from "@/api/combatduty/militarydutyinfo";
import { listThebus } from "@/api/militaryvehicleManagement/thebus";

export default {
  name: "Militarydutyinfo",
  dicts: ["military"],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 军车值班表格数据
      militarydutyinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        attendedTime: null,
        numberPlateId: null,
        numberPlateName: null,
        carUnitId: null,
        carUnitName: null,
        carType: null,
        carStates: null,
        adress: null,
        driverId: null,
        driverName: null,
        telephone: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        attendedTime: [
          {
            required: true,
            message: "值班时间不能为空",
            trigger: "blur",
          },
        ],
        carUnitId: [
          {
            required: true,
            message: "单位不能为空",
            trigger: "blur",
          },
        ]
      },
      //人员数据
      rylist: [],
      //单位数据
      deptOptions: [],
      qdflg: null,
      //车牌号
      buslist: [],
      // 保存显示隐藏
      baoCun: true,
      // 车牌号多选、
      busDuoList: [],
      // 车属单位多选
      busDuoDan: [],
      // 司机名字多选
      busDuoShi: [],
      // 所有车辆
      suoYouBus: [],
      buses: [],
      chuFn: false,
      // 手机号多选
      tags: [],
      arr: [],
      arr1: []
    };
  },
  created() {
    this.getList();
    this.gettreeselect();
    // this.getrylist();
    this.getthebusfun();
    this.getQuanBus()
  },
  methods: {
    // 删除当前手机号
    tagsFn(i) {
      this.tags.splice(i, 1)
      this.arr.splice(i, 1)
    },
    // 点击司机删除某一个时候手机号也删除
    busShi(val) {
      this.arr1.forEach((item, i) => {
        if (val[i] == (item.userId || item)) {
        } else {
          if (val.length == this.tags.length - 1) {
            this.tags.splice(i, 1)
            this.arr.splice(i, 1)
          }
        }
      })
    },
    //删除所有单位的某一个触发
    selent() {
      // this.renyuanEs();
      this.busDuoShi = [];
      this.tags = [];
      this.arr = [];
      this.busDuoList = [];
    },
    // //点击多选单位
    // danFn() {
    //   this.renyuanEs();
    // },
    // //获取多选单位下的人员
    // renyuanEs() {
    //   if (this.busDuoDan.length > 0) {
    //     var ids = this.busDuoDan.join(",");
    //     getUnitUsers(ids).then((res) => {
    //       this.rylist = res.data;
    //     });
    //   } else {
    //     this.rylist = [];
    //   }
    // },
    // 修改拿回来的数据放到车牌上
    getBianList() {
      this.buses = [];
      this.buslist = [];
      console.log("suoYouBus", this.suoYouBus);
      this.suoYouBus.forEach((item, i) => {
        if (this.form.numberPlateIdList.indexOf(item.id) != -1) {
          this.buses.push(item);
        } else {
          this.buses.push(item);
        }
      });
      this.buslist = this.buses;
      console.log("buslist", this.buslist);
    },
    // 获取所有车辆
    getQuanBus() {
      listThebus().then(res => {
        this.suoYouBus = res.rows
      })
    },
    // 更改军车状态
    getStatuse() {
      updatestatus().then(res => {
      })
    },
    // 获取每个单位下的司机
    getRen(id) {
      listPeople({ deptId: id }).then((res) => {
        this.rylist = res.rows;
      });
    },
    //岗位
    // carUnitIdFormat(row, column) {
    //   var deptName = "";
    //   this.deptOptions.map(item => {
    //     if (item.deptId == row.carUnitId) {
    //       deptName = item.deptName;
    //     }
    //   });
    //    return deptName;
    // },
    //获取人员
    getrylist() {
      listPeople().then((res) => {
        this.rylist = res.rows;
      });
    },
    // 单位获取
    gettreeselect() {
      getDept().then((res) => {
        if (res.code == 200) {
          this.deptOptions = res.data;
        }
      });
    },
    // 获取在位车辆
    getthebusfun() {
      getthebus(1).then((res) => {
        if (res.code == 200) {
          this.buslist = res.rows;
        }
      });
    },
    /** 查询军车值班列表 */
    getList() {
      this.loading = true;
      listMilitarydutyinfo(this.queryParams).then((response) => {
        this.militarydutyinfoList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.getStatuse()
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        attendedTime: null,
        numberPlateId: null,
        numberPlateName: null,
        carUnitId: null,
        carUnitName: null,
        carType: null,
        carStates: null,
        adress: null,
        driverId: null,
        driverName: null,
        telephone: null,
        modelInfo: null,
      };
      this.rylist = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getthebusfun();
      this.baoCun = true;
      this.open = true;
      this.title = "添加军车值班";
      this.qdflg = 1;
      this.chuFn = true;
      this.form.carStates = '2';
      this.busDuoList = [];
      this.busDuoShi = [];
      this.tags = [];
      this.arr = [];
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.gettreeselect();
      this.getthebusfun();
      this.tags = [];
      this.arr = [];
      this.chuFn = true;
      const id = row.id || this.ids;
      getMilitarydutyinfo(id).then((response) => {
        this.baoCun = false;
        this.form = response.data;
        this.form.carUnitId=response.data.carUnitId*1;
        this.busDuoList = response.data.numberPlateIdList;
        this.busDuoShi = response.data.sjId.map(Number);
        let ponst = response.data.phone;
        ponst.forEach(item => {
          this.tags.push({ name: item, type: 'info' })
          this.arr.push(item);
          this.arr1.push(item);
        })
        if (response.data.numberPlateIdList) {
          this.getBianList();
        }
        this.open = true;
        this.title = "修改军车值班";
        this.qdflg = 2;
        this.getRen(this.form.carUnitId);
      });
    },
    // 保存并继续编辑按钮
    submitForm1() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.busDuoList.length > 0 && this.busDuoShi.length > 0) {
            // 手机号
            this.form.phone = this.arr;
            this.form.sjId = this.busDuoShi;
            this.form.numberPlateIdList = this.busDuoList
            this.form.timequjian = this.form.attendedTime;
            this.form.attendedTime = this.form.attendedTime.join(",");
            addMilitarydutyinfo(this.form).then((response) => {
              this.getList();
            });
            this.reset();
            this.form.carStates = '1';
            this.getthebusfun();
            this.busDuoList = [];
            this.busDuoShi = [];
            this.tags = [];
            this.arr = [];
          } else {
            this.$message.error('车属单位或号码号牌或司机名称不能为空！')
          }
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            if (this.busDuoList.length > 0 && this.busDuoShi.length > 0) {
              this.form.numberPlateIdList = this.busDuoList
              this.form.phone = this.arr;
              this.form.sjId = this.busDuoShi;
              updateMilitarydutyinfo(this.form).then((response) => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              this.$message.error('车属单位或号码号牌或司机名称不能为空！')
            }

          } else {
            if (this.busDuoList.length > 0 && this.busDuoShi.length > 0) {
              // 手机号
              this.form.phone = this.arr;
              this.form.sjId = this.busDuoShi;
              this.form.timequjian = this.form.attendedTime;
              this.form.attendedTime = this.form.attendedTime.join(",");
              this.form.numberPlateIdList = this.busDuoList
              addMilitarydutyinfo(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            } else {
              this.$message.error('车属单位或号码号牌或司机名称不能为空！')
            }
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除该数据项？")
        .then(function () {
          return delMilitarydutyinfo(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有军车值班数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportMilitarydutyinfo(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => { });
    },
    //司机联系方式
    driverNamefun(val) {
      if (this.arr.indexOf(val.phone) != -1) {

      } else {
        this.arr.push(val.phone)
        this.tags.push({
          name: val.phone,
          type: 'info'
        })
        this.arr1.push(val)
      }
      // this.form.telephone = val.phonenumber
      // this.form.driverName = val.name;
      // this.form.telephone = val.phone;
      // this.form.driverId = val.id;
    },
    carUnitNamechange(val) {
      this.form.carUnitName = val.deptName;
      this.getRen(this.form.carUnitId);
      this.busDuoShi = [];
      this.tags = [];
      this.arr = [];
      this.busDuoList = [];
    },
    numberPlateNamechange(val) {
      this.form.numberPlateId = val.id;
      this.form.numberPlateName = val.vehiclenumber;
      this.form.modelInfo = val.vehiclemodel;
    },
  },
};
</script>
<style scoped>
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}

::v-deep .el-textarea__inner {
  width: 784px;
  height: 124px;
  margin-left: 29px;
}

::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
}

::v-deep .el-dialog__title {
  color: #fff;
  font: 18px;
}

::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}

::v-deep .el-form-item__label {
  font: 16px;
  color: #fff;
  width: 100px !important;
}

/* 对话框表单间距
::v-deep .el-dialog__body .el-form-item__label {
  width: 175px !important;
} */
::v-deep .el-dialog__body .el-form--inline .el-form-item {
  display: inline-block;
  margin-right: 10px;
  vertical-align: top;
  padding-left: 50px;
}

::v-deep .el-input__inner {
  /* width: 200px !important;
  height: 36px; */
  background: transparent !important;
  color: #fff;
}

::v-deep .el-dialog__footer {
  margin-right: 95px !important;
}

/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}

/* 基本信息背景 */
.jiben {
  width: 952px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}

/* 调整表单间距
::v-deep .el-form-item__content {
  width: 200px;
} */
/* 时间选择 */
::v-deep .el-input--small .el-input__inner {
  width: 200px;
  height: 36px;
  line-height: 36px;
}

.el-date-editor.el-input {
  width: 200px;
  height: 36px;
  line-height: 36px;
}

::v-deep .el-date-editor.el-input .el-input__inner {
  height: 36px;
  line-height: 36px;
}

/* 计数器按钮 */
::v-deep .el-input-number__increase {
  background-color: transparent !important;
  border: none;
  color: #cccccc;
  border-bottom: none !important;
}

::v-deep .el-input-number__decrease {
  background-color: transparent !important;
  border: none !important;
  color: #cccccc;
}

.el-radio-group {
  width: 216px;
}

::v-deep .el-range-input {
  background: #004d86;
  color: #fff;
  font-size: 12px;
}

::v-deep .el-range-separator {
  color: #fff;
  font-size: 12px;
}

.el-form-item ::v-deep .el-input__inner {
  width: 200px;
  height: 36px;
  background: #004d86;
  color: #fff important;
  border: 1px solid #fff !important;
}

::v-deep .el-date-editor.el-input {
  width: 200px;
  height: 36px;
}

.el-select-dropdown__item {
  /* background-color: white; */
  color: #000 !important;
}

::v-deep .el-input-number__increase {
  background-color: transparent !important;
  border: none;
  color: #cccccc;
  border-bottom: none !important;
}

::v-deep .el-input-number__decrease {
  background-color: transparent !important;
  border: none !important;
  color: #cccccc;
}


::v-deep .ren_yuans .el-input__inner {
  width: 559px;
}

.ren_yuans {
  width: 559px;
}

::v-deep .el-select__input {
  color: #fff;
}

.tags {
  border: 1px solid #fff;
  border-radius: 4px;
  line-height: 33px;
  padding: 0 5px;
  min-height: 33px;
}

::v-deep .tags .el-tag.el-tag--info {
  margin-right: 10px;
}
</style>
