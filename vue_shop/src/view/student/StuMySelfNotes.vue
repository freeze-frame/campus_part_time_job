<!--
<template>
	<div class="myself-container">
		<div class="main">
			<div class="my">
				<h2>上传简历：</h2>
				<div class="notes">
					<el-upload
						accept=".pdf, .doc, .docx, .jpg, .png"
						class="upload-demo"
						action=""
						:limit="1"
						:auto-upload="false"
						:on-change="handleChange"
						:show-file-list="false"
						:before-upload="beforeUpload"
						:file-list="fileList"
						:on-success="handleFileUpload"
					>
						<el-button size="small" type="primary">点击上传</el-button>
						<div slot="tip" class="el-upload__tip">只能上传.pdf, .doc, .docx, .jpg, .png文件，且不超过5mb</div>
					</el-upload>
					<el-card class="box-card">
						<el-table :data="notesList" border style="width: 100%">
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
			</div>
		</div>
	</div>
</template>

<script>
import { stuUploadNotesAPI, getNotesAPI, downloadNotesAPI, stuDelNotesAPI } from '@/api/AxiosAPI';
export default {
	created() {
		this.getNotes();
	},
	data() {
		return {
			userToken: sessionStorage.getItem('token') || '',
			//线上数据
			notesList: [],
			//线下数据
			fileList: [],
		};
	},
	methods: {
		async handleClick(row) {
			window.open(row.filepath);
		},
		//上传成功后钩子
		handleFileUpload(reponse, file, fileList) {},
		//上传之前钩子
		beforeUpload(file) {
			const fileSuffix = file.name.substring(file.name.lastIndexOf('.') + 1);

			const whiteList = ['pdf', 'doc', 'docx'];

			if (whiteList.indexOf(fileSuffix) === -1) {
				this.$message.error('上传文件只能是 .pdf, .doc, .docx格式');
				return false;
			}

			const isLt5M = file.size / 1024 / 1024 < 5;

			if (!isLt5M) {
				this.$message.error('上传文件大小不能超过 5MB');
				return false;
			}
		},
		//删除简历
		async handleDelClick(row) {
			this.$confirm('确认删除？')
				.then(async (_) => {
					const { data: res } = await stuDelNotesAPI(row.uid, row.filename);
					if (res.code !== 200) return this.$message.error(res.message);
					this.$message.success(res.message);
					//更新表格
					this.getNotes();
					done();
				})
				.catch((_) => {});
		},
		//获取简历
		async getNotes() {
			const { data: res } = await getNotesAPI(sessionStorage.getItem('userUid'));
			if (res.code !== 200) {
				this.notesList = [];
				return this.$message.error(res.message);
			}
			this.notesList = res.data;
		},

		//上传简历
		async handleChange(file, fileList) {
			let fileData = new FormData();
			fileData.append('file', file.raw);
			const { data: res } = await stuUploadNotesAPI(sessionStorage.getItem('userUid'), fileData);
			if (res.code !== 200) return this.$message.error(res.message);
			this.$message.success(res.message);
			//获取成功制空
			this.fileList = [];
			//更新表格
			this.getNotes();
		},
	},
};
</script>

<style lang="less" scoped>
.myself-container {
	width: 100%;
	height: 100%;
	background-color: rgb(228, 233, 233);
	.main {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		margin: 0 auto;
		width: 1240px;
		height: 100%;

		.my {
			position: relative;
			display: flex;
			flex-direction: column;
			padding: 50px;
			width: 1200px;
			height: 500px;
			background-color: rgb(255, 255, 255);

			.op {
				padding-top: 50px;
				display: flex;
				flex-direction: row;
				justify-content: center;
				.btn {
					padding: 20px 100px;
					margin: 0 30px;
				}
			}
			.notes {
				height: 300px;
				.box-card {
					padding: 30px;
					margin: 20px 0;
				}
			}
		}
	}
}

