<template>
  <div class="register">
    <div class="register-background">
      <div class="register-left"></div>
      <div class="register-right">
        <el-form
          ref="registerForm"
          :model="registerForm"
          :rules="registerRules"
          class="register-form"
        >
          <!-- 身份证 -->
          <el-form-item prop="idcard" class="form-item-inline">
            <span class="label">身份证：</span>
            <el-input
              v-model="registerForm.idcard"
              placeholder="请输入身份证号"
              autocomplete="off"
              clearable
            >
              <svg-icon slot="prefix" icon-class="user" class="input-icon" />
            </el-input>
          </el-form-item>

          <!-- 账号 -->
          <el-form-item prop="username" class="form-item-inline">
            <span class="label">账号：</span>
            <el-input
              v-model="registerForm.username"
              placeholder="请输入账号"
              autocomplete="off"
              clearable
            >
              <svg-icon slot="prefix" icon-class="user" class="input-icon" />
            </el-input>
          </el-form-item>

          <!-- 密码 -->
          <el-form-item prop="password" class="form-item-inline">
            <span class="label">密码：</span>
            <el-input
              v-model="registerForm.password"
              type="password"
              placeholder="请输入密码"
              autocomplete="off"
              clearable
            >
              <svg-icon
                slot="prefix"
                icon-class="password"
                class="input-icon"
              />
            </el-input>
          </el-form-item>

          <!-- 确认密码 -->
          <el-form-item prop="confirmPassword" class="form-item-inline">
            <span class="label">确认密码：</span>
            <el-input
              v-model="registerForm.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              autocomplete="off"
              clearable
            >
              <svg-icon
                slot="prefix"
                icon-class="password"
                class="input-icon"
              />
            </el-input>
          </el-form-item>

          <!-- 验证码 -->
          <el-form-item
            prop="code"
            v-if="captchaOnOff"
            class="form-item-inline"
          >
            <span class="label">验证码：</span>
            <el-input
              v-model="registerForm.code"
              placeholder="请输入验证码"
              autocomplete="off"
              clearable
            >
              <svg-icon
                slot="prefix"
                icon-class="validCode"
                class="input-icon"
              />
            </el-input>
            <img :src="codeUrl" @click="getCode" class="code-img" />
          </el-form-item>

          <!-- 注册按钮 -->
          <el-form-item class="register-btn-row">
            <el-button
              :loading="loading"
              type="primary"
              class="register-btn"
              @click.native.prevent="handleRegister"
            >
              <span v-if="!loading">注 册</span>
              <span v-else>注 册 中...</span>
            </el-button>
            <div class="to-login">
              <router-link class="link-type" :to="'/login'"
                >使用已有账户登录</router-link
              >
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg, register } from "@/api/login";

export default {
  name: "Register",
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      codeUrl: "",
      registerForm: {
        idcard: "",
        username: "",
        password: "",
        confirmPassword: "",
        code: "",
        uuid: "",
      },
      registerRules: {
        idcard: [
          { required: true, message: "请输入身份证号", trigger: "blur" },
          {
            pattern:
              /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
            message: "身份证号格式错误",
            trigger: "blur",
          },
        ],
        username: [
          { required: true, message: "请输入账号", trigger: "blur" },
          {
            min: 2,
            max: 20,
            message: "长度在 2 到 20 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 5,
            max: 20,
            message: "密码长度在 5 到 20 个字符",
            trigger: "blur",
          },
          { validator: this.validatePasswordStrength, trigger: "input" },
        ],
        confirmPassword: [
          { required: true, message: "请确认密码", trigger: "blur" },
          { validator: equalToPassword, trigger: "blur" },
        ],
        code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },
      getCodeLoading: false,
      loading: false,
      captchaOnOff: true,
    };
  },
  created() {
    this.getCode();
  },
  methods: {
    getCode() {
      if (this.getCodeLoading) return; // 防止在图片加载时重复请求
      this.getCodeLoading = true;
      getCodeImg()
        .then((res) => {
          this.captchaOnOff = res.captchaOnOff ?? true;
          if (this.captchaOnOff) {
            this.codeUrl = "data:image/gif;base64," + res.img;
            this.registerForm.uuid = res.uuid;
          }
        })
        .finally(() => {
          this.getCodeLoading = false;
        });
    },
    handleRegister() {
      this.$refs.registerForm.validate((valid) => {
        if (!valid) return;
        this.loading = true;
        register(this.registerForm)
          .then(() => {
            this.$alert(
              `<font color='red'>恭喜你，账号 ${this.registerForm.username} 注册成功！</font>`,
              "系统提示",
              { dangerouslyUseHTMLString: true },
            ).then(() => this.$router.push("/login"));
          })
          .catch(() => {
            this.loading = false;
            this.getCode();
          });
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
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url("../images/背景图片这是.png") center no-repeat;
  background-size: cover;
}

.register-background {
  display: flex;
  width: 1200px;
  height: 650px;
  background: url("../assets/images/边框.png") no-repeat center;
  background-size: cover;
}

.register-left {
  width: 50%;
}

.register-right {
  width: 50%;
  display: flex;
  justify-content: center;
  align-items: center; /* 确保表单垂直居中 */
}

.register-form {
  width: 80%;
  margin-top: 30%;
  margin-left: 5%;
}

.form-item-inline {
  display: flex; /* 让图片也能参与 flex 居中 */
  align-items: center;
  justify-content: flex-start; /* 左对齐 */

  .label {
    display: inline-block;
    height: 40px;
    width: 80px; /* 左侧文字宽度 */
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

  .code-img {
    background-color: #2aabd2;
    width: 80px; /* 设置验证码图片宽度 */
    height: 40px; /* 设置验证码图片高度 */
    object-fit: cover; /* 保证图片不拉伸 */
    cursor: pointer; /* 鼠标指针效果 */
    margin-left: 10px;
    vertical-align: middle; /* 保证验证码图片与输入框垂直居中 */
  }
}

.input-icon {
  color: #01cfff;
}

.register-btn-row {
  width: 360px;
  margin-top: 0px; /* 给按钮和表单项之间留空隙 */
}

.register-btn {
  width: 100%;
  max-width: 400px; /* 限制最大宽度 */
  height: 45px;
  background-color: #0384ea;
  font-size: 18px;
  font-weight: 600;
  border-radius: 6px;
  margin-top: 00px;
}

.to-login {
  margin-top: 10px;
  text-align: center;

  .link-type {
    color: #01cfff;
    font-size: 14px;
    text-decoration: underline;
  }
}
</style>
