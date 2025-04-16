<template>
	<div class="">
		<el-col :span="20">
			<el-form :inline="true">
				<el-form-item>
					<el-input v-model="search" placeholder="店名关键字"></el-input>
				</el-form-item>
				<el-form-item>
					<div style="text-align: right">
						<!--自定义searchHandler函数-->
						<el-button type="primary" @click="searchHandler">查询</el-button>
						<el-button type="info" @click="getShopList">重置</el-button>
					</div>
				</el-form-item>
			</el-form>
		</el-col>

		<el-table :data="shopList" style="width: 100%" border v-loading="loading" >
			<el-table-column type="expand" label="相关招聘" >
				<template slot-scope="props">
					<el-form v-if="props.row.children" label-position="left" inline class="demo-table-expand">
						<el-form-item v-for="(item, index) in props.row.children" :key="index" class="expand-item">
							<el-card v-if="item !== null" class="box-card">
								<div slot="header" class="clearfix">
									<span class="b-tag">
										{{ item.recStation }}
									</span>

									<el-button style="font-size: 18px; float: right; padding: 3px 10px" type="text" @click="delRecruitmentInfoBtn(item)"
										>删除信息</el-button
									>

									<el-button style="font-size: 18px; float: right; padding: 3px 10px" type="text" @click="editRecruitmentInfoBtn(item)"
										>修改信息</el-button
									>
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
												<i class="el-icon-office-building"></i>
												简介：
											</template>
											<div v-html="item.recInfo" class="custom-html"></div>
										</el-descriptions-item>
									</el-descriptions>
									<h3>简历：</h3>
									<el-table v-if="item.children" :data="item.children" border style="width: 100%">
										<el-table-column label="创建时间">
											<template slot-scope="scope">
												<span style="margin-left: 10px">{{ scope.row.createTime }}</span>
											</template>
										</el-table-column>
										<el-table-column label="简历名称">
											<template slot-scope="scope">
												<span style="margin-left: 10px">{{ scope.row.filename }}</span>
											</template>
										</el-table-column>

										<el-table-column label="操作" align="center">
											<template slot-scope="scope">
												<el-button @click="handleClick(scope.row)" type="primary" round size="small">下载</el-button>

												<el-button type="danger" round @click="handleDelClick(scope.row.nid)" size="small">删除</el-button>
											</template>
										</el-table-column>
									</el-table>
								</div>
							</el-card>
						</el-form-item>
					</el-form>
					<div v-if="props.row.children.length == 0" class="empty-box">
						<el-empty description="当前商家未添加招聘信息"></el-empty>
					</div>
				</template>
			</el-table-column>

			<el-table-column label="店名" align="center">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.bName }}</span>
				</template>
			</el-table-column>

			<el-table-column label="归属账户" align="center">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.username }}</span>
				</template>
			</el-table-column>

			<el-table-column label="负责人" align="center">
				<template slot-scope="scope">
					<el-tag size="medium">{{ scope.row.bHead }}</el-tag>
				</template>
			</el-table-column>

			<el-table-column label="地址" align="center">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.bAddress }}</span>
				</template>
			</el-table-column>

			<el-table-column label="店面" align="center">
				<template slot-scope="scope">
					<span style="margin-left: 10px">
						<!-- <img :src="scope.row.imageData" width="50" > -->
						<img :src="scope.row.bImage" width="50" class="image-avatar" @click="viewImage(scope.row.bImage)" />
					</span>
				</template>
			</el-table-column>
      <el-table-column label="认证状态" align="center">
        <template slot-scope="scope">
          <el-tag size="medium">{{ scope.row.bIdentity==0?'未认证':scope.row.bIdentity==1?'认证通过':'认证失败'}}</el-tag>
          <!-- <span style="margin-left: 10px">{{ scope.row.pid == 2?'学生':'商家' }}</span> -->
        </template>
      </el-table-column>


			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
					<el-button size="mini" type="danger" @click="handleEditDelete(scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<div class="block">
			<!-- 每页多少条 -->
			<!-- 当前第几页 -->
			<!-- 用户选择一页选择多少条的数组 -->
			<!-- 当前默认一页多少条 -->
			<!-- 总条数 -->
			<el-pagination
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
				:current-page.sync="currentpage"
				:page-sizes="[3, 5, 10, 20]"
				:page-size="pagesize"
				layout="sizes, prev, pager, next"
				:total="total"
			>
			</el-pagination>
      <!-- 图片放大查看 -->
      <el-dialog :visible.sync="IdendialogVisible">
        <img :src="dialogImageUrl" width="100%" />
      </el-dialog>
		</div>

		<el-dialog :visible.sync="dialogVisible" width="30%" :before-close="handleEditDigClose">
			<el-form :model="shopEditForm" :rules="rules" class="edit_form" ref="shopEditFormRef">
				<h2>修改商店信息</h2>
				<el-form-item prop="bName">
					<el-input v-model="shopEditForm.bName" placeholder="店名" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>

				<el-form-item prop="mobile">
					<el-input v-model="shopEditForm.bHead" placeholder="负责人" prefix-icon="el-icon-phone"></el-input>
				</el-form-item>
				<el-form-item prop="email">
					<el-input v-model="shopEditForm.bAddress" placeholder="地址" prefix-icon="el-icon-eleme"></el-input>
				</el-form-item>
				<div style="padding-bottom: 15px">店铺图片:</div>
				<el-upload class="avatar-uploader" :action="''" :auto-upload="false" :show-file-list="false" :on-change="handleAvatarChangeIcon">
					<img v-if="shopEditForm.bImage" :src="shopEditForm.bImage" class="edit-avatar" />
					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
			</el-form>

			<span slot="footer" class="dialog-footer">
				<el-button type="info" @click="resetEditForm">重 置</el-button>
				<el-button type="primary" @click="excuteEditForm">修 改</el-button>
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
				<el-button type="info" @click="draResetEditForm">重 置</el-button>
				<el-button type="primary" @click="excuteDraEditForm">修 改</el-button>
			</span>
		</el-drawer>
	</div>
