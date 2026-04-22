<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!-- <el-form-item label="单位" prop="unitId">
        <el-input
          v-model="queryParams.unitId"
          placeholder="请输入单位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item prop="peopleName">
        <el-input
          v-model="queryParams.peopleName"
          placeholder="请输入疗养人"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <!-- <el-button type="primary" size="btn" @click="handleQuery"
          >搜索</el-button
        > -->
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
          v-hasPermi="['medicalhealth:recuperate:add']"
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
          v-hasPermi="['medicalhealth:recuperate:edit']"
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
          v-hasPermi="['medicalhealth:recuperate:remove']"
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
          v-hasPermi="['medicalhealth:recuperate:export']"
          >导出</el-button
        >
      </el-col> -->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="recuperateList"
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
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="人员名称" align="center" prop="peopleName" />
      <el-table-column label="岗位" align="center" prop="job" />
      <el-table-column label="疗养类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.convalescent_type"
            :value="scope.row.type"
          />
        </template>
      </el-table-column>
      <el-table-column label="疗养天数" align="center" prop="number" />
      <el-table-column label="疗养地点" align="center" prop="address" />
      <el-table-column
        label="开始时间"
        align="center"
        prop="startTime"
        width="120"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="结束时间"
        align="center"
        prop="endTime"
        width="120"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column
        label="一级审批人"
        align="center"
        prop="peopleName1"
        width="130"
      />
      <el-table-column
        label="一级审批状态"
        align="center"
        prop="updatetime"
        width="150"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.status1 == '1'" style="color: #13ce66"
            >通过</span
          >
          <span
            v-if="scope.row.status1 == '2'"
            style="color: #00eaff"
            :title="scope.row.reasons"
            >驳回</span
          >
          <span v-if="scope.row.status1 == '0'" style="color: #ffba00"
            >未审批</span
          >
        </template>
      </el-table-column>
      <el-table-column
        label="二级审批人"
        align="center"
        prop="peopleName2"
        width="130"
      />
      <el-table-column
        label="二级审批状态"
        align="center"
        prop="unitId2"
        width="150"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.status2 == '1'" style="color: #13ce66"
            >通过</span
          >
          <span
            v-if="scope.row.status2 == '2'"
            style="color: #00eaff"
            :title="scope.row.reasons"
            >驳回</span
          >
          <span v-if="scope.row.status2 == '0'" style="color: #ffba00"
            >未审批</span
          >
        </template>
      </el-table-column>
      <el-table-column
        label="三级审批人"
        align="center"
        prop="peopleName3"
        width="130"
      />
      <el-table-column
        label="三级审批状态"
        align="center"
        prop="peopleId1"
        width="150"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.status3 == '1'" style="color: #13ce66"
            >通过</span
          >
          <span
            v-if="scope.row.status3 == '2'"
            style="color: #00eaff"
            :title="scope.row.reasons"
            >驳回</span
          >
          <span v-if="scope.row.status3 == '0'" style="color: #ffba00"
            >未审批</span
          >
        </template>
      </el-table-column>
      <el-table-column
        label="人员状态"
        align="center"
        prop="peopleState1"
        width="150"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.peopleState1 == '1'" style="color: #13ce66"
            >归队</span
          >
          <span
            v-if="scope.row.peopleState1 == '2'"
            style="color: #ffba00"
            :title="scope.row.reasons"
            >疗养中</span
          >
          <span v-if="scope.row.peopleState1 == '0'" style="color: #13ce66"
            >在位</span
          >
        </template>
      </el-table-column>
      <!-- <el-table-column label="申请时间" align="center" prop="peopleId2" /> -->
      <!-- <el-table-column label="更新时间" align="center" prop="peopleId3" /> -->
      <!-- <el-table-column label="整体审批状态" align="center" prop="status" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="190"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.common == '5'"
            size="btu"
            type="text"
            @click="handleshenpi5(scope.row)"
            >归队</el-button
          >
          <el-button
            v-if="scope.row.common == '1'"
            size="btk"
            type="text"
            @click="handleshenpi1(scope.row)"
            >审批</el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medicalhealth:recuperate:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medicalhealth:recuperate:remove']"
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

    <!-- 添加或修改疗养管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
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
          <!-- <el-input v-model="form.unitId" placeholder="请输入单位" /> -->
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="人员姓名" prop="peopleId">
          <!-- <el-input v-model="form.peopleId" placeholder="请输入疗养人" /> -->
          <el-select v-model="form.peopleId" placeholder="请选择请假人员">
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="qingRen(item.name)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="疗养类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择疗养类型">
            <el-option
              v-for="(item, i) in liaoYang"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="疗养天数" prop="number">
          <el-input v-model="form.number" placeholder="请输入疗养天数" />
        </el-form-item>
        <el-form-item label="疗养地点" prop="address">
          <el-input v-model="form.address" placeholder="请输入疗养地点" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="一级审批单位" prop="unitId1">
          <treeselect
            v-model="form.unitId1"
            :options="deptOptionsQ"
            placeholder="请选择一级审批单位"
            @select="selectPeo1"
          />
        </el-form-item>
        <el-form-item label="一级审批人" prop="peopleId1">
          <!-- <el-input v-model="form.peopleId1" placeholder="请输入备注" /> -->
          <el-select v-model="form.peopleId1" placeholder="请选择一级审批人">
            <el-option
              v-for="(item, i) in renYuan1"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="qingRen1(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级审批单位" prop="unitId2">
          <treeselect
            v-model="form.unitId2"
            :options="deptOptionsQ"
            placeholder="请选择二级审批单位"
            @select="selectPeo2"
          />
        </el-form-item>
        <el-form-item label="二级审批人" prop="peopleId2">
          <!-- <el-input v-model="form.peopleId2" placeholder="请输入备注" /> -->
          <el-select v-model="form.peopleId2" placeholder="请选择二级审批人">
            <el-option
              v-for="(item, i) in renYuan2"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="qingRen2(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级审批单位" prop="unitId3">
          <!-- <el-input v-model="form.peopleId2" placeholder="请输入备注" /> -->
          <treeselect
            v-model="form.unitId3"
            :options="deptOptionsQ"
            placeholder="请选择三级审批单位"
            @select="selectPeo3"
          />
        </el-form-item>
        <el-form-item label="三级审批人" prop="peopleId3">
          <!-- <el-input v-model="form.peopleId3" placeholder="请输入备注" /> -->
          <el-select v-model="form.peopleId3" placeholder="请选择三级审批人">
            <el-option
              v-for="(item, i) in renYuan3"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="qingRen3(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改 -->
    <el-dialog
      :title="title"
      :visible.sync="openXiu"
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
          <!-- <el-input v-model="form.unitId" placeholder="请输入单位" /> -->
          <treeselect
            v-model="form.unitId"
            :options="deptOptionsQ"
            placeholder="请选择单位"
            @select="selectPeo"
            :disabled="prohibit"
          />
        </el-form-item>
        <el-form-item label="人员姓名" prop="peopleId">
          <!-- <el-input v-model="form.peopleId" placeholder="请输入疗养人" /> -->
          <el-select
            v-model="form.peopleId"
            placeholder="请选择请假人员"
            :disabled="prohibit"
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="qingRen(item.name)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="疗养类型" prop="type">
          <el-select
            v-model="form.type"
            placeholder="请选择疗养类型"
            :disabled="prohibit"
          >
            <el-option
              v-for="(item, i) in liaoYang"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="疗养天数" prop="number">
          <el-input
            v-model="form.number"
            placeholder="请输入疗养天数"
            :disabled="prohibit"
          />
        </el-form-item>
        <el-form-item label="疗养地点" prop="address">
          <el-input
            v-model="form.address"
            placeholder="请输入疗养地点"
            :disabled="prohibit"
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间"
            :disabled="prohibit"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间"
            :disabled="prohibit"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="一级审批单位" prop="unitId1">
          <treeselect
            v-model="form.unitId1"
            :options="deptOptionsQ"
            placeholder="请选择一级审批单位"
            @select="selectPeo1"
            :disabled="prohibit"
          />
        </el-form-item>
        <el-form-item label="一级审批人" prop="peopleId1">
          <!-- <el-input v-model="form.peopleId1" placeholder="请输入备注" /> -->
          <el-select
            v-model="form.peopleId1"
            placeholder="请选择一级审批人"
            :disabled="prohibit"
          >
            <el-option
              v-for="(item, i) in renYuan1"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="qingRen1(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级审批单位" prop="unitId2">
          <treeselect
            v-model="form.unitId2"
            :options="deptOptionsQ"
            placeholder="请选择二级审批单位"
            @select="selectPeo2"
            :disabled="prohibit"
          />
        </el-form-item>
        <el-form-item label="二级审批人" prop="peopleId2">
          <el-select
            v-model="form.peopleId2"
            placeholder="请选择二级审批人"
            :disabled="prohibit"
          >
            <el-option
              v-for="(item, i) in renYuan2"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="qingRen2(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级审批单位" prop="unitId3">
          <treeselect
            v-model="form.unitId3"
            :options="deptOptionsQ"
            placeholder="请选择三级审批单位"
            @select="selectPeo3"
            :disabled="prohibit"
          />
        </el-form-item>
        <el-form-item label="三级审批人" prop="peopleId3">
          <el-select
            v-model="form.peopleId3"
            placeholder="请选择三级审批人"
            :disabled="prohibit"
          >
            <el-option
              v-for="(item, i) in renYuan3"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="qingRen3(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
            :disabled="prohibit"
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
      title="疗养管理审批"
      :visible.sync="opene"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="forme"
        :model="form"
        :rules="ruless"
        label-width="80px"
        inline
      >
        <el-form-item label="单位" prop="unitId">
          <!-- <el-input v-model="form.unitId" placeholder="请输入单位" /> -->
          <treeselect
            v-model="form.unitId"
            :options="deptOptionsQ"
            placeholder="请选择单位"
            @select="selectPeo"
            disabled
          />
        </el-form-item>
        <el-form-item label="人员姓名" prop="peopleId">
          <!-- <el-input v-model="form.peopleId" placeholder="请输入疗养人" /> -->
          <el-select
            v-model="form.peopleId"
            placeholder="请选择请假人员"
            disabled
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="qingRen(item.name)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="疗养类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择疗养类型" disabled>
            <el-option
              v-for="(item, i) in liaoYang"
              :key="i"
              :label="item.dictLabel"
              :value="item.dictValue"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="疗养天数" prop="number">
          <el-input
            v-model="form.number"
            placeholder="请输入疗养天数"
            disabled
          />
        </el-form-item>
        <el-form-item label="疗养地点" prop="address">
          <el-input
            v-model="form.address"
            placeholder="请输入疗养地点"
            disabled
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="一级审批单位" prop="unitId1">
          <treeselect
            v-model="form.unitId1"
            :options="deptOptionsQ"
            placeholder="请选择一级审批单位"
            @select="selectPeo1"
            disabled
          />
        </el-form-item>
        <el-form-item label="一级审批人" prop="peopleId1">
          <!-- <el-input v-model="form.peopleId1" placeholder="请输入备注" /> -->
          <el-select
            v-model="form.peopleId1"
            placeholder="请选择一级审批人"
            disabled
          >
            <el-option
              v-for="(item, i) in renYuan1"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="qingRen1(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级审批单位" prop="unitId2">
          <treeselect
            v-model="form.unitId2"
            :options="deptOptionsQ"
            placeholder="请选择二级审批单位"
            @select="selectPeo2"
            disabled
          />
        </el-form-item>
        <el-form-item label="二级审批人" prop="peopleId2">
          <el-select
            v-model="form.peopleId2"
            placeholder="请选择二级审批人"
            disabled
          >
            <el-option
              v-for="(item, i) in renYuan2"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="qingRen2(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级审批单位" prop="unitId3">
          <treeselect
            v-model="form.unitId3"
            :options="deptOptionsQ"
            placeholder="请选择三级审批单位"
            @select="selectPeo3"
            disabled
          />
        </el-form-item>
        <el-form-item label="三级审批人" prop="peopleId3">
          <el-select
            v-model="form.peopleId3"
            placeholder="请选择三级审批人"
            disabled
          >
            <el-option
              v-for="(item, i) in renYuan3"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="qingRen3(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
            disabled
          />
        </el-form-item>
        <div class="jiben">驳回原因</div>
        <el-form-item prop="reasons">
          <el-input
            v-model="form.reasons"
            type="textarea"
            placeholder="请输入驳回原因"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForms">审 批</el-button>
        <el-button @click="boHui" size="btn">驳 回</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listRecuperate,
  getRecuperate,
  delRecuperate,
  addRecuperate,
  updateRecuperate,
  exportRecuperate,
  getreciuperUser,
  getreciuperUsers,
} from "@/api/medicalhealth/recuperate";
// 查询字典
import { getDicts } from "@/api/system/dict/data";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

