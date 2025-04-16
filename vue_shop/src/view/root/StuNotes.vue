<template>
	<div>
		<el-card class="box-card">
			<h3>学生简历</h3>
			<el-table :data="notesList" border style="width: 100%">
				<el-table-column label="账号">
					<template slot-scope="scope">
						<span style="margin-left: 10px">{{ scope.row.username }}</span>
					</template>
				</el-table-column>
				<el-table-column label="邮箱">
					<template slot-scope="scope">
						<span style="margin-left: 10px">{{ scope.row.email }}</span>
					</template>
				</el-table-column>
				<el-table-column label="手机号">
					<template slot-scope="scope">
						<span style="margin-left: 10px">{{ scope.row.mobile }}</span>
					</template>
				</el-table-column>
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

						<el-button type="danger" round @click="handleDelClick(scope.row)" size="small">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
	</div>
</template>

<script>
import { getStuNotesAPI, stuDelNotesAPI } from '@/api/AxiosAPI';
export default {
	name: 'MyRootStuNotesManage',
	created() {
		this.getStuNotes();
	},
	data() {
		return {
			notesList: [],
		};
	},
	methods: {
		//删除简历
		async handleDelClick(row) {
			this.$confirm('确认删除？')
				.then(async (_) => {
					const { data: res } = await stuDelNotesAPI(row.uid, row.filename);
					if (res.code !== 200) return this.$message.error(res.message);
					this.$message.success(res.message);
					//更新表格
					this.getStuNotes();
					done();
				})
				.catch((_) => {});
		},
		handleClick(row) {
			window.open(row.filepath);
		},
		async getStuNotes() {
			const { data: res } = await getStuNotesAPI();
			if (res.code !== 200) return this.$message.error(res.message);
			this.notesList = res.data;
		},
	},
};
</script>

<style></style>
