<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5" class="isSubmit">
        <el-radio-group v-model="aaa" @change="radioGroup">
          <el-radio-button label="1">发布任务</el-radio-button>
          <el-radio-button label="0">执行任务</el-radio-button>
        </el-radio-group>
      </el-col>
    </el-row>

    <hr />
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="" prop="taskName">
        <el-input
          v-model="queryParams.taskName"
          placeholder="请输入任务名称"
          clearable
          @input="handleQuery"
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
        <el-button icon="el-icon-refresh" type="btr" @click="resetQuery"
          >重置</el-button
        >
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
          v-if="aaa == 1"
          v-hasPermi="['workingArrangements:worktaskplan:add']"
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
          v-hasPermi="['workingArrangements:worktaskplan:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['workingArrangements:worktaskplan:remove']"
          >删除</el-button
        >
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workingArrangements:worktaskplan:export']"
          >导出</el-button
        >
      </el-col> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="worktaskplanList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="50">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="任务名称"
        align="center"
        prop="taskName"
        show-overflow-tooltip
      />
      <el-table-column
        label="开始时间"
        align="center"
        prop="startTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="结束时间"
        align="center"
        prop="endTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="任务总数"
        align="center"
        prop="taskCount"
        v-if="taskCounts"
        width="100"
      />
      <el-table-column
        label="当前进度"
        align="center"
        prop="taskProgress"
        v-if="taskProgres"
        width="100"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.taskProgress }}%</span>
        </template>
      </el-table-column>
      <el-table-column
        label="执行单位数"
        align="center"
        prop="unitCount"
        v-if="unitCounts"
        width="100"
      />
      <el-table-column
        label="备注"
        align="center"
        prop="remarkInfo"
        show-overflow-tooltip
      />

      <el-table-column
        v-if="aaa == 0"
        label="任务说明"
        align="center"
        prop="planDetail.taskDescription"
        show-overflow-tooltip
      />

      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="400"
      >
        <template slot-scope="scope">
          <el-button
            v-if="aaa == 0"
            size="btk"
            type="text"
            @click="handleUpdatebZXCK(scope.row)"
            v-hasPermi="['workingArrangements:taskplandetail:add']"
            >查看</el-button
          >
          <el-button
            v-if="aaa == 0 && scope.row.taskStatus == 1"
            size="btk"
            type="text"
            @click="handleUpdateb(scope.row)"
            v-hasPermi="['workingArrangements:taskplandetail:add']"
            >执行</el-button
          >

          <el-button
            v-if="aaa != 0"
            size="btk"
            type="text"
            @click="handleUpdatechakan(scope.row)"
            v-hasPermi="['workingArrangements:taskplandetail:add']"
            >查看</el-button
          >
          <el-button
            v-if="aaa != 0"
            size="btzx"
            type="text"
            @click="handleUpdateAdd(scope.row)"
            v-hasPermi="['workingArrangements:taskplandetail:add']"
            >添加分支任务</el-button
          >
          <el-button
            v-if="aaa != 0"
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workingArrangements:worktaskplan:edit']"
            >修改</el-button
          >
          <el-button
            v-if="aaa != 0"
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workingArrangements:worktaskplan:remove']"
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

    <!-- 添加或修改任务计划基本信息对话框 -->
    <el-dialog
      title="新增工作任务"
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      custom-class="addbox"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        inline
      >
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="form.taskName" placeholder="" class="ren_wu" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            clearable
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          >
          </el-date-picker>
        </el-form-item>
        <div class="jiben">任务附件</div>
        <fileUpload v-model="form.annexList" class="m-l-20" :limit="5" />
        <div class="jiben">备注</div>
        <el-form-item label="" prop="remarkInfo">
          <el-input
            v-model="form.remarkInfo"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加人员 -->
    <el-dialog
      :title="title"
      :visible.sync="opens"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      custom-class="box"
    >
      <el-form
        ref="form6"
        :model="form6"
        :rules="rules6"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="执行单位" prop="unitId">
          <treeselect
            v-model="form6.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="执行人员" prop="peopleId">
          <el-select v-model="form6.peopleId" placeholder="请选择执行人员">
            <el-option
              v-for="(item, i1) in executor"
              :key="i1 + ' '"
              :label="item.nickName"
              :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <div class="jiben">任务说明</div>
        <el-form-item label="" prop="taskDescription">
          <el-input
            v-model="form6.taskDescription"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForms">提 交</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改工作任务 -->
    <el-dialog
      title="修改工作任务"
      :visible.sync="openx"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
      custom-class="box2"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="form.taskName" placeholder="" />
        </el-form-item>
        <el-form-item
          label="开始时间"
          prop="startTime"
          style="margin-left: 280px"
        >
          <el-date-picker
            clearable
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发布人" prop="createBy" style="margin-left: 280px">
          <el-input v-model="form.createBy" disabled></el-input>
        </el-form-item>
        <div class="jiben">任务进度</div>
        <el-progress
          :text-inside="true"
          :stroke-width="24"
          :percentage="form.taskProgress * 1"
          color="#0079fe"
          :class="form.taskProgress * 1 < 60 ? 'a' : 'b'"
        ></el-progress>
        <div class="jiben">任务详情</div>
        <el-table
          :data="renWuXiangQingList"
          @selection-change="handleSelectionChange"
          :header-cell-style="{ background: '#2a6b9a', color: 'white' }"
        >
          <el-table-column label="序号" align="center" prop="" width="50">
            <template scope="scope">
              <span>{{
                (queryParams.pageNum - 1) * queryParams.pageSize +
                scope.$index +
                1
              }}</span>
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unitId" :formatter="unitDan" />
          <el-table-column
            label="人员"
            prop="peopleId"
            :formatter="peopleRen"
          />
          <el-table-column
            label="任务描述"
            prop="taskDescription"
            width="100"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            label="执行情况"
            prop="taskStatus"
            :formatter="taskStatusZhi"
          >
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
            width="200"
          >
            <template slot-scope="scope">
              <el-button
                size="btu"
                type="text"
                @click="handleUpdates(scope.row)"
                v-hasPermi="['workingArrangements:worktaskplan:edit']"
                >修改</el-button
              >
              <el-button
                size="btd"
                type="text"
                @click="handleUpdateEdit(scope.row)"
                v-hasPermi="['workingArrangements:worktaskplan:edit']"
                >删 除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <div class="jiben">任务附件</div>
        <div
          v-for="(item, i2) in form.annexList"
          :key="i2 + '   '"
          class="box3"
        >
          <el-input
            label="附件名称"
            v-model="item.name"
            style="width: 820px"
            disabled
          />
          <a
            :href="url + item.url"
            :download="item.name"
            title="下载"
            style="color: white; margin-left: 10px"
            >下载附件</a
          >
        </div>
        <!-- <fileUpload v-model="form.annexList" class="m-l-20" :limit="5" /> -->
        <div class="jiben">备注</div>
        <el-form-item label="" prop="remarkInfo">
          <el-input
            v-model="form.remarkInfo"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 发布人查看工作任务 -->
    <el-dialog
      title="查看工作任务"
      :visible.sync="openxc"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
      custom-class="box2"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="form.taskName" placeholder="" disabled />
        </el-form-item>
        <el-form-item
          label="开始时间"
          prop="startTime"
          style="margin-left: 280px"
        >
          <el-date-picker
            disabled
            clearable
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            disabled
            clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发布人" prop="createBy" style="margin-left: 280px">
          <el-input v-model="form.createBy" disabled></el-input>
        </el-form-item>
        <div class="jiben">任务进度</div>
        <el-progress
          :text-inside="true"
          :stroke-width="24"
          :percentage="form.taskProgress * 1"
          color="#0079fe"
          :class="form.taskProgress * 1 < 60 ? 'a' : 'b'"
        ></el-progress>
        <div class="jiben">任务详情</div>
        <el-table
          :data="renWuXiangQingList"
          @selection-change="handleSelectionChange"
          :header-cell-style="{ background: '#2a6b9a', color: 'white' }"
        >
          <el-table-column label="序号" align="center" prop="" width="50">
            <template scope="scope">
              <span>{{
                (queryParams.pageNum - 1) * queryParams.pageSize +
                scope.$index +
                1
              }}</span>
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unitId" :formatter="unitDan" />
          <el-table-column
            label="人员"
            prop="peopleId"
            :formatter="peopleRen"
          />
          <el-table-column
            label="任务描述"
            prop="taskDescription"
            width="100"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            label="执行情况"
            prop="taskStatus"
            :formatter="taskStatusZhi"
          >
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
            width="200"
          >
            <template slot-scope="scope">
              <el-button
                size="btk"
                type="text"
                @click="handleUpdateCha(scope.row)"
                v-hasPermi="['workingArrangements:worktaskplan:add']"
                >查看</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <div class="jiben">任务附件</div>
        <div
          v-for="(item, i2) in form.annexList"
          :key="i2 + '   '"
          class="box3"
        >
          <el-input
            label="附件名称"
            v-model="item.name"
            style="width: 820px"
            disabled
          />
          <a
            :href="url + item.url"
            :download="item.name"
            title="下载"
            style="color: white; margin-left: 10px"
            >下载附件</a
          >
        </div>
        <!-- <fileUpload v-model="form.annexList" class="m-l-20" :limit="5" /> -->
        <div class="jiben">备注</div>
        <el-form-item label="" prop="remarkInfo">
          <el-input
            v-model="form.remarkInfo"
            type="textarea"
            placeholder="请输入内容"
            disabled
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" type="primary">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 修改添加人员 -->
    <el-dialog
      :title="title"
      :visible.sync="openxs"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      custom-class="box"
    >
      <el-form
        ref="form"
        :model="form1"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="执行单位" prop="unitId">
          <treeselect
            v-model="form1.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeo"
          />
        </el-form-item>
        <el-form-item label="执行人员" prop="peopleId">
          <el-select v-model="form1.peopleId" placeholder="请选择执行人员">
            <el-option
              v-for="(item, i1) in executor"
              :key="i1 + ' '"
              :label="item.nickName"
              :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <div class="jiben">任务说明</div>
        <el-form-item label="" prop="taskDescription">
          <el-input
            v-model="form1.taskDescription"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormsx">提 交</el-button>
        <el-button @click="cancels">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看执行单位列表 -->
    <el-dialog
      :title="title"
      :visible.sync="openxC"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      custom-class="box4"
    >
      <el-form
        ref="form"
        :model="form3"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <div class="jiben">任务说明</div>
        <el-form-item label="执行单位" prop="unitId">
          <treeselect
            v-model="form3.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            disabled
          />
        </el-form-item>
        <el-form-item label="执行人员" prop="peopleId">
          <el-select
            v-model="form3.peopleId"
            placeholder="请选择执行人员"
            disabled
          >
            <el-option
              v-for="(item, i1) in executor"
              :key="i1 + ' '"
              :label="item.nickName"
              :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="" prop="taskDescription">
          <el-input
            v-model="form3.taskDescription"
            type="textarea"
            placeholder="请输入内容"
            disabled
          />
        </el-form-item>
        <div v-if="form3.feedbackTime">
          <div class="jiben">执行反馈</div>
          <el-form-item label="反馈时间" prop="startTime">
            <el-date-picker
              clearable
              v-model="form3.feedbackTime"
              type="date"
              value-format="yyyy-MM-dd"
              disabled
            >
            </el-date-picker>
          </el-form-item>

          <el-input
            v-model="form3.feedbackDescription"
            type="textarea"
            placeholder="请输入内容"
            disabled
          />
          <div
            v-for="(item, i3) in form3.annexList"
            :key="i3 + '    '"
            class="box3"
            style="margin-top: 20px"
          >
            <el-input
              label="附件名称"
              v-model="item.name"
              style="width: 820px"
              disabled
            />
            <a
              :href="url + item.url"
              :download="item.name"
              title="下载"
              style="color: white; margin-left: 10px"
              >下载附件</a
            >
          </div>
          <div v-if="form3.rejectContent">
            <div class="jiben">驳回原因</div>
            <el-input
              v-model="form3.rejectContent"
              type="textarea"
              placeholder=""
              disabled
            />
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancelc">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 执行人任务查看 -->
    <el-dialog
      :title="title"
      :visible.sync="openzxck"
      width="900px"
      append-to-body
      :close-on-click-modal="false"
      custom-class="box5"
    >
      <el-form
        ref="form"
        :model="form8"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="任务名称" prop="taskName">
          <el-input
            v-model="form8.taskName"
            placeholder=""
            disabled
            class="ren_wu1"
          />
        </el-form-item>
        <el-form-item
          label="开始时间"
          prop="startTime"
          style="margin-right: 155px"
        >
          <el-date-picker
            clearable
            v-model="form8.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            v-model="form8.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发布人" prop="createBy">
          <el-input v-model="form8.createBy" disabled></el-input>
        </el-form-item>
        <div
          style="height: 33px; color: #fff; font-size: 15px; padding-left: 26px"
        >
          附件
        </div>
        <div
          v-for="(item, i2) in form8.annexList"
          :key="i2 + '   '"
          class="box3"
          style="margin-left: 22px"
        >
          <el-input
            label="附件名称"
            v-model="item.name"
            style="width: 820px"
            disabled
          />
          <a
            :href="url + item.url"
            :download="item.name"
            title="下载"
            style="color: white; margin-left: 10px"
            >下载附件</a
          >
        </div>
        <div
          style="height: 33px; color: #fff; font-size: 15px; margin-left: 26px"
        >
          任务说明
        </div>
        <el-form-item label="" prop="remarkInfo" style="margin-left: 20px">
          <el-input
            v-model="form9.taskDescription"
            type="textarea"
            placeholder="请输入内容"
            disabled
          />
        </el-form-item>
        <div
          style="height: 33px; color: #fff; font-size: 15px; margin-left: 26px"
        >
          备注
        </div>
        <el-form-item label="" prop="remarkInfo" style="margin-left: 20px">
          <el-input
            v-model="form8.remarkInfo"
            type="textarea"
            placeholder="请输入内容"
            disabled
          />
        </el-form-item>
        <div v-if="form9.feedbackDescription">
          <div class="jiben">反馈说明</div>
          <el-form-item label="" prop="feedbackDescription">
            <el-input
              v-model="form9.feedbackDescription"
              type="textarea"
              placeholder="请输入内容"
              disabled
            />
          </el-form-item>
        </div>
        <div
          v-for="(item, i3) in form9.annexList"
          :key="i3 + '    '"
          class="box3"
          style="margin-left: 26px"
        >
          <el-input
            label="附件名称"
            v-model="item.name"
            style="width: 820px"
            disabled
          />
          <a
            :href="url + item.url"
            :download="item.name"
            title="下载"
            style="color: white; margin-left: 10px"
            >下载附件</a
          >
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancelb">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 执行 -->
    <el-dialog
      :title="title"
      :visible.sync="openxb"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
      custom-class="box2"
    >
      <el-form
        ref="form"
        :model="form2"
        :rules="rules"
        label-width="80px"
        :inline="true"
      >
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="form2.taskName" placeholder="" disabled />
        </el-form-item>
        <el-form-item
          label="开始时间"
          prop="startTime"
          style="margin-left: 280px"
          disabled
        >
          <el-date-picker
            clearable
            v-model="form2.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            v-model="form2.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder=""
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发布人" prop="createBy" style="margin-left: 280px">
          <el-input v-model="form2.createBy" disabled></el-input>
        </el-form-item>
        <div style="height: 33px; color: #fff; font-size: 15px">附件</div>
        <div
          v-for="(item, i2) in form2.annexList"
          :key="i2 + '   '"
          class="box3"
        >
          <el-input
            label="附件名称"
            v-model="item.name"
            style="width: 820px"
            disabled
          />
          <a
            :href="url + item.url"
            :download="item.name"
            title="下载"
            style="color: white; margin-left: 10px"
            >下载附件</a
          >
        </div>
        <div style="height: 33px; color: #fff">任务说明</div>
        <el-form-item label="" prop="remarkInfo">
          <el-input
            v-model="nairong"
            type="textarea"
            placeholder="请输入内容"
            disabled
          />
        </el-form-item>
        <div style="height: 33px; color: #fff; font-size: 15px">备注</div>
        <el-form-item label="" prop="remarkInfo">
          <el-input
            v-model="form2.remarkInfo"
            type="textarea"
            placeholder="请输入内容"
            disabled
          />
        </el-form-item>
        <div class="jiben">反馈说明</div>
        <el-form-item label="" prop="feedbackDescription">
          <el-input
            v-model="form2.feedbackDescription"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <fileUpload v-model="form2.annexLists" class="m-l-20" :limit="5" />
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormsb">提 交</el-button>
        <el-button @click="cancelb">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listWorktaskplan,
  getWorktaskplan,
  delWorktaskplan,
  addWorktaskplan,
  updateWorktaskplan,
  taskplandetail,
  getBytTskPlanId,
  updateTaskplandetail,
  getDepts,
  listUsers,
  getUserzs,
  addAnnex,
  taskplandetailIds,
  audit,
  delTaskplandetail,
  executeList,
} from "@/api/workingArrangements/worktaskplan";
import { getDept } from "@/api/grassrootsregistration/bdglmeeting";
// 获取用户信息
import { getUserProfile } from "@/api/system/user";

// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
// 查询字典
import { getDicts } from "@/api/system/dict/data";

export default {
  name: "Worktaskplan",
  components: {
    Treeselect,
  },
  props: ["task_status"],
  data() {
    return {
      url: process.env.VUE_APP_BASE_API,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedBdglWorkTaskPlanDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 任务计划基本信息表格数据
      worktaskplanList: [],
      // 任务计划执行详情表格数据
      bdglWorkTaskPlanDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 修改的弹出层
      openx: false,
      // 修改任务详情人员
      openxs: false,
      //  执行单位列表查看
      openxC: false,
      // 编辑弹出层
      openxb: false,
      // 审批的弹出层
      openxShen: false,
      // 外面查看弹出层
      openxc: false,
      // 执行人查看弹出层
      openzxck: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskName: null,
        startTime: null,
        endTime: null,
        annex: null,
        remarkInfo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        taskName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "任务开始时间不能为空", trigger: "blur" },
        ],
        endTime: [
          { required: true, message: "任务结束时间不能为空", trigger: "blur" },
        ],
      },
      // 添加执行单位人员
      opens: false,
      // 获取单位
      deptOptions: [],
      // 执行人
      executor: [],
      // 当前行的id
      ides: null,
      // 任务总进度
      comstatus: 0,
      // 任务详情列表
      renWuXiangQingList: [],
      // 任务详情列表修改
      form1: {},
      // 所有单位
      unitId: [],
      // 所有人员
      renList: [],
      // 执行情况字典
      zhiXinQingKuang: [],
      // 获取用户信息
      userS: {},
      // 编辑
      form2: {},
      // 查看
      form3: {},
      // 发布人
      fabuRen: null,
      // 审批
      form4: {},
      form5: {},
      // 添加执行单位人员
      form6: {},
      rules6: {
        unitId: [
          { required: true, message: "执行单位不能为空", trigger: "blur" },
        ],
        peopleId: [
          { required: true, message: "执行人员不能为空", trigger: "blur" },
        ],
      },
      // 编辑提交数据
      form7: {},
      usersid: null,
      // 执行人查看
      form8: {},
      form9: {},
      // 里面当前列表刷新
      LiMianId: null,
      aaa: 1,
      // 任务总数
      taskCounts: true,
      // 当前进度
      taskProgres: true,
      // 执行单位数
      unitCounts: true,
      // 执行按钮任务说明
      nairong: null,
    };
  },
  created() {
    this.getList();
    //部门数据
    getDepts().then((res) => {
      if (res.code == 200) {
        this.unitId = res.data;
      }
    });
    // 获取所有人员
    listUsers().then((res) => {
      this.renList = res.rows;
    });
    getDicts("task_status").then((response) => {
      this.zhiXinQingKuang = response.data;
    });
    this.getUser();
  },
  methods: {
    // 执行人查看
    handleUpdatebZXCK(row) {
      // console.log(row);
      this.usersid = row.planDetail.id;
      this.form8.id = row.id;
      getWorktaskplan(this.form8.id).then((res) => {
        this.form8 = res.data;
      });
      taskplandetailIds(this.usersid).then((res) => {
        this.form9 = res.data;
      });
      this.title = "查看";
      this.openzxck = true;
    },
    // 列表的两个请求
    radioGroup() {
      if (this.aaa == 1) {
        this.taskCounts = true;
        this.taskProgres = true;
        this.unitCounts = true;
        this.getList();
      } else {
        this.taskCounts = false;
        this.taskProgres = false;
        this.unitCounts = false;
        executeList(this.queryParams).then((res) => {
          this.worktaskplanList = res.rows;
          this.total = res.total;
          this.loading = false;
        });
      }
    },
    // 修改工作计划列表删除
    handleUpdateEdit(row) {
      const id = row.id;
      delTaskplandetail(id).then((res) => {
        this.getListS(this.LiMianId);
      });
    },
    btns(row) {
      if (row == 1) {
        this.aaa = 1;
      } else {
        this.aaa = 0;
      }
    },
    // 查询任务详细列表
    getListS(id) {
      getBytTskPlanId(id).then((res) => {
        this.renWuXiangQingList = res.data;
      });
    },
    // 审批通过按钮
    submitFormTong() {
      this.form5.taskStatus = 2;
      audit(this.form5).then((res) => {
        this.openxShen = false;
        this.getListS(this.LiMianId);
      });
    },
    // 驳回按钮
    cancelBo() {
      this.form5.rejectContent = this.form4.rejectContent;
      this.form5.taskStatus = 3;
      if (this.form5.rejectContent) {
        audit(this.form5).then((res) => {
          this.openxShen = false;
          this.getListS(this.LiMianId);
        });
      } else {
        this.$message.error("请填写驳回原因");
      }
    },
    // 审批按钮操作
    handleUpdateShen(row) {
      this.form5.id = row.id;
      this.getBuMeng();
      const id = row.id || this.ids;
      taskplandetailIds(id).then((res) => {
        this.form4 = res.data;
        this.getRen(this.form4.unitId);
      });
      this.openxShen = true;
    },
    // 编辑提交按钮
    submitFormsb() {
      this.form7.id = this.usersid;
      this.form7.annexList = this.form2.annexLists || null;
      this.form7.feedbackDescription = this.form2.feedbackDescription || null;
      if (this.form7.id && this.form7.feedbackDescription) {
        addAnnex(this.form7).then((res) => {
          this.openxb = false;
          this.$modal.msgSuccess("执行成功");
          executeList(this.queryParams).then((res) => {
            this.worktaskplanList = res.rows;
            this.total = res.total;
            this.loading = false;
          });
        });
      } else {
        this.$message.error("请填写反馈说明");
      }
    },
    // 点击执行查看
    getChaKan(id) {},
    // 点击执行
    handleUpdateb(row) {
      this.nairong = row.planDetail.taskDescription;
      this.usersid = row.planDetail.id;
      this.form2.id = row.id;
      getWorktaskplan(this.form2.id).then((res) => {
        this.form2 = res.data;
        //  console.log(this.form2);
      });
      this.getChaKan(this.usersid);
      this.title = "编辑";
      this.openxb = true;
    },
    // 获取登录信息
    getUser() {
      getUserProfile().then((response) => {
        this.userS = response.data;
      });
    },
    // 执行单位列表查看
    handleUpdateCha(row) {
      this.getBuMeng();
      const id = row.id || this.ids;
      taskplandetailIds(id).then((res) => {
        this.form3 = res.data;
        this.getRen(this.form3.unitId);
      });
      this.openxC = true;
      this.title = "查看";
    },
    // 执行情况翻译
    taskStatusZhi(row) {
      var name = "";
      this.zhiXinQingKuang.map((item) => {
        if (row.taskStatus == item.dictValue) {
          name = item.dictLabel;
        }
      });
      return name;
    },
    // 人员翻译
    peopleRen(row) {
      var name = "";
      this.renList.map((item) => {
        if (item.userId == row.peopleId) {
          name = item.nickName;
        }
      });
      return name;
    },
    // 单位翻译
    unitDan(row) {
      var deptName = "";
      this.unitId.map((item) => {
        if (item.deptId == row.unitId) {
          deptName = item.deptName;
        }
      });
      return deptName;
    },
    // 修改工作任务的执行人
    submitFormsx() {
      if (this.form1.id) {
        updateTaskplandetail(this.form1).then((res) => {
          this.openxs = false;
        });
      }
    },
    // 点击修改里面的按钮
    handleUpdates(row) {
      this.getBuMeng();
      this.form1 = row;
      this.getRen(this.form1.unitId);
      this.openxs = true;
    },
    // 获取执行人
    getRen(id) {
      getUserzs(id).then((res) => {
        this.executor = res.rows;
      });
    },
    // 选择部门单位触发
    selectPeo(data) {
      this.form.unitId = data.id;
      this.form.peopleId = null;
      this.form1.peopleId = null;
      this.getRen(this.form.unitId);
    },
    // 获取部门列表
    getBuMeng() {
      getDept().then((res) => {
        this.deptOptions = res.data;
      });
    },
    // 提交执行任务按钮操作
    submitForms() {
      this.$refs["form6"].validate((valid) => {
        if (valid) {
          this.form6.taskPlanId = this.ides;
          taskplandetail(this.form6).then((response) => {
            this.$modal.msgSuccess("添加成功，请继续编辑");
            this.reset();
          });
        }
      });
    },
    // 添加执行单位
    handleUpdateAdd(row) {
      this.ides = row.id;
      this.getBuMeng();
      this.opens = true;
      this.title = "添加执行单位";
    },
    /** 查询任务计划基本信息列表 */
    getList() {
      this.loading = true;
      listWorktaskplan(this.queryParams).then((response) => {
        this.worktaskplanList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.opens = false;
      this.openx = false;
      this.openxs = false;
      this.openxc = false;
      this.reset();
    },
    // 修改内部取消按钮
    cancels() {
      this.openxs = false;
    },
    // 编辑的取消
    cancelb() {
      this.form2 = {};
      this.openxb = false;
      this.openzxck = false;
      this.form8 = {};
      this.form9 = {};
    },
    // 查看取消
    cancelc() {
      this.form3 = {};
      this.openxC = false;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        taskName: null,
        startTime: null,
        endTime: null,
        annex: null,
        remarkInfo: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        unitId: null,
        peopleId: null,
        taskDescription: null,
        taskProgress: null,
      };
      this.form6 = {};
      this.bdglWorkTaskPlanDetailList = [];
      this.resetForm("form");
      this.resetForm("form6");
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
      this.title = "新增工作任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      this.LiMianId = row.id;
      getWorktaskplan(id).then((response) => {
        this.form = response.data;
        this.form.taskProgress = this.form.taskProgress * 1;
        this.fabuRen = response.data.createBy;
        this.openx = true;
        this.title = "修改工作任务";
      });
      this.getListS(id);
    },

    /** 发布人查看按钮操作 */
    handleUpdatechakan(row) {
      this.reset();
      const id = row.id || this.ids;
      this.LiMianId = row.id;
      getWorktaskplan(id).then((response) => {
        this.form = response.data;
        this.form.taskProgress = this.form.taskProgress * 1;
        this.fabuRen = response.data.createBy;
        this.openxc = true;
        this.title = "查看工作任务";
      });
      this.getListS(id);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateWorktaskplan(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.openx = false;
              this.getList();
            });
          } else {
            addWorktaskplan(this.form).then((response) => {
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
        .confirm("是否确认删除任务计划的数据项？")
        .then(function () {
          return delWorktaskplan(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$download(
        "workingArrangements/worktaskplan/export",
        {
          ...this.queryParams,
        },
        `worktaskplan_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
<style scoped>
/* 总进度条字体颜色 */
::v-deep .b .el-progress-bar__innerText {
  color: #fff;
}
::v-deep .a .el-progress-bar__innerText {
  color: #da4655;
}
/* 总进度条样式 */
::v-deep .el-progress-bar {
  margin-bottom: 20px;
}
::v-deep .el-progress-bar__outer {
  height: 19px !important;
  width: 859px;
}
/* 修改任务详情列表样式 */
::v-deep .box2 .el-table {
  margin-bottom: 25px;
}
/* 修改弹框中的备注 */
::v-deep .box2 .el-textarea__inner {
  width: 855px;
}
::v-deep .box2 .el-dialog__body {
  margin-left: 51px;
}
::v-deep .box2 .el-dialog__footer {
  margin-right: 58px;
}

::v-deep .box1 .el-table__row {
  height: 0px !important;
}
::v-deep .el-dialog__body .el-table th.is-leaf {
  border: none !important;
}
::v-deep .el-dialog__body table td {
  border: none !important;
}
::v-deep .el-input__inner {
  height: 36px;
  background-color: transparent !important;
  color: #fff;
  border: 1px solid white !important;
}
::v-deep .el-textarea__inner {
  width: 619px;
  height: 104px;
}
::v-deep .box .el-textarea__inner {
  width: 614px;
  height: 104px;
}
::v-deep .box1 .el-form-item__content {
  width: 800px;
}
::v-deep .el-dialog__body .el-table tr:nth-child(odd) {
  background-color: #004d86 !important;
}
::v-deep .el-dialog__body .el-table tr:nth-child(even) {
  background-color: #2a6b9a !important;
}

::v-deep .el-input__inner {
  border: 1px solid white !important;
}
.el-select-dropdown__item.selected {
  color: #000;
  font-weight: bold;
}
.vue-treeselect__menu {
  color: #606266;
}
/* 日历样式 */
.el-date-picker__editor-wrap .el-input .el-input__inner {
  width: 100px !important;
}
.el-date-picker__time-header {
  border-bottom: none;
}
::v-deep .el-input--small .el-input__inner {
  height: 36px;
}
::v-deep .el-upload__tip {
  position: static !important;
  margin-left: 3px;
  margin-bottom: 21px;
}
.ren_wu {
  width: 500px !important;
}
.ren_wu1 {
  width: 636px;
}
/* 基本信息背景 */
.jiben {
  width: 700px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
  background-size: 100%;
}
::v-deep .el-form--inline .el-form-item {
  margin-right: 21px;
}
::v-deep .el-dialog__body {
  margin-left: 73px;
}
::v-deep .vue-treeselect__control {
  width: 217px;
}
::v-deep .box3 .el-input__inner {
  margin-bottom: 20px;
}
.box3 a {
  border: 1px solid white;
  display: inline-block;
  width: 100px;
  height: 33px;
  border-radius: 5px;
  line-height: 33px;
  text-align: center;
}
::v-deep .el-input.is-disabled .el-input__inner {
  color: #fff;
}
::v-deep .upload-file-list .el-upload-list__item {
  max-width: 500px !important;
}
/* 新增工作任务 */
::v-deep .addbox .el-dialog__footer {
  margin-right: 107px;
}
::v-deep .addbox .el-dialog__body {
  margin-left: 56px !important;
}
/* 查看 */
::v-deep .box4 .box3 .is-disabled {
  width: 500px !important;
}
::v-deep .box4 .el-textarea__inner {
  width: 614px;
}

::v-deep .isSubmit {
  margin-left: 44%;
}

::v-deep .box5 .box3 .el-input {
  width: 577px !important;
}
::v-deep .box5 .el-dialog__footer {
  margin-right: 88px;
}

::v-deep .box5 .el-textarea__inner {
  width: 687px !important;
}
::v-deep .box5 .el-dialog__body {
  margin-left: 69px;
}

::v-deep .box .el-dialog__footer {
  margin-right: 73px !important;
}

/* ::v-deep .box2 .el-form-item {
  margin-right: 147px !important;
} */
::v-deep .box2 .el-table {
  width: 860px !important;
}
::v-deep .box2 .box3 .el-input {
  width: 747px !important;
}
hr {
  border: none;
  height: 1px;
  background-color: rgba(204, 204, 204, 0.3);
  margin-bottom: 20px;
  margin-top: 20px;
}
::v-deep .box4 .el-dialog__footer {
  margin-right: 79px;
}

/* 查看 添加分支任务*/
.el-button--btzx.is-active,
.el-button--btzx:active {
  background: #1d96ff;
  border-color: #1d96ff;
  color: #ffffff;
}

.el-button--btzx:focus,
.el-button--btzx:hover {
  background: #41a4fa;
  border-color: #41a4fa;
  color: #ffffff;
}

.el-button--btzx {
  width: 100px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #1d96ff;
}

::v-deep .el-dialog {
  background-color: #004d86;
}
::v-deep .el-dialog__title {
  color: #fff;
}

::v-deep .el-link.el-link--default {
  color: #fff;
}
::v-deep .el-upload__tip {
  color: #fff;
}
::v-deep .vue-treeselect__control {
  background: transparent;
}
::v-deep .vue-treeselect__single-value {
  color: #fff;
}
::v-deep .el-progress-bar__outer {
  background-color: rgba(25, 98, 153, 1);
}
::v-deep .el-date-editor.el-input,
.el-date-editor {
  width: 200px;
}
::v-deep .el-pagination .el-input__inner {
  width: 100px;
  height: 28px;
}
::v-deep .el-pagination__jump .el-input__inner {
  width: 46px;
}
</style>
