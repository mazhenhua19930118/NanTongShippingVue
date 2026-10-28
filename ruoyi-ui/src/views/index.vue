<template>
  <div class="app-container home">
    <!-- 品牌横幅（与登录页蓝色海洋风格一致） -->
    <section class="home-hero">
      <svg class="hero-ship" viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
        <circle cx="100" cy="100" r="78" fill="none" stroke="rgba(255,255,255,.25)" stroke-width="2" stroke-dasharray="6 8" />
        <path d="M40 120 L160 120 L146 150 L54 150 Z" fill="rgba(255,255,255,.85)" />
        <path d="M100 40 L100 120 M100 56 L140 80 L100 92 Z" fill="rgba(255,255,255,.9)" />
        <circle cx="100" cy="30" r="7" fill="rgba(255,255,255,.9)" />
      </svg>
      <div class="hero-text">
        <h1 class="hero-title">{{ title }}</h1>
        <p class="hero-slogan">智慧航运 · 通达天下</p>
      </div>
    </section>

    <el-row :gutter="20" class="home-intro">
      <el-col :sm="24" :lg="16">
        <el-card class="intro-card" shadow="never">
          <div slot="header" class="clearfix">
            <span class="card-title">系统简介</span>
          </div>
          <div class="intro-body">
            <p>
              船务调度管理系统广泛应用于沿海、内河及国际航运领域，通过科学的调度计划，实现船舶的合理分配和有效利用。该系统采用模块化设计，涵盖了船舶信息管理、航线规划、任务分配、等多个功能模块，为航运企业的日常运营提供全方位的支持。
            </p>
            <p>1、详细记录每艘船舶的基本信息、技术参数、航行历史等，便于调度人员快速了解船舶状况，为船舶的合理调度提供基础数据支持。</p>
            <p>2、根据船舶的实际状况和航线需求，合理分配装卸货任务、港口停靠计划等，确保船舶在预定时间内完成各项任务。这有助于提高船舶调度效率，减少等待时间。</p>
          </div>
        </el-card>
      </el-col>
      <el-col :sm="24" :lg="8">
        <el-card class="intro-card" shadow="never">
          <div slot="header" class="clearfix">
            <span class="card-title">快捷入口</span>
          </div>
          <div class="quick-list">
            <router-link to="/ship/info" class="quick-item">
              <i class="el-icon-ship"></i><span>船舶信息</span>
            </router-link>
            <router-link to="/dispatch/plan" class="quick-item">
              <i class="el-icon-connection"></i><span>调度计划</span>
            </router-link>
            <router-link to="/goods/manage" class="quick-item">
              <i class="el-icon-box"></i><span>货物管理</span>
            </router-link>
            <router-link to="/monitor/job" class="quick-item">
              <i class="el-icon-data-line"></i><span>数据看板</span>
            </router-link>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.7",
      title: process.env.VUE_APP_TITLE
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  overflow-x: hidden;
}

/* ===== 品牌横幅 ===== */
.home-hero {
  position: relative;
  display: flex;
  align-items: center;
  gap: 28px;
  padding: 36px 40px;
  margin-bottom: 20px;
  border-radius: 12px;
  overflow: hidden;
  color: #fff;
  background-image:
    radial-gradient(900px 500px at 12% 20%, rgba(255, 255, 255, .18), transparent 60%),
    radial-gradient(700px 400px at 88% 90%, rgba(80, 160, 240, .45), transparent 60%),
    linear-gradient(135deg, #2f7fd0 0%, #4f9fe0 55%, #7cb6e8 100%);
  box-shadow: 0 12px 30px rgba(28, 63, 110, .18);
}

.hero-ship {
  width: 120px;
  height: 120px;
  flex-shrink: 0;
  opacity: .95;
  animation: heroSpin 22s linear infinite, heroFloat 6s ease-in-out infinite;
  filter: drop-shadow(0 8px 18px rgba(0, 0, 0, .18));
}

@keyframes heroSpin {
  from { transform: rotate(0); }
  to   { transform: rotate(360deg); }
}

@keyframes heroFloat {
  0%, 100% { transform: translateY(0); }
  50%      { transform: translateY(-10px); }
}

.hero-title {
  margin: 0;
  font-size: 34px;
  font-weight: 700;
  letter-spacing: 2px;
  text-shadow: 0 4px 18px rgba(0, 0, 0, .2);
}

.hero-slogan {
  margin: 10px 0 0;
  font-size: 16px;
  letter-spacing: 5px;
  opacity: .92;
}

/* ===== 内容卡片 ===== */
.home-intro {
  margin-top: 4px;
}

.intro-card {
  border-radius: 10px;

  .card-title {
    position: relative;
    padding-left: 12px;
    font-size: 16px;
    font-weight: 600;
    color: #1c3f6e;

    &::before {
      content: '';
      position: absolute;
      left: 0;
      top: 2px;
      bottom: 2px;
      width: 4px;
      border-radius: 2px;
      background: linear-gradient(180deg, #2f7fd0, #4f9fe0);
    }
  }
}

.intro-body {
  font-size: 14px;
  line-height: 2;
  color: #4a5568;

  p {
    margin: 8px 0;
  }
}

/* 快捷入口 */
.quick-list {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.quick-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 14px 16px;
  border-radius: 10px;
  background: #eef5fc;
  color: #1c3f6e;
  font-size: 14px;
  font-weight: 600;
  text-decoration: none;
  transition: transform .15s ease, box-shadow .15s ease, background .15s ease;

  i {
    font-size: 20px;
    color: #2f7fd0;
  }

  &:hover {
    transform: translateY(-2px);
    background: #e1eefb;
    box-shadow: 0 8px 18px rgba(47, 127, 208, .2);
  }
}
</style>
