<template>
  <div class="app-container">
    <!-- 查询条件 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!-- 单位 -->
      <el-form-item label="单位" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位"
          clearable
          @input="handleQuery"
        />
      </el-form-item>

      <!-- 出车时间（只到天） -->
      <el-form-item label="出车时间" prop="outtime">
        <el-date-picker
          v-model="queryParams.outtime"
          type="date"
          placeholder="请选择出车日期"
          value-format="yyyy-MM-dd"
          clearable
          @change="handleQuery"
        />
      </el-form-item>

      <!-- 用车人员姓名 -->
      <el-form-item label="用车人员" prop="peopleName">
        <el-input
          v-model="queryParams.peopleName"
          placeholder="请输入用车人员姓名"
          clearable
          @input="handleQuery"
        />
      </el-form-item>

      <!-- 流程（正常/特殊） -->
      <el-form-item label="流程" prop="isTeshuYongChe">
        <el-select
          v-model="queryParams.isTeshuYongChe"
          placeholder="请选择流程"
          clearable
          @change="handleQuery"
        >
          <el-option label="正常" value="0" />
          <el-option label="特殊" value="1" />
        </el-select>
      </el-form-item>

      <!-- 是否归队 -->
      <el-form-item label="是否归队" prop="placeState">
        <el-select
          v-model="queryParams.placeState"
          placeholder="请选择是否归队"
          clearable
          @change="handleQuery"
        >
          <el-option label="已归队" value="1" />
          <el-option label="未归队" value="2" />
        </el-select>
      </el-form-item>

      <!-- 按钮组：重置 + 新增 + 导出 -->
      <el-form-item>
        <div class="btn-group">
          <el-button size="btr" @click="resetQuery">重置</el-button>

