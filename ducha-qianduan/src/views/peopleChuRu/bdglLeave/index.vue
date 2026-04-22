<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="" prop="unitId">
        <treeselect
          v-model="queryParams.unitId"
          :options="deptOptions"
          placeholder="请选择单位"
          @select="selectPeos"
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
          v-hasPermi="['peopleChuRu:bdglLeave:add']"
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
          v-hasPermi="['peopleChuRu:bdglLeave:edit']"
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
          v-hasPermi="['peopleChuRu:bdglLeave:remove']"
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
          v-hasPermi="['peopleChuRu:bdglLeave:export']"
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
      :data="bdglLeaveList"
      @selection-change="handleSelectionChange"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id">
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
        width="150px"
      />
      <el-table-column label="请假人员" align="center" prop="peopleName" />

      <el-table-column
        label="请假开始时间"
        align="center"
        prop="startTime"
        width="200"
      />

      <el-table-column
        label="请假结束时间"
        align="center"
        prop="endTime"
        width="200"
      />

      <el-table-column label="请假类型" align="center" prop="leaveType">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.leave_type"
            :value="scope.row.leaveType"
          />
        </template>
      </el-table-column>
      <el-table-column label="出行方式" align="center" prop="travelMode">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.travel_mode"
            :value="scope.row.travelMode"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="请假原因"
        align="center"
        prop="leaveReason"
        width="130"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="离队时间"
        align="center"
        prop="departureTime"
        width="200"
      />

      <el-table-column
        label="归队时间"
        align="center"
        prop="returnTime"
        width="200"
      />

      <el-table-column label="是否归队" align="center" prop="isReturn">
        <template slot-scope="scope">
          <!--          <dict-tag-->
          <!--            :options="dict.type.sys_yes_no"-->
          <!--            :value="scope.row.isReturn"-->
          <!--          />-->
          <el-tag :type="scope.row.isReturn === '1' ? 'success' : 'danger'">
            {{ scope.row.isReturn === "1" ? "是" : "否" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="一级审批人"
        align="center"
        prop="peopleName1"
        width="100"
      />
      <el-table-column
        label="一级审批状态"
        align="center"
        prop="peopleId2"
        width="100"
      >
        <template slot-scope="scope">
          <el-col v-if="scope.row.peopleId1 == (null || '' || undefined)">
          </el-col>
          <el-col
            v-if="
              scope.row.status1 == 0 &&
              scope.row.peopleId1 != (null || '' || undefined)
            "
            style="color: #ffba00"
            >未审批</el-col
          >
          <el-col v-if="scope.row.status1 == 1" style="color: #13ce66"
            >已通过</el-col
          >
          <el-col
            v-if="scope.row.status1 == 2"
            :title="scope.row.reasons"
            style="color: #ff4949"
            >已驳回</el-col
          >
          <el-col
            v-if="scope.row.status1 == 3"
            :title="
              scope.row.bdglLeaveFus.length > 0
                ? scope.row.bdglLeaveFus[0].specialApproval
                : ''
            "
            style="color: #1f73d7"
            >特殊审批</el-col
          >
        </template></el-table-column
      >
      <el-table-column
        label="二级审批人"
        align="center"
        prop="peopleName2"
        width="100"
      />
      <el-table-column
        label="二级审批状态"
        align="center"
        prop="peopleId4"
        width="100"
      >
        <template slot-scope="scope">
          <el-col v-if="scope.row.peopleId2 == (null || '' || undefined)">
          </el-col>
          <el-col
            v-if="
              scope.row.status2 == 0 &&
              scope.row.peopleId2 != (null || '' || undefined)
            "
            style="color: #ffba00"
            >未审批</el-col
          >
          <el-col v-if="scope.row.status2 == 1" style="color: #13ce66"
            >已通过</el-col
          >
          <el-col
            v-if="scope.row.status2 == 2"
            :title="scope.row.reasons"
            style="color: #ff4949"
            >已驳回</el-col
          >
          <el-col
            v-if="scope.row.status2 == 3"
            :title="
              scope.row.bdglLeaveFus.length > 1
                ? scope.row.bdglLeaveFus[1].specialApproval
                : ''
            "
            style="color: #1f73d7"
            >特殊审批</el-col
          >
        </template>
      </el-table-column>
      <el-table-column
        label="三级审批人"
        align="center"
        prop="peopleName3"
        width="100"
      />
      <el-table-column
        label="三级审批状态"
        align="center"
        prop="status2"
        width="100"
      >
        <template slot-scope="scope">
          <el-col v-if="scope.row.peopleId3 == (null || '' || undefined)">
          </el-col>
          <el-col
            v-if="
              scope.row.status3 == 0 &&
              scope.row.peopleId3 != (null || '' || undefined)
            "
            style="color: #ffba00"
            >未审批</el-col
          >
          <el-col v-if="scope.row.status3 == 1" style="color: #13ce66"
            >已通过</el-col
          >
          <el-col
            v-if="scope.row.status3 == 2"
            :title="scope.row.reasons"
            style="color: #ff4949"
            >已驳回</el-col
          >
          <el-col
            v-if="scope.row.status3 == 3"
            style="color: #1f73d7"
            :title="
              scope.row.bdglLeaveFus.length > 2
                ? scope.row.bdglLeaveFus[2].specialApproval
                : ''
            "
            >特殊审批</el-col
          >
        </template>
        <!-- :title="scope.row.bdglLeaveFus[2].specialApproval" -->
      </el-table-column>
      <!-- <el-table-column
        label="四级审批人"
        align="center"
        prop="peopleName4"
        width="100"
      /> -->
      <!-- <el-table-column
        label="四级审批状态"
        align="center"
        prop="status4"
        width="100"
      >
        <template slot-scope="scope">
          <el-col v-if="scope.row.peopleId4 == (null || '' || undefined)">
          </el-col>
          <el-col
            v-if="
              scope.row.status4 == 0 &&
              scope.row.peopleId4 != (null || '' || undefined)
            "
            style="color: #ffba00"
            >未审批</el-col
          >
          <el-col v-if="scope.row.status4 == 1" style="color: #13ce66"
            >已通过</el-col
          >
          <el-col
            v-if="scope.row.status4 == 2"
            :title="scope.row.reasons"
            style="color: #ff4949"
            >已驳回</el-col
          >
          <el-col
            v-if="scope.row.status4 == 3"
            style="color: #1f73d7"
            :title="
              scope.row.bdglLeaveFus.length > 3
                ? scope.row.bdglLeaveFus[3].specialApproval
                : ''
            "
            >特殊审批</el-col
          >
        </template>
      </el-table-column> -->
      <el-table-column
        label="总审批状态"
        align="center"
        prop="contents"
        width="120"
      />
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="300"
      >
        <template slot-scope="scope">
          <el-button size="btc" type="text" @click="handleSee(scope.row)"
            >查看</el-button
          >
          <el-button
            :class="[scope.row.common == '1' ? '' : 'active']"
            size="btc"
            type="text"
            @click="handleUpdates(scope.row)"
            >审批</el-button
          >

          <el-button
            :class="[scope.row.common == '2' ? '' : 'active']"
            size="mini"
            type="text"
            @click="handleUpdatess(scope.row)"
            v-hasPermi="['peopleChuRu:bdglLeave:teshu']"
            >特殊审批</el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['peopleChuRu:bdglLeave:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['peopleChuRu:bdglLeave:remove']"
            >删除</el-button
          >
          <el-button
            v-if="scope.row.isReturn == '0'"
            size="btc"
            type="text"
            @click="handleGuiDui(scope.row)"
            v-hasPermi="['peopleChuRu:bdglLeave:guidui']"
            >归队</el-button
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
    <!-- 添加人员请假对话框 -->
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
        <div class="jiben">基本信息</div>

        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeo"
            @open="blurSelect"
          />
        </el-form-item>

        <el-form-item label="请假开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假类型" prop="leaveType">
          <el-select v-model="form.leaveType" placeholder="请选择请假类型">
            <!-- <el-option label="请选择字典生成" value="" /> -->
            <el-option
              v-for="item in ziDian"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出行方式" prop="travelMode">
          <el-select
            v-model="form.travelMode"
            placeholder="请选择出行方式"
            size="small"
            @change="handleTravelModeChange"
          >
            <el-option
              v-for="item in travelModeDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 入口门闸选择框 -->
        <el-form-item
          label="入口门闸"
          prop="doorIn"
          :rules="[
            {
              required: true,
              message: '入口门闸不能为空',
              trigger: 'blur',
            },
          ]"
        >
          <el-select
            v-model="form.doorIn"
            placeholder="请选择入口门闸"
            size="small"
          >
            <el-option
              v-for="item in entryGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 出口门闸选择框 -->
        <el-form-item
          label="出口门闸"
          prop="doorOut"
          :rules="
            form.travelMode === '1'
              ? []
              : [
                  {
                    required: true,
                    message: '出口门闸不能为空',
                    trigger: 'blur',
                  },
                ]
          "
        >
          <el-select
            v-model="form.doorOut"
            placeholder="请选择出口门闸"
            size="small"
          >
            <el-option
              v-for="item in exitGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 车辆类型选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="车辆类型"
          prop="carType"
        >
          <el-switch
            v-model="form.carType"
            active-value="1"
            inactive-value="0"
            active-text="军车"
            inactive-text="私家车"
            size="small"
            @change="handleCarTypeChange"
          />
        </el-form-item>

        <!-- 入口车闸选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="入口车闸"
          prop="carIn"
        >
          <el-select
            v-model="form.carIn"
            placeholder="请选择入口车闸"
            size="small"
          >
            <el-option
              v-for="item in entryBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 出口车闸选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="出口车闸"
          prop="carOut"
        >
          <el-select
            v-model="form.carOut"
            placeholder="请选择出口车闸"
            size="small"
          >
            <el-option
              v-for="item in exitBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 车牌号下拉搜索框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1' && form.carType === '0'"
          label="车牌号"
          prop="plateNumber"
        >
          <el-select
            v-model="form.plateNumber"
            filterable
            remote
            reserve-keyword
            placeholder="请输入车牌号进行搜索"
            size="small"
            :remote-method="queryPlateNumber"
            :loading="plateNumberLoading"
          >
            <el-option
              v-for="item in plateNumberOptions"
              :key="item.id"
              :label="item.carIds"
              :value="item.carIds"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="一级审批人" prop="peopleId1">
          <!-- <el-input v-model="form.peopleId2" placeholder="请输入二级审批人" /> -->
          <el-select
            v-model="form.peopleId1"
            filterable
            placeholder="请选择一级审批人"
            ref="abcdef"
            clearable
            @clear="peopleIdOne"
          >
            <el-option
              v-for="(item, i) in lianJiRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="oneRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级审批人" prop="peopleId2">
          <!-- <el-input v-model="form.peopleId2" placeholder="请输入二级审批人" /> -->
          <el-select
            v-model="form.peopleId2"
            filterable
            placeholder="请选择二级审批人"
            ref="abcde"
            clearable
            @clear="peopleIdTwo"
          >
            <el-option
              v-for="(item, i) in yingJiRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="twoRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级审批人" prop="peopleId3">
          <!-- <el-input v-model="form.peopleId3" placeholder="请输入三级审批人" /> -->
          <el-select
            v-model="form.peopleId3"
            filterable
            placeholder="请选择三级审批人"
            ref="abcd"
            clearable
            @clear="peopleIdThere"
          >
            <el-option
              v-for="(item, i) in zhengZhiRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="thereRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="四级审批人" prop="peopleId4" style="display: none">
          <el-select
            v-model="form.peopleId4"
            filterable
            placeholder="请选择四级审批人"
            ref="abc"
            disabled
          >
            <el-option
              v-for="(item, i) in yongHu"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="fourRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="请假人员" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请输入请假人员"
            ref="headerSearchSelect"
            multiple
            class="ren_yuans"
            filterable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="qingRen(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <!-- 司机选择（仅自驾时从请假人员中选择） -->
        <el-form-item
          label="司机"
          v-if="
            form.travelMode === '1' && form.peopleId && form.peopleId.length > 0
          "
        >
          <el-select
            v-model="form.selectedDriver"
            placeholder="请从请假人员中选择司机"
            size="small"
            clearable
            @change="onDriverChange"
          >
            <el-option
              v-for="id in form.peopleId"
              :key="id"
              :label="getPersonName(id)"
              :value="id"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <div class="jiben">请假原因</div>
        <el-form-item prop="leaveReason">
          <el-input
            v-model="form.leaveReason"
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

    <!-- 修改人员请假对话框 -->
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
        label-width="80px"
        inline
      >
        <div class="jiben">基本信息</div>

        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeo"
            @open="blurSelect"
            :disabled="xiugai"
          />
        </el-form-item>

        <el-form-item label="请假开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            placeholder="选择请假结束时间"
            type="datetime"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假类型" prop="leaveType">
          <el-select v-model="form.leaveType" placeholder="请选择请假类型">
            <!-- <el-option label="请选择字典生成" value="" /> -->
            <el-option
              v-for="item in ziDian"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出行方式" prop="travelMode">
          <el-select
            v-model="form.travelMode"
            placeholder="请选择出行方式"
            size="small"
            @change="handleTravelModeChange"
          >
            <el-option
              v-for="item in travelModeDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 入口门闸选择框 -->
        <el-form-item label="入口门闸" prop="doorIn">
          <el-select
            v-model="form.doorIn"
            placeholder="请选择入口门闸"
            size="small"
          >
            <el-option
              v-for="item in entryGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 出口门闸选择框 -->
        <el-form-item label="出口门闸" prop="doorOut">
          <el-select
            v-model="form.doorOut"
            placeholder="请选择出口门闸"
            size="small"
          >
            <el-option
              v-for="item in exitGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="离队时间" prop="departureTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.departureTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择离队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="归队时间" prop="returnTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择归队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>

        <!-- 车辆类型选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="车辆类型"
          prop="carType"
        >
          <el-switch
            v-model="form.carType"
            active-value="1"
            inactive-value="0"
            active-text="军车"
            inactive-text="私家车"
            size="small"
            @change="handleCarTypeChange"
          />
        </el-form-item>

        <!-- 入口车闸选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="入口车闸"
          prop="carIn"
        >
          <el-select
            v-model="form.carIn"
            placeholder="请选择入口车闸"
            size="small"
          >
            <el-option
              v-for="item in entryBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 出口车闸选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="出口车闸"
          prop="carOut"
        >
          <el-select
            v-model="form.carOut"
            placeholder="请选择出口车闸"
            size="small"
          >
            <el-option
              v-for="item in exitBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 车牌号下拉搜索框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1' && form.carType === '0'"
          label="车牌号"
          prop="plateNumber"
        >
          <el-select
            v-model="form.plateNumber"
            filterable
            remote
            reserve-keyword
            placeholder="请输入车牌号进行搜索"
            size="small"
            :remote-method="queryPlateNumber"
            :loading="plateNumberLoading"
          >
            <el-option
              v-for="item in plateNumberOptions"
              :key="item.id"
              :label="item.carIds"
              :value="item.carIds"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="一级审批人" prop="peopleId2">
          <!-- <el-input v-model="form.peopleId2" placeholder="请输入二级审批人" /> -->
          <el-select
            v-model="form.peopleId1"
            filterable
            placeholder="请输入一级审批人"
            ref="abcdef"
            :disabled="xiugai"
            clearable
          >
            <el-option
              v-for="(item, i) in lianJiRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="oneRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级审批人" prop="peopleId2">
          <!-- <el-input v-model="form.peopleId2" placeholder="请输入二级审批人" /> -->
          <el-select
            v-model="form.peopleId2"
            filterable
            placeholder="请输入二级审批人"
            ref="abcde"
            :disabled="xiugai"
            clearable
          >
            <el-option
              v-for="(item, i) in yingJiRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="twoRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级审批人" prop="peopleId3">
          <!-- <el-input v-model="form.peopleId3" placeholder="请输入三级审批人" /> -->
          <el-select
            v-model="form.peopleId3"
            filterable
            placeholder="请输入三级审批人"
            ref="abcd"
            :disabled="xiugai"
            clearable
          >
            <el-option
              v-for="(item, i) in zhengZhiRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="thereRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="四级审批人" prop="peopleId4" style="display: none">
          <!-- <el-input v-model="form.peopleId4" placeholder="请输入四级审批人" /> -->
          <el-select
            v-model="form.peopleId4"
            filterable
            placeholder="请输入四级审批人"
            ref="abc"
            :disabled="xiugai"
          >
            <el-option
              v-for="(item, i) in yongHu"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="fourRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="请假人员" prop="peopleId">
          <!-- <el-input v-model="form.peopleId" placeholder="请输入请假人员" /> -->
          <el-select
            v-model="form.peopleId"
            placeholder="请输入请假人员"
            ref="headerSearchSelect"
            :disabled="xiugai"
            multiple
            class="ren_yuans"
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="qingRen(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item
          label="司机"
          prop="selectedDriver"
          v-if="
            form.travelMode === '1' && form.peopleId && form.peopleId.length > 0
          "
        >
          <el-select
            v-model="form.selectedDriver"
            placeholder="请选择司机"
            class="ren_yuans"
            @change="onDriverChange"
          >
            <el-option
              v-for="(item, i) in form.peopleId"
              :key="i"
              :label="getPersonName(item)"
              :value="item"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <div class="jiben">请假原因</div>
        <el-form-item prop="leaveReason">
          <el-input
            v-model="form.leaveReason"
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

    <!-- 审批人员请假对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open2"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form1"
        :model="form"
        :rules="rulesF"
        label-width="80px"
        inline
      >
        <div class="jiben">基本信息</div>

        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="shenAllList"
            placeholder="请选择单位"
            @select="selectPeo"
            @open="blurSelect"
            disabled
          />
        </el-form-item>

        <el-form-item label="请假开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假类型" prop="leaveType">
          <el-select
            v-model="form.leaveType"
            placeholder="请选择请假类型"
            disabled
          >
            <!-- <el-option label="请选择字典生成" value="" /> -->
            <el-option
              v-for="item in ziDian"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出行方式" prop="travelMode">
          <el-select
            v-model="form.travelMode"
            placeholder="请选择出行方式"
            disabled
            size="small"
          >
            <el-option
              v-for="item in travelModeDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 入口门闸选择框 -->
        <el-form-item label="入口门闸" prop="doorIn">
          <el-select
            v-model="form.doorIn"
            placeholder="请选择入口门闸"
            size="small"
            disabled
          >
            <el-option
              v-for="item in entryGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 出口门闸选择框 -->
        <el-form-item label="出口门闸" prop="doorOut">
          <el-select
            v-model="form.doorOut"
            placeholder="请选择出口门闸"
            size="small"
            disabled
          >
            <el-option
              v-for="item in exitGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 车辆类型选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="车辆类型"
          prop="carType"
        >
          <el-switch
            v-model="form.carType"
            active-value="1"
            inactive-value="0"
            active-text="军车"
            inactive-text="私家车"
            size="small"
            disabled
          />
        </el-form-item>

        <!-- 入口车闸选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="入口车闸"
          prop="carIn"
          :rules="[
            { required: true, message: '入口车闸不能为空', trigger: 'blur' },
          ]"
        >
          <el-select
            v-model="form.carIn"
            placeholder="请选择入口车闸"
            size="small"
            disabled
          >
            <el-option
              v-for="item in entryBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 出口车闸选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="出口车闸"
          prop="carOut"
          :rules="[
            { required: true, message: '出口车闸不能为空', trigger: 'blur' },
          ]"
        >
          <el-select
            v-model="form.carOut"
            placeholder="请选择出口车闸"
            size="small"
            disabled
          >
            <el-option
              v-for="item in exitBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 车牌号字段 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="车牌号"
          prop="plateNumber"
        >
          <el-input
            v-model="form.plateNumber"
            placeholder="车牌号"
            size="small"
            disabled
          />
        </el-form-item>
        <el-form-item label="离队时间" prop="departureTime">
          <!-- <el-input v-model="form.departureTime" placeholder="请输入离队时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.departureTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择离队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="归队时间" prop="returnTime">
          <!-- <el-input v-model="form.returnTime" placeholder="请输入归队时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择归队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="一级审批人" prop="peopleId2">
          <!-- <el-input v-model="form.peopleId2" placeholder="请输入二级审批人" /> -->
          <el-select
            v-model="form.peopleId1"
            filterable
            placeholder="请输入一级审批人"
            ref="abcdef"
            disabled
            clearable
          >
            <el-option
              v-for="(item, i) in shenAllRenList"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="oneRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级审批人" prop="peopleId2">
          <!-- <el-input v-model="form.peopleId2" placeholder="请输入二级审批人" /> -->
          <el-select
            v-model="form.peopleId2"
            filterable
            placeholder="请输入二级审批人"
            ref="abcde"
            disabled
            clearable
          >
            <el-option
              v-for="(item, i) in yingJiRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="twoRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级审批人" prop="peopleId3">
          <!-- <el-input v-model="form.peopleId3" placeholder="请输入三级审批人" /> -->
          <el-select
            v-model="form.peopleId3"
            filterable
            placeholder="请输入三级审批人"
            ref="abcd"
            disabled
          >
            <el-option
              v-for="(item, i) in zhengZhiRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="thereRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="四级审批人" prop="peopleId4" style="display: none">
          <!-- <el-input v-model="form.peopleId4" placeholder="请输入四级审批人" /> -->
          <el-select
            v-model="form.peopleId4"
            filterable
            placeholder="请输入四级审批人"
            ref="abc"
            disabled
          >
            <el-option
              v-for="(item, i) in yongHu"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="fourRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="请假人员" prop="peopleId">
          <!-- <el-input v-model="form.peopleId" placeholder="请输入请假人员" /> -->
          <el-select
            v-model="form.peopleId"
            placeholder="请输入请假人员"
            ref="headerSearchSelect"
            disabled
            multiple
            class="ren_yuans"
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="qingRen(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item
          label="司机"
          prop="selectedDriver"
          v-if="
            form.travelMode === '1' && form.peopleId && form.peopleId.length > 0
          "
        >
          <el-select
            v-model="form.selectedDriver"
            placeholder="请选择司机"
            class="ren_yuans"
            disabled
          >
            <el-option
              v-for="(item, i) in form.peopleId"
              :key="i"
              :label="getPersonName(item)"
              :value="item"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <div class="jiben">请假原因</div>
        <el-form-item prop="leaveReason">
          <el-input
            v-model="form.leaveReason"
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
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button type="primary" @click="submitForms">通过</el-button>
        <el-button @click="boHui" size="btn">驳回</el-button>
      </div>
    </el-dialog>
    <!--特殊审批对话框 -->
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
        :rules="rulesform"
        label-width="80px"
        inline
      >
        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="shenAllList"
            placeholder="选择单位"
            @select="selectPeo"
            @open="blurSelect"
            disabled
          />
        </el-form-item>

        <el-form-item label="请假开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假类型" prop="leaveType">
          <el-select
            v-model="form.leaveType"
            placeholder="请选择请假类型"
            disabled
          >
            <el-option
              v-for="item in ziDian"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出行方式" prop="travelMode">
          <el-select
            v-model="form.travelMode"
            placeholder="请选择出行方式"
            disabled
            size="small"
          >
            <el-option
              v-for="item in travelModeDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="离队时间" prop="departureTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.departureTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择离队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="归队时间" prop="returnTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择归队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="一级审批人" prop="peopleId1">
          <el-select
            v-model="form.peopleId1"
            filterable
            placeholder="请输入一级审批人"
            ref="abcdef"
            disabled
          >
            <el-option
              v-for="(item, i) in shenAllRenList"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="oneRen(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级审批人" prop="peopleId2">
          <el-select
            v-model="form.peopleId2"
            filterable
            placeholder="请输入二级审批人"
            ref="abcde"
            disabled
          >
            <el-option
              v-for="(item, i) in shenAllRenList"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="twoRen(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级审批人" prop="peopleId3">
          <el-select
            v-model="form.peopleId3"
            filterable
            placeholder="请输入三级审批人"
            ref="abcd"
            disabled
          >
            <el-option
              v-for="(item, i) in zhengZhiRen"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="thereRen(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="四级审批人" prop="peopleId4" style="display: none">
          <el-select
            v-model="form.peopleId4"
            filterable
            placeholder="请输入四级审批人"
            ref="abc"
            disabled
          >
            <el-option
              v-for="(item, i) in yongHu"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="fourRen(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="请假人员" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请选择请假人员"
            ref="headerSearchSelect"
            disabled
            multiple
            class="ren_yuans"
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

        <div class="jiben">请假原因</div>
        <el-form-item prop="leaveReason">
          <el-input
            v-model="form.leaveReason"
            type="textarea"
            placeholder="请输入内容"
            disabled
          />
        </el-form-item>
        <div class="jiben">驳回原因</div>
        <el-form-item prop="">
          <el-input
            v-model="form.reasons"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <div class="jiben">特殊审批原因</div>
        <el-form-item prop="specialApproval">
          <el-input
            v-model="form.specialApproval"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button type="primary" @click="TSsubmitForms">通过</el-button>
        <el-button @click="TSboHui" size="btn">驳回</el-button>
      </div>
    </el-dialog>

    <!-- 查看对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="opensee"
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
        <div class="jiben">基本信息</div>

        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="shenAllList"
            placeholder="请选择单位"
            @select="selectPeo"
            @open="blurSelect"
            disabled
          />
        </el-form-item>

        <el-form-item label="请假开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择请假结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假类型" prop="leaveType">
          <el-select
            v-model="form.leaveType"
            placeholder="请选择请假类型"
            disabled
          >
            <el-option
              v-for="item in ziDian"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出行方式" prop="leaveType">
          <el-select
            v-model="form.travelMode"
            placeholder="请选择出行方式"
            disabled
          >
            <!-- <el-option label="请选择字典生成" value="" /> -->
            <el-option
              v-for="item in travelModeDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </el-select>
        </el-form-item>

        <!-- 入口门闸选择框 -->
        <el-form-item label="入口门闸" prop="doorIn">
          <el-select
            v-model="form.doorIn"
            placeholder="请选择入口门闸"
            size="small"
            disabled
          >
            <el-option
              v-for="item in entryGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 出口门闸选择框 -->
        <el-form-item label="出口门闸" prop="doorOut">
          <el-select
            v-model="form.doorOut"
            placeholder="请选择出口门闸"
            size="small"
            disabled
          >
            <el-option
              v-for="item in exitGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 车辆类型选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="车辆类型"
          prop="carType"
        >
          <el-switch
            v-model="form.carType"
            :active-value="1"
            :inactive-value="0"
            disabled
          />
        </el-form-item>

        <!-- 入口车闸选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="入口车闸"
          prop="carIn"
        >
          <el-select
            v-model="form.carIn"
            placeholder="请选择入口车闸"
            size="small"
            disabled
          >
            <el-option
              v-for="item in entryBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 出口车闸选择框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="出口车闸"
          prop="carOut"
        >
          <el-select
            v-model="form.carOut"
            placeholder="请选择出口车闸"
            size="small"
            disabled
          >
            <el-option
              v-for="item in exitBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 车牌号输入框 - 仅当出行方式为自驾时显示 -->
        <el-form-item
          v-if="form.travelMode === '1'"
          label="车牌号"
          prop="plateNumber"
        >
          <el-input
            v-model="form.plateNumber"
            placeholder="请输入车牌号"
            size="small"
            disabled
          />
        </el-form-item>

        <el-form-item label="离队时间" prop="departureTime">
          <!-- <el-input v-model="form.departureTime" placeholder="请输入离队时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.departureTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择离队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="归队时间" prop="returnTime">
          <!-- <el-input v-model="form.returnTime" placeholder="请输入归队时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择归队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="一级审批人" prop="peopleId2">
          <!-- <el-input v-model="form.peopleId2" placeholder="请输入二级审批人" /> -->
          <el-select
            v-model="form.peopleId1"
            filterable
            placeholder="请输入一级审批人"
            ref="abcdef"
            disabled
          >
            <el-option
              v-for="(item, i) in shenAllRenList"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="oneRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级审批人" prop="peopleId2">
          <!-- <el-input v-model="form.peopleId2" placeholder="请输入二级审批人" /> -->
          <el-select
            v-model="form.peopleId2"
            filterable
            placeholder="请输入二级审批人"
            ref="abcde"
            disabled
          >
            <el-option
              v-for="(item, i) in shenAllRenList"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="twoRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级审批人" prop="peopleId3">
          <!-- <el-input v-model="form.peopleId3" placeholder="请输入三级审批人" /> -->
          <el-select
            v-model="form.peopleId3"
            filterable
            placeholder="请输入三级审批人"
            ref="abcd"
            disabled
          >
            <el-option
              v-for="(item, i) in shenAllRenList"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="thereRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="请假人员" prop="peopleId">
          <!-- <el-input v-model="form.peopleId" placeholder="请输入请假人员" /> -->
          <el-select
            v-model="form.peopleId"
            placeholder="请输入请假人员"
            ref="headerSearchSelect"
            disabled
            multiple
            class="ren_yuans"
            style="width: 100%"
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="qingRen(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item
          label="司机"
          prop="selectedDriver"
          v-if="
            form.travelMode === '1' && form.peopleId && form.peopleId.length > 0
          "
        >
          <el-select
            v-model="form.selectedDriver"
            placeholder="请选择司机"
            class="ren_yuans"
            disabled
          >
            <el-option
              v-for="(item, i) in form.peopleId"
              :key="i"
              :label="getPersonName(item)"
              :value="item"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <div class="jiben">请假原因</div>
        <el-form-item prop="leaveReason">
          <el-input
            v-model="form.leaveReason"
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
            placeholder="请输入内容"
            disabled
          />
        </el-form-item>
        <div class="jiben" v-if="oneShenPi">一级特殊审批原因</div>
        <el-form-item prop="reasons" v-if="oneShenPi">
          <el-input
            type="textarea"
            placeholder="请输入内容"
            disabled
            v-model="shenPiQuan.one"
          />
        </el-form-item>
        <div class="jiben" v-if="twoShenPi">二级特殊审批原因</div>
        <el-form-item prop="reasons" v-if="twoShenPi">
          <el-input
            type="textarea"
            placeholder="请输入内容"
            disabled
            v-model="shenPiQuan.two"
          />
        </el-form-item>
        <div class="jiben" v-if="thereShenPi">三级特殊审批原因</div>
        <el-form-item prop="reasons" v-if="thereShenPi">
          <el-input
            type="textarea"
            placeholder="请输入内容"
            disabled
            v-model="shenPiQuan.there"
          />
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button type="primary" @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listBdglLeave,
  getBdglLeave,
  delBdglLeave,
  addBdglLeave,
  updateBdglLeave,
  exportBdglLeave,
  YingJIUSer,
  LIanJIUSer,
  ZhengZhiUSer,
  getBdglList,
  getAllDept,
  getAll,
  guidui,
  updateBdglLeaveShen,
  updateBdglLeaveTe,
  getPlateNumbers,
  getGateDevices,
} from "@/api/peopleChuRu/bdglLeave";

