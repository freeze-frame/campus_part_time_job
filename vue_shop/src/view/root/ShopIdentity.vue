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
						<el-button type="info" @click="getShopIdenList">重置</el-button>
					</div>
				</el-form-item>
			</el-form>
		</el-col>

		<el-table :data="shopIdenList" style="width: 100%" border v-loading="loading" >
      <el-table-column label="商店名称" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.bName }}</span>
        </template>
      </el-table-column>
			<el-table-column label="负责人" align="center">
				<template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.bHead }}</span>
				</template>
			</el-table-column>
			<el-table-column label="商店认证图" align="center">
				<template slot-scope="scope">
					<span style="margin-left: 10px">
						<!-- <img :src="scope.row.imageData" width="50" > -->
						<img :src="scope.row.bIdentityImage" width="50" class="image-avatar" @click="viewImage(scope.row.bIdentityImage)"/>
					</span>
				</template>
			</el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="identityPass(scope.row)" style="background-color: bisque ">认证通过</el-button>
          <el-button size="mini" @click="identityFail(scope.row)" style="background-color: bisque ">认证失败</el-button>
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
      <el-dialog :visible.sync="dialogImVisible">
        <img :src="dialogImageUrl" width="100%" />
      </el-dialog>
		</div>
	</div>
</template>

<script>
import {
  shopSearchAPI,
  shopIdenShowAPI,
  editShopInfo, editShopIdenInfo,
} from '@/api/AxiosAPI';
export default {
	name: 'MyShopManage',
	created() {
		this.getShopIdenList();
	},
	data() {
		return {
			drawer: false,
			direction: 'rtl',
			imageData: '',
			shopIdenList: [],
			search: '',
			shopEditForm: {},
			loading: true,
			dialogVisible: false,
      dialogImVisible: false,
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
    // 显示图片放大查看对话框
    viewImage(url) {
      this.dialogImageUrl = url;
      this.dialogImVisible = true;
    },

		async handleClick(row) {
			window.open(row.filepath);
		},
		handleSizeChange(val) {
			this.pagesize = val;
			this.getShopIdenList();
		},
		handleCurrentChange(val) {
			this.currentpage = val;
			this.getShopIdenList();
		},
		async getShopIdenList() {
			const { data: res } = await shopIdenShowAPI(this.currentpage, this.pagesize);
			if (res.code !== 200) return this.$message.error(res.message);
			this.shopIdenList = res.data.records;
			this.total = res.data.total;
			this.loading = false;
			// this.$message.success(res.message);
		},
		// 搜索框
		async searchHandler() {
			if (this.search.trim() == '' || this.search == null) return this.$message.error('请输入正确的值！');
			const { data: res } = await shopSearchAPI(this.currentpage, this.pagesize, this.search);
			if (res.code !== 200) return this.$message.error(res.message);
			this.shopIdenList = res.data.records;
			this.total = res.data.total;
			this.search = '';
			this.$message.success(res.message);
		},
    async identityPass(row) {
      row.bIdentity = 1;
      await this.updateIdentification(row);
    },

    async identityFail(row) {
      row.bIdentity = 2;
      await this.updateIdentification(row);
    },
    async updateIdentification(updatedRow) {
      // 调用后端API更新identification值
      const { data: res } = await editShopIdenInfo(updatedRow);
      if (res.code !== 200) {
        return this.$message.error(res.message);
      }
      this.$message.success(res.message);
      // 刷新shopIdenList
      await this.getShopIdenList();
    },
    // 对话框退出按钮
    handleEditDigClose(done) {
      this.$confirm('确认关闭？')
          .then((_) => {
            this.shopIdenList= {};
            done();
            resetEditForm();
          })
          .catch((_) => {});
    },
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