import {
  getDept,
  getQuanBu,
  getDept2,
} from "@/api/grassrootsregistration/bdglmeeting";
// 所有部门

export default {
  name: "Recuperate",
  dicts: ["convalescent_type"],
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
      // 疗养管理表格数据
      recuperateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 修改弹出层
      openXiu: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitId: null,
        peopleId: null,
        type: null,
        number: null,
        startTime: null,
        endTime: null,
        createtime: null,
        updatetime: null,
        unitId1: null,
        unitId2: null,
        unitId3: null,
        peopleId1: null,
        peopleId2: null,
        peopleId3: null,
        status1: null,
        status2: null,
        status3: null,
        peopleState1: null,
        peopleState2: null,
        peopleState3: null,
        people1Remark: null,
        people2Remark: null,
        people3Remark: null,
        people1Authtime: null,
        people2Authtime: null,
        people3Authtime: null,
        status: null,
        job: null,
        address: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitId1: [
          { required: true, message: "请选择一级审批单位", trigger: "blur" },
        ],
        unitId2: [
          { required: true, message: "请选择二级审批单位", trigger: "blur" },
        ],
        unitId3: [
          { required: true, message: "请选择三级审批单位", trigger: "blur" },
        ],
        peopleId1: [
          { required: true, message: "请选择一级审批人", trigger: "blur" },
        ],
        peopleId2: [
          { required: true, message: "请选择二级审批人", trigger: "blur" },
        ],
        peopleId3: [
          { required: true, message: "请选择三级审批人", trigger: "blur" },
        ],
      },
      // 疗养列表
      liaoYang: [],
      // 树形结构列表
      deptOptions: [],
      // 树形结构列表全部单位
      deptOptionsQ: [],
      // 人员列表
      renYuan: [],
      // 人员列表1
      renYuan1: [],
      // 人员列表2
      renYuan2: [],
      // 人员列表3
      renYuan3: [],
      //  审批弹出层
      opene: false,
      // 审批表单验证
      ruless: {
        reasons: [
          { required: true, message: "请输入驳回原因", trigger: "blur" },
        ],
      },
      // 禁止修改状态
      prohibit: false,
    };
  },
  created() {
    this.getList();
    getDicts("convalescent_type").then((response) => {
      this.liaoYang = response.data;
    });
    // 全部单位
    this.getUserQuan();
  },
  methods: {
    // 获取全部人员
    getRWenQ() {
      getreciuperUsers().then((res) => {
        this.renYuan2 = res.rows;
        this.renYuan1 = res.rows;
        this.renYuan3 = res.rows;
      });
    },
    // 获取全部单位
    getUserQuan() {
      getDept2().then((res) => {
        // console.log(res);
        this.deptOptionsQ = res.data;
      });
    },
    // 人员姓名选中
    qingRen(name) {
      this.form.peopleName = name;
    },
    // 人员姓名选中1
    qingRen1(name) {
      this.form.peopleName1 = name;
    },
    // 人员姓名选中2
    qingRen2(name) {
      this.form.peopleName2 = name;
    },
    // 人员姓名选中3
    qingRen3(name) {
      this.form.peopleName3 = name;
    },
    // 获取人员
    getRen(id) {
      getQuanBu(id).then((response) => {
        this.renYuan = response.data;
      });
    },
    // 获取人员1
    getRen1(id) {
      getreciuperUser(id).then((response) => {
        this.renYuan1 = response.rows;
      });
    },
    // 获取人员2
    getRen2(id) {
      getreciuperUser(id).then((response) => {
        this.renYuan2 = response.rows;
      });
    },
    // 获取人员3
    getRen3(id) {
      getreciuperUser(id).then((response) => {
        this.renYuan3 = response.rows;
      });
    },
    // 选择部门单位触发
    selectPeo(data) {
      this.form.unitId = data.id;
      this.form.unitName = data.label;
      this.form.peopleId = null;
      this.getRen(this.form.unitId);
    },
    // 一级审批单位触发
    selectPeo1(data) {
      this.form.unitId1 = data.id;
      this.form.unitName1 = data.label;
      this.form.peopleId1 = null;
      this.getRen1(this.form.unitId1);
    },
    // 二级审批单位触发
    selectPeo2(data) {
      this.form.unitId2 = data.id;
      this.form.unitName2 = data.label;
      this.form.peopleId2 = null;
      this.getRen2(this.form.unitId2);
    },
    // 三级审批单位触发
    selectPeo3(data) {
      this.form.unitId3 = data.id;
      this.form.unitName3 = data.label;
      this.form.peopleId3 = null;
      this.getRen3(this.form.unitId3);
    },
    // 获取部门列表
    getBuMeng() {
      getDept().then((res) => {
        this.deptOptions = res.data;
      });
    },
    /** 查询疗养管理列表 */
    getList() {
      this.loading = true;
      listRecuperate(this.queryParams).then((response) => {
        this.recuperateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openXiu = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        unitId: null,
        peopleId: null,
        type: null,
        number: null,
        startTime: null,
        endTime: null,
        remark: null,
        createtime: null,
        updatetime: null,
        unitId1: null,
        unitId2: null,
        unitId3: null,
        peopleId1: null,
        peopleId2: null,
        peopleId3: null,
        status1: null,
        status2: null,
        status3: null,
        peopleState1: null,
        peopleState2: null,
        peopleState3: null,
        people1Remark: null,
        people2Remark: null,
        people3Remark: null,
        people1Authtime: null,
        people2Authtime: null,
        people3Authtime: null,
        status: 0,
        job: null,
        address: null,
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
      this.getBuMeng();
      this.reset();
      this.open = true;
      this.title = "添加疗养管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      if (row.status1 == "1") {
        this.prohibit = true;
      } else {
        this.prohibit = false;
      }
      this.getUserQuan();
      this.reset();
      const id = row.id || this.ids;
      getRecuperate(id).then((response) => {
        this.form = response.data;
        this.getRen(this.form.unitId);
        // 全部人
        this.getRWenQ();
        this.openXiu = true;
        this.title = "修改疗养管理";
      });
    }, // 归队按钮

    handleshenpi5(row) {
      this.form = row;
      this.$confirm("是否确认归队?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.form.peopleState1 = 1;
          updateRecuperate(this.form).then((response) => {
            this.$modal.msgSuccess("归队成功");
            this.getList();
          });
        })
        .catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateRecuperate(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.openXiu = false;
              this.getList();
            });
          } else {
            addRecuperate(this.form).then((response) => {
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
      this.$confirm("是否确认删除疗养管理的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delRecuperate(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有疗养管理数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.exportLoading = true;
          return exportRecuperate(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    // 审批弹出层内容
    handleshenpi1(row) {
      this.getUserQuan();
      this.reset();
      const id = row.id || this.ids;
      getRecuperate(id).then((response) => {
        this.form = response.data;
        this.getRen(this.form.unitId);
        // 全部人
        this.getRWenQ();
        this.opene = true;
      });
    },
    //审批通过按钮
    submitForms() {
      this.recuperateList.forEach((item) => {
        if (item.spJiBie == "一级") {
          this.form.status1 = 1;
          this.form.status = "一级审批通过";
        } else if (item.spJiBie == "二级") {
          this.form.status2 = 1;
          this.form.status = "二级审批通过";
        } else if (item.spJiBie == "三级") {
          this.form.status3 = 1;
          this.form.status = "三级级审批通过";
        }
      });
      updateRecuperate(this.form).then((response) => {
        this.$modal.msgSuccess("审批成功");
        this.opene = false;
        this.getList();
      });
    },
    // 审批驳回按钮
    boHui() {
      this.$refs["forme"].validate((valid) => {
        if (valid) {
          this.recuperateList.forEach((item) => {
            if (item.spJiBie == "一级") {
              this.form.status1 = 2;
              this.form.status = "一级级审批驳回";
            } else if (item.spJiBie == "二级") {
              this.form.status2 = 2;
              this.form.status = "二级级审批驳回";
            } else if (item.spJiBie == "三级") {
              this.form.status3 = 2;
              this.form.status = "三级级审批驳回";
            }
          });
          updateRecuperate(this.form).then((response) => {
            this.$modal.msgSuccess("审批成功");
            this.opene = false;
            this.getList();
          });
        }
      });
    },
  },
};
</script>
<style scoped>
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
  width: 115px !important;
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
  margin: 10px 30px 20px 50px;
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
/* 刷新图标 */
/* .el-icon-refresh {
  width: 76px;
  height: 36px;
  color: #fff;
  background-color: #1d96ff !important;
  border-radius: 4px;
  border: none;
  margin-left: 20px;
  font-size: 14px;
} */
/* 下拉菜单字体/背景颜色 */
.el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #004d86;
  color: #000;
}
.el-select-dropdown__item {
  color: #000;
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
  padding: 18px 42px 30px 0px;
  margin-right: 30px;
}
::v-deep .el-dialog {
  width: 1100px !important;
  background: #004d86 !important;
}
::v-deep .el-dialog__body {
  padding: none !important;
}
::v-deep .box4 .el-textarea__inner {
  min-height: 120px !important;
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
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent !important;
  color: #fff;
}
</style>
