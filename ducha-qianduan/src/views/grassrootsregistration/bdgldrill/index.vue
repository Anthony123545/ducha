<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
      class="shouForm"
    >
      <el-form-item prop="unitname">
        <el-input
          placeholder="请输入单位"
          v-model="queryParams.unitname"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button size="btr" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['grassrootsregistration:bdgldrill:add']"
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
          v-hasPermi="['grassrootsregistration:bdgldrill:edit']"
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
          v-hasPermi="['grassrootsregistration:bdgldrill:remove']"
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
          v-hasPermi="['grassrootsregistration:bdgldrill:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      :data="bdgldrillList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="训练类别"
        align="center"
        prop="trainingCategory"
      />
      <el-table-column
        label="训练地点"
        align="center"
        prop="address"
        show-overflow-tooltip
      />
      <el-table-column
        label="训练时间"
        align="center"
        prop="drillTime"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.drillTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="训练结束时间"
        align="center"
        prop="drillEndtime"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.drillEndtime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="单位"
        align="center"
        prop="unitname"
        width="200"
      />
      <!-- <el-table-column label="应训" align="center" prop="oughtTo" /> -->
      <el-table-column
        label="文件名称"
        align="center"
        prop="filename"
        show-overflow-tooltip
      />
      <el-table-column label="装备" align="center" prop="equipment" />
      <el-table-column label="弹药" align="center" prop="ammunition" />
      <el-table-column label="摩托时间" align="center" prop="mototime" />
      <el-table-column
        label="备注"
        align="center"
        prop="fail"
        show-overflow-tooltip
      />

      <!-- <el-table-column label="创建人id" align="center" prop="createId" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button size="btk" type="text" @click="handleChakan(scope.row)"
            ><span class="chakan">查看</span></el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['grassrootsregistration:bdgldrill:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:bdgldrill:remove']"
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

    <!-- 添加军事训练登记簿对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1060px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        :inline="true"
        label-width="120px"
      >
        <div class="jiben">基本信息</div>
        <el-form-item label="单位" prop="unitId">
          <treeselect
            @select="selectPeo"
            v-model="form.unitId"
            :options="rese"
            placeholder="请选择单位"
          />
        </el-form-item>
        <el-form-item
          label="训练类别"
          prop="trainingCategory"
          style="margin-left: 100px"
        >
          <treeselect
            v-model="form.trainingCategory"
            :options="treeselects"
            :normalizer="defaultProps"
            placeholder="请选择训练类别"
            @select="trainingCategoryselectPeo"
          />
        </el-form-item>
        <el-form-item
          label="证明材料"
          prop="project2"
          style="display: inline-block; width: 900px"
        >
          <fileUpload
            v-model="form.filepath"
            style="display: inline-block"
            :show-file-list="false"
            class="m-l-20"
            :limit="1"
          />
        </el-form-item>
        <el-form-item label="训练开始时间" prop="drillTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.drillTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择训练开始时间"
            @change="timeDay"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="训练结束时间"
          prop="drillEndtime"
          style="margin-left: 130px"
        >
          <!-- <el-date-picker
            clearable
            size="small"
            v-model="form.drillEndtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择训练结束时间"
            @change="drillTimechange"
          >
          </el-date-picker> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.drillEndtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择训练结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="周工作内容" class="boxtext">
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入内容"
            v-model="form.trainingContent"
            autosize
          >
          </el-input>
        </el-form-item>
        <el-row>
          <el-checkbox
            v-model="form.safety"
            style="
              margin-right: 80px;
              height: 40px;
              margin-left: 70px;
              color: #fff;
            "
            >安全训练</el-checkbox
          >
          <el-checkbox
            v-model="form.chiefOfficial"
            style="
              margin-right: 80px;
              height: 40px;
              margin-left: 70px;
              color: #fff;
            "
            >主官训练</el-checkbox
          >
          <el-checkbox
            v-model="form.targeted"
            style="
              margin-right: 80px;
              height: 40px;
              margin-left: 70px;
              color: #fff;
            "
            >针对性训练</el-checkbox
          >
          <el-checkbox
            v-model="form.system"
            style="
              margin-right: 80px;
              height: 40px;
              margin-left: 70px;
              color: #fff;
            "
            >系统性训练</el-checkbox
          >
        </el-row>
        <el-form-item label="地点" prop="address">
          <el-input v-model="form.address" placeholder="请输入训练地点" />
        </el-form-item>
        <el-form-item label="弹药" prop="ammunition">
          <el-input v-model="form.ammunition" placeholder="请输入弹药" />
        </el-form-item>
        <el-form-item label="摩托时间" prop="mototime">
          <el-input v-model="form.mototime" placeholder="请输入摩托时间" />
        </el-form-item>
        <el-form-item label="装备" prop="equipment">
          <el-input v-model="form.equipment" placeholder="请输入装备" />
        </el-form-item>
        <el-row>
          <el-checkbox
            v-model="form.assessment"
            @change="checkboxs"
            style="
              margin-right: 80px;
              height: 40px;
              margin-left: 70px;
              color: #fff;
            "
            >是否参加考核</el-checkbox
          >
        </el-row>
        <el-form-item label="应训(人数)" prop="yingxun">
          <el-input
            v-model.number="form.yingxun"
            placeholder="请输入应训人数"
            @change="yingxun"
          />
        </el-form-item>
        <el-form-item label="实训(人数)" prop="shixun">
          <el-input
            v-model.number="form.shixun"
            placeholder="请输入实训人数"
            @change="shixun"
          />
        </el-form-item>
        <el-form-item label="参训率" prop="participationRate">
          <el-input v-model="form.participationRate" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="效果(成绩)" prop="pass">
          <el-input
            v-model="form.pass"
            placeholder="请输入效果成绩"
            :disabled="dilabled"
          />
        </el-form-item>
        <el-form-item label="消耗" prop="excellent">
          <el-input v-model="form.excellent" placeholder="请输入消耗" />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item label="" prop="fail">
          <el-input
            v-model="form.fail"
            type="textarea"
            placeholder="请输入内容"
            autosize
          />
        </el-form-item>
        <el-row>
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="adddrillFus"
            style="margin-left: 0px; width: 100px; margin-bottom: 20px"
            >添加人员</el-button
          >
        </el-row>
        <el-table
          :data="drillFus"
          :header-cell-style="{ background: '#003C69', color: 'white' }"
        >
          <el-table-column label="姓名" align="center" prop="peopleId">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.peopleId"
                placeholder=""
                filterable
                disabled
              >
                <el-option
                  :label="item.name"
                  v-for="(item, i) in renYuan"
                  :key="i"
                  :value="item.id"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            label="参训状态"
            align="center"
            prop="trainingStatus"
          >
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.trainingStatus"
                placeholder="请选择参训状态"
                filterable
              >
                <el-option
                  v-for="dict in dict.type.military_training"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.label"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="state">
            <template slot-scope="scope">
              <el-button size="btd" type="text" @click="deldeitem(scope.$index)"
                ><span class="delete">删除</span></el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改军事训练登记薄 -->
    <el-dialog
      :title="title"
      :visible.sync="open1"
      width="1060px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        :inline="true"
        label-width="120px"
      >
        <div class="jiben">基本信息</div>
        <el-form-item label="单位" prop="unitId">
          <treeselect
            @select="selectPeo"
            v-model="form.unitId"
            :options="rese"
            placeholder="请选择单位"
            disabled
          />
        </el-form-item>
        <el-form-item
          label="训练类别"
          prop="trainingCategory"
          style="margin-left: 100px"
        >
          <!-- <el-input
            v-model="form.trainingCategory"
            placeholder="请输入训练类别"
          /> -->
          <treeselect
            v-model="form.trainingCategory"
            :options="treeselects"
            :normalizer="defaultProps"
            placeholder="请选择训练类别"
            @select="trainingCategoryselectPeo"
          />
        </el-form-item>
        <!-- <el-form-item label="训练项目" prop="project">
          <el-input v-model="form.project" placeholder="请输入训练项目" />
        </el-form-item> -->
        <el-form-item
          label="证明材料"
          prop="project2"
          style="display: inline-block; width: 900px"
        >
          <!-- <input
            type="text"
            v-model="form.filename"
            style="
              width: 280px;
              height: 36px;
              background: transparent;
              color: #fff;
              border: 1px solid #fff !important;
              display: inline-block;
            "
          /> -->
          <fileUpload
            v-model="form.filepath"
            style="display: inline-block"
            :show-file-list="false"
            class="m-l-20"
            :limit="1"
          />
        </el-form-item>
        <el-form-item label="训练开始时间" prop="drillTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.drillTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择训练开始时间"
            @change="timeDay"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="训练结束时间"
          prop="drillEndtime"
          style="margin-left: 130px"
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.drillEndtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择训练结束时间"
            @change="drillTimechange"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="周工作内容" class="boxtext">
          <el-input
            type="textarea"
            placeholder="请输入内容"
            v-model="form.trainingContent"
            autosize
          >
          </el-input>
          <!-- <input
            v-model="nr"
            placeholder=""
            style="
              width: 810px;
              height: 36px;
              background: transparent;
              color: #fff;
              border: 1px solid #fff !important;
            "
            disabled
          /> -->
        </el-form-item>
        <el-row>
          <el-checkbox
            v-model="form.safety"
            style="
              margin-right: 80px;
              height: 40px;
              margin-left: 70px;
              color: #fff;
            "
            >安全训练</el-checkbox
          >
          <el-checkbox
            v-model="form.chiefOfficial"
            style="
              margin-right: 80px;
              height: 40px;
              margin-left: 70px;
              color: #fff;
            "
            >主官训练</el-checkbox
          >
          <el-checkbox
            v-model="form.targeted"
            style="
              margin-right: 80px;
              height: 40px;
              margin-left: 70px;
              color: #fff;
            "
            >针对性训练</el-checkbox
          >
          <el-checkbox
            v-model="form.system"
            style="
              margin-right: 80px;
              height: 40px;
              margin-left: 70px;
              color: #fff;
            "
            >系统性训练</el-checkbox
          >
        </el-row>
        <el-form-item label="地点" prop="address">
          <el-input v-model="form.address" placeholder="请输入训练地点" />
        </el-form-item>
        <el-form-item label="弹药" prop="ammunition">
          <el-input v-model="form.ammunition" placeholder="请输入弹药" />
        </el-form-item>
        <el-form-item label="摩托时间" prop="mototime">
          <el-input v-model="form.mototime" placeholder="请输入摩托时间" />
        </el-form-item>
        <el-form-item label="装备" prop="equipment">
          <el-input v-model="form.equipment" placeholder="请输入装备" />
        </el-form-item>
        <el-row>
          <el-checkbox
            v-model="form.assessment"
            @change="checkboxs"
            style="
              margin-right: 80px;
              height: 40px;
              margin-left: 70px;
              color: #fff;
            "
            >是否参加考核</el-checkbox
          >
        </el-row>
        <el-form-item label="应训(人数)" prop="yingxun">
          <el-input
            v-model.number="form.yingxun"
            placeholder="请输入应训人数"
            @change="yingxun"
          />
        </el-form-item>
        <el-form-item label="实训(人数)" prop="shixun">
          <el-input
            v-model.number="form.shixun"
            placeholder="请输入实训人数"
            @change="shixun"
          />
        </el-form-item>
        <el-form-item label="参训率" prop="participationRate">
          <el-input v-model="form.participationRate" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="效果(成绩)" prop="pass">
          <el-input
            v-model="form.pass"
            placeholder="请输入效果成绩"
            :disabled="dilabled"
          />
        </el-form-item>
        <el-form-item label="消耗" prop="excellent">
          <el-input v-model="form.excellent" placeholder="请输入消耗" />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item label="" prop="fail">
          <el-input
            v-model="form.fail"
            type="textarea"
            placeholder="请输入内容"
            autosize
          />
        </el-form-item>
        <el-row>
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="adddrillFus"
            style="margin-left: 0px; width: 100px; margin-bottom: 20px"
            >添加人员</el-button
          >
        </el-row>
        <el-table
          :data="drillFus"
          :header-cell-style="{ background: '#003C69', color: 'white' }"
        >
          <el-table-column label="姓名" align="center" prop="peopleId">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.peopleId"
                placeholder=""
                filterable
                disabled
              >
                <el-option
                  :label="item.name"
                  v-for="(item, i) in renYuan"
                  :key="i"
                  :value="item.id"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            label="参训状态"
            align="center"
            prop="trainingStatus"
          >
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.trainingStatus"
                placeholder="请选择参训状态"
                filterable
              >
                <el-option
                  v-for="dict in dict.type.military_training"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.label"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="state">
            <template slot-scope="scope">
              <el-button
                size="btd"
                type="text"
                @click="deldeitem1(scope.$index, scope.row)"
                ><span class="delete">删除</span></el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看表格 -->
    <div class="fff">
      <el-dialog
        :visible.sync="menuRoleVisible"
        :title="title"
        append-to-body
        id="chakan"
        :close-on-click-modal="false"
        width="1060px"
      >
        <bdgldrill
          v-if="menuRoleVisible"
          ref="menuRole"
          :message="wordInfo"
        ></bdgldrill>
      </el-dialog>
    </div>

    <!-- 添加人员弹出层 -->
    <el-dialog
      title="添加人员"
      :visible.sync="open2"
      width="750px"
      append-to-body
      :close-on-click-modal="false"
      custom-class="boxs"
    >
      <el-transfer
        v-model="arr1"
        :data="renYuan"
        :props="{
          key: 'id',
          label: 'name',
        }"
        :titles="['全部人员', '已添加人员']"
      ></el-transfer>
      <div slot="footer" class="dialog-footer" style="margin-right: 28px">
        <el-button type="primary" @click="submitForm3">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listBdgldrill,
  getBdgldrill,
  delBdgldrill,
  addBdgldrill,
  updateBdgldrill,
  exportBdgldrill,
  treeselect,
  getweekwork,
  drillFu,
  treeselects,
  getInfoByUnitId,
} from "@/api/grassrootsregistration/bdgldrill";
import bdgldrill from "@/components/look_word/bdgldrill.vue";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {
  getDept,
  getZhuChiRen,
  getDept2,
} from "@/api/grassrootsregistration/bdglmeeting";
import { jieZhiList } from "@/api/grassrootsregistration/bdgldiary";
export default {
  name: "Bdgldrill",
  components: { Treeselect, bdgldrill },
  dicts: ["military_training"],
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
      // 军事训练登记簿表格数据
      bdgldrillList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        project: null,
        drillTime: null,
        address: null,
        oughtTo: null,
        filename: null,
        filepath: null,
        equipment: null,
        ammunition: null,
        mototime: null,
        drillEndtime: null,
        unitId: null,
        trainingCategory: null,
        createtime: null,
        createId: null,
      },
      // 表单参数
      form: {
        safety: false,
        chiefOfficial: false,
        targeted: false,
        system: false,
        assessment: false,
      },
      // 表单校验
      rules: {
        unitId: [{ required: true, message: "请选择单位", trigger: "blur" }],
      },
      cities: ["安全训练"],
      cities2: ["主官组训施教"],
      cities3: ["针对性训练"],
      cities4: ["使用模拟训练系统"],
      checkedCities: [],
      checkedCities2: [],
      checkedCities3: [],
      checkedCities4: [],
      checkedCities5: [],
      drillFus: [],
      formdrillFus: {
        peopleName: "",
        state: "",
        trainingStatus: "",
      },
      open2: false,
      rese: [],
      renYuan: [],
      treeselects: [],
      defaultProps(node) {
        return {
          children: node.children,
          label: node.ancestors,
        };
      },
      menuRoleVisible: false,
      wordInfo: [],
      nr: null,
      fiel: [],
      // 修改弹出层的显示与隐藏
      open1: false,
      // 是否禁用效果成绩输入框
      dilabled: true,
      // 添加人员弹出层
      open2: false,
      arr1: [],
      falg: null,
      form1: {},
      // 当月日期
      current_month: [],
      // 下月日期
      current_month1: [],
      // 日期开关状态
      dayTime: null,
    };
  },
  created() {
    this.getList();
    this.getYear();
    treeselect().then((res) => {
      this.treeselects = res.rows;
    });
    // 获取当月
    this.getDay();
    // 获取下月
    this.getDayX();
    jieZhiList().then((res) => {
      this.dayTime = res.rows[0].time;
    });
  },
  methods: {
    // 获取当月日期
    getDay() {
      // 获取标准时间
      let now = new Date();
      // 年
      var year = new Date().getFullYear();
      // 月
      var month = new Date().getMonth() + 1;
      // 日
      var day = new Date(year, month, 0).getDate();
      // console.log(now);
      let current_month_num = day;
      this.current_month = [];
      for (let i = 1; i <= current_month_num; i++) {
        let day = now.setDate(i);
        let date = new Date(day); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        let Y = date.getFullYear() + "-";
        let M =
          (date.getMonth() + 1 < 10
            ? "0" + (date.getMonth() + 1)
            : date.getMonth() + 1) + "-";
        let D =
          (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "";
        this.current_month.push(Y + M + D);
        // console.log(this.current_month);
      }
    },
    // 获取下个月日期
    getDayX() {
      // 获取标准时间
      let now = new Date();
      // 年
      var year = new Date().getFullYear();
      // 月
      var month = new Date().getMonth() + 2;
      // 日
      var day = new Date(year, month, 0).getDate();
      let current_month_num1 = day;
      for (let i = 1; i <= current_month_num1; i++) {
        let day = now.setDate(i);
        let date = new Date(day); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        let Y = date.getFullYear() + "-";
        let M =
          (date.getMonth() + 2 < 10
            ? "0" + (date.getMonth() + 2)
            : date.getMonth() + 2) + "-";
        let D =
          (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "";
        this.current_month.push(Y + M + D);
      }
    },
    timeDay(val) {
      if (!val) return;

      // 用户输入的时间戳（秒）
      const date1 = Math.floor(new Date(val).getTime() / 1000);

      // 获取当月第一天 00:00:00
      const now = new Date(val);  // 这里用 val 保证选中月份为准
      const firstDay = new Date(now.getFullYear(), now.getMonth(), 1).getTime() / 1000;

      // 获取当月最后一天 23:59:59
      const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0, 23, 59, 59).getTime() / 1000;

      // 判断是否在当月范围内
      if (this.dayTime === 1 && (date1 < firstDay || date1 > lastDay)) {
        this.$confirm("请在规定时间内填写", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.form.recordDate = null;
          })
          .catch(() => {
            this.form.recordDate = null;
          });
      }
    },
    getYear() {
      var myDate = new Date();
      var tYear = myDate.getFullYear();
      this.form1.year = tYear;
    },
    // 添加人员确定
    submitForm3() {
      this.open2 = false;
      this.arr1.forEach((item) => {
        this.drillFus.push({
          peopleId: item,
          state: "",
          trainingStatus: "",
        });
      });
      for (var i = 0; i < this.drillFus.length - 1; i++) {
        for (var j = i + 1; j < this.drillFus.length; j++) {
          if (this.drillFus[i].peopleId == this.drillFus[j].peopleId) {
            this.drillFus.splice(j, 1);
            j--;
          }
        }
      }
    },
    // 应训人数触发
    yingxun() {
      if (this.form.yingxun && this.form.shixun) {
        var num = (this.form.shixun / this.form.yingxun) * 100;
        num = num.toFixed(2) + "%";
        this.$set(this.form, "participationRate", num);
      }
    },
    // 实训人数触发
    shixun() {
      if (this.form.yingxun && this.form.shixun) {
        var num = (this.form.shixun / this.form.yingxun) * 100;
        num = num.toFixed(2) + "%";
        this.$set(this.form, "participationRate", num);
      }
    },
    // 是否参加考核触发
    checkboxs(data) {
      this.dilabled = !data;
      if (!data) {
        this.form.pass = null;
      }
    },
    // 获取全部单位树形
    getQuanShu() {
      getDept2().then((res) => {
        this.rese = res.data;
      });
    },
    // 点击删除删除循环出来的当前数据
    deldeitem(row) {
      this.drillFus.splice(row.ids, 1);
    },
    // 修改中的删除循环数据
    deldeitem1(row, rows) {
      this.drillFus.splice(row.ids, 1);
      drillFu(rows.id).then((res) => {});
    },

    /** 查询军事训练登记簿列表 */
    getList() {
      this.loading = true;
      listBdgldrill(this.queryParams).then((response) => {
        this.bdgldrillList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open2 = false;
      this.open1 = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        project: null,
        drillTime: null,
        address: null,
        oughtTo: null,
        filename: null,
        filepath: null,
        equipment: null,
        ammunition: null,
        mototime: null,
        remark: null,
        drillEndtime: null,
        unitId: null,
        trainingCategory: null,
        createtime: null,
        createId: null,
      };
      this.formdrillFus = {
        peopleName: null,
        state: null,
        trainingStatus: null,
        ids: null,
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
      this.getTreeselect();
      this.title = "添加军事训练登记簿";
      this.drillFus = [];
      this.nr = null;
      this.arr1 = [];
      this.falg = true;
    },
    //查看按钮操作
    handleChakan(row) {
      this.form1.unitId = row.unitId;
      // 是否隐藏按钮
      this.reset();
      getInfoByUnitId(this.form1).then((response) => {
        this.wordInfo = response.rows;
        this.title = "军事训练登记簿";
        this.menuRoleVisible = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.nr = null;
      this.falg = false;
      this.arr1 = [];
      this.reset();
      this.getQuanShu();
      const id = row.id || this.ids;
      getBdgldrill(id).then((response) => {
        this.form = response.data;
        if (response.data.filename != null || response.data.filepath != null) {
          this.fiel = [
            {
              name: response.data.filename,
              url: response.data.filepath,
            },
          ];

          this.form.filepath = this.fiel;
        }

        if (this.form.safety == 1) {
          this.form.safety = true;
        } else {
          this.form.safety = false;
        }
        if (this.form.chiefOfficial == 1) {
          this.form.chiefOfficial = true;
        } else {
          this.form.chiefOfficial = false;
        }
        if (this.form.targeted == 1) {
          this.form.targeted = true;
        } else {
          this.form.targeted = false;
        }
        if (this.form.system == 1) {
          this.form.system = true;
        } else {
          this.form.system = false;
        }
        if (this.form.assessment == 1) {
          this.form.assessment = true;
        } else {
          this.form.assessment = false;
        }
        this.nr = response.data.common;
        this.drillFus = response.data.drillFus;
        this.getRen(response.data.unitId);
        this.open1 = true;
        this.title = "修改军事训练登记簿";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.filepath) {
            if (typeof this.form.filepath != "string") {
              let documentProofs = this.form.filepath;
              this.form.filepath = documentProofs[0].url;
              this.form.filename = documentProofs[0].name;
            }
          }
          this.form.drillFus = this.drillFus;
          if (this.form.safety) {
            this.form.safety = 1;
          } else {
            this.form.safety = 0;
          }
          if (this.form.chiefOfficial) {
            this.form.chiefOfficial = 1;
          } else {
            this.form.chiefOfficial = 0;
          }
          if (this.form.targeted) {
            this.form.targeted = 1;
          } else {
            this.form.targeted = 0;
          }
          if (this.form.system) {
            this.form.system = 1;
          } else {
            this.form.system = 0;
          }
          if (this.form.assessment) {
            this.form.assessment = 1;
          } else {
            this.form.assessment = 0;
          }

          if (this.form.id != null) {
            updateBdgldrill(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open1 = false;
              this.getList();
            });
          } else {
            console.log(this.form);
            addBdgldrill(this.form).then((response) => {
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
        .confirm("是否确认删除军事训练登记簿的数据项？")
        .then(function () {
          return delBdgldrill(ids);
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
        .confirm("是否确认导出所有军事训练登记簿数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdgldrill(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    // 新增人员
    adddrillFus() {
      this.open2 = true;
      if (this.falg) {
        console.log(1);
        this.renYuan.map((item, i) => {
          if (this.drillFu) {
            if (item.id == this.drillFu[i]) {
              this.arr1[i] = item;
            }
          }
          return this.arr1;
        });
      } else if (this.falg == false) {
        // this.arr1=this.drillFus
        this.drillFus.forEach((item) => {
          this.arr1.push(item.peopleId);
        });
      }
    },
    submitForm2() {
      this.drillFus.push(this.formdrillFus);
      this.open = true;
      this.open2 = false;
    },
    cancel2() {
      this.open = true;
      this.open2 = false;
      this.reset();
    },
    handleCheckedCitiesChange() {},
    /** 新增查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.rese = response.data;
      });
    },
    // 单位选择触发
    selectPeo(data) {
      this.form.unitname = data.label;
      this.getRen(data.id);
    },
    // 获取人员
    getRen(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    trainingCategoryselectPeo(val) {},
    // 训练开始时间触发
    drillTimechange() {
      if (this.form.drillTime && this.form.drillEndtime && this.form.unitId) {
        getweekwork({
          drillTime: this.form.drillTime,
          drillEndtime: this.form.drillEndtime,
          unitId: this.form.unitId,
        }).then((res) => {
          this.nr = "";
          res.data.map((item, i) => {
            if (i == 0) {
              this.nr += item.contents;
            } else {
              this.nr += "," + item.contents;
            }
          });
        });
      }
    },
  },
};
</script>
<style scoped>
::v-deep .el-dialog {
  /* width: 1060px !important; */
  background-color: #004d86;
  margin: auto;
}
::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
  margin: auto;
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
.el-form-item ::v-deep .el-input__inner {
  width: 350px;
  height: 36px;
  background: transparent;
  color: #fff;
  border: 1px solid #fff !important;
}
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
  color: #fff !important;
}
::v-deep .vue-treeselect__control {
  width: 250px;
  color: #fff !important;
}
/* ::v-deep .el-textarea__inner {
  width: 920px;
  height: 104px;
}
::v-deep .el-select-dropdown__list {
  border: none;
}
.el-date-table,
.el-select-dropdown__item {
  color: #fff;
}
.el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #196299;
} */

::v-deep .el-upload__tip {
  display: none;
}
/* 去掉上面的线 */
/* ::v-deep .el-dialog .el-table th.is-leaf {
  border: 1px solid white !important;
} */

/* 去掉最下面的那一条线 */
/* ::v-deep .el-dialog .el-table::before {
  border: 1px solid white !important;
} */

/* 去掉表格内的线 */
/* ::v-deep .el-dialog table td {
  border: 1px solid white !important;
} */
::v-deep .el-upload-list {
  display: inline-block;
  margin-left: 10px;
}
::v-deep .upload-file-uploader {
  display: inline-block;
}
::v-deep .vue-treeselect__single-value {
  color: #fff !important;
}
::v-deep .el-link--inner .el-icon-document {
  color: #fff !important;
}
::v-deep .el-input--medium .el-input__inner {
  background: transparent;
  color: #fff;
}
::v-deep .vue-treeselect--single .vue-treeselect__input {
  color: #fff;
}
::v-deep .el-textarea__inner {
  width: 920px;
  margin: auto;
  min-height: 120px !important;
}
#chakan /deep/ .el-dialog__body {
  padding: 30px 64px;
  margin-left: 39px;
}
.shouForm /deep/ .el-input__inner {
  width: 220px;
}

::v-deep .boxtext .el-textarea__inner {
  width: 810px;
}
::v-deep .el-dialog__body {
  margin-left: 53px;
}
::v-deep .el-form .el-table {
  width: 921px;
}
::v-deep .el-dialog__footer {
  margin-right: 47px;
}

::v-deep
  .el-transfer-panel
  .el-transfer-panel__header
  .el-checkbox
  .el-checkbox__label
  span {
  color: #fff;
}
::v-deep .el-transfer-panel__item.el-checkbox {
  color: #fff;
}
::v-deep
  .el-transfer-panel
  .el-transfer-panel__header
  .el-checkbox
  .el-checkbox__label {
  color: #fff;
}
::v-deep
  .el-transfer-panel
  .el-transfer-panel__header
  .el-checkbox
  .el-checkbox__label {
  color: #fff;
}
::v-deep .el-transfer-panel {
  background: rgba(255, 255, 255, 0.2);
}
::v-deep .el-transfer-panel .el-transfer-panel__header {
  background: rgba(255, 255, 255, 0.2);
}
::v-deep .el-transfer-panel .el-transfer-panel__empty {
  color: #fff;
}
::v-deep .boxs .el-dialog__body {
  margin-left: 59px;
}
</style>
