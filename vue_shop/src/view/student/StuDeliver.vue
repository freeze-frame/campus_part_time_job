<template>
	<div class="studeliver_container">
		<div class="main">
			<el-card v-if="myNotesList.length > 0" v-for="(item, i) in myNotesList" :key="i" class="box-card">
				<div slot="header" class="clearfix">
					<h3 style="display: inline-block">投递的岗位：{{ item.recStation }}</h3>
					<el-button @click="goRecInfo(item.recId)" style="float: right; font-size: 15px" type="text">查看详细</el-button>
					<el-button @click="delMyNotes(item.children[0])" style="float: right; font-size: 15px; padding-right: 10px" type="text">撤销投递</el-button>
				</div>

				<div style="margin: 30px 0" v-for="(notes, index) in item.children" :key="index">
					<el-steps :active-color="'#1511'" :active="notes.state" :finish-status="notes.state == 4 ? 'error ' : 'success'">
						<el-step title="已投递"></el-step>
						<el-step title="已查看"></el-step>
						<el-step style="border-color: red" :title="notes.state == 4 ? '未通过' : '已通过'"></el-step>
					</el-steps>

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

						<el-table-column label="商家回复" align="center">
							<template slot-scope="scope">
								<span style="margin-left: 10px" v-if="scope.row.state < 3">商家暂未回复</span>
								<span style="margin-left: 10px">{{ scope.row.notesInfo }}</span>
							</template>
						</el-table-column>
					</el-table>
				</div>
			</el-card>
			<div v-if="myNotesList.length == 0" class="empty-box">
				<el-empty description="该学生未投递过"></el-empty>
			</div>
		</div>
	</div>
</template>

<script>
import { getMyDeliverAPI, delNotesByRecAPI } from '@/api/AxiosAPI';
export default {
	name: 'StuComplaint',
	created() {
		this.getMyNotesFn();
	},
	data() {
		return {
			myNotesList: null,
		};
	},
	methods: {
		async delMyNotes(item) {
			const { data: res } = await delNotesByRecAPI(item.nid);
			if (res.code !== 200) return this.$message.error(res.message);
			this.$message.success(res.message);
			this.getMyNotesFn();
		},
		goRecInfo(recId) {
			this.$router.push({
				path: '/suthome/recshow/' + recId,
			});
		},
		async getMyNotesFn() {
			const { data: res } = await getMyDeliverAPI(sessionStorage.getItem('userUid'));
			if (res.code !== 200) return this.$message.error(res.message);
			this.myNotesList = res.data;
		},
	},
};
</script>

<style lang="less" scoped>
.studeliver_container {
	margin: 60px 0;
	width: 100%;
	.main {
		margin: 0 auto;
		width: 1240px;
		padding-bottom: 60px;

		/deep/ .el-card {
			margin: 30px 0;
		}
		/deep/ .el-table {
			margin-top: 30px;
		}
	}
}
</style>
