<template>
  <div class="login">
    <div class="login-background">
      <div class="login-left"></div>
      <div class="login-right">
        <el-form
          ref="loginForm"
          :model="loginForm"
          :rules="loginRules"
          class="login-form"
        >
          <el-form-item prop="username" class="form-item-inline">
            <span class="label">账号：</span>
            <el-input
              v-model="loginForm.username"
              type="text"
              auto-complete="off"
              placeholder="账号"
              clearable
            >
              <svg-icon
                slot="prefix"
                icon-class="user"
                class="el-input__icon input-icon"
              />
            </el-input>
          </el-form-item>
          <el-form-item prop="password" class="form-item-inline">
            <span class="label">密码：</span>
            <el-input
              v-model="loginForm.password"
              type="password"
              auto-complete="off"
              placeholder="密码"
              @keyup.enter.native="handleLogin"
              class="password"
              clearable
            >
              <svg-icon
                slot="prefix"
                icon-class="password"
                class="el-input__icon input-icon"
              />
            </el-input>
          </el-form-item>
          <!--          <el-form-item v-if="captchaOnOff" v-show="false">-->
          <!--            <el-input-->
          <!--              v-model="loginForm.code"-->
          <!--              auto-complete="off"-->
          <!--              placeholder="验证码"-->
          <!--              style="width: 63%"-->
          <!--              @keyup.enter.native="handleLogin"-->
          <!--            >-->
          <!--              <svg-icon-->
          <!--                slot="prefix"-->
          <!--                icon-class="validCode"-->
          <!--                class="el-input__icon input-icon"-->
          <!--              />-->
          <!--            </el-input>-->
          <!--            <div class="login-code">-->
          <!--              <img :src="codeUrl" @click="getCode" class="login-code-img" />-->
          <!--            </div>-->
          <!--          </el-form-item>-->
          <el-form-item class="login-btn-row">
            <el-button
              :loading="loading"
              type="primary"
              class="login-btn"
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>
            <div class="to-register">
              <router-link class="link-type" :to="'/register'"
                >立即注册</router-link
              >
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      cookiePassword: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: "",
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" },
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入密码" },
          {
            min: 6,
            max: 20,
            message: "密码长度应为6到20个字符",
            trigger: "blur",
          },
          // { validator: this.validatePasswordStrength, trigger: "input" },
        ],
        // code: [{ required: true, trigger: "change", message: "请输入验证码" }],
      },
      loading: false,
      // 验证码开关
      captchaOnOff: true,
      // 注册开关
      register: true,
      redirect: undefined,
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  created() {
    // this.getCode();
    this.getCookie();
  },
  methods: {
    //捕获游览器全屏
    requestFullScreen() {
      var element = document.getElementsByTagName("body")[0];
      var requestMethod =
        element.requestFullScreen ||
        element.webkitRequestFullScreen ||
        element.mozRequestFullScreen ||
        element.msRequestFullScreen;
      if (requestMethod) {
        requestMethod.call(element);
      } else if (typeof window.ActiveXObject !== "undefined") {
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript !== null) {
          wscript.SendKeys("{F11}");
        }
      }
    },
    getCode() {
      getCodeImg().then((res) => {
        this.captchaOnOff =
          res.captchaOnOff === undefined ? true : res.captchaOnOff;
        if (this.captchaOnOff) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          // this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get("rememberMe");
      this.loginForm = {
        username: username || this.loginForm.username,
        password: password ? decrypt(password) : this.loginForm.password,
        rememberMe: rememberMe ? Boolean(rememberMe) : false,
      };
    },
    // 请求
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), {
              expires: 30,
            });
            Cookies.set("rememberMe", this.loginForm.rememberMe, {
              expires: 30,
            });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            // Cookies.remove("rememberMe");
          }

          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              const token = this.$store.getters.token;
              if (token) {
                this.$store.dispatch("ws/connectWebSocket", token);
              }
              // this.requestFullScreen()
              // this.$router.push({ path: this.redirect || "/" }).catch(() => {});
              this.$router
                .push({ path: this.redirect || "/pt" })
                .catch(() => {});
              // this.$router.push({ path: "/pt" }).catch(() => {});
            })
            .catch(() => {
              this.loading = false;
              this.$message.error("登录失败，请检查您的账号和密码！");
              if (this.captchaOnOff) {
                this.getCode();
              }
            });
        }
      });
    },
    validatePasswordStrength(rule, value, callback) {
      if (!value) {
        callback(new Error("密码不能为空"));
      } else {
        const strength = this.passwordStrength(value);
        if (strength < 2) {
          callback(new Error("密码强度较弱"));
        } else {
          callback();
        }
      }
    },
    passwordStrength(password) {
      let strength = 0;
      if (/[a-z]/.test(password)) strength++;
      if (/[A-Z]/.test(password)) strength++;
      if (/\d/.test(password)) strength++;
      if (/[^A-Za-z0-9]/.test(password)) strength++;
      return strength;
    },
  },
};
</script>

<style>
/* 针对账号标签的特殊样式 */
.form-item-inline >>> .label,
.form-item-inline .label {
  color: #ffffff !important;
  font-size: 18px !important; /* 调大字体，从16px改为18px */
  font-weight: 600;
  text-shadow: 0 0 3px rgba(255, 255, 255, 0.8);
}

.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url("../images/背景图片这是.png") center no-repeat;
  background-size: cover;
}

.login-background {
  display: flex;
  width: 1200px;
  height: 650px;
  background: url("../assets/images/边框.png") no-repeat center;
  background-size: cover;
}

.login-left {
  width: 50%;
}

.login-right {
  width: 50%;
  display: flex;
  justify-content: center;
  align-items: center; /* 确保表单垂直居中 */
}

.login-form {
  width: 70%;
  margin-top: 25%;
  margin-left: 5%;
}

.form-item-inline {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-bottom: 40px; /* 每个表单项之间的间距 */

  .label {
    display: inline-block;
    height: 40px;
    width: 60px; /* 左侧文字宽度 */
    text-align: right; /* 文字右对齐 */
    margin-right: 0px;
    color: #fff;
    font-size: 16px;
    flex-shrink: 0; /* 防止被压缩 */
  }

  .el-input {
    height: 40px;
    width: 280px;
  }

  .el-input__inner {
    background-color: transparent;
    color: #fff;
    border: 1px solid #01cfff;
  }
}

.input-icon {
  color: #01cfff;
}

.login-btn-row {
  width: 360px;
  margin-top: 30px; /* 给按钮和表单项之间留空隙 */
}

.login-btn {
  width: 100%;
  max-width: 400px; /* 限制最大宽度 */
  height: 45px;
  background-color: #0384ea;
  font-size: 18px;
  font-weight: 600;
  border-radius: 6px;
  margin-top: 10px;
}

.to-register {
  margin-top: 10px;
  text-align: center;

  .link-type {
    color: #01cfff;
    font-size: 14px;
    text-decoration: underline;
  }
}
</style>
