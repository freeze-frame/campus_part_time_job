<template>
  <div class="sturecshow_container">
    <div class="detailtitle">
      <div class="main">
        <div>
          <h1>
            {{ recInfo.recStation }} <span>￥{{ recInfo.recSalary }}/h</span>
          </h1>
          <h2>{{ recInfo.bName }} {{ recInfo.bAddress }}</h2>
        </div>

        <div class="like-btn">
          <button class="niceButton5" @click="sendBn"><span>我要投递</span></button>
        </div>
      </div>
    </div>
    <div class="main">
      <div class="jdbxo">
        <div class="image-left">
          <img :src="recInfo.bImage" alt="" />
        </div>
        <div class="jdrgtitle">
          <h3>职位描述:</h3>
          <div v-html="recInfo.recInfo" class="custom-html"></div>
        </div>
      </div>
    </div>

    <div class="main">
      <div class="title">
        <h1>热招职位</h1>
      </div>
      <div v-clickoutside="hideReplyBtn" class="my-reply">
        <div class="user-avater"><img class="header-img" :src="myImage" /></div>

        <div class="reply-info">
          <input
              id="replyInput"
              placeholder="点击输入评论..."
              class="reply-input"
              ref="replyCommentRef"
              @focus="showReplyBtn"
              v-model="replyComment"
          />
        </div>
        <div class="reply-btn-box" v-show="btnShow">
          <el-button class="reply-btn" size="medium" @click="sendComment()" type="primary">发表评论</el-button>
        </div>
      </div>

      <!-- 父级 -->
      <div v-for="(item, i) in commentsList" :key="i" class="author-title reply-father">
        <div class="user-avater"><img class="header-img" :src="item.bImage" /></div>

        <div class="author-info">
          <!-- 名字 -->
          <span class="author-name">{{ item.username }}</span>
          <!-- 时间 -->
          <span class="author-time">{{ item.createTime }}</span>
        </div>
        <!-- 点击评论-->
        <div class="icon-btn">
					<span @click="showReplyInput(item, i)">
						<i class="iconfont el-icon-s-order">{{ item.children.length }}</i>
					</span>
          <span v-if="item.uid == myUid" @click="delOneFn(item.comId)">
						<i class="iconfont el-icon-delete-solid"></i>
					</span>
        </div>
        <!-- 父级内容 -->
        <div class="talk-box">
          <p>
            <span class="reply">{{ item.comRemark }}</span>
          </p>
        </div>
        <!-- 子集 -->
        <div class="reply-box">
          <div v-for="(reply, j) in item.children" :key="j" class="author-title">
            <div class="user-avater"><img class="header-img" :src="reply.imageData" /></div>
            <div class="icon-btn">
							<span v-if="reply.uid == myUid" @click="delTwoFn(reply.repId)">
								<i class="iconfont el-icon-delete-solid"></i>
							</span>
            </div>
            <div class="author-info">
              <!-- 名字 -->
              <span class="author-name">{{ reply.username }}</span>
              <!-- 时间 -->
              <span class="author-time">{{ reply.createTime }}</span>
            </div>
            <!-- 回复 -->
            <div class="talk-box">
              <p>
                <span>回复 {{ item.username }}:</span>
                <span class="reply">{{ reply.repRemark }}</span>
              </p>
            </div>
          </div>
        </div>

        <!-- 子评论的评论框 -->
        <div v-show="item.showState" class="my-reply my-comment-reply">
          <div class="user-avater"><img class="header-img" :src="myImage" /></div>
          <div class="reply-info">
            <input placeholder="点击输入评论..." class="reply-input reply-comment-input" :ref="'childenRef' + i" v-model="replyCommentChilden" />
          </div>

          <!-- 发评论的按钮操作 -->
          <div class="reply-btn-box">
            <el-button class="reply-btn" size="medium" @click="sendCommentReply(item)" type="primary">发表评论</el-button>
          </div>
        </div>
      </div>
    </div>

    <div class="footer">
      <div class="footmain">
        <div class="footbox" style="width: 120px">
          <div class="widget-title">企业服务</div>
          <div class="foottext">
            <ul>
              <li><a href="#">职位搜索 </a></li>
              <li><a href="#">新闻资讯 </a></li>
              <li><a href="#">BOSS直聘APP </a></li>
              <li><a href="#">投资者关系 </a></li>
            </ul>
          </div>
        </div>
        <div class="footbox" style="width: 120px">
          <div class="widget-title">使用与帮助</div>
          <div class="footblog">
            <ul>
              <li><a href="#">协议与规则 </a></li>
              <li><a href="#">个人信息保护政策 </a></li>
              <li><a href="#">防骗指南 </a></li>
              <li><a href="#">使用帮助 </a></li>
            </ul>
          </div>
        </div>
        <div class="footbox" style="width: 120px">
          <div class="widget-title">招聘频道介绍</div>
          <div class="footblog">
            <ul>
              <li><a href="#">北京培训策划招聘</a></li>
              <li><a href="#">北京磨工招聘 </a></li>
              <li><a href="#"> 北京培训策划招聘</a></li>
              <li><a href="#"> 北京模具工招聘</a></li>
            </ul>
          </div>
        </div>
        <div class="footbox">
          <div class="widget-title">联系我们</div>
        </div>
        <div class="footbox">
          <div class="widget-title">关于我们</div>
          <div class="footabout">
            <p>GYK直聘肇庆招聘频道，展示肇庆招聘信息，百万在线直聘，直接开聊，在线面试，广应科找工作就上GYK直聘网站。</p>
            <p>GYK直聘肇庆招聘频道，展示肇庆招聘信息，百万在线直聘，直接开聊，在线面试，广应科找工作就上GYK直聘网站。</p>
          </div>
        </div>
      </div>
    </div>

    <el-dialog append-to-body :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <h2>当前选择的简历为：{{ clickNotesFilename ? clickNotesFilename : '未选择' }}</h2>
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
            <el-button @click="handleClick(scope.row)" type="primary" round size="small">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisibleFn">取 消</el-button>
				<el-button type="primary" @click="excuteSendNotBn">确 定</el-button>
			</span>
    </el-dialog>
  </div>
