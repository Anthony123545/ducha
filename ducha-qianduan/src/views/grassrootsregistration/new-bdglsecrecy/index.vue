<template>
  <div class="app-container">
    <!-- 登记类型切换 -->
    <el-radio-group v-model="mode" size="medium" style="margin-bottom: 15px">
      <el-radio-button label="device">互联网、智能电子设备</el-radio-button>
      <el-radio-button label="secret">涉密载体使用管理</el-radio-button>
    </el-radio-group>
    <!-- 顶部操作栏 -->
    <div style="margin-bottom: 15px">
      <el-button type="primary" @click="openAdd">新增</el-button>
    </div>

    <!-- 已保存记录列表 -->
    <el-table :data="list" border style="width: 100%">
      <template v-if="mode === 'device'">
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="dept" label="部别" />
        <el-table-column prop="useDate" label="填写日期" />
      </template>
      <template v-else>
        <el-table-column prop="carrierType" label="载体类型" />
        <el-table-column prop="brandModel" label="品牌型号" />
        <el-table-column prop="secretLevel" label="密级" />
      </template>
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <el-button
            size="btk"
            type="text"
            @click="view(scope.row)"
            v-hasPermi="['grassrootsregistration:new-bdglsecrecy:query']"
            ><span class="chakan">查看</span></el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增弹窗 -->
    <el-dialog
      :title="
        mode === 'device' ? '新增智能电子设备使用登记' : '新增涉密载体使用登记'
      "
      :visible.sync="addVisible"
      width="80%"
      top="5vh"
    >
      <table v-if="mode === 'device'" class="form-table">
        <tr>
          <td>姓名</td>
          <td><el-input v-model="form.name" /></td>
          <td>部别</td>
          <td><el-input v-model="form.dept" /></td>
        </tr>
        <tr>
          <td>电话号码1<br />及启停时间</td>
          <td colspan="3">
            <el-input v-model="form.phone1" placeholder="号码" />
            <el-input v-model="form.phone1Time" placeholder="启停时间" />
          </td>
        </tr>
        <tr>
          <td>电话号码2<br />及启停时间</td>
          <td colspan="3">
            <el-input v-model="form.phone2" />
            <el-input v-model="form.phone2Time" />
          </td>
        </tr>
        <tr>
          <td>电话号码3<br />及启停时间</td>
          <td colspan="3">
            <el-input v-model="form.phone3" />
            <el-input v-model="form.phone3Time" />
          </td>
        </tr>
        <tr>
          <td>电话号码4<br />及启停时间</td>
          <td colspan="3">
            <el-input v-model="form.phone4" />
            <el-input v-model="form.phone4Time" />
          </td>
        </tr>
        <tr>
          <td>智能手机1<br />品牌型号</td>
          <td><el-input v-model="form.smartPhone1" /></td>
          <td>智能手机2<br />品牌型号</td>
          <td><el-input v-model="form.smartPhone2" /></td>
        </tr>
        <tr>
          <td>智能手机3<br />品牌型号</td>
          <td><el-input v-model="form.smartPhone3" /></td>
          <td>非智能手机<br />品牌型号</td>
          <td><el-input v-model="form.nonSmartPhone" /></td>
        </tr>
        <tr>
          <td>其他智能电子设备</td>
          <td colspan="3">
            <el-input type="textarea" v-model="form.otherDevice" />
          </td>
        </tr>
        <tr>
          <td>微信账号</td>
          <td><el-input v-model="form.wechat" /></td>
          <td>QQ账号</td>
          <td><el-input v-model="form.qq" /></td>
        </tr>
        <tr>
          <td>微博账号</td>
          <td><el-input v-model="form.weibo" /></td>
          <td>抖音账号</td>
          <td><el-input v-model="form.douyin" /></td>
        </tr>
        <tr>
          <td>其他社交媒体账号</td>
          <td colspan="3">
            <el-input v-model="form.otherSocial" />
          </td>
        </tr>
        <tr>
          <td>使用人</td>
          <td><el-input v-model="form.userName" /></td>
          <td>日期</td>
          <td><el-input v-model="form.date" /></td>
        </tr>
      </table>
      <!-- 新的涉密载体表格 -->
      <table v-else class="form-table">
        <tr>
          <td>载体类型</td>
          <td><el-input v-model="form.carrierType" /></td>
          <td>品牌型号</td>
          <td><el-input v-model="form.brandModel" /></td>
          <td>密级</td>
          <td><el-input v-model="form.secretLevel" /></td>
        </tr>
        <tr>
          <td>启用日期</td>
          <td><el-input v-model="form.startDate" /></td>
          <td>产品序列号</td>
          <td><el-input v-model="form.number" /></td>
        </tr>
        <tr>
          <td>其他信息</td>
          <td><el-input v-model="form.other_information" /></td>
        </tr>

        <tr>
          <td colspan="6" style="font-weight: bold">责任人信息</td>
        </tr>

        <tr v-for="(item, index) in form.managers" :key="'manager-' + index">
          <td>责任人</td>
          <td>
            <el-input v-model="item.person" />
          </td>

          <td>管理日期</td>
          <td>
            <el-input v-model="item.startDate" placeholder="开始日期" />
          </td>

          <td>
            <el-input v-model="item.endDate" placeholder="结束日期" />
          </td>

          <td>
            <el-button
              type="text"
              @click="removeManager(index)"
              v-if="form.managers.length > 1"
            >
              删除
            </el-button>
          </td>
        </tr>

        <tr>
          <td colspan="6" align="center">
            <el-button
              size="mini"
              @click="addManager"
              :disabled="form.managers.length >= 6"
            >
              + 添加责任人
            </el-button>
          </td>
        </tr>
        <!--        使用维护记录-->
        <tr>
          <td colspan="6" style="font-weight: bold">使用维护记录</td>
        </tr>

        <tr v-for="(item, index) in form.records" :key="'record-' + index">
          <td>日期</td>
          <td>
            <el-input v-model="item.date" />
          </td>

          <td>事项</td>
          <td>
            <el-input v-model="item.matter" />
          </td>

          <td>承办人签字</td>
          <td>
            <el-input v-model="item.signer" />
          </td>
        </tr>

        <tr>
          <td colspan="6" align="center">
            <el-button
              size="mini"
              @click="addRecord"
              :disabled="form.records.length >= 5"
            >
              + 添加记录
            </el-button>
          </td>
        </tr>
      </table>

      <span slot="footer">
        <el-button @click="addVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </el-dialog>

    <!-- 查看弹窗：关联外部组件 -->
    <el-dialog
      :title="
        mode === 'device' ? '智能电子设备使用登记详情' : '涉密载体使用管理详情'
      "
      :visible.sync="menuRoleVisible"
      width="80%"
      top="5vh"
      :close-on-click-modal="false"
    >
      <!-- 引入外部详情组件，传递数据 -->
      <bdglsecrecy1
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
        @close="menuRoleVisible = false"
      >
      </bdglsecrecy1>
      <!-- 新组件 -->
      <bdglsecrecy2
        v-if="menuRoleVisible && mode === 'secret'"
        :message="wordInfo"
      />
      <span slot="footer">
        <el-button @click="menuRoleVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
