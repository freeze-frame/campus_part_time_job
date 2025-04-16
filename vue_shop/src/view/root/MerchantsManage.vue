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
						<el-button type="info" @click="getBusinessList">重置</el-button>
					</div>
				</el-form-item>
			</el-form>
		</el-col>

		<el-table :data="businessList" style="width: 100%" border v-loading="loading">
			<el-table-column label="#" type="index" width="100" align="center"> </el-table-column>

      <el-table-column label="账号"align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px " >{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名"align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.realname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="年龄" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.age }}</span>
        </template>
      </el-table-column>

      <el-table-column label="联系电话"align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.mobile }}</span>
        </template>
      </el-table-column>

      <el-table-column label="邮箱"align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.email }}</span>
        </template>
      </el-table-column>

      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>

      <el-table-column label="身份" align="center">
        <template slot-scope="scope">
          <el-tag size="medium">{{ scope.row.pid == 2 ? '学生' : '商家' }}</el-tag>
          <!-- <span style="margin-left: 10px">{{ scope.row.pid == 2?'学生':'商家' }}</span> -->
        </template>
      </el-table-column>
      <el-table-column label="认证状态" align="center">
        <template slot-scope="scope">
          <el-tag size="medium">{{ scope.row.identification==0?'未认证':scope.row.identification==1?'认证通过':'认证失败'}}</el-tag>
          <!-- <span style="margin-left: 10px">{{ scope.row.pid == 2?'学生':'商家' }}</span> -->
        </template>
      </el-table-column>
      <el-table-column label="头像"align="center">
        <template slot-scope="scope">
					<span style="margin-left: 10px">
						<!-- <img :src="scope.row.imageData" width="50" > -->
						<img :src="scope.row.imageData" width="50" title="点击查看图片" @click="viewImage(scope.row.imageData)"/>
					</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center">
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
      <el-dialog :visible.sync="imageDialogVisible">
        <img :src="dialogImageUrl" width="100%" />
      </el-dialog>
		</div>

		<el-dialog :visible.sync="dialogVisible" width="30%" :before-close="handleEditDigClose">
			<el-form :model="businessEditForm" :rules="rules" class="edit_form" ref="businessEditFormRef">
				<h2>修改商店信息</h2>
        <!-- 账号 -->
        <el-form-item prop="username" label="账号">
          <el-input v-model="businessEditForm.username" placeholder="账号" prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>
        <!-- 用户名 -->
        <el-form-item prop="realname" label="用户名">
          <el-input v-model="businessEditForm.realname" placeholder="用户名" prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>

        <!-- 年龄 -->
        <el-form-item prop="age" label="年龄">
          <el-input v-model="businessEditForm.age" placeholder="年龄" prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>

        <!-- 手机号 -->
        <el-form-item prop="mobile" label="手机号码">
          <el-input v-model="businessEditForm.mobile" placeholder="手机号码" prefix-icon="el-icon-phone"></el-input>
        </el-form-item>
        <!-- 邮箱 -->
        <el-form-item prop="email" label="邮箱地址">
          <el-input v-model="businessEditForm.email" placeholder="邮箱地址" prefix-icon="el-icon-eleme"></el-input>
        </el-form-item>

        <el-form-item>
          <template>
            <el-select v-model="businessEditForm.pid" placeholder="请选择">
              <el-option v-for="item in pidOptions" :key="item.value" :label="item.label" :value="item.value"> </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item>
          <template>
            <el-select v-model="businessEditForm.identification" placeholder="请选择">
              <el-option v-for="item in idenOptions" :key="item.value" :label="item.label" :value="item.value"> </el-option>
            </el-select>
          </template>
        </el-form-item>
        <div style="padding-bottom: 15px">头像:</div>
        <el-upload class="avatar-uploader" :action="''" :auto-upload="false" :show-file-list="false" :on-change="handleAvatarChangeIcon">
          <img v-if="businessEditForm.imageData" :src="businessEditForm.imageData" class="edit-avatar" title="点击图片修改" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form>


      <span slot="footer" class="dialog-footer">
				<el-button type="info" @click="resetEditForm">重 置</el-button>
				<el-button type="primary" @click="excuteEditForm">修 改</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
