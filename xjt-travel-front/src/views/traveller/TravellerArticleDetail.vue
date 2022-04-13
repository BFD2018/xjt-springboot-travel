<template>
	<div class="traveller-author-view">
		<div class="main">
			<div class="travs_position">
				<router-link to="/traveller" title="旅行家专栏">旅行家专栏</router-link>
				<strong> >{{aboutArticleInfo.writer_name}} 的专栏</strong>
				<span style="color: #fd8a4d"> >{{aboutArticleInfo.title}}</span>
			</div>

			<h1>{{aboutArticleInfo.title}}</h1>

			<div class="top-extra">
				<div class="author">By <span>{{aboutArticleInfo.writer_name}}</span></div>
				<div class="publish_time">
					<i class="el-icon-time"></i>
					{{aboutArticleInfo.publish_time}}
				</div>
				<div class="view">
					<i class="el-icon-view"></i>
					已经有 <span>{{aboutArticleInfo.view}}</span> 人阅读
				</div>
			</div>

			<div class="article-wrapper" v-html="aboutArticleInfo.content"></div>
		</div>

		<div class="side">
			<div class="person" style="margin: 20px auto;text-align: center;">
				<div class="per_avatar hvr-float-shadow" >
					<el-image
							style="width: 160px; height: 160px;border-radius: 50%;border: 4px solid #fafafa;"
							:src="aboutArticleInfo.writer_avatar"
							fit="fill"></el-image>
				</div>

				<div class="person-name">
					<h2 @click="$router.push('/traveller/author/' + aboutArticleInfo.writer_id)"
							class="hvr-underline-from-center">{{aboutArticleInfo.writer_name}}</h2>
				</div>

				<div class="person_desc" style="color: #aaa;">{{aboutArticleInfo.writer_description}}</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: "TravellerNoteDetail",
		components:{
		},
		data(){
			return{
				//文章及其作者信息
				aboutArticleInfo:{
					writer_description: "",
					thumbnail: "",
					view: "",
					writer_avatar: "",
					publish_time: "",
					description: "",
					id: "",
					detail_content:"",
					title: "",
					writer_id: "",
					writer_name: "",
					content: "",
				},
			}
		},
		methods:{
			//初始化数据
			initTravellerAuthorArtice(){
				this.$getRequest("/traveller/note/article/byId?id="+this.aboutArticleInfo.id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.aboutArticleInfo = res.data.obj;
					}
				})
			},
		},
		created() {
			// console.log(this.$route);
			this.aboutArticleInfo.id = this.$route.params.id

			this.initTravellerAuthorArtice();
		},
	}
</script>

<style lang="less" scoped>
	.traveller-author-view{
		width: 100%;
		display: flex;
		.main{
			flex: 1;
			.travs_position{
				height: 18px;
				margin: 23px 0 33px;
			}
			.top-extra{
				display: flex;
				padding: 0 60px 0 0;
				line-height: 40px;
				.publish_time{
					flex: 1;
					margin-left: 20px;
					margin-right: 20px;
				}
				span{
					color: #fd8a4d;
					font-weight: bold;
				}
			}
			.article-wrapper{
				padding: 0 60px 0 0;
			}
		}

		.side{
			width: 400px;
			border: 1px solid #ddd;
      border-radius: 8px;
			padding: 0 10px;

			h2{
				font-size: 28px;
				font-weight: bold;
				color: #ff8a87;
			}
		}
	}
</style>
