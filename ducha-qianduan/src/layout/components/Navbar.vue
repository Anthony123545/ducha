<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container"
      @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav" />
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav" />

    <div class="right-menu">
      <div class="yong_hu">
        <span style="color: ">单位 :</span>
        <span class="span" style="padding-left: 4px">{{ danWie }}</span>
        <router-link to="/user/profile">
          <span style="color: #00f6ff">用户名 :</span>
          <span style="padding-left: 4px; color: #00f6ff; margin-right: 15px">{{
          yongHu
          }}</span>
          <!-- <el-dropdown-item>个人中心</el-dropdown-item> -->
        </router-link>
      </div>
      <template v-if="device !== 'mobile'">
        <screenfull id="screenfull" class="right-menu-item hover-effect" />
      </template>
      <!-- <el-dropdown
        class="avatar-container right-menu-item hover-effect"
        trigger="click"
      > -->
      <div class="avatar-wrapper">
        <span class="iconfont icon-logout" @click="logout"></span>
        <!-- <el-button @click.native="logout" size="btl">退出登录</el-button> -->
        <!-- <img :src="avatar" class="user-avatar" />
          <i class="el-icon-caret-bottom" /> -->
      </div>
      <!-- <el-dropdown-menu slot="dropdown"> -->
      <!-- <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item> -->
      <!-- </el-dropdown-menu> -->
      <!-- </el-dropdown> -->
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import TopNav from "@/components/TopNav";
import Hamburger from "@/components/Hamburger";
import Screenfull from "@/components/Screenfull";
import SizeSelect from "@/components/SizeSelect";
import Search from "@/components/HeaderSearch";
// import supervisionGit from '@/components/supervision/Git'
// import supervisionDoc from '@/components/supervision/Doc'
import RuoYiGit from "@/components/supervision/Git";
import RuoYiDoc from "@/components/supervision/Doc";
import { getUserProfile } from "@/api/system/user";
import WebSocketService from "@/utils/WebSocketService";

export default {
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc,
    // supervisionGit,
    // supervisionDoc
  },
  computed: {
    ...mapGetters(["sidebar", "avatar", "device"]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings;
      },
      set(val) {
        this.$store.dispatch("settings/changeSetting", {
          key: "showSettings",
          value: val,
        });
      },
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav;
      },
    },
  },
  data() {
    return {
      user: {},
      danWie: "",
      yongHu: "",
    };
  },
  created() {
    this.getUser();
    // console.log(this.sidebar)
    // console.log(this.$route)
    if (this.$route.query.name == "/regulations") {
      this.sidebar.opened = false;
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch("app/toggleSideBar");
    },
    async logout() {
      try {
        await this.$confirm("确定注销并退出系统吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        });

        // 调用 Vuex 退出登录
        await this.$store.dispatch("LogOut");

        // 断开 WebSocket（如果存在）
        if (this.$store.getters.webSocketState === 'open') {
          WebSocketService.close();
        }

        // 跳转登录页
        location.href = "/index";
      } catch (err) {
        // 用户取消操作，不处理
      }
    },
    getUser() {
      getUserProfile().then((response) => {
        this.user = response.data;
        this.danWie = response.data.dept.deptName;
        this.yongHu = response.data.nickName;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        // margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}

.yong_hu {
  display: inline-block;
  vertical-align: top;
  font-size: 15px;
}

.span {
  margin-right: 21px;
}

/* 退出登录 btl */
.el-button--btl.is-active,
.el-button--btl:active {
  background: #ff4949;
  border-color: #ff4949;
  color: #ffffff;
}

.el-button--btl:focus,
.el-button--btl:hover {
  background: #ff4949;
  border-color: #ff4949;
  color: #ffffff;
}

.el-button--btl {
  width: 80px !important;
  border: 1px solid transparent;
  padding: 3px 8px;
  font-size: 14px;
  line-height: 1.5;
  border-radius: 3px;
  color: #fff;
  background-color: #ff4949;
  height: 29px;
}

.avatar-wrapper {
  display: inline-block;
  padding: 0 8px;
  height: 100%;
  font-size: 18px;
  color: #5a5e66;
  vertical-align: text-bottom;
  margin-right: 20px;
}

.iconfont {
  color: #fff;
  font-size: 27px;
  cursor: pointer;
}
</style>
