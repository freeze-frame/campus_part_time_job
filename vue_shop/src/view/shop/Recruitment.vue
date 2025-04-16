<template>
	<div class="card-container">
		<div class="card-operate">
			<el-button type="primary" @click="addRecruitmentInfoBtn">添加招聘信息</el-button>
			<!-- <el-button type="primary">查看所有招聘</el-button> -->
		</div>

		<el-card v-if="recruitmentInfo" class="box-card" v-for="(item, index) in recruitmentInfo" :key="index">
			<div slot="header" class="clearfix">
				<span class="b-tag">
					{{ item.recStation }}
				</span>

				<el-button style="font-size: 18px; float: right; padding: 3px 10px" type="text" @click="delRecruitmentInfoBtn(item)">删除信息</el-button>

				<el-button style="font-size: 18px; float: right; padding: 3px 10px" type="text" @click="editRecruitmentInfoBtn(item)">修改信息</el-button>
				<el-button style="font-size: 18px; float: right; padding: 3px 10px" type="text" @click="RecruitmentPosterBtn(item)">海报挑战</el-button>
			</div>

			<div class="recruitment-container">
				<el-descriptions class="margin-top" :column="3" border>
					<el-descriptions-item>
						<template slot="label">
							<i class="el-icon-user"></i>
							职位：
						</template>
						{{ item.recStation }}
					</el-descriptions-item>
					<el-descriptions-item>
						<template slot="label">
							<i class="el-icon-mobile-phone"></i>
							薪资待遇：
						</template>
						{{ item.recSalary }}
					</el-descriptions-item>
					<el-descriptions-item>
						<template slot="label">
							<i class="el-icon-location-outline"></i>
							招聘对象：
						</template>
						<el-tag size="small">{{ item.recObj }}</el-tag>
					</el-descriptions-item>
					<el-descriptions-item>
						<template slot="label">
							<i class="el-icon-tickets"></i>
							店名：
						</template>
						{{ item.bname }}
					</el-descriptions-item>
					<el-descriptions-item>
						<template slot="label">
							<i class="el-icon-office-building"></i>
							负责人：
						</template>
						{{ item.bhead }}
					</el-descriptions-item>
					<el-descriptions-item>
						<template slot="label">
							<i class="el-icon-office-building"></i>
							地址：
						</template>
						{{ item.baddress }}
					</el-descriptions-item>
					<el-descriptions-item>
						<template slot="label">
							<i class="el-icon-office-building"></i>
							简介：
						</template>
						<div v-html="item.recInfo" class="custom-html"></div>
					</el-descriptions-item>
				</el-descriptions>
			</div>
		</el-card>

		<div v-if="!recruitmentInfo" class="empty-box">
			<el-empty description="当前商家未添加招聘信息"></el-empty>
		</div>

		<el-dialog :visible.sync="addDialogVisible" width="30%" :before-close="handleAddDigClose" append-to-body>
			<el-form :model="addRecruitmentInfoForm" :rules="rules" class="addRecruitment_form" ref="RecruitmentAddFormRef">
				<h2>添加招聘信息</h2>
				<el-form-item prop="recStation">
					<el-input v-model="addRecruitmentInfoForm.recStation" placeholder="岗位" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>

				<el-form-item prop="recObj">
					<el-input v-model="addRecruitmentInfoForm.recObj" placeholder="对象" prefix-icon="el-icon-eleme"></el-input>
				</el-form-item>

				<el-form-item prop="recSalary">
					<el-input v-model="addRecruitmentInfoForm.recSalary" placeholder="薪资" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>

				<el-form-item prop="recInfo">
					<el-input v-model="addRecruitmentInfoForm.recInfo" type="textarea" placeholder="简介" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>
			</el-form>

			<span slot="footer" class="dialog-footer">
				<el-button type="info" @click="resetAddForm">重 置</el-button>
				<el-button type="primary" @click="excuteAddForm">添 加</el-button>
			</span>
		</el-dialog>

		<el-drawer append-to-body :withHeader="false" :visible.sync="drawer" :direction="direction" :before-close="handleDrawerClose">
			<el-form :model="editRecruitmentInfoForm" :rules="rules" class="editShop_form" ref="RecruitmentEditFormRef">
				<h2>修改招聘信息</h2>
				<el-form-item prop="recStation">
					<el-input v-model="editRecruitmentInfoForm.recStation" placeholder="岗位" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>

				<el-form-item prop="recObj">
					<el-input v-model="editRecruitmentInfoForm.recObj" placeholder="对象" prefix-icon="el-icon-eleme"></el-input>
				</el-form-item>

				<el-form-item prop="recSalary">
					<el-input v-model="editRecruitmentInfoForm.recSalary" placeholder="薪资" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>

				<el-form-item prop="recInfo">
					<el-input v-model="editRecruitmentInfoForm.recInfo" type="textarea" placeholder="简介" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>
			</el-form>
			<span class="drawer-footer">
				<el-button type="info" @click="resetEditForm">重 置</el-button>
				<el-button type="primary" @click="excuteEditForm">修 改</el-button>
			</span>
		</el-drawer>

		<el-dialog :visible.sync="addPosterObjDialogVisible" width="30%" :before-close="handleAddPosterObjDigClose" append-to-body>
			<el-form :model="posterObj">
				<h2>添加招聘头条</h2>

				<el-form-item prop="topTitle">
					<el-input v-model="posterObj.topTitle" placeholder="热门标题" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>
				<div class="red">点击添加/修改海报 挑战首页轮播图展示比赛:</div>
				<el-upload class="avatar-uploader" :action="''" :auto-upload="false" :show-file-list="false" :on-change="handleAvatarChangeIcon">
					<img v-if="posterObj.rotImage" :src="posterObj.rotImage" class="edit-avatar" />
					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
			</el-form>

			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="excuteAddPosterObj">添 加</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
