<template>
  <div class="app-container">
    <div class="box-lfet">
      <div class="data">
        <p>日期</p>
        <div class="a1"></div>
        <el-date-picker
          v-model="tiem"
          type="date"
          :editable="false"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
          @change="shiJian"
        >
        </el-date-picker>
      </div>
      <div class="dadui">
        <img src="../../../images/星星.png" alt="" />
        单位
      </div>
      <div class="tree">
        <el-tree
          class="userAgrees"
          style="height: calc(100vh - 230px); overflow: auto"
          :data="deptOptions"
          :props="defaultProps"
          @node-click="handleNodeClick"
          default-expand-all
          highlight-current
          :expand-on-click-node="false"
        ></el-tree>
      </div>
    </div>

    <div class="box-right">
      <!-- 搜索条件区域 -->
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
      >
      </el-form>
      <!-- 添加，删除 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['grassrootsregistration:bdgldiary:add']"
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
            v-hasPermi="['grassrootsregistration:inspect:export']"
            >导出</el-button
          >
        </el-col>
        <div class="shuJu" @click="dengJI">
          <span class="box44 public"> 当前数据为 : {{ tiems }}</span>
          <span class="box5 public">
            已登记部门 : {{ registrationData.yiShenPi }}</span
          >
          <span class="box6">
            未登记部门 : {{ registrationData.weiShenPi }}</span
          >
        </div>
        <!-- <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['grassrootsregistration:bdgldiary:edit']"
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
            v-hasPermi="['grassrootsregistration:bdgldiary:remove']"
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
            v-hasPermi="['grassrootsregistration:bdgldiary:export']"
            >导出</el-button
          >
        </el-col> -->
      </el-row>

      <!-- 搜索结果区域 -->
      <el-table
        v-loading="loading"
        :data="bdgldiaryList"
        @selection-change="handleSelectionChange"
        :header-cell-style="{ background: '#003C69', color: 'white' }"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" type="index" width="50" align="center">
          <template scope="scope">
            <span>{{
              (queryParams.pageNum - 1) * queryParams.pageSize +
              scope.$index +
              1
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="单位" align="center" prop="unitName" />
        <el-table-column label="值班人员" align="center" prop="peopleName" />
        <el-table-column
          label="值班日期"
          align="center"
          prop="diaryTime"
          width="180"
          :editable="false"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.diaryTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column label="编制干部" align="center" prop="orgcadre" />
        <el-table-column label="编制战士" align="center" prop="orgsoldier" />
        <el-table-column label="编制内其他" align="center" prop="orgother" />
        <el-table-column label="编制文职" align="center" prop="orgcivilian" />
        <el-table-column label="查铺查哨" align="center" prop="inspect">
          <template slot-scope="scope">
            <span
              style="color: #67c23a"
              v-if="scope.row.bdglDailyInspectLen != 0"
              >登记</span
            >
            <span
              style="color: #ff4949"
              v-if="scope.row.bdglDailyInspectLen == 0"
              >未登记</span
            >
          </template>
        </el-table-column>
        <el-table-column label="审批" align="center" prop="examine">
          <template slot-scope="scope">
            <span v-if="scope.row.examine == '1'" style="color: #67c23a"
              >已审批</span
            >
            <span v-else style="color: #ff4949">未审批</span>
          </template>
        </el-table-column>
        <el-table-column
          width="250"
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.common == '1'"
              size="btd"
              type="text"
              @click="handleChakans(scope.row)"
              v-hasPermi="['grassrootsregistration:bdgldiary:edit']"
              ><span class="chakan">审批</span></el-button
            >
            <el-button
              size="btk"
              type="text"
              @click="handleChakan(scope.row)"
              v-hasPermi="['grassrootsregistration:bdgldiary:query']"
              ><span class="chakan">查看</span></el-button
            >
            <el-button
              size="btu"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['grassrootsregistration:bdgldiary:edit']"
              ><span class="edit">修改</span></el-button
            >
            <el-button
              size="btd"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['grassrootsregistration:bdgldiary:remove']"
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

      <!-- 添加或修改要事日记对话框 -->

      <el-dialog
        :title="title"
        :visible.sync="open"
        width="1060px"
        append-to-body
        :close-on-click-modal="false"
        custom-class="userDig"
      >
        <div class="userAgree">
          <el-form
            ref="form"
            :model="form"
            :rules="rules"
            label-width="80px"
            :inline="true"
          >
            <div class="jiben">基本信息</div>
            <el-form-item label="单位" prop="unitId">
              <treeselect
                @select="selectPeo"
                v-model="form.unitId"
                :options="rese"
                placeholder="请选择单位"
              />
            </el-form-item>

            <el-form-item label="值班人员" prop="peopleId">
              <el-select
                v-model="form.peopleName"
                placeholder="请输入单位"
                filterable
                clearable
                @change="btn"
              >
                <el-option
                  v-for="(item, i) in renYuan"
                  :key="i"
                  :label="item.name"
                  :value="item"
                  class="input_xiala"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="值班日期" prop="blogDate">
              <el-date-picker
                clearable
                size="small"
                v-model="form.blogDate"
                type="datetime"
                placeholder="选择值班日期"
                class="text"
                @change="blogDate"
                value-format="yyyy-MM-dd HH:mm:ss"
                :disabled="!statustimer"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item label="天气" prop="weather">
              <el-input v-model="form.weather" placeholder="请输入天气" />
            </el-form-item>
            <el-form-item label="值班首长" prop="lead">
              <el-select
                v-model="form.lead"
                filterable
                placeholder="请选择首长"
                clearable
              >
                <el-option
                  :label="item.nickName"
                  v-for="(item, i) in shouZhang"
                  :key="i"
                  :value="item.userId"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
            <div class="jiben">实力情况</div>
            <el-form-item label="编制干部" prop="orgcadre">
              <el-input-number
                v-model="form.orgcadre"
                controls-position="right"
                :min="0"
                :max="99999999"
              >
              </el-input-number>
            </el-form-item>
            <el-form-item label="编制战士" prop="orgsoldier">
              <!-- <el-input v-model="form.orgsoldier" placeholder="请输入编制战士" /> -->
              <el-input-number
                v-model="form.orgsoldier"
                controls-position="right"
                :min="0"
                :max="99999999"
              >
              </el-input-number>
            </el-form-item>
            <el-form-item label="编制内其他" prop="orgother">
              <el-input-number
                v-model="form.orgother"
                controls-position="right"
                :min="0"
                :max="99999999"
              >
              </el-input-number>
            </el-form-item>
            <el-form-item label="编制文职" prop="orgcivilian">
              <!-- <el-input v-model="form.orgcivilian" placeholder="请输入编制文职" /> -->
              <el-input-number
                v-model="form.orgcivilian"
                controls-position="right"
                :min="0"
                :max="99999999"
              >
              </el-input-number>
            </el-form-item>
            <el-form-item label="现有干部" prop="nowcadre">
              <!-- <el-input v-model="form.nowcadre" placeholder="请输入现有干部" /> -->
              <el-input-number
                v-model="form.nowcadre"
                controls-position="right"
                :min="0"
                :max="99999999"
              >
              </el-input-number>
            </el-form-item>
            <el-form-item label="现有战士" prop="nowsoldier">
              <el-input-number
                v-model="form.nowsoldier"
                controls-position="right"
                :min="0"
                :max="99999999"
              >
              </el-input-number>
            </el-form-item>
            <el-form-item label="现有文职" prop="nowcivilian">
              <!-- <el-input v-model="form.nowcivilian" placeholder="请输入现有文职" /> -->
              <el-input-number
                v-model="form.nowcivilian"
                controls-position="right"
                :min="0"
                :max="99999999"
              >
              </el-input-number>
            </el-form-item>
            <div class="jiben">训练、教育或者执行其他任务情况</div>
            <div class="box" v-for="(item, i) in xunlian" :key="i">
              <table
                width="850"
                cellspacing="0"
                align="center"
                border="1"
                style="
                  border-bottom: white;
                  border-collapse: collapse;
                  mso-border-left-alt: 0.5pt solid windowtext;
                  mso-border-top-alt: 0.5pt solid windowtext;
                  mso-border-right-alt: 0.5pt solid windowtext;
                  mso-border-bottom-alt: 0.5pt solid windowtext;
                  mso-border-insideh: 0.5pt solid windowtext;
                  mso-border-insidev: 0.5pt solid windowtext;
                  mso-padding-alt: 0pt 0pt 0pt 0pt;
                  margin-left: 61px;
                "
                bordercolor="white"
              >
                <tr align="center">
                  <td width="200" height="40">区分</td>
                  <td colspan="2" width="420">内容</td>
                  <td width="100">应到人数</td>
                  <td width="100">实到人数</td>
                  <td width="100">到课率</td>
                </tr>
                <tr align="center">
                  <td height="60">
                    <el-input v-model="item.dateline" placeholder=""></el-input>
                  </td>
                  <td rowspan="1" colspan="2">
                    <el-input
                      type="textarea"
                      :rows="1"
                      placeholder="请输入内容"
                      v-model="item.contents"
                      :autosize="true"
                      resize="none"
                    >
                    </el-input>
                  </td>
                  <td>
                    <el-input
                      v-model="item.shouldArrive"
                      placeholder="应到人数"
                      @blur="yinDao"
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model="item.realTo"
                      placeholder="实到人数"
                      @blur="shiDao"
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model="item.percentage"
                      placeholder="到课率"
                      readonly
                    ></el-input>
                  </td>
                </tr>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  v-if="i > 0"
                  @click="tebleDelForms(i)"
                >
                </el-button>
              </table>
            </div>
            <!-- 工作登记 -->
            <div class="box1">
              <el-form
                ref="form"
                :model="tomList"
                :rules="rules"
                label-width="80px"
                :inline="true"
              >
                <el-form-item label="" prop="day">
                  <el-select
                    v-model="tomList.wook"
                    @change="datelinefun"
                    placeholder="早晨"
                  >
                    <el-option
                      v-for="item in day"
                      :key="item.value"
                      :label="item.label"
                      :value="item.label"
                      class="input_xiala"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>

                <el-time-select
                  class="b1"
                  placeholder="起始时间"
                  v-model="tomList.startTime"
                  :picker-options="option"
                  style="margin-right: 22px"
                >
                </el-time-select>
                <el-time-select
                  class="b1"
                  placeholder="结束时间"
                  v-model="tomList.endTime"
                  :picker-options="option"
                  style="margin-right: 22px"
                >
                </el-time-select>
                <div class="box2">
                  <el-form-item label="" prop="organizer">
                    <el-input
                      v-model="tomList.organizer"
                      placeholder="组织人"
                      clearable
                      size="small"
                    />
                  </el-form-item>
                  <!-- 工作登记-添加按钮 -->
                  <div class="btn">
                    <el-button
                      type="primary"
                      icon="el-icon-check"
                      @click="tijiao"
                    ></el-button>
                  </div>
                </div>
                <el-form-item label="" prop="content">
                  <el-input
                    type="textarea"
                    v-model="tomList.contents"
                    placeholder="内容"
                    clearable
                    size="small"
                  />
                </el-form-item>
              </el-form>
            </div>

            <div class="jiben">公差勤务</div>
            <el-form-item prop="tolerance">
              <div class="box4">
                <el-input
                  v-model="form.tolerance"
                  type="textarea"
                  placeholder="请输入内容"
                  autosize
                />
              </div>
            </el-form-item>
            <div class="jiben">人员及装备变动</div>
            <el-form-item prop="transfer">
              <div class="box4">
                <el-input
                  v-model="form.transfer"
                  type="textarea"
                  placeholder="请输入内容"
                  autosize
                />
              </div>
            </el-form-item>
            <div class="jiben">装备变动</div>
            <el-form-item prop="equip">
              <div class="box4">
                <el-input
                  v-model="form.equip"
                  type="textarea"
                  placeholder="请输入内容"
                  autosize
                />
              </div>
            </el-form-item>
            <div class="jiben">装备、军容风纪、内务卫生检查情况</div>
            <el-form-item prop="internal">
              <div class="box4">
                <el-input
                  v-model="form.internal"
                  type="textarea"
                  placeholder="请输入内容"
                  autosize
                />
              </div>
            </el-form-item>
            <div class="jiben">配班情况</div>
            <el-form-item label="值班组织人" prop="dutylead">
              <el-select
                v-model="form.dutylead"
                clearable
                placeholder="请选择值班组织人"
              >
                <el-option
                  :label="item.name"
                  :value="item.name"
                  v-for="(item, i) in renYuan"
                  :key="i"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="值班交班人" prop="dutya">
              <!-- <el-input v-model="form.dutya" placeholder="请输入值班交班人" /> -->
              <el-select
                v-model="form.dutya"
                clearable
                placeholder="请选择值班交班人"
              >
                <el-option
                  :label="item.name"
                  :value="item.name"
                  v-for="(item, i) in renYuan"
                  :key="i"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="值班接班人" prop="dutyb">
              <!-- <el-input v-model="form.dutyb" placeholder="请输入值班接班人" /> -->
              <el-select
                v-model="form.dutyb"
                placeholder="请选择值班接班人"
                clearable
              >
                <el-option
                  :label="item.name"
                  :value="item.name"
                  v-for="(item, i) in renYuan"
                  :key="i"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="值日交班人" prop="dutyc">
              <!-- <el-input v-model="form.dutyc" placeholder="请输入值日交班人" /> -->
              <el-select
                v-model="form.dutyc"
                clearable
                placeholder="请选择值日交班人"
              >
                <el-option
                  :label="item.name"
                  :value="item.name"
                  v-for="(item, i) in renYuan"
                  :key="i"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="值日接班人" prop="dutyd">
              <!-- <el-input v-model="form.dutyd" placeholder="请输入值日接班人" /> -->
              <el-select
                v-model="form.dutyd"
                clearable
                placeholder="请选择值日接班人"
              >
                <el-option
                  :label="item.name"
                  :value="item.name"
                  v-for="(item, i) in renYuan"
                  :key="i"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="厨房交班人" prop="dutye">
              <!-- <el-input v-model="form.dutye" placeholder="请输入厨房交班人" /> -->
              <el-select
                v-model="form.dutye"
                clearable
                placeholder="请选择厨房交班人"
              >
                <el-option
                  :label="item.name"
                  :value="item.name"
                  v-for="(item, i) in renYuan"
                  :key="i"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="厨房接班人" prop="dutyf">
              <el-select
                v-model="form.dutyf"
                clearable
                placeholder="请选择厨房接班人"
              >
                <el-option
                  :label="item.name"
                  :value="item.name"
                  v-for="(item, i) in renYuan"
                  :key="i"
                  class="input_xiala"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="交接时间" prop="dutyDate">
              <!-- <el-date-picker
                clearable
                size="small"
                v-model="form.dutyDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择交接时间"
              >
              </el-date-picker> -->
              <el-time-picker
                size="small"
                v-model="form.dutyDate"
                :picker-options="{
                  selectableRange: '00:00:00 - 23:59:59',
                }"
                placeholder=""
              >
              </el-time-picker>
            </el-form-item>
            <div class="jiben">值班交接情况</div>
            <el-form-item prop="dutyinfoA">
              <div class="box4">
                <el-input
                  v-model="form.dutyinfoa"
                  type="textarea"
                  placeholder="请输入内容"
                  autosize
                />
              </div>
            </el-form-item>
            <div class="jiben">值日交接情况</div>
            <el-form-item prop="dutyinfoC">
              <div class="box4">
                <el-input
                  v-model="form.dutyinfoc"
                  type="textarea"
                  placeholder="请输入内容"
                  autosize
                />
              </div>
            </el-form-item>
            <div class="jiben">厨房交接情况</div>
            <el-form-item prop="dutyinfoE">
              <div class="box4">
                <el-input
                  v-model="form.dutyinfoe"
                  type="textarea"
                  placeholder="请输入内容"
                  autosize
                />
              </div>
            </el-form-item>
            <div class="jiben">病号处理情况</div>
            <el-form-item prop="invalid">
              <div class="box4">
                <el-input
                  v-model="form.invalid"
                  placeholder="请输入内容"
                  type="textarea"
                  autosize
                />
              </div>
            </el-form-item>
            <div class="jiben">上级通知、指示及其他重要事项</div>
            <el-form-item prop="notice">
              <div class="box4">
                <el-input
                  v-model="form.notice"
                  placeholder="请输入内容"
                  type="textarea"
                  autosize
                />
              </div>
            </el-form-item>

            <div class="jiben">查铺查哨</div>
            <div class="box3">
              <el-form v-for="(item1, e) in checkShop" :key="e">
                <div>
                  <el-form-item label="" prop="">
                    <el-input
                      v-model="item1.peopleName"
                      placeholder="检查人"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-input
                      v-model="item1.foremanName"
                      placeholder="领班员"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-input
                      v-model="item1.sentryName"
                      placeholder="哨兵姓名"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-time-picker
                      v-model="item1.examineDate"
                      :picker-options="{
                        selectableRange: '00:00:00 - 23:59:59',
                      }"
                      placeholder="选择日期时间"
                      value-format="HH:mm:ss"
                    >
                    </el-time-picker>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-input
                      v-model="item1.dialogue"
                      placeholder="检查情况"
                    ></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button
                      type="primary"
                      icon="el-icon-plus"
                      size="mini"
                      @click="addForms"
                      v-if="e == checkShop.length - 1"
                    ></el-button>
                    <el-button
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      v-if="e > 0"
                      @click="delForms(e)"
                    >
                    </el-button>
                  </el-form-item>
                </div>
              </el-form>
            </div>
            <div class="jiben">临时来队亲属</div>
            <div class="box3">
              <div class="box4">
                <el-form v-for="(item1, i) in relatives" :key="i">
                  <el-form-item label="" prop="">
                    <el-select v-model="item1.peopleId" placeholder="军人姓名">
                      <el-option
                        v-for="item in renYuan"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                        class="input_xiala"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-input
                      v-model="item1.name"
                      placeholder="家属姓名"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-input
                      v-model="item1.relation"
                      placeholder="关系"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-time-picker
                      v-model="item1.comeDate"
                      :picker-options="{
                        selectableRange: '00:00:00 - 23:59:59',
                      }"
                      placeholder="来队时间"
                      value-format="HH:mm:ss"
                    >
                    </el-time-picker>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-time-picker
                      v-model="item1.leaveDate"
                      :picker-options="{
                        selectableRange: '00:00:00 - 23:59:59',
                      }"
                      placeholder="离队时间"
                      value-format="HH:mm:ss"
                    >
                    </el-time-picker>
                  </el-form-item>
                  <el-form-item>
                    <el-button
                      type="primary"
                      icon="el-icon-plus"
                      size="mini"
                      @click="addFormss"
                      v-if="i == relatives.length - 1"
                    ></el-button>
                    <el-button
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      v-if="i > 0"
                      @click="delFormss(i)"
                    >
                    </el-button>
                  </el-form-item>
                </el-form>
              </div>
            </div>

            <!-- <div class="jiben">请假外出</div>
            <div class="box3 qing_jia">
              <el-form v-for="(item, e) in qingJia" :key="e + ' '">
                <div>
                  <el-form-item label="" prop="">
                    <el-input v-model="item.name" placeholder="姓名"></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-input v-model="item.job" placeholder="职务"></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-input
                      v-model="item.reason"
                      placeholder="事由"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-input
                      v-model="item.leaveDuration"
                      placeholder="时长或天数"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-time-picker
                      placeholder="选择离队时间"
                      v-model="item.leaveTime"
                      :picker-options="{
                        start: '00:01',
                        step: '00:01',
                        end: '23:59',
                      }"
                      value-format="HH:mm"
                    >
                    </el-time-picker>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-input
                      v-model="item.leaveApprover"
                      placeholder="准假部门人"
                    ></el-input>
                  </el-form-item>

                  <el-form-item label="" prop="">
                    <el-time-picker
                      v-model="item.backTime"
                      :picker-options="{
                        start: '00:01',
                        step: '00:01',
                        end: '23:59',
                      }"
                      placeholder="选择归队时间"
                      value-format="HH:mm"
                    >
                    </el-time-picker>
                  </el-form-item>
                  <el-form-item label="" prop="">
                    <el-input
                      v-model="item.overTime"
                      placeholder="超误天数时长"
                    ></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button
                      type="primary"
                      icon="el-icon-plus"
                      size="mini"
                      @click="addQinJias"
                      v-if="e == qingJia.length - 1"
                    ></el-button>
                    <el-button
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      v-if="e > 0"
                      @click="delWeiFn(e)"
                    >
                    </el-button>
                  </el-form-item>
                </div>
              </el-form>
            </div> -->
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm(1)">确 定</el-button>
          <el-button @click="resertwo" size="btn">重置</el-button>
        </div>
      </el-dialog>
      <!-- 查看 -->
      <el-dialog
        :visible.sync="menuRoleVisible"
        :title="title"
        append-to-body
        id="chakan"
        :close-on-click-modal="false"
      >
        <bdgldiary
          v-if="menuRoleVisible"
          ref="menuRole"
          :message="wordInfo"
        ></bdgldiary>
        <div slot="footer" class="dialog-footer" v-if="statusShen">
          <el-button type="primary" @click="queDing">通过</el-button>
        </div>
      </el-dialog>

      <!-- 数据查看弹出层 -->
      <el-dialog
        :visible.sync="dialogVisible"
        title="统计详情"
        append-to-body
        id="dialoges"
        :close-on-click-modal="false"
      >
        <div class="zuiDa">
          <div class="shuJuTime">当前数据为 ： {{ tiems }}</div>
          <div class="jiben">已登记部门:{{ yishenpi }}个</div>
          <div class="boxxx">
            <div v-for="(item, i) in cg" :key="i" class="greed">
              <span class="greedss"> </span><span>{{ item }}</span>
            </div>
          </div>
          <!-- <span v-for="(item, i) in cg" :key="i" class="greed">{{ item }}</span> -->
          <div class="jiben">未登记部门:{{ weishenpi }}个</div>
          <div class="boxxx">
            <div v-for="(item, i) in sb" :key="i" class="red">
              <span class="greeds"> </span><span>{{ item }}</span>
            </div>
          </div>
        </div>
      </el-dialog>

      <!-- 导出查铺查哨 -->
      <el-dialog
        :visible.sync="exportOpen"
        title="导出查铺查哨"
        append-to-body
        :close-on-click-modal="false"
        custom-class="exportDialog"
      >
        <el-form
          ref="form3"
          :model="form3"
          :rules="rules1"
          label-width="80px"
          inline
        >
          <el-form-item label="单位" prop="unitId">
            <treeselect
              v-model="form3.unitId"
              :options="rese"
              placeholder="请选择单位"
            />
          </el-form-item>
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form3.startTime"
              type="datetime"
              placeholder="选择开始时间"
              class="text"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker
              clearable
              size="small"
              v-model="form3.endTime"
              type="datetime"
              placeholder="选择结束时间"
              class="text"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitFormTi">确 定</el-button>
          <el-button @click="reserexport" size="btn">关闭</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import bdgldiary from "@/components/look_word/bdgldiary.vue";
