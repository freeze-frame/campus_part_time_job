<template>
  <el-container class="home-container">
    <!-- 头部 -->
    <el-header height="60px" class="header">
      <div class="header-sign">
        <img :src="imageData" alt="Logo" />
        <span>{{ headerTitle }}</span>
      </div>
      <el-button type="primary" class="logout" @click="logout">退出</el-button>
    </el-header>

    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="toggle_menu ? '64px' : '220px'" class="aside">
        <div class="toggle_button" @click="toggleCollapse" ref="toggle_dom">|||</div>

        <el-menu
            :default-active="activePath"
            router
            :collapse-transition="true"
            :collapse="toggle_menu"
            class="menu"
            background-color="#2C3E50"
            text-color="#B0BEC5"
            active-text-color="#42A5F5"
            :unique-opened="true"
        >
          <el-submenu v-for="item in menList" :key="item.id" :index="item.order + ''">
            <template slot="title">
              <i :class="getIcons(item.order)"></i>
              <span>{{ item.authName }}</span>
            </template>

            <el-menu-item v-for="son in item.children" :key="son.id" :index="son.path" @click="savaNavState(son.path, item.authName, son.authName)">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>{{ son.authName }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <!-- 主题 -->
      <el-main class="main-content">
        <Nav :second="navForm.second" :third="navForm.third"></Nav>
        <router-view class="main"></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  created() {
    const { pid } = this.$route.query;
    this.pid = pid;
    this.activePath = sessionStorage.getItem('activePath');
    this.imageData = sessionStorage.getItem('imageData');
    this.navForm.second = sessionStorage.getItem('navFormSecond');
    this.navForm.third = sessionStorage.getItem('navFormThird');
    this.headerTitle = this.pid === 1 ? '学生管理' : this.pid === 2 ? '商家管理' : '校园兼职服务平台';
  },
  data() {
    return {
      headerTitle: '校园兼职服务平台',
      navForm: { second: '', third: '' },
      pid: null,
      imageData: '',
      menList: [
        {
          id: 101,
          authName: '学生管理',
          path: 'stu',
          children: [
            { id: 1, authName: '学生信息总览', path: '/stu/show' },
            { id: 2, authName: '学生简历管理', path: '/stu/notes' },
            { id: 3, authName: '学生认证管理', path: '/stu/stuIdentity' }
          ],
          order: 1
        },
        {
          id: 102,
          authName: '商家管理',
          path: 'business',
          children: [
            { id: 3, authName: '商家信息总览', path: '/business/show' },
            { id: 4, authName: '商店信息总览', path: '/shop/show' },
            { id: 5, authName: '商家认证管理', path: '/business/merchantIdentity' },
            { id: 6, authName: '商店认证管理', path: '/shop/shopIdentity' }
          ],
          order: 2
        },
        {
          id: 103,
          authName: '评价管理',
          path: 'evaluate',
          children: [{ id: 7, authName: '评价总览', path: '/evaluate/show' }],
          order: 3
        }
      ],
      toggle_menu: false,
      activePath: ''
    };
  },
  methods: {
    getIcons(i) {
      switch (i) {
        case 1: return 'el-icon-user';   // 学生管理
        case 2: return 'el-icon-suitcase'; // 商家管理
        case 3: return 'el-icon-star'; // 评价管理
        default: return 'el-icon-document'; // 默认图标
      }
    },
    toggleCollapse() {
      this.toggle_menu = !this.toggle_menu;
      this.$refs.toggle_dom.innerText = this.toggle_menu ? '≡' : '|||';
    },
    savaNavState(activePath, nav1, nav2) {
      sessionStorage.setItem('navFormSecond', nav1);
      sessionStorage.setItem('navFormThird', nav2);
      this.navForm.second = nav1;
      this.navForm.third = nav2;
      sessionStorage.setItem('activePath', activePath);
    },
    logout() {
      this.$confirm('此操作将退出登录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        sessionStorage.clear();
        this.$router.push('/login');
        this.$message({ type: 'success', message: '退出成功!' });
      }).catch(() => {
        this.$message({ type: 'info', message: '退出取消' });
      });
    }
  }
};
</script>

<style scoped>
/* 基础字体大小和布局 */
html {
  font-size: 16px; /* 设置基准字体大小 */
}

body {
  font-family: 'Arial', sans-serif;
}

.home-container {
  height: 100%;
}

/* 头部 */
.el-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #2C3E50;
  color: white;
  line-height: 4rem; /* 增大行高 */
  padding: 0 2rem;
  font-size: 1.2rem; /* 增大字体 */
}

.header-sign {
  font-size: 1.5rem;
  font-weight: 600;
}

.header-sign img {
  width: 3rem;
  height: 3rem;
  vertical-align: middle;
  margin-right: 1rem;
}

.logout {
  height: 2.5rem;
  font-size: 1rem;
  background-color: #3498DB;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 0.25rem;
}

/* 侧边栏 */
.el-aside {
  width: 220px;
  background-color: #2C3E50;
  color: #BDC3C7;
  transition: all 0.3s;
  height: 100%;
  padding-top: 2rem;
}

.toggle_button {
  height: 2.5rem;
  line-height: 2.5rem;
  text-align: center;
  background-color: #34495E;
  color: #BDC3C7;
  font-size: 1rem;
  cursor: pointer;
  margin-bottom: 1rem;
}

.menu {
  border-right: none;
  overflow: hidden;
}

.el-menu {
  font-size: 1.1rem; /* 增大菜单字体 */
}

.el-menu-item {
  padding-left: 2rem;
}

.el-submenu__title {
  font-size: 1.1rem; /* 增大菜单标题字体 */
}

/* 主题内容区 */
.el-main {
  background-color: #ECF0F1;
  color: #333;
  padding: 2rem;
  border-radius: 1rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  flex: 1;
  font-size: 1.2rem;
}

/* 响应式设计 */
@media (max-width: 1024px) {
  html {
    font-size: 14px; /* 更小的屏幕时缩小字体 */
  }

  .el-header {
    font-size: 1rem;
  }

  .header-sign {
    font-size: 1.2rem;
  }

  .el-main {
    padding: 1.5rem;
  }
}

@media (max-width: 768px) {
  html {
    font-size: 12px;
  }

  .el-header {
    font-size: 0.9rem;
  }

  .header-sign {
    font-size: 1.1rem;
  }

  .el-main {
    padding: 1rem;
  }
}
</style>
