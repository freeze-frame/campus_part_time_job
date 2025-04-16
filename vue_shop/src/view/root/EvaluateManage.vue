<template>
	<div>
		<div v-for="(item, i) in commentList" :key="i" class="author-title reply-father">
			<div class="author-info">
				<span class="author-name">用户UID：{{ item.uid }}</span>
				<!-- 时间 -->
				<span class="author-time">{{ item.createTime }}</span>
			</div>
			<!-- 点击评论-->
			<div class="icon-btn">
				<span @click="delOneFn(item.comId)">
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
						<span @click="delTwoFn(reply.repId)">
							<i class="iconfont el-icon-delete-solid"></i>
						</span>
					</div>
					<div class="author-info">
						<!-- 名字 -->
						<span class="author-name">用户UID：{{ reply.uid }}</span>
						<!-- 时间 -->
						<span class="author-time">{{ reply.createTime }}</span>
					</div>
					<!-- 回复 -->
					<div class="talk-box">
						<p>
							<span>回复 用户UID：{{ item.uid }}:</span>
							<span class="reply">{{ reply.repRemark }}</span>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { getCommentAPI, delCommentAPI, delCommentReplyAPI } from '@/api/AxiosAPI';
export default {
	created() {
		this.getCommentFn();
	},
	data() {
		return {
			commentList: null,
		};
	},
	methods: {
		//删除子评论
		async delTwoFn(repId) {
			const { data: res } = await delCommentReplyAPI(repId);
			if (res.code !== 200) return this.$message.error(res.message);
			this.getCommentFn();
		},
		//删除父评论
		async delOneFn(comId) {
			const { data: res } = await delCommentAPI(comId);
			if (res.code !== 200) return this.$message.error(res.message);
			this.getCommentFn();
		},
		async getCommentFn() {
			const { data: res } = await getCommentAPI();
			if (res.code !== 200) return this.$message.error(res.message);
			this.commentList = res.data;
		},
	},
};
</script>

<style lang="less" scoped>
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
		width: flex;
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