a {
	text-decoration: none; /* 去除默认的下划线 */
	outline: none; /* 去除旧版浏览器的点击后的外虚线框 */
	color: #000; /* 去除默认的颜色和点击后变化的颜色 */
}
</style>
-->

<!--
<template>
  <div class="myself-container">
    <div class="main">
      <div class="my">
        <h2>上传简历：</h2>
        <div class="notes">
          <el-upload
              accept=".pdf, .doc, .docx, .jpg, .png"
              class="upload-demo"
              action=""
              :limit="1"
              :auto-upload="false"
              :on-change="handleChange"
              :show-file-list="false"
              :before-upload="beforeUpload"
              :file-list="fileList"
              :on-success="handleFileUpload"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传.pdf, .doc, .docx, .jpg, .png文件，且不超过5mb</div>
          </el-upload>
          <el-card class="box-card">
            <el-table :data="notesList" border style="width: 100%">
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
              <el-table-column label="文件类型">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.filetype }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                  <el-button @click="previewFile(scope.row)" type="primary" round size="small">预览</el-button>
                  <el-button @click="handleClick(scope.row)" type="primary" round size="small">下载</el-button>
                  <el-button type="danger" round @click="handleDelClick(scope.row)" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </div>
      </div>
    </div>
    <view-item-file :showTime="dialogVisible" :url="fileUrl" :type="fileType" @update:showTime="dialogVisible = false" />
  </div>
</template>


<script>
import { stuUploadNotesAPI, getNotesAPI, stuDelNotesAPI } from '@/api/AxiosAPI';
import ViewItemFile from '@/components/ViewItemFile.vue'; // 假设这个路径

export default {
  components: {
    ViewItemFile
  },
  created() {
    this.getNotes();
  },
  data() {
    return {
      userToken: sessionStorage.getItem('token') || '',
      notesList: [],
      fileList: [],
      dialogVisible: false,
      fileUrl: '',
      filetype: '',
      createTime:'',
      fileType:''
    };
  },
  methods: {
    handleClick(row) {
      window.open(row.filepath);
    },
  //上传成功后钩子
  handleFileUpload(reponse, file, fileList) {},
  //上传之前钩子
  beforeUpload(file) {
    const fileSuffix = file.name.substring(file.name.lastIndexOf('.') + 1);

    const whiteList = ['pdf', 'doc', 'docx',, 'jpg', 'png'];

    if (whiteList.indexOf(fileSuffix) === -1) {
      this.$message.error('上传文件只能是 .pdf, .doc, .docx,.jpg, .png 格式');
      return false;
    }

    const isLt5M = file.size / 1024 / 1024 < 5;

    if (!isLt5M) {
      this.$message.error('上传文件大小不能超过 5MB');
      return false;
    }
},
    async handleDelClick(row) {
      this.$confirm('确认删除？')
          .then(async (_) => {
            const { data: res } = await stuDelNotesAPI(row.uid, row.filename);
            if (res.code !== 200) return this.$message.error(res.message);
            this.$message.success(res.message);
            this.getNotes();
          })
          .catch((_) => {});
    },
    async getNotes() {
      const { data: res } = await getNotesAPI(sessionStorage.getItem('userUid'));
      if (res.code !== 200) return this.$message.error(res.message);
      this.notesList = res.data;
    },
    previewFile(row) {
      this.fileUrl = row.filepath;
      this.fileType = row.filetype;
      this.dialogVisible = true;
    },
    //上传简历
    async handleChange(file, fileList) {
      let fileData = new FormData();
      fileData.append('file', file.raw);
      const { data: res } = await stuUploadNotesAPI(sessionStorage.getItem('userUid'), fileData);
      if (res.code !== 200) return this.$message.error(res.message);
      this.$message.success(res.message);
      //获取成功制空
      this.fileList = [];
      //更新表格
      await this.getNotes();
    },
  }
};
</script>


