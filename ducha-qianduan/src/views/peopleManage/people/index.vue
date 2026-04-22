<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="dadui">
          <img src="../../../images/星星.png" alt />
          单位
        </div>
        <div class="tree">
          <el-tree
            class="userAgrees"
            style="height: 700px; overflow: auto"
            :data="deptOptions"
            :props="defaultProps"
            @node-click="handleNodeClick"
            default-expand-all
            highlight-current
            :expand-on-click-node="false"
          ></el-tree>
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="68px"
        >
          <el-form-item label="查询类型" prop="searchType">
            <el-select
              v-model="queryParams.searchType"
              placeholder="选择查询类型"
              clearable
              size="small"
            >
              <el-option label="姓名" value="name"></el-option>
              <el-option label="工号" value="employeeNumber"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="关键字" prop="searchKeyword">
            <el-input
              v-model="queryParams.searchKeyword"
              placeholder="请输入关键字"
              clearable
              size="small"
              @input="handleQuery"
            />
          </el-form-item>
          <!-- <el-form-item label="年龄" prop="age">
        <el-input
          v-model="queryParams.age"
          placeholder="请输入年龄"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出生年月" prop="birthDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.birthDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择出生年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="命令号" prop="command">
        <el-input
          v-model="queryParams.command"
          placeholder="请输入命令号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部职别" prop="duty">
        <el-input
          v-model="queryParams.duty"
          placeholder="请输入部职别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职务" prop="postId">
        <el-input
          v-model="queryParams.postId"
          placeholder="请输入职务"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="等级" prop="gradeId">
        <el-input
          v-model="queryParams.gradeId"
          placeholder="请输入等级"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职务等级时间" prop="postDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.postDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择职务等级时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="工作岗位" prop="job">
        <el-input
          v-model="queryParams.job"
          placeholder="请输入工作岗位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="技术职务" prop="skillId">
        <el-input
          v-model="queryParams.skillId"
          placeholder="请输入技术职务"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="技术职务时间" prop="skillDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.skillDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择技术职务时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="技术等级" prop="levelId">
        <el-input
          v-model="queryParams.levelId"
          placeholder="请输入技术等级"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="技术等级时间" prop="levelDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.levelDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择技术等级时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="军人证号" prop="military">
        <el-input
          v-model="queryParams.military"
          placeholder="请输入军人证号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号" prop="idcard">
        <el-input
          v-model="queryParams.idcard"
          placeholder="请输入身份证号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入伍年月" prop="enlistDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.enlistDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择入伍年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="入伍地点" prop="enlistaddress">
        <el-input
          v-model="queryParams.enlistaddress"
          placeholder="请输入入伍地点"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="军衔" prop="rankId">
        <el-input
          v-model="queryParams.rankId"
          placeholder="请输入军衔"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="军衔时间" prop="rankDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.rankDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择军衔时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="民族" prop="nationId">
        <el-input
          v-model="queryParams.nationId"
          placeholder="请输入民族"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="籍贯" prop="origin">
        <el-input
          v-model="queryParams.origin"
          placeholder="请输入籍贯"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="人员类别" prop="classId">
        <el-input
          v-model="queryParams.classId"
          placeholder="请输入人员类别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学历" prop="educationId">
        <el-input
          v-model="queryParams.educationId"
          placeholder="请输入学历"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学位" prop="degreeId">
        <el-input
          v-model="queryParams.degreeId"
          placeholder="请输入学位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="毕业时间" prop="graduationDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.graduationDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择毕业时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="毕业院校" prop="school">
        <el-input
          v-model="queryParams.school"
          placeholder="请输入毕业院校"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="党团情况" prop="partyId">
        <el-input
          v-model="queryParams.partyId"
          placeholder="请输入党团情况"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="党团时间" prop="partyDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.partyDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择党团时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="简历名称" prop="readmeFileName">
        <el-input
          v-model="queryParams.readmeFileName"
          placeholder="请输入简历名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部别" prop="unitId">
        <el-input
          v-model="queryParams.unitId"
          placeholder="请输入部别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="照片" prop="avatar">
        <el-input
          v-model="queryParams.avatar"
          placeholder="请输入照片"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出生地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入出生地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="0在位，1调出" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择0在位，1调出" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="0在位，1调出" prop="createtimes">
        <el-date-picker clearable size="small"
          v-model="queryParams.createtimes"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择0在位，1调出">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="0在位，1调出" prop="updatetimes">
        <el-date-picker clearable size="small"
          v-model="queryParams.updatetimes"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择0在位，1调出">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="0在位，1休假，2出差,3借调，4学习，5执行任务,6外诊，7其他" prop="reign">
        <el-input
          v-model="queryParams.reign"
          placeholder="请输入0在位，1休假，2出差,3借调，4学习，5执行任务,6外诊，7其他"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作时间" prop="jobTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.jobTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择工作时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="健康情况" prop="healthy">
        <el-input
          v-model="queryParams.healthy"
          placeholder="请输入健康情况"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="血型" prop="bloodType">
        <el-select v-model="queryParams.bloodType" placeholder="请选择血型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="职务时间" prop="postTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.postTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择职务时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="专业" prop="major">
        <el-input
          v-model="queryParams.major"
          placeholder="请输入专业"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入学时间" prop="admissionTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.admissionTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择入学时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="手机型号" prop="mobilePhoneModel">
        <el-input
          v-model="queryParams.mobilePhoneModel"
          placeholder="请输入手机型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="保障卡号" prop="guaranteeCardNo">
        <el-input
          v-model="queryParams.guaranteeCardNo"
          placeholder="请输入保障卡号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工资卡号" prop="payrollCardNo">
        <el-input
          v-model="queryParams.payrollCardNo"
          placeholder="请输入工资卡号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="家庭住址" prop="live">
        <el-input
          v-model="queryParams.live"
          placeholder="请输入家庭住址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门ID" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="现军衔" prop="nowMilitaryRank">
        <el-input
          v-model="queryParams.nowMilitaryRank"
          placeholder="请输入现军衔"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="待遇级别" prop="treatmentLevel">
        <el-input
          v-model="queryParams.treatmentLevel"
          placeholder="请输入待遇级别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="现待遇级别时间" prop="rankUpgradeTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.rankUpgradeTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择现待遇级别时间">
        </el-date-picker>
          </el-form-item>-->
          <el-form-item>
            <!-- <el-button type="primary" size="btn" @click="handleQuery"
              >搜索</el-button
            >-->
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
              v-hasPermi="['peopleManage:people:add']"
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
              v-hasPermi="['peopleManage:people:edit']"
              >修改</el-button
            >
          </el-col>-->
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['peopleManage:people:remove']"
              >删除</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              :loading="exportLoading"
              @click="handleExport"
              v-hasPermi="['peopleManage:people:export']"
              >导出</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-upload"
              size="mini"
              @click="showImportDialog"
              v-hasPermi="['peopleManage:people:import']"
              >导入</el-button
            >
          </el-col>
          <!-- <right-toolbar
            :showSearch.sync="showSearch"
            @queryTable="getList"
          ></right-toolbar>-->
        </el-row>

        <el-table
          v-loading="loading"
          :data="peopleList"
          :header-cell-style="{ background: '#003C69', color: 'white' }"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序号" align="center" type="index" />
          <el-table-column
            label="单位"
            align="center"
            prop="deptId"
            width="180"
            :formatter="deptIdformatter"
          />
          <el-table-column label="姓名" align="center" prop="name" />
          <el-table-column label="工号" align="center" prop="employeeNumber" />
          <el-table-column label="性别" align="center" prop="sex">
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.sys_user_sex"
                :value="scope.row.sex"
              />
            </template>
          </el-table-column>
          <el-table-column label="年龄" align="center" prop="age" />
          <el-table-column
            label="身份证号"
            align="center"
            prop="idcard"
            width="170"
          />
          <el-table-column
            label="在位情况"
            align="center"
            prop="reign"
            width="150"
          >
            <template slot-scope="scope">
              <dict-tag
                style="display: inline-block"
                :options="dict.type.people_state"
                :value="scope.row.reign"
              />
              <!-- <span v-if="scope.row.statusrk">- {{scope.row.statusrk}}</span> -->
            </template>
          </el-table-column>
          <el-table-column
            label="部职别"
            align="center"
            width="300"
            prop="duty"
          />
          <el-table-column
            label="职务"
            align="center"
            width="120"
            prop="postId"
          >
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.post_Level"
                :value="scope.row.postId"
              />
            </template>
          </el-table-column>
          <!-- <el-table-column label="等级" align="center" prop="gradeId" /> -->
          <el-table-column
            label="职务等级时间"
            align="center"
            prop="postDate"
            width="180"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.postDate, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="籍贯"
            align="center"
            prop="origin"
            width="150"
          />
          <el-table-column
            label="操作"
            align="center"
            width="200"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                size="btu"
                type="text"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['peopleManage:people:edit']"
                >修改</el-button
              >
              <el-button
                size="btk"
                type="text"
                @click="handlesee(scope.row)"
                v-hasPermi="['peopleManage:people:query']"
                >查看</el-button
              >
              <el-button
                size="btd"
                type="text"
                @click="handleDelete(scope.row)"
                v-hasPermi="['peopleManage:people:remove']"
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

    <!-- 添加或修改人员管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1400px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree" style="overflow: -Scroll; overflow-x: hidden">
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          :inline="true"
          label-width="120px"
        >
          <div class="jiben">基本信息</div>
          <div class="div-box-title1">
            <span>个人信息</span>
            <img src="@/assets/images/虚线.png" alt />
          </div>
          <div style="width: 100%; display: flex; flex-direction: row">
            <div>
              <el-row>
                <el-form-item label="姓名" prop="name">
                  <el-input v-model="form.name" placeholder />
                </el-form-item>
                <el-form-item label="工号" prop="employeeNumber">
                  <el-input
                    v-model="form.employeeNumber"
                    placeholder
                    :disabled="!btnflg"
                  />
                </el-form-item>
                <el-form-item label="身份证号" prop="idcard">
                  <el-input
                    v-model="form.idcard"
                    placeholder
                    @blur="idcardfun()"
                  />
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="民族" prop="nationId">
                  <el-select v-model="form.nationId" clearable placeholder>
                    <el-option
                      v-for="dict in nationIds"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="parseInt(dict.dictValue)"
                      style="width: 200px; height: 36px"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                  <el-select v-model="form.sex" clearable placeholder>
                    <el-option
                      v-for="dict in sexList"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                      style="width: 200px; height: 36px"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="年龄" prop="age">
                  <el-input v-model="form.age" placeholder />
                </el-form-item>
                <el-form-item label="出生年月" prop="birthDate">
                  <el-date-picker
                    clearable
                    size="small"
                    v-model="form.birthDate"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder
                    style="width: 200px; height: 36px"
                  ></el-date-picker>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="籍贯" prop="origin">
                  <el-input v-model="form.origin" placeholder />
                </el-form-item>
                <el-form-item label="家庭住址" prop="live">
                  <el-input v-model="form.live" placeholder />
                </el-form-item>
              </el-row>
            </div>
            <div style="margin-left: 50px; display: flex">
              <div class="pictureUploading-img">
                <img src="@/assets/images/提示.png" alt />
              </div>
              <el-form-item prop="pictureUploading" class="personphoto">
                <imageUpload
                  :limit="1"
                  v-model="form.avatar"
                  style="height: 200px"
                />
              </el-form-item>
            </div>
          </div>
          <el-row>
            <el-form-item label="血型" prop="bloodType">
              <el-select v-model="form.bloodType" clearable placeholder>
                <el-option
                  v-for="dict in bloodType"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="政治面貌" prop="partyId">
              <el-select v-model="form.partyId" clearable placeholder>
                <el-option
                  v-for="dict in partyIds"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="党团时间" prop="partyDate">
              <el-date-picker
                clearable
                size="small"
                v-model="form.partyDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder
              ></el-date-picker>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="健康情况" prop="healthy">
              <el-select v-model="form.healthy" clearable placeholder>
                <el-option
                  v-for="dict in healthy"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="保障卡号" prop="guaranteeCardNo">
              <el-input v-model="form.guaranteeCardNo" placeholder />
            </el-form-item>
            <el-form-item label="工资卡号" prop="payrollCardNo">
              <el-input v-model="form.payrollCardNo" placeholder />
            </el-form-item>
          </el-row>
          <div class="div-box-title1">
            <span>婚姻信息</span>
            <img src="@/assets/images/虚线.png" alt />
          </div>
          <el-row>
            <el-form-item label="婚姻情况" prop="marryList">
              <el-select v-model="form.marryList" clearable placeholder>
                <el-option
                  v-for="dict in marryList"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="子女情况" prop="childrenList">
              <el-select v-model="form.childrenList" clearable placeholder>
                <el-option
                  v-for="dict in childrenList"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <div class="div-box-title1">
            <span>学历教育</span>
            <img src="@/assets/images/虚线.png" alt />
          </div>
          <el-form-item label="学历" prop="educationId">
            <el-select v-model="form.educationId" clearable placeholder>
              <el-option
                v-for="dict in educationId"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="学位" prop="degreeId">
            <el-select v-model="form.degreeId" clearable placeholder>
              <el-option
                v-for="dict in degree"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="入学时间" prop="admissionTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form.admissionTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder
            ></el-date-picker>
          </el-form-item>
          <el-form-item label=" 专业" prop="major">
            <el-input v-model="form.major" placeholder />
          </el-form-item>
          <el-form-item label="毕业时间" prop="graduationDate">
            <el-date-picker
              clearable
              size="small"
              v-model="form.graduationDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="毕业学校" prop="school">
            <el-input v-model="form.school" placeholder />
          </el-form-item>
          <div class="div-box-title1">
            <span>联系方式</span>
            <img src="@/assets/images/虚线.png" alt />
          </div>
          <el-form-item label="手机型号" prop="mobilePhoneModel">
            <el-input v-model="form.mobilePhoneModel" placeholder />
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="form.phone" placeholder />
          </el-form-item>
          <el-form-item label="手机号码2" prop="phoneStandby">
            <el-input v-model="form.phoneStandby" placeholder />
          </el-form-item>
          <div class="jiben">职务信息</div>
          <el-form-item label="军人证号" prop="military">
            <el-input v-model="form.military" placeholder />
          </el-form-item>
          <el-form-item label="人员类别" prop="classId">
            <el-select v-model="form.classId" clearable placeholder>
              <el-option
                v-for="dict in classId"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单位" prop="deptId">
            <el-select v-model="form.deptId" ref="deptId" clearable placeholder>
              <el-option
                v-for="dict in unitId"
                :key="dict.deptId"
                :label="dict.deptName"
                :value="dict.deptId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="部职别" prop="duty">
            <el-input v-model="form.duty" placeholder />
          </el-form-item>
          <el-form-item label="入伍时间" prop="enlistDate">
            <el-date-picker
              clearable
              size="small"
              v-model="form.enlistDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="入伍地点" prop="enlistaddress">
            <el-input v-model="form.enlistaddress" placeholder />
          </el-form-item>
          <el-form-item label="职务层级" prop="postId">
            <el-select v-model="form.postId" clearable placeholder>
              <el-option
                v-for="dict in postIds"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="现职务层级时间" prop="postDate">
            <el-date-picker
              clearable
              size="small"
              v-model="form.postDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="在位情况" prop="reign">
            <el-select
              v-model="form.reign"
              placeholder="请选择在位情况"
              clearable
            >
              <el-option
                v-for="dict in dict.type.people_state"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="军衔" prop="rankId">
            <el-select v-model="form.rankId" clearable placeholder>
              <el-option
                v-for="dict in rankIds"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="现军衔" prop="nowMilitaryRank">
            <el-select v-model="form.nowMilitaryRank" clearable placeholder>
              <el-option
                v-for="dict in rankIds"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="现军衔时间" prop="rankDate">
            <el-date-picker
              clearable
              size="small"
              v-model="form.rankDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="是否编制" prop="authorizedStrength">
            <el-select v-model="form.authorizedStrength" clearable placeholder>
              <el-option
                v-for="dict in authorizedStrength"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="命令号" prop="command">
            <el-input v-model="form.command" placeholder />
          </el-form-item>
          <el-form-item label="待遇级别" prop="treatmentLevel">
            <el-input v-model="form.treatmentLevel" placeholder />
          </el-form-item>
          <el-form-item label="现待遇级别时间" prop="rankUpgradeTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form.rankUpgradeTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="专业是否对口" prop="isBianzhi">
            <el-select v-model="form.authorizedStrength" clearable placeholder>
              <el-option
                v-for="dict in authorizedStrength"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="工作时间" prop="jobTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form.jobTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="在位备注" prop="statusrk">
            <el-input v-model="form.statusrk" placeholder />
          </el-form-item>
          <div class="jiben">家庭信息</div>
          <!-- <span @click="tiaddfun" class="zengJia">+</span> -->
          <el-row
            style="padding-top: 20px; margin-top: 10px; margin-bottom: 20px"
            v-for="(item, index) in jtlist"
            :key="index"
          >
            <el-form-item label="姓名" prop>
              <el-input v-model="item.name" placeholder />
            </el-form-item>
            <el-form-item label="与本人关系" prop="characterRelationship">
              <el-input v-model="item.characterRelationship" placeholder />
            </el-form-item>
            <el-form-item label="联系电话" prop="phoneNumber">
              <el-input v-model="item.phoneNumber" placeholder />
            </el-form-item>
            <el-form-item label="单位" prop="unitName">
              <!-- <el-input v-model="item.unitName" placeholder /> -->
              <input type="text" v-model="item.unitName" class="input" />
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                icon="el-icon-plus"
                size="mini"
                @click="tiaddfun"
                v-if="index == jtlist.length - 1"
              ></el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                v-if="index > 0"
                @click="jtDelete(index)"
              ></el-button>
            </el-form-item>
            <!-- <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="jtDelete(index)"
            style="height: 36px; margin-left: 575px"
            v-if="index != 0"
            >删除</el-button
            >-->
          </el-row>
          <div class="jiben">培训经历</div>
          <el-form-item label prop="training">
            <el-input v-model="form.training" type="textarea" placeholder />
          </el-form-item>
          <div class="jiben">奖惩情况</div>
          <el-form-item label prop="prize">
            <el-input v-model="form.prize" type="textarea" placeholder />
          </el-form-item>
          <div class="jiben">简历</div>
          <el-form-item
            label
            label-width="0px"
            class="laborcontractuploading"
            prop="readmeFile"
          >
            <fileUpload v-model="form.readmeFile" class="m-l-20" />
          </el-form-item>
          <!-- <el-form-item label prop="readmeFile">
          <el-input v-model="form.readmeFile" type="textarea" placeholder />
          </el-form-item>-->
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="btnflg"
          >确 定</el-button
        >
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog
      :title="'导入人员数据'"
      :visible.sync="importDialogVisible"
      width="500px"
      @close="closeImportDialog"
    >
      <div class="import-dialog-content">
        <el-upload
          ref="upload"
          class="upload-demo"
          action=""
          :auto-upload="false"
          :on-change="handleFileChange"
          :limit="1"
          :file-list="[]"
          accept=".xlsx,.xls"
        >
          <el-button
            slot="trigger"
            size="small"
            type="primary"
            :loading="importLoading"
            >选择文件</el-button
          >
          <div slot="tip" class="el-upload__tip">
            请选择 .xlsx 或 .xls 格式的Excel文件
          </div>
        </el-upload>

        <div class="import-buttons">
          <el-button
            type="primary"
            @click="downloadTemplate"
            style="margin-right: 10px"
          >
            下载模板
          </el-button>
          <el-button
            type="success"
            @click="submitImport"
            :loading="importLoading"
          >
            确认导入
          </el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listPeople,
  getPeople,
  delPeople,
  addPeople,
  updatePeople,
  exportPeople,
  getDept,
  listDept,
  treeselect,
  countNumber,
  downloadTemplate,
  importPeople,
} from "@/api/peopleManage/people";
import Cookies from "js-cookie";

