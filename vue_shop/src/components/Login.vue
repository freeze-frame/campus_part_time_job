<template>
	<div class="login_container">
		<div class="login_box">
			<!-- 表单区域 -->
			<el-form v-if="!addUserFlag" :model="loginForm" :rules="rules" class="login_form" ref="LoginFormRef">
				<h2>校园兼职服务平台</h2>
				<!-- 账号 -->
				<el-form-item prop="username">
					<el-input v-model="loginForm.username" placeholder="账号" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>
				<!-- 密码 -->
				<el-form-item prop="password">
					<el-input type="password" v-model="loginForm.password" placeholder="密码" prefix-icon="el-icon-lock"></el-input>
				</el-form-item>
				<!-- 登录与重置 -->
				<el-form-item class="form_btn">
					<el-button type="success" @click="login">登录</el-button>
					<el-button type="primary" @click="addUserFlag = !addUserFlag">注册</el-button>
					<el-button type="info" @click="resetForm">重置</el-button>
				</el-form-item>
			</el-form>

			<!-- 表单区域 -->
			<el-form v-if="addUserFlag" :model="addUserForm" :rules="rules" class="login_form" ref="AddUserFormRef">
				<h2>用户注册</h2>
				<!-- 账号 -->
				<el-form-item prop="username">
					<el-input v-model="addUserForm.username" placeholder="账号" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>
				<!-- 密码 -->
				<el-form-item prop="password">
					<el-input type="password" v-model="addUserForm.password" placeholder="密码" prefix-icon="el-icon-lock"></el-input>
				</el-form-item>
				<div></div>
				<!-- 确认密码 -->
				<el-form-item label="" prop="repassword">
					<el-input type="password" v-model="addUserForm.repassword" placeholder="确认密码" prefix-icon="el-icon-lock"></el-input>
				</el-form-item>
        <!-- 姓名 -->
        <el-form-item prop="realname">
          <el-input v-model="addUserForm.realname" placeholder="用户名" prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>
        <!-- 年龄-->
        <el-form-item prop="age">
          <el-input v-model="addUserForm.age" placeholder="年龄" prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>
				<!-- 手机号 -->
				<el-form-item prop="mobile">
					<el-input v-model="addUserForm.mobile" placeholder="手机号码" prefix-icon="el-icon-phone"></el-input>
				</el-form-item>
				<!-- 邮箱 -->
				<el-form-item prop="email">
					<el-input v-model="addUserForm.email" placeholder="邮箱地址" prefix-icon="el-icon-eleme"></el-input>
				</el-form-item>
        <!--身份-->
				<el-form-item>
					<template>
						<el-select v-model="addUserForm.pid" placeholder="请选择">
							<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"> </el-option>
						</el-select>
					</template>
				</el-form-item>
          <!--上传身份认证图片 -->
          <el-form-item label="上传身份认证图片">
            <el-upload
                class="identityImage-uploader"
                :http-request="handleCustomUpload"
                :show-file-list="false"
            >
              <img v-if="addUserForm.identityImage" :src="addUserForm.identityImage" class="identityImage" alt="上传身份认证图片" />
              <i v-else class="el-icon-plus identityImage-uploader-icon"></i>
            </el-upload>
          </el-form-item>
				<!-- 登录与重置 -->
				<el-form-item class="form_btn retuen_btn">
					<el-button type="success" @click="addUser">注册</el-button>
					<el-button type="primary" @click="addUserFlag = !addUserFlag">返回</el-button>
					<el-button type="info" @click="resetAddForm">重置</el-button>
				</el-form-item>
			</el-form>
		</div>
	</div>
</template>



