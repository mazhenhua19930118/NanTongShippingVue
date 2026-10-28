<template>
  <div class="login">
    <!-- 左侧品牌展示区 -->
    <div class="login-banner">
      <div class="banner-decor">
        <!-- 装饰：航船 SVG -->
        <svg class="ship" viewBox="0 0 120 120" xmlns="http://www.w3.org/2000/svg">
          <path d="M10 70 L110 70 L98 92 L22 92 Z" fill="rgba(255,255,255,.85)"/>
          <path d="M60 22 L60 70 M60 30 L92 48 L60 56 Z" fill="rgba(255,255,255,.9)"/>
          <circle cx="60" cy="14" r="6" fill="rgba(255,255,255,.9)"/>
        </svg>
      </div>
      <div class="banner-content">
        <div class="banner-logo">
          <svg viewBox="0 0 24 24" width="34" height="34" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M3 18l9-6 9 6"/><path d="M3 18v3h18v-3"/><path d="M12 3v9"/><path d="M9 6h6"/>
          </svg>
        </div>
        <h1 class="banner-title">{{ title }}</h1>
        <p class="banner-slogan">智慧航运 · 通达天下</p>
        <ul class="banner-feats">
          <li><span class="dot"></span>实时船舶动态监控</li>
          <li><span class="dot"></span>一站式航运业务管理</li>
          <li><span class="dot"></span>数据安全 · 高效协同</li>
        </ul>
      </div>
      <!-- 底部波浪 -->
      <svg class="waves" viewBox="0 0 1440 200" preserveAspectRatio="none" xmlns="http://www.w3.org/2000/svg">
        <defs>
          <path id="wave" d="M0,80 C240,160 480,0 720,80 C960,160 1200,0 1440,80 L1440,200 L0,200 Z"/>
        </defs>
        <use href="#wave" class="wave wave1"/>
        <use href="#wave" class="wave wave2"/>
        <use href="#wave" class="wave wave3"/>
      </svg>
    </div>

    <!-- 右侧装饰区 -->
    <div class="login-aside">
      <svg class="aside-ship" viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
        <circle cx="100" cy="100" r="78" fill="none" stroke="rgba(255,255,255,.25)" stroke-width="2" stroke-dasharray="6 8"/>
        <path d="M40 120 L160 120 L146 150 L54 150 Z" fill="rgba(255,255,255,.85)"/>
        <path d="M100 40 L100 120 M100 56 L140 80 L100 92 Z" fill="rgba(255,255,255,.9)"/>
        <circle cx="100" cy="30" r="7" fill="rgba(255,255,255,.9)"/>
      </svg>
      <div class="aside-cards">
        <div class="aside-card">
          <svg viewBox="0 0 24 24" width="22" height="22" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 12h18M3 6h18M3 18h18"/></svg>
          <div><p class="ac-t">智能航线</p><p class="ac-d">一键规划最优航路</p></div>
        </div>
        <div class="aside-card">
          <svg viewBox="0 0 24 24" width="22" height="22" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 3v18h18"/><path d="M7 14l4-4 3 3 5-6"/></svg>
          <div><p class="ac-t">数据看板</p><p class="ac-d">全局态势实时掌控</p></div>
        </div>
        <div class="aside-card">
          <svg viewBox="0 0 24 24" width="22" height="22" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2l8 4v6c0 5-3.5 8-8 10-4.5-2-8-5-8-10V6z"/></svg>
          <div><p class="ac-t">安全合规</p><p class="ac-d">全程数据加密保障</p></div>
        </div>
      </div>
    </div>

    <!-- 右侧登录表单区 -->
    <div class="login-main">
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <div class="form-head">
          <h3 class="title">{{ title }}</h3>
          <p class="subtitle">欢迎登录管理系统</p>
        </div>
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            type="text"
            auto-complete="off"
            placeholder="请输入账号"
          >
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            placeholder="请输入密码"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="code" v-if="captchaEnabled">
          <el-input
            v-model="loginForm.code"
            auto-complete="off"
            placeholder="请输入验证码"
            style="width: 63%"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" @click="getCode" class="login-code-img"/>
          </div>
        </el-form-item>
        <el-checkbox v-model="loginForm.rememberMe" class="remember">记住密码</el-checkbox>
        <el-form-item style="width:100%;">
          <el-button
            :loading="loading"
            size="medium"
            type="primary"
            class="login-btn"
            style="width:100%;"
            @click.native.prevent="handleLogin"
          >
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>
          <div class="reg-link" v-if="register">
            <span>还没有账号？</span>
            <router-link class="link-type" :to="'/register'">立即注册</router-link>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getCode()
    this.getCookie()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },
    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 })
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove("username")
            Cookies.remove("password")
            Cookies.remove('rememberMe')
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{})
          }).catch(() => {
            this.loading = false
            if (this.captchaEnabled) {
              this.getCode()
            }
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  position: relative;
  display: flex;
  height: 100%;
  width: 100%;
  overflow: hidden;
  background: linear-gradient(135deg, #2f7fd0 0%, #4f9fe0 55%, #7cb6e8 100%);
}

/* ===== 左侧品牌区（绝对定位，不占 flex 空间，让表单可整体居中） ===== */
.login-banner {
  position: absolute;
  left: 0; top: 0; bottom: 0;
  width: 46%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  overflow: hidden;
  background:
    radial-gradient(900px 500px at 20% 20%, rgba(255,255,255,.18), transparent 60%),
    radial-gradient(700px 400px at 80% 80%, rgba(80,160,240,.40), transparent 60%);
}
.banner-decor {
  position: absolute;
  top: 12%;
  right: 14%;
  opacity: .25;
  animation: float 6s ease-in-out infinite;
}
.ship { width: 160px; height: 160px; }
@keyframes float {
  0%,100% { transform: translateY(0) rotate(-2deg); }
  50% { transform: translateY(-18px) rotate(2deg); }
}
.banner-content { position: relative; z-index: 2; padding: 0 8%; max-width: 520px; }
.banner-logo {
  width: 64px; height: 64px;
  display: flex; align-items: center; justify-content: center;
  border-radius: 16px;
  background: rgba(255,255,255,.15);
  backdrop-filter: blur(6px);
  color: #fff;
  margin-bottom: 26px;
  box-shadow: 0 8px 24px rgba(0,0,0,.15);
}
.banner-title {
  font-size: 40px;
  font-weight: 700;
  margin: 0 0 10px;
  letter-spacing: 2px;
  text-shadow: 0 4px 18px rgba(0,0,0,.2);
}
.banner-slogan {
  font-size: 18px;
  opacity: .9;
  margin: 0 0 36px;
  letter-spacing: 4px;
}
.banner-feats {
  list-style: none;
  padding: 0; margin: 0;
  font-size: 15px;
  line-height: 2.4;
  opacity: .92;
}
.banner-feats .dot {
  display: inline-block;
  width: 8px; height: 8px;
  border-radius: 50%;
  background: #1d6fe0;
  margin-right: 12px;
  box-shadow: 0 0 10px #4a90e2;
}

/* ===== 底部波浪动画 ===== */
.waves {
  position: absolute;
  bottom: 0; left: 0;
  width: 100%; height: 180px;
}
.wave { fill: rgba(255,255,255,.18); }
.wave1 { animation: waveMove 9s linear infinite; }
.wave2 { animation: waveMove 13s linear infinite reverse; opacity: .5; }
.wave3 { animation: waveMove 17s linear infinite; opacity: .3; }
@keyframes waveMove {
  from { transform: translateX(0); }
  to   { transform: translateX(-1440px); }
}

/* ===== 右侧登录表单区 ===== */
.login-main {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
}

/* ===== 右侧装饰区 ===== */
.login-aside {
  position: absolute;
  right: 0; top: 0; bottom: 0;
  width: 30%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 26px;
  padding: 24px;
  color: #fff;
}
.aside-ship {
  width: 150px; height: 150px;
  opacity: .9;
  animation: spin 22s linear infinite;
}
@keyframes spin {
  from { transform: rotate(0); }
  to   { transform: rotate(360deg); }
}
.aside-cards {
  display: flex;
  flex-direction: column;
  gap: 14px;
  width: 100%;
  max-width: 260px;
}
.aside-card {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 14px 16px;
  border-radius: 12px;
  background: rgba(255,255,255,.65);
  backdrop-filter: blur(8px);
  border: 1px solid rgba(255,255,255,.6);
  box-shadow: 0 8px 20px rgba(20,60,120,.15);
  color: #143a66;
  animation: rise .6s ease both;
}
.aside-card:nth-child(2) { animation-delay: .12s; }
.aside-card:nth-child(3) { animation-delay: .24s; }
.aside-card svg { color: #1d6fe0; flex-shrink: 0; }
.ac-t { margin: 0; font-size: 15px; font-weight: 600; color: #0b3d91; }
.ac-d { margin: 2px 0 0; font-size: 12px; color: #4a6b8c; opacity: .85; }

.login-form {
  width: 100%;
  max-width: 400px;
  padding: 40px 38px 30px;
  border-radius: 16px;
  background: rgba(255,255,255,.92);
  backdrop-filter: blur(10px);
  box-shadow: 0 20px 60px rgba(0,0,0,.25);
  animation: rise .6s ease both;
}
@keyframes rise {
  from { opacity: 0; transform: translateY(24px); }
  to   { opacity: 1; transform: translateY(0); }
}
.form-head { text-align: center; margin-bottom: 26px; }
.title {
  margin: 0;
  font-size: 26px;
  font-weight: 700;
  color: #0b3d91;
  letter-spacing: 1px;
}
.subtitle {
  margin: 8px 0 0;
  font-size: 14px;
  color: #8a94a6;
}
.login-form .el-input {
  height: 44px;
  input {
    height: 44px;
    border-radius: 8px;
    padding-left: 36px;
  }
}
.login-form .input-icon {
  height: 44px;
  width: 16px;
  margin-left: 4px;
  color: #1890ff;
}
.remember { margin: 0 0 22px; color: #6b7280; }
.login-btn {
  height: 44px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  letter-spacing: 4px;
  background: linear-gradient(135deg, #3f8fd4 0%, #1855a8 100%);
  box-shadow: 0 8px 20px rgba(31,111,224,.35);
  transition: transform .15s ease, box-shadow .15s ease;
}
.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 26px rgba(24,144,255,.45);
}
.reg-link {
  margin-top: 14px;
  text-align: center;
  font-size: 14px;
  color: #8a94a6;
}
.reg-link .link-type { color: #1890ff; margin-left: 4px; }

.login-code {
  width: 33%;
  height: 44px;
  float: right;
  img { cursor: pointer; vertical-align: middle; border-radius: 8px; }
}
.login-code-img {
  height: 44px;
  width: 100%;
}

/* 响应式：窄屏隐藏两侧装饰 */
@media (max-width: 900px) {
  .login-banner { display: none; }
  .login-aside { display: none; }
  .login-main { flex: 1 1 100%; max-width: none; }
  .login { background: linear-gradient(135deg, #2f7fd0 0%, #7cb6e8 100%); }
}
</style>