import {
  getUserzs,
  getDept,
  getQuanBu,
} from "@/api/grassrootsregistration/bdglmeeting";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
// 查询字典
import { getDicts } from "@/api/system/dict/data";
import request from "@/utils/request";
//人员信息
import { getUserProfile } from "@/api/system/user";

export default {
  name: "BdglLeave",
  components: {
    Treeselect,
  },
  dicts: ["leave_type", "sys_yes_no", "vocation_state", "travel_mode"],
  data() {
    return {
      isActive: false,
      // 审批按钮状态 审批按钮状态1 修改为0,超级审批状态2
      statussp: "1",
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
      // 人员请假表格数据
      bdglLeaveList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示特殊权限弹出层
      opens: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        peopleId: null,
        unitId: null,
        // unitId: this.$store.getters.deptId,
        startTime: null,
        endTime: null,
        leaveType: null,
        travelMode: null,
        leaveReason: null,
        returnStatus: null,
        isReturn: null,
        updatetime: null,
        adminId: null,
        createtime: null,
        peopleId1: null,
        postId: null,
        peopleId2: null,
        peopleId3: null,
        peopleId4: null,
        status1: null,
        status2: null,
        status3: null,
        status4: null,
        contents: null,
        statusd: null,
        rejoin: null,
        peopleName1: null,
        peopleName2: null,
        peopleName3: null,
      },
      // 表单参数
      form: {
        selectedDriver: null,
      },
      // 表单校验
      rules: {
        unitId: [{ required: true, message: "单位不能为空", trigger: "blur" }],
        peopleId: [
          { required: true, message: "人员不能为空", trigger: "blur" },
        ],
        // reasons: [
        //   { required: true, message: "请填写驳回原因", trigger: "blur" },
        // ],
        leaveType: [
          { required: true, message: "请假类型不能为空", trigger: "blur" },
        ],
        travelMode: [
          { required: true, message: "出行方式不能为空", trigger: "blur" },
        ],
        // 门闸相关字段
        doorIn: [],
        doorOut: [],
        // 车闸相关字段（当选择自驾时必填）
        carIn: [],
        carOut: [],
      },
      // 审批验证
      rulesF: {
        unitId: [{ required: true, message: "单位不能为空", trigger: "blur" }],
        peopleId: [
          { required: true, message: "人员不能为空", trigger: "blur" },
        ],
        reasons: [
          { required: true, message: "请填写驳回原因", trigger: "blur" },
        ],
        leaveType: [
          { required: true, message: "请假类型不能为空", trigger: "blur" },
        ],
        travelMode: [
          { required: true, message: "出行方式不能为空", trigger: "blur" },
        ],
        // 门闸相关字段
        doorIn: [],
        doorOut: [],
        // 车闸相关字段（当选择自驾时必填）
        carIn: [],
        carOut: [],
      },
      // 特殊审批表单验证
      rulesform: {
        reasons: [
          { required: true, message: "请填写驳回原因", trigger: "blur" },
        ],
        specialApproval: [
          {
            required: true,
            message: "审批原因不能为空",
            trigger: "blur",
          },
        ],
      },
      // 树形结构列表
      deptOptions: [],
      // 用户列表
      yongHu: [],
      // 人员列表
      renYuan: [],
      // 查询字典请假列表
      dictType: {
        dictType: "Leave_type",
      },
      // 出行方式字典类型
      travelModeDictType: {
        dictType: "travel_mode",
      },
      // 字典的列表
      ziDian: [],
      // 出行方式字典列表
      travelModeDict: [],
      // 门闸数据列表（动态获取）
      entryGateDict: [], // 入口门闸
      exitGateDict: [], // 出口门闸
      // 车闸数据列表（动态获取）
      entryBarrierDict: [], // 入口车闸
      exitBarrierDict: [], // 出口车闸
      // 保持原有变量名兼容
      gateDict: [],
      barrierDict: [],
      // 审批状态
      ziDians: [],
      // 特殊审批通过验证
      trueng: true,
      // 归队数据
      guidui: {},
      // 审批对象
      shenPis: {},
      // 特殊审批对象
      teShu: {},
      // 修改弹出层
      open1: false,
      // 修改审批人禁止修改
      xiugai: false,
      // 修改拿到的数据
      result: {},
      // 审批弹出层显示与隐藏
      open2: false,
      // 营级人员
      yingJiRen: [],
      // 车牌号搜索相关
      plateNumberLoading: false,
      plateNumberOptions: [],
      // 当前时间（用于判断车辆有效期）
      currentTime: new Date().getTime(),
      // 连级人员
      lianJiRen: [],
      // 政治人员
      zhengZhiRen: [],
      // 查看弹出层的显示与隐藏
      opensee: false,
      // 所有审批人
      yingJiRen1: [],
      // 一级特殊审批原因
      oneShenPi: false,
      // 二级特殊审批原因
      twoShenPi: false,
      // 三级特殊审批原因
      thereShenPi: false,
      shenPiQuan: {
        one: null,
        two: null,
        there: null,
      },
      // 审批单位所有列表
      shenAllList: [],
      // 审批获取所有人
      shenAllRenList: [],
    };
  },
  created() {
    // console.log("完整的用户信息是：", this.$store.state.user);
    // // this.queryParams.unitId = myDepeId;
    // const id2 = this.$store.state.user.user ?this.$store.state.user.user.deptId : null;
    // console.log("当前登录人的单位ID是：", id2);

    getUserProfile().then((response) => {
      const user = response.data;
      if (user && user.deptId) {
        console.log("成功获取当前人单位id:", user.deptId);
        this.queryParams.unitId = user.deptId;
        this.getList();
      } else {
        this.getList();
      }
    });
    // 字典查询数据
    this.getDicts("approve").then((response) => {
      this.ziDians = response.data;
    });
    // 获取出行方式字典
    this.getTravelModeDict();
    this.getYing();
    this.getLiai();
    this.getZheng();
    this.getSuoYou();
    this.getBuMeng();
    // 获取门闸设备信息
    this.getGateDevicesData();
  },
  methods: {
    // 获取所有审批人员
    getShenRen() {
      getAll().then((res) => {
        this.shenAllRenList = res.rows;
        console.log("获取所有审批人员", this.shenAllRenList);
      });
    },

    // 获取门闸设备信息
    getGateDevicesData() {
      // 获取入口门闸
      getGateDevices("door_in")
        .then((res) => {
          if (res.code === 200) {
            // 转换后端数据格式为前端需要的格式
            this.entryGateDict = res.data.map((device) => ({
              dictCode: device.id,
              dictLabel: device.name || device.deviceName || "未知门闸",
              dictValue: device.id,
            }));
            // 保持兼容性
            this.gateDict = this.entryGateDict;
          }
        })
        .catch((error) => {
          console.error("获取入口门闸失败:", error);
        });

      // 获取出口门闸
      getGateDevices("door_out")
        .then((res) => {
          if (res.code === 200) {
            this.exitGateDict = res.data.map((device) => ({
              dictCode: device.id,
              dictLabel: device.name || device.deviceName || "未知门闸",
              dictValue: device.id,
            }));
          }
        })
        .catch((error) => {
          console.error("获取出口门闸失败:", error);
        });

      // 获取入口车闸
      getGateDevices("car_in")
        .then((res) => {
          if (res.code === 200) {
            this.entryBarrierDict = res.data.map((device) => ({
              dictCode: device.id,
              dictLabel: device.name || device.deviceName || "未知车闸",
              dictValue: device.id,
            }));
            // 保持兼容性
            this.barrierDict = this.entryBarrierDict;
          }
        })
        .catch((error) => {
          console.error("获取入口车闸失败:", error);
        });

      // 获取出口车闸
      getGateDevices("car_out")
        .then((res) => {
          if (res.code === 200) {
            this.exitBarrierDict = res.data.map((device) => ({
              dictCode: device.id,
              dictLabel: device.name || device.deviceName || "未知车闸",
              dictValue: device.id,
            }));
          }
        })
        .catch((error) => {
          console.error("获取出口车闸失败:", error);
        });
    },
    // 审批获取所有单位
    getShenAll() {
      getAllDept().then((res) => {
        this.shenAllList = res.data;
      });
    },
    // 一级审批人清除按钮触发
    peopleIdOne() {
      this.form.peopleName1 = null;
    },
    // 二级审批人清除按钮触发
    peopleIdTwo() {
      this.form.peopleName2 = null;
    },
    // 三级审批人清除按钮触发
    peopleIdThere() {
      this.form.peopleName3 = null;
    },
    // 营级人员
    getYing() {
      YingJIUSer().then((res) => {
        this.yingJiRen = res.data;
      });
    },
    // 连级人员
    getLiai() {
      LIanJIUSer().then((res) => {
        this.lianJiRen = res.data;
      });
    },
    // 政治人员
    getZheng() {
      ZhengZhiUSer().then((res) => {
        this.zhengZhiRen = res.data;
      });
    },
    // 审批人所有
    getSuoYou() {
      getBdglList().then((res) => {
        this.yingJiRen1 = res.rows;
      });
    },

    // 归队按钮
    handleGuiDui(row) {
      const id = row.id || this.ids;
      getBdglLeave(id).then((response) => {
        this.form = response.data;
      });
      this.$modal
        .confirm("是否确认归队人员的数据项？")
        .then(() => {
          if (this.form.id != null) {
            this.form.rejoin = "1";
            guidui(this.form).then((response) => {
              this.$modal.msgSuccess("归队成功");
              this.getList();
            });
          }
        })
        .catch(() => {});
    },
    // // 特殊审批按钮触发
    handleUpdatess(row) {
      this.teShu = row;
      this.getShenAll();
      this.getShenRen();
      // 获取字典类型
      this.getdict();
      // 获取出行方式字典
      this.getTravelModeDict();
      // 获取门闸和车闸设备信息
      this.getGateDevicesData();
      this.reset();
      const id = row.id || this.ids;
      getBdglLeave(id).then((response) => {
        this.result = response.data;
        this.result.peopleId = this.result.peopleId.split(",");
        this.result.peopleId = this.result.peopleId.map(Number);
        // 确保carType和plateNumber字段存在
        this.form = {
          ...response.data,
          specialApproval: null,
          carType: response.data.carType || null,
          plateNumber: response.data.plateNumber || null,
          doorIn: response.data.doorIn || null,
          doorOut: response.data.doorOut || null,
          carIn: response.data.carIn || null,
          carOut: response.data.carOut || null,
          // 将司机设置为请假人员的第一个
          selectedDriver:
            this.result.peopleId.length > 0 ? this.result.peopleId[0] : null,
        };
        this.getYong(this.form.unitId);
        this.getRen(this.form.unitId);
        this.opens = true;
        this.title = "审批人员请假";
      });
    },
    // 审批按钮操作
    handleUpdates(row) {
      this.shenPis = row;
      this.getShenAll();
      this.getShenRen();
      // 获取字典类型
      this.getdict();
      // 获取出行方式字典
      this.getTravelModeDict();
      // 获取门闸和车闸设备信息
      this.getGateDevicesData();
      this.reset();
      const id = row.id || this.ids;
      getBdglLeave(id).then((response) => {
        this.result = response.data;
        this.result.peopleId = this.result.peopleId.split(",");
        this.result.peopleId = this.result.peopleId.map(Number);
        // 确保carType、plateNumber和门闸车闸相关字段存在
        this.form = {
          ...this.result,
          carType: this.result.carType || null,
          plateNumber: this.result.plateNumber || null,
          doorIn: this.result.doorIn || null,
          doorOut: this.result.doorOut || null,
          carIn: this.result.carIn || null,
          carOut: this.result.carOut || null,
          // 将司机设置为请假人员的第一个
          selectedDriver:
            this.result.peopleId.length > 0 ? this.result.peopleId[0] : null,
        };
        this.getYong(this.form.unitId);
        this.getRen(this.form.unitId);
        this.open2 = true;
        this.title = "审批人员请假";
      });
    },
    // 让单位失去焦点
    blurSelect() {
      this.$refs.headerSearchSelect.blur();
      this.$refs.abc.blur();
      this.$refs.abcd.blur();
      this.$refs.abcde.blur();
      this.$refs.abcdef.blur();
    },
    // 重置表单人员
    ChongZhiRen() {
      this.form.peopleId = null;
      this.form.peopleId1 = null;
      this.form.peopleId2 = null;
      this.form.peopleId3 = null;
      this.form.peopleId4 = null;
    },
    // 查询请假类型
    getdict() {
      console.log("字典类型", this.dictType.dictType);
      getDicts(this.dictType.dictType).then((res) => {
        console.log("res", res);
        // res.data.splice(0, 1);
        this.ziDian = res.data;
        console.log("字典数据", this.ziDian);
      });
    },
    // 获取出行方式字典
    getTravelModeDict() {
      getDicts(this.travelModeDictType.dictType).then((res) => {
        this.travelModeDict = res.data;
        console.log("出行方式字典数据", this.travelModeDict);
      });
    },
    // 请假人选中
    qingRen(name) {
      // this.form.peopleName = name;
    },
    // 一级审批人选中
    oneRen(name) {
      this.form.peopleName1 = name;
    },
    //二级审批人选中
    twoRen(name) {
      this.form.peopleName2 = name;
    },
    // 三级审批人选中
    thereRen(name) {
      this.form.peopleName3 = name;
    },
    // 四级审批人选中
    fourRen(name) {
      this.form.peopleName4 = name;
    },
    // 获取部门列表
    getBuMeng() {
      getDept().then((res) => {
        this.deptOptions = res.data;
      });
    },
    selectPeos(data) {
      this.queryParams.unitId = data.id;
      this.ChongZhiRen();
      this.form.unitId = data.id;
      this.getYong(this.form.unitId);
      this.getRen(this.form.unitId, null);
      this.getList();
    },
    // 选择部门单位触发
    selectPeo(data) {
      this.ChongZhiRen();
      this.form.unitId = data.id;
      this.getYong(this.form.unitId);
      this.getRen(this.form.unitId, null);
    },
    // 获取用户列表
    getYong(id) {
      getUserzs(id).then((res) => {
        this.yongHu = res.rows;
      });
    },
    // 获取人员
    getRen(id, num) {
      getQuanBu(id, num).then((response) => {
        this.renYuan = response.data;
      });
    },
    /** 查询人员请假列表 */
    getList() {
      this.loading = true;
      listBdglLeave(this.queryParams).then((response) => {
        this.bdglLeaveList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open1 = false;
      this.open2 = false;
      this.opens = false;
      this.opensee = false;
      this.reset();
    },
    //审批通过按钮
    submitForms() {
      if (this.shenPis.shenPiJiBie == "一级") {
        this.form.status1 = 1;
        this.form.contents = "一级审批通过";
      } else if (this.shenPis.shenPiJiBie == "二级") {
        this.form.status2 = 1;
        this.form.contents = "二级审批通过";
      } else if (this.shenPis.shenPiJiBie == "三级") {
        this.form.status3 = 1;
        this.form.contents = "三级审批通过";
      } else if (this.shenPis.shenPiJiBie == "四级") {
        this.form.status4 = 1;
        this.form.contents = "四级审批通过";
      }
      this.form.peopleId = this.form.peopleId.join(",");
      // updateBdglLeaveShen(this.form).then((response) => {
      //   this.$modal.msgSuccess("审批成功");
      //   this.open2 = false;
      //   this.getList();
      // });
      updateBdglLeaveShen(this.form).then((res) => {
        if (res.code === 200) {
          // ---- 2. 审批通过后，调用后台 peopleInfo/update 更新门禁权限 ----
          const params = {
            UserInfo: {
              name: this.form.name, // 请假人姓名
              Valid: {
                enable: 1,
                beginTime: this.form.startTime, // 请假开始
                endTime: this.form.endTime, // 请假结束
                timeType: null,
              },
            },
          };

          request({
            url: "/peopleInfo/update",
            method: "POST",
            data: params,
          }).then(() => {
            console.log("已触发海康门禁权限更新");
          });

          // ---- 3. 最后关闭弹窗 + 刷新页面 ----
          // this.$message.success("审批完成");
          this.$modal.msgSuccess(res.msg);
          console.log("res.msg为：", res.msg);
          this.open2 = false;
          this.getList();
        }
      });
    },
    // 审批驳回按钮
    boHui() {
      this.$refs["form1"].validate((valid) => {
        if (valid) {
          if (this.shenPis.shenPiJiBie == "一级") {
            this.form.status1 = 2;
            this.form.contents = "一级审批驳回";
          } else if (this.shenPis.shenPiJiBie == "二级") {
            this.form.status2 = 2;
            this.form.contents = "二级审批驳回";
          } else if (this.shenPis.shenPiJiBie == "三级") {
            this.form.status3 = 2;
            this.form.contents = "三级审批驳回";
          } else if (this.shenPis.shenPiJiBie == "四级") {
            this.form.status4 = 2;
            this.form.contents = "四级审批驳回";
          }
          this.form.peopleId = this.form.peopleId.join(",");
          updateBdglLeaveShen(this.form).then((response) => {
            this.$modal.msgSuccess("审批成功");
            this.open2 = false;
            this.getList();
          });
        }
      });
    },
    //超级审批通过按钮
    TSsubmitForms() {
      this.$refs["forms"].validateField("specialApproval", (valid) => {
        if (!valid) {
          if (this.teShu.shenPiJiBie == "一级") {
            this.form.status1 = 3;
            this.form.common = "1";
            this.form.contents = "一级审批通过";
          } else if (this.teShu.shenPiJiBie == "二级") {
            this.form.status2 = 3;
            this.form.common = "2";
            this.form.contents = "二级审批通过";
          } else if (this.teShu.shenPiJiBie == "三级") {
            this.form.status3 = 3;
            this.form.common = "3";
            this.form.contents = "三级审批通过";
          } else if (this.teShu.shenPiJiBie == "四级") {
            this.form.status4 = 3;
            this.form.common = "4";
            this.form.contents = "四级审批通过";
          }
          if (this.form.id != null) {
            this.form.peopleId = this.form.peopleId.join(",");
            updateBdglLeaveTe(this.form).then((response) => {
              this.$modal.msgSuccess("审批成功");
              this.opens = false;
              this.getList();
            });
          }
        }
      });
    },
    // 超级审批驳回按钮
    TSboHui() {
      this.$refs["forms"].validate((valid) => {
        if (valid) {
          if (this.teShu.shenPiJiBie == "一级") {
            this.form.status1 = 2;
            this.form.common = "1";
            this.form.contents = "一级审批驳回";
          } else if (this.teShu.shenPiJiBie == "二级") {
            this.form.status2 = 2;
            this.form.common = "2";
            this.form.contents = "二级审批驳回";
          } else if (this.teShu.shenPiJiBie == "三级") {
            this.form.status3 = 2;
            this.form.common = "3";
            this.form.contents = "三级审批驳回";
          } else if (this.teShu.shenPiJiBie == "四级") {
            this.form.status4 = 2;
            this.form.common = "4";
            this.form.contents = "四级审批驳回";
          }
          if (this.form.id != null) {
            this.form.peopleId = this.form.peopleId.join(",");
            updateBdglLeaveTe(this.form).then((response) => {
              this.$modal.msgSuccess("审批成功");
              this.opens = false;
              this.getList();
            });
          }
        }
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        peopleId: null,
        unitId: null,
        startTime: null,
        endTime: null,
        leaveType: null,
        travelMode: null,
        leaveReason: null,
        returnStatus: "0",
        isReturn: null,
        updatetime: null,
        adminId: null,
        createtime: null,
        peopleId1: null,
        postId: null,
        peopleId2: null,
        peopleId3: null,
        peopleId4: null,
        status1: null,
        status2: null,
        status3: null,
        status4: null,
        contents: null,
        statusd: null,
        rejoin: null,
        carType: null,
        plateNumber: null,
        // 门闸相关字段
        doorIn: null,
        doorOut: null,
        // 车闸相关字段
        carIn: null,
        carOut: null,
        // 增加用于兼容bdgl_thebus_apply表的字段
        vehicleType: null,
      };
      this.plateNumberOptions = [];
      this.resetForm("form");
    },

    // 出行方式变化处理
    handleTravelModeChange() {
      // 当出行方式改变时，重置车辆类型、车牌号和车闸相关字段
      this.form.carType = null;
      this.form.plateNumber = null;
      this.form.carIn = null;
      this.form.carOut = null;
      this.plateNumberOptions = [];

      // 新增：重置表单校验状态
      if (this.$refs.form) {
        this.$refs.form.clearValidate([
          "doorIn",
          "doorOut",
          "carIn",
          "carOut",
          "plateNumber",
        ]);
      }
    },

    // 车辆类型变化处理
    handleCarTypeChange() {
      console.log("handleCarTypeChange", this.form.carType);
      // 当车辆类型改变时，重置车牌号
      this.form.plateNumber = null;
      this.plateNumberOptions = [];
      this.queryPlateNumber("");
      // // 如果有选择车辆类型和请假人员，可以自动触发一次搜索
      // if (this.form.carType !== null && this.form.peopleId && this.form.peopleId.length > 0) {
      //   // 可以留空查询，获取所有符合条件的车辆

      // }
    },

    // 根据人员ID获取人员姓名
    getPersonName(id) {
      const person = this.renYuan.find((item) => item.id === id);
      return person ? person.name : "";
    },

    // 司机选择变化处理
    onDriverChange(driverId) {
      if (!driverId || !this.form.peopleId || this.form.peopleId.length === 0)
        return;

      // 从请假人员列表中移除司机ID（如果存在）
      const filteredPeopleId = this.form.peopleId.filter(
        (id) => id !== driverId,
      );
      // 将司机ID放在首位，然后拼接其他人员ID
      this.form.peopleId = [driverId, ...filteredPeopleId];
    },

    // 车牌号搜索方法 - 连接bdgl_thebus_apply表
    queryPlateNumber(query) {
      // console.log('queryPlateNumber', query);
      // if (!query || ((this.form.peopleId == null || this.form.peopleId.length === 0) && this.form.carType == 0) || this.form.carType === null) {
      //   this.plateNumberOptions = [];
      //   return;
      // }

      this.plateNumberLoading = true;

      // 所有车辆信息都从bdgl_thebus_apply表获取
      // 根据vehicle_type区分车辆类型，不等于PRIVATE的为军车
      const params = {
        keyword: query,
        peopleId: "", // 请假人员ID
        vehicleType: this.form.carType == 0 ? "PRIVATE" : "", // 0表示私家车，需要过滤PRIVATE类型
        status: "通过", // 只查询已审核通过的车辆
      };

      // 调用封装好的API获取车牌号信息
      getPlateNumbers(params)
        .then((response) => {
          // 格式化返回的数据为车牌号选项
          this.plateNumberOptions = response.data.rows;
          this.plateNumberLoading = false;
        })
        .catch(() => {
          this.plateNumberLoading = false;
          this.plateNumberOptions = [];
        });
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
      this.renYuan = [];
      // 获取单位树形
      this.getBuMeng();
      // 获取字典类型
      this.getdict();
      this.reset();
      this.open = true;
      this.title = "添加人员请假";
      this.getShenRen();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      if (
        row.status1 != 0 ||
        row.status2 != 0 ||
        row.status3 != 0 ||
        row.status4 != 0
      ) {
        this.xiugai = true;
      } else if (
        row.status1 == 0 ||
        row.status2 == 0 ||
        row.status3 == 0 ||
        row.status4 == 0
      ) {
        this.xiugai = false;
      }
      this.getBuMeng();
      // 获取字典类型
      this.getdict();
      // 获取出行方式字典
      this.getTravelModeDict();
      // 获取门闸和车闸设备信息
      this.getGateDevicesData();
      this.reset();
      const id = row.id || this.ids;
      getBdglLeave(id).then((response) => {
        this.result = response.data;
        this.result.peopleId = this.result.peopleId.split(",");
        this.result.peopleId = this.result.peopleId.map(Number);
        // 确保carType和plateNumber字段存在
        this.form = {
          ...this.result,
          carType: this.result.carType || null,
          plateNumber: this.result.plateNumber || null,
          doorIn: this.result.doorIn || null,
          doorOut: this.result.doorOut || null,
          carIn: this.result.carIn || null,
          carOut: this.result.carOut || null,
          // 将司机设置为请假人员的第一个
          selectedDriver:
            this.result.peopleId.length > 0 ? this.result.peopleId[0] : null,
        };
        this.getYong(this.form.unitId);
        this.getRen(this.form.unitId);
        this.open1 = true;
        this.title = "修改人员请假";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            this.form.peopleId = this.form.peopleId.join(",");
            updateBdglLeave(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.open1 = false;
              this.getList();
            });
          } else if (this.form.common != null) {
          } else {
            if (
              this.form.peopleId2 ||
              this.form.peopleId1 ||
              this.form.peopleId3
            ) {
              if (this.form.peopleId.length > 0) {
                this.form.peopleId = this.form.peopleId.join(",");
              }
              addBdglLeave(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            } else {
              this.$modal.msgWarning("审批人必须有一个");
            }
          }
        }
      });
    },
    // 点击查看按钮触发
    handleSee(row) {
      this.oneShenPi = false;
      this.twoShenPi = false;
      this.thereShenPi = false;
      if (row.status1 == 3) {
        this.shenPiQuan.one = row.bdglLeaveFus[0].specialApproval;
        this.oneShenPi = true;
      } else if (row.status2 == 3) {
        this.shenPiQuan.two = row.bdglLeaveFus[0].specialApproval;
        this.twoShenPi = true;
      } else if (row.status3 == 3) {
        this.shenPiQuan.there = row.bdglLeaveFus[0].specialApproval;
        this.thereShenPi = true;
      }
      this.getShenAll();
      this.getShenRen();
      // 获取字典类型
      this.getdict();
      // 获取出行方式字典
      this.getTravelModeDict();
      this.reset();
      const id = row.id || this.ids;
      getBdglLeave(id).then((response) => {
        this.result = response.data;
        this.result.peopleId = this.result.peopleId.split(",");
        this.result.peopleId = this.result.peopleId.map(Number);
        // 确保carType、plateNumber和门闸车闸相关字段存在
        this.form = {
          ...this.result,
          carType: this.result.carType || null,
          plateNumber: this.result.plateNumber || null,
          doorIn: this.result.doorIn || null,
          doorOut: this.result.doorOut || null,
          carIn: this.result.carIn || null,
          carOut: this.result.carOut || null,
          // 将司机设置为请假人员的第一个
          selectedDriver:
            this.result.peopleId.length > 0 ? this.result.peopleId[0] : null,
        };
        this.getYong(this.form.unitId);
        this.getRen(this.form.unitId);
        this.opensee = true;
        this.title = "查看人员请假";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      if (!(row.rejoin == 0 && row.isReturn == "0")) {
        this.$modal
          .confirm("是否确认删除人员请假的数据项？")
          .then(function () {
            return delBdglLeave(ids);
          })
          .then(() => {
            this.getList();
            this.$modal.msgSuccess("删除成功");
          })
          .catch(() => {});
      } else {
        this.$modal.msgWarning("请将请假人员归队");
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有人员请假数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdglLeave(queryParams);
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
  border: 1px solid white !important;
}
/* 基本信息背景 */
.jiben {
  width: 920px;
  height: 32px;
  background-image: url(../../../images/小标题底.png);
  background-size: 100%;
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
  padding: 18px 50px !important;
  margin-right: 19px !important;
}
::v-deep .el-dialog__body {
  margin: 10px 0px 20px 51px;
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
  color: #333;
}
.el-select-dropdown__item {
  color: #333;
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
  background: transparent !important;
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
  margin-right: 42px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}
::v-deep .el-dialog__body {
  padding: none !important;
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
/* ::v-deep .el-ta8be__body tr.hover-row > td.el-table__cell {
  background-color: none;
} */
::v-deep .el-table .el-table__body .el-table__row.hover-row td {
  background-color: #004d86;
}
::v-deep .el-table__fixed-right {
  height: 100% !important;
}
::v-deep .box {
  background-color: #ffba00 !important;
}
::v-deep .el-input.is-disabled .el-input__inner {
  background-color: transparent;
  color: #fff;
}
.active {
  display: none;
}
::v-deep .el-select__input {
  color: #fff;
}

.ren_yuans {
  width: 200px !important;
}
</style>
