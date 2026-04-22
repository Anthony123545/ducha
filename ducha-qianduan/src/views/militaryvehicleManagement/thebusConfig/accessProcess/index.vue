<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="unitId">
        <el-input
          v-model="queryParams.peopleName"
          placeholder="请输入用车人姓名"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item>
        <!-- <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        > -->
        <el-button size="btr" @click="handleQuery">查询</el-button>
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
          v-hasPermi="['militaryvehicleManagement:thebusApply:add']"
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
          v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
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
          v-hasPermi="['militaryvehicleManagement:thebusApply:remove']"
        >删除</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          class="box"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['militaryvehicleManagement:thebusApply:export']"
          >导出</el-button
        >
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          size="mini"
          :disabled="piliang"
          @click="piliangList"
          v-hasPermi="['militaryvehicleManagement:thebusApply:examine']"
        >批量审批</el-button
        >
      </el-col>
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>
    <!-- 列表选项 -->
    <el-table
      v-loading="loading"
      :data="thebusApplyList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" width="50">
        <template scope="scope">
          <span>{{
              (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
            }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="人数" align="center" prop="peopleCount" /> -->
      <el-table-column
        label="单位"
        align="center"
        prop="unitName"
        width="200"
      />
      <el-table-column
        label="申请人"
        align="center"
        prop="peopleName"
        width="150"
      />
      <el-table-column label="带车人" align="center" prop="pepelonamed" />
      <!-- <el-table-column
        label="联系方式"
        align="center"
        prop="contact"
        width="130"
      /> -->
      <el-table-column
        label="外出路线"
        align="center"
        prop="route"
        show-overflow-tooltip
        width="200"
      />
      <el-table-column
        label="外出事由"
        align="center"
        prop="cause"
        show-overflow-tooltip
        width="200"
      />
      <el-table-column
        label="开始时间"
        align="center"
        prop="authStime"
        width="100"
      >
        <template slot-scope="scope">
          <span>{{
              parseTime(scope.row.authStime, "{y}-{m}-{d}")
            }}<br/>
          {{
              parseTime(scope.row.authStime, "{h}:{m}:{s}")
            }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="结束时间"
        align="center"
        prop="authEtime"
        width="100"
      >
        <template slot-scope="scope">
          <span>{{
              parseTime(scope.row.authEtime, "{y}-{m}-{d}")
            }}<br/>
          {{
              parseTime(scope.row.authEtime, "{h}:{m}:{s}")
            }}
        </span>
        </template>
      </el-table-column>
      <el-table-column
        label="所在单位审批人"
        align="center"
        prop="unitPeopleName"
        width="120"
      />
      <el-table-column
        label="所在单位审批时间"
        align="center"
        prop="battaliontime"
        width="130"
      >
        <template slot-scope="scope">
          <span>{{
              parseTime(scope.row.battaliontime, "{y}-{m}-{d}")
            }}<br/>
          {{
              parseTime(scope.row.battaliontime, "{h}:{m}:{s}")
            }}
        </span>
        </template>
      </el-table-column>
      <el-table-column label="所在单位审批状态" align="center" width="130">
        <template slot-scope="scope">
          <el-col v-if="scope.row.unitPeopleState == 1" style="color: #ffba00"
          >未处理</el-col
          >
          <el-col v-if="scope.row.unitPeopleState == 2" style="color: #13ce66"
          >已审批</el-col
          >
          <el-col
            v-if="scope.row.unitPeopleState == 3"
            :title="scope.row.unitPeopleRemark"
            style="color: #00eaff"
          >驳回</el-col
          >
          <el-col
            v-if="scope.row.unitPeopleState == 4"
            :title="scope.row.unitPeopleRemark"
            style="color: #1d96ff"
          >特殊审批</el-col
          >
        </template>
      </el-table-column>
      <el-table-column label="初审状态" align="center">
        <template slot-scope="scope">
          <el-col v-if="scope.row.chushenState == 1" style="color: #ffba00">未处理</el-col>
          <el-col v-if="scope.row.chushenState == 2" style="color: #13ce66">已审批</el-col>
          <el-col v-if="scope.row.chushenState == 3" :title="scope.row.chushenRemark" style="color: #00eaff">驳回</el-col>
          <el-col v-if="scope.row.chushenState == 4" :title="scope.row.chushenRemark" style="color: #1d96ff">特殊审批</el-col>
        </template>
      </el-table-column>
      <el-table-column
        label="一级审批人"
        align="center"
        prop="people1Name"
        width="90"
      />
      <el-table-column label="一级审批状态" align="center" width="100">
        <template slot-scope="scope">
          <el-col
            v-if="scope.row.people1Id == (null || '' || undefined)"
          ></el-col>
          <el-col
            v-if="
              scope.row.people1State == 1 &&
              scope.row.people1Id != (null || '' || undefined)
            "
            style="color: #ffba00"
          >未处理</el-col
          >
          <el-col
            v-if="
              scope.row.people1State == 2 &&
              scope.row.people1Id != (null || '' || undefined)
            "
            style="color: #13ce66"
          >已审批</el-col
          >
          <el-col
            v-if="
              scope.row.people1State == 3 &&
              scope.row.people1Id != (null || '' || undefined)
            "
            :title="scope.row.people1Remark"
            style="color: #00eaff"
          >驳回</el-col
          >
          <el-col
            v-if="
              scope.row.people1State == 4 &&
              scope.row.people1Id != (null || '' || undefined)
            "
            :title="scope.row.people1Remark"
            style="color: #1d96ff"
          >特殊审批</el-col
          >
        </template>
      </el-table-column>
      <el-table-column
        label="二级审批人"
        align="center"
        prop="people2Name"
        width="90"
      />
      <el-table-column label="二级审批状态" align="center" width="100">
        <template slot-scope="scope">
          <el-col
            v-if="scope.row.people2Id == (null || '' || undefined)"
          ></el-col>
          <el-col
            v-if="
              scope.row.people2State == 1 &&
              scope.row.people2Id != (null || '' || undefined)
            "
            style="color: #ffba00"
          >未处理</el-col
          >
          <el-col
            v-if="
              scope.row.people2State == 2 &&
              scope.row.people2Id != (null || '' || undefined)
            "
            style="color: #13ce66"
          >已审批</el-col
          >
          <el-col
            v-if="
              scope.row.people2State == 3 &&
              scope.row.people2Id != (null || '' || undefined)
            "
            :title="scope.row.people2Remark"
            style="color: #00eaff"
          >驳回</el-col
          >
          <el-col
            v-if="
              scope.row.people2State == 4 &&
              scope.row.people2Id != (null || '' || undefined)
            "
            :title="scope.row.people2Remark"
            style="color: #1d96ff"
          >特殊审批</el-col
          >
        </template>
      </el-table-column>
      <el-table-column
        label="三级审批人"
        align="center"
        prop="people3Name"
        width="90"
      />
      <el-table-column label="三级审批状态" align="center" width="100">
        <template slot-scope="scope">
          <el-col
            v-if="scope.row.people3Id == (null || '' || undefined)"
          ></el-col>
          <el-col
            v-if="
              scope.row.people3State == 1 &&
              scope.row.people3Id != (null || '' || undefined)
            "
            style="color: #ffba00"
          >未处理</el-col
          >
          <el-col
            v-if="
              scope.row.people3State == 2 &&
              scope.row.people3Id != (null || '' || undefined)
            "
            style="color: #13ce66"
          >已审批</el-col
          >
          <el-col
            v-if="
              scope.row.people3State == 3 &&
              scope.row.people3Id != (null || '' || undefined)
            "
            :title="scope.row.people3Remark"
            style="color: #00eaff"
          >驳回</el-col
          >
          <el-col
            v-if="
              scope.row.people3State == 4 &&
              scope.row.people3Id != (null || '' || undefined)
            "
            :title="scope.row.people3Remark"
            style="color: #1d96ff"
          >特殊审批</el-col>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="280"
        class="shenpis"
      >
        <template slot-scope="scope">
          <div
            v-if="
              scope.row.isTeShu == '1' &&
              scope.row.shenpijiedian != '2' &&
              scope.row.yingjicaozuoren != 'oneji'
            "
            style="display: inline-block"
          >
            <!-- 1 -->
            <el-button
              size="bts"
              type="text"
              v-if="
                scope.row.people1State == '1' &&
                scope.row.shenpijiedian == '3'
              "
              @click="handleTeShuShenPi(scope.row)"
              v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
              style="width: 100px"
            >特殊审批</el-button
            >
            <!-- 2 -->
            <el-button
              size="bts"
              type="text"
              v-if="
                scope.row.people2State == '1' &&
                scope.row.shenpijiedian == '4'
              "
              @click="handleTeShuShenPi(scope.row)"
              v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
            >特殊审批</el-button
            >
            <!-- 3 -->
            <el-button
              size="bts"
              type="text"
              v-if="
                scope.row.people3State == '1' &&
                scope.row.shenpijiedian == '5'
              "
              @click="handleTeShuShenPi(scope.row)"
              v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
            >特殊审批</el-button
            >
          </div>
          <div
            v-if="scope.row.status == '1' && scope.row.isCaoZuo == '0'"
            style="display: inline-block"
          >
            <!-- 1 -->
            <el-button
              size="btc"
              type="text"
              v-if="
                scope.row.unitPeopleState == '1' &&
                scope.row.shenpijiedian == '1' &&
                scope.row.yingjicaozuoren == 'yingji'
              "
              @click="handleShenPi(scope.row)"
              v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
            >审批</el-button
            >
            <!-- 2 -->
            <el-button
              size="btc"
              type="text"
              v-if="
                scope.row.chushenState == '1' &&
                scope.row.shenpijiedian == '2' &&
                scope.row.chushenyuan == 'chushen'
              "
              @click="handleShenPi(scope.row)"
              v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
            >审批</el-button
            >

            <!-- 3 -->
            <el-button
              size="btc"
              type="text"
              v-if="
                scope.row.people1State == '1' &&
                scope.row.shenpijiedian == '3'
              "
              @click="handleShenPi(scope.row)"
              v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
            >审批</el-button
            >
            <!-- 4 -->
            <el-button
              size="btc"
              type="text"
              v-if="
                scope.row.peoplet2State == '1' &&
                scope.row.shenpijiedian == '4'
              "
              @click="handleShenPi(scope.row)"
              v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
            >审批</el-button
            >
            <!-- 5 -->
            <el-button
              size="btc"
              type="text"
              v-if="
                scope.row.people3State == '1' &&
                scope.row.shenpijiedian == '5'
              "
              @click="handleShenPi(scope.row)"
              v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
            >审批</el-button
            >
          </div>
          <el-button
            size="btbo"
            type="text"
            v-if="
              scope.row.unitPeopleState == 3 ||
              scope.row.chushenState == 3 ||
              scope.row.people1State == 3 ||
              scope.row.people2State == 3 ||
              scope.row.people3State == 3
            "
            @click="handleUpdates(scope.row)"
          >驳回原因</el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusApply:edit']"
          >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['militaryvehicleManagement:thebusApply:remove']"
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

    <!-- 添加或修改用车申请对话框 -->
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
          <treeselect
            v-model="form.unitId"
            :options="treeList"
            placeholder="选择单位"
            @select="selectPeo"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="人数" prop="peopleCount">
          <el-input
            v-model="form.peopleCount"
            placeholder="请输入人数"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="申请人员" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请选择申请人员"
            filterable
            :disabled="dislable"
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
            v-model="form.pepelonamed"
            placeholder="请选择带车人员"
            filterable
            :disabled="dislable"
            clearable
          >
            <el-option
              v-for="(item, i) in daiList"
              :key="i"
              :label="item.name"
              :value="item.name"
              class="input_xiala"
              @click.native="peopelName(item)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="contact">
          <el-input
            v-model="form.contact"
            placeholder="请输入联系方式"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="用车型号" prop="model">
<!--          <el-input-->
<!--            v-model="form.model"-->
<!--            placeholder="请输入用车型号"-->
<!--            :disabled="dislable"-->
<!--          />-->
          <el-select v-model="form.model" placeholder="请输入车辆型号">
            <el-option
                v-for="(item, i) in brand"
                :key="i"
                :label="item.dictLabel"
                :value="item.dictValue"
                class="input_xiala"
                @click.native="getCatPai(item)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出车路线" prop="route">
          <el-input
            v-model="form.route"
            placeholder="请输入出车路线"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="用车开始时间" prop="authStime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.authStime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="用车开始时间"
            :disabled="dislable"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="用车结束时间" prop="authEtime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.authEtime"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="用车结束时间"
            :disabled="dislable"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="单位审批人" prop="unitPeopleId">
          <el-select
            v-model="form.unitPeopleId"
            placeholder="请选择单位审批人"
            filterable
            :disabled="dislable"
          >
            <el-option
              v-for="(item, i) in userRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="yinJi(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批模板" prop="thebusThemplateId">
          <el-select
            v-model="form.thebusThemplateId"
            placeholder="请选择审批模板"
            filterable
            :disabled="dislable"
          >
            <el-option
              v-for="(item, i) in shenPi"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="shenPiThemplate(item)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一级审批人" prop="people1Id">
          <el-input
            v-model="form.people1Name"
            placeholder="请输入一级审批人"
            class="input_xiala"
            readonly
          />
        </el-form-item>
        <el-form-item label="二级审批人" prop="people2Id">
          <el-input
            v-model="form.people2Name"
            placeholder="请输入二级审批人"
            class="input_xiala"
            readonly
          />
        </el-form-item>
        <el-form-item label="三级审批人" prop="people3Id">
          <el-input
            v-model="form.people3Name"
            placeholder="请输入三级审批人"
            class="input_xiala"
            readonly
          />
        </el-form-item>
        <div class="jiben">外出事由</div>
        <el-form-item label="" prop="cause">
          <el-input
            v-model="form.cause"
            placeholder="请输入外出事由"
            type="textarea"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 审批对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="opens"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="forms"
        :model="form"
        :rules="formRules"
        label-width="80px"
        inline
      >
        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="treeList"
            placeholder="选择单位"
            @select="selectPeo"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="人数" prop="peopleCount">
          <el-input
            v-model="form.peopleCount"
            placeholder="请输入人数"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="申请人员" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请选择申请人员"
            filterable
            :disabled="dislable"
            clearable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="cheRen(item.name)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="带车人员" prop="pepelonamed">
          <el-select
            v-model="form.pepelonamed"
            placeholder="请选择带车人员"
            filterable
            :disabled="dislable"
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
          <el-input
            v-model="form.contact"
            placeholder="请输入联系方式"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="用车型号" prop="model">
<!--          <el-input-->
<!--            v-model="form.model"-->
<!--            placeholder="请输入用车型号"-->
<!--            :disabled="dislable"-->
<!--          />-->
          <el-select v-model="form.model" placeholder="请输入车辆型号">
            <el-option
                v-for="(item, i) in brand"
                :key="i"
                :label="item.dictLabel"
                :value="item.dictValue"
                class="input_xiala"
                @click.native="getCatPai(item)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出车路线" prop="route">
          <el-input
            v-model="form.route"
            placeholder="请输入出车路线"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="用车开始时间" prop="authStime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.authStime"
            type="datetime"
            placeholder="用车开始时间"
            :disabled="dislable"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="用车结束时间" prop="authEtime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.authEtime"
            type="datetime"
            placeholder="用车结束时间"
            :disabled="dislable"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="所在单位审批" prop="unitPeopleId">
          <el-select
            v-model="form.unitPeopleId"
            placeholder="请选择所在单位审批人"
            filterable
            :disabled="dislable"
          >
            <el-option
              v-for="(item, i) in userRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="yinJi(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批模板" prop="thebusThemplateId">
          <el-select
            v-model="form.thebusThemplateId"
            placeholder="请选择审批模板"
            filterable
            :disabled="dislable"
          >
            <el-option
              v-for="(item, i) in shenPi"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="shenPiThemplate(item)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一级审批人" prop="people1Id">
          <el-input
            v-model="form.people1Name"
            placeholder="请输入一级审批人"
            readonly
          />
        </el-form-item>
        <el-form-item label="二级审批人" prop="people2Id">
          <el-input
            v-model="form.people2Name"
            placeholder="请输入二级审批人"
            readonly
          />
        </el-form-item>
        <el-form-item label="三级审批人" prop="people3Id">
          <el-input
            v-model="form.people3Name"
            placeholder="请输入三级审批人"
            readonly
          />
        </el-form-item>
        <div class="jiben">外出事由</div>
        <el-form-item label="" prop="cause">
          <el-input
            v-model="form.cause"
            placeholder="请输入外出事由"
            type="textarea"
          />
        </el-form-item>
        <div class="jiben">不通过原因</div>
        <el-form-item prop="routes">
          <el-input v-model="form.routes" type="textarea" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormShenPi">通过</el-button>
        <el-button @click="cancelShenPi" size="btn">不通过</el-button>
      </div>
    </el-dialog>

    <!-- 特殊审批对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="opensTe"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="formsTe"
        :model="form"
        :rules="formRulesTe"
        label-width="80px"
        inline
      >
        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="treeList"
            placeholder="选择单位"
            @select="selectPeo"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="人数" prop="peopleCount">
          <el-input
            v-model="form.peopleCount"
            placeholder="请输入人数"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="申请人员" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请选择申请人员"
            filterable
            :disabled="dislable"
            clearable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="cheRen(item.name)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="带车人员" prop="pepelonamed">
          <el-select
            v-model="form.pepelonamed"
            placeholder="请选择带车人员"
            filterable
            :disabled="dislable"
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
          <el-input
            v-model="form.contact"
            placeholder="请输入联系方式"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="用车型号" prop="model">
<!--          <el-input-->
<!--            v-model="form.model"-->
<!--            placeholder="请输入用车型号"-->
<!--            :disabled="dislable"-->
<!--          />-->
          <el-select v-model="form.model" placeholder="请输入车辆型号">
            <el-option
                v-for="(item, i) in brand"
                :key="i"
                :label="item.dictLabel"
                :value="item.dictValue"
                class="input_xiala"
                @click.native="getCatPai(item)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出车路线" prop="route">
          <el-input
            v-model="form.route"
            placeholder="请输入出车路线"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="用车开始时间" prop="authStime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.authStime"
            type="datetime"
            placeholder="用车开始时间"
            :disabled="dislable"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="用车结束时间" prop="authEtime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.authEtime"
            type="datetime"
            placeholder="用车结束时间"
            :disabled="dislable"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="所在单位审批" prop="unitPeopleId">
          <el-select
            v-model="form.unitPeopleId"
            placeholder="请选择所在单位审批人"
            filterable
            :disabled="dislable"
          >
            <el-option
              v-for="(item, i) in userRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="yinJi(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批模板" prop="thebusThemplateId">
          <el-select
            v-model="form.thebusThemplateId"
            placeholder="请选择审批模板"
            filterable
            :disabled="dislable"
          >
            <el-option
              v-for="(item, i) in shenPi"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="shenPiThemplate(item)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一级审批人" prop="people1Id">
          <el-input
            v-model="form.people1Name"
            placeholder="请输入一级审批人"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="二级审批人" prop="people2Id">
          <el-input
            v-model="form.people2Name"
            placeholder="请输入二级审批人"
            :disabled="dislable"
          />
        </el-form-item>
        <el-form-item label="三级审批人" prop="people3Id">
          <el-input
            v-model="form.people3Name"
            placeholder="请输入三级审批人"
            :disabled="dislable"
          />
        </el-form-item>
        <div class="jiben">外出事由</div>
        <el-form-item label="" prop="cause">
          <el-input
            v-model="form.cause"
            placeholder="请输入外出事由"
            type="textarea"
          />
        </el-form-item>
        <div class="jiben">特殊审批原因</div>
        <el-form-item prop="teShuYuanYing">
          <el-input v-model="form.teShuYuanYing" type="textarea" />
        </el-form-item>
        <!-- <div class="jiben">不通过原因</div>
        <el-form-item prop="routes">
          <el-input v-model="form.routes" type="textarea" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormTeShenPi">通过</el-button>
        <el-button @click="cancelTeShenPi" size="btn">驳回</el-button>
      </div>
    </el-dialog>

    <!-- 驳回查看对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open1"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="forms"
        :model="form"
        :rules="formRules"
        label-width="80px"
        inline
      >
        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="treeList"
            placeholder="选择单位"
            @select="selectPeo"
            disabled
          />
        </el-form-item>
        <el-form-item label="人数" prop="peopleCount">
          <el-input
            v-model="form.peopleCount"
            placeholder="请输入人数"
            disabled
          />
        </el-form-item>
        <el-form-item label="申请人员" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请选择申请人员"
            filterable
            disabled
            clearable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="cheRen(item.name)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="带车人员" prop="pepelonamed">
          <el-select
            v-model="form.pepelonamed"
            placeholder="请选择带车人员"
            filterable
            disabled
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
          <el-input
            v-model="form.contact"
            placeholder="请输入联系方式"
            disabled
          />
        </el-form-item>
        <el-form-item label="用车型号" prop="model">
<!--          <el-input-->
<!--            v-model="form.model"-->
<!--            placeholder="请输入用车型号"-->
<!--            disabled-->
<!--          />-->
          <el-select v-model="form.model" placeholder="请输入车辆型号">
            <el-option
                v-for="(item, i) in brand"
                :key="i"
                :label="item.dictLabel"
                :value="item.dictValue"
                class="input_xiala"
                @click.native="getCatPai(item)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出车路线" prop="route">
          <el-input
            v-model="form.route"
            placeholder="请输入出车路线"
            disabled
          />
        </el-form-item>
        <el-form-item label="用车开始时间" prop="authStime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.authStime"
            type="datetime"
            placeholder="用车开始时间"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="用车结束时间" prop="authEtime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.authEtime"
            type="datetime"
            placeholder="用车结束时间"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="所在单位审批" prop="unitPeopleId">
          <el-select
            v-model="form.unitPeopleId"
            placeholder="请选择所在单位审批人"
            filterable
            disabled
          >
            <el-option
              v-for="(item, i) in userRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="yinJi(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批模板" prop="thebusThemplateId">
          <el-select
            v-model="form.thebusThemplateId"
            placeholder="请选择审批模板"
            filterable
            disabled
          >
            <el-option
              v-for="(item, i) in shenPi"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="shenPiThemplate(item)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一级审批人" prop="people1Id">
          <el-input
            v-model="form.people1Name"
            placeholder="请输入一级审批人"
            readonly
          />
        </el-form-item>
        <el-form-item label="二级审批人" prop="people2Id">
          <el-input
            v-model="form.people2Name"
            placeholder="请输入二级审批人"
            readonly
          />
        </el-form-item>
        <el-form-item label="三级审批人" prop="people3Id">
          <el-input
            v-model="form.people3Name"
            placeholder="请输入三级审批人"
            readonly
          />
        </el-form-item>
        <div class="jiben">外出事由</div>
        <el-form-item label="" prop="cause">
          <el-input
            v-model="form.cause"
            placeholder="请输入外出事由"
            type="textarea"
          />
        </el-form-item>
        <div class="jiben">不通过原因</div>
        <el-form-item >
          <el-input v-model="form.unitPeopleRemark" type="textarea" v-if="form.unitPeopleState==3&&form.unitPeopleRemark"/>
          <el-input v-model="form.chushenRemark" type="textarea" v-if="form.chushenState==3&&form.chushenRemark"/>
          <el-input v-model="form.people1Remark" type="textarea"  v-if="form.people1State==3&&form.people1Remark"/>
          <el-input v-model="form.people2Remark" type="textarea" v-if="form.people2State==3&&form.people2Remark"/>
          <el-input v-model="form.people3Remark" type="textarea"  v-if="form.people3State==3&&form.people3eRemark"/>
          <el-input v-model="form.ojtsYuanyin" type="textarea" v-if="form.people1State==3&&form.ojtsYuanyin"/>
          <el-input v-model="form.tjtsYuanyin" type="textarea" v-if="form.people2State==3&&form.tjtsYuanyin"/>
          <el-input v-model="form.sjtsYuanyin" type="textarea" v-if="form.people3State==3&&form.sjtsYuanyin"/>
        </el-form-item>
        <!-- <div class="jiben" v-if="form.tjtsYuanyin">特殊审批原因</div>
        <el-form-item prop="routes" v-if="form.tjtsYuanyin">
          <el-input v-model="form.tjtsYuanyin" type="textarea"/>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="quxiao" size="btn">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listThebusApply,
  getThebusApply,
  delThebusApply,
  addThebusApply,
  updateThebusApply,
  exportThebusApply,
  exportThebusYin,
  exportThebusChu,
  exportThebusOne,
  exportThebusTwo,
  exportThebusThree,
  examine,
} from "@/api/militaryvehicleManagement/thebusApply";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getZhuChiRen, getDept } from "@/api/militaryvehicleManagement/thebus";
import {
  listThebusConfig,
  getThebusUser,
} from "@/api/militaryvehicleManagement/thebusConfig";
import { getAll, getUser } from "@/api/grassrootsregistration/bdglmeeting";
import {listThebusThemplate} from "@/api/militaryvehicleManagement/thebusThemplate";
import {getDicts} from "@/api/system/dict/data";

export default {
  name: "ThebusApply",
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
      // 批量多个禁用
      piliang: true,
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
        model: null,
        unitId: null,
        peopleId: null,
        thebusId: null,
        contact: null,
        cause: null,
        authStime: null,
        authEtime: null,
        thebusThemplateId: null,
        people1Id: null,
        people1State: null,
        people1Remark: null,
        people1Authtime: null,
        people2Id: null,
        people2State: null,
        people2Remark: null,
        people2Authtime: null,
        people3Id: null,
        people3State: null,
        people3Remark: null,
        people3Authtime: null,
        status: null,
        outstatus: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        driver: null,
        driverContact: null,
        peopleCount: null,
        unitPeopleId: null,
        unitPeopleState: null,
        unitPeopleRemark: null,
        chushenState: null,
        chushenRemark: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitPeopleId: [
          { required: true, message: "请选择所在单位审批人", trigger: "blur" },
        ],
        unitId: [{ required: true, message: "请选择单位", trigger: "blur" }],
        peopleCount: [
          { required: true, message: "请输入人数", trigger: "blur" },
        ],
        peopleId: [
          { required: true, message: "请选择申请人员", trigger: "blur" },
        ],
        pepelonamed: [
          { required: true, message: "请选择带车人员", trigger: "blur" },
        ],
        contact: [
          { required: true, message: "请输入联系方式", trigger: "blur" },
        ],
        model: [{ required: true, message: "请输入用车型号", trigger: "blur" }],
        route: [{ required: true, message: "请输入出车路线", trigger: "blur" }],
        cause: [{ required: true, message: "请输入外出事由", trigger: "blur" }],
      },
      // 树形列表
      treeList: [],
      // 人员列表
      renYuan: [],
      // 获取所在单位审批人
      userRen: [],
      // 审批模板
      shenPi: [],
      // 审批是否显示弹出层
      opens: false,
      // 审批表单校验
      formRules: {
        routes: [
          { required: true, message: "请输入不通过原因", trigger: "blur" },
        ],
      },
      // 特殊审批是否显示弹出层
      opensTe: false,
      // 特殊审批表单校验·
      formRulesTe: {
        routes: [
          // { required: true, message: "请输入不通过原因", trigger: "blur" },
        ],
        teShuYuanYing: [
          { required: true, message: "请输入特殊审批原因", trigger: "blur" },
        ],
      },
      // 用车审批状态
      carApprova: [],
      teJi: true,
      // 禁止输入
      dislable: false,
      // 批量审批数组
      examine: [],
      // 带车人员列表
      daiList: [],
      // 车牌型号
      brand: [],
      // 驳回弹出层
      open1:false
    };
  },
  created() {
    this.getList();
    this.getCatPai();
    this.getSuo();
  },
  mounted() {},
  methods: {
    // 获取当前时间
    getDays() {
      let date = new Date(); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
      let Y = date.getFullYear() + "-";
      let M =
        (date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1) + "-";
      let D =
        (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + " ";
      console.log(Y + M + D);
      let H =
        (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":"; //获取当前小时数(0-23)
      let F =
        (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) +
        ":"; //获取当前分钟数(0-59)
      let S =
        (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds()) +
        ""; //获取当前秒数(0-59)
      this.form.battaliontime = Y + M + D + H + F + S;
    },
    // 带车人触发手机号
    peopelName(phone) {
      this.form.contact = phone.phone;
    },
    // 获取带车人信息
    getDaiAll() {
      getAll().then((res) => {
        this.daiList = res.data;
      });
    },
    // 获取搜索所有人
    getSuo() {
      getAll().then((res) => {
        this.renYuan = res.data;
      });
    },
    // 特殊审批通过
    submitFormTeShenPi() {
      this.$refs["formsTe"].validateField("teShuYuanYing", (valid) => {
        if (!valid) {
          if (this.form.id != null) {
            if (this.form.shenpijiedian == "1") {
              this.form.isTongguo = "0";
              this.form.yjtsYuanyin = this.form.teShuYuanYing;
              this.form.isTeShu = "1";
              exportThebusYin(this.form).then((response) => {
                this.$modal.msgSuccess("审批成功");
                this.opensTe = false;
                this.getList();
              });
            } else if (this.form.shenpijiedian == "2") {
              this.form.isTongguo = "0";
              this.form.cstsYuanyin = this.form.teShuYuanYing;
              this.form.isTeShu = "1";
              exportThebusChu(this.form).then((response) => {
                this.$modal.msgSuccess("审批成功");
                this.opensTe = false;
                this.getList();
              });
            } else if (this.form.shenpijiedian == "3") {
              this.form.isTongguo = "0";
              this.form.ojtsYuanyin = this.form.teShuYuanYing;
              this.form.isTeShu = "1";
              exportThebusOne(this.form).then((response) => {
                this.$modal.msgSuccess("审批成功");
                this.getList();
                this.opensTe = false;
              });
            } else if (this.form.shenpijiedian == "4") {
              this.form.isTongguo = "0";
              this.form.tjtsYuanyin = this.form.teShuYuanYing;
              this.form.isTeShu = "1";
              exportThebusTwo(this.form).then((response) => {
                this.$modal.msgSuccess("审批成功");
                this.getList();
                this.opensTe = false;
              });
            } else if (this.form.shenpijiedian == "5") {
              this.form.isTongguo = "0";
              this.form.sjtsYuanyin = this.form.teShuYuanYing;
              this.form.isTeShu = "1";
              exportThebusThree(this.form).then((response) => {
                this.$modal.msgSuccess("审批成功");
                this.getList();
                this.opensTe = false;
              });
            }
          }
        }
      });
    },
    // 特殊审批不通过
    cancelTeShenPi() {
      this.$refs["formsTe"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            if (this.form.shenpijiedian == "1") {
              this.form.isTongguo = "1";
              this.form.yjtsYuanyin = this.form.teShuYuanYing;
              this.form.isTeShu = "1";
              exportThebusYin(this.form).then((response) => {
                this.$modal.msgSuccess("审批成功");
                this.opensTe = false;
                this.getList();
              });
            } else if (this.form.shenpijiedian == "2") {
              this.form.isTongguo = "1";
              this.form.cstsYuanyin = this.form.teShuYuanYing;
              this.form.isTeShu = "1";
              exportThebusChu(this.form).then((response) => {
                this.$modal.msgSuccess("审批成功");
                this.opensTe = false;
                this.getList();
              });
            } else if (this.form.shenpijiedian == "3") {
              this.form.isTongguo = "1";
              this.form.ojtsYuanyin = this.form.teShuYuanYing;
              this.form.isTeShu = "1";
              exportThebusOne(this.form).then((response) => {
                this.$modal.msgSuccess("审批成功");
                this.getList();
                this.opensTe = false;
              });
            } else if (this.form.shenpijiedian == "4") {
              this.form.isTongguo = "1";
              this.form.tjtsYuanyin = this.form.teShuYuanYing;
              this.form.isTeShu = "1";
              exportThebusTwo(this.form).then((response) => {
                this.$modal.msgSuccess("审批成功");
                this.getList();
                this.opensTe = false;
              });
            } else if (this.form.shenpijiedian == "5") {
              this.form.isTongguo = "1";
              this.form.sjtsYuanyin = this.form.teShuYuanYing;
              this.form.isTeShu = "1";
              exportThebusThree(this.form).then((response) => {
                this.$modal.msgSuccess("审批成功");
                this.getList();
                this.opensTe = false;
              });
            }
          }
        }
      });
    },
    // 特殊审批按钮
    handleTeShuShenPi(row) {
      this.reset();
      this.getTreeList();
      this.getYingJi();
      this.getShenPi();
      this.getSuo();
      this.getDaiAll();
      const id = row.id || this.ids;
      if (row.shenpijiedian != "1") {
        this.dislable = true;
      }
      getThebusApply(id).then((response) => {
        this.form = response.data;
        // this.getRen(this.form.unitId);
        this.opensTe = true;
        this.title = "审批用车申请";
      });
    },
    // 审批通过
    submitFormShenPi() {
      if (this.form.shenpijiedian == "1") {
        this.getDays();
        this.form.isTongguo = "0";
        if (this.form.id != null) {
          exportThebusYin(this.form).then((response) => {
            this.$modal.msgSuccess("审批成功");
            this.opens = false;
            this.getList();
          });
          return;
        }
      } else if (this.form.shenpijiedian == "2") {
        this.form.isTongguo = "0";
        exportThebusChu(this.form).then((response) => {
          this.$modal.msgSuccess("审批成功");
          this.opens = false;
          this.getList();
        });
        return;
      } else if (this.form.shenpijiedian == "3") {
        this.form.isTongguo = "0";
        exportThebusOne(this.form).then((response) => {
          this.$modal.msgSuccess("审批成功");
          this.getList();
          this.opens = false;
        });
        return;
      } else if (this.form.shenpijiedian == "4") {
        this.form.isTongguo = "0";
        exportThebusTwo(this.form).then((response) => {
          this.$modal.msgSuccess("审批成功");
          this.getList();
          this.opens = false;
        });
        return;
      } else if (this.form.shenpijiedian == "5") {
        this.form.isTongguo = "0";
        exportThebusThree(this.form).then((response) => {
          this.$modal.msgSuccess("审批成功");
          this.getList();
          this.opens = false;
        });
      }
    },
    // 审批不通过
    cancelShenPi() {
      this.$refs["forms"].validate((valid) => {
        if (valid) {
          if (this.form.shenpijiedian == "1") {
            this.form.isTongguo = "1";
            this.form.unitPeopleRemark = this.form.routes;
            exportThebusYin(this.form).then((res) => {
              this.opens = false;
              this.getList();
            });
            return;
          } else if (this.form.shenpijiedian == "2") {
            this.form.isTongguo = "1";
            this.form.chushenRemark = this.form.routes;
            exportThebusChu(this.form).then((res) => {
              this.opens = false;
              this.getList();
            });
            return;
          } else if (this.form.shenpijiedian == "3") {
            this.form.isTongguo = "1";
            this.form.people1Remark = this.form.routes;
            exportThebusOne(this.form).then((res) => {
              this.opens = false;
              this.getList();
            });
            return;
          } else if (this.form.shenpijiedian == "4") {
            this.form.isTongguo = "1";
            this.form.people2Remark = this.form.routes;
            exportThebusTwo(this.form).then((res) => {
              this.opens = false;
              this.getList();
            });
            return;
          } else if (this.form.shenpijiedian == "5") {
            this.form.isTongguo = "1";
            this.form.people3Remark = this.form.routes;
            exportThebusThree(this.form).then((res) => {
              this.opens = false;
              this.getList();
            });
          }
        }
      });
    },
    // 审批按钮
    handleShenPi(row) {
      this.reset();
      this.getTreeList();
      this.getYingJi();
      this.getShenPi();
      this.getDaiAll();
      const id = row.id || this.ids;
      this.dislable = true;
      getThebusApply(id).then((response) => {
        this.form = response.data;
        this.getSuo();
        this.opens = true;
        this.title = "审批用车申请";
      });
    },
    // 选择营级人员触发
    yinJi(name) {
      this.form.unitPeopleName = name;
    },
    // 选择审批模板触发
    shenPiThemplate(data) {
      this.$set(this.form, "people1Name", data.peopleName1);
      this.$set(this.form, "people2Name", data.peopleName2);
      this.$set(this.form, "people3Name", data.peopleName3);
      this.$set(this.form, "people1Id", data.people1Id);
      this.$set(this.form, "people2Id", data.people2Id);
      this.$set(this.form, "people3Id", data.people3Id);
    },
    // 获取审批模板列表
    getShenPi() {
      listThebusThemplate().then((response) => {
        console.log("获取审批模板列表", response);
        this.shenPi = response.rows;
      });
    },
    // 获取营级审批
    getYingJi() {
      getThebusUser().then((res) => {
        this.userRen = res.rows;
      });
    },
    // 选择人员触发
    cheRen(name) {
      this.form.peopleName = name;
    },
    // 获取人员列表
    getRen(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    //  获取单位列表
    getTreeList() {
      getDept().then((res) => {
        this.treeList = res.data;
      });
    },
    // 选择部门单位触发
    selectPeo(data) {
      this.form.unitName = data.label;
      this.form.peopleId = "";
      this.getRen(data.id);
    },
    getSuozaiRen(id) {
      getUser(id).then((res) => {
        this.userRen = res.data;
      });
    },
    /** 查询用车申请列表 */
    getList() {
      this.loading = true;
      listThebusApply(this.queryParams).then((response) => {
        this.thebusApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 车辆型号
    getCatPai() {
      getDicts("brand").then((response) => {
        this.brand = response.data;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        model: null,
        unitId: null,
        peopleId: null,
        thebusId: null,
        contact: null,
        cause: null,
        authStime: null,
        authEtime: null,
        thebusThemplateId: null,
        people1Id: null,
        people1State: null,
        people1Remark: null,
        people1Authtime: null,
        people2Id: null,
        people2State: null,
        people2Remark: null,
        people2Authtime: null,
        people3Id: null,
        people3State: null,
        people3Remark: null,
        people3Authtime: null,
        status: null,
        outstatus: null,
        adminId: null,
        createtime: null,
        updatetime: null,
        driver: null,
        driverContact: null,
        peopleCount: null,
        unitPeopleId: null,
        unitPeopleState: null,
        unitPeopleRemark: null,
        chushenState: null,
        chushenRemark: null,
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
      this.queryParams.peopleName = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.examine = selection;
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
      this.piliang = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.dislable = false;
      this.reset();
      this.getSuo();
      this.getYingJi();
      this.getShenPi();
      this.getTreeList();
      this.getDaiAll();
      this.open = true;
      this.title = "添加用车申请";
      var today = new Date();
      var y = today.getFullYear();
      var M = today.getMonth() + 1;
      var d = today.getDate();
      var h = today.getHours();
      var m = today.getMinutes();
      var s = today.getSeconds();
      var w = today.getDay();
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
      this.form.authStime = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
      this.form.authEtime = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
    },

    // 驳回原因按钮操作
    handleUpdates(row) {
      this.reset();
      this.getTreeList();
      this.getYingJi();
      this.getShenPi();
      this.getSuo();
      this.getDaiAll();
      const id = row.id || this.ids;
      getThebusApply(id).then((response) => {
        this.form = response.data;
        this.open1 = true;
        this.title = "驳回查看";
      });
    },
    quxiao() {
      this.reset();
      this.open1 = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.dislable = false;
      this.reset();
      this.getTreeList();
      this.getYingJi();
      this.getShenPi();
      this.getSuo();
      this.getDaiAll();
      const id = row.id || this.ids;
      if (row.shenpijiedian != "1") {
        this.dislable = true;
      }
      getThebusApply(id).then((response) => {
        this.form = response.data;
        // this.getRen(this.form.unitId);
        this.open = true;
        this.title = "修改用车申请";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateThebusApply(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addThebusApply(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    // 批量审批
    piliangList() {
      this.$confirm("是否确认批量审批", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          examine(this.examine).then((res) => {
            this.$modal.msgSuccess("审批成功");
            this.getList();
          });
        })
        .catch(() => {
        });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除用车申请的数据项？")
        .then(function () {
          return delThebusApply(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {
        });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有用车申请数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportThebusApply(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {
        });
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
}

/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}

/* 基本信息背景 */
.jiben {
  width: 920px;
  height: 32px;
  background-image: url(../../../../images/小标题底.png);
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
  color: #fff;
}

.el-select-dropdown__item {
  color: #fff;
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

/* 右侧上浮框 */
::v-deep .el-table .el-table__body .el-table__row.hover-row td {
  background-color: #004d86;
}

/* 文本域提示文字颜色 */
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}

/* 审批按钮样式 */
::v-deep .el-table_1_column_18 .cell {
  /* display: flex !important; */
  margin-top: 10px;
}

.box {
  background-color: #ffba00 !important;
}

::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent;
  color: #fff;
}

/* 驳回原因按钮 */
.el-button--btbo.is-active,
.el-button--btbo:active {
  background: #1d96ff;
  border-color: #1d96ff;
  color: #ffffff;
}

.el-button--btbo:focus,
.el-button--btbo:hover {
  background: #41a4fa;
  border-color: #41a4fa;
  color: #ffffff;
}

.el-button--btbo {
  width: 82px !important;
  border: 1px solid transparent;
  padding: 4px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #1d96ff;
}
</style>