import {
	recruitmentShowByBid,
	addRecruitmentInfo,
	deleteRecruitmentInfo,
	editRecruitmentInfo,
	RecruitmentPosterExistAPI,
	RecruitmentPosterAPI,
} from '@/api/AxiosAPI';
export default {
	created() {
		this.getRecruitmentByBid(sessionStorage.getItem('shopBid'));
	},
	data() {
		return {
			addPosterObjDialogVisible: false,
			posterObj: {
				recId: null,
				rotImage: null,
				topTitle: null,
				bannerUrl: null,
			},
			recruitmentInfo: null,
			size: '',
			addDialogVisible: false,
			addRecruitmentInfoForm: {
				recId: null,
				bid: null,
				recObj: null,
				recSalary: null,
				recStation: null,
				recInfo: null,
				topHeat: null,
			},
			drawer: false,
			direction: 'ltr',
			editRecruitmentInfoForm: {
				recId: null,
				recObj: null,
				recSalary: null,
				recStation: null,
				recInfo: null,
				topHeat: null,
			},
			rules: {
				topTitle: [
					{ required: true, message: '请输入头条标题', trigger: 'blur' },
					{
						min: 3,
						max: 10,
						message: '长度在 3 到 10 个字符',
						trigger: 'blur',
					},
				],
				recStation: [
					{ required: true, message: '请输入招聘岗位', trigger: 'blur' },
					{
						min: 2,
						max: 10,
						message: '长度在 2 到 10 个字符',
						trigger: 'blur',
					},
				],
				recObj: [
					{ required: true, message: '请输入招聘对象', trigger: 'blur' },
					{
						min: 2,
						max: 7,
						message: '长度在 2 到 7 个字符',
						trigger: 'blur',
					},
				],
				recSalary: [
					{ required: true, message: '请输入岗位工资', trigger: 'change' },
					{
						pattern: /^\d{1,9}(\.\d{1,2})?$/,
						message: '请输入正确的工资',
						trigger: 'change',
					},
				],
				recInfo: [
					{ required: true, message: '请输入招聘简介', trigger: 'blur' },
					{
						min: 4,
						max: 2000,
						message: '长度在 4 到 2000 个字符',
						trigger: 'blur',
					},
				],
			},
		};
	},
	methods: {
		//海报操作
		async excuteAddPosterObj() {
			const { data: res } = await RecruitmentPosterAPI(this.posterObj);
			if (res.code !== 200) return this.$message.error(res.message);
			this.$message.success(res.message);
			(this.posterObj.recId = null), (this.posterObj.rotImage = null), (this.posterObj.bannerUrl = null);
			this.addPosterObjDialogVisible = false;
		},
		//修改页面图片上传和回显的
		handleAvatarChangeIcon(file, fileList) {
			const isJPGORPNG = file.raw.type === 'image/jpeg' || file.raw.type === 'image/png';
			const isLt1M = file.size / 1024 / 1024 < 1;

			if (!isJPGORPNG) {
				this.$message.info('上传头像图片只能是 JPG 或 PNG 格式!');
				return;
			}
			if (!isLt1M) {
				this.$message.info('上传头像图片大小不能超过 1MB!');
				return;
			}
			var This = this;
			var reader = new FileReader();
			reader.readAsDataURL(file.raw);
			reader.onload = function (e) {
				This.posterObj.rotImage = this.result;
			};
		},

		handleAddPosterObjDigClose(done) {
			this.$confirm('确认关闭？')
				.then((_) => {
					done();
					(this.posterObj.recId = null), (this.posterObj.rotImage = null), (this.posterObj.bannerUrl = null);
				})
				.catch((_) => {});
		},
		async RecruitmentPosterBtn(item) {
			const { data: res } = await RecruitmentPosterExistAPI(item.recId);
			if (res.data == null && res.code !== 200) {
				this.$message.error(res.message);
				this.posterObj.recId = item.recId;
			} else {
				this.posterObj = res.data;
			}
			this.addPosterObjDialogVisible = true;
		},
		//修改的重置操作
		resetEditForm() {
			this.editRecruitmentInfoForm = {};
		},
		//修改的执行API
		excuteEditForm() {
			this.$refs.RecruitmentEditFormRef.validate(async (valid) => {
				if (!valid) return;
				const { data: res } = await editRecruitmentInfo(this.editRecruitmentInfoForm);
				if (res.code !== 200) return this.$message.error(res.message);
				this.getRecruitmentByBid(this.editRecruitmentInfoForm.bid);
				this.drawer = false;
				this.resetEditForm();
				this.$message.success(res.message);
			});
		},
		//修改页面关闭的
		handleDrawerClose(done) {
			this.$confirm('确认关闭？')
				.then((_) => {
					done();
					this.resetEditForm();
				})
				.catch((_) => {});
		},
		//修改执行
		editRecruitmentInfoBtn(item) {
			this.editRecruitmentInfoForm = JSON.parse(JSON.stringify(item));
			this.drawer = true;
		},
		//增加招聘信息重置
		resetAddForm() {
			this.addRecruitmentInfoForm = {};
		},
		//增加招聘信息确定按钮
		excuteAddForm() {
			this.$refs.RecruitmentAddFormRef.validate(async (valid) => {
				if (!valid) return;
				this.addRecruitmentInfoForm.bid = sessionStorage.getItem('shopBid');
				const { data: res } = await addRecruitmentInfo(this.addRecruitmentInfoForm);
				if (res.code !== 200) return this.$message.error(res.message);
				this.getRecruitmentByBid(this.addRecruitmentInfoForm.bid);
				this.resetAddForm();
				this.addDialogVisible = false;
			});
		},

		async getRecruitmentByBid(bid) {
			const { data: res } = await recruitmentShowByBid(bid);
			if (res.code !== 200) this.$message.error(res.message);
			this.recruitmentInfo = res.data;
		},

		//删除执行
		async delRecruitmentInfoBtn(item) {
			this.$confirm('确认删除？')
				.then(async (_) => {
					const { data: res } = await deleteRecruitmentInfo(item.recId);
					if (res.code !== 200) return this.$message.error(res.message);
					this.getRecruitmentByBid(item.bid);
					this.$message.success(res.message);
					done();
				})
				.catch((_) => {});
		},
		//增加执行
		addRecruitmentInfoBtn() {
			this.addDialogVisible = true;
		},
		//退出修改增加框
		handleAddDigClose(done) {
			this.$confirm('确认关闭？')
				.then((_) => {
					done();
					resetAddForm();
				})
				.catch((_) => {});
		},
	},
};
</script>

