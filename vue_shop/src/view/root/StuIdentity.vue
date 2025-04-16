<template>
	<div class="myStuManage_container">
		<el-col :span="19">
			<el-form :inline="true">
				<el-form-item label="用户名">
					<el-input v-model="search" placeholder="用户名关键字"></el-input>
				</el-form-item>
				<el-form-item>
					<div style="text-align: right">
						<!--自定义searchHandler函数-->
						<el-button type="primary" @click="searchHandler">查询</el-button>
						<el-button type="info" @click="getstuList">重置</el-button>
					</div>
				</el-form-item>
			</el-form>
		</el-col>

		<el-table :data="stuList" style="width: 100%" border v-loading="loading">
			<el-table-column label="#" type="index" width="100" align="center"> </el-table-column>

			<el-table-column label="账号" align="center">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.username }}</span>
				</template>
			</el-table-column>

      <el-table-column label="用户名"align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.realname }}</span>
        </template>
      </el-table-column>

      <el-table-column label="认证状态" align="center">
        <template slot-scope="scope">
          <el-tag size="medium">{{ scope.row.identification==0?'未认证':scope.row.identification==1?'认证通过':'认证失败'}}</el-tag>
          <!-- <span style="margin-left: 10px">{{ scope.row.pid == 2?'学生':'商家' }}</span> -->
        </template>
      </el-table-column>
			<el-table-column label="认证图" align="center">
				<template slot-scope="scope">
					<span style="margin-left: 10px">
						<img :src="scope.row.identityImage" width="50" title="点击查看图片"
                 @click="viewImage(scope.row.identityImage)"/>
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
      <el-dialog :visible.sync="dialogVisible">
        <img :src="dialogImageUrl" width="100%" />
      </el-dialog>
		</div>
	</div>
</template>

<script>
import { stuShowAPI,stuSearchAPI,stuEditAPI } from '@/api/AxiosAPI';
export default {
	created() {
		this.getstuList();
	},
	name: 'MyRootStuManage',
	data() {
		return {
      stuList: [], // 过滤后的学生列表
      loading: true, // 加载状态
			search: '',
			StuEditForm: {},
			dialogVisible: false,
      dialogImageUrl: '',
			total: 0, //总数据条数
			currentpage: 1, //当前所在页默认是第一页
			pagesize: 10, //每页显示多少行数据 默认设置为10


		};
	},
	methods: {
		handleSizeChange(val) {
			this.pagesize = val;
			this.getstuList();
		},

		handleCurrentChange(val) {
			this.currentpage = val;
			this.getstuList();
		},

    viewImage(url) {
      // 显示图片放大查看对话框
      this.dialogImageUrl = url;
      this.dialogVisible = true;
    },

		async getstuList() {
      const { data: res } = await stuShowAPI(this.currentpage, this.pagesize);
      if (res.code !== 200) {
        this.loading = false;
        return this.$message.error(res.message);
      }
      // 过滤出 identification 值为 0 的用户
      this.stuList = res.data.records.filter(stu => stu.identification === 0);
      this.total = res.data.total;
      this.loading = false;
		},
		// 搜索框
		async searchHandler() {
			if (this.search.trim() == '' || this.search == null) return this.$message.error('请输入正确的值！');
			const { data: res } = await stuSearchAPI(this.currentpage, this.pagesize, this.search);
			if (res.code !== 200) return this.$message.error(res.message);
			this.stuList = res.data.records;
			this.total = res.data.total;
			this.$message.success(res.message);

			this.search = '';
		},

    async identityPass(row) {
      row.identification = 1;
      await this.updateIdentification(row);
    },

    async identityFail(row) {
      row.identification = 2;
      await this.updateIdentification(row);
    },

    async updateIdentification(updatedRow) {
      // 调用后端API更新identification值
      const { data: res } = await stuEditAPI(updatedRow);
      if (res.code !== 200) {
        return this.$message.error(res.message);
      }
      this.$message.success(res.message);
      // 刷新stuList
      await this.getStuList();
    },

		// 对话框退出按钮
		handleEditDigClose(done) {
			this.$confirm('确认关闭？')
				.then((_) => {
					this.StuEditForm = {};
					done();
					resetEditForm();
				})
				.catch((_) => {});
		},
	},
  mounted() {
    // 在组件挂载后调用 getStuList 方法
    this.getStuList();
  }
};
</script>

<style lang="less" scoped>
.block {
	padding: 20px 0;
}
.edit-avatar {
	width: 300px;
	height: 300px;
	border: #d2dddf 2px dashed;
}
</style>