</template>

<script>
const clickoutside = {
  // 初始化指令
  bind(el, binding, vnode) {
    function documentHandler(e) {
      // 这里判断点击的元素是否是本身，是本身，则返回
      if (el.contains(e.target)) {
        return false;
      }
      // 判断指令中是否绑定了函数
      if (binding.expression) {
        // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
        binding.value(e);
      }
    }
    // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
    el.vueClickOutside = documentHandler;
    document.addEventListener('click', documentHandler);
  },
  update() {},
  unbind(el, binding) {
    // 解除事件监听
    document.removeEventListener('click', el.vueClickOutside);
    delete el.vueClickOutside;
  },
};
import {
  getRecruitmentInfoByidAPI,
  commentShowByRecIdAPI,
  addCommentAPI,
  addCommentReplyAPI,
  delCommentAPI,
  delCommentReplyAPI,
  sendNotesBnAPI,
  getNotesAPI,
} from '@/api/AxiosAPI';
export default {
  name: 'MyStuRecShow',
  created() {
    this.getRecruitmentInfoByRid(this.$route.params.rid);
    this.getComment();
    this.myUid = sessionStorage.getItem('userUid');

    this.myImage = sessionStorage.getItem('imageData');
  },
  data() {
    return {
      tid: null,
      clickNotesFilename: null,
      dialogVisible: false,
      notesList: [],
      myUid: null,
      btnShow: false,
      replyShow: false,
      replyComment: '',
      index: '0',
      replyCommentChilden: '',
      commentsList: [],
      childenRef: null,
      sendNotesInfo: {
        uid: sessionStorage.getItem('userUid'),
        tId: this.infobyRecId,
        recId: null,
      },

      recInfo: {},
      myImage: null,
    };
  },

  directives: { clickoutside },
  methods: {
    async excuteSendNotBn() {
      if (this.clickNotesFilename == null) return this.$message.error('请选择简历！');
      const { data: res } = await sendNotesBnAPI({
        uid: sessionStorage.getItem('userUid'),
        tId: this.tid,
        recId: sessionStorage.getItem('infobyRecId'),
        bossId: sessionStorage.getItem('infobyBossUid'),
        bId: sessionStorage.getItem('infobyBid'),
      });
      if (res.code !== 200) {
        this.notesList = [];
        this.dialogVisibleFn();

        return this.$message.error(res.message);
      }
      this.$message.success(res.message);
      this.dialogVisibleFn();
    },
    handleClick(row) {
      this.clickNotesFilename = row.filename;
      this.tid = row.tid;
    },
    dialogVisibleFn() {
      this.clickNotesFilename = null;
      this.tid = null;
      this.dialogVisible = false;
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

    handleClose(done) {
      this.$confirm('确认关闭？')
          .then((_) => {
            this.clickNotesFilename = null;
            this.tid = null;
            done();
          })
          .catch((_) => {});
    },
    async sendBn() {
      await this.getNotes();
      this.dialogVisible = true;
    },
    //删除子评论
    async delTwoFn(repId) {
      const { data: res } = await delCommentReplyAPI(repId);
      if (res.code !== 200) return this.$message.error(res.message);
      // item.showState = !item.showState;
      this.getComment();
    },
    //删除夫评论
    async delOneFn(comId) {
      const { data: res } = await delCommentAPI(comId);
      if (res.code !== 200) return this.$message.error(res.message);
      // item.showState = !item.showState;
      this.getComment();
    },
    async getComment() {
      const { data: res } = await commentShowByRecIdAPI(this.$route.params.rid);
      if (res.code !== 200) {
        this.commentsList = null;
        return this.$message.error(res.message);
      }
      const newList = res.data.map((item) => {
        item.showState = false;
        return item;
      });
      this.commentsList = newList;
    },

    showReplyBtn() {
      this.btnShow = true;
    },
    hideReplyBtn() {
      this.btnShow = false;
    },

    //点击子评论显示
    showReplyInput(item, i) {
      let ref = 'childenRef' + i;
      item.showState = !item.showState;

      //dom更新再获取ref
      this.$nextTick(() => {
        this.$refs[ref][0].focus();
      });
    },

    async sendComment() {
      //校验
      if (this.replyComment.trim() == '' || this.replyComment == null) {
        return this.$message.warning('评论不能为空');
      }
      //没问题就发送数据
      const sendInfo = {
        uid: sessionStorage.getItem('userUid'),
        comRemark: this.replyComment,
        recId: this.$route.params.rid,
      };
      const { data: res } = await addCommentAPI(sendInfo);
      if (res.code !== 200) return this.$message.error(res.message);
      //重置评论信息
      this.btnShow = false;
      this.replyComment = '';
      this.getComment();
    },
    //子评论发送
    async sendCommentReply(item) {
      if (this.replyCommentChilden.trim() == '' || this.replyCommentChilden == null) {
        return this.$message.warning('评论不能为空');
      }
      const sendInfo = {
        uid: sessionStorage.getItem('userUid'),
        repRemark: this.replyCommentChilden,
        answerId: item.comId,
      };
      const { data: res } = await addCommentReplyAPI(sendInfo);
      if (res.code !== 200) return this.$message.error(res.message);
      this.replyCommentChilden = null;
      item.showState = !item.showState;
      this.getComment();
    },

    dateStr(date) {
      //获取js 时间戳
      var time = new Date().getTime();
      //去掉 js 时间戳后三位，与php 时间戳保持一致
      time = parseInt((time - date) / 1000);
      //存储转换值
      var s;
      if (time < 60 * 10) {
        //十分钟内
        return '刚刚';
      } else if (time < 60 * 60 && time >= 60 * 10) {
        //超过十分钟少于1小时
        s = Math.floor(time / 60);
        return s + '分钟前';
      } else if (time < 60 * 60 * 24 && time >= 60 * 60) {
        //超过1小时少于24小时
        s = Math.floor(time / 60 / 60);
        return s + '小时前';
      } else if (time < 60 * 60 * 24 * 30 && time >= 60 * 60 * 24) {
        //超过1天少于30天内
        s = Math.floor(time / 60 / 60 / 24);
        return s + '天前';
      } else {
        //超过30天ddd
        var date = new Date(parseInt(date));
        return date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate();
      }
    },

    async getRecruitmentInfoByRid(rid) {
      const { data: res } = await getRecruitmentInfoByidAPI(rid);
      if (res.code !== 200) return this.$message.error(res.message);
      this.recInfo = res.data;
    },
  },
};
</script>

<style lang="less" scoped>
.reply-comment-input {
  width: 100%;
  border: 2px solid rgb(255, 115, 0);
}
.title {
  margin-top: 50px;
  border-top: 2px solid #f60;
  display: flex;
  justify-content: space-between;
  padding: 30px 0;
  color: #f60;
}
.user-avater {
  overflow: hidden;
  border-radius: 100%;
  width: 60px;
  height: 60px;
  display: inline-block;
  vertical-align: top;
  img {
    width: 100%;
    height: 100%;
  }
}

.my-reply {
  padding: 8px 8px;
  background-color: #fafbfc;
  #replyInput {
    width: 100%;
    border: 2px solid rgb(255, 115, 0);
  }
  .reply-info {
    display: inline-block;
    margin-left: 5px;
    width: 90%;

    @media screen and (max-width: 1200px) {
      width: 80%;
    }

    .reply-input {
      min-height: 20px;
      margin: 5px;
      line-height: 40px;
      padding: 10px 10px;
      color: #080808;
      background-color: #fff;
      border-radius: 5px;

      &:empty:before {
        content: attr(placeholder);
      }

      &:focus:before {
        content: none;
      }

      &:focus {
        padding: 8px 8px;
        border: 2px solid blue;
        box-shadow: none;
        outline: none;
      }
    }
  }

  .reply-btn-box {
    height: 25px;
    margin: 10px 0;

    .reply-btn {
      position: relative;
      float: right;
      margin-right: 15px;
    }
  }
}