<style lang="less" scoped>
.myself-container {
  width: 100%;
  height: 100%;
  background-color: rgb(228, 233, 233);
  .main {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 0 auto;
    width: 1240px;
    height: 100%;

    .my {
      position: relative;
      display: flex;
      flex-direction: column;
      padding: 50px;
      width: 1200px;
      height: 500px;
      background-color: rgb(255, 255, 255);

      .op {
        padding-top: 50px;
        display: flex;
        flex-direction: row;
        justify-content: center;
        .btn {
          padding: 20px 100px;
          margin: 0 30px;
        }
      }
      .notes {
        height: 300px;
        .box-card {
          padding: 30px;
          margin: 20px 0;
        }
      }
    }
  }
}

a {
  text-decoration: none; /* 去除默认的下划线 */
  outline: none; /* 去除旧版浏览器的点击后的外虚线框 */
  color: #000; /* 去除默认的颜色和点击后变化的颜色 */
}
</style>
-->
<!--
<template>
  <div class="myself-container">
    <div class="main">
      <div class="my">
        <h2>上传简历：</h2>
        <div class="notes">
          <el-upload
              accept=".pdf, .doc, .docx, .jpg, .png"
              class="upload-demo"
              action=""
              :limit="1"
              :auto-upload="false"
              :on-change="handleChange"
              :show-file-list="false"
              :before-upload="beforeUpload"
              :file-list="fileList"
              :on-success="handleFileUpload"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传.pdf, .doc, .docx, .jpg, .png文件，且不超过5mb</div>
          </el-upload>
          <el-card class="box-card">
            <el-table :data="notesList" border style="width: 100%">
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
              <el-table-column label="文件类型">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.filetype }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                  <el-button @click="previewFile(scope.row)" type="primary" round size="small">预览</el-button>
                  <el-button @click="handleClick(scope.row)" type="primary" round size="small">下载</el-button>
                  <el-button type="danger" round @click="handleDelClick(scope.row)" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </div>
      </div>
    </div>
    <div class="viewItemFile">
      <el-dialog
          width="70%"
          class="viewItemFileDialog"
          title="文件预览"
          v-model="dialogVisible"
          :before-close="handleClose"
          :close-on-click-modal="false"
          :close-on-press-escape="false"
      >
        &lt;!&ndash; 图片预览 &ndash;&gt;
        <div v-if="isImage">
          <img :src="fileUrl" style="display: block; max-width: 100%; margin: 0 auto;" alt="文件预览"/>
        </div>
        &lt;!&ndash; PDF预览 &ndash;&gt;
        <div v-if="isPdf">
          <iframe :src="fileUrl" type="application/pdf" width="100%" height="600px"></iframe>
        </div>
        &lt;!&ndash; DOCX预览 &ndash;&gt;
        <div v-if="isDocx" ref="docContainer"></div>
        &lt;!&ndash; XLSX预览 &ndash;&gt;
        <div v-if="isXlsx">
          <el-radio-group v-model="activeSheetName" @change="renderSheet">
            <el-radio-button v-for="(name, index) in sheetNames" :key="index" :label="name">{{ name }}</el-radio-button>
          </el-radio-group>
          <div v-html="sheetContent" style="margin-top: 10px; border: 1px solid #dcdcdc; padding: 10px;"></div>
        </div>
      </el-dialog>
    </div>
    <view-item-file :showTime="dialogVisible" :url="fileUrl" :type="fileType" @update:showTime="dialogVisible = false" />
  </div>
</template>


<script>
import { stuUploadNotesAPI, getNotesAPI, stuDelNotesAPI } from '@/api/AxiosAPI';
import ViewItemFile from '@/components/ViewItemFile.vue';
import {computed, ref, watch} from "vue";
import axios from "axios";
import * as docx from "docx-preview";
import XLSX from "xlsx"; // 假设这个路径

