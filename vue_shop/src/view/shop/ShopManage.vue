<template>
	<div class="card-container">
		<img src="../../assets/3574.jpg" alt="" style="width: 1240px" />

		<div class="card-operate">
			<el-button type="primary" @click="addShopInfoFun">添加商店信息</el-button>
			<!-- <el-button type="primary">查看所有招聘</el-button> -->
		</div>

		<el-card v-if="userInfo" class="box-card" v-for="(item, index) in userInfo" :key="index">
			<div slot="header" class="clearfix">
				<span class="b-tag">
					{{ item.bName }}
				</span>
				<el-button style="font-size: 18px; float: right; padding: 3px 10px" type="text" @click="deleteShopInfoBtn(item)">删除信息</el-button>
				<el-button style="font-size: 18px; float: right; padding: 3px 10px" type="text" @click="gotoRecruitmentByBid(item.bid)">招聘信息</el-button>
				<el-button style="font-size: 18px; float: right; padding: 3px 10px" type="text" @click="handleEdit(item)">修改信息</el-button>
			</div>
			<div class="card-context">
        <div class="b-image"><img :src="item.bImage" alt="" /></div>
				<div class="b-info">
					<div class="text item"><i class="el-icon-user"></i> <span class="b-tag"> 负责人：</span>{{ item.bHead }}</div>
					<div class="text item"><i class="el-icon-phone"></i> <span class="b-tag"> 电话：</span>{{ item.mobile }}</div>
					<div class="text item"><i class="el-icon-info"></i> <span class="b-tag"> 店名：</span>{{ item.bName}}</div>
					<div class="text item">
						<i class="el-icon-s-home"></i> <span class="b-tag"> 地址：</span><span>{{ item.bAddress }}</span>
          </div>
          <div class="text item"><span class="b-tag"> 认证状态：</span><span>{{ item.bIdentity==0?"未认证":item.bIdentity==1?"认证通过":"认证失败" }}</span></div>
				</div>
        <div class="b-idenImage">商店认证图片<img :src="item.bIdentityImage" alt="" /></div>
			</div>

		</el-card>

		<div v-if="userInfo == null" class="empty-box">
			<el-empty description="当前商家未添加店铺信息"></el-empty>
		</div>

    <!--修改商店信息-->
    <el-dialog :visible.sync="dialogVisible" width="60%" :before-close="handleEditDigClose" append-to-body>
      <el-form :model="editShopInfoForm" :rules="rules" class="edit_form" ref="ShopEditFormRef">
        <h2>修改商店信息</h2>
