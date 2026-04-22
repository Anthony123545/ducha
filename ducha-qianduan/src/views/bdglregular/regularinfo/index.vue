<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5" class="isSubmit">
        <el-radio-group
          v-model="queryParams.caoZuoType"
          @change="caoZuoTypefun"
        >
          <el-radio-button label="1">加分</el-radio-button>
          <el-radio-button label="0">减分</el-radio-button>
        </el-radio-group>
      </el-col>
    </el-row>

    <hr />
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          v-if="queryParams.caoZuoType == 0"
          style="background: #1d96ff"
          @click="handleAdd"
          v-hasPermi="['bdglregular:regularinfo:jian']"
          >减分录入</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          v-if="queryParams.caoZuoType == 1"
          style="background: #1d96ff"
          v-hasPermi="['bdglregular:regularinfo:jia']"
          @click="handleAdd"
          >加分录入</el-button
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
          v-hasPermi="['bdglregular:regularinfo:remove']"
          >删除</el-button
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
          v-hasPermi="['bdglregular:regularinfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['bdglregular:regularinfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['bdglregular:regularinfo:export']"
        >导出</el-button>
      </el-col>-->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
      >
        <el-form-item prop="inspectedUnitId">
          <treeselect
            v-model="queryParams.inspectedUnitId"
            :options="deptOptions"
            placeholder="请选择受检单位"
            @select="selectPeos"
          />
        </el-form-item>
        <el-form-item prop="currentState">
          <el-select
            v-model="queryParams.currentState"
            placeholder="请选择状态"
            clearable
            size="small"
            @change="handleQuery"
            class="states"
          >
            <el-option
              v-for="item in statelist"
              :key="item"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button size="btr" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-row>

    <el-table
      v-loading="loading"
      :data="regularinfoList"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column
        label="受检单位"
        align="center"
        key="inspectedUnit"
        prop="inspectedUnit"
      />
      <el-table-column
        label="类型"
        align="center"
        key="inspectionType"
        prop="inspectionType"
      >
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.inspectionType"
            :value="scope.row.inspectionType"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="检查单位"
        align="center"
        prop="unitCheck"
        width="150"
        key="unitCheck"
      />
      <el-table-column
        label="检查时间"
        align="center"
        prop="inspectionTime"
        width="150px"
        key="inspectionTime"
      />
      <el-table-column
        width="250"
        show-overflow-tooltip
        label="检查地点"
        align="center"
        prop="inspectionSite"
        key="inspectionSite"
      />
      <el-table-column label="分值" align="center" key="score" prop="score">
        <template slot-scope="scope">
          <div style="color: rgb(255 106 0)" v-if="queryParams.caoZuoType == 0">
            {{ scope.row.score }}
          </div>
          <div style="color: rgb(2 255 2)" v-if="queryParams.caoZuoType == 1">
            {{ scope.row.score }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="当前状态"
        align="center"
        prop="currentState"
        key="currentState"
      >
        <template slot-scope="scope">
          <div
            style="color: rgb(255 106 0)"
            v-if="scope.row.currentState == '未通过'"
          >
            {{ scope.row.currentState }}
          </div>
          <div style="color: #ffa500" v-if="scope.row.currentState == '待处理'">
            {{ scope.row.currentState }}
          </div>
          <div style="color: #3498db" v-if="scope.row.currentState == '待验收'">
            {{ scope.row.currentState }}
          </div>
          <div
            style="color: rgb(2 255 2)"
            v-if="scope.row.currentState == '已通过'"
          >
            {{ scope.row.currentState }}
          </div>
          <div style="color: #909399" v-if="scope.row.currentState == '已逾期'">
            {{ scope.row.currentState }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="事由"
        show-overflow-tooltip
        align="center"
        prop="question"
        key="question"
      />
      <el-table-column
        label="整改时间"
        align="center"
        prop="rectificationTime"
        v-if="queryParams.caoZuoType == 0"
        width="150px"
        key="rectificationTime"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="250"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            size="btk"
            type="text"
            v-if="
              scope.row.caoZuoType == '0' &&
              scope.row.isCaoZuoDept == 0 &&
              (scope.row.currentState == '未通过' ||
                scope.row.currentState == '待处理')
            "
            @click="handleUpdate(scope.row)"
            >整改</el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="shenpifun(scope.row)"
            v-if="
              queryParams.caoZuoType == 0 &&
              scope.row.isShenPi == 0 &&
              scope.row.currentState == '待验收'
            "
            >审批</el-button
          >
          <!-- v-if="scope.row.unitCheckId==this.danWies" -->
          <!-- bdglregular:regularinfo:query -->
          <el-button
            v-if="scope.row.currentState == '待处理' && scope.row.isShenPi == 0"
            size="btu"
            type="text"
            @click="handleAdds(scope.row)"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-if="scope.row.isShenPi == 0"
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

    <!-- 添加或修改经常性检查对话框 -->
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
        :inline="true"
        label-width="120px"
      >
        <div class="jiben">基本信息</div>
        <el-form-item
          label="检查单位"
          prop="unitCheck"
        >
          <el-select
            v-model="form.unitCheck"
            @change="unitCheckIdfun"
            clearable
            placeholder="请选择检查单位"
            :disabled="disabled && btnnum != 0 && btnnum != 1 && zg == 3"
          >
            <el-option
              v-for="dict in unitId"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          :label="queryParams.caoZuoType == 0 ? '受检单位' : '单位'"
          prop="inspectedUnit"
        >
          <el-select
            v-model="form.inspectedUnit"
            @change="inspectedUnitfun"
            clearable
            :placeholder="
              queryParams.caoZuoType == 0 ? '请选择受检单位' : '请选择单位'
            "
            :disabled="disabled && btnnum != 0 && btnnum != 1 && zg == 3"
          >
            <el-option
              v-for="dict in unitId"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="inspectionType">
          <el-select
            v-model="form.inspectionType"
            :disabled="disabled && btnnum != 0 && btnnum != 1 && zg == 3"
            clearable
            placeholder="请选择类型"
          >
            <el-option
              v-for="dict in inspectionType"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检查时间" prop="inspectionTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.inspectionTime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择检查时间"
            :disabled="disabled && btnnum != 0 && btnnum != 1 && zg == 3"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="检查地点" prop="inspectionSite">
          <el-input
            v-model="form.inspectionSite"
            :disabled="disabled && btnnum != 0 && btnnum != 1 && zg == 3"
            placeholder="请输入检查地点"
          />
        </el-form-item>
        <el-form-item label="分值" prop="score" v-if="jiannum == 0">
          <!-- <el-input v-model="form.score" type="number" @blur="scoreblur" :disabled="disabled" placeholder="请输入分值" /> -->
          <el-input-number
            v-model="form.score"
            controls-position="right"
            :min="0"
          ></el-input-number>
        </el-form-item>
        <el-form-item
          label="整改时间"
          prop="rectificationTime"
          v-if="queryParams.caoZuoType == 0"
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.rectificationTime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择整改时间"
            :disabled="disabled && btnnum != 0 && btnnum != 1 && zg == 3"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
          label="受检负责人"
          prop="chargePeople"
          v-if="jiannum != 0"
        >
          <el-select
            v-model="form.chargePeople"
            clearable
            @change="chargePeoplefun"
            placeholder="请选择受检负责人"
            :disabled="disabled && btnnum != 0 && btnnum != 1 && zg != 3"
          >
            <el-option
              v-for="dict in peopleIds"
              :key="dict.id"
              :label="dict.name"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item>

        <div class="jiben" v-if="queryParams.caoZuoType == 0">存在问题</div>
        <div class="jiben" v-if="queryParams.caoZuoType == 1">加分事由</div>
        <el-form-item label prop="question">
          <el-input
            v-model="form.question"
            :disabled="disabled && btnnum != 0 && btnnum != 1 && zg == 3"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <div class="jiben" v-if="jiannum != 0">整改措施</div>
        <el-form-item label prop="correctiveAction" v-if="jiannum != 0">
          <el-input
            v-model="form.correctiveAction"
            :disabled="disabled && btnnum != 0 && btnnum != 1 && zg != 3"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <div class="jiben" v-if="jiannum != 0">图片举证</div>
        <el-form-item label prop="pictureProof" v-if="jiannum != 0">
          <imageUpload v-model="form.pictureProof" :limit="1" />
        </el-form-item>
        <div class="jiben" v-if="jiannum != 0">文件举证</div>
        <el-form-item label prop="documentProof" v-if="jiannum != 0">
          <fileUpload
            v-model="form.documentProof"
            :limit="1"
            :class="{ active: isActive }"
          />
        </el-form-item>
        <div class="jiben" v-if="currentState == 1">未通过原因</div>
        <el-form-item label prop="score" v-if="currentState == 1">
          <el-input
            v-model="form.reasonReturn"
            :disabled="disabled || curr"
            type="textarea"
            placeholder="请输入未通过原因"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="submitForm"
          v-if="btnnum != 0 && btnnum != 1 && zg != 3"
          >确 定</el-button
        >
        <el-button
          type="primary"
          @click="submitForm2"
          v-if="btnnum != 0 && btnnum != 1 && zg == 3"
          >确 定</el-button
        >
        <el-button type="primary" @click="tongguofun" v-if="btnnum == 0"
          >通 过</el-button
        >
        <el-button type="primary" @click="zhenggaifun" v-if="btnnum == 0"
          >重新整改</el-button
        >
        <el-button type="primary" @click="weitongguofun" v-if="btnnum == 1"
          >确 定</el-button
        >
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :title="title"
      :visible.sync="open1"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        :inline="true"
        label-width="120px"
      >
        <div class="jiben">基本信息</div>
        <el-form-item
          label="检查单位"
          prop="unitCheck"
        >
          <el-select
            v-model="form.unitCheck"
            @change="unitCheckIdfun"
            clearable
            placeholder="请选择检查单位"
            :disabled="disabled"
          >
            <el-option
              v-for="dict in unitId"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          :label="queryParams.caoZuoType == 0 ? '受检单位' : '单位'"
          prop="inspectedUnit"
        >
          <el-select
            v-model="form.inspectedUnit"
            @change="inspectedUnitfun"
            clearable
            :placeholder="
              queryParams.caoZuoType == 0 ? '请选择受检单位' : '请选择单位'
            "
            :disabled="disabled"
          >
            <el-option
              v-for="dict in unitId"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="inspectionType">
          <el-select
            v-model="form.inspectionType"
            :disabled="disabled"
            clearable
            placeholder="请选择类型"
          >
            <el-option
              v-for="dict in inspectionType"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检查时间" prop="inspectionTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.inspectionTime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择检查时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="检查地点" prop="inspectionSite">
          <el-input
            v-model="form.inspectionSite"
            :disabled="disabled"
            placeholder="请输入检查地点"
          />
        </el-form-item>
        <el-form-item label="分值" prop="score">
          <!-- <el-input v-model="form.score" type="number" @blur="scoreblur" :disabled="disabled" placeholder="请输入分值" /> -->
          <el-input-number
            v-model="form.score"
            controls-position="right"
            :min="0"
          ></el-input-number>
        </el-form-item>
        <el-form-item
          label="整改时间"
          prop="rectificationTime"
          v-if="queryParams.caoZuoType == 0"
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.rectificationTime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择整改时间"
            :disabled="disabled"
          ></el-date-picker>
        </el-form-item>

        <div class="jiben" v-if="queryParams.caoZuoType == 0">存在问题</div>
        <div class="jiben" v-if="queryParams.caoZuoType == 1">加分事由</div>
        <el-form-item label prop="question">
          <el-input
            v-model="form.question"
            :disabled="disabled"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open1 = false" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :title="title"
      :visible.sync="open3"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        :inline="true"
        label-width="120px"
      >
        <div class="jiben">基本信息</div>
        <el-form-item
          label="检查单位"
          prop="unitCheck"
        >
          <el-select
            v-model="form.unitCheck"
            @change="unitCheckIdfun"
            clearable
            placeholder="请选择检查单位"
            :disabled="disabled"
          >
            <el-option
              v-for="dict in unitId"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          :label="queryParams.caoZuoType == 0 ? '受检单位' : '单位'"
          prop="inspectedUnit"
        >
          <el-select
            v-model="form.inspectedUnit"
            @change="inspectedUnitfun"
            clearable
            :placeholder="
              queryParams.caoZuoType == 0 ? '请选择受检单位' : '请选择单位'
            "
            :disabled="disabled"
          >
            <el-option
              v-for="dict in unitId"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="inspectionType">
          <el-select
            v-model="form.inspectionType"
            :disabled="disabled"
            clearable
            placeholder="请选择类型"
          >
            <el-option
              v-for="dict in inspectionType"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检查时间" prop="inspectionTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.inspectionTime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择检查时间"
            :disabled="disabled"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="检查地点" prop="inspectionSite">
          <el-input
            v-model="form.inspectionSite"
            :disabled="disabled"
            placeholder="请输入检查地点"
          />
        </el-form-item>
        <el-form-item label="分值" prop="score" v-if="jiannum == 0">
          <!-- <el-input v-model="form.score" type="number" @blur="scoreblur" :disabled="disabled" placeholder="请输入分值" /> -->
          <el-input-number
            v-model="form.score"
            controls-position="right"
            :min="0"
          ></el-input-number>
        </el-form-item>
        <el-form-item
          label="整改时间"
          prop="rectificationTime"
          v-if="queryParams.caoZuoType == 0"
        >
          <el-date-picker
            clearable
            size="small"
            v-model="form.rectificationTime"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择整改时间"
            :disabled="disabled"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
          label="受检负责人"
          prop="chargePeople"
          v-if="jiannum != 0"
        >
          <el-select
            v-model="form.chargePeople"
            clearable
            @change="chargePeoplefun"
            placeholder="请选择受检负责人"
            :disabled="disabled"
          >
            <el-option
              v-for="dict in peopleIds"
              :key="dict.id"
              :label="dict.name"
              :value="dict"
            ></el-option>
          </el-select>
        </el-form-item>

        <div class="jiben" v-if="queryParams.caoZuoType == 0">存在问题</div>
        <div class="jiben" v-if="queryParams.caoZuoType == 1">加分事由</div>
        <el-form-item label prop="question">
          <el-input
            v-model="form.question"
            :disabled="disabled"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <div class="jiben" v-if="jiannum != 0">整改措施</div>
        <el-form-item label prop="correctiveAction" v-if="jiannum != 0">
          <el-input
            v-model="form.correctiveAction"
            :disabled="disabled"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <div class="jiben" v-if="jiannum != 0">图片举证</div>
        <el-form-item label prop="pictureProof" v-if="jiannum != 0">
          <imageUpload v-model="form.pictureProof" :limit="1" />
        </el-form-item>
        <div class="jiben" v-if="jiannum != 0&&wenName==1">文件举证</div>
        <el-form-item label prop="documentProof" v-if="jiannum != 0&&wenName==1">
          <!-- <fileUpload
            v-model="form.documentProof"
            :limit="1"
            :class="{ active: isActive }"
          /> -->
          <input
            label="附件名称"
            v-model="form.wenjianname"
            style="width: 600px"
            class="wenjian"
            disabled
          />
          <a
            :href="url + form.documentProof"
            :download="form.wenjianname"
            title="下载"
            style="color: white; margin-left: 10px"
            class="downloadfile"
            >下载附件</a
          >
        </el-form-item>
        <div class="jiben" v-if="currentState == 1">未通过原因</div>
        <el-form-item label prop="score" v-if="currentState == 1">
          <el-input
            v-model="form.reasonReturn"
            :disabled="disabled || curr"
            type="textarea"
            placeholder="请输入未通过原因"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="submitForm"
          v-if="btnnum != 0 && btnnum != 1 && zg != 3"
          >确 定</el-button
        >
        <el-button
          type="primary"
          @click="submitForm2"
          v-if="btnnum != 0 && btnnum != 1 && zg == 3"
          >确 定</el-button
        >
        <el-button type="primary" @click="tongguofun" v-if="btnnum == 0"
          >通 过</el-button
        >
        <el-button type="primary" @click="zhenggaifun" v-if="btnnum == 0"
          >重新整改</el-button
        >
        <el-button type="primary" @click="weitongguofun" v-if="btnnum == 1"
          >确 定</el-button
        >
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listRegularinfo,
  getRegularinfo,
  delRegularinfo,
  addRegularinfo,
  updateRegularinfo,
  exportRegularinfo,
  getDept,
  listPeople,
  editquestion,
  editissuccess,
  editisfalse,
  bumenlist,
  getDeptss,
  listRegularinfos,
} from "@/api/bdglregular/regularinfo";
import { getUserProfile } from "@/api/system/user";
// 查询字典
import { getDicts } from "@/api/system/dict/data";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Regularinfo",
  dicts: ["inspectionType"],
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
      // 经常性检查表格数据
      regularinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inspectedUnitId: null,
        inspectedUnit: null,
        inspectionType: null,
        unitCheckId: null,
        unitCheck: null,
        inspectionTime: null,
        inspectionSite: null,
        score: null,
        rectificationTime: null,
        currentState: null,
        question: null,
        createUserId: null,
        createUser: null,
        chargePeopleId: null,
        chargePeople: null,
        correctiveAction: null,
        pictureProof: null,
        documentProof: null,
        caoZuoType: 0,
      },
      // 用户
      user: {},
      // danWies:0,
      yongHu: "",
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitCheck: [
          { required: true, message: "检查单位不能为空", trigger: "change" },
        ],
        inspectedUnit: [
          { required: true, message: "受检单位不能为空", trigger: "change" },
        ],
        inspectionType: [
          { required: true, message: "类型不能为空", trigger: "change" },
        ],
        inspectionTime: [
          { required: true, message: "检查时间不能为空", trigger: "change" },
        ],
        inspectionSite: [
          { required: true, message: "检查地点不能为空", trigger: "blur" },
        ],
        score: [{ required: true, message: "分值不能为空", trigger: "blur" }],
        rectificationTime: [
          { required: true, message: "整改时间不能为空", trigger: "change" },
        ],
        chargePeople: [
          { required: true, message: "负责人不能为空", trigger: "change" },
        ],
        question: [
          {
            required: true,
            message: "存在问题或加分事由不能为空",
            trigger: "change",
          },
        ],
        correctiveAction: [
          { required: true, message: "整改措施不能为空", trigger: "change" },
        ],
      },
      //单位
      unitId: [],
      // 树形结构列表
      deptOptions: [],
      //检查类型
      inspectionType: [],
      //调动人员对象
      peopleIds: [],
      //区分减分、增改
      jiannum: null,
      btnnum: null,
      disabled: false,
      currentState: null,
      curr: null,
      isdisabled: false,
      open1: false,
      zg: null,
      isActive: null,
      sp: null,
      open3: false,
      url: process.env.VUE_APP_BASE_API,
      statelist: ["未通过", "待处理", "待验收", "已通过", "已逾期"],
      restdata: false,
      wenName:null
    };
  },
  created() {
    // this.danWiess()
    this.queryParams = {
      pageNum: 1,
      pageSize: 10,
      inspectedUnitId: null,
      inspectedUnit: null,
      inspectionType: null,
      unitCheckId: null,
      unitCheck: null,
      inspectionTime: null,
      inspectionSite: null,
      score: null,
      rectificationTime: null,
      currentState: null,
      question: null,
      createUserId: null,
      createUser: null,
      chargePeopleId: null,
      chargePeople: null,
      correctiveAction: null,
      pictureProof: null,
      documentProof: null,
      caoZuoType: 0,
    };
    // this.danWiess()
    //部门数据
    getDept().then((res) => {
      if (res.code == 200) {
        this.unitId = res.data;
      }
    });
    //调动人员对象
    listPeople().then((res) => {
      if (res.code == 200) {
        this.peopleIds = res.rows;
      }
    });
    //检查类型
    this.getDicts("inspectionType").then((response) => {
      this.inspectionType = response.data;
    });
    if (this.$route.query.obj) {
      this.queryParams.inspectedUnitId =
        this.$route.query.obj.inspectedUnitId || this.$route.query.obj.id;
      this.queryParams.currentState =
        this.$route.query.obj.currentState || this.$route.query.obj.name;
      listRegularinfos(this.queryParams).then((res) => {
        this.regularinfoList = res.rows;
        this.total = res.total;
        this.loading = false;
      });
    } else {
      this.getList();
    }
    // 获取单位树形
    this.getBuMeng();
  },
  watch: {
    $route(to, from) {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        inspectedUnitId: null,
        inspectedUnit: null,
        inspectionType: null,
        unitCheckId: null,
        unitCheck: null,
        inspectionTime: null,
        inspectionSite: null,
        score: null,
        rectificationTime: null,
        currentState: null,
        question: null,
        createUserId: null,
        createUser: null,
        chargePeopleId: null,
        chargePeople: null,
        correctiveAction: null,
        pictureProof: null,
        documentProof: null,
        caoZuoType: 0,
      };
      if (to.query.obj) {
        this.queryParams.inspectedUnitId =
          to.query.obj.inspectedUnitId || to.query.obj.id;
        this.queryParams.currentState =
          to.query.obj.currentState || to.query.name;
        listRegularinfos(this.queryParams).then((res) => {
          this.regularinfoList = res.rows;
          this.total = res.total;
          this.loading = false;
        });
      } else {
        this.getList();
      }
      // listRegularinfos
    },
  },
  mounted() {
    // this.danWiess()
  },
  methods: {
    selectPeos(data) {
      this.queryParams.inspectedUnitId = data.id;
      this.getList();
    },
    // 获取单位列表
    getBuMeng() {
      getDeptss().then((res) => {
        this.deptOptions = res.data;
      });
    },
    danWiess() {
      // 获取当前用户接口
      getUserProfile().then((response) => {
        this.user = response.data;

        // if(response)
        this.danWies = response.data.dept.deptId;
      });
    },
    /** 查询经常性检查列表 */
    getList() {
      this.loading = true;
      listRegularinfo(this.queryParams).then((response) => {
        this.regularinfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open3 = false;
      this.reset();
      this.jiannum = null;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        inspectedUnitId: null,
        inspectedUnit: null,
        inspectionType: null,
        unitCheckId: null,
        unitCheck: null,
        inspectionTime: null,
        inspectionSite: null,
        score: null,
        rectificationTime: null,
        currentState: null,
        question: null,
        createTime: null,
        createUserId: null,
        createUser: null,
        updateTime: null,
        chargePeopleId: null,
        chargePeople: null,
        correctiveAction: null,
        pictureProof: null,
        documentProof: null,
        reasonReturn: null,
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
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        caoZuoType: 0,
      };
      // this.resetForm("queryForm");
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
      this.title = "经常性检查";
      this.jiannum = 0;
      this.btnnum = null;
      this.isdisabled = false;
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
      this.form.inspectionTime =
        y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
    },
    // // 修改按钮操作
    // handleAdds(row){
    //   this.open = true;
    //   this.title = "经常性检查";
    //   const id = row.id || this.ids;
    //   getRegularinfo(id).then((response)=>{
    //     this.form = response.data;
    //     this.open = true;
    //   })
    // },
    /** 整改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getRegularinfo(id).then((response) => {
        this.form = response.data;
        this.disabled = true;
        this.open = true;
        this.title = "修改经常性检查";
        this.jiannum = 1;
        this.btnnum = null;
        this.zg = 3;
        this.isActive = null;
        if (this.form.currentState == "未通过") {
          this.currentState = 1;
          this.curr = true;
        }
      });
    },
    // 修改按钮
    handleAdds(row) {
      this.isdisabled = true;
      this.reset();
      const id = row.id || this.ids;
      getRegularinfo(id).then((response) => {
        this.form = response.data;
        this.open1 = true;
        this.title = "修改经常性检查";
        // this.jiannum = 1;
        // this.btnnum = null;
        // if (this.form.currentState == "未通过") {
        //   this.currentState = 1;
        //   this.curr = true;
        // }
      });
    },
    /** 审批按钮操作 */
    shenpifun(row) {
      this.reset();
      const id = row.id || this.ids;
      getRegularinfo(id).then((response) => {
        this.disabled = true;
        this.form = response.data;
        if(response.data.wenjianname){
          this.wenName=1
        }else{
          this.wenName=0
        }
        this.open3 = true;
        this.title = "修改经常性检查";
        this.jiannum = 1;
        this.btnnum = 0;
        this.isActive = 1;
        this.sp = 0;
      });
    },
    //通过
    tongguofun() {
      editissuccess(this.form).then((res) => {
        if (res.code == 200) {
          this.$modal.msgSuccess("审批成功");
          this.open = false;
          this.open3 = false;
          this.getList();
        }
      });
    },
    //重新整改
    zhenggaifun() {
      this.jiannum = 0;
      this.btnnum = 1;
      this.disabled = false;
      this.currentState = 1;
      this.curr = false;
      this.open3 = false;
      this.open = true;
      // this.reset();
      // editissuccess().then(res => {
      //   if (res.code == 200) {
      //     this.$modal.msgSuccess("审批成功");
      //   }
      // });
    },
    //未通过提交
    weitongguofun() {
      editisfalse(this.form).then((res) => {
        this.$modal.msgSuccess("操作成功");
        this.open = false;
        this.getList();
      });
    },
    submitForm2() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.pictureProof || this.form.documentProof) {
            if (this.form.documentProof) {
              console.log(this.form.documentProof.length>0)
              if (typeof this.form.documentProof != "string") {
                let documentProofs = this.form.documentProof;
                if(documentProofs.length>0){
                  this.form.documentProof = documentProofs[0].url;
                this.form.wenjianname = documentProofs[0].name;
                }else{
                  this.form.documentProof = "";
                this.form.wenjianname = "";
                }
              }
            }
            this.form.caoZuoType = this.queryParams.caoZuoType;
            editquestion(this.form).then((response) => {
              this.$modal.msgSuccess("整改成功");
              this.open = false;
              this.open1 = false;
              // this.open3=false;
              this.getList();
            });
          }else{
            this.$message.error("请上传图片或者文件！")
          }
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.documentProof) {
            if (typeof this.form.documentProof != "string") {
              let documentProofs = this.form.documentProof;
              this.form.documentProof = documentProofs[0].url;
              this.form.wenjianname = documentProofs[0].name;
            }
          }
          if (this.form.id != null) {
            this.form.caoZuoType = this.queryParams.caoZuoType;
            updateRegularinfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.open1 = false;
              // this.open3=false;
              this.getList();
            });
          } else {
            this.form.caoZuoType = this.queryParams.caoZuoType;
            addRegularinfo(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.open1 = false;
              // this.open3=false;
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
        .confirm("是否确认删除经常性检查的数据项？")
        .then(function () {
          return delRegularinfo(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有经常性检查数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportRegularinfo(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    //检查单位
    unitCheckIdfun(val) {
      this.form.unitCheckId = val.deptId;
      this.form.unitCheck = val.deptName;
    },
    //受检单位
    inspectedUnitfun(val) {
      this.form.inspectedUnitId = val.deptId;
      this.form.inspectedUnit = val.deptName;
    },
    //负责人
    chargePeoplefun(val) {
      this.form.chargePeopleId = val.id;
      this.form.chargePeople = val.name;
    },
    caoZuoTypefun() {
      this.getList();
    },
    scoreblur() {
      if (this.form.score < 0) {
        this.form.score = null;
      }
    },
  },
};
</script>
<style scoped>
/* 选择状态样式 */
::v-deep .states .el-input .el-input__inner {
  background-color: transparent !important;
}
/* 树形样式 */
::v-deep .vue-treeselect {
  width: 220px;
}
::v-deep .vue-treeselect__control {
  background-color: transparent;
}
::v-deep .vue-treeselect__single-value {
  color: #fff;
}
::v-deep .el-dialog {
  width: 1030px !important;
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
  background: #004d86;
  color: #fff;
  border: 1px solid #fff !important;
}
::v-deep .el-date-editor.el-input {
  width: 200px;
  height: 36px;
}
::v-deep .el-upload--picture-card {
  height: 210px;
  width: 170px;
  line-height: 210px;
}
.el-upload--picture-card /deep/ .el-upload--picture-card i {
  font-size: 48px;
}
::v-deep .el-upload__tip {
  display: none;
}
::v-deep .el-textarea__inner {
  width: 980px;
  height: 104px;
}
.div-box-title span {
  width: 25px;
  height: 25px;
  display: inline-block;
  background-color: #1c86e1;
  line-height: 25px;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  margin-left: 865px;
}
.el-select-dropdown__item:hover {
  color: #1c86e1;
}
.el-select-dropdown__item {
  color: #000;
}
.el-tree {
  background-color: #00365f;
  color: #fff;
}
::v-deep .el-tree-node__content:hover {
  color: #1c86e1;
}
::v-deep .el-upload-list--picture-card .el-upload-list__item {
  height: 210px;
  width: 170px;
}
::v-deep .radiogroup {
  height: 36px;
}
::v-deep .radiogroup .el-radio-button {
  height: 36px;
}
::v-deep .el-input-number__increase {
  background-color: transparent !important;
  border: none;
  color: #cccccc;
  border-bottom: none !important;
}
::v-deep .el-input-number__decrease {
  background-color: transparent !important;
  border: none !important;
  color: #cccccc;
}
::v-deep .el-dialog__body {
  padding: 30px 0px 30px 25px;
}
::v-deep .el-dialog__footer {
  margin-right: 5px;
}
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
/* 将水平线设为红色： */
hr {
  border: none;
  height: 1px;
  background-color: rgba(204, 204, 204, 0.5);
}
::v-deep img {
  object-fit: cover;
}
::v-deep .active .upload-file-uploader .el-upload .el-button {
  display: none;
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
.wenjian {
  width: 600px !important;
  height: 33px;
}

::v-deep .el-link.el-link--default {
  color: #fff;
}
</style>