const props = defineProps({
  showTime: Boolean,
  url: String,
  type: String,
});
const emits = defineEmits(['update:showTime']);
const dialogVisible = ref(props.showTime);
const fileUrl = ref(props.url);
const fileType = ref(props.type);

const isImage = computed(() => ['jpg', 'jpeg', 'png'].includes(fileType.value.toLowerCase()));
const isPdf = computed(() => fileType.value.toLowerCase() === 'pdf');
const isDocx = computed(() => fileType.value.toLowerCase() === 'docx');
const isXlsx = computed(() => fileType.value.toLowerCase() === 'xlsx');

const sheetNames = ref([]);
const activeSheetName = ref('');
const sheetContent = ref('');

watch(dialogVisible, (newVal) => {
  if (!newVal) {
    emits('update:showTime', false);
  }
});

watch(fileUrl, async (newUrl) => {
  if (isDocx.value) {
    await renderDocx(newUrl);
  } else if (isXlsx.value) {
    await loadXlsx(newUrl);
  }
});

const renderDocx = async (url) => {
  const response = await axios.get(url, { responseType: 'blob' });
  const container = document.createElement('div');
  await docx.renderAsync(response.data, container);
  document.querySelector('.viewItemFileDialog .el-dialog__body').appendChild(container);
};
const loadXlsx = async (url) => {
  const response = await axios.get(url, { responseType: 'arraybuffer' });
  const workbook = XLSX.read(new Uint8Array(response.data), { type: 'array' });
  sheetNames.value = workbook.SheetNames;
  activeSheetName.value = sheetNames.value[0];
  await renderSheet(activeSheetName.value, workbook);
};

const renderSheet = async (sheetName, workbook) => {
  const sheet = workbook.Sheets[sheetName];
  const html = XLSX.utils.sheet_to_html(sheet);
  sheetContent.value = html;
};

function handleClose() {
  dialogVisible.value = false;
};
export default {
  components: {
    ViewItemFile
  },
  created() {
    this.getNotes();
  },
  data() {
    return {
      userToken: sessionStorage.getItem('token') || '',
      notesList: [],
      fileList: [],
      dialogVisible: false,
      fileUrl: '',
      filetype: '',
      createTime:'',
      fileType:''
    };
  },
  methods: {
    handleClick(row) {
      window.open(row.filepath);
    },
    //上传成功后钩子
    handleFileUpload(reponse, file, fileList) {},
    //上传之前钩子
    beforeUpload(file) {
      const fileSuffix = file.name.substring(file.name.lastIndexOf('.') + 1);

      const whiteList = ['pdf', 'doc', 'docx',, 'jpg', 'png'];

      if (whiteList.indexOf(fileSuffix) === -1) {
        this.$message.error('上传文件只能是 .pdf, .doc, .docx,.jpg, .png 格式');
        return false;
      }

      const isLt5M = file.size / 1024 / 1024 < 5;

      if (!isLt5M) {
        this.$message.error('上传文件大小不能超过 5MB');
        return false;
      }
    },
    async handleDelClick(row) {
      this.$confirm('确认删除？')
          .then(async (_) => {
            const { data: res } = await stuDelNotesAPI(row.uid, row.filename);
            if (res.code !== 200) return this.$message.error(res.message);
            this.$message.success(res.message);
            this.getNotes();
          })
          .catch((_) => {});
    },
    async getNotes() {
      const { data: res } = await getNotesAPI(sessionStorage.getItem('userUid'));
      if (res.code !== 200) return this.$message.error(res.message);
      this.notesList = res.data;
    },
    previewFile(row) {
      this.fileUrl = row.filepath;
      this.fileType = row.filetype;
      this.dialogVisible = true;
    },
    //上传简历
    async handleChange(file, fileList) {
      let fileData = new FormData();
      fileData.append('file', file.raw);
      const { data: res } = await stuUploadNotesAPI(sessionStorage.getItem('userUid'), fileData);
      if (res.code !== 200) return this.$message.error(res.message);
      this.$message.success(res.message);
      //获取成功制空
      this.fileList = [];
      //更新表格
      await this.getNotes();
    },



    }}
