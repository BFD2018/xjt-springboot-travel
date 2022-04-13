<template>
	<div class="home-view">
		<div class="layout-left">
			<!--轮播图-->
			<div class="carousel-images">
				<el-carousel trigger="click" :interval="3000" indicator-position="outside" height="300px" arrow="always">
					<el-carousel-item v-for="(item,index) in carouselImages" :key="index">
						<div class="carousel-item" style="position: relative;overflow-y: hidden;">
							<el-image @click="$router.push('/travelnote/'+ item.id)"
												style="width: 100%; height: 100%"
												:src="item.coverImage"
												fit="none"></el-image>
							<div class="carousel-desc" style="position: absolute;left: 15%;top: 20px;color: white;">
								<div class="title" style="font-size: 32px;">{{item.title}}</div>
								<div class="location" style="margin-top: 10px;"><i class="el-icon-location"></i> {{item.location}}</div>
							</div>
						</div>
					</el-carousel-item>
				</el-carousel>
			</div>

			<el-divider/>

			<div class="main-content" style="width: 100%;display: flex;">
				<div class="main-left" style="flex:1;">
					<!--nav类型-->
					<div class="nav-container" style="line-height: 50px;display: flex">
						<div class="nav-left" style="flex: 1;display: flex;">
							<div class="navHot"
									 @click="tabToggleClick('热门游记')"
									 :class="{active:currentTab=='热门游记'}"
									 style="line-height: 50px;font-size: 24px;padding: 0 10px;">热门游记</div>
							<div class="navNew"
									 style="line-height: 50px;font-size: 24px;padding: 0 10px;"
									 :class="{active:currentTab=='最新发表'}"
									 @click="tabToggleClick('最新发表')">最新发表</div>
						</div>

						<div class="nav-right" style="width: 120px;line-height: 50px;">
							<el-button type="warning" @click="$router.push('/write/travelnote')">
								<i class="el-icon-plus"></i>
								写游记
							</el-button>
						</div>
					</div>

					<div class="main-table" v-loading="travelNoteListLoading">
						<div class="travel-item" v-for="(item,index) in travelNoteList" :key="index">
							<travel-note-item :travel="item"></travel-note-item>
						</div>
					</div>

					<!--分页-->
					<div class="home-pagination">
						<el-pagination
								background
								@size-change="handleSizeChange"
								@current-change="handleCurrentChange"
								:current-page="currentPage"
								:page-sizes="pageSizes"
								:page-size="pageSize"
								layout="total, sizes, prev, pager, next, jumper"
								:total="total">
						</el-pagination>
					</div>
				</div>

				<div class="main-right"
						 style="width: 400px;margin-left: 20px;">
					<div class="traveller-box">
						<nav-bar class = "navBarBox">
							<div slot = "left-slot">旅行家专栏</div>
							<div slot = "right-slot">
								<router-link to="/traveller">专栏首页</router-link>
							</div>
						</nav-bar>

						<div class="traveller-item" v-for="(item,index) in TravellerNoteList" :key="index">
							<traveller-item :article="item"></traveller-item>
						</div>
					</div>

					<div class="unknownTravel-box" style="margin-top: 20px">
						<nav-bar class = "navBarBox">
							<div slot = "left-slot">未知旅行实验室</div>
							<div slot = "right-slot">
								查看更多>
							</div>
						</nav-bar>
						<el-image
								style="width: 100%; height: 200px;"
								src="http://images.mafengwo.net/images/new-index/unknownTravel181120.png"
								fit="cover"></el-image>
					</div>

					<div class="learnUs-box" style="margin-top: 20px">
						<nav-bar class = "navBarBox">
							<div slot = "left-slot">了解我们</div>
							<div slot = "right-slot">
								查看更多>
							</div>
						</nav-bar>
						<el-image
								style="width: 100%; height: 200px;"
								src="https://b1-q.mafengwo.net/s14/M00/01/D0/wKgE2l1t1WqAOUvnAACxwvHwMhM32.jpeg"
								fit="cover"></el-image>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import TravelNoteItem from "./comps/TravelNoteItem"
	import TravellerItem from "./comps/TravellerItem.vue"
	import NavBar from "@/components/navbar/NavBar.vue"

	export default {
		name: "Home",
		components:{
			TravelNoteItem,
			TravellerItem,
			NavBar,
		},
		props:{
			inputSearchKey: {
				type:String,
			}
		},
		data(){
			return{
				currentTab:"热门游记",

				carouselImages:[],

				activeTagName:"navHot",

				travelNoteListLoading:true,

				travelNoteList:[],

				TravellerNoteList:[],

				//分页
				total:0,
				currentPage: 1,
				pageSizes: [3, 6, 9, 12],
				pageSize: 9,
			}
		},
		methods:{
			initTravelNoteList(){
				let params = {
					"type":this.currentTab =="热门游记"?1:0,
					"title":this.inputSearchKey,
					"currentPage":this.currentPage,
					"pageSize":this.pageSize
				}
				let baseUrl = "/travelNote/getByTitleType?" + this.$qs.stringify(params,{encode:false});

				this.$getRequest(baseUrl).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.travelNoteList.splice(0);
						this.travelNoteList.push(...res.data.obj.records);
						this.total = Number(res.data.obj.total);
						this.travelNoteListLoading = false;
					}else{
						this.$message.error(res.data.msg);
					}
				})
			},

			initCarousel(){
				//按照点赞数获取6个轮播数据
				this.$getRequest("/travelNote/getByUpDesc").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.carouselImages.splice(0);
						this.carouselImages.push(...res.data.obj);
					}
				})
			},

			initTravellerNoteList(){
				this.$getRequest("/traveller/note/getByTime?limit=4").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.TravellerNoteList.splice(0);
						this.TravellerNoteList.push(...res.data.obj);
					}
				})
			},

			tabToggleClick(tabName){
				this.currentTab = tabName;
				this.initTravelNoteList();
			},


			/*分页*/
			handleSizeChange(size){
				console.log(`每页 ${size} 条`);
				this.pageSize = size
				this.initTravelNoteList();
			},
			handleCurrentChange(current){
				console.log(`当前页: ${current}`);
				this.currentPage = current
				this.initTravelNoteList();
			},
		},
		created() {
			this.initTravelNoteList();

			this.initCarousel();

			this.initTravellerNoteList();
		},
		watch:{
			//监测名-----精简写法
			// inputSearchKey(newValue,oldValue){
			// 	console.log('inputSearchKey被改了',newValue,oldValue)
			// 	this.inputSearchKey = newValue;
			// 	this.initTravelNoteList();
			// }
		}
	}
</script>

<style lang="less" scoped>
	.home-view{
		.layout-left{
			position: relative;
			flex: 1;
			box-shadow: 0 0 3px #dddddd;
			.carousel-images{
				width: 90%;
				margin: 0 auto;
			}
			.main-content{
				.main-left{
					.nav-container{
						.active{
							background-color: rgba(255, 138, 0,0.7);
							color: white;
							border-bottom: 4px solid rgba(255, 138, 0,1);
						}
					}
				}
			}

			.home-pagination{
				position: absolute;
				left: 0;
				bottom: 0;
				border: 1px solid #ddd;
				padding: 10px;
				border-radius: 5px;
			}
		}
	}
</style>
