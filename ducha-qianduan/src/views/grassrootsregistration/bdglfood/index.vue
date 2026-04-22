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
      <el-form-item label="" prop="peopleId">
        <el-select
          v-model="queryParams.peopleId"
          placeholder="请选择值班员"
          @change="Renyuan"
          filterable
          class="input_xiala"
        >
          <el-option
            v-for="item in renYuans"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            class="input_xiala"
          >
          </el-option>
        </el-select>
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
          v-hasPermi="['grassrootsregistration:bdglfood:add']"
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
          v-hasPermi="['grassrootsregistration:bdglfood:edit']"
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
          v-hasPermi="['grassrootsregistration:bdglfood:remove']"
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
          v-hasPermi="['grassrootsregistration:bdglfood:export']"
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
      :data="bdglfoodList"
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
      <el-table-column label="值班员" align="center" prop="peopleName" />
      <!-- <el-table-column label="更新时间" align="center" prop="id" /> -->
      <el-table-column
        label="登记日期"
        align="center"
        prop="recordDate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recordDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>

      <el-table-column label="经委会主任" align="center" prop="chairmanName" />
      <el-table-column label="单位首长" align="center" prop="people1Name" />
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
            v-hasPermi="['grassrootsregistration:bdglfood:edit']"
            ><span class="edit">修改</span></el-button
          >
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['grassrootsregistration:bdglfood:remove']"
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

    <!-- 添加或修改伙食管理登记薄对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1016px"
      append-to-body
      :close-on-click-modal="false"
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
              v-model="form.unitId"
              :options="deptOptions"
              placeholder="请选择单位"
              @select="selectPeo"
              @open="blurSelect"
            />
          </el-form-item>
          <el-form-item label="值班员" prop="peopleId">
            <el-select v-model="form.peopleId" placeholder="请选择值班员">
              <el-option
                v-for="item in renYuan"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                :disabled="item.status == 1"
                @click.native="zhiBan(item.name)"
                class="input_xiala"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="登记日期" prop="recordDate">
            <el-date-picker
              clearable
              size="small"
              v-model="form.recordDate"
              type="date"
              :editable="false"
              value-format="yyyy-MM-dd"
              placeholder="请选择登记日期"
              @change="timeDay"
            >
            </el-date-picker>
          </el-form-item>

          <el-form-item label="经委会主任" prop="chairmanId">
            <!-- <el-input v-model="form.chairmanId" placeholder="请输入经委会主任" /> -->
            <el-select
              ref="aa"
              v-model="form.chairmanId"
              placeholder="请选择经委会主任"
            >
              <el-option
                v-for="item in renYuan"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                :disabled="item.status == 1"
                @click.native="ZhuRen(item.name)"
                class="input_xiala"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单位首长" prop="peopleId1">
            <el-select v-model="form.peopleId1" placeholder="请选择单位首长">
              <el-option
                v-for="item in renYuan"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                :disabled="item.status == 1"
                @click.native="SouZhang(item.name)"
                class="input_xiala"
              ></el-option>
            </el-select>
          </el-form-item>

          <div class="jiben">一日三餐(早餐)</div>
          <div class="box1">
            <table
              border="1"
              width="920"
              cellspacing="0"
              align="center"
              style="border-color: white; margin: 0px"
            >
              <tr align="center">
                <td colspan="2">主食内容：</td>
                <td colspan="10">
                  <el-input
                    v-model="meal.stapleFood"
                    placeholder="主食内容"
                  ></el-input>
                </td>
              </tr>
              <tr align="center">
                <td colspan="2">菜谱：</td>
                <td colspan="10">
                  <el-input v-model="meal.menu" placeholder="菜谱"></el-input>
                </td>
              </tr>
              <tr align="center">
                <td colspan="2">就餐人员总数：</td>
                <td colspan="2">
                  <el-input
                    v-model.number="meal.jiucanCount"
                    placeholder="就餐人员总数"
                    oninput="value=value.replace(/[^\d]/g,'')"
                  ></el-input>
                </td>
                <td colspan="2">联队就餐人数：</td>
                <td colspan="2">
                  <el-input
                    v-model.number="meal.lianduiCount"
                    placeholder="联队就餐人数"
                    oninput="value=value.replace(/[^\d]/g,'')"
                  ></el-input>
                </td>
                <td colspan="2">外来就餐人数：</td>
                <td colspan="2">
                  <el-input
                    v-model.number="meal.wailaiCount"
                    placeholder="外来就餐人数"
                    oninput="value=value.replace(/[^\d]/g,'')"
                  ></el-input>
                </td>
              </tr>
            </table>
            <div class="box2">
              <table
                border="1"
                width="920"
                cellspacing="0"
                align="center"
                style="border-color: white; margin: 0px"
              >
                <tr align="center">
                  <td>面粉<span>(kg)</span></td>
                  <td>大米<span>(kg)</span></td>
                  <td>肉类<span>(kg)</span></td>
                  <td>鱼类<span>(kg)</span></td>
                  <td>油类<span>(L)</span></td>
                  <td>黄豆<span>(kg)</span></td>
                  <td>蔬菜<span>(kg)</span></td>
                  <td>水果<span>(kg)</span></td>
                  <td>糖<span>(kg)</span></td>
                  <td>燃料<span>(L)</span></td>
                  <td>调料<span>(kg)</span></td>
                </tr>
                <tr align="center">
                  <td>
                    <el-input
                      v-model.number="meal.mianfen"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="meal.dami"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="meal.rolei"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="meal.yulei"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="meal.youlei"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="meal.huangdo"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="meal.suchai"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="meal.shuiguo"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="meal.tang"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="meal.ranliao"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="meal.tiaoliao"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                </tr>
              </table>
            </div>
          </div>

          <div class="jiben">一日三餐(午餐)</div>
          <div class="box1">
            <table
              border="1"
              width="920"
              cellspacing="0"
              align="center"
              style="border-color: white; margin: 0px"
            >
              <tr align="center">
                <td colspan="2">主食内容：</td>
                <td colspan="10">
                  <el-input
                    v-model="zhongCan.stapleFood"
                    placeholder="主食内容"
                  ></el-input>
                </td>
              </tr>
              <tr align="center">
                <td colspan="2">菜谱：</td>
                <td colspan="10">
                  <el-input
                    v-model="zhongCan.menu"
                    placeholder="菜谱"
                  ></el-input>
                </td>
              </tr>
              <tr align="center">
                <td colspan="2">就餐人员总数：</td>
                <td colspan="2">
                  <el-input
                    v-model.number="zhongCan.jiucanCount"
                    placeholder="就餐人员总数"
                    oninput="value=value.replace(/[^\d]/g,'')"
                  ></el-input>
                </td>
                <td colspan="2">联队就餐人数：</td>
                <td colspan="2">
                  <el-input
                    v-model.number="zhongCan.lianduiCount"
                    placeholder="联队就餐人数"
                    oninput="value=value.replace(/[^\d]/g,'')"
                  ></el-input>
                </td>
                <td colspan="2">外来就餐人数：</td>
                <td colspan="2">
                  <el-input
                    v-model.number="zhongCan.wailaiCount"
                    placeholder="外来就餐人数"
                    oninput="value=value.replace(/[^\d]/g,'')"
                  ></el-input>
                </td>
              </tr>
            </table>
            <div class="box2">
              <table
                border="1"
                width="920"
                cellspacing="0"
                align="center"
                style="border-color: white; margin: 0px"
              >
                <tr align="center">
                  <td>面粉<span>(kg)</span></td>
                  <td>大米<span>(kg)</span></td>
                  <td>肉类<span>(kg)</span></td>
                  <td>鱼类<span>(kg)</span></td>
                  <td>油类<span>(L)</span></td>
                  <td>黄豆<span>(kg)</span></td>
                  <td>蔬菜<span>(kg)</span></td>
                  <td>水果<span>(kg)</span></td>
                  <td>糖<span>(kg)</span></td>
                  <td>燃料<span>(L)</span></td>
                  <td>调料<span>(kg)</span></td>
                </tr>
                <tr align="center">
                  <td>
                    <el-input
                      v-model.number="zhongCan.mianfen"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="zhongCan.dami"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="zhongCan.rolei"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="zhongCan.yulei"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="zhongCan.youlei"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="zhongCan.huangdo"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="zhongCan.suchai"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="zhongCan.shuiguo"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="zhongCan.tang"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="zhongCan.ranliao"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="zhongCan.tiaoliao"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                </tr>
              </table>
            </div>
          </div>

          <div class="jiben">一日三餐(晚餐)</div>
          <div class="box1">
            <table
              border="1"
              width="920"
              cellspacing="0"
              align="center"
              style="border-color: white; margin: 0px"
            >
              <tr align="center">
                <td colspan="2">主食内容：</td>
                <td colspan="10">
                  <el-input
                    v-model="wanCan.stapleFood"
                    placeholder="主食内容"
                  ></el-input>
                </td>
              </tr>
              <tr align="center">
                <td colspan="2">菜谱：</td>
                <td colspan="10">
                  <el-input v-model="wanCan.menu" placeholder="菜谱"></el-input>
                </td>
              </tr>
              <tr align="center">
                <td colspan="2">就餐人员总数：</td>
                <td colspan="2">
                  <el-input
                    v-model.number="wanCan.jiucanCount"
                    placeholder="就餐人员总数"
                    oninput="value=value.replace(/[^\d]/g,'')"
                  ></el-input>
                </td>
                <td colspan="2">联队就餐人数：</td>
                <td colspan="2">
                  <el-input
                    v-model.number="wanCan.lianduiCount"
                    placeholder="联队就餐人数"
                    oninput="value=value.replace(/[^\d]/g,'')"
                  ></el-input>
                </td>
                <td colspan="2">外来就餐人数：</td>
                <td colspan="2">
                  <el-input
                    v-model.number="wanCan.wailaiCount"
                    placeholder="外来就餐人数"
                    oninput="value=value.replace(/[^\d]/g,'')"
                  ></el-input>
                </td>
              </tr>
            </table>
            <div class="box2">
              <table
                border="1"
                width="920"
                cellspacing="0"
                align="center"
                style="border-color: white; margin: 0px"
              >
                <tr align="center">
                  <td>面粉<span>(kg)</span></td>
                  <td>大米<span>(kg)</span></td>
                  <td>肉类<span>(kg)</span></td>
                  <td>鱼类<span>(kg)</span></td>
                  <td>油类<span>(L)</span></td>
                  <td>黄豆<span>(kg)</span></td>
                  <td>蔬菜<span>(kg)</span></td>
                  <td>水果<span>(kg)</span></td>
                  <td>糖<span>(kg)</span></td>
                  <td>燃料<span>(L)</span></td>
                  <td>调料<span>(kg)</span></td>
                </tr>
                <tr align="center">
                  <td>
                    <el-input
                      v-model.number="wanCan.mianfen"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="wanCan.dami"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="wanCan.rolei"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="wanCan.yulei"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="wanCan.youlei"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="wanCan.huangdo"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="wanCan.suchai"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="wanCan.shuiguo"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="wanCan.tang"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="wanCan.ranliao"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                  <td>
                    <el-input
                      v-model.number="wanCan.tiaoliao"
                      placeholder=""
                      oninput="value=value.replace(/[^\d]/g,'')"
                    ></el-input>
                  </td>
                </tr>
              </table>
            </div>
          </div>

          <div class="jiben">实物检查验收</div>
          <div class="box3">
            <el-form v-for="(item, i) in acceptance" :key="i">
              <el-form-item label="" prop="">
                <el-select
                  v-model="item.distinctions"
                  placeholder="区分"
                  clearable
                >
                  <el-option
                    v-for="item in pingMing"
                    :key="item.id"
                    :label="item.name"
                    :value="item.name"
                    class="input_xiala"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-select v-model="item.name" placeholder="品名" clearable>
                  <el-option
                    v-for="item in ziDian"
                    :key="item.dictCode"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                    class="input_xiala"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input v-model="item.weight" placeholder="重量"></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item.unitPrice"
                  placeholder="单价"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item.money"
                  placeholder="金额"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model="item.types"
                  placeholder="购买或自产"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input v-model="item.remark" placeholder="备注"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="mini"
                  v-if="i == acceptance.length - 1"
                  @click="tianJia"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  @click="jtDelete(i)"
                  v-if="i != 0"
                ></el-button>
              </el-form-item>
            </el-form>
          </div>

          <div class="jiben">采购食材</div>
          <div class="box5">
            <el-form v-for="(item1, i) in caiGou" :key="i + ' '">
              <el-form-item label="" prop="">
                <el-select
                  v-model="item1.distinction"
                  placeholder="品名"
                  clearable
                >
                  <el-option
                    v-for="item in ziDian"
                    :key="item.dictCode"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                    class="input_xiala"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-date-picker
                  v-model="item1.time"
                  type="date"
                  :editable="false"
                  size="mini"
                  placeholder="选择日期"
                  value-format="yyyy-MM-dd"
                >
                </el-date-picker>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input v-model="item1.name" placeholder="品名"></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item1.amount"
                  placeholder="数量"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item1.price"
                  placeholder="价格"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model="item1.consumption"
                  placeholder="消耗品"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  @click="tianJia1"
                  v-if="i == caiGou.length - 1"
                  size="mini"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  @click="jtDelete1(i)"
                  v-if="i > 0"
                ></el-button>
              </el-form-item>
            </el-form>
          </div>

          <div class="jiben">交/退伙食费</div>
          <div class="box5">
            <el-form v-for="(item2, i) in expenses" :key="i + 's'">
              <el-form-item label="" prop="">
                <el-select v-model="item2.peopleId" placeholder="姓名">
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
                <el-select v-model="item2.type" placeholder="退交伙食费">
                  <el-option
                    v-for="item in jiao"
                    :key="item.id"
                    :label="item.name"
                    :value="item.name"
                    class="input_xiala"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item2.sky"
                  placeholder="天数"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model="item2.standard"
                  placeholder="标准"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item2.money"
                  placeholder="金额"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="mini"
                  v-if="i == expenses.length - 1"
                  @click="tianJia2"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  @click="jtDelete2(i)"
                  v-if="i > 0"
                ></el-button>
              </el-form-item>
            </el-form>
          </div>

          <div class="jiben">在食堂购物</div>
          <div class="box5">
            <el-form v-for="(item3, i) in canteen" :key="i + 'p'">
              <el-form-item label="" prop="">
                <el-select v-model="item3.peopleId" placeholder="姓名">
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
                  v-model="item3.zName"
                  placeholder="主食品名"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item3.zWeight"
                  placeholder="主食重量"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item3.zMoney"
                  placeholder="主食金额"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model="item3.fName"
                  placeholder="副食品名"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item3.fWeight"
                  placeholder="副食重量"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item3.fMoney"
                  placeholder="副食金额"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model="item3.qName"
                  placeholder="其他物品品名"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item3.qWeight"
                  placeholder="其他物品重量"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model="item3.qMoney"
                  placeholder="其他物品金额"
                ></el-input>
              </el-form-item>
              <el-form-item label="" prop="">
                <el-input
                  v-model.number="item3.total"
                  placeholder="合计"
                  oninput="value=value.replace(/[^\d]/g,'')"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="mini"
                  v-if="i == canteen.length - 1"
                  @click="tianJia3"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  @click="jtDelete3(i)"
                  v-if="i > 0"
                ></el-button>
              </el-form-item>
            </el-form>
          </div>

          <div class="jiben">后厨</div>
          <div class="box4">
            <table
              border="1"
              width="920"
              cellspacing="0"
              align="center"
              style="border-color: white; margin: 0px"
            >
              <tr align="center">
                <td colspan="2" width="150">炊事人员及食堂卫生：</td>
                <td colspan="7">
                  <el-input
                    type="textarea"
                    :rows="1"
                    placeholder="请输入内容"
                    v-model="form.contents"
                    autosize
                    resize="none"
                  >
                  </el-input>
                </td>
              </tr>
              <tr align="center">
                <td colspan="2">帮厨人员：</td>
                <td colspan="7">
                  <el-select
                    v-model="form.peopleIds"
                    multiple
                    placeholder="请选择"
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
                <td colspan="2">就餐人员意见：</td>
                <td colspan="7">
                  <el-input
                    v-model="form.repastContent"
                    placeholder="请输入内容"
                  ></el-input>
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
      <bdglfood
        v-if="menuRoleVisible"
        ref="menuRole"
        :message="wordInfo"
      ></bdglfood>
    </el-dialog>
  </div>