</script>


<style lang="less" scoped>
.myself-container {
  width: 100%;
  height: 100%;
  background-color: rgb(228, 233, 233);
  .main {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 0 auto;
    width: 1240px;
    height: 100%;

    .my {
      position: relative;
      display: flex;
      flex-direction: column;
      padding: 50px;
      width: 1200px;
      height: 500px;
      background-color: rgb(255, 255, 255);

      .op {
        padding-top: 50px;
        display: flex;
        flex-direction: row;
        justify-content: center;
        .btn {
          padding: 20px 100px;
          margin: 0 30px;
        }
      }
      .notes {
        height: 300px;
        .box-card {
          padding: 30px;
          margin: 20px 0;
        }
      }
    }
  }
}

a {
  text-decoration: none; /* 去除默认的下划线 */
  outline: none; /* 去除旧版浏览器的点击后的外虚线框 */
  color: #000; /* 去除默认的颜色和点击后变化的颜色 */
}
.viewItemFileDialog {
  max-width: 80%;
}
</style>-->

<template>
  <div class="myself-container">
    <div class="main">
      <div class="my">
        <h2>上传简历：</h2>
        <div class="notes">
          <el-upload
              accept=".pdf, .doc, .docx, .jpg, .png"
              class="upload-demo"
              action=""
              :limit="1"
              :auto-upload="false"
              :on-change="handleChange"
              :show-file-list="false"
              :before-upload="beforeUpload"
              :file-list="fileList"
              :on-success="handleFileUpload"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传.pdf, .doc, .docx, .jpg, .png文件，且不超过5mb</div>
          </el-upload>
          <el-card class="box-card">
            <el-table :data="notesList" border style="width: 100%">
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
              <el-table-column label="文件类型">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.filetype }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                  <el-button @click="previewFile(scope.row)" type="primary" round size="small">预览</el-button>
                  <el-button @click="handleClick(scope.row)" type="primary" round size="small">下载</el-button>
                  <el-button type="danger" round @click="handleDelClick(scope.row)" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </div>
      </div>
    </div>
    <div class="viewItemFile">
      <el-dialog
          width="70%"
          class="viewItemFileDialog"
          title="文件预览"
          v-model="dialogVisible"
          :before-close="handleClose"
          :close-on-click-modal="false"
          :close-on-press-escape="false"
      >
        <!-- 图片预览 -->
        <div v-if="isImage">
          <img :src="fileUrl" style="display: block; max-width: 100%; margin: 0 auto;" alt="文件预览"/>
        </div>
        <!-- PDF预览 -->
        <div v-if="isPdf">
          <iframe :src="fileUrl" type="application/pdf" width="100%" height="600px"></iframe>
        </div>
        <!-- DOCX预览 -->
        <div v-if="isDocx" ref="docContainer"></div>
        <!-- XLSX预览 -->
        <div v-if="isXlsx">
          <el-radio-group v-model="activeSheetName" @change="renderSheet">
            <el-radio-button v-for="(name, index) in sheetNames" :key="index" :label="name">{{ name }}</el-radio-button>
          </el-radio-group>
          <div v-html="sheetContent" style="margin-top: 10px; border: 1px solid #dcdcdc; padding: 10px;"></div>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { stuUploadNotesAPI, getNotesAPI, stuDelNotesAPI } from '@/api/AxiosAPI';
import axios from 'axios';
import * as docx from 'docx-preview';

