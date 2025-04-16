<template>
	<div class="main">
		<div class="top-banner">
			<div class="top-10">
				<ul v-for="(item, index) in TopAndBannerList" :key="index">
					<li @click="infoPageFn(item.bannerUrl)">
						<span class="before-num">{{ index + 1 }}</span>
						<a>{{ item.topTitle }}</a>
					</li>
				</ul>
			</div>

			<div class="block">
				<el-carousel trigger="click" height="500px">
					<el-carousel-item v-for="(item, index) in TopAndBannerList" :key="index">
						<img @click="infoPageFn(item.bannerUrl)" :src="item.rotImage" width="100%" height="100%" alt="" />
					</el-carousel-item>
				</el-carousel>
			</div>
		</div>

		<div class="title">
			<h1>热招职位</h1>
			<div class="search">
				<el-form :inline="true">
					<el-form-item>
						<el-input v-model="search" placeholder="职位关键字"></el-input>
					</el-form-item>
					<el-form-item>
						<div style="text-align: right">
							<!--自定义searchHandler函数-->
							<el-button type="primary" @click="searchHandler">查询</el-button>
							<el-button type="info" @click="RecList">重置</el-button>
						</div>
					</el-form-item>
				</el-form>
			</div>
		</div>
		<div class="list">
			<ul class="list-ul">
				<li @click="recInfoFn(item)" v-for="(item, index) in recList" :key="index">
					<div class="subbox">
						<a>
							<div class="subtop">
								<p class="name">{{ item.recStation }}</p>
								<span class="salary">￥{{ item.recSalary }}/h</span>
							</div>
							<p class="job-text">{{ item.recInfo }}</p>
						</a>
						<div class="commanybox">
							<a class="user-info">
								<p><img :src="item.bImage" /></p>
							</a>
							<a class="commany-info">
								<span>{{ item.bName }}</span>
								<span>{{ item.bAddress }}</span>
								<span>{{ item.recObj }}</span>
							</a>
						</div>
					</div>
				</li>
			</ul>
		</div>

		<div class="footer">
			<div class="footmain">
				<div class="footbox" style="width: 120px">
					<div class="widget-title">企业服务</div>
					<div class="foottext">
						<ul>
							<li><a href="#">职位搜索 </a></li>
							<li><a href="#">新闻资讯 </a></li>
							<li><a href="#">BOSS直聘APP </a></li>
							<li><a href="#">投资者关系 </a></li>
						</ul>
					</div>
				</div>
				<div class="footbox" style="width: 120px">
					<div class="widget-title">使用与帮助</div>
					<div class="footblog">
						<ul>
							<li><a href="#">协议与规则 </a></li>
							<li><a href="#">个人信息保护政策 </a></li>
							<li><a href="#">防骗指南 </a></li>
							<li><a href="#">使用帮助 </a></li>
						</ul>
					</div>
				</div>
				<div class="footbox" style="width: 120px">
					<div class="widget-title">招聘频道介绍</div>
					<div class="footblog">
						<ul>
							<li><a href="#">南昌培训策划招聘</a></li>
							<li><a href="#">南昌磨工招聘 </a></li>
							<li><a href="#"> 南昌培训策划招聘</a></li>
							<li><a href="#"> 南昌模具工招聘</a></li>
						</ul>
					</div>
				</div>
				<div class="footbox">
					<div class="widget-title">联系我们</div>
					<div class="footblog">
						<ul>
							<li><a href="#">188164795421 彭</a></li>
							<li><a href="#">13432728897 王</a></li>
							<li><a href="#">13432728897 熊</a></li>
						</ul>
					</div>
				</div>
				<div class="footbox">
					<div class="widget-title">关于我们</div>
					<div class="footabout">
						<p>校园兼职招聘频道，展示校园招聘信息，百万在线直聘，直接开聊，在线面试，找兼职就上校园直聘网站。</p>
						<p>校园兼职招聘频道，展示校园招聘信息，百万在线直聘，直接开聊，在线面试，找兼职就上校园直聘网站。</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { getRecruitmentAPI, addRecHeatAPI, getTopAPI, getSearchRecInfoAPI } from '@/api/AxiosAPI';
export default {
	created() {
		this.getRecList();
		this.getTopAndBanner();
	},
	data() {
		return {
			search: null,
			recList: null,
			TopAndBannerList: null,
		};
	},
	methods: {
		//搜索
		async searchHandler() {
			// let regu = /^[^\s]+[\s]*.+$/;
			if (this.search.trim() == '' || this.search == null) {
				return this.$message.error('请输入正确关键字');
			}
			const { data: res } = await getSearchRecInfoAPI(this.search);
			if (res.code !== 200) return this.$message.error(res.message);
			//获取成功刷新数据
			this.recList = res.data;
			this.search = null;
			this.$message.success(res.message);
		},
		//重置搜索框
		RecList() {
			this.getRecList();
		},
		infoPageFn(page) {
			this.$router.push({
				path: page,
			});
		},
		//获取头条数据
		async getTopAndBanner() {
			const { data: res } = await getTopAPI();
			if (res.code !== 200) return this.$message.error(res.message);
			this.TopAndBannerList = res.data;
		},
		//获取简历信息信息
		async recInfoFn(item) {
			sessionStorage.setItem('infobyBossUid', item.uid);
			sessionStorage.setItem('infobyRecId', item.recId);
			sessionStorage.setItem('infobyBid', item.bId);
			//增加这个招聘的热度
			const { data: res } = await addRecHeatAPI(item.recId);
			if (res.code !== 200) return this.$message.error(res.message);

			this.$router.push({
				path: 'recshow/' + item.recId,
			});
		},
		//获取首页信息
		async getRecList() {
			const { data: res } = await getRecruitmentAPI();
			if (res.code !== 200) return this.$message.error(res.message);
			this.recList = res.data;
			console.log(this.recList);
		},
	},
};
</script>

