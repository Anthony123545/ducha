<template>
  <div style="padding: 10px">
    <!-- 搜索栏 -->
    <div style="height: 50px; display: flex; align-items: center; gap: 10px">
      <el-input
        v-model="inputPlateNumber"
        placeholder="输入车牌号码"
        size="small"
        style="width: 150px"
        clearable
        @keyup.enter.native="handleSearch"
      />
      <el-select
        v-model="inputPlateType"
        placeholder="请选择车牌类型"
        size="small"
        style="width: 150px"
        @change="handleSearch"
        filterable
        clearable
        class="full-width-select"
      >
        <el-option label="普通车牌" value="normal" />
        <el-option label="临时车牌" value="temporary" />
        <el-option label="新能源车牌" value="newEnergy" />
        <el-option label="军车牌" value="military" />
        <el-option label="警车牌" value="police" />
        <el-option label="教练车牌" value="coach" />
        <el-option label="挂车牌" value="trailer" />
        <el-option label="领馆车牌" value="consulate" />
        <el-option label="港澳车牌" value="hkMacao" />
        <el-option label="应急车牌" value="emergency" />
        <el-option label="其他" value="other" />
      </el-select>
      <el-select
        v-model="inputVehicleType"
        placeholder="请选择车辆类型"
        size="small"
        style="width: 150px"
        @change="handleSearch"
        filterable
        clearable
        class="full-width-select"
      >
        <el-option label="小型车" value="small" />
        <el-option label="大型车" value="large" />
        <el-option label="中型车" value="medium" />
        <el-option label="电动车" value="electric" />
        <el-option label="摩托车" value="motorcycle" />
        <el-option label="货车" value="truck" />
        <el-option label="客车" value="bus" />
        <el-option label="特种车辆" value="special" />
        <el-option label="其他" value="other" />
      </el-select>
      <el-select
        v-model="inputVehicleBrand"
        placeholder="请选择或输入品牌"
        size="small"
        style="width: 150px"
        @change="handleSearch"
        filterable
        allow-create
        clearable
        default-first-option
        class="full-width-select"
      >
        <el-option label="丰田" value="Toyota" />
        <el-option label="本田" value="Honda" />
        <el-option label="日产" value="Nissan" />
        <el-option label="大众" value="Volkswagen" />
        <el-option label="宝马" value="BMW" />
        <el-option label="奔驰" value="Mercedes-Benz" />
        <el-option label="奥迪" value="Audi" />
        <el-option label="比亚迪" value="BYD" />
        <el-option label="特斯拉" value="Tesla" />
        <el-option label="福特" value="Ford" />
        <el-option label="长安" value="Changan" />
        <el-option label="吉利" value="Geely" />
        <el-option label="五菱" value="Wuling" />
        <el-option label="其他" value="other" />
      </el-select>

      <el-button
        type="primary"
        @click="handleSearch()"
        style="margin-left: 10px"
        >查询</el-button
      >
      <el-button type="primary" @click="handleAdd()" style="margin-left: 10px"
        >新增</el-button
      ><el-button
        :disabled="multipleSelection.length <= 0"
        type="danger"
        @click="handleBatchDelete()"
        style="margin-left: 10px"
        >批量删除</el-button
      >
    </div>

    <el-table
      v-loading="loading"
      :data="tableData"
      style="width: 100%; margin-top: 10px"
      border
      :header-cell-style="changeHeaderCellStyle"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" min-width="50" align="center" sortable>
        <template #default="scope">
          {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        prop="plateNumber"
        label="车牌号码"
        min-width="120"
        align="center"
        sortable
      />
      <!--      <el-table-column prop="cardNo" label="卡号" min-width="100"/>-->
      <!--      <el-table-column prop="cardType" label="卡片类型" min-width="100"/>-->
      <!--      <el-table-column prop="cardFee" label="工本费(元)" min-width="100"/>-->
      <!--      <el-table-column prop="balance" label="余额(元)" min-width="100"/>-->
      <!--      <el-table-column prop="startTime" label="开始时间" min-width="160"/>-->
      <!--      <el-table-column prop="endTime" label="截止时间" min-width="160"/>-->
      <!--      <el-table-column prop="registerTime" label="注册时间" min-width="160"/>-->
      <!--      <el-table-column prop="department" label="发卡部门" min-width="120"/>-->
      <!--      <el-table-column prop="cardStatus" label="卡片状态" min-width="100"/>-->
      <!--      <el-table-column prop="issuer" label="发卡人员" min-width="120"/>-->
      <el-table-column
        prop="parkingType"
        label="停车类型"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="plateColor"
        label="车牌颜色"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="vehicleType"
        label="车辆类型"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="plateType"
        label="车牌类型"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="vehicleColor"
        label="车辆颜色"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="vehicleBrand"
        label="车辆品牌"
        min-width="100"
        align="center"
        sortable
      />
      <el-table-column
        prop="ownerName"
        label="车主姓名"
        min-width="120"
        align="center"
        sortable
      />
      <el-table-column
        prop="phoneNumber"
        label="手机号码"
        min-width="120"
        align="center"
        sortable
      />
      <el-table-column
        prop="certificateNo"
        label="证件号码"
        min-width="120"
        align="center"
        sortable
      />
      <!--      <el-table-column prop="address" label="住址" min-width="200"/>-->
      <!--      <el-table-column prop="position" label="职位" min-width="120"/>-->
      <!--      <el-table-column prop="company" label="所属公司" min-width="150"/>-->
      <!--      <el-table-column prop="departmentName" label="所在部门" min-width="150"/>-->
      <!--      <el-table-column prop="cardIssuerType" label="发卡器类型" min-width="120"/>-->
      <!--      <el-table-column prop="groupName" label="分组名称" min-width="120"/>-->
      <!--      <el-table-column prop="parkingPermission" label="场库权限" min-width="120"/>-->
      <!--      <el-table-column prop="parkingLotName" label="场库名称" min-width="120"/>-->
      <!--      <el-table-column prop="vehicleGroup" label="车辆群组" min-width="120"/>-->
      <el-table-column
        prop="dailyTimeRange"
        label="每日有效时段"
        min-width="120"
        align="center"
        sortable
      />
      <el-table-column
        prop="historicalValidity"
        label="历史有效期"
        min-width="120"
        align="center"
        sortable
      />
      <el-table-column
        prop="remarks"
        label="备注"
        min-width="100"
        align="center"
        sortable
      />
      <!--      <el-table-column prop="dataVersion" label="数据版本" min-width="100"/>-->
      <!--      <el-table-column prop="packageInfo" label="包时信息" min-width="120"/>-->
      <!--      <el-table-column prop="engineNo" label="发动机号" min-width="120"/>-->
      <!--      <el-table-column prop="emissionNo" label="排放量号" min-width="120"/>-->
      <!--      <el-table-column prop="recognitionCode" label="识别代码" min-width="120"/>-->
      <!--      <el-table-column prop="vin" label="车架号" min-width="150"/>-->
      <!--      <el-table-column prop="displacement" label="车辆排量" min-width="120"/>-->
      <!--      <el-table-column prop="fleetName" label="车队名称" min-width="150"/>-->
      <el-table-column
        label="操作"
        fixed="right"
        width="250"
        align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleView(scope.row)"
            >查看</el-button
          >
          <el-button type="primary" size="small" @click="handleEdit(scope.row)"
            >修改</el-button
          >
          <el-button type="danger" size="small" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      class="custom-pagination"
      style="margin-top: 20px; text-align: right"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      :page-sizes="[10, 15, 20, 25, 30]"
      layout="total, prev, pager, next, jumper, sizes"
      @current-change="handlePageChange"
      @size-change="handleSizeChange"
    />
    <!-- 查看详情弹窗 -->
    <car-detail-dialog
      :visible.sync="viewVisible"
      :detail="currentCar" />
    <car-edit-dialog
      :visible.sync="editVisible"
      :carData="currentCar"
      @submit="handleCarSubmit"
      @cancel="handleCarCancel"
    />
  </div>
</template>

<script>
import request from "@/utils/request";
import CarDetailDialog from "./CarDetailDialog.vue";
import CarEditDialog from "./CarEditDialog.vue";

export default {
  name: "carManage",
  components: {
    CarDetailDialog,
    CarEditDialog,
  },
  data() {
    return {
      inputPlateNumber: null,
      inputPlateType: null,
      inputVehicleType: null,
      inputVehicleBrand: null,

      searchVisible: false,
      viewVisible: false,
      editVisible: false,
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,

      multipleSelection: [], // 存储选中的多行数据
      currentCar: null,
      loading: false,
    };
  },
  mounted() {
    this.handleSearch();
  },
  methods: {
    async handleSearch() {
      this.loading = true;
      try {
        const params = {
          plateNumber: this.inputPlateNumber,
          plateType: this.inputPlateType,
          vehicleType: this.inputVehicleType,
          vehicleBrand: this.inputVehicleBrand,
          page: this.currentPage,
          size: this.pageSize,
        };
        console.log("查询参数:", params);
        const res = await request({
          url: "/car/list",
          method: "POST",
          data: params,
        });
        console.log("查询结果:", res);
        if (res && res.list) {
          this.tableData = res.list;
          this.total = res.total;
        } else {
          this.tableData = [];
          this.total = 0;
          this.$message.warning("未获取到数据");
        }
        console.log("查询结果:this.tableData", this.tableData);
      } finally {
        this.loading = false;
      }
    },
    async handleCarSubmit(carData) {
      console.log("提交参数:", carData);
      const url = carData.id ? "/car/update" : "/car/add";
      const res = await request({ url, method: "POST", data: carData });
      console.log("提交结果:", res);
      this.editVisible = false;
      await this.$message(res.message);
      await this.handleSearch();
    },
    handleCarCancel() {
      this.editVisible = false;
    },
    handleView(row) {
      this.currentCar = row;
      this.viewVisible = true;
    },
    handleAdd() {
      console.log("新增");
      this.currentCar = {};
      this.editVisible = true;
    },
    handleEdit(row) {
      console.log("修改", row);
      this.currentCar = row;
      this.editVisible = true;
    },
    // 切换页码
    handlePageChange(page) {
      this.currentPage = page;
      this.handleSearch();
    },
    // 修改每页条数
    handleSizeChange(size) {
      this.pageSize = size;
      this.currentPage = 1; // 切换条数后回到第一页
      this.handleSearch();
    },
    // 表格样式修改
    changeHeaderCellStyle(row, column, rowIndex, columnIndex) {
      if (row.columnIndex === 0) {
        return "background: #004279 ; color:#fff;"; // 修改的样式
      } else {
        return "background: #004279 ;color:#fff; ";
      }
    },
    // 监听多选变化
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 单条删除
    handleDelete(row) {
      this.$confirm(`确定要删除设备号为【${row.id}】的车辆吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          try {
            this.loading = true;
            const res = await request({
              url: `/car/delete/${row.id}`,
              method: "DELETE",
            });
            console.log("删除结果：", res);
            if (res.status) {
              this.$message.success(res.message || "删除成功");
              this.getDeviceInfo();
            } else {
              this.$message.error(res.message || "删除失败");
            }
          } catch (error) {
            console.error("删除失败:", error);
            this.$message.error("删除车辆失败，请稍后重试");
          } finally {
            this.loading = false;
          }
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 批量删除
    handleBatchDelete() {
      const ids = this.multipleSelection.map((item) => item.id);
      if (ids.length === 0) {
        this.$message.warning("请先选择要删除的车辆");
        return;
      }
      this.$confirm(`确定要删除选中的 ${ids.length} 个车辆吗？`, "批量删除确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          try {
            this.loading = true;
            const res = await request({
              url: "/car/deleteBatch",
              method: "POST",
              data: ids, // ✅ body是数组，例如 [1,2,3]
            });
            console.log("批量删除结果：", res);
            if (res.status) {
              this.$message.success(res.message || "批量删除成功");
              this.getDeviceInfo();
            } else {
              this.$message.error(res.message || "批量删除失败");
            }
          } catch (error) {
            console.error("批量删除失败:", error);
            this.$message.error("批量删除请求出错，请稍后重试");
          } finally {
            this.loading = false;
          }
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
  },
};
</script>