import { businessShowAPI, businessEditAPI, businessDelAPI, businessSearchAPI } from '@/api/AxiosAPI';

export default {
	created() {
		this.getBusinessList();
	},
	name: 'MyMerchantsManage',
	data() {
		return {
      pidOptions: [
        {
          value: 2,
          label: '学生',
        },
        {
          value: 3,
          label: '商户',
        },
      ],
      idenOptions:[
        {
          value: 0,
          label: '未认证',
        },
        {
          value: 1,
          label: '认证通过',
        },
        {
          value: 2,
          label: '认证失败',
        },
      ],
			imageData: '',
			businessList: [],
			search: '',
			businessEditForm: {},
			loading: true,
			dialogVisible: false,
      imageDialogVisible:false,
      dialogImageUrl:'',

			// 表单规则
			rules: {
				username: [
					{ required: true, message: '请输入账号', trigger: 'blur' },
					{
						min: 4,
						max: 13,
						message: '长度在 4 到 13 个字符',
						trigger: 'blur',
					},
				],
				mobile: [
					// 添加正则表达式 pattern: /^1[3|5|7|8|9]\d{9}$/，验证手机号是否正确
					{ required: true, message: '请输入手机号', trigger: 'change' },
					{
						pattern: /^1[3|5|7|8|9]\d{9}$/,
						message: '请输入正确的号码格式',
						trigger: 'change',
					},
				],
				email: [
					{ required: true, message: '请输入邮箱地址', trigger: 'blur' },
					{
						type: 'email',
						message: '请输入正确的邮箱地址',
						trigger: ['blur', 'change'],
					},
				],
				age: [
					{
						pattern: /^(?:[1-9][0-9]?|1[01][0-9]|120)$/,
						required: true,
						message: '请输入1~120',
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
      this.imageDialogVisible = true;
    },
		handleSizeChange(val) {
			this.pagesize = val;
			this.getBusinessList();
		},
		handleCurrentChange(val) {
			this.currentpage = val;
			this.getBusinessList();
		},
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
				This.businessEditForm.imageData = this.result;
			};
		},
		async getBusinessList() {
			const { data: res } = await businessShowAPI(this.currentpage, this.pagesize);
			if (res.code !== 200) return this.$message.error(res.message);
			this.businessList = res.data.records;
			this.total = res.data.total;
			this.loading = false;
			// this.$message.success(res.message);
		},
		// 搜索框
		async searchHandler() {
			if (this.search.trim() == '' || this.search == null) return this.$message.error('请输入正确的值！');
			const { data: res } = await businessSearchAPI(this.currentpage, this.pagesize, this.search);
			if (res.code !== 200) return this.$message.error(res.message);
			this.businessList = res.data.records;
			this.total = res.data.total;
			this.search = '';
			this.$message.success(res.message);
		},
		// 编辑按钮
		handleEdit(row) {
			// 深克隆
			this.businessEditForm = JSON.parse(JSON.stringify(row));
			this.dialogVisible = true;
		},
    // 对话框退出按钮
    handleEditDigClose(done) {
      this.$confirm('确认关闭？')
          .then((_) => {
            this.businessEditForm = {};
            done();
            resetEditForm();
          })
          .catch((_) => {});
    },
		// 删除按钮
		handleEditDelete(row) {
			this.$confirm('确认删除？')
				.then(async (_) => {
					const { data: res } = await businessDelAPI(row.uid);
					if (res.code !== 200) return this.$message.error(res.message);
					this.getBusinessList();
					this.$message.success(res.message);

					done();
				})
				.catch((_) => {});
		},
		//重置
		resetEditForm() {
			this.businessEditForm = {};
			this.$refs.editStuUpRef.clearFiles();
		},
		// 修改提交操作
		excuteEditForm() {
			this.$refs.businessEditFormRef.validate(async (valid) => {
				if (!valid) return;
				const { data: res } = await businessEditAPI(this.businessEditForm);
				if (res.code !== 200) return this.$message.error(res.message);
				this.dialogVisible = false;
				//刷新
				this.getBusinessList();
				this.$message.success(res.message);
			});
		},
	},
};
</script>

<style lang="less" scoped>
.edit-avatar {
	width: 300px;
	height: 300px;
	border: #d2dddf 2px dashed;
}
.block {
	padding: 20px 0;
}
</style>
