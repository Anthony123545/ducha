<template>
  <div id="wrap">
    <div class="wrap-box-top">
      <div class="wrap-box-top-box1">
        <div class="wrap-box-top-box1-header">
          <span>人员五率</span>
          <a
            class="btn btn-xs btn-info btn-view btn-dialog"
            @click="editor1(this)"
            style="padding-left: 22 px"
          >
            <span id="box">编辑</span>
          </a>
        </div>
        <div class="wrap-box-top-box1-center" v-loading="loading1">
          <div class="wrap-box-top-box1-center-top">
            <div
              id="wrap-box-top-box1-center-top-box1"
              class="wrap-box-top-box1-center-top-box1 manbian"
            ></div>
            <div
              id="wrap-box-top-box1-center-top-box2"
              class="wrap-box-top-box1-center-top-box1"
            ></div>
            <div
              id="wrap-box-top-box1-center-top-box3"
              class="wrap-box-top-box1-center-top-box1"
            ></div>
          </div>
          <div class="wrap-box-top-box1-center-top">
            <div
              id="wrap-box-top-box1-center-top-box4"
              class="wrap-box-top-box1-center-top-box1"
              style="margin-left: 90px"
            ></div>
            <div
              id="wrap-box-top-box1-center-top-box5"
              class="wrap-box-top-box1-center-top-box1"
              style="margin-right: 90px"
            ></div>
          </div>
        </div>
      </div>
      <div class="wrap-box-top-box2" v-loading="loading2">
        <div class="wrap-box-top-box1-header">
          <span>装备五率</span>
          <a class="btn btn-xs btn-info btn-view btn-dialog" @click="editor2"
            ><img src="/static/icon-img/编辑.png" alt="" />编辑</a
          >
        </div>
        <div class="wrap-box-top-box1-center">
          <div class="wrap-box-top-box1-center-top">
            <div
              id="wrap-box-top-box1-center-top-box6"
              class="wrap-box-top-box1-center-top-box1"
            ></div>
            <div
              id="wrap-box-top-box1-center-top-box7"
              class="wrap-box-top-box1-center-top-box1"
            ></div>
            <div
              id="wrap-box-top-box1-center-top-box8"
              class="wrap-box-top-box1-center-top-box1"
            ></div>
          </div>
          <div class="wrap-box-top-box1-center-top">
            <div
              id="wrap-box-top-box1-center-top-box9"
              class="wrap-box-top-box1-center-top-box1"
              style="margin-left: 90px"
            ></div>
            <div
              id="wrap-box-top-box1-center-top-box10"
              class="wrap-box-top-box1-center-top-box1"
              style="margin-right: 90px"
            ></div>
          </div>
        </div>
      </div>
    </div>
    <div class="wrap-box-botom" v-loading="loading3">
      <div class="wrap-box-top-box1-header" id="wrap-box-top-box1-header">
        <span style="margin-left: 55px">六量</span>
        <a
          class="btn btn-xs btn-info btn-view btn-dialog"
          style="margin-right: -48px"
          @click="editor3"
          ><img src="/static/icon-img/编辑.png" alt="" />编辑</a
        >
      </div>
      <div class="wrap-box-botom-list" id="wrap-box-botom-list-id">
        <div class="wrap-box-botom-list-item">
          <div class="wrap-box-botom-list-item-title">
            <img
              src="../../../images/小标题.png"
              alt=""
              class="img-left"
            /><span>油料储备量</span
            ><img
              src="../../../images/小标题右边.png"
              alt=""
              class="img-right"
            />
          </div>

          <ul class="wrap-box-botom-list-item-ul" id="ul-id">
            <li>
              <img
                src="../../../images/renoften/油料.png"
                style="margin-left: 21px"
                alt=""
              />
              <div
                class="wrap-box-botom-list-item-ul-left"
                style="margin-left: 16px"
              >
                油料战术储备
              </div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.ylZscb }}
              </div>
            </li>
            <li>
              <img
                src="../../../images/renoften/阈值.png"
                style="margin-left: 19px"
                alt=""
              />
              <div class="wrap-box-botom-list-item-ul-left">储备阈值</div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.ylCxyz }}
              </div>
            </li>
            <li>
              <img
                src="../../../images/renoften/自持能力.png"
                style="margin-left: 21px"
                alt=""
              />
              <div class="wrap-box-botom-list-item-ul-left">自持能力</div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.ylZcnl }}
              </div>
            </li>
          </ul>
        </div>
        <div class="wrap-box-botom-list-item">
          <div class="wrap-box-botom-list-item-title">
            <img
              src="../../../images/小标题.png"
              alt=""
              class="img-left"
            /><span>军需储备量</span
            ><img
              src="../../../images/小标题右边.png"
              alt=""
              class="img-right"
            />
          </div>

          <ul class="wrap-box-botom-list-item-ul" id="ul-id">
            <li>
              <img
                src="../../../images/renoften/军需物资.png"
                style="margin-left: 21px"
                alt=""
              />
              <div class="wrap-box-botom-list-item-ul-left">军需战术储备</div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.jxZscb }}
              </div>
            </li>
            <li>
              <img
                src="../../../images/renoften/阈值.png"
                alt=""
                style="margin-left: 19px"
              />
              <div class="wrap-box-botom-list-item-ul-left">储备阈值</div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.jxCxyz }}
              </div>
            </li>
            <li>
              <img
                src="../../../images/renoften/自持能力.png"
                alt=""
                style="margin-left: 21px"
              />
              <div class="wrap-box-botom-list-item-ul-left">自持能力</div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.jxZcnl }}
              </div>
            </li>
          </ul>
        </div>
        <div class="wrap-box-botom-list-item">
          <div class="wrap-box-botom-list-item-title">
            <img
              src="../../../images/小标题.png"
              alt=""
              class="img-left"
            /><span>战备金储备量</span
            ><img
              src="../../../images/小标题右边.png"
              alt=""
              class="img-right"
            />
          </div>
          <ul class="wrap-box-botom-list-item-ul" id="ul-id">
            <li>
              <img
                src="../../../images/renoften/战备金.png"
                alt=""
                style="margin-left: 21px"
              />
              <div class="wrap-box-botom-list-item-ul-left">战备金战术存储</div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.zbjZscb }}
              </div>
            </li>
          </ul>
        </div>
        <div class="wrap-box-botom-list-item1">
          <div class="wrap-box-botom-list-item-title">
            <img
              src="../../../images/小标题.png"
              alt=""
              class="img-left"
            /><span>战救药材储备量</span
            ><img
              src="../../../images/小标题右边.png"
              alt=""
              class="img-right"
            />
          </div>
          <ul class="wrap-box-botom-list-item-ul" id="ul-id">
            <li>
              <img
                src="../../../images/renoften/卫生物资.png"
                alt=""
                style="margin-left: 21px"
              />
              <div class="wrap-box-botom-list-item-ul-left">
                卫生物资战术储备
              </div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.wswzZscb }}
              </div>
            </li>
            <li>
              <img
                src="../../../images/renoften/阈值.png"
                alt=""
                style="margin-left: 21px"
              />
              <div class="wrap-box-botom-list-item-ul-left">储备阈值</div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.wswzCxyz }}
              </div>
            </li>
          </ul>
        </div>
        <div class="wrap-box-botom-list-item1">
          <div class="wrap-box-botom-list-item-title">
            <img
              src="../../../images/小标题.png"
              alt=""
              class="img-left"
            /><span>弹药储备量</span
            ><img
              src="../../../images/小标题右边.png"
              alt=""
              class="img-right"
            />
          </div>

          <ul class="wrap-box-botom-list-item-ul" id="ul-id">
            <li>
              <img
                src="../../../images/renoften/弹药.png"
                alt=""
                style="margin-left: 21px"
              />
              <div class="wrap-box-botom-list-item-ul-left">弹药储存量</div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.dyCcl }}
              </div>
            </li>
          </ul>
        </div>
        <div class="wrap-box-botom-list-item1">
          <div class="wrap-box-botom-list-item-title">
            <img
              src="../../../images/小标题.png"
              alt=""
              class="img-left"
            /><span>维修器材储备量</span
            ><img
              src="../../../images/小标题右边.png"
              alt=""
              class="img-right"
            />
          </div>

          <ul class="wrap-box-botom-list-item-ul" id="ul-id">
            <li>
              <img
                src="../../../images/renoften/维修.png"
                alt=""
                style="margin-left: 21px"
              />
              <div class="wrap-box-botom-list-item-ul-left">维修器材储存量</div>
              <div class="wrap-box-botom-list-item-ul-right">
                {{ this.sixnum.wxCcl }}
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="bottomsss"></div>
    <!-- 编辑人员五率弹框 -->
    <div class="box1">
      <el-dialog
        style="color: white"
        title="编辑人员五率"
        :visible.sync="open"
        width="450px"
        append-to-body
        class="el-dialog__header bianzhi"
        :close-on-click-modal="false"
      >
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="80px"
          :inline="true"
        >
          <el-form-item label="编制人数" prop="bzrs">
            <el-input v-model="form.bzrs" class="BIANJI"></el-input>
          </el-form-item>
          <el-form-item label="实际人数" prop="sjrs">
            <el-input
              v-model="form.sjrs"
              class="BIANJI"
              :disabled="true"
            ></el-input>
            <!-- <div class="thistexts">{{ this.form.sjrs }}</div> -->
          </el-form-item>
          <el-form-item label="在位人数" prop="zwrs">
            <el-input
              v-model="form.zwrs"
              class="BIANJI"
              :disabled="true"
            ></el-input>
            <!-- <div class="thistexts">{{ this.form.zwrs }}</div> -->
          </el-form-item>
          <el-form-item label="对口人数" prop="dkrs">
            <el-input
              v-model="form.dkrs"
              class="BIANJI"
              :disabled="true"
            ></el-input>
            <!-- <div class="thistexts">{{ this.form.dkrs }}</div> -->
          </el-form-item>
          <el-form-item label="出动人数" prop="cdrs">
            <el-input
              v-model="form.cdrs"
              class="BIANJI"
              :disabled="true"
            ></el-input>
            <!-- <div class="thistexts">{{ this.form.cdrs }}</div> -->
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel" size="btn">取 消</el-button>
        </div>
      </el-dialog>
    </div>

    <!--编辑 装备五率的弹框 -->
    <div class="box2">
      <el-dialog
        style="color: white"
        title="编辑装备五率"
        :visible.sync="open1"
        width="980px"
        append-to-body
        class="el-dialog__header bianji"
        :close-on-click-modal="false"
      >
        <el-form
          ref="form1"
          :model="form1"
          :rules="rules"
          label-width="100px"
          :inline="true"
          style="margin-left: 55px"
        >
          <el-form-item prop="zbbz" label="装备编制数">
            <!-- <el-input-number
            v-model="form1.zbbz"
            controls-position="right"
            :min="0"
            :max="100"
          ></el-input-number> -->
            <el-input v-model="form1.zbbz" class="BIANJI"></el-input>
          </el-form-item>
          <el-form-item prop="zbsy" label="装备实有数">
            <!-- <el-input-number
            v-model="form1.zbsy"
            controls-position="right"
            :min="0"
            :max="100"
          ></el-input-number> -->
            <el-input v-model="form1.zbsy" class="BIANJI"></el-input>
          </el-form-item>
          <el-form-item prop="zbky" label="装备可用数">
            <el-input
              v-model="form1.zbky"
              class="BIANJI"
              :disabled="true"
            ></el-input>
            <!-- <div class="thistexts">{{ this.form1.zbky }}</div> -->
          </el-form-item>
          <el-form-item prop="name" label="实际配套数">
            <!-- <el-input-number
            v-model="form1.sjpt"
            controls-position="right"
            :min="0"
            :max="100"
          ></el-input-number> -->
            <el-input v-model="form1.sjpt" class="BIANJI"></el-input>
          </el-form-item>
          <el-form-item prop="name" label="编制配套数">
            <!-- <el-input-number
            v-model="form1.bzpt"
            controls-position="right"
            :min="0"
            :max="100"
          ></el-input-number> -->
            <el-input v-model="form1.bzpt" class="BIANJI"></el-input>
          </el-form-item>
          <el-form-item prop="name" label="装备出动数">
            <!-- <el-input-number
            v-model="form1.zbcd"
            controls-position="right"
            :min="0"
            :max="100"
          ></el-input-number> -->
            <el-input v-model="form1.zbcd" class="BIANJI"></el-input>
          </el-form-item>
          <el-form-item
            prop="name"
            class="dadao"
            label="达到装备储存标准配套数"
            label-width="170px"
          >
            <!-- <el-input-number
            v-model="form1.cczb"
            controls-position="right"
            :min="0"
            :max="100"
          ></el-input-number> -->
            <el-input v-model="form1.cczb" class="BIANJIs"></el-input>
          </el-form-item>

          <div></div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm1">确 定</el-button>
          <el-button @click="cancel" size="btn">取 消</el-button>
        </div>
      </el-dialog>
    </div>

    <!-- 编辑六量弹框 -->
    <div class="box3">
      <el-dialog
        style="color: white"
        title="编辑六量"
        :visible.sync="open2"
        width="830px"
        append-to-body
        class="el-dialog__header bianjiliuliang"
        :close-on-click-modal="false"
      >
        <el-form
          ref="form2"
          :model="form2"
          :rules="rules"
          label-width="100px"
          :inline="true"
        >
          <el-form-item prop="ylZscb">
            <el-form-item label="油料战术储备">
              <el-input v-model="form2.ylZscb"></el-input>
            </el-form-item>
          </el-form-item>
          <el-form-item prop="ylCxyz">
            <el-form-item label="油料储备阈值">
              <el-input v-model="form2.ylCxyz"></el-input>
            </el-form-item>
          </el-form-item>
          <el-form-item prop="ylZcnl">
            <el-form-item label="油料自持能力">
              <el-input v-model="form2.ylZcnl"></el-input>
            </el-form-item>
          </el-form-item>
          <el-form-item prop="jxCxyz">
            <el-form-item label="军需储备阈值">
              <el-input v-model="form2.jxCxyz"></el-input>
            </el-form-item>
          </el-form-item>
          <el-form-item prop="dyCcl">
            <el-form-item label="弹药储存量">
              <el-input v-model="form2.dyCcl"></el-input>
            </el-form-item>
          </el-form-item>
          <el-form-item prop="jxZcnl">
            <el-form-item label="军需自持能力">
              <el-input v-model="form2.jxZcnl"></el-input>
            </el-form-item>
          </el-form-item>
          <el-form-item prop="jxZscb">
            <el-form-item
              label="军需物资战术储备"
              label-width="130px"
              style="margin-left: -30px"
            >
              <el-input v-model="form2.jxZscb" class="BIANJIss"></el-input>
            </el-form-item>
          </el-form-item>
          <el-form-item prop="zbjZscb">
            <el-form-item
              label="战备金战术存储"
              label-width="130px"
              style="margin-left: -30px"
            >
              <el-input v-model="form2.zbjZscb" class="BIANJIss"></el-input>
            </el-form-item>
          </el-form-item>
          <el-form-item prop="wswzZscb">
            <el-form-item
              label="卫生物资战术储备"
              label-width="130px"
              style="margin-left: -30px"
            >
              <el-input v-model="form2.wswzZscb" class="BIANJIss"></el-input>
            </el-form-item>
          </el-form-item>
          <el-form-item prop="wswzCxyz">
            <el-form-item
              label="卫生物资储备阈值"
              label-width="130px"
              style="margin-left: -30px"
            >
              <el-input v-model="form2.wswzCxyz" class="BIANJIss"></el-input>
            </el-form-item>
          </el-form-item>
          <el-form-item prop="wxCcl">
            <el-form-item
              label="维修器材储存量"
              label-width="130px"
              style="margin-left: -30px"
            >
              <el-input v-model="form2.wxCcl" class="BIANJIss"></el-input>
            </el-form-item>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm2">确 定</el-button>
          <el-button @click="cancel" size="btn">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import * as echarts from "echarts";
