<template>
  <div class="box-container">
    <!-- 优化导航栏结构 -->
    <header class="main-header">
      <div class="header-content">
        <div class="brand-area">
          <img class="logo" :src="imageData" alt="平台logo" />
          <h1 class="title" >校园兼职服务平台</h1>
        </div>

        <nav class="navigation">
          <el-menu
              :default-active="activePath"
              router
              mode="horizontal"
              class="enhanced-menu"
              background-color="transparent"
              text-color="#e6e6e6"
              active-text-color="#41b883"
          >
            <el-menu-item
                v-for="item in menuList"
                :key="item.id"
                :index="item.path"
                class="menu-item"
                @click="handleMenuItem(item.path)"
            >
              <i class="el-icon-menu"></i>
              <span class="menu-text">{{ item.authName }}</span>
            </el-menu-item>

            <el-submenu index="/suthome/myself" popper-class="profile-submenu">
              <template slot="title">
                <i class="el-icon-user"></i>
                <span class="profile-text">个人中心</span>
              </template>
              <el-menu-item index="/suthome/myself/info">
                <i class="el-icon-document"></i>个人信息
              </el-menu-item>
              <el-menu-item index="/suthome/myself/notes">
                <i class="el-icon-notebook-2"></i>个人简历
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </nav>

        <el-button
            type="success"
            class="logout-btn"
            @click="logout"
            round
        >
          <i class="el-icon-switch-button"></i>
          <span>安全退出</span>
        </el-button>
      </div>
    </header>

    <!-- 优化内容区域 -->
    <main class="content-wrapper">
      <div class="content-card">
        <router-view></router-view>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  created() {
    // 当一被创建时就把路由链接赋值给activePath,用于对应高亮显示
    this.imageData = sessionStorage.getItem('imageData');
    this.activePath = sessionStorage.getItem('activePath');
  },
  data() {
    return {
      userBaseInfo: null,
      imageData: '',
      // 用于菜单路由动态高亮
      activePath: '',
      menuList: [
        {
          id: 101,
          authName: '首页',
          path: '/suthome/stuindex',
          order: 1,
        },
        {
          id: 102,
          authName: '联系商家',
          path: '/suthome/chat',
          order: 2,
        },
        {
          id: 103,
          authName: '我的投递',
          path: '/suthome/deliver',
          order: 3,
        },
      ],
    };
  },
  methods: {
    handleMenuItem(activePath) {
      sessionStorage.setItem('activePath', activePath);
    },
    logout() {
      this.$confirm('此操作将退出登录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
          .then(() => {
            sessionStorage.clear();
            this.$router.push('/login');
            this.$message({
              type: 'success',
              message: '退出成功!',
            });
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '退出取消',
            });
          });
    },
  },
};
</script>

<style lang="less" scoped>
.box-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: rgba(75, 139, 251, 0);
  width: 100%;
  font-family: 'Roboto', sans-serif; /* 设置艺术字体系列 */ /* 设置全局字体系列 */
  font-size: 20px;
}

