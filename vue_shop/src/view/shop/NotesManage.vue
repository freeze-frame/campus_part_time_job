<template>
	<div class="myself-container">
		<el-card v-for="(item, i) in notesList" :key="i" class="box-card">
			<h3>招聘岗位：{{ item.recStation }}</h3>
			<el-table :data="item.children" border style="width: 100%">
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
						<span style="margin-left: 10px" v-if="scope.row.state > 2">
							<el-tooltip :content="scope.row.notesInfo" placement="bottom" effect="light">
								<el-tag style="cursor: pointer" :type="scope.row.state == 4 ? 'danger' : 'success'" effect="dark">
									{{ '已处理' }}
								</el-tag>
							</el-tooltip>
						</span>

						<el-button v-if="scope.row.state < 3" @click="handleClick(scope.row)" type="primary" round size="small">下载</el-button>
						<el-button v-if="scope.row.state < 3" type="success" round @click="handleExcClick(scope.row)" size="small">通 过</el-button>
						<el-button v-if="scope.row.state < 3" type="danger" round @click="handleOutClick(scope.row)" size="small">淘 汰</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>

		<div v-if="notesList.length == 0" class="empty-box">
			<el-empty description="当前商家未收到简历"></el-empty>
		</div>

		<el-dialog title="提示" :visible.sync="dialogVisible" width="30%" append-to-body :before-close="handleClose">
			<h3>添加通知</h3>
			<el-form :model="notesForm" :rules="rules" class="" ref="notisFormRef">
				<el-form-item prop="notesInfo">
					<el-input v-model="notesForm.notesInfo" placeholder="请输入通知信息" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>
			</el-form>

			<span slot="footer" class="dialog-footer">
				<el-button @click="closeFn">取 消</el-button>
				<el-button type="primary" @click="excuteFn">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
import { getMyAllNotesAPI, changeNotesInfoAndStateAPI, changeState } from '@/api/AxiosAPI';
export default {
	name: 'MyNotesManage',
	created() {
		this.getNotes();
	},
	data() {
		return {
			notesList: [],
			dialogVisible: null,
			notesForm: {
				nId: null,
				notesInfo: null,
				state: null,
			},
			rules: {
				notesInfo: [
					{ required: true, message: '请输入通知信息', trigger: 'blur' },
					{
						min: 2,
						max: 50,
						message: '长度在 2 到 50 个字符',
						trigger: 'blur',
					},
				],
			},
		};
	},
	methods: {
		//退出按钮
		closeFn() {
			this.notesForm = {
				nId: null,
				notesInfo: null,
				state: null,
			};
			this.dialogVisible = false;
		},
		//确定按钮
		async excuteFn() {
			this.$refs.notisFormRef.validate(async (valid) => {
				if (!valid) return;
				console.log(this.notesForm);
				const { data: res } = await changeNotesInfoAndStateAPI(this.notesForm);
				if (res.code !== 200) return this.$message.error(res.message);
				this.closeFn();
				this.getNotes();
			});
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
				.then((_) => {
					this.notesForm = {
						nId: null,
						notesInfo: null,
						state: null,
					};
					done();
				})
				.catch((_) => {});
		},
		handleOutClick(row) {
			this.notesForm.nId = row.nid;
			this.notesForm.state = 4;
			this.dialogVisible = true;
		},
		handleExcClick(row) {
			this.notesForm.nId = row.nid;
			this.notesForm.state = 3;
			this.dialogVisible = true;
		},
		async handleClick(row) {
			const { data: res } = await changeState(row.nid);
			if (res.code !== 200) return this.$message.error(res.message);
			window.open(row.filepath);
		},
		async getNotes() {
			const { data: res } = await getMyAllNotesAPI(sessionStorage.getItem('userUid'));
			if (res.code !== 200) return this.$message.error(res.message);
			this.notesList = res.data;
		},
	},
};
</script>

<style lang="less" scoped>
.myself-container {
	padding-top: 60px;
	width: 100%;

	height: 100%;
	.main {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		margin: 0 auto;
		width: 1240px;
		height: 100%;
	}
	.box-card {
		margin-bottom: 60px;
	}
}
</style>