// 引入外部详情组件（和要事日记的 bdgldiary.vue 逻辑一致）
import bdglsecrecy1 from "@/components/look_word/bdglsecrecy1.vue";
// import bdglsecrecy2 from "@/components/look_word/bdglsecrecy2.vue";
import {
  listDeviceRegister,
  addDeviceRegister,
  getDeviceRegisterDetail,
} from "@/api/grassrootsregistration/new-bdglsecrecy";

export default {
  // 注册外部组件
  components: { bdglsecrecy1 },
  data() {
    return {
      mode: "device", // device | secret
      list: [],
      addVisible: false,
      menuRoleVisible: false,
      form: {
        managers: [{ person: "", startDate: "", endDate: "" }],
        records: [{ date: "", matter: "", signer: "" }],
      },
      wordInfo: {},
      managers: [{ person: "", startDate: "", endDate: "" }],
      records: [{ date: "", matter: "", signer: "" }],
    };
  },
  created() {
    this.loadList();
  },
  methods: {
    loadList() {
      listDeviceRegister().then((res) => {
        this.list = res.rows || [];
        console.log("看看数据", this.list);
      });
    },
    initForm() {
      this.form = {
        carrierType: "",
        brandModel: "",
        secretLevel: "",
        startDate: "",
        number: "",
        other_information: "",

        managers: [{ person: "", startDate: "", endDate: "" }],

        records: [{ date: "", matter: "", signer: "" }],
      };
    },
    openAdd() {
      this.form = {
        managers: [{ person: "", startDate: "", endDate: "" }],

        records: [{ date: "", matter: "", signer: "" }],
      };
      this.addVisible = true;
    },
    save() {
      const payload = {
        ...this.form,
        type: this.mode, // device / secret
      };
      addDeviceRegister(payload)
        .then(() => {
          this.$message.success("保存成功");
          this.addVisible = false;
          this.loadList();
        })
        .catch((error) => {
          this.$message.error("保存失败：" + error.message);
        });
    },
    view(row) {
      // 清空之前的详情数据
      this.wordInfo = {};
      // 调用接口获取详情
      getDeviceRegisterDetail(row.id)
        .then((res) => {
          // 赋值给 wordInfo（和要事日记变量名一致）
          this.wordInfo = res.data || res;
          console.log("详情数据:", this.wordInfo);
          // 打开查看弹窗（核心：控制变量设为true）
          this.menuRoleVisible = true;
        })
        .catch((error) => {
          console.error("查看详情失败:", error);
          this.$message.error("查看失败：" + (error.message || "获取数据异常"));
        });
    },

    addManager() {
      if (this.form.managers.length < 6) {
        this.form.managers.push({
          person: "",
          startDate: "",
          endDate: "",
        });
      }
    },
    removeManager(index) {
      this.form.managers.splice(index, 1);
    },
    addRecord() {
      if (this.form.records.length < 5) {
        this.form.records.push({
          date: "",
          matter: "",
          signer: "",
        });
      }
    },
  },
};
</script>

<style scoped>
.form-table {
  width: 100%;
  border-collapse: collapse;
}
.form-table td {
  border: 1px solid #000;
  padding: 6px;
}
.chakan {
  color: #409eff;
  cursor: pointer;
}
</style>