<script>
import { loginResultAPI, addUserAPI } from '@/api/AxiosAPI';
export default {
	name: 'MyLogin',
	data() {
		//确认密码的
		const validatePass2 = (rule, value, callback) => {
			if (value == '') {
				callback(new Error('请再次输入密码'));
				// password 是表单上绑定的字段
			} else if (value !== this.addUserForm.password) {
				callback(new Error('两次输入密码不一致!'));
			} else {
				callback();
			}
		};
		// 年龄的
		const checkAge = (rule, value, callback) => {
			let reg = /^(?:[1-9][0-9]?|1[01][0-9]|120)$/; //年龄是1-120之间有效
			if (value == undefined) {
				callback([new Error('请输入年龄')]);
			} else {
				if (value && value.length > 0) {
					if (!reg.test(value)) {
						callback([new Error('年龄输入不合法')]);
					} else {
						callback();
					}
				} else if (value.length == 0) {
					callback();
				} else {
					callback(new Error('年龄输入不合法'));
				}
			}
		};

		return {
			options: [
				// {
				//   value: '1',
				//   label: '管理员'
				// },
				{
					value: '2',
					label: '学生',
				},
				{
					value: '3',
					label: '商户',
				},
			],


			addUserFlag: false,
			//注册表单
			addUserForm: {
				username: '',
				password: '',
				repassword: '',
        realname:'',
        age: null,
				mobile: '',
				email: '',
				pid: '2',
        identityImage:'',// 存储上传成功后的图片base64

			},
			loginForm: {
				username: '',
				password: '',
			},
			// 表单规则
			rules: {
				username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          {
            min: 4,
            max: 13,
            message: '长度在 4 到 13 个字符',
            trigger: 'blur'
          },
          {
            validator: (rule, value, callback) => {
              const reg = /^[A-Za-z0-9]+$/; // 正则表达式，匹配字母和数字的字符串
              if (!reg.test(value)) {
                callback(new Error('账号必须是字母和数字的组合'));
              } else {
                callback();
              }
            },
            trigger: 'blur'
          }
				],
				repassword: [{ required: true, validator: validatePass2, trigger: 'blur' }],
				password: [
					{ required: true, message: '请输入密码', trigger: 'blur' },
					{
						min: 6,
						max: 13,
						message: '长度在 6 到 13 个字符',
						trigger: 'blur',
					},
				],
        realname:[
          { required: true, message: '请输入用户名', trigger: 'change' },
          {
            validator: (rule, value, callback) => {
              const reg = /^[\u4e00-\u9fa5]+$/; // 正则表达式，匹配全是中文字符的字符串
              if (!reg.test(value)) {
                callback(new Error('用户名必须全是中文'));
              } else {
                callback();
              }
            },
            trigger: 'blur'
          }
        ],
				mobile: [
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
				age: [{ required: true, validator: checkAge, trigger: 'blur' }],
			},
		};
	},
	methods: {
		resetAddForm() {
			this.$refs.AddUserFormRef.resetFields();
		},
		// 表单重置
		resetForm() {
			this.$refs.LoginFormRef.resetFields();
		},
		// 表单预验证  validate(callback: Function(boolean, object))
		login() {
			this.$refs.LoginFormRef.validate(async (valid) => {
				// 如果为预验证false就不发请求
				if (!valid) return;
				// 直接传递一个账号密码对象过去
				const { data: res } = await loginResultAPI(this.loginForm);
				// 如果状态码不是200 就登陆失败 跳出失败弹框
				if (res.code !== 200) return this.$message.error(res.message);

				//保存token
				sessionStorage.setItem('token', res.data.token);

				// 否则跳出登陆成功
				this.$message.success(res.message);
				this.loginUser = res.data;

				// 1.将登陆成功后的token，保存到客户端的sessionStorage中
				// 1.1 项目中除了登录之外的API接口，必须要登录之后才能访问
				// 1.2 token这应该在当前打开期间生效，所以将token存放在sessionStorage中
				// sessionStorage.setItem('token', res.data.token)
				sessionStorage.setItem('imageData', res.data.imageData);

				// 2.通过路由导航跳到后台主页

				if (res.data.pid == 1) {
					sessionStorage.setItem('activePath', '/home/wecome');
					this.$router.push({
						path: '/home',
					});
				} else if (res.data.pid == 2) {
					sessionStorage.setItem('userUid', res.data.uid);
					sessionStorage.setItem('activePath', '/suthome/stuindex');

					this.$router.push({
						path: '/suthome',
					});
				} else if (res.data.pid == 3) {
					sessionStorage.setItem('activePath', '/managehome/manageindex');
					sessionStorage.setItem('userUid', res.data.uid);
					this.$router.push({
						path: '/managehome',
					});
				} else {
					this.$message.error('获取用户数据失败!!');
				}
			});
		},
		addUser() {
			this.$refs.AddUserFormRef.validate(async (valid) => {
				if (!valid) return;
				const { data: res } = await addUserAPI(this.addUserForm);
				if (res.code !== 200) return this.$message.error(res.message);
				this.$message.success('注册成功,请登录!');
				this.addUserFlag = !this.addUserFlag;
			});
		},
   /* handleUploadSuccess(file) {
     this.$message.success('上传成功');
    },
    handleUploadError(err, file, fileList) {
      this.$message.error('上传失败');
    },
    beforeIdentityImageUpload(file) {
      const isJPGORPNG = file.raw.type === "image/jpeg" || file.raw.type === "image/png";
      const isLt4M = file.size / 1024 / 1024 < 4;
      if (!isJPGORPNG) {
        this.$message.info("上传头像图片只能是 JPG 或 PNG 格式!");
        return;
      }
      if (!isLt4M) {
        this.$message.info("上传头像图片大小不能超过 4MB!");
        return;
      }
      var This = this;
      var reader = new FileReader();
      reader.readAsDataURL(file.raw);
      reader.onload = function (e) {
        This.StuEditForm.imageData = this.result;
      };
      return true;
    },*/
    handleCustomUpload({ file }) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
      if (!isJpgOrPng) {
        this.$message.error('上传头像图片只能是 JPG 或 PNG 格式');
        return;
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB');
        return;
      }

      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = (e) => {
        this.addUserForm.identityImage = e.target.result;
      };
    },
  }
};
</script>



