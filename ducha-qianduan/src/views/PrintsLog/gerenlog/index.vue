<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" inline v-show="showSearch" label-width="auto">
      <!-- <el-form-item prop="printName">
        <el-input
          v-model="queryParams.printName"
          placeholder="请输入打印文件名称"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>-->
      <el-form-item prop="levelM">
        <el-select
          v-model="queryParams.levelM"
          clearable
          @change="handleQuery"
          placeholder="请选择密级级别"
        >
          <el-option
            v-for="dict in levelMs"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="printfileName">
        <el-input
          v-model="queryParams.printfileName"
          placeholder="请输入打印人名称"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item prop="printfileName">
        <el-input
          v-model="queryParams.printfileName"
          placeholder="请输入打印人名称"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>-->
      <!-- <el-form-item prop="printCode">
        <el-input
          v-model="queryParams.printCode"
          placeholder="请输入打印编码"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>-->
      <el-form-item prop="statusG">
        <el-select
          v-model="queryParams.statusG"
          clearable
          placeholder="请选择归还状态"
          @change="handleQuery"
        >
          <el-option
            v-for="dict in statusGlist"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-button type="primary" size="btn" @click="handleQuery"
          >搜索</el-button
      >-->
      <el-button size="btr" @click="resetQuery">重置</el-button>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <!-- <el-button
          type="primary"
          @click="handleUpdate"
          style="width: 76px; height: 36px"
          v-hasPermi="['PrintsLog:printlog:add']"
        >归还</el-button> -->
      </el-col>
      <el-col :span="1.5">
        <!-- <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['PrintsLog:printlog:export']"
        >导出</el-button> -->
      </el-col>
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>-->
    </el-row>

    <el-table
      v-loading="loading"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      :data="printlogList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="现文件路径" align="center" prop="id" />
      <el-table-column label="打印id" align="center" prop="printId" />-->
      <el-table-column label="打印文件名称" align="center" prop="printName">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.printName" placement="top">
            <div
              style="
                overflow: hidden;
                display: -webkit-box;
                text-overflow: ellipsis;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                white-space: normal;
              "
            >{{ scope.row.printName }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="unitId" :formatter="unitFn" />
      <el-table-column label="打印份数" align="center" prop="printNumber" />
      <!-- <el-table-column
        label="是否需要归还(0否1是)"
        align="center"
        prop="isReturn"
      />-->
      <!-- <el-table-column label="是否需要归还" align="center" prop="isReturn">
        <template slot-scope="scope">
          <div v-if="scope.row.isReturn == 0">无需归还</div>
          <div v-if="scope.row.isReturn == 1">归还</div>
        </template>
      </el-table-column>-->
      <!-- <el-table-column label="归还状态" align="center" prop="statusG" /> -->
      <el-table-column label="文件打印人" align="center" prop="printfileName" />
      <el-table-column label="密级" align="center" prop="levelM" :formatter="Alfred" />
      <el-table-column label="打印时间" align="center" prop="printTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.printTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="归还状态" align="center" prop="statusG">
        <template slot-scope="scope">
          <!-- <dict-tag :options="dict.type.sys_statusG" :value="scope.row.statusG" /> -->
          <div style="color: #00ff00" v-if="scope.row.statusG == '1'">已归还</div>
          <div style="color: #90da90" v-if="scope.row.statusG == '0'">未归还</div>
        </template>
      </el-table-column>
      <el-table-column label="归还份数" align="center" prop="returnNumber">
        <template slot-scope="scope">
          <span>{{ scope.row.returnNumber ? scope.row.returnNumber : 0 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="归还时间" align="center" prop="returnTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.returnTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="btk"
            type="text"
            @click="handlesee(scope.row)"
            v-hasPermi="['PrintsLog:printlog:query']"
          >查看</el-button>
          <!-- <el-button size="mini" type="text" v-if="scope.row.statusG==1||scope.row.statusG==2">已归还</el-button> -->
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['PrintsLog:printlog:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改集中文印记录对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1030px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="120px">
        <!-- <el-form-item label="打印id" prop="printId">
          <el-input v-model="form.printId" placeholder="请输入打印id" />
        </el-form-item>-->
        <div class="jiben">打印编码</div>
        <el-form-item label="打印编码" prop="printCode">
          <el-input v-model="form.printCode" @input="printCodeblur" placeholder="请输入打印编码" />
          <!-- <el-select v-model="form.printCode" filterable @change="printCodechange" clearable placeholder="请选择打印编码">
          <el-option
            v-for="dict in printlogList"
            :key="dict.printId"
            :label="dict.printCode"
            :value="dict"
          ></el-option>
          </el-select>-->
          <!-- <el-input v-model="form.printCode" placeholder="请输入打印编码" /> -->
        </el-form-item>
        <div class="jiben">基本信息</div>
        <!-- <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" />
        </el-form-item>-->
        <!-- <div
          style="
            font-size: 18px;
            color: red;
            margin-bottom: 36px;
            margin-left: 40%;
          "
        >
          未归还份数 <span>{{ form.num }}</span>
        </div>-->
        <el-form-item label="打印文件名称" prop="printName">
          <el-input v-model="form.printName" placeholder="请输入打印文件名称" />
        </el-form-item>
        <!-- <el-form-item label="打印份数" prop="printNumber">
          <el-input v-model="form.printNumber" type="Number" placeholder="请输入打印份数" />
        </el-form-item>
        <el-form-item label="是否需要归还" prop="isReturn">
          <el-radio-group v-model="form.isReturn" size="mini" style="width:200px;">
            <el-radio label="1">归还</el-radio>
            <el-radio label="0">无需归还</el-radio>
          </el-radio-group>
        </el-form-item>-->
        <!-- <el-form-item label="归还状态" prop="statusG">
          <el-select v-model="form.statusG" clearable placeholder="请选择归还状态">
            <el-option
              v-for="dict in statusGlist"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>-->
        <!-- <el-form-item label="归还状态(1 已归还  0未归还)" prop="statusG">
          <el-input v-model="form.statusG" placeholder="请输入归还状态(1 已归还  0未归还)" />
        </el-form-item>-->
        <!-- <el-form-item label="是否需要归还(0否1是)" prop="isReturn">
          <el-input v-model="form.isReturn" placeholder="请输入是否需要归还(0否1是)" />
        </el-form-item>-->
        <el-form-item label="归还份数" prop="returnNumber">
          <el-input v-model="form.returnNumber" @blur="returnNumberblur" placeholder="请输入归还份数" />
        </el-form-item>
        <el-form-item label="未归还份数" prop>
          <el-input v-model="form.num" placeholder disabled />
        </el-form-item>
        <el-form-item label="文件打印人" prop="printfileName">
          <el-input v-model="form.printfileName" placeholder="请输入文件打印人" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="flg != 1">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 导出弹框 -->
    <el-dialog
      title="导出记录"
      :visible.sync="open1"
      width="680px"
      append-to-body
      :close-on-click-modal="false"
      @close="remoer"
    >
      <el-form ref="form" :model="form3" inline :rules="rules" label-width="120px">
        <el-form-item label prop="unitIds" style="margin-right: 500px">
          <el-select multiple filterable v-model="form3.unitIds" id="execute" placeholder="请选择单位">
            <el-option
              v-for="(item, i) in danWeisList"
              :label="item.deptName"
              :value="item.deptId"
              :key="i"
              id="execute"
            />
          </el-select>
          <!-- <treeselect
            v-model="queryParams.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeos"
          />-->
        </el-form-item>
        <el-form-item prop="levelM">
          <el-select v-model="form3.levelM" clearable placeholder="请选择级别">
            <el-option
              v-for="dict in levelMs"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="form3.startt"
            type="date"
            placeholder="选择开始时间"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="form3.endt"
            type="date"
            placeholder="选择结束时间"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForms">确 定</el-button>
        <el-button @click="cancels" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listPrintlog,
  getPrintlog,
  delPrintlog,
  addPrintlog,
  updatePrintlog,
  exportPrintlog,
  personalList,
  listPeople
} from "@/api/PrintsLog/printlog";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

import { getDept } from "@/api/grassrootsregistration/bdglmeeting";
import { listDept } from "@/api/system/dept";
import { getUserProfile } from "@/api/system/user";

export default {
  name: "Printlog",
  dicts: ["sys_statusG"],
  components: {
    Treeselect
  },
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
      // 集中文印记录表格数据
      printlogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        printId: null,
        printName: null,
        printNumber: null,
        isReturn: null,
        printfileName: null,
        printTime: null,
        statusG: null,
        returnNumber: null,
        printCode: null,
        primaryPath: null,
        presentPath: null,
        startt: null,
        endt: null,
        levelM: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        returnNumber: [
          { required: true, message: "归还份数不能为空", trigger: "blur" }
        ]
      },
      statusGlist: [],
      rylist: [],
      returnNumber: null,
      flg: null,
      // 树形结构列表
      deptOptions: [],
      //级别
      levelMs: [],
      open1: false,
      //获取单位所有不是树形结构
      danWeisList: [],
      // 用户信息
      user: {},
      deptId: null,
      yongHu: "",
      form3: {}
    };
  },
  created() {
    this.getList();
    //检查类型
    this.getDicts("sys_statusG").then(response => {
      this.statusGlist = response.data;
    });
    this.getBuMeng();
    //级别
    this.getDicts("level").then(response => {
      this.levelMs = response.data;
    });
    this.getUser();
    this.danWeis();
  },
  methods: {
    remoer() {
      this.form3.levelM = null;
      this.form3.unitIds = null;
      this.form3.startt = null;
      this.form3.endt = null;
    },
    unitFn(row) {
      var name = "";
      this.danWeisList.map(item => {
        if (item.deptId == row.unitId) {
          name = item.deptName;
        }
      });
      return name;
    },
    // 获取登录信息
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        this.deptId = response.data.deptId;
        this.yongHu = response.data.nickName;
        console.log(response);
      });
    },
    //获取所有单位
    danWeis() {
      listDept().then(res => {
        this.danWeisList = res.data;
      });
    },
    Alfred(row) {
      var name = "";
      this.levelMs.map(item => {
        if (item.dictValue == row.levelM) {
          name = item.dictLabel;
        }
      });
      return name;
    },
    // 获取部门列表
    getBuMeng() {
      getDept().then(res => {
        this.deptOptions = res.data;
      });
    },
    selectPeos(data) {
      this.queryParams.unitId = data.id;
    },
    //获取人员
    getrylist() {
      listPeople().then(res => {
        if (res.code == 200) {
          this.rylist = res.rows;
        }
      });
    },
    /** 查询集中文印记录列表 */
    getList() {
      this.loading = true;
      personalList(this.queryParams).then(response => {
        this.printlogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.flg = null;
    },
    cancels() {
      this.form3.levelM = null;
      this.form3.unitIds = [];
      this.form3.startt = null;
      this.form3.endt = null;
      this.open1 = false;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        printId: null,
        printName: null,
        printNumber: null,
        isReturn: null,
        printfileName: null,
        printTime: null,
        statusG: null,
        returnNumber: null,
        printCode: null,
        primaryPath: null,
        presentPath: null,
        unitName: null
      };
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加集中文印记录";
      this.getrylist();
    },
    /** 查看按钮操作 */
    handlesee(row) {
      getPrintlog(row.id).then(res => {
        if (res.code == 200) {
          this.form = res.data;
          if (this.form.printNumber) {
            this.form.num =
              this.form.printNumber - this.form.returnNumber == 0
                ? 0
                : this.form.printNumber - this.form.returnNumber;
          }
          if (res.data.returnNumber) {
            this.form.returnNumber = res.data.returnNumber;
          } else {
            this.form.returnNumber = 0;
          }

          this.title = "归还记录";
          this.open = true;
          this.flg = 1;
        }
      });
    },
    /** 修改按钮操作 */
    handleUpdate() {
      this.reset();
      this.title = "归还记录";
      this.open = true;
      // const id = row.id || this.ids;
      // getPrintlog(id).then(response => {
      //   if (!response.data.returnNumber) {
      //     this.returnNumber = 0;
      //   } else {
      //     this.returnNumber = response.data.returnNumber;
      //   }

      //   this.form = response.data;
      //   this.form.isReturn = this.form.isReturn + "";
      //   this.open = true;
      //   // if(this.form.returnNumber==this.form.printNumber){
      //   //      this.form.statusG = 1;
      //   // }else{
      //   //   this.form.statusG=this.form.statusG;
      //   // }

      //   this.form.returnNumber = response.data.printNumber;
      //   this.title = "修改集中文印记录";
      // });
    },
    returnNumberblur() {
      if (Number(this.form.returnNumber) > Number(this.form.num)) {
        this.$modal.msgSuccess(`应归还${Number(this.form.num)}份`);
        this.form.returnNumber = null;
      }
      if (
        Number(this.form.returnNumber) + Number(this.returnNumber) >
        Number(this.form.printNumber)
      ) {
        this.$modal.msgSuccess(
          `应归还${Number(this.form.printNumber) - Number(this.returnNumber)}份`
        );
        this.form.returnNumber = null;
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.returnNumber =
            Number(this.form.returnNumber) + Number(this.returnNumber);
          if (this.form.returnNumber == this.form.printNumber) {
            this.form.statusG = 1;
          }
          if (this.form.id != null) {
            updatePrintlog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPrintlog(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否删除该记录？")
        .then(function() {
          return delPrintlog(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.open1 = true;
      this.danWeis();
    },
    // 获取默认时间
    daty() {
      var tiem = new Date();
      let y = tiem.getFullYear();
      let m = tiem.getMonth() + 1;
      m = m < 10 ? "0" + m : m;
      let d = tiem.getDate();
      d = d < 10 ? "0" + d : d;
      this.queryParams.endt = y + "-" + m + "-" + d;
      return;
    },
    // 导出操作
    submitForms() {
      if (this.form3.startt || this.form3.endt) {
        const queryParamss = this.form3;
        this.$modal
          .confirm("是否确认导出集中文印记录数据项？")
          .then(() => {
            this.exportLoading = true;
            if (this.form3.unitIds == null) {
              this.form3.unitIds = [];
            }
            if (this.form3.unitIds.length > 0) {
              this.form3.unitIds = this.form3.unitIds.join(",");
            }
            return exportPrintlog(queryParamss);
          })
          .then(response => {
            this.$download.name(response.msg);
            this.exportLoading = false;
            this.cancels();
          })
          .catch(() => {});
      } else {
        this.$message.error("开始时间或结束时间不能为空");
        this.form3.unitIds = [];
        this.form3.levelM = null;
        this.form3.startt = null;
        this.form3.endt = null;
      }
    },
    //打印编号
    printCodeblur() {
      if (this.form.printCode) {
        personalList({ printCode: this.form.printCode }).then(response => {
          if (response.rows[0]) {
            this.form = response.rows[0];
            if (this.form.returnNumber) {
              this.form.returnNumber = this.form.returnNumber;
              this.form.num =
                this.form.printNumber - this.form.returnNumber == 0
                  ? 0
                  : this.form.printNumber - this.form.returnNumber;
            } else {
              this.form.returnNumber = 0;
              this.form.num =
                this.form.printNumber - this.form.returnNumber == 0
                  ? 0
                  : this.form.printNumber - this.form.returnNumber;
            }
          } else {
          }
        });
      }
    }
  }
};
</script>
<style scoped>
::v-deep .el-dialog {
  background-color: #004d86;
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
.el-form-item ::v-deep .el-input__inner {
  width: 200px;
  height: 36px;
  background: transparent;
  color: #fff;
  border: 1px solid #fff !important;
}
::v-deep .el-date-editor.el-input {
  width: 200px;
  height: 36px;
}

.pictureUploading-img img {
  width: 60px;
  height: 210px;
  margin-right: 10px;
}

::v-deep .el-upload--picture-card {
  height: 210px;
  width: 170px;
  line-height: 210px;
  background-image: url("../../../assets/images/头像.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
::v-deep .el-upload--picture-card .el-icon-plus {
  display: none;
}
.el-upload--picture-card /deep/ .el-upload--picture-card i {
  font-size: 48px;
}
::v-deep .el-upload__tip {
  display: none;
}
::v-deep .el-textarea__inner {
  width: 200px;
  height: 64px;
}
.jiben span {
  width: 25px;
  height: 25px;
  display: inline-block;
  background-color: #1c86e1;
  line-height: 25px;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  /* margin-left: 851px; */
  position: absolute;
  right: 20px;
}
.el-select-dropdown__item:hover {
  color: #111;
}
.el-select-dropdown__item {
  color: #111;
}
.el-tree {
  background-color: #00365f;
  color: #fff;
}
::v-deep .el-tree-node__content:hover {
  color: #1c86e1;
}
::v-deep .el-upload-list--picture-card .el-upload-list__item {
  height: 210px;
  width: 170px;
}
.dadui {
  height: 40px;
  background: #003156;
  /* border-bottom: 1px solid #718A9D ; */
  background-image: url(../../../images/矩形底部边框.png);
  background-repeat: no-repeat;
  background-position: 0px 39px;
  font-size: 16px;
  color: #1d96ff;
}
.dadui img {
  margin: 10px 10px 0px 10px;
}

.data {
  height: 40px;
  background: #003156;
  margin-bottom: 12px;
  color: #fff;
  display: flex;
  font-size: 13px;
  line-height: 25px;
  position: relative;
}
.el-tree {
  padding: 10px;
  background: #003156;
  color: #fff;
}

.data p {
  position: absolute;
  top: -5px;
  left: 15px;
}
.data .a1 {
  width: 128px;
  height: 26px;
  background: rgba(23, 74, 112, 0.4);
  border-radius: 13px;
  position: absolute;
  top: 7px;
  left: 60px;
}
::v-deep .data .el-date-editor.el-input {
  width: 100%;
  border: none;
}
::v-deep .data .el-input__inner {
  width: 100% !important;
  border: none;
}
::v-deep .data .el-input--medium .el-input__icon {
  line-height: 36px;
  position: absolute;
  right: -223px;
  top: 2px;
}
::v-deep .data .el-input__inner {
  background-color: transparent;
  color: #fff;
  text-align: center;
  margin-top: 2px;
}
::v-deep .data .el-input__suffix {
  position: absolute;
  right: 281px;
}
::v-deep
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  color: #00365f !important;
  /* background-color: #003156 !important; */
}
/* 调整输入框提示文字颜色 */
::v-deep .vue-treeselect__placeholder {
  color: #bdbdbd4f !important;
}
::v-deep input::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
::v-deep input:-moz-placeholder {
  color: #bdbdbd4f !important;
}
/* 文本域提示文字颜色 */
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: transparent !important;
  color: #fff;
}
/* 单位下拉菜单选中字体颜色 */
::v-deep .vue-treeselect__single-value {
  color: #fff !important;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
/* 执行 */
::v-deep .el-input--suffix #execute {
  width: 620px;
  /* width: auto; */
}
</style>
