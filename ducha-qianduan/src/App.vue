<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      loginForm: {
        username: "fsdd",
        password: "123456",
      },
    };
  },
  metaInfo() {
    return {
      title:
        this.$store.state.settings.dynamicTitle &&
        this.$store.state.settings.title,
      titleTemplate: (title) => {
        return title
          ? `${title} - ${process.env.VUE_APP_TITLE}`
          : process.env.VUE_APP_TITLE;
      },
    };
  },
  created() {
    // 获取当前地址的名字
    var url = window.document.location.pathname;
    // 获取当前地址之后的目录
    var url1 = window.document.location.hash;
    if (url === "/admin") {
      this.getloing();
    }
  },
  methods: {
    // btn(){
    //   (document.body || document.documentElement).requestFullscreen()
    // },
    getloing() {
      this.$store.dispatch("Login", this.loginForm).then(() => {
        this.$router.push({ path: "/pt" }).catch(() => {});
        window.document.location.pathname='/123'
      });
    },
  },
};
</script>
<style scoped>
/deep/ .owner-label .el-form-item__label {
  color: #000000 !important;
}
</style>

