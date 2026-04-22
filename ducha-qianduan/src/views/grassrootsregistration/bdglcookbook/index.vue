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
          v-hasPermi="['grassrootsregistration:bdglcookbook:add']"
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
          v-hasPermi="['grassrootsregistration:bdglcookbook:edit']"
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
          v-hasPermi="['grassrootsregistration:bdglcookbook:remove']"
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
          v-hasPermi="['grassrootsregistration:bdglcookbook:export']"
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
      :data="bdglcookbookList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="序号" align="center" prop="id" /> -->
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
      <!-- <el-table-column label="更新时间" align="center" prop="id" /> -->
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
      <!-- <el-table-column label="食谱内容" align="center" prop="contents" />
      <el-table-column label="创建时间" align="center" prop="createtime" />
      <el-table-column label="更新时间" align="center" prop="updatetime" /> -->
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
            @click="handleChakan(scope.row)"
            v-hasPermi="['grassrootsregistration:bdgldiary:edit']"
            ><span class="chakan">查看</span></el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglcookbook:edit']"
            ><span class="edit">修改</span></el-button
          >
          <el-button
            size="mini"
            type="warning"
            @click="handleExportExcel(scope.row)"
            ><span class="edit">Excel导出</span></el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglcookbook:remove']"
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
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <div class="jiben">基本信息</div>
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="80px"
          :inline="true"
        >
          <el-form-item label="单位" prop="unitId">
            <!-- <treeselect
            @select="selectPeo"
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="选择单位"
          /> -->
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
            <!-- <el-input v-model="form.messOfficer" placeholder="请输入司务长" /> -->
            <el-select
              v-model="form.messOfficer"
              placeholder="请选择单位"
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
            <!-- <el-input v-model="form.peopleId" placeholder="请输入班长" /> -->
            <el-select
              v-model="form.peopleId"
              placeholder="请选择单位"
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
          <!-- <el-form-item label="食谱内容" prop="contents">
          <el-input
            v-model="form.contents"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item> -->
          <div class="jiben">食谱内容</div>
          <div class="">
            <table
              border="1"
              width="920"
              cellspacing="0"
              align="center"
              style="border-color: white; margin-left: -1px; color: white"
            >
              <!-- 星期一 -->
              <tr align="center">
                <td rowspan="6">星期一</td>
                <td rowspan="2">早餐</td>
                <td>
                  <el-select
                    v-model="Monday.zaoZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="(item, i) in yiZhou.zaoZhu"
                      :key="i"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    ></el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Monday.zaoZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Monday.zaoFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zaoFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Monday.zaoFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 午餐 -->
              <tr align="center">
                <td rowspan="2">午餐</td>
                <td>
                  <el-select
                    v-model="Monday.zhongZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Monday.zhongZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Monday.zhongFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongFU"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Monday.zhongFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 晚餐 -->
              <tr align="center">
                <td rowspan="2">晚餐</td>
                <td>
                  <el-select
                    v-model="Monday.wanZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Monday.wanZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Monday.wanFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Monday.wanFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 星期二 -->
              <tr align="center">
                <td rowspan="6">星期二</td>
                <td rowspan="2">早餐</td>
                <td>
                  <el-select
                    v-model="Tuesday.zaoZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="(item, i) in yiZhou.zaoZhu"
                      :key="i"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    ></el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Tuesday.zaoZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Tuesday.zaoFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zaoFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Tuesday.zaoFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 午餐 -->
              <tr align="center">
                <td rowspan="2">午餐</td>
                <td>
                  <el-select
                    v-model="Tuesday.zhongZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Tuesday.zhongZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Tuesday.zhongFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongFU"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Tuesday.zhongFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 晚餐 -->
              <tr align="center">
                <td rowspan="2">晚餐</td>
                <td>
                  <el-select
                    v-model="Tuesday.wanZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Tuesday.wanZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Tuesday.wanFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Tuesday.wanFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 星期三 -->
              <tr align="center">
                <td rowspan="6">星期三</td>
                <td rowspan="2">早餐</td>
                <td>
                  <el-select
                    v-model="Wednesday.zaoZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="(item, i) in yiZhou.zaoZhu"
                      :key="i"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    ></el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Wednesday.zaoZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Wednesday.zaoFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zaoFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Wednesday.zaoFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 午餐 -->
              <tr align="center">
                <td rowspan="2">午餐</td>
                <td>
                  <el-select
                    v-model="Wednesday.zhongZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Wednesday.zhongZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Wednesday.zhongFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongFU"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Wednesday.zhongFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 晚餐 -->
              <tr align="center">
                <td rowspan="2">晚餐</td>
                <td>
                  <el-select
                    v-model="Wednesday.wanZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Wednesday.wanZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Wednesday.wanFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Wednesday.wanFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 星期四 -->
              <tr align="center">
                <td rowspan="6">星期四</td>
                <td rowspan="2">早餐</td>
                <td>
                  <el-select
                    v-model="Thursday.zaoZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="(item, i) in yiZhou.zaoZhu"
                      :key="i"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    ></el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Thursday.zaoZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Thursday.zaoFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zaoFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Thursday.zaoFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 午餐 -->
              <tr align="center">
                <td rowspan="2">午餐</td>
                <td>
                  <el-select
                    v-model="Thursday.zhongZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Thursday.zhongZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Thursday.zhongFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongFU"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Thursday.zhongFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 晚餐 -->
              <tr align="center">
                <td rowspan="2">晚餐</td>
                <td>
                  <el-select
                    v-model="Thursday.wanZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Thursday.wanZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Thursday.wanFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Thursday.wanFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 星期五 -->
              <tr align="center">
                <td rowspan="6">星期五</td>
                <td rowspan="2">早餐</td>
                <td>
                  <el-select
                    v-model="Friday.zaoZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="(item, i) in yiZhou.zaoZhu"
                      :key="i"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    ></el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Friday.zaoZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Friday.zaoFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zaoFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Friday.zaoFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 午餐 -->
              <tr align="center">
                <td rowspan="2">午餐</td>
                <td>
                  <el-select
                    v-model="Friday.zhongZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Friday.zhongZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Friday.zhongFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongFU"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Friday.zhongFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 晚餐 -->
              <tr align="center">
                <td rowspan="2">晚餐</td>
                <td>
                  <el-select
                    v-model="Friday.wanZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Friday.wanZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Friday.wanFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Friday.wanFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 星期六 -->
              <tr align="center">
                <td rowspan="6">星期六</td>
                <td rowspan="2">早餐</td>
                <td>
                  <el-select
                    v-model="Saturday.zaoZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="(item, i) in yiZhou.zaoZhu"
                      :key="i"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    ></el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Saturday.zaoZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Saturday.zaoFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zaoFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Saturday.zaoFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 午餐 -->
              <tr align="center">
                <td rowspan="2">午餐</td>
                <td>
                  <el-select
                    v-model="Saturday.zhongZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Saturday.zhongZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Saturday.zhongFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongFU"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Saturday.zhongFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 晚餐 -->
              <tr align="center">
                <td rowspan="2">晚餐</td>
                <td>
                  <el-select
                    v-model="Saturday.wanZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Saturday.wanZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Saturday.wanFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Saturday.wanFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 星期日 -->
              <tr align="center">
                <td rowspan="6">星期日</td>
                <td rowspan="2">早餐</td>
                <td>
                  <el-select
                    v-model="Sunday.zaoZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="(item, i) in yiZhou.zaoZhu"
                      :key="i"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    ></el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Sunday.zaoZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Sunday.zaoFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zaoFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Sunday.zaoFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 午餐 -->
              <tr align="center">
                <td rowspan="2">午餐</td>
                <td>
                  <el-select
                    v-model="Sunday.zhongZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Sunday.zhongZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Sunday.zhongFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.zhongFU"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Sunday.zhongFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <!-- 晚餐 -->
              <tr align="center">
                <td rowspan="2">晚餐</td>
                <td>
                  <el-select
                    v-model="Sunday.wanZu1"
                    placeholder="请选择主食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanZhu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Sunday.wanZu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
              <tr align="center">
                <td>
                  <el-select
                    v-model="Sunday.wanFu1"
                    placeholder="请选择副食"
                    multiple
                  >
                    <el-option
                      v-for="item in yiZhou.wanFu"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
                <td>
                  <el-select
                    v-model="Sunday.wanFu1Ren"
                    placeholder="请选择炊事员"
                    multiple
                  >
                    <el-option
                      v-for="item in renYuan"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </td>
              </tr>
            </table>
          </div>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(1)">确 定</el-button>
        <el-button @click="cancel" size="btn">取消</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="menuRoleVisible"
      :title="title"
      append-to-body
      id="chakan"
      :close-on-click-modal="false"
    >
      <bdglcookbook
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
      ></bdglcookbook>
    </el-dialog>
  </div>