<style lang="less" scoped>
.red {
	padding-bottom: 15px;
	color: red;
	font-size: 15px;
}
.edit-avatar {
	width: 100%;
	height: 300px;
	border: #00d9ff 2px dashed;
}
.avatar-uploader {
	/deep/.el-upload {
		width: 100%;
	}
}
/deep/.el-textarea__inner {
	height: 300px;
}
.editShop_form {
	padding-top: 20px;
	width: 500px;
	margin: 0 auto;
}
.drawer-footer {
	padding: 30px;
	float: right;
}
.card-container {
	.recruitment-container {
		//   display: flex;
		//   flex-direction: row;
		//   flex-wrap: wrap;
		//   justify-content: center;

		//   width: 1240px;
		//   padding-bottom: 100px;
		//   .margin-top {
		//     width: 1240px;

		//   }
		/deep/.custom-html {
			width: 900px;
			white-space: pre-wrap;
		}
	}

	// display: flex;
	// flex-direction: column;
	// align-items: center;
	display: flex;
	flex-direction: row;
	flex-wrap: wrap;
	justify-content: center;

	width: 1240px;
	padding-bottom: 100px;
	.card-operate {
		flex-basis: 100%;
		width: 600px;
		display: flex;
		justify-content: center;
		margin-top: 50px;
		margin-bottom: 30px;
		button {
			width: 600px;
			border-radius: 30px;
			font-size: 19px;
		}
	}

	.empty-box {
		border-radius: 10px;
		width: 800px;
		margin: 15px;
		background-color: rgb(235, 226, 226);
		p {
			color: #000;
			font-size: 18px;
		}
	}
}

.box-card {
	position: relative;
	width: 100%;
	margin: 15px;
	overflow: visible;

	transition: all 1s linear;
	.b-tag {
		font-weight: bold;
		font-size: 20px;
	}
}

.box-card:hover {
	top: -2px;
	right: -2px;
	background-color: rgb(231, 245, 248);
}

.box-card:hover::before {
	content: '';
	position: absolute;
	top: -2px;
	left: -2px;
	right: -2px;
	bottom: -2px;
	background: #fff;
	border-radius: 10px;
	background: linear-gradient(235deg, #00d9ff, #010615, #00bcd4);
	z-index: -1;

	cursor: pointer;
	transition: 0.2s;
	transition-duration: 1s;
	transition-timing-function: ease-in-out;
}
.box-card:hover::after {
	content: '';
	position: absolute;
	top: -2px;
	left: -2px;
	right: -2px;
	bottom: -2px;
	background: #fff;
	filter: blur(8px);
	border-radius: 10px;
	background: linear-gradient(235deg, #ff5e00, #010615, #00bcd4);
	z-index: -2;

	cursor: pointer;
	transition: 0.2s;
	transition-duration: 1s;
	transition-timing-function: ease-in-out;
}
</style>
