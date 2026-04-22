import { login, logout, getInfo } from "@/api/login";
import { getToken, setToken, removeToken } from "@/utils/auth";
import Cookies from "js-cookie";

const user = {
  state: {
    token: getToken(),
    name: "",
    avatar: "",
    roles: [],
    permissions: [],
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_NAME: (state, name) => {
      state.name = name;
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar;
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles;
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions;
    },
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim();
      const password = userInfo.password;
      const code = userInfo.code;
      const uuid = userInfo.uuid;
      return new Promise((resolve, reject) => {
        login(username, password, code, uuid)
          .then((res) => {
            setToken(res.token);
            commit("SET_TOKEN", res.token);
            resolve();
          })
          .catch((error) => {
            reject(error);
          });
      });
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo()
          .then((res) => {
            const user = res.user;
            Cookies.set("nickName", res.user.nickName);
            Cookies.set("userId", res.user.userId);
            Cookies.set("deptId", res.user.deptId);
            // Cookies.set("roles", res.user.roles[0].roleName);
            const avatar =
              user.avatar == ""
                ? require("@/assets/images/profile.jpg")
                : process.env.VUE_APP_BASE_API + user.avatar;
            if (res.roles && res.roles.length > 0) {
              // 验证返回的roles是否是一个非空数组
              commit("SET_ROLES", res.roles);
              commit("SET_PERMISSIONS", res.permissions);
            } else {
              commit("SET_ROLES", ["ROLE_DEFAULT"]);
            }
            commit("SET_NAME", user.userName);
            commit("SET_AVATAR", avatar);
            resolve(res);
          })
          .catch((error) => {
            reject(error);
          });
      });
    },

    // 退出系统
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token)
          .then(() => {
            commit("SET_TOKEN", "");
            commit("SET_ROLES", []);
            commit("SET_PERMISSIONS", []);
            removeToken();
            // 新增开始
            Cookies.remove("nickName");
            Cookies.remove("userId");
            Cookies.remove("deptId");
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove("rememberMe");
            window.location.href = "/#/login";
            resolve();
          })
          .catch((error) => {
            reject(error);
          });
      });
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise((resolve) => {
        commit("SET_TOKEN", "");
        removeToken();
        // 新增3行
        Cookies.remove("nickName");
        Cookies.remove("userId");
        Cookies.remove("deptId");
        Cookies.remove("username");
        Cookies.remove("password");
        Cookies.remove("rememberMe");
        window.location.href = "/#/login";
        resolve();
      });
    },
  },
};

export default user;
