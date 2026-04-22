<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['thought:thoughtPlan:add']"
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
          @click="handleUpdate4"
          v-hasPermi="['thought:responses:edit']"
          >修改</el-button
        >
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['thought:thoughtPlan:remove']"
          >删除</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['thought:responses:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="responsesList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="计划名称"
        align="center"
        prop="planName"
        width="100"
      />
      <el-table-column
        label="计划简介"
        align="center"
        prop="planIntroduction"
        width="120"
        show-overflow-tooltip
      />
      <el-table-column
        label="计划个数"
        align="center"
        prop="planIntroduction"
        width="120"
        show-overflow-tooltip
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="planStartTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.planStartTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="需响应计划数"
        align="center"
        prop="planNumber"
        width="100"
      />
      <el-table-column
        label="已响应计划数"
        align="center"
        prop="responseNumber"
        width="100"
      />

      <el-table-column
        label="开始时间"
        align="center"
        prop="planStartTime"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.planStartTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="结束时间"
        align="center"
        prop="planEndTime"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.planEndTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="state">
        <template slot-scope="scope">
          <span v-if="scope.row.state == '0'" style="color: #00eaff"
            >未响应</span
          >
          <span v-if="scope.row.state == '1'" style="color: #13ce66"
            >已完成</span
          >
          <span v-if="scope.row.state == '2'" style="color: #ffba00">通过</span>
          <span v-if="scope.row.state == '3'" style="color: #ff4949">驳回</span>
          <span v-if="scope.row.state == '4'" style="color: #00eaff"
            >进行中</span
          >
        </template>
      </el-table-column>
      <el-table-column label="操作" align="left" width="260" fixed="right">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.state == '3'"
            size="btu"
            type="text"
            @click="handleUpdate3(scope.row)"
            v-hasPermi="['thought:thoughtPlan:get']"
            >整改</el-button
          >
          <el-button
            v-if="scope.row.state == '4'"
            size="btj"
            type="text"
            @click="handleUpdate2(scope.row)"
            v-hasPermi="['thought:thoughtPlan:get']"
            >继续响应</el-button
          >
          <el-button
            v-if="scope.row.state == '0'"
            size="btc"
            type="text"
            @click="handleUpdate1(scope.row)"
            v-hasPermi="['thought:thoughtPlan:getDetails']"
            >响应</el-button
          >
          <el-button
            v-if="scope.row.common == '0'"
            size="btu"
            type="text"
            @click="handleUpdate4(scope.row)"
            v-hasPermi="['thought:thoughtPlan:updatePlan']"
            >修改</el-button
          >
          <el-button
            size="btc"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['thought:thoughtPlan:query']"
            >查看</el-button
          >
          <!-- <el-button
              size="btu"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['thought:responses:edit']"
              >修改</el-button
            > -->
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['thought:responses:remove']"
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

    <!-- 添加或修改月教育计划响应对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1064px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <el-form
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
          <div class="jiben">计划简介</div>
          <el-form-item label="" prop="planIntroduction">
            <el-input
              type="textarea"
              v-model="form.planIntroduction"
              placeholder="请输入计划简介"
              autosize
            />
          </el-form-item>
          <div class="jiben">计划条数</div>
          <div class="jiHua">
            <el-form v-for="(item, e) in checkShops" :key="e">
              <el-form-item prop="remarks">
                <el-input
                  type="textarea"
                  v-model="item.planContent"
                  placeholder="请输入计划详情"
                  autosize
                />
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="mini"
                  @click="addForms"
                  v-if="e == checkShops.length - 1"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  v-if="e > 0"
                  @click="delForms(e)"
                ></el-button>
                <el-switch
                  v-model="item.isresponse"
                  active-color="#13ce66"
                  inactive-color="#ccc"
                  active-value="1"
                  inactive-value="0"
                >
                </el-switch>
                <span class="greed" v-if="item.isresponse == '1'"
                  >需要响应</span
                >
                <span class="CCC" v-if="item.isresponse == '0'"
                  >非必要响应</span
                >
              </el-form-item>
            </el-form>
          </div>
          <div class="jiben">文件</div>
          <el-form-item>
            <FileUpload v-model="planResponsefus" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 响应月教育计划响应对话框 -->
    <el-dialog
      title="响应月教育计划"
      :visible.sync="open1"
      width="1064px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <el-form
          ref="form1"
          :model="form"
          :rules="rules1"
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
              autosize
              readonly
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
                  autosize
                  readonly
                />
                <el-switch
                  v-model="item.responseStatus"
                  active-color="#13ce66"
                  inactive-color="#ccc"
                  active-value="1"
                  inactive-value="0"
                >
                </el-switch>
                <span class="greed" v-if="item.responseStatus == '1'"
                  >响应</span
                >
                <!-- <span class="CCC" v-if="item.responseStatus == '0'"
                  >非必要响应</span
                > -->
              </el-form-item>
            </el-form>
          </div>
          <div class="jiben">文件</div>
          <el-form-item>
            <FileUpload v-model="planResponsefus" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="baocun1">保存进度</el-button>
        <el-button type="primary" @click="submitForm1">响应</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 整改月教育计划响应对话框 -->
    <el-dialog
      title="整改月教育计划"
      :visible.sync="open4"
      width="1064px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <el-form
          ref="form4"
          :model="form"
          :rules="rules4"
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
          <el-form-item prop="planIntroduction">
            <el-input
              type="textarea"
              v-model="form.planIntroduction"
              placeholder="请输入计划简介"
              autosize
              readonly
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
                  autosize
                  readonly
                />
                <el-switch
                  v-model="item.responseStatus"
                  active-color="#13ce66"
                  inactive-color="#ccc"
                  active-value="1"
                  inactive-value="0"
                >
                </el-switch>
                <span class="greed" v-if="item.responseStatus == '1'"
                  >响应</span
                >
                <!-- <span class="CCC" v-if="item.responseStatus == '0'"
                  >非必要响应</span
                > -->
              </el-form-item>
            </el-form>
          </div>
          <div class="jiben">驳回原因</div>
          <el-form-item prop="reasons">
            <el-input
              type="textarea"
              v-model="form.reasons"
              placeholder="请输入计划简介"
              autosize
              readonly
            />
          </el-form-item>
          <div class="jiben">文件</div>
          <el-form-item>
            <FileUpload v-model="planResponsefus" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="baocun2">保存进度</el-button>
        <el-button type="primary" @click="submitForm3">响应</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 继续响应月教育计划响应对话框 -->
    <el-dialog
      title="继续响应月教育计划"
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
          <el-form-item prop="planIntroduction">
            <el-input
              type="textarea"
              v-model="form.planIntroduction"
              placeholder="请输入计划简介"
              autosize
              readonly
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
                  autosize
                  readonly
                />
                <el-switch
                  v-model="item.responseStatus"
                  active-color="#13ce66"
                  inactive-color="#ccc"
                  active-value="1"
                  inactive-value="0"
                >
                </el-switch>
                <span class="greed" v-if="item.responseStatus == '1'"
                  >响应</span
                >
                <!-- <span class="CCC" v-if="item.responseStatus == '0'"
                  >非必要响应</span
                > -->
              </el-form-item>
            </el-form>
          </div>
          <div class="jiben">文件</div>
          <el-form-item>
            <FileUpload v-model="planResponsefus" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="baocun2">保存进度</el-button>
        <el-button type="primary" @click="submitForm3">响应</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
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
              autosize
              readonly
            />
          </el-form-item>
          <div class="jiben">计划条数</div>
          <div class="jiHua">
            <el-form v-for="(item, e) in checkShops" :key="e">
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
                  autosize
                  readonly
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
                style="width: 200px"
                readonly
              />
              <a
                :href="item.filePath"
                download
                title="下载"
                style="color: white; margin-left: 0px"
                class="downloadfile"
                >下载附件</a
              >
            </el-form-item>
          </el-form>
        </el-form>
        <!-- <div
          slot="footer"
          class="dialog-footer"
          style="text-align: right; margin-right: 76px; margin-top: 54px"
        >
          <el-button @click="cancel" size="btn">取 消</el-button>
        </div> -->
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listThoughtPlan,
  getThoughtPlan,
  delThoughtPlan,
  addThoughtPlan,
  updateThoughtPlan,
  exportThoughtPlan,
  xiangYing,
  JXxiangYing,
  JXxiangYingXiangYing,
  getXiuGaiButton,
  getResponse,
  getModify,
} from "@/api/thought/thoughtPlan";
import { getWeiShu } from "@/api/grassrootsregistration/bdglmeeting";
import { getUserProfile } from "@/api/system/user";

