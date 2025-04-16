<template>
  <div class="myself-container">
    <div class="main">
      <div class="my">
        <div class="active" @click="changeImageFn">
          <img :src="myInfoList.imageData" alt="" title="点击更换头像" />
        </div>
        <div class="info">
          <div class="info-item">
            <h3>账 号:</h3>
            <span>{{ myInfoList.username }}</span>
          </div>
          <div class="info-item">
            <h3>用户名:</h3>
            <span>{{ myInfoList.realname }}</span>
          </div>
          <div class="info-item">
            <h3>年 龄:</h3>
            <span>{{ myInfoList.age }}岁</span>
          </div>
          <div class="info-item">
            <h3>号 码:</h3>
            <span>{{ myInfoList.mobile }}</span>
          </div>
          <div class="info-item">
            <h3>邮 箱:</h3>
            <span>{{ myInfoList.email }}</span>
          </div>
          <div class="info-item">
            <h3>认证状态:</h3>
            <span>{{ myInfoList.identification==0?'未认证':myInfoList.identification==1?'认证通过':'认证失败' }}</span>
          </div>
          <div class="info-item">
            <h3>身份认证:</h3>
          </div>
        <div class="iden-image" @click="changeIdenImageFn">
            <img :src="myInfoList.identityImage" alt="" title="点击重新上传身份认证"/>
        </div>
        </div>
        <div class="op">
          <el-button
            class="btn"
            type="primary"
            size="medium"
            round
            @click="handleEdit(myInfoList)"
            >修改信息</el-button
          >
          <el-button
            class="btn"
            type="primary"
            size="medium"
            round
            @click="handleEditPass()"
            >更改密码</el-button
          >
        </div>
      </div>
    </div>

    <el-dialog
      :visible.sync="editImgDialogVisible"
      width="50%"
      :before-close="handleEditImgDigClose"
      append-to-body
    >
      <h2>点击更换头像:</h2>
      <el-upload
        class="avatar-uploader"
        :action="''"
        :auto-upload="false"
        :show-file-list="false"
        :on-change="handleAvatarChangeIcon"
      >
        <img
          v-if="MerEditFrom.imageData"
          :src="MerEditFrom.imageData"
          class="edit-avatar"
        />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="excuteEditImgForm">修 改</el-button>
      </span>
    </el-dialog>

    <el-dialog
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleEditDigClose"
      append-to-body
    >
      <el-form
        :model="MerEditFrom"
        :rules="rules"
        class="edit_form"
        ref="MerEditFromRef"
      >
        <h2>修改商家信息</h2>
        <!-- 账号 -->
        <el-form-item prop="username">
          <el-input
            v-model="MerEditFrom.username"
            placeholder="账号"
            prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
        <!-- 用户名 -->
        <el-form-item prop="realname">
          <el-input
              v-model="MerEditFrom.realname"
              placeholder="用户名"
              prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
        <!-- 年龄 -->
        <el-form-item prop="age">
          <el-input
              v-model="MerEditFrom.age"
              placeholder="年龄"
              prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
        <!-- 手机号 -->
        <el-form-item prop="mobile">
          <el-input
            v-model="MerEditFrom.mobile"
            placeholder="手机号码"
            prefix-icon="el-icon-phone"
          ></el-input>
        </el-form-item>
        <!-- 邮箱 -->
        <el-form-item prop="email">
          <el-input
            v-model="MerEditFrom.email"
            placeholder="邮箱地址"
            prefix-icon="el-icon-eleme"
          ></el-input>
        </el-form-item>

        <!-- <div style="padding-bottom: 15px">用户图片:</div> -->
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button type="info" @click="resetEditForm">重 置</el-button>
        <el-button type="primary" @click="excuteEditForm">修 改</el-button>
      </span>
    </el-dialog>
    <el-dialog
        :visible.sync="editIdenImgDialogVisible"
        width="50%"
        :before-close="handleEditIdenImgDigClose"
        append-to-body
    >
      <h2>点击更换身份认证图片:</h2>
      <el-upload
          class="avatar-uploader"
          :action="''"
          :auto-upload="false"
          :show-file-list="false"
          :on-change="handleAvatarChangeIdenIcon"
      >
        <img
            v-if="MerEditFrom.identityImage"
            :src="MerEditFrom.identityImage"
            class="edit-avatar"
        />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="excuteEditIdenImgForm">修 改</el-button>
      </span>
    </el-dialog>
    <el-dialog
      :visible.sync="dialogPassVisible"
      width="30%"
      :before-close="handleEditPassDigClose"
      append-to-body
    >
      <el-form
        :model="StuEditPassForm"
        :rules="rules"
        class="edit_form"
        ref="StuEditPassFormRef"
      >
        <h2>修改密码</h2>
        <!-- 账号 -->
        <el-form-item>
          <el-input
            v-model="StuEditPassForm.oldPassword"
            placeholder="旧密码"
            prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="StuEditPassForm.password"
            placeholder="新密码"
            prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
        <el-form-item prop="repassword">
          <el-input
            v-model="StuEditPassForm.repassword"
            placeholder="确认密码"
            prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button type="info" @click="resetEditPassForm">重 置</el-button>
        <el-button type="primary" @click="excuteEditPassForm">修 改</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getInfoAPI,
  stuEditAPI,
  editStuImg,
  editStuPassword,
} from "@/api/AxiosAPI";
export default {
  created() {
    this.$nextTick(() => {
      this.getInfo();
    });
  },
  data() {
    //确认密码的
    const validatePass2 = (rule, value, callback) => {
      if (value == "") {
        callback(new Error("请再次输入密码"));
        // password 是表单上绑定的字段
      } else if (value !== this.StuEditPassForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      dialogPassVisible: false,
      StuEditPassForm: {
        oldPassword: null,
        password: null,
        repassword: null,
      },
      editImgDialogVisible: false,
      editIdenImgDialogVisible: false,
      dialogVisible: false,
      MerEditFrom: {
        username: null,
        realname:null,
        mobile: null,
        email: null,
        age: null,
        imageData: null,
        identityImage:null,
      },
      myInfoList: {
        username: null,
        realname:null,
        mobile: null,
        email: null,
        age: null,
        imageData: null,
        identityImage:null,
      },
      rules: {
        username: [
          { required: true, message: "请输入账号", trigger: "blur" },
          {
            min: 4,
            max: 13,
            message: "长度在 4 到 13 个字符",
            trigger: "blur",
          },
        ],
        mobile: [
          // 添加正则表达式 pattern: /^1[3|5|7|8|9]\d{9}$/，验证手机号是否正确
          { required: true, message: "请输入手机号", trigger: "change" },
          {
            pattern: /^1[3|5|7|8|9]\d{9}$/,
            message: "请输入正确的号码格式",
            trigger: "change",
          },
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        age: [
          {
            pattern: /^(?:[1-9][0-9]?|1[01][0-9]|120)$/,
            required: true,
            message: "请输入1~120",
            trigger: "blur",
          },
        ],
        repassword: [
          { required: true, validator: validatePass2, trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 13,
            message: "长度在 6 到 13 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    resetEditPassForm() {
      this.StuEditPassForm = {
        oldPassword: null,
        password: null,
        repassword: null,
      };
    },
    async excuteEditPassForm() {
      this.$refs.StuEditPassFormRef.validate(async (valid) => {
        if (!valid) return;

        const { data: res } = await editStuPassword(
          sessionStorage.getItem("userUid"),
          this.StuEditPassForm.oldPassword,
          this.StuEditPassForm.password
        );
        if (res.code !== 200) return this.$message.error(res.message);
        this.resetEditPassForm();
        this.dialogPassVisible = false;
        this.$message.success(res.message);
      });
    },
    handleEditPass() {
      this.dialogPassVisible = true;
    },
    handleEditPassDigClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          // getInfo();
          done();
        })
        .catch((_) => {});
    },

    async excuteEditImgForm() {
      const { data: res } = await editStuImg(this.MerEditFrom);
      if (res.code !== 200) return this.$message.error(res.message);
      //刷新
      sessionStorage.setItem("imageData", this.MerEditFrom.imageData);
      this.getInfo();
      this.editImgDialogVisible = false;
      this.$message.success(res.message);
    },
    changeImageFn() {
      this.MerEditFrom.imageData = this.myInfoList.imageData;
      this.editImgDialogVisible = true;
    },
    handleEditImgDigClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          // getInfo();
          done();
        })
        .catch((_) => {});
    },
    handleAvatarChangeIcon(file, fileList) {
      const isJPGORPNG =
        file.raw.type === "image/jpeg" || file.raw.type === "image/png";
      const isLt1M = file.size / 1024 / 1024 < 1;

      if (!isJPGORPNG) {
        this.$message.info("上传头像图片只能是 JPG 或 PNG 格式!");
        return;
      }
      if (!isLt1M) {
        this.$message.info("上传头像图片大小不能超过 1MB!");
        return;
      }
      var This = this;
      var reader = new FileReader();
      reader.readAsDataURL(file.raw);
      reader.onload = function (e) {
        This.MerEditFrom.imageData = this.result;
      };
    },
    //重置
    resetEditForm() {
      this.MerEditFrom = {};
    },
    // 修改提交操作
    excuteEditForm() {
      this.$refs.MerEditFromRef.validate(async (valid) => {
        if (!valid) return;
        const { data: res } = await stuEditAPI(this.MerEditFrom);
        if (res.code !== 200) return this.$message.error(res.message);
        this.dialogVisible = false;
        //刷新
        this.getInfo();
        this.$message.success(res.message);
      });
    },
    // 编辑按钮
    handleEdit(row) {
      // 深克隆
      this.MerEditFrom = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },

    // 对话框退出按钮
    handleEditDigClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          this.MerEditFrom = {};
          done();
          resetEditForm();
        })
        .catch((_) => {});
    },

    async getInfo() {
      const { data: res } = await getInfoAPI(sessionStorage.getItem("userUid"));
      if (res.code !== 200) return this.$message.error(res.message);
      this.myInfoList = res.data;
      this.MerEditFrom.uid = res.data.uid;
      this.MerEditFrom.imageData = res.data.imageData;
    },
    changeIdenImageFn() {
      this.MerEditFrom.identityImage = this.myInfoList.identityImage;
      this.editIdenImgDialogVisible = true;
    },
    handleEditIdenImgDigClose(done) {
      this.$confirm("确认关闭？")
          .then((_) => {
            // getInfo();
            done();
          })
          .catch((_) => {});
    },
    handleAvatarChangeIdenIcon(file, fileList) {
      const isJPGORPNG =
          file.raw.type === "image/jpeg" || file.raw.type === "image/png";
      const isLt4M = file.size / 1024 / 1024 < 4;

      if (!isJPGORPNG) {
        this.$message.info("上传认证图片只能是 JPG 或 PNG 格式!");
        return;
      }
      if (!isLt4M) {
        this.$message.info("上传认证图片大小不能超过 4MB!");
        return;
      }
      var This = this;
      var reader = new FileReader();
      reader.readAsDataURL(file.raw);
      reader.onload = function (e) {
        This.MerEditFrom.identityImage = this.result;
      };
    },
    async excuteEditIdenImgForm() {
      const { data: res } = await editStuImg(this.MerEditFrom);
      if (res.code !== 200) return this.$message.error(res.message);
      //刷新
      sessionStorage.setItem("identityImage", this.MerEditFrom.identityImage);
      this.getInfo();
      this.editIdenImgDialogVisible=false;
      this.$message.success(res.message);
    },
  },
};
</script>

