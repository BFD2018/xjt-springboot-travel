<template>
	<div class="traveller-author-view">
		<div class="main">
			<div class="travs_position">
				<router-link to="/traveller" title="旅行家专栏">旅行家专栏</router-link>
				<strong> >{{author.name}} 的专栏</strong>
			</div>

			<div class="articles-wrapper">
				<div class="article-item" v-for="(item,index) in travellerAuthorArticelList" :key="index">
					<div class="traveller-article">
						<div class="article-title hvr-underline-from-center" @click="$router.push('/traveller/article/'+item.id)">{{item.title}}</div>

						<div class="article-item">
							<div class="left">
								<el-image class="hvr-grow"
													style="width: 100%; height: 100%;"
													:src="item.thumbnail"
													fit="fill"></el-image>
							</div>
							<div class="right">
								<div class="article-desc">{{item.description}}</div>
								<div class="article-extra">
									<div class="name"><i class="el-icon-location"></i>By  <span class="author-name">{{author.name}}</span></div>
									<div class="pulishTime"><i class="el-icon-time"></i> {{item.publishTime.toString().slice(0,10)}}</div>
									<div class="viewCount"><i class="el-icon-view"></i> {{item.view}}</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="side">
			<div class="person" style="margin: 20px auto;text-align: center;">
				<div class="per_avatar hvr-float-shadow" >
					<el-image
										style="width: 160px; height: 160px;border-radius: 50%;border: 4px solid #fafafa;"
										:src="author.avatar"
										fit="fill"></el-image>
				</div>

				<div class="person-name">
					<h2 style="font-size: 28px;font-weight: bold;color: #ff8a87;">{{author.name}}</h2>
				</div>

				<div class="person_desc" style="color: #aaa;">{{author.description}}</div>
			</div>
		</div>
	</div>
</template>

<script>
	import NavBar from "@/components/navbar/NavBar.vue"
	import TravellerArticleItem from "./comps/TravellerArticleItem";

	export default {
		name: "Traveller",
		components:{
			TravellerArticleItem,
			NavBar,
		},
		data(){
			return{
				author:{
					id:"",
					name:"",
					avatar:"",
					description:"",
				},
				//旅行家专栏文章列表
				travellerAuthorArticelList:[],
			}
		},
		methods:{
			//初始化数据
			initAuthor(){
				this.$getRequest("/traveller/author?id="+this.author.id).then(res => {
					console.log(res);
					if(res.data.status === 200){
						this.author = res.data.obj;
					}
				})
			},

			initTravellerAuthorArticeList(){
				this.$getRequest("/traveller/note/articles/byWriterId?id="+this.author.id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.travellerAuthorArticelList.splice(0);
						this.travellerAuthorArticelList.push(...res.data.obj)
					}
				})
			},
		},
		created() {
			this.author.id = this.$route.params.id

			this.initAuthor();
			this.initTravellerAuthorArticeList();
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
			.articles-wrapper{
				.traveller-article{
					border-bottom: 1px dashed #ddd;
					padding: 20px 30px;
					&:hover{
						background-color: #fafafa;
					}

					.article-title{
						font-size: 26px;
						line-height: 50px;
					}

					.article-item{
						margin-top: 10px;
						display: flex;
						.left{
							width: 280px;
							height: 160px;
							overflow: hidden;
						}
						.right{
							flex: 1;
							margin-left: 20px;
							.article-desc{
								font-size: 14px;
								height: 90px;
								line-height: 20px;
								overflow: hidden;
								text-overflow: ellipsis;
								white-space: pre-line;
							}
							.article-extra{
								display: flex;
								line-height: 40px;
								div{
									margin-left: 20px;
									width: 100px;
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
					}
				}
			}
		}
		.side{
			width: 400px;
			border: 1px solid red;
			padding: 0 10px;
		}
	}

</style>
