<template>
	<div class="box-container">
		<div class="header">
			<el-header>
				<div class="header-sign">
					<img :src="imageData" alt="" />
					校园兼职服务平台——商家端
				</div>

				<div class="header-menu">
					<el-menu
						:default-active="activePath"
						router
						class="el-menu-demo"
						mode="horizontal"
						background-color="#313743"
						text-color="#fff"
						active-text-color="#ffd04b"
					>
						<el-menu-item :index="item.path" v-for="item in menuList" :key="item.id" @click="handleMenuItem(item.path)">{{
							item.authName
						}}</el-menu-item>
					</el-menu>
				</div>

				<el-button type="info" class="logout" @click="logout">退出</el-button>
			</el-header>
		</div>

		<div class="managehome-container">
			<el-container class="home-container">
				<router-view class="main"> </router-view>
			</el-container>
		</div>
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
					path: '/managehome/manageindex',
					order: 1,
				},
				{
					id: 102,
					authName: '我的店铺',
					path: '/managehome/shop',
					order: 2,
				},
				{
					id: 103,
					authName: '学生简历',
					path: '/managehome/notes',
					order: 3,
				},
				{
					id: 104,
					authName: '沟通',
					path: '/managehome/chat',
					order: 4,
				},
				{
					id: 105,
					authName: '个人中心',
					path: '/managehome/myshop',
					order: 5,
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
	// watch:{
	//   $route:{
	//     handler(val,oldval){
	//       console.log(val.path);
	//       // console.log(oldval);
	//       this.activePath = val.path;
	//     },
	//     deep: true
	//   }
	// }
};
</script>

<style lang="less" scoped>
.box-container {
	height: 100%;
	// background: url("../assets/453.png");
	background-color: #313743;

	// background-size: cover;
	overflow: hidden;
	&::before {
		position: absolute;
		content: '';
		height: 100%;
		width: 100%;
		background-color: rgba(31, 33, 41, 0.2);
		backdrop-filter: blur(10px);
	}
	.managehome-container {
		position: relative;
		top: 60px;
		overflow: auto;
		height: 100%;
		width: 100%;
		z-index: 99;

		.home-container {
			display: flex;
      height: 100%;
      width: 100%;
			margin: 0 auto;
		}
	}
}

.header {
	position: fixed;
	width: 100%;
	min-width: 1240px;
}

.el-header {
	display: flex;
	// 首个元素放置于起点，末尾元素放置于终点
	justify-content: space-between;
	// 侧轴对齐 center
	align-items: center;
	background-color: #313743;
	line-height: 60px;
	.header-menu {
		margin-left: 10px;
		display: flex;
		margin-right: auto;
	}
	.header-sign {
		font-size: 19px;
		font-weight: 700;
		color: #41b883;

		img {
			cursor: pointer;
			width: 45px;
			height: 45px;
			// 图片基线对齐方式
			vertical-align: middle;
		}
	}
	.logout {
		height: 40px;
	}
}
.main-middle {
	width: 1240px;
	margin: 0 auto;
}
</style>
