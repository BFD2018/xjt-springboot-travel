<template>
	<div class="blog-detail-view">
		<el-card class="blog-card">
			<div slot="header" class="clearfix">
				<el-row type="flex" class="baseInfo" style="height: 60px;line-height: 60px;">
					<el-col :span="2">
						<el-image
								class="my-border"
								style="width: 60px;height: 60px;"
								:src="blogInfo.avatar"
								fit="cover"></el-image>
					</el-col>
					<el-col :span="1">{{blogInfo.username}}</el-col>
					<el-col :span="4">
						<i class="el-icon-date"> {{blogInfo.create_time | filterFormatDateTime(that)}}</i></el-col>
					<el-col :span="1">
						<i class="el-icon-view"> {{blogInfo.views}}</i></el-col>
					<el-col :span="1">
						<i class="el-icon-chat-square"> {{blogInfo.comment_count}} </i></el-col>
					<el-col :span="2" :offset="14">
						<el-button type="info" size="mini" @click="$router.back(-1)">
							<i class="el-icon-d-arrow-right">返回</i> </el-button>
					</el-col>
				</el-row>
			</div>

			<div class="blog-content my-padding">
				<el-image
						class="my-border"
						style="width: 100%;height: 260px;"
						:src="blogInfo.first_picture"
						fit="cover"></el-image>
				<div class="my-title my-marginTop20 my-middle">{{blogInfo.title}}</div>

				<div class="tags my-middle my-marginTop20">
					<el-tag type="success">{{blogInfo.flag}}</el-tag>
					<el-tag type="success" class="my-marginLeft10">{{blogInfo.type_name}}</el-tag>
				</div>

				<div class="my-marginTop20">
					<mavon-editor
							:value="blogInfo.content"
							defaultOpen="preview"
							:boxShadow="false"
							style="z-index:1;height:80vh"
							:editable="false"
							:subfield="false"
							:toolbarsFlag="false">
					</mavon-editor>
				</div>

				<div class="my-divider"></div>

				<!--评论-->
				<div class="my-marginTop20">
					<el-card class="showComments-card my-marginTop10" style="border-top: 2px solid lightskyblue">
						<div slot="header" class="clearfix">
							<span class="my-title">评论</span>
							<el-button style="float: right; padding: 3px 0" type="text" @click="handelReplyBtn('-1')">评论博客</el-button>
						</div>

						<div class="all-comments-container" style="border-left: 2px solid skyblue;padding-left: 10px;">
							<div v-for="(item1,index1) in allComments" :key="index1">
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
												<p style="font-size: 13px;line-height: 30px;color: lightskyblue;">{{item1.content}}</p>
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
									<div  v-for="(item2,index2) in item1.replyComments" :key=index2
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
													<span style="font-size: 14px;line-height: 26px;font-weight: normal;color: #007eff;margin-left: 10px;">@{{item2.parent_comment_nickname}}</span>
													<p style="font-size: 12px;line-height: 26px;">{{item2.content}}</p>
												</div>
												<div>
													<span style="font-size: 10px;font-weight: normal;line-height: 26px;">  {{item2.create_time|filterFormatDateTime(that)}}</span>
													<span style="font-size: 13px;line-height: 26px;margin-left: 10px;" @click="handelReplyBtn(item2)">
														<el-link style="font-size: 5px;line-height: 26px;">回复</el-link>
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
		</el-card>

		<el-dialog
				title="请输入评论..."
				:visible.sync="inputCommentDialogVisible"
				width="30%">
			<span>
				<vue-emoji-picker @sumit_comment="submitReply"/>
			</span>
		</el-dialog>

	</div>
</template>

<script>
	import {_getBlogDetailByBlogId} from "@/network/api";

	import VueEmojiPicker from "@/components/v-emoji-input/VueEmojiPicker.vue";

	export default {
		name: "BlogDetail",
		components:{
			VueEmojiPicker,
		},
		data(){
			return{
				blog_id:"",
				blogInfo: {},
				commentForm:{},		//评论表单
				allComments:[],

				currentReplyParentId:"",		//对当前评论id 进行回复
				inputCommentDialogVisible:false,
			}
		},
		methods:{
			submitReply(val){
				//console.log(val);		//输入的内容
				let params = {
					"user_id":this.$store.state.login_user.id,
					"content":val,
					"blog_id":this.blog_id,
					"parent_comment_id":this.currentReplyParentId,
				}
				console.log(params);
				this.$postRequest("/comment/save",params).then(res =>{
					console.log(res);
					this.inputCommentDialogVisible = false;
				this.currentReplyParentId = "";
					if(res.data.status === 200){
						this.$notify.success("评论成功!")
					}
				})
			},
			handelReplyBtn(item){
				let login_user = this.$store.state.login_user;
				if(Object.keys(login_user).length > 0){
					this.inputCommentDialogVisible = true;
					//当前评论id
					if(item === '-1'){
						//对博客评论
						this.currentReplyParentId = "-1";
					}else{
						//回复
						this.currentReplyParentId = item.id;
					}
				}else{
					this.$message.warning("尚未登录，请先去登录！")
					this.$router.push("/toLogin");
				}
			},
		},
		created() {
			const blog_id = this.$route.query.blog_id;
			this.blog_id = blog_id;

			_getBlogDetailByBlogId(blog_id).then(res =>{
				console.log(res);
				this.blogInfo = res.data.obj;
			})

			this.$getRequest("/comment/getAllByBlogId?blog_id="+blog_id).then(res =>{
				console.log(res);
				if(res.data.status === 200){
					this.allComments.push(...res.data.obj);
				}
			})
		}
	}
</script>

<style scoped>
	.blog-detail-view{
		width: 80%;
		margin: auto;
	}
	.post-comment-card{
		width: 100%;
		height: 440px;
	}
</style>