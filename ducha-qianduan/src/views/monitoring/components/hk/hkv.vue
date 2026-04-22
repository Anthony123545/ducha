<template>
  <div class="video-player">
    <div id="divPlugin2" class="divPlugin"></div>
  </div>
</template>
<script>
export default {
  name: "videoPlayer",
  props: {
    //从父组件传来的数据，IP、用户名、密码、端口号
    companyVideoData: {
      type: Array,
    },
    //分屏
    videoType: Number,
  },
  data() {
    return {
      plugin: true,
      zoom: window.devicePixelRatio,
      iWidth: "100%",
      iHeight: "100%",
      webVideo: {},
      iStreamType: 1,
      iProtocol: 1,
      bZeroChannel: false,
      xmlDocData: {},
      g_iWndIndex: -1, //可以不用设置这个变量，有窗口参数的接口中，不用传值，开发包会默认使用当前选择窗口
      videoNum: "",
      deviceId: [],
      refreshMark: true,
      wndNum: 4,
    };
  },
  created() {},
  mounted() {
    this.deviceId = new Array();
    this.videoInitPlugin();
    this.refreshMark = true;
  },
  beforeUnmount() {
    window.removeEventListener("resize", this.handleResize);
  },
  destroyed() {
    this.$router.go(0);
  },
  methods: {
    handleResize() {
      this.zoom = window.devicePixelRatio;
      let h
      let w
      if(document.body.clientWidth>=(document.body.clientHeight*2)){
        h=document.body.clientHeight
        w=document.body.clientHeight*(2200/1080)
      }else{
        h=document.body.clientWidth/(2200/1080)
        w=document.body.clientWidth
      }
      setTimeout(() => {
        // console.log("this.zoom :>> ", this.zoom);
        WebVideoCtrl.I_Resize(w/3.9, h/2.8);
      }, 1000);
    },
    stopAll() {
      this.refreshMark = false;
      WebVideoCtrl.I_StopAllPlay();
      console.log(" 停止全部播放");
      this.logoutAll();
    },
    logoutAll() {
      for (let i = 0; i < this.deviceId.length; i++) {
        WebVideoCtrl.I_Logout(this.deviceId[i]);
        console.log("退出登录ip： ", this.deviceId[i]);
      }
      this.deviceId = [];
    },
    videoInitPlugin() {
      var iRet = WebVideoCtrl.I_CheckPluginInstall();
      if (iRet === -1) {
        alert("您还未安装过插件，请安装WebComponentsKit.exe插件！");
        return;
      }
      this.initPlugin();
    },
    //插件初始化
    initPlugin() {
      var that = this;
      console.log("hkv 开始初始化插件 >> ", that.g_iWndIndex);
      WebVideoCtrl.I_InitPlugin({
        bWndFull: true,
        iWndowType: 2,
        bDebugMode: true,
        cbSelWnd: function (xmlDoc) {
          that.g_iWndIndex = parseInt($(xmlDoc).find("SelectWnd").eq(0).text(), 10);
        },
      });
      console.log("🚀 初始化插件完成 >> ", that.g_iWndIndex);
      console.log("🚀 插入插件");
      let h
      let w
      if(document.body.clientWidth>=(document.body.clientHeight*2)){
        h=document.body.clientHeight
        w=document.body.clientHeight*(2200/1080)
      }else{
        h=document.body.clientWidth/(2200/1080)
        w=document.body.clientWidth
      }
      WebVideoCtrl.I_InsertOBJECTPlugin("divPlugin2").then(() => {
        WebVideoCtrl.I_Resize(w/3.9, h/2.8).then(() => {
          console.log("🚀 data >> ", that.companyVideoData);
          this.getLoginParams().then((res) => {
            console.log("🚀 解析完 >> ", res);
            this.login(res);
          });
        })
        window.addEventListener("resize", that.handleResize);
      });
      console.log("🚀 插入完成");
    },
    async getLoginParams() {
      var loginParams = {};
      var that = this;
      let videoData = JSON.parse(JSON.stringify(that.companyVideoData))
      for (const item of videoData) {
        if (item !== undefined) {
          const key = JSON.stringify(item);
          if (!loginParams.hasOwnProperty(key)) {
            loginParams[key] = item;
          }
        }
      }
      console.log("paras: ", loginParams)
      return loginParams;
    },
    async login(loginParams) {
      var that = this;
      // for (const loginParam of Object.values(loginParams)) {
      //   await that.doLogin(loginParam)
      // }
      var windowIndex = 0;
      Object.values(loginParams).forEach((item) => {
        item["windowIndex"] = windowIndex;
        windowIndex += 1;
      })
      const loginResultList =  await Promise.all(
        Object.values(loginParams).map(async (loginParam) => {
          const result = await that.doLogin(loginParam);
          console.log("🚀 result >> ", result);
          return result;
        })
      );
      console.log("🚀 results >> ", loginResultList);
    },
    async doLogin(loginParam) {
      console.log("🚀 loginPara >> ", loginParam);
      var that = this;
      WebVideoCtrl.I_Login(
        loginParam.ip,
        1,
        loginParam.Port,
        loginParam.Username,
        loginParam.Password,
        {
          timeout:1000,
          success: function(xmlDoc) {
            console.log("🚀 登录成功 >> ", loginParam.ip, loginParam.Port);
            that.doGetChannelInfo(loginParam.ip).then((channels) => {
              var s = JSON.stringify(channels);
              console.log("tezt", JSON.stringify(channels), channels.length, channels[0], channels[0].channelId, channels[0]["channelId"])
              // console.log("🚀 通道列表 >> ", loginParam.ip, channels, channels.length, s[0]["channelName"]);
              var channelId = that.getChannelIdByName(loginParam.channelName, channels);
              console.log("🚀 获得id >> ", channelId);
              if (channelId !== null) {
                console.log("🚀 获得id >> ", channelId);
                that.startPlay(loginParam.ip, channelId, loginParam.windowIndex);
              } else
                console.log("🚀 通道号无效 >> ", loginParam.ip, loginParam.channelName);
            })
          },
          error: function (oError) {
            if (oError.errorCode === 2001) {
              that.doGetChannelInfo(loginParam.ip).then((channels) => {
                var s = JSON.stringify(channels);
                console.log("tezt", JSON.stringify(channels), channels.length, channels[0], channels[0].channelId, channels[0]["channelId"])
                // console.log("🚀 通道列表 >> ", loginParam.ip, channels, channels.length, s[0]["channelName"]);
                var channelId = that.getChannelIdByName(loginParam.channelName, channels);
                console.log("🚀 获得id >> ", channelId);
                if (channelId !== null) {
                  console.log("🚀 获得id >> ", channelId);
                  that.startPlay(loginParam.ip, channelId, loginParam.windowIndex);
                } else
                  console.log("🚀 通道号无效 >> ", loginParam.ip, loginParam.channelName);
              })
            } else
              console.log("🚀 登录失败 >> ", loginParam.ip, loginParam.Port);
          },
        }
      );
    },
    getChannelIdByName(channelName, channels) {
      const channel = channels.find((channel) => {
        if (channel["channelName"] == channelName) {
          return channel;
        }
      });
      if (channel) {
        return channel.channelId;
      } else {
        return null;
      }
    },
    doGetChannelInfo(ip) {
      return new Promise((resolve, reject) => {
        var getAnalogChannelInfo = new Promise((resolve, reject) => {
          WebVideoCtrl.I_GetAnalogChannelInfo(ip, {
            success: function(xmlDoc) {
              let channels = [];
              var oChannels = $(xmlDoc).find("VideoInputChannel");
              $.each(oChannels, function(i) {
                var id = $(this).find("id").eq(0).text(),
                  name = $(this).find("name").eq(0).text();
                if ("" == name) {
                  name = "Camera " + (i < 9 ? "0" + (i + 1) : (i + 1));
                }
                channels.push({
                  channelId: id,
                  channelName: name,
                })
              });
              console.log("🚀 获取模拟通道成功 >> ", channels);
              resolve(channels);
            },
            error: function(oError) {
              console.log("🚀 获取模拟通道失败 >> ", ip, oError.errorCode, oError.errorMsg);
            },
          })
        });
        var getDigitalChannelInfo = new Promise((resolve, reject) => {
          WebVideoCtrl.I_GetDigitalChannelInfo(ip, {
            success: function(xmlDoc) {
              let channels = [];
              var oChannels = $(xmlDoc).find("InputProxyChannelStatus");
              $.each(oChannels, function(i) {
                var id = $(this).find("id").eq(0).text(),
                  name = $(this).find("name").eq(0).text(),
                  online = $(this).find("online").eq(0).text();
                if ("false" == online) {// 过滤禁用的数字通道
                  return true;
                }
                if ("" == name) {
                  name = "IPCamera " + (i < 9 ? "0" + (i + 1) : (i + 1));
                }
                channels.push({
                  channelId: id,
                  channelName: name,
                })
              });
              console.log("🚀 获取数字通道成功 >> ", channels);
              resolve(channels);
            },
            error: function(oError) {
              console.log("🚀 获取数字通道失败 >> ", ip, oError.errorCode, oError.errorMsg);
              resolve([]);
            }
          });
        });
        var getZeroChannelInfo = new Promise((resolve, reject) => {
          WebVideoCtrl.I_GetZeroChannelInfo(ip, {
            success: function(xmlDoc) {
              let channels = [];
              var oChannels = $(xmlDoc).find("ZeroVideoChannel");
              $.each(oChannels, function(i) {
                var id = $(this).find("id").eq(0).text(),
                  name = $(this).find("name").eq(0).text();
                if ("" == name) {
                  name = "Zero Channel " + (i < 9 ? "0" + (i + 1) : (i + 1));
                }
                if ("true" == $(this).find("enabled").eq(0).text()) {// 过滤禁用的零通道
                  channels.push({
                    channelId: id,
                    channelName: name,
                  })
                }
              });
              console.log("🚀 获取零通道成功 >> ", channels);
              resolve(channels);
            },
            error: function(oError) {
              console.log("🚀 获取零通道失败 >> ", ip, oError.errorCode, oError.errorMsg);
              resolve([]);
            }
          });
        });
        Promise.allSettled([getAnalogChannelInfo, getDigitalChannelInfo, getZeroChannelInfo])
          .then((resList) => {
            console.log("🚀 res >> ", resList);
            const fulfilledValues = resList.reduce((list, result) => {
              if (result.status === 'fulfilled' && result.value.length !== 0) {
                const value = result.value;
                list.push(value);
              }
              return list;
            }, []);
            resolve(fulfilledValues.flat());
          })
          .catch((error) => {
            console.log("🚀 获取通道失败 >> ", ip);
          });
      })
    },
    startPlay(ip, channelId, windowIndex) {
      // this.g_iWndIndex = this.g_iWndIndex + 1;
      WebVideoCtrl.I_Stop(windowIndex).then(() => {
        console.log("🚀 开始播放 >> ", ip, channelId, windowIndex);
        WebVideoCtrl.I_StartRealPlay(ip, {
          iStreamType: 1,
          iChannelID: channelId,
          iWndIndex: windowIndex
        })
      })
    },
    clickLogin() {
      return new Promise((resolve) => {
        const uniqueArr = [];
        const uniqueSet = new Set();
        this.companyVideoData.forEach((obj) => {
          if (obj !== undefined) {
            const key = JSON.stringify(obj);
            if (!uniqueSet.has(key)) {
              uniqueSet.add(key);
              uniqueArr.push(obj);
            }
          }
        });
        console.log(uniqueArr);
        for (let index = 0; index < uniqueArr.length; index++) {
          this.singleLogin(uniqueArr[index], index);
          this.deviceId.push(uniqueArr[index].ip);
        }
        // 完成登录操作后调用resolve
        resolve();
      });
    },
    singleLogin(Data, index) {
      setTimeout(() => {
        var that = this;
        WebVideoCtrl.I_Login(
          Data.ip,
          1,
          Data.Port,
          Data.Username,
          Data.Password,
          {
            timeout: 1000,
            async: true,
            success: function (xmlDoc) {
              console.log("开始预览"); //不能删除
              let oChannels = that.getChannelInfo();
              that.initPlay(Data, index,oChannels);
            },
            error: function () {
              console.log("login error");
            },
          }
        );
      }, 100);
    },
    //获取通道
    getChannelInfo() {
      var that = this;
      // 模拟通道
      WebVideoCtrl.I_GetAnalogChannelInfo(this.szIP, {
        async: false,
        success: function (xmlDoc) {
          var oChannels = $(xmlDoc).find("VideoInputChannel");
          nAnalogChannel = oChannels.length;
          console.log("获取模拟通道成功！");
          return oChannels
        },
        error: function () {
          console.log("获取模拟通道失败！" + WebVideoCtrl.I_GetLastError());
        },
      });
      // 数字通道
      WebVideoCtrl.I_GetDigitalChannelInfo(that.companyVideoData.ip, {
        async: false,
        success: function (xmlDoc) {
          var oChannels = $(xmlDoc).find("InputProxyChannelStatus");
          return oChannels
        },
        error: function () {
          WebVideoCtrl.I_GetLastError();
          console.log("获取数字通道失败！" + WebVideoCtrl.I_GetLastError());
        },
      });
      // 零通道
      WebVideoCtrl.I_GetZeroChannelInfo(this.szIP, {
        async: false,
        success: function (xmlDoc) {
          var oChannels = $(xmlDoc).find("ZeroVideoChannel");
          return oChannels
        },
        error: function () {
          console.log("获取零通道失败！" + WebVideoCtrl.I_GetLastError());
        },
      });
    },
    //初始化视频，为了让用户进来就可以看到视频播放
    initPlay(data, index,oChannels) {
      let szIP = data.ip; //ip地址
      //循环16次是因为插件分屏最大为4x4（可以根据情况而定）
      WebVideoCtrl.I_StartRealPlay(szIP, {
        iStreamType: 1,
        iChannelID: oChannels.id,//按格式修改下
        iWndIndex: index,
      });
    },
    // 点击查看具体哪个监控
    startRealPlay(oChannels) {
      let that = this;
      let szIP = this.companyVideoData.ip; //ip地址
      let iChannelID = oChannels; //播放通道号
      var iStreamType = 1;
      var oWndInfo = WebVideoCtrl.I_GetWindowStatus(that.g_iWndIndex);
      if (oWndInfo != null) {
        // 已经在播放了，先停止
        WebVideoCtrl.I_Stop();
      }
      WebVideoCtrl.I_StartRealPlay(szIP, {
        iStreamType: iStreamType,
        iChannelID: iChannelID,
      });
    },
  },
  //因为项目中需求，总公司要查看所有子公司的监控，所以做了个监听来切换查看各个子公司的监控
  watch: {
    companyVideoData: {
      handler(val, oldVal) {
        console.log("🚀 val >> ", val, oldVal);
        if (oldVal.length !== 0) {
          console.log("🚀 oldVal >> ", oldVal);
          for (const item of oldVal) {
            if (item !== undefined) {
              console.log("🚀 退出登录 >> ", item.ip);
              WebVideoCtrl.I_Logout(item.ip)
            }
          }
          for (let i = 0; i < this.wndNum; i++) {
            WebVideoCtrl.I_Stop(i);
            console.log("🚀 关闭 >> ", i);
          }
          this.getLoginParams().then((res) => {
            console.log("🚀 解析完 >> ", res);
            this.login(res);
          });
          // for (let i = 0; i < this.wndNum; i++) {
          //   WebVideoCtrl.I_Stop(i);
          //   console.log("🚀 关闭 >> ", i);
          //   WebVideoCtrl.I_StopAllPlay ()
          // }
          // WebVideoCtrl.I_StopAllPlay().then(() => {
          //   console.log("🚀 采集卡全关 >> ", );
          //   this.getLoginParams().then((res) => {
          //     console.log("🚀 解析完 >> ", res);
          //     this.login(res);
          //   });
          // })
        }
      },
    },
    videoType: {
      handler(val, oldval) {
        if (oldval) {
          WebVideoCtrl.I_ChangeWndNum(this.videoType); //分屏
          this.initPlay();
        }
      },
      immediate: true, //关键
      deep: true,
    },
  },
};
</script>
<style scoped>
#video-player {
  width: 100%;
  height: 100%;
  position: relative;
}
#video-player #divPlugin {
  width: 100%;
  height: 100%;
  color: red;
  /* display: flex; */
  justify-content: center;
  align-items: center;
  font-size: 18px;
  position: fixed;
  /* top: 680px; */
  /* top: 0px; */
  /* right: 0px; */

  overflow: hidden;
  position: absolute;
  right: 0;
  bottom: 0;
}
.down {
  width: 100%;
  height: 100%;
  color: red;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 18px;
}
.down a {
  color: red;
}
</style>
