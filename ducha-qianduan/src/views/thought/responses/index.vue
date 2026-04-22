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
      <el-col :span="2" class="mb8">
        <div class="shuJu" @click="dengJI">
          <!-- <span class="box44 public"> 当前数据为 : {{ time }}</span> -->
          <span class="box5 public"> 已响应部门 : {{ yishenpi }}</span>
          <span class="box6"> 未响应部门 : {{ weishenpi }}</span>
        </div>
        <!-- <el-button
          v-show="true"
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['thought:thoughtPlan:add']"
          >新增</el-button
        > -->
      </el-col>
      <el-col :span="1.5">
        <!-- <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['thought:thoughtPlan:edit']"
          >修改</el-button
        > -->
      </el-col>
      <el-col :span="1.5">
        <!-- <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['thought:thoughtPlan:remove']"
          >删除</el-button
        > -->
      </el-col>
      <el-col :span="1.5">
        <!-- <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['thought:thoughtPlan:export']"
          >导出</el-button
        > -->
      </el-col>
      <!-- </el-row> -->
      <el-col :span="20" :xs="24">
        <el-table
          v-loading="loading"
          :header-cell-style="{ background: '#003C69', color: 'white' }"
          :data="thoughtPlanList"
          max-width="10"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序号" align="center" type="index" />
          <!-- <el-table-column label="主键id" align="center" prop="id" /> -->
          <el-table-column label="计划名称" align="center" prop="planName" />
          <el-table-column
            label="计划简介"
            align="center"
            prop="planIntroduction"
            show-overflow-tooltip
          />
          <el-table-column
            label="计划开始时间"
            align="center"
            prop="planStartTime"
            width="120"
          >
            <template slot-scope="scope">
              <span>{{
                parseTime(scope.row.planStartTime, "{y}-{m}-{d}")
              }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="计划结束时间"
            align="center"
            prop="planEndTime"
            width="120"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.planEndTime, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column label="计划个数" align="center" prop="planNumber" /> -->
          <el-table-column
            label="需响应计划数"
            align="center"
            prop="planNumber"
          />
          <el-table-column
            label="已响应计划数"
            align="center"
            prop="responseNumber"
          />
          <el-table-column label="计划状态" align="center" prop="state">
            <template slot-scope="scope">
              <span v-if="scope.row.state == '0'" style="color: #00eaff"
                >未响应</span
              >
              <span v-if="scope.row.state == '1'" style="color: #ffba00"
                >待审批</span
              >
              <span v-if="scope.row.state == '2'" style="color: #13ce66"
                >已审批</span
              >
              <span
                v-if="scope.row.state == '3'"
                style="color: #ff4949"
                :title="scope.row.reasons"
                >已驳回</span
              >
              <span v-if="scope.row.state == '4'" style="color: #00eaff"
                >进行中</span
              >
            </template>
          </el-table-column>
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            width="120"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            width="200"
            label="操作"
            align="left"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.state == '1'"
                size="btc"
                type="text"
                @click="handleUpdate1(scope.row)"
                v-hasPermi="['thought:responses:edit']"
                >审批</el-button
              >
              <el-button
                size="btc"
                type="text"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['thought:responses:query']"
                >查看</el-button
              >
              <!-- <el-button
              size="btc"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['thought:thoughtPlan:remove']"
              >删除</el-button
            > -->
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

    <!-- 添加或修改月教育计划对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1200px"
      append-to-body
    >
      <!-- <el-form
        ref="form"
        :model="form"
        :rules="rules"
        :inline="true"
        label-width="100px"
      >
        <div class="jiben">文档信息</div>
        <el-form-item label="计划名称" prop="planName">
          <el-input v-model="form.planName" placeholder="请输入计划名称" />
        </el-form-item>
        <el-form-item label="计划开始时间" prop="planStartTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.planStartTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择计划开始时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="计划结束时间" prop="planEndTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.planEndTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择计划结束时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="计划简介" prop="planIntroduction">
          <el-input
            type="textarea"
            v-model="form.planIntroduction"
            placeholder="请输入计划简介"
          />
        </el-form-item>
        <div class="jiben">计划条数</div>
        <div class="jiHua">
          <el-form v-for="(item1, e) in checkShop" :key="e">
            <el-form-item prop="remark">
              <el-input
                type="textarea"
                v-model="form.planIntroduction"
                placeholder="请输入计划详情"
              />
              <el-radio v-model="radio" label="1">是否需要响应</el-radio>
              <el-button
                type="primary"
                icon="el-icon-plus"
                size="mini"
                @click="addForms"
                v-if="e == checkShop.length - 1"
              ></el-button>
              <el-button
                type="primary"
                icon="el-icon-delete"
                size="mini"
                v-if="e > 0"
                @click="delForms(e)"
              ></el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="jiben">文件</div>
        <el-form-item>
          <FileUpload v-model="form.fileName" />
        </el-form-item>
      </el-form> -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 审批月教育计划响应对话框 -->
    <el-dialog
      title="审批月教育计划"
      :visible.sync="open2"
      width="1064px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <el-form
          ref="form2"
          :model="form"
          :rules="rules2"
          :inline="true"
          label-width="100px"
        >
          <div class="jiben">文档信息</div>
          <el-form-item label="计划名称" prop="planName">
            <el-input
              v-model="form.planName"
              placeholder="请输入计划名称"
              readonly
            />
          </el-form-item>
          <el-form-item label="计划开始时间" prop="planStartTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form.planStartTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择计划开始时间"
              readonly
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="计划结束时间" prop="planEndTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form.planEndTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择计划结束时间"
              readonly
            ></el-date-picker>
          </el-form-item>
          <div class="jiben">计划简介</div>
          <el-form-item label="" prop="planIntroduction">
            <el-input
              type="textarea"
              v-model="form.planIntroduction"
              placeholder="请输入计划简介"
              readonly
              autosize
            />
          </el-form-item>
          <div class="jiben">计划条数</div>
          <div class="jiHua">
            <el-form v-for="(item, e) in checkShop" :key="e">
              <span
                style="
                  color: #13ce66;
                  margin-bottom: 9px;
                  display: inline-block;
                "
                v-if="item.isresponse == '1'"
                >此条计划需各单位配合执行</span
              >
              <span
                style="
                  color: #ff4949;
                  margin-bottom: 9px;
                  display: inline-block;
                "
                v-if="item.isresponse == '0'"
                >此条计划非必要，各单位根据实际执行</span
              >
              <el-form-item prop="remark">
                <el-input
                  type="textarea"
                  v-model="item.planContent"
                  placeholder="请输入计划详情"
                  readonly
                  autosize
                />
              </el-form-item>
            </el-form>
          </div>
          <div class="jiben" v-if="planResponsefus.length != 0">文件</div>
          <el-form v-for="(item, i) in planResponsefus" :key="i">
            <el-form-item prop="file">
              <el-input
                label="附件名称"
                v-model="item.fileName"
                style="width: 260px"
                readonly
              />
              <a
                :href="item.filePath"
                download
                title="下载"
                style="color: white; margin-left: -58px"
                class="downloadfile"
                >下载附件</a
              >
            </el-form-item>
          </el-form>
          <div class="jiben">审批驳回原因</div>
          <el-form-item prop="reasons">
            <el-input v-model="form.reasons" type="textarea" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1">通过</el-button>
        <el-button @click="submitFormBoHui" size="btn">驳回</el-button>
      </div>
    </el-dialog>

    <!-- 查看月教育计划响应对话框 -->
    <el-dialog
      title="查看月教育计划"
      :visible.sync="open3"
      width="1064px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <el-form
          ref="form3"
          :model="form"
          :rules="rules3"
          :inline="true"
          label-width="100px"
        >
          <div class="jiben">文档信息</div>
          <el-form-item label="计划名称" prop="planName">
            <el-input
              v-model="form.planName"
              placeholder="请输入计划名称"
              readonly
            />
          </el-form-item>
          <el-form-item label="计划开始时间" prop="planStartTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form.planStartTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择计划开始时间"
              readonly
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="计划结束时间" prop="planEndTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form.planEndTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择计划结束时间"
              readonly
            ></el-date-picker>
          </el-form-item>
          <div class="jiben">计划简介</div>
          <el-form-item label="" prop="planIntroduction">
            <el-input
              type="textarea"
              v-model="form.planIntroduction"
              placeholder="请输入计划简介"
              readonly
              autosize
            />
          </el-form-item>
          <div class="jiben">计划条数</div>
          <div class="jiHua">
            <el-form v-for="(item, e) in checkShop" :key="e">
              <span
                style="
                  color: #13ce66;
                  margin-bottom: 9px;
                  display: inline-block;
                "
                v-if="item.isresponse == '1'"
                >此条计划需各单位配合执行</span
              >
              <span
                style="
                  color: #ff4949;
                  margin-bottom: 9px;
                  display: inline-block;
                "
                v-if="item.isresponse == '0'"
                >此条计划非必要，各单位根据实际执行</span
              >
              <el-form-item prop="remark">
                <el-input
                  type="textarea"
                  v-model="item.planContent"
                  placeholder="请输入计划详情"
                  readonly
                  autosize
                />
              </el-form-item>
            </el-form>
          </div>
          <div class="jiben" v-if="planResponsefus.length != 0">文件</div>
          <el-form v-for="(item, i) in planResponsefus" :key="i">
            <el-form-item prop="file">
              <el-input
                label="附件名称"
                v-model="item.fileName"
                style="width: 260px"
                readonly
              />
              <a
                :href="item.filePath"
                download
                title="下载"
                style="color: white; margin-left: -58px"
                class="downloadfile"
                >下载附件</a
              >
            </el-form-item>
          </el-form>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" size="btn">取消</el-button>
      </div>
    </el-dialog>

    <!-- 数据查看弹出层 -->
    <el-dialog
      :visible.sync="dialogVisible"
      title="统计详情"
      append-to-body
      id="dialoges"
      :close-on-click-modal="false"
    >
      <div class="zuiDa">
        <!-- <div class="shuJuTime">当前数据为 ： {{ time }}</div> -->
        <div class="jiben">已响应部门:{{ yishenpi }}个</div>
        <div class="boxxx">
          <div v-for="(item, i) in cg" :key="i" class="greed">
            <span class="greedss"> </span><span>{{ item }}</span>
          </div>
        </div>
        <div class="jiben">未响应部门:{{ weishenpi }}个</div>
        <div class="boxxx">
          <div v-for="(item, i) in sb" :key="i" class="red">
            <span class="greeds"> </span><span>{{ item }}</span>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listResponses,
  getResponses,
  delResponses,
  addResponses,
  updateResponses,
  exportResponses,
  Exhibition,
} from "@/api/thought/responses";
import { getWeiShu } from "@/api/grassrootsregistration/bdglmeeting";
import { exportBdgShuju } from "@/api/grassrootsregistration/bdgldiary";