<style lang="less" scoped>
.avatar-uploader {
  width: 300px;
  height: 300px;
  /deep/.el-upload {
    width: 100%;
    height: 100%;
    img {
      width: 100%;
      height: 100%;
    }
  }
}
.myself-container {
  // background: url("https://images7.alphacoders.com/122/thumb-1920-1227760.jpg")
  //   no-repeat;
  background: url("@/assets/6.png")
  no-repeat;
  background-size: cover;
  &::before {
    position: absolute;
    content: "";
    height: 100%;
    width: 100%;
    background-color: rgba(31, 33, 41, 0.2);
    backdrop-filter: blur(3px);
  }
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

    .my {
      position: relative;
      display: flex;
      flex-direction: column;
      width: 800px;
      height: 1100px;
      background-color: rgba(236, 236, 236,0.5);
      border: 1px solid #dad4d4;

      .active {
        position: absolute;
        top: 20px;
        border-radius: 100%;
        width: 200px;
        height: 200px;
        align-self: center;
        overflow: hidden;
        cursor: pointer;
        img {
          width: 100%;
          height: 100%;
        }
      }
      .iden-image {border-radius: 100%;
        width: 200px;
        height: 200px;
        padding: 0px 110px;
        cursor: pointer;
        img {
          width: 100%;
          height: 100%;
        }
      }
      .info {
        display: flex;
        flex-direction: column;
        align-self: center;
        padding: 10px 0px 10px 0px;
        width: 400px;
        margin-top: 240px;
        .info-item {
          h3 {
            font-size: 24px;
            width: 120px;
            display: inline-block;
          }
          span {
            font-size: 24px;
          }
        }
      }
      .op {
        margin-top: 50px;
        text-align: center;
        .btn {
          padding: 20px;
          margin: 0 80px;
          font-size: 24px;
        }
      }
    }
  }
}
</style>
