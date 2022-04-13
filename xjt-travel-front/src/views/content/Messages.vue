<template>
	<div class="xjt-messages-view">
		<div style="font-size: 26px;padding: 15px 0;font-weight: 600;text-align: center;">给我留言</div>
		<div id="editor" style="width: 80%;margin: auto;z-index: 100;"></div>

		<div class="form-editor">
			<div class="guest-input">
				<el-form :inline="true" :rules="rules" ref="guestLogin" :model="guestLoginForm" class="demo-form-inline">
					<el-form-item prop="username">
						<el-input v-model="guestLoginForm.username" placeholder="用户名"></el-input>
					</el-form-item>
					<el-form-item prop="password">
						<el-input v-model="guestLoginForm.password" placeholder="密码"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<div class="creator-avatar">
				<el-image
						style="width: 60px; height: 60px;border: 1px solid #ddd;border-radius: 50%;"
						:src="$store.state.login_guest.avatar | filterImgUrl(that)"
						fit="fill"></el-image>
			</div>
			<div class="btns">
				<div class="guest-yesLogin" v-if="$store.state.login_guest.id">
					<el-button type="warning" size="mini" @click="previewEditorContent">预览</el-button>
					<el-button type="success" size="mini" @click="submitEditorContent">提交</el-button>
				</div>
				<div class="guest-noLogin" v-else>
					<el-button type="danger" size="medium" @click="handleGuestLogin">
						<i class="el-icon-user"></i>
						登录后留言
					</el-button>
				</div>
			</div>
		</div>

		<el-dialog title="留言预览" :visible.sync="dialogTableVisible">
			<div class="editor-html" v-html="editorContent"></div>
		</el-dialog>

		<div class="my-marginTop20">
			<el-card class="showComments-card my-marginTop10" style="border-top: 2px solid lightskyblue">
				<div slot="header" class="clearfix">
					<span class="my-title">留言板</span>
				</div>

				<div class="all-comments-container" style="border-left: 2px solid skyblue;padding-left: 10px;">
					<div v-for="(item1,index1) in messageList" :key="index1">
						<!--父评论-->
						<div class="my-marginTop20">
							<el-row :gutter="15" type="flex">
								<el-col style="width: 120px;">
									<div>
										<el-image
												class="my-border"
												style="width: 90px; height: 90px;border-radius: 50%;"
												:src="item1.avatar | filterImgUrl(that)">
										</el-image>
									</div>
								</el-col>
								<el-col :span="20">
									<div style=" width: 100%;">
										<p style="font-size: 16px;line-height: 30px;font-weight:bold;">{{item1.nickname}}</p>
										<p style="font-size: 13px;line-height: 30px;color: lightskyblue;" v-html="item1.content"></p>
										<div style="font-size: 10px;font-weight: lighter;">
											<span style="line-height: 30px;"> {{item1.create_time|filterFormatDateTime(that)}}</span>
											<span style="margin-left: 10px;line-height: 30px;" @click="handelReplyBtn(item1)">
														<el-link style="font-size: 5px;" :underline="false">回复</el-link>
													</span>
										</div>
									</div>
								</el-col>
							</el-row>
						</div>

						<!--子评论-->
						<div class="second-comments">
							<div v-for="(item2,index2) in item1.replayMessages" :key=index2
									 style="margin-top: 16px;margin-left: 120px;">
								<el-row :gutter="15" type="flex">
									<el-col style="width: 100px">
										<div style="padding-left: 10px;">
											<el-image
													style="width: 80px; height: 80px;border-radius: 50%;border: 1px solid #ddd;"
													:src="item2.avatar | filterImgUrl(that)">
											</el-image>
										</div>
									</el-col>
									<el-col :span="20" style="background-color: #eee;margin-left: 10px;padding: 5px;">
										<div style=" width: 100%;">
											<span style="font-size: 16px;line-height: 26px;font-weight:bold;">{{item2.nickname}}</span>
											<span style="font-size: 14px;line-height: 26px;font-weight: normal;color: #007eff;margin-left: 10px;">@{{item2.parent_message_nickname}}</span>
											<p style="font-size: 12px;line-height: 26px;" v-html="item1.content"></p>
										</div>
										<div>
											<span style="font-size: 10px;font-weight: normal;line-height: 26px;">  {{item2.create_time|filterFormatDateTime(that)}}</span>
											<span style="font-size: 13px;line-height: 26px;margin-left: 10px;" @click="handelReplyBtn(item2)">
														<el-link style="font-size: 5px;line-height: 26px;" class="hvr-fade">博主回复</el-link>
													</span>
										</div>
									</el-col>
								</el-row>
							</div>
						</div>
					</div>
				</div>
			</el-card>
		</div>
	</div>
</template>

