<template>
  <video
    autoplay
    controls
    style="
      width: 100%;
      height: 100%;
      margin-left: 5px;
      margin-bottom: 5px;
      position: relative;
    "
    :id="id"
  ></video>
</template>

<script>
import flvjs from "flv.js";
export default {
  name: "faith",
  props: ["id", "url"],
  data() {
    return {
      flvPlayer: null,
    };
  },
  created() {},
  mounted() {
    this.singleplayvideo();
  },
  methods: {
    reloadVideo(flvPlayer) {},
    // 初始化视频
    singleplayvideo() {
      if (flvjs.isSupported()) {
        var videoElement = document.getElementById(this.id);
        this.flvPlayer = flvjs.createPlayer(
          {
            type: "flv",
            url: this.url,
          },
          {
            enableWorker: false, //不启用分离线程
            enableStashBuffer: false, //关闭IO隐藏缓冲区
            reuseRedirectedURL: true, //重用301/302重定向url，用于随后的请求，如查找、重新连接等。
            autoCleanupSourceBuffer: true, //自动清除缓存
            fixAudioTimestampGap: false,
          }
        );
        this.$nextTick(() => {
          // 可以使用回调函数的写法
          // 这个函数中DOM必定渲染完成
          // let videoElement1 = document.getElementById("singleplay" + num);
          // debugger;
          this.flvPlayer.attachMediaElement(videoElement);
          // name.load();
          // name.play();
          if (this.url !== "" && this.url !== null) {
          }
          this.flvPlayer.on(flvjs.Events.ERROR, (errType, errDetail) => {
            // alert("网络波动,正在尝试连接中...");
            if (this.flvPlayer) {
              this.reloadVideo(this.flvPlayer);
            }
          });
          // this.flvPlayerList1.push(this.flvPlayer);
        });
        this.flvPlayer.attachMediaElement(videoElement);
        this.flvPlayer.load();
        this.flvPlayer.play();
      }
    },
    destoryVideo(flvPlayer) {
      flvPlayer.pause();
      flvPlayer.unload();
      flvPlayer.detachMediaElement();
      flvPlayer.destroy();
      flvPlayer = null;
    },
  },
  watch: {
    url(newname) {
      this.destoryVideo(this.flvPlayer);
      this.singleplayvideo();
    },
  },
};
</script>

<style>
</style>