import {
  getReteOfTen,
  getliuliang,
  updateliuliang,
  updatewulv,
  updatezbwl,
  getzbwl,
  // 获取装备可用接口
  getzbky,
  // 获取装备五率接口
  addzbwl,
  getliulian,
} from "@/api/combatduty/ReteOfTen";
import Cookies from "js-cookie";
export default {
  data() {
    return {
      list: 50,
      open: false,
      open1: false,
      open2: false,
      // 人员五率
      form: {},
      // 装备五率
      form1: {},
      // 六量
      form2: {},
      // 表单校验
      rules: {
        bzrs: [
          { required: true, message: "编制人数不能为空", trigger: "blur" },
        ],
        zbbz: [
          { required: true, message: "装备编制不能为空", trigger: "blur" },
        ],
        zbsy: [
          { required: true, message: "装备实有不能为空", trigger: "blur" },
        ],
        sjpt: [
          { required: true, message: "实际配套不能为空", trigger: "blur" },
        ],
        bzpt: [
          { required: true, message: "编制配套不能为空", trigger: "blur" },
        ],
        zbcd: [
          { required: true, message: "战术储备不能为空", trigger: "blur" },
        ],
        cczb: [
          { required: true, message: "存储标准不能为空", trigger: "blur" },
        ],
      },
      zaiweirenshu: 0,
      chudongrenshu: 0,
      shijirenshu: 0,
      duikourenshu: 0,
      peopleNum: 0,
      sixnum: [],
      // 人员五律加载
      loading1: false,
      // 装备五律加载
      loading2: false,
      // 六量加载
      loading3: false,
      zhuangbei: [],
      zbky: "",
    };
  },
  mounted() {
    getReteOfTen().then((res) => {
      this.loading1 = true;
      this.form = res.rows[0];
      this.myEcharts();
      this.loading1 = false;
    });
    this.getZbwl();
  },
  created() {
    // if (Cookies.get("shuaxin") != "true") {
    //   window.location.reload();
    // }
    // Cookies.set("shuaxin", "true");
    this.getLiuliang();
    getzbky().then((res) => {
      this.zbky = res.data;
    });
  },
  methods: {
    getZbwl() {
      getzbwl().then((res) => {
        this.loading2 = true;
        this.form1 = {};
        this.zhuangbei = res.rows[0];
        this.myEcharts();
        this.loading2 = false;
      });
    },
    getLiuliang() {
      this.loading3 = true;
      getliuliang().then((res) => {
        this.form2 = {};
        this.sixnum = res.rows[0];
        this.loading3 = false;
      });
    },
    // 编辑人员五率
    editor1() {
      this.open = true;
    },
    // 编辑装备五率
    editor2() {
      console.log(1111)
      this.open1 = true;
      this.form1.zbky = this.zbky;

      this.form1 = this.zhuangbei;
      console.log(this.form1)
      addzbwl().then((res) => {
        console.log(res.rows)
        this.form1 = res.rows[0];
      });
    },
    editor3() {
      this.open2 = true;
      this.form2 = this.sixnum;
      getliulian().then((res) => {
        this.form2 = res.rows[0];
      });
    },
    // 编辑人员五率确定
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          updatewulv(this.form).then((response) => {
            this.form = this.form;
            this.$modal.msgSuccess("上传成功");
            this.open = false;
            this.myEcharts();
          });
        }
      });
    },
    // 编辑装备五率确定
    submitForm1() {
      this.$refs["form1"].validate((valid) => {
        this.form.zbky = 0;
        if (valid) {
          // 添加装备五率的接口
          updatezbwl(this.form1).then((response) => {
            this.$modal.msgSuccess("上传成功");
            this.open1 = false;
            // 调用装备五率列表接口
            this.getZbwl();
          });
        }
      });
    },
    // 编辑六量确定
    submitForm2() {
      this.$refs["form2"].validate((valid) => {
        if (valid) {
          updateliuliang(this.form2).then((response) => {
            this.$modal.msgSuccess("上传成功");
            this.open2 = false;
            this.getLiuliang();
          });
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open1 = false;
      this.open2 = false;
    },
    myEcharts() {
      var _this = this;

      let bzrs = this.form.bzrs; //编制人数
      //   满编率
      var myChart1 = echarts.init(
        document.getElementById("wrap-box-top-box1-center-top-box1")
      );
      //   在位率
      var myChart2 = echarts.init(
        document.getElementById("wrap-box-top-box1-center-top-box2")
      );
      //   对口率
      var myChart3 = echarts.init(
        document.getElementById("wrap-box-top-box1-center-top-box3")
      );
      //   出动率
      var myChart4 = echarts.init(
        document.getElementById("wrap-box-top-box1-center-top-box4")
      );
      //   称职率
      var myChart5 = echarts.init(
        document.getElementById("wrap-box-top-box1-center-top-box5")
      );
      //   装备满编率
      var myChart6 = echarts.init(
        document.getElementById("wrap-box-top-box1-center-top-box6")
      );
      //   装备可用率
      var myChart7 = echarts.init(
        document.getElementById("wrap-box-top-box1-center-top-box7")
      );
      //   装备配套率
      var myChart8 = echarts.init(
        document.getElementById("wrap-box-top-box1-center-top-box8")
      );
      //   装备战备存储率
      var myChart9 = echarts.init(
        document.getElementById("wrap-box-top-box1-center-top-box9")
      );
      //   装备出动率
      var myChart10 = echarts.init(
        document.getElementById("wrap-box-top-box1-center-top-box10")
      );
      //   满编率
      var data = [
        {
          value: this.form.sjrs,
          name: "实际人数",
        },
        {
          value: this.form.sjrs,
        },
      ];
      data[1].value = this.form.bzrs - data[0].value;
      let data11 = this.form.bzrs;
      var option = {
        tooltip: {
          trigger: "item",
          position: ["10%", "50%"],
          formatter: function (params) {
            return (
              "实际人数" +
              "(" +
              data[0].value +
              ")" +
              ":" +
              "编制人数" +
              "(" +
              data11 +
              ")"
            );
          },
          backgroundColor: "rgba(32, 99, 200,0.9 ) ",
          borderColor: "#fff",
        },
        color: ["#F2AC72", "#144d72"],
        series: [
          {
            name: "",
            type: "pie",
            radius: ["70%", "80%"],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: "center",
              rich: {
                value: { fontSize: 28, color: "#fff" },
                name: { color: "#fff", fontSize: 14 },
              },
              formatter: function (params) {
                // 设置满编率
                // var n = data[0].value / (data[1].value + data[0].value);
                if (_this.form.bzrs == 0 || _this.form.sjrs == 0) {
                  var s =
                    `{value|${(0).toFixed()}%}` + "\n" + "\n" + `{name|满编率}`;
                  return s;
                } else {
                  var n = _this.form.sjrs / _this.form.bzrs;
                  var s =
                    `{value|${(n * 100).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|满编率}`;
                  return s;
                }
              },
            },
            normal: {
              label: {
                show: true,
                formatter: "{b} : {c} ({d}%)",
              },
              labelLine: { show: true },
            },
            // 样式
            // itemStyle: {
            //   borderRadius: 2,
            //   borderWidth: 10,
            // },
            // emphasis: {
            //   label: {
            //     // show: false,
            //     // fontSize: "14",
            //     // fontWeight: "bold",
            //   },
            // },
            labelLine: {
              // show: true,
            },
            data: data,
          },
        ],
      };
      // 在位率
      var data2 = [
        {
          value: this.form.zwrs,
          name: "在位人数",
        },
        {
          value: this.form.zwrs,
          name: "未在位人数",
        },
      ];
      data2[1].value = this.form.bzrs - data2[0].value;
      let data22 = this.form.zwrs;

      var data3 = [
        {
          value: this.form.dkrs,
          name: "指挥员/参谋员/专业技术人员/主战专业军士实际专业对口",
        },
        {
          value: this.form.dkrs,
          name: "实际人数",
        },
      ];
      data3[1].value = this.form.sjrs - data3[0].value;

      var data4 = [
        {
          value: this.form.cdrs,
          name: "出动人数",
        },
        {
          value: this.form.cdrs,
          name: "未出动率",
        },
      ];
      data4[1].value = this.form.bzrs - data4[0].value;
      let data44 = this.form.cdrs;
      var data5 = [
        {
          value: 5,
          name: "军事训练成绩达到合格人数",
        },
        {
          value: 200,
          name: "未合格人数",
        },
      ];
      data5[1].value = this.form.zwrs - data5[0].value;
      let data55 = this.form.zwrs;
      var data6 = [
        {
          value: this.zhuangbei.zbsy,
          name: "按编配配备的装备实力数",
        },
        {
          value: 200,
          name: "未配备人数",
        },
      ];
      data6[1].value = this.zhuangbei.zbbz - data6[0].value;

      var data7 = [
        {
          value: this.zhuangbei.zbky,
          name: "装备可用数",
        },
        {
          value: 200,
          name: "装备不可用数",
        },
      ];
      data7[1].value = this.zhuangbei.zbsy - this.zhuangbei.zbky;

      var data8 = [
        {
          value: this.zhuangbei.sjpt,
          name: "装备实际配套数",
        },
        {
          value: 200,
          name: "装备未配套数",
        },
      ];
      data8[1].value = this.zhuangbei.bzpt - data8[0].value;

      var data9 = [
        {
          value: this.zhuangbei.cczb,
          name: "建制单位达到战备储存标准的装备数量",
        },
        {
          value: 200,
          name: "未达标",
        },
      ];
      data9[1].value = this.zhuangbei.zbsy - data9[0].value;

      var data10 = [
        {
          value: this.zhuangbei.zbcd,
          name: "装备出动数",
        },
        {
          value: 200,
          name: "装备未出动数",
        },
      ];
      data10[1].value = this.zhuangbei.zbbz - data10[0].value;

      var option2 = {
        tooltip: {
          trigger: "item",
          position: ["10%", "50%"],
          formatter: function (params) {
            return (
              data2[0].name +
              "(" +
              data22 +
              ")" +
              ":" +
              "编制人数" +
              "(" +
              bzrs +
              ")"
            );
          },
          backgroundColor: "rgba(32, 99, 200,0.8 )",
          borderColor: "#17F765",
        },
        color: ["#27A1F8", "#144d72"],
        series: [
          {
            name: "",
            type: "pie",
            radius: ["70%", "80%"],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: "center",
              rich: {
                value: { fontSize: 28, color: "#fff" },
                name: { color: "#fff", fontSize: 14 },
              },
              formatter: function (params) {
                if (_this.form.zwrs == 0 || _this.form.bzrs == 0) {
                  var s =
                    `{value|${(0).toFixed()}%}` + "\n" + "\n" + `{name|在位率}`;
                  return s;
                } else {
                  var n = _this.form.zwrs / _this.form.bzrs;
                  if (_this.form.bzrs == 0) {
                    var s =
                      `{value|${(0).toFixed()}%}` +
                      "\n" +
                      "\n" +
                      `{name|在位率}`;
                    return s;
                  } else {
                    var s =
                      `{value|${(n * 100).toFixed()}%}` +
                      "\n" +
                      "\n" +
                      `{name|在位率}`;
                    return s;
                  }
                }
              },
            },
            data: data2,
          },
        ],
      };
      var option3 = {
        tooltip: {
          trigger: "item",
          position: ["10%", "50%"],
          formatter: function (params) {
            if (data3[0].name.length > 20) {
              return (
                data3[0].name.slice(0, 20) +
                "<br/>" +
                data3[0].name.slice(20, data3[0].name.length) +
                "(" +
                data3[0].value +
                ")" +
                ":" +
                data3[1].name +
                "(" +
                data11 +
                ")"
              );
            } else {
              return (
                data3[0].name +
                "(" +
                data3[0].value +
                ")" +
                ":" +
                data3[1].name +
                "(" +
                data11 +
                ")"
              );
            }
          },
          backgroundColor: "rgba(32, 99, 200,0.8 )",
          borderColor: "#17F765",
        },
        color: ["#FAC858", "#144d72"],
        series: [
          {
            name: "",
            type: "pie",
            radius: ["70%", "80%"],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: "center",
              // formatter: (params)=> { let str =(`{value|${params.value}}`)+ '\n'+(`{name|${params.name}}`); return str },
              // borderWidth: 5, borderRadius: 5, // padding: [0, 86], height: 70, fontSize: 20, show: true,
              rich: {
                value: { fontSize: 28, color: "#fff" },
                name: { color: "#fff", fontSize: 14 },
              },
              formatter: function (params) {
                if (_this.form.dkrs == 0 || _this.form.sjrs == 0) {
                  var s =
                    `{value|${(0).toFixed()}%}` + "\n" + "\n" + `{name|对口率}`;
                  return s;
                } else {
                  var n = _this.form.dkrs / _this.form.sjrs;
                  var s =
                    `{value|${(n * 100).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|对口率}`;
                  return s;
                }
              },
            },
            data: data3,
          },
        ],
      };
      var option4 = {
        tooltip: {
          trigger: "item",
          position: ["10%", "50%"],
          formatter: function (params) {
            return (
              data4[0].name +
              "(" +
              data44 +
              ")" +
              ":" +
              "编制人数" +
              "(" +
              bzrs +
              ")"
            );
          },
          backgroundColor: "rgba(32, 99, 200,0.8 )",
          borderColor: "#17F765",
        },
        color: ["#05D5E9", "#144d72"],
        series: [
          {
            name: "",
            type: "pie",
            radius: ["70%", "80%"],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            // hoverOffset: 5,
            label: {
              show: true,
              position: "center",
              rich: {
                value: { fontSize: 28, color: "#fff" },
                name: { color: "#fff", fontSize: 14 },
              },
              formatter: function (params) {
                if (_this.form.cdrs == 0 || _this.form.bzrs == 0) {
                  var s =
                    `{value|${(0).toFixed()}%}` + "\n" + "\n" + `{name|出动率}`;
                  return s;
                } else {
                  var n = _this.form.cdrs / _this.form.bzrs;
                  var s =
                    `{value|${(n * 100).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|出动率}`;
                  return s;
                }
              },
            },
            data: data4,
          },
        ],
      };
      var option5 = {
        tooltip: {
          trigger: "item",
          position: ["10%", "50%"],
          formatter: function (params) {
            return (
              data5[0].name +
              "(" +
              data5[0].value +
              ")" +
              ":" +
              data2[0].name +
              "(" +
              data55 +
              ")"
            );
          },
          backgroundColor: "rgba(32, 99, 200,0.8 )",
          borderColor: "#17F765",
        },
        color: ["#11F0A0 ", "#144d72"],
        series: [
          {
            name: "",
            type: "pie",
            radius: ["70%", "80%"],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            // hoverOffset: 5,
            label: {
              show: true,
              position: "center",
              // formatter: (params)=> { let str =(`{value|${params.value}}`)+ '\n'+(`{name|${params.name}}`); return str },
              // borderWidth: 5, borderRadius: 5, // padding: [0, 86], height: 70, fontSize: 20, show: true,
              rich: {
                value: { fontSize: 28, color: "#fff" },
                name: { color: "#fff", fontSize: 14 },
              },
              formatter: function (params) {
                if (data5[0].value == 0 || _this.form.zwrs == 0) {
                  var s =
                    `{value|${(0).toFixed()}%}` + "\n" + "\n" + `{name|称职率}`;
                  return s;
                } else {
                  var n = data5[0].value / _this.form.zwrs;
                  var s =
                    `{value|${(n * 100).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|称职率}`;
                  return s;
                }
              },
            },
            data: data5,
          },
        ],
      };
      var option6 = {
        tooltip: {
          trigger: "item",
          position: ["10%", "50%"],
          formatter: function (params) {
            return (
              data6[0].name +
              "(" +
              data6[0].value +
              ")" +
              ":" +
              "编制数" +
              "(" +
              (data6[1].value + data6[0].value) +
              ")"
            );
          },
          backgroundColor: "rgba(32, 99, 200,0.8 )",
          borderColor: "#17F765",
        },
        color: ["#F2AC72", "#144d72"],
        series: [
          {
            name: "",
            type: "pie",
            radius: ["70%", "80%"],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: "center",
              rich: {
                value: { fontSize: 28, color: "#fff" },
                name: { color: "#fff", fontSize: 14 },
              },
              formatter: function (params) {
                if (_this.zhuangbei.zbsy == 0 || _this.zhuangbei.zbbz == 0) {
                  var s =
                    `{value|${(0).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|装备满编率}`;
                  return s;
                } else {
                  var n = _this.zhuangbei.zbsy / _this.zhuangbei.zbbz;
                  var s =
                    `{value|${(n * 100).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|装备满编率}`;
                  return s;
                }
              },
            },
            labelLine: {
              show: true,
            },
            data: data6,
          },
        ],
      };
      var option7 = {
        tooltip: {
          trigger: "item",
          position: ["10%", "50%"],
          formatter: function (params) {
            return (
              data7[0].name +
              "(" +
              data7[0].value +
              ")" +
              ":" +
              "装备实有数" +
              "(" +
              (data7[1].value + data7[0].value) +
              ")"
            );
          },
          backgroundColor: "rgba(32, 99, 200,0.8 )",
          borderColor: "#17F765",
        },
        color: ["#27A1F8", "#144d72"],
        series: [
          {
            name: "Access From",
            type: "pie",
            radius: ["70%", "80%"],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            // hoverOffset: 5,
            label: {
              show: true,
              position: "center",
              rich: {
                value: { fontSize: 28, color: "#fff" },
                name: { color: "#fff", fontSize: 14 },
              },
              formatter: function (params) {
                if (_this.zhuangbei.zbky == 0 || _this.zhuangbei.zbsy == 0) {
                  var s =
                    `{value|${(0).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|装备可用率}`;
                  return s;
                } else {
                  var n = _this.zhuangbei.zbky / _this.zhuangbei.zbsy;
                  var s =
                    `{value|${(n * 100).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|装备可用率}`;
                  return s;
                }
              },
            },
            data: data7,
          },
        ],
      };
      var option8 = {
        tooltip: {
          trigger: "item",
          position: ["10%", "50%"],
          formatter: function (params) {
            return (
              data8[0].name +
              "(" +
              data8[0].value +
              ")" +
              ":" +
              "编制配套数" +
              "(" +
              (data8[1].value + data8[0].value) +
              ")"
            );
          },
          backgroundColor: "rgba(32, 99, 200,0.8 )",
          borderColor: "#17F765",
        },
        color: ["#FAC858", "#144d72"],
        series: [
          {
            name: "",
            type: "pie",
            radius: ["70%", "80%"],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: "center",
              rich: {
                value: { fontSize: 28, color: "#fff" },
                name: { color: "#fff", fontSize: 14 },
              },
              formatter: function (params) {
                // var n = data8[0].value / (data8[1].value + data8[0].value);
                if (_this.zhuangbei.sjpt == 0 || _this.zhuangbei.bzpt == 0) {
                  var s =
                    `{value|${(0).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|装备配套率}`;
                  return s;
                } else {
                  var n = _this.zhuangbei.sjpt / _this.zhuangbei.bzpt;
                  var s =
                    `{value|${(n * 100).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|装备配套率}`;
                  return s;
                }
              },
            },
            data: data8,
          },
        ],
      };
      var option9 = {
        tooltip: {
          trigger: "item",
          position: ["10%", "50%"],
          formatter: function (params) {
            if (data9[0].name.length > 15) {
              return (
                data9[0].name.slice(0, 15) +
                "<br/>" +
                data9[0].name.slice(15, data9[0].name.length) +
                "(" +
                data9[0].value +
                ")" +
                ":" +
                "装备实有数" +
                "(" +
                (data7[1].value + data7[0].value) +
                ")"
              );
            } else {
              return (
                data9[0].name +
                "(" +
                data9[0].value +
                ")" +
                ":" +
                "装备实有数" +
                "(" +
                (data7[1].value + data7[0].value) +
                ")"
              );
            }
          },
          backgroundColor: "rgba(32, 99, 200,0.8 )",
          borderColor: "#17F765",
        },
        color: ["#05D5E9", "#144d72"],
        series: [
          {
            name: "",
            type: "pie",
            radius: ["70%", "80%"],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            // hoverOffset: 5,
            label: {
              show: true,
              position: "center",
              rich: {
                value: { fontSize: 28, color: "#fff" },
                name: { color: "#fff", fontSize: 14 },
              },
              formatter: function (params) {
                if (_this.zhuangbei.cczb == 0 || _this.zhuangbei.zbsy == 0) {
                  var s =
                    `{value|${(0).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|装备战备储存率}`;
                  return s;
                } else {
                  var n = _this.zhuangbei.cczb / _this.zhuangbei.zbsy;
                  var s =
                    `{value|${(n * 100).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|装备战备储存率}`;
                  return s;
                }
              },
            },
            data: data9,
          },
        ],
      };
      var option10 = {
        tooltip: {
          trigger: "item",
          position: ["10%", "50%"],
          formatter: function (params) {
            return (
              data10[0].name +
              "(" +
              data10[0].value +
              ")" +
              ":" +
              "编制数" +
              "(" +
              (data10[1].value + data10[0].value) +
              ")"
            );
          },
          backgroundColor: "rgba(32, 99, 200,0.8 )",
          borderColor: "#17F765",
        },
        color: ["#11F0A0", "#144d72"],
        series: [
          {
            name: "",
            type: "pie",
            radius: ["70%", "80%"],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            // hoverOffset: 5,
            label: {
              show: true,
              position: "center",
              rich: {
                value: { fontSize: 28, color: "#fff" },
                name: { color: "#fff", fontSize: 14 },
              },
              formatter: function (params) {
                if (_this.zhuangbei.zbcd == 0 || _this.form.zbbz == 0) {
                  var s =
                    `{value|${(0).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|装备出动率}`;
                  return s;
                } else {
                  var n = data10[0].value / (data10[1].value + data10[0].value);
                  var s =
                    `{value|${(n * 100).toFixed()}%}` +
                    "\n" +
                    "\n" +
                    `{name|装备出动率}`;
                  return s;
                }
              },
            },
            data: data10,
          },
        ],
      };

      myChart1.setOption(option);
      myChart1.dispatchAction({
        type: "highlight",
        seriesIndex: 0,
        dataIndex: 0,
      });
      myChart2.setOption(option2);
      myChart2.dispatchAction({
        type: "highlight",
        seriesIndex: 0,
        dataIndex: 0,
      });
      myChart3.setOption(option3);
      myChart3.dispatchAction({
        type: "highlight",
        seriesIndex: 0,
        dataIndex: 0,
      });
      myChart4.setOption(option4);
      myChart4.dispatchAction({
        type: "highlight",
        seriesIndex: 0,
        dataIndex: 0,
      });
      myChart5.setOption(option5);
      myChart5.dispatchAction({
        type: "highlight",
        seriesIndex: 0,
        dataIndex: 0,
      });
      myChart6.setOption(option6);
      myChart6.dispatchAction({
        type: "highlight",
        seriesIndex: 0,
        dataIndex: 0,
      });
      myChart7.setOption(option7);
      myChart7.dispatchAction({
        type: "highlight",
        seriesIndex: 0,
        dataIndex: 0,
      });
      myChart8.setOption(option8);
      myChart8.dispatchAction({
        type: "highlight",
        seriesIndex: 0,
        dataIndex: 0,
      });
      myChart9.setOption(option9);
      myChart9.dispatchAction({
        type: "highlight",
        seriesIndex: 0,
        dataIndex: 0,
      });
      myChart10.setOption(option10);
      myChart10.dispatchAction({
        type: "highlight",
        seriesIndex: 0,
        dataIndex: 0,
      });
    },
  },
};
</script>
<style scoped>
.bottomsss {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 10px;
  background-color: pink;
}
::v-deep .el-form--inline .el-form-item {
  margin-right: 19px !important;
}
::v-deep .BIANJI .el-input__inner {
  width: 250px;
  text-align: center !important;
  /* text-indent: 4.3em !important; */
}
::v-deep .el-input__inner {
  /* width: 250px; */
  border: 1px solid white !important;
  text-align: center !important;
  /* text-indent: 4.3em !important; */
}
::v-deep .BIANJIs .el-input__inner {
  width: 670px;
  text-align: center !important;
  /* text-indent: 4.3em !important; */
}
::v-deep .BIANJIss .el-input__inner {
  width: 590px;
  text-align: center !important;
  /* text-indent: 4.3em !important; */
}
::v-deep .el-form-item__label {
  margin-left: 50px !important;
}
::v-deep .el-dialog__header {
  border-bottom: 1px solid white !important;
}
::v-deep .bianjiliuliang .el-form-item__label {
  margin-left: 10px !important;
}
html,
body {
  width: 100%;
  height: 100%;
  background-color: #103360;
}

