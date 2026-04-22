<!--<template>-->
<!--&lt;!&ndash;  <div class="fatherDiv">&ndash;&gt;-->
<!--&lt;!&ndash;    <div ref="videoBox" id="divPlugin" class="plugin">nihao</div>&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;    <el-button @click="remove">清除</el-button>&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;    <button @click="togglePlugin">显示/隐藏插件</button>&ndash;&gt;-->
<!--&lt;!&ndash;  </div>&ndash;&gt;-->
<!--  <div>-->
<!--    <div  class="plugin"  id="divPlugin"></div>-->
<!--    <div class="test">-->
<!--      <div  class="plugin1"  id="divPlugin1"></div>-->
<!--    </div>-->
<!--  </div>-->

<!--</template>-->

<!--<script>-->
<!--import $ from 'jquery'-->
<!--export default {-->
<!--  name: "videoPlugin",-->
<!--  data() {-->
<!--    return {-->
<!--      top: null,-->
<!--      left: null,-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    initPlugin() {-->
<!--      // 初始化插件参数及插入插件-->
<!--      WebVideoCtrl.I_InitPlugin({-->
<!--        bWndFull: true,     //是否支持单窗口双击全屏，默认支持 true:支持 false:不支持-->
<!--        iWndowType: 2,-->
<!--        cbInitPluginComplete: function () {-->
<!--          WebVideoCtrl.I_InsertOBJECTPlugin("divPlugin").then(() => {-->
<!--            // 检查插件是否最新-->
<!--            WebVideoCtrl.I_CheckPluginVersion().then((bFlag) => {-->
<!--              if (bFlag) {-->
<!--                alert("检测到新的插件版本，双击开发包目录里的HCWebSDKPlugin.exe升级！");-->
<!--              }-->
<!--            });-->

<!--            // 初始化插件参数及插入插件-->
<!--            WebVideoCtrl.I_InitPlugin({-->
<!--              bWndFull: true,     //是否支持单窗口双击全屏，默认支持 true:支持 false:不支持-->
<!--              iWndowType: 7,-->
<!--              cbInitPluginComplete: function () {-->
<!--                WebVideoCtrl.I_InsertOBJECTPlugin("divPlugin1").then(() => {-->
<!--                  // 检查插件是否最新-->
<!--                  WebVideoCtrl.I_CheckPluginVersion().then((bFlag) => {-->
<!--                    if (bFlag) {-->
<!--                      alert("检测到新的插件版本，双击开发包目录里的HCWebSDKPlugin.exe升级！");-->
<!--                    }-->
<!--                  });-->
<!--                }, () => {-->
<!--                  alert("插件初始化失败，请确认是否已安装插件；如果未安装，请双击开发包目录里的HCWebSDKPlugin.exe安装！");-->
<!--                });-->
<!--              }-->
<!--            });-->
<!--          }, () => {-->
<!--            alert("插件初始化失败，请确认是否已安装插件；如果未安装，请双击开发包目录里的HCWebSDKPlugin.exe安装！");-->
<!--          });-->
<!--        }-->
<!--      });-->


<!--    },-->

<!--    updateWindowWidth() {-->
<!--      console.log("🚀 ~ file:videoPlugin method:updateWindowWidth line:60 -&#45;&#45;&#45;&#45;", "resize");-->
<!--      setTimeout(() => {-->
<!--        this.$router.go(0);-->
<!--      }, 2000);-->
<!--    }-->
<!--  },-->
<!--  mounted() {-->
<!--    this.initPlugin()-->
<!--    // window.addEventListener('resize', this.updateWindowWidth);-->
<!--  },-->
<!--  beforeDestroy() {-->
<!--    this.$router.go(0);-->
<!--  }-->
<!--};-->
<!--</script>-->

<!--<style scoped>-->
<!--/*.fatherDiv {*/-->
<!--/*  width:300px;*/-->
<!--/*  height:200px;*/-->
<!--/*  !*margin-left: 10%;*!*/-->
<!--/*}*/-->
<!--/*插件*/-->
<!--.plugin-->
<!--{-->
<!--  /*width:100%;*/-->
<!--  /*height:100%;*/-->
<!--  /*display: none;*/-->
<!--  width:500px;-->
<!--  height:300px;-->
<!--  /*position: fixed;*/-->
<!--  /*top: 100px; !* 距离页面顶部的距离 *!*/-->
<!--  /*right: 20px; !* 距离页面右侧的距离 *!*/-->
<!--  margin-left: 500px;-->

<!--}-->
<!--.test {-->
<!--  width: 200px;-->
<!--  height: 100px;-->
<!--  overflow: hidden;-->
<!--  /*position: fixed;*/-->
<!--}-->
<!--.plugin1 {-->
<!--  /*width:100%;*/-->
<!--  /*height:100%;*/-->
<!--  /*display: none;*/-->
<!--  width:500px;-->
<!--  height:300px;-->
<!--  /*position: fixed;*/-->
<!--  /*top: 100px; !* 距离页面顶部的距离 *!*/-->
<!--  /*right: 20px; !* 距离页面右侧的距离 *!*/-->
<!--  /*margin-left: 500px;*/-->
<!--  right: 0;-->
<!--  bottom: 0;-->
<!--  position: absolute;-->
<!--}-->
<!--</style>-->