<!--        &lt;!&ndash; 商店id &ndash;&gt;
        <el-form-item prop="bId" label="商店id：">
          <el-input v-model="editShopInfoForm.bId" placeholder=""></el-input>
        </el-form-item>-->
        <!-- 商店名称 -->
        <el-form-item prop="bName" label="商店名称：">
          <el-input v-model="editShopInfoForm.bName" placeholder=""></el-input>
        </el-form-item>
        <!-- 负责人 -->
        <el-form-item prop="bHead" label="负责人：">
          <el-input v-model="editShopInfoForm.bHead" placeholder="负责人"></el-input>
        </el-form-item>
        <!-- 地址 -->
        <el-form-item prop="bAddress" label="地址：">
          <el-input v-model="editShopInfoForm.bAddress" placeholder="地址"></el-input>
        </el-form-item>
        <!-- 商店图片 -->
        <el-form-item prop="bImage">
          <div class="active" @click="changeBImageFn">
            <span>商店图片:</span>
            <el-upload class="avatar-uploader" :action="''" :auto-upload="false" :show-file-list="false" :on-change="handleAvatarChangeIcon" :http-request="httpRequest">
              <img :src="editShopInfoForm.bImage" alt="" title="点击更换图片" />
            </el-upload>
          </div>
        </el-form-item>
        <!-- 商店认证图 -->
        <el-form-item prop="bIdentityImage">
          <div class="active" @click="changeBIdenImageFn">
            <span>商店认证图:</span>
            <el-upload class="avatar-uploader" :action="''" :auto-upload="false" :show-file-list="false" :on-change="handleIdenAvatarChangeIcon" :http-request="httpRequest">
              <img :src="editShopInfoForm.bIdentityImage" alt="" title="点击更换图片" />
            </el-upload>
          </div>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="excuteEditForm">修 改</el-button>
      <el-button type="primary" @click="resetEditForm">重置</el-button>
    </span>
    </el-dialog>

    <!--添加商店-->
		<el-dialog :visible.sync="addDialogVisible" width="30%" :before-close="handleAddDigClose" append-to-body>
			<el-form :model="addShopInfoForm" :rules="rules" class="addShop_form" ref="ShopAddFormRef">
				<h2>添加商店信息</h2>
				<!-- 商店名称 -->
				<el-form-item prop="bName">
					<el-input v-model="addShopInfoForm.bName" placeholder="店名" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>
        <!--负责人-->
				<el-form-item prop="bHead">
					<el-input v-model="addShopInfoForm.bHead" placeholder="负责人" prefix-icon="el-icon-eleme"></el-input>
				</el-form-item>
        <!-- 地址  -->
				<el-form-item prop="bAddress">
					<el-input v-model="addShopInfoForm.bAddress" placeholder="地址" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>
        <!-- 商店图片  -->
				<div style="padding-bottom: 15px">商店图片:</div>
				<el-upload class="upload-demo" drag ref="addShopUpRef" action=""
					file-list="fileList" :limit="1" :on-change="onChange" :http-request="httpRequest" >
					<i class="el-icon-upload"></i>
					<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
					<div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过1mb</div>
				</el-upload>
        <!-- 商店认证图  -->
        <div style="padding-bottom: 15px">商店认证图:</div>
        <el-upload class="upload-demo" drag ref="addShopIdenUpRef" action=""
                   file-list="IdenfileList" :limit="1" :on-change="onIdenChange" :http-request="httpRequest" >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过3mb</div>
        </el-upload>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button type="info" @click="resetAddForm">重 置</el-button>
				<el-button type="primary" @click="excuteAddForm">添 加</el-button>
			</span>
		</el-dialog>


	</div>
</template>

