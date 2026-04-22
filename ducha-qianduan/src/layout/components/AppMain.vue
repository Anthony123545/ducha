<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <keep-alive :include="cachedViews">
        <router-view :key="new Date().getTime()" />
      </keep-alive>
    </transition>
    <div class="tkbox" v-if="open">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../../assets/images/关闭.png" alt @click="showfun" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../../assets/images/警示.png" alt />
          <span>{{title}}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun">去处理</button>
      </div>
    </div>
    <div class="tkbox2" v-if="open2">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../../assets/images/关闭.png" alt @click="showfun2" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../../assets/images/警示.png" alt />
          <span>{{title2}}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun2">去处理</button>
      </div>
    </div>
    <div class="tkbox3" v-if="open3">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../../assets/images/关闭.png" alt @click="showfun3" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../../assets/images/警示.png" alt />
          <span>{{ title3 }}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun3">去处理</button>
      </div>
    </div>
    <div class="tkbox4" v-if="open4">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../../assets/images/关闭.png" alt @click="showfun4" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../../assets/images/警示.png" alt />
          <span>{{ title4 }}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun4">去处理</button>
      </div>
    </div>
    <div class="tkbox4" v-if="open7">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../../assets/images/关闭.png" alt @click="showfun7" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../../assets/images/警示.png" alt />
          <span>{{ opentitel7 }}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun7">去处理</button>
      </div>
    </div>
  </section>
</template>

<script>
import {
  printTrigger,
  printTriggerClear,
  printEachTrigger
} from "@/api/PrintsManage/manage";
import { workEachTrigger, workTrigger, getInfoD } from "@/api/pt";
import Cookies from "js-cookie";
export default {
  name: "AppMain",
  data() {
    return {
      open: false,
      timer: null,
      title: "",
      open2: false,
      title2: "",
      open3: false,
      open4: false,
      title3: "",
      title4: "",
      n: 0,
      open7: false,
      opentitel7: null
    };
  },
  created() {
    this.timer = null;
    this.startTime();
  },
  computed: {
    cachedViews() {
      return this.$store.state.tagsView.cachedViews;
    },
    key() {
      return this.$route.path;
    }
  },
  mounted() {
    // window.location.reload()
    //  this.$forceUpdate();
  },
  methods: {
    showfun() {
      this.open = false;
      printTriggerClear().then(res => {
        // console.log(res)
      });
    },
    showfun2() {
      this.open2 = false;
      printTriggerClear().then(res => {
        // console.log(res)
      });
    },
    showfun3() {
      this.open3 = false;
      printTriggerClear().then(res => {
        // console.log(res)
      });
    },
    showfun4() {
      this.open4 = false;
      printTriggerClear().then(res => {
        // console.log(res)
      });
    },
    showfun7() {
      this.open7 = false;
      printTriggerClear().then(res => {});
    },
    quchulifun() {
      printTriggerClear().then(res => {
        if (res.code == 200) {
          window.open(`/print/manage`, "_self");
        }
      });
    },
    quchulifun2() {
      printTriggerClear().then(res => {
        if (res.code == 200) {
          this.open = false;
          window.open(`/print/manage`, "_self");
        }
      });
    },
    quchulifun3() {
      printTriggerClear().then(res => {
        if (res.code == 200) {
          this.open3 = false;
          window.open(`/workingArrangements/workTask`, "_self");
        }
      });
    },
    quchulifun4() {
      printTriggerClear().then(res => {
        if (res.code == 200) {
          this.open4 = false;
          window.open(`/workingArrangements/workTask`, "_self");
        }
      });
    },
    quchulifun7() {
      printTriggerClear().then(res => {
        if (res.code == 200) {
          window.open(`/#/barracksManagement/repair`, "_self");
        }
      });
    },
    startTime() {
      this.timer = setInterval(() => {
        // printTrigger().then(res => {
        //   if (res.code == 200) {
        //     if (res.print == "print") {
        //       if (res.numb != 0) {
        //         this.title2 = "您有打印文件需要处理，请尽快处理";
        //         this.open2 = true;
        //       }
        //     }
        //   }
        // });
        printEachTrigger().then(res => {
          if (res.code == 200) {
            if (res.print == "printUserInfo") {
              var sendId = Cookies.get("userId");
              if (res.printUserInfo == sendId) {
                if (res.numb != 0) {
                  this.title = res.msg;
                  this.open = true;
                }
              }
            }
          }
        });
        workEachTrigger().then(res => {
          var sendId = Cookies.get("userId");
          if (res.code == 200) {
            if (res.workEach != 0) {
              if (res.workUserid == sendId) {
                this.title3 = res.msg;
                this.open3 = true;
              }
            }
          }
        });
        workTrigger().then(res => {
          // var sendId = Cookies.get("userId");
          if (res.code == 200) {
            if (res.workRequest != 0) {
              this.title4 = res.msg;
              this.open4 = true;
            }
          }
        });
        getInfoD().then(res => {
          if (res.code == 200) {
            if (res.data != 0) {
              this.opentitel7 = res.data;
              this.open7 = true;
            }
          }
        });
      }, 10000); /* 每500毫秒执行一次，实现动态显示时间效果 */
    }
  },
  destroyed() {
    clearInterval(this.timer); // 清除定时器
    this.timer = null;
  }
};
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
  position: relative;
}

