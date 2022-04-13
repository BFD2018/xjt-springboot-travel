<template>
	<div class="traveller-view">
		<div class="main">
			<div class="thumb">
				<!--轮播图-->
				<div class="carousel-images">
					<el-carousel trigger="click" :interval="3000" type="card" indicator-position="outside" height="360px" arrow="always">
						<el-carousel-item v-for="(item,index) in travellerArticelList" :key="index">
							<div class="carousel-item">
								<el-image style="width: 100%; height: 100%"
													:src="item.thumbnail"
													fit="cover">
								</el-image>
							</div>
						</el-carousel-item>
					</el-carousel>
				</div>
			</div>

			<div class="articles">
				<div class="article-nav" style="flex: 1;display: flex;">
					<div class="navHot"
							 @click="tabToggleClick('最新专栏')"
							 :class="{active:currentTab=='最新专栏'}"
							 style="line-height: 50px;font-size: 24px;padding: 0 10px;">最新专栏</div>
					<div class="navNew"
							 style="line-height: 50px;font-size: 24px;padding: 0 10px;"
							 :class="{active:currentTab=='最热专栏'}"
							 @click="tabToggleClick('最热专栏')">最热专栏</div>
				</div>

				<div class="article-content">
					<div class="article-item" v-for="(item,index) in travellerArticelList" :key="index">
						<traveller-article-item :article="item"></traveller-article-item>
					</div>
				</div>
			</div>

			<!--分页-->
			<div style="margin-top: 20px;" class="my-border-padding">
				<el-pagination
						background
						@current-change="handleCurrentChange"
						:current-page.sync="currentPage"
						:page-size="10"
						layout="total, prev, pager, next"
						:total="total">
				</el-pagination>
			</div>
		</div>

		<div class="side">
			<div class="traveller-logo">
				<el-image
						style="width: 100%; height: auto;"
						:src="require('@/assets/images/common/traveller-logo.png')"
						fit="fill">
				</el-image>
			</div>
			<div class="join-us">
				<div class="wx">
					<el-image
							style="width: 100%; height: auto;"
							:src="require('@/assets/images/common/wx.png')"
							fit="fill"></el-image>
				</div>
				<div class="QQ">
					<el-image
							style="width: 100%; height: auto;"
							:src="require('@/assets/images/common/qq.jpg')"
							fit="fill"></el-image>
				</div>
			</div>
			<div class="recommed-writer-list">
				<div class="writer-wrapper">
					<nav-bar class = "navBarBox">
						<div slot = "left-slot">推荐作者</div>
					</nav-bar>

					<div class="writer-item" v-for="(item,index) in recommedWriterList" :key="index">
						<div class="writer-info">
							<div class="writer-avatar hvr-float-shadow">
								<el-image
										style="width: 120px; height: 120px;border-radius: 50%;"
										:src="item.avatar"
										fit="fill"></el-image>
							</div>
							<div class="txt">
								<div class="writer-name hvr-underline-reveal" @click="$router.push('/traveller/author/'+item.id)">{{item.name}}</div>
								<div class="writer-desc">{{item.description}}</div>
							</div>
						</div>
					</div>
				</div>
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
				currentTab:"最新专栏",
				//推荐作者列表
				recommedWriterList:[],

				//旅行家专栏文章列表
				travellerArticelList:[],

				//分页
				total:0,
				currentPage: 1,
				pageSize: 6,
			}
		},
		methods:{
			//初始化数据
			initTravellerArticeList(){
				let orderBy = this.currentTab == "最新专栏"?"publish_time":"view";
				let params = {
					currentPage:this.currentPage,
					pageSize:this.pageSize,
					orderBy:orderBy,
				}
				this.$getRequest("/traveller/note/getByPage?"+this.$qs.stringify(params,{encode:false})).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.travellerArticelList.splice(0);
						this.travellerArticelList.push(...res.data.obj.list)
						this.total = res.data.obj.total
					}
				})
			},

			tabToggleClick(tabName){
				this.currentTab = tabName;
				this.initTravellerArticeList();
			},

			initRecomendWriter(){
				//默认获取5条推荐  /traveller/recommend?limit=10
				this.$getRequest("/traveller/recommend").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.recommedWriterList.splice(0);
						this.recommedWriterList.push(...res.data.obj)
					}
				})
			},

			/*分页*/
			handleCurrentChange(current){
				console.log(`当前页: ${current}`);
				this.currentPage = current
				this.initTravellerArticeList();
			},
		},
		created() {
			this.initTravellerArticeList();

			this.initRecomendWriter();
		},
	}
</script>

<style lang="less" scoped>
	.traveller-view{
		width: 100%;
		display: flex;
		.main{
			flex: 1;
			.article-nav{
				.active{
					background-color: rgba(255, 138, 0,0.7);
					color: white;
					border-bottom: 4px solid rgba(255, 138, 0,1);
				}
			}
		}
		.side{
			width: 400px;
			/*border: 1px solid #ddd;*/
      /*border-radius: 8px;*/
			padding: 0 10px;
			.join-us{
				margin-top: 20px;
				margin-bottom: 20px;
				display: flex;
				div{
					flex: 1;
					width: 180px;
					height: 180px;
					margin-left: 10px;
					margin-right: 10px;
				}
			}

			.writer-info{
				display: flex;
				padding: 20px;
				border-bottom: 1px dashed #ddd;
				.writer-avatar{
					width: 120px;
					height: 120px;
				}
				.txt{
					margin-left: 20px;
					height: 120px;
					.writer-name{
						height: 40px;
						line-height: 40px;
						font-size: 22px;
					}
					.writer-desc{
						display: -webkit-box;
						-webkit-box-orient: vertical;
						-webkit-line-clamp: 4;
						overflow: hidden;
					}
				}

			}
		}
	}

</style>