export default {
  data() {
    return {
      userToken: sessionStorage.getItem('token') || '',
      notesList: [],
      fileList: [],
      dialogVisible: false,
      fileUrl: '',
      fileType: '',
      sheetNames: [],
      activeSheetName: '',
      sheetContent: '',
      filetype:'',
    };
  },
  computed: {
    isImage() {
      return ['jpg', 'jpeg', 'png'].includes(this.fileType.toLowerCase());
    },
    isPdf() {
      return this.fileType.toLowerCase() === 'pdf';
    },
    isDocx() {
      return this.fileType.toLowerCase() === 'docx';
    },
    isXlsx() {
      return this.fileType.toLowerCase() === 'xlsx';
    }
  },
  created() {
    this.getNotes();
  },
  methods: {
    handleClick(row) {
      window.open(row.filepath);
    },
    handleFileUpload(response, file, fileList) {},
    beforeUpload(file) {
      const fileSuffix = file.name.substring(file.name.lastIndexOf('.') + 1).toLowerCase();
      const whiteList = ['pdf', 'docx', 'jpg', 'png'];

      if (!whiteList.includes(fileSuffix)) {
        this.$message.error('上传文件只能是 .pdf, .doc, .docx, .jpg, .png 格式');
        return false;
      }

      const isLt5M = file.size / 1024 / 1024 < 5;

      if (!isLt5M) {
        this.$message.error('上传文件大小不能超过 5MB');
        return false;
      }

      return true;
    },
    async handleDelClick(row) {
      this.$confirm('确认删除？')
          .then(async () => {
            const { data: res } = await stuDelNotesAPI(row.uid, row.filename);
            if (res.code !== 200) return this.$message.error(res.message);
            this.$message.success(res.message);
            this.getNotes();
          })
          .catch(() => {});
    },
    async getNotes() {
      const { data: res } = await getNotesAPI(sessionStorage.getItem('userUid'));
      if (res.code !== 200) return this.$message.error(res.message);
      this.notesList = res.data;
    },
    //预览文件
    previewFile(row) {
      this.fileUrl = row.filepath;
      this.fileType = row.filetype;
      this.dialogVisible = true;
      this.renderFile();
    },
    async handleChange(file, fileList) {
      let fileData = new FormData();
      fileData.append('file', file.raw);
      const { data: res } = await stuUploadNotesAPI(sessionStorage.getItem('userUid'), fileData);
      if (res.code !== 200) return this.$message.error(res.message);
      this.$message.success(res.message);
      this.fileList = [];
      await this.getNotes();
    },
    async renderFile() {
      if (this.isDocx) {
        await this.renderDocx(this.fileUrl);
      }
    },
    async renderDocx(url) {
      const response = await axios.get(url, { responseType: 'blob' });
      const container = document.createElement('div');
      await docx.renderAsync(response.data, container);
      this.$refs.docContainer.innerHTML = '';
      this.$refs.docContainer.appendChild(container);
    },

    async getWorkbook() {
      const response = await axios.get(this.fileUrl, { responseType: 'arraybuffer' });
      return XLSX.read(new Uint8Array(response.data), { type: 'array' });
    },
    handleClose() {
      this.dialogVisible = false;
    }
  }
};
</script>

<style lang="less" scoped>
.myself-container {
  width: 100%;
  height: 100%;
  background-color: rgb(228, 233, 233);
  .main {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 0 auto;
    width: 1240px;
    height: 100%;
    .my {
      position: relative;
      display: flex;
      flex-direction: column;
      padding: 50px;
      width: 1200px;
      height: 500px;
      background-color: rgb(255, 255, 255);
      .op {
        padding-top: 50px;
        display: flex;
        flex-direction: row;
        justify-content: center;
        .btn {
          padding: 20px 100px;
          margin: 0 30px;
        }
      }
      .notes {
        height: 300px;
        .box-card {
          padding: 30px;
          margin: 20px 0;
        }
      }
    }
  }
}

a {
  text-decoration: none; /* 去除默认的下划线 */
  outline: none; /* 去除旧版浏览器的点击后的外虚线框 */
  color: #000; /* 去除默认的颜色和点击后变化的颜色 */
}
.viewItemFileDialog {
  max-width: 80%;
}
</style>

