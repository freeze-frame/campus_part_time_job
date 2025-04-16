<style lang="less" scoped>
.login_container {
  overflow: hidden;
  position: relative;
  height: 100%;
  background: url('https://images.unsplash.com/photo-1551009175-8a68da93d5f9') no-repeat; /* 替换为合适的背景图片 */
  background-size: cover;

  &::before {
    position: absolute;
    content: '';
    height: 100%;
    width: 100%;
    background-color: rgba(255, 255, 255, 0.5); /* 更浅的背景色 */
    backdrop-filter: blur(5px);
  }

  .logo {
    text-align: center;
    margin-bottom: 20px;

    .running-student {
      width: 150px;
      animation: run 2s infinite alternate;
    }

    @keyframes run {
      0% {
        transform: translateX(0);
      }
      100% {
        transform: translateX(10px);
      }
    }
  }

  .login_box {
    position: relative;
    width: 450px;
    background-color: rgba(255, 255, 255, 0.9);
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 30px;
    border-radius: 10px;

    h2 {
      text-align: center;
      color: #333; /* 深色字体 */
      margin-bottom: 20px;
      font-family: 'Arial', sans-serif; /* 现代字体 */
    }

    .login_form {
      .el-form-item {
        .el-input__inner {
          border-color: #333; /* 深色边框 */
          border-radius: 5px;
          background-color: rgba(255, 255, 255, 0.8); /* 半透明背景 */
        }
        .el-input__prefix {
          color: #333; /* 深色前缀 */
        }
        .form_hint {
          font-size: 12px;
          color: #606266;
          margin-top: 5px;
        }
      }
      .form_btn {
        display: flex;
        justify-content: flex-end;
        margin-top: 20px;
        .el-button {
          border-radius: 5px;
          font-family: 'Arial', sans-serif; /* 现代字体 */
        }
        .el-button--success {
          background-color: #4CAF50; /* 绿色 */
          border-color: #4CAF50; /* 绿色 */
          color: white;
        }
        .el-button--primary {
          background-color: #2196F3; /* 蓝色 */
          border-color: #2196F3; /* 蓝色 */
          color: white;
        }
        .el-button--info {
          background-color: #808080; /* 灰色 */
          border-color: #808080; /* 灰色 */
          color: white;
        }
      }
      .identityImage-uploader {
        display: block;
        width: 178px;
        height: 178px;
        line-height: 178px;
        border: 1px dashed #333; /* 深色边框 */
        border-radius: 6px;
        cursor: pointer;
        overflow: hidden;
        position: relative;
        margin: 0 auto;
      }
      .identityImage-uploader-icon {
        font-size: 28px;
        color: #333; /* 深色图标 */
        display: block;
        text-align: center;
      }
      .identityImage {
        width: 100%;
        height: 100%;
        display: block;
      }
    }
  }

  .slogan {
    text-align: center;
    margin-top: 20px;

    h3 {
      color: #333; /* 深色字体 */
      font-size: 1.5em;
      margin-bottom: 10px;
    }

    p {
      color: #606266; /* 较浅字体 */
      margin-bottom: 20px;
    }

    .el-button {
      background-color: #FF9800; /* 橙色 */
      border-color: #FF9800; /* 橙色 */
      color: white;
      font-family: 'Arial', sans-serif; /* 现代字体 */
    }
  }
}
</style>
