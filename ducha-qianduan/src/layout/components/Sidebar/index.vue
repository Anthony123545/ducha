<template>
  <div
    :class="{ 'has-logo': showLogo }"
    :style="{
      backgroundColor:
        settings.sideTheme === 'theme-dark'
          ? variables.menuBackground
          : variables.menuLightBackground,
    }"
  >
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar :class="settings.sideTheme" wrap-class="scrollbar-wrapper">
      <el-menu
        :collapse="false"
        :background-color="
          settings.sideTheme === 'theme-dark'
            ? variables.menuBackground
            : variables.menuLightBackground
        "
        :text-color="
          settings.sideTheme === 'theme-dark'
            ? variables.menuColor
            : variables.menuLightColor
        "
        :unique-opened="true"
        :active-text-color="settings.theme"
        :collapse-transition="false"
        mode="vertical"
        :default-openeds="defaultpath"
        :default-active="$route.path"
      >
        <!-- @click.native="a(route,index)" -->
        <sidebar-item
          v-for="(route, index) in routs"
          :key="route.path + index"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import Logo from "./Logo";
import SidebarItem from "./SidebarItem";
import variables from "@/assets/styles/variables.scss";
import Cookies from "js-cookie";

export default {
  data() {
    return {
      routs: [],
      p: null,
      defaultpath: null
    };
  },
  components: { SidebarItem, Logo },
  computed: {
    ...mapState(["settings"]),
    ...mapGetters(["sidebarRouters", "sidebar"]),
    activeMenu() {
      const route = this.$route;
      const { meta, path } = route;
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu;
      }
      return path;
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo;
    },
    variables() {
      return variables;
    },
    isCollapse() {
      return !this.sidebar.opened;
    }
  },
  created() {
    if (this.$route.query.name) {
      this.sidebarRoutersfun();
    } else {
      this.$route.query.name = "/" + this.$route.path.split("/")[1];
      this.sidebarRoutersfun();
    }
    // window.location.reload()
  },
  methods: {
    a(route, index) {},
    sidebarRoutersfun() {
      console.log("this.$route.query.name", this.$route.query.name);
      console.log("侧边栏权限菜单：", this.sidebarRouters);
      console.log("当前访问模块：", this.$route.query.name);
      this.sidebarRouters.map(item => {
        if (item.path == this.$route.query.name) {
          console.log("item.path", item.path)
          this.routs.push(item);
          if (this.$route.name == "Notice") {
            //  this.p = this.routs[0].path + "/" + this.routs[0].children[0].path;
            this.$router.push({ path: "/system/notice" });
          } else {
            if (this.routs[0].children && this.routs[0].children[0].children) {
              this.defaultpath = [this.routs[0].children[0].path];
              this.p =
                this.routs[0].path
                + "/" + this.routs[0].children[0].path
                + "/" + this.routs[0].children[0].children[0].path;
            } else if (this.routs[0].children) {
              this.defaultpath = [this.routs[0].path];
              this.p =
                this.routs[0].path
                + "/" + this.routs[0].children[0].path;
            } else {
              this.defaultpath = [this.routs[0].path];
              this.p =
                this.routs[0].path
            }
            this.$router.push({ path: this.p });
          }
        }
        // console.log(this.routs.length)
      });
      console.log("this.routs", this.routs)
      if (this.routs.length <= 0) {
        // window.open(`/phone`, "_self");
        this.$router
          .push({
            name: "pt",
            params: {
              name: "您暂无权限访问"
            }
          })
          .catch(() => {});
      }
    }
  }
};
</script>