</template>

<script>
import { shopShowAPI, shopEditAPI, shopDelAPI, shopSearchAPI, editRecruitmentInfo, deleteRecruitmentInfo, delNotesByRecAPI } from '@/api/AxiosAPI';
export default {
	name: 'MyShopManage',
	created() {
		this.getShopList();
	},
	data() {
		return {
			drawer: false,
			direction: 'rtl',
			imageData: '',
			shopList: [],
			search: '',
			shopEditForm: {},
			loading: true,
			dialogVisible: false,
      IdendialogVisible:false,
      dialogImageUrl: '',
			editRecruitmentInfoForm: {
				recId: null,
				recObj: null,
				recSalary: null,
				recStation: null,
				recInfo: null,
				topHeat: null,
			},
			// 表单规则
			rules: {
				bName: [
					{ required: true, message: '请输入商店名', trigger: 'blur' },
					{
						min: 2,
						max: 13,
						message: '长度在 2 到 13 个字符',
						trigger: 'blur',
					},
				],
				bHead: [
					{ required: true, message: '请输入负责人姓名', trigger: 'blur' },
					{
						min: 2,
						max: 10,
						message: '长度在 2 到 10 个字符',
						trigger: 'blur',
					},
				],
				bAddress: [
					{ required: true, message: '请输入地址', trigger: 'blur' },
					{
						min: 4,
						max: 20,
						message: '长度在 4 到 20 个字符',
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
			total: 0,
			currentpage: 1,
			pagesize: 10,
		};
	},
	methods: {
    viewImage(url) {
      // 显示图片放大查看对话框
      this.dialogImageUrl = url;
      this.IdendialogVisible = true;
    },
		//删除简历
		async handleDelClick(nid) {
			this.$confirm('确认删除？')
				.then(async (_) => {
					const { data: res } = await delNotesByRecAPI(nid);
					if (res.code !== 200) return this.$message.error(res.message);
					this.$message.success(res.message);
					//更新表格
					this.getShopList();
					done();
				})
				.catch((_) => {});
		},
		async handleClick(row) {
			window.open(row.filepath);
		},
		handleSizeChange(val) {
			this.pagesize = val;
			this.getShopList();
		},
		handleCurrentChange(val) {
			this.currentpage = val;
			this.getShopList();
		},
		async getShopList() {
			const { data: res } = await shopShowAPI(this.currentpage, this.pagesize);
			if (res.code !== 200) return this.$message.error(res.message);

			this.shopList = res.data.records;
			this.total = res.data.total;
			this.loading = false;
			// this.$message.success(res.message);
		},
		//修改的执行API
		excuteDraEditForm() {
			this.$refs.RecruitmentEditFormRef.validate(async (valid) => {
				if (!valid) return;
				const { data: res } = await editRecruitmentInfo(this.editRecruitmentInfoForm);
				if (res.code !== 200) return this.$message.error(res.message);
				this.drawer = false;
				this.draResetEditForm();
				this.getShopList();
				this.$message.success(res.message);
			});
		},
		//修改的重置操作
		draResetEditForm() {
			this.editRecruitmentInfoForm = {};
		},
		//删除执行
		async delRecruitmentInfoBtn(item) {
			this.$confirm('确认删除？')
				.then(async (_) => {
					const { data: res } = await deleteRecruitmentInfo(item.recId);
					if (res.code !== 200) return this.$message.error(res.message);
					this.getShopList();
					this.$message.success(res.message);
					done();
				})
				.catch((_) => {});
		},
		//修改执行
		editRecruitmentInfoBtn(item) {
			this.editRecruitmentInfoForm = JSON.parse(JSON.stringify(item));
			this.drawer = true;
		},
		//修改页面关闭的
		handleDrawerClose(done) {
			this.$confirm('确认关闭？')
				.then((_) => {
					done();
					draResetEditForm();
				})
				.catch((_) => {});
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
				This.shopEditForm.bImage = this.result;
			};
		},

		// 搜索框
		async searchHandler() {
			if (this.search.trim() == '' || this.search == null) return this.$message.error('请输入正确的值！');
			const { data: res } = await shopSearchAPI(this.currentpage, this.pagesize, this.search);
			if (res.code !== 200) return this.$message.error(res.message);
			this.shopList = res.data.records;
			this.total = res.data.total;
			this.search = '';
			this.$message.success(res.message);
		},
		// 编辑按钮
		handleEdit(row) {
			// 深克隆
			this.shopEditForm = JSON.parse(JSON.stringify(row));
			this.dialogVisible = true;
		},
		// 删除按钮
		handleEditDelete(row) {
			this.$confirm('确认删除？')
				.then(async (_) => {
					const { data: res } = await shopDelAPI(row.bid);
					if (res.code !== 200) return this.$message.error(res.message);
					this.getShopList();
					this.$message.success(res.message);

					done();
				})
				.catch((_) => {});
		},
		// 对话框退出按钮
		handleEditDigClose(done) {
			this.$confirm('确认关闭？')
				.then((_) => {
					this.shopEditForm = {};
					done();
					resetEditForm();
				})
				.catch((_) => {});
		},
		//重置
		resetEditForm() {
			this.shopEditForm = {};
			this.$refs.editShopUpRef.clearFiles();
		},
		// 修改提交操作
		excuteEditForm() {
			this.$refs.shopEditFormRef.validate(async (valid) => {
				if (!valid) return;
				const { data: res } = await shopEditAPI(this.shopEditForm);
				if (res.code !== 200) return this.$message.error(res.message);
				this.dialogVisible = false;
				//刷新
				this.getShopList();
				this.$message.success(res.message);
			});
		},
		//上传图片变化钩子
		onChange(file, fileList) {
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
				This.shopEditForm.bImage = this.result;
			};
		},
		// 覆盖默认的上传行为，可以自定义上传的实现
		httpRequest() {},
	},
};
</script>

<style lang="less" scoped>
/deep/.el-form-item__content {
	width: 100%;
}
.main {
}
.block {
	padding: 20px 0;
}
.box-card:hover {
	top: -2px;
	right: -2px;
	border: #ebdede 1px solid;
	background-color: rgb(229, 236, 238);
	cursor: pointer;
}

/deep/.el-textarea__inner {
	height: 500px;
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
.expand-item {
	display: flex;
}

.image-avatar {
	width: 150px;
	height: 100px;
}
.edit-avatar {
	width: 300px;
	height: 200px;
	border: #00d9ff 2px dashed;
}
.box-card {
	width: 100%;
	margin: 15px;
	transition: all 1s linear;
	.b-tag {
		font-weight: bold;
		font-size: 20px;
	}
}

.recruitment-container {
	/deep/.custom-html {
		white-space: pre-wrap;
		width: 1200px;
	}
}

.empty-box {
	border-radius: 10px;
	width: 100%;
	margin: 15px;
	border: #e9dfdf 1px solid;
	p {
		color: #000;
		font-size: 18px;
	}
}
</style>
