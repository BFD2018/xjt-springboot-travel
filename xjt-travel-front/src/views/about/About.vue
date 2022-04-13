<template>
	<div class="xjt-about-view">
		<div class="about-left">
			<div class="header">
				<div class="title">给我留言</div>

				<!--wang-editor编辑器-->
				<div id="editor" style="width: 80%;margin: auto;"></div>

				<div class="btns-wrapper">
					<el-button type="danger" @click="submitEditorContent">发表</el-button>
				</div>
			</div>

			<div class="main-content">
				<nav-bar class = "navBarBox">
					<div slot = "left-slot" style="border-left: 4px solid #fd8a4d;padding-left: 10px;">访客留言板</div>
					<div slot = "right-slot" style="font-size: 22px;color: rgba(32, 152, 209,1);">共 {{messageList.length}} 条</div>
				</nav-bar>

				<div class="all-messages-wrapper" style="margin-left: 10px;">
					<div v-for="(item1,index1) in messageList" :key="index1" style="border: 1px solid #eee;border-radius: 5px;padding: 10px;margin-top: 15px">
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
										<div style="background-color:#f4f4f4;font-size: 13px;line-height: 40px;
												color: lightskyblue;max-height: 100px;overflow-y: scroll;" v-html="item1.content"></div>

										<div style="font-size: 10px;font-weight: lighter;margin-top: 10px;">
											<span style="line-height: 30px;"><i class="el-icon-date"></i> {{item1.create_time|filterFormatDateTime(that)}}</span>

											<span style="margin-left: 15px;" @click="goodToMessage(index1,item1)">
												<el-badge :value="item1.counts" class="good-badge">
												<svg t="1641884617464" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2559" width="26" height="26"><path d="M885.9 533.7c16.8-22.2 26.1-49.4 26.1-77.7 0-44.9-25.1-87.4-65.5-111.1a67.67 67.67 0 0 0-34.3-9.3H572.4l6-122.9c1.4-29.7-9.1-57.9-29.5-79.4-20.5-21.5-48.1-33.4-77.9-33.4-52 0-98 35-111.8 85.1l-85.9 311h-0.3v428h472.3c9.2 0 18.2-1.8 26.5-5.4 47.6-20.3 78.3-66.8 78.3-118.4 0-12.6-1.8-25-5.4-37 16.8-22.2 26.1-49.4 26.1-77.7 0-12.6-1.8-25-5.4-37 16.8-22.2 26.1-49.4 26.1-77.7-0.2-12.6-2-25.1-5.6-37.1zM112 528v364c0 17.7 14.3 32 32 32h65V496h-65c-17.7 0-32 14.3-32 32z" p-id="2560" fill="#d81e06"></path></svg>
											</el-badge>
											</span>

											<el-tag type="success" style="margin-left: 30px;" @click="handelReplyBtn(item1)">站长回复</el-tag>
										</div>
									</div>
								</el-col>
							</el-row>
						</div>

						<!--子评论-->
						<div class="second-comments">
							<div v-for="(item2,index2) in item1.replyMessages" :key=index2
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
									<el-col :span="20" style="background-color: #eee;margin-left: 10px;padding: 5px 10px;">
										<div style=" width: 100%;">
											<span style="font-size: 16px;line-height: 26px;font-weight:bold;">{{item2.nickname}}</span>
											<span style="font-size: 14px;line-height: 26px;font-weight: normal;color: #007eff;margin-left: 10px;">@{{item1.nickname}}</span>
											<div style="font-size: 12px;line-height: 40px;background-color:#f4f4f4;
											max-height: 100px;overflow-y: scroll;" v-html="item2.content"></div>
										</div>

										<div>
											<span style="font-size: 10px;font-weight: normal;line-height: 26px;"><i class="el-icon-date"></i> {{item2.create_time|filterFormatDateTime(that)}}</span>
											<span style="margin-left: 15px;" @click="goodToReply(index1,index2,item2)">
												<el-badge :value="item2.counts" class="good-badge">
												<svg t="1641884617464" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2559" width="26" height="26"><path d="M885.9 533.7c16.8-22.2 26.1-49.4 26.1-77.7 0-44.9-25.1-87.4-65.5-111.1a67.67 67.67 0 0 0-34.3-9.3H572.4l6-122.9c1.4-29.7-9.1-57.9-29.5-79.4-20.5-21.5-48.1-33.4-77.9-33.4-52 0-98 35-111.8 85.1l-85.9 311h-0.3v428h472.3c9.2 0 18.2-1.8 26.5-5.4 47.6-20.3 78.3-66.8 78.3-118.4 0-12.6-1.8-25-5.4-37 16.8-22.2 26.1-49.4 26.1-77.7 0-12.6-1.8-25-5.4-37 16.8-22.2 26.1-49.4 26.1-77.7-0.2-12.6-2-25.1-5.6-37.1zM112 528v364c0 17.7 14.3 32 32 32h65V496h-65c-17.7 0-32 14.3-32 32z" p-id="2560" fill="#d81e06"></path></svg>
											</el-badge>
											</span>
										</div>
									</el-col>
								</el-row>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="about-right">
			<div class="site-author">
				<div class="author-avatar hvr-float-shadow" style="overflow: hidden;margin: auto;width: 100px;height: 100px ;border: 2px solid #ccc;border-radius: 50%;">
					<img v-lazy="$store.state.login_user.avatar" width="100%" height="100%" alt="">
				</div>
				<div class="name">小熊学编程</div>
				<p>小熊学习springboot+vue开发出的旅游网站，记录学习的心得体会</p>
				<div class="site-state-wrapper">
					<div class="state-item">
						<p class="num">615</p>
						<p>日志</p>
					</div>
					<div class="state-item" style="border-left: 1px solid #ddd;border-right: 1px solid #ddd;">
						<p class="num">2</p>
						<p>分类</p>
					</div>
					<div class="state-item">
						<p class="num">312</p>
						<p>标签</p>
					</div>
				</div>
				<div class="author-links">
					<a class="link-item" href="https://github.com/BFD2018/xjt-springboot-travel">
						<svg t="1641816853945" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2157" width="18" height="18"><path d="M511.6 76.3C264.3 76.2 64 276.4 64 523.5 64 718.9 189.3 885 363.8 946c23.5 5.9 19.9-10.8 19.9-22.2v-77.5c-135.7 15.9-141.2-73.9-150.3-88.9C215 726 171.5 718 184.5 703c30.9-15.9 62.4 4 98.9 57.9 26.4 39.1 77.9 32.5 104 26 5.7-23.5 17.9-44.5 34.7-60.8-140.6-25.2-199.2-111-199.2-213 0-49.5 16.3-95 48.3-131.7-20.4-60.5 1.9-112.3 4.9-120 58.1-5.2 118.5 41.6 123.2 45.3 33-8.9 70.7-13.6 112.9-13.6 42.4 0 80.2 4.9 113.5 13.9 11.3-8.6 67.3-48.8 121.3-43.9 2.9 7.7 24.7 58.3 5.5 118 32.4 36.8 48.9 82.7 48.9 132.3 0 102.2-59 188.1-200 212.9 23.5 23.2 38.1 55.4 38.1 91v112.5c0.8 9 0 17.9 15 17.9 177.1-59.7 304.6-227 304.6-424.1 0-247.2-200.4-447.3-447.5-447.3z" p-id="2158"></path></svg>
						Github
					</a>
					<div class="link-item">
						<svg t="1641886986616" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3449" width="18" height="18"><path d="M972.8 934.274509 998.4 908.54902 127.868001 908.54902C85.502225 908.54902 51.2 874.019706 51.2 831.263017L51.2 111.058823 25.6 136.784314 896.131998 136.784314C938.452011 136.784314 972.8 171.324278 972.8 213.860934L972.8 278.566694C972.8 292.77449 984.26151 304.292183 998.4 304.292183 1012.53849 304.292183 1024 292.77449 1024 278.566694L1024 213.860934C1024 142.916556 966.736828 85.333333 896.131998 85.333333L25.6 85.333333 0 85.333333 0 111.058823 0 831.263017C0 902.415639 57.205646 960 127.868001 960L998.4 960 1024 960 1024 934.274509 1024 457.69849C1024 443.490694 1012.53849 431.973001 998.4 431.973001 984.26151 431.973001 972.8 443.490694 972.8 457.69849L972.8 934.274509Z" p-id="3450"></path><path d="M512.651558 567.164817C520.64791 572.874498 531.187889 573.490406 539.788919 568.750601L1002.624789 313.693694C1015.021598 306.862133 1019.560139 291.225196 1012.761901 278.767619 1005.963665 266.310041 990.403006 261.749254 978.006197 268.580816L515.170327 523.637722 542.307689 525.223505 194.028065 276.539467C182.502788 268.310009 166.520953 271.027598 158.331639 282.609372 150.142325 294.191146 152.846657 310.251322 164.371935 318.480781L512.651558 567.164817Z" p-id="3451"></path></svg>
						邮件
					</div>
					<a class="link-item" href="https://space.bilibili.com/241154045">
						<svg t="1641887033872" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3965" width="18" height="18"><path d="M306.005333 117.632L444.330667 256h135.296l138.368-138.325333a42.666667 42.666667 0 0 1 60.373333 60.373333L700.330667 256H789.333333A149.333333 149.333333 0 0 1 938.666667 405.333333v341.333334a149.333333 149.333333 0 0 1-149.333334 149.333333h-554.666666A149.333333 149.333333 0 0 1 85.333333 746.666667v-341.333334A149.333333 149.333333 0 0 1 234.666667 256h88.96L245.632 177.962667a42.666667 42.666667 0 0 1 60.373333-60.373334zM789.333333 341.333333h-554.666666a64 64 0 0 0-63.701334 57.856L170.666667 405.333333v341.333334a64 64 0 0 0 57.856 63.701333L234.666667 810.666667h554.666666a64 64 0 0 0 63.701334-57.856L853.333333 746.666667v-341.333334A64 64 0 0 0 789.333333 341.333333zM341.333333 469.333333a42.666667 42.666667 0 0 1 42.666667 42.666667v85.333333a42.666667 42.666667 0 0 1-85.333333 0v-85.333333a42.666667 42.666667 0 0 1 42.666666-42.666667z m341.333334 0a42.666667 42.666667 0 0 1 42.666666 42.666667v85.333333a42.666667 42.666667 0 0 1-85.333333 0v-85.333333a42.666667 42.666667 0 0 1 42.666667-42.666667z" p-id="3966"></path></svg>
						B站
					</a>
					<a class="link-item" href="https://www.yuque.com/yuquexiongjt/qh2h6g">
						<svg t="1641887129124" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4993" width="18" height="18"><path d="M863.418182 698.181818c-13.963636 0-23.272727 9.309091-23.272727 23.272727v186.181819c0 39.563636-25.6 69.818182-69.818182 69.818181H134.981818c-41.890909 0-86.109091-30.254545-86.109091-69.818181V114.036364C48.872727 74.472727 93.090909 46.545455 134.981818 46.545455h635.345455c44.218182 0 69.818182 30.254545 69.818182 69.818181v325.818182c0 13.963636 9.309091 23.272727 23.272727 23.272727s23.272727-9.309091 23.272727-23.272727V116.363636c0-65.163636-46.545455-116.363636-116.363636-116.363636H134.981818C69.818182 0 2.327273 46.545455 2.327273 114.036364V907.636364c0 69.818182 69.818182 116.363636 132.654545 116.363636h635.345455c69.818182 0 116.363636-51.2 116.363636-116.363636v-186.181819c0-13.963636-11.636364-23.272727-23.272727-23.272727z m153.6-260.654545c-9.309091-9.309091-23.272727-9.309091-32.581818 0L512 889.018182c-9.309091 9.309091-9.309091 23.272727 0 32.581818 9.309091 9.309091 23.272727 9.309091 32.581818 0l472.436364-451.490909c6.981818-6.981818 6.981818-23.272727 0-32.581818zM630.690909 302.545455h-372.363636c-13.963636 0-23.272727 9.309091-23.272728 23.272727s9.309091 23.272727 23.272728 23.272727h372.363636c13.963636 0 23.272727-9.309091 23.272727-23.272727s-11.636364-23.272727-23.272727-23.272727z m0 232.727272h-372.363636c-13.963636 0-23.272727 9.309091-23.272728 23.272728s9.309091 23.272727 23.272728 23.272727h372.363636c13.963636 0 23.272727-9.309091 23.272727-23.272727s-11.636364-23.272727-23.272727-23.272728z" fill="#666666" p-id="4994"></path></svg>
						语雀
					</a>
				</div>
			</div>

			<div class="author-quote" style="margin-top: 20px;">
				<div style="border-left: 4px solid #fd8a4d;padding-left: 10px;font-size: 26px;line-height: 60px;">收藏语录</div>

				<div class="content">
					<div class="hvr-fade my-border-padding">在不幸的源头，总有一桩意外。在幸福的源头，总有一桩巧合</div>
					<div class="hvr-fade my-border-padding">孤独，不一定不快乐；得到，不一定能长久；失去，不一定不再拥有</div>
					<div class="hvr-fade my-border-padding">有的人对你好，是因为你对他好，有的人对你好，是因为懂得你的好</div>
					<div class="hvr-fade my-border-padding">绝望是在每一次的失望中累积出来的，而爱则是被不知感恩的人消耗殆尽的</div>
					<div class="hvr-fade my-border-padding">小时候就觉得酒很苦，为什么大人们还是那么爱喝，长大后才发现比起生活，酒确实甜的许多</div>
					<div class="hvr-fade my-border-padding">随口答应，还不如认真拒绝。承诺有限，精力紧张，大家可以学习另外两种技能：随口拒绝讨厌鬼，认真答应心上人</div>
				</div>
			</div>
		</div>

	</div>