</template>

<script>
import bdglcookbook from "@/components/look_word/bdglcookbook.vue";

import {
  listBdglcookbook,
  getBdglcookbook,
  exportBdglcookbookExcel,
  delBdglcookbook,
  addBdglcookbook,
  updateBdglcookbook,
  exportBdglcookbook,
  getThings,
} from "@/api/grassrootsregistration/bdglcookbook";
import {
  getZhuChiRen,
  getShouBu,
} from "@/api/grassrootsregistration/bdglmeeting";

// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getDept } from "@/api/grassrootsregistration/bdglmeeting";
import { jieZhiList } from "@/api/grassrootsregistration/bdgldiary";

export default {
  name: "Bdglcookbook",
  components: {
    Treeselect,
    bdglcookbook,
  },
  data() {
    return {
      //查看页面
      menuRoleVisible: false,
      wordInfo: {},
      //判断关闭
      style: "2",
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
      // 一周食谱表格数据
      bdglcookbookList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        startTime: null,
        endTime: null,
        messOfficer: null,
        peopleId: null,
        contents: null,
        unitId: null,
        createtime: null,
        updatetime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitId: [
          { required: true, message: "单位名称不能为空", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
      },
      options: [],
      value: "",
      // 单位树形
      deptOptions: [],
      // 人员列表
      renYuan: [],
      food: [],
      // 一周食物
      yiZhou: [],
      // 部门下拉列表
      deptOptionss: [],
      // 食谱内容
      // 食物名称
      rows: {},
      Monday: {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      },
      Tuesday: {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      },
      Wednesday: {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      },
      Thursday: {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      },
      Friday: {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      },
      Saturday: {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      },
      Sunday: {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      },
      // 1
      zaoZu1: {},
      zaoFu1: {},
      zhongZhu1: {},
      zhongFu1: {},
      wanZhu1: {},
      wanFu1: {},
      // 2
      zaoZu2: {},
      zaoFu2: {},
      zhongZhu2: {},
      zhongFu2: {},
      wanZhu2: {},
      wanFu2: {},
      // 3
      zaoZu3: {},
      zaoFu3: {},
      zhongZhu3: {},
      zhongFu3: {},
      wanZhu3: {},
      wanFu3: {},
      // 4
      zaoZu4: {},
      zaoFu4: {},
      zhongZhu4: {},
      zhongFu4: {},
      wanZhu4: {},
      wanFu4: {},
      // 5
      zaoZu5: {},
      zaoFu5: {},
      zhongZhu5: {},
      zhongFu5: {},
      wanZhu5: {},
      wanFu5: {},
      // 6
      zaoZu6: {},
      zaoFu6: {},
      zhongZhu6: {},
      zhongFu6: {},
      wanZhu6: {},
      wanFu6: {},
      // 7
      zaoZu7: {},
      zaoFu7: {},
      zhongZhu7: {},
      zhongFu7: {},
      wanZhu7: {},
      wanFu7: {},
      arr: [],
      // 当月日期
      current_month: [],
      // 下月日期
      current_month1: [],
      // 日期开关状态
      dayTime: null,
    };
  },
  created() {
    this.getList();
    this.getdept();
    // 获取当月
    this.getDay();
    // 获取下月
    this.getDayX();
    jieZhiList().then((res) => {
      this.dayTime = res.rows[0].time;
    });
  },
  methods: {
    // 获取当月日期
    getDay() {
      // 获取标准时间
      let now = new Date();
      // 年
      var year = new Date().getFullYear();
      // 月
      var month = new Date().getMonth() + 1;
      // 日
      var day = new Date(year, month, 0).getDate();
      // console.log(now);
      let current_month_num = day;
      this.current_month = [];
      for (let i = 1; i <= current_month_num; i++) {
        let day = now.setDate(i);
        let date = new Date(day); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        let Y = date.getFullYear() + "-";
        let M =
          (date.getMonth() + 1 < 10
            ? "0" + (date.getMonth() + 1)
            : date.getMonth() + 1) + "-";
        let D =
          (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "";
        this.current_month.push(Y + M + D);
        // console.log(this.current_month);
      }
    },
    // 获取下个月日期
    getDayX() {
      // 获取标准时间
      let now = new Date();
      // 年
      var year = new Date().getFullYear();
      // 月
      var month = new Date().getMonth() + 2;
      // 日
      var day = new Date(year, month, 0).getDate();
      let current_month_num1 = day;
      for (let i = 1; i <= current_month_num1; i++) {
        let day = now.setDate(i);
        let date = new Date(day); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        let Y = date.getFullYear() + "-";
        let M =
          (date.getMonth() + 2 < 10
            ? "0" + (date.getMonth() + 2)
            : date.getMonth() + 2) + "-";
        let D =
          (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "";
        this.current_month.push(Y + M + D);
      }
    },
    timeDay(val) {
      if (!val) return;

      // 用户选择的时间戳（秒）
      const date1 = Math.floor(new Date(val).getTime() / 1000);

      // 当前月份
      const now = new Date(val);

      // 当月第一天 00:00:00
      const firstDay = Math.floor(new Date(now.getFullYear(), now.getMonth(), 1, 0, 0, 0).getTime() / 1000);

      // 当月最后一天 23:59:59
      const lastDay = Math.floor(new Date(now.getFullYear(), now.getMonth() + 1, 0, 23, 59, 59).getTime() / 1000);

      // 判断是否在当月范围内
      if (this.dayTime === 1 && (date1 < firstDay || date1 > lastDay)) {
        this.$confirm("请在规定时间内填写", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.form.startTime = null;
          })
          .catch(() => {
            this.form.startTime = null;
          });
      }
    },
    // //获取部门列表
    getdept() {
      getShouBu().then((res) => {
        this.deptOptionss = res.data;
      });
    },
    // 获取人员
    getRen(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 搜索时间触发
    time() {
      this.getList();
    },
    // 搜索单位触发
    host() {
      this.getList();
    },
    // 选中班长触发
    monitor(name) {
      this.form.peopleName = name;
    },
    // 选中司务触发
    siWu(name) {
      this.form.messOfficerName = name;
    },
    // 获取一天的食物
    getThingsbook() {
      getThings().then((res) => {
        this.yiZhou = res.data;
      });
    },
    // 重置人员
    ChongZhi() {
      (this.form.messOfficer = null), (this.form.peopleId = null);
    },
    // 新增选中单位触发
    selectPeo(data) {
      this.ChongZhi();
      this.form.unitId = data.id;
      this.getRen(this.form.unitId);
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 查询一周食谱列表 */
    getList() {
      this.loading = true;
      listBdglcookbook(this.queryParams).then((response) => {
        this.bdglcookbookList = response.rows;
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
        title: null,
        startTime: null,
        endTime: null,
        messOfficer: null,
        peopleId: null,
        contents: null,
        unitId: null,
        createtime: null,
        updatetime: null,
      };
      this.resetForm("form");
    },
    // 重置菜谱
    resets() {
      this.Monday = {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      };
      this.Tuesday = {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      };
      this.Wednesday = {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      };
      this.Thursday = {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      };
      this.Friday = {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      };
      this.Saturday = {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      };
      this.Sunday = {
        zaoZu1: [],
        zaoZu1Ren: [],
        zaoFu1: [],
        zaoFu1Ren: [],
        zhongZu1: [],
        zhongZu1Ren: [],
        zhongFu1: [],
        zhongFu1Ren: [],
        wanZu1: [],
        wanZu1Ren: [],
        wanFu1: [],
        wanFu1Ren: [],
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.startTime = null;
      this.handleQuery();
    },
    /** 对话框重置按钮操作 */
    resertwo() {
      this.reset();
      this.resets();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      // 人员列表
      (this.renYuan = []), this.reset();
      this.resets();
      this.getTreeselect();
      this.getThingsbook();
      this.open = true;
      this.style = 2;
      this.title = "添加一周食谱";
    },
    //查看按钮操作
    handleChakan(row) {
      // 是否隐藏按钮
      this.opens = false;
      const id = row.id || this.ids;
      getBdglcookbook(id).then((response) => {
        this.wordInfo = response.data;
        this.title = "查看一周食谱";
        this.menuRoleVisible = true;
      });
    },
    //导出按钮操作
    handleExportExcel(row) {
      const id = row.id || this.ids;
      exportBdglcookbookExcel(id).then(res => {
        // 处理文件下载
        const blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = "(" + row.title + ")" + '一周食谱表.xlsx';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        URL.revokeObjectURL(link.href);
      }).catch(error => {
        console.error('导出Excel失败:', error);
        this.$message.error('导出失败，请重试');
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getTreeselect();
      this.form.unitId = row.unitId;
      this.getThingsbook();
      this.getRen(row.unitId);
      this.reset();
      const id = row.id || this.ids;
      getBdglcookbook(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改一周食谱";
        this.arr = this.form.cookbookRegistrations;
        this.arr.forEach((element) => {
          if (
            element.weeklist === "星期一" &&
            element.timelist === "早餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoZu1 = element;
            this.Monday.zaoZu1 = this.zaoZu1.cookcategoryIds.split(",");
            this.Monday.zaoZu1 = this.Monday.zaoZu1.map((i) => parseInt(i, 0));
            this.Monday.zaoZu1Ren = this.zaoZu1.peopleIds.split(",");
            this.Monday.zaoZu1Ren = this.Monday.zaoZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期一" &&
            element.timelist === "早餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoFu1 = element;
            this.Monday.zaoFu1 = this.zaoFu1.cookcategoryIds.split(",");
            this.Monday.zaoFu1 = this.Monday.zaoFu1.map((i) => parseInt(i, 0));
            this.Monday.zaoFu1Ren = this.zaoFu1.peopleIds.split(",");
            this.Monday.zaoFu1Ren = this.Monday.zaoFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期一" &&
            element.timelist === "午餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongZhu1 = element;
            this.Monday.zhongZu1 = this.zhongZhu1.cookcategoryIds.split(",");
            this.Monday.zhongZu1 = this.Monday.zhongZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Monday.zhongZu1Ren = this.zhongZhu1.peopleIds.split(",");
            this.Monday.zhongZu1Ren = this.Monday.zhongZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期一" &&
            element.timelist === "午餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongFu1 = element;
            this.Monday.zhongFu1 = this.zhongFu1.cookcategoryIds.split(",");
            this.Monday.zhongFu1 = this.Monday.zhongFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Monday.zhongFu1Ren = this.zhongFu1.peopleIds.split(",");
            this.Monday.zhongFu1Ren = this.Monday.zhongFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期一" &&
            element.timelist === "晚餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanZhu1 = element;
            this.Monday.wanZu1 = this.wanZhu1.cookcategoryIds.split(",");
            this.Monday.wanZu1 = this.Monday.wanZu1.map((i) => parseInt(i, 0));
            this.Monday.wanZu1Ren = this.wanZhu1.peopleIds.split(",");
            this.Monday.wanZu1Ren = this.Monday.wanZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期一" &&
            element.timelist === "晚餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanFu1 = element;
            this.Monday.wanFu1 = this.wanFu1.cookcategoryIds.split(",");
            this.Monday.wanFu1 = this.Monday.wanFu1.map((i) => parseInt(i, 0));
            this.Monday.wanFu1Ren = this.wanFu1.peopleIds.split(",");
            this.Monday.wanFu1Ren = this.Monday.wanFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期二" &&
            element.timelist === "早餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoZu2 = element;
            this.Tuesday.zaoZu1 = this.zaoZu2.cookcategoryIds.split(",");
            this.Tuesday.zaoZu1 = this.Tuesday.zaoZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Tuesday.zaoZu1Ren = this.zaoZu2.peopleIds.split(",");
            this.Tuesday.zaoZu1Ren = this.Tuesday.zaoZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期二" &&
            element.timelist === "早餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoFu2 = element;
            this.Tuesday.zaoFu1 = this.zaoFu2.cookcategoryIds.split(",");
            this.Tuesday.zaoFu1 = this.Tuesday.zaoFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Tuesday.zaoFu1Ren = this.zaoFu2.peopleIds.split(",");
            this.Tuesday.zaoFu1Ren = this.Tuesday.zaoFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            // 在这里
            element.weeklist === "星期二" &&
            element.timelist === "午餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongZhu2 = element;
            this.Tuesday.zhongZu1 = this.zhongZhu2.cookcategoryIds.split(",");
            this.Tuesday.zhongZu1 = this.Tuesday.zhongZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Tuesday.zhongZu1Ren = this.zhongZhu2.peopleIds.split(",");
            this.Tuesday.zhongZu1Ren = this.Tuesday.zhongZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期二" &&
            element.timelist === "午餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongFu2 = element;
            this.Tuesday.zhongFu1 = this.zhongFu2.cookcategoryIds.split(",");
            this.Tuesday.zhongFu1 = this.Tuesday.zhongFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Tuesday.zhongFu1Ren = this.zhongFu2.peopleIds.split(",");
            this.Tuesday.zhongFu1Ren = this.Tuesday.zhongFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期二" &&
            element.timelist === "晚餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanZhu2 = element;
            this.Tuesday.wanZu1 = this.wanZhu2.cookcategoryIds.split(",");
            this.Tuesday.wanZu1 = this.Tuesday.wanZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Tuesday.wanZu1Ren = this.wanZhu2.peopleIds.split(",");
            this.Tuesday.wanZu1Ren = this.Tuesday.wanZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期二" &&
            element.timelist === "晚餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanFu2 = element;
            this.Tuesday.wanFu1 = this.wanFu2.cookcategoryIds.split(",");
            this.Tuesday.wanFu1 = this.Tuesday.wanFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Tuesday.wanFu1Ren = this.wanFu2.peopleIds.split(",");
            this.Tuesday.wanFu1Ren = this.Tuesday.wanFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期三" &&
            element.timelist === "早餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoZu3 = element;
            this.Wednesday.zaoZu1 = this.zaoZu3.cookcategoryIds.split(",");
            this.Wednesday.zaoZu1 = this.Wednesday.zaoZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Wednesday.zaoZu1Ren = this.zaoZu3.peopleIds.split(",");
            this.Wednesday.zaoZu1Ren = this.Wednesday.zaoZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期三" &&
            element.timelist === "早餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoFu3 = element;
            this.Wednesday.zaoFu1 = this.zaoFu3.cookcategoryIds.split(",");
            this.Wednesday.zaoFu1 = this.Wednesday.zaoFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Wednesday.zaoFu1Ren = this.zaoFu3.peopleIds.split(",");
            this.Wednesday.zaoFu1Ren = this.Wednesday.zaoFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期三" &&
            element.timelist === "午餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongZhu3 = element;
            this.Wednesday.zhongZu1 = this.zhongZhu3.cookcategoryIds.split(",");
            this.Wednesday.zhongZu1 = this.Wednesday.zhongZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Wednesday.zhongZu1Ren = this.zhongZhu3.peopleIds.split(",");
            this.Wednesday.zhongZu1Ren = this.Wednesday.zhongZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期三" &&
            element.timelist === "午餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongFu3 = element;
            this.Wednesday.zhongFu1 = this.zhongFu3.cookcategoryIds.split(",");
            this.Wednesday.zhongFu1 = this.Wednesday.zhongFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Wednesday.zhongFu1Ren = this.zhongFu3.peopleIds.split(",");
            this.Wednesday.zhongFu1Ren = this.Wednesday.zhongFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期三" &&
            element.timelist === "晚餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanZhu3 = element;
            this.Wednesday.wanZu1 = this.wanZhu3.cookcategoryIds.split(",");
            this.Wednesday.wanZu1 = this.Wednesday.wanZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Wednesday.wanZu1Ren = this.wanZhu3.peopleIds.split(",");
            this.Wednesday.wanZu1Ren = this.Wednesday.wanZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期三" &&
            element.timelist === "晚餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanFu3 = element;
            this.Wednesday.wanFu1 = this.wanFu3.cookcategoryIds.split(",");
            this.Wednesday.wanFu1 = this.Wednesday.wanFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Wednesday.wanFu1Ren = this.wanFu3.peopleIds.split(",");
            this.Wednesday.wanFu1Ren = this.Wednesday.wanFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期四" &&
            element.timelist === "早餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoZu4 = element;
            this.Thursday.zaoZu1 = this.zaoZu4.cookcategoryIds.split(",");
            this.Thursday.zaoZu1 = this.Thursday.zaoZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Thursday.zaoZu1Ren = this.zaoZu4.peopleIds.split(",");
            this.Thursday.zaoZu1Ren = this.Thursday.zaoZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期四" &&
            element.timelist === "早餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoFu4 = element;
            this.Thursday.zaoFu1 = this.zaoFu4.cookcategoryIds.split(",");
            this.Thursday.zaoFu1 = this.Thursday.zaoFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Thursday.zaoFu1Ren = this.zaoFu4.peopleIds.split(",");
            this.Thursday.zaoFu1Ren = this.Thursday.zaoFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期四" &&
            element.timelist === "午餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongZhu4 = element;
            this.Thursday.zhongZu1 = this.zhongZhu4.cookcategoryIds.split(",");
            this.Thursday.zhongZu1 = this.Thursday.zhongZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Thursday.zhongZu1Ren = this.zhongZhu4.peopleIds.split(",");
            this.Thursday.zhongZu1Ren = this.Thursday.zhongZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期四" &&
            element.timelist === "午餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongFu4 = element;
            this.Thursday.zhongFu1 = this.zhongFu4.cookcategoryIds.split(",");
            this.Thursday.zhongFu1 = this.Thursday.zhongFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Thursday.zhongFu1Ren = this.zhongFu4.peopleIds.split(",");
            this.Thursday.zhongFu1Ren = this.Thursday.zhongFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期四" &&
            element.timelist === "晚餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanZhu4 = element;
            this.Thursday.wanZu1 = this.wanZhu4.cookcategoryIds.split(",");
            this.Thursday.wanZu1 = this.Thursday.wanZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Thursday.wanZu1Ren = this.wanZhu4.peopleIds.split(",");
            this.Thursday.wanZu1Ren = this.Thursday.wanZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期四" &&
            element.timelist === "晚餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanFu4 = element;
            this.Thursday.wanFu1 = this.wanFu4.cookcategoryIds.split(",");
            this.Thursday.wanFu1 = this.Thursday.wanFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Thursday.wanFu1Ren = this.wanFu4.peopleIds.split(",");
            this.Thursday.wanFu1Ren = this.Thursday.wanFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期五" &&
            element.timelist === "早餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoZu5 = element;
            this.Friday.zaoZu1 = this.zaoZu5.cookcategoryIds.split(",");
            this.Friday.zaoZu1 = this.Friday.zaoZu1.map((i) => parseInt(i, 0));
            this.Friday.zaoZu1Ren = this.zaoZu5.peopleIds.split(",");
            this.Friday.zaoZu1Ren = this.Friday.zaoZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期五" &&
            element.timelist === "早餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoFu5 = element;
            this.Friday.zaoFu1 = this.zaoFu5.cookcategoryIds.split(",");
            this.Friday.zaoFu1 = this.Friday.zaoFu1.map((i) => parseInt(i, 0));
            this.Friday.zaoFu1Ren = this.zaoFu5.peopleIds.split(",");
            this.Friday.zaoFu1Ren = this.Friday.zaoFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期五" &&
            element.timelist === "午餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongZhu5 = element;
            this.Friday.zhongZu1 = this.zhongZhu5.cookcategoryIds.split(",");
            this.Friday.zhongZu1 = this.Friday.zhongZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Friday.zhongZu1Ren = this.zhongZhu5.peopleIds.split(",");
            this.Friday.zhongZu1Ren = this.Friday.zhongZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期五" &&
            element.timelist === "午餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongFu5 = element;
            this.Friday.zhongFu1 = this.zhongFu5.cookcategoryIds.split(",");
            this.Friday.zhongFu1 = this.Friday.zhongFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Friday.zhongFu1Ren = this.zhongFu5.peopleIds.split(",");
            this.Friday.zhongFu1Ren = this.Friday.zhongFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期五" &&
            element.timelist === "晚餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanZhu5 = element;
            this.Friday.wanZu1 = this.wanZhu5.cookcategoryIds.split(",");
            this.Friday.wanZu1 = this.Friday.wanZu1.map((i) => parseInt(i, 0));
            this.Friday.wanZu1Ren = this.wanZhu5.peopleIds.split(",");
            this.Friday.wanZu1Ren = this.Friday.wanZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期五" &&
            element.timelist === "晚餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanFu5 = element;
            this.Friday.wanFu1 = this.wanFu5.cookcategoryIds.split(",");
            this.Friday.wanFu1 = this.Friday.wanFu1.map((i) => parseInt(i, 0));
            this.Friday.wanFu1Ren = this.wanFu5.peopleIds.split(",");
            this.Friday.wanFu1Ren = this.Friday.wanFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期六" &&
            element.timelist === "早餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoZu6 = element;
            this.Saturday.zaoZu1 = this.zaoZu6.cookcategoryIds.split(",");
            this.Saturday.zaoZu1 = this.Saturday.zaoZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Saturday.zaoZu1Ren = this.zaoZu6.peopleIds.split(",");
            this.Saturday.zaoZu1Ren = this.Saturday.zaoZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期六" &&
            element.timelist === "早餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoFu6 = element;
            this.Saturday.zaoFu1 = this.zaoFu6.cookcategoryIds.split(",");
            this.Saturday.zaoFu1 = this.Saturday.zaoFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Saturday.zaoFu1Ren = this.zaoFu6.peopleIds.split(",");
            this.Saturday.zaoFu1Ren = this.Saturday.zaoFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期六" &&
            element.timelist === "午餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongZhu6 = element;
            this.Saturday.zhongZu1 = this.zhongZhu6.cookcategoryIds.split(",");
            this.Saturday.zhongZu1 = this.Saturday.zhongZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Saturday.zhongZu1Ren = this.zhongZhu6.peopleIds.split(",");
            this.Saturday.zhongZu1Ren = this.Saturday.zhongZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期六" &&
            element.timelist === "午餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongFu6 = element;
            this.Saturday.zhongFu1 = this.zhongFu6.cookcategoryIds.split(",");
            this.Saturday.zhongFu1 = this.Saturday.zhongFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Saturday.zhongFu1Ren = this.zhongFu6.peopleIds.split(",");
            this.Saturday.zhongFu1Ren = this.Saturday.zhongFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期六" &&
            element.timelist === "晚餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanZhu6 = element;
            this.Saturday.wanZu1 = this.wanZhu6.cookcategoryIds.split(",");
            this.Saturday.wanZu1 = this.Saturday.wanZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Saturday.wanZu1Ren = this.wanZhu6.peopleIds.split(",");
            this.Saturday.wanZu1Ren = this.Saturday.wanZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期六" &&
            element.timelist === "晚餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanFu6 = element;
            this.Saturday.wanFu1 = this.wanFu6.cookcategoryIds.split(",");
            this.Saturday.wanFu1 = this.Saturday.wanFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Saturday.wanFu1Ren = this.wanFu6.peopleIds.split(",");
            this.Saturday.wanFu1Ren = this.Saturday.wanFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期日" &&
            element.timelist === "早餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoZu7 = element;
            this.Sunday.zaoZu1 = this.zaoZu7.cookcategoryIds.split(",");
            this.Sunday.zaoZu1 = this.Sunday.zaoZu1.map((i) => parseInt(i, 0));
            this.Sunday.zaoZu1Ren = this.zaoZu7.peopleIds.split(",");
            this.Sunday.zaoZu1Ren = this.Sunday.zaoZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期日" &&
            element.timelist === "早餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zaoFu7 = element;
            this.Sunday.zaoFu1 = this.zaoFu7.cookcategoryIds.split(",");
            this.Sunday.zaoFu1 = this.Sunday.zaoFu1.map((i) => parseInt(i, 0));
            this.Sunday.zaoFu1Ren = this.zaoFu7.peopleIds.split(",");
            this.Sunday.zaoFu1Ren = this.Sunday.zaoFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期日" &&
            element.timelist === "午餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongZhu7 = element;
            this.Sunday.zhongZu1 = this.zhongZhu7.cookcategoryIds.split(",");
            this.Sunday.zhongZu1 = this.Sunday.zhongZu1.map((i) =>
              parseInt(i, 0)
            );
            this.Sunday.zhongZu1Ren = this.zhongZhu7.peopleIds.split(",");
            this.Sunday.zhongZu1Ren = this.Sunday.zhongZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期日" &&
            element.timelist === "午餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.zhongFu7 = element;
            this.Sunday.zhongFu1 = this.zhongFu7.cookcategoryIds.split(",");
            this.Sunday.zhongFu1 = this.Sunday.zhongFu1.map((i) =>
              parseInt(i, 0)
            );
            this.Sunday.zhongFu1Ren = this.zhongFu7.peopleIds.split(",");
            this.Sunday.zhongFu1Ren = this.Sunday.zhongFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期日" &&
            element.timelist === "晚餐" &&
            element.typelist === "主食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanZhu7 = element;
            this.Sunday.wanZu1 = this.wanZhu7.cookcategoryIds.split(",");
            this.Sunday.wanZu1 = this.Sunday.wanZu1.map((i) => parseInt(i, 0));
            this.Sunday.wanZu1Ren = this.wanZhu7.peopleIds.split(",");
            this.Sunday.wanZu1Ren = this.Sunday.wanZu1Ren.map((i) =>
              parseInt(i, 0)
            );
          } else if (
            element.weeklist === "星期日" &&
            element.timelist === "晚餐" &&
            element.typelist === "副食" &&
            element.cookcategoryNames !== null &&
            element.peopleIds !== ","
          ) {
            this.wanFu7 = element;
            this.Sunday.wanFu1 = this.wanFu7.cookcategoryIds.split(",");
            this.Sunday.wanFu1 = this.Sunday.wanFu1.map((i) => parseInt(i, 0));
            this.Sunday.wanFu1Ren = this.wanFu7.peopleIds.split(",");
            this.Sunday.wanFu1Ren = this.Sunday.wanFu1Ren.map((i) =>
              parseInt(i, 0)
            );
          }
        });
      });
    },
    /** 提交按钮 */
    submitForm(style) {
      this.style = style;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            // 1.早主;
            if (this.Monday.zaoZu1 != "") {
              var str1 = this.Monday.zaoZu1.join(",");
              var pstr1 = this.Monday.zaoZu1Ren.join(",");
            }
            // 早副;
            if (this.Monday.zaoFu1 != "") {
              var str11 = this.Monday.zaoFu1.join(",");
              var pstr11 = this.Monday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Monday.zhongZu1 != "") {
              var str2 = this.Monday.zhongZu1.join(",");
              var pstr2 = this.Monday.zhongZu1Ren.join(",");
            }
            // 中副
            if (this.Monday.zhongFu1 != "") {
              var str22 = this.Monday.zhongFu1.join(",");
              var pstr22 = this.Monday.zhongFu1Ren.join(",");
            }
            // 晚主
            if (this.Monday.wanZu1 != "") {
              var str3 = this.Monday.wanZu1.join(",");
              var pstr3 = this.Monday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Monday.wanFu1 != "") {
              var str33 = this.Monday.wanFu1.join(",");
              var pstr33 = this.Monday.wanFu1Ren.join(",");
            }

            // 2.早主;
            if (this.Tuesday.zaoZu1 != "") {
              var str4 = this.Tuesday.zaoZu1.join(",");
              var pstr4 = this.Tuesday.zaoZu1Ren.join(",");
            }
            // 早副;
            if (this.Tuesday.zaoFu1 != "") {
              var str44 = this.Tuesday.zaoFu1.join(",");
              var pstr44 = this.Tuesday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Tuesday.zhongZu1 != "") {
              var str5 = this.Tuesday.zhongZu1.join(",");
              var pstr5 = this.Tuesday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Tuesday.zhongFu1 != "") {
              var str55 = this.Tuesday.zhongFu1.join(",");
              var pstr55 = this.Tuesday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Tuesday.wanZu1 != "") {
              var str6 = this.Tuesday.wanZu1.join(",");
              var pstr6 = this.Tuesday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Tuesday.wanFu1 != "") {
              var str66 = this.Tuesday.wanFu1.join(",");
              var pstr66 = this.Tuesday.wanFu1Ren.join(",");
            }

            // 3.早主;
            if (this.Wednesday.zaoZu1 != "") {
              var str7 = this.Wednesday.zaoZu1.join(",");
              var pstr7 = this.Wednesday.zaoZu1Ren.join(",");
            }

            // 早副;
            if (this.Wednesday.zaoFu1 != "") {
              var str77 = this.Wednesday.zaoFu1.join(",");
              var pstr77 = this.Wednesday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Wednesday.zhongZu1 != "") {
              var str8 = this.Wednesday.zhongZu1.join(",");
              var pstr8 = this.Wednesday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Wednesday.zhongFu1 != "") {
              var str88 = this.Wednesday.zhongFu1.join(",");
              var pstr88 = this.Wednesday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Wednesday.wanZu1 != "") {
              var str9 = this.Wednesday.wanZu1.join(",");
              var pstr9 = this.Wednesday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Wednesday.wanFu1 != "") {
              var str99 = this.Wednesday.wanFu1.join(",");
              var pstr99 = this.Wednesday.wanFu1Ren.join(",");
            }

            // 4.早主;
            if (this.Thursday.zaoZu1 != "") {
              var str10 = this.Thursday.zaoZu1.join(",");
              var pstr10 = this.Thursday.zaoZu1Ren.join(",");
            }

            // 早副;
            if (this.Thursday.zaoFu1 != "") {
              var str1010 = this.Thursday.zaoFu1.join(",");
              var pstr1010 = this.Thursday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Thursday.zhongZu1 != "") {
              var str12 = this.Thursday.zhongZu1.join(",");
              var pstr12 = this.Thursday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Thursday.zhongFu1 != "") {
              var str1212 = this.Thursday.zhongFu1.join(",");
              var pstr1212 = this.Thursday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Thursday.wanZu1 != "") {
              var str13 = this.Thursday.wanZu1.join(",");
              var pstr13 = this.Thursday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Thursday.wanFu1 != "") {
              var str1313 = this.Thursday.wanFu1.join(",");
              var pstr1313 = this.Thursday.wanFu1Ren.join(",");
            }

            // 5.早主;
            if (this.Friday.zaoZu1 != "") {
              var str14 = this.Friday.zaoZu1.join(",");
              var pstr14 = this.Friday.zaoZu1Ren.join(",");
            }

            // 早副;
            if (this.Friday.zaoFu1 != "") {
              var str1414 = this.Friday.zaoFu1.join(",");
              var pstr1414 = this.Friday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Friday.zhongZu1 != "") {
              var str15 = this.Friday.zhongZu1.join(",");
              var pstr15 = this.Friday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Friday.zhongFu1 != "") {
              var str1515 = this.Friday.zhongFu1.join(",");
              var pstr1515 = this.Friday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Friday.wanZu1 != "") {
              var str16 = this.Friday.wanZu1.join(",");
              var pstr16 = this.Friday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Friday.wanFu1 != "") {
              var str1616 = this.Friday.wanFu1.join(",");
              var pstr1616 = this.Friday.wanFu1Ren.join(",");
            }

            // 6.早主;
            if (this.Saturday.zaoZu1 != "") {
              var str17 = this.Saturday.zaoZu1.join(",");
              var pstr17 = this.Saturday.zaoZu1Ren.join(",");
            }

            // 早副;
            if (this.Saturday.zaoFu1 != "") {
              var str1717 = this.Saturday.zaoFu1.join(",");
              var pstr1717 = this.Saturday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Saturday.zhongZu1 != "") {
              var str18 = this.Saturday.zhongZu1.join(",");
              var pstr18 = this.Saturday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Saturday.zhongFu1 != "") {
              var str1818 = this.Saturday.zhongFu1.join(",");
              var pstr1818 = this.Saturday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Saturday.wanZu1 != "") {
              var str19 = this.Saturday.wanZu1.join(",");
              var pstr19 = this.Saturday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Saturday.wanFu1 != "") {
              var str1919 = this.Saturday.wanFu1.join(",");
              var pstr1919 = this.Saturday.wanFu1Ren.join(",");
            }

            // 7.早主;
            if (this.Sunday.zaoZu1 != "") {
              var str20 = this.Sunday.zaoZu1.join(",");
              var pstr20 = this.Sunday.zaoZu1Ren.join(",");
            }

            // 早副;
            if (this.Sunday.zaoFu1 != "") {
              var str2020 = this.Sunday.zaoFu1.join(",");
              var pstr2020 = this.Sunday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Sunday.zhongZu1 != "") {
              var str21 = this.Sunday.zhongZu1.join(",");
              var pstr21 = this.Sunday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Sunday.zhongFu1 != "") {
              var str2121 = this.Sunday.zhongFu1.join(",");
              var pstr2121 = this.Sunday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Sunday.wanZu1 != "") {
              var str23 = this.Sunday.wanZu1.join(",");
              var pstr23 = this.Sunday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Sunday.wanFu1 != "") {
              var str2323 = this.Sunday.wanFu1.join(",");
              var pstr2323 = this.Sunday.wanFu1Ren.join(",");
            }

            this.arr = [
              {
                cookcategoryIds: str1,
                peopleIds: pstr1,
                // id: this.arr[0].id,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期一",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[1].id,
                cookcategoryIds: str11,
                peopleIds: pstr11,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期一",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[2].id,
                cookcategoryIds: str2,
                peopleIds: pstr2,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期一",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[3].id,
                cookcategoryIds: str22,
                peopleIds: pstr22,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期一",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[4].id,
                cookcategoryIds: str3,
                peopleIds: pstr3,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期一",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[5].id,
                cookcategoryIds: str33,
                peopleIds: pstr33,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期一",
                  typelist: "副食",
                },
              },

              {
                // id: this.arr[6].id,
                cookcategoryIds: str4,
                peopleIds: pstr4,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期二",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[7].id,
                cookcategoryIds: str44,
                peopleIds: pstr44,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期二",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[8].id,
                cookcategoryIds: str5,
                peopleIds: pstr5,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期二",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[9].id,
                cookcategoryIds: str55,
                peopleIds: pstr55,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期二",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[10].id,
                cookcategoryIds: str6,
                peopleIds: pstr6,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期二",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[11].id,
                cookcategoryIds: str66,
                peopleIds: pstr66,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期二",
                  typelist: "副食",
                },
              },

              {
                // id: this.arr[12].id,
                cookcategoryIds: str7,
                peopleIds: pstr7,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期三",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[13].id,
                cookcategoryIds: str77,
                peopleIds: pstr77,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期三",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[14].id,
                cookcategoryIds: str8,
                peopleIds: pstr8,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期三",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[15].id,
                cookcategoryIds: str88,
                peopleIds: pstr88,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期三",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[16].id,
                cookcategoryIds: str9,
                peopleIds: pstr9,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期三",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[17].id,
                cookcategoryIds: str99,
                peopleIds: pstr99,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期三",
                  typelist: "副食",
                },
              },

              {
                // id: this.arr[18].id,
                cookcategoryIds: str10,
                peopleIds: pstr10,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期四",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[19].id,
                cookcategoryIds: str1010,
                peopleIds: pstr1010,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期四",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[20].id,
                cookcategoryIds: str12,
                peopleIds: pstr12,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期四",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[21].id,
                cookcategoryIds: str1212,
                peopleIds: pstr1212,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期四",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[22].id,
                cookcategoryIds: str13,
                peopleIds: pstr13,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期四",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[23].id,
                cookcategoryIds: str1313,
                peopleIds: pstr1313,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期四",
                  typelist: "副食",
                },
              },

              {
                // id: this.arr[24].id,
                cookcategoryIds: str14,
                peopleIds: pstr14,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期五",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[25].id,
                cookcategoryIds: str1414,
                peopleIds: pstr1414,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期五",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[26].id,
                cookcategoryIds: str15,
                peopleIds: pstr15,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期五",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[27].id,
                cookcategoryIds: str1515,
                peopleIds: pstr1515,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期五",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[28].id,
                cookcategoryIds: str16,
                peopleIds: pstr16,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期五",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[29].id,
                cookcategoryIds: str1616,
                peopleIds: pstr1616,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期五",
                  typelist: "副食",
                },
              },

              {
                // id: this.arr[30].id,
                cookcategoryIds: str17,
                peopleIds: pstr17,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期六",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[31].id,
                cookcategoryIds: str1717,
                peopleIds: pstr1717,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期六",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[32].id,
                cookcategoryIds: str18,
                peopleIds: pstr18,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期六",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[33].id,
                cookcategoryIds: str1818,
                peopleIds: pstr1818,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期六",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[34].id,
                cookcategoryIds: str19,
                peopleIds: pstr19,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期六",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[35].id,
                cookcategoryIds: str1919,
                peopleIds: pstr1919,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期六",
                  typelist: "副食",
                },
              },

              {
                // id: this.arr[36].id,
                cookcategoryIds: str20,
                peopleIds: pstr20,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期日",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[37].id,
                cookcategoryIds: str2020,
                peopleIds: pstr2020,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期日",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[38].id,
                cookcategoryIds: str21,
                peopleIds: pstr21,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期日",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[39].id,
                cookcategoryIds: str2121,
                peopleIds: pstr2121,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期日",
                  typelist: "副食",
                },
              },
              {
                // id: this.arr[40].id,
                cookcategoryIds: str23,
                peopleIds: pstr23,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期日",
                  typelist: "主食",
                },
              },
              {
                // id: this.arr[41].id,
                cookcategoryIds: str2323,
                peopleIds: pstr2323,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期日",
                  typelist: "副食",
                },
              },
            ];
            this.form.cookbookRegistrations = this.arr;
            updateBdglcookbook(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            // 1.早主;
            if (this.Monday.zaoZu1 != "") {
              var str1 = this.Monday.zaoZu1.join(",");
              var pstr1 = this.Monday.zaoZu1Ren.join(",");
            }
            // 早副;
            if (this.Monday.zaoFu1 != "") {
              var str11 = this.Monday.zaoFu1.join(",");
              var pstr11 = this.Monday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Monday.zhongZu1 != "") {
              var str2 = this.Monday.zhongZu1.join(",");
              var pstr2 = this.Monday.zhongZu1Ren.join(",");
            }
            // 中副
            if (this.Monday.zhongFu1 != "") {
              var str22 = this.Monday.zhongFu1.join(",");
              var pstr22 = this.Monday.zhongFu1Ren.join(",");
            }
            // 晚主
            if (this.Monday.wanZu1 != "") {
              var str3 = this.Monday.wanZu1.join(",");
              var pstr3 = this.Monday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Monday.wanFu1 != "") {
              var str33 = this.Monday.wanFu1.join(",");
              var pstr33 = this.Monday.wanFu1Ren.join(",");
            }

            // 2.早主;
            if (this.Tuesday.zaoZu1 != "") {
              var str4 = this.Tuesday.zaoZu1.join(",");
              var pstr4 = this.Tuesday.zaoZu1Ren.join(",");
            }
            // 早副;
            if (this.Tuesday.zaoFu1 != "") {
              var str44 = this.Tuesday.zaoFu1.join(",");
              var pstr44 = this.Tuesday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Tuesday.zhongZu1 != "") {
              var str5 = this.Tuesday.zhongZu1.join(",");
              var pstr5 = this.Tuesday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Tuesday.zhongFu1 != "") {
              var str55 = this.Tuesday.zhongFu1.join(",");
              var pstr55 = this.Tuesday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Tuesday.wanZu1 != "") {
              var str6 = this.Tuesday.wanZu1.join(",");
              var pstr6 = this.Tuesday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Tuesday.wanFu1 != "") {
              var str66 = this.Tuesday.wanFu1.join(",");
              var pstr66 = this.Tuesday.wanFu1Ren.join(",");
            }

            // 3.早主;
            if (this.Wednesday.zaoZu1 != "") {
              var str7 = this.Wednesday.zaoZu1.join(",");
              var pstr7 = this.Wednesday.zaoZu1Ren.join(",");
            }

            // 早副;
            if (this.Wednesday.zaoFu1 != "") {
              var str77 = this.Wednesday.zaoFu1.join(",");
              var pstr77 = this.Wednesday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Wednesday.zhongZu1 != "") {
              var str8 = this.Wednesday.zhongZu1.join(",");
              var pstr8 = this.Wednesday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Wednesday.zhongFu1 != "") {
              var str88 = this.Wednesday.zhongFu1.join(",");
              var pstr88 = this.Wednesday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Wednesday.wanZu1 != "") {
              var str9 = this.Wednesday.wanZu1.join(",");
              var pstr9 = this.Wednesday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Wednesday.wanFu1 != "") {
              var str99 = this.Wednesday.wanFu1.join(",");
              var pstr99 = this.Wednesday.wanFu1Ren.join(",");
            }

            // 4.早主;
            if (this.Thursday.zaoZu1 != "") {
              var str10 = this.Thursday.zaoZu1.join(",");
              var pstr10 = this.Thursday.zaoZu1Ren.join(",");
            }

            // 早副;
            if (this.Thursday.zaoFu1 != "") {
              var str1010 = this.Thursday.zaoFu1.join(",");
              var pstr1010 = this.Thursday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Thursday.zhongZu1 != "") {
              var str12 = this.Thursday.zhongZu1.join(",");
              var pstr12 = this.Thursday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Thursday.zhongFu1 != "") {
              var str1212 = this.Thursday.zhongFu1.join(",");
              var pstr1212 = this.Thursday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Thursday.wanZu1 != "") {
              var str13 = this.Thursday.wanZu1.join(",");
              var pstr13 = this.Thursday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Thursday.wanFu1 != "") {
              var str1313 = this.Thursday.wanFu1.join(",");
              var pstr1313 = this.Thursday.wanFu1Ren.join(",");
            }

            // 5.早主;
            if (this.Friday.zaoZu1 != "") {
              var str14 = this.Friday.zaoZu1.join(",");
              var pstr14 = this.Friday.zaoZu1Ren.join(",");
            }

            // 早副;
            if (this.Friday.zaoFu1 != "") {
              var str1414 = this.Friday.zaoFu1.join(",");
              var pstr1414 = this.Friday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Friday.zhongZu1 != "") {
              var str15 = this.Friday.zhongZu1.join(",");
              var pstr15 = this.Friday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Friday.zhongFu1 != "") {
              var str1515 = this.Friday.zhongFu1.join(",");
              var pstr1515 = this.Friday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Friday.wanZu1 != "") {
              var str16 = this.Friday.wanZu1.join(",");
              var pstr16 = this.Friday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Friday.wanFu1 != "") {
              var str1616 = this.Friday.wanFu1.join(",");
              var pstr1616 = this.Friday.wanFu1Ren.join(",");
            }

            // 6.早主;
            if (this.Saturday.zaoZu1 != "") {
              var str17 = this.Saturday.zaoZu1.join(",");
              var pstr17 = this.Saturday.zaoZu1Ren.join(",");
            }

            // 早副;
            if (this.Saturday.zaoFu1 != "") {
              var str1717 = this.Saturday.zaoFu1.join(",");
              var pstr1717 = this.Saturday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Saturday.zhongZu1 != "") {
              var str18 = this.Saturday.zhongZu1.join(",");
              var pstr18 = this.Saturday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Saturday.zhongFu1 != "") {
              var str1818 = this.Saturday.zhongFu1.join(",");
              var pstr1818 = this.Saturday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Saturday.wanZu1 != "") {
              var str19 = this.Saturday.wanZu1.join(",");
              var pstr19 = this.Saturday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Saturday.wanFu1 != "") {
              var str1919 = this.Saturday.wanFu1.join(",");
              var pstr1919 = this.Saturday.wanFu1Ren.join(",");
            }

            // 7.早主;
            if (this.Sunday.zaoZu1 != "") {
              var str20 = this.Sunday.zaoZu1.join(",");
              var pstr20 = this.Sunday.zaoZu1Ren.join(",");
            }

            // 早副;
            if (this.Sunday.zaoFu1 != "") {
              var str2020 = this.Sunday.zaoFu1.join(",");
              var pstr2020 = this.Sunday.zaoFu1Ren.join(",");
            }

            // 中主;
            if (this.Sunday.zhongZu1 != "") {
              var str21 = this.Sunday.zhongZu1.join(",");
              var pstr21 = this.Sunday.zhongZu1Ren.join(",");
            }

            // 中副
            if (this.Sunday.zhongFu1 != "") {
              var str2121 = this.Sunday.zhongFu1.join(",");
              var pstr2121 = this.Sunday.zhongFu1Ren.join(",");
            }

            // 晚主
            if (this.Sunday.wanZu1 != "") {
              var str23 = this.Sunday.wanZu1.join(",");
              var pstr23 = this.Sunday.wanZu1Ren.join(",");
            }

            // 晚副
            if (this.Sunday.wanFu1 != "") {
              var str2323 = this.Sunday.wanFu1.join(",");
              var pstr2323 = this.Sunday.wanFu1Ren.join(",");
            }

            var contentsShi = [
              {
                cookcategoryIds: str1,
                peopleIds: pstr1,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期一",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str11,
                peopleIds: pstr11,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期一",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str2,
                peopleIds: pstr2,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期一",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str22,
                peopleIds: pstr22,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期一",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str3,
                peopleIds: pstr3,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期一",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str33,
                peopleIds: pstr33,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期一",
                  typelist: "副食",
                },
              },

              {
                cookcategoryIds: str4,
                peopleIds: pstr4,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期二",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str44,
                peopleIds: pstr44,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期二",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str5,
                peopleIds: pstr5,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期二",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str55,
                peopleIds: pstr55,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期二",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str6,
                peopleIds: pstr6,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期二",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str66,
                peopleIds: pstr66,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期二",
                  typelist: "副食",
                },
              },

              {
                cookcategoryIds: str7,
                peopleIds: pstr7,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期三",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str77,
                peopleIds: pstr77,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期三",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str8,
                peopleIds: pstr8,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期三",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str88,
                peopleIds: pstr88,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期三",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str9,
                peopleIds: pstr9,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期三",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str99,
                peopleIds: pstr99,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期三",
                  typelist: "副食",
                },
              },

              {
                cookcategoryIds: str10,
                peopleIds: pstr10,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期四",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str1010,
                peopleIds: pstr1010,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期四",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str12,
                peopleIds: pstr12,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期四",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str1212,
                peopleIds: pstr1212,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期四",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str13,
                peopleIds: pstr13,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期四",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str1313,
                peopleIds: pstr1313,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期四",
                  typelist: "副食",
                },
              },

              {
                cookcategoryIds: str14,
                peopleIds: pstr14,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期五",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str1414,
                peopleIds: pstr1414,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期五",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str15,
                peopleIds: pstr15,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期五",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str1515,
                peopleIds: pstr1515,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期五",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str16,
                peopleIds: pstr16,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期五",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str1616,
                peopleIds: pstr1616,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期五",
                  typelist: "副食",
                },
              },

              {
                cookcategoryIds: str17,
                peopleIds: pstr17,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期六",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str1717,
                peopleIds: pstr1717,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期六",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str18,
                peopleIds: pstr18,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期六",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str1818,
                peopleIds: pstr1818,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期六",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str19,
                peopleIds: pstr19,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期六",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str1919,
                peopleIds: pstr1919,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期六",
                  typelist: "副食",
                },
              },

              {
                cookcategoryIds: str20,
                peopleIds: pstr20,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期日",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str2020,
                peopleIds: pstr2020,
                cookbookRegistration: {
                  timelist: "早餐",
                  weeklist: "星期日",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str21,
                peopleIds: pstr21,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期日",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str2121,
                peopleIds: pstr2121,
                cookbookRegistration: {
                  timelist: "午餐",
                  weeklist: "星期日",
                  typelist: "副食",
                },
              },
              {
                cookcategoryIds: str23,
                peopleIds: pstr23,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期日",
                  typelist: "主食",
                },
              },
              {
                cookcategoryIds: str2323,
                peopleIds: pstr2323,
                cookbookRegistration: {
                  timelist: "晚餐",
                  weeklist: "星期日",
                  typelist: "副食",
                },
              },
            ];
            this.form.cookbookRegistrations = contentsShi;
            addBdglcookbook(this.form).then((response) => {
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
        // .confirm('是否确认删除一周食谱编号为"' + ids + '"的数据项？')
        .confirm("是否确认删除该数据？")
        .then(function () {
          return delBdglcookbook(ids);
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
        .confirm("是否确认导出所有一周食谱数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdglcookbook(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped>
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}
::v-deep .el-textarea__inner {
  width: 920px;
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
  border-color: #fff !important;
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
::v-deep .el-dialog__body {
  /* margin: 10px 30px 20px 50px; */
  margin: 10px 0px 20px 62px;
  padding-top: 20px !important;
  padding-right: 0px !important;
  padding-bottom: 0px !important;
  padding-left: 0px !important;
  box-sizing: border-box;
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
/* 表格宽度 */
table td {
  border-bottom: none !important;
  margin: 0px;
  padding: 0px;
  width: 200px;
}
/* 表格下拉 */
::v-deep td .el-input__inner {
  background: transparent;
  color: #fff;
  border: none;
}
/* ::v-deep .el-dialog__body {
  color: #fff;
  font-size: 14px;
} */
::v-deep .el-input__inner {
  cursor: pointer !important;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 42px 30 0px;
  margin-right: 60px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}
::v-deep .el-dialog__body {
  padding: none !important;
}
/* 树形下拉背景颜色 */
/* .el-dialog__body {
  color: #fff !important;
}
::v-deep .vue-treeselect__menu {
  background: #004d86 !important;
} */
/* ::v-deep.el-input_inner ::-webkit-input-placeholder{
        font-size:16px;
        color: rgba(255,255,255,0.1) !important;
}  */

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
#chakan /deep/ .el-dialog__body {
  margin: 10px 0px 20px 64px;
}
</style>
