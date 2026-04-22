<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="unitName">
        <el-input
          placeholder="请输入单位"
          v-model="queryParams.unitName"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="createtime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择时间"
          @change="time"
          :editable="false"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
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
          v-hasPermi="['grassrootsregistration:weekRecipe:add']"
          >新增</el-button
        >
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="weekRecipeList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="单位"
        align="center"
        prop="unitName"
        width="150"
      />
      <el-table-column label="食谱标题" align="center" prop="title" />
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
      <el-table-column label="司务长" align="center" prop="messOfficerName" />
      <el-table-column label="班长" align="center" prop="peopleName" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="280"
      >
        <template slot-scope="scope">
          <el-button
            size="btk"
            type="text"
            @click="handleChakan(scope.row)"
            v-hasPermi="['grassrootsregistration:weekRecipe:edit']"
            ><span class="chakan">预览</span></el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['grassrootsregistration:weekRecipe:edit']"
            ><span class="edit">修改</span></el-button
          >
          <el-button
            size="mini"
            type="warning"
            @click="handleExportWord(scope.row)"
            ><span class="edit">Word 导出</span></el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:weekRecipe:remove']"
            ><span class="delete">删除</span></el-button
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

    <!-- 添加或修改一周食谱对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1200px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <div class="jiben">基本信息</div>
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="100px"
          :inline="true"
        >
          <el-form-item label="单位" prop="unitId">
            <treeselect
              v-model="form.unitId"
              :options="deptOptions"
              placeholder="请选择单位"
              @select="selectPeo"
            />
          </el-form-item>
          <el-form-item label="食谱标题" prop="title">
            <el-input v-model="form.title" placeholder="请输入食谱标题" />
          </el-form-item>
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form.startTime"
              type="date"
              :editable="false"
              value-format="yyyy-MM-dd"
              placeholder="请选择开始时间"
              @change="timeDay"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker
              clearable
              :editable="false"
              size="small"
              v-model="form.endTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择结束时间"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="司务长" prop="messOfficer">
            <el-select
              v-model="form.messOfficer"
              placeholder="请选择人员"
              clearable
            >
              <el-option
                v-for="item in renYuan"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                :disabled="item.status == 1"
                @click.native="siWu(item.name)"
                class="input_xiala"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="班长" prop="peopleId">
            <el-select
              v-model="form.peopleId"
              placeholder="请选择人员"
              clearable
            >
              <el-option
                v-for="item in renYuan"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                :disabled="item.status == 1"
                @click.native="monitor(item.name)"
                class="input_xiala"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>

        <div class="jiben">食谱内容</div>
        <div class="recipe-table-container">
          <table border="1" width="100%" cellspacing="0" class="recipe-table">
            <thead>
              <tr>
                <th width="8%">餐次</th>
                <th width="13%">星期一<br/>{{ mondayDate }}</th>
                <th width="13%">星期二<br/>{{ tuesdayDate }}</th>
                <th width="13%">星期三<br/>{{ wednesdayDate }}</th>
                <th width="13%">星期四<br/>{{ thursdayDate }}</th>
                <th width="13%">星期五<br/>{{ fridayDate }}</th>
                <th width="13%">星期六<br/>{{ saturdayDate }}</th>
                <th width="14%">星期日<br/>{{ sundayDate }}</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td rowspan="2" class="meal-type">早餐</td>
                <td>
                  <el-input
                    v-model="recipeContent.monday.breakfast"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.tuesday.breakfast"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.wednesday.breakfast"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.thursday.breakfast"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.friday.breakfast"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.saturday.breakfast"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.sunday.breakfast"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
              </tr>
              <tr>
                <td>
                  <el-input
                    v-model="recipeContent.monday.breakfast2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.tuesday.breakfast2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.wednesday.breakfast2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.thursday.breakfast2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.friday.breakfast2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.saturday.breakfast2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.sunday.breakfast2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
              </tr>
              <tr>
                <td rowspan="2" class="meal-type">午餐</td>
                <td>
                  <el-input
                    v-model="recipeContent.monday.lunch"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.tuesday.lunch"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.wednesday.lunch"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.thursday.lunch"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.friday.lunch"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.saturday.lunch"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.sunday.lunch"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
              </tr>
              <tr>
                <td>
                  <el-input
                    v-model="recipeContent.monday.lunch2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.tuesday.lunch2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.wednesday.lunch2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.thursday.lunch2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.friday.lunch2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.saturday.lunch2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.sunday.lunch2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
              </tr>
              <tr>
                <td rowspan="2" class="meal-type">晚餐</td>
                <td>
                  <el-input
                    v-model="recipeContent.monday.dinner"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.tuesday.dinner"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.wednesday.dinner"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.thursday.dinner"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.friday.dinner"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.saturday.dinner"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.sunday.dinner"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入菜品"
                  ></el-input>
                </td>
              </tr>
              <tr>
                <td>
                  <el-input
                    v-model="recipeContent.monday.dinner2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.tuesday.dinner2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.wednesday.dinner2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.thursday.dinner2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.friday.dinner2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.saturday.dinner2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
                <td>
                  <el-input
                    v-model="recipeContent.sunday.dinner2"
                    type="textarea"
                    :rows="2"
                    placeholder="补充菜品"
                  ></el-input>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="jiben" style="margin-top: 20px;">签字栏</div>
        <el-form :inline="true" class="sign-form">
          <el-form-item label="单位首长">
            <el-input v-model="form.leaderSign" placeholder="请输入单位首长姓名" />
          </el-form-item>
          <el-form-item label="军人委员会">
            <el-input v-model="form.committeeSign" placeholder="请输入军人委员会姓名" />
          </el-form-item>
          <el-form-item label="司务长">
            <el-input v-model="form.messOfficerSign" placeholder="请输入司务长姓名" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 预览对话框 -->
    <el-dialog
      :title="previewTitle"
      :visible.sync="open2"
      width="1200px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="preview-container">
        <div class="preview-header">
          <div class="preview-title">一周食谱表</div>
          <div class="preview-info">
            <span>单位：{{ previewData.unitName }}</span>
            <span>时间：{{ previewData.startTime }} 至 {{ previewData.endTime }}</span>
          </div>
        </div>
        <table border="1" width="100%" cellspacing="0" class="preview-table">
          <thead>
            <tr>
              <th width="8%">餐次</th>
              <th width="13%">星期一</th>
              <th width="13%">星期二</th>
              <th width="13%">星期三</th>
              <th width="13%">星期四</th>
              <th width="13%">星期五</th>
              <th width="13%">星期六</th>
              <th width="14%">星期日</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="meal-type">早餐</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.monday) ? previewData.recipeContent.monday.breakfast : '' }}{{ (previewData.recipeContent && previewData.recipeContent.monday && previewData.recipeContent.monday.breakfast2) ? '\n' + previewData.recipeContent.monday.breakfast2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.tuesday) ? previewData.recipeContent.tuesday.breakfast : '' }}{{ (previewData.recipeContent && previewData.recipeContent.tuesday && previewData.recipeContent.tuesday.breakfast2) ? '\n' + previewData.recipeContent.tuesday.breakfast2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.wednesday) ? previewData.recipeContent.wednesday.breakfast : '' }}{{ (previewData.recipeContent && previewData.recipeContent.wednesday && previewData.recipeContent.wednesday.breakfast2) ? '\n' + previewData.recipeContent.wednesday.breakfast2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.thursday) ? previewData.recipeContent.thursday.breakfast : '' }}{{ (previewData.recipeContent && previewData.recipeContent.thursday && previewData.recipeContent.thursday.breakfast2) ? '\n' + previewData.recipeContent.thursday.breakfast2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.friday) ? previewData.recipeContent.friday.breakfast : '' }}{{ (previewData.recipeContent && previewData.recipeContent.friday && previewData.recipeContent.friday.breakfast2) ? '\n' + previewData.recipeContent.friday.breakfast2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.saturday) ? previewData.recipeContent.saturday.breakfast : '' }}{{ (previewData.recipeContent && previewData.recipeContent.saturday && previewData.recipeContent.saturday.breakfast2) ? '\n' + previewData.recipeContent.saturday.breakfast2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.sunday) ? previewData.recipeContent.sunday.breakfast : '' }}{{ (previewData.recipeContent && previewData.recipeContent.sunday && previewData.recipeContent.sunday.breakfast2) ? '\n' + previewData.recipeContent.sunday.breakfast2 : '' }}</td>
            </tr>
            <tr>
              <td class="meal-type">午餐</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.monday) ? previewData.recipeContent.monday.lunch : '' }}{{ (previewData.recipeContent && previewData.recipeContent.monday && previewData.recipeContent.monday.lunch2) ? '\n' + previewData.recipeContent.monday.lunch2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.tuesday) ? previewData.recipeContent.tuesday.lunch : '' }}{{ (previewData.recipeContent && previewData.recipeContent.tuesday && previewData.recipeContent.tuesday.lunch2) ? '\n' + previewData.recipeContent.tuesday.lunch2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.wednesday) ? previewData.recipeContent.wednesday.lunch : '' }}{{ (previewData.recipeContent && previewData.recipeContent.wednesday && previewData.recipeContent.wednesday.lunch2) ? '\n' + previewData.recipeContent.wednesday.lunch2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.thursday) ? previewData.recipeContent.thursday.lunch : '' }}{{ (previewData.recipeContent && previewData.recipeContent.thursday && previewData.recipeContent.thursday.lunch2) ? '\n' + previewData.recipeContent.thursday.lunch2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.friday) ? previewData.recipeContent.friday.lunch : '' }}{{ (previewData.recipeContent && previewData.recipeContent.friday && previewData.recipeContent.friday.lunch2) ? '\n' + previewData.recipeContent.friday.lunch2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.saturday) ? previewData.recipeContent.saturday.lunch : '' }}{{ (previewData.recipeContent && previewData.recipeContent.saturday && previewData.recipeContent.saturday.lunch2) ? '\n' + previewData.recipeContent.saturday.lunch2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.sunday) ? previewData.recipeContent.sunday.lunch : '' }}{{ (previewData.recipeContent && previewData.recipeContent.sunday && previewData.recipeContent.sunday.lunch2) ? '\n' + previewData.recipeContent.sunday.lunch2 : '' }}</td>
            </tr>
            <tr>
              <td class="meal-type">晚餐</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.monday) ? previewData.recipeContent.monday.dinner : '' }}{{ (previewData.recipeContent && previewData.recipeContent.monday && previewData.recipeContent.monday.dinner2) ? '\n' + previewData.recipeContent.monday.dinner2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.tuesday) ? previewData.recipeContent.tuesday.dinner : '' }}{{ (previewData.recipeContent && previewData.recipeContent.tuesday && previewData.recipeContent.tuesday.dinner2) ? '\n' + previewData.recipeContent.tuesday.dinner2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.wednesday) ? previewData.recipeContent.wednesday.dinner : '' }}{{ (previewData.recipeContent && previewData.recipeContent.wednesday && previewData.recipeContent.wednesday.dinner2) ? '\n' + previewData.recipeContent.wednesday.dinner2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.thursday) ? previewData.recipeContent.thursday.dinner : '' }}{{ (previewData.recipeContent && previewData.recipeContent.thursday && previewData.recipeContent.thursday.dinner2) ? '\n' + previewData.recipeContent.thursday.dinner2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.friday) ? previewData.recipeContent.friday.dinner : '' }}{{ (previewData.recipeContent && previewData.recipeContent.friday && previewData.recipeContent.friday.dinner2) ? '\n' + previewData.recipeContent.friday.dinner2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.saturday) ? previewData.recipeContent.saturday.dinner : '' }}{{ (previewData.recipeContent && previewData.recipeContent.saturday && previewData.recipeContent.saturday.dinner2) ? '\n' + previewData.recipeContent.saturday.dinner2 : '' }}</td>
              <td>{{ (previewData.recipeContent && previewData.recipeContent.sunday) ? previewData.recipeContent.sunday.dinner : '' }}{{ (previewData.recipeContent && previewData.recipeContent.sunday && previewData.recipeContent.sunday.dinner2) ? '\n' + previewData.recipeContent.sunday.dinner2 : '' }}</td>
            </tr>
          </tbody>
        </table>
        <div class="preview-footer">
          <span>单位首长：{{ previewData.leaderSign }}</span>
          <span>军人委员会：{{ previewData.committeeSign }}</span>
          <span>司务长：{{ previewData.messOfficerSign }}</span>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel2" size="btn">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listWeekRecipe,
  getWeekRecipe,
  delWeekRecipe,
  addWeekRecipe,
  updateWeekRecipe,
  exportWeekRecipeWord
} from "@/api/grassrootsregistration/weekRecipe";
import { treeselect } from "@/api/system/dept";
import { listBdglfood } from "@/api/grassrootsregistration/bdglfood";