.my-comment-reply {
  margin-left: 50px;

  .reply-input {
  }
}

.author-title:not(:last-child) {
  border-bottom: 1px solid rgba(178, 186, 194, 0.3);
}

.author-title {
  padding: 10px;

  .header-img {
    display: inline-block;
    vertical-align: top;
  }

  .author-info {
    display: inline-block;
    margin-left: 5px;
    width: 60%;
    height: 60px;
    line-height: 25px;
    padding-left: 10px;
    > span {
      display: block;
      cursor: pointer;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }

    .author-name {
      color: rgb(231, 134, 171);
      font-size: 18px;
      font-weight: bold;
    }

    .author-time {
      font-size: 14px;
    }
  }

  .icon-btn {
    width: 20%;
    margin: 10px;
    padding: 10px !important;
    float: right;

    @media screen and (max-width: 1200px) {
      width: 20%;
      padding: 7px;
    }

    > span {
      cursor: pointer;
    }

    .iconfont {
      margin: 0 5px;
    }
  }

  .talk-box {
    padding-left: 80px;
    > p {
      margin: 0;
    }
    span {
      color: #00bfff;
    }

    .reply {
      font-size: 20px;
      color: #000;
    }
  }

  .reply-box {
    margin: 10px 0 0 50px;
    background-color: #efefef;
  }
}

