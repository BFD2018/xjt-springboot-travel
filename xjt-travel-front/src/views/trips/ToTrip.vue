<template>
	<div class="trip-view">
		<div class="nav-container">
			<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
				<el-menu-item index="1">国内游</el-menu-item>
				<el-menu-item index="2">港澳台</el-menu-item>
				<el-menu-item index="3">东南亚</el-menu-item>
				<el-menu-item index="4">欧美游</el-menu-item>
				<el-menu-item index="5">日韩游</el-menu-item>
				<el-menu-item index="6">跟团游</el-menu-item>
			</el-menu>

			<div class="inputSearch">
				<el-input placeholder="请输入内容" v-model="input_route_name" class="input-with-select">
					<el-button slot="append" icon="el-icon-search" @click="handleInputKeyWord"></el-button>
				</el-input>
			</div>
		</div>

		<div class="mod-promo">
			<div class="mod-hd">
				<nav-bar>
					<div slot = "left-slot" class="nav-left">热门旅游路线</div>
					<div slot = "right-slot" class="nav-right">共{{total}}条</div>
				</nav-bar>
				<div class="mod-bd">
					<div class="bd-item" v-for="(item,index) in routeData" :key="index">
						<list-item>
							<div slot = "left-slot">
								<el-image class="hvr-grow"
										@click="$router.push('/travelnote/'+ item.id)"
													style="width: 100%; height: 100%"
													:src="item.routePhoto.split(',')[0]"
													fit="none"></el-image>
							</div>

							<div slot="right-slot">
								<div class="title hvr-underline-from-center"
										 @click="$router.push('trips/'+item.id)">{{item.title}}</div>
								<div class="price-buy">
									<span>￥{{item.price}}起</span>
									<span class="my-right">
										<el-button type="warning" round v-if="item.flag == 1"
															 @click="$router.push('trips/'+item.id)">立即预定</el-button>
										<el-button type="info" round v-else
															 disabled>已下架</el-button>
									</span>
								</div>
								<div class="view-count">
									<i class="el-icon-star-off"></i>
									{{item.counts}}</div>
							</div>
						</list-item>
					</div>
				</div>
			</div>
		</div>

		<!--分页-->
		<div style="margin-top: 20px;" class="my-border-padding">
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
</template>

<script>
	import NavBar from "@/components/navbar/NavBar.vue"
	import ListItem from "@/components/list-item/ListItem";

	export default {
		name: "ToTrip",
		components:{
			NavBar,
			ListItem,
		},
		data(){
			return{
				activeIndex:"1",
				routeCatagory:"1",
				title:"",

				input_route_name:"",

				routeData:[],
				//分页
				total:0,
				currentPage: 1,
				pageSizes: [3, 6, 9, 12],
				pageSize: 6,
			}
		},
		methods:{
			initTripsData(){
				//路线分类（国内游、自由行、东南亚、欧美游、日韩游、跟团游）
				let params = {
					category_id:this.routeCatagory,
					route_name:this.input_route_name,
					currentPage:this.currentPage,
					pageSize:this.pageSize,
				}
				this.$getRequest("/route/getByPage?"+this.$qs.stringify(params)).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.routeData.splice(0);
						this.routeData.push(...res.data.obj.records);
						this.total = res.data.obj.total;
					}else{
					  this.$message.error(res.data.msg);
          }
				})
			},
			handleSelect(key, keyPath) {
				this.routeCatagory = key;
				this.initTripsData();
			},

			handleInputKeyWord(){
				this.initTripsData();
			},

			/*分页*/
			handleSizeChange(size){
				console.log(`每页 ${size} 条`);
				this.pageSize = size
				this.initTripsData();
			},
			handleCurrentChange(current){
				console.log(`当前页: ${current}`);
				this.currentPage = current
				this.initTripsData();
			},
		},
		created() {
			this.initTripsData();
		}
	}
</script>

<style lang="less" scoped>
	.trip-view{
		width: 80%;
	}
	.nav-container{
		display: flex;
		.el-menu{
			flex: 1;
		}
		.inputSearch{
			width: 360px;
			line-height: 60px;
		}
	}
	.mod-promo{
		margin-top: 20px;
		.mod-hd{
			border-bottom: 1px solid #eee;
			.nav-left{
				border-left: 4px solid #fd8a4d;
				padding-left: 10px;
			}
			.nav-right{
				font-size: 22px;
				color: rgba(32, 152, 209,1);
			}
		}
		.bd-item{
			padding: 15px 10px;
			border-bottom: 1px dashed #ddd;
			&:hover{
				background-color: #eee;
			}
			.title{
				font-size: 24px;
				line-height: 60px;
				width: 100%;
				text-overflow: ellipsis;
				white-space: nowrap;
				overflow-x: hidden;
			}
			.price-buy{
				margin-top: 10px;
				span{
					color: #ff9d00;
					font-size: 24px;
					font-weight: bold;
					line-height: 40px;
				}
			}
			.view-count{
				line-height: 40px;
			}
		}
	}
</style>