<style lang="less" scoped>
.login_container {
  display: flex; // 启用弹性布局
  justify-content: center; // 水平居中
  align-items: center; // 垂直居中
  min-height: 100vh; // 确保容器高度撑满视口
  background: url('@/assets/3.png') no-repeat;
  background-size: cover;

	&::before {
		position: absolute;
		content: '';
		height: 100%;
		width: 100%;
		background-color: rgba(31, 33, 41, 0.2);
		backdrop-filter: blur(1px);
	}
	.login_box {
		// 450*300 居中 背景#fff
    width: 450px;
    height: 400px;
    display: flex;
    position: relative; // 移除绝对定位
    top: auto;
    left: auto;
    transform: none; // 取消位移变换
    box-shadow: 0 0 20px rgba(163, 189, 252, 0.5);



		.login_form[data-v-ef68022e] {
			position: relative;
		}
		.login_form {
      //width: 40%; // 调整比例
      //position: relative; // 移除absolute定位
      //height: 100%; // 继承父容器高度
      //border-radius: 0 4px 4px 0; // 右侧圆角
			//background-color: rgba(255, 255, 255, 1);
      ////position: absolute;
      ////bottom: 0;
      //box-sizing: border-box;
      ////width: 100%;
      //padding: 40px 25px; // 增加纵向间距
      //overflow-y: auto; // 添加滚动条
			//.retuen_btn {
			//	padding-top: 10px;
			//}
      width: 450px;
      padding: 40px;
      background: rgba(255, 255, 255, 0.95);
      border-radius: 0 10px 10px 0;
      box-shadow: 0px 0 15px rgba(0,0,0,0.1);
      z-index: 2;

			.upload {
				position: absolute;
				bottom: 50px;
				ul {
					position: absolute;
					bottom: 0;
				}
			}
			h2 {
				text-align: center;
			}
      .identityImage-uploader {
        display: block;
        width: 178px;
        height: 178px;
        line-height: 178px;
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        overflow: hidden;
        position: relative;
      }
      .identityImage-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        display: block;
        text-align: center;
      }
      .identityImage {
        width: 100%;
        height: 100%;
        display: block;
      }
			.form_btn {
				//display: flex;
				//justify-content: flex-end;
        margin-top: 80px;
        margin-left: 18%;
			}
		}
	}
}
</style>
