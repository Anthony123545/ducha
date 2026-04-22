<template>
  <div class="app-container">
    <div class="hezi">
      <div class="tji">发射大队人员总统计</div>
      <div style="display: flex; flex-wrap: wrap; padding: 11px 20px">
        <div
          style="width: 25%; display: flex; align-items: center; height: 100px"
          v-for="item in tongJiList"
          :key="item.name"
        >
          <div class="div-l" style="flex: 2; text-align: right; margin-right: 30px">
            <span>{{ item.name }}</span>
          </div>
          <div class="div-r" style="flex: 1; text-align: right; margin-right: 120px">
            <span>
              <span style="font-size: 25px; margin-right: 18px">{{ item.num }}</span>人
            </span>
          </div>
        </div>
      </div>
    </div>
    <el-table :data="peopleList" :header-cell-style="{ background: '#004d86', color: 'white' }">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="单位名称" align="center" prop="name" />
      <el-table-column label="指挥管理军官" align="center" prop="zhjg" />
      <el-table-column label="专业技术军官" align="center" prop="zyjg" />
      <el-table-column label="文职人员" align="center" prop="wz"></el-table-column>
      <el-table-column label="初级士官" align="center" prop="cjsg"></el-table-column>
      <el-table-column label="中级士官" align="center" prop="zjsg"></el-table-column>
      <el-table-column label="高级士官" align="center" prop="gjsg"></el-table-column>
      <el-table-column label="义务兵" align="center" prop="sb" />
      <el-table-column label="各单位人数" align="center">
        <template scope="scope">
          <span v-if="scope.row.type==2">
            {{
            scope.row.zs
            }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="营级总人数" align="center">
        <template scope="scope">
          <span v-if="scope.row.type==1">
            {{
            scope.row.zs
            }}
          </span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="各单位人数" align="center" prop="zs" v-if />
      <el-table-column label="营级总人数" align="center" prop="zs" />-->
    </el-table>
  </div>
</template>

<script>
import { countNumber } from "@/api/peopleManage/people";
export default {
  data() {
    return {
      tongJiList: [
        { name: "总人数", num: 0 },
        { name: "义务兵", num: 0 },
        { name: "初级士官", num: 0 },
        { name: "中级士官", num: 0 },
        { name: "高级士官", num: 0 },
        { name: "文职人员", num: 0 },
        { name: "指挥管理军官", num: 0 },
        { name: "专业技术军官", num: 0 }
      ],
      peopleList: []
    };
  },
  created() {
    countNumber().then(res => {
      this.tongJiList.forEach(item => {
        if (item.name == "指挥管理军官") {
          item.num = res.data.zhjg;
        } else if (item.name == "总人数") {
          item.num = res.data.zongRenShu;
        } else if (item.name == "专业技术军官") {
          item.num = res.data.zyjg;
        } else if (item.name == "文职人员") {
          item.num = res.data.wz;
        } else if (item.name == "初级士官") {
          item.num = res.data.cjsg;
        } else if (item.name == "中级士官") {
          item.num = res.data.zjsg;
        } else if (item.name == "高级士官") {
          item.num = res.data.gjsg;
        } else if (item.name == "义务兵") {
          item.num = res.data.sb;
        }
      });
      this.peopleList = res.data.各单位;
    });
  }
};
</script>

<style scoped>
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
</style>
