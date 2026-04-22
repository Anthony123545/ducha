<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="dadui">
          <img src="../../../images/星星.png" alt />
          大队
        </div>
        <div class="tree">
          <el-tree
            class="userAgrees"
            style="height: calc(100vh - 170px); overflow: auto"
            :data="deptOptions"
            :props="defaultProps"
            @node-click="handleNodeClick"
            default-expand-all
            highlight-current
            :expand-on-click-node="false"
          ></el-tree>
        </div>
      </el-col>
      <el-col :span="1.5" class="mb8">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="68px"
        >
          <el-form-item prop="talkPeople">
            <el-input
              placeholder="请输入谈心人名称"
              v-model="queryParams.talkPeople"
              @input="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button size="btr" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <el-col :span="1.5" class="mb8">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['thought:talk:add']"
          >新增</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['thought:talk:edit']"
          >修改</el-button
        >
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['thought:talk:remove']"
          >删除</el-button
        >
      </el-col> -->
      <!-- </el-row> -->
      <el-col :span="20" :xs="24">
        <el-table
          v-loading="loading"
          :data="talkList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column
            label="序号"
            type="index"
            width="50"
            align="center"
          />
          <el-table-column label="谈心人" align="center" prop="talkPeople" />
          <el-table-column label="被谈心人" align="center" prop="talkObject" />
          <el-table-column
            label="被谈心单位"
            align="center"
            prop="unitName"
            show-overflow-tooltip
            :formatter="unit2Format"
          />
          <!-- <el-table-column label="单位id" align="center" prop="unitId" /> -->
          <!--         
        <el-table-column
          label="谈心对象id"
          align="center"
          prop="talkObjectId"
        /> -->
          <el-table-column
            label="谈心时间"
            align="center"
            prop="startTime"
            width="180"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="谈心分类"
            align="center"
            prop="talktype"
            width="180"
            :formatter="talkT"
          >
          </el-table-column>
          <!-- <el-table-column
            label="谈话内容"
            align="center"
            prop="talkContent"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <span>{{ onEditor(scope.row.talkContent) }}</span>
            </template>
          </el-table-column> -->
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
            width="200px"
          >
            <template slot-scope="scope">
              <el-button
                size="btk"
                type="text"
                @click="handlelook(scope.row)"
                v-hasPermi="['thought:talk:query']"
                >查看</el-button
              >
              <el-button
                size="btu"
                type="text"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['thought:talk:edit']"
                >修改</el-button
              >
              <el-button
                size="btd"
                type="text"
                @click="handleDelete(scope.row)"
                v-hasPermi="['thought:talk:remove']"
                >删除</el-button
              >
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
      </el-col>
    </el-row>

    <!-- 添加或修改谈心记录卡对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1120px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="110px"
        :inline="true"
      >
        <el-form-item label="谈心人员" prop="talkPeople">
          <!-- <el-input v-model="form.talkPeople" placeholder="请输入谈心人员" /> -->
          <el-select
            filterable
            v-model="form.talkPeople"
            ref="createName"
            placeholder="请选择谈心人员"
            clearable
          >
            <el-option
              v-for="item in executors"
              :key="item.id"
              :label="item.name"
              :value="item.name"
              @click.native="zuzhiPeople(item.id)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="单位" prop="unitId">
          <!-- <el-input v-model="form.unitName" placeholder="请输入单位名称" /> -->
          <treeselect
            v-model="form.unitId"
            :options="users"
            placeholder="请选择单位"
            class="threeselects"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="谈心时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="datetime"
            placeholder="选择谈心时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="被谈心人" prop="talkObject">
          <el-select
            filterable
            v-model="form.talkObject"
            placeholder="请选择被谈心人"
            ref="peoplenames"
          >
            <el-option
              v-for="item in tanxinexecutor"
              :key="item.id"
              :label="item.name"
              :value="item.name"
              @click.native="peopleIds(item)"
            />
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="谈心地点" prop="talkAddress" label-width="125px">
          <el-input
            placeholder="请输入谈心地点"
            v-model="form.talkAddress"
          ></el-input>
        </el-form-item> -->
        <el-form-item label="谈心分类" prop="talktype" label-width="115px">
          <el-select
            filterable
            v-model="form.talktype"
            ref="createName"
            placeholder="请选择谈心分类"
            clearable
          >
            <el-option
              v-for="item in tanList"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="谈心结束时间" prop="endTime" label-width="125px">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="datetime"
            placeholder="选择结束时间"
          >
          </el-date-picker>
        </el-form-item> -->
        <div class="jiben">谈话内容</div>
        <el-form-item>
          <quill-editor
            class="quill-editor"
            ref="myTextEditor"
            v-model="form.talkContent"
            style="
              height: 150px;
              width: 1040px;
              margin-left: 0px;
              color: white;
              margin-bottom: 60px;
            "
          ></quill-editor>
        </el-form-item>
        <div class="jiben">附件</div>
        <el-form-item>
          <FileUpload v-model="form.fileName" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看谈心记录卡 -->
    <el-dialog
      :title="title1"
      :visible.sync="open1"
      width="1120px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form1"
        :model="form1"
        :rules="rules"
        label-width="110px"
        :inline="true"
      >
        <el-form-item label="谈心人" prop="talkPeople">
          <!-- <el-input v-model="form.talkPeople" placeholder="请输入谈心人员" /> -->
          <el-select
            filterable
            disabled
            v-model="form1.talkPeople"
            ref="createName"
            placeholder="请选择谈心人"
          >
            <el-option
              v-for="item in executors"
              :key="item.id"
              :label="item.name"
              :value="item.name"
              @click.native="zuzhiPeople(item.id)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="单位" prop="unitId">
          <!-- <el-input v-model="form.unitName" placeholder="请输入单位名称" /> -->
          <treeselect
            disabled
            v-model="form1.unitId"
            :options="users"
            placeholder="请选择单位"
            class="threeselects"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="谈心时间" prop="startTime">
          <el-date-picker
            disabled
            clearable
            size="small"
            v-model="form1.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="被谈心人" prop="talkObject">
          <el-select
            disabled
            filterable
            v-model="form1.talkObject"
            placeholder="请选择被谈心人"
            ref="peoplenames"
          >
            <el-option
              v-for="item in tanxinexecutor"
              :key="item.id"
              :label="item.name"
              :value="item.name"
              @click.native="peopleIds(item)"
            />
          </el-select>
        </el-form-item>
      
        <!-- <el-form-item label="谈心地点" prop="talkAddress" label-width="125px">
          <el-input
            placeholder="请输入谈心地点"
            v-model="form1.talkAddress"
            disabled
          ></el-input>
        </el-form-item> -->
        <el-form-item label="谈心分类" prop="talktype" label-width="115px">
          <el-select
            filterable
            v-model="form1.talktype"
            ref="createName"
            placeholder="请选择谈心分类"
            clearable
            disabled
          >
            <el-option
              v-for="item in tanList"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="谈心结束时间" prop="endTime" label-width="125px">
          <el-date-picker
            disabled
            clearable
            size="small"
            v-model="form1.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间"
          >
          </el-date-picker>
        </el-form-item> -->
        <div class="jiben">谈话内容</div>
        <el-form-item>
          <quill-editor
            class="quill-editor"
            ref="myTextEditor"
            v-model="form1.talkContent"
            style="
              height: 150px;
              width: 1040px;
              margin-left: 0px;
              color: white;
              margin-bottom: 60px;
            "
          ></quill-editor>
        </el-form-item>
        <div class="jiben">附件</div>
        <!-- <el-form-item> -->
        <el-form-item class="fujian">
          <span class="filename">{{ form1.fileName }}</span>
          <a
            :href="form1.filePath"
            :download="form1.fileName"
            title="下载"
            style="color: white; margin-left: 10px"
            class="downloadfile"
            >下载附件</a
          >
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="open1 = false">关 闭</el-button>
        <!-- <el-button @click="open1=false" size="btn">取 消</el-button> -->
      </div>
    </el-dialog>
    <!-- </div> -->
  </div>