/* #wrap {
  min-height: calc(100vh - 85px);
  width: 100%;
  position: relative;
  overflow: hidden;
  background-color: #103360;
  margin: 0;
  padding:0;
} */
/* #wrap{
  display: flex;
  flex-wrap: wrap;
} */
#wrap {
  position: relative;
}
.content {
  background-color: #103360;
}
/* 备注 */
.thistext {
  color: white;
  width: 940px;
  border: 1px solid #fff;
  border-radius: 4px;
  height: auto;
  min-height: 35px;
  text-indent: 1em;
  margin-bottom: 20px;
  /* margin: auto; */
}
/* 备注 */
.thistexts {
  color: white;
  width: 250px;
  border: 1px solid #fff;
  border-radius: 4px;
  height: auto;
  min-height: 35px;
  text-align: center;
  /* text-indent: 5.3em; */
  /* margin-bottom: 20px; */
  /* margin: auto; */
}
.wrap-box-top-box1 {
  width: 49.2%;
  height: 100%;
  background-image: url("../../../images/五率底.png");
  border-radius: 12px;
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
.wrap-box-top-box2 {
  /* position: absolute; */
  /* top: 10px; */
  /* right: 10px; */
  width: 49.2%;
  height: 100%;
  /* background: #002843; */
  border-radius: 12px;
  background-image: url("../../../images/五率底.png");
  /* margin: 3px 1.5%; */
  margin-left: 10px;
  background-size: 100% 100%;
  /*margin-top: 10px;*/
  background-repeat: no-repeat;
}

.wrap-box-top-box1-header {
  width: 95%;
  height: 35px;
  background-image: url("../../../images/renoften/标题底.png");
  background-repeat: no-repeat;
  /* background-size: 77% 100%; */
  margin-top: 10px;
  margin-left: 10px;
  display: inline-block;
}

.wrap-box-top-box1-header span {
  font-size: 18px;
  color: #fff;
  margin-left: 55px;
  line-height: 35px;
}

.wrap-box-top-box1-header a {
  height: 20px;
  float: right;
  margin-right: -5px;
  margin-top: 5px;
}

.wrap-box-top-box1-header a img {
  width: 15px;
  height: 15px;
  margin-bottom: 3px;
  margin-right: 6px;
}

.wrap-box-top-box1-center {
  width: 100%;
  height: 370px;
  padding: 20px;
  /* background-color: #fff; */
}

.wrap-box-top-box1-center-top {
  width: 100%;
  height: 180px;
  display: flex;
}

.wrap-box-top-box1-center-top-box1 {
  flex: 1;
  height: 180px;
}
/* .manbian{
  top: 20px;
  left: 30px;
} */
.wrap-box-top {
  position: absolute;
  display: flex;
  flex-flow: flex-flow;
  /* margin-left: 20px; */
  /* margin-bottom: 10px; */
  margin: 10px;
  width: 100%;
  height: 45vh;
}
.wrap-box-botom {
  position: absolute;
  top: 46vh;
  width: 99%;
  height: 44vh;
  background-image: url("../../../images/六量底.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  margin: 10px;
}

.wrap-box-botom-header {
  width: 100%;
  height: 35px;
  position: relative;
  margin-bottom: 10px;
}

.wrap-box-botom-header img {
  width: 95%;
  height: 35px;
  position: absolute;
  left: 2%;
  top: 10px;
}

.wrap-box-botom-header h3 {
  font-size: 18px;
  color: #fff;
  margin-left: 170px;
  line-height: 50px;
}

.wrap-box-botom-list {
  width: 100%;
  height: 45vh;
  margin-top: 10px;
}

.wrap-box-botom-list-item {
  width: 32.5%;
  height: 46%;
  background-color: #002843;
  /* margin: 5px 1.5%; */
  margin-left: 10px;
  display: inline-block;
}
.wrap-box-botom-list-item1 {
  margin: 10px 0px;
  width: 32.5%;
  height: 35%;
  background-color: #002843;
  /* margin: 0 1.5%; */
  /* margin:10px; */
  margin-left: 10px;
  display: inline-block;
}

.wrap-box-botom-list-item-title {
  width: 100%;
  height: 30px;
  text-align: center;
  line-height: 30px;
  color: #fff;
  margin-top: 10px;
}

.wrap-box-botom-list-item-title .img-left {
  margin-right: 5px;
  height: 15px;
  /* margin-left: 140px; */
  margin-bottom: -2px;
}

.wrap-box-botom-list-item-title .img-right {
  margin-left: 5px;
  height: 15px;
  margin-bottom: -2px;
}

.wrap-box-botom-list-item-ul {
  width: 100%;
  height: 120px;
  overflow: hidden;
  list-style: none;
  margin-top: 8px;
}

.wrap-box-botom-list-item-ul li {
  width: 90%;
  height: 30px;
  background-image: url("../../../images/renoften/六量数据底.png");
  /* background-color: #103a5e; */
  margin: 0 1.5%;
  display: flex;
  margin-top: 6px;
}

.wrap-box-botom-list-item-ul li div {
  flex: 1;
  height: 20px;
}

.wrap-box-botom-list-item-ul-left {
  text-align: left;
  line-height: 30px;
  color: #fff;
  margin-left: 10px;
  font-size: 0.14rem;
}

.wrap-box-botom-list-item-ul-right {
  text-align: right;
  line-height: 30px;
  margin-right: 10px;
  color: #19f8a2;
  font-size: 18px;
}
.btn-info {
  width: 80px;
  height: 30px !important;
  color: #fff;
  background-color: #18bc9c;
  border-color: #18bc9c;
  text-align: center;
  line-height: 30px;
  font-size: 15px;
  border-radius: 5px;
  background-image: url("../../../images/编辑.png");
  background-repeat: no-repeat;
  background-position: 15px;
}
.wrap-box-top-box1-header a {
  margin-top: 0px;
}
.wrap-box-botom-list-item-title {
  color: #05d5e9;
}
#wrap-box-top-box1-header {
  /* background-size: 89% 100%; */
  background-position: 0px 0px;
  background-image: url("../../../assets/images/六量标题底.png");
}
/* 弹框背景 */
::v-deep .el-dialog {
  background-color: #004d86 !important;
}
::v-deep .el-input input {
  background-color: #004d86 !important;
  color: white;
}
::v-deep .el-input-number__increase {
  background-color: #004d86 !important;
  color: white !important;
}
::v-deep .el-input-number__decrease {
  background-color: #004d86 !important;
  color: white !important;
}

::v-deep .el-dialog__title {
  color: white !important;
}
::v-deep .el-form-item__label {
  text-align: end !important;
}
::v-deep .dadao .el-form-item__label {
  margin-left: -20px !important;
}
::v-deep .el-dialog__footer {
  margin-right: 84px !important;
}
.el-dialog__footer {
  margin-right: 50px !important;
}
::v-deep .el-dialog__body {
  padding: 30px 0px 30px 0px !important;
}
#box {
  margin-left: 23px;
  font-size: 15px;
  line-height: 30px;
}
</style>
