<template>
  <div
    id="divPluginMonitorScreen"
    ref="pluginWrapper"
    style="width: 100%; height: 100%; position: relative; overflow: hidden"
  ></div>
</template>
<script>

export default {
  name: "monitorHK",
  props: {
    // 从父组件传来的数据，IP、用户名、密码、端口号
    companyVideoData: {
      type: Array,
      required: true,
    },
    num: {
      type: Number,
      required: true,
      validator(val) {
        // 确保 num 是一个数字
        return !isNaN(val);
      },
    },
    // 分屏
    videoType: Number,
    bofang: {
      type: Boolean,
      default: false,
    },
    chooseWindow: {
      type: Number,
      required: true,
    },
    isFullScreen: Boolean,
  },
  data() {
    return {
      plugin: true,
      iWidth: "100%",
      iHeight: "100%",
      webVideo: {},
      iStreamType: 1,
      iProtocol: 1,
      bZeroChannel: false,
      xmlDocData: {},
      g_iWndIndex: 0, //可以不用设置这个变量，有窗口参数的接口中，不用传值，开发包会默认使用当前选择窗口
      videoNum: "",
      videoData: [],
      iWndowType: 1,
      windowNum: this.num,
      zoom: window.devicePixelRatio,
      width: 1980,
      height: 1080,

      pluginInited: false,
    };
  },
  created() {},
  mounted() {
    this.videoInitPlugin();
    // 初始更新
    this.$nextTick(() => {
      this.updateSize();
    });

    // 监听窗口变化
    window.addEventListener("resize", this.handleResize);
  },
  beforeUnmount() {
    window.removeEventListener("resize", this.updateSize);
    window.removeEventListener("resize", this.handleResize);
    if (this.resizeTimeout) clearTimeout(this.resizeTimeout);
  },
  watch: {
    num: {
      handler(val) {
        console.log("监控num值的变化:", val);
        this.windowNum = val;
        console.log("监控windowNum值:", this.windowNum);
        console.log("开始分屏num", this.windowNum);
        WebVideoCtrl.I_ChangeWndNum(this.windowNum);
        // const that = this;
        // WebVideoCtrl.I_InitPlugin({
        //   bWndFull: true, //是否支持单窗口双击全屏，默I_CheckPluginInstall
        //   iWndowType: this.iWndowType,
        //   bDebugMode: true,
        //   cbSelWnd: function (xmlDoc) {
        //     // 获取当前窗口
        //     that.g_iWndIndex = parseInt(
        //       $(xmlDoc).find("SelectWnd").eq(0).text(),
        //       10,
        //     );
        //     // var szInfo = "当前选择的窗口编号：" + that.g_iWndIndex;
        //     that.dangqianchuangkou = that.g_iWndIndex;
        //     that.$emit("change-window", this.dangqianchuangkou);
        //     console.log("当前选择窗口编号:", that.g_iWndIndex);
        //   },
        // });
      },
    },
    // companyVideoData: {
    //   handler(val) {
    //     console.log("companyVideoData:", val);
    //     const layoutMap = [
    //       { max: 1, num: 1, wnd: 1 },
    //       { max: 4, num: 4, wnd: 2 },
    //       { max: 9, num: 9, wnd: 3 },
    //       { max: 16, num: 16, wnd: 4 },
    //     ];
    //
    //     let layout =
    //       layoutMap.find((l) => val.length <= l.max) ||
    //       layoutMap[layoutMap.length - 1];
    //
    //     this.windowNum = layout.num;
    //     console.log("开始分屏companyVideoData", layout.wnd);
    //     WebVideoCtrl.I_ChangeWndNum(layout.wnd);
    //
    //     this.$emit("button-value", this.windowNum);
    //   },
    //   // immediate: true, //关键
    //   // deep: true,
    // },
    videoType: {
      handler(val, oldval) {
        if (oldval) {
          console.log("开始分屏videoType", this.videoType);
          WebVideoCtrl.I_ChangeWndNum(this.videoType); //分屏
          this.initPlay();
        }
      },
      immediate: true, //关键
      deep: true,
    },
    isFullScreen() {
      this.$nextTick(() => {
        this.updateSize();
      });
    },
  },
  // destroyed() {
  //   this.$router.go(0);
  // },
  methods: {
    // 插件初始化
    async initPlugin() {
      if (this.pluginInited) return; // 避免重复初始化
      this.pluginInited = true;

      this.iWndowType = Math.floor(this.windowNum);

      console.log("初始化插件, iWndowType:", this.iWndowType);

      await this.$nextTick();

      const that = this;
      const container = that.$refs.pluginWrapper;
      if (!container) {
        console.error("插件容器未找到");
        return;
      }
      const containerId = container.id && container.id.trim() !== ''
        ? container.id
        : 'divPluginMonitorScreen';
      try {
        console.log("monitorHK 开始初始化插件", containerId);
        // 初始化插件参数
        WebVideoCtrl.I_InitPlugin(
          "100%", // szWidth 插件的宽度（单位为”px”， 100%表示撑满插件容器）
          "100%", // szHight 插件的高度（单位为”px”， 100%表示撑满插件容器）
          {
            // szColorProperty: "plugin-background:ffffff; sub-background:ffffff; sub-border:ffffff; sub-border-select:ffffff",
            // 插件的背景颜色。表示插件的背景颜色，子窗口的背景颜色，子窗口的边框颜色，子窗框选中的边框颜色
            iWndowType: 1, // 分屏类型：1- 1*1，2- 2*2，3- 3*3，4- 4*4 默认值为 1，单画面
            bWndFull: true, //单窗口双击全屏，默认支持
            // iPlayMode: 2, // 播放模式，默认值为 2，正常播放模式。暂不支持其它模式
            bDebugMode: true, // JS 调试模式，控制台打印调试信息，
            cbSelWnd: function (xmlDoc) { // 窗口选中事件回调函数
              let g_iWndIndex = parseInt($(xmlDoc).find("SelectWnd").eq(0).text(), 10);
              const szInfo = '当前选择的窗口编号：' + g_iWndIndex
              console.log(szInfo);
              that.g_iWndIndex = g_iWndIndex;
              that.dangqianchuangkou = that.g_iWndIndex;
              that.$emit("changeWindow", g_iWndIndex);
            },
            cbDoubleClickWnd: function (iWndIndex, bFullScreen) { // 窗口双击回调函数，
              let szInfo = '当前放大的窗口编号：' + iWndIndex
              if (!bFullScreen) {
                szInfo = "当前还原的窗口编号：" + iWndIndex;
              }
              console.log(szInfo);
            },
          }
        );

        // WebVideoCtrl.I_InitPlugin({
        //   bWndFull: true, // 是否支持单窗口双击全屏，默I_CheckPluginInstall
        //   iWndowType: this.iWndowType,
        //   bDebugMode: true,
        //   cbSelWnd: function (xmlDoc) {
        //     const index = parseInt($(xmlDoc).find("SelectWnd").eq(0).text(), 10);
        //     // 获取当前窗口
        //     that.g_iWndIndex = index;
        //     that.$emit("changeWindow", index);
        //     console.log("🟢 当前选中窗口：", index);
        //   },
        //   cbDoubleClickWnd: function (iWndIndex, bFullScreen) {
        //     let info = bFullScreen
        //       ? `当前放大的窗口编号: ${iWndIndex}`
        //       : `当前还原的窗口编号: ${iWndIndex}`;
        //     console.log(info);
        //   },
        // });

        // 嵌入无插件
        let szContainerID = "divPluginMonitorScreen";
        WebVideoCtrl.I_InsertOBJECTPlugin(szContainerID);

        WebVideoCtrl.I_Resize(container.clientWidth, container.clientHeight);

        console.log("插件初始化成功！");

        // 可在此登录视频或获取参数
        const loginParams = await this.getLoginParams();
        console.log("登录参数解析完成:", loginParams);

      } catch (error) {
        console.error("初始化插件失败:", error);
      }

      // // 插件插入到 div
      // // 获取 div 容器真实宽高
      // const pluginDiv = document.getElementById("divPluginMonitorScreen");
      // console.log("🚀 获取插件容器真实宽高:", pluginDiv);
      // const { w, h } = { w: pluginDiv.clientWidth, h: pluginDiv.clientHeight };
      //
      // const el = this.$refs.pluginWrapper;
      // console.log("4:that.$refs.pluginWrapper :", that.$refs.pluginWrapper);
      // if (el) {
      //   console.log("4:el:", el);
      //   console.log("4:el:", el.getBoundingClientRect());
      //   console.log(
      //     "4:clientWidth:",
      //     el.clientWidth,
      //     "clientHeight:",
      //     el.clientHeight,
      //   );
      //   // this.iWidth = el.clientWidth;
      //   // this.iHeight = el.clientHeight;
      // }
      //
      // console.log("🚀 插件尺寸:", w, h);
      // await WebVideoCtrl.I_InsertOBJECTPlugin("divPluginMonitorScreen");
      // // await WebVideoCtrl.I_Resize(w, h);
      // await WebVideoCtrl.I_Resize(400, 300);

      // console.log("插件初始化完成");
    },
    handleResize() {
      // 防抖：50ms 内连续 resize 只触发一次
      if (this.resizeTimeout) clearTimeout(this.resizeTimeout);
      this.resizeTimeout = setTimeout(() => {
        this.updateSize();
      }, 50);
    },
    updateSize() {
      const el = this.$refs.pluginWrapper;
      if (!el) return;

      // 获取元素最新尺寸（包含 padding）
      const rect = el.getBoundingClientRect();
      this.iWidth = rect.width;
      this.iHeight = rect.height;

      console.log("组件宽:", this.iWidth, "组件高:", this.iHeight);

      // 调用 WebVideoCtrl，保证 DOM 更新完成后执行
      this.$nextTick(() => {
        if (typeof WebVideoCtrl !== "undefined" && WebVideoCtrl.I_Resize) {
          WebVideoCtrl.I_Resize(this.iWidth, this.iHeight);
        }
      });
    },
    videoInitPlugin() {
      try {
        console.log("WebVideoCtrl", WebVideoCtrl);
        // 检查插件对象是否存在
        if (typeof WebVideoCtrl === "undefined") {
          alert("插件脚本未加载，请检查 WebVideoCtrl.js 是否正确引入！");
          return;
        }

        // 检查浏览器是否支持无插件
        var iRet = window.WebVideoCtrl.I_SupportNoPlugin();
        if (!iRet) {
          alert("当前浏览器版本过低，不支持无插件，请升级后再试！");
          return;
        }

        // 初始化插件
        this.initPlugin();
        return true;
      } catch (error) {
        console.error("初始化插件时出错:", error);
        alert("插件初始化失败，请检查控制台日志。");
        return false;
      }
    },
    async getLoginParams() {
      console.log("解析登录参数 >> ");
      const loginParams = {};
      this.companyVideoData.forEach((item) => {
        if (item) {
          const key = JSON.stringify(item);
          if (!loginParams[key]) {
            loginParams[key] = item;
          }
        }
      });
      return loginParams;
    },
    async login(loginParams) {
      // console.log("开始登录设备", loginParams);
      // 将 Object.values(loginParams) 转成 Promise 数组
      const results = await Promise.all(
        Object.values(loginParams).map(async (loginParam) => {
          try {
            await this.doLogin(loginParam);
            return { ip: loginParam.ip, port: loginParam.port, success: true };
          } catch (err) {
            console.error("登录失败:", loginParam, err);
            return { ip: loginParam.ip, port: loginParam.port, success: false, errorCode: err };
          }
        })
      );

      // 这里返回每台设备登录结果，包括失败码
      return results;
    },
    async doLogin(loginParam) {
      const that = this;
      console.log("登录设备信息", loginParam);
      return new Promise((resolve, reject) => {
        const iRet = WebVideoCtrl.I_Login(
          loginParam.ip, // szIP 设备的 IP 地址或者普通域名
          1, // iPrototocol http 协议，1 表示 http 协议 2 表示 https 协议
          loginParam.port, // iPort 登录设备的 http/https 端口号，根据 iPrototocol 选择传入不同的端口
          loginParam.username, // szUserName 登录用户名称
          loginParam.password, // szPassword 用户密码
          {
            timeout: 1000,
            success: function(xmlDoc) { // 成功回调函数
              console.log(`${loginParam.ip}_${loginParam.port} 登录成功！`);
              // 用 setTimeout 模拟异步处理
              setTimeout(() => {
                // 登录成功后再获取通道等信息
                that.getChannelInfo(loginParam.ip);
                that.getDevicePort(loginParam.ip);
                resolve(true);
              }, 10);
            },
            error: function(status, xmlDoc) { // 失败回调函数
              console.error(`${loginParam.ip}_${loginParam.port} 登录失败！`, status, xmlDoc);
              reject(status);
            }
          }
        )
          // .catch((error) => {
          // // 捕获错误并获取错误代码
          // const errorCode = error.errorCode
          // // console.log(errorCode);
          // if (errorCode == 2001) {
          //   //继续播放
          //   this.$emit('handlePlayError', '2001')
          //   that.doGetChannelInfo(loginParam.ip).then((channels) => {
          //     var s = JSON.stringify(channels)
          //     console.log(
          //       'tezt',
          //       JSON.stringify(channels),
          //       channels.length,
          //       channels[0],
          //       channels[0].channelId,
          //       channels[0]['channelId']
          //     )
          //     // console.log("🚀 通道列表 >> ", loginParam.ip, channels, channels.length, s[0]["channelName"]);
          //     var channelId = that.getChannelIdByName(
          //       loginParam.channelName,
          //       channels
          //     )
          //     console.log('🚀 获得id >> ', channelId)
          //     if (channelId !== null) {
          //       console.log('🚀 获得id >> ', channelId)
          //       that.startPlay(loginParam.ip, channelId)
          //     } else {
          //       console.log(
          //         '🚀 通道号无效 >> ',
          //         loginParam.ip,
          //         loginParam.channelName
          //       )
          //     }
          //   });
          // }
        // })

        // 如果返回 -1 表示已经登录过
        if (iRet === -1) {
          console.log(`${loginParam.ip}_${loginParam.port} 已登录过！`);
          resolve(true);
        }
      });
    },
    // 播放某个视频到指定窗口
    async playVideoInSelectWindow(video) {
      const that = this;
      console.log("开始播放", video);

      return new Promise((resolve, reject) => {
        WebVideoCtrl.I_StartRealPlay(video.ip, {
          iWndIndex: video.iWndIndex, // 播放窗口
          iStreamType: 1,             // 主码流
          iChannelID: video.channelId,// 通道号
          bZeroChannel: false,        // 是否播放零通道
          success: function () {
            console.log(`窗口 ${video.iWndIndex} 播放 ${video.ip} 成功`);
            // 更新 windowList
            if (!video.windowList) video.windowList = [];
            if (!video.windowList.includes(video.iWndIndex)) {
              video.windowList.push(video.iWndIndex);
            }
            that.$emit("handleVideoPlay", "playSuccess");
            that.$emit("init", true);
            resolve(true);
          },
          error: function (oError) {
            console.error(`窗口 ${video.iWndIndex} 播放 ${video.ip} 失败`, oError);
            WebVideoCtrl.I_Stop();
            that.$emit("handleVideoPlay", "playFail");
            reject(oError);
          },
        });
          // .then(() => {
          //   that.$emit("handleVideoPlay", "bofangchenggong");
          //   that.$emit("init", true);
          // })
          // .catch((error) => {
          //   // 捕获错误并获取错误代码
          //   const errorCode = error.errorCode;
          //   console.log("errorCode", errorCode);
          //   if (errorCode == 3001) {
          //     // 播放失败
          //     this.$emit("handleVideoPlay", "3001");
          //   }
          //   if (errorCode == 1000) {
          //     this.$emit("handleVideoPlay", "1000");
          //   }
          // });
      });
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
    // 获取设备信息
    clickGetDeviceInfo(ip) {
      if(ip == null) {
        return;
      }
      WebVideoCtrl.I_GetDeviceInfo(ip, {
        success: function (xmlDoc) {
          var arrStr = [];
          arrStr.push("设备名称：" + $(xmlDoc).find("deviceName").eq(0).text() + "\r\n");
          arrStr.push("设备ID：" + $(xmlDoc).find("deviceID").eq(0).text() + "\r\n");
          arrStr.push("型号：" + $(xmlDoc).find("model").eq(0).text() + "\r\n");
          arrStr.push("设备序列号：" + $(xmlDoc).find("serialNumber").eq(0).text() + "\r\n");
          arrStr.push("MAC地址：" + $(xmlDoc).find("macAddress").eq(0).text() + "\r\n");
          arrStr.push("主控版本：" + $(xmlDoc).find("firmwareVersion").eq(0).text() + " " + $(xmlDoc).find("firmwareReleasedDate").eq(0).text() + "\r\n");
          arrStr.push("编码版本：" + $(xmlDoc).find("encoderVersion").eq(0).text() + " " + $(xmlDoc).find("encoderReleasedDate").eq(0).text() + "\r\n");
          console.log(ip + " 获取设备信息成功！", arrStr.join(""));
        },
        error: function (status, xmlDoc) {
          console.log(ip + " 获取设备信息失败！", status, xmlDoc);
        }
      });
    },
    // 获取通道
    getChannelInfo(ip) {
      if (ip == null) {
        return;
      }
      // 模拟通道
      WebVideoCtrl.I_GetAnalogChannelInfo(ip, {
        async: false, // http交互方式，true表示异步，false表示同步
        success: function (xmlDoc) {
          var oChannels = $(xmlDoc).find("VideoInputChannel");
          $.each(oChannels, function (i) {
            var id = $(this).find("id").eq(0).text(),
              name = $(this).find("name").eq(0).text();
            if ("" == name) {
              name = "Camera " + (i < 9 ? "0" + (i + 1) : (i + 1));
            }
            oSel.append("<option value='" + id + "' bZero='false'>" + name + "</option>");
          });
          console.log(ip + " 获取模拟通道成功！");
        },
        error: function (status, xmlDoc) {
          console.log(ip + " 获取模拟通道失败！", status, xmlDoc);
        }
      });
      // 数字通道
      WebVideoCtrl.I_GetDigitalChannelInfo(ip, {
        async: false,
        success: function (xmlDoc) {
          var oChannels = $(xmlDoc).find("InputProxyChannelStatus");

          $.each(oChannels, function (i) {
            var id = $(this).find("id").eq(0).text(),
              name = $(this).find("name").eq(0).text(),
              online = $(this).find("online").eq(0).text();
            if ("false" == online) {// 过滤禁用的数字通道
              return true;
            }
            if ("" == name) {
              name = "IPCamera " + (i < 9 ? "0" + (i + 1) : (i + 1));
            }
            oSel.append("<option value='" + id + "' bZero='false'>" + name + "</option>");
          });
          console.log(ip + " 获取数字通道成功！");
        },
        error: function (status, xmlDoc) {
          console.log(ip + " 获取数字通道失败！", status, xmlDoc);
        }
      });
      // 零通道
      WebVideoCtrl.I_GetZeroChannelInfo(ip, {
        async: false,
        success: function (xmlDoc) {
          var oChannels = $(xmlDoc).find("ZeroVideoChannel");

          $.each(oChannels, function (i) {
            var id = $(this).find("id").eq(0).text(),
              name = $(this).find("name").eq(0).text();
            if ("" == name) {
              name = "Zero Channel " + (i < 9 ? "0" + (i + 1) : (i + 1));
            }
            if ("true" == $(this).find("enabled").eq(0).text()) {// 过滤禁用的零通道
              oSel.append("<option value='" + id + "' bZero='true'>" + name + "</option>");
            }
          });
          console.log(ip + " 获取零通道成功！");
        },
        error: function (status, xmlDoc) {
          console.log(ip + " 获取零通道失败！", status, xmlDoc);
        }
      });
    },
    // 获取端口
    getDevicePort(ip) {
      if(ip == null) {
        return;
      }
      var oPort = WebVideoCtrl.I_GetDevicePort(ip);
      if (oPort != null) {
        $("#deviceport").val(oPort.iDevicePort);
        $("#rtspport").val(oPort.iRtspPort);
        console.log(ip + " 获取端口成功！");
      } else {
        console.log(ip + " 获取端口失败！");
      }
    },
    doGetChannelInfo(ip) {
      return new Promise((resolve, reject) => {
        const getAnalogChannelInfo = new Promise((resolve, reject) => {
          WebVideoCtrl.I_GetAnalogChannelInfo(ip, {
            success: function(xmlDoc) {
              let channels = []
              var oChannels = $(xmlDoc).find('VideoInputChannel')
              $.each(oChannels, function(i) {
                var id = $(this).find('id').eq(0).text(),
                  name = $(this).find('name').eq(0).text()
                if ('' == name) {
                  name = 'Camera ' + (i < 9 ? '0' + (i + 1) : i + 1)
                }
                channels.push({
                  channelId: id,
                  channelName: name
                })
              })
              console.log('🚀 获取模拟通道成功 >> ', channels)
              resolve(channels)
            },
            error: function(oError) {
              console.log(
                '🚀 获取模拟通道失败 >> ',
                ip,
                oError.errorCode,
                oError.errorMsg
              )
            }
          })
        })
        const getDigitalChannelInfo = new Promise((resolve, reject) => {
          WebVideoCtrl.I_GetDigitalChannelInfo(ip, {
            success: function(xmlDoc) {
              let channels = []
              var oChannels = $(xmlDoc).find('InputProxyChannelStatus')
              $.each(oChannels, function(i) {
                var id = $(this).find('id').eq(0).text(),
                  name = $(this).find('name').eq(0).text(),
                  online = $(this).find('online').eq(0).text()
                if ('false' == online) {
                  // 过滤禁用的数字通道
                  return true
                }
                if ('' == name) {
                  name = 'IPCamera ' + (i < 9 ? '0' + (i + 1) : i + 1)
                }
                channels.push({
                  channelId: id,
                  channelName: name
                })
              })
              console.log('🚀 获取数字通道成功 >> ', channels)
              resolve(channels)
            },
            error: function(oError) {
              console.log(
                '🚀 获取数字通道失败 >> ',
                ip,
                oError.errorCode,
                oError.errorMsg
              )
              resolve([])
            }
          })
        })
        const getZeroChannelInfo = new Promise((resolve, reject) => {
          WebVideoCtrl.I_GetZeroChannelInfo(ip, {
            success: function(xmlDoc) {
              let channels = []
              var oChannels = $(xmlDoc).find('ZeroVideoChannel')
              $.each(oChannels, function(i) {
                var id = $(this).find('id').eq(0).text(),
                  name = $(this).find('name').eq(0).text()
                if ('' == name) {
                  name = 'Zero Channel ' + (i < 9 ? '0' + (i + 1) : i + 1)
                }
                if ('true' == $(this).find('enabled').eq(0).text()) {
                  // 过滤禁用的零通道
                  channels.push({
                    channelId: id,
                    channelName: name
                  })
                }
              })
              console.log('🚀 获取零通道成功 >> ', channels)
              resolve(channels)
            },
            error: function(oError) {
              console.log(
                '🚀 获取零通道失败 >> ',
                ip,
                oError.errorCode,
                oError.errorMsg
              )
              resolve([])
            }
          })
        })
        Promise.allSettled([
          getAnalogChannelInfo,
          getDigitalChannelInfo,
          getZeroChannelInfo,
        ])
          .then((resList) => {
            console.log("🚀 res >> ", resList);
            const fulfilledValues = resList.reduce((list, result) => {
              if (result.status === "fulfilled" && result.value.length !== 0) {
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
      });
    },
    // 全屏
    clickFullScreen() {
      WebVideoCtrl.I_FullScreen(true);
    },
    // 暂停
    clickPause() {
      var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex),
        szInfo = "";

      if (oWndInfo != null) {
        WebVideoCtrl.I_Pause({
          success: function () {
            szInfo = "暂停成功！";
            console.log(oWndInfo.szDeviceIdentify + " " + szInfo);
          },
          error: function () {
            szInfo = "暂停失败！";
            console.log(oWndInfo.szDeviceIdentify + " " + szInfo);
          }
        });
      }
    },
    startPlay(ip, channelId) {
      let that = this;
      console.log("开始播放 >> ", ip, channelId);
      WebVideoCtrl.I_StartRealPlay(ip, {
        iWndIndex: this.dangqianchuangkou, // 播放窗口，如果不传，则默认使用当前选择窗口播放（默认选中窗口0）
        iStreamType: 1, // 码流类型1-主码流，2-子码流，3-第三码流，默认使用主码流预览
        iChannelID: channelId, // 播放通道号，默认通道1
        bZeroChannel: false,// 是否播放零通道，默认为false
        success: function () {
          that.$emit("handleVideoPlay", "playSuccess");
        },
        error: function (oError) {
          that.$emit("handleVideoPlay", "playFail");
          WebVideoCtrl.I_Stop();
        },
      })
      .then(() => {
        that.$emit("handleVideoPlay", "playSuccess");
        that.$emit("init", true);
      })
      .catch((error) => {
        // 捕获错误并获取错误代码
        const errorCode = error.errorCode;
        console.log("errorCode", errorCode);
        this.$emit("handleVideoPlay", "playFail");
      });
    },
    // 点击查看具体哪个监控
    startRealPlay(oChannels) {
      let that = this;
      let szIP = this.companyVideoData.ip; // ip地址
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
        success: function () {
          that.$emit("handleVideoPlay", "playSuccess");
        },
        error: function (oError) {
          that.$emit("handleVideoPlay", "playSuccess");
          WebVideoCtrl.I_Stop();
        },
      });
    },
    // 停止停止全部窗口播放视频
    stopAllRealPlay() {
      const that = this;
      WebVideoCtrl.I_StopAll().then(() => {
        that.$emit("handleVideoPlay", { code: "stopAllSuccess" });
      }).catch(() => {
        that.$emit("handleVideoPlay", { code: "stopAllFail" });
      });
    },
    // 停止指定窗口播放视频
    stopOneWindowPlayVideo(iWndIndex) {
      let that = this;
      return new Promise((resolve) => {
        WebVideoCtrl.I_Stop({
          iWndIndex,
          success() {
            that.$emit("handleVideoPlay", { code: "stopSuccess", iWndIndex });
            resolve();
          },
          error() {
            that.$emit("handleVideoPlay", { code: "stopFail", iWndIndex });
            resolve();
          }
        });
      });
    },
    // 停止停止多个窗口播放视频
    stopAllWindowPlayVideo(windows) {
      let that = this;
      const promises = windows.map(win => this.stopOneWindowPlayVideo(parseInt(win)));
      return Promise.all(promises.map(p => p.catch(e => e)))
        .then(() => {
          that.$emit("handleVideoPlay", { code: "stopAllSuccess" });
        });
    },
  },
};
</script>
<style scoped></style>
