<template>
	<div class="traveller-notes-view">
		<div class="articles-wrapper">
			<div class="article-item" v-for="(item,index) in noteList" :key="index">
				<div class="traveller-article">
					<div class="article-title">{{item.title}}</div>

					<div class="article">
						<div class="left">
							<img v-lazy="item.thumbnail" alt="" style="border: 1px solid  #ddd;" width="100%" height="100%">
						</div>

						<div class="middle">
							<div class="article-desc">{{item.description}}</div>
							<div class="article-extra">
								<div class="name"><i class="el-icon-location"></i>By  <span class="author-name">{{author.name}}</span></div>
								<div class="pulishTime"><i class="el-icon-time"></i> {{item.publishTime.toString().slice(0,10)}}</div>
								<div class="viewCount"><i class="el-icon-view"></i> {{item.view}}</div>
							</div>
						</div>

						<div class="right">
							<el-button type="success" size="mini" @click="previewNoteContent(item)">预览</el-button>
							<el-button type="warning" size="mini" @click="updateNote(item)">编辑</el-button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="author-container">
			<div class="person" style="margin: 20px auto;text-align: center;">
				<div class="per_avatar hvr-float-shadow">
					<img v-lazy="author.avatar" alt=""
							 style="width: 160px; height: 160px;border-radius: 50%;border: 4px solid #fafafa;">
				</div>

				<div class="person-name">
					<h2 style="font-size: 28px;font-weight: bold;color: #ff8a87;">{{author.name}}</h2>
				</div>

				<div class="person_desc" style="color: #aaa;">{{author.description}}</div>

				<div class="btn" style="line-height: 80px;">
					<el-button @click="$router.push('/traveller/article/add?writer_id='+traveller_id)" type="warning" size="medium" icon="el-icon-plus">新增文章</el-button>
				</div>
			</div>
		</div>

		<el-dialog title="预览文章" :visible.sync="dialogTableVisible"
							 @close="currentArticle = {}"
							 append-to-body width="70%">
			<div class="previewNote" v-html="currentArticle">
			</div>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: "TravellerArticles",
		data() {
			return {
				traveller_id: "",		//旅行家id
				author: {},		//旅行家基本信息
				noteList: [],		//旅行家写的所有游记集合
				dialogTableVisible:false,
				currentArticle:"",
			}
		},
		methods: {
			initTraveller() {
				this.$getRequest("/traveller/author?id=" + this.traveller_id).then(res => {
					console.log(res);
					if (res.data.status === 200) {
						this.author = res.data.obj;
					} else {
						this.$message.error(res.data.msg);
					}
				})
			},
			initTravellerNoteList() {
				this.$getRequest("/traveller/note/articles/byWriterId?id=" + this.traveller_id).then(res => {
					console.log(res);
					if (res.data.status === 200) {
						this.noteList.splice(0);
						this.noteList.push(...res.data.obj);
					} else {
						this.$message.error(res.data.msg);
					}
				})
			},
			previewNoteContent(note) {
				this.$getRequest("/traveller/note/article/byId?id="+note.id).then(res =>{
					//console.log(res);
					if (res.data.status === 200) {
						this.currentArticle = res.data.obj.content
						this.dialogTableVisible = true;
					} else {
						this.$message.error(res.data.msg);
					}
				})
			},
			updateNote(item) {
				console.log(item);
			}
		},
		created() {
			console.log(this.$route);
			this.traveller_id = this.$route.params.id;

			this.initTraveller();
			this.initTravellerNoteList();
		},
	}
</script>

<style lang="less" scoped>
	.traveller-notes-view {
		width: 97%;
		margin: 50px auto;
		display: flex;

		.articles-wrapper{
			flex: 1;
			.traveller-article{
				border-bottom: 1px dashed #ddd;
				padding: 20px;
				text-align: left;
				&:hover{
					background-color: #fafafa;
				}

				.article-title{
					font-size: 25px;
					line-height: 50px;
					color: #F56C6C;
					text-shadow: 0 0 0.1em, 0 0 0.3em;
				}

				.article{
					margin-top: 10px;
					display: flex;
					.left{
						width: 280px;
						height: 160px;
						overflow: hidden;
					}
					.middle{
						flex: 1;
						margin-left: 20px;
						height: 160px;
						.article-desc{
							font-size: 14px;
							height: 120px;
							line-height: 26px;
							overflow: hidden;
							text-overflow: ellipsis;
							white-space: pre-line;
						}
						.article-extra{
							display: flex;
							line-height: 40px;
							div{
								width: 150px;
							}
							.pulishTime{
								flex: 1;
							}
							.author-name{
								color: #fd8a4d;
								&:hover{
									text-underline: #fd8a4d;
									cursor: pointer;
									border-bottom: 1px solid #fd8a4d;
								}
							}
						}
					}
					.right{
						width: 150px;
						height: 160px;
						line-height: 80px;
						text-align: center;
					}
				}
			}
		}

		.author-container {
			width: 400px;
			border: 1px solid #ddd;
			margin-left: 30px;
		}
	}

</style>
