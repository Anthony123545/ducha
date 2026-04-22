<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入文件名称"
          clearable
          size="small"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="sendName">
        <el-input
          v-model="queryParams.sendName"
          placeholder="请输入发送人"
          clearable
          size="small"
          @input="handleQuery"
        />
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
          size="mini"
          @click="handleAdd"
          v-hasPermi="['PrintsManage:manage:add']"
        >发送文件</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['PrintsManage:manage:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <div class="hezi">
      <div class="tji">集中文印统计</div>
      <div style="display: flex; flex-wrap: wrap; padding: 11px 20px">
        <div style="width: 30%; display: flex; align-items: center; height: 100px">
          <div class="div-l" style="flex: 2; text-align: right; margin-right: 30px">
            <span>打印份数</span>
          </div>
          <div class="div-r" style="flex: 1; text-align: right; margin-right: 120px">
            <span>
              <span style="font-size: 25px; margin-right: 18px">{{ tongJiList.print_number }}</span>份
            </span>
          </div>
        </div>
        <div style="width: 30%; display: flex; align-items: center; height: 100px">
          <div class="div-l" style="flex: 2; text-align: right; margin-right: 30px">
            <span>归还份数</span>
          </div>
          <div class="div-r" style="flex: 1; text-align: right; margin-right: 120px">
            <span>
              <span style="font-size: 25px; margin-right: 18px">{{ tongJiList.return_number }}</span>份
            </span>
          </div>
        </div>
        <div style="width: 30%; display: flex; align-items: center; height: 100px">
          <div class="div-l" style="flex: 2; text-align: right; margin-right: 30px">
            <span>未归还份数</span>
          </div>
          <div class="div-r" style="flex: 1; text-align: right; margin-right: 120px">
            <span>
              <span style="font-size: 25px; margin-right: 18px">{{ tongJiList.weiGuiHuan }}</span>份
            </span>
          </div>
        </div>
      </div>
    </div>

    <el-table
      v-loading="loading"
      :header-cell-style="{ background: '#003C69', color: 'white' }"
      :data="manageList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="打印回复信息" align="center" prop="id" /> -->
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="文件名称" align="center" prop="name">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.name" placement="top">
            <div
              style="
                overflow: hidden;
                display: -webkit-box;
                text-overflow: ellipsis;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                white-space: normal;
              "
            >{{ scope.row.name }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <!-- <el-table-column label="路径" align="center" prop="file" /> -->
      <el-table-column label="类型" align="center" prop="type" width="50" />
      <el-table-column label="是否彩打" align="center" prop="isPrintColor" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.isPrintColor == 0 ? "否" : "是" }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column
        label="是否双面打"
        align="center"
        prop="isPrintTwo"
        width="100"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.isPrintTwo == 0 ? "否" : "是" }}</span>
        </template>
      </el-table-column>-->
      <el-table-column label="打印状态" align="center" prop="printStatus" width="100">
        <template slot-scope="scope">
          <span
            style="color: #90da90"
            v-if="
              (scope.row.printStatus == 1 &&
                scope.row.printRoom == 0 &&
                roles == '15号文印室') ||
              (scope.row.printStatus == 1 &&
                scope.row.printRoom == 1 &&
                roles == '19号文印室')
            "
          >待打印</span>

          <span
            style="color: #90da90"
            v-if="
              scope.row.printStatus == 1 &&
              roles != '19号文印室' &&
              roles != '15号文印室'
            "
          >打印中</span>
          <span style="color: #00ff00" v-if="scope.row.printStatus == 2">已打印</span>
        </template>
      </el-table-column>
      <el-table-column label="级别" align="center" prop="levelM" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.level" :value="scope.row.levelM" />
        </template>
      </el-table-column>
      <el-table-column label="发送人" align="center" prop="sendName" width="80" />
      <el-table-column label="发送时间" align="center" prop="creattime" width="180">
        <template slot-scope="scope">
          <span>
            {{
            parseTime(scope.row.creattime, "{y}-{m}-{d} {h}:{i}:{s}")
            }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="归还时间" align="center" prop="returnTime" width="180">
        <template slot-scope="scope">
          <span>
            {{
            parseTime(scope.row.returnTime, "{y}-{m}-{d} {h}:{i}:{s}")
            }}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        label="备注"
        align="center"
        prop="explainInfo"
        width="120px"
        show-overflow-tooltip
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="280px"
      >
        <template slot-scope="scope">
          <el-button
            size="btu"
            type="text"
            @click="handleok(scope.row)"
            v-hasPermi="['PrintsLog:printlog:edit']"
            v-if="scope.row.isShow == 1"
          >确定</el-button>
          <el-button
            size="btk"
            type="text"
            @click="handlelook(scope.row)"
            v-hasPermi="['PrintsLog:printlog:edit']"
          >查看</el-button>
          <el-button
            size="btk"
            type="text"
            @click="handlePrint(scope.row)"
            v-hasPermi="['PrintsLog:printlog:edit']"
          >打印</el-button>
          <el-button
            size="btu"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['PrintsManage:manage:edit']"
          >修改</el-button>
          <el-button
            size="btd"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['PrintsManage:manage:remove']"
          >删除</el-button>
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

    <!-- 添加或修改集中文印主对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1010px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="userAgree">
        <el-form
          ref="form"
          :model="form"
          :inline="true"
          :rules="rules"
          label-width="120px"
          style="margin-left: 34px"
        >
          <div class="jiben">发送人信息</div>
          <el-form-item label="发送人" prop="sendName" class="box1">
            <el-input v-model="form.sendName" disabled placeholder="请输入发送人" />
          </el-form-item>
          <el-form-item label="文印室" prop="printRoom" class="box1">
            <el-select v-model="form.printRoom" clearable placeholder="请选择文印室">
              <el-option
                v-for="dict in printRooms"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="归还时间" prop="returnTime" class="box1">
            <el-date-picker
              clearable
              size="small"
              v-model="form.returnTime"
              type="datetime"
              placeholder="选择归还时间"
              class="text"
            ></el-date-picker>
          </el-form-item>
          <div class="jiben">打印说明</div>
          <el-form-item label prop="explainInfo">
            <el-input
              v-model="form.explainInfo"
              type="textarea"
              placeholder="请输入打印说明"
              class="textarea-explainInfo"
            />
          </el-form-item>
          <div class="jiben">文件信息</div>
          <div class="box3" v-for="(item, i) in tomList" :key="i">
            <el-form
              ref="refform"
              :model="form"
              :rules="rules"
              label-width="80px"
              :inline="true"
              style="margin-left: 20px"
            >
              <el-form-item prop="type">
                <el-input class="forEach" disabled v-model="item.type" placeholder="请输入文件类型" />
                <!-- <el-select v-model="form.type" placeholder="请选择类型">
            <el-option label="请选择字典生成" value />
                </el-select>-->
              </el-form-item>
              <el-form-item prop="size">
                <el-input class="forEach" disabled v-model="item.size" placeholder="请输入文件大小" />
              </el-form-item>
              <el-form-item prop="levelM">
                <el-select v-model="item.levelM" clearable class="forEach" placeholder="请选择级别">
                  <el-option
                    v-for="dict in levelMs"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="parseInt(dict.dictValue)"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item prop="printNumber">
                <el-input
                  class="forEach"
                  v-model="item.printNumber"
                  :min="0"
                  placeholder="请输入打印份数"
                />
              </el-form-item>
              <el-form-item>
                <el-button
                  type="danger"
                  icon="el-icon-close"
                  size="mini"
                  style="padding: 11px 10px 10px 10px"
                  v-if="i >= 0"
                  @click="delForms(i)"
                ></el-button>
              </el-form-item>
              <el-form-item>
                <el-input
                  class="fileName"
                  suffix-icon="el-icon-document"
                  disabled
                  v-model="item.name"
                  placeholder="请输入文件名称"
                />
              </el-form-item>
              <el-form-item label="是否为彩打" label-width="120px" prop="isPrintColor">
                <el-switch
                  v-model="item.isPrintColor"
                  active-color="#13ce66"
                  inactive-color="#ccc"
                  active-value="1"
                  inactive-value="0"
                ></el-switch>
                <span class="greed" v-if="item.isPrintColor == '1'"></span>
                <span class="CCC" v-if="item.isPrintColor == '0'"></span>
              </el-form-item>
              <el-form-item
                label="是否为双面打"
                label-width="120px"
                prop="isPrintTwo"
                style="margin-right: 100px"
              >
                <el-switch
                  v-model="item.isPrintTwo"
                  active-color="#13ce66"
                  inactive-color="#ccc"
                  active-value="1"
                  inactive-value="0"
                ></el-switch>
                <span class="greed" v-if="item.isPrintTwo == '1'"></span>
                <span class="CCC" v-if="item.isPrintTwo == '0'"></span>
              </el-form-item>
            </el-form>
          </div>
          <el-form-item style="margin-left: 20px" prop="name">
            <!-- <fileUpload v-model="form.file" /> -->
            <el-upload
              :action="uploadFileUrl"
              :before-upload="handleBeforeUpload"
              :file-list="fileList"
              :limit="limit"
              :on-error="handleUploadError"
              :on-exceed="handleExceed"
              :on-success="handleUploadSuccess"
              :show-file-list="false"
              :headers="headers"
              class="upload-file-uploader"
              ref="upload"
              style="width: 200px"
              name="pfile"
            >
              <!-- 上传按钮 -->
              <el-button size="mini" type="primary" class="select_file">选取文件</el-button>
            </el-upload>
          </el-form-item>
          <!-- <el-form-item label="发送人ID" prop="sendId">
          <el-input v-model="form.sendId" placeholder="请输入发送人ID" />
          </el-form-item>-->
          <!-- <el-form-item label="创建时间" prop="creattime">
          <el-date-picker
            clearable
            size="small"
            v-model="form.creattime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择创建时间"
          ></el-date-picker>
          </el-form-item>-->

          <!-- <el-form-item label="打印状态(1 打印中  2 已打印)">
          <el-radio-group v-model="form.printStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="接收人ID" prop="receiveId">
          <el-input v-model="form.receiveId" placeholder="请输入接收人ID" />
        </el-form-item>
        <el-form-item label="接收人" prop="receiveName">
          <el-input v-model="form.receiveName" placeholder="请输入接收人" />
        </el-form-item>
        <el-form-item label="生成路径">
          <fileUpload v-model="form.sFile" />
        </el-form-item>
        <el-form-item label="打印回复信息" prop="printInfo">
          <el-input v-model="form.printInfo" placeholder="请输入打印回复信息" />
          </el-form-item>-->
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer" style="margin-right: 30px">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改集中文印 -->
    <el-dialog
      :title="title1"
      :visible.sync="open1"
      width="1010px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="form1"
        :model="form1"
        :inline="true"
        :rules="ruless"
        label-width="120px"
        style="margin-left: 37px"
      >
        <div class="jiben">发送人信息</div>
        <el-form-item label="发送人" prop="sendName" class="box1">
          <el-input v-model="form1.sendName" disabled placeholder="请输入发送人" />
        </el-form-item>
        <el-form-item label="文印室" prop="printRoom" class="box1">
          <el-select v-model="form1.printRoom" clearable placeholder="请选择文印室">
            <el-option
              v-for="dict in printRooms"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="归还时间" prop="returnTime" class="box1">
          <el-date-picker
            clearable
            size="small"
            v-model="form1.returnTime"
            type="datetime"
            placeholder="选择归还时间"
            class="text"
          ></el-date-picker>
          <!-- <el-date-picker
          v-model="form1.returnTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择日期">
          </el-date-picker>-->
        </el-form-item>
        <div class="jiben">打印说明</div>
        <el-form-item label prop="explainInfo">
          <el-input
            v-model="form1.explainInfo"
            type="textarea"
            placeholder="请输入打印说明"
            class="textarea-explainInfo1"
          />
        </el-form-item>
        <div class="jiben">文件信息</div>
        <div style="margin-left: 20px">
          <el-form-item prop="type" style="margin-right: 23px">
            <el-input class="forEach" disabled v-model="form1.type" placeholder="请输入文件类型" />
            <!-- <el-select v-model="form.type" placeholder="请选择类型">
          <el-option label="请选择字典生成" value />
            </el-select>-->
          </el-form-item>
          <el-form-item prop="size" style="margin-right: 23px">
            <el-input class="forEach" disabled v-model="form1.size" placeholder="请输入文件大小" />
          </el-form-item>
          <el-form-item prop="levelM" style="margin-right: 23px">
            <el-select v-model="form1.levelM" clearable class="forEach" placeholder="请选择级别">
              <el-option
                v-for="dict in levelMs"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="printNumber">
            <el-input class="forEach" v-model="form1.printNumber" :min="0" placeholder="请输入打印份数" />
          </el-form-item>
          <el-form-item>
            <el-input
              class="fileName"
              suffix-icon="el-icon-document"
              disabled
              v-model="form1.name"
              placeholder="请输入文件名称"
            />
          </el-form-item>
          <el-form-item label="是否为彩打" label-width="120px" prop="isPrintColor">
            <el-switch
              v-model="form1.isPrintColor"
              active-color="#13ce66"
              inactive-color="#ccc"
              active-value="1"
              inactive-value="0"
            ></el-switch>
            <span class="greed" v-if="form1.isPrintColor == '1'"></span>
            <span class="CCC" v-if="form1.isPrintColor == '0'"></span>
          </el-form-item>
          <el-form-item
            label="是否为双面打"
            label-width="120px"
            prop="isPrintTwo"
            style="margin-right: 200px"
          >
            <!-- <el-radio-group
              v-model="item.isPrintTwo"
              style="width: 200px"
              size="mini"
            >
              <el-radio label="1">是</el-radio>
              <el-radio label="0">否</el-radio>
            </el-radio-group>-->
            <el-switch
              v-model="form1.isPrintTwo"
              active-color="#13ce66"
              inactive-color="#ccc"
              active-value="1"
              inactive-value="0"
            ></el-switch>
            <span class="greed" v-if="form1.isPrintTwo == '1'"></span>
            <span class="CCC" v-if="form1.isPrintTwo == '0'"></span>
          </el-form-item>
          <!-- <el-form-item label="是否需归还" label-width="120px" prop="isReturns">
            <el-switch
              v-model="form1.isReturn"
              active-color="#13ce66"
              inactive-color="#ccc"
              :active-value="1"
              :inactive-value="0"
            >
            </el-switch>
            <span class="greed" v-if="form1.isReturn == 1"></span>
            <span class="CCC" v-if="form1.isReturn == 0"></span>
          </el-form-item>-->
          <el-form-item prop="name">
            <!-- <fileUpload v-model="form.file" /> -->
            <el-upload
              :action="uploadFileUrl"
              :before-upload="handleBeforeUpload"
              :file-list="fileList"
              :limit="limit"
              :on-error="handleUploadError"
              :on-exceed="handleExceed"
              :on-success="handleUploadSuccess"
              :show-file-list="false"
              :headers="headers"
              class="upload-file-uploader"
              ref="upload"
              style="width: 200px"
              name="pfile"
            >
              <!-- 上传按钮 -->
              <el-button size="mini" type="primary" class="select_file">选取文件</el-button>
            </el-upload>
          </el-form-item>
        </div>
      </el-form>

      <div slot="footer" class="dialog-footer" style="margin-right: 18px">
        <el-button type="primary" @click="updatesubmit">确 定</el-button>
        <el-button @click="open1 = false" size="btn">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :title="title"
      :visible.sync="dyopen"
      append-to-body
      width="700px"
      style="padding-top: 100px"
      :close-on-click-modal="false"
      @close="close"
    >
      <el-form ref="dyform" :model="dyform" :inline="true" :rules="rules" label-width="120px">
        <div class="jiben jiben2">打印信息</div>
        <el-form-item label="打印文件" prop="printName">
          <el-input v-model="dyform.printName" placeholder="请输入打印文件" />
        </el-form-item>
        <el-form-item label="打印份数" prop="printNumber">
          <el-input v-model="dyform.printNumber" :min="0" placeholder="请输入打印份数" />
        </el-form-item>

        <!-- <el-form-item label="是否需要归还" prop="isReturn">
          <el-radio-group
            v-model="dyform.isReturn"
            style="width: 200px"
            size="mini"
            @change="dianji"
          >
            <el-radio label="1">归还</el-radio>
            <el-radio label="0">无需归还</el-radio>
          </el-radio-group>
        </el-form-item>-->
        <!-- <el-form-item label="是否为彩打" prop="isPrintColor">
          <el-radio-group v-model="dyform.isPrintColor" style="width:200px;" size="mini">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否为双面打" prop="isPrintTwo">
          <el-radio-group v-model="dyform.isPrintTwo" style="width:200px;" size="mini">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>-->
        <!-- <div class="jiben">打印人信息</div> -->
        <el-form-item label="打印人" prop="printfileName">
          <el-select v-model="dyform.printfileName" filterable clearable placeholder="请选择打印人">
            <el-option v-for="item in rylist" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <div class="jiben jiben2">打印说明</div>
        <div
          style="
            width: 98.5%;
            height: 45px;
            font-size: 14px;
            line-height: 45px;
            color: #fff;
            border-bottom: 1px solid #fff;
            margin-bottom: 20px;
            padding-left: 15px;
          "
          v-if="yaoqiuflg"
        >其他要求：{{ yaoqiu }}</div>
        <el-form-item label prop="explainInfo">
          <el-input
            v-model="dyform.explainInfo"
            type="textarea"
            placeholder="请输入打印说明"
            class="dyform-textarea"
          />
        </el-form-item>
        <div class="jiben jiben2" v-if="ramrk">备注</div>
        <el-form-item label prop v-if="ramrk">
          <el-input
            v-model="dyform.remark"
            type="textarea"
            placeholder="请输入备注"
            class="dyform-textarea"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dysubmitForm">打 印</el-button>
        <el-button @click="dycancel" size="btn">取 消</el-button>
      </div>
    </el-dialog>
    <!-- <el-dialog
      :title="title"
      :visible.sync="dyopen2"
      append-to-body
      width="1500px"
      style="padding-top:100px;"
    >
      <div ref="file" v-html="tableau" style="width:100%;"></div>
    </el-dialog>-->

    <!-- 添加预览doc文件弹框 -->
    <el-dialog
      style="color: pink"
      :title="title3"
      :visible.sync="open33"
      width="1100px"
      append-to-body
      id="fileinfodocx"
      class="el-dialog__header"
      :close-on-click-modal="false"
    >
      <div ref="file"></div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm3">关 闭</el-button>
        <!-- <el-button @click="open33 = false" size="" class="aa1">
          取 消</el-button
        >-->
      </div>
    </el-dialog>

    <!-- 预览PDF文件弹框 -->
    <el-dialog
      :title="title3"
      :visible.sync="previewDialog"
      width="850px"
      append-to-body
      id="fileinfopdf"
      class="el-dialog__header"
      :close-on-click-modal="false"
    >
      <template>
        <!-- <div style="margin-bottom:20px"> -->
        <div style="display: flex">
          <el-button
            :class="gopage == true ? 'bg' : ''"
            class="gopage"
            :theme="'default'"
            type="submit"
            :title="'上一页'"
            @click.native="prePage"
          >上一页</el-button>
          <div
            style="
              width: 50px;
              height: 28px;
              background: #f4f4f4;
              text-align: center;
              line-height: 28px;
            "
          >{{ pageNum }}/{{ pageTotalNum }}</div>
          <el-button
            :class="gopage == false ? 'bg' : ''"
            :theme="'default'"
            class="downpage"
            type="submit"
            :title="'下一页'"
            @click.native="nextPage"
          >下一页</el-button>
          <!-- <el-button :theme="'default'" type="submit" :title="'顺时针旋转'" @click.native="clock" > 顺时针旋转</el-button>
            <el-button :theme="'default'" type="submit" :title="'逆时针旋转'" @click.native="counterClock" > 逆时针旋转</el-button>
          <el-button :theme="'default'" type="submit" :title="'打印'" @click.native="pdfPrintAll"> 打印</el-button>-->
        </div>
        <PDF
          ref="pdf"
          :src="pdfurl"
          :page="pageNum"
          :rotate="pageRotate"
          @progress="loadedRatio = $event"
          @page-loaded="pageLoaded($event)"
          @num-pages="pageTotalNum = $event"
          @error="pdfError($event)"
          @link-clicked="page = $event"
        ></PDF>
        <!-- </div> -->
        <!-- <div class="el-dialog__footer"> -->
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="previewDialog = false">关 闭</el-button>
          <!-- <el-button @click="previewDialog = false" size="" class="aa1"
            >取 消</el-button
          >-->
        </div>
        <!-- </div> -->
      </template>
    </el-dialog>

    <el-dialog
      :title="title3"
      :visible.sync="open44"
      width="1100px"
      append-to-body
      id="fileinfopdf"
      class="el-dialog__header"
      :close-on-click-modal="false"
    >
      <template>
        <div class="home">
          <div v-html="tableau"></div>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="open44 = false">关 闭</el-button>
          <!-- <el-button @click="open44 = false" size="" class="aa1"
            >取 消</el-button
          >-->
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  listManage,
  getManage,
  delManage,
  addManage,
  updateManage,
  exportManage,
  printFile,
  listPeople,
  printCode,
  tkbox,
  printCount
} from "@/api/PrintsManage/manage";
import { rizongwenyin } from "@/api/taishi/taishi";
import { getToken } from "@/utils/auth";
import Cookies from "js-cookie";
import axios from "axios";
var XLSX = require("xlsx");
const docx = require("docx-preview");
window.JSZip = require("jszip");
import PDF from "vue-pdf";
export default {
  name: "Manage",
  dicts: ["level", "print_room"],
  props: {
    // 值
    value: [String, Object, Array],
    // 数量限制
    limit: {
      type: Number,
      default: 10
    },
    // 大小限制(MB)
    fileSize: {
      type: Number,
      default: 50
    },
    // 文件类型, 例如['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => ["doc", "xls", "ppt", "pdf"]
    },
    // 是否显示提示
    isShowTip: {
      type: Boolean,
      default: true
    }
  },
  components: {
    PDF
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
      // 集中文印主表格数据
      manageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dyopen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        file: null,
        type: null,
        size: null,
        unitId: null,
        unitName: null,
        levelM: null,
        sendId: null,
        sendName: null,
        creattime: null,
        updatetime: null,
        explainInfo: null,
        printStatus: null,
        receiveId: null,
        receiveName: null,
        sFile: null,
        printInfo: null,
        printRoom: null
      },
      // 表单参数
      form: {
        printsManageList: []
      },
      dyform: {},
      // 表单校验
      rules: {
        printRoom: [
          { required: true, message: "打印室不能为空", trigger: "change" }
        ],
        sendName: [
          { required: true, message: "发送人不能为空", trigger: "change" }
        ],
        printName: [
          { required: true, message: "打印文件不能为空", trigger: "change" }
        ],
        printfileName: [
          { required: true, message: "打印人不能为空", trigger: "change" }
        ],
        returnTime: [
          { required: true, message: "归还时间不能为空", trigger: "change" }
        ]
      },
      ruless: {
        returnTime: [
          { required: true, message: "归还时间不能为空", trigger: "change" }
        ]
      },
      //级别
      levelMs: [],
      //文印室
      printRooms: [],
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadFileUrl:
        process.env.VUE_APP_BASE_API + "/PrintsManage/manage/printFile", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken()
      },
      fileList: [],
      faith: null,
      rylist: [],
      islist: [],
      yaoqiu: "",
      dyopen2: false,
      tableau: null,
      yaoqiuflg: false,
      roles: "",
      // 虚拟字段
      fid: "",
      open33: false,
      title3: "预览文件",
      // 下载文件路径
      downurl: process.env.VUE_APP_BASE_API,
      // pdf文件
      previewDialog: false,
      pdfurl: "",
      pageNum: 1,
      pageTotalNum: 1,
      pageRotate: 0,
      // 加载进度
      loadedRatio: 0,
      curPageNum: 0,
      gopage: true,
      // excel表格
      tableau: null,
      open44: false,
      // 确定按钮
      printshow: false,
      printManage: {},
      //添加文件为空
      tomList: [
        {
          // 文件名称
          name: null,
          // 打印份数
          printNumber: null,
          // 文件大小
          size: null,
          // 级别
          levelM: null,
          // 是否为彩打
          isPrintColor: null,
          // 是否为双面打
          isPrintTwo: null
        }
      ],
      // 上传文件
      tomLists: [],
      form1: {},
      open1: false,
      title1: "",
      // 打印备注
      ramrk: false,
      tongJiList: []
    };
  },
  created() {
    this.getList();
    //级别
    this.getDicts("level").then(response => {
      this.levelMs = response.data;
    });
    //文印室
    this.getDicts("print_room").then(response => {
      this.printRooms = response.data;
    });
    //是否
    this.getDicts("sys_is").then(response => {
      this.islist = response.data;
    });
    //集中文印
    rizongwenyin().then(res => {
      console.log(res);
      // this.wylist = res.data;
    });
    printCount().then(res => {
      this.tongJiList = res.data;
    });
  },
  mounted() {
    this.roles = Cookies.get("roles");
  },
  methods: {
    // 点击上传文件触发
    fileChange(data) {},
    // 打印点击叉号关闭事件
    close() {
      this.ramrk = false;
    },
    // 点击是否需要归还触发
    dianji(data) {
      if (data == "1") {
        this.ramrk = false;
      } else if (data == "0") {
        this.ramrk = true;
      }
    },
    // 增加工作登记
    addForms() {
      this.tomList.push({
        // 文件名称
        name: null,
        // 打印份数
        printNumber: null,
        // 文件大小
        size: null,
        // 级别
        levelM: null,
        // 是否为彩打
        isPrintColor: null,
        // 是否为双面打
        isPrintTwo: null
      });
    },
    // 删除对话框工作登记
    delForms(i) {
      this.tomList.splice(i, 1);
    },
    /** 查询集中文印主列表 */
    getList() {
      this.loading = true;
      listManage(this.queryParams).then(response => {
        this.manageList = response.rows;

        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    dycancel() {
      this.dyopen = false;
      this.ramrk = false;
      this.dyreset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        file: null,
        type: null,
        size: null,
        unitId: null,
        unitName: null,
        levelM: null,
        sendId: null,
        sendName: null,
        creattime: null,
        updatetime: null,
        explainInfo: null,
        printStatus: 0,
        receiveId: null,
        receiveName: null,
        sFile: null,
        printInfo: null,
        isReturn: null,
        printNumber: null,
        isPrintTwo: null,
        isPrintColor: null,
        printRoom: null
      };
      this.resetForm("form");
    },
    // 表单重置
    dyreset() {
      this.dyform = {
        id: null,
        printName: null,
        printNumber: null,
        isReturn: null,
        printfileName: null,
        explainInfo: null,
        file: null,
        printId: null,
        statusG: null,
        isPrintTwo: null,
        isPrintColor: null,
        printRoom: null
      };
      this.resetForm("dyform");
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      (this.tomList = [
        {
          // 文件名称
          name: null,
          // 打印份数
          printNumber: null,
          // 文件大小
          size: null,
          // 级别
          levelM: null,
          // 是否为彩打
          isPrintColor: null,
          // 是否为双面打
          isPrintTwo: null
        }
      ]),
        (this.fileList = []);
      this.reset();
      this.open = true;
      this.title = "添加集中文印";
      this.form.sendName = Cookies.get("nickName");
      this.form.sendId = Cookies.get("userId");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // this.tomList=[]
      this.tomList = [
        {
          // 文件名称
          name: null,
          // 打印份数
          printNumber: null,
          // 文件大小
          size: null,
          // 级别
          levelM: null,
          // 是否为彩打
          isPrintColor: null,
          // 是否为双面打
          isPrintTwo: null,
          isReturn: null
        }
      ];
      this.fileList = [];
      this.reset();
      const id = row.id || this.ids;
      getManage(id).then(response => {
        this.form1 = response.data;
        this.form1.isPrintColor = response.data.isPrintColor + "";
        this.form1.isPrintTwo = response.data.isPrintTwo + "";
        // this.tomList[0].name=this.form.name
        // this.tomList[0].type=this.form.type
        // this.tomList[0].file=this.form.file
        // this.tomList[0].printNumber=this.form.printNumber
        // this.tomList[0].size=this.form.size
        // this.tomList[0].levelM=this.form.levelM
        //   this.tomList[0].isPrintColor=this.form.isPrintColor
        // this.tomList[0].isPrintTwo=this.form.isPrintTwo
        this.open1 = true;
        this.title1 = "修改集中文印";
      });
    },
    // 修改提交
    updatesubmit() {
      this.$refs["form1"].validate(valid => {
        if (valid) {
          this.form1.isReturn = 1;
          updateManage(this.form1).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open1 = false;
            this.getList();
          });
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateManage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.printsManageList = this.tomList;
            var num = 0;
            var num1 = this.tomList.length;
            for (let index = 0; index < this.tomList.length; index++) {
              this.tomList[index].isReturn = 1;
              if (
                this.tomList[index].levelM &&
                this.tomList[index].printNumber
              ) {
                num += 1;
              } else {
                return this.$modal.msgWarning("请选择级别,份数");
              }
            }
            this.form.printsManageList = this.tomList;
            // this.tomList.forEach((item,i)=>{
            //   if(item.levelM){
            //     num+=1
            //   }else{
            //      return this.$modal.msgWarning("请选择级别");
            //   }
            // })
            if (num1 == num) {
              addManage(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除该数据项？")
        .then(function() {
          return delManage(ids);
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
        .confirm("是否确认导出所有集中文印主数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportManage(queryParams);
        })
        .then(response => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      // if (this.tomList.length > 1) {
      //   console.log(this.tomList);
      //    this.$message.error("上传失败, 请重试");
      //  callback(new Error('请填写必填项'))
      // } else {
      // 校检文件类型
      this.tomLists.name = file.name;
      this.form1.name = file.name;
      if (file.size == 0) {
        this.tomLists.size = "0 B";
        this.form1.size = "0 B";
      }
      var k = 1024;
      var sizes = ["B", "KB", "MB", "GB", "TB", "EB", "ZB", "YB"];
      var i = Math.floor(Math.log(file.size) / Math.log(k));
      this.tomLists.size = file.size;
      this.form1.size = file.size;
      this.tomLists.size =
        (file.size / Math.pow(k, i)).toPrecision(3) + "" + sizes[i];
      this.form1.size =
        (file.size / Math.pow(k, i)).toPrecision(3) + "" + sizes[i];
      if (this.fileType) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
          this.tomLists.type = fileExtension;
          this.form1.type = fileExtension;
        }
        const isTypeOk = this.fileType.some(type => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
        if (!isTypeOk) {
          this.$message.error(
            `文件格式不正确, 请上传${this.fileType.join("/")}格式文件!`
          );
          this.tomLists.name = null;
          this.tomLists.size = null;
          this.tomLists.type = null;
          this.form1.name = null;
          this.form1.size = null;
          this.form1.type = null;
          return false;
        }
      }
      // 校检文件大小
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$message.error(`上传文件大小不能超过 ${this.fileSize} MB!`);
          return false;
        }
      }
      return true;
      // }
    },
    // 文件个数超出
    handleExceed() {
      this.$message.error(`上传文件数量不能超过 ${this.limit} 个!`);
    },
    // 上传失败
    handleUploadError(err) {
      this.$message.error("上传失败, 请重试");
    },
    // 上传成功回调
    handleUploadSuccess(res, file) {
      this.$message.success("上传成功");
      this.fileList.push({
        name: file.name,
        file: res.url,
        size: this.tomLists.size,
        type: this.tomLists.type
      });

      this.$emit("input", this.fileList);

      this.tomList = this.fileList;
      this.form1.file = res.url;
      // this.tomList.push(this.form)
    },
    //打印
    handlePrint(row) {
      this.dyreset();
      this.dyopen = true;
      this.title = "打印";
      this.dyform.printName = row.name;
      this.dyform.explainInfo = row.explainInfo;
      this.dyform.printId = row.id;
      this.dyform.file = row.file;
      this.dyform.statusG = 0;
      this.dyform.printfileName = row.sendName;
      this.dyform.printNumber = row.printNumber;
      this.dyform.printRoom = row.printRoom;
      this.dyform.type = row.type;
      this.dyform.isReturn = row.isReturn;
      this.dyform.isReturn = row.isReturn + "";
      if (row.isReturn == "1") {
        this.ramrk = false;
      }
      if (row.isReturn == "0") {
        this.ramrk = true;
      }

      if (row.isPrintColor == 1) {
        this.yaoqiu = "彩打";
        this.yaoqiuflg = true;
      }
      if (row.isPrintTwo == 1) {
        this.yaoqiuflg = true;
        if (this.yaoqiu) {
          this.yaoqiu += " 双面打";
        } else {
          this.yaoqiu = "双面打";
        }
      }
      if (row.isPrintTwo != 1 && row.isPrintColor != 1) {
        this.yaoqiuflg = false;
      }
      this.getrylist();
    },
    // 查看按钮预览
    handlelook(row) {
      if (row.type == "docx" || row.type == "doc") {
        this.open33 = true;
        axios({
          method: "get",
          responseType: "blob", // 设置响应文件格式
          url: row.file
        }).then(({ data }) => {
          $(".docx").attr("id", "printArea");

          docx.renderAsync(data, this.$refs.file); // 渲染到页面预览
        });
      } else if (row.type == "pdf") {
        this.previewDialog = true;
        this.pdfurl = row.file;
        this.pageNum = 1;
      } else if (row.type == "xlsx") {
        axios
          .get(row.file, {
            responseType: "arraybuffer" // 设置响应体类型为arraybuffer
          })
          .then(({ data }) => {
            let workbook = XLSX.read(new Uint8Array(data), { type: "array" }); // 解析数据
            this.open44 = true;
            var worksheet = workbook.Sheets[workbook.SheetNames[0]]; // workbook.SheetNames 下存的是该文件每个工作表名字,这里取出第一个工作表
            this.tableau = XLSX.utils.sheet_to_html(worksheet); // 渲染
          });
      }
    },
    // 预览弹框
    submitForm3() {
      this.open33 = false;
    },
    //获取人员
    getrylist() {
      listPeople().then(res => {
        if (res.code == 200) {
          this.rylist = res.rows;
        }
      });
    },
    //打印提交
    dysubmitForm() {
      this.$refs["dyform"].validate(valid => {
        if (valid) {
          //
          console.log(this.dyform);
          if (this.dyform.isReturn == 1) {
            this.dyform.statusG = 0;
          } else {
            this.dyform.statusG = 1;
          }
          printCode(this.dyform).then(res => {
            if (res.code == 200) {
              if (this.dyform.type == "pdf") {
                // let blob = new Blob([res]);
                // let objectUrl = URL.createObjectURL(blob);
                // let link = document.createElement("a");
                // link.download =this.dyform.file;
                // link.href = objectUrl;
                // link.click();
                // link.remove();
                let xhr = new XMLHttpRequest();
                xhr.open(
                  "get",
                  process.env.VUE_APP_BASE_API +
                    "/profile/print/" +
                    res.printFileName,
                  true
                );
                xhr.setRequestHeader("Content-Type", `application/pdf`);
                xhr.responseType = "blob";
                let that = this;
                xhr.onload = function() {
                  if (this.status == 200) {
                    //接受二进制文件流
                    var blob = this.response;
                    // that.downloadExportFile(blob, fileName);
                    let downloadElement = document.createElement("a");
                    let href = blob;
                    if (typeof blob == "string") {
                      downloadElement.target = "_blank";
                    } else {
                      href = window.URL.createObjectURL(blob); //创建下载的链接
                    }
                    downloadElement.href = href;

                    downloadElement.download = that.dyform.printName;
                    // tagFileName +
                    //下载后文件名
                    document.body.appendChild(downloadElement);
                    downloadElement.click(); //点击下载
                    document.body.removeChild(downloadElement); //下载完成移除元素
                    if (typeof blob != "string") {
                      window.URL.revokeObjectURL(href); //释放掉blob对象
                    }
                  }
                };
                xhr.send();
                this.dyopen = false;
                this.ramrk = false;
                this.getList();
              } else {
                let downloadElement = document.createElement("a");
                downloadElement.href =
                  process.env.VUE_APP_BASE_API +
                  "/profile/print/" +
                  res.printFileName;
                downloadElement.download = this.dyform.printName;
                document.body.appendChild(downloadElement);
                downloadElement.click(); //点击下载
                document.body.removeChild(downloadElement);
                this.dyopen = false;
                this.ramrk = false;
                this.printshow = true;
                this.getList();
              }
              // var open=window.open();
              //  location.href =res.file;
            }
          });
        }
      });

      // open.location.href="http://192.168.5.188:8080/priofile/print/202203211244.docx";
      // window.open( `https://view.officeapps.live.com/op/view.aspx?src=http://192.168.5.188:8080/profile/print/202203211428.docx`, "_blank");
    },
    // 确定
    handleok(row) {
      // this.dyform.printName = row.name;
      // this.dyform.explainInfo = row.explainInfo;
      // this.dyform.printId = row.id;
      // this.dyform.file = row.file;
      // this.dyform.statusG = 0;
      // this.dyform.printfileName = row.sendName;
      // this.dyform.printNumber = row.printNumber;
      // this.dyform.printRoom = row.printRoom;
      // this.dyform.type = row.type;

      this.printManage = row;

      tkbox(this.printManage).then(res => {
        this.printshow = false;
        this.getList();
      });
    },
    /**
     * 预览PDF
     */
    previewPDF(row, index) {
      this.previewDialog = true;
    },
    // 上一页函数，
    prePage() {
      this.gopage = true;
      var page = this.pageNum;
      page = page > 1 ? page - 1 : this.pageTotalNum;
      this.pageNum = page;
    },
    // 下一页函数
    nextPage() {
      this.gopage = false;
      var page = this.pageNum;
      page = page < this.pageTotalNum ? page + 1 : 1;
      this.pageNum = page;
    },
    // 页面顺时针翻转90度。
    clock() {
      this.pageRotate += 90;
    },
    // 页面逆时针翻转90度。
    counterClock() {
      this.pageRotate -= 90;
    },
    // 页面加载回调函数，其中e为当前页数
    pageLoaded(e) {
      this.curPageNum = e;
    },
    // 错误时回调函数。
    pdfError(error) {
      console.error(error);
    },
    // 打印全部
    pdfPrintAll() {
      /**
       * 打印界面字符乱码是因为你pdf中使用了自定义字体导致的,谷歌浏览器打印的时候预览界面真的变成了真·方块字 ,解决方案如下：
       * 用文章最后的pdfjsWrapper.js在替换掉node_modules/vue-pdf/src/pdfjsWrapper.js
       */

      this.$refs.pdf.print();
    }
  }
};
</script>
<style scoped>
::v-deep .el-dialog {
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
  background: transparent;
  color: #fff;
  border: 1px solid #fff !important;
}
::v-deep .el-date-editor.el-input {
  width: 200px;
  height: 36px;
}

.pictureUploading-img img {
  width: 60px;
  height: 210px;
  margin-right: 10px;
}

::v-deep .el-upload--picture-card {
  height: 210px;
  width: 170px;
  line-height: 210px;
  background-image: url("../../../assets/images/头像.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
::v-deep .el-upload--picture-card .el-icon-plus {
  display: none;
}
.el-upload--picture-card /deep/ .el-upload--picture-card i {
  font-size: 48px;
}
::v-deep .el-upload__tip {
  display: none;
}
::v-deep .textarea-explainInfo .el-textarea__inner {
  margin-left: 20px;
  width: 880px;
  height: 104px;
}
::v-deep .textarea-explainInfo1 .el-textarea__inner {
  margin-left: 20px;
  width: 880px;
  height: 104px;
}
::v-deep .dyform-textarea .el-textarea__inner {
  width: 650px;
  height: 64px;
}

.jiben2 {
  width: 650px;
}

.jiben span {
  width: 25px;
  height: 25px;
  display: inline-block;
  background-color: #1c86e1;
  line-height: 25px;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  /* margin-left: 851px; */
  position: absolute;
  right: 20px;
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
.dadui {
  height: 40px;
  background: #003156;
  /* border-bottom: 1px solid #718A9D ; */
  background-image: url(../../../images/矩形底部边框.png);
  background-repeat: no-repeat;
  background-position: 0px 39px;
  font-size: 16px;
  color: #1d96ff;
}
.dadui img {
  margin: 10px 10px 0px 10px;
}

.data {
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
  padding: 10px;
  background: #003156;
  color: #fff;
}

.data p {
  position: absolute;
  top: -5px;
  left: 15px;
}
.data .a1 {
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
::v-deep
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  color: #00365f !important;
  /* background-color: #003156 !important; */
}
::v-deep .el-radio-group .el-radio {
  color: #fff;
}
/* ::v-deep .el-radio:last-child {
  margin-left: 35px;
} */
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
/* 文本域提示文字颜色 */
::v-deep ::-webkit-input-placeholder {
  color: #bdbdbd4f !important;
}
::v-deep .el-dialog__footer {
  padding-right: 30px !important;
}
::v-deep .el-input__inner {
  border: 1px solid white !important;
}
/* 预览弹框背景去除 */
::v-deep .docx-wrapper {
  padding: 0 !important;
  background: none !important;
}
/* excel表格样式 */
::v-deep .home table tr td {
  text-align: right;
  height: 20px;
  min-width: 60px;
  text-align: center;
  /* margin-left: -5px ; */
  /* width: 70px !important; */
  border-right: 1px solid black;
  border-bottom: 1px solid black !important;
}
::v-deep .home table {
  width: 100%;
  border-spacing: 0px !important;
  border-top: 1px solid black;
  border-left: 1px solid black;
  /* border-collapse: collapse !important;
  border-color:#b6ff00 !important; */
}
::v-deep .docx-wrapper table tr td {
  border-bottom: 1px solid black !important;
}
::v-deep .docx {
  width: 100% !important;
}
/* 文件关闭小叉号 */
::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #ccc !important;
}
/* 上一页样式 */
.gopage {
  width: 60px;
  height: 28px;
  background: #f4f4f4;
  color: #60627d;
  border-radius: 14px 0px 0px 14px;
  padding: 6px 9px;
}
/* 下一页样式 */
.downpage {
  width: 60px;
  height: 28px;
  background: #f4f4f4;
  border-radius: 0px 14px 14px 0px;
  padding: 6px 9px;
}
.bg {
  background: #1d96ff;
  color: white;
}
.greed {
  position: absolute;
  top: 26px;
  right: -10px;
  color: #13ce66;
}
.CCC {
  position: absolute;
  top: 26px;
  right: 118px;
  color: #cccccc;
}
/* 文件名称表格 */
::v-deep .fileName .el-input__inner {
  width: 316px !important;
  text-indent: 1em;
  /* background-color: #196299; */
}
::v-deep .fileName .el-input__suffix {
  left: -280px;
}
::v-deep .box1 .el-form-item__label {
  width: 91px !important;
}

.hezi {
  margin-bottom: 13px;
  margin-top: 15px;
  position: relative;
  background: rgb(0, 60, 105);
  color: #fff;
  font-size: 18px;
  font-weight: 600;
}

.tji {
  width: 176px;
  height: 36px;
  text-align: center;
  line-height: 36px;
  position: absolute;
  top: -18px;
  right: 87px;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  background: #004d86;
}
</style>
