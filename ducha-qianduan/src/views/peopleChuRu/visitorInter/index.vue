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
          v-hasPermi="['peopleChuRu:bdglVisitorEnter:add']"
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
          v-hasPermi="['peopleChuRu:bdglVisitorEnter:edit']"
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
          v-hasPermi="['peopleChuRu:bdglVisitorEnter:remove']"
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
          v-hasPermi="['peopleChuRu:bdglVisitorEnter:export']"
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
      :data="visitorEnterList"
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
      <el-table-column label="访客姓名" align="center" prop="peopleName" />

      <el-table-column
        label="入所开始时间"
        align="center"
        prop="startTime"
        width="200"
      />

      <el-table-column
        label="入所结束时间"
        align="center"
        prop="endTime"
        width="200"
      />


      <el-table-column label="出行方式" align="center" prop="travelMode">
      <template slot-scope="scope">
        <dict-tag 
            :options="dict.type.travel_mode" 
            :value="scope.row.travelMode"/>
      </template>
    </el-table-column>
      <!-- 车辆类型列已隐藏 -->
    <!-- <el-table-column label="车辆类型" align="center" prop="carType">
      <template slot-scope="scope">
        <span v-if="scope.row.carType == 0">私家车</span>
        <span v-if="scope.row.carType == 1">公家车</span>
      </template>
    </el-table-column> -->
      <el-table-column label="车牌号" align="center" prop="plateNumber" width="100"/>
      <el-table-column label="入口门闸" align="center" prop="doorIn" width="100"/>
      <el-table-column label="出口门闸" align="center" prop="doorOut" width="100"/>
      <el-table-column label="入口车闸" align="center" prop="carIn" width="100"/>
      <el-table-column label="出口车闸" align="center" prop="carOut" width="100"/>
      <el-table-column
        label="来访事由"
        align="center"
        prop="leaveReason"
        width="130"
        :show-overflow-tooltip="true"
      />

      <el-table-column
        label="入所时间"
        align="center"
        prop="returnTime"
        width="200"
      />

      <el-table-column
        label="离所时间"
        align="center"
        prop="departureTime"
        width="200"
      />
      <el-table-column label="是否离所" align="center" prop="isReturn">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isReturn" />
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
        prop="status1"
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
        prop="status2"
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
        prop="status3"
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
        label="审批状态"
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
            >审批</el-button>

          <el-button
            :class="[scope.row.common == '2' ? '' : 'active']"
            size="mini"
            type="text"
            @click="handleUpdatess(scope.row)"
            v-hasPermi="['peopleChuRu:bdglVisitorEnter:teshu']"
            >特殊审批</el-button
          >
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['peopleChuRu:bdglVisitorEnter:edit']"
            >修改</el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['peopleChuRu:bdglVisitorEnter:remove']"
            >删除</el-button
          >
          <el-button
          :class="[scope.row.common == '3' ? '' : 'active']"
            size="btc"
            type="text"
            @click="handleGuiDui(scope.row)"
            v-hasPermi="['peopleChuRu:bdglVisitorEnter:edit']"
            >确认离所</el-button
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
        <div class="jiben">访客信息</div>

        <el-form-item label="单位" prop="unitId">
          <treeselect
            v-model="form.unitId"
            :options="deptOptions"
            placeholder="请选择单位"
            @select="selectPeo"
            @open="blurSelect"
          />
        </el-form-item>

        <el-form-item label="入所开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择入所开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="入所结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择入所结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>

         <el-form-item label="出行方式" prop="travelMode">
           <el-select v-model="form.travelMode" placeholder="请选择出行方式" size="small">
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
         
         <!-- 车辆类型选择框 - 仅当出行方式为自驾时显示（已隐藏） -->
         <!-- <el-form-item 
           v-if="form.travelMode === '1'" 
           label="车辆类型" 
           prop="carType"
         >
           <el-switch
             v-model="form.carType"
             active-value=1
             inactive-value=0
             active-text="公家车"
             inactive-text="私家车"
             size="small"
           />
         </el-form-item> -->
         
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
         
         <!-- 车牌号输入框 - 仅当出行方式为自驾时显示 -->
         <el-form-item 
           v-if="form.travelMode === '1'" 
           label="车牌号" 
           prop="plateNumber"
         >
           <el-select 
             v-model="form.plateNumber" 
             filterable
             remote
             :remote-method="getPlateNumbersData"
             :loading="plateNumberLoading"
             placeholder="请输入或选择车牌号" 
             size="small"
             clearable
           >
             <el-option
               v-for="item in plateNumberOptions"
               :key="item.value"
               :label="item.label"
               :value="item.value"
               class="input_xiala"
             ></el-option>
           </el-select>
         </el-form-item>
 
        <el-form-item label="负责人" prop="leaveType">
          <!-- <el-input v-model="form.leaveType" placeholder="请选择负责人" /> -->
          <el-select
            v-model="form.leaveType"
            filterable
            placeholder="请选择负责人"
            ref="abcdef"
            clearable
            @clear="peopleIdOne"
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="oneRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一级审批人" prop="peopleId1">
          <!-- <el-input v-model="form.peopleId1" placeholder="请输入一级审批人" /> -->
          <el-select
            v-model="form.peopleId1"
            filterable
            placeholder="请输入一级审批人"
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
            placeholder="请输入三级审批人"
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
        <el-form-item label="四级审批人" prop="peopleId4" style="display: none;">
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

        <el-form-item label="访客" prop="peopleId">
          <el-select
            v-model="form.peopleId"
            placeholder="请选择访客"
            ref="headerSearchSelect"
            multiple
            class="ren_yuans"
            filterable
          >
            <el-option
              v-for="(item, i) in visitors"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="qingRen(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <div class="jiben">来访事由</div>
        <el-form-item prop="leaveReason">
          <el-input
            v-model="form.leaveReason"
            type="textarea"
            placeholder="请输入来访事由"
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
        <div class="jiben">访客信息</div>

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

        <el-form-item label="出行方式" prop="travelMode">
          <el-select v-model="form.travelMode" placeholder="请选择出行方式" size="small">
            <el-option
              v-for="item in travelModeDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        
        <!-- 车辆相关信息 -->
        <!-- 车辆类型字段已隐藏 -->
        
        <el-form-item label="车牌号" prop="plateNumber">
          <el-input v-model="form.plateNumber" placeholder="请输入车牌号" size="small" />
        </el-form-item>
        
        <el-form-item label="入口门闸" prop="doorIn">
          <el-select v-model="form.doorIn" placeholder="请选择入口门闸" size="small">
            <el-option
              v-for="item in entryGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="出口门闸" prop="doorOut">
          <el-select v-model="form.doorOut" placeholder="请选择出口门闸" size="small">
            <el-option
              v-for="item in exitGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="入口车闸" prop="carIn">
          <el-select v-model="form.carIn" placeholder="请选择入口车闸" size="small">
            <el-option
              v-for="item in entryBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="出口车闸" prop="carOut">
          <el-select v-model="form.carOut" placeholder="请选择出口车闸" size="small">
            <el-option
              v-for="item in exitBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="负责人" prop="leaveType">
          <!-- <el-input v-model="form.leaveType" placeholder="请选择负责人" /> -->
          <el-select
            v-model="form.leaveType"
            filterable
            placeholder="请选择负责人"
            ref="abcdef"
            clearable
            @clear="peopleIdOne"
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="oneRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一级审批人" prop="peopleId1">
          <!-- <el-input v-model="form.peopleId1" placeholder="请输入一级审批人" /> -->
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
              v-for="(item, i) in yingJiRen1"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="thereRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="四级审批人" prop="peopleId4"  style="display: none;">
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

        <el-form-item label="访客" prop="peopleId">
          <!-- <el-input v-model="form.peopleId" placeholder="请选择访客" /> -->
          <el-select
            v-model="form.peopleId"
            placeholder="请选择访客"
            ref="headerSearchSelect"
            :disabled="xiugai"
            multiple
            class="ren_yuans"
          >
            <el-option
              v-for="(item, i) in visitors"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="qingRen(item.name)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <div class="jiben">来访事由</div>
        <el-form-item prop="leaveReason">
          <el-input
            v-model="form.leaveReason"
            type="textarea"
            placeholder="请输入来访事由"
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

        <el-form-item label="入所开始时间" prop="startTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.startTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择入所开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="入所结束时间" prop="endTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.endTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择入所结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item label="出行方式" prop="travelMode">
          <el-select v-model="form.travelMode" placeholder="请选择出行方式" disabled size="small">
            <el-option
              v-for="item in travelModeDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        
        <!-- 车辆相关信息 -->
        <!-- 车辆类型字段已隐藏 -->
        
        <el-form-item label="车牌号" prop="plateNumber">
          <el-input v-model="form.plateNumber" placeholder="请输入车牌号" disabled size="small" />
        </el-form-item>
        
        <el-form-item label="入口门闸" prop="doorIn">
          <el-select v-model="form.doorIn" placeholder="请选择入口门闸" disabled size="small">
            <el-option
              v-for="item in entryGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="出口门闸" prop="doorOut">
          <el-select v-model="form.doorOut" placeholder="请选择出口门闸" disabled size="small">
            <el-option
              v-for="item in exitGateDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="入口车闸" prop="carIn">
          <el-select v-model="form.carIn" placeholder="请选择入口车闸" disabled size="small">
            <el-option
              v-for="item in entryBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="出口车闸" prop="carOut">
          <el-select v-model="form.carOut" placeholder="请选择出口车闸" disabled size="small">
            <el-option
              v-for="item in exitBarrierDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
          <el-form-item label="入所时间" prop="returnTime">
            <!-- <el-input v-model="form.returnTime" placeholder="请输入入所时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择入所时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="离所时间" prop="departureTime">
          <!-- <el-input v-model="form.returnTime" placeholder="请输入入所时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择归队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="一级审批人" prop="peopleId1">
          <!-- <el-input v-model="form.peopleId1" placeholder="请输入一级审批人" /> -->
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
              v-for="(item, i) in yingJiRen1"
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
              v-for="(item, i) in yingJiRen1"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="thereRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="四级审批人" prop="peopleId4"  style="display: none;">
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
        <el-form-item label="负责人" prop="leaveType">
          <el-select
            v-model="form.leaveType"
            placeholder="请选择负责人"
            filterable
            clearable
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="oneRen(item.nickName)"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出行方式" prop="travelMode">
          <el-select v-model="form.travelMode" placeholder="请选择出行方式" disabled size="small">
            <el-option
              v-for="item in travelModeDict"
              :key="item.dictCode"
              :label="item.dictLabel"
              :value="item.dictValue"
              class="input_xiala"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入所时间" prop="returnTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择入所时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="离所时间" prop="departureTime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择归队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
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
              v-for="(item, i) in yingJiRen1"
              :key="i"
              :label="item.nickName"
              :value="item.userId"
              @click.native="thereRen(item.nickName)"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="四级审批人" prop="peopleId4"  style="display: none;">
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
        <el-form-item label="出行方式" prop="travelMode">
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
        <el-form-item label="入所时间" prop="returnTime">
          <!-- <el-input v-model="form.returnTime" placeholder="请输入入所时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择入所时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="离所时间" prop="departureTime">
          <!-- <el-input v-model="form.returnTime" placeholder="请输入入所时间" /> -->
          <el-date-picker
            clearable
            size="small"
            v-model="form.returnTime"
            type="datetime"
            default-time="00:00:00"
            placeholder="选择归队时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="负责人" prop="leaveType">
          <!-- <el-input v-model="form.leaveType" placeholder="请输入负责人" /> -->
          <el-select
            v-model="form.leaveType"
            filterable
            placeholder="请输入负责人"
            disabled
          >
            <el-option
              v-for="(item, i) in renYuan"
              :key="i"
              :label="item.name"
              :value="item.id"
              @click.native="oneRen(item.nickName)"
              class="input_xiala"
            >
            </el-option>
          </el-select>
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
            style = "width: 100%;"
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
  listBdglVisitorEnter,
  getBdglVisitorEnter,
  delBdglVisitorEnter,
  addBdglVisitorEnter,
  updateBdglVisitorEnter,
  exportBdglVisitorEnter,
  getBdglList,
  getAllDept,
  getAll,
  updateBdglVisitorEnterShen,
  updateBdglVisitorEnterTe,
  YingJIUSer,
  LIanJIUSer,
  ZhengZhiUSer,
  getVisitorList,
  getPlateNumbers,
  getGateDevices
} from "@/api/peopleChuRu/bdglVisitorEnter";

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
export default {
  name: "BdglLeave",
  components: {
    Treeselect,
  },
  dicts: ["Leave_type", "sys_yes_no", "vocation_state", "travel_mode"],
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
      // 访客入所申请表格数据
      visitorEnterList: [],
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
        startTime: null,
        endTime: null,
        travelMode:null,
        leaveReason: null,
        departureTime: null,
        returnTime: null,
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
      // 审批人数据源
      lianJiRen: [],
      yingJiRen: [],
      zhengZhiRen: [],
      yongHu: [],
      fuZeRen: [], // 负责人数据源
      // 表单参数
      form: {
        doorIn: null, // 入口门闸
        doorOut: null, // 出口门闸
        carType: "0", // 车辆类型(0:私家车，1:公家车)
        carIn: null, // 入口车闸
        carOut: null, // 出口车闸
        plateNumber: null // 车牌号
      },
      // 门闸相关字典
      gateDict: [],
      entryGateDict: [],
      exitGateDict: [],
      entryBarrierDict: [],
      exitBarrierDict: [],
      // 车牌号相关
      plateNumberOptions: [], // 车牌号列表
      plateNumberLoading: false, // 车牌号加载状态
      // 表单校验
      rules: {
        unitId: [{ required: true, message: "单位不能为空", trigger: "blur" }],
        peopleId: [
          { required: true, message: "人员不能为空", trigger: "blur" },
        ],
        travelMode: [
          { required: true, message: "出行方式不能为空", trigger: "blur" },
        ],
        doorIn: [{ required: true, message: "入口门闸不能为空", trigger: "blur" }],
        doorOut: [{ required: true, message: "出口门闸不能为空", trigger: "blur" }],
        // 车闸相关字段（当选择自驾时必填）
        carIn: [{ required: false, message: "入口车闸不能为空", trigger: "blur" }],
        carOut: [{ required: false, message: "出口车闸不能为空", trigger: "blur" }],
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

        travelMode: [
          { required: true, message: "出行方式不能为空", trigger: "blur" },
        ],
        doorIn: [{ required: true, message: "入口门闸不能为空", trigger: "blur" }],
        doorOut: [{ required: true, message: "出口门闸不能为空", trigger: "blur" }],
        // 车闸相关字段（当选择自驾时必填）
        carIn: [{ required: false, message: "入口车闸不能为空", trigger: "blur" }],
        carOut: [{ required: false, message: "出口车闸不能为空", trigger: "blur" }],
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
      // 访客列表
      visitors: [],
      // 出行方式字典类型
      travelModeDictType: {
        dictType: "travel_mode",
      },
      // 出行方式字典列表
      travelModeDict: [],
      // 门闸和车闸字典
      entryGateDict: [],
      exitGateDict: [],
      entryBarrierDict: [],
      exitBarrierDict: [],
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
    this.getList();
    // 字典查询数据
    this.getDicts("approve").then((response) => {
      this.ziDians = response.data;
    });
    // 获取出行方式字典
    this.getTravelModeDict();
    // 获取门闸设备信息
    this.getGateDevicesData();
    // 获取各级审批人数据源
    this.getYing();
    this.getLiai();
    this.getZheng();
    this.getSuoYou();
    this.getBuMeng();
    // 获取访客选项数据
    this.getVisitorOptions();
  },
  methods: {
    // 获取所有审批人员
    getShenRen() {
      getAll().then((res) => {
        this.shenAllRenList = res.rows;
        console.log("获取所有审批人员", this.shenAllRenList)
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

    // 营级人员（二级审批人）
    getYing() {
      YingJIUSer().then((res) => {
        this.yingJiRen = res.data;
      });
    },
    // 连级人员（一级审批人和负责人）
    getLiai() {
      LIanJIUSer().then((res) => {
        this.lianJiRen = res.data;
        this.fuZeRen = res.data; // 负责人使用连级人员数据
      });
    },
    // 政治人员（三级审批人）
    getZheng() {
      ZhengZhiUSer().then((res) => {
        this.zhengZhiRen = res.data;
      });
    },
    // 审批人所有
    getSuoYou() {
      getBdglList().then((res) => {
        this.yingJiRen1 = res.rows;
        // 如果其他API调用失败，可以用这个作为备选
        if (!this.lianJiRen || this.lianJiRen.length === 0) {
          this.lianJiRen = res.rows;
          this.fuZeRen = res.rows; // 负责人使用连级人员数据作为备选
        }
        if (!this.yingJiRen || this.yingJiRen.length === 0) {
          this.yingJiRen = res.rows;
        }
        if (!this.zhengZhiRen || this.zhengZhiRen.length === 0) {
          this.zhengZhiRen = res.rows;
        }
        if (!this.yongHu || this.yongHu.length === 0) {
          this.yongHu = res.rows;
        }
      });
    },

    // 确认离所按钮
    handleGuiDui(row) {
      // 先清空renYuan数组，避免数据混淆
      this.renYuan = [];
      const id = row.id || this.ids;
      getBdglVisitorEnter(id).then((response) => {
        this.form = response.data;
        
        // 提前获取单位数据和人员信息
        this.getYong(this.form.unitId);
        
        // 确保getRen方法返回Promise并等待数据加载完成
        Promise.resolve(this.getRen(this.form.unitId,0)).then(() => {
          // 数据加载完成后再显示确认对话框
          this.$modal
            .confirm("是否确认访客离所？")
            .then(() => {
              if (this.form.id != null) {
                this.form.rejoin = "1";
                updateBdglVisitorEnter(this.form).then((response) => {
                  this.$modal.msgSuccess("确认离所成功");
                  this.getList();
                });
              }
            })
            .catch(() => {});
        });
        
        // 使用setTimeout确保DOM已更新，然后刷新select组件
        setTimeout(() => {
          // 移除updateKeyDownState调用，避免类型错误
            // 如果需要刷新select组件，可以通过其他方式实现，如重新设置值或使用组件提供的refresh方法
        }, 0);
      });
    },
    // // 特殊审批按钮触发
    handleUpdatess(row) {
      this.teShu = row;
      this.getShenAll();
      this.getShenRen();
      // 获取字典类型

      // 获取出行方式字典
      this.getTravelModeDict();
      this.reset();
      // 先清空renYuan数组，避免数据混淆
      this.renYuan = [];
      const id = row.id || this.ids;
      getBdglVisitorEnter(id).then((response) => {
        this.result = response.data;
        this.result.peopleId = this.result.peopleId.split(",");
        this.result.peopleId = this.result.peopleId.map(Number);
        this.form = response.data;
        this.form.specialApproval = null;
        
        // 提前获取单位数据
        this.getYong(this.form.unitId);
        
        // 确保getRen方法返回Promise并等待数据加载完成
        Promise.resolve(this.getRen(this.form.unitId,0)).then(() => {
          // 使用$nextTick确保DOM在下一次更新循环中刷新
          this.$nextTick(() => {
            // 强制更新视图，确保el-select组件能够正确显示最新选项
            this.$forceUpdate();
            // 数据加载完成后再显示表单
            this.opens = true;
            this.title = "审批人员请假";
          });
          
          // 使用setTimeout确保DOM已更新，然后刷新select组件
          setTimeout(() => {
            // 移除updateKeyDownState调用，避免类型错误
            // 如果需要刷新select组件，可以通过其他方式实现，如重新设置值或使用组件提供的refresh方法
          }, 0);
        });
      });
    },
    // 审批按钮操作
    handleUpdates(row) {
      this.shenPis = row;
      this.getShenAll();
      this.getShenRen();
      // 获取字典类型
      // 请假类型字典已移除
      // 获取出行方式字典
      this.getTravelModeDict();
      this.reset();
      // 先清空renYuan数组，避免数据混淆
      this.renYuan = [];
      const id = row.id || this.ids;
      getBdglVisitorEnter(id).then((response) => {
        this.result = response.data;
        this.result.peopleId = this.result.peopleId.split(",");
        this.result.peopleId = this.result.peopleId.map(Number);
        this.form = response.data;
        
        // 提前获取单位数据
        this.getYong(this.form.unitId);
        
        // 确保getRen方法返回Promise并等待数据加载完成
        Promise.resolve(this.getRen(this.form.unitId,0)).then(() => {
          // 使用$nextTick确保DOM在下一次更新循环中刷新
          this.$nextTick(() => {
            // 强制更新视图，确保el-select组件能够正确显示最新选项
            this.$forceUpdate();
            // 数据加载完成后再显示表单
            this.open2 = true;
            this.title = "审批人员请假";
          });
          
          // 使用setTimeout确保DOM已更新，然后刷新select组件
          setTimeout(() => {
            // 移除updateKeyDownState调用，避免类型错误
            // 如果需要刷新select组件，可以通过其他方式实现，如重新设置值或使用组件提供的refresh方法
          }, 0);
        });
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

    // 获取出行方式字典
    getTravelModeDict() {
      getDicts(this.travelModeDictType.dictType).then((res) => {
        this.travelModeDict = res.data;
        console.log("出行方式字典数据", this.travelModeDict);
      });
    },
    // 获取门闸设备信息
    getGateDevicesData() {
      // 获取入口门闸
      getGateDevices('door_in').then(res => {
        if (res.code === 200) {
          // 转换后端数据格式为前端需要的格式
          this.entryGateDict = res.data.map(device => ({
            dictCode: device.id,
            dictLabel: device.name || device.deviceName || '未知门闸',
            dictValue: device.id
          }));
          // 保持兼容性
          this.gateDict = this.entryGateDict;
        }
      }).catch(error => {
        console.error('获取入口门闸失败:', error);
      });
      
      // 获取出口门闸
      getGateDevices('door_out').then(res => {
        if (res.code === 200) {
          this.exitGateDict = res.data.map(device => ({
            dictCode: device.id,
            dictLabel: device.name || device.deviceName || '未知门闸',
            dictValue: device.id
          }));
        }
      }).catch(error => {
        console.error('获取出口门闸失败:', error);
      });
      
      // 获取入口车闸
      getGateDevices('car_in').then(res => {
        if (res.code === 200) {
          this.entryBarrierDict = res.data.map(device => ({
            dictCode: device.id,
            dictLabel: device.name || device.deviceName || '未知车闸',
            dictValue: device.id
          }));
        }
      }).catch(error => {
        console.error('获取入口车闸失败:', error);
      });
      
      // 获取出口车闸
      getGateDevices('car_out').then(res => {
        if (res.code === 200) {
          this.exitBarrierDict = res.data.map(device => ({
            dictCode: device.id,
            dictLabel: device.name || device.deviceName || '未知车闸',
            dictValue: device.id
          }));
        }
      }).catch(error => {
        console.error('获取出口车闸失败:', error);
      });
    },
    // 获取车牌号列表
    getPlateNumbersData(query) {
      this.plateNumberLoading = true;
      const params = {
        plateNumber: query // 将搜索词作为车牌号查询参数
      };
      getPlateNumbers(params).then((response) => {
        this.plateNumberOptions = response.rows.map(item => ({
          label: item.plateNumber,
          value: item.plateNumber
        }));
        this.plateNumberLoading = false;
      }).catch((error) => {
        console.error('获取车牌号失败:', error);
        this.plateNumberLoading = false;
      });
    },
    // 访客选中
    qingRen(name) {
      this.form.peopleName = name;
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
      this.getRen(this.form.unitId, 0);
      this.getList();
    },
    // 选择部门单位触发
    selectPeo(data) {
      this.ChongZhiRen();
      this.form.unitId = data.id;
      this.getYong(this.form.unitId);
      this.getRen(this.form.unitId, 0);
    },
    // 获取用户列表
    getYong(id) {
      getUserzs(id).then((res) => {
        this.yongHu = res.rows;
      });
    },
    // 获取人员
    getRen(id, num) {
      return new Promise((resolve) => {
        getQuanBu(id, num).then((response) => {
          // 使用this.$set方法来确保Vue能够检测到renYuan数组的变化
          // 先清空数组
          this.renYuan.splice(0, this.renYuan.length);
          // 然后使用this.$set逐个添加元素
          if (response.data && Array.isArray(response.data)) {
            response.data.forEach((item, index) => {
              this.$set(this.renYuan, index, item);
            });
          } else {
            // 如果没有数据，确保renYuan是一个空数组
            this.renYuan = [];
          }
          console.log("获取人员数据", this.renYuan);
          resolve();
        });
      });
    },
    // 新方法：获取访客选项列表（直接调用访客管理接口）
    getVisitorOptions() {
      // 调用getVisitorList方法获取访客列表
      getVisitorList({}).then((response) => {
        console.log('访客列表数据已加载:', response);
        // 适配接口返回的数据格式
        if (response.rows && Array.isArray(response.rows)) {
          // 为访客选项设置正确的数据格式
          this.visitors = response.rows.map(item => ({
            id: item.id,
            name: item.name || item.visitorName || '未命名访客'
          }));
          console.log('访客选项数据已加载:', this.visitors);
        } else {
          this.visitors = [];
          console.log('未获取到访客数据');
        }
      }).catch(error => {
        console.error('获取访客数据失败:', error);
        this.visitors = [];
      });
    },
    /** 查询访客入所申请列表 */
    getList() {
      this.loading = true;
      listBdglVisitorEnter(this.queryParams).then((response) => {
        //将leaveType由字符串转为数字
        response.rows.forEach((item) => {
          item.leaveType = parseInt(item.leaveType)
        });

        this.visitorEnterList = response.rows;

        console.log("访客入所申请列表数据", this.visitorEnterList);
        
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
      
      // 日期格式转换
      if (this.form.startTime) {
        this.form.startTime = this.formatDate(this.form.startTime);
      }
      if (this.form.endTime) {
        this.form.endTime = this.formatDate(this.form.endTime);
      }
      
      this.form.peopleId = this.form.peopleId.join(",");
      updateBdglVisitorEnterShen(this.form).then((response) => {
        this.$modal.msgSuccess("审批成功");
        this.open2 = false;
        this.getList();
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
              // 日期格式转换
          if (this.form.startTime) {
            this.form.startTime = this.formatDate(this.form.startTime);
          }
          if (this.form.endTime) {
            this.form.endTime = this.formatDate(this.form.endTime);
          }
          
          updateBdglVisitorEnterShen(this.form).then((response) => {
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
          
          // 日期格式转换
          if (this.form.startTime) {
            this.form.startTime = this.formatDate(this.form.startTime);
          }
          if (this.form.endTime) {
            this.form.endTime = this.formatDate(this.form.endTime);
          }
          
          if (this.form.id != null) {
            this.form.peopleId = this.form.peopleId.join(",");
            updateBdglVisitorEnterTe(this.form).then((response) => {
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
            updateBdglVisitorEnterTe(this.form).then((response) => {
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
        carType: "0", // 车辆类型默认值：私家车
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
      this.renYuan = [];
      // 获取单位树形
      this.getBuMeng();
      // 获取字典类型
      // 请假类型字典已移除
      this.reset();
      this.open = true;
      this.title = "添加访客入所申请";
      this.getShenRen()
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
      // 请假类型字典已移除
      this.reset();
      // 先清空renYuan数组，避免数据混淆
      this.renYuan = [];
      const id = row.id || this.ids;
      getBdglVisitorEnter(id).then((response) => {
        this.result = response.data;
        this.result.peopleId = this.result.peopleId.split(",");
        this.result.peopleId = this.result.peopleId.map(Number);
        this.form = this.result;
        
        // 提前获取单位数据
        this.getYong(this.form.unitId);
        this.open1 = true;
        this.title = "修改访客入所申请";
        // 确保getRen方法返回Promise并等待数据加载完成
        Promise.resolve(this.getRen(this.form.unitId,0)).then(() => {
          // 使用$nextTick确保DOM在下一次更新循环中刷新
          this.$nextTick(() => {
            this.$forceUpdate();
            // 再添加一个短暂的延迟，确保DOM完全更新
            setTimeout(() => {
              // 数据加载完成后再显示表单
              this.open1 = true;
              this.title = "修改访客入所申请";
            }, 0);
          });
          
          // 使用setTimeout确保DOM已更新，然后刷新select组件
          setTimeout(() => {
            // 移除updateKeyDownState调用，避免类型错误
            // 如果需要刷新select组件，可以通过其他方式实现，如重新设置值或使用组件提供的refresh方法
          }, 0);
        });
      });
    },
    // 日期格式转换方法
    formatDate(date) {
      if (typeof date === 'string') {
        // 如果已经是字符串，尝试解析为日期对象
        date = new Date(date);
      }
      if (date instanceof Date && !isNaN(date.getTime())) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      }
      return date;
    },
    
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          // 日期格式转换
          if (this.form.startTime) {
            this.form.startTime = this.formatDate(this.form.startTime);
          }
          if (this.form.endTime) {
            this.form.endTime = this.formatDate(this.form.endTime);
          }

          
          if (this.form.id != null) {
            this.form.peopleId = this.form.peopleId.join(",");
            updateBdglVisitorEnter(this.form).then((response) => {
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
              addBdglVisitorEnter(this.form).then((response) => {
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
        this.shenPiQuan.one = row.visitorEnterFus[0].specialApproval;
        this.oneShenPi = true;
      } else if (row.status2 == 3) {
        this.shenPiQuan.two = row.visitorEnterFus[0].specialApproval;
        this.twoShenPi = true;
      } else if (row.status3 == 3) {
        this.shenPiQuan.there = row.visitorEnterFus[0].specialApproval;
        this.thereShenPi = true;
      }
      this.getShenAll();
      this.getShenRen();
      // 获取字典类型
      // 请假类型字典已移除
      this.reset();
      // 先清空renYuan数组，避免数据混淆
      this.renYuan = [];
      
      // 提前获取单位数据和人员信息
      const id = row.id || this.ids;
      getBdglVisitorEnter(id).then((response) => {
        this.result = response.data;
        this.result.peopleId = this.result.peopleId.split(",");
        this.result.peopleId = this.result.peopleId.map(Number);
        this.form = this.result;
        
        // 提前获取单位数据
        this.getYong(this.form.unitId);
        
        // 确保getRen方法返回Promise并等待数据加载完成
        Promise.resolve(this.getRen(this.form.unitId,0)).then(() => {
          // 使用$nextTick确保DOM在下一次更新循环中刷新
          this.$nextTick(() => {
            // 强制更新视图，确保el-select组件能够正确显示最新选项
            this.$forceUpdate();
            // 数据加载完成后再显示表单
            this.opensee = true;
            this.title = "查看访客入所申请";
          });
          
          // 使用setTimeout确保DOM已更新，然后刷新select组件
          setTimeout(() => {
            // 移除updateKeyDownState调用，避免类型错误
            // 如果需要刷新select组件，可以通过其他方式实现，如重新设置值或使用组件提供的refresh方法
          }, 0);
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      if (!(row.rejoin == 0 && row.isReturn == "0")) {
        this.$modal
          .confirm("是否确认删除访客入所申请的数据项？")
          .then(function () {
            return delBdglVisitorEnter(ids);
          })
          .then(() => {
            this.getList();
            this.$modal.msgSuccess("删除成功");
          })
          .catch(() => {});
      } else {
        this.$modal.msgWarning("请将请假访客出队");
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有访客入所申请数据项？")
          .then(() => {
            this.exportLoading = true;
            return exportBdglVisitorEnter(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
  },
  // 监听出行方式变化，动态设置车辆字段必填
  watch: {
    'form.travelMode': function(newVal) {
      if (newVal === '1') { // 假设1代表自驾
        // 当选择自驾时，车辆相关字段变为必填
        this.rules.carIn[0].required = true;
        this.rules.carOut[0].required = true;
        this.rules.plateNumber[0].required = true;
      } else {
        // 当选择非自驾时，车辆相关字段变为非必填
        this.rules.carIn[0].required = false;
        this.rules.carOut[0].required = false;
        this.rules.plateNumber[0].required = false;
      }
    }
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
::v-deep .el-select__input{
  color:#fff
}
.ren_yuans{
  width: 200px !important;
}
</style>
