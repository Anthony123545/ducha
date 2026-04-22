<template>
  <div class="app-container workTask">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="120px"
    >
      <el-form-item prop="type" class="plantypes" label-width="80px">
        <el-input
          placeholder="请输入任务类型"
          v-model="queryParams.type"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="adminId" class="plantypes" label-width="80px">
        <treeselect
          v-model="queryParams.adminId"
          :options="users"
          placeholder="请选择发布单位"
          class="threeselects"
          @select="selectPeo3"
        />
      </el-form-item>
      <el-form-item>
        <!-- <el-button
          type="primary"
          icon="el-icon-search"
          size="btn"
          @click="handleQuery"
          >搜索</el-button
        >-->
        <el-button
          type="btr"
          icon="el-icon-refresh"
          size="mini"
          @click="resetQuery"
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
          v-hasPermi="['workingArrangements:workTask:add']"
          >新增</el-button
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
          v-hasPermi="['workingArrangements:workTask:export']"
          >导出</el-button
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
          v-hasPermi="['workingArrangements:workTask:edit']"
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
          v-hasPermi="['workingArrangements:workTask:remove']"
          >删除</el-button
        >
      </el-col>-->
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['workingArrangements:workTask:export']"
          >导出</el-button
        >
      </el-col>-->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>-->
    </el-row>

    <el-table
      v-loading="loading"
      :data="workTaskList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="名称" align="center" prop="name" />
      <!-- <el-table-column
        label="发布单位"
        align="center"
        prop="adminId"
        :formatter="unit3Format"
      />-->
      <el-table-column label="发布人" align="center" prop="username" />
      <el-table-column label="任务类型" align="center" prop="type" />
      <el-table-column
        label="执行人"
        align="center"
        prop="peopleId"
        :formatter="unit2Format"
        show-overflow-tooltip
      />
      <!-- <el-table-column label="编号" align="center" prop="number" /> -->
      <el-table-column
        label="任务开始时间"
        align="center"
        prop="startTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="任务结束时间"
        align="center"
        prop="endTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="描述"
        align="center"
        prop="contents"
        show-overflow-tooltip
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="btlook"
            type="text"
            @click="ViewProgress(scope.row)"
            v-if="scope.row.common == 1 && scope.row.taskProgress != '100'"
          >
            <span>进度录入</span>
          </el-button>
          <el-button
            size="btk"
            type="text"
            @click="handleChakan(scope.row)"
            v-if="scope.row.common == 1"
          >
            <!-- 单人进度查看 -->
            <span class="chakan">查看</span>
          </el-button>
          <el-button
            size="btk"
            type="text"
            @click="handleChakan1(scope.row)"
            v-if="scope.row.common == 2 || scope.row.common == 0"
          >
            <!-- 多人进度查看 -->
            <span class="chakan">查看</span>
          </el-button>
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workingArrangements:planTask:edit']"
            v-if="scope.row.common == 2"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-if="scope.row.common == 2"
            v-hasPermi="['workingArrangements:planTask:remove']"
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
    <!-- 添加或修改工作任务对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="980px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="100px"
        :inline="true"
      >
        <div class="jiben">基本信息</div>
        <el-form-item label="任务名称" prop="name">
          <el-input
            placeholder="请选择任务名称"
            v-model="form.name"
            @click.native="planName"
          />
        </el-form-item>
        <el-form-item label="任务编号" prop="number">
          <el-input
            placeholder="请选择任务编号"
            label="任务编号"
            v-model="form.number"
            :disabled="true"
            class="ipt"
          ></el-input>
        </el-form-item>
        <el-form-item label="发布单位" prop="adminId">
          <treeselect
            v-model="form.adminId"
            :options="part"
            @select="selectPeo"
            class="threeselects"
            disabled
            placeholder="请选择发布单位"
          />
        </el-form-item>
        <el-form-item label="任务类型" prop="type">
          <el-input
            placeholder="请输入任务类型"
            v-model="form.type"
            style="width: 200px"
          ></el-input>
        </el-form-item>
        <el-form-item label="发布人" prop="username">
          <el-input
            placeholder="请输入发布人"
            v-model="form.username"
            :disabled="true"
            class="ipt"
          ></el-input>
        </el-form-item>
        <div>
          <el-form-item label="执行单位" prop="unitId" label-width="100px">
            <div>
              <el-select
                multiple
                filterable
                v-model="form.unitId"
                id="execute"
                ref="peoplenames"
                placeholder="请选择执行单位"
                @remove-tag="selent"
              >
                <el-option
                  v-for="(item, i) in danWeisList"
                  :key="i"
                  :label="item.deptName"
                  :value="item.deptId"
                  id="execute"
                  @click.native="danFn()"
                />
              </el-select>
            </div>
          </el-form-item>
        </div>

        <div>
          <el-form-item label="执行人" prop="peopleName" label-width="100px">
            <div>
              <el-select
                multiple
                filterable
                v-model="form.peopleName"
                id="execute"
                ref="peoplenames"
                placeholder="请选择执行人"
              >
                <el-option
                  v-for="(item,i1) in executor"
                  :key="i1+' '"
                  :label="item.nickName"
                  :value="item.userId"
                  id="execute"
                />
              </el-select>
            </div>
          </el-form-item>
        </div>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            placeholder="请选择开始时间"
            clearable
            size="small"
            v-model="form.startTime"
            type="datetime"
            style="width: 200px"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px" prop="endTime">
          <el-date-picker
            placeholder="请选择结束时间"
            clearable
            size="small"
            v-model="form.endTime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <div class="jiben">任务附件</div>
        <el-form-item style="margin-left: 30px">
          <fileUpload v-model="form.file" :limit="1" />
        </el-form-item>
        <div class="jiben">任务描述</div>
        <el-form-item prop="contents">
          <el-input
            v-model="form.contents"
            type="textarea"
            placeholder="请输入内容"
            style="width: 950px"
          />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
            style="width: 950px"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 进度录入弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="open1"
      width="980px"
      append-to-body
      class="el-dialog__header"
      :close-on-click-modal="false"
    >
      <el-form
        ref="Progress"
        :model="Progress"
        :rules="rules1"
        label-width="100px"
        :inline="true"
      >
        <div class="jiben">完成进度</div>
        <el-form-item prop="planProgress">
          <div>
            <el-input-number
              v-model="Progress.planProgress"
              controls-position="right"
              :min="0"
              :max="100"
            ></el-input-number>
            <el-progress
              :text-inside="true"
              :stroke-width="24"
              :percentage="Progress.planProgress"
              color="rgba(41, 231, 205, 1)"
              class="jinduProgress"
            ></el-progress>
          </div>
        </el-form-item>
        <div class="jiben">附件</div>
        <el-form-item>
          <fileUpload v-model="Progress.file" />
        </el-form-item>
        <div class="jiben">备注</div>
        <el-form-item prop="remark">
          <el-input
            v-model="Progress.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <div class="jiben">完成时间</div>
        <el-form-item prop="accomplishTime">
          <el-date-picker
            class="comtimer"
            clearable
            size="small"
            v-model="Progress.accomplishTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择完成时间"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 单人进度查看弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="open2"
      width="980px"
      append-to-body
      class="el-dialog__header"
      :close-on-click-modal="false"
    >
      <el-form
        ref="singleTable"
        :model="singleTable"
        :rules="rules"
        label-width="100px"
        :inline="true"
      >
        <div class="jiben">完成进度</div>
        <!-- <el-form-item prop="comPlan"> -->
        <!-- <el-input
            label="计划名称"
            v-model="ProgressLook.comPlan"
            class="big"
        />-->
        <!-- </el-form-item> -->
        <el-progress
          :text-inside="true"
          :stroke-width="24"
          :percentage="singleTable.planProgresses"
          style="margin-bottom: 20px"
          color="rgba(41, 231, 205, 1)"
        ></el-progress>
        <div class="jiben">任务信息</div>
        <el-form-item label="执行人" prop="peopleName" label-width="100px">
          <div>
            <el-select
              multiple
              v-model="singleTable.peopleName"
              ref="peoplenames"
              placeholder="请选择执行人"
              style="width: 350px"
            >
              <el-option
                v-for="item in executor"
                :key="item.deptId"
                :label="item.nickName"
                :value="item.userId"
                @click.native="returnExecutor(item.deptId, item.nickName)"
              />
            </el-select>
          </div>
        </el-form-item>
        <el-form-item prop="comWeek" label="任务周期" label-width="100px">
          <!-- <el-input
            v-model="singleTable."
            class="big"
          />-->
          <div style="width: 350px">
            {{ singleTable.startTime }} 至 {{ singleTable.endTime }}
          </div>
        </el-form-item>
        <!-- <div class="thistext"></div> -->
        <div class="jiben">附件文档</div>
        <el-form-item prop="file">
          <el-input
            label="附件名称"
            v-model="singleTable.fileName"
            style="width: 820px"
          />
          <!-- <div>{{singleTableb.fileName}}</div> -->
          <a
            :href="singleTable.file"
            :download="singleTable.fileName"
            title="下载"
            style="color: white; margin-left: 10px"
            class="downloadfile"
            >下载附件</a
          >
        </el-form-item>
        <div class="jiben">任务描述</div>
        <div class="thistext">{{ singleTable.contents }}</div>
        <div class="jiben">备注</div>
        <div class="thistext">{{ singleTable.remark }}</div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm2">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 多人进度查看弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="open3"
      width="980px"
      append-to-body
      class="el-dialog__header jinduchakan"
      :close-on-click-modal="false"
    >
      <el-form
        ref="moreTable"
        :model="moreTable"
        :rules="rules"
        label-width="100px"
        :inline="true"
      >
        <div class="jiben">完成进度</div>
        <el-progress
          :text-inside="true"
          :stroke-width="24"
          :percentage="comstatus"
          style="margin-bottom: 20px"
          color="rgba(41, 231, 205, 1)"
        ></el-progress>
        <div class="jiben">任务信息</div>
        <el-form-item label="发布人" prop="username" label-width="auto">
          <el-input
            placeholder="请输入发布人"
            v-model="moreTable.username"
            :disabled="true"
            class="ipt"
            style="color: #fff; width: 350px"
          ></el-input>
        </el-form-item>
        <el-form-item prop="comWeek" label="任务周期" label-width="100px">
          <div
            style="
              width: 350px;
              border: 1px solid #fff;
              border-radius: 4px;
              min-height: 35px;
              height: auto !important;
              line-height: 35px;
              text-indent: 1em;
              margin-bottom: 20px;
              color: #fff;
            "
          >
            {{ moreTable.startTime }} 至 {{ moreTable.endTime }}
          </div>
        </el-form-item>
        <div class="jiben">附件文档</div>
        <el-form-item prop="file">
          <el-input
            label="附件名称"
            v-model="moreTable.fileName"
            style="width: 820px"
          />
          <!-- <div>{{singleTableb.fileName}}</div> -->
          <a
            :href="moreTable.file"
            :download="moreTable.fileName"
            title="下载"
            style="color: white; margin-left: 10px"
            class="downloadfile"
            >下载附件</a
          >
        </el-form-item>
        <div class="jiben">任务描述</div>
        <div class="thistext">{{ moreTable.contents }}</div>
        <div class="jiben">备注</div>
        <div class="thistext">{{ moreTable.remark }}</div>
        <div class="jiben">执行人信息</div>
        <el-table
          :data="moreTable.workProgresses"
          border
          style="width: 100%; background: #004d86"
          class="tabless"
        >
          <el-table-column
            prop="peopleName"
            label="执行人"
            width="180"
          ></el-table-column>
          <el-table-column label="任务周期" width="190">
            <template slot-scope="scope">
              <div>{{ scope.row.taskCycle }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="schedule" label="进度">
            <template slot-scope="scope">
              <el-progress
                :text-inside="true"
                :stroke-width="24"
                :percentage="scope.row.schedule"
                style="margin-bottom: 20px"
                color="rgba(41, 231, 205, 1)"
              ></el-progress>
            </template>
          </el-table-column>
          <el-table-column
            prop="accomplishTime"
            label="完成时间"
            width="180"
          ></el-table-column>
          <el-table-column label="状态" width="180">
            <template slot-scope="scope">
              <div>{{ scope.row.schedule == 100 ? "完成" : "未完成" }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="下载">
            <template slot-scope="scope">
              <a :href="scope.row.file" download>下载附件</a>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm2">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :title="title"
      :visible.sync="open2"
      width="980px"
      append-to-body
      class="el-dialog__header"
      :close-on-click-modal="false"
    >
      <el-form
        ref="singleTable"
        :model="singleTable"
        :rules="rules"
        label-width="100px"
        :inline="true"
      >
        <div class="jiben">完成进度</div>
        <el-progress
          :text-inside="true"
          :stroke-width="24"
          :percentage="singleTable.planProgresses"
          style="margin-bottom: 20px"
          color="rgba(41, 231, 205, 1)"
        ></el-progress>
        <div class="jiben">任务信息</div>
        <el-form-item label="发布人" prop="username" label-width="auto">
          <el-input
            placeholder="请输入发布人"
            v-model="singleTable.username"
            :disabled="true"
            class="ipt"
            style="color: #fff; width: 350px"
          ></el-input>
        </el-form-item>
        <el-form-item prop="comWeek" label="任务周期" label-width="100px">
          <div
            style="
              width: 350px;
              border: 1px solid #fff;
              border-radius: 4px;
              min-height: 35px;
              height: auto !important;
              line-height: 35px;
              text-indent: 1em;
              margin-bottom: 20px;
              color: #fff;
            "
          >
            {{ singleTable.startTime }} 至 {{ singleTable.endTime }}
          </div>
        </el-form-item>
        <!-- <div class="thistext"></div> -->
        <div class="jiben">附件文档</div>
        <el-form-item prop="file">
          <el-input
            label="附件名称"
            v-model="singleTable.fileName"
            style="width: 820px"
          />
          <!-- <div>{{singleTableb.fileName}}</div> -->
          <a
            :href="singleTable.file"
            download
            title="下载"
            style="color: white; margin-left: 10px"
            class="downloadfile"
            >下载附件</a
          >
        </el-form-item>
        <div class="jiben">备注</div>
        <div class="thistext">{{ singleTable.remark }}</div>
        <div class="jiben">任务描述</div>
        <div class="thistext">{{ singleTable.contents }}</div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm2">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 多人进度查看弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="open4"
      width="450px"
      append-to-body
      class="el-dialog__header jinduchakan"
      :close-on-click-modal="false"
    >
      <el-form
        ref="moreTable"
        :model="daoruobj"
        :rules="rules"
        label-width="100px"
        :inline="true"
      >
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            placeholder="请选择开始时间"
            clearable
            size="small"
            v-model="daoruobj.startTime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 200px"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px" prop="endTime">
          <el-date-picker
            placeholder="请选择结束时间"
            clearable
            size="small"
            v-model="daoruobj.endTime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm4">导 出</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 导出弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="open5"
      width="450px"
      append-to-body
      class="el-dialog__header jinduchakan"
      :close-on-click-modal="false"
    >
      <el-form
        ref="queryParamsTime"
        :model="queryParamsTime"
        :rules="queryParamsRules"
        label-width="100px"
        :inline="true"
      >
        <div>
          <el-form-item label="请选择类型">
            <el-select
              v-model="category"
              placeholder="请选择类型"
              @change="leixingchange"
              id="leixing"
            >
              <el-option value="时间">时间</el-option>
              <el-option value="年">年</el-option>
              <el-option value="月">月</el-option>
            </el-select>
          </el-form-item>
        </div>
        <el-form-item label="开始时间" prop="startTime" v-if="shijian">
          <el-date-picker
            placeholder="请选择开始时间"
            clearable
            size="small"
            v-model="queryParamsTime.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            style="width: 200px"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
          label="结束时间"
          label-width="100px"
          prop="endTime"
          v-if="shijian"
        >
          <el-date-picker
            placeholder="请选择结束时间"
            clearable
            size="small"
            v-model="queryParamsTime.endTime"
            type="date"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="年份" prop="createtime" v-if="nian">
          <el-date-picker
            placeholder="请选择年份"
            clearable
            size="small"
            v-model="queryParamsTime.createtime"
            type="year"
            value-format="yyyy"
            style="width: 200px"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="月份" label-width="100px" prop="yue" v-if="yue">
          <el-date-picker
            placeholder="请选择月份"
            clearable
            size="small"
            v-model="queryParamsTime.yue"
            type="month"
            value-format="yyyy-MM"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm4" :href="daoChu"
          >导 出</el-button
        >
        <el-button @click="cancel">取 消</el-button>
        <a v-show="false" id="dianji">111</a>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listWorkTask,
  getWorkTask,
  delWorkTask,
  addWorkTask,
  updateWorkTask,
  exportWorkTask,
  singProgress,
  moreProgress,
  uploadProgress,
  getUnitUsers,
} from "@/api/workingArrangements/workTask";
import {
  deptUser,
  allUser,
  getDept,
  getDeptList,
} from "@/api/workingArrangements/workTask";
import { getDicts } from "@/api/system/dict/data";
import { listDept } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getDept2 } from "@/api/grassrootsregistration/bdglmeeting";
import Cookies from "js-cookie";
export default {
  name: "WorkTask",
  dicts: ["work_type"],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      url: process.env.VUE_APP_BASE_API,
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
      // 工作任务表格数据
      workTaskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 显示进度录入框
      open1: false,
      // 显示进度查看框
      open2: false,
      // 显示多人进度查看框
      open3: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        peopleId: null,
        number: null,
        type: null,
        startTime: null,
        endTime: null,
        file: null,
        fileName: null,
        contents: null,
        adminId: null,
        unitId: null,
        unitName: null,
        createtime: null,
        updatetime: null,
        peopleName: null,
        unitName2: null,
        taskProgress: null,
        releaseUnit: null,
        executableUnit: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "任务名称不能为空", trigger: "blur" },
        ],
        number: [{ required: true, message: "编号不能为空", trigger: "blur" }],
        peopleName: [
          { required: true, message: "执行人不能为空", trigger: "blur" },
        ],
        // name: [{ required: true, message: "不能为空", trigger: "blur" }],
        adminId: {
          required: true,
          message: "发布单位不能为空",
          trigger: "blur",
        },
        unitId: {
          required: true,
          message: "执行单位不能为空",
          trigger: "blur",
        },
        type: { required: true, message: "计划类型不能为空", trigger: "blur" },
        startTime: {
          required: true,
          message: "开始时间不能为空",
          trigger: "blur",
        },
        endTime: {
          required: true,
          message: "结束时间不能为空",
          trigger: "blur",
        },
      },
      rules1: {
      },
      // 发布单位
      part: [],
      // 执行单位
      users: [],
      // 执行人
      executor: [],
      rylist: [],
      // 查询字典
      dictType: {
        dictType: "work_type",
      },
      ziDian: [],
      // 部门列表
      getdeptList: [],
      // 单人进度查看框
      singleTable: [],
      // 多人进度查看框
      moreTable: [],

      comstatus: 0,
      // 进度录入参数
      Progress: {
        schedule: "",
        file: "",
        remark: "",
        accomplishTime: "",
        taskId: "",
        id: "",
        publisherId: "",
      },
      defaultTime: null,
      daoruobj: {},
      open4: null,
      // 导出弹出层的显示与隐藏
      open5: false,
      shijian: true,
      nian: false,
      yue: false,
      category: "时间",
      queryParamsRules: {
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" },
        ],
        createtime: {
          required: true,
          message: "年份不能为空",
          trigger: "blur",
        },
        yue: {
          required: true,
          message: "月份不能为空",
          trigger: "blur",
        },
      },
      queryParamsTime: {
        startTime: null,
        endTime: null,
        createtime: null,
        yue: null,
      },
      // 导出文件的路径
      daoChu: "",
      //获取单位所有不是树形结构
      danWeisList: [],
    };
  },
  created() {
    this.getList();
    this.getdept();
    allUser().then((res) => {
      this.rylist = res.rows;
    });
    getDeptList().then((res) => {
      this.getdeptList = res.data;
    });
    this.getdict();
    this.chooseplan();
    getDept2().then((res) => {
      this.users = res.data;
    });
  },
  methods: {
    //点击多选单位
    danFn() {
      this.renyuanEs();
    },
    //获取多选单位下的人员
    renyuanEs() {
      if (this.form.unitId.length > 0) {
        var ids = this.form.unitId.join(",");
        getUnitUsers(ids).then((res) => {
          this.executor = res.data;
        });
      } else {
        this.executor = [];
      }
    },
    //删除所有单位的某一个触发
    selent() {
      this.renyuanEs();
      this.form.peopleName = [];
    },
    //获取所有单位
    danWeis() {
      listDept().then((res) => {
        this.danWeisList = res.data;
      });
    },
    // 导出重置
    resets() {
      this.queryParamsTime = {
        startTime: null,
        endTime: null,
        createtime: null,
        yue: null,
      };
      this.resetForm("queryParamsTime");
    },
    // 点击类型触发
    leixingchange(value) {
      if (value == "时间") {
        this.resets();
        this.shijian = true;
        this.nian = false;
        this.yue = false;
      } else if (value == "年") {
        this.resets();
        this.shijian = false;
        this.nian = true;
        this.yue = false;
      } else {
        this.resets();
        this.shijian = false;
        this.nian = false;
        this.yue = true;
      }
    },
    // startTime() {
    //   this.timer = setInterval(() => {
    //     var today = new Date();
    //     var y = today.getFullYear();
    //     var M = today.getMonth() + 1;
    //     var d = today.getDate();
    //     var h = today.getHours();
    //     var m = today.getMinutes();
    //     var s = today.getSeconds();
    //     var w = today.getDay();
    //     if (h < 10) {
    //       h = "0" + h;
    //     }
    //     if (m < 10) {
    //       m = "0" + m;
    //     }
    //     if (s < 10) {
    //       s = "0" + s;
    //     }
    //     this.defaultTime = h + ":" + m + ":" + s;
    //   }, 500); /* 每500毫秒执行一次，实现动态显示时间效果 */
    // },
    /** 进度录入按钮操作 */
    ViewProgress(row) {
      // this.reset();
      this.open1 = true;
      this.title = "进度录入";
      this.jinduId = row.id;
      singProgress(this.jinduId).then((res) => {
        this.Progress.id = res.data.workProgress.id;
        this.Progress.taskId = res.data.id;
        this.Progress.planProgress = res.data.workProgress.schedule;
        this.Progress.publisherId = res.data.workProgress.publisherId;
      });
    },
    // 进度查看单人进度
    handleChakan(row) {
      this.reset();
      this.open2 = true;
      this.title = "进度查看";
      // 获取当前行的id
      singProgress(row.id).then((res) => {
        this.singleTable = res.data;
        this.singleTable.planProgresses =
          this.singleTable.workProgress.schedule;
        this.Progress.id = this.singleTable.workProgress.id;

        this.singleTable.file =
          process.env.VUE_APP_BASE_API + this.singleTable.file;

        this.singleTable.fileName = this.singleTable.workProgress.fileName;
        this.singleTable.peopleName = res.data.peopleName

          .split(",")
          .map(Number);
        // this.getZhuChi(res.data.unitId);
      });
    },
    // 多人进度查看
    handleChakan1(row) {
      this.reset();
      this.open3 = true;
      this.title = "进度查看";
      // 获取当前行的id
      moreProgress(row.id).then((res) => {
        this.comstatus = 0;
        this.moreTable = res.data;

        this.moreTable.file =
          process.env.VUE_APP_BASE_API + this.moreTable.file;
        this.moreTable.workProgresses.map((item) => {
          item.file = process.env.VUE_APP_BASE_API + item.file;
        });

        this.moreTable.workProgresses.forEach((item) => {
          this.comstatus += parseInt(
            item.schedule / this.moreTable.workProgresses.length
          );
        });
      });
    },

    /** 查询工作任务列表 */
    getList() {
      this.loading = true;
      listWorkTask(this.queryParams).then((response) => {
        this.workTaskList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.comstatus = 0;
      this.reset();
      this.open1 = false;
      this.open2 = false;
      this.open3 = false;
      this.open4 = false;
      this.open5 = false;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        peopleId: null,
        number: null,
        type: null,
        startTime: null,
        endTime: null,
        file: null,
        fileName: null,
        remark: null,
        contents: null,
        adminId: null,
        unitId: null,
        unitName: null,
        createtime: null,
        updatetime: null,
        peopleName: null,
        unitName2: null,
        taskProgress: null,
        releaseUnit: null,
        executableUnit: null,
      };
      this.resetForm("form");
    },
    zidainType(item) {
      // this.queryParams.type = item.dictValue;
      this.getList();
    },
    // 发布单位选中触发
    selectPeo3(val) {
      this.queryParams.adminId = val.id;
      this.getList();
      // this.queryParams.unitId = val.id;
      //this.getZhuChi(this.queryParams.unitId);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {};
      this.resetForm("queryForm");
      this.handleQuery();
      this.getList();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    // 获取执行人
    getZhuChi(id) {
      deptUser(id).then((res) => {
        // this.renYuan = res.data;
        this.executor = res.data;
      });
    },
    // 发布单位选中触发
    selectPeo(val) {
      this.form.adminId = val.id;
    },
    // 执行单位选中触发
    selectPeo1(val) {
      this.form.unitId = val.id;
      this.form.peopleName = "";
      this.getZhuChi(this.form.unitId);
    },

    // 查询计划类型
    getdict() {
      getDicts(this.dictType.dictType).then((res) => {
        this.ziDian = res.data;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工作任务";
      this.danWeis();
      this.form.adminId = Cookies.get("deptId");
      this.form.username = Cookies.get("nickName");
      this.form.userId = Cookies.get("userId");
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
      this.form.startTime = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
      // this.form.endTime = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;

      // 获取执行单位列表
      getDept2().then((res) => {
        this.users = res.data;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      getDept2().then((res) => {
        this.users = res.data;
      });
      const id = row.id || this.ids;
      getWorkTask(id).then((response) => {
        this.form = response.data;
        // this.form.type = parseInt(this.form.type);
        // this.getDept2()
        //this.getZhuChi(this.form.unitId);
        this.form.peopleName = this.form.peopleName.split(",").map(Number);
        this.form.unitId = this.form.unitId.split(",").map(Number);
        this.danWeis();
        this.renyuanEs();
        this.open = true;
        this.title = "修改工作计划";
      });
    },
    unit2Format(row, column) {
      var deptName = "";
      if (
        parseInt(this.workTaskList[0].common) == 1 ||
        this.workTaskList[0].common == 2
      ) {
        this.rylist.map((item) => {
          row.peopleName.split(",").forEach((items, index) => {
            if (item.userId == items) {
              deptName += item.nickName + "  ";
            }
          });
        });
      } else if (parseInt(this.workTaskList[0].common) == 0) {
        this.rylist.map((item) => {
          row.peopleName.split(",").forEach((items, index) => {
            if (item.userId == items) {
              deptName += item.nickName + " ";
            }
          });
        });
      }
      return deptName;
    },
    unit3Format(row, column) {
      var deptName = "";
      if (
        parseInt(this.workTaskList[0].common) == 1 ||
        this.workTaskList[0].common == 2
      ) {
        this.getdeptList.map((item) => {
          if (item.deptId == row.adminId) {
            deptName = item.deptName;
          }
        });
      } else if (parseInt(this.workTaskList[0].common) == 0) {
        this.getdeptList.map((item) => {
          if (item.deptId == row.adminId) {
            deptName = item.deptName;
          }
        });
      }
      return deptName;
    },
    unit4Format(row, column) {
      var deptName = "";
      this.ziDian.map((item) => {
        if (item.dictSort == row.type) {
          deptName = item.dictLabel;
        }
      });
      return deptName;
    },
    // 选择计划
    chooseplan() {
      this.form.type = this.queryParams.type;
    },
    /** 提交按钮 */
    submitForm() {
      if (this.form.file) {
        this.form.fileName = this.form.file[0].name;
        this.form.file = this.form.file[0].url;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.adminId === this.form.unitId) {
            alert("发布单位和执行单位不能相同");
          } else {
            this.form.peopleName = this.form.peopleName.join(",");
            this.form.peopleId = this.form.peopleName;

            if (this.form.id != null) {
              if (this.form.unitId.length > 0) {
                this.form.unitId = this.form.unitId.join(",");
              }
              updateWorkTask(this.form).then((response) => {
                this.$modal.msgSuccess("修改成功");
                this.queryParams.type = "";
                this.open = false;
                this.getList();
              });
            } else {
              if (this.form.unitId.length > 0) {
                this.form.unitId = this.form.unitId.join(",");
              }
              addWorkTask(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        }
      });
      // this.$refs["form"].validate((valid) => {
      // if (valid) {
      //   this.form.peopleName = this.form.peopleName.join(",");
      //   this.form.peopleId = this.form.peopleName;

      //   if (this.form.id != null) {
      //     updateWorkTask(this.form).then((response) => {
      //       this.$modal.msgSuccess("修改成功");
      //       this.open = false;
      //       this.getList();
      //     });
      //   } else {
      //     addWorkTask(this.form).then((response) => {
      //       this.$modal.msgSuccess("新增成功");
      //       this.open = false;
      //       this.getList();
      //     });
      //   }
      // }
      // });
    },
    submitForm1() {
      this.$refs["Progress"].validate((valid) => {
        if (valid) {
          this.Progress.taskId = this.jinduId;
          this.Progress.schedule = this.Progress.planProgress;
          if (this.Progress.file) {
            this.Progress.fileName = this.Progress.file[0].name;
            this.Progress.file = this.Progress.file[0].url;
          }

          uploadProgress(this.Progress).then((res1) => {
            this.$modal.msgSuccess("上传成功");
            this.reset();
            this.open1 = false;
            this.getList();
          });
        }
      });
    },
    submitForm2() {
      this.comstatus = 0;
      this.open2 = false;
      this.open3 = false;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除？")
        .then(function () {
          return delWorkTask(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.open5 = true;
      this.title = "导出工作数据";
      this.resets();
    },
    //导出
    submitForm4() {
      this.$refs["queryParamsTime"].validate((valid) => {
        if (valid) {
          if (this.category == "月") {
            this.queryParamsTime.createtime = this.queryParamsTime.yue;
          }
          this.$modal
            .confirm("是否确认导出数据项？")
            .then(() => {
              this.exportLoading = true;
              return exportWorkTask(this.queryParamsTime);
            })
            .then((response) => {
              this.$download.name(response.msg);
              this.exportLoading = false;
              this.open5 = false;
            })
            .catch(() => {});
        }
      });

      // this.$modal;
      // .confirm("是否确认导出所有工作任务数据项？")
      // .then(() => {
      //   this.exportLoading = true;
      //   return exportWorkTask(this.daoruobj);
      // })
      // .then((response) => {
      //   this.$download.name(response.msg);
      //   this.exportLoading = false;
      //   this.open4 = false;
      // })
      // .catch(() => {});
    },
    // 发布单位接口
    getdept() {
      getDept().then((res) => {
        this.part = res.data;
      });
    },
    // 获取执行单位列表
    getdeptlist() {
      getDeptList().then((res) => {
        this.ExecutableUnit = res.data;
      });
    },
    // 选中执行人id
    // choosedeptid(id) {

    //   this.form.unitId = id;
    //   deptUser(id).then((res) => {
    //     this.executor = res.data;
    //   });
    // },

    // 计划编号
    planName() {
      let date = new Date();
      let year = date.getFullYear();
      let month =
        date.getMonth() + 1 <= 9
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let day = date.getDate();
      let hour = date.getHours() <= 9 ? "0" + date.getHours() : date.getHours();
      let minute =
        date.getMinutes <= 9 ? "0" + date.getMinutes() : date.getMinutes();
      this.form.number = year + month + day + hour + minute;
    },
    // 获取发布单位id
    issueunit(id) {
      this.form.adminId = id;
    },
  },
};
</script>
<style>
.el-input__inner {
  height: 36px;
  background-color: #00365f;
  color: #fff;
  border: 1px solid white !important;
}
.el-select-dropdown {
  background-color: white;
}
.el-dialog__wrapper .el-form-item__label {
  color: white !important;
}
.el-dialog {
  background-color: #004d86 !important;
}
.el-form-item__label {
  width: 30%;
}
.el-textarea {
  width: 70%;
}
.el-dialog__title {
  color: white;
}
.el-dialog__header {
  border-bottom: 1px solid #718a9d;
}
.el-textarea__inner {
  width: 945px;
  height: 104px;
}
.el-textarea__inner {
  width: 940px;
  height: 104px;
}
/* 表宽度 */
.el-input {
  width: 200px;
}
.el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 200px;
}
/* 执行 */
#execute {
  width: 510px;
  /* width: auto; */
}
.el-input--suffix {
  width: auto;
}
/* 上传附件样式 */
.el-icon-document {
  padding: 3px 5px;
  color: white;
}
.el-upload-list__item:hover {
  background-color: #00365f;
}
/* 上传附件的删除按钮样式 */
.el-link--inner {
  margin-left: 3px;
}
/* 弹框背景 */
.el-dialog {
  background: #00365f;
}
.el-dialog__title {
  color: white;
}
.el-input .el-input__inner {
  background: rgba(0, 0, 0, 0);
}
/* 下拉框样式 */
.el-select-dropdown__wrap .el-scrollbar__view {
  background-color: white;
}
/* 计划附件 */
.el-upload__tip {
  position: absolute;
  top: -5px;
  width: 500px;
  left: 100px;
  color: white;
}
/* 上传附件样式 */
.el-icon-document {
  padding: 3px 5px;
  color: white;
}
.el-upload-list__item:hover {
  background-color: #00365f;
}
/* 修改编号样式 */
.ipt .el-input__inner {
  background-color: #004d86 !important;
}
/* 进度查看 */
.el-button--btlook.is-active,
.el-button--btlook:active {
  background: #32a5d3;
  border-color: #32a5d3;
  color: #ffffff;
}
.el-button--btlook:focus,
.el-button--btlook:hover {
  background: #32a5d3;
  border-color: #32a5d3;
  color: #ffffff;
}
.el-button--btlook {
  width: 70px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #32a5d3;
}
.big .el-input__inner {
  width: 940px;
}
/* 树形 */
.threeselects {
  width: 200px;
}
.threeselects .vue-treeselect__input {
  background-color: #004d86;
  /* color: white; */
}
.el-form-item__content {
  /* color: white; */
  border-bottom: 1px solid #004d86;
}
.vue-treeselect__control {
  background-color: #004d86;
  border-bottom: 1px solid white;
  /* color: whi; */
  /* color: white; */
}
.vue-treeselect__placeholder {
  color: white;
}

.change_plan_type .el-input__inner {
  width: 200px;
}
/* 表格样式 */
.el-table__empty-block {
  background-color: #004d86;
}
.el-table {
  background-color: #004d86;
}
.comtimer .el-input__inner {
  width: 940px;
}
/* 备注 */
.thistext {
  color: white;
  width: 930px;
  border: 1px solid #fff;
  border-radius: 4px;
  min-height: 35px;
  height: auto !important;
  line-height: 35px;
  text-indent: 1em;
  margin-bottom: 20px;
}
/* 完成进度 */
.complue .el-input__inner {
  width: 940px;
}
.complue {
  width: 940px;
}
/* 进度 */
.jinduProgress {
  width: 740px;
  position: absolute;
  left: 210px;
  top: 5px;
}
/* 表格 */
.tabless .el-table__row {
  background-color: #004d86;
}

.has-gutter {
  background-color: #004d86;
}
.el-dialog__body .el-table tr:nth-child(odd) {
  background-color: #004d86 !important;
}
.el-dialog__body .el-table tr:nth-child(even) {
  background-color: #004d86 !important;
}
.el-dialog__body table td {
  border-bottom: 1px solid white !important;
}
.jinduchakan .el-dialog__body .el-table th.is-leaf {
  border: 1px solid white !important;
}
/* 下载按钮 */
.downloadfile {
  border: 1px solid white;
  display: inline-block;
  width: 100px;
  height: 33px;
  border-radius: 5px;
  line-height: 33px;
  text-align: center;
}
.el-table__row {
  border-bottom: 1px solid white;
}
.el-input-number__increase {
  background-color: #004d86;
}
.el-input-number__decrease {
  background-color: #004d86;
}
/* 进度条百分比 */
.el-progress-bar__innerText {
  color: #004d86;
  /* font-weight: 200; */
}
.el-progress-bar__outer {
  background-color: rgba(25, 98, 153, 1);
}
/* 上下箭头 */
.el-icon-arrow-up,
.el-icon-arrow-down {
  color: white;
}
.vue-treeselect__single-value {
  color: white;
}
/* 文字多余部分省略 */
.el-table_1_column_8 .cell {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.el-table__empty-block {
  border-bottom: 1px solid white;
}
.vue-treeselect__control {
  background-color: transparent !important;
}
.threeselects .vue-treeselect__input {
  background-color: transparent !important;
}
.el-table__body-wrapper .el-table__empty-block {
  border: none !important;
}
.vue-treeselect__placeholder {
  color: #ccc;
}
.el-form-item__content {
  border-bottom: none !important;
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
#leixing {
  width: 200px !important;
}
</style>