</template>

<script>
	import NavBar from "@/components/navbar/NavBar.vue"

	import WEditor from 'wangeditor'
	import hljs from 'highlight.js'
	import 'highlight.js/styles/monokai-sublime.css'

	export default {
		name: "About",
		components:{
			NavBar,
		},
		data(){
			return{
				userInfo:this.$store.state.login_user,
				inputMessage:"",

				messageList:[],
				currentReplyParentId:"-1",		//默认是直接给站长留言 父id=-1
			}
		},
		methods:{
			initWangEditor() {
				const editor = new WEditor("#editor");
				// 设置编辑区域高度为 300px
				editor.config.height = 300
				//编辑器 z-index 默认为 10000，可以自行调整
				editor.config.zIndex = 1
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

			submitEditorContent() {
				let login_user = this.$store.state.login_user;
				if (Object.keys(login_user).length > 0) {
					//向后台发请求提交Editor
					let params = {
						"user_id": this.$store.state.login_user.userId,
						"content": this.$editor.txt.html(),
						"parent_id": this.currentReplyParentId,
					}
					console.log(params);
					this.$postRequest("/message/save", params).then(res => {
						console.log(res);
						if (res.data.status === 200) {
							this.currentReplyParentId = "-1";
							this.messageList.unshift(params);
							this.$notify.success("留言成功!")
						}
					})
				} else {
					this.$message.warning("尚未登录，请先去登录！")
					this.$router.push("/toLogin");
				}
			},

			initMessageData(){
				this.$getRequest("/message/all").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.messageList.splice(0);
						this.messageList.push(...res.data.obj);
					}
				})
			},
			/*对留言点赞，index是索引，val是包含message的对象*/
			goodToMessage(index,val){
				this.$getRequest("/message/goodById?id="+val.id).then(res =>{
					if(res.data.status === 200){
						this.$message.success("成功点赞！")
						//更新页面上的数据
						this.messageList[index].counts++;
					}
				})
			},
			/*对站长的回复点赞，par是父id，index是索引，val是包含message的对象*/
			goodToReply(par,index,val){
				this.$getRequest("/message/goodById?id="+val.id).then(res =>{
					if(res.data.status === 200){
						this.$message.success("成功点赞！")
						//更新页面上的数据
						this.messageList[par]["replyMessages"][index].counts++;
					}
				})
			},

			handelReplyBtn(val){
				window.scrollTo(0, 0);
				this.currentReplyParentId = val.id;
				this.$message.success("请输入回复内容...")
			},
		},
		mounted() {
			this.$nextTick(() => {
				this.initWangEditor();
			})
		},
		created() {
			this.initMessageData();
		}
	}
</script>

<style lang="less" scoped>
	.xjt-about-view{
		width: 100%;
		display: flex;
		.about-left{
			flex: 1;
			.header{
				margin: auto;
				.title{
					font-size: 30px;
					line-height: 60px;
					text-align: center;
				}
				.btns-wrapper{
					margin-top: 10px;
					margin-bottom: 10px;
					text-align: center;
				}
			}
			.main-content{

			}
		}

		.about-right{
			width: 400px;
			margin-left: 20px;
			padding: 10px;
			.site-author{
				padding: 10px;
				border: 1px solid #ddd;
				border-radius: 5px;
				text-align: center;
				.name{
					font-size: 24px;
					line-height: 50px;
				}
				.site-state-wrapper{
					width: 70%;
					margin: auto;
					display: flex;
					.state-item{
						flex: 1;
					}
				}
				.author-links{
					display: grid;
					grid-template-columns: 50% 50%;
					.link-item{
						padding: 10px 0;
            text-decoration: none;
						&:hover{
							background-color: #eeeeee;
						}
					}
				}
			}
		}
	}

</style>