</template>

<script>
import bdglfood from "@/components/look_word/bdglfood.vue";
import {
  listBdglfood,
  getBdglfood,
  delBdglfood,
  addBdglfood,
  updateBdglfood,
  exportBdglfood,
} from "@/api/grassrootsregistration/bdglfood";
import { getDept } from "@/api/grassrootsregistration/bdglparty";
import {
  getZhuChiRen,
  getShouBu,
  getAll,
} from "@/api/grassrootsregistration/bdglmeeting";
// 导入树形结构
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
// 字典
import { getDicts } from "@/api/system/dict/data";
import { jieZhiList } from "@/api/grassrootsregistration/bdgldiary";

export default {
  name: "Bdglfood",
  components: {
    Treeselect,
    bdglfood,
  },
  data() {
    return {
      //查看页面
      menuRoleVisible: false,
      wordInfo: {},

      textarea: "",
      value1: "",
      //表格输入框
      input: "",
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
      // 伙食管理登记薄表格数据
      bdglfoodList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recordDate: null,
        unitId: null,
        peopleId: null,
        chairmanId: null,
        peopleId1: null,
        contents: null,
        peopleIds: null,
        repastContent: null,
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
        peopleId: [
          { required: true, message: "值班员不能为空", trigger: "blur" },
        ],
        recordDate: [
          { required: true, message: "登记日期不能为空", trigger: "blur" },
        ],
        chairmanId: [
          { required: true, message: "经委会主任不能为空", trigger: "blur" },
        ],
        peopleId1: [
          { required: true, message: "单位首长不能为空", trigger: "blur" },
        ],
      },
      //下拉
      value2: [],
      roleOptions: [],
      // 部门树形列表
      deptOptions: [],
      // 树形配置对象
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 人员列表
      renYuan: [],
      // 查询字典
      dictType: {
        dictType: "purchaseFood",
      },
      // 字典列表
      ziDian: [],
      // 一日三餐
      meal: {
        stapleFood: null,
        menu: null,
        type1: 1,
        jiucanCount: null,
        lianduiCount: null,
        wailaiCount: null,
        mianfen: null,
        dami: null,
        rolei: null,
        yulei: null,
        youlei: null,
        huangdo: null,
        suchai: null,
        shuiguo: null,
        tang: null,
        ranliao: null,
        tiaoliao: null,
      },
      // 中
      zhongCan: {
        stapleFood: null,
        menu: null,
        type1: 2,
        jiucanCount: null,
        lianduiCount: null,
        wailaiCount: null,
        mianfen: null,
        dami: null,
        rolei: null,
        yulei: null,
        youlei: null,
        huangdo: null,
        suchai: null,
        shuiguo: null,
        tang: null,
        ranliao: null,
        tiaoliao: null,
      },
      // 晚
      wanCan: {
        stapleFood: null,
        menu: null,
        type1: 3,
        jiucanCount: null,
        lianduiCount: null,
        wailaiCount: null,
        mianfen: null,
        dami: null,
        rolei: null,
        yulei: null,
        youlei: null,
        huangdo: null,
        suchai: null,
        shuiguo: null,
        tang: null,
        ranliao: null,
        tiaoliao: null,
      },
      options: [],
      //  实物检查验收
      acceptance: [
        {
          distinctions: null,
          name: null,
          weight: null,
          money: null,
          types: null,
          time: null,
        },
      ],
      // 交退伙食费
      expenses: [
        {
          peopleId: null,
          type: null,
          sky: null,
          standard: null,
          money: null,
        },
      ],
      // 在食堂购物
      canteen: [
        {
          peopleId: null,
          zName: null,
          zWeight: null,
          zMoney: null,
          total: null,
          fName: null,
          fWeight: null,
          fMoney: null,
          qName: null,
          qWeight: null,
          qMoney: null,
        },
      ],
      // 采购食物
      caiGou: [
        {
          distinction: null,
          time: null,
          name: null,
          amount: null,
          price: null,
          consumption: null,
        },
      ],
      // 交退记录
      jiao: [
        { name: "交", id: 1 },
        { name: "退", id: 2 },
      ],
      // 定义一个数组用来放对象提交
      Arr: [],
      arr: [],
      // 获取搜索单位列表
      deptOptionss: [],
      // 外面的人员
      renYuans: [],
      // 试验检查验收品名
      pingMing: [
        {
          name: "主食",
          id: 1,
        },
        {
          name: "副食",
          id: 2,
        },
        {
          name: "其他",
          id: 3,
        },
      ],
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
    this.getSuo();
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
      var date1 = Math.round(new Date(val) / 1000);

      // 获取本月第一天 00:00:00
      var now = new Date();
      var firstDay = new Date(now.getFullYear(), now.getMonth(), 1, 0, 0, 0);
      var monthStart = Math.round(firstDay.getTime() / 1000);

      // 获取本月最后一天 23:59:59
      var lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0, 23, 59, 59);
      var monthEnd = Math.round(lastDay.getTime() / 1000);

      if (this.dayTime == 1) {
        if (date1 > monthEnd || date1 < monthStart) {
          this.$confirm("请在规定时间内填写", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              this.form.recordDate = null;
            })
            .catch(() => {
              this.form.recordDate = null;
            });
        }
      }
    },
    // 搜索人员触发
    Renyuan() {
      this.getList();
    },
    // 获取搜索所有人
    getSuo() {
      getAll().then((res) => {
        this.renYuans = res.rows;
      });
    },
    // //获取部门列表
    getdept() {
      getShouBu().then((res) => {
        this.deptOptionss = res.data;
      });
    },
    // 搜索单位触发
    host() {
      this.getList();
    },
    //增加实物检查验收表单
    tianJia() {
      this.acceptance.push({
        distinctions: null,
        name: null,
        weight: null,
        money: null,
        types: null,
        time: null,
      });
    },
    //点击删除工作登记
    jtDelete(i) {
      this.acceptance.splice(i, 1);
    },
    //增加采购食材表单
    tianJia1() {
      this.caiGou.push({
        distinction: null,
        time: null,
        name: null,
        amount: null,
        price: null,
        consumption: null,
      });
    },
    //点击删除采购食材表单
    jtDelete1(i) {
      this.caiGou.splice(i, 1);
    },
    //点击添加退交伙食费表单
    tianJia2() {
      this.expenses.push({
        peopleId: null,
        type: null,
        sky: null,
        standard: null,
        money: null,
      });
    },
    //点击删除腿交伙食费
    jtDelete2(i) {
      this.expenses.splice(i, 1);
    },
    //添加在食堂购物表单
    tianJia3() {
      this.canteen.push({
        peopleId: null,
        zName: null,
        zWeight: null,
        zMoney: null,
        total: null,
        fName: null,
        fWeight: null,
        fMoney: null,
        qName: null,
        qWeight: null,
        qMoney: null,
      });
    },
    //点击删除腿交伙食费
    jtDelete3(i) {
      this.canteen.splice(i, 1);
    },
    // 让select失去焦点
    blurSelect() {
      this.$refs.aa.blur();
    },

    // 查询电子产品
    getdict() {
      getDicts(this.dictType.dictType).then((res) => {
        this.ziDian = res.data;
      });
    },
    // 首长选中触发
    SouZhang(name) {
      this.form.people1Name = name;
    },
    // 委员会主任选中触发
    ZhuRen(name) {
      this.form.chairmanName = name;
    },
    // 值班人员选中触发
    zhiBan(name) {
      this.form.peopleName = name;
    },
    // 获取人员列表
    getRen(id) {
      getZhuChiRen(id).then((res) => {
        this.renYuan = res.data;
      });
    },
    // 重置表单人员
    ChongZhiRen() {
      (this.form.peopleId1 = null), (this.form.chairmanId = null);
      this.form.peopleId = null;
      this.expenses.forEach((item) => {
        item.peopleId = null;
      });
      this.canteen.forEach((item) => {
        item.peopleId = null;
      });
    },
    // 单位选择触发
    selectPeo(val) {
      this.ChongZhiRen();
      this.form.unitId = val.id;
      this.getRen(this.form.unitId);
    },
    /** 查询伙食管理登记薄列表 */
    getList() {
      this.loading = true;
      listBdglfood(this.queryParams).then((response) => {
        this.bdglfoodList = response.rows;
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
        recordDate: null,
        unitId: null,
        peopleId: null,
        chairmanId: null,
        peopleId1: null,
        contents: null,
        peopleIds: null,
        repastContent: null,
        createtime: null,
        updatetime: null,
      };
      // 一日三餐
      this.meal = {
        stapleFood: null,
        menu: null,
        type1: 1,
        jiucanCount: null,
        lianduiCount: null,
        wailaiCount: null,
        mianfen: null,
        dami: null,
        rolei: null,
        yulei: null,
        youlei: null,
        huangdo: null,
        suchai: null,
        shuiguo: null,
        tang: null,
        ranliao: null,
        tiaoliao: null,
      };
      // 中
      this.zhongCan = {
        stapleFood: null,
        menu: null,
        type1: 2,
        jiucanCount: null,
        lianduiCount: null,
        wailaiCount: null,
        mianfen: null,
        dami: null,
        rolei: null,
        yulei: null,
        youlei: null,
        huangdo: null,
        suchai: null,
        shuiguo: null,
        tang: null,
        ranliao: null,
        tiaoliao: null,
      };
      // 晚
      this.wanCan = {
        stapleFood: null,
        menu: null,
        type1: 3,
        jiucanCount: null,
        lianduiCount: null,
        wailaiCount: null,
        mianfen: null,
        dami: null,
        rolei: null,
        yulei: null,
        youlei: null,
        huangdo: null,
        suchai: null,
        shuiguo: null,
        tang: null,
        ranliao: null,
        tiaoliao: null,
      };
      this.acceptance = [
        {
          distinctions: null,
          name: null,
          weight: null,
          money: null,
          types: null,
          time: null,
        },
      ];
      // 交退伙食费
      this.expenses = [
        {
          peopleId: null,
          type: null,
          sky: null,
          standard: null,
          money: null,
        },
      ];
      // 在食堂购物
      this.canteen = [
        {
          peopleId: null,
          zName: null,
          zWeight: null,
          zMoney: null,
          total: null,
          fName: null,
          fWeight: null,
          fMoney: null,
          qName: null,
          qWeight: null,
          qMoney: null,
        },
      ];
      this.caiGou = [
        {
          distinction: null,
          time: null,
          name: null,
          amount: null,
          price: null,
          consumption: null,
        },
      ];
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
    /** 对话框重置按钮操作 */
    resertwo() {
      this.reset();
    },
    // 获取部门下拉树形结构
    getShu() {
      getDept().then((res) => {
        this.deptOptions = res.data;
      });
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
      this.renYuan = [];
      this.reset();
      this.getShu();
      this.getdict();
      this.open = true;
      this.style = 2;
      this.title = "添加伙食管理登记薄";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getShu();
      const id = row.id || this.ids;
      getBdglfood(id).then((response) => {
        this.form = response.data;
        this.getdict();
        this.getRen(this.form.unitId);
        // 一日三餐
        this.Arr = response.data.bdglFoodMeal;
        this.Arr.forEach((item) => {
          if (item.type1 == 1) {
            this.meal = item;
          }
          if (item.type1 == 2) {
            this.zhongCan = item;
          }
          if (item.type1 == 3) {
            this.wanCan = item;
          }
        });
        // 实物检查验收
        this.acceptance = response.data.bdglFoodKinds;
        // 采购食材
        this.caiGou = response.data.bdglFoodProcurements;
        // 交/退伙食费
        if (response.data.bdglFoodBoardWagess.length > 0) {
          this.expenses = response.data.bdglFoodBoardWagess;
        } else {
          this.expenses = [
            {
              peopleId: null,
              type: null,
              sky: null,
              standard: null,
              money: null,
            },
          ];
        }

        // 在食堂购物
        this.canteen = response.data.bdglFoodShoppings;
        // 后厨
        if (response.data.peopleIds !== null) {
          if (response.data.peopleIds !== "") {
            this.form.peopleIds = response.data.peopleIds.split(",");
            this.arr = this.form.peopleIds;
            this.form.peopleIds = this.arr.map((i) => parseInt(i, 0));
          } else {
            this.form.peopleIds = null;
          }
        } else {
          this.form.peopleIds = null;
        }
        this.open = true;
        this.title = "修改伙食管理登记薄";
      });
    },
    //查看按钮操作
    handleChakan(row) {
      // 是否隐藏按钮
      this.opens = false;
      const id = row.id || this.ids;
      getBdglfood(id).then((response) => {
        this.wordInfo = response.data;
        this.title = "查看伙食管理登记薄";
        this.menuRoleVisible = true;
        // console.log(this.wordInfo);
      });
    },
    /** 提交按钮 */
    submitForm(style) {
      this.style = style;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            // 转换字符串
            if (this.form.peopleIds) {
              this.form.peopleIds = this.form.peopleIds.join(",");
            }
            // 追加元素
            this.Arr.push(this.meal);
            this.Arr.push(this.zhongCan);
            this.Arr.push(this.wanCan);
            this.form.bdglFoodMeal = this.Arr;
            // 赋值字段
            this.form.bdglFoodKinds = this.acceptance;
            // 采购食物
            this.form.bdglFoodProcurements = this.caiGou;
            // 交退伙食
            this.form.bdglFoodBoardWagess = this.expenses;
            // 在食堂购物
            this.form.bdglFoodShoppings = this.canteen;
            updateBdglfood(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            // 转换字符串
            if (this.form.peopleIds !== null) {
              this.form.peopleIds = this.form.peopleIds.join(",");
            }
            // 追加元素
            this.Arr.push(this.meal, this.zhongCan, this.wanCan);
            this.form.bdglFoodMeal = this.Arr;
            // 赋值字段
            this.form.bdglFoodKinds = this.acceptance;
            // 采购食物
            this.form.bdglFoodProcurements = this.caiGou;
            // 交退伙食
            this.form.bdglFoodBoardWagess = this.expenses;
            // 在食堂购物
            this.form.bdglFoodShoppings = this.canteen;
            addBdglfood(this.form).then((response) => {
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
        // .confirm('是否确认删除伙食管理登记薄编号为"' + ids + '"的数据项？')
        .confirm("是否确认删除该数据？")
        .then(function () {
          return delBdglfood(ids);
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
        .confirm("是否确认导出所有伙食管理登记薄数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportBdglfood(queryParams);
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
  border: 1px solid white !important;
  border-color: #fff !important;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
::v-deep .el-input__inner {
  cursor: pointer !important;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 30px 50px;
}
::v-deep .el-dialog__body {
  /* margin: 10px 30px 20px 50px; */
  /* margin: 10px 0px 20px 38px; */
  padding-top: 20px !important;
  box-sizing: border-box;
  /* padding: 30px 12px 30px 28px; */
  padding: 30px 0px 30px 64px;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
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
.el-icon-refresh {
  width: 76px;
  height: 36px;
  color: #fff;
  background-color: #1d96ff !important;
  border-radius: 4px;
  border: none;
  margin-left: 20px;
  font-size: 14px;
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
/* 计数器按钮 */
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
/* 三餐表格设置 */
.box1 {
  padding-bottom: 30px;
  margin-left: 0px;
  padding-left: 0px;
}
::v-deep .box2 td .el-input__inner {
  text-align: center;
  border: none !important;
}
/* 表格宽度 */
table td {
  border-bottom: none !important;
  margin: 0px;
  padding: 0px;
  width: 79px;
  height: 38px;
  color: #fff;
}
::v-deep .box .a1 {
  width: 200px !important;
}
::v-deep td .el-input__inner {
  background: transparent;
  color: #fff;
  border: none;
  font-size: 10px;
  border: none !important;
}
/* 实物检查验收表格 */
.box3 {
  width: 920px;
  min-height: 70px;
  border: 1px solid white;
  border-radius: 5px;
  /* background: #409eff; */
  margin-bottom: 30px;
  padding-top: 15px;
  padding-left: 15px;
}
/* 表单长度 */
::v-deep .box3 .el-form-item__content {
  width: 116px;
}
::v-deep .box3 .el-input__inner {
  width: 116px;
}
/* 添加删除按钮 */
::v-deep .box3 .el-button {
  width: 32px;
  height: 32px;
  text-align: center;
  position: relative;
  top: 0;
  left: 798px;
}
::v-deep .box3 .el-button .el-icon-plus {
  margin-left: -6px;
}
::v-deep .box3 .el-button .el-icon-delete {
  margin-left: -6px;
}

/* 实物检查验收表格 */
.box5 {
  width: 920px;
  min-height: 70px;
  border: 1px solid white;
  border-radius: 5px;
  /* background: #409eff; */
  margin-bottom: 30px;
  padding-top: 15px;
  padding-left: 15px;
}
/* 表单长度 */
::v-deep .box5 .el-form-item__content {
  width: 116px;
}
::v-deep .box5 .el-input__inner {
  width: 116px;
}
/* 添加删除按钮 */
::v-deep .box5 .el-button {
  width: 32px;
  height: 32px;
  text-align: center;
}
::v-deep .box5 .el-button .el-icon-plus {
  margin-left: -6px;
}
::v-deep .box5 .el-button .el-icon-delete {
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
/* 后厨文本框 */
::v-deep .box4 .el-textarea__inner {
  width: 704px;
  border: none;
  margin: 0px;
  padding: 5px 0px;
}
::v-deep .box4 .el-select el-select--medium {
  width: 704px !important;
  margin: 0;
  padding: 0;
}
::v-deep .box4 .el-select__tags {
  width: 704px !important;
  margin: 0;
  padding: 0;
}
::v-deep .box4 .el-input el-input--medium el-input--suffix {
  width: 704px !important;
  margin: 0;
  padding: 0;
}
::v-deep .box4 .el-input__inner {
  width: 704px !important;
  margin: 0;
  padding: 0;
  border: none !important;
}
::v-deep .box4 .el-textarea__inner {
  min-height: 100px !important;
}
table td {
  border-bottom: none !important;
  margin: 0px;
  padding: 0px;
  width: 155px;
  height: 38px;
  color: #fff;
}
/* 底部确定取消按钮 */
::v-deep .el-dialog__footer {
  padding: 18px 42px 30px 0px;
  /* margin-right: 30px; */
  margin-right: 36px;
}
/* 增加按钮弹框 */
::v-deep .el-dialog {
  width: 1060px !important;
}
::v-deep .el-dialog__body {
  padding: none !important;
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
::v-deep :-ms-input-placeholder {
  color: #bdbdbd4f !important;
}
::v-deep ::-moz-placeholder {
  color: #bdbdbd4f !important;
}
.box2 tr td span {
  font-size: 0.1em;
}
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
/* 添加内部滚动条 */
::v-deep .el-dialog__body .userAgree {
  height: 600px;
  overflow: auto;
}
/*定义滚动条宽高及背景，宽高分别对应横竖滚动条的尺寸*/
.userAgree::-webkit-scrollbar {
  width: 14px;
  /* height: 2px !important; */
  background: #3d83b8;
}
/*定义滑块，内阴影及圆角*/
.userAgree::-webkit-scrollbar-thumb {
  /* border-radius: 20px; */
  /* height: 1px;
  width: 3px; */
  background: rgba(144, 206, 252, 1);
}
/*定义滚动条的轨道，内阴影及圆角*/
/* .userAgree::-webkit-scrollbar-track {
  border-radius: 0;
  background: rgba(0, 0, 0, 0);
} */
#chakan /deep/ .el-dialog__body {
  padding: 30px 0px 30px 0px;
}
</style>