export default {
  name: "People",
  dicts: [
    "sys_user_sex",
    "post_Level",
    "sys_user_rank",
    "personnel_type",
    "Degree_level",
    "degree",
    "Caucus",
    "marryList",
    "sys_yw",
    "healthy",
    "bloodType",
    "sys_yes_no",
    "sys_yes_no",
    "people_state",
  ],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedBdglPeople: [],
      // 导入弹窗是否显示
      importDialogVisible: false,
      // 导入进度
      importLoading: false,
      // 上传文件
      uploadFile: null,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人员管理表格数据
      peopleList: [],
      // 人员管理表格数据
      bdglPeopleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        searchType: "name",
        searchKeyword: null,
        name: null,
        sex: null,
        age: null,
        birthDate: null,
        command: null,
        duty: null,
        postId: null,
        gradeId: null,
        postDate: null,
        job: null,
        skillId: null,
        skillDate: null,
        levelId: null,
        levelDate: null,
        military: null,
        idcard: null,
        enlistDate: null,
        enlistaddress: null,
        rankId: null,
        rankDate: null,
        nationId: null,
        origin: null,
        classId: null,
        educationId: null,
        degreeId: null,
        graduationDate: null,
        school: null,
        partyId: null,
        partyDate: null,
        training: null,
        readmeFile: null,
        readmeFileName: null,
        prize: null,
        marryList: null,
        childrenList: null,
        unitId: null,
        avatar: null,
        address: null,
        phone: null,
        status: null,
        createtimes: null,
        updatetimes: null,
        reign: null,
        jobTime: null,
        healthy: null,
        bloodType: null,
        postTime: null,
        major: null,
        admissionTime: null,
        mobilePhoneModel: null,
        guaranteeCardNo: null,
        payrollCardNo: null,
        authorizedStrength: null,
        aboral: null,
        live: null,
        deptId: null,
        userId: null,
        nowMilitaryRank: null,
        treatmentLevel: null,
        rankUpgradeTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        idcard: [
          { required: true, message: "身份证号不能为空", trigger: "blur" },
          {
            pattern:
              /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
            message: "请输入正确的身份证号",
            trigger: "blur",
          },
        ],
        nationId: [
          { required: true, message: "民族不能为空", trigger: "blur" },
        ],
        sex: [{ required: true, message: "性别不能为空", trigger: "blur" }],
        age: [{ required: true, message: "年龄不能为空", trigger: "blur" }],
        birthDate: [
          { required: true, message: "出生年月不能为空", trigger: "blur" },
        ],
        origin: [{ required: true, message: "籍贯不能为空", trigger: "blur" }],
        live: [
          { required: true, message: "家庭住址不能为空", trigger: "blur" },
        ],
        partyId: [
          { required: true, message: "政治面貌不能为空", trigger: "change" },
        ],
        healthy: [
          { required: true, message: "健康情况不能为空", trigger: "change" },
        ],
        marryList: [
          { required: true, message: "婚姻情况不能为空", trigger: "change" },
        ],
        educationId: [
          { required: true, message: "学历不能为空", trigger: "change" },
        ],
        admissionTime: [
          { required: true, message: "入学时间不能为空", trigger: "change" },
        ],
        graduationDate: [
          { required: true, message: "毕业时间不能为空", trigger: "change" },
        ],
        school: [
          { required: true, message: "毕业学校不能为空", trigger: "blur" },
        ],
        phone: [
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
        phoneStandby: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
        enlistDate: [
          { required: true, message: "入伍时间不能为空", trigger: "change" },
        ],
        authorizedStrength: [
          { required: true, message: "是否编制不能为空", trigger: "change" },
        ],
        deptId: [
          { required: true, message: "单位不能为空", trigger: "change" },
        ],
        rankId: [
          { required: true, message: "军衔不能为空", trigger: "change" },
        ],
        nowMilitaryRank: [
          { required: true, message: "现军衔不能为空", trigger: "change" },
        ],
        postId: [
          { required: true, message: "职务层级不能为空", trigger: "change" },
        ],
        // name: [{ required: true, message: "亲属姓名不能为空", trigger: "blur" }]
        // jtname:[{ validator: jtnameblur, trigger: "blur" }],
        // characterRelationship: [
        //   { required: true, message: "与本人关系必填一个", trigger: "blur" }
        // ],
        // phoneNumber: [
        //   { required: true, message: "联系电话不能为空", trigger: "blur" },
        //   {
        //     pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
        //     message: "请输入正确的手机号码",
        //     trigger: "blur"
        //   }
        // ],
        // unitName: [{ required: true, message: "单位不能为空", trigger: "blur" }]
      },
      //性别数组
      sexList: [],
      //血型数组
      bloodType: [],
      //政治面貌
      partyIds: [],
      //健康状况
      healthy: [],
      //婚姻情况
      marryList: [],
      //学历
      educationId: [],
      //学位
      degree: [],
      //是否编制
      authorizedStrength: [],
      //子女情况
      childrenList: [],
      //人员类别
      classId: [],
      //职务层级
      postIds: [],
      //军衔
      rankIds: [],
      //单位
      deptId: [],
      //民族
      nationIds: [],
      unitId: [],
      jtlist: [
        {
          name: null,
          characterRelationship: null,
          phoneNumber: null,
          unitName: null,
        },
      ],
      defaultProps: {
        children: "children",
        label: "label",
      },
      deptOptions: [],
      value: null,
      btnflg: false,
    };
  },
  created() {
    // if (Cookies.get("shuaxin") != "true") {
    //   window.location.reload();
    // }
    // Cookies.set("shuaxin", "true");
    this.getList();
    //性别
    this.getDicts("sys_user_sex").then((response) => {
      this.sexList = response.data;
    });
    //血型
    this.getDicts("bloodType").then((response) => {
      this.bloodType = response.data;
    });
    //政治面貌
    this.getDicts("Caucus").then((response) => {
      this.partyIds = response.data;
    });
    //健康情况
    this.getDicts("healthy").then((response) => {
      this.healthy = response.data;
    });
    //婚姻情况
    this.getDicts("marryList").then((response) => {
      this.marryList = response.data;
    });
    //学历
    this.getDicts("Degree_level").then((response) => {
      this.educationId = response.data;
    });
    //学位
    this.getDicts("degree").then((response) => {
      this.degree = response.data;
    });
    //是否编制
    this.getDicts("sys_yes_no").then((response) => {
      this.authorizedStrength = response.data;
    });
    //子女情况
    this.getDicts("sys_yw").then((response) => {
      this.childrenList = response.data;
    });
    //人员类别
    this.getDicts("personnel_type").then((response) => {
      this.classId = response.data;
    });
    //职务层级
    this.getDicts("post_Level").then((response) => {
      this.postIds = response.data;
    });
    //军衔
    this.getDicts("sys_user_rank").then((response) => {
      this.rankIds = response.data;
    });
    //民族
    this.getDicts("sys_familyName").then((response) => {
      this.nationIds = response.data;
    });
    //部门数据
    getDept().then((res) => {
      if (res.code == 200) {
        this.unitId = res.data;
      }
    });
    treeselect().then((res) => {
      if (res.code == 200) {
        this.deptOptions = res.data;
      }
    });
  },
  methods: {
    //单位翻译
    deptIdformatter(row, column) {
      var deptName = "";
      this.unitId.map((item) => {
        if (item.deptId == row.deptId) {
          deptName = item.deptName;
        }
      });
      return deptName;
    },
    /** 查询人员管理列表 */
    getList() {
      this.loading = true;
      listPeople(this.queryParams).then((response) => {
        this.peopleList = response.rows;
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
        name: null,
        sex: null,
        age: null,
        birthDate: null,
        command: null,
        duty: null,
        postId: null,
        gradeId: null,
        postDate: null,
        job: null,
        skillId: null,
        skillDate: null,
        levelId: null,
        levelDate: null,
        military: null,
        idcard: null,
        enlistDate: null,
        enlistaddress: null,
        rankId: null,
        rankDate: null,
        nationId: null,
        origin: null,
        classId: null,
        educationId: null,
        degreeId: null,
        graduationDate: null,
        school: null,
        partyId: null,
        partyDate: null,
        training: null,
        readmeFile: null,
        readmeFileName: null,
        prize: null,
        marryList: null,
        childrenList: null,
        unitId: null,
        avatar: null,
        address: null,
        phone: null,
        status: 0,
        createtimes: null,
        updatetimes: null,
        reign: null,
        jobTime: null,
        healthy: null,
        bloodType: null,
        postTime: null,
        major: null,
        admissionTime: null,
        mobilePhoneModel: null,
        guaranteeCardNo: null,
        payrollCardNo: null,
        authorizedStrength: null,
        aboral: null,
        live: null,
        deptId: null,
        userId: null,
        nowMilitaryRank: null,
        treatmentLevel: null,
        rankUpgradeTime: null,
        phoneStandby: null,
        deptName: null,
        isBianzhi: null,
        employeeNumber: null,
      };
      this.bdglPeopleList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // 根据选择的查询类型设置相应的查询参数
      if (this.queryParams.searchType && this.queryParams.searchKeyword) {
        // 清除其他查询字段的值，避免干扰
        this.queryParams.name = null;
        this.queryParams.employeeNumber = null;

        // 根据选择的类型设置对应的查询字段
        if (this.queryParams.searchType === "name") {
          this.queryParams.name = this.queryParams.searchKeyword;
        } else if (this.queryParams.searchType === "employeeNumber") {
          this.queryParams.employeeNumber = this.queryParams.searchKeyword;
        }
      } else {
        // 如果没有选择查询类型或没有输入关键字，清除查询字段
        this.queryParams.name = null;
        this.queryParams.employeeNumber = null;
      }

      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // 重置查询参数
      this.queryParams.searchType = "name";
      this.queryParams.searchKeyword = null;
      this.queryParams.name = null;
      this.queryParams.employeeNumber = null;
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
      this.title = "添加人员管理";
      this.btnflg = true;
      this.jtlist = [
        {
          name: null,
          characterRelationship: null,
          phoneNumber: null,
          unitName: null,
        },
      ];
    },
    /** 查看按钮操作 */
    handlesee(row) {
      console.log("查看", row);
      this.reset();
      const id = row.id || this.ids;
      getPeople(id)
        .then((response) => {
          console.log("response", response);
          this.form = response.data;
          this.jtlist = response.data.familyInformation;
          //家庭人员判空处理
          console.log(
            "this.form.familyInformation",
            this.form.familyInformation,
          );
          if (
            this.form.familyInformation &&
            this.form.familyInformation.length > 0
          ) {
            this.jtlist = response.data.familyInformation;
          } else {
            this.jtlist = [
              {
                name: null,
                characterRelationship: null,
                phoneNumber: null,
                unitName: null,
              },
            ];
          }
          this.bdglPeopleList = response.data.bdglPeopleList;
          this.open = true;
          this.title = "查看人员管理";
          this.btnflg = false;
        })
        .catch((err) => {
          console.error("getPeople请求失败", err);
        });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPeople(id).then((response) => {
        this.form = response.data;
        //家庭人员判空处理
        if (this.form.familyInformation.length > 0) {
          this.jtlist = response.data.familyInformation;
        } else {
          this.jtlist = [
            {
              name: null,
              characterRelationship: null,
              phoneNumber: null,
              unitName: null,
            },
          ];
        }

        this.bdglPeopleList = response.data.bdglPeopleList;
        this.open = true;
        this.title = "修改人员管理";
        this.btnflg = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.readmeFile) {
            this.form.readmeFile = this.form.readmeFile[0].name;
          }
          this.form.deptName = this.$refs["deptId"].selectedLabel;
          this.form.bdglPeopleList = this.bdglPeopleList;
          this.form.familyInformation = this.jtlist;
          if (this.form.id != null) {
            updatePeople(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.familyInformation = this.jtlist;
            this.form.deptName = this.$refs["deptId"].selectedLabel;
            addPeople(this.form).then((response) => {
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
        .confirm("是否确认删除人员管理的数据项？")
        .then(function () {
          return delPeople(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 人员管理序号 */
    rowBdglPeopleIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 人员管理添加按钮操作 */
    handleAddBdglPeople() {
      let obj = {};
      obj.name = "";
      obj.sex = "";
      obj.age = "";
      obj.birthDate = "";
      obj.command = "";
      obj.duty = "";
      obj.postId = "";
      obj.gradeId = "";
      obj.postDate = "";
      obj.job = "";
      obj.skillId = "";
      obj.skillDate = "";
      obj.levelId = "";
      obj.levelDate = "";
      obj.military = "";
      obj.idcard = "";
      obj.enlistDate = "";
      obj.enlistaddress = "";
      obj.rankId = "";
      obj.rankDate = "";
      obj.nationId = "";
      obj.origin = "";
      obj.classId = "";
      obj.educationId = "";
      obj.degreeId = "";
      obj.graduationDate = "";
      obj.school = "";
      obj.partyId = "";
      obj.partyDate = "";
      obj.training = "";
      obj.readmeFile = "";
      obj.readmeFileName = "";
      obj.prize = "";
      obj.marryList = "";
      obj.childrenList = "";
      obj.unitId = "";
      obj.avatar = "";
      obj.address = "";
      obj.phone = "";
      obj.status = "";
      obj.createtimes = "";
      obj.updatetimes = "";
      obj.reign = "";
      obj.jobTime = "";
      obj.healthy = "";
      obj.bloodType = "";
      obj.postTime = "";
      obj.major = "";
      obj.admissionTime = "";
      obj.mobilePhoneModel = "";
      obj.guaranteeCardNo = "";
      obj.payrollCardNo = "";
      obj.authorizedStrength = "";
      obj.aboral = "";
      obj.live = "";
      obj.userId = "";
      obj.nowMilitaryRank = "";
      obj.treatmentLevel = "";
      obj.rankUpgradeTime = "";
      this.bdglPeopleList.push(obj);
    },
    /** 人员管理删除按钮操作 */
    handleDeleteBdglPeople() {
      if (this.checkedBdglPeople.length == 0) {
        this.$modal.msgError("请先选择要删除的人员管理数据");
      } else {
        const bdglPeopleList = this.bdglPeopleList;
        const checkedBdglPeople = this.checkedBdglPeople;
        this.bdglPeopleList = bdglPeopleList.filter(function (item) {
          return checkedBdglPeople.indexOf(item.index) == -1;
        });
      }
    },
    /** 复选框选中数据 */
    handleBdglPeopleSelectionChange(selection) {
      this.checkedBdglPeople = selection.map((item) => item.index);
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有人员管理数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportPeople(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    //新增家庭成员
    tiaddfun() {
      this.jtlist.push({
        name: null,
        characterRelationship: null,
        phoneNumber: null,
        unitName: null,
      });
    },
    //删除家庭成员
    jtDelete(i) {
      this.jtlist.splice(i, 1);
    },
    //身份证号
    idcardfun() {
      this.form.sex =
        this.form.idcard.substr(this.form.idcard.length - 2, 1) % 2 == 1
          ? "0"
          : "1";
      this.form.birthDate =
        this.form.idcard.substr(6, 4) +
        "-" +
        this.form.idcard.substr(10, 2) +
        "-" +
        this.form.idcard.substr(12, 2);
      var myDate = new Date();
      this.form.age = myDate.getFullYear() - this.form.idcard.substr(6, 4);
      listPeople({ idcard: this.form.idcard }).then((response) => {
        if (response.rows.length > 0) {
          this.form.idcard = null;
          this.form.age = null;
          this.form.birthDate = null;
          this.form.sex = null;
          this.$modal.msgError("该人员已存在，无需重复添加");
        }
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
    // 显示导入对话框
    showImportDialog() {
      this.importDialogVisible = true;
      if (this.$refs.upload) {
        this.$refs.upload.clearFiles();
      }
      this.uploadFile = null;
    },
    // 处理文件选择
    handleFileChange(file, fileList) {
      this.uploadFile = file;
    },
    // 下载Excel模板
    downloadTemplate() {
      downloadTemplate()
        .then((response) => {
          const blob = new Blob([response], {
            type: "application/vnd.ms-excel",
          });
          const url = window.URL.createObjectURL(blob);
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", "人员导入模板.xlsx");
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          window.URL.revokeObjectURL(url);
        })
        .catch((error) => {
          this.$modal.msgError("模板下载失败");
          console.error("模板下载失败:", error);
        });
    },
    // 提交导入
    submitImport() {
      if (!this.uploadFile) {
        this.$modal.msgError("请选择要导入的文件");
        return;
      }

      this.importLoading = true;

      // 调用后端导入接口
      importPeople(this.uploadFile.raw)
        .then((response) => {
          if (response.code === 200) {
            this.$modal.msgSuccess("导入成功");
            this.importDialogVisible = false;
            this.getList(); // 重新加载列表
          }
        })
        .catch((error) => {
          // this.$modal.msgError("11111111111"+error);
        })
        .finally(() => {
          this.importLoading = false;
          this.uploadFile = null;
        });
    },
    // 关闭导入对话框
    closeImportDialog() {
      this.importDialogVisible = false;
      if (this.$refs.upload) {
        this.$refs.upload.clearFiles();
      }
      this.uploadFile = null;
    },
  },
};
</script>
<style scoped>
/* 查询表单样式修复 */
::v-deep .el-form--inline .el-form-item {
  margin-right: 10px;
  margin-bottom: 10px;
}

::v-deep .el-form--inline .el-form-item__label {
  color: #fff;
  padding-right: 8px;
}

::v-deep .el-dialog {
  width: 1100px !important;
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
  background: transparent !important;
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

/* 导入对话框样式 */
.import-dialog-content {
  padding: 20px 0;
}

.import-buttons {
  margin-top: 20px;
  text-align: center;
}
.el-upload--picture-card /deep/ .el-upload--picture-card i {
  font-size: 48px;
}
::v-deep .el-upload__tip {
  display: none;
}
::v-deep .el-textarea__inner {
  width: 990px;
  height: 104px;
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
  color: #1c86e1;
}
.el-select-dropdown__item {
  color: #000 !important;
}
.el-tree {
  background-color: #00365f;
  color: #fff;
}
::v-deep .el-tree-node__content:hover {
  color: #fff;
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
::v-deep
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  color: #fff !important;
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
::v-deep .el-tree-node__label {
  color: #fff !important;
}
::v-deep .el-upload-list--picture-card .el-upload-list__item {
  height: 210px;
  width: 170px;
}
.dadui {
  height: 40px;
  background: #003156;
  /* border-bottom: 1px solid #718A9D ; */
  background-image: url("../../../assets/images/矩形 1386 拷贝.png");
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
::v-deep .tree .el-tree {
  min-height: 740px;
}
::v-deep .el-tree-node__content {
  height: 32px;
}
::v-deep .el-dialog__body {
  padding: 0px 0px;
  padding-top: 30px;
  padding-bottom: 30px;
  padding-left: 50px;
}
::v-deep .el-dialog__footer {
  padding-right: 60px;
}
/* 家庭信息单位输入框 */
.input {
  outline: none;
  color: #fff;
  background-color: transparent;
  border: 1px solid #fff;
  height: 36px;
  width: 521px;
  border-radius: 4px;
  padding: 0px 18px;
  margin-right: 230px;
}
::v-deep img {
  object-fit: cover;
}
</style>