<script>
	import WEditor from 'wangeditor'
	import hljs from 'highlight.js'
	import 'highlight.js/styles/monokai-sublime.css'

	export default {
		name: "Message",
		data() {
			return {
				messageList: [],
				dialogTableVisible: false,
				editorContent: "",
				$editor: null,
				guestLoginForm: {
					username: '',
					password: '',
				},
				rules: {
					username: [
						{required: true, message: '请输入用户名', trigger: 'blur'},
						{min: 1, max: 18, message: '长度在 1 到 18 个字符', trigger: 'blur'}
					],
					password: [
						{required: true, message: '请输入密码', trigger: 'blur'},
						{min: 1, max: 18, message: '长度在 1 到 18 个字符', trigger: 'blur'}
					],
				},

				//创建者
				creator_id:"",
				//回复对象
				currentReplyParentId: "-1",
			}
		},
		methods: {
			initWangEditor() {
				const editor = new WEditor("#editor");
				// 设置编辑区域高度为 300px
				editor.config.height = 300
				//编辑器 z-index 默认为 10000，可以自行调整
				editor.config.zIndex = 100
				//占位文字
				editor.config.placeholder = '请输入你的留言'
				// 配置菜单栏，设置不需要的菜单
				editor.config.excludeMenus = [
					'image',
					'video'
				]
				//配置颜色（文字颜色、背景色）
				editor.config.colors = [
					'#000000',
					'#eeece0',
					'#1c487f',
					'#4d80bf',
					'#ff0000',
					'#00ff00',
					'#0000ff',
					'#ffffff',
				]
				// 挂载highlight插件
				editor.highlight = hljs

				editor.create();
				this.$editor = editor;
			},

			handleGuestLogin() {
				console.log(this.guestLoginForm);
				this.$refs.guestLogin.validate((valid) => {
					if (valid) {
						this.$postRequest("/user/guest/login", this.guestLoginForm).then(res => {
							console.log(res);
							if (res.data.status === 200) {
								this.$notify.success("登录成功！");
								window.sessionStorage.setItem("login_guest", res.data.obj)
								this.$store.commit("updateLoginGuest", res.data.obj);
								this.creator_id = res.data.obj.id;
							} else {
								this.$notify.error("登录失败！");
							}
						})
					} else {
						this.$message.warning("请检查输入的格式");
						return false;
					}
				})
			},

			//处理回复按钮（滚动到顶部，赋值currentReplyParentId）
			handelReplyBtn(item) {
				let login_user = this.$store.state.login_guest;
				if (Object.keys(login_user).length > 0) {
					window.scrollTo(0, 0);
					this.creator_id = this.$store.state.login_user.id;
					this.currentReplyParentId = item.id;
				} else {
					this.$message.warning("尚未登录，请先去登录！")
					this.$router.push("/toLogin");
				}
			},

			//预览
			previewEditorContent() {
				this.dialogTableVisible = true;
				this.editorContent = this.$editor.txt.html();
			},
			submitEditorContent() {
				let login_user = this.$store.state.login_user;
				if (Object.keys(login_user).length > 0) {
					//向后台发请求提交Editor
					let params = {
						"user_id": this.$store.state.login_user.id,
						"content": this.$editor.txt.html(),
						"creator_id": this.creator_id,
						"parent_message_id": this.currentReplyParentId,
					}
					console.log(params);
					this.$postRequest("/message/add", params).then(res => {
						console.log(res);
						if (res.data.status === 200) {
							this.currentReplyParentId = "-1";
							this.creator_id = this.$store.state.login_guest.id;
							this.messageList.unshift(res.data.obj);
							this.$notify.success("成功留言!")
						}
					})
				} else {
					this.$message.warning("尚未登录，请先去登录！")
					this.$router.push("/toLogin");
				}
			},
			_getAllMessages(){
				let blogger_id = this.$store.state.login_user.id;
				if(blogger_id){
					this.$getRequest("/message/all?id="+blogger_id).then(res =>{
						console.log(res);
						if(res.data.status === 200){
							this.messageList.push(...res.data.obj);
						}
					})
				}
			},
		},
		mounted() {
			this.$nextTick(() => {
				this.initWangEditor();
			})
		},
		created() {
			this._getAllMessages();
		}
	}
</script>

<style lang="less" scoped>
	.form-editor {
		width: 80%;
		margin: auto;
		height: 80px;
		display: flex;

		.guest-input {
			flex: 1;
			margin-top: 10px;
		}

		.creator-avatar {
			width: 100px;
			margin-top: 10px;
		}

		.msg {
			width: 200px;
			line-height: 80px;
			align-items: center;
		}

		.btns {
			width: 160px;
			line-height: 80px;
			align-items: center;
		}

	}

	.editor-html {
		/* table 样式 */

		table {
			border-top: 1px solid #ccc;
			border-left: 1px solid #ccc;
		}

		table td,
		table th {
			border-bottom: 1px solid #ccc;
			border-right: 1px solid #ccc;
			padding: 3px 5px;
		}

		table th {
			border-bottom: 2px solid #ccc;
			text-align: center;
		}

		/* blockquote 样式 */

		blockquote {
			display: block;
			border-left: 8px solid #d0e5f2;
			padding: 5px 10px;
			margin: 10px 0;
			line-height: 1.4;
			font-size: 100%;
			background-color: #f1f1f1;
		}

		/* code 样式 */

		code {
			display: inline-block;
			*display: inline;
			*zoom: 1;
			background-color: #f1f1f1;
			border-radius: 3px;
			padding: 3px 5px;
			margin: 0 3px;
		}

		pre code {
			display: block;
		}

		/* ul ol 样式 */

		ul, ol {
			margin: 10px 0 10px 20px;
		}
	}
</style>