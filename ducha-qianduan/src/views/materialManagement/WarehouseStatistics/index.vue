<template>
  <div class="app-container">
    <div class="hezi">
      <div class="tji">库房统计</div>
      <div style="display: flex; flex-wrap: wrap; padding: 11px 20px">
        <div
          style="width: 25%; display: flex; align-items: center; height: 100px"
          v-for="item in tongJiList"
          :key="item.name"
        >
          <div
            class="div-l"
            style="flex: 1.5; text-align: left; margin-left:20px; margin-right: 30px"
          >
            <span>{{ item.name }}:</span>
          </div>
          <div class="div-r" style="flex: 2; text-align: left; margin-right: 120px">
            <span>
              <span style="font-size: 25px; margin-right: 18px">{{ item.num }}</span>
            </span>
          </div>
        </div>
      </div>
    </div>
    <div>
      <ul>
        <li v-for="(item, i) in lieList" :key="i + ' '">
          <div class="names">{{ item.dictLabel }}</div>
          <div class="btn">
            <el-button size="btJS" @click="chuku(item.dictValue)">出库记录</el-button>
            <el-button size="btJS" @click="ruku(item.dictLabel)">入库记录</el-button>
          </div>
        </li>
      </ul>
    </div>
    <!-- 添加或修改物资入库登记对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1300px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-table
        :data="materialRegistrationList"
        :header-cell-style="{ background: '#003C69', color: 'white', }"
        v-if="jilunum==1"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" type="index" width="50" align="center">
          <template scope="scope">
            <span>
              {{
              (queryParams.pageNum - 1) * queryParams.pageSize +
              scope.$index +
              1
              }}
            </span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="物品类型" align="center" prop="wuPinType">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.category"
            :value="scope.row.wuPinType"
          />
        </template>
        </el-table-column>-->
        <el-table-column label="物品名称" align="center" prop="name" />
        <el-table-column label="条形码编号" align="center" prop="barcode" />
        <el-table-column label="物品规格" align="center" prop="materialSpecification" />
        <el-table-column label="物品型号" align="center" prop="materialXh" />
        <!-- <el-table-column label="营具类型" align="center" prop="yingJuType">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.camp_equipment"
            :value="scope.row.yingJuType"
          />
        </template>
        </el-table-column>-->
        <!-- <el-table-column
        label="责任单位"
        align="center"
        prop="responsibilityUnit"
        />-->
        <el-table-column label="库房" align="center" prop="warehouse" />
        <el-table-column label="价值" align="center" prop="price" />
        <el-table-column label="入库数量" align="center" prop="putInt" />
        <!-- <el-table-column label="创建人" align="center" prop="createName" /> -->
        <el-table-column label="入库时间" align="center" prop="storageTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.storageTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="入库编号" align="center" prop="inboundNo" /> -->
        <!-- <el-table-column label="存放位置" align="center" prop="storageLocation" /> -->
        <el-table-column label="购买时间" align="center" prop="buyingTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.buyingTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
        <!-- <el-table-column label="库房" align="center" prop="warehouse">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.storage_room"
            :value="scope.row.warehouse"
          />
        </template>
        </el-table-column>-->
        <!-- <el-table-column label="档案id" align="center" prop="archivesId" /> -->
        <!-- <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="150px"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['materialManagement:materialRegistration:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['materialManagement:materialRegistration:remove']"
            >删除</el-button
          >
        </template>
        </el-table-column>-->
      </el-table>
      <el-table
        :data="distributionList"
        v-if="jilunum==2"
        :header-cell-style="{ background: '#003C69', color: 'white' }"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" align="center">
          <template scope="scope">
            <span>
              {{
              (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
              }}
            </span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="物品类别" align="center" prop="wuPinType">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.category"
            :value="scope.row.wuPinType"
          />
        </template>
        </el-table-column>-->
        <el-table-column label="物品名称" align="center" prop="name" />
        <el-table-column label="条形码编号" align="center" prop="barcode" />
        <el-table-column label="物品规格" align="center" prop="materialSpecification" />
        <el-table-column label="物品型号" align="center" prop="materialXh" />
        <el-table-column label="营具类型" align="center" prop="yingJuType">
          <!-- <template slot-scope="scope">
          <dict-tag :options="dict.type.camp_equipment" :value="scope.row.yingJuType" />
          </template>-->
        </el-table-column>
        <!-- <el-table-column
        label="责任单位"
        align="center"
        prop="responsibilityUnit"
        />-->
        <el-table-column label="出库数量" align="center" prop="putOut" />
        <el-table-column label="价值" align="center" prop="price" />
        <el-table-column label="发放时间" align="center" prop="putOutTime" width="120">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.putOutTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column label="发放单位" align="center" prop="responsibilityUnit" width="150px" />
        <el-table-column label="发放对象" align="center" prop="outPeople"></el-table-column>
        <el-table-column label="接收单位" align="center" prop="outUnit" width="150px" />
        <el-table-column label="接收人" align="center" prop="recipient" />
        <el-table-column label="存放位置" align="center" prop="storageLocation" />
        <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
        <el-table-column label="库房" align="center" prop="warehouse">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.storage_room" :value="scope.row.warehouse" />
          </template>
        </el-table-column>
        <el-table-column label="库存数量" align="center" prop="total" />
        <!-- <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="120"
        >
          <template slot-scope="scope">
            <el-button
              size="btu"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['materialManagement:distribution:edit']"
            >修改</el-button>
            <el-button
              size="btd"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['materialManagement:distribution:remove']"
            >删除</el-button>
          </template>
        </el-table-column>-->
      </el-table>
      <pagination
        v-show="total > 0"
        v-if="distributionList.length>=1"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getchulist"
      />
      <pagination
        v-show="total > 0"
        v-if="materialRegistrationList.length>=1"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getrulist"
      />
      <div slot="footer" class="dialog-footer">
        <!-- <el-button type="primary" @click="submitForm">确 定</el-button> -->
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listArchives } from "@/api/materialManagement/WarehouseStatistics";
import { listMaterialRegistration } from "@/api/materialManagement/materialRegistration";
import { listDistribution } from "@/api/materialManagement/distribution";

export default {
  dicts: ["category", "camp_equipment", "storage_room", "distribution_object"],
  data() {
    return {
      lieList: [],
      tongJiList: [],
      open: false,
      title: "",
      // 总条数
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wuPinType: null,
        name: null,
        materialSpecification: null,
        materialXh: null,
        yingJuType: null,
        responsibilityUnit: null,
        personLiable: null,
        putInt: null,
        createName: null,
        storageTime: null,
        inboundNo: null,
        storageLocation: null,
        buyingTime: null,
        price: null,
        warehouse: null,
        archivesId: null
      },
      materialRegistrationList: [],
      distributionList: [],
      jilunum: 0
    };
  },
  created() {
    listArchives().then(res => {
      this.lieList = res.data.date;
      this.tongJiList = [{ name: "总库房数", num: res.data.number }];
      console.log(res.data, 111);
    });
  },
  methods: {
    ruku(e) {
      this.queryParams.warehouse = e;
      this.title = "入库记录";
      this.jilunum = 1;
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getrulist();
    },
    getrulist() {
      this.distributionList = [];
      listMaterialRegistration(this.queryParams).then(response => {
        this.materialRegistrationList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.open = true;
      });
    },
    chuku(e) {
      this.queryParams.warehouse = e;
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.jilunum = 2;
      this.title = "出库记录";
      this.getchulist();
    },
    getchulist() {
      this.materialRegistrationList = [];
      listDistribution(this.queryParams).then(response => {
        this.distributionList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.jilunum = 2;
        this.title = "出库记录";
        this.open = true;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
    }
  }
};
</script>

<style scoped>
.names {
  color: #fff;
  font-size: 14px;
  text-align: center;
  padding: 26px 26px 38px 26px;
  letter-spacing: 1px;
}
.btn {
  text-align: center;
}
ul {
  /* justify-content: space-between; */
  padding: 0 22px;
  display: flex;
  flex-wrap: wrap;
}
.span {
  display: flex;
  flex-wrap: wrap;
}

li {
  list-style: none;
  background: url("../../../assets/images/book.png") no-repeat;
  height: 140px;
  width: 222px;
  /* margin: 20px 0; */
  margin-bottom: 20px;
  background-size: 100% 100%;
  flex: 0 0 16%;
  margin-right: 10px;
}
.hezi {
  margin-bottom: 13px;
  margin-top: 15px;
  position: relative;
  background: rgb(0, 60, 105);
  color: #fff;
  font-size: 18px;
  font-weight: 600;
}

.tji {
  width: 176px;
  height: 36px;
  text-align: center;
  line-height: 36px;
  position: absolute;
  top: -18px;
  right: 87px;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  background: #004d86;
}
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}
::v-deep .el-textarea__inner {
  width: 965px;
  height: 104px;
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
::v-deep .el-dialog__body {
  overflow: auto;
}
::v-deep .pagination-container {
  margin-top: 20px;
  height: 50px;
}
::v-deep .el-button--btJS {
  width: 80px !important;
  text-align: center;
}
</style>