export default {
  name: "WeekRecipe",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      weekRecipeList: [],
      title: "",
      open: false,
      open2: false,
      previewTitle: "一周食谱预览",
      previewData: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitName: null,
        startTime: null
      },
      form: {
        id: null,
        unitId: null,
        unitName: null,
        title: null,
        startTime: null,
        endTime: null,
        messOfficer: null,
        messOfficerName: null,
        peopleId: null,
        peopleName: null,
        leaderSign: null,
        committeeSign: null,
        messOfficerSign: null
      },
      rules: {
        unitId: [{ required: true, message: "请选择单位", trigger: "blur" }],
        title: [{ required: true, message: "请输入食谱标题", trigger: "blur" }],
        startTime: [{ required: true, message: "请选择开始时间", trigger: "change" }],
        endTime: [{ required: true, message: "请选择结束时间", trigger: "change" }]
      },
      deptOptions: [],
      renYuan: [],
      renYuans: [],
      recipeContent: {
        monday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        tuesday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        wednesday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        thursday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        friday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        saturday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        sunday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' }
      },
      startDate: null,
      mondayDate: '',
      tuesdayDate: '',
      wednesdayDate: '',
      thursdayDate: '',
      fridayDate: '',
      saturdayDate: '',
      sundayDate: ''
    };
  },
  created() {
    this.getList();
    this.getDeptTre();
  },
  methods: {
    getList() {
      this.loading = true;
      listWeekRecipe(this.queryParams).then(response => {
        this.weekRecipeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getDeptTre() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    selectPeo(node) {
      this.form.unitName = node.label;
      this.getPeopleList();
    },
    getPeopleList() {
      const params = { unitId: this.form.unitId };
      listBdglfood(params).then(response => {
        this.renYuan = response.rows || [];
      });
    },
    siWu(name) {
      this.form.messOfficerName = name;
    },
    monitor(name) {
      this.form.peopleName = name;
    },
    timeDay() {
      this.startDate = this.form.startTime;
      this.calculateWeekDates();
    },
    calculateWeekDates() {
      if (!this.startDate) return;
      const start = new Date(this.startDate);
      const dayOfWeek = start.getDay();
      const monday = new Date(start);
      monday.setDate(start.getDate() - (dayOfWeek === 0 ? 6 : dayOfWeek - 1));

      const formatDate = (date) => {
        const month = date.getMonth() + 1;
        const day = date.getDate();
        return `${month}月${day}日`;
      };

      this.mondayDate = formatDate(monday);
      this.tuesdayDate = formatDate(new Date(monday.getTime() + 86400000));
      this.wednesdayDate = formatDate(new Date(monday.getTime() + 86400000 * 2));
      this.thursdayDate = formatDate(new Date(monday.getTime() + 86400000 * 3));
      this.fridayDate = formatDate(new Date(monday.getTime() + 86400000 * 4));
      this.saturdayDate = formatDate(new Date(monday.getTime() + 86400000 * 5));
      this.sundayDate = formatDate(new Date(monday.getTime() + 86400000 * 6));
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加一周食谱";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWeekRecipe(id).then(response => {
        this.form = response.data;
        if (response.data.recipeContent) {
          this.recipeContent = JSON.parse(response.data.recipeContent);
        }
        this.startDate = this.form.startTime;
        this.calculateWeekDates();
        this.open = true;
        this.title = "修改一周食谱";
      });
    },
    handleChakan(row) {
      const id = row.id;
      getWeekRecipe(id).then(response => {
        this.previewData = response.data;
        if (response.data.recipeContent) {
          this.previewData.recipeContent = JSON.parse(response.data.recipeContent);
        }
        this.open2 = true;
      });
    },
    handleExportWord(row) {
      const id = row.id;
      exportWeekRecipeWord(id).then(response => {
        const blob = new Blob([response.data], {
          type: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
        });
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = `${row.title || '一周食谱'}.docx`;
        link.click();
        window.URL.revokeObjectURL(link.href);
        this.$modal.msgSuccess("导出成功");
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除一周食谱编号为"' + ids + '"的数据项？')
        .then(function() {
          return delWeekRecipe(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.recipeContent = JSON.stringify(this.recipeContent);
          if (this.form.id != null) {
            updateWeekRecipe(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWeekRecipe(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    cancel2() {
      this.open2 = false;
    },
    reset() {
      this.form = {
        id: null,
        unitId: null,
        unitName: null,
        title: null,
        startTime: null,
        endTime: null,
        messOfficer: null,
        messOfficerName: null,
        peopleId: null,
        peopleName: null,
        leaderSign: null,
        committeeSign: null,
        messOfficerSign: null
      };
      this.recipeContent = {
        monday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        tuesday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        wednesday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        thursday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        friday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        saturday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' },
        sunday: { breakfast: '', breakfast2: '', lunch: '', lunch2: '', dinner: '', dinner2: '' }
      };
      this.startDate = null;
      this.mondayDate = '';
      this.tuesdayDate = '';
      this.wednesdayDate = '';
      this.thursdayDate = '';
      this.fridayDate = '';
      this.saturdayDate = '';
      this.sundayDate = '';
      this.resetForm("form");
    }
  }
};
</script>

<style scoped>
::v-deep .el-dialog {
  background: #004d86 !important;
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

.jiben {
  color: #fff;
  font-size: 16px;
  margin-bottom: 10px;
  margin-left: 5px;
}

.recipe-table-container {
  margin: 10px 0;
  overflow-x: auto;
}

.recipe-table {
  border-collapse: collapse;
  background: #fff;
}

.recipe-table th,
.recipe-table td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: center;
}

.recipe-table th {
  background: #f5f5f5;
  color: #333;
}

.meal-type {
  background: #e8e8e8;
  font-weight: bold;
}

::v-deep .el-textarea__inner {
  width: 100%;
  min-height: 60px !important;
}

.sign-form {
  margin-top: 20px;
}

.sign-form ::v-deep .el-form-item__label {
  width: 80px !important;
}

.sign-form ::v-deep .el-input__inner {
  width: 150px;
}

.preview-container {
  background: #fff;
  padding: 20px;
  color: #333;
}

.preview-header {
  text-align: center;
  margin-bottom: 20px;
}

.preview-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

.preview-info {
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
}

.preview-table {
  border-collapse: collapse;
  width: 100%;
}

.preview-table th,
.preview-table td {
  border: 1px solid #333;
  padding: 10px;
  text-align: center;
}

.preview-table th {
  background: #f0f0f0;
}

.meal-type {
  font-weight: bold;
}

.preview-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
  padding: 0 20px;
}

.input_xiala {
  background: #fff;
  color: #333;
}
</style>
