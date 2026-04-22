<template>
  <div>
    <el-row>
      <el-col :span="1.5"> </el-col>
      <el-col :span="1.5" class="isSubmit">
        <button
          @click="faith_one"
          id="onevideos"
          class="single"
          :class="!this.isfour ? 'bg' : ''"
        >
          单屏
        </button>
        <button
          @click="faith_four"
          id="allvideos"
          class="allFlv"
          :class="this.isfour ? 'bg' : ''"
        >
          全通道FLV
        </button>
      </el-col>
    </el-row>
    <hr />
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="dadui">
          <img
            style="padding-top: 3px; margin-left: 15px"
            src="../../../images/摄像头icon.png"
            alt
          />
          摄像头
        </div>
        <div class="tree">
          <!-- 左侧 -->
          <div class="videolist">
            <div class="userAgrees">
              <ul style="height: calc(100vh - 240px); overflow: auto">
                <li
                  style="margin-left: -40px; padding-left: 40px"
                  v-for="(item, index) in fourVideo"
                  :key="item.id"
                  @click="clickhandleitem(item, index)"
                >
                  <i class="el-icon-video-camera-solid"></i>
                  {{ item.cname }}
                </li>
              </ul>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="19" :xs="24">
        <!-- 单屏播放 -->
        <div style="position: relative" v-if="!isfour">
          <div
            style="
              width: 100%;
              height: calc(100vh - 200px);
              margin-left: 5px;
              margin-bottom: 5px;
              position: relative;
            "
          >
            <flv id="faith" :url="onevideoUrl.caddr" />
          </div>
        </div>
        <!-- 四屏播放 -->
        <!-- <keep-alive> -->
        <div class="video1" v-else>
          <div v-for="(item, index) in arr" :key="index" class="fourscreen">
            <flv :id="index" :url="arr[index].caddr" />
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- <div  v-else v-for="(item, index) in fourVideo" :key="index">
      <flv :id="index" :url="item.caddr"/>
    </div> -->
  </div>
</template>

<script>
import { listCamera } from "@/api/monitoring/camera";
export default {
  name: "Stui",
  data() {
    return {
      isfour: false,
      onevideoUrl: {
        caddr: "",
        name: "",
      },
      fourVideo: [],
      faith: 0,
      arr: [],
    };
  },
  created() {
    this.jiekou();
  },

  methods: {
    faith_one() {
      this.isfour = false;
    },
    faith_four() {
      this.isfour = true;
    },
    jiekou() {
      var index = this.$route.path.split("/")[2];
      listCamera({ belongLocal: index }).then((response) => {
        this.fourVideo = response.rows;
        if (this.fourVideo.length < 1) {
          this.onevideoUrl.caddr = './shipin.flv'
        }
        this.onevideoUrl.caddr = this.fourVideo[0].caddr
        if (this.fourVideo.length >= 4) {
          this.fourVideo.forEach((item, i) => {
            if (i <= 3) {
              this.arr.push(item);
            }
          });
        } else {
          this.fourVideo.forEach((item, i) => {
            if (i <= 3) {
              this.arr.push(item);
            }
          });
        }
        if (response.rows.lenght > 0) {
          this.onevideoUrl = [...response.rows][0];
        }
      });
    },

    // 单分屏点击事件。
    clickhandleitem(data, index) {
      if (!this.isfour) {
        this.onevideoUrl = [...this.fourVideo][index];
      } else {
        this.arr[this.faith].caddr = this.fourVideo[index].caddr;
        this.faith += 1;
        if (this.faith == 4) {
          this.faith = 0;
          var index = this.$route.path.split("/")[2];
          listCamera({ belongLocal: index }).then((response) => {
            this.fourVideo = response.rows;
          });
        }
      }
    },
  },
};
</script>

<style scoped>
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
.isSubmit {
  margin-left: 46% !important;
  margin-right: 20%;
  margin-bottom: 5px;
  padding-bottom: 5px;
}
/* 按钮样式 */
::v-deep .el-radio-button:first-child .el-radio-button__inner {
  width: 82px;
  height: 36px;
  background: #196299;
  border-radius: 8px 0px 0px 8px;
  color: white;
  border: none;
  margin-right: 2px;
  font-size: 14px;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #ffffff;
}
::v-deep .el-radio-button__orig-radio:checked + .el-radio-button__inner {
  background: #006cff;
}
::v-deep .el-radio-button--medium .el-radio-button__inner {
  width: 82px;
  height: 36px;
  background: #196299;
  /* border-radius: 8px 0px 0px 8px; */
  color: white;
  border: none;
  margin-right: 2px;
  font-size: 14px;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #ffffff;
}