.main-header {
  position: sticky;
  top: 0;
  z-index: 1000;
  background: rgba(204, 82, 82, 0.1);
  backdrop-filter: blur(10px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  height: 64px; /* 固定导航栏高度 */
  width: 100%; /* 固定导航栏宽度 */
  margin: 0 auto;
}

.header-content {
  width: 1280px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  height: 100%;
  padding: 0 30px;
}

.brand-area {
  display: flex;
  align-items: center;
  margin-right: 50px;

  .logo {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    margin-right: 15px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  .title {
    font-size: 26px; /* 增加字体大小 */
    font-family: 'Great Vibes', cursive; /* 设置艺术字体系列 */
    color: #4074e4;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    letter-spacing: 1.5px;
    width: 300px; /* 增加宽度以适应更大的字体 */
    text-transform: uppercase; /* 转换为大写，增强艺术感 */
  }
}

.navigation {
  flex: 1;

  .enhanced-menu {
    border-bottom: none;
    background: transparent;
    display: flex;
    align-items: center;
    height: 60px;

    .menu-item {
      margin: 0 10px;
      transition: all 0.3s;
      border-radius: 6px;
      padding: 0 20px;
      height: 100%;
      display: flex;
      align-items: center;

      &:hover {
        background: rgb(255, 255, 255) !important;
        transform: translateY(-2px);
      }

      .menu-text {
        margin-left: 8px;
        font-weight: 500;
      }
    }
  }
}

.logout-btn {
  padding: 10px 25px;
  font-weight: 500;
  letter-spacing: 1px;
  transition: all 0.3s;
  height: 40px; /* 固定按钮高度 */
  display: flex;
  align-items: center;

  &:hover {
    transform: scale(1.05);
    box-shadow: 0 4px 12px rgba(65, 184, 131, 0.3);
  }
}

.content-wrapper {
  display: flex;
  justify-content: center;
  align-items: flex-start; /* 确保内容从顶部开始 */
  height: calc(100vh - 64px); /* 减去header的高度 */
  width: 100%; /* 固定宽度为网页大小 */
  height: 300px;
}

.content-card {
  width: 100%;
  background: #96ddf6;
  border-radius: 12px;
  overflow-y: auto;
}

/* 优化子菜单样式 */
/deep/ .profile-submenu {
  .el-menu-item {
    min-width: 160px;
    padding: 0 25px !important;
    height: 40px;
    line-height: 40px;
    transition: all 0.3s;
    color: black;

    i {
      margin-right: 10px;
    }

    &:hover {
      background: #f5f7fa !important;
      color: #41b883 !important;
    }
  }
}
</style>



<!--<template>-->
<!--	<div class="box-container">-->
<!--		<div class="bz"></div>-->
<!--		<div class="header">-->
<!--			<el-header>-->
<!--				<div class="header-sign">-->
<!--					<img :src="imageData" alt="" />-->
<!--					校园兼职服务平台-->
<!--				</div>-->

<!--				<div class="header-menu">-->
<!--					<el-menu-->
<!--						:default-active="activePath"-->
<!--						router-->
<!--						class="el-menu-demo"-->
<!--						mode="horizontal"-->
<!--						background-color="#313743"-->
<!--						text-color="#fff"-->
<!--						active-text-color="#ffd04b"-->
<!--						menu-trigger="click"-->
<!--					>-->
<!--						<el-menu-item :index="item.path" v-for="item in menuList" :key="item.id" @click="handleMenuItem(item.path)">{{-->
<!--							item.authName-->
<!--						}}</el-menu-item>-->

<!--						<el-submenu index="/suthome/myself">-->
<!--							<template slot="title">个人中心</template>-->
<!--							<el-menu-item index="/suthome/myself/info" @click="handleMenuItem('/suthome/myself/info')">个人信息</el-menu-item>-->
<!--							<el-menu-item index="/suthome/myself/notes" @click="handleMenuItem('/suthome/myself/notes')">个人简历</el-menu-item>-->
<!--						</el-submenu>-->
<!--					</el-menu>-->
<!--				</div>-->

<!--				<el-button type="info" class="logout" @click="logout">退出</el-button>-->
<!--			</el-header>-->
<!--		</div>-->

<!--		<div class="stu-container">-->
<!--			<el-container class="home-container">-->
<!--				<router-view> </router-view>-->
<!--			</el-container>-->
<!--		</div>-->
<!--	</div>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--	created() {-->
<!--		// 当一被创建时就把路由链接赋值给activePath,用于对应高亮显示-->
<!--		this.imageData = sessionStorage.getItem('imageData');-->
<!--		this.activePath = sessionStorage.getItem('activePath');-->
<!--	},-->
<!--	data() {-->
<!--		return {-->
<!--			userBaseInfo: null,-->
<!--			imageData: '',-->
<!--			// 用于菜单路由动态高亮-->
<!--			activePath: '',-->
<!--			menuList: [-->
<!--				{-->
<!--					id: 101,-->
<!--					authName: '首页',-->
<!--					path: '/suthome/stuindex',-->
<!--					order: 1,-->
<!--				},-->
<!--				{-->
<!--					id: 102,-->
<!--					authName: '联系商家',-->
<!--					path: '/suthome/chat',-->
<!--					order: 2,-->
<!--				},-->
<!--				{-->
<!--					id: 103,-->
<!--					authName: '我的投递',-->
<!--					path: '/suthome/deliver',-->
<!--					order: 3,-->
<!--				},-->
<!--			],-->
<!--		};-->
<!--	},-->
<!--	methods: {-->
<!--		handleMenuItem(activePath) {-->
<!--			sessionStorage.setItem('activePath', activePath);-->
<!--		},-->
<!--		logout() {-->
<!--			this.$confirm('此操作将退出登录, 是否继续?', '提示', {-->
<!--				confirmButtonText: '确定',-->
<!--				cancelButtonText: '取消',-->
<!--				type: 'warning',-->
<!--			})-->
<!--				.then(() => {-->
<!--					sessionStorage.clear();-->
<!--					this.$router.push('/login');-->
<!--					this.$message({-->
<!--						type: 'success',-->
<!--						message: '退出成功!',-->
<!--					});-->
<!--				})-->
<!--				.catch(() => {-->
<!--					this.$message({-->
<!--						type: 'info',-->
<!--						message: '退出取消',-->
<!--					});-->
<!--				});-->
<!--		},-->
<!--	},-->
<!--	// watch:{-->
<!--	//   $route:{-->
<!--	//     handler(val,oldval){-->
<!--	//       console.log(val.path);-->
<!--	//       // console.log(oldval);-->
<!--	//       this.activePath = val.path;-->
<!--	//     },-->
<!--	//     deep: true-->
<!--	//   }-->
<!--	// }-->
<!--};-->
<!--</script>-->

<!--<style lang="less" scoped>-->
<!--.bz {-->
<!--}-->
<!--.box-container {-->
<!--	// background: url("../assets/453.png");-->
<!--	// background-color: #313743;-->
<!--	// background-size: cover;-->
<!--	// &::before {-->
<!--	//   position: absolute;-->
<!--	//   content: "";-->
<!--	//   height: 100%;-->
<!--	//   width: 100%;-->
<!--	//   // background-color: rgba(31, 33, 41, 0.2);-->
<!--	//   // backdrop-filter: blur(10px);-->
<!--	// }-->
<!--	height: 100%;-->
<!--	.stu-container {-->
<!--		// background-color: rgba(31, 33, 41, 0.2);-->
<!--		position: relative;-->
<!--		height: 100%;-->
<!--		width: 100%;-->
<!--		z-index: 1;-->
<!--		height: 100%;-->

<!--		// padding-top: 60px;-->
<!--		.home-container {-->
<!--			height: 100%;-->
<!--			margin: 0 auto;-->
<!--		}-->
<!--	}-->
<!--}-->

<!--.header {-->
<!--	position: fixed;-->
<!--	top: 0;-->
<!--	width: 100%;-->
<!--	min-width: 1240px;-->
<!--	z-index: 3;-->
<!--}-->

<!--.el-header {-->
<!--	display: flex;-->
<!--	// 首个元素放置于起点，末尾元素放置于终点-->
<!--	justify-content: space-between;-->
<!--	// 侧轴对齐 center-->
<!--	align-items: center;-->
<!--	background-color: #313743;-->
<!--	line-height: 60px;-->
<!--	.header-menu {-->
<!--		margin-left: 10px;-->
<!--		display: flex;-->
<!--		margin-right: auto;-->
<!--	}-->
<!--	.header-sign {-->
<!--		font-size: 19px;-->
<!--		font-weight: 700;-->
<!--		color: #41b883;-->

<!--		img {-->
<!--			cursor: pointer;-->
<!--			width: 45px;-->
<!--			height: 45px;-->
<!--			// 图片基线对齐方式-->
<!--			vertical-align: middle;-->
<!--		}-->
<!--	}-->
<!--	.logout {-->
<!--		height: 40px;-->
<!--	}-->
<!--}-->
<!--.main-middle {-->
<!--	width: 1240px;-->
<!--	margin: 0 auto;-->
<!--}-->
<!--</style>-->