<script>
import {shopShowById, addShopInfo, editShopInfo, deleteShopInfo, getInfoAPI} from '@/api/AxiosAPI';
export default {
	created() {
		this.getShopById(sessionStorage.getItem('userUid'));
		this.addShopInfoForm.uid = sessionStorage.getItem('userUid');
	},
	name: 'MyShopManage',
	data() {
		return {
			editShopInfoForm: {
        uid:null,
        bId: null,
				bName: null,
				bImage: null,
				bHead: null,
				bAddress: null,
        bIdentityImage:null,
			},
      dialogVisible:false,
      fileList: [],
      IdenfileList: [],
			userInfo: null,
			addDialogVisible: false,
			addShopInfoForm: {
        bId:null,
				bName: null,
				bImage: null,
				bHead: null,
				bAddress: null,
        bIdentity:null,
        bIdentityImage:null,
			},
			drawer: false,
			direction: 'ltr',
			rules: {
        //商店名称规则
				bName: [
					{ required: true, message: '请输入商店名', trigger: 'blur' },
					{
						min: 2,
						max: 13,
						message: '长度在 2 到 13 个字符',
						trigger: 'blur',
					},
				],
        //负责人规则
				bHead: [
					{ required: true, message: '请输入负责人姓名', trigger: 'blur' },
					{
						min: 2,
						max: 10,
						message: '长度在 2 到 10 个字符',
						trigger: 'blur',
					},
				],
        //地址规则
				bAddress: [
					{ required: true, message: '请输入地址', trigger: 'blur' },
					{
						min: 4,
						max: 20,
						message: '长度在 4 到 20 个字符',
						trigger: 'blur',
					},
				],
			},
		};
	},
	methods: {
		//去看这个商店的招聘信息
		gotoRecruitmentByBid(bid) {
			sessionStorage.setItem('shopBid', bid);
			this.$router.push({
				path: 'shop/recruitment',
			});
		},
		//删除商店的
		async deleteShopInfoBtn(item) {
			this.$confirm('确认删除？')
				.then(async (_) => {
					const { data: res } = await deleteShopInfo(item.bid);
					if (res.code !== 200) return this.$message.error(res.message);
					this.getShopById(item.uid);
					this.$message.success(res.message);
					done();
				})
				.catch((_) => {});
		},

    //处理修改商店信息
    // 编辑按钮
    handleEdit(item) {
      this.editShopInfoForm.uid=item.uid;
      this.editShopInfoForm.bId=item.bid;
      this.editShopInfoForm.bName=item.bName;
      this.editShopInfoForm.bImage=item.bImage;
      this.editShopInfoForm.bHead=item.bHead;
      this.editShopInfoForm.bAddress=item.bAddress;
      this.editShopInfoForm.bIdentityImage=item.bIdentityImage;
      this.dialogVisible = true;
    },
    //对话框退出按钮
    handleEditDigClose(done) {
      this.$confirm("确认关闭？")
          .then(() => {
            this.editShopInfoForm = {
              bName: null,
              bHead: null,
              bImage: null,
              bAddress: null,
              bIdentityImage: null
            };
            this.fileList = [];
            this.IdenfileList = [];
            done();
          })
          .catch(() => {});
    },
    //重置修改信息的
    resetEditForm() {
      this.editShopInfoForm = {
        bName: null,
        bHead: null,
        bImage: null,
        bAddress: null,
        bIdentityImage: null
      };
      this.fileList = [];
      this.IdenfileList = [];
    },
    // 修改提交操作
    excuteEditForm() {
      this.$refs.ShopEditFormRef.validate(async (valid) => {
        if (!valid) return;
        try {
          const { data: res } = await editShopInfo(this.editShopInfoForm);
          if (res.code !== 200) return this.$message.error(res.message);
          await this.getShopById(this.editShopInfoForm.uid);
          this.dialogVisible = false;
          this.resetEditForm();
          this.$message.success(res.message);
        } catch (e) {
          console.error('API请求出错', e);
          this.$message.error('修改失败....');
        }
      });
    },
    //通过uid获取店铺信息
    async getShopById(id) {
      const { data: res } = await shopShowById(id);
      if (res.code !== 200) return this.$message.error(res.message);
      this.userInfo = res.data;
    },
    addShopInfoFun() {
      this.addDialogVisible = true;
    },


    //更换商店图片
    changeBImageFn() {
      this.userInfo.bImage = this.editShopInfoForm.bImage;
      /*this.editImgDialogVisible = true;*/
    },
    //修改页面图片上传和回显的
    handleAvatarChangeIcon(file) {
      const isJPGORPNG = file.raw.type === 'image/jpeg' || file.raw.type === 'image/png';
      const isLt1M = file.size / 1024 / 1024 < 1;

      if (!isJPGORPNG) {
        this.$message.info('上传图片只能是 JPG 或 PNG 格式!');
        return;
      }
      if (!isLt1M) {
        this.$message.info('上传图片大小不能超过 1MB!');
        return;
      }
      this.convertFileToBase64(file.raw).then(base64img => {
        this.editShopInfoForm.bImage = base64img;
      }).catch(error => {
        this.$message.error('图片转换失败!');
      });
    },

    //更换商店认证图片
    changeBIdenImageFn() {
      this.userInfo.bIdentityImage = this.editShopInfoForm.bIdentityImage;
    },
    handleIdenAvatarChangeIcon(file) {
      const isJPGORPNG = file.raw.type === 'image/jpeg' || file.raw.type === 'image/png';
      const isLt3M = file.size / 1024 / 1024 < 3;

      if (!isJPGORPNG) {
        this.$message.info('上传头像只能是 JPG 或 PNG 格式!');
        return;
      }
      if (!isLt3M) {
        this.$message.info('上传图片大小不能超过 3MB!');
        return;
      }
      this.convertFileToBase64(file.raw).then(base64img => {
        this.editShopInfoForm.bIdentityImage = base64img;
      }).catch(error => {
        this.$message.error('图片转换失败!');
      });
    },


		// uploadImg(file) {
		//   //文件上传操作
		//   let formData = new FormData();
		//   formData.append("file", file.raw);
		//   uploadImg(formData).then((res) => {
		//     if (res.code == "200") {
		//       console.log("图片上传成功");
		//     }
		//   });
		// },
		/*//修改页面关闭的
		handleDrawerClose(done) {
			this.$confirm('确认关闭？')
				.then((_) => {
					done();
				})
				.catch((_) => {});
		},*/

		// 对话框退出按钮
		handleAddDigClose(done) {
			this.$confirm('确认关闭？')
				.then((_) => {
					this.resetAddForm();
					done();
				})
				.catch((_) => {});
		},
		//添加重置
		resetAddForm() {
			this.addShopInfoForm = {
				uid: sessionStorage.getItem('userUid'),
				bName: null,
				bImage: null,
				bHead: null,
				bAddress: null,
        bIdentityImage: null,
			};

			this.$refs.addShopUpRef.clearFiles();
      this.$refs.addShopIdenUpRef.clearFiles();
		},
		// 增加商店信息的api操作
		excuteAddForm() {
			this.$refs.ShopAddFormRef.validate(async (valid) => {
				if (!valid) return;
				const { data: res } = await addShopInfo(this.addShopInfoForm);
				if (res.code !== 200) return this.$message.error(res.message);
				this.$message.success(res.message);
				await this.getShopById(this.addShopInfoForm.uid);
				//添加成功后关闭 重置表单
				this.addShopInfoForm = {
					uid: sessionStorage.getItem('userUid'),
					bName: null,
					bImage: null,
					bHead: null,
					bAddress: null,
          bIdentityImage: null,
				};
				this.fileList = [];
        this.IdenfileList=[];
				this.addDialogVisible = false;
			});
		},
    //将图片文件转为base64
    convertFileToBase64(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
      });
    },
		//上传商店图片变化钩子
		onChange(file, fileList) {
			const isJPGORPNG = file.raw.type === 'image/jpeg' || file.raw.type === 'image/png';
			const isLt1M = file.size / 1024 / 1024 < 1;

			if (!isJPGORPNG) {
				this.$message.info('上传图片只能是 JPG 或 PNG 格式!');
				return;
			}
			if (!isLt1M) {
				this.$message.info('上传图片大小不能超过 1MB!');
				return;
			}
      this.convertFileToBase64(file.raw).then(base64img => {
        this.addShopInfoForm.bImage = base64img;
      }).catch(error => {
        this.$message.error('图片转换失败!');
      });
		},
    //上传商店认证图片变化钩子
    onIdenChange(file, IdenfileList) {
      const isJPGORPNG = file.raw.type === 'image/jpeg' || file.raw.type === 'image/png';
      const isLt3M = file.size / 1024 / 1024 <3;

      if (!isJPGORPNG) {
        this.$message.info('上传图片只能是 JPG 或 PNG 格式!');
        return;
      }
      if (!isLt3M) {
        this.$message.info('上传图片大小不能超过 3MB!');
        return;
      }
      this.convertFileToBase64(file.raw).then(base64img => {
        this.addShopInfoForm.bIdentityImage = base64img;
      }).catch(error => {
        this.$message.error('图片转换失败!');
      });
    },
		// 覆盖默认的上传行为，可以自定义上传的实现
		httpRequest() {},
	},
};
</script>

<style lang="less" scoped>
.active{
  img{
    width: 300px;
    height: 300px;
    border: #99a9bf 2px solid;
  }
}

.edit-avatar {
	width: 250px;
	height: 200px;
	border: #00d9ff 2px dashed;
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
.text {
	font-size: 14px;
}

.item {
	margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
	display: table;
	content: '';
}
.clearfix:after {
	clear: both;
}

.box-card {
	position: relative;
	width: 800px;
  height: 350px;
	margin: 15px;
	background-color: rgb(235, 226, 226);
	overflow: visible;
	transition: all 1s linear;
	.b-tag {
		font-weight: bold;
		font-size: 20px;
	}
	.b-image {
		padding-right: 30px;
		img {
			width: 200px;
			height: 180px;
		}
	}
  .b-idenImage {
    padding-left: 40px;
    font-size: 20px;
    img {
      width: 200px;
      height: 180px;
    }
  }
	.card-context {
		display: flex;
		.b-info div {
			font-size: 18px;
		}
	}
}

.box-card:hover {
	top: -2px;
	right: -2px;
	background-color: rgb(206, 237, 245);
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