.main {
  width: 1240px;
  margin: 0 auto;
}

.sturecshow_container {
  padding-top: 60px;
  width: 100%;

  .detailtitle {
    background-color: rgb(248, 241, 237);
    width: 100%;
    padding: 30px 0;

    .main {
      display: flex;
      justify-content: space-between;
    }

    h1 {
      span {
        color: #f60;
      }
    }

    h2 {
      font-size: 21px;
      font-weight: normal;
    }

    .like-btn {
      // .btn {
      // background-color: dodgerblue;
      // color: white;
      // width: 300px;
      // height: 47px;
      // border: 0;
      // font-size: 16px;
      // border-radius: 30px;
      // }
      display: flex;
      flex-direction: column;
      justify-content: center;
      padding-right: 50px;

      .niceButton5 {
        display: inline-block;
        width: 250px;
        border-radius: 50px;
        background-color: #f4511e;
        border: none;
        color: #ffff;
        text-align: center;
        font-size: 28px;
        font-weight: 400;
        padding: 18px;
        transition: all 0.5s;
        cursor: pointer;
        margin: 5px;
        vertical-align: middle;
      }

      .niceButton5 span {
        cursor: pointer;
        height: 20px;
        line-height: 20px;
        display: inline-block;
        position: relative;
        transition: 0.5s;
      }

      .niceButton5 span::after {
        content: '>';
        position: absolute;
        opacity: 0;
        top: 0;
        right: -20px;
        transition: 0.5s;
      }

      .niceButton5:hover span {
        padding-right: 30px;
      }

      .niceButton5:hover span::after {
        opacity: 1;
        right: 0;
      }
    }
  }

  .jdbxo {
    display: flex;
    font-size: 19px;
    padding: 50px 0px;

    .image-left {
      width: 500px;
      padding: 20px 30px 20px 20px;

      img {
        width: 400px;
      }
    }

    .jdrgtitle {
    }
  }
}

.footer {
  margin-top: 100px;
  border-top: 5px solid #f08b3d;
  color: #888;
  overflow: hidden;
  padding: 20px 0 0;
}

.footmain {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}

.footbox {
  width: 250px;
}

.widget-title {
  color: #333;
  text-transform: uppercase;
  margin-bottom: 25px;
  font-size: 14px;
}

.footer a {
  color: #888;
}

.foottext img {
  padding: 4px;
  border: solid 1px #424242;
  float: left;
  margin-right: 10px;
}

.foottext ul li {
  overflow: hidden;
  margin-bottom: 20px;
}

.footblog ul li {
  line-height: 28px;
}

.footimg {
  overflow: hidden;
}

.footimg ul li {
  float: left;
  margin-right: 20px;
  text-align: center;
}

.footimg ul li img {
  padding: 4px;
  border: solid 1px #424242;
  width: 50px;
  height: 50px;
}

.footabout p {
  line-height: 26px;
  margin-bottom: 20px;
}

.foottips {
  background: #1b1b1b;
  width: 100%;
}

.footlayout {
  background: #1b1b1b center center no-repeat;
  width: 1100px;
  margin: 0 auto;
  height: 90px;
  line-height: 90px;
  color: #999;
}

.custom-html {
  white-space: pre-wrap;
}
</style>