<style lang="less" scoped>
.top-10 {
	cursor: pointer;
	height: 458px;
	float: left;
	width: 340px;
	padding: 20px;
	background: #fff;
	border: 1px solid #fff;
}

.top-10 ul {
	counter-reset: section;
}

.top-10 li {
	float: left;
	width: 260px;
	border-bottom: 1px solid #b8c2cc;
	line-height: 46px;
	height: 46px;
	overflow: hidden;
	color: #525c66;
	font-size: 14px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

.top-10 li .before-num {
	background-color: rgb(180, 1, 1);

	display: inline-block;
	padding: 0 12px;
	margin-right: 10px;
	height: 18px;
	line-height: 18px;
	color: #fff;
	border-radius: 3px;
	font-size: 9px;
}

#rank-more {
	float: left;
	width: 100%;
	margin-top: 8px;
	text-align: center;
}

.span-h7 {
	font-size: 14px;
	color: #b8c2cc;
	font-weight: 300;
}

.top-banner {
	box-shadow: #d0d0d0 1px 1px 10px 0px;

	display: flex;
	border: rgb(211, 223, 217) 1px solid;

	.block {
		width: 100%;
		cursor: pointer;
	}
}
.main {
	padding-top: 60px;
	width: 1240px;
	margin: 0 auto;
}

body,
div,
dl,
dt,
dd,
ul,
ol,
li,
h1,
h2,
h3,
img,
form,
input,
p,
th,
td {
	border: none;
	padding: 0;
	margin: 0;
	list-style: none;
}

a {
	color: #333;
	text-decoration: none;
}

a:hover {
	color: #f60;
	text-decoration: none;
}

.el-carousel__item h3 {
	color: #475669;
	font-size: 14px;
	opacity: 0.75;
	line-height: 150px;
	margin: 0;
}

.el-carousel__item:nth-child(2n) {
	background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
	background-color: #d3dce6;
}

.title {
	display: flex;
	justify-content: space-between;
	padding: 30px 0;
	color: #f60;
}

.title h1 {
	padding-left: 10px;
	// text-align: center;
}

.list {
	overflow: hidden;
	padding-bottom: 50px;
}
.list ul {
	display: flex;
	justify-content: space-between;
	flex-wrap: wrap;
}
.list ul li {
	height: 130px;
	width: 350px;
	// float: left;
	cursor: pointer;
	margin: 15px 10px;
	background: #fff;
	border: 1px solid rgb(211, 223, 217);
}

.subbox {
	display: block;
	width: 100%;
	padding: 16px 20px;
	box-sizing: border-box;
}
.subtop {
	overflow: hidden;
}
.name {
	position: relative;
	font-weight: bold;
	font-size: 17px;
	color: #414a60;
	margin-right: 8px;

	overflow: hidden;

	float: left;
}
.salary {
	float: right;
	color: #f90;
	font-size: 14px;
}
.job-text {
	height: 40px;
	line-height: 40px;

	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}
.user-info img {
	width: 30px;
	height: 30px;
	float: left;
	margin-right: 10px;
}

.commanybox {
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	border-top: 1px solid #eef0f5;
	line-height: 30px;
	padding-top: 12px;
}
.commany-info span {
	font-size: 14px;
	margin-right: 10px;
}

.footer {
	border-top: 5px solid #f08b3d;

	color: #888;
	overflow: hidden;
	padding: 20px 0 0;
}

.footmain {
	width: 1200px;
	margin: 0 auto;
	display: flex;
	justify-content: space-between;
}

.footbox {
	width: 250px;
}

.widget-title {
	color: #333;
	text-transform: uppercase;
	margin-bottom: 25px;
	font-size: 14px;
}

.footer a {
	color: #888;
}

.foottext img {
	padding: 4px;
	border: solid 1px #424242;
	float: left;
	margin-right: 10px;
}

.foottext ul li {
	overflow: hidden;
	margin-bottom: 20px;
}

.footblog ul li {
	line-height: 28px;
}

.footimg {
	overflow: hidden;
}

.footimg ul li {
	float: left;
	margin-right: 20px;
	text-align: center;
}

.footimg ul li img {
	padding: 4px;
	border: solid 1px #424242;
	width: 50px;
	height: 50px;
}

.footabout p {
	line-height: 26px;
	margin-bottom: 20px;
}

.foottips {
	background: #1b1b1b;
	width: 100%;
}

.footlayout {
	background: #1b1b1b center center no-repeat;
	width: 1100px;
	margin: 0 auto;
	height: 90px;
	line-height: 90px;
	color: #999;
}

.custom-html {
	white-space: pre-wrap;
}
</style>