::v-deep .allFlv {
  width: 100px;
  height: 36px;
  color: white;
  margin-left: 5px;
  background: #196299;
  /* padding-left: 10px !important; */
  font-size: 14px;
  border: none;
  border-radius: 0px 8px 8px 0px;
}

::v-deep .allFlv1 {
  width: 82px;
  height: 36px;
  background: #196299;
  padding-left: 13px !important;
  font-size: 14px;
}

/* 全屏按钮 */
::v-deep .fullscreen .el-radio-button__inner {
  margin-left: 10px;
  background-image: url("../../../images/全屏 (1) 拷贝.png") !important;
  background-repeat: no-repeat !important;
  background-position: 17px 10px !important;
  padding-left: 38px;
  border-radius: 8px;
}

/* 水平线颜色 */
hr {
  background-color: #718a9d !important;
}

::v-deep .data .el-date-editor.el-input {
  width: 100%;
  border: none !important;
}
::v-deep .data .el-input__inner {
  width: 100% !important;
  border: none !important;
}
::v-deep .data .el-input--medium .el-input__icon {
  line-height: 36px;
  position: absolute;
  right: -223px;
  top: 2px;
}
::v-deep .data .el-input__inner {
  background-color: transparent !important;
  color: #fff;
  text-align: center;
  margin-top: 2px;
}
::v-deep .data .el-input__suffix {
  position: absolute;
  right: 281px;
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
/* 头部样式 */
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
/* 输入框 */
::v-deep .el-dialog .el-input__inner {
  background-color: #004d86 !important;
  width: 216px;
  color: white;
  margin-right: 20px;
  border: 1px solid white !important;
}
::v-deep .el-input__inner {
  border: 1px solid white !important;
}

/* 划过效果 */
::v-deep .el-tree-node__content:hover {
  /* color: #fff; */
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
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
  flex: 1;
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
::v-deep
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  background-color: #003156;
  height: 32px;
}
::v-deep .el-tree-node__content {
  height: 32px;
}
li {
  list-style: none;
}
::v-deep ul {
  /* height: 32px; */
  background: #003156;
}
::v-deep li {
  height: 32px;
  color: white;
  line-height: 32px;
}
.tree {
  margin-top: -15px;
}
video {
  background-color: rgba(0, 160, 233, 0.11);
}
.video1 {
  width: 100%;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
}
.app-container {
  padding: 15px 15px 0px 15px !important;
}
/* 选择通道按钮 */
.choose {
  width: 86px;
  height: 36px;
  color: white;
  background: #1d96ff;
  border-radius: 4px;
  border: none;
  display: none;
}
.choose:focus {
  outline: none;
}

/* 表格样式 */
::v-deep .el-table .el-table__header-wrapper th {
  background-color: #004d86 !important;
  color: white;
}
/* 标题弹框 */
::v-deep .el-dialog__title {
  color: white;
  /* border-bottom: 1px solid white; */
}
/* 标题下划线 */
::v-deep .el-dialog__header {
  border-bottom: 1px solid white;
}
::v-deep .el-dialog {
  background-color: #004d86 !important;
}
/* 树形样式 */
::v-deep .vue-treeselect__control {
  background-color: #004d86;
  width: 216px;
  margin-right: 25px;
  color: white;
}
.threeselects {
  width: 216px;
  margin-right: 22px;
}
::v-deep .vue-treeselect__single-value {
  color: white;
}

/* 划过效果 */
::v-deep li:hover {
  /* color: #fff; */
  background-color: #003156;
  background-image: url("../../../assets/images/选中.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
::v-deep .single {
  width: 82px;
  height: 36px;
  background: #196299;
  border: none;
  margin-left: 5px;
  color: white;
  font-size: 14px;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #ffffff;
  border-radius: 8px 0px 0px 8px;
}
::v-deep .singles {
  width: 82px;
  height: 36px;
  background: #196299;
  border: none;
  margin-left: 5px;
  color: white;
  font-size: 14px;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #ffffff;
  /* border-radius: 8px 0px 0px 8px; */
}
.single:focus {
  outline: none;
}
.singles:focus {
  outline: none;
}
.allFlv:focus {
  outline: none;
}
.bg {
  background: #006cff;
}
.fullscreen {
  width: 100%;
  height: calc(100vh - 200px);
}
.fourscreen {
  width: 49.5%;
  height: calc(50vh - 106px);
  margin-left: 5px;
  margin-bottom: 5px;
  position: relative;
}
</style>