.tkbox {
  width: 500px;
  height: 220px;
  background-image: url("../../assets/images/框.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: absolute;
  left: 20%;
  top: 10%;
}

.tkbox2 {
  width: 500px;
  height: 220px;
  background-image: url("../../assets/images/框.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: absolute;
  right: 26%;
  top: 10%;
}

.tkbox3 {
  width: 500px;
  height: 220px;
  background-image: url("../../assets/images/框.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: absolute;
  left: 20%;
  top: 15%;
}

.tkbox4 {
  width: 500px;
  height: 220px;
  background-image: url("../../assets/images/框.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: absolute;
  right: 26%;
  top: 15%;
}

.tkbox-title {
  width: 100%;
  height: 90px;
}

.tkbox-title-left {
  width: 90%;
  display: inline-block;
  line-height: 90px;
  position: relative;
}

.tkbox-title-left img {
  position: absolute;
  left: 35px;
  top: 30px;
}

.tkbox-title-left span {
  color: #fff;
  line-height: 95px;
  margin-left: 75px;
}

.tkbox-title-right {
  width: 10%;
  text-align: right;
  display: inline-block;
}

.tkbox-title-right img {
  text-align: right;
  margin-right: 30px;
}

.tkbox-main {
  width: 100%;
  height: 60px;
}

.tkbox-main p {
  width: 90%;
  height: auto;
  margin: 0 auto;
  color: #fff;
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.tkbox-main p span {
  margin-left: 10px;
}

.tkbox-footer {
  width: 100%;
  height: 30px;
  margin-bottom: 30px;
  text-align: center;
}

.fixed-header + .app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(100vh - 84px);
  }

  .fixed-header + .app-main {
    padding-top: 84px;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 17px;
  }
}
// 设置背景颜色
.app-main {
  background: #022845;
}
// 头部背景颜色、字体颜色
.navbar {
  background: #114a80 !important;
  color: #fff !important;
  // box-shadow:5px 5px 10px black;
  border-bottom: 1px solid #15324e;
}
.scroll-container .el-scrollbar__wrap .el-scrollbar__view {
  background-color: #114a80;
}
.app-breadcrumb.el-breadcrumb .no-redirect {
  color: #fff !important;
}
.el-breadcrumb__inner a {
  color: #fff !important;
}
.tags-view-container {
  background: #022845 !important;
  border: 0px !important;
}

.navbar .right-menu .avatar-container .avatar-wrapper .el-icon-caret-bottom {
  color: #fff !important;
}
.navbar .right-menu .right-menu-item {
  color: #fff !important;
}

// 设置主题内外边距
.app-container {
  margin: 10px 10px 10px 10px;
  padding: 15px 15px 15px 15px;
}
.tkbox-footer button {
  width: 60px;
  height: 25px;
  text-align: center;
  line-height: 21px;
  background-color: #409eff;
  color: #fff;
  border-radius: 6px;
  border: none;
}
</style>