export default {
  name: "Responses",
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
      // 月教育计划响应表格数据
      responsesList: [],
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
      //响应计划条数表格为空
      checkShop: [
        {
          planContent: null,
          responseStatus: "0",
        },
      ],
      //添加计划条数表格为空
      checkShops: [
        {
          planContent: null,
          isresponse: "0",
        },
      ],
      // 上传文件
      planResponsefus: [],
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
      // 响应弹出层的显示与隐藏
      open1: false,
      rules1: {},
      thoughtPlanResponses: {},
      unitId: null,
      // 继续响应
      JiXuXY: null,
      open2: false,
      rules2: {},
      // 查看弹出层的显示与隐藏
      open3: false,
      rules3: {},
      files: [],
      // 整改弹出层的想显示与隐藏
      open4: false,
      rules4: {},
      // 新增单选框状态
      danxuan: "1",
      // 用户信息
      user: {},
    };
  },
  created() {
    // this.getList();
    this.treeselect();
    this.getUser();
  },
  methods: {
    // 获取登录信息
    getUser() {
      getUserProfile().then((response) => {
        this.user = response.data;
        this.unitId = response.data.deptId;
        this.queryParams.unitId = this.unitId;
        this.getList();
      });
    },

    // xuanZhong(value, e) {
    //   if (value == "1") {
    //     this.checkShops[e].isresponse = null;
    //     return;
    //   }
    //   this.checkShops[e].isresponse = "1";
    // },

    // 继续响应保存进度
    baocun2() {
      this.form.common = "0";
      this.form.planId = this.form.planId;
      this.form.unitId = this.unitId;
      this.form.planResponsefus = this.planResponsefus;
      this.form.responseContents = this.checkShop;
      this.thoughtPlanResponses = this.form;

      JXxiangYingXiangYing(this.thoughtPlanResponses).then((response) => {
        this.$modal.msgSuccess("响应成功");
        this.open1 = false;
        this.open2 = false;
        this.open4 = false;
        this.getList();
      });
    },
    // 继续响应
    handleUpdate2(row) {
      this.JiXuXY = row.id;
      JXxiangYing(this.unitId, this.JiXuXY).then((res) => {
        this.form = res.data;
        this.checkShop = res.data.responseContents;
        res.data.planResponsefus.forEach((element) => {
          element.name = element.fileName;
          element.url = element.filePath;
        });
        this.planResponsefus = res.data.planResponsefus;
        this.open2 = true;
      });
    },
    // 继续响应响应按钮提交
    submitForm2() {
      if (this.checkShop.every((item) => item.responseStatus)) {
        this.form.common = "1";
        this.form.planId = this.form.planId;
        this.form.unitId = this.unitId;
        this.form.responseContents = this.checkShop;
        this.form.planResponsefus = this.planResponsefus;
        this.thoughtPlanResponses = this.form;
        JXxiangYingXiangYing(this.thoughtPlanResponses).then((response) => {
          this.$modal.msgSuccess("响应成功");
          this.open2 = false;
          this.getList();
        });
      }
    },
    // 响应按钮
    handleUpdate1(row) {
      this.reset();
      const id = row.id || this.ids;
      getResponse(id).then((response) => {
        this.form = response.data;
        this.checkShop = response.data.planFus;
        this.open1 = true;
      });
    },
    // 响应提交按钮
    submitForm1() {
      var number = null;
      var numbers = 0;
      this.checkShop.forEach((item) => {
        if (item.isresponse == "1") {
          number++;
        }
        if (item.responseStatus == "1") {
          numbers++;
        }
      });
      if (numbers > number) {
        return;
      } else {
        this.form.common = "1";
        this.form.planId = this.form.id;
        this.form.unitId = this.unitId;
        this.form.responseContents = this.checkShop;
        this.form.planResponsefus = this.planResponsefus;
        this.thoughtPlanResponses = this.form;

        xiangYing(this.thoughtPlanResponses).then((response) => {
          this.$modal.msgSuccess("响应成功");
          this.open1 = false;
          this.getList();
        });
      }
    },
    // 保存响应进度提交按钮
    baocun1() {
      this.form.common = "0";
      this.form.planId = this.form.id;
      this.form.unitId = this.unitId;
      this.form.planResponsefus = this.planResponsefus;
      this.form.responseContents = this.checkShop;
      this.thoughtPlanResponses = this.form;
      xiangYing(this.thoughtPlanResponses).then((response) => {
        this.$modal.msgSuccess("响应成功");
        this.open1 = false;
        this.getList();
      });
    },
    // 整改按钮
    handleUpdate3(row) {
      this.JiXuXY = row.id;
      JXxiangYing(this.unitId, this.JiXuXY).then((res) => {
        this.form = res.data;
        this.checkShop = res.data.responseContents;
        this.planResponsefus = res.data.planResponsefus;
        this.open4 = true;
      });
    },
    // 整改响应提交按钮
    submitForm3() {
      // var number = null;
      // var numbers = 0;
      // this.checkShop.forEach((item) => {
      //   if (item.isresponse == "1") {
      //     number++;
      //   }
      //   if (item.responseStatus == "1") {
      //     numbers++;
      //   }
      // });
      // if (numbers > number) {
      //   return;
      // } else {
      this.form.common = "1";
      this.form.unitId = this.unitId;
      this.form.responseContents = this.checkShop;
      this.form.planResponsefus = this.planResponsefus;
      this.thoughtPlanResponses = this.form;
      JXxiangYingXiangYing(this.thoughtPlanResponses).then((response) => {
        this.$modal.msgSuccess("响应成功");
        this.open4 = false;
        this.open2 = false;
        this.getList();
      });
      // }
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
      this.unitId = data.id;
      this.getList();
    },
    // 增加对话框
    addForms() {
      this.checkShops.push({
        planContent: null,
        isresponse: null,
      });
    },

    // 删除对话框
    delForms(i) {
      this.checkShops.splice(i, 1);
    },
    /** 查询月教育计划响应列表 */
    getList() {
      this.loading = true;
      listThoughtPlan(this.queryParams).then((response) => {
        this.responsesList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open1 = false;
      this.open2 = false;
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
        state: null,
      };
      (this.checkShop = [
        {
          planContent: null,
          responseStatus: null,
        },
      ]),
        (this.checkShops = [
          {
            planContent: null,
            isresponse: null,
          },
        ]),
        (this.planResponsefus = []),
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
    /** 查看按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getThoughtPlan(id).then((response) => {
        this.form = response.data;
        this.checkShops = response.data.planFus;
        response.data.planFujians.forEach((element) => {
          element.name = element.fileName;
          element.url = element.filePath;
        });
        // 附件下载的路径拼接
        if (response.data.planFujians.length != 0) {
          this.planResponsefus = response.data.planFujians;
        }
        this.planResponsefus.forEach((item) => {
          item.filePath = process.env.VUE_APP_BASE_API + item.filePath;
        });
        this.open3 = true;
      });
    },
    // 修改按钮操作
    handleUpdate4(row) {
      this.reset();
      const id = row.id || this.ids;
      getModify(id).then((response) => {
        this.form = response.data;
        this.checkShops = response.data.planFus;
        response.data.planFujians.forEach((element) => {
          element.name = element.fileName;
          element.url = element.filePath;
        });
        this.planResponsefus = response.data.planFujians;
        this.open = true;
        this.title = "修改月教育计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            this.form.planFus = this.checkShops;
            this.form.planFujians = this.planResponsefus;
            updateThoughtPlan(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (this.checkShops[0].planContent != null) {
              this.form.planFus = this.checkShops;
              this.form.planFujians = this.planResponsefus;
              addThoughtPlan(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            } else {
              this.$modal.msgError("计划条数必须有一条");
            }
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除月教育计划响应的数据项？")
        .then(function () {
          return delThoughtPlan(ids);
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
        .confirm("是否确认导出所有月教育计划响应数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportThoughtPlan(queryParams);
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
::v-deep .canjiaren .el-input__inner {
  width: 560px !important;
}
::v-deep .el-dialog {
  background-color: #004d86 !important;
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
}
::v-deep .el-dialog__body {
  padding-left: 76px !important;
  padding-right: 0px !important;
  padding-bottom: 20px !important;
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
::v-deep .el-input__suffix {
  right: 22px;
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
  /* color: white; */
}
::v-deep .vue-treeselect__single-value {
  color: white;
}
/* 所搜到访单位 */
::v-deep .waibudaofang .vue-treeselect__control {
  background-color: #00365f !important;
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
::v-deep .el-form-item__content .el-textarea .el-textarea__inner {
  width: 912px;
  min-height: 80px !important;
}
.el-switch {
  position: absolute;
  color: #fff;
  top: 12px;
  right: 106px;
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
/* 左侧树状盒子 */
.app-container .box-lfet {
  width: 15%;
  min-height: 740px;
  margin-right: 10px;
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
::v-deep .canjiaren .el-input__inner {
  width: 560px;
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

/* 添加按钮样式 */
.el-button--btq.is-active,
.el-button--btq:active {
  background: #1890ff;
  border-color: #1890ff;
  color: #ffffff;
}

.el-button--btq:focus,
.el-button--btq:hover {
  background: #1890ff;
  border-color: #1890ff;
  color: #ffffff;
}

.el-button--btq {
  /* border-color: #20B2AA; */
  color: #ffffff;
  width: 44px;
  height: 28px;
  background-color: #1890ff;
  border-radius: 4px;
  font-size: 14px;
  text-align: center;
  margin-top: 1px;
  padding-top: 10px;
  padding-right: 30px;
}
/* 继续整改 btj */
.el-button--btj.is-active,
.el-button--btj:active {
  background: #13ce66;
  border-color: #13ce66;
  color: #ffffff;
}

.el-button--btj:focus,
.el-button--btj:hover {
  background: #35ca78;
  border-color: #35ca78;
  color: #ffffff;
}

.el-button--btj {
  width: 72px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #13ce66;
  height: 29px;
}
::v-deep .el-table__fixed-right {
  height: 100% !important;
}
::v-deep .el-dialog__footer {
  margin-right: 54px;
}
::v-deep .el-input__suffix-inner {
  margin-right: 40px;
}
::v-deep .el-textarea__inner {
  padding: 10px 10px;
}
::v-deep th {
  background: #004d86 !important;
}
.el-button--btc {
  margin-right: 0px;
}
/* 计划条数 */
::v-deep .jiHua form .el-form-item .el-textarea .el-textarea__inner {
  padding-right: 94px !important;
}
.greed {
  position: absolute;
  top: 26px;
  right: 97px;
  color: #13ce66;
}
.CCC {
  position: absolute;
  top: 26px;
  right: 92px;
  color: #cccccc;
}
/* 附件下载 */
::v-deep .el-upload-list__item:hover {
  background-color: transparent;
}
</style>