</template>

<script>
import {
  listTalk,
  getTalk,
  delTalk,
  addTalk,
  updateTalk,
  exportTalk,
} from "@/api/thought/talk";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {
  getDept2,
  getAll,
  getWeiShu,
} from "@/api/grassrootsregistration/bdglmeeting";
import { deptUser } from "@/api/doormanManage/bdglVisit";
import { getDeptList } from "@/api/workingArrangements/workTask";
// 字典
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "Talk",
  components: { Treeselect },
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
      // 谈心记录卡表格数据
      talkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        talkPeople: null,
        unitName: null,
        unitId: null,
        talkObject: null,
        talkObjectId: null,
        startTime: null,
        endTime: null,
        createName: null,
        createId: null,
        filePath: null,
        fileName: null,
        talkContent: null,
        talkTime: null,
        talkAddress: null,
        recorder: null,
        recorderId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        talkPeople: [
          { required: true, message: "谈心人员不能为空", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
        // endTime: [
        //   { required: true, message: "结束时间不能为空", trigger: "blur" },
        // ],
        unitName: [
          { required: true, message: "谈心对象单位不能为空", trigger: "blur" },
        ],
        talkObject: [
          { required: true, message: "谈心对象不能为空", trigger: "blur" },
        ],
      },
      // 左侧树形
      deptOptions: [],
      //左侧时间选择
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      // 树形配置
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 搜索时间的保存
      timeDate: "",
      tiem: "",
      executors: [],
      recodeexecutor: [],
      // 获取单位列表
      users: [],
      // 谈心对象列表
      tanxinexecutor: [],
      unitName: "",
      // 获取单位列表
      getdeptList: [],
      form1: {},
      open1: false,
      title1: "",
      // 谈心分类
      tanList: [],
    };
  },
  created() {
    this.getList();
    this.treeselect();
    this.getSuoYou();
    // 获取执行单位列表
    getDept2().then((res) => {
      this.users = res.data;
    });
    // 获取部门列表
    getDeptList().then((res) => {
      this.getdeptList = res.data;
    });
    getDicts("sys_classify").then((res) => {
      console.log(res);
      this.tanList = res.data;
    });
  },
  methods: {
    // 分类翻译
    talkT(row) {
      var name = "";
      this.tanList.map((res) => {
        if (res.dictValue == row.talktype) {
          name = res.dictLabel;
        }
      });
      return name;
    },
    unit2Format(row) {
      var deptName = "";
      this.getdeptList.map((item) => {
        // if(ite)
        if (item.deptId == row.unitId) {
          deptName = item.deptName;
        }
      });
      return deptName;
    },
    // 转义视图不加标签
    onEditor(str) {
      //可以在这里进行对应的操作 quill为富文本对象,html为富文本对象内内容由html标签组成,text为quill内容里面的所有文本内容
      if (str) {
        var str = str.replace(/<\/?[^>]*>/g, ""); //去除HTML tag
        str = str.replace(/[ | ]*\n/g, "\n"); //去除行尾空白
        return str;
      }
    },
    peopleIds(item) {
      this.form.talkObjectId = item.id;
    },
    // 选中单位
    selectPeo(val) {
      this.form.talkObject = "";
      this.form.unitId = val.id;
      // this.unitName = val.label;
      // this.form.phone = "";
      this.getZhuChi(this.form.unitId);
    },
    // 获取执行人
    getZhuChi(id) {
      deptUser(id).then((res) => {
        // 谈心对象
        this.tanxinexecutor = res.data;
        // this.executors = res.data;

        // this.form.unitName=this.executor
      });
    },
    // 获取所有人员
    getSuoYou() {
      getAll().then((response) => {
        console.log(response);
        this.executors = response.data;
        // 添加的人员
        // this.executor = response.rows;
        // 记录人
        this.recodeexecutor = response.rows;
      });
    },
    zuzhiPeople(id) {
      this.form.talkPeopleId = id;
    },
    jiluPeople(id) {
      this.form.recorderId = id;
    },
    // 获取部门数据
    getShuJu(data) {
      exportBdgShuju(data).then((res) => {
        this.DataTime = data;
        this.registrationData = res;
        this.cg = res.cg;
        this.sb = res.sb;
        // this.yishenpi = res.yiShenPi;
        // this.weishenpi = res.weiShenPi;
        this.tiems = res.time;
      });
    },
    // 搜索时间触发
    shiJian(data) {
      this.queryParams.diaryTime = data;
      this.timeDate = data;
      this.getList();
      this.getShuJu(data);
    },
    // 获取外面部门树形
    treeselect() {
      getWeiShu().then((res) => {
        this.deptOptions = res.data;
      });
    },
    //外面树形选择搜索
    handleNodeClick(data) {
      this.queryParams.unitId = data.id;
      this.getList();
    },
    /** 查询谈心记录卡列表 */
    getList() {
      this.loading = true;
      listTalk(this.queryParams).then((response) => {
        this.talkList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        talkPeople: null,
        unitName: null,
        unitId: null,
        talkObject: null,
        talkObjectId: null,
        startTime: null,
        endTime: null,
        createTime: null,
        updateTime: null,
        createName: null,
        createId: null,
        filePath: null,
        fileName: null,
        talkContent: null,
        talkTime: null,
        talkAddress: null,
        recorder: null,
        recorderId: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "谈心录入";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTalk(id).then((response) => {
        this.form = response.data;
        this.form.talktype = response.data.talktype + "";
        this.open = true;
        this.title = "修改谈心录入";
      });
    },
    // 查看按钮操作
    handlelook(row) {
      this.reset();
      const id = row.id || this.ids;
      getTalk(id).then((response) => {
        this.form1 = response.data;
        this.form1.talktype = response.data.talktype + "";
        this.form1.filePath =
          process.env.VUE_APP_BASE_API + this.form1.filePath;
        this.open1 = true;
        this.title1 = "查看谈心录入";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if (this.form.fileName) {
        this.form.filePath = this.form.fileName[0].url;
        this.form.fileName = this.form.fileName[0].name;
      }
      this.form.unitName = this.form.unitId;

      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTalk(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTalk(this.form).then((response) => {
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
        .confirm("是否确认删除？")
        .then(function () {
          return delTalk(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有谈心记录卡数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportTalk(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped>
/* 附件 */
::v-deep .fujian .el-form-item__content {
  display: flex;
}
/* 备注 */
.thistext {
  color: white;
  width: 900px;
  border: 1px solid #fff;
  border-radius: 4px;
  height: auto;
  min-height: 30px;
  text-indent: 1em;
  padding: 2px;
  line-height: 30px;
  margin-bottom: 20px;
  /* margin: auto; */
}
/* 附件名称样式 */
.filename {
  display: inline-block;
  color: white;
  width: 930px;
  height: 30px;
  border: 1px solid white;
  line-height: 30px;
  text-indent: 1em;
}
/* 下载按钮 */
.downloadfile {
  border: 1px solid white;
  display: inline-block;
  width: 100px;
  height: 33px;
  border-radius: 5px;
  line-height: 33px;
  text-align: center;
}
::v-deep .el-pagination__sizes .el-input__suffix {
  right: 6px;
}
/* 树形样式 */
::v-deep .vue-treeselect__control {
  background-color: #004d86;
  width: 216px;
  margin-right: 25px;
  color: white;
}
.threeselects {
  width: 216px;
  margin-right: 22px;
}
::v-deep .vue-treeselect__single-value {
  color: white;
}
::v-deep .el-dialog {
  background-color: #004d86 !important;
}
/* 输入框 */
::v-deep .el-dialog .el-input__inner {
  background-color: #004d86 !important;
  width: 216px;
  color: white;
  margin-right: 20px;
  border: 1px solid white !important;
}
::v-deep .el-input__inner {
  border: 1px solid white !important;
}
/* 表格样式 */
::v-deep .el-table .el-table__header-wrapper th {
  background-color: #004d86 !important;
  color: white;
}
/* 标题弹框 */
::v-deep .el-dialog__title {
  color: white;
  /* border-bottom: 1px solid white; */
}
/* 标题下划线 */
::v-deep .el-dialog__header {
  border-bottom: 1px solid white;
}
/* 日历样式 */
::v-deep .el-date-editor .el-input__inner {
  height: 36px !important;
  line-height: 36px;
}
::v-deep .datesend .el-form-item__label {
  width: 115px !important;
}
/* 会议文本域样式 */
::v-deep .el-form-item__content .el-textarea {
  width: 660px;
  /* height: 80px !important; */
}
::v-deep .el-form-item__content .el-textarea .el-textarea__inner {
  /* width: 1000px; */
  height: 80px !important;
}
::v-deep .el-dialog__body {
  padding-left: 40px !important;
  padding-right: 0px !important;
  padding-bottom: 0px !important;
}
/* 富文本 */
::v-deep .ql-snow .ql-picker:focus {
  outline: none !important;
}
::v-deep .ql-toolbar.ql-snow .ql-picker.ql-expanded .ql-picker-label:focus {
  outline: none !important;
}
::v-deep .ql-toolbar.ql-snow .ql-picker-label {
  border: none !important;
}
::v-deep .ql-formats:focus {
  outline: none !important;
}
::v-deep .ql-toolbar.ql-snow .ql-picker.ql-expanded .ql-picker-options {
  border-color: none !important;
  color: black !important;
}
::v-deep .ql-snow .ql-picker-options .ql-picker-item:focus {
  outline: none !important;
}
/* normal */
::v-deep .ql-active {
  outline: none !important;
}
::v-deep .ql-snow .ql-fill,
.ql-snow .ql-stroke.ql-fill {
  fill: #fff !important;
}
::v-deep .ql-snow .ql-stroke {
  stroke: #fff !important;
}
::v-deep .ql-snow .ql-picker {
  color: #fff !important;
}
::v-deep .ql-editor ql-blank {
  color: #fff !important;
}
::v-deep .ql-snow .ql-picker-label {
  position: absolute;
  bottom: 5px;
}
::v-deep .ql-snow .ql-picker:not(.ql-color-picker):not(.ql-icon-picker) svg {
  margin-top: -2px;
}
::v-deep .ql-toolbar.ql-snow {
  display: flex;
  justify-content: space-around;
}
/* 富文本删除功能 */

/* 富文本删除功能 */
::v-deep .ql-blockquote {
  display: none !important;
}
::v-deep .ql-strike {
  display: none !important;
}
::v-deep .ql-script {
  display: none !important;
}
::v-deep .ql-code-block {
  display: none !important;
}
::v-deep .ql-direction {
  display: none !important;
}
.ql-toolbar.ql-snow .ql-formats {
  margin-right: 7px !important;
}
::v-deep .el-input__inner {
  /* height: 36px !important; */
  color: white;
  background-color: #00365f !important;
}
/* 文字多余部分省略 */
::v-deep .el-table__cell .cell {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
::v-deep .el-input__suffix {
  right: 22px;
}
::v-deep .data .el-date-editor.el-input {
  width: 100%;
  border: none !important;
}
::v-deep .data .el-input__inner {
  width: 100% !important;
  border: none !important;
}
::v-deep .data .el-input--medium .el-input__icon {
  line-height: 36px;
  position: absolute;
  right: -223px;
  top: 2px;
}
::v-deep .data .el-input__inner {
  background-color: transparent !important;
  color: #fff;
  text-align: center;
  margin-top: 2px;
}
::v-deep .data .el-input__suffix {
  position: absolute;
  right: 281px;
}
.app-container .box-lfet .data {
  height: 40px;
  background: #003156;
  margin-bottom: 12px;
  color: #fff;
  display: flex;
  font-size: 13px;
  line-height: 25px;
  position: relative;
}

.box-lfet .data p {
  position: absolute;
  top: -5px;
  left: 15px;
}
.box-lfet .data .a1 {
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
/* 头部样式 */
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

.box-lfet .data p {
  position: absolute;
  top: -5px;
  left: 15px;
}
.box-lfet .data .a1 {
  width: 128px;
  height: 26px;
  background: rgba(23, 74, 112, 0.4);
  border-radius: 13px;
  position: absolute;
  top: 7px;
  left: 60px;
}
/* 划过效果 */
::v-deep .el-tree-node__content:hover {
  /* color: #fff; */
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
.app-container {
  display: flex;
}
/* 左侧树状盒子 */
.app-container .box-lfet {
  width: 15%;
  min-height: 740px;
  /* background: #003156; */
  /* padding: 10px; */
  margin-right: 10px;
  /* border: 1px solid white; */
}
/* 右侧内容盒子 */
.app-container .box-right {
  flex: 1;
}
.app-container .box-lfet .data {
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
  background: #003156;
  color: #fff;
  margin-right: 2px;
  padding-top: 7px;
}
::v-deep .el-dialog {
  background-color: #004d86 !important;
}
::v-deep .el-input__suffix {
  right: 22px;
}
::v-deep .el-tree-node__content {
  height: 32px;
}
::v-deep
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  height: 32px;
  color: #fff !important;
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
/* 计划附件 */
::v-deep .el-upload__tip {
  color: white;
  position: absolute;
  top: -5px;
  width: 500px;
  left: 90px;
  color: white;
}
::v-deep .el-link.el-link--default {
  color: white;
  text-indent: 0.5em;
}
/* 卡片样式 */
.app-container {
  height: calc(100vh - 108px) !important;
}
</style>