import {
  listBdgldiary,
  getBdgldiary,
  delBdgldiary,
  addBdgldiary,
  updateBdgldiary,
  exportBdgShuju,
  updateBdglShenPi,
  exportDaily,
} from "@/api/grassrootsregistration/bdgldiary";

import {
  getDept,
  getZhuChiRen,
  getShiLi,
  getXunLian,
  getWeiShu,
  getUsers,
} from "@/api/grassrootsregistration/bdglmeeting";

import { getDicts } from "@/api/system/dict/data";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getUserzs } from "@/api/grassrootsregistration/bdgldiary";
// 获取当前单位
import { getUserProfile } from "@/api/system/user";
// 获取时间状态
import { listDiary } from "@/api/system/diary";
export default {
  components: { Treeselect, bdgldiary },
  name: "Bdgldiary",
  data() {
    return {
      // 审批状态
      statusShen: false,
      //查看页面
      menuRoleVisible: false,
      wordInfo: {},
      //判断关闭
      style: "2",
      // 获取首长
      shouZhang: [],
      // 人员组织
      renYuan: [],
      // 添加的树形单位
      rese: [],
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
      // 要事日记表格数据
      bdgldiaryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitId: null,
        peopleId: null,
        blogDate: null,
        weather: null,
        orgcadre: null,
        orgsoldier: null,
        orgother: null,
        orgcivilian: null,
        nowcadre: null,
        nowsoldier: null,
        nowcivilian: null,
        tolerance: null,
        transfer: null,
        equip: null,
        internal: null,
        dutylead: null,
        dutya: null,
        dutyb: null,
        dutyc: null,
        dutyd: null,
        dutye: null,
        dutyf: null,
        dutyinfoa: null,
        dutyinfoc: null,
        dutyinfoe: null,
        dutyDate: null,
        invalid: null,
        notice: null,
        lead: null,
        weekwork: null,
        examine: null,
        peopleId1: null,
        contents: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitId: [
          { required: true, message: "单位名称不能为空", trigger: "blur" },
        ],
        peopleId: [
          // { required: true, message: "值班人员不能为空", trigger: "blur" },
        ],
        blogDate: [
          { required: true, message: "值班日期不能为空", trigger: "blur" },
        ],
        weather: [{ required: true, message: "天气不能为空", trigger: "blur" }],
        lead: [
          { required: true, message: "值班首长不能为空", trigger: "change" },
        ],
        orgcadre: [
          { required: true, message: "编制干部不能为空", trigger: "blur" },
        ],
        orgsoldier: [
          { required: true, message: "编制战士不能为空", trigger: "blur" },
        ],
        orgcivilian: [
          { required: true, message: "编制文职不能为空", trigger: "blur" },
        ],
        nowcadre: [
          { required: true, message: "现有干部不能为空", trigger: "blur" },
        ],
        nowsoldier: [
          { required: true, message: "现有战士不能为空", trigger: "blur" },
        ],
        nowcivilian: [
          { required: true, message: "现有文职不能为空", trigger: "blur" },
        ],
      },
      BdgDay: [],
      // 搜索人员
      Renyuan: [],
      // 字典类型查询
      dictType: {
        dictType: "yssp",
      },
      // 字典数据
      ziDian: [],
      //左侧树状
      deptOptions: null,
      //左侧时间选择
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      // 树形配置
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 组织人搜索列表00
      list: [],
      // 下拉选项显示隐藏
      xiaLa: false,
      //添加对话框查铺表格为空
      checkShop: [
        {
          peopleName: null,
          foremanName: null,
          sentryName: null,
          examineDate: null,
          dialogue: null,
        },
      ],
      // 添加对话框请假外出
      qingJia: [
        {
          name: null,
          job: null,
          reason: null,
          leaveDuration: null,
          leaveApprover: null,
          leaveTime: null,
          backTime: null,
          overTime: null,
        },
      ],
      //添加对话框来访人员表格为空
      relatives: [
        {
          peopleId: null,
          name: null,
          relation: null,
          comeDate: null,
          leaveDate: null,
        },
      ],
      // 临时来队亲属
      relatives: [
        {
          peopleId: null,
          name: null,
          relation: null,
          comeDate: null,
          leaveDate: null,
        },
      ],
      // 搜索时间保存
      tiem: null,
      // 训练情况
      xunlian: [],
      // 工作安排字段
      tomList: {
        wook: null,
        startTime: null,
        endTime: null,
        peopleIds: null,
        address: null,
        contents: null,
        organizer: null,
      },
      // 数据时间
      DataTime: "",
      //  右边登记数据
      registrationData: {},
      // 搜索时间的保存
      timeDate: "",
      // 数据查看的弹出层
      dialogVisible: false,
      // 获取成功对象
      cg: {},
      // 获取失败对象
      sb: {},
      // 获取登记部门数量
      yishenpi: "",
      // 获取未登记数量
      weishenpi: "",
      // 数据时间的保存
      tiems: "",
      centertext: null,
      // 获取全部首长
      quanShou: [],
      sz: null,
      dw: {},
      formes: {},
      statustimer: false,
      //早中晚选中
      day: [
        {
          value: "选项1",
          label: "早晨",
        },
        {
          value: "选项2",
          label: "上午",
        },
        {
          value: "选项3",
          label: "下午",
        },
        {
          value: "选项4",
          label: "晚上",
        },
      ],
      option: {
        start: "06:00",
        step: "00:05",
        end: "08:00",
      },
      // 训练列表
      xunList: [],
      // 导出
      exportOpen: false,
      form3: {},
      rules1: {},
    };
  },
  created() {
    // if (Cookies.get("shuaxin") != "true") {
    //   window.location.reload();
    // }
    // Cookies.set("shuaxin", "true");
    this.getList();
    // 获取单位
    this.getDept();
    // 获取字典通过未通过
    this.getdict();
    // 获取外部部门树形
    this.treeselect();
    // 获取数据
    this.getShuJu();
    // 获取全部首长
    this.getQuanShou();
    // 获取登录信息
    this.getUser();
  },
  methods: {
    resetTomList() {
      this.tomList = {
        wook: null,
        startTime: null,
        endTime: null,
        peopleIds: null,
        address: null,
        contents: null,
        organizer: null,
      };
    },
    // 删除一周工作
    tebleDelForms(i) {
      this.xunlian.splice(i, 1);
    },
    // 一周工作提交
    tijiao() {
      if (
        this.tomList.wook != null &&
        this.tomList.startTime != null &&
        this.tomList.endTime != null &&
        this.tomList.contents != null &&
        this.tomList.organizer != null
      ) {
        this.xunlian.push({
          dateline: this.tomList.wook,
          contents:
            this.tomList.startTime +
            "-" +
            this.tomList.endTime +
            " " +
            this.tomList.contents +
            "(" +
            this.tomList.organizer +
            ")",
          percentage: 0,
          realTo: 0,
          shouldArrive: 0,
        });
        this.resetTomList();
      } else {
        this.$message.error("内容填写不完整！");
      }
    },
    datelinefun(val) {
      if (val == "早晨") {
        this.option.start = "06:00";
        this.option.end = "08:00";
        this.tomList.startTime = null;
        this.tomList.endTime = null;
      } else if (val == "上午") {
        this.option.start = "08:00";
        this.option.end = "12:00";
        this.tomList.startTime = null;
        this.tomList.endTime = null;
      } else if (val == "下午") {
        this.option.start = "12:00";
        this.option.end = "18:00";
        this.tomList.startTime = null;
        this.tomList.endTime = null;
      } else if (val == "晚上") {
        this.option.start = "18:00";
        this.option.end = "23:00";
        this.tomList.startTime = null;
        this.tomList.endTime = null;
      }
    },
    // 动态判断时间的修改
    gettimer() {
      listDiary().then((res) => {
        if (res.rows[0].setnum == 1) {
          this.statustimer = true;
        }
      });
    },
    // 获取登录信息
    getUser() {
      getUserProfile().then((response) => {
        this.dw = response.data.dept;
      });
    },
    // 新增时间获取
    getTimes() {
      var tiem = new Date();
      let y = tiem.getFullYear();
      let m = tiem.getMonth() + 1;
      m = m < 10 ? "0" + m : m;
      let d = tiem.getDate();
      d = d < 10 ? "0" + d : d;
      let h = tiem.getHours();
      h = h < 10 ? "0" + h : h;
      let M = tiem.getMinutes();
      M = M < 10 ? "0" + M : M;
      let s = tiem.getSeconds();
      s = s < 10 ? "0" + s : s;
      this.form.blogDate = y + "-" + m + "-" + d + " " + h + ":" + M + ":" + s;
      return;
    },
    getShoufan(id) {
      this.quanShou.map((item) => {
        if (item.userId == id) {
          this.sz = item.nickName;
        }
        return this.sz;
      });
    },
    getQuanShou() {
      getUserzs().then((respones) => {
        console.log("全部首长信息", respones.rows.nickName);
        this.quanShou = respones.rows.nickName;
      });
    },
    // 点击数据盒子
    dengJI() {
      this.dialogVisible = true;
    },
    //审批按钮操作
    handleChakans(row) {
      // 是否隐藏按钮
      this.opens = false;
      this.statusShen = true;
      const id = row.id || this.ids;
      getBdgldiary(id).then((response) => {
        if (new Date(response.data.blogDate).getDay() == 0) {
          response.data.week = "星期日";
        } else if (new Date(response.data.blogDate).getDay() == 1) {
          response.data.week = "星期一";
        } else if (new Date(response.data.blogDate).getDay() == 2) {
          response.data.week = "星期二";
        } else if (new Date(response.data.blogDate).getDay() == 3) {
          response.data.week = "星期三";
        } else if (new Date(response.data.blogDate).getDay() == 4) {
          response.data.week = "星期四";
        } else if (new Date(response.data.blogDate).getDay() == 5) {
          response.data.week = "星期五";
        } else if (new Date(response.data.blogDate).getDay() == 6) {
          response.data.week = "星期六";
        }
        this.wordInfo = response.data;
        this.title = "审批要事日记";
        this.menuRoleVisible = true;
      });
    },
    // 审批页面确定
    queDing() {
      // this.wordInfo.examine = 1;
      this.formes.examine = 1;
      if (this.wordInfo.id != null) {
        this.formes.id = this.wordInfo.id;
        updateBdglShenPi(this.formes).then((response) => {
          this.menuRoleVisible = false;
          this.getList();
          this.getShuJu(this.timeDate);
          this.$forceUpdate();
        });
      }
    },
    // 获取部门数据
    getShuJu(data) {
      exportBdgShuju(data).then((res) => {
        this.DataTime = data;
        this.registrationData = res;
        this.cg = res.cg;
        this.sb = res.sb;
        this.yishenpi = res.yiShenPi;
        this.weishenpi = res.weiShenPi;
        this.tiems = res.time;
      });
    },
    // 搜索时间触发
    shiJian(data) {
      this.queryParams.diaryTime = data;
      this.timeDate = data;
      this.getList();
      this.getShuJu(data);
    },
    // 应到人数失去焦点触发
    yinDao() {
      this.xunlian.forEach((item) => {
        if (item.shouldArrive != null && item.realTo != null) {
          var str = ((item.realTo / item.shouldArrive) * 100).toFixed(2);
          str += "%";
          item.percentage = str;
        }
      });
    },
    // 实到人数失去焦点触发
    shiDao() {
      this.xunlian.forEach((item) => {
        if (item.shouldArrive != null && item.realTo != null) {
          var str = ((item.realTo / item.shouldArrive) * 100).toFixed(2);
          str += "%";
          item.percentage = str;
        }
      });
    },
    // 获取训练
    XunLian(id, tiem) {
      getXunLian(id, tiem).then((res) => {
        if (res.data) {
          this.xunlian = res.data;
          if (this.xunlian) {
            for (var i = 0; i < this.xunlian.length; i++) {
              // centertext
              this.xunlian[i].contents =
                this.xunlian[i].startTime +
                "-" +
                this.xunlian[i].endTime +
                "  " +
                this.xunlian[i].contents +
                "(" +
                this.xunlian[i].organizer +
                ")";
            }
          }
        } else {
          this.xunlian = [];
        }
      });
    },
    // 选中军人触发
    junRen(val) {
      this.relatives[0].peopleId = val.id;
      this.relatives[0].peopleName = val.name;
    },
    /** 新增查询部门下拉树结构 */
    getTreeselect() {
      getDept().then((response) => {
        this.rese = response.data;
      });
    },

    // 增加对话框查铺
    addForms() {
      this.checkShop.push({
        peopleName: null,
        foremanName: null,
        sentryName: null,
        examineDate: null,
        dialogue: null,
      });
    },

    // 删除对话框查铺
    delForms(i) {
      this.checkShop.splice(i, 1);
    },

    //增加对话框临时来访人员
    addFormss() {
      this.relatives.push({
        peopleId: null,
        name: null,
        relation: null,
        comeDate: null,
        leaveDate: null,
      });
    },
    //删除对话框临时来访人员
    delFormss(e) {
      this.relatives.splice(e, 1);
    },
    // 增加请假外出
    addQinJias() {
      this.qingJia.push({
        name: null,
        job: null,
        reason: null,
        leaveDuration: null,
        leaveApprover: null,
        leaveTime: null,
        backTime: null,
        overTime: null,
      });
    },
    //删除请假外出
    delWeiFn(e) {
      this.qingJia.splice(e, 1);
    },
    // 获取外面部门树形
    treeselect() {
      getWeiShu().then((res) => {
        this.deptOptions = res.data;
      });
    },
    //外面树形选择搜索
    handleNodeClick(data) {
      this.queryParams.unitId = data.id;
      // this.queryParams.IsUnit = "1";
      this.getList();
    },

    // 选择值班人触发
    btn(val) {
      this.form.peopleId1 = val.id;
      this.form.peopleName = val.name;
    },
    // 当选择单位时候重置人员
    chongZhi() {
      this.form = {
        peopleId: null,
        lead: null,
        dutyA: null,
        dutyB: null,
        dutyC: null,
        dutyD: null,
        dutyE: null,
        dutyF: null,
        dutylead: null,
      };
      this.relatives = [
        {
          peopleId: null,
        },
      ];
    },
    // 当点击完成值班时间的选择发起请求
    blogDate(data) {
      // 判断是否全部选择
      if (this.form.unitId !== null && this.form.blogDate !== null) {
        this.XunLian(this.form.unitId, this.form.blogDate);
      }
    },
    // 获取实力情况
    getShiLiRen(id) {
      getShiLi(id).then((res) => {
        this.form.orgcadre = res.data.ganbu;
        this.form.orgsoldier = res.data.shiBin;
        this.form.orgother = res.data.qita;
        this.form.orgcivilian = res.data.wenZhi;
        this.form.nowcadre = res.data.xianYouGanBu;
        this.form.nowsoldier = res.data.xianYouShiBin;
        this.form.nowcivilian = res.data.xianYouWenZhi;
      });
    },
    // 单位选择触发
    selectPeo(data) {
      this.chongZhi();
      this.form.unitId = data.id;
      this.getRenYuan(this.form.unitId);
      // 获取当前时间
      if (this.statustimer) {
      } else {
        this.getTimes();
      }
      // 获取实力情况
      this.form.deptId = data.id;
      this.getShiLiRen(this.form.deptId);
      this.getShous(this.form.unitId);
      // this.getTimes();
      // 判断是否全部选择
      if (this.form.unitId !== null && this.form.blogDate) {
        this.XunLian(this.form.unitId, this.form.blogDate);
      }
    },
    // 根据部门获取人员
    getRenYuan(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 获取首长
    getShous(id) {
      getUsers(id).then((res) => {
        this.shouZhang = res.data;
      });
    },
    // 搜索第一个选中触发
    search() {
      getZhuChiRen(this.queryParams.deptId).then((res) => {
        this.Renyuan = res.data;
      });
      this.getList();
    },
    // 搜索审批触发
    approve(val) {
      this.queryParams.examine = val * 1;
      this.getList();
    },
    // 字典查询
    getdict() {
      getDicts(this.dictType.dictType).then((res) => {
        this.ziDian = res.data;
      });
    },
    /** 查询要事日记列表 */
    getList() {
      this.loading = true;
      listBdgldiary(this.queryParams).then((response) => {
        this.bdgldiaryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 查询单位列表
    async getDept() {
      const res = await getDept();
      this.rese = res.data;
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
        unitId: null,
        peopleId: null,
        blogDate: null,
        weather: null,
        orgcadre: null,
        orgsoldier: null,
        orgcivilian: null,
        nowcadre: null,
        nowsoldier: null,
        nowcivilian: null,
        tolerance: null,
        transfer: null,
        equip: null,
        internal: null,
        dutylead: null,
        dutya: null,
        dutyb: null,
        dutyc: null,
        dutyd: null,
        dutye: null,
        dutyf: null,
        dutyinfoa: null,
        dutyinfoc: null,
        dutyinfoe: null,
        dutyDate: null,
        invalid: null,
        notice: null,
        lead: null,
        weekwork: null,
        examine: null,
        peopleId1: null,
        contents: null,
        createId: null,
        createName: null,
      };
      this.relatives = [
        {
          name: null,
          relation: null,
          comeDate: null,
          leaveDate: null,
          peopleId: null,
          peopleName: null,
        },
      ];
      this.checkShop = [
        {
          peopleName: null,
          foremanName: null,
          sentryName: null,
          examineDate: null,
          dialogue: null,
        },
      ];
      this.xunlian = [];
      this.qingJia = [
        {
          name: null,
          job: null,
          reason: null,
          leaveDuration: null,
          leaveApprover: null,
          leaveTime: null,
          backTime: null,
          overTime: null,
        },
      ];
      this.tomList = {
        wook: null,
        startTime: null,
        endTime: null,
        peopleIds: null,
        adorganizerdress: null,
        contents: null,
        organizer: null,
      };
      this.resetForm("form");
    },
    /** 对话框重置按钮操作 */
    resertwo() {
      this.reset();
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
    handleAdd(e) {
      // 获取首长
      this.shouZhang = [];
      // 人员组织
      this.renYuan = [];
      this.getTreeselect();
      this.reset();
      this.gettimer();
      this.form.unitId = this.dw.deptId;
      this.getShiLiRen(this.form.deptId);
      this.getRenYuan(this.form.unitId);
      this.getShous(this.form.unitId);
      this.getTimes();
      // 判断是否全部选择
      if (this.form.unitId !== null && this.form.blogDate) {
        this.XunLian(this.form.unitId, this.form.blogDate);
      }
      this.open = true;
      this.title = "添加要事日记";
      this.style = 2;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.title = "修改要事日记";
      this.statustimer = false;
      this.getTreeselect();
      this.reset();
      const id = row.id || this.ids;
      getBdgldiary(id).then((response) => {
        // 转换时间格式
        var tiems = new Date(response.data.blogDate);
        this.form = response.data;
        this.form.blogDate = tiems;
        //  请求首长
        this.getShous(this.form.unitId);
        // 训练计划
        this.xunList = response.data.bdglDiaryWeekworkRegisterList;
        if (this.xunList) {
          for (let index = 0; index < this.xunList.length; index++) {
            this.xunlian.push(this.xunList[index]);
          }
        } else {
          this.xunlian = [];
        }

        // 请求人员
        this.getRenYuan(this.form.unitId);
        this.open = true;
        // 临时来对
        if (response.data.kinsfolk != null && response.data.kinsfolk != "") {
          if (response.data.kinsfolk[0].peopleId != null) {
            this.relatives = response.data.kinsfolk;
          }
        }
        // 查铺查询
        if (response.data.inspect != null && response.data.inspect != "") {
          this.checkShop = response.data.inspect;
        }
      });
    },

    //查看按钮操作
    handleChakan(row) {
      // 是否隐藏按钮
      this.opens = false;
      this.statusShen = false;
      const id = row.id || this.ids;
      getBdgldiary(id).then((response) => {
        if (new Date(response.data.blogDate).getDay() == 0) {
          response.data.week = "星期日";
        } else if (new Date(response.data.blogDate).getDay() == 1) {
          response.data.week = "星期一";
        } else if (new Date(response.data.blogDate).getDay() == 2) {
          response.data.week = "星期二";
        } else if (new Date(response.data.blogDate).getDay() == 3) {
          response.data.week = "星期三";
        } else if (new Date(response.data.blogDate).getDay() == 4) {
          response.data.week = "星期四";
        } else if (new Date(response.data.blogDate).getDay() == 5) {
          response.data.week = "星期五";
        } else if (new Date(response.data.blogDate).getDay() == 6) {
          response.data.week = "星期六";
        }
        // this.getShoufan(response.data.lead);
        this.wordInfo = response.data;
        this.wordInfo.sz = this.sz;
        this.title = "查看要事日记";
        this.menuRoleVisible = true;
      });
    },

    /** 提交按钮 */
    submitForm(style) {
      this.style = style;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (
            (this.relatives[0].name ||
              this.relatives[0].peopleId ||
              this.relatives[0].peopleName) != null
          ) {
            this.form.kinsfolk = this.relatives;
          }
          if (
            (this.checkShop[0].peopleName ||
              this.checkShop[0].foremanName ||
              this.checkShop[0].sentryName ||
              this.checkShop[0].examineDate ||
              this.checkShop[0].dialogue) != null
          ) {
            this.form.inspect = this.checkShop;
          }
          // 外出请假
          if (
            this.qingJia[0].name !== null ||
            this.qingJia[0].job !== null ||
            this.qingJia[0].reason !== null ||
            this.qingJia[0].leaveDuration !== null ||
            this.qingJia[0].leaveApprover != null ||
            this.qingJia[0].leaveTime != null
          ) {
            this.form.bdglDiaryLeaveList = this.qingJia;
          }
          if (this.xunlian.length > 0) {
            // 工作安排
            if (
              this.xunlian[0].dateline != null &&
              this.xunlian[0].contents != null &&
              this.xunlian[0].percentage != null
            ) {
              this.form.bdglDiaryWeekworkRegisterList = this.xunlian;
            }
          }
          if (this.form.id != null) {
            updateBdgldiary(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            // 查铺查哨
            if (
              this.checkShop[0].peopleName !== null ||
              this.checkShop[0].sentryName !== null ||
              this.checkShop[0].examineDate !== null ||
              this.checkShop[0].foremanName !== null ||
              this.checkShop[0].dialogue != null
            ) {
              this.form.inspect = this.checkShop;
            }
            // 外出请假
            if (
              this.qingJia[0].name !== null ||
              this.qingJia[0].job !== null ||
              this.qingJia[0].reason !== null ||
              this.qingJia[0].leaveDuration !== null ||
              this.qingJia[0].leaveApprover != null ||
              this.qingJia[0].leaveTime != null
            ) {
              this.form.bdglDiaryLeaveList = this.qingJia;
            }
            // 来对亲属
            if (this.relatives[0].peopleId !== null) {
              this.form.kinsfolk = this.relatives;
            }
            // 工作安排
            if (this.xunlian.length > 0) {
              if (
                this.xunlian[0].dateline != null &&
                this.xunlian[0].contents != null
              ) {
                this.form.bdglDiaryWeekworkRegisterList = this.xunlian;
              }
            }
            this.form.blogDate = new Date(this.form.blogDate);
            // // 训练计划
            this.form.weekworkRegisters = this.xunlian;
            addBdgldiary(this.form).then((response) => {
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
        // .confirm('是否确认删除要事日记编号为"' + ids + '"的数据项？')
        .confirm("是否确认删除该数据？")
        .then(function () {
          return delBdgldiary(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.exportOpen = true;
    },
    // 关闭导出弹框
    reserexport() {
      this.exportOpen = false;
      this.form3 = {};
    },
    submitFormTi() {
      const queryParams = this.form3;
      // console.log(this.form3);
      this.$modal
        .confirm("是否确认导出所有要事日记数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportDaily(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
          this.reserexport();
        })
        .catch(() => {});
    },
  },
};
</script>

<style scoped>
::v-deep .el-dialog {
  width: 1060px;
  background-color: #004d86;
  margin: auto;
}

::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
  margin: auto;
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

::v-deep .el-input--small .el-input__inner {
  width: 200px !important;
  height: 36px;
  border: 1px solid white !important;
  color: #fff !important;
}

::v-deep .el-textarea__inner {
  width: 920px;
  height: 104px;
}

::v-deep .el-select-dropdown__list {
  border: none;
}

.el-date-table,
.el-select-dropdown__item {
  color: #fff;
}

.el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #196299;
}

/* .el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  color: #fff !important;
} */
::v-deep .el-dialog__body {
  margin: 10px 0px 20px 68px;
  padding-top: 20px !important;
  box-sizing: border-box;
  /* padding: 30px 12px 30px 28px; */
}

/* 表单背景 */
::v-deep .el-input__inner {
  background-color: transparent !important;
  color: #fff;
  /* border: 1px solid white !important; */
  border-color: #fff !important;
}

/* 调整表单间距 */
::v-deep .el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 200px;
}

.el-dialog__body .roll-dialog {
  padding: 3px, 0px;
  overflow-y: auto;
}

.box {
  font-size: 14px;
  color: #fff;
  margin-left: -55px;
  position: relative;
}

::v-deep .btn {
  width: 54px;
  height: 36px;
  color: #fff;
  background-color: #1d96ff;
  border-radius: 4px;
  border: none;
  font-size: 14px;
  display: inline-block;
  margin-left: 60px;
}

::v-deep .el-icon-refresh {
  font-size: 16px;
  margin-left: -3px;
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

::v-deep .el-form-item__content {
  width: 200px;
  cursor: pointer !important;
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

::v-deep .el-pagination.is-background .btn-prev,
.el-pagination.is-background .btn-next,
.el-pagination.is-background .el-pager li {
  background: #004d86 !important;
  color: #fff !important;
}

.app-container {
  display: flex;
}

/* 左侧树状盒子 */
.app-container .box-lfet {
  width: 15%;
  min-height: 740px;
  /* background: #003156; */
  /* padding: 10px; */
  margin-right: 10px;
  /* border: 1px solid white; */
}

/* 右侧内容盒子 */
.app-container .box-right {
  width: 85%;
}

.app-container .box-lfet .data {
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
  background: #003156;
  color: #fff;
  margin-right: 2px;
  padding-top: 7px;
}

::v-deep .el-tree-node__content {
  height: 32px !important;
}

::v-deep .el-table .el-table__header-wrapper th,
.el-table .el-table__fixed-header-wrapper th {
  word-break: break-word;
  background-color: #004d86 !important;
  color: #fff;
}

.box-lfet .dadui {
  height: 40px;
  background: #003156;
  /* border-bottom: 1px solid #718A9D ; */
  background-image: url(../../../images/矩形底部边框.png);
  background-repeat: no-repeat;
  background-position: 0px 39px;
  font-size: 16px;
  color: #1d96ff;
}

.box-lfet .dadui img {
  margin: 10px 10px 0px 10px;
}

.box-lfet .data p {
  position: absolute;
  top: -5px;
  left: 15px;
}

.box-lfet .data .a1 {
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

/* 输入框显示手指 */
::v-deep .el-input__inner {
  cursor: pointer !important;
}

/* 训练、教育或者执行其他任务情况 */
.box {
  margin-bottom: 25px;
  padding-bottom: 20px;
}

::v-deep .box .el-textarea__inner {
  width: 395px;
  border: none;
  height: 50px;
}

::v-deep .box .el-input__inner {
  width: 100%;
  height: 100%;
  border: none;
  text-align: center;
}

/* 查铺查哨-临时来对亲属 */
.box3 {
  width: 920px;
  min-height: 70px;
  border: 1px solid white;
  border-radius: 5px;
  /* background: #409eff; */
  /* margin-bottom: 30px; */
  padding-top: 15px;
  padding-left: 15px;
  margin-bottom: 20px !important;
}

/* 表单长度 */
::v-deep .box3 .el-form-item__content {
  width: 140px;
  display: flex;
}

::v-deep .box3 .el-input__inner {
  width: 142px;
  height: 37px;
  margin-bottom: 18px;
}

::v-deep .el-select__caret .el-input__icon .el-icon-arrow-up {
  margin-top: -10px;
}

/* 添加删除按钮 */
::v-deep .box3 .el-button {
  width: 32px;
  height: 32px;
  text-align: center;
  margin-right: 3px;
  margin-top: 2px;
  margin-bottom: 10px;
}

::v-deep .box3 .el-button .el-icon-plus {
  margin-left: -6px;
}

::v-deep .box3 .el-button .el-icon-delete {
  margin-left: -6px;
}

::v-deep .el-input__prefix {
  display: none;
}

::v-deep .el-date-editor.el-input {
  width: 116px;
  height: 36px;
  line-height: 36px;
}

::v-deep .box3 .el-form-item__content .el-input__inner {
  padding: 0px 15px;
}

.box3 .el-form-item {
  margin-bottom: 0px;
}

::v-deep .el-select__caret {
  margin-top: -10px !important;
}

.el-dialog__body {
  color: #fff;
}

/* ::v-deep .vue-treeselect__menu {
  background: #004d86;
} */
.greedd > .el-tree-node__content:after {
  content: "";
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: #67c23a;
  margin-left: 80px;
}

/* .greedd  {
  background-color: #67c23a
} */
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 50px;
  margin-right: 20px;
}

::v-deep .el-dialog__body {
  padding-left: 0px !important;
  padding-right: 0px !important;
  padding-bottom: 0px !important;
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

.mb8 {
  position: static;
}

.shuJu {
  height: 16px;
  margin-top: 13px;
  margin-right: 90px;
  float: right;
  /* background-color: red; */
  color: #fff;
  font-size: 14px;
  position: absolute;
  right: 10px;
}

/* .public {
  margin-right: 40px;
} */

.box44::before {
  display: inline-block;
  content: "";
  width: 8px;
  height: 8px;
  /* border-radius: 50%; */
  background-color: #1d96ff;
  margin-left: 26px;
}

.box5::before {
  display: inline-block;
  content: "";
  width: 8px;
  height: 8px;
  /* border-radius: 50%; */
  background-color: #67c23a;
  margin-left: 26px;
}

.box6::before {
  display: inline-block;
  content: "";
  width: 8px;
  height: 8px;
  /* border-radius: 50%; */
  background-color: #ff4949;
  margin-left: 26px;
}

/* 文本域提示文字颜色 */
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}

::v-deep .el-tree-node__content:hover {
  /* color: #fff; */
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

::v-deep .box table td {
  border-bottom: 1px solid white !important;
}

::v-deep .data .el-input__inner {
  border: none !important;
}

::v-deep .box .el-input__inner {
  border: none !important;
}

/* 卡片样式 */
.app-container {
  height: calc(100vh - 108px) !important;
}

::v-deep .el-dialog .el-input__suffix-inner .el-icon-circle-close:before {
  content: "";
  display: inline-block;
  /* margin-left: 87px; */
}

::v-deep .el-dialog .text .el-input__suffix-inner .el-icon-circle-close:before {
  content: "";
  display: inline-block;
  margin-left: 87px;
}

::v-deep .el-dialog .el-input--small .el-input__icon {
  line-height: 37px;
}

/* 请假样式 */
::v-deep .qing_jia {
  margin-bottom: 30px;
}
::v-deep .qing_jia .el-input__suffix-inner .el-icon-circle-close:before {
  content: "";
  display: inline-block;
}
/* 新增工作登记 */
.box1 {
  width: 920px;
  min-height: 100px;
  border: 1px solid white;
  color: #fff;
  border-radius: 5px;
  margin-bottom: 30px;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  position: relative;
  padding: 20px 20px 0px 30px;
}
.box1 ::v-deep .el-textarea__inner {
  width: 806px;
  min-height: 104px !important;
  font-size: 14px;
}
.box1 ::v-deep .el-form-item__content {
  margin-right: 12px;
}
.box1 ::v-deep .el-date-editor {
  width: 170px !important;
  height: 36px;
  line-height: 36px;
}
.box1 ::v-deep .el-form-item__content {
  width: 130px;
  cursor: pointer !important;
}
.box2 ::v-deep .el-form-item__content .el-input__inner {
  width: 170px !important;
}
.box2 {
  display: inline-block;
}

.box ::v-deep .el-button--danger {
  position: absolute;
  top: 47px;
  right: 64px;
}
::v-deep .exportDialog {
  width: 500px;
}
::v-deep .exportDialog .el-dialog__footer {
  margin-right: 83px;
}
</style>