export default {
  name: "responses",
  data() {
    return {
      // 遮罩层
      loading: true,
      tiem: null,
      // 导出遮罩层
      exportLoading: false,
      url: process.env.VUE_APP_BASE_API,
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
      // 月教育计划表格数据
      thoughtPlanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planName: null,
        planStartTime: null,
        planEndTime: null,
        planIntroduction: null,
        filePath: null,
        fileName: null,
        createName: null,
        createId: null,
        planNumber: null,
        responseNumber: null,
        unitId: null,
        state: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
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
      //添加计划条数表格为空
      checkShop: [
        {
          planContent: null,
          responseStatus: null,
        },
      ],
      radio: "",
      // 查看弹出层的显示与隐藏
      open3: false,
      rules3: {},
      files: [],
      // 下载文件
      planResponsefus: [],
      // 审批弹出层的显示与隐藏
      open2: false,
      rules2: {
        reasons: [
          { required: true, message: "请输入驳回原因", trigger: "blur" },
        ],
      },
      // 数据时间的保存
      tiems: "",
      // 获取成功对象
      cg: {},
      // 获取失败对象
      sb: {},
      // 获取登记部门数量
      yishenpi: null,
      // 获取未登记数量
      weishenpi: null,
      // 数据查看的弹出层
      dialogVisible: false,
      //  右边登记数据
      registrationData: {},
      time: "",
    };
  },
  created() {
    this.getList();
    this.treeselect();
    // 获取数据
    this.getShuJu();
    this.startTime();
  },
  methods: {
    // 时间
    startTime() {
      var today = new Date();
      var y = today.getFullYear();
      var M = today.getMonth() + 1;
      var d = today.getDate();
      if (M < 10) {
        M = "0" + M;
      }
      if (d < 10) {
        d = "0" + d;
      }
      this.time = y + "-" + M + "-" + d;
    },
    // 点击数据盒子
    dengJI() {
      this.dialogVisible = true;
    },
    // 获取部门数据
    getShuJu() {
      Exhibition().then((res) => {
        this.registrationData = res.data;
        this.cg = res.data[0];
        this.sb = res.data[1];
        this.yishenpi = res.data[2];
        this.weishenpi = res.data[3];
        this.tiems = res.time;
      });
    },
    // 审批确定提交操作
    submitForm1() {
      if (this.form.id != null) {
        this.form.state = "2";

        updateResponses(this.form).then((response) => {
          this.$modal.msgSuccess("审批成功");
          this.open2 = false;
          this.getList();
        });
      }
    },
    // 驳回提交操作
    submitFormBoHui() {
      this.$refs["form2"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            this.form.state = "3";

            updateResponses(this.form).then((response) => {
              this.$modal.msgSuccess("审批成功");
              this.open2 = false;
              this.getList();
            });
          }
        }
      });
    },
    // 审批按钮操作
    handleUpdate1(row) {
      this.reset();
      const id = row.id || this.ids;
      getResponses(id).then((response) => {
        this.form = response.data;
        this.form.reasons = null;
        this.checkShop = response.data.responseContents;
        response.data.planResponsefus.forEach((element) => {
          element.name = element.fileName;
          element.url = element.filePath;
        });
        // 附件下载的路径拼接
        if (response.data.planResponsefus.length != 0) {
          this.planResponsefus = response.data.planResponsefus;
        }
        this.planResponsefus.forEach((item) => {
          item.filePath = process.env.VUE_APP_BASE_API + item.filePath;
        });
        this.open2 = true;
      });
    },

    // 增加对话框
    addForms() {
      this.checkShop.push({
        planContent: null,
        responseStatus: null,
      });
    },
    // 删除对话框
    delForms(i) {
      this.checkShop.splice(i, 1);
    },
    /** 查询月教育计划列表 */
    getList() {
      this.loading = true;
      listResponses(this.queryParams).then((response) => {
        this.thoughtPlanList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open3 = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        planName: null,
        planStartTime: null,
        planEndTime: null,
        planIntroduction: null,
        filePath: null,
        fileName: null,
        createTime: null,
        updateTime: null,
        createName: null,
        createId: null,
        remark: null,
        planNumber: null,
        responseNumber: null,
        unitId: null,
        state: null,
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
      this.title = "添加月教育计划";
    },
    /** 查看按钮操作(修改) */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getResponses(id).then((response) => {
        this.form = response.data;
        this.checkShop = response.data.responseContents;
        response.data.planResponsefus.forEach((element) => {
          element.name = element.fileName;
          element.url = element.filePath;
        });
        // 附件下载的路径拼接
        if (response.data.planResponsefus.length != 0) {
          this.planResponsefus = response.data.planResponsefus;
        }
        this.planResponsefus.forEach((item) => {
          item.filePath = process.env.VUE_APP_BASE_API + item.filePath;
        });
        this.open3 = true;
        // this.title = "修改月教育计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateResponses(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addResponses(this.form).then((response) => {
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
        .confirm("是否确认删除月教育计划的数据项？")
        .then(function () {
          return delResponses(ids);
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
        .confirm("是否确认导出所有月教育计划数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportResponses(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
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
  },
};
</script>
<style scoped>
::v-deep .el-pagination__sizes .el-input__suffix {
  right: 6px;
}
::v-deep .ql-snow .ql-picker-options .ql-picker-item:focus {
  outline: none !important;
}
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
/* normal */
::v-deep .ql-active {
  outline: none !important;
}
/* 输入框 */
::v-deep .el-dialog .el-input__inner {
  background-color: #004d86 !important;
  width: 176px;
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
  width: 1000px;
  /* height: 80px !important; */
}
::v-deep .el-form-item__content .el-textarea .el-textarea__inner {
  /* width: 1000px; */
  width: 912px;
  min-height: 80px !important;
}
::v-deep .el-dialog__body {
  padding-left: 0px !important;
  padding-right: 0px !important;
  padding-bottom: 0px !important;
}
::v-deep .el-dialog__body {
  margin: 0px 0px 10px 68px;
  padding-top: 20px !important;
  box-sizing: border-box;
  /* padding: 30px 12px 30px 28px; */
}
/* 富文本内容 */
/* ::v-deep .editor{
  width: 1010px;
  color: white;
} */
/* 富文本 */
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
/* 划过效果 */
::v-deep .el-tree-node__content:hover {
  /* color: #fff; */
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
/* .app-container {
  display: flex;
} */
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
  right: 60px;
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
::v-deep .canjiaren .el-input__inner {
  width: 560px;
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
::v-deep .el-table__fixed-right {
  height: 100% !important;
}
::v-deep .el-dialog__footer {
  margin-right: 54px;
}
::v-deep th {
  background: #004d86 !important;
}
/* 下载按钮 */
.downloadfile {
  border: 1px solid white;
  display: inline-block;
  width: 100px;
  height: 36px;
  border-radius: 5px;
  line-height: 33px;
  text-align: center;
}
/* 卡片样式 */
.app-container {
  height: calc(100vh - 108px) !important;
}
.shuJu {
  height: 16px;
  margin-top: 13px;
  margin-right: 90px;
  float: right;
  /* background-color: red; */
  color: #fff;
  font-size: 14px;
  position: absolute;
  right: 10px;
}
.box44::before {
  display: inline-block;
  content: "";
  width: 8px;
  height: 8px;
  /* border-radius: 50%; */
  background-color: #1d96ff;
  margin-left: 26px;
}
.box5::before {
  display: inline-block;
  content: "";
  width: 8px;
  height: 8px;
  /* border-radius: 50%; */
  background-color: #67c23a;
  margin-left: 26px;
}
.box6::before {
  display: inline-block;
  content: "";
  width: 8px;
  height: 8px;
  /* border-radius: 50%; */
  background-color: #ff4949;
  margin-left: 26px;
}
.mb8 {
  height: 40px;
}
</style>