<!--          <el-button-->
<!--            type="primary"-->
<!--            plain-->
<!--            icon="el-icon-plus"-->
<!--            size="mini"-->
<!--            @click="handleAdds"-->
<!--            v-hasPermi="['militaryvehicleManagement:thebusApply:add']"-->
<!--          >新增</el-button>-->

          <el-button
            class="box"
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            :loading="exportLoading"
            @click="handleExport"
            v-hasPermi="['militaryvehicleManagement:thebusApply:export']"
          >导出</el-button>
        </div>
      </el-form-item>
    </el-form>

    <hr />

    <!-- 列表选项 -->
    <el-table
      v-loading="loading"
      :data="thebusApplyList"
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
      <!--      <el-table-column-->
      <!--        label="车辆型号"-->
      <!--        align="center"-->
      <!--        prop="vehiclecolor"-->
      <!--        :formatter="vehiclecolor"-->
      <!--      />-->
      <el-table-column
        label="车辆型号"
        align="center"
        prop="model"
        :formatter="vehicleModel"
      />
      <el-table-column label="用车人员单位" align="center" prop="unitName" />
      <el-table-column label="用车人员姓名" align="center" prop="peopleName" />
      <el-table-column label="联系方式" align="center" prop="contact" />
      <!-- <el-table-column label="事由" align="center" prop="cause" /> -->
      <el-table-column label="出车路线" align="center" prop="route" />
      <el-table-column label="出车状态" align="center" prop="">
        <template slot-scope="scope">
          <el-col v-if="scope.row.outstatus == 0" style="color: #ffba00"
          >未出车</el-col>
          <el-col v-if="scope.row.outstatus == 1" style="color: #13ce66"
          >已出车</el-col>
        </template>
      </el-table-column>
      <el-table-column
        label="出车时间"
        align="center"
        prop="outtime"
        width="200"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.outtime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="流程" align="center" prop="outtime">
        <template slot-scope="scope">
          <span v-if="scope.row.isTeshuYongChe == '0'" style="color: #ffba00"
          >正常</span>
          <span
            v-if="scope.row.isTeshuYongChe == '1'"
            style="color: #13ce66"
            :title="scope.row.bdglDepartureYuanyin"
          >特殊</span>
        </template>
      </el-table-column>
      <el-table-column label="是否归队" align="center" prop="placeState">
        <template slot-scope="scope">
          <span v-if="scope.row.placeState == '1'" style="color: #13ce66"
          >已归队</span>
          <span v-if="scope.row.placeState != '1'" style="color: red"
          >未归队</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.outstatus == 0"
            size="btu"
            type="text"
            @click="handleAdd(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
          >出车</el-button>
          <el-button
            class="a1"
            v-else
            size=""
            type="text"
            @click="singles(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
          >查看</el-button>
          <el-button
            v-if="scope.row.placeState == 2"
            class="a1"
            size=""
            type="text"
            @click="returnCar(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
          >归队</el-button>
          <!-- 修改按钮 -->
          <el-button
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改</el-button>

          <!-- 删除按钮 -->
          <el-button
            size="mini"
            type="text"
            style="color: red"
            @click="handleDelete(scope.row)"
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

    <!-- 新增出车会话框 -->
    <el-dialog
      :title="title"
      :visible.sync="openes"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form1"
        :model="fomr1"
        :rules="rules1"
        label-width="80px"
        inline
      >
        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="fomr1.unitId"
            :options="treeList"
            placeholder="选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="人数" prop="peopleCount">
          <el-input v-model="fomr1.peopleCount" placeholder="请输入人数" />
        </el-form-item>
        <el-form-item label="申请人员" prop="peopleId">
          <el-select
            v-model="fomr1.peopleId"
            placeholder="请选择申请人员"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="cheRen(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="带车人员" prop="pepelonamed">
          <el-select
            v-model="fomr1.pepelonamed"
            placeholder="请选择带车人员"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in daiList"
              :key="i"
              :label="item.name"
              :value="item.name"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="contact">
          <el-input v-model="fomr1.contact" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="外出事由" prop="cause">
          <el-input v-model="fomr1.cause" placeholder="请输入外出事由" />
        </el-form-item>
        <el-form-item label="用车开始时间" prop="authStime">
          <el-date-picker
            clearable
            size="small"
            v-model="fomr1.authStime"
            type="datetime"
            placeholder="用车开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="用车结束时间" prop="authEtime">
          <el-date-picker
            clearable
            size="small"
            v-model="fomr1.authEtime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="用车结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="车辆类别" prop="vehicleType">
          <el-select v-model="form.vehicleType" placeholder="请选择车辆类别" @change="handleVehicleTypeChange">
            <el-option label="私家车" value="PRIVATE" class="input_xiala"></el-option>
            <el-option label="军车" value="MILITARY" class="input_xiala"></el-option>
          </el-select>
        </el-form-item>
        <div class="box3">
          <el-form v-for="(item1, e) in checkShops" :key="e">
            <el-form-item label="" prop="bdglThebusId">
              <el-select
                v-model="item1.bdglThebusId"
                placeholder="请选择车辆车牌"
                filterable
              >
                <el-option
                  v-for="(item, i) in chucarlist"
                  :key="i"
                  :label="item.vehiclenumber"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="twoshenpirenid">
              <el-select
                v-model="item1.driverId"
                placeholder="请选择司机"
                filterable
              >
                <el-option
                  v-for="(item, i) in driverList"
                  :key="i"
                  :label="item.peopleName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                icon="el-icon-plus"
                size="btc"
                @click="addFormss"
                v-if="e == checkShop.length - 1"
              ></el-button>
              <el-button
                type="primary"
                icon="el-icon-delete"
                size="btd"
                v-if="e > 0"
                @click="delFormss(e)"
              ></el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="jiben">出车路线</div>
        <el-form-item prop="route">
          <el-input
            v-model="fomr1.route"
            type="textarea"
            placeholder="请输入出车路线"
          />
        </el-form-item>
        <div class="jiben">出车原因</div>
        <el-form-item prop="bdglDepartureYuanyin">
          <el-input
            v-model="fomr1.bdglDepartureYuanyin"
            type="textarea"
            placeholder="请输入出车原因"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 出车会话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        inline
      >
        <el-form-item label="单位" prop="unitId">
          <el-input
            placeholder="选择单位"
            v-model="form.unitName"
            readonly
          ></el-input>
        </el-form-item>
        <el-form-item label="申请人员" prop="peopleId">
          <el-input
            placeholder="请选择申请人员"
            v-model="form.peopleName"
            readonly
          ></el-input>
        </el-form-item>
        <el-form-item label="带车人员" prop="pepelonamed">
          <el-input
            placeholder=""
            v-model="form.pepelonamed"
            readonly
          ></el-input>
        </el-form-item>

        <el-form-item label="人数" prop="peopleCount">
          <el-input
            v-model="form.peopleCount"
            placeholder="请输入人数"
            readonly
          />
        </el-form-item>
        <el-form-item label="联系方式" prop="contact">
          <el-input
            v-model="form.contact"
            placeholder="请输入联系方式"
            readonly
          />
        </el-form-item>
        <el-form-item label="外出事由" prop="cause">
          <el-input
            v-model="form.cause"
            placeholder="请输入外出事由"
            readonly
          />
        </el-form-item>

        <div class="box6">
          <el-form v-for="(item1, e) in chuchEList" :key="e">
            <el-form-item label="指定出车" prop="carUnitId">
              <treeselect
                :options="treeList"
                placeholder="选择出车单位"
                v-model="item1.carUnitId"
                disabled
              />
            </el-form-item>
            <el-form-item label="" prop="bdglThebusId">
              <el-select
                placeholder="请选择车辆车牌"
                filterable
                v-model="item1.bdglThebusId"
                disabled
              >
                <el-option
                  v-for="(item, i) in chePaiList"
                  :key="i"
                  :label="item.vehiclenumber"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="driverId">
              <el-select
                placeholder="请选择司机"
                filterable
                v-model="item1.driverId"
                disabled
              >
                <el-option
                  v-for="(item, i) in driverLists"
                  :key="i"
                  :label="item.peopleName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                icon="el-icon-delete"
                size="btd"
                @click="delForms(e)"
              ></el-button>
            </el-form-item>
          </el-form>
        </div>

        <div class="box3">
          <el-form v-for="(item1, e) in checkShop" :key="e">
            <el-form-item label="" prop="carUnitId">
              <treeselect
                v-model="item1.carUnitId"
                :options="treeList"
                placeholder="选择出车单位"
                @input="treeselectss"
              />
            </el-form-item>
            <el-form-item label="" prop="bdglThebusId">
              <el-select
                v-model="item1.bdglThebusId"
                placeholder="请选择车辆车牌"
                filterable
                clearable
              >
                <el-option
                  v-for="(item, i) in chucarlist"
                  :key="i"
                  :label="item.vehiclenumber"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="driverId">
              <el-select
                v-model="item1.driverId"
                placeholder="请选择司机"
                filterable
              >
                <el-option
                  v-for="(item, i) in driverList"
                  :key="i"
                  :label="item.peopleName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                icon="el-icon-check"
                size="btc"
                @click="addForms"
                v-if="e == checkShop.length - 1"
              ></el-button>
            </el-form-item>
          </el-form>
        </div>

        <div class="jiben">出车路线</div>
        <el-form-item prop="route">
          <el-input
            v-model="form.route"
            type="textarea"
            placeholder="请输入出车路线"
            disabled
          />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="route">
          <el-input v-model="form.remarkInfo" type="textarea" placeholder="" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看会话框 -->
    <el-dialog
      title="查看可出车列表"
      :visible.sync="opens"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="forms"
        :rules="rules"
        label-width="80px"
        inline
      >
        <el-form-item label="单位" prop="unitId">
          <el-input v-model="forms.unitName" disabled></el-input>
        </el-form-item>
        <el-form-item label="申请人员" prop="peopleId">
          <el-input v-model="forms.peopleName" disabled></el-input>
        </el-form-item>
        <el-form-item label="带车人员" prop="peopleId">
          <el-input v-model="forms.pepelonamed" disabled></el-input>
        </el-form-item>
        <el-form-item label="人数" prop="peopleCount">
          <el-input v-model="forms.peopleCount" disabled />
        </el-form-item>
        <el-form-item label="联系方式" prop="contact">
          <el-input v-model="forms.contact" disabled />
        </el-form-item>
        <el-form-item label="外出事由" prop="cause">
          <el-input v-model="forms.cause" disabled />
        </el-form-item>
        <el-form-item label="出车原因" prop="bdglDepartureYuanyin">
          <el-input type="textarea" v-model="forms.bdglDepartureYuanyin" disabled />
        </el-form-item>
        <div class="box4">
          <table
            border="1"
            style="
              border-collapse: collapse;
              border: none;
              mso-border-left-alt: 0.5pt solid windowtext;
              mso-border-top-alt: 0.5pt solid windowtext;
              mso-border-right-alt: 0.5pt solid windowtext;
              mso-border-bottom-alt: 0.5pt solid windowtext;
              mso-border-insideh: 0.5pt solid windowtext;
              mso-border-insidev: 0.5pt solid windowtext;
              mso-padding-alt: 0pt 0pt 0pt 0pt;
            "
            width="907px"
          >
            <tbody style="">
              <tr align="center" class="tr">
                <td width="220" height="40">车牌型号</td>
                <td width="220" height="40">号牌号码</td>
                <td width="220" height="40">司机</td>
                <td width="220" height="40">司机联系方式</td>
              </tr>
              <tr
                align="center"
                class="tr"
                v-for="(item, i) in chaCarLists"
                :key="i"
              >
                <td width="220" height="40" class="tds">
                  <el-select
                    v-model="item.vehiclemodel"
                    placeholder=""
                    disabled
                  >
                    <el-option
                      v-for="(item1, i) in brand"
                      :key="i"
                      :label="item1.dictLabel"
                      :value="item1.dictValue"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td width="220" height="40">{{ item.vehiclenumber }}</td>
                <td width="220" height="40">{{ item.driverName }}</td>
                <td width="220" height="40">{{ item.driverPhone }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  getThebusApply,
  delThebusApply,
  exportThebusApply,
  exportThebusCar,
  exportThebusCha,
  zaiweiCar,
  chuCarList,
  addKeChuChe,
  updateKeChuChe,
  sb,
  guiDui,
  delThebus, exportThebusAvailableList
} from "@/api/militaryvehicleManagement/thebusApply";
// 查询字典
import { getDicts } from "@/api/system/dict/data";
import { listDriver } from "@/api/militaryvehicleManagement/driver";
import { getAll } from "@/api/grassrootsregistration/bdglmeeting";
import { getAllDept } from "@/api/peopleChuRu/bdglLeave";

// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

import {
  getZhuChiRen,
  getDept,
  listThebus,
} from "@/api/militaryvehicleManagement/thebus";
import {parseTime} from "../../../utils/supervision";
import {listPrivateCar, zaiweiPrivateCar} from "@/api/militaryvehicleManagement/privateCar";

export default {
  name: "ThebusList",
  dicts: ["brand"],
  components: {
    Treeselect,
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
      // 用车申请表格数据
      thebusApplyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        chaxunType: "0",          // 默认为“所有”，现在不再在界面展示
        unitName: undefined,      // 单位
        outtime: undefined,       // 出车时间
        peopleName: undefined,    // 用车人员姓名
        isTeshuYongChe: undefined,// 流程
        placeState: undefined     // 是否归队
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 人员列表
      renYuan: [],
      //出车对话框司机
      checkShop: [
        {
          bdglThebusId: null,
          driverId: null,
          carUnitId: null,
        },
      ],
      // 新增出车对话框
      checkShops: [
        {
          bdglThebusId: null,
          driverId: null,
        },
      ],
      // 车牌型号
      brand: [],
      // 司机
      driverList: [],
      // 查看
      forms: {},
      // 查看框显示
      opens: false,
      // 外面的人员
      renYuans: [],
      // 查看司机，车辆列表
      chaCarLists: [],
      // 出车获取车辆列表
      chucarlist: [],
      // 车牌型号的保存
      names: "",
      // 新增弹出层的显示与隐藏
      openes: false,
      // 树形列表
      treeList: [],
      // 新增参数
      fomr1: {},
      arr: [],
      rules1: {
        bdglDepartureYuanyin: [
          { required: true, message: "请填写原因", trigger: "blur" },
        ],
      },
      // 出车数据
      chuchEList: [],
      // 出车获取车辆列表
      driverLists: [],
      // 出车获取车辆列表
      chePaiList: [],
      // 带车人员列表
      daiList: [],
    };
  },
  created() {
    this.getList();
    this.getSuo();
    getDicts("brand").then((response) => {
      this.brand = response.data;
    });
    // this.getCar;
    this.siJI();
    this.chePai();
  },
  methods: {
    parseTime,
    handleVehicleTypeChange(){
      this.getCar();
    },
    /** 修改按钮 **/
    // handleUpdate(row) {
    //   // 先把表单重置
    //   // this.resetForm("form");
    //   // 打开弹窗
    //   this.openes = true;
    //   this.title = "修改出车记录";
    //   // 回显数据
    //   // this.form = Object.assign({}, row);
    //   this.fomr1 = { ...row };
    // },

    /** 删除按钮 **/
    handleDelete(row) {
      this.$modal.confirm('确定要删除这条出车记录吗？')
        .then(() => {
          return delThebus(row.id);   // 这里调用后端删除接口
        })
        .then(() => {
          this.$modal.msgSuccess("删除成功");
          this.getList(); // 重新加载列表
        })
        .catch(() => {});
    },

    // 获取带车人信息
    getDaiAll() {
      getAll().then((res) => {
        this.daiList = res.data;
      });
    },
    // 获取车牌号翻译
    chePai() {
      // listThebus().then((res) => {
      //   this.chePaiList = res.rows;
      // });
      const api = this.form.vehicleType === "PRIVATE"
        ? listPrivateCar
        : listThebus;
      api().then(res => {
        this.chePaiList = res.data || res.data || [];
      });
    },
    // 获取司机翻译
    siJI() {
      listDriver().then((res) => {
        this.driverLists = res.rows;
      });
    },
    // 选择出车单位触发
    treeselectss(i) {
      this.getSi();
      this.getCar();
    },
    // 归队按钮触发
    returnCar(row) {
      this.form = row;
      console.log("form22",this.form);
      this.$confirm("是否确认归队", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.form.placeState = "1";
          guiDui(this.form).then((res) => {
            this.$modal.msgSuccess("归队成功");
            this.getList();
          });
        })
        .catch(() => {});
    },
    // 获取人
    getRen(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 选择人员触发
    cheRen(name) {
      this.fomr1.peopleName = name;
    },
    // 选择部门单位触发
    selectPeo(data) {
      this.fomr1.unitName = data.label;
      this.getRen(data.id);
    },
    //  获取单位列表
    getTreeList() {
      getAllDept().then((res) => {
        this.treeList = res.data;
      });
    },
    // 今日，明日按钮触发
    caoZuoTypefun(data) {
      if (data == "0") {
        this.getList();
      } else if (data == "1") {
        this.queryParams.chaxunType = "1";
        this.getList();
      } else if (data == "2") {
        this.queryParams.chaxunType = "2";
        this.getList();
      }
    },
    // 翻译车牌型号
    car(value) {
      this.brand.map((item) => {
        if (item.dictValue == value) {
          this.names = item.dictLabel;
        }
      });
    },
    // 翻译车信息
    vehiclecolor(row) {
      var vehiclecolor = "";
      this.brand.map((item) => {
        if (row.bdglDepartureInfos) {
          row.bdglDepartureInfos.forEach((items, index) => {
            if (item.dictValue == items.vehiclemodel) {
              vehiclecolor += item.dictLabel + " ";
            }
          });
        } else {
          vehiclecolor = "";
        }
      });
      return vehiclecolor;
    },
    // 翻译车辆型号
    vehicleModel(row, column) {
      var model = "";
      this.brand.map((item) => {
        if (item.dictValue == row.model) {
          model = item.dictLabel;
        }
      });
      return model;
    },
    // 获取搜索所有人
    getSuo() {
      getAll().then((res) => {
        this.renYuans = res.rows;
      });
    },
    // 查看
    singles(row) {
      let id = row.id;
      exportThebusCha(id).then((res) => {
        this.forms = res.data;
        this.opens = true;
        this.chaCarLists = res.data.bdglDepartureInfos;
      });
    },
    // 获取司机
    getSi(id) {
      listDriver({ unitId: id }).then((response) => {
        this.driverList = response.rows;
      });
    },
    // 获取在位车辆型号
    getCar(id) {
      // zaiweiCar({ unitId: id }).then((res) => {
      //   this.chucarlist = res.rows;
      // });
      console.log("form3", this.form);
      const api = this.form.vehicleType === "PRIVATE"
        ? zaiweiPrivateCar
        : zaiweiCar;

      api({ unitId: id }).then(res => {
        this.chucarlist = res.rows || res.rows || [];
      });
    },

    // 删除出车对话框选择司机
    delForms(i) {
      this.chuchEList.splice(i, 1);
    },
    // 删除出车对话框选择司机
    delFormss(i) {
      this.checkShops.splice(i, 1);
    },

    // 出车对话框选择司机
    addForms() {
      this.chuchEList.push(this.checkShop[0]);
      this.checkShop = [
        {
          driverId: null,
          bdglThebusId: null,
          carUnitId: null,
        },
      ];
    },
    // 增加对话框选择司机
    addFormss() {
      this.checkShops.push({
        driverId: null,
        bdglThebusId: null,
      });
    },
    /** 查询出车申请列表 */
    getList() {
      this.loading = true;
      chuCarList(this.queryParams).then((response) => {
        this.thebusApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openes = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form.remarkInfo = null;
      this.form = {};
      this.form1 = {};
      this.checkShop = [
        {
          bdglThebusId: null,
          driverId: null,
          carUnitId: null,
        },
      ];
      this.chuchEList = [];
      this.resetForm("form");
      this.resetForm("form1");
      this.resetForm("checkShop");
      this.resetForm("chuchEList");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.unitName = undefined;
      this.queryParams.outtime = undefined;
      this.queryParams.peopleName = undefined;
      this.queryParams.isTeshuYongChe = undefined;
      this.queryParams.placeState = undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 出车按钮操作 */
    handleAdd(row) {
      this.form.id = row.id;
      this.form = row;
      console.log("row", row);
      console.log("form", this.form);
      this.getCar();
      this.getSi();
      this.getTreeList();
      this.open = true;
      this.title = "出车确认";
    },
    /** 新增按钮操作 */
    handleAdds() {
      this.reset();
      this.getCar();
      this.getSi();
      this.getDaiAll();
      this.openes = true;
      this.title = "新增可出车列表";
      var today = new Date();
      var y = today.getFullYear();
      var M = today.getMonth() + 1;
      var d = today.getDate();
      var h = today.getHours();
      var m = today.getMinutes();
      var s = today.getSeconds();
      if (M < 10) {
        M = "0" + M;
      }
      if (d < 10) {
        d = "0" + d;
      }
      if (h < 10) {
        h = "0" + h;
      }
      if (m < 10) {
        m = "0" + m;
      }
      if (s < 10) {
        s = "0" + s;
      }
      // this.fomr1.authStime =
      //   y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
      // this.fomr1.authEtime =
      //   y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
      this.getTreeList();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeList();
      const id = row.id || this.ids;
      getThebusApply(id).then((response) => {
        this.fomr1 = { ...this.fomr1, ...response.data };
        this.getRen(this.form.unitId);
        this.openes = true;
        this.title = "修改可出车列表";
      });
    },
    /** 出车提交按钮 */
    submitForm() {
      if (this.chuchEList.length) {
        this.form.bdglDepartureInfos = this.chuchEList;
        this.chuchEList.forEach((item) => {
          this.arr.push(item.bdglThebusId);
        });
      }
      if (this.arr.length) {
        this.form.thebusId = this.arr.join(",");
      }
      this.form.outstatus = "1";
      exportThebusCar(this.form).then((response) => {
        this.$modal.msgSuccess("新增成功");
        this.open = false;
        this.getList();
        this.arr = [];
      });
    },
    // 新增提交按钮
    submitForm1() {
      // this.$refs["form1"].validate((valid) => {
      //   if (valid) {
      //     if (this.checkShop.length) {
      //       this.fomr1.bdglDepartureInfos = this.checkShops;
      //       this.checkShops.forEach((item) => {
      //         this.arr.push(item.bdglThebusId);
      //       });
      //     }
      //     if (this.arr.length) {
      //       this.fomr1.thebusId = this.arr.join(",");
      //     }
      //     this.fomr1.outstatus = "1";
      //     addKeChuChe(this.fomr1).then((response) => {
      //       this.$modal.msgSuccess("新增成功");
      //       this.openes = false;
      //       this.getList();
      //       this.arr = [];
      //     });
      //   }
      // });
      this.$refs["form1"].validate(valid => {
        if (!valid) return;
        if (this.fomr1.id) {
          // 有 id 表示是修改
          updateKeChuChe(this.fomr1).then(res => {
            this.$modal.msgSuccess("修改成功");
            this.openes = false;
            this.getList();
          });
        } else {
          // 无 id 表示是新增
          addKeChuChe(this.fomr1).then(res => {
            this.$modal.msgSuccess("新增成功");
            this.openes = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    // handleDelete(row) {
    //   const ids = row.id || this.ids;
    //   this.$modal
    //     .confirm("是否确认删除可出车列表的数据项？")
    //     .then(function () {
    //       return delThebusApply(ids);
    //     })
    //     .then(() => {
    //       this.getList();
    //       this.$modal.msgSuccess("删除成功");
    //     })
    //     .catch(() => {});
    // },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有可出车列表数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportThebusAvailableList(queryParams);
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
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
  width: 800px !important;
}
::v-deep .el-textarea__inner {
  width: 920px;
  height: 104px;
  margin: auto;
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
::v-deep .el-input__inner {
  /* width: 200px !important;
  height: 36px; */
  background: transparent;
  color: #fff;
  border: 1px solid white !important;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 基本信息背景 */
.jiben {
  width: 920px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
::v-deep .el-input__inner {
  cursor: pointer !important;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 30px 50px;
}
::v-deep .el-dialog__body {
  margin: 10px 24px 20px 30px;
  padding-top: 20px !important;
  box-sizing: border-box;
  /* padding: 30px 12px 30px 28px; */
}
.contents {
  padding: 0px 40px !important;
}
/* 下拉菜单 */
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
/* 下拉菜单字体/背景颜色 */
.el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #004d86;
  color: #111;
}
.el-select-dropdown__item {
  color: #111;
}
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
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
  color: #fff;
}
/* 单位下拉菜单选中字体颜色 */
::v-deep .vue-treeselect__single-value {
  color: #fff !important;
}
/* 分页按钮 */
::v-deep .el-pagination.is-background .el-pager li {
  background-color: #004d86;
  color: #fff;
}
::v-deep .el-pagination.is-background .btn-next {
  background-color: #004d86;
  color: #fff;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 50px;
  margin-right: 28px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}
::v-deep .el-dialog__body {
  padding: 30px 35px;
}
/* 小手样式 */
::v-deep .el-table__cell {
  cursor: pointer;
}
.el-button--mini {
  width: 80px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #1890ff;
}
.box3 {
  width: 920px;
  min-height: 70px;
  border: 1px solid white;
  border-radius: 5px;
  /* background: #409eff; */
  margin-bottom: 30px;
  padding-top: 15px;
  padding-left: 15px;
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
/* 查看表格 */
.box4 {
  width: 923px;
  min-height: 70px;
  border-radius: 5px;
  /* background: #409eff; */
  margin-bottom: 30px;
  padding-top: 15px;
  padding-left: 15px;
}
.box4 tr {
  color: #fff;
}
.box4 .tr td {
  border-bottom: 1px solid #fff;
}
.box4 table {
  border-color: #fff;
}
::v-deep .box4 table td {
  border-bottom: 1px solid white !important;
}
.a1 {
  width: 46px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #1890ff;
}
/* 今日明日按钮 */
.isSubmit {
  margin-left: 42% !important;
}
.el-radio-button {
  width: 122px !important;
  height: 30px !important;
  line-height: 30px !important;
  border: none !important;
}
::v-deep .el-radio-button__inner {
  width: 120px !important;
  height: 30px !important;
  line-height: 8px !important;
  background: #196299;
  border: none;
  color: #fff;
}
::v-deep .el-radio-button:first-child .el-radio-button__inner {
  border-left: none !important;
}
::v-deep .el-radio-button__orig-radio:checked + .el-radio-button__inner {
  color: #ffffff;
  background-color: #006cff !important;
  border-color: #006cff !important;
  -webkit-box-shadow: -1px 0 0 0 #006cff !important;
  box-shadow: -1px 0 0 0 #006cff !important;
}
hr {
  border: none;
  height: 1px;
  background-color: rgba(204, 204, 204, 0.5);
}
::v-deep .tds .el-input__inner {
  border: none !important;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background: transparent;
  color: #fff;
}
::v-deep .box6 .el-form .el-form-item {
  margin-right: 9px !important ;
}

::v-deep .is-disabled .el-textarea__inner {
  width: 820px;
  height: 104px;
  margin: auto;
}
.box {
  background-color: #ffba00 !important;
}
.btn-group {
  display: flex;
  align-items: center;
}

.btn-group .el-button + .el-button {
  margin-left: 8px;
}
</